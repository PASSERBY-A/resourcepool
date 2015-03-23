package com.hp.avmon.performance.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.store.KpiDataStore;
import com.hp.avmon.utils.Constants;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.SqlManager;

@Service
public class VmListService {
	
	private static final Log logger = LogFactory.getLog(VmListService.class);	
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	@Qualifier("kpiDataStore")
	private KpiDataStore kpiDataStore;
	
	/**
	 * 获得物理机列表
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")	
	public Map<String,Object> getPhysicalMachineList(HttpServletRequest request) {
		String datacenterId  = request.getParameter("datacenterId");//("moId");
		String agentId  = request.getParameter("agentId");
		Map<String,Object> map = new HashMap<String, Object>(); 
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String sql = SqlManager.getSql(this.getClass(), "getPhysicalMachineList");
		String countSql = SqlManager.getSql(this.getClass(), "getPhysicalMachineCnt");
		sql = String.format(sql, agentId);
		countSql = String.format(countSql, agentId);
		
		if(datacenterId!=null&&datacenterId.length()>0){
			sql = sql + " and h.obj_parent='"+datacenterId+"'";
			countSql = countSql + " and obj_parent='"+datacenterId+"'";
		}
		
		String pagesize = request.getParameter("pagesize")!=null?request.getParameter("pagesize"):"20";
		String pagenum = request.getParameter("pagenum")!=null?request.getParameter("pagenum"):"0";
		Integer pagenum1 = (Integer.parseInt(pagenum));
		
//		String startStr = request.getParameter("start");
//		String limitStr = request.getParameter("limit");
//		int start = StringUtils.isEmpty(startStr)?1:Integer.parseInt(startStr);
//		int limit = StringUtils.isEmpty(limitStr)?50:Integer.parseInt(limitStr);;
		
		String querySql = DBUtils.paginationforjqw(sql,  pagenum1,Integer.valueOf(pagesize));//paginationforjqw
		int count = jdbcTemplate.queryForInt(countSql);
		result = jdbcTemplate.queryForList(querySql);
		logger.debug("========getPhysicalMachineList=========="+count);
		map.put("items", result);
		map.put("totalRows", count);
		return map;
	}
	// add by mark start 2014-2-21

	/**
	 * 
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getVirtualMachineList(HttpServletRequest request) {
		String agentId  = request.getParameter("agentId");
		String hostId  = request.getParameter("hostId");
		String datacenterId  = request.getParameter("datacenterId");
		Map<String,Object> map = new HashMap<String, Object>(); 
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String sql = SqlManager.getSql(this.getClass(), "getVirtualMachineList");
		String countSql = SqlManager.getSql(this.getClass(), "getVirtualMachineCnt");
		sql = String.format(sql,agentId);
		countSql = String.format(countSql,agentId);
		
		if(hostId!=null&&hostId.length()>0){
			sql = sql + " and obj_parent='"+hostId+"'";
			countSql = countSql + " and obj_parent='"+hostId+"'";
		}
		if(datacenterId!=null&&datacenterId.length()>0){
			sql = sql + " and obj_parent in (select obj_id from td_avmon_amp_vm_host where obj_parent='" + datacenterId + "') ";
			countSql = countSql + " and obj_parent in (select obj_id from td_avmon_amp_vm_host where obj_parent='" + datacenterId + "') ";
		}
		
		String pagesize = request.getParameter("pagesize")!=null?request.getParameter("pagesize"):"20";
		String pagenum = request.getParameter("pagenum")!=null?request.getParameter("pagenum"):"0";
		Integer pagenum1 = (Integer.parseInt(pagenum));
		
//		String startStr = request.getParameter("start");
//		String limitStr = request.getParameter("limit");
//		int start = StringUtils.isEmpty(startStr)?1:Integer.parseInt(startStr);
//		int limit = StringUtils.isEmpty(limitStr)?50:Integer.parseInt(limitStr);;
		
		String querySql = DBUtils.paginationforjqw(sql,  pagenum1,Integer.valueOf(pagesize));//pagination(sql, start, limit);
		int count = jdbcTemplate.queryForInt(countSql);
		result = jdbcTemplate.queryForList(querySql);
		
		map.put("items", result);
		map.put("total", count);
		return map;
	}

    /**
     * 获得存储列表
     * 
     * @param request
     * @param writer
     * @return
     */
	@SuppressWarnings("unchecked")
	public Map<String,Object> getStorageList(HttpServletRequest request) {
		String datacenterId  = request.getParameter("datacenterId");
		String agentId  = request.getParameter("agentId");
		Map<String,Object> map = new HashMap<String, Object>(); 
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String sql = SqlManager.getSql(this.getClass(), "getStorageList");
		String countSql = SqlManager.getSql(this.getClass(), "getStorageCnt");
		sql = String.format(sql, agentId);
		countSql = String.format(countSql, agentId);
		
		if(datacenterId!=null&&datacenterId.length()>0){
			sql = sql + " and obj_parent='"+datacenterId+"'";
			countSql = countSql + " and obj_parent='"+datacenterId+"'";
		}
		
		String pagesize = request.getParameter("pagesize")!=null?request.getParameter("pagesize"):"20";
		String pagenum = request.getParameter("pagenum")!=null?request.getParameter("pagenum"):"0";
		Integer pagenum1 = (Integer.parseInt(pagenum));
		
//		String startStr = request.getParameter("start");
//		String limitStr = request.getParameter("limit");
//		int start = StringUtils.isEmpty(startStr)?1:Integer.parseInt(startStr);
//		int limit = StringUtils.isEmpty(limitStr)?50:Integer.parseInt(limitStr);;
		
		String querySql = DBUtils.paginationforjqw(sql,  pagenum1,Integer.valueOf(pagesize));//.pagination(sql, start, limit);
		int count = jdbcTemplate.queryForInt(countSql);
		result = jdbcTemplate.queryForList(querySql);
		
		logger.debug("getStorageList size "+count);
		
		map.put("items", result);
		map.put("total", count);
		return map;
	}
	
