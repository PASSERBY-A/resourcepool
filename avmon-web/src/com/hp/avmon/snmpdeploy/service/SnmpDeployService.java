package com.hp.avmon.snmpdeploy.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import sun.misc.BASE64Encoder;

import com.hp.avmon.common.Config;
import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.config.service.AgentManageService;
import com.hp.avmon.deploy.store.AgentStore;
import com.hp.avmon.deploy.web.AgentTypes;
import com.hp.avmon.equipmentCenter.service.EquipmentCenterService;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.Utils;
import com.hp.avmonserver.api.AvmonServer;
import com.hp.avmonserver.entity.Agent;
import com.hp.avmonserver.entity.MO;

@Service
public class SnmpDeployService {

	private static final Logger logger = LoggerFactory
			.getLogger(SnmpDeployService.class);

	// private static volatile List<HashMap> alarmList=null;

	// private static volatile Map<String,Alarm> alarmCache=null;

	// private final static Object lock = new Object();

	@Autowired
	private AvmonServer avmonServer;

	@Autowired
	private JdbcTemplate jdbc;
	
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private EquipmentCenterService equipmentCenterService;

	public SnmpDeployService() {

	}

	private List<Map> getMoTree(String userId, String parentId) {

		// parentId=Utils.decodeString(parentId);

		/*
		 * String sql=String.format("select a.id as \"id\"," +
		 * "a.text as \"text\"," +
		 * "case when a.agent_status=2 then 'icon-pause' when b.status=1 then iconCls else iconCls||'-b' end as \"iconCls\","
		 * + "a.leaf as \"leaf\"," + "a.expanded as \"expanded\"," +
		 * "b.last_update_time as \"agentLastUpdateTime\"," +
		 * "case when a.agent_status=2 then '暂停' when b.status=1 then '正在运行' else '未知' end as \"agentStatus\","
		 * + "a.view_template as \"viewTemplate\" " +
		 * "from stg_avmon_performance_tree a " +
		 * "left join (select id,to_char(last_update_time,'YYYY-MM-DD HH24:MI:SS') as last_update_time, case when last_update_time>(sysdate-1/24) then 1 else 0 end as status from STG_AVMON_AVAILABLE_SUMMARY) b "
		 * + "on a.id=b.id " + "where a.pid='%s'", parentId);
		 * //logger.info(sql);
		 */
		// if(parentId.equals("root"))parentId="1";
		String sql = String.format(SqlManager.getSql(this, "menu_tree"),
				parentId, parentId);

		List<Map> list = jdbc.queryForList(sql);
		// BASE64Encoder base64=new BASE64Encoder();
		// for(Map map:list){
		//
		// String id=(String) map.get("id");
		// id=Utils.encodeString(id);
		// String pid=(String) map.get("pid");
		// pid=Utils.encodeString(pid);
		//
		// map.put("id", id);
		// map.put("pid", pid);
		// }
		return list;
	}

	public String getMoTreeJson(String userId, String parentId) {

		List<Map> list = getMoTree(userId, parentId);
		return JackJson.fromObjectHasDateToJson(list);
	}

	// 修改moId的状态
	public String changeMoStatus(String moId, String status) {

		StringBuffer sb = new StringBuffer();
		sb.append(" update TD_AVMON_MO_INFO ");
		sb.append(" set ");
		sb.append(" AGENT_STATUS = '" + status + "'");
		sb.append(" where ");
		sb.append(" MO_ID = '" + moId + "'");

		return jdbc.update(sb.toString()) > 0 ? "01" : "00";
	}

	private void getMoProperty(List listAttr, String type, String moId) {
		// TODO Auto-generated method stub
		// id,typeId,propId,propName,typeName,value
		String sql = String
				.format("select a.type_id||'.'||a.name as \"id\",a.type_id as \"typeId\",a.name as \"propId\",a.caption as \"propName\",c.caption as \"typeName\",b.value as \"value\",c.parent_id,a.KPI_CODE "
						+ "from td_avmon_snmp_type_attribute a left join TD_AVMON_SNMP_INFO_ATTRIBUTE b "
						+ "on (a.name=b.name and b.mo_id='%s'),TD_AVMON_SNMP_TYPE c "
						+ "where a.type_id=c.type_id and a.type_id='%s' order by a.order_index",
						moId, type);
		// logger.info(sql);
		List<Map> list = jdbc.queryForList(sql);
		if (list != null) {
			int n = 0;

			for (Map m : list) {
				int index = listAttr.size() + 1;
				m.put("index", index);
				if (m.get("KPI_CODE") != null && !m.get("KPI_CODE").equals("")) {
					m.put("propName",
							"<img width=10 height=10 src='../resources/images/menus/lock.gif'/>"
									+ m.get("propName"));
				}
				listAttr.add(m);

			}

		}

		sql = String.format(
				"select parent_id from TD_AVMON_SNMP_TYPE where type_id='%s'",
				type);
		list = jdbc.queryForList(sql);
		if (list != null && list.size() > 0) {
			String parentTypeId = (String) list.get(0).get("PARENT_ID");
			getMoProperty(listAttr, parentTypeId, moId);
		}
	}

	public Map getMoDetail(String moId, HttpServletRequest request) {
		String sql = String.format(
				"select * from td_avmon_snmp_info where mo_id='%s'", moId);
		List listMap = jdbc.queryForList(sql);
		Map map = null;
		MO mo = null;
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		if (listMap.size() > 0) {
			map = (Map) listMap.get(0);
		}
		if (map != null) {
			List listAttr = new ArrayList();
			// id,typeId,propId,propName,typeName,value
			// put basic property first
			Map m;
			// m=new HashMap();
			// m.put("index",1);
			// m.put("id","basic-mo-id");
			// m.put("typeId", "basic");
			// m.put("propId", "basic-mo-id");
			// m.put("propName", "ID");
			// m.put("typeName", " 基本属性");
			// m.put("value", map.get("MO_ID"));
			// listAttr.add(m);
			m = new HashMap();
			m.put("index", 1);
			m.put("id", "basic-mo-caption");
			m.put("typeId", "basic");
			m.put("propId", "basic-mo-caption");
			m.put("propName", bundle.getString("name"));
			m.put("typeName", bundle.getString("basicParameters"));
			m.put("value", map.get("CAPTION"));
			listAttr.add(m);
			m = new HashMap();
			m.put("index", 2);
			m.put("id", "basic-mo-description");
			m.put("typeId", "basic");
			m.put("propId", "basic-mo-description");
			m.put("propName", bundle.getString("desicription"));
			m.put("typeName", bundle.getString("basicParameters"));
			m.put("value", map.get("DESCRIPTION"));
			listAttr.add(m);
			m = new HashMap();
			m.put("index", 3);
			m.put("id", "basic-mo-type");
			m.put("typeId", "basic");
			m.put("propId", "basic-mo-type");
			m.put("propName", bundle.getString("objectType"));
			m.put("typeName", bundle.getString("advancedConfigurationCarefully"));
			m.put("value", map.get("TYPE_ID"));
			listAttr.add(m);
			m = new HashMap();
			m.put("index", 4);
			m.put("id", "basic-mo-agent");
			m.put("typeId", "basic");
			m.put("propId", "basic-mo-agent");
			m.put("propName",
					"<img width=10 height=10 src='../resources/images/menus/lock.gif'/>"
							+ "Agent ID");
			m.put("typeName", bundle.getString("advancedConfigurationCarefully"));
			m.put("value", map.get("agent_id"));
			listAttr.add(m);
			// put extra property from each type ,include parent type
			getMoProperty(listAttr, (String) map.get("TYPE_ID"), moId);
			map.put("properties", listAttr);
			map.put("propertyTotal", listAttr.size());
		} else {
			map = new HashMap();
			map.put("properties", new ArrayList());
			map.put("propertyTotal", 0);
		}
		return map;
	}
	

	//modify oidmanage
	public Map getMoDetail_oidmanage(String moId, HttpServletRequest request) {
		String sql = String.format(
				"select * from td_avmon_snmpoid_info where mo_id='%s'", moId);
		List listMap = jdbc.queryForList(sql);
		Map map = null;
		MO mo = null;
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		if (listMap.size() > 0) {
			map = (Map) listMap.get(0);
		}
		if (map != null) {
			List listAttr = new ArrayList();
			// id,typeId,propId,propName,typeName,value
			// put basic property first
			Map m;
			// m=new HashMap();
			// m.put("index",1);
			// m.put("id","basic-mo-id");
			// m.put("typeId", "basic");
			// m.put("propId", "basic-mo-id");
			// m.put("propName", "ID");
			// m.put("typeName", " 基本属性");
			// m.put("value", map.get("MO_ID"));
			// listAttr.add(m);
			m = new HashMap();
			m.put("index", 1);
			m.put("id", "basic-mo-caption");
			m.put("typeId", "basic");
			m.put("propId", "basic-mo-caption");
			m.put("propName", bundle.getString("name"));
			m.put("typeName", bundle.getString("basicParameters"));
			m.put("value", map.get("CAPTION"));
			listAttr.add(m);
			m = new HashMap();
			m.put("index", 2);
			m.put("id", "basic-mo-description");
			m.put("typeId", "basic");
			m.put("propId", "basic-mo-description");
			m.put("propName", bundle.getString("desicription"));
			m.put("typeName", bundle.getString("basicParameters"));
			m.put("value", map.get("DESCRIPTION"));
			listAttr.add(m);
			m = new HashMap();
			m.put("index", 3);
			m.put("id", "basic-mo-type");
			m.put("typeId", "basic");
			m.put("propId", "basic-mo-type");
			m.put("propName", bundle.getString("objectType"));
			m.put("typeName", bundle.getString("advancedConfigurationCarefully"));
			m.put("value", map.get("TYPE_ID"));
			listAttr.add(m);
			m = new HashMap();
			m.put("index", 4);
			m.put("id", "basic-mo-agent");
			m.put("typeId", "basic");
			m.put("propId", "basic-mo-agent");
			m.put("propName",
					"<img width=10 height=10 src='../resources/images/menus/lock.gif'/>"
							+ "Agent ID");
			m.put("typeName", bundle.getString("advancedConfigurationCarefully"));
			m.put("value", map.get("agent_id"));
			listAttr.add(m);
			
			
			m = new HashMap();
			m.put("index", 5);
			m.put("id", "basic-mo-appindex");
			m.put("typeId", "basic");
			m.put("propId", "basic-mo-appindex");
			m.put("propName", "实例号");
			m.put("typeName", bundle.getString("advancedConfigurationCarefully"));
			m.put("value", map.get("app_index"));
			listAttr.add(m);			
			
			// put extra property from each type ,include parent type
//			getMoProperty(listAttr, (String) map.get("TYPE_ID"), moId);
			map.put("properties", listAttr);
			map.put("propertyTotal", listAttr.size());
		} else {
			map = new HashMap();
			map.put("properties", new ArrayList());
			map.put("propertyTotal", 0);
		}
		return map;
	}	

