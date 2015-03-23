package com.hp.avmon.performance.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.performance.store.KpiDataStore;
import com.hp.avmon.performance.store.ResourceStore;
import com.hp.avmon.utils.Constants;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.MyString;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.Utils;
import com.hp.avmonserver.api.AvmonServer;
import com.hp.avmonserver.entity.KpiEvent;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@Service
public class PerformanceService {
    
    private static final Log logger = LogFactory.getLog(PerformanceService.class);
    
    @Autowired
    private AvmonServer avmonServer;
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired @Qualifier("kpiDataStore")
    private KpiDataStore kpiDataStore;
    
    private static List<KpiEvent> kpiValueList= new ArrayList<KpiEvent>();
    private static Map kpiNameCache=new HashMap();
    private static Map kpiTypeCache=new HashMap();
    
    public PerformanceService(){
     
    }
    
    //*/

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
                map.put("leaf","false");
            }else{
                map.put("leaf","true");
            }
        }
        
        map.put("iconClsPause","icon-pause");
        map.put("iconClsError","icon-error");
        map.put("resourcePicture","network.jpg");
        map.put("resourcePictureDirection","0");
        map.put("isInstance","0");
        map.put("expanded",false);
        map.put("type","VM");
    }

    public String getMoTreeJson(String userId,String parentId,String checkbox,ResourceBundle bundle) throws IOException {
    	
    	String pingStatus = bundle.getString("pingStatus");
        String sql=String.format(SqlManager.getSql(this, "perf_menu_tree"),parentId,parentId);
        List<Map> list = jdbcTemplate.queryForList(sql);
        if(!"true".equalsIgnoreCase(checkbox) && "root".equals(parentId)){
        	Map pingMap = new HashMap();
            pingMap.put("id", "PINGSTATUS");
            pingMap.put("text", pingStatus);
            pingMap.put("pid", "root");
            pingMap.put("views", "pingStatusPanel");
            pingMap.put("defaultView", "pingStatusPanel");
            pingMap.put("iconCls", "icon-hardware");
            pingMap.put("iconClsPause", "icon-pause");
            pingMap.put("iconClsError", "icon-error");
            pingMap.put("resourcePicture", "hardware.jpg");
            pingMap.put("resourcePictureDirection", 0);
            pingMap.put("isInstance", 0);
            pingMap.put("leaf", "true");
            pingMap.put("expanded", "true");
            list.add(pingMap);
        }

        //查询虚机主节点
        if(parentId.equals("VMHOST")){
            String vmMainSql = "SELECT DISTINCT a.agent_id AS \"agentId\",a.amp_inst_id ||'$'||a.agent_id ||'$'||'VMHOST' AS \"id\",c.ip AS \"text\",'resourceListPanel' as \"views\",'resourceListPanel' as \"defaultView\" FROM TD_AVMON_AMP_INST a,TD_AVMON_AMP b ,td_avmon_agent c WHERE a.amp_id=b.amp_id and a.AGENT_ID = c.AGENT_ID  and   a.status = 1 AND upper(b.type)='VM'";
            List<Map> vmMainList = jdbcTemplate.queryForList(vmMainSql);
            for(int i=0;i<vmMainList.size();i++) {
                Map map =  vmMainList.get(i);
                // 添加其他参数
                setJsonValue(map,1);
            }
            list.clear();
            list.addAll(vmMainList);
            
        }
        //一级VM节点 8F-TSLab
        else if(parentId.indexOf("$") >-1){
            String agentId = parentId.split("\\$")[1];
            String instId = parentId.split("\\$")[0];
            logger.debug("agentId="+agentId);
            logger.debug("instId="+instId);
           String firstSubNodeSql = "SELECT obj_id ||'&'||amp_inst_id || '&'||agent_id || '&'||mo_id AS \"id\",obj_name AS \"text\"," +
                "amp_inst_id ||'$'||agent_id AS \"pid\",obj_id as \"obj_id\",obj_type as \"obj_type\",'vmList' as \"views\",'vmList' as \"defaultView\" " +
                "FROM TD_AVMON_AMP_VM_HOST WHERE agent_id=? AND amp_inst_id=? AND obj_parent='-1'";
            List<Map> vmFirstSubList = jdbcTemplate.queryForList(firstSubNodeSql,new Object[]{agentId,instId});

            for(int i=0;i<vmFirstSubList.size();i++) {
                Map map =  vmFirstSubList.get(i);
                // 添加其他参数
                setJsonValue(map,2);
            }
            list.addAll(vmFirstSubList);
        }
        //二级以下节点
        else if(parentId.indexOf("&") >-1) {
            String[] ids = parentId.split("\\&");
            String objId = ids[0];
            String agentId = ids[2];
            String instId = ids[1];
            String secondNodesSql = "select obj_id ||'@'|| mo_id ||'@'|| agent_id as \"id\",obj_name as \"text\"," +
                    "obj_parent || '&'||amp_inst_id || '&'||agent_id,obj_id as \"obj_id\",obj_type as \"obj_type\", " +
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
                    ;

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
            String secondNodesSql = "select mo_id as \"id\",obj_name as \"text\"," +
                    "obj_parent || '&'||amp_inst_id || '&'||agent_id,obj_id as \"obj_id\",obj_type as \"obj_type\", " +
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
                    ;

            List<Map> vmSecondNodeList = jdbcTemplate.queryForList(secondNodesSql,new String[]{objId,agentId});

            for(int i=0;i<vmSecondNodeList.size();i++) {
                Map map =  vmSecondNodeList.get(i);
                // 添加其他参数
                setJsonValue(map,4);
            }
            list.addAll(vmSecondNodeList);
        }
        
        else if("iloRoot".equals(parentId)){
            String iloSql = "select mo_id as \"id\",caption as \"text\",'iloRoot' as \"pid\"," +
                    "'true' as \"leaf\",'ILO' as \"type\",'iloHost,kpiListPanel,kpiTrend,alarmSearch' as \"views\",'iloHost' as \"defaultView\" " +
                    "from td_avmon_mo_info where type_id ='iloRoot'";
            List<Map> iloList = jdbcTemplate.queryForList(iloSql);
            list.clear();
            list.addAll(iloList);
        }
        
        //SNMP start
        else if(parentId.startsWith("SNMP")){
        	String snmpSql = SqlManager.getSql(this, "perf_menu_tree_snmp");
        	if("SNMP".equals(parentId)){
        		snmpSql = String.format(snmpSql, "SNMPOID","SNMPOID");
        	}else{
        		String param = (String) parentId.substring(4, parentId.length());
        		snmpSql = String.format(snmpSql, param, param);
        	}
        	
            List<Map> snmpList = jdbcTemplate.queryForList(snmpSql);
            list.clear();
            list.addAll(snmpList);
        }
        //SNMP end

        String treeDataJson = JackJson.fromObjectToJson(list);
        
        return treeDataJson;

    }

    public String kpiList(String moId, String ampInstId, String kpiCode) throws Exception {

        List<KpiEvent> kpiList=null;
        List<Map> listMap=new LinkedList<Map>();
        
        String sql="select KPI_CODE,CAPTION,KPI_TYPE from TD_AVMON_KPI_INFO";
        List<Map<String,Object>> kpiInfoList=jdbcTemplate.queryForList(sql);
        kpiNameCache.clear();
        kpiTypeCache.clear();
        if(kpiInfoList!=null){
            for(Map<String,Object> m:kpiInfoList){
                kpiNameCache.put(m.get("KPI_CODE"), m.get("CAPTION"));
                kpiTypeCache.put(m.get("KPI_CODE"), m.get("KPI_TYPE"));
            }
        }

        if(kpiCode==null || kpiCode.equals("")){
            kpiList=kpiDataStore.getKpiList(moId);
            logger.debug(JackJson.fromObjectToJson(kpiList));
            if(kpiList!=null){
                Map<String,Map> cache=new HashMap();
                for(KpiEvent event:kpiList){
                    String eventKey=event.getAmpInstId()+"."+event.getKpiCode();
                    Map map=cache.get(eventKey);
                    if(map==null){
                        map=new HashMap();
                        map.put("MO_ID", event.getMoId());
                        map.put("AMP_INSTANCE_ID",event.getAmpInstId());
                        map.put("KPI_CODE", event.getKpiCode());
                        map.put("VALUE", event.getValue());
                        map.put("STR_VALUE", event.getStrValue());
                        map.put("NUM_VALUE", event.getNumValue());
                        map.put("VALUE_DESC", event.getDescription());
                        map.put("KPI_TIME", event.getKpiTime());
                        map.put("INSTANCE", event.getInstance());
                        map.put("THRESHOLD_LEVEL", event.getThresholdLevel());
                        map.put("LAST_UPDATE_TIME", event.getKpiTime());
                        map.put("HAS_CHILDREN", 0);
                        map.put("ITEM_COUNT",0);
                        map.put("THRESHOLD", event.getThreshold());
                        map.put("MO_NAME", event.getMoId());
                        map.put("KPI_NAME", kpiNameCache.get(event.getKpiCode()));
                        map.put("AMP_INSTANCE_NAME",kpiTypeCache.get(event.getKpiCode()));
                        cache.put(eventKey, map);
                        listMap.add(map);
                    }
                    else{
                        map.put("HAS_CHILDREN", 1);
                        if(event.getThresholdLevel()>(Integer)map.get("THRESHOLD_LEVEL")){
                            map.put("THRESHOLD_LEVEL", event.getThresholdLevel());
                        }
                        map.put("ITEM_COUNT", (Integer)map.get("ITEM_COUNT")+1);
                        map.put("VALUE_DESC","");
                    }
                }
            }
        }
        else{
            kpiList=kpiDataStore.getKpiList(moId, ampInstId, kpiCode);
            for(KpiEvent event:kpiList){
                Map map=new HashMap();
                map.put("MO_ID", event.getMoId());
                map.put("AMP_INSTANCE_ID",event.getAmpInstId());
                map.put("KPI_CODE", event.getKpiCode());
                map.put("VALUE", event.getValue());
                map.put("STR_VALUE", event.getStrValue());
                map.put("NUM_VALUE", event.getNumValue());
                map.put("VALUE_DESC", event.getDescription());
                map.put("KPI_TIME", event.getKpiTime());
                map.put("INSTANCE", event.getInstance());
                map.put("THRESHOLD_LEVEL", event.getThresholdLevel());
                map.put("LAST_UPDATE_TIME", event.getKpiTime());
                map.put("HAS_CHILDREN", 0);
                map.put("ITEM_COUNT",0);
                map.put("THRESHOLD", event.getThreshold());
                map.put("MO_NAME", event.getMoId());
                map.put("KPI_NAME", kpiNameCache.get(event.getKpiCode()));
                map.put("AMP_INSTANCE_NAME",event.getAmpInstId());
                listMap.add(map);
            }
        }
        
        
        return JackJson.fromObjectToJson(listMap);
        
    }


    private String getMonitorInstanceName(String moId, String kpiId) {
        // TODO Auto-generated method stub
        return null;
    }


    public Map getPerformanceSummaryList(String parentId,String businessSystem,ResourceBundle bundle) {
    	String businessSystemSqlOther = bundle.getString("businessSystemSqlOther");
    	String w0 = " where 1=1";
        String w1="1=2";
        String w2="1=1";
        String agentId = "";
//        Boolean vcenterRoot = true;
        if(parentId.indexOf("&") >-1)
        {
            String[] ids = parentId.split("\\&");
            if(ids[1].equals("amp_vcenter"))
            {
                parentId = "VMHOST";
//                vcenterRoot = false;
            }
        }
        //add by mark start 2014-6-25
        if(parentId.indexOf("$") >-1)
        {
            String[] ids = parentId.split("\\$");
            if(ids[0].equals("amp_vcenter"))
            {
                parentId = "VMHOST";
                agentId = ids[1];
            }
        }
        
        //add by mark end 2014-6-25
        
        if(businessSystem!=null && !businessSystem.equals("")){
            w2="a.type_id='"+parentId+"' and a.mo_id in (select a.mo_id from TD_AVMON_MO_INFO a left join TD_AVMON_MO_INFO_ATTRIBUTE b on a.mo_id=b.mo_id and b.name='businessSystem' where ((b.value is null and '"+businessSystem+"'='"+businessSystemSqlOther+"') or b.value='"+businessSystem+"'))";
        }
        else{
            w1="a.parent_id='"+parentId+"'";
            w2="a.type_id='"+parentId+"'";
        }
        
        if(!StringUtils.isEmpty(agentId)){
        	w0 = " and a.agent_id =  '"+agentId+"' ";
//        	w1=" 2=2 ";
        	w2+= " and agent_id = '"+agentId+"'";
        }
        
        String sql = StringUtils.EMPTY;
        
        if("HARDWARE".equals(parentId)){
        	sql = SqlManager.getSql(this, "getPerformanceSummaryList_hardware");
        }else{
        	sql=String.format(SqlManager.getSql(this, "getPerformanceSummaryList"),w0,w1,w2);	
        }
        
        logger.debug("=========getPerformanceSummaryList==============="+sql);
        List<Map> list=jdbcTemplate.queryForList(sql);
        Map map=new HashMap();
        if(list!=null){
            map.put("rows",list);
            map.put("total", list.size());
        }
        else{
            map.put("rows",new ArrayList());
            map.put("total", 0);
        }
        return map;
    }
    

    public boolean isSnmpMontiorObject(HttpServletRequest request) throws Exception {
        String moId=request.getParameter("moId");
        String sql="select (case  WHEN issnmp is null then  '0' ELSE  issnmp END) as " +
        		" \"issnmp\" from td_avmon_mo_info where mo_id = '"+moId+"'";
        List<Map> list=jdbcTemplate.queryForList(sql);
        String issnmp=(String) list.get(0).get("issnmp");
        if(issnmp.equals("0")){
        	return false;
        }
        return true;
    }

    public List totalViewData(HttpServletRequest request) {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
    	String others = bundle.getString("others");
   /* 	String userId=Utils.getCurrentUserId(request);
        String parentId=request.getParameter("id");
        if(parentId==null||parentId.equals("")){
            parentId="root";
        }*/
        String sql=SqlManager.getSql(this, "totalview").replace("{totalViewOther}", others);
        List<Map> list=jdbcTemplate.queryForList(sql);
        
  /*      Map map=new HashMap();
        if(list!=null){
            map.put("rows",list);
            map.put("total", list.size());
        }
        else{
            map.put("rows",new ArrayList());
            map.put("total", 0);
        }*/
        return list;

    }
    
    
    public List getAllBiz(HttpServletRequest request) {
        Map map=new HashMap(); 
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
    	String others = bundle.getString("others");
        String sql="select id as \"id\", businessname as \"name\" from tf_avmon_biz_dictionary";
        List list=jdbcTemplate.queryForList(sql);
        
        Map otherMap=new HashMap();
        otherMap.put("id", list.size()+1);
        //otherMap.put("name", "others");
        otherMap.put("name", "其他");
        list.add(otherMap);
        return list;
    }
    
    
    public List getAllLevelAlarmDataByType(HttpServletRequest request) {
    	String typeId=request.getParameter("typeId");
    	String queryIp=request.getParameter("queryIp");//add by muzh
    	String bizName=request.getParameter("bizName");
    	String bizCon = " ";
    	try {
    		bizName=URLDecoder.decode(bizName, "utf-8");
    		if(bizName.length()>0){
    			bizCon = " and c.value='"+bizName+"' ";
    		}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}//字符编码和文档编码一致
    	 String sql="";
    	if(bizName.equals("others")){
    		sql="select count(*) as \"count\" , a.current_grade as \"grade\",b.type_id as \"type\" " +
    				" from tf_avmon_alarm_data a inner join " +
    				" TD_AVMON_MO_INFO b on a.mo_id = b.mo_id " +
    				" and b.type_id like '"+typeId+"_%' " +
	        		" and upper(b.caption) like upper('%"+(queryIp!=null?queryIp:"")+"%')" +
    				" inner join  " +
    				" (select attr.mo_id,attr.value from td_avmon_mo_info_attribute attr where name='businessSystem' " +
    				" and attr.value not in (select businessname as \"name\" from tf_avmon_biz_dictionary) union all " +
    						" ( select distinct mo_id, 'Other' from td_avmon_mo_info_attribute attr " +
    						" where attr.mo_id not in (select mo_id from td_avmon_mo_info_attribute attr " +
    						" where name='businessSystem')) ) t on a.mo_id = t.mo_id " +
    						" group by a.current_grade,b.type_id order by a.current_grade";
    	}else{
    		  sql="select count(*) as \"count\" , a.current_grade as \"grade\",b.type_id as \"type\" " +
    	        		"from tf_avmon_alarm_data a, TD_AVMON_MO_INFO b ,td_avmon_mo_info_attribute c " +
    	        		"where a.mo_id=b.mo_id  and  b.mo_id = c.mo_id and c.name='businessSystem' " +
    	        		" and b.type_id like '"+typeId+"_%' " +
    	        		bizCon+
    	        		" and upper(b.caption) like upper('%"+(queryIp!=null?queryIp:"")+"%')" +//add by muzh 
    	        		" group by a.current_grade,b.type_id order by a.current_grade";
    	}
       
        List list=jdbcTemplate.queryForList(sql);
        return list;
    }
    
    public List getAllLevelAlarmDataByOtherHWType(HttpServletRequest request) {
    	String typeId=request.getParameter("typeId");
    	String queryIp=request.getParameter("queryIp");//add by muzh
    	String bizName=request.getParameter("bizName");
    	try {
    		bizName=URLDecoder.decode(bizName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}//字符编码和文档编码一致
        String sql="select count(*) as \"count\" , a.current_grade as \"grade\",b.type_id as \"type\" " +
        		"from tf_avmon_alarm_data a, TD_AVMON_MO_INFO b ,td_avmon_mo_info_attribute c " +
        		"where a.mo_id=b.mo_id  and  b.mo_id = c.mo_id and c.name='businessSystem' " +
        		" and b.type_id like  parent_id = 'HARDWARE' and type_id not in ('HARDWARE_HP','HARDWARE_DELL') " +
        		" and c.value='"+bizName+"'"+
        		" and upper(b.caption) like upper('%"+(queryIp!=null?queryIp:"")+"%')" +//add by muzh 
        		" group by a.current_grade,b.type_id order by a.current_grade";
        List list=jdbcTemplate.queryForList(sql);
        return list;
    }
    
    public Map getMoBasicInfo(String moId) {
        // TODO Auto-generated method stub
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        Map<String, Object> map = new HashMap<String, Object>();
        if(typeId.equals("HOST_WINDOWS")){
            map.put("hostImage", "SYS_Windows.png");
        }else if(typeId.equals("HOST_LINUX")){
            map.put("hostImage", "SYS_Linux.png");
        }else if(typeId.equals("HOST_SUNOS")){
            map.put("hostImage", "SYS_Solaris.png");
        }else if(typeId.equals("HOST_AIX")){
            map.put("hostImage", "SYS_Aix.png");
        }else if(typeId.equals("HOST_HP-UX")){
            map.put("hostImage", "SYS_Unix.png");
        }else {
            map.put("hostImage", "SYS_Others.png");
        }
        return map;
    }
    
    /**
	 * 取得活动告警总数
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	public int getAlarmListByCriteriaCount(
			HttpServletRequest request) throws Exception {
		HashMap filter = new HashMap();
		// 高级检索条件对应
		// 设备名称
		filter.put("moId",request.getParameter("moId"));
		if (null != request.getParameter("s_moCaption")) {
			filter.put(
					"s_moCaption",
					new String(request.getParameter("s_moCaption").getBytes(
							"ISO-8859-1"), "UTF-8"));
		}

		// 告警内容
		if (null != request.getParameter("s_content_t")) {

			filter.put(
					"s_content_t",
					new String(request.getParameter("s_content_t").getBytes(
							"ISO-8859-1"), "UTF-8"));
		}

		// 处理人
		if (!StringUtil.isEmpty(request.getParameter("s_aknowledge_by"))) {
			JdbcTemplate jdbcTemplate = SpringContextHolder
					.getBean("jdbcTemplate");

			String sql = "select USER_ID as \"USER_ID\" from portal_users where real_name like '%"
					+ new String(request.getParameter("s_aknowledge_by")
							.getBytes("ISO-8859-1"), "UTF-8") + "%' ";
			List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
			String userId = "";
			for (Map<String, Object> map : list) {
				userId += "'" + map.get("USER_ID") + "'" + ",";
			}
			if (!StringUtil.isEmpty(userId) && userId.length() > 0) {
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark start 2014-1-20
			else {
				userId += "'" + request.getParameter("s_aknowledge_by") + "'"
						+ ",";
				userId = userId.substring(0, userId.length() - 1);
			}
			// add by mark end 2014-1-20

			filter.put("s_aknowledge_by", userId);
		}

		

		// 告警开始时间
		String s_start_date = request.getParameter("s_start_time");
		String s_start_hours = request.getParameter("s_start_hours");
		String s_start_minutes = request.getParameter("s_start_minutes");
		if(s_start_hours==null&&s_start_minutes==null){
			s_start_hours="00";
			s_start_minutes="00";
		} 
		if (!StringUtil.isEmpty(s_start_date)
				&& !StringUtil.isEmpty(s_start_hours)
				&& !StringUtil.isEmpty(s_start_minutes)) {
			String s_start_time = s_start_date + " " + s_start_hours + ":"
					+ s_start_minutes;
			filter.put("s_start_time", s_start_time);
		} else {
			filter.put("s_start_time", null);
		}

		// 告警结束时间 2012/12/05 00:00
		String s_end_date = request.getParameter("s_end_time");
		String s_end_hours = request.getParameter("s_end_hours");
		String s_end_minutes = request.getParameter("s_end_minutes");
		if(s_end_hours==null&&s_end_minutes==null){
			s_end_hours="00";
			s_end_minutes="00";
		} 
		if (!StringUtil.isEmpty(s_end_date) && !StringUtil.isEmpty(s_end_hours)
				&& !StringUtil.isEmpty(s_end_minutes)) {
			String s_end_time =null;
			//if(s_end_date.equals(s_start_date)){
				  SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
				  Calendar cal = Calendar.getInstance();
					 cal.setTime(format.parse(s_end_date));
					 cal.add(Calendar.DATE, 1);
					 s_end_date=format.format(cal.getTime());
					 s_end_time = s_end_date + " " + s_end_hours + ":"+ s_end_minutes;
			filter.put("s_end_time", s_end_time);
		} else {
			filter.put("s_end_time", null);
		}
		String limit = request.getParameter("limit_active");
		String start = request.getParameter("start_active");
		int count=0;
		count = getActiveAlarmByCriteriaCount(request,
					filter, limit, start);
		return count;
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private int getActiveAlarmByCriteriaCount(HttpServletRequest request, Map filter, String limit, String start) {
		String whereCondition = this.getActiveAlarmSqlWhereByCriteria(
				filter);
		String sql = String.format(
				SqlManager.getSql(PerformanceService.class, "getAlarmSearchListCount"),
				whereCondition);
     	int count = jdbcTemplate.queryForInt(sql);
		return count;
	}
	
    public List<Map<String, Object>> getAlarmListByCriteria(
			HttpServletRequest request) throws Exception {
    	// 高级检索条件对应
    			// 设备名称
    	
    	HashMap filter = new HashMap();
    	filter.put("moId",request.getParameter("moId"));
    			if (null != request.getParameter("s_moCaption")) {
    				filter.put(
    						"s_moCaption",
    						new String(request.getParameter("s_moCaption").getBytes(
    								"ISO-8859-1"), "UTF-8"));
    			}

    			// 告警内容
    			if (null != request.getParameter("s_content_t")) {

    				filter.put(
    						"s_content_t",
    						new String(request.getParameter("s_content_t").getBytes(
    								"ISO-8859-1"), "UTF-8"));
    			}
    			// 处理人
    			if (!StringUtil.isEmpty(request.getParameter("s_aknowledge_by"))) {
    				JdbcTemplate jdbcTemplate = SpringContextHolder
    						.getBean("jdbcTemplate");

    				String sql = "select USER_ID as \"USER_ID\" from portal_users where real_name like '%"
    						+ new String(request.getParameter("s_aknowledge_by")
    								.getBytes("ISO-8859-1"), "UTF-8") + "%' ";
    				List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
    				String userId = "";
    				for (Map<String, Object> map : list) {
    					userId += "'" + map.get("USER_ID") + "'" + ",";
    				}
    				if (!StringUtil.isEmpty(userId) && userId.length() > 0) {
    					userId = userId.substring(0, userId.length() - 1);
    				}
    				// add by mark start 2014-1-20
    				else {
    					userId += "'" + request.getParameter("s_aknowledge_by") + "'"
    							+ ",";
    					userId = userId.substring(0, userId.length() - 1);
    				}
    				// add by mark end 2014-1-20

    				filter.put("s_aknowledge_by", userId);
    			}

    			// 告警开始时间
    			String s_start_date = request.getParameter("s_start_time");
    			String s_start_hours = request.getParameter("s_start_hours");
    			String s_start_minutes = request.getParameter("s_start_minutes");
    			if(s_start_hours==null&&s_start_minutes==null){
    				s_start_hours="00";
    				s_start_minutes="00";
    			}
    			
    			if (!StringUtil.isEmpty(s_start_date)
    					//&& !StringUtil.isEmpty(s_start_hours)
    					//&& !StringUtil.isEmpty(s_start_minutes)
    					) {
    				String s_start_time = s_start_date + " " + s_start_hours + ":"
    						+ s_start_minutes;
    				filter.put("s_start_time", s_start_time);
    			} else {
    				filter.put("s_start_time", null);
    			}

    			// 告警结束时间 2012/12/05 00:00
    			String s_end_date = request.getParameter("s_end_time");
    			String s_end_hours = request.getParameter("s_end_hours");
    			String s_end_minutes = request.getParameter("s_end_minutes");
    			if(s_end_hours==null&&s_end_minutes==null){
    				s_end_hours="00";
    				s_end_minutes="00";
    			}
    			if (!StringUtil.isEmpty(s_end_date) ) {
    				String s_end_time =null;
    				//if(s_end_date.equals(s_start_date)){
    					  SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
    					  Calendar cal = Calendar.getInstance();
    						 cal.setTime(format.parse(s_end_date));
    						 cal.add(Calendar.DATE, 1);
    						 s_end_date=format.format(cal.getTime());
    						 s_end_time = s_end_date + " " + s_end_hours + ":"+ s_end_minutes;
    				//}
    				//else{
    					 //s_end_time = s_end_date + " " + s_end_hours + ":"+ s_end_minutes;
    				//}
    				filter.put("s_end_time", s_end_time);
    			} else {
    				filter.put("s_end_time", null);
    			}

    			// List<Integer> levelList = new ArrayList<Integer>();
    			// int rowCount = getActiveAlarmCount(path, levels, filter);
    			String limit = request.getParameter("limit_active");
    			String start = request.getParameter("start_active");
    			List<Map<String, Object>> list = null;
    			list = getActiveAlarmByCriteria(request,filter, limit, start);
    			return list;
    	
    	
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
	private List<Map<String, Object>> getActiveAlarmByCriteria(HttpServletRequest request,
		 Map filter, String limit, String start) {
		String whereCondition = this.getActiveAlarmSqlWhereByCriteria(filter);
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		if (sortdatafield == null)
			sortdatafield = "MO_CAPTION";
		if (sortorder == null)
			sortorder = "desc";
		//String ssString=SqlManager.getSql(PerformanceService.class, "getAlarmSearchCriteriaList");
		String ssString=SqlManager.getSql(PerformanceService.class, "getAlarmSearchList");
		String sql = String.format(ssString,whereCondition);
		String pagesize = request.getParameter("pagesize");
		if(pagesize==null)
			pagesize="20";
		String pagenum = request.getParameter("pagenum");
		if(pagenum==null)
			pagenum="0";
		Integer pagenum1 = (Integer.parseInt(pagenum));
		String querySql="";
		if(DBUtils.isOracle()){
			querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
			querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
					+ sortorder;
		}
		else{
			querySql = generatPageSqlForJQW(sql+ " order by " + "\"" + sortdatafield + "\"" + " " + sortorder, pagesize, pagenum1.toString());
		}
		//System.out.println("queryPerformanceSql is:"+querySql);
		List<Map<String, Object>> listMap = jdbcTemplate.queryForList(querySql);
		return listMap;
	}
    @SuppressWarnings("rawtypes")
	private String getActiveAlarmSqlWhereByCriteria(Map filter) {
		//String where = " where 1=1 ";
		String where = " ";
		// other filters
		if (filter != null) {
			String moId = (String) filter.get("moId");
			if (moId != null && moId.length() > 0) {
				where += " and t1.MO_ID =" + "'"+ moId +"'"+ " ";
			}
			// 设备名称
			String s_moCaption = (String) filter.get("s_moCaption");
			if (s_moCaption != null && s_moCaption.length() > 0) {
				where += " and t2.CAPTION like '%" + s_moCaption + "%' ";
			}
			// 告警内容
			String s_content_t = (String) filter.get("s_content_t");
			if (s_content_t != null && s_content_t.length() > 0) {
				where += " and t1.content like '%" + s_content_t + "%' ";
			}
			// IP地址
			String s_attr_ipaddr = (String) filter.get("s_attr_ipaddr");
			if (s_attr_ipaddr != null && s_attr_ipaddr.length() > 0) {
				where += " and t3.VALUE like '%" + s_attr_ipaddr + "%' ";
			}
			// 处理人
			String s_aknowledge_by = (String) filter.get("s_aknowledge_by");
			if (s_aknowledge_by != null && s_aknowledge_by.length() > 0) {
				where += " and t1.CONFIRM_USER in (" + s_aknowledge_by + ") ";
			}
			// 业务系统
			String s_attr_businessSystem = (String) filter
					.get("s_attr_businessSystem");
			if (s_attr_businessSystem != null
					&& s_attr_businessSystem.length() > 0) {
				where += " and t4.VALUE like '%" + s_attr_businessSystem
						+ "%' ";
			}
			// 位置
			String s_attr_position = (String) filter.get("s_attr_position");
			if (s_attr_position != null && s_attr_position.length() > 0) {
				where += " and t5.VALUE like '%" + s_attr_position + "%' ";
			}
			// 告警开始时间
			String s_start_time = (String) filter.get("s_start_time");
			if (s_start_time != null && s_start_time.length() > 0) {
				try {
					where += " and t1.first_occur_time >= TO_DATE('"
							+ s_start_time + "', 'YYYY-MM-DD HH24:MI:SS')";
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			// 告警结束时间
			String s_end_time = (String) filter.get("s_end_time");
			if (s_end_time != null && s_end_time.length() > 0) {
				try {
					where += " and t1.first_occur_time < TO_DATE('"
							+ s_end_time + "', 'YYYY-MM-DD HH24:MI:SS')";
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}

			String content = (String) filter.get("content");
			if (content != null && content.length() > 0 && !content.equals("*")) {
				where += " and t1.content like '%" + content + "%' ";
			}

			if (filter.get("occurTimes") != null
					&& ((String) filter.get("occurTimes")).length() > 0) {
				Integer occurTimes = Integer.valueOf((String) filter
						.get("occurTimes"));
				where += " and t1.count>=" + occurTimes + " ";
			}
			// 页面最后提交请求时间
			String lastUpdateTimeStr = (String) filter.get("lastUpdateTime");
			if (lastUpdateTimeStr != null && lastUpdateTimeStr.length() > 0) {
				where += " and t1.LAST_OCCUR_TIME >= TO_DATE('"
						+ lastUpdateTimeStr + "', 'YYYY-MM-DD HH24:MI:SS')";
			}
		}
		return where;
	}
	
    /**
     * 取得活动告警
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List getAlarmList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
    	//String treeId = request.getParameter("treeId");
    	// add by mark start 2013-9-14
    	//this is about the VCenter's moId (是由  obj_id ||'&'||amp_inst_id || '&'||agent_id)
    	/*if (treeId.contains("&")) {
    		String[] params = treeId.split("\\&");
    		String objId = params[0];
    		String ampInstId = params[1];
    		String agentId = params[2];
			String moId = this.getVmMoId(objId,ampInstId,agentId);
			if (null!= moId) {
				treeId = moId;
			}
			logger.info("moId is>>>>>>>>>>>>>>> "+moId);
		}*/
    	// add by mark end 2013-9-14

        String tempSql = SqlManager.getSql(PerformanceService.class, "getAlarmSearchList");
       
        StringBuilder sb = new StringBuilder();
        sb.append(" and t1.MO_ID='" + moId + "'");
        String sql = String.format(tempSql, sb.toString());
       // String querySql = MyFunc.generatPageSql(sql, limit, start);
        //System.out.println("tempSql is:"+sql);
        String pagesize = request.getParameter("pagesize");
		if(pagesize==null)
			pagesize="20";
		String pagenum = request.getParameter("pagenum");
		if(pagenum==null)
			pagenum="0";
		Integer pagenum1 = (Integer.parseInt(pagenum));
        String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		//System.out.println("allquerySql is:"+querySql);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(querySql);
        
		/*Map<String, Object> map = new HashMap<String, Object>();
        map.put("alarmTotal", list.size());
        map.put("alarmData", list);*/
		return list;
    }
    
    
    /**
     * 取得活动告警
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public int getAlarmListCount(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        String tempSql = SqlManager.getSql(PerformanceService.class, "getAlarmSearchListCount");
        StringBuilder sb = new StringBuilder();
        sb.append(" and t1.MO_ID='" + moId + "'"); 
        String sql = String.format(tempSql, sb.toString());
       // String querySql = MyFunc.generatPageSql(sql, limit, start);
       /* String pagesize = request.getParameter("pagesize");
		if(pagesize==null)
			pagesize="20";
		String pagenum = request.getParameter("pagenum");
		if(pagenum==null)
			pagenum="0";
		Integer pagenum1 = (Integer.parseInt(pagenum) + 1);
        String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());*/
		int count = jdbcTemplate.queryForInt(sql);
		return count;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getBasicInfoList(HttpServletRequest request) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
    	String hostName = bundle.getString("hostName");
    	String hostAddr = bundle.getString("hostAddr");
    	String manufacturer = bundle.getString("manufacturer");
    	String cpuCount = bundle.getString("cpuCount");
    	String cpuType = bundle.getString("cpuType");
    	String memSize = bundle.getString("memSize");
    	String osVersion = bundle.getString("osVersion");
       // String treeId = request.getParameter("treeId");
    	String moId = request.getParameter("moId");
   
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map m = new HashMap();
        m.put("KEY", hostName);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "1002001001"));
        list.add(m);
        m = new HashMap();
        m.put("KEY", hostAddr);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "1002001019"));
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", manufacturer);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "1002001003"));
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", cpuCount);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "1002001004"));
        list.add(m);

        m = new HashMap();
        m.put("KEY", cpuType);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "1002001007"));
        list.add(m);

        m = new HashMap();
        
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        String memSizeKpiCode="";
        if(typeId.equals("HOST_WINDOWS")){
        	memSizeKpiCode="1702010012"; 
        }
        else{
        	memSizeKpiCode="1002001009";
        }
        m.put("KEY", memSize);
        
        m.put("VAL",MyString.toVolumeString(kpiDataStore.getCurrentKpiNumValue(moId, memSizeKpiCode)*1024*1024));
        list.add(m);

        m = new HashMap();
        m.put("KEY", osVersion);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "1002001012"));
        list.add(m);

		Map<String, Object> map = new HashMap<String, Object>();
        map.put("basicTotal", list.size());
        map.put("basicData", list);
        
        return map;
    }
    
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
   	public Map getBasicInfoForDBList(HttpServletRequest request) throws Exception {
       	Locale locale = request.getLocale();
       	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
       	/*String hostName = bundle.getString("hostName");
       	String hostAddr = bundle.getString("hostAddr");
       	String manufacturer = bundle.getString("manufacturer");
       	String cpuCount = bundle.getString("cpuCount");
       	String cpuType = bundle.getString("cpuType");
       	String memSize = bundle.getString("memSize");
       	String osVersion = bundle.getString("osVersion");*/
          // String treeId = request.getParameter("treeId");
       	

           String DBName="DBName";
           String DBVer="DBVer";
           String DBOpt="DBOpt";
           String DBBit="DBBit";
           String DBFile="DBFile";
           String moId = request.getParameter("moId");
           
           List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
           Map m = new HashMap();
           m.put("KEY", DBName);
           m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "2102016001"));
           list.add(m);
           m = new HashMap();
           m.put("KEY", DBVer);
           m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "2102016002"));
           list.add(m);
           
           m = new HashMap();
           m.put("KEY", DBOpt);
           m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "2102016003"));
           list.add(m);
           
           m = new HashMap();
           m.put("KEY", DBBit);
           m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "2102016004"));
           list.add(m);

           m = new HashMap();
           m.put("KEY", DBFile);
           m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "2102016005"));
           list.add(m);   
   		   Map<String, Object> map = new HashMap<String, Object>();
           map.put("basicTotal", list.size());
           map.put("basicData", list);
           
           return map;
       }
       
    @SuppressWarnings({ "rawtypes", "unchecked" })
   	public Map getDBStatusInfoData(HttpServletRequest request) throws Exception {
       	Locale locale = request.getLocale();
       	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
           String database_status="DBStatus";
           String listener_status="LisStatus";
           String errNO="ErrNo";
           String moId = request.getParameter("moId");
           List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
           Map m = new HashMap();
           m.put("KEY", database_status);
           m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "2101020001"));
           list.add(m);
           m = new HashMap();
           m.put("KEY", listener_status);
           m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "2102023001"));
           list.add(m);
           
           m = new HashMap();
           m.put("KEY", errNO);
           m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "2102024002"));
           list.add(m);
   		   Map<String, Object> map = new HashMap<String, Object>();
           map.put("basicTotal", list.size());
           map.put("basicData", list);
           
           return map;
       }
    /**
	 * 【网口信息】
	 * 
	 * @param moId
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineNetworks(String moId) {

        Map map=new HashMap();
        
        if(moId!=null){
            String sql=SqlManager.getSql(this, "host_kpi_list_network");
            sql=sql.replaceAll("\\{MO_ID\\}", moId);
            List list=jdbcTemplate.queryForList(sql);
            map.put("root",list);
            map.put("total", list.size());
            logger.warn(sql);
        }
        else{
            logger.warn("moId is null");
        }
        return map;   
    }
    
	/**
	 * 进程  百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineCourseInfo(String moId) {
        Map basicMap = new HashMap();
        
        List<KpiEvent> tempList = new ArrayList<KpiEvent>();
        // add by mark start 2013-12-24
        String sql = "select TYPE_ID AS \"TYPE_ID\" from td_avmon_mo_info t where t.mo_id = '"+moId+"'";
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = jdbcTemplate.queryForList(sql);
        String moType = StringUtil.EMPTY;
     /*	String kpicode1="";
        if(DBUtils.issnmp(moId)){
        	kpicode1=
        }
        else{
        	kpicode1="1701010009";
        }*/
        if(list != null &&list.size() > 0){
        	moType = list.get(0).get("TYPE_ID");
        	if("HOST_WINDOWS".equals(moType)){
        		tempList = kpiDataStore.getKpiList(moId, "", "1701010009");
        	}else{
        		tempList = kpiDataStore.getKpiList(moId, "", "1001008002");
        	}
        }
        //1001008003
        //1001008004
        List<KpiEvent> usrProcPercentList = kpiDataStore.getKpiList(moId, "", "1001008004");//
        String usrProcPercent = "0";
        // add by mark end 2013-12-24
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("USR_PROC_NUM", tempList.get(0).getValue());
        	if(usrProcPercentList != null && usrProcPercentList.size() > 0){
        		usrProcPercent = usrProcPercentList.get(0).getValue();
        	}
        	
        	basicMap.put("USR_PROC_USAGE",usrProcPercent + "%");
        	basicMap.put("USR_PROC_Percent", usrProcPercent);
        	basicMap.put("USR_PROC_Level", tempList.get(0).getThresholdLevel());
        } else {
        	basicMap.put("USR_PROC_NUM", "0");
        	basicMap.put("USR_PROC_USAGE", "0%");
        	basicMap.put("USR_PROC_Percent", "0");
        	basicMap.put("USR_PROC_Level", "1");
        }
        
        return basicMap;
    }
    
    /**
	 * memory 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineMemInfo(String moId) {

    	Map basicMap = new HashMap();
        List<KpiEvent> tempList = kpiDataStore.getKpiList(moId, "", "1001010001");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("MEM_SYS", tempList.get(0).getValue() + "%");
        } else {
        	basicMap.put("MEM_SYS", "0%");
        }
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        if(typeId.equals("HOST_WINDOWS")){
        	 tempList = kpiDataStore.getKpiList(moId, "", "1701010010");
             if (tempList != null && tempList.size() != 0) {
             	String freeMemSize = tempList.get(0).getStrValue();
             	if(freeMemSize.toUpperCase().indexOf("MB")>-1)
             	{
             		basicMap.put("FREE_MEM", tempList.get(0).getNumValue() + " MB");
             	}
             	else if(freeMemSize.toUpperCase().indexOf("KB")>-1)
             	{
             		Float f=tempList.get(0).getNumValue();
             		basicMap.put("FREE_MEM", f*1024 + " KB");
             	}
             	else if(freeMemSize.toUpperCase().indexOf("GB")>-1)
             	{
             		Float f=tempList.get(0).getNumValue();
             		basicMap.put("FREE_MEM", f/1024 + " GB");
             	}
             	else
             	{
             		basicMap.put("FREE_MEM", tempList.get(0).getNumValue() + " MB");
             	}
             }
             else {
                 basicMap.put("FREE_MEM", "");
             }
             
        }else{
        tempList = kpiDataStore.getKpiList(moId, "", "1001010005");
        if (tempList != null && tempList.size() != 0) {
        	String freeMemSize = tempList.get(0).getStrValue();
        	if(freeMemSize.toUpperCase().indexOf("MB")>-1)
        	{
        		basicMap.put("FREE_MEM", tempList.get(0).getNumValue() + " MB");
        	}
        	else if(freeMemSize.toUpperCase().indexOf("KB")>-1)
        	{
        		Float f=tempList.get(0).getNumValue();
        		basicMap.put("FREE_MEM", f*1024 + " KB");
        	}
        	else if(freeMemSize.toUpperCase().indexOf("GB")>-1)
        	{
        		Float f=tempList.get(0).getNumValue();
        		basicMap.put("FREE_MEM", f/1024 + " GB");
        	}
        	else
        	{
        		basicMap.put("FREE_MEM", tempList.get(0).getNumValue() + " MB");
        	}
        }
        else {
            basicMap.put("FREE_MEM", "");
        }
        }
        
        return basicMap;
    }
    /**
	 * memory 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List getMainEngineMem(String moId) {

        Map map = new HashMap();
        List<KpiEvent> tempList = kpiDataStore.getKpiList(moId, "", "1001010001");
        if (tempList != null && tempList.size() != 0) {
        	map.put("MEM_USR", tempList.get(0).getValue());
        	map.put("MEM_LEVEL", tempList.get(0).getThresholdLevel());
        } else {
        	map.put("MEM_USR", "0");
        	map.put("MEM_LEVEL", "1");
        }

        List<Map> result = new ArrayList<Map>();
        result.add(map);
        
        return result;
    }
    /**
	 * CPU 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List getMainEngineCpuInfo(String moId) {
        Map map = new HashMap();
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        String kpicode1="";
        if(typeId.equals("HOST_WINDOWS")){
        	kpicode1="1001011001";
        }
        else{
        	kpicode1="1001011005";
        }
        List<KpiEvent> tempList = kpiDataStore.getKpiList(moId, "", kpicode1);
        if (tempList != null && tempList.size() != 0) {
        	map.put("CPU_USR", tempList.get(0).getNumValue());
        	map.put("CPU_LEVEL", tempList.get(0).getThresholdLevel());
        } else {
        	map.put("CPU_USR", "0");
        	map.put("CPU_LEVEL", "1");
        	
        }
        List<Map> result = new ArrayList<Map>();
        result.add(map);
        
        return result;
    }
    
    
    /**
	 * Disk 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineDisk(String moId) {
        Map map=new HashMap();
        Map buckets = new HashMap();
        if(moId!=null){
            String sql=SqlManager.getSql(this, "host_kpi_list_disk");
            sql=sql.replaceAll("\\{MO_ID\\}", moId);
            List<Map> list=jdbcTemplate.queryForList(sql);
            List<Map> bucketList = new ArrayList();
            for(int i = 0; i < list.size(); i++)
            {
             	Map basic = new HashMap();
             	basic.put("usage",list.get(i).get("BUSY_RATE"));
             	basic.put("name",list.get(i).get("DISK_NAME"));
             	basic.put("rwrate",list.get(i).get("RW_RATE"));
             	basic.put("tranBytes",list.get(i).get("TRAN_BYTES"));
             	basic.put("kpi",list.get(i).get("BUSY_RATE_VALUE"));
             	basic.put("level",list.get(i).get("DS_LEVEL"));
             	basic.put("src","../mainEngine/tinyColumn.jsp?kpi="+list.get(i).get("BUSY_RATE_VALUE")+"&level="+list.get(i).get("DS_LEVEL"));
             	
             	bucketList.add(basic);
            }

            buckets.put("buckets",bucketList);
            logger.warn(sql);
        }
        else{
            logger.warn("moId is null");
        }
        return buckets;   
    }
    /**
	 * 换页文件 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineSkipFileInfo(String moId) {

        Map basicMap = new HashMap();
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        Map<String, Object> map = new HashMap<String, Object>();
        String kpicode1="";
        String kpicode2="";
        String kpicode3="";
        String kpicode4="";
        String kpicode5="";
        if(typeId.equals("HOST_WINDOWS")){
        	kpicode1="1701013002";//VMVirtualMemoryUsage虚拟内存使用率 SWAP_UTIL
        	kpicode2="1701013003";//VMFreeVirtualMemory剩余虚拟内存 SWAP_NAME
        	kpicode3="1702013001";//VMTotalVirtualMemorySize虚拟内存大小 SWAP_SIZE
        }else{
        	kpicode1="1001013003";//SWAP_UTIL	设备交换区使用率
        	kpicode2="1001013001";//SWAP_NAME	设备交换区路径
        	kpicode3="1001013002";//SWAP_SIZE	设备交换区大小
        	kpicode4="1001013004";//SWAP_FREE	交换区空闲大小
        	kpicode5="1001013005";//SWAP_USED	交换区使用大小
        }
        List<KpiEvent> tempList = kpiDataStore.getKpiList(moId, "", kpicode1);
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("SWAP_UTIL", tempList.get(0).getValue() + " %");
        } else {
        	basicMap.put("SWAP_UTIL", "");
        }
        tempList = kpiDataStore.getKpiList(moId, "", kpicode2);
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("SWAP_NAME", tempList.get(0).getValue());
        } else {
        	basicMap.put("SWAP_NAME", "");
        }
        tempList = kpiDataStore.getKpiList(moId, "", kpicode4);
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("SWAP_FREE", tempList.get(0).getValue());
        } else {
        	basicMap.put("SWAP_FREE", "");
        }
        
        tempList = kpiDataStore.getKpiList(moId, "", kpicode5);
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("SWAP_USED", tempList.get(0).getValue());
        } else {
        	basicMap.put("SWAP_USED", "");
        }
        
        tempList = kpiDataStore.getKpiList(moId, "", kpicode3);
        if (tempList != null && tempList.size() != 0) {
        	String swapSize = tempList.get(0).getStrValue();
        	if(swapSize.toUpperCase().indexOf("MB")>-1)
        	{
        		basicMap.put("SWAP_SIZE", tempList.get(0).getNumValue() + " MB");
        	}
        	else if(swapSize.toUpperCase().indexOf("KB")>-1)
        	{
        		Float f=tempList.get(0).getNumValue();
        		basicMap.put("SWAP_SIZE", f*1024 + " KB");
        	}
        	else if(swapSize.toUpperCase().indexOf("GB")>-1)
        	{
        		Float f=tempList.get(0).getNumValue();
        		basicMap.put("SWAP_SIZE", f/1024 + " GB");
        	}
        	else
        	{
        		basicMap.put("SWAP_SIZE", tempList.get(0).getNumValue() + " MB");
        	}
        	
        	
        } else {
        	basicMap.put("SWAP_SIZE",  "");
        }
        
        return basicMap;
    }
    
    /**
	 * Disk 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineDiskInfo(String moId) {

    	Map basicMap = new HashMap();
        List<KpiEvent> tempList = kpiDataStore.getKpiList(moId, "", "10010050");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("FDISK_CAPABILITY", tempList.get(0).getValue() + "%");
        } else {
        	basicMap.put("FDISK_CAPABILITY", "0%");
        }
        
        tempList = kpiDataStore.getKpiList(moId, "", "10010049");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("FDISK_NAME", tempList.get(0).getValue());
        } else {
        	basicMap.put("FDISK_NAME", "");
        }
        
        tempList = kpiDataStore.getKpiList(moId, "", "10010052");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("FDISK_TOTAL", tempList.get(0).getValue() + "G");
        } else {
        	basicMap.put("FDISK_TOTAL",  "0");
        }
        
        return basicMap;
    }
    /**
	 * file system 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineFileSys(String moId) {
        
        Map map=new HashMap();
        String sql="";
        List<Map> bucketList = new ArrayList();
        List<Map> list=null;
        if(moId!=null){
            String typeId=ResourceStore.getMoPropValue(moId,"typeId");
            
            if(typeId.equals("HOST_WINDOWS")){
            	  sql=SqlManager.getSql(this, "host_kpi_list_lv_windows");
                   sql=sql.replaceAll("\\{MO_ID\\}", moId);
                   list=jdbcTemplate.queryForList(sql);
                   bucketList = new ArrayList();
                  for(int i = 0; i < list.size(); i++)
                  {
                   	Map basic = new HashMap();
                   	basic.put("timePercent",list.get(i).get("timePercent"));
                   	basic.put("freePercent",list.get(i).get("freePercent"));
                   	basic.put("used",list.get(i).get("used"));
                	basic.put("name",list.get(i).get("name"));
                   	bucketList.add(basic);
                  }
            }
            else{
            	  sql=SqlManager.getSql(this, "host_kpi_list_lv");
                  sql=sql.replaceAll("\\{MO_ID\\}", moId);
                  
                   list=jdbcTemplate.queryForList(sql);
                   bucketList = new ArrayList();
                  for(int i = 0; i < list.size(); i++)
                  {
                   	Map basic = new HashMap();
                   	basic.put("total",list.get(i).get("FS_TOTAL"));
                   	basic.put("used",list.get(i).get("FS_USED"));
                   	basic.put("usage",list.get(i).get("FS_PERCENT"));
                   	basic.put("name",list.get(i).get("FS_PATH"));
                   	basic.put("kpi",list.get(i).get("FS_USAGE"));
                   	basic.put("level",list.get(i).get("FS_LEVEL"));
                   	basic.put("src","../mainEngine/tinyColumn.jsp?kpi="+list.get(i).get("FS_USAGE")+"&level="+list.get(i).get("FS_LEVEL"));
                   	
                   	bucketList.add(basic);
                  }
            }
           
    
            map.put("buckets",bucketList);
            logger.warn(sql);
        }
        else{
            logger.warn("moId is null");
        }
        return map;  
    }
    
    /**
	 * 取得日志错误信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getLogError(String moId) {
    	Map map=new HashMap();
    	// modify by mark start 2013-12-18
    	List<Map<String,Object>> syslogList = new ArrayList<Map<String,Object>>();
    	syslogList = this.getAlarmListByKpiCode(moId, "1001005001");
    	// modify by mark start 2013-12-18 end
    	map.put("leftLogLabel", "syslog");
    	if (syslogList != null && syslogList.size() != 0) {
        	map.put("leftLogNum", syslogList.size());
        }else {
        	map.put("leftLogNum", "0");
        }
    	// modify by mark start 2013-12-18
    	List<Map<String,Object>> rclogList = new ArrayList<Map<String,Object>>();
    	rclogList = this.getAlarmListByKpiCode(moId, "1001007001");
    	// modify by mark start 2013-12-18 end
    	map.put("rightLogLabel", "rclog");
    	if (rclogList != null && rclogList.size() != 0) {
        	map.put("rightLogNum", rclogList.size());
        }else {
        	map.put("rightLogNum", "0");
        } 
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        if(typeId.equals("HOST_HP-UX")){
        	//syslog mplog rclog
        	// modify by mark start 2013-12-18 start
        	List<Map<String,Object>> mplogList = new ArrayList<Map<String,Object>>();
        	mplogList = this.getAlarmListByKpiCode(moId, "1001034004");
        	// modify by mark end 2013-12-18 start
        	map.put("centerLogLabel", "mplog");
        	if (mplogList != null && mplogList.size() != 0) {
            	map.put("centerLogNum", mplogList.size());
            }else {
            	map.put("centerLogNum", "0");
            } 
        }else {
        	//syslog maillog rclog
        	// modify by mark start 2013-12-18 start
        	List<Map<String,Object>> maillogList = new ArrayList<Map<String,Object>>();
        	maillogList = this.getAlarmListByKpiCode(moId, "1001006001");
//        	List<KpiEvent> maillogList = kpiInfo.getKpiList(moId, "", "1001006001");
        	// modify by mark start 2013-12-18 end
        	map.put("centerLogLabel", "maillog");
        	if (maillogList != null && maillogList.size() != 0) {
            	map.put("centerLogNum", maillogList.size());
            }else {
            	map.put("centerLogNum", "0");
            }
        }
        return map;
    }
    
    public List<Map<String,Object>> getAlarmListByKpiCode(String  moId, String kpiCode) {

    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String tempSql = SqlManager.getSql(AlarmSearchService.class, "getAlarmSearchList");
        StringBuilder sb = new StringBuilder();
        sb.append(" and t1.MO_ID='" + moId + "'");
        //add by yuanpeng get logErrorAlarm
        if(kpiCode != null)
        {
            sb.append(" and t1.KPI_CODE = '"+kpiCode+"'");
        }
        
        String sql = String.format(tempSql, sb.toString());
		list = jdbcTemplate.queryForList(sql);
		
		if(null==list ||list.size()==0){
			return null;
		}
        return list;
    }
    public List getHostPerformanceList(HttpServletRequest request) {
    	String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		String queryIP=request.getParameter("queryIP");
		
		String typeId=request.getParameter("typeId");
		if(typeId==null){
			typeId="'HOST_%'";
		}
		else{
			typeId="'"+typeId+"'";
		}
		if (sortdatafield == null)
			sortdatafield = "count";
		if (sortorder == null)
			sortorder = "desc";
		String bizName=request.getParameter("bizName");
		try {
    		bizName=URLDecoder.decode(bizName, "utf-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//字符编码和文档编码一致
		String where="";
		String sqlString="";
		String whereString="";
		String sql="";
		if(queryIP!=null){
			where="where a.caption like '%"+queryIP+"%'";
			
		}
		String moId=request.getParameter("moId");
		if(moId!=null){
			where="where a.mo_id='"+moId+"'";
		}
		whereString=" attr.value ='"+bizName+"' ";
		if(bizName.equals("others")){
			 sqlString=SqlManager.getSql(PerformanceService.class,"getHostPerformanceOtherDataList"); 
			 sql=String.format(sqlString,typeId,where);
		}else{
			sqlString=SqlManager.getSql(PerformanceService.class,"getHostPerformanceDataList");
			sql=String.format(sqlString,typeId,whereString,where);
		}
		
		
		String pagesize = request.getParameter("pagesize");
		if(pagesize==null)
			pagesize="20";
		String pagenum = request.getParameter("pagenum");
		if(pagenum==null)
			pagenum="0";
		Integer pagenum1 = (Integer.parseInt(pagenum));
		String querySql="";
		if(DBUtils.isOracle()){
			querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
			querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
					+ sortorder;
			}
			else{
				 querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
			}
			
		
		//System.out.println("oracle querySql is:"+querySql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql);
        return list;
    }
    
    public List getHostOverViewPerformanceList(HttpServletRequest request) {
    	
    	String bizName=request.getParameter("bizName");
    	String queryIP=request.getParameter("queryIP");
    	try {
    		bizName=URLDecoder.decode(bizName, "utf-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//字符编码和文档编码一致
    	
    	String typeId=request.getParameter("typeId");
		if(typeId==null){
			typeId="'HOST_%'";
		}
		else{
			typeId="'"+typeId+"'";
		}
		String whereString=" attr.value ='"+bizName+"' ";
		String where="";
		String sqlString="";
		String sql="";
		if(queryIP!=null){
			where="where a.caption like '%"+queryIP+"%'";
		}
		String moId=request.getParameter("moId");
		if(moId!=null){
			where="where a.mo_id='"+moId+"'";
		}
		if(bizName.equals("others")){
			 sqlString=SqlManager.getSql(PerformanceService.class,"getHostOverViewPerformanceOtherDataList");
			 sql=String.format(sqlString,typeId,where);
		}else{
			 sqlString=SqlManager.getSql(PerformanceService.class,"getHostOverViewPerformanceDataList");
			 sql=String.format(sqlString,typeId,whereString,where);
		}
		//System.out.println("sqlaq is :"+sql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    
    
    
 public List queryPerformanceList(HttpServletRequest request) {
        String queryIP=request.getParameter("queryip");
		String where="";
		String sqlString="";
		String sql="";
		if(queryIP!=null){
			where="where a.caption like '%"+queryIP+"%'";
		}
	    sqlString=SqlManager.getSql(PerformanceService.class,"getQueryPerformanceDataList");
	    sql=String.format(sqlString,where);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    
 public List getDBOverViewPerformanceList(HttpServletRequest request) {
    	String bizName=request.getParameter("bizName");
		String queryIP=request.getParameter("queryIP");
    	try {
    		bizName=URLDecoder.decode(bizName, "utf-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//字符编码和文档编码一致
    	String typeId=request.getParameter("typeId");
		if(typeId==null){
			typeId="'DATABASE_%'";
		}
		else{
			typeId="'"+typeId+"'";
		}
    	
		String whereString=" attr.value ='"+bizName+"' ";
		//String sqlString=SqlManager.getSql(PerformanceService.class,"getDBOverViewPerformanceDataList");
		
		String where="";
		if(queryIP!=null){
			where="where a.mo_id like '%"+queryIP+"%'";
		}
		String moId=request.getParameter("moId");
		if(moId!=null){
			where="where a.mo_id='"+moId+"'";
		}
		String sqlString="";
		String sql="";
		if(bizName.equals("others")){
			 sqlString=SqlManager.getSql(PerformanceService.class,"getDBOverViewPerformanceOtherDataList");
			 sql=String.format(sqlString,typeId,where);
		}else{
			 sqlString=SqlManager.getSql(PerformanceService.class,"getDBOverViewPerformanceDataList");
			 sql=String.format(sqlString,typeId,whereString,where);
		}
		//String sql=String.format(sqlString,typeId,whereString,where);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return list;
    }
    
    
    public List getDBPerformanceList(HttpServletRequest request) {
    	String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		String queryIP=request.getParameter("queryIP");
		if (sortdatafield == null)
			sortdatafield = "count";
		if (sortorder == null)
			sortorder = "desc";
		String bizName=request.getParameter("bizName");
		try {
    		bizName=URLDecoder.decode(bizName, "utf-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}//字符编码和文档编码一致
		
		String typeId=request.getParameter("typeId");
		if(typeId==null){
			typeId="'DATABASE_%'";
		}
		else{
			typeId="'"+typeId+"'";
		}
		
		String whereString=" attr.value ='"+bizName+"' ";
		//String sqlString=SqlManager.getSql(PerformanceService.class,"getDBPerformanceDataList");
		
		String where="";
		if(queryIP!=null){
			where="where a.mo_id like '%"+queryIP+"%'";
		}
		String moId=request.getParameter("moId");
		if(moId!=null){
			where="where a.mo_id='"+moId+"'";
		}
		//String sql=String.format(sqlString,typeId,whereString,where);
		
		String sqlString="";
		String sql="";
		if(bizName.equals("others")){
			 sqlString=SqlManager.getSql(PerformanceService.class,"getDBPerformanceOtherDataList");
			 sql=String.format(sqlString,typeId,where);
		}else{
			 sqlString=SqlManager.getSql(PerformanceService.class,"getDBPerformanceDataList");
			 sql=String.format(sqlString,typeId,whereString,where);
		}
		
		
		String pagesize = request.getParameter("pagesize");
		if(pagesize==null)
			pagesize="20";
		String pagenum = request.getParameter("pagenum");
		if(pagenum==null)
			pagenum="0";
		Integer pagenum1 = (Integer.parseInt(pagenum));
		String querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		if(querySql.contains("limit")) {//若是PostgreSQL
			querySql = querySql.replace("limit", " order by " + "\"" + sortdatafield + "\"" + " "+ sortorder+" limit ");
		}else{
			querySql = querySql + " order by " + "\"" + sortdatafield + "\"" + " "
					+ sortorder;
		}
		List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql);
        return list;
    }
    
    public int getHostPerformanceListCount(HttpServletRequest request) {
        String bizName=request.getParameter("bizName");
        String queryIP=request.getParameter("queryIP");
        String typeId=request.getParameter("typeId");
        if(typeId==null){
        	typeId="'HOST_%'"; 
        }
        else{
        	typeId="'"+typeId+"'";
        }
        try {
    		bizName=URLDecoder.decode(bizName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}//字符编码和文档编码一致
        String where="";
        String whereString="";
        String sqlString="";
        String sql="";
      	if(queryIP!=null){
			where="where a.caption like '%"+queryIP+"%'";
		}
      	String moId=request.getParameter("moId");
		if(moId!=null){
			where="where a.mo_id='"+moId+"'";
		}
		if(bizName.equals("others")){
			sqlString=SqlManager.getSql(PerformanceService.class,"getHostPerformanceOtherDataListCount");
			sql=String.format(sqlString,typeId,where);
		}else{
			whereString=" attr.value ='"+bizName+"' ";
			sqlString=SqlManager.getSql(PerformanceService.class,"getHostPerformanceDataListCount");
			sql=String.format(sqlString,typeId,whereString,where);
		}
		
		
		int count = jdbcTemplate.queryForInt(sql);
        return count;
    }
    
    public int getDBPerformanceListCount(HttpServletRequest request) {
    	String bizName=request.getParameter("bizName");
    	String queryIP=request.getParameter("queryIP");
    	 try {
     		bizName=URLDecoder.decode(bizName, "utf-8");
 		} catch (UnsupportedEncodingException e) {
 			e.printStackTrace();
 		}//字符编码和文档编码一致
    	String typeId=request.getParameter("typeId");
 		if(typeId==null){
 			typeId="'DATABASE_%'";
 		}
 		else{
 			typeId="'"+typeId+"'";
 		}
		String whereString=" attr.value ='"+bizName+"' ";
		
		String where="";
		if(queryIP!=null){
			where="where a.mo_id like '%"+queryIP+"%'";
		}
		String moId=request.getParameter("moId");
		if(moId!=null){
			where="where a.mo_id='"+moId+"'";
		}
		//String sqlString=SqlManager.getSql(PerformanceService.class,"getDBPerformanceDataListCount");
		//String sql=String.format(sqlString,typeId,whereString,where);
		
		String sqlString="";
		String sql="";
		if(bizName.equals("others")){
			 sqlString=SqlManager.getSql(PerformanceService.class,"getDBPerformanceOtherDataListCount");
			 sql=String.format(sqlString,typeId,where);
		}else{
			 sqlString=SqlManager.getSql(PerformanceService.class,"getDBPerformanceDataListCount");
			 sql=String.format(sqlString,typeId,whereString,where);
		}
		
		int count = jdbcTemplate.queryForInt(sql);
        return count;
    }
    
    
	/**
	 * 网络传输 发送包 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineNetworkSend(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        String kpicode1="";
        if(typeId.equals("HOST_WINDOWS")){
        	kpicode1="1701004003";
        }
        else{
        	kpicode1="1001003004";
        }
        List<KpiEvent> tempList = kpiDataStore.getKpiHistory(moId, "", kpicode1, "", 5);
        Map map = new HashMap();
        Map m = new HashMap();
        List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);
        String speed="";
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }
        //网卡发送流量：1001003004
        List<KpiEvent> tempSendList = kpiDataStore.getKpiList(moId, "",kpicode1);
        if (tempSendList != null && tempSendList.size() != 0) {
        	float f=(float) 0.0;
        	for(KpiEvent temp : tempSendList)
        	{
        		f+=temp.getNumValue();
        	}
        	BigDecimal big = new BigDecimal(f);
        	speed = big + "";
        } else {
        	//speed = noData;
        }
        map.put("speed", speed);
        m = new HashMap();
        m.put("speed_text", speed);
        map.put("content", m);
        
        logger.debug("Network> "+map);
        
        return map;
    }
    
    /**
	 * 网络传输 接收包 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineNetworkReceive(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    	  String typeId=ResourceStore.getMoPropValue(moId,"typeId");
          String kpicode1="";
         if(typeId.equals("HOST_WINDOWS")){
          	kpicode1="1001003003";
         }
         else{
          	kpicode1="1701004002";
         }
        List<KpiEvent> tempList = kpiDataStore.getKpiHistory(moId, "", "10010025", "", 5);
        Map map = new HashMap();
        Map m = new HashMap();
        List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);

        //add by TED. default value 0 while the db is empty
        String speed="";
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }
        //网卡接受流量：1001003003 单位bps
        List<KpiEvent> tempSendList = kpiDataStore.getKpiList(moId, "", kpicode1);
        if (tempSendList != null && tempSendList.size() != 0) {
        	float f=(float) 0.0;
        	for(KpiEvent temp : tempSendList)
        	{
        		f+=temp.getNumValue();
        	}
        	BigDecimal big = new BigDecimal(f);
        	speed = big + "";
        } else {
        	//speed = noData;
        }
        map.put("speed", speed);
        
        m = new HashMap();
        //m.put("speed_text", "35 k/s");
        m.put("speed_text", speed);
        map.put("content", m);
        
        return map;
    }
    
    /**
	 * 磁盘 读写 信息 (只有windows有)
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineDiskRead(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    	 String speed="";
    	 String speed1="";
    	 Map m = new HashMap();
        //R/W：1001014003 单位K/s
    	String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        String kpicode1="";
        String kpicode2="";
        if(typeId.equals("HOST_WINDOWS")){
         	kpicode1="1701014006";
         	kpicode2="1701014007";
        }
        else{
         	return m;
        }
        List<KpiEvent> tempSendList1 = kpiDataStore.getKpiList(moId, "", kpicode1);
        List<KpiEvent> tempSendList2 = kpiDataStore.getKpiList(moId, "", kpicode2);
        BigDecimal big1=null;
        if (tempSendList1 != null && tempSendList1.size() != 0) {
        	float f=(float) 0.0;
        	for(KpiEvent temp : tempSendList1)
        	{
        		f+=temp.getNumValue();
        	}
        	 big1 = new BigDecimal(f);
        	//speed = big + "";
        } else {
        	//speed = noData;
        }
        BigDecimal big2=null;
        if (tempSendList2 != null && tempSendList2.size() != 0) {
        	float f=(float) 0.0;
        	for(KpiEvent temp : tempSendList2)
        	{
        		f+=temp.getNumValue();
        	}
        	big2 = new BigDecimal(f);
        	//speed1 = big + "";
        } else {
        	//speed1 = noData;
        }
        if(big1!=null){
        	  big1=big1.add(big2);
              
              speed=big1.toString();
        }
        else{
        	speed="";
        }
        m = new HashMap();
        m.put("speed", speed);
        return m;
    	
    }
    
    /**
	 * 磁盘 写 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineDiskWrite(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
    	 String speed="";
    	 Map m = new HashMap();
      /*  List<KpiEvent> tempList = kpiDataStore.getKpiHistory(moId, "", "1001010002", "", 5);
        Map map = new HashMap();
       
        List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);
       
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }*/
      //R/W：1001014003 单位K/s
    	String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        String kpicode1="";
        if(typeId.equals("HOST_WINDOWS")){
         	//kpicode1="1701014007";
        }
        else{
         	kpicode1="1001014003";
        }
        List<KpiEvent> tempSendList = kpiDataStore.getKpiList(moId, "", kpicode1);
        if (tempSendList != null && tempSendList.size() != 0) {
        	float f=(float) 0.0;
        	for(KpiEvent temp : tempSendList)
        	{
        		f+=temp.getNumValue();
        	}
        	BigDecimal big = new BigDecimal(f);
        	speed = big + "";
        } else {
        	//speed = noData;
        }
       // map.put("speed", speed);
        m = new HashMap();
        m.put("speed_text", speed);
       // map.put("content", m);
        
        return m;
    	
    }
    
    /**
	 * 换页文件 读 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineSkipfilePagein(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Map map = new HashMap();
        Map m = new HashMap();
        String speed="";
       // List<KpiEvent> tempList = kpiDataStore.getKpiHistory(moId, "", "1001010002", "", 5);
       /* List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);
       
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }*/
      //Pagein：1001010002 单位p/s
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        String kpicode1="";
        if(typeId.equals("HOST_WINDOWS")){
         	kpicode1="1701010005";
         	//map.put("info", "R/W Pages Per Sec");
        }
        else{
         	kpicode1="1001010002";
        }
        List<KpiEvent> tempSendList = kpiDataStore.getKpiList(moId, "", kpicode1);
        if (tempSendList != null && tempSendList.size() != 0) {
        	 speed = tempSendList.get(0).getValue();
        } else {
        	//speed = noData;
        }
        map.put("speed", speed);
      
       // m = new HashMap();
       // m.put("speed_text", speed);
       // map.put("content", m);
        return map;
        
    }
    
    /**
	 * 换页文件 接收包 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getMainEngineSkipfilePageout(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        Map map = new HashMap();
        Map m = new HashMap();
        String speed="";
        List<KpiEvent> tempList = kpiDataStore.getKpiHistory(moId, "", "1001010002", "", 5);
       /* List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);
       
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }*/
      //Pagein：1001010002 单位p/s
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        String kpicode1="";
        if(typeId.equals("HOST_WINDOWS")){
         	//kpicode1="1701010006";
         	//map.put("info", "R/W Cache Fault Per Sec");
        }
        else{
         	kpicode1="1001010003";
        }
        List<KpiEvent> tempSendList = kpiDataStore.getKpiList(moId, "", kpicode1);
        String value= kpiDataStore.getCurrentKpiValue(moId, kpicode1);
       
       //System.out.println("value is@@@@@@@@@@@@@@@@@@@@:"+value);
        if (tempSendList != null && tempSendList.size() != 0) {
        	 speed = tempSendList.get(0).getValue();
        } else {
        	//speed = noData;
        }
        map.put("speed", speed);
      
        m = new HashMap();
        m.put("speed_text", speed);
        map.put("content", m);
        return map;