	/**
    *
    * @param map
    * @param flag 1 主节点 2 二级节点 3 三级节点
    */
    private void setJsonValue(Map map,int flag){
       // 添加其他参数
       if(flag == 1){
           map.put("pid","root");
           map.put("iconCls","icon-virtualcenters");
           map.put("leaf",false);
       }else{
           //根据分类设置节点显示图标--liqiang提供mapping关系后需做调整
           String objType = map.get("obj_type")!=null?map.get("obj_type").toString():"";
           if(Constants.HostSystem.equalsIgnoreCase(objType)){
               map.put("iconCls","icon-esxhosts");
           }else if(Constants.Datacenter.equalsIgnoreCase(objType)){
               map.put("iconCls","icon-datacenters");
           }else if(Constants.ResourcePool.equalsIgnoreCase(objType)){
               map.put("iconCls","icon-resourcepools");
           }else if(Constants.VirtualMachine.equalsIgnoreCase(objType)){
               map.put("iconCls","icon-virtualmachines");
           }else if(Constants.DataStore.equalsIgnoreCase(objType)){
               map.put("iconCls","icon-datastores");
           }else if(Constants.Network.equalsIgnoreCase(objType)){
               map.put("iconCls","icon-network");
           }else{
               map.put("iconCls","icon-network");
           }
           
           String childCountSql = "select count(1) from td_avmon_amp_vm_host where obj_parent='" + map.get("obj_id") + "'";
           int childCount = jdbcTemplate.queryForInt(childCountSql);
           if(childCount>0){
               map.put("leaf",false);
           }else{
               map.put("leaf",true);
           }
           
           
       }
       if(!(Boolean) map.get("leaf")){
    	   List<Map> list = new ArrayList<Map>();
           Map chirldMap = new HashMap();
           chirldMap.put("value", "../vcenter/vmTree");//?id="+map.get("id").toString());
           chirldMap.put("label", "Loading...");
           list.add(chirldMap);
           map.put("items", list);
       }
       map.put("iconClsPause","icon-pause");
       map.put("iconClsError","icon-error");
       map.put("resourcePicture","network.jpg");
       map.put("resourcePictureDirection","0");
       map.put("isInstance","0");
       map.put("expanded",false);
       map.put("type","VM");
    }
	