	public Map saveMoProperty(String moId, String props) {
		String[] ss = props.split(",");
		for (String prop : ss) {
			String[] tmp = prop.split("=");
			if (tmp.length == 2) {
				String propName = tmp[0];
				String propValue = tmp[1];
				saveMoProperty(moId, propName, propValue);
			}
		}
		return null;
	}
	

	public Map saveMoProperty_oidmanage(String moId, String props) {
		String[] ss = props.split(",");
		for (String prop : ss) {
			String[] tmp = prop.split("=");
			if (tmp.length == 2) {
				String propName = tmp[0];
				String propValue = tmp[1];
				saveMoProperty_oidmanage(moId, propName, propValue);
			}
		}
		return null;
	}

	private void saveMoProperty(String moId, String propName, String propValue) {
		if (propName.equals("basic-mo-caption")) {
			String sql = String
					.format("update td_avmon_snmp_info set caption='%s' where mo_id='%s'",
							propValue, moId);
			jdbc.execute(sql);
		} else if (propName.equals("basic-mo-description")) {
			String sql = String
					.format("update td_avmon_snmp_info set description='%s' where mo_id='%s'",
							propValue, moId);
			jdbc.execute(sql);
		} else if (propName.equals("basic-mo-type")) {
			String sql = String
					.format("update td_avmon_snmp_info set type_id='%s' where mo_id='%s'",
							propValue, moId);
			jdbc.execute(sql);
		} else if (propName.equals("basic-mo-agent")) {
			String sql = String
					.format("update td_avmon_snmp_info set agent_id='%s' where mo_id='%s'",
							propValue, moId);
			jdbc.execute(sql);
		} else {
			String sqlUpdate = String
					.format("update td_avmon_snmp_info_ATTRIBUTE set value='%s' where name='%s' and mo_id='%s'",
							propValue, propName, moId);
			String sqlInsert = String
					.format("insert into td_avmon_snmp_info_ATTRIBUTE (value,name,mo_id) values('%s','%s','%s')",
							propValue, propName, moId);
			if (jdbc.update(sqlUpdate) < 1) {
				jdbc.execute(sqlInsert);
			}
		}
	}
	

	private void saveMoProperty_oidmanage(String moId, String propName, String propValue) {
		if (propName.equals("basic-mo-caption")) {
			String sql = String
					.format("update td_avmon_snmpoid_info set caption='%s' where mo_id='%s'",
							propValue, moId);
			jdbc.execute(sql);
		} else if (propName.equals("basic-mo-description")) {
			String sql = String
					.format("update td_avmon_snmpoid_info set description='%s' where mo_id='%s'",
							propValue, moId);
			jdbc.execute(sql);
		} else if (propName.equals("basic-mo-type")) {
			String sql = String
					.format("update td_avmon_snmpoid_info set type_id='%s' where mo_id='%s'",
							propValue, moId);
			jdbc.execute(sql);
		} else if (propName.equals("basic-mo-agent")) {
			String sql = String
					.format("update td_avmon_snmpoid_info set agent_id='%s' where mo_id='%s'",
							propValue, moId);
			jdbc.execute(sql);
		} else if (propName.equals("basic-mo-appindex")) {
			String sql = String
					.format("update td_avmon_snmpoid_info set app_index='%s' where mo_id='%s'",
							propValue, moId);
			jdbc.execute(sql);
		}
		
		
		
		else {
			String sqlUpdate = String
					.format("update td_avmon_snmp_info_ATTRIBUTE set value='%s' where name='%s' and mo_id='%s'",
							propValue, propName, moId);
			String sqlInsert = String
					.format("insert into td_avmon_snmp_info_ATTRIBUTE (value,name,mo_id) values('%s','%s','%s')",
							propValue, propName, moId);
			if (jdbc.update(sqlUpdate) < 1) {
				jdbc.execute(sqlInsert);
			}
		}
	}

	public Map deleteMo(String moId) {
		String sql = String.format(
				"delete from td_avmon_snmp_info_ATTRIBUTE where mo_id='%s' ",
				moId);
		jdbc.execute(sql);
		sql = String.format("delete from td_avmon_snmp_info where mo_id='%s' ",
				moId);
		jdbc.execute(sql);
		sql = String.format(
				"delete from TF_AVMON_ALARM_DATA where mo_id='%s' ", moId);
		jdbc.execute(sql);
		sql = String.format(
				"delete from TF_AVMON_ALARM_HISTORY where mo_id='%s' ", moId);
		jdbc.execute(sql);
		sql = String.format("delete from TF_AVMON_KPI_VALUE where mo_id='%s' ",
				moId);
		jdbc.execute(sql);
//		sql = String.format(
//				"delete from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='%s' ",
//				moId);
//		jdbc.execute(sql);
		// 同步
		// ...
		// return
		Map m = new HashMap();
		m.put("success", true);
		m.put("moId", moId);
		return m;
	}
	

	public Map deleteMo_oidmanage(String moId) {
		String sql = String.format(
				"delete from td_avmon_snmp_info_ATTRIBUTE where mo_id='%s' ",
				moId);
		jdbc.execute(sql);
		sql = String.format("delete from td_avmon_snmpoid_info where mo_id='%s' ",
				moId);
		jdbc.execute(sql);
		sql = String.format(
				"delete from TF_AVMON_ALARM_DATA where mo_id='%s' ", moId);
		jdbc.execute(sql);
		sql = String.format(
				"delete from TF_AVMON_ALARM_HISTORY where mo_id='%s' ", moId);
		jdbc.execute(sql);
		sql = String.format("delete from TF_AVMON_KPI_VALUE where mo_id='%s' ",
				moId);
		jdbc.execute(sql);
//		sql = String.format(
//				"delete from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) where mo_id='%s' ",
//				moId);
//		jdbc.execute(sql);
		// 同步
		// ...
		// return
		Map m = new HashMap();
		m.put("success", true);
		m.put("moId", moId);
		return m;
	}	

	// changed by mark start,add businessType
	public Map createMo(String moId, String moType, String moCaption,
			String agentId, String businessType, HttpServletRequest request) {
		Map map = new HashMap();
		String sql = String.format(
				"select count(*) from td_avmon_snmp_info where mo_id='%s'", moId);
		int c = jdbc.queryForInt(sql);
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		if (c > 0) {
			map.put("errorMsg", bundle.getString("theNumberAlreadyExists"));
			return map;
		}
		sql = String.format(
				"select count(*) from td_avmon_snmp_type where type_id='%s'",
				moType);
		c = jdbc.queryForInt(sql);
		if (c == 0) {
			map.put("errorMsg", bundle.getString("theObjectNotExist"));
			return map;
		}

		if (agentId == null || agentId.equals("")) {
			agentId = moId;
		}

		// modify start
		sql = String
				.format("insert into td_avmon_snmp_info(mo_id,type_id,caption,agent_id,parent_id) values('%s','%s','%s','%s','%s')",
						moId, moType, moCaption, agentId, moType);
		jdbc.execute(sql);
		// add by mark end
		// add by mark sta
		if(!"数据中心".equals(businessType) && null != businessType){
			sql = String
					.format("insert into td_avmon_snmp_info_ATTRIBUTE(mo_id,name,value) values('%s','%s','%s')",
							moId, "businessSystem", businessType);
			jdbc.execute(sql);	
		}
		
		// add end
		map.put("success", true);
		return map;
	}
	
	
	public Map createMo_oidmanage(String moId, String moType, String moCaption,
			String agentId, String businessType, String instanceNumbers, HttpServletRequest request) {
		Map map = new HashMap();
		String sql = String.format(
				"select count(*) from td_avmon_snmpoid_info where mo_id='%s'", moId);
		int c = jdbc.queryForInt(sql);
		Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		if (c > 0) {
 			map.put("errorMsg", bundle.getString("theNumberAlreadyExists"));
			return map;
		}
		sql = String.format(
				"select count(*) from td_avmon_snmp_type where type_id='%s'",
				moType);
		c = jdbc.queryForInt(sql);
		if (c == 0) {
			map.put("errorMsg", bundle.getString("theObjectNotExist"));
			return map;
		}

		if (agentId == null || agentId.equals("")) {
			agentId = moId;
		}

		// modify start
		sql = String
				.format("insert into td_avmon_snmpoid_info(mo_id,type_id,caption,agent_id,parent_id,app_index) values('%s','%s','%s','%s','%s','%s')",
						moId, moType, moCaption, agentId, moType, instanceNumbers);
		jdbc.execute(sql);
		// add by mark end
		// add by mark sta
		if(!"数据中心".equals(businessType) && null != businessType){
			sql = String
					.format("insert into td_avmon_snmp_info_ATTRIBUTE(mo_id,name,value) values('%s','%s','%s')",
							moId, "businessSystem", businessType);
			jdbc.execute(sql);	
		}
		
		// add end
		map.put("success", true);
		return map;
	}	