/*    	String noData = bundle.getString("noData");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        List<KpiEvent> tempList = kpiDataStore.getKpiHistory(moId, "", "1001010003", "", 5);
        Map map = new HashMap();
        Map m = new HashMap();
        List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);
        String speed="";
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }
      //PageOut：1001010003 单位p/s
        List<KpiEvent> tempSendList = kpiDataStore.getKpiList(moId, "", "1001010003");
        if (tempSendList != null && tempSendList.size() != 0) {
        	 speed = tempSendList.get(0).getValue();
        } else {
        	speed = noData;
        }
        map.put("speed", speed);
        m = new HashMap();
        m.put("speed_text", speed);
        map.put("content", m);
        
        logger.debug("SWAP>>> "+map);
        
        return map;*/
    	
    }
     //jqw分页
  	private String generatPageSqlForJQW(String sql, String limit, String start) {
  		Integer limitL = Integer.valueOf(limit);
  		Integer startL = Integer.valueOf(start);

  		// 构造oracle数据库的分页语句
  		return DBUtils.paginationforjqw(sql, startL, limitL);
  	}

    public Map getMoPath(String moId) {
        Map map=new HashMap();
        map.put("success","true");
        String path="";
        try{
            String typeId=moId;
            String sql="select TYPE_ID from TD_AVMON_MO_INFO where mo_id='"+moId+"' ";
            List<Map> a=jdbcTemplate.queryForList(sql);
            if(a!=null && a.size()>0){
                typeId=(String) a.get(0).get("TYPE_ID");
            }
//          String typeId=(String) jdbcTemplate.queryForObject(sql, String.class);
            sql="select type_id as ID,parent_id as PID from TD_AVMON_MO_TYPE";
            List<Map> list=jdbcTemplate.queryForList(sql);
            path=makeMoPath(typeId,list);
            if(moId.equals(typeId)){
                path=path+typeId;
            }
            else{
                path=path+typeId+"/"+moId;
            }
        }catch(Exception e){
            map.put("success","false");
        }
        //System.out.println("path="+path);

        map.put("path",path);
        return map;
    }

    private String makeMoPath(String typeId, List<Map> list) {
        for(Map map:list){
            String id=(String) map.get("ID");
            String pid=(String) map.get("PID");
            if(id.equals(typeId)){
                if(pid.equals("root")){
                    return "/root/";
                }
                else{
                    return makeMoPath(pid,list)+pid+"/";
                }
            }
        }
        return "/root/";
    }

    /**
     * 
     * @param moId
     * @param userId
     * @return
     */
	public List getGroups(HttpServletRequest request) {
		Locale locale = request.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
		String other = bundle.getString("others");
        String userId=Utils.getCurrentUserId(request);
        String moId=request.getParameter("moId");
		String cacheSql = "select KPI_CODE,CAPTION,KPI_TYPE from TD_AVMON_KPI_INFO";
		List<Map<String, Object>> kpiNameList = jdbcTemplate.queryForList(cacheSql);
		kpiNameCache.clear();
		kpiTypeCache.clear();
		if (kpiNameList != null) {
			for (Map<String, Object> m : kpiNameList) {
				kpiNameCache.put(m.get("KPI_CODE"), m.get("CAPTION"));
				kpiTypeCache.put(m.get("KPI_CODE"), m.get("KPI_TYPE"));
			}
		}

		List<KpiEvent> kpiList = new ArrayList();
		List list = new ArrayList<String>();
		kpiList = kpiDataStore.getKpiList(moId);
		kpiValueList.clear();
		//kpiValueList = kpiList;
		if(kpiList!=null){
			kpiValueList.addAll(kpiList);
		}
		else{
			logger.warn("------------kpiDataStore.getKpiList(moId) is null-------------");
			return kpiList;
		}
		StringBuffer kpiCodeList = new StringBuffer();
		String kpiCode = "";
		for (KpiEvent event : kpiList) {
			kpiCode = event.getKpiCode();
			//排除重复by muzh
			if(kpiCodeList.indexOf(kpiCode)<0){
				kpiCodeList.append(kpiCode);
				kpiCodeList.append("','");
			}
		}
		String kpicodes = kpiCodeList.toString();
		if (kpiList.size() > 0 ) {
			kpicodes = "('" + kpicodes.substring(0, kpicodes.length() - 2) + ")";
		}else{
			//return "[]";
			return new ArrayList();
		}

		List<Map> listMap = new LinkedList<Map>();

		String sql = "select distinct KPI_TYPE as \"kpiType\",'" + moId
				+ "' as \"moId\"  from TD_AVMON_KPI_INFO WHERE KPI_CODE IN "
				+ kpicodes + " AND KPI_TYPE IS NOT NULL AND KPI_TYPE!='null' ORDER BY KPI_TYPE ";
		List<Map<String, Object>> kpiInfoList = jdbcTemplate.queryForList(sql);
		String[] values;
		String kpiType;
		for (Map<String, Object> map : kpiInfoList) {
			values = new String[2];
			if (map.get("kpiType") == null) {
				kpiType = other;
			} else {
				kpiType = map.get("kpiType").toString();
			}
			values[1] = kpiType;
			values[0] = moId;
			list.add(values);
		}
		return list;
		//String result = JackJson.fromObjectToJson(list);
		//logger.debug(result);
		//return result;
	}

	/**
	 * 
	 * @param moId
	 * @param userId
	 * @return
	 */
	public Map getInnderGrid(String groupName, String userId,ResourceBundle bundle) {
		List<KpiEvent> kpiList = null;
		List<Map> listMap = new LinkedList<Map>();
		String groupNameOther = bundle.getString("groupNameOther");
		String instanceName = bundle.getString("instanceName");
		String kpiTime = bundle.getString("kpiTime");
		if(groupNameOther.equals(groupName)){
			groupName = null;
		}
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		kpiList = kpiValueList;
		
		Map<String, Map> instMap = new HashMap();
		Map<String, String> instanceMap = new HashMap();
		if (kpiList != null) {
			List<String> addedList = new ArrayList<String>();
			for (KpiEvent event : kpiList) {
				if (null != kpiTypeCache.get(event.getKpiCode())&& !((String) kpiTypeCache.get(event.getKpiCode())).equalsIgnoreCase("")) {
					if (((String) kpiTypeCache.get(event.getKpiCode())).equalsIgnoreCase(groupName)) {
						Map map = new HashMap();
						map.put("instance", event.getInstance());
						map.put(event.getKpiCode(), event.getStrValue());
						map.put("name" + event.getKpiCode(),kpiNameCache.get(event.getKpiCode()));
						map.put("kpitime" + event.getKpiCode(),event.getKpiTime());
						instMap.put(event.getInstance(), map);
						list.add(map);
					}
				}
			}
		}
		
		logger.debug(list);
		
		List<Map<String,String>> values = new ArrayList<Map<String,String>>();
		List <String> fieldsNames = new ArrayList<String>();
		List<Map<String,String>> columModle = new ArrayList<Map<String,String>>();
		 
		for (Entry<String, Map> entity: instMap.entrySet()) {
			Map<String,String> topMap = new HashMap<String, String>();
			String key = entity.getKey();
			topMap.put("instance", key);
			Map value = new HashMap<String, String>();
			String kpitime = "";
			for(Map<String, Object> map:list){
				Map<String,String> cloumnMap = new HashMap<String, String>();
				String instance = map.get("instance").toString();
				if(key.equals(instance)){
					
					String keyValue = "";
					String innerMapKey = "";
					String kpiCode = "";
					String kpiValue = "";
					String kpiName = "";
					
					for (Entry<String, Object> innerMap: map.entrySet()) {
						innerMapKey = innerMap.getKey();
						if(innerMapKey.startsWith("name")){
							kpiCode = innerMapKey.substring(4, innerMapKey.length());
							kpiName = innerMap.getValue().toString();
							fieldsNames.add(kpiCode);
							cloumnMap.put("text", kpiName);
							cloumnMap.put("datafield", kpiCode);
							cloumnMap.put("width", "200");
							kpiValue = map.get(kpiCode).toString();
							logger.debug("=============kpiValue==========="+kpiValue);
							if(!StringUtils.isEmpty(kpiValue) && !"NA".equals(kpiValue)){
								SimpleDateFormat format =  new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss"); 
								kpitime = format.format(map.get("kpitime"+kpiCode));
								logger.debug("=============kpitime==========="+kpitime);
								topMap.put("kpitime", kpitime);
							}
							topMap.put(kpiCode, kpiValue);
							columModle.add(cloumnMap);
						}
					}
				}
			}
			values.add(topMap);
		}
		
		
		Map<String,Object> resutlMap = new HashMap<String, Object>();
		
		Map<String, String> middleMap = new HashMap<String, String>();
		for (Map<String, String> map : columModle) {
			middleMap.put(map.get("text"),map.get("datafield"));
		}
		
		columModle.clear();
		Map<String,String> instanceIndex = new HashMap<String, String>();
		instanceIndex.put("datafield", "instance");
		instanceIndex.put("text", instanceName);
		columModle.add(instanceIndex);
		instanceIndex = new HashMap<String, String>();
		instanceIndex.put("datafield", "kpitime");
		instanceIndex.put("text", kpiTime);
		columModle.add(instanceIndex);
		
		for (Entry<String, String> tempMap: middleMap.entrySet()) {
			Map<String,String> colum = new HashMap<String, String>();
			colum.put("text",tempMap.getKey());
			colum.put("datafield",tempMap.getValue());
			columModle.add(colum);
		}
		
		HashSet<String> set = new HashSet<String>(fieldsNames);
		fieldsNames.clear();
		fieldsNames.add("instance");
		fieldsNames.add("kpitime");
		fieldsNames.addAll(set);
		resutlMap.put("data", values);
		resutlMap.put("columModle", columModle);
		resutlMap.put("fieldsNames", fieldsNames);

		//logger.debug(JackJson.fromObjectToJson(resutlMap));
		//return JackJson.fromObjectToJson(resutlMap);
        return resutlMap;
	}

}