	public String getVmTreeJson(String userId,String parentId,String checkbox,ResourceBundle bundle) throws IOException {
    	
        List<Map> list = new ArrayList<Map>();
        //"items": [{ "value": "ajax1.htm", "label": "Loading..." }] }, 
        //查询虚机主节点
        if(parentId.equals("VMHOST")){
            String vmMainSql = "SELECT DISTINCT '../resources/images/Vcenter_ICON2.png' as \"icon\",a.agent_id AS \"agentId\","
            		+ "a.amp_inst_id ||'$'||a.agent_id ||'$'||'VMHOST' AS \"id\","
            		+ "d.value AS \"label\",'resourceListPanel' as \"views\","
            		+ "'resourceListPanel' as \"defaultView\" "
            		+ "FROM TD_AVMON_AMP_INST a,TD_AVMON_AMP b ,td_avmon_agent c ,td_avmon_amp_inst_attr d "
            		+ "WHERE a.amp_id=b.amp_id and a.AGENT_ID = c.AGENT_ID and a.status = 1 AND upper(b.type)='VM' and d.agent_id=a.agent_id and d.name='vc_server' "
            		+ "order by d.value ";
            List<Map> vmMainList = jdbcTemplate.queryForList(vmMainSql);
            for(int i=0;i<vmMainList.size();i++) {
                Map map =  vmMainList.get(i);
                // 添加其他参数
                setJsonValue(map,1);
            }
            list.addAll(vmMainList);
            
        }
        //一级VM节点 8F-TSLab
        else if(parentId.indexOf("$") >-1){
            String agentId = parentId.split("\\$")[1];
            String instId = parentId.split("\\$")[0];
            logger.debug("agentId="+agentId);
            logger.debug("instId="+instId);
           String firstSubNodeSql = "SELECT '../resources/images/datacenter_ICON.png' as \"icon\",obj_id ||'#'||amp_inst_id || '#'||agent_id || '#'||(case when mo_id is null then '' else mo_id end) AS \"id\",obj_name AS \"label\"," +
                "amp_inst_id ||'$'||agent_id AS \"pid\",obj_id as \"obj_id\",obj_type as \"obj_type\",'vmList' as \"views\",'vmList' as \"defaultView\" " +
                "FROM TD_AVMON_AMP_VM_HOST WHERE agent_id=? AND amp_inst_id=? AND obj_parent='-1' and enable_flag = 1 "
                + "order by obj_name ";
            List<Map> vmFirstSubList = jdbcTemplate.queryForList(firstSubNodeSql,new Object[]{agentId,instId});

            for(int i=0;i<vmFirstSubList.size();i++) {
                Map map =  vmFirstSubList.get(i);
                // 添加其他参数
                setJsonValue(map,2);
            }
            list.addAll(vmFirstSubList);
        }
        //二级以下节点
        else if(parentId.indexOf("#") >-1) {
            String[] ids = parentId.split("\\#");
            String objId = ids[0];
            String agentId = ids[2];
            String instId = ids[1];
            String secondNodesSql = "select '../resources/images/motype/server.gif' as \"icon\",obj_id ||'@'|| (case when mo_id is null then '' else mo_id end) ||'@'|| agent_id as \"id\",obj_name as \"label\"," +
                    "obj_parent || '#'||amp_inst_id || '#'||agent_id as \"pid\",obj_id as \"obj_id\",obj_type as \"obj_type\", " +
                    "case when obj_type='HostSystem' then 'vmHost' " +
                    "when obj_type='VirtualMachine' then 'vmVHost' " +
                    "else '' end as \"defaultView\", " +
                    "case when obj_type='HostSystem' then 'vmHost,kpiListPanel,kpiTrend,alarmSearch' " +
                    "when obj_type='VirtualMachine' then 'vmVHost,kpiListPanel,kpiTrend,alarmSearch' " +
                    "else '' end as \"views\" " +
                    "from TD_AVMON_AMP_VM_HOST " +
                    "where agent_id=? and amp_inst_id=?  " +
                    "and obj_parent = ? " +
                    "and enable_flag = 1 " +
                    "and obj_type in ('HostSystem')"
                    + " order by obj_name";

            List<Map> vmSecondNodeList = jdbcTemplate.queryForList(secondNodesSql,new Object[]{agentId,instId,objId});

            for(int i=0;i<vmSecondNodeList.size();i++) {
                Map map =  vmSecondNodeList.get(i);
                // 添加其他参数
                setJsonValue(map,3);
            }
            list.addAll(vmSecondNodeList);
        }
        //三级以下节点
        else if(parentId.indexOf("@") >-1) {
            String[] ids = parentId.split("\\@");
            String objId = ids[0];
            String agentId = ids[2];
            String secondNodesSql = "select '../resources/images/VMHOST.png' as \"icon\",obj_id ||'/'|| (case when mo_id is null then '' else mo_id end) ||'/'|| agent_id as \"id\",obj_name as \"label\"," +
                    "obj_parent || '#'||amp_inst_id || '#'||agent_id as \"pid\",obj_id as \"obj_id\",obj_type as \"obj_type\", " +
                    "case when obj_type='HostSystem' then 'vmHost' " +
                    "when obj_type='VirtualMachine' then 'vmVHost' " +
                    "else '' end as \"defaultView\", " +
                    "case when obj_type='HostSystem' then 'vmHost,kpiListPanel,kpiTrend,alarmSearch' " +
                    "when obj_type='VirtualMachine' then 'vmVHost,kpiListPanel,kpiTrend,alarmSearch' " +
                    "else '' end as \"views\" " +
                    "from TD_AVMON_AMP_VM_HOST\n" +
                    "where obj_parent = ?  \n" +
                    "and agent_id = ? \n" +
                    "and enable_flag = 1 " +
                    "and obj_type in ('VirtualMachine')"
                    + " order by obj_name";

            List<Map> vmSecondNodeList = jdbcTemplate.queryForList(secondNodesSql,new String[]{objId,agentId});

            for(int i=0;i<vmSecondNodeList.size();i++) {
                Map map =  vmSecondNodeList.get(i);
                // 添加其他参数
                setJsonValue(map,4);
            }
            list.addAll(vmSecondNodeList);
        }
        
        String treeDataJson = JackJson.fromObjectToJson(list);
        
        return treeDataJson;

    }