	public Map getMonitorProperty(String moId, String monitorInstanceId) {
		String sql = String
				.format("select c.instance_id as \"monitorInstanceId\",a.name as \"propId\","
						+ "a.caption as \"propName\",b.value as \"value\",a.order_index "
						+ " from TD_AVMON_MONITOR_ATTR a  left join TD_AVMON_MONITOR_INST_ATTR b "
						+ " on (a.name=b.name and b.AMP_INST_ID='%s' and b.mo_id='%s') ,TD_AVMON_MONITOR_INSTANCE c "
						+ "where a.monitor_id=c.monitor_id and c.instance_id='%s'",
						monitorInstanceId, moId, monitorInstanceId);
		// logger.debug(sql);
		List<Map> list = jdbc.queryForList(sql);

		Map map = new HashMap();
		if (list != null) {
			map.put("rows", list);
			map.put("total", list.size());
		} else {
			map.put("rows", new ArrayList());
			map.put("total", 0);
		}
		return map;
	}

	public Map getAmpList() {
		String sql = SqlManager.getSql(this, "getAmpList");
		List<Map> list = jdbc.queryForList(sql);

		Map map = new HashMap();
		if (list != null) {
			map.put("rows", list);
			map.put("total", list.size());
		} else {
			map.put("rows", new ArrayList());
			map.put("total", 0);
		}
		return map;
	}

	public List getAmpInstanceList(String agentId, String ampId) {
		String con = "";
		if (agentId != null && !agentId.equals("")) {
			con += " and a.agent_id='" + agentId + "'";
		}
		if (ampId != null && !ampId.equals("")) {
			con += " and a.amp_id='" + ampId + "'";
		}
		
		String sql = String.format(
				SqlManager.getSql(this, "getAmpInstanceList"), con);
		// logger.debug(sql);
		List<Map> list = jdbc.queryForList(sql);

		if (list != null) {
			for (Map map : list) {
				String schedule = (String) map.get("schedule");
				if (schedule != null) {
					String[] s = schedule.split(" ");
					for (int i = 0; i < s.length; i++) {
						int n = i + 1;
						map.put("schedule" + n, s[i]);
					}
				}
			}
		}

		return list;

		// Map map=new HashMap();
		// if(list!=null){
		// map.put("rows",list);
		// map.put("total", list.size());
		// }
		// else{
		// map.put("rows",new ArrayList());
		// map.put("total", 0);
		// }
		// return map;
	}

	public Map addMonitor(String moId, String monitorType) {
		String agentId = moId;
		String sql = String.format(
				"select agent_id from TD_AVMON_MO_INFO where mo_id='%s'", moId);
		Map mapAgent = jdbc.queryForMap(sql);
		if (mapAgent != null) {
			agentId = (String) mapAgent.get("AGENT_ID");
			if (agentId == null || agentId.equals("")) {
				agentId = moId;
			}
		}

		String instanceId = monitorType.toLowerCase();
		sql = String
				.format("select count(1) from TD_AVMON_MONITOR_INSTANCE where agent_host='%s' and instance_id='%s'",
						agentId, instanceId);
		int n = jdbc.queryForInt(sql);
		int index = 0;
		if (n > 0) {
			for (int i = 2; i < 1000; i++) {
				instanceId = monitorType.toLowerCase() + i;
				sql = String
						.format("select count(1) from TD_AVMON_MONITOR_INSTANCE where agent_host='%s' and instance_id='%s'",
								agentId, instanceId);
				n = jdbc.queryForInt(sql);
				if (n == 0) {
					index = i;
					break;
				}
			}
		}
		// create
		String version = "1.0";
		String caption = monitorType;
		sql = String
				.format("select caption,version from TD_AVMON_MONITOR where monitor_id='%s'",
						monitorType);
		Map map = jdbc.queryForMap(sql);
		if (map != null) {
			caption = (String) map.get("CAPTION");
			version = (String) map.get("VERSION");
			if (index > 0) {
				if (index < 10) {
					caption += "-0" + index;
				} else {
					caption += "-" + index;
				}
			}
		}
		String sqlInsert = String
				.format("INSERT INTO TD_AVMON_MONITOR_INSTANCE ( MONITOR_ID, TARGET_ID, "
						+ "IS_INSTANCE, INSTANCE_ID, IS_DEPLOYED, VERSION, ENABLE_FLAG,"
						+ " ATTRIBUTE, IS_PARENT_SCHEDULE, SCHEDULE, IS_PARENT_THRESHOLD, "
						+ "THRESHOLD, AGENT_HOST, CAPTION) VALUES ('%s' ,'%s' , 1 , '%s' , 0 ,"
						+ " '%s', 1, '', 1, '0 0 * * * *' , 1 ,'' ,'%s' , '%s')",
						monitorType, moId, instanceId, version, agentId,
						caption);
		jdbc.execute(sqlInsert);

		Map m = new HashMap();
		m.put("success", true);
		m.put("instanceId", instanceId);
		m.put("moId", moId);
		m.put("monitorType", monitorType);
		return m;
	}

	public Map deleteMonitor(String moId, String instanceId) {
		// String
		// sql=String.format("delete from TD_AVMON_MONITOR_INSTANCE where target_id='%s' and instance_id='%s'",
		// moId,instanceId);
		// jdbc.execute(sql);
		//
		// Map m=new HashMap();
		// m.put("success", true);
		// m.put("instanceId", instanceId);
		// m.put("moId", moId);
		// return m;
		return null;
	}

	public Map deployMonitor(String agentId, String ampInstanceId) {
		logger.debug("deploy amp {} - {}", agentId, ampInstanceId);

		if(null==avmonServer){
			avmonServer = SpringContextHolder.getBean("avmonServer");
		}
		String s = avmonServer.deployAmpPackage(agentId, ampInstanceId);

		Agent agent = AgentStore.getAgent(agentId);

		if (s == null)
			s = "未知错误";
		Map m = new HashMap();
		m.put("success", s.startsWith("00:"));
		m.put("instanceId", ampInstanceId);
		if (s.startsWith("00:")) {
			s = "下发成功.";
		}
		m.put("msg", agent.getIp() + "(id=" + agentId + ",inst="
				+ ampInstanceId + "): " + s);
		m.put("moId", agentId);

		return m;

	}

	public Map saveMonitor(String moId, String instanceId, String props,
			String caption, String scheduleType, String schedule) {

		String sql = String
				.format("update TD_AVMON_MONITOR_INSTANCE set caption='%s',is_parent_schedule=%s,schedule='%s' "
						+ "where target_id='%s' and instance_id='%s'", caption,
						scheduleType, schedule, moId, instanceId);
		jdbc.execute(sql);

		// save props
		String[] ss = props.split(",");
		for (String prop : ss) {
			String[] tmp = prop.split("=");
			if (tmp.length == 2) {
				String propName = tmp[0];
				String propValue = tmp[1];
				saveMonitorProperty(moId, instanceId, propName, propValue);
			}
		}

		Map m = new HashMap();
		m.put("success", true);
		m.put("instanceId", instanceId);
		m.put("moId", moId);
		return m;
	}

	private void saveMonitorProperty(String moId, String instanceId,
			String propName, String propValue) {
		String sqlUpdate = String
				.format("update TD_AVMON_MONITOR_INST_ATTR set value='%s' where name='%s' and mo_id='%s' and AMP_INST_ID='%s'",
						propValue, propName, moId, instanceId);
		String sqlInsert = String
				.format("insert into TD_AVMON_MONITOR_INST_ATTR (value,name,mo_id,AMP_INST_ID) values('%s','%s','%s','%s')",
						propValue, propName, moId, instanceId);
		if (jdbc.update(sqlUpdate) < 1) {
			jdbc.execute(sqlInsert);
		}
	}

	public Map addMo(String moId, String agentId, String type, String caption,
			String ip, String os, String osVersion, boolean overwrite) {

		// 注册agent
		if (agentId != null && agentId != "") {
			String sql = String.format(
					"select count(1) from TD_AVMON_AGENT where agent_id='%s'",
					agentId);
			int n = jdbc.queryForInt(sql);
			if (n == 0) {
				sql = String
						.format("insert into TD_AVMON_AGENT(agent_id,os,ip) values('%s','%s','%s')",
								agentId, os, ip);
				jdbc.execute(sql);
			}
		}

		// 添加对象
		Map m = new HashMap();
		m.put("success", true);
		m.put("moId", moId);

		String sql = String
				.format("select count(1) from TD_AVMON_MO_INFO where mo_id='%s' ",
						moId);
		int n = jdbc.queryForInt(sql);
		if (n > 0) {
			return m;
			// 不覆盖目标对象，直接返回
			// if(overwrite){
			// sql=String.format("delete from TD_AVMON_MO_INFO where mo_id='%s'",moId);
			// jdbc.execute(sql);
			// }
			// else{
			// m.put("success", false);
			// m.put("msg", "该对象编号已经存在！");
			// return m;
			// }
		}

		String sqlInsert = String
				.format("INSERT INTO TD_AVMON_MO_INFO ( MO_ID, TYPE_ID, CAPTION, agent_id) VALUES ('%s' ,'%s' ,'%s' ,'%s')",
						moId, type, ip, agentId);
		jdbc.execute(sqlInsert);
		sql = String
				.format("delete from TD_AVMON_MO_INFO_ATTRIBUTE where mo_id='%s'",
						moId);
		jdbc.execute(sql);
		sql = String
				.format("insert into TD_AVMON_MO_INFO_ATTRIBUTE(mo_id,name,value) values('%s','%s','%s')",
						moId, "os", os);
		jdbc.execute(sql);
		sql = String
				.format("insert into TD_AVMON_MO_INFO_ATTRIBUTE(mo_id,name,value) values('%s','%s','%s')",
						moId, "ip", ip);
		jdbc.execute(sql);
		sql = String
				.format("insert into TD_AVMON_MO_INFO_ATTRIBUTE(mo_id,name,value) values('%s','%s','%s')",
						moId, "osVersion", osVersion);
		jdbc.execute(sql);

		return m;
	}

	public Map getDiscoveryList(String startIp, String endIp) {
		String sql = String
				.format("select a.mo_id as \"moId\",type_id as \"type\","
						+ "caption as \"caption\",ip as \"ip\",os as \"os\","
						+ "version as \"version\",is_deployed as \"deployed\","
						+ "b.status as \"status\" from TD_AVMON_DISCOVERY a left join (select mo_id,'成功:'||sum(deploy_ok)||',失败:'||(count(1)-sum(deploy_ok)) as status from TD_AVMON_DISCOVERY_RESULT group by mo_id) b on a.mo_id=b.mo_id order by ip");
		List<Map> list = jdbc.queryForList(sql);

		Map map = new HashMap();
		if (list != null) {
			map.put("rows", list);
			map.put("total", list.size());
		} else {
			map.put("rows", new ArrayList());
			map.put("total", 0);
		}
		return map;
	}

	public Map deployDiscoverys(String moIds) {
		String sql = "truncate table TD_AVMON_DISCOVERY_RESULT";
		jdbc.execute(sql);

		logger.info("Deploy Mos ={}", moIds);

		Map map = new HashMap();
		List<Map> list = new ArrayList<Map>();
		for (String moId : moIds.split(",")) {
			if (!moId.equals("")) {
				list.add(deployDiscovery(moId));
			}
		}

		// try {
		// Thread.sleep(5000);
		// } catch (InterruptedException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		//
		// for(Map mo:list){
		// String json=avmonServer.getDeployMonitorResult(mo.get("moId"));
		// Map result=JackJson.fromJsonToObject(json, Map.class);
		// StringBuffer log=(StringBuffer) mo.get("log");
		// log.append(result.get("log"));
		// }
		// List<String> results=avmonServer.getDeployMonitorResult(moIds);
		// for(String result:results){
		// Map m=JackJson.fromJsonToObject(result, Map.class);
		// if()
		// if(m.get("moId").equals(obj))
		// }

		map.put("items", list);
		return map;
	}

	public Map deployDiscovery(String moId) {
		if (moId == null || moId.equals("")) {
			return null;
		}
		logger.info("Deploy Mo ={}", moId);
		String sql = String.format(
				"delete from TD_AVMON_DISCOVERY_RESULT where mo_id='%s'", moId);
		jdbc.execute(sql);
		// String s=avmonServer.deployMo(moId);
		sql = String
				.format("SELECT T.MO_ID, T.TYPE_ID, T.CAPTION,T.IP, T.OS, T.VERSION FROM TD_AVMON_DISCOVERY T where mo_id='%s'",
						moId);

		Map<String, Object> map = jdbc.queryForMap(sql);
		String type = (String) map.get("TYPE_ID");
		String caption = (String) map.get("CAPTION");
		String ip = (String) map.get("IP");
		String os = (String) map.get("OS");
		String osVersion = (String) map.get("VERSION");

		Map m = addMo(moId, moId, type, caption, ip, os, osVersion, true);

		StringBuffer log = new StringBuffer();
		if ((Boolean) m.get("success")) {
			String s = deployDefaultMonitors(log, moId, type);
			m.put("msg", s);
			if (s.equals("")) {
				sql = String
						.format("update TD_AVMON_DISCOVERY set is_deployed=1,deploy_result='部署成功' where mo_id='%s'",
								moId);
				jdbc.execute(sql);
				m.put("result", "部署成功");
				log.append("部署成功.\n");
			}
		}

		String s = (String) m.get("msg");
		if (!s.equals("")) {
			sql = String
					.format("update TD_AVMON_DISCOVERY set is_deployed=0,deploy_result='部署失败：%s' where mo_id='%s'",
							s, moId);
			jdbc.execute(sql);
			m.put("result", "部署失败");
			log.append("部署失败.");
		}

		m.put("moId", moId);
		m.put("log", log);

		return m;
	}

	private String deployDefaultMonitors(StringBuffer log, String moId,
			String type) {

		// 清除目标对象已经存在的monitor实例
		String sql = String.format(
				"delete from TD_AVMON_MONITOR_INSTANCE where target_id='%s'",
				moId);
		jdbc.execute(sql);

		// 部署默认monitor
		sql = String.format(
				"select * from TD_AVMON_MONITOR where target_mo_type='%s'",
				type);
		List<Map> list = jdbc.queryForList(sql);
		String result = "";
		for (Map map : list) {
			String monitorId = (String) map.get("MONITOR_ID");
			Map monitor = addMonitor(moId, monitorId);
			String monitorInstanceId = (String) monitor.get("instanceId");
			if (monitorInstanceId == null)
				return String.format("未能创建%s的实例.", monitorId);
			Map m = deployMonitor(moId, monitorInstanceId);
			if (m.get("msg") != null && !m.get("msg").equals("")) {
				result += m.get("msg");
				log.append(String.format("部署%s(%s)失败:%s\n", monitorId,
						monitorInstanceId, m.get("msg")));
			} else {
				// log.append(String.format("部署%s(%s)成功!\n",monitorId,monitorInstanceId));
			}
		}
		return result;
	}

	public Map getDeployResult(String moId) {
		String sql = String.format("select mo_id as \"moId\","
				+ "monitor_id as \"monitorId\","
				+ "AMP_INST_ID as \"monitorInstanceId\","
				+ "error_msg as \"msg\""
				+ " from TD_AVMON_DISCOVERY_RESULT order by mo_id,AMP_INST_ID");
		List<Map> list = jdbc.queryForList(sql);

		Map map = new HashMap();
		if (list != null) {
			map.put("rows", list);
			map.put("total", list.size());
		} else {
			map.put("rows", new ArrayList());
			map.put("total", 0);
		}
		return map;
	}

	public Map getAgentStatus(String moId) {
		// TODO Auto-generated method stub
		return null;
	}

	public Map startAgent(String moId) {
		String sql = String.format(
				"update TD_AVMON_MO_INFO set agent_status=1 where mo_id='%s'",
				moId);
		jdbc.execute(sql);
		// avmonServer.startAgent(moId);
		Map m = new HashMap();
		m.put("success", true);
		m.put("moId", moId);
		return m;
	}

	public Map pauseAgent(String moId) {
		String sql = String.format(
				"update TD_AVMON_MO_INFO set agent_status=2 where mo_id='%s'",
				moId);
		jdbc.execute(sql);
		// avmonServer.pauseAgent(moId);
		Map m = new HashMap();
		m.put("success", true);
		m.put("moId", moId);
		return m;
	}

	/*
	 * public Map getKpiList(String moId) { String
	 * sql=String.format(" select a.*," +
	 * "case when b.THRESHOLD is null then mb.THRESHOLD else b.THRESHOLD end as th2,"
	 * +
	 * "case when c.THRESHOLD is null then mc.THRESHOLD else c.THRESHOLD end as th3,"
	 * +
	 * "case when d.THRESHOLD is null then md.THRESHOLD else d.THRESHOLD end as th4,"
	 * + " b.check_optr,b.accumulate_count," +
	 * "case when b.b.THRESHOLD is null and c.b.THRESHOLD is null and d.b.THRESHOLD is null then 1 else 0 end as is_parent_threshold "
	 * +
	 * "from ( select a.target_id as mo_id,a.monitor_id,a.instance_id,b.name as kpi_name,b.caption,"
	 * + "b.datatype from TD_AVMON_MONITOR_INSTANCE a,TD_AVMON_KPI_INFO b " +
	 * "where a.monitor_id=b.monitor_id ) a " +
	 * "left join TD_AVMON_KPI_THRESHOLD b on a.mo_id=b.mo and a.instance_id=b.monitor_instance and a.kpi_name=b.kpi and b.alarm_Level=2 "
	 * +
	 * "left join TD_AVMON_KPI_THRESHOLD c on a.mo_id=c.mo and a.instance_id=c.monitor_instance and a.kpi_name=c.kpi and c.alarm_Level=3 "
	 * +
	 * "left join TD_AVMON_KPI_THRESHOLD d on a.mo_id=d.mo and a.instance_id=d.monitor_instance and a.kpi_name=d.kpi and d.alarm_Level=4 "
	 * +
	 * "left join TD_AVMON_KPI_THRESHOLD mb on mb.mo='*' and mb.monitor_instance='*' and a.kpi_name=mb.kpi and mb.alarm_Level=2 "
	 * +
	 * "left join TD_AVMON_KPI_THRESHOLD mc on mc.mo='*' and mc.monitor_instance='*' and a.kpi_name=mc.kpi and mc.alarm_Level=3 "
	 * +
	 * "left join TD_AVMON_KPI_THRESHOLD md on md.mo='*' and md.monitor_instance='*' and a.kpi_name=md.kpi and md.alarm_Level=4 "
	 * + "where a.mo_id='%s' order by monitor_id,instance_id,a.kpi_name ",moId);
	 * List<Map> list=jdbc.queryForList(sql);
	 * 
	 * Map map=new HashMap(); if(list!=null){ map.put("rows",list);
	 * map.put("total", list.size()); } else{ map.put("rows",new ArrayList());
	 * map.put("total", 0); } return map; }
	 */