	public Map getVmBasicInfo(HttpServletRequest request) {
		String moId = request.getParameter("moId");
		Map m = new HashMap();
		m.put("vmIp", kpiDataStore.getCurrentKpiStringValue(moId, "5102200006"));
		m.put("vmModel", kpiDataStore.getCurrentKpiStringValue(moId, "5102100009"));
		m.put("vmCpuMhz", kpiDataStore.getCurrentKpiStringValue(moId, "5102200015"));
		m.put("vmCpuCore", kpiDataStore.getCurrentKpiStringValue(moId, "5102200012"));
		m.put("vmMem", kpiDataStore.getCurrentKpiStringValue(moId, "5102200011"));
		m.put("vmNet", kpiDataStore.getCurrentKpiStringValue(moId, "5102200013"));
		m.put("vmDisk", kpiDataStore.getCurrentKpiStringValue(moId, "5102200014"));
		m.put("vmUsedNet", kpiDataStore.getCurrentKpiStringValue(moId, "5102200017"));
		m.put("vmUsedPool", kpiDataStore.getCurrentKpiStringValue(moId, "5102200018"));
		m.put("vmPower", kpiDataStore.getCurrentKpiStringValue(moId, "5102200010"));
		m.put("vmNetUsedAvg", kpiDataStore.getCurrentKpiStringValue(moId, "5101205001"));
		
		String allMem = kpiDataStore.getCurrentKpiValue(moId, "5101202004");
		String usedMem = kpiDataStore.getCurrentKpiValue(moId, "5101202005");

		if(allMem!=null&&allMem.length()>0){
			if(usedMem!=null&&usedMem.length()>0){
				m.put("vmMemLeft", Integer.valueOf(allMem)-Integer.valueOf(usedMem));
			}else{
				m.put("vmMemLeft",allMem);
			}
		}else{
			m.put("vmMemLeft","");
		}
		m.put("vmMemUsage", kpiDataStore.getCurrentKpiStringValue(moId, "5101202002"));
		
		m.put("vmCpuUsage", kpiDataStore.getCurrentKpiStringValue(moId, "5101201001"));
		m.put("vmNetRead", kpiDataStore.getCurrentKpiStringValue(moId, "5101205002"));
		m.put("vmNetWrite", kpiDataStore.getCurrentKpiStringValue(moId, "5101205003"));
		
		/**
		"5101203001";"虚拟磁盘平均写入速度（KBps）"
		"5101203002";"虚拟磁盘平均读取速度（KBps）"
		"5101203003";"虚拟机磁盘使用量(KBps)"
		"5102200014";"虚拟机虚拟磁盘个数"
		 */
		m.put("vmDiskNum", kpiDataStore.getCurrentKpiStringValue(moId, "5102200014"));
		m.put("vmDiskRead", kpiDataStore.getCurrentKpiStringValue(moId, "5101203001"));
		m.put("vmDiskWrite", kpiDataStore.getCurrentKpiStringValue(moId, "5101203002"));
		m.put("vmDiskUsage", kpiDataStore.getCurrentKpiStringValue(moId, "5101203003"));
		return m;
	}

	public List<Map<String, Object>> getOverViewList(HttpServletRequest request) {
		String vmType=request.getParameter("vmType");
		String sqlString=SqlManager.getSql(this.getClass(),"getOverViewList");
		String sql=String.format(sqlString,vmType);
		
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
	}
	
	public List getVmAlarmData(HttpServletRequest request) {//以后可能要加查询条件
        String sql="select count(*) as \"count\" , a.current_grade as \"grade\",amp_inst_id as \"type\" " + 
			"from tf_avmon_alarm_data a " + 
			"left join td_avmon_amp_vm_host b " + 
			"on a.mo_id=b.mo_id " + 
			"where a.mo_id " + 
			"in(select mo_id from td_avmon_amp_vm_host where enable_flag=1 ) " + 
			"group by a.current_grade,b.amp_inst_id " + 
			"order by a.current_grade" ;
        List list=jdbcTemplate.queryForList(sql);
        return list;
    }
}