	/*
	 * public Map saveKpi(String moId, String monitorInstanceId, String kpiCode,
	 * String optr,String accumulateCount,int th2, int th3, int th4, String
	 * isParentThreshold) {
	 * 
	 * String sql=String.format(
	 * "delete from TD_AVMON_KPI_THRESHOLD where mo='%s' and monitor_instance='%s' and kpi='%s' "
	 * , moId,monitorInstanceId,kpiCode); jdbc.execute(sql);
	 * 
	 * if("0".equals(isParentThreshold)){ sql=String.format(
	 * "select case when max(id) is null then 0 else max(id) end from TD_AVMON_KPI_THRESHOLD"
	 * ); int maxId=jdbc.queryForInt(sql); maxId++; sql=String.format(
	 * "insert into TD_AVMON_KPI_THRESHOLD(id,accumulate_count,alarm_level,check_optr,content,kpi,mo,monitor_instance,threshold) "
	 * + "values (%d,%s,%d,%s,'','%s','%s','%s','%d')",
	 * maxId,accumulateCount,2,optr,kpiCode,moId,monitorInstanceId,th2);
	 * //System.out.println(sql); jdbc.execute(sql);
	 * 
	 * maxId++; sql=String.format(
	 * "insert into TD_AVMON_KPI_THRESHOLD(id,accumulate_count,alarm_level,check_optr,content,kpi,mo,monitor_instance,threshold) "
	 * + "values (%d,%s,%d,%s,'','%s','%s','%s','%d')",
	 * maxId,accumulateCount,3,optr,kpiCode,moId,monitorInstanceId,th3);
	 * jdbc.execute(sql); maxId++; sql=String.format(
	 * "insert into TD_AVMON_KPI_THRESHOLD(id,accumulate_count,alarm_level,check_optr,content,kpi,mo,monitor_instance,threshold) "
	 * + "values (%d,%s,%d,%s,'','%s','%s','%s','%d')",
	 * maxId,accumulateCount,4,optr,kpiCode,moId,monitorInstanceId,th4);
	 * jdbc.execute(sql); //sql=String.format(
	 * "insert into TD_AVMON_KPI_THRESHOLD (MO_ID,MONITOR_ID,INSTANCE_ID,KPI_NAME)  "
	 * ) }
	 * 
	 * Map m=new HashMap(); m.put("success", true); m.put("moId", moId); return
	 * m; }
	 */

	public Map deployMonitors(String moId, String instanceIds) {
		String msg = "";
		String[] instanceIdList = instanceIds.split(",");
		// add by mark start
		ExecutorService executor = Executors.newFixedThreadPool(10);
		List<Callable<Map<String,String>>> tasklist = new ArrayList<Callable<Map<String,String>>>();
		List<Future<Map<String,String>>> results = new ArrayList<Future<Map<String,String>>>();
		// add by mark end		
		for (String inst : instanceIdList) {
			if (inst.trim().length() > 0) {
				//加入线程池
				// add by mark start
				tasklist.add(new DeployMonitorThread(moId,inst));
				// add by mark end
			}
		}
		// add by mark start
		try {
			results = executor.invokeAll(tasklist, 30, TimeUnit.SECONDS);
			for (Future<Map<String, String>> future : results) {
				Map<String, String> map =  future.get();
				String s = map.get("msg");
				if (s != null && s.length() > 0) {
					msg += s + "<br>";
				}
				//TODO 将没有下发AMP的主机更新为
			}
		} catch (InterruptedException e) {
			logger.error(this.getClass().getName()+e.getMessage());
		} catch (ExecutionException e) {
			logger.error(this.getClass().getName()+e.getMessage());
		}
		// add by mark start 
		
		Map m = new HashMap();
		m.put("success", true);
		m.put("msg", msg);
		m.put("moId", moId);
		return m;
	}

	/**
	 * 批量部署多个mo的monitorInstance 参数格式：mo11|cpu01,mo12|cpu01...
	 * 
	 * @param moIdAndMonitorInstanceIdList
	 * @return
	 */
	public Map batchDeployMonitor(String moIdAndMonitorInstanceIdList) {
		String msg = "";
		String[] instanceIdList = moIdAndMonitorInstanceIdList.split(",");
		// add by mark start
		ExecutorService executor = Executors.newCachedThreadPool();
		List<Callable<Map<String,String>>> tasklist = new ArrayList<Callable<Map<String,String>>>();
		List<Future<Map<String,String>>> results = new ArrayList<Future<Map<String,String>>>();
		// add by mark end	
		for (String inst : instanceIdList) {
			if (inst.split("\\|").length > 1) {
				String moId = inst.split("\\|")[0];
				String monitorInstanceId = inst.split("\\|")[1];
				// add by mark start
				String ampType = inst.split("\\|")[2];
				String ampName = inst.split("\\|")[3];
				String status = "0";
				String agentId = moId; 
				String ampId = ampName;
				String ampInstId = monitorInstanceId;
				
				try {
					saveAgentAmp(ampId, ampInstId, ampName, status, agentId, ampType);
				} catch (Exception e) {
					logger.error(this.getClass().getName()+e.getMessage());
				}
				
				//批量下发AMP
				tasklist.add(new DeployMonitorThread(moId,monitorInstanceId));
				// add by mark end
			}
		}
		

		// add by mark start
		try {
			results = executor.invokeAll(tasklist, 20, TimeUnit.MINUTES);
			for (Future<Map<String, String>> future : results) {
				Map<String, String> map =  future.get();
				String s = map.get("msg");
				if (s != null && s.length() > 0) {
					msg += s + "<br>";
				}
				//TODO 将没有下发AMP的主机更新为
			}
			executor.shutdown();
		} catch (InterruptedException e) {
			logger.error(this.getClass().getName()+e.getMessage());
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		// add by mark start 
		Map m = new HashMap();
		m.put("success", true);
		m.put("msg", msg);
		return m;
	}

	public String getMoTypeTree(String parentId) {

		parentId = Utils.decodeString(parentId);

		String sql = String
				.format("select type_id as \"id\",caption as \"text\",a.parent_id as \"pid\",'icon-'||lower(type_id) as \"iconCls\",case when b.parent_id is null then 'true' else 'false' end as \"leaf\" from td_avmon_snmp_type a  left join (select parent_id from td_avmon_snmp_type group by parent_id) b on a.type_id=b.parent_id where a.parent_id='%s'",
						parentId);
		logger.info(sql);
		List<Map> list = jdbc.queryForList(sql);
		BASE64Encoder base64 = new BASE64Encoder();
		for (Map map : list) {

			String id = (String) map.get("id");
			id = Utils.encodeString(id);
			String pid = (String) map.get("pid");
			pid = Utils.encodeString(pid);

			map.put("id", id);
			map.put("pid", pid);
		}
		return JackJson.fromObjectToJson(list);
	}

	public Map syncMem(String type) {
		// update stg_avmon_performance_tree
		// String sql="truncate table stg_avmon_performance_tree";
		// jdbc.execute(sql);
		// sql=SqlManager.getSql(this, "stg_avmon_performance_tree");
		// jdbc.execute(sql);
		//
		// sync mem of server
		avmonServer.updateCache(type);

		// return
		Map m = new HashMap();
		m.put("success", true);
		m.put("msg", "同步成功！");
		return m;
	}

	/**
	 * 
	 * @param pageNo 页码从1开始
	 * @param pageSize 每页数量
	 * @return
	 */
	public String getHostListForPing(int pageNo,int pageSize) {
		String result = "";
		String sql = SqlManager.getSql(this, "host_list_for_ping");
		List<Map> list = new ArrayList<Map>();
		if(0==pageNo&&0==pageSize){
			sql = "select mo_id, host_name as , ip from td_avmon_agent order by ip";
			list = jdbc.queryForList(sql);
		}else{
			list = jdbc.queryForList(String.format(sql,pageSize*(pageNo),(pageNo-1)*pageSize+1));	
		}
		
		for (Map map : list) {
			String moId = (String) map.get("mo_Id");
			String hostName = (String) map.get("HOSTNAME");
			String ip = (String) map.get("IP");
			result += String.format("%s^%s\n", moId , ip);
		}
		
		return result;
	}

	public List getDownloadList() {

		String sql = "select icon as \"icon\",os as \"os\",description as \"description\",'"
				+ Config.getInstance().getDownloadBaseUrl()
				+ "'||download_file as \"url\" from TD_AVMON_DOWNLOAD order by display_index";
		List<Map> list = jdbc.queryForList(sql);
		return list;
	}

	/**
	 * 得到所有的业务系统
	 * 
	 * @param userId
	 * @return 所有的业务系统
	 */
	public String getAgentMgrBizTypes(String userId) {

		String json = null;
		List<Map<String, String>> moTreeList = this.getBusinessTree(userId);

		List<Map<String, String>> treeNodes = new ArrayList<Map<String, String>>();
		Map<String, String> treeNode = null;

		if(null != moTreeList && moTreeList.size() > 0){
			for (Map<String, String> map : moTreeList) {
				map.put("id", map.get("id"));
				map.put("pid", "businessTree");
				map.put("expanded", "false");
				map.put("iconCls", "icon-alarm-node");
			}
		}
		
		//添加数据中心分类
		treeNode = new HashMap<String, String>(); 
		treeNode.put("id", "666656");
		treeNode.put("text", "数据中心");
		treeNode.put("pid", "businessTree");
		treeNode.put("expanded", "false");
		treeNode.put("iconCls", "icon-alarm-node");
		moTreeList.add(treeNode);

		json = JackJson.fromObjectHasDateToJson(moTreeList);
		return json;
	}

	/**
	 * 得到所有的host类型
	 * 
	 * @return String
	 */
	public String getAgentHostTypes(String parentId) {

		parentId = Utils.decodeString(parentId);

		String sql = String
				.format("select type_id as \"id\",caption as \"text\",a.parent_id as \"pid\",'icon-'||upper(caption) as \"iconCls\",case when b.parent_id is null then 'true' else 'false' end as \"leaf\" from TD_AVMON_MO_TYPE a  left join (select parent_id from TD_AVMON_MO_TYPE group by parent_id) b on a.type_id=b.parent_id where a.parent_id='%s'",
						AgentTypes.MO_HOST_PARENT_ID);
		logger.info(sql);
		List<Map> list = jdbc.queryForList(sql);
		BASE64Encoder base64 = new BASE64Encoder();
		for (Map map : list) {

			String id = (String) map.get("id") + "*" + parentId;
			String pid = (String) map.get("pid");

			map.put("id", id);
			map.put("pid", pid);
			// map.put("iconCls", "icon-alarm-node");
		}

		return JackJson.fromObjectToJson(list);
	}

	// add  start
	private List<Map> getMoTree(String userId, String parentId, String modelType) {

		String sql = null;
		if("数据中心".equals(modelType)){
			sql = String.format(
					SqlManager.getSql(this, "menu_tree_by_businesstype_other"), parentId,
					parentId);
		}else{
			sql = String.format(
					SqlManager.getSql(this, "menu_tree_by_businessType"), parentId,
					parentId, modelType);	
		}
		
		// String countSql = String.format(SqlManager.getSql(this,
		// "menu_tree_by_businessType_count"),parentId,parentId,modelType);
		List<Map> list = jdbc.queryForList(sql);
		return list;
	}
	
	
	private List<Map> getMoTreeOidmanage(String userId, String parentId, String modelType) {

		String sql = null;
		if("数据中心".equals(modelType)){
			sql = String.format(
					SqlManager.getSql(this, "menu_tree_by_businesstype_other_Oidmanage"), parentId,
					parentId);
		}else{
			sql = String.format(
					SqlManager.getSql(this, "menu_tree_by_businessType_Oidmanage"), parentId,
					parentId, modelType);	
		}
		
		// String countSql = String.format(SqlManager.getSql(this,
		// "menu_tree_by_businessType_count"),parentId,parentId,modelType);
		List<Map> list = jdbc.queryForList(sql);
		return list;
	}	
	
	/**
	 * 根据主机名模糊查找主机
	 * @param userId
	 * @param name 主机名
	 * @return
	 */
	private List<Map> getMoTreeByName(String userId, String name) {

		String sql = null;		
		sql = String.format(
				SqlManager.getSql(this, "menu_tree_by_name"));
		
		sql+="and a.caption like '%"+name+"%'";
		
		List<Map> list = jdbc.queryForList(sql);
		return list;
	}
	
	
	private List<Map> getMoTreeByNameOidmanage(String userId, String name) {

		String sql = null;		
		sql = String.format(
				SqlManager.getSql(this, "menu_tree_by_name_Oidmanage"));
		
		sql+="and a.caption like '%"+name+"%'";
		
		List<Map> list = jdbc.queryForList(sql);
		return list;
	}	

	private List<Map> getMoTypeTree(String userId, String parentId) {

		String sql = String.format(SqlManager.getSql(this, "menu_tree_type"),
				parentId);

		List<Map> list = jdbc.queryForList(sql);
		return list;
	}

	public String getMoTypeTree(String userId, String id, String pid) {

		List<Map> list = new ArrayList<Map>();
		String sql = null;

		if ("businessTree".equals(pid)) {

			sql = String
					.format("select type_id as \"id\",caption as \"text\",a.parent_id as \"pid\",'icon-'||lower(type_id) as \"iconCls\",case when b.parent_id is null then 'true' else 'false' end as \"leaf\" from td_avmon_snmp_type a  left join (select parent_id from td_avmon_snmp_type group by parent_id) b on a.type_id=b.parent_id where a.parent_id='root'");

			list = jdbc.queryForList(sql);
			int nodeCount = 0;

			for (Map map : list) {
				this.getNodeCount(map, id);
			}
		} else {
			if (null != id && id.length() > 0) {

				String parentId = id.split("\\*")[0];
				String businessType = id.split("\\*")[1];
				sql = String
						.format("select type_id as \"id\",caption as \"text\",a.parent_id as \"pid\","
								+ "case WHEN a.parent_id='HOST' THEN 'icon-' || upper(a.caption) else 'icon-' || lower(a.type_id) end as \"iconCls\","
								+ "case when b.parent_id is null then 'true' else 'false' end as \"leaf\" "
								+ "from td_avmon_snmp_type a  "
								+ "left join (select parent_id from td_avmon_snmp_type group by parent_id) b "
								+ "on a.type_id=b.parent_id "
								+ "where a.parent_id='%s'", parentId);

				Log.info(sql);
				list = jdbc.queryForList(sql);
				for (Map map : list) {
					this.getNodeCount(map, businessType);
				}
			} else {
				Log.error("id is null");
			}
		}

		return JackJson.fromObjectHasDateToJson(list);
	}

	public List<Map<String, String>> getBusinessTree(String userId) {

		String sql = String.format(SqlManager.getSql(this, "getBusinessTree"));

		List<Map<String, String>> list = jdbc.queryForList(sql);
		return list;
	}


	public String getMoTreeJson(String userId, String parentId, String modelType) {

		List<Map> list = getMoTree(userId, parentId, modelType);
		return JackJson.fromObjectHasDateToJson(list);
	}
	
	public String getMoTreeJsonOidmanage(String userId, String parentId, String modelType) {

		List<Map> list = getMoTreeOidmanage(userId, parentId, modelType);
		return JackJson.fromObjectHasDateToJson(list);
	}	

	/**
	 * 
	 * @param userId
	 * @param name
	 * @return
	 */
	public String getMoTreeJsonByName(String userId, String name) {

		List<Map> list = getMoTreeByName(userId, name);
		return JackJson.fromObjectHasDateToJson(list);
	}
	

	//modify
	public String getMoTreeJsonByNameOidmanage(String userId, String name) {

		List<Map> list = getMoTreeByNameOidmanage(userId, name);
		return JackJson.fromObjectHasDateToJson(list);
	}
	//modify 
	
	
	public String getMoTypeTreeJson(String userId, String parentId) {

		List<Map> list = getMoTypeTree(userId, parentId);
		return JackJson.fromObjectHasDateToJson(list);
	}

	public String getMoCaptionById(String typeId) {
		String caption = null;
		List<Map> list = null;
		String sql = "select caption from TD_AVMON_MO_TYPE where type_id = '%s'";
		if (null != typeId && typeId.length() > 0) {
			sql = String.format(sql, typeId);
			list = jdbc.queryForList(sql);
		}

		if (list.size() > 0) {
			caption = (String) list.get(0).get("caption");
		}

		return caption;
	}

	/**
	 * 缺德业务系统下各个节点的子节点的总数
	 * 
	 * @return
	 */
	private void getNodeCount(Map map, String bizSys) {

		int count = 0;
		String id = (String) map.get("id");
		//String bizSysStr = AgentTypes.BIZMAP.get(bizSys);
		String bizSysStr = null;
		if("666656".equals(bizSys)){
			bizSysStr = "其他";
		}else{
			bizSysStr = equipmentCenterService.getBusinessNameById(bizSys);	
		}
		
		if ("HARDWARE".equals(id)) {
			
			int hostCount = 0;
			int iloHostCount = 0;
			
			int otherHardWareCount = 0;
			if(bizSysStr.equals("其他")){
				// 1.取得该业务系统下所有主机的个数
//				hostCount = this.getCountOfOther("getAllHostNum_Other");
				
				otherHardWareCount = this.getCountOfOther(
						"getOtherHardWareCount_Other");
				iloHostCount = this.getCountOfOther("getIloHostNum_Other");
				
			}else{
				// 1.取得该业务系统下所有主机的个数
//				hostCount = this.getCountByBiz("getAllHostNum", bizSysStr);
				// 2。存储、外围设备及数据中心、网络设备的个数	
				otherHardWareCount = this.getCountByBiz(
						"getOtherHardWareCount", bizSysStr);
				
				iloHostCount = this.getCountByBiz("getIloHostNum", bizSysStr);
			}
			
//			count = hostCount + otherHardWareCount + iloHostCount;
			count = otherHardWareCount + iloHostCount;
		}

		// 数据库
		else if ("DATABASE".equals(id)) {
			
			if(bizSysStr.equals("其他")){
				count = this.getCountOfOther("getDataBaseCount_Other");
			}else{
				count = this.getCountByBiz("getDataBaseCount", bizSysStr);	
			}
			
		}
		// 中间件
		else if ("MIDDLEWARE".equals(id)) {
			if(bizSysStr.equals("其他")){
				count = this.getCountOfOther("getMiddleWareCount_Other");
			}else{
				count = this.getCountByBiz("getMiddleWareCount", bizSysStr);	
			}
			
		}
		// 业务监控
		else if ("BUSINESS".equals(id)) {
			
			if(bizSysStr.equals("其他")){
				count = this.getCountOfOther("getBusinessCount_Other");
			}else{
				count = this.getCountByBiz("getBusinessCount", bizSysStr);	
			}
			
		}
		// 主机
		else if ("HOST".equals(id)) {
			if(bizSysStr.equals("其他")){
				count = this.getCountOfOther("getAllHostNum_Other");				
			}else{
				count = this.getCountByBiz("getAllHostNum", bizSysStr);	
			}
		}
		// 主机
		else if ("VMHOST".equals(id)) {
			if(bizSysStr.equals("其他")){
				count = this.getCountOfOther("getAllVmHostNum_Other");				
			}else{
				count = this.getCountByBiz("getAllVmHostNum", bizSysStr);	
			}
		}
		
		
		else if ("SNMPOID".equals(id)) {
			if(bizSysStr.equals("其他")){
				count = this.getCountOfOther("getAllSNMPNum_Other");				
			}else{
				count = this.getCountByBiz("getAllSNMPNum", bizSysStr);	
			}
		}		
		// ILO主机
		else if ("iloRoot".equals(id)) {
			if(bizSysStr.equals("其他")){
				count = this.getCountOfOther("getIloHostNum_Other");				
			}else{
				count = this.getCountByBiz("getIloHostNum", bizSysStr);	
			}
		}
		// 存储
		else if ("STORAGE".equals(id)) {
			if(bizSysStr.equals("其他")){
				count = this.getCountOfOther("getStoreRageCount_Other");
			}else{
				count = this.getCountByBiz("getStoreRageCount", bizSysStr);	
			}
		}
		// 网络
		else if ("NETWORK".equals(id)) {
			
			int huaweicount = 0;
			int networkcount = 0;
			
			if(bizSysStr.equals("其他")){
				//临时屏蔽计数代码
//				networkcount = this.getCountOfOther("getNetWorkCount_Other");
//				huaweicount = this.getCountOfOther("gethuaweiCount_Other"); 
//				count = this.getCountOfOther("getNetWorkCount_Other");
			}else{
				//临时屏蔽计数代码
//				networkcount = this.getCountOfOther("getNetWorkCount");
//				huaweicount = this.getCountOfOther("gethuaweiCount"); 
//				count = this.getCountByBiz("getNetWorkCount", bizSysStr);	
			}
			count = networkcount + huaweicount;
		}
		// 取得外围设备以及数据中心的个数
		else if ("OTHERS".equals(id)) {
			
			if(bizSysStr.equals("其他")){
				count = this.getCountOfOther("getOthersCount_Other");
			}else{
				count = this.getCountByBiz("getOthersCount", bizSysStr);	
			}
		}		

		else if ("huawei".equals(id)) {
			
			if(bizSysStr.equals("其他")){
//				count = this.getCountOfOther("gethuaweiCount_Other");
			}else{
//				count = this.getCountByBiz("gethuaweiCount", bizSysStr);	
			}
		}		
		//
		else if ("HOST_AIX".equals(id) || "HOST_LINUX".equals(id)
				|| "HOST_SUNOS".equals(id) || "HOST_WINDOWS".equals(id)
				|| "HOST_HP-UX".equals(id)
				|| "s1700".equals(id)) {
			
			if(bizSysStr.equals("其他")){
				count = this.getHostNumByOs("getHostNumByOs_Other",bizSysStr,id);
			}else{
				count = this.getHostNumByOs("getHostNumByOs",bizSysStr,id);	
			}
		}

		map.put("pid", bizSys);
		map.put("id", map.get("id") + "*" + bizSys);
		//数据不全，临时屏蔽计数代码
//		map.put("text", map.get("text") + "(" + count + ")");

	}

	private int getCountOfOther(String bizName) {
		int count = 0;
		String sql = SqlManager.getSql(this, bizName);
		sql = String.format(sql);
		count = jdbc.queryForInt(sql);
		return count;
	}

	

	private int getHostNumByOs(String bizName,String bizSysStr, String id) {
		int count = 0;
		String sql = SqlManager.getSql(this, bizName);
		
		if(bizSysStr.equals("数据中心")){
			sql = String.format(sql,id);
		}else{
			sql = String.format(sql, bizSysStr,id);	
		}
		
		count = jdbc.queryForInt(sql);
		return count;
	}

	private int getCountByBiz(String bizName, String bizSys) {
		int count = 0;
		String sql = SqlManager.getSql(this, bizName);
		sql = String.format(sql, bizSys);
		count = jdbc.queryForInt(sql);
		return count;
	}

	/**
	 * 递归查找所有节点
	 * 
	 * @param id
	 * @param bizSysStr
	 * @return
	 */
	private List<Map<String, String>> getAllNodes(String id, String bizSysStr) {

		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		List<Map<String, String>> midList = new ArrayList<Map<String, String>>();
		String sql = SqlManager.getSql(this, "menu_tree_all_nodes");
		String parentId = null;
		sql = String.format(sql, bizSysStr);
		if (null != id && !"".equals(id)) {
			sql += (" and a.parent_id = '" + id + "'");
		}
		midList = jdbc.queryForList(sql);
		for (Map<String, String> map : midList) {
			String midId = map.get("id");
			String midPid = map.get("pid");
			list = getAllNodes(midId, bizSysStr);
		}
		return list;
	}
	
	/**
     * 保存AMP信息
     * @param request
     * @return
     * @throws Exception
     */
	public void saveAgentAmp(String ampId, String ampInstId, String ampName,
			String status, String agentId, String ampType) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);

		// TD_AVMON_AMP.CAPTION eg OS-LINUX
		// String ampId = request.getParameter("ampId");
		// TD_AVMON_AMP.amp_id eg os-linux
		// String ampInstId = request.getParameter("ampInstId");
		// TD_AVMON_AMP.CAPTION eg OS-LINUX
		// String ampName = request.getParameter("ampName");
		// eg.是0,否1
		// String status = request.getParameter("status");
		//
		// String agentId = request.getParameter("agentId");

		// eg.DB or OS
		// String ampType = request.getParameter("ampType");

		// 查询AMP数据到实例表中有无相关记录，如果有，则不插入
		String countSql = "select count(*) from TD_AVMON_AMP_INST a where a.AGENT_ID='%s' and a.AMP_INST_ID = '%s'";
		countSql = String.format(countSql, agentId,ampInstId);
		int result = jdbc.queryForInt(countSql);

		if (result <= 0) {
			List<String> sqlList = new ArrayList<String>();

			// 插入AMP数据到实例表
			String insertSql = String
					.format("insert into TD_AVMON_AMP_INST(AMP_ID,AMP_INST_ID,AMP_VERSION,ENABLE_FLAG,SCHEDULE,AGENT_ID,CAPTION,STATUS,LAST_ACTIVE_TIME)\n"
							+ "    values ('%s','%s',(select version from TD_AVMON_AMP where amp_id='%s' ),\n"
							+ "    '%d',(select default_schedule from TD_AVMON_AMP where amp_id='%s' ),'%s','%s','0','')",
							ampId, ampInstId, ampId, Integer.parseInt(status),
							ampId, agentId, ampName);
			// jdbcTemplate.execute(insertSql);

			sqlList.add(insertSql);

			if (!"vm".equalsIgnoreCase(ampType)
					&& !"ilo".equalsIgnoreCase(ampType)) {
				// 复制AMP关联KPI数据到POLICY表中 只有添加普通amp实例时需要copy对应信息
				// 查找关联API数据
				String apiSql = String
						.format("select AMP_ID as \"ampId\" ,KPI_CODE as \"kpiCode\",SCHEDULE as \"schedule\",KPI_GROUP as \"kpiGroup\"  from TD_AVMON_AMP_KPI where AMP_ID = '%s'",
								ampId);
				List<Map<String, Object>> apiList = jdbc.queryForList(apiSql);

				for (Map<String, Object> kpi : apiList) {
					String insertApiPolicySql = "INSERT INTO TD_AVMON_AMP_POLICY(KPI_CODE,KPI_GROUP,SCHEDULE,AGENT_ID,AMP_INST_ID,NODE_KEY)"
							+ "VALUES('%s','%s','%s','%s','%s','NA')";
					insertApiPolicySql = String.format(insertApiPolicySql, kpi
							.get("kpiCode").toString(),
							kpi.get("kpiGroup") != null ? kpi.get("kpiGroup")
									.toString() : "",
							kpi.get("schedule") != null ? kpi.get("schedule")
									.toString() : "", agentId, ampInstId);
					sqlList.add(insertApiPolicySql);
				}
			}

			// 复制AMP关联ATTR数据到INST_ATTR表中
			// 查找关联ATTR数据
			String attrSql = String
					.format("select name as \"name\" ,default_value as \"value\" from td_avmon_amp_attr where AMP_ID = '%s'",
							ampId);
			List<Map<String, Object>> attrList = jdbc.queryForList(attrSql);

			for (Map<String, Object> attr : attrList) {
				String insertInstAttrSql = "INSERT INTO td_avmon_amp_inst_attr(NAME,VALUE,AGENT_ID,AMP_INST_ID)"
						+ "VALUES('%s','%s','%s','%s')";
				insertInstAttrSql = String.format(insertInstAttrSql,
						attr.get("name").toString(),
						attr.get("value") != null ? attr.get("value")
								.toString() : "", agentId, ampInstId);
				sqlList.add(insertInstAttrSql);
			}

			String batchSql[] = sqlList.toArray(new String[sqlList.size()]);
			try {
				String returnString = transfer1(batchSql);
				if ("1".equals(returnString)) {
					throw new Exception(this.getClass().getName()
							+ "saveAgentAmp transfer error!");
				} else if ("ORA-00001".equals(returnString)) {
					throw new Exception(this.getClass().getName()
							+ "saveAgentAmp ORA-00001 error!");
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error(this.getClass().getName() + " saveAgentAmp()", e);
				throw e;
			}
		}
		// return map;
	}

  //用于控制事务
  	public String transfer1(final String[] sqls) throws Exception{
  		String flag = transactionTemplate.execute(new TransactionCallback<String>() {
  			public String doInTransaction(TransactionStatus status) {
  				try {
  					// JdbcTemplate batchUpdate操作
  					jdbc.batchUpdate(sqls);
  				} catch (Exception e) {
  					System.out.println("----------RuntimeException-----" + e);
  					status.setRollbackOnly(); // 回滚
  					
  					if(e.toString().indexOf("ORA-00001: unique constraint")>-1){
  						return "ORA-00001";
  		            }else{
  		            	return "1";
  		            }
  				}
  				return "0";
  			}
  		});
  		return flag;
  	}

  	/**
  	 * td_avmon_agent_upgrade_files
  	 * 查询所有的agnet升级包
  	 * @return
  	 */
	public List<Map<String,String>> getMonitorUpgradeFiles() {
		String json = null;
		String sql ="";
		sql = SqlManager.getSql(this, "getMonitorUpgradeFiles");
		List<Map<String,String>> list = jdbc.queryForList(sql);
		return list;
	}
	
	/**
     * 获取Agent grid 信息
     * @param request
     * @return
     * @throws Exception
     */
    public Map findAgentGridInfo(HttpServletRequest request) throws Exception{

        Map<String, Object> map = new HashMap<String, Object>();

        //获取查询参数
        String os = request.getParameter("os")==null?"":request.getParameter("os");
        String osVersion = request.getParameter("osVersion")==null?"":request.getParameter("osVersion");
        
        
        String where = "where 1=1 ";
        if(null != os){
        	where = where + " and ag.os = '"+os+"' ";
        }
        if(null != osVersion){
        	where = where + " and ag.os_version = '"+osVersion+"' ";
        }
        
        String orderBy = "";
        String sortBy = request.getParameter("sort");
        if(sortBy!=null){
			JSONArray jsonArr = JSONArray.fromObject(sortBy);
			String sort = jsonArr.getJSONObject(0).get("property").toString();
			if("ampCount".equalsIgnoreCase(sort)||"ampCount1".equalsIgnoreCase(sort)){
				sort = "amp.\"ampCount\"";
			}else if("hostName".endsWith(sort)){
				sort = "host.value";
			}else if("agentId".endsWith(sort)){
				sort = "ag.AGENT_ID";
			}else if("agentVersion".endsWith(sort)){
				sort = "ag.agent_version";
			}else if("agentStatus".endsWith(sort)){
				sort = "ag.agent_status";
			}else if("lastHeartbeatTime".endsWith(sort)){
			    sort = "ag.last_heartbeat_time";
			}else if("osVersion".endsWith(sort)){
                sort = "ag.os_version";
            }else if("moId".endsWith(sort)){
                sort = "ag.mo_id";
            }
			orderBy = " order by " + sort + " " + "DESC";
		}
        
        String listSql = null;
        String countSql = null;
    	listSql = SqlManager.getSql(AgentManageService.class, "getAgentGridInfo");
        String finSql = listSql+where+orderBy;
        Log.info(finSql);

        List<Map<String,Object>> list =  jdbc.queryForList(finSql);

        map.put("root", list);
        return map;
    }
	

    /**
     * 批量更新Agent
     * @param request
     * @return
     */
	public String batchUpgradeAgent(HttpServletRequest request) {
		String items = request.getParameter("items")==null?"":request.getParameter("items");
		String msg = "[";
		if(null!=items){
			String[] itemArr = items.split(",");
			for (String agentId : itemArr) {
				String result = upgradeAgent(agentId);
				msg+=result;
				msg+="<br>";
			}
			msg+="]";
		}
		Map m = new HashMap();
		m.put("success", true);
		m.put("msg", msg);
		return msg;
	}
	
	 private String upgradeAgent(String  agentId) {
	        String json = "";
	        String result = avmonServer.upgradeAgent(agentId);
	        
	        if(result.startsWith("00")){
	            json="{success:true,msg:'Agent新程序下发成功，大约1分钟后Agent会自动升级并重连，请耐心等待!'}";
	        }else {
	            json="{success:false,msg:'" + result + "'}";
	        }
	        return json;
	    }

	 /**
	     * 下发脚本（调度+配置一起下发）成功后为AMP状态为停止运行
	     * @param request 方法需要改进，整体事务控制--muzh
	     * @return
	     */
	    public Map pushAgentAmpScript(HttpServletRequest request){
	        Map<String, Object> map = new HashMap<String, Object>();
	        //获取需要操作的AMPList
	        final  List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(request.getParameter("agentAmpInfo"));
	        boolean flag = false;
	        //TODO 进行AMP脚本下发
	        //下发成功后修改AMP状态为  --停止运行
	        /**
	         *  agentId: Agent ID
				ampInstId: AMP实例ID
	         */
	        
//	        boolean pushFlag = false;
	        for(Map<String,String> ampMap : ampListMapF){
	        	String ampInstId = ampMap.get("ampInstId");
	        	String agentId = ampMap.get("agentId");
	        	String ampId = ampMap.get("ampId");
	        	
	        	String result = avmonServer.deployAmpPackage(agentId,ampInstId);
	        	if(result.startsWith("00")){
	        		String updateAmpStatusSql = String.format("UPDATE TD_AVMON_AMP_INST SET STATUS = 2 WHERE AMP_INST_ID ='%s' AND AMP_ID ='%s' AND AGENT_ID ='%s'",ampInstId,ampId,agentId);
	        		jdbc.update(updateAmpStatusSql);
	        		flag = true;
	        		map.put("errorMsg", "脚本下发成功!");
	        	}else {
	            	map.put("errorMsg", result);
	            }
	        }
//	        if(pushFlag){
//	            flag  = updateNormalAMPStatus(ampListMapF,ConfigConstant.AMP_STATUS_DISABLED);
//	        }

	        if(flag) {
	            map.put("success",true);
	        }else{
	            map.put("success",false);
	        }
	        return map;
	    }
	    
	    /**
	     * 转化JSON数组为对应ListMap信息
	     * @param jsonString
	     * @return
	     */
	    private List<Map<String,String>> getListMapByJsonArrayString(String jsonString) {
	        List<Map<String,String>> ampListMap = new ArrayList<Map<String, String>>();
	        JSONArray jsonArray = null;
	        try {
	            jsonArray = JSONArray.fromObject(jsonString);//new JSONArray(jsonString);
	            for(int s=0;s<jsonArray.size();s++){//.length();s++){
	                Map<String,String>  entityMap= JackJson.fromJsonToObject(jsonArray.get(s).toString(), Map.class);
	                ampListMap.add(entityMap);
	            }
	        } catch (JSONException e) {
	            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
	        }
	        return ampListMap;
	    }

	    /**
	     * 
	     * @return
	     */
		public Map getHostPing(HttpServletRequest request) {
	        
			String limit = request.getParameter("limit");
	        String start = request.getParameter("start");
	        String where = " ";
	        String orderBy = "";
	        String sortBy = request.getParameter("sort");
	        String ip = request.getParameter("ip")==null?"":request.getParameter("ip");
	        String sql = SqlManager.getSql(this, "getHostPing");
	        String countSql = SqlManager.getSql(this, "getHostPingCount");
	        
	        
	        if(ip.length() > 0){
	        	where += " and  ip like '%"+ip+"%'";
	        }
	        
	        if(null != sortBy ){
	        	JSONArray jsonArr = JSONArray.fromObject(sortBy);
				String sort = jsonArr.getJSONObject(0).get("property").toString();
				if("ip".equalsIgnoreCase(sort)||"ip".equalsIgnoreCase(sort)){
					sort = "agent.ip";
				}else if("agentId".equalsIgnoreCase(sort)){
					sort = "agent.agent_id";
				}else if("os".equalsIgnoreCase(sort)){
					sort = "agent.os";
				}else if("pingTime".equalsIgnoreCase(sort)){
					sort = "curr.kpi_time";
				}else if("pingStatus".equalsIgnoreCase(sort)){
					sort = "curr.value";
				}
				orderBy = " order by " + sort + " " + jsonArr.getJSONObject(0).get("direction");
	        }else{
	        	orderBy = " order by  agent.ip ";
	        }
	        
	        String pingSql =  generatPageSql(sql+where+orderBy,limit,start);

	        List<Map<String,String>> list  = new ArrayList<Map<String,String>>();
			int pingCount = jdbc.queryForInt(countSql);
			list = jdbc.queryForList(pingSql);
			
			
			Map<String, Object> map = new HashMap<String, Object>();			
			map.put("total", pingCount);
			map.put("root", list);
	        return map;
		}
		
	    private String generatPageSql(String sql,String limit,String start){
	        Integer limitL = Integer.valueOf(limit);
	        Integer startL = Integer.valueOf(start)+1;

	        //构造oracle数据库的分页语句
	        StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
	        paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
	        paginationSQL.append(sql);
	        paginationSQL.append(" ) temp where ROWNUM <= " + (startL-1+limitL));//limitL*((startL-1)/10+1));//limitL*startL);
	        paginationSQL.append(" ) WHERE num > " + (startL-1));
	        return paginationSQL.toString();
	    }
		
	    // add by mark end

}
