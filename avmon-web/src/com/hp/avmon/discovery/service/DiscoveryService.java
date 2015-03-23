package com.hp.avmon.discovery.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import net.percederberg.mibble.MibLoaderException;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.hp.avmon.common.Config;
import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.home.service.HomeService;
import com.hp.avmon.home.service.LicenseService;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;
import com.hp.avmon.snmp.discover.DiscoverService;
import com.hp.avmon.utils.Constants;
import com.hp.avmon.utils.DBUtils;

@Service
public class DiscoveryService {
    @Autowired
    private DiscoverService discoverService;
    
    @Autowired
    private LicenseService licenseService;
    
    private static final Log logger = LogFactory.getLog(HomeService.class);
    private static String deviceIds = "";
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
//    @Autowired
//    private MibFileParserDB22 mibFileParser;
    
    private static final String FAILURE = "1";
    private static final String SUCCESS = "0";
    
    public DiscoveryService() {
    	if (discoverService==null) {
    		discoverService = SpringContextHolder.getBean("discoverService");
		}
		discoverService.initDeviceTypeInfoList();
	}

	public String scan(HttpServletRequest request) throws Exception{
    	String changepageFlag = request.getParameter("changepageFlag");
    	String params = request.getParameter("scanParams");
    	if (StringUtils.isEmpty(params)) {
			return FAILURE;
		}
    	if(!StringUtils.isEmpty(changepageFlag)){
    		List<Map<String,String>> ampListMapF = getListMapByJsonArrayString(params);
        	
        	List<SNMPTarget> snmpTargetList = new Vector<SNMPTarget>();
        	String ips = StringUtil.EMPTY;
        	String startIp = "";
        	String endIp = "";
        	List<String> resultList  = new ArrayList<String>();
        	List<String> tempList  = new ArrayList<String>();
        	SNMPTarget target;
        	
        	for (Map<String, String> map : ampListMapF) {
        		ips = map.get("ip");
        		target = new SNMPTarget();
        		target.port = StringUtils.isEmpty(map.get("port"))?161:Integer.valueOf(map.get("port"));
            	target.readCommunity = StringUtils.isEmpty(map.get("authNo"))?"public":map.get("authNo");
        		if(!StringUtils.isEmpty(ips)){
            		if(ips.indexOf("-") > -1){
            			startIp =ips.substring(0,ips.indexOf("-")).trim();
            			endIp = ips.substring(ips.indexOf("-")+1).trim();
            			snmpTargetList.add(target);
            			tempList = discoverService.saveDeviceInfo(startIp, endIp,snmpTargetList, false);
            		}else{
            			startIp =ips.trim();
            			if(startIp.indexOf("*") > -1){
            				startIp = startIp.replace("*", "0");
            			}
            			snmpTargetList.add(target);
                    	tempList = discoverService.saveDeviceInfo(startIp,snmpTargetList, false);
            		}
            	}
            	
            	resultList.addAll(tempList);
    		}
        	logger.debug(ampListMapF);
        	
        	String ids = "";
        	if (resultList.size() > 0) {
        		for (String deviceId : resultList) {
        			if(!StringUtils.isEmpty(deviceId) && !"null".equals(deviceId)){
        				ids = ids+ deviceId+"','";
        			}
        		}
    		}
        	
        	deviceIds = ids;
        	logger.debug("devices is : "+ids);
        	request.setAttribute("ids", ids);
    	}
    	
    	return this.getDevices(request);
    }
    
    /**
     * 查询设备列表
     * @param request 
     * @return
     */
    public String getDevices(HttpServletRequest request){
    	String types = request.getParameter("type");
    	String from = request.getParameter("from");
    	String to = request.getParameter("to");
    	String ip = request.getParameter("ip");
    	String status = request.getParameter("status");
    	String sort = request.getParameter("sortdatafield");
    	String sortorder = request.getParameter("sortorder");
    	int pageNo = StringUtils.isEmpty(request.getParameter("pagenum"))?0:Integer.valueOf(request.getParameter("pagenum"));
    	int pageSize = StringUtils.isEmpty(request.getParameter("pagesize"))?10:Integer.valueOf(request.getParameter("pagesize"));
    	String mode = request.getParameter("mode");
    	String ids = null;
    	if("scan".equals(mode)){
    		ids = deviceIds;
    	}
    	
    	String sortStr = "";
    	
    	if(!StringUtils.isEmpty(sort)){
    		
    		if("deviceName".equals(sort)){
    			sort="device.device_name";
    		}else if("ip".equals(sort)){
    			sort="device.device_ip";
    		}else if("desc".equals(sort)){
    			sort="device.device_desc";
    		}else if("protocol".equals(sort)){
    			sort = "device.protocol";
    		}else if("status".equals(sort)){
    			sort = "device.status";
    		}else if("createDt".equals(sort)){
    			sort = "device.create_dt";
    		}else if("vender".equals(sort)){
    			sort = "type.vender";
    		}else if("family".equals(sort)){
    			sort = "type.family";
    		}else if("type".equals(sort)){
    			sort = "type.type";
    		}else if("moTypeId".equals(sort)){
    			sort = "type.mo_type_id";
    		}
    	}else{
    		sort="device.device_ip";
    	}
    	
    	if (StringUtils.isEmpty(sortorder)) {
			sortorder = "asc";
		}
    	sortStr = " order by "+sort+" "+sortorder;
    	
    	String where =" where 1=1 ";
    	
    	if(!StringUtils.isEmpty(types)){
    		where += " and type.type in ('"+types+"') ";
    	}
    	
    	if (ids != null) {
    		where += " and device.device_id in ('"+ids+"')";
		}
    	
    	if(!StringUtils.isEmpty(from)){
			where += " and device.create_dt >="+DBUtils.getDBToDateFunction()+"('"+from+" 00:00:00','dd/MM/yyyy HH24:MI:SS') ";
    					
    	}
    	
    	if(!StringUtils.isEmpty(to)){
			where += " and device.create_dt  <="+DBUtils.getDBToDateFunction()+"('"+to+" 23:59:59','dd/MM/yyyy HH24:MI:SS') ";
    	}
    	
    	if(!StringUtils.isEmpty(status)){
    		where +=" and device.status ='"+status+"'";
    	}
    	
    	if(!StringUtils.isEmpty(ip)){
    		where +=" and device.device_ip like '%"+ip+"%'";
    	}
    	
    	
    	String json = "";
    	String sql = "select device.device_id as \"deviceId\","+
					       "device.device_ip as \"ip\","+
					       "device.device_name as \"deviceName\","+
					       "device.device_desc as \"desc\","+
					       "device.protocol as \"protocol\","+
					       "device.status   as \"status\","+
					       "device.create_dt   as \"createDt\","+
					       "type.vender as \"vender\","+
					       "type.family as \"family\","+
					       "type.type as \"type\","+
					       "type.mo_type_id as \"moTypeId\","
					       + "'<a href=''#'' onclick=\"configSchedule('''||device.device_id||''','''||device.device_name||''','''||device.status||''')\">修改调度</a>' as \"link\" "+
					  "from td_avmon_discovery_device_info device "+
					  "left join td_avmon_device_type_info type on device.device_type = type.id" + where + sortStr;
    	int start = pageNo*pageSize;
    	int limit = start+pageSize;
    	String cntSql = "select count(*) from td_avmon_discovery_device_info device "+
					  "left join td_avmon_device_type_info type on device.device_type = type.id "+where;
    	int totalRows = jdbcTemplate.queryForInt(cntSql);
    	sql = this.pagination(sql, start, limit);
		@SuppressWarnings("unchecked")
		List<Map<String,String>> list  = jdbcTemplate.queryForList(sql);
		HashMap<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
    	if(list.size() > 0){
    		map.put("devices", list);
    		map.put("totalRows", totalRows);
    		result.add(map);
    		json = JackJson.fromObjectToJson(result);
    	}else{
    		json = FAILURE;//失败
    	}
    	
    	return json;
    }
    
    
    /**
	 * OID管理
	 * @param request
	 * @return
	 */
	public String getDeviceOids(HttpServletRequest request) {
		String oid = request.getParameter("oid");
		String oidName = request.getParameter("oidName");
		String moId = request.getParameter("moId");
    	String sort = request.getParameter("sortdatafield");
    	String sortorder = request.getParameter("sortorder");
    	int pageNo = StringUtils.isEmpty(request.getParameter("pagenum"))?0:Integer.valueOf(request.getParameter("pagenum"));
    	int pageSize = StringUtils.isEmpty(request.getParameter("pagesize"))?0:Integer.valueOf(request.getParameter("pagesize"));
    	
    	String sortStr = "";
    	
    	if(!StringUtils.isEmpty(sort)){
    		
    		if("oid".equals(sort)){
    			sort="oid_id";
    		}else if("oidName".equals(sort)){
    			sort = "oid_name";
    		}else if("status".equals(sort)){
    			sort = "status";
    		}else if("oidGroup".equals(sort)){
    			sort = "oid_group";
    		}else if("schedule".equals(sort)){
    			sort = "schedule";
    		}else{
    			sort = "oid_id";
    		}
    		sortStr = " order by "+sort+" "+sortorder;
    	}else{
    		sort="oid_id";
    	}
    	
    	String where =" where 1=1 ";
    	
    	if(!StringUtils.isEmpty(moId)){
    		where +=" and  mo_id ='"+moId+"'";
    	}
    	if (!StringUtils.isEmpty(oid)) {
			where +=" and oid_id like '%"+oid+"%'";
		}
    	if (!StringUtils.isEmpty(oidName)) {
    		where +=" and UPPER(oid_name) like '%"+oidName.toUpperCase()+"%'";
		}
    	
    	String json = "";
    	String sql = "select mo_id as \"moId\",oid_id as \"oid\",\"schedule\" as \"schedule\",oid_index as \"oidIndex\","
    			+ "oid_group as \"oidGroup\",oid_name as \"oidName\",oid_status as \"status\" "
    			+ "from td_avmon_snmp_schedule "+
				   where + sortStr;
    	
    	int start = pageNo*pageSize;
    	int limit = start+pageSize;
    	String cntSql = "select  count(*) from td_avmon_snmp_schedule "+where;
    	int totalRows = jdbcTemplate.queryForInt(cntSql);
    	sql = this.pagination(sql, start, limit);
		@SuppressWarnings("unchecked")
		List<Map<String,String>> list  = jdbcTemplate.queryForList(sql);
		HashMap<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
    	if(list.size() > 0){
    		request.getSession().setAttribute("queryList", list);
    		map.put("oids", list);
    		map.put("totalRows", totalRows);
    		result.add(map);
    		json = JackJson.fromObjectToJson(result);
    	}else{
    		json = FAILURE;//失败
    	}
    	
    	return json;
	}
    
    
    
 // startL 起始记录，从0开始
    private  String pagination(String sql, int startL, int limitL) {
    	String psql = sql;
    	int dbType = DBUtils.getDbType();
    	if(dbType == Constants.DB_ORACLE) {
    		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
    		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
    		paginationSQL.append(sql);
    		paginationSQL.append(" ) temp where ROWNUM <= " + (startL + limitL));//limitL*startL);
    		paginationSQL.append(" ) WHERE num > " + (startL));
    		psql = paginationSQL.toString();
    	}
    	if(dbType == Constants.DB_POSTGRESQL) {
    		if(startL==1){
    			startL = 0;
    		}
    		psql = sql + " limit " + limitL + " offset " + startL;
    	}
    	System.out.println("psql is:"+psql);
    	logger.debug(this.getClass().getName()+psql);
    	return psql;
    }

	@SuppressWarnings("unchecked")
	public String getTypeTreeJson(HttpServletRequest request) {
		
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();
		String parentId = request.getParameter("parentId");
		if(StringUtils.isEmpty(parentId)){
			parentId = "root";
		}
		
		String sql = "select type_id as \"id\", parent_id as \"parentid\", caption as \"text\" from td_avmon_mo_type ";
		list = jdbcTemplate.queryForList(sql);
		
		return JackJson.fromObjectToJson(list);
	}

	/**
	 * 更新设备类型详细信息
	 * @param request
	 * @return
	 */
	public String updateDeviceType(HttpServletRequest request) {
		Map<String,String> map = new HashMap<String, String>();
		String typeId = StringUtils.isEmpty(request.getParameter("typeId"))?"":request.getParameter("typeId");
		String caption = StringUtils.isEmpty(request.getParameter("caption"))?"":request.getParameter("caption");
		String parentId = StringUtils.isEmpty(request.getParameter("parentId"))?"":request.getParameter("parentId");
		String typeViews = StringUtils.isEmpty(request.getParameter("typeViews"))?"":request.getParameter("typeViews");
		String typeDefaultView = StringUtils.isEmpty(request.getParameter("typeDefaultView"))?"":request.getParameter("typeDefaultView");
		String iconCls = StringUtils.isEmpty(request.getParameter("iconCls"))?"":request.getParameter("iconCls");
		String iconClsPause = StringUtils.isEmpty(request.getParameter("iconClsPause"))?"":request.getParameter("iconClsPause");
		String iconClsError = StringUtils.isEmpty(request.getParameter("iconClsError"))?"":request.getParameter("iconClsError");
		String resourcePicture = StringUtils.isEmpty(request.getParameter("resourcePicture"))?"":request.getParameter("resourcePicture");
		String displayFlag = StringUtils.isEmpty(request.getParameter("displayFlag"))?"":request.getParameter("displayFlag");
		String displayOrder = StringUtils.isEmpty(request.getParameter("displayOrder"))?"":request.getParameter("displayOrder");
		String resourcePictureDirection = StringUtils.isEmpty(request.getParameter("resourcePictureDirection"))?"":request.getParameter("resourcePictureDirection");
		String instanceViews = StringUtils.isEmpty(request.getParameter("instanceViews"))?"":request.getParameter("instanceViews");
		String instanceDefaultView = StringUtils.isEmpty(request.getParameter("instanceDefaultView"))?"":request.getParameter("instanceDefaultView");
		
		String sql = "select count(*) from td_avmon_mo_type where type_id = '%s' or caption = '%s'";
		String insertSql = "update td_avmon_mo_type set type_id = '%s',caption = '%s',parent_id = '%s',type_views = '%s',type_default_view = '%s',icon_cls = '%s',icon_cls_pause = '%s',icon_cls_error = '%s',resource_picture = '%s',display_flag = %s,display_order = %s,resource_picture_direction = %s,instance_views = '%s',instance_default_view = '%s'";
		insertSql = String.format(insertSql,typeId,caption,parentId,typeViews,typeDefaultView,iconCls,iconClsPause,iconClsError,resourcePicture,displayFlag,displayOrder,resourcePictureDirection,instanceViews,instanceDefaultView);
		int cnt = jdbcTemplate.queryForInt(sql);
		if (cnt > 0) {
			map.put("result", "1");
			map.put("msg", "类型ID或者类型名已经存在!");
		}else{
			try {
				jdbcTemplate.execute(insertSql);
				map.put("result", "success");
				map.put("msg", "保存成功!");
			} catch (Exception e) {
				logger.error(e);
				map.put("msg", "保存失败!");
			}
		}
		return JackJson.fromObjectToJson(map);
	}

	/**
	 * 保存TYPE基本信息
	 * @param request
	 * @return
	 */
	public String saveDeviceType(HttpServletRequest request) {
		Map<String,String> map = new HashMap<String, String>();
		String sql = "insert into td_avmon_mo_type(type_id,caption,parent_id) values('%s','%s','%s')";
		String typeId = request.getParameter("typeId");
		String caption = request.getParameter("caption");
		String parentId = request.getParameter("parentId");
		sql = String.format(sql, typeId,caption,parentId);
		try {
			jdbcTemplate.execute(sql);
			map.put("result", "success");
			map.put("msg", "保存成功!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+e.getMessage());
			map.put("msg", "保存失败!");
		}
		return JackJson.fromObjectToJson(map);
	}

	/**
	 * 删除类型
	 * @param request
	 * @return
	 */
	public String deleteType(HttpServletRequest request) {
		String typeId = request.getParameter("typeId");
		Map<String,String> map = new HashMap<String, String>();
		String queryMoCntSql = "select count(*) from td_avmon_mo_info where type_id ='"+typeId+"'";
		String delTypeSql = "delete from td_avmon_mo_type where type_id ='"+typeId+"'";
		String delAttrSql = "delete from td_avmon_mo_type_attribute where type_id ='"+typeId+"'";
		
		int cnt = jdbcTemplate.queryForInt(queryMoCntSql);
		
		if(cnt > 0){
			map.put("msg", "该类型有监控对象使用,不能删除!");
		}
		
		try {
			jdbcTemplate.execute(delAttrSql);
			jdbcTemplate.execute(delTypeSql);
			map.put("result", "success");
			map.put("msg", "删除成功!");
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			map.put("msg", "删除失败!");
		}
		
		return JackJson.fromObjectToJson(map);
	}

	/**
	 * 获取类型详细信息
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String,Object>> getTypeDetail(HttpServletRequest request) {
		List<Map<String,Object>> paramList = new ArrayList<Map<String,Object>>();
		HashMap<String,Object> details = new HashMap<String,Object>();
		List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
		String typeId = request.getParameter("typeId");
		String sql ="select type_id	as 	\"typeId\","+
						"caption	as 	\"caption\","+
						"parent_id	as 	\"parentId\","+
						"type_views	as 	\"typeView\","+
						"type_default_view	as 	\"typeDefaultView\","+
						"icon_cls	as 	\"iconCls\","+
						"icon_cls_pause	as 	\"iconClsPause\","+
						"icon_cls_error	as 	\"iconClsError\","+
						"resource_picture	as 	\"resourcePicture\","+
						"display_flag	as 	\"displayFlag\","+
						"display_order	as 	\"displayOrder\","+
						"resource_picture_direction	as 	\"resourcePictureDirection\","+
						"instance_views	as 	\"instanceView\","+
						"instance_default_view	as 	\"instanceDefaultView\" "+
						"from td_avmon_mo_type where type_id ='"+typeId+"'";
		
		list = jdbcTemplate.queryForList(sql);
		if(list.size()==1){
			details = list.get(0);
		}
		
		Map<String,Object> map ;
		for (Map.Entry<String, Object> entry : details.entrySet()) {
			map = new HashMap<String,Object>();
			map.put("name", entry.getKey());
			map.put("value", entry.getValue());
			paramList.add(map);
		}
		return paramList;
	}

	/**
	 * 获得设备类型属性下拉菜单
	 * @param request
	 * @return
	 */
	public List<Map<String, Object>> getDetailCombox(HttpServletRequest request) {
		String name = request.getParameter("name");
		String value = "";
		
		if(name.equals("typeView")){
			value = "type_views";
		}else if(name.equals("typeDefaultView")){
			value = "type_Default_View";
		}else if(name.equals("iconCls")){
			value = "icon_Cls";
		}else if(name.equals("iconClsPause")){
			value = "icon_Cls_Pause";
		}else if(name.equals("iconClsError")){
			value = "icon_Cls_Error";
		}else if(name.equals("resourcePicture")){
			value = "resource_Picture";
		}else if(name.equals("displayFlag")){
			value = "display_Flag";
		}else if(name.equals("displayOrder")){
			value = "display_Order";
		}else if(name.equals("resourcePictureDirection")){
			value = "resource_picture_direction";
		}else if(name.equals("instanceView")){
			value = "instance_views";
		}else if(name.equals("instanceDefaultView")){
			value = "instance_default_view";
		}
		
		String sql ="select distinct "+value+" as \"label\","+value+" as \"value\" from td_avmon_mo_type where "+value+" is not null";
		
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		
		return list;
	}

	/**
	 * 更新类型内容
	 * @param request
	 * @return
	 */
	public Map<String, Object> updateTypeDetail(HttpServletRequest request) {
		Map<String, Object> msg = new HashMap<String, Object>();
		msg.put("result", false);
		String name = request.getParameter("name");
		String value = request.getParameter("value");
		String typeId = request.getParameter("typeId");
		
		boolean isString = false;
		String column = "";
		if(name.equals("typeView")){
			column = "type_views";
		}else if(name.equals("typeDefaultView")){
			column = "type_Default_View";
		}else if(name.equals("iconCls")){
			column = "icon_Cls";
		}else if(name.equals("iconClsPause")){
			column = "icon_Cls_Pause";
		}else if(name.equals("iconClsError")){
			column = "icon_Cls_Error";
		}else if(name.equals("resourcePicture")){
			column = "resource_Picture";
		}else if(name.equals("displayFlag")){
			column = "display_Flag";
			isString = true;
		}else if(name.equals("displayOrder")){
			column = "display_Order";
			isString = true;
		}else if(name.equals("resourcePictureDirection")){
			column = "resource_picture_direction";
			isString = true;
		}else if(name.equals("instanceView")){
			column = "instance_views";
		}else if(name.equals("instanceDefaultView")){
			column = "instance_default_view";
		}
		
		if(StringUtils.isEmpty(value)){
			value = "null";
		}
		
		if(!isString&& (!StringUtils.isEmpty(value))){
			value = "'"+value+"'";
		}
		
		String sql = "update td_avmon_mo_type set "+column+" ="+value +" where type_id ='"+typeId+"'";
		try {
			jdbcTemplate.execute(sql);
			msg.put("result", true);
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			msg.put("result", false);
		}
		
		return msg;
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
        	logger.error(this.getClass().getName()+e.getMessage());
        }
        return ampListMap;
    }

    /**
     * 添加监控对象
     * @param request
     * @return
     */
	public Map<String, String> addWatch(HttpServletRequest request) {
		Map<String, String> msg = new HashMap<String, String>();
		String ids = request.getParameter("ids");
		String password = request.getParameter("snmpPwd");
		String insertMoSql = "insert into TD_AVMON_MO_INFO (MO_ID, TYPE_ID, CAPTION, PARENT_ID, DESCRIPTION, AGENT_ID,protocal_method) "
				+ "select d.device_id, t.type, d.device_ip, t.mo_type_id, d.device_desc, d.device_id,'SNMP' "
				+ "from td_avmon_discovery_device_info d "
				+ "left join td_avmon_device_type_info t "
				+ "on d.device_type = t.id "
				+ "where d.device_id in"
				+ "('"+ids+"')";
		String insertMoAttrSql = "insert into td_avmon_mo_info_attribute(mo_id,name,value) "
				+ "select device_id,'ip', t.device_ip from td_avmon_discovery_device_info t "
				+ "where t.device_id in"
				+ "('"+ids+"')";
		String insertSnmpPwdSql = "insert into td_avmon_mo_info_attribute(mo_id,name,value) "
				+ "select device_id,'snmppwd', '"+password+"' from td_avmon_discovery_device_info t "
				+ "where t.device_id in"
				+ "('"+ids+"')";
		
		String updateSnmpStatusSql = "update td_avmon_discovery_device_info set status = '1' where device_id in ('"+ids+"')";
		String insertSnmpSchedule = "INSERT INTO td_avmon_snmp_schedule(mo_id, oid_id, schedule, oid_index, oid_group, oid_name, oid_status)"
				+ " select distinct i.device_id,o.oid_id,o.schedule,null,o.oid_group,o.oid_name,'0' from td_avmon_discovery_device_info i,td_avmon_device_type_info t,td_avmon_snmp_mibfile f,td_avmon_snmp_miboid o "
				+ " where i.device_type = t.id and (t.type=f.device_type OR f.device_type = t.mo_type_id) and f.mib_file_name=o.mibfile_name and i.device_id in('"+ids+"')";
		try {
			int newMoCnt = ids.split(",").length;
			boolean notOverCount = licenseService.checkMoCount(newMoCnt);
			if (!notOverCount) {
				msg.put("result", "监控对象数量超过License允许最大值,添加失败!");
			}else{
				jdbcTemplate.batchUpdate(new String[]{insertMoSql,insertMoAttrSql,insertSnmpPwdSql,updateSnmpStatusSql,insertSnmpSchedule});
				msg.put("result", "监控添加成功!");
			}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			if(e.getMessage().indexOf("ORA-00001")>-1||e.getMessage().indexOf("duplicate key")>-1){
				msg.put("result", "监控对象已存在!");
			}else{
				msg.put("result", "监控添加失败!");
			}
		}
		
		return msg;
	}

	/**
	 * OID管理
	 * @param request
	 * @return
	 */
	public String getOids(HttpServletRequest request) {
		String oid = request.getParameter("oid");
    	String typeId = request.getParameter("type");
//    	String status = request.getParameter("status");
    	String mibfile = request.getParameter("mibfile");
    	String sort = request.getParameter("sortdatafield");
    	String sortorder = request.getParameter("sortorder");
    	int pageNo = StringUtils.isEmpty(request.getParameter("pagenum"))?0:Integer.valueOf(request.getParameter("pagenum"));
    	int pageSize = StringUtils.isEmpty(request.getParameter("pagesize"))?0:Integer.valueOf(request.getParameter("pagesize"));
    	
    	String sortStr = "";
    	
    	if(!StringUtils.isEmpty(sort)){
    		
    		if("oid".equals(sort)){
    			sort="o.oid_id";
    		}else if("deviceType".equals(sort)){
    			sort="f.device_type";
    		}else if("deviceName".equals(sort)){
    			sort="f.device_name";
    		}else if("oidType".equals(sort)){
    			sort = "o.oid_type";
    		}else if("oidName".equals(sort)){
    			sort = "o.oid_name";
    		}else if("status".equals(sort)){
    			sort = "o.status";
    		}else if("mibfileName".equals(sort)){
    			sort = "f.mib_file_Name";
    		}else if("oidGroup".equals(sort)){
    			sort = "o.oid_group";
    		}else if("schedule".equals(sort)){
    			sort = "o.schedule";
    		}else if("ocdt".equals(sort)){
    			sort = "o.create_dt";
    		}else if("oidDesc".equals(sort)){
    			sort = "o.oid_desc";
    		}else{
    			sort = "o.mib_file_name";
    		}
    		sortStr = " order by "+sort+" "+sortorder;
    	}
    	
    	String where =" where 1=1 ";
    	
    	if(!StringUtils.isEmpty(mibfile)){
    		where +=" and upper(o.mibfile_name) like '%"+mibfile.toUpperCase()+"%'";
    	}
    	
    	if(!StringUtils.isEmpty(typeId)){
    		where +=" and f.device_type ='"+typeId+"'";
    	}
    	if (!StringUtils.isEmpty(oid)) {
			where +=" and o.oid_id like '%"+oid+"%'";
		}
    	
    	String json = "";
    	String sql = "select distinct f.device_type as \"deviceType\","
    			+ "f.mib_file_name as \"mibfileName\","
    			+ "f.device_name as \"deviceName\","
    			+ "o.oid_id as \"oid\","
    			+ "o.oid_type as \"oidType\","
    			+ "o.oid_name as \"oidName\","
    			+ "o.oid_group as \"oidGroup\","
    			+ "o.schedule as \"schedule\","
    			+ "o.oid_desc as \"oidDesc\","
    			+ "o.status as \"status\","
    			+ "o.create_dt as \"ocdt\" "
    			+ "from td_avmon_snmp_miboid o "
    			+ "left join  td_avmon_snmp_mibfile f  on  o.mibfile_name = f.mib_file_name "+
					   where + sortStr;
    	int start = pageNo*pageSize;
    	int limit = start+pageSize;
    	String cntSql = "select  count(*) from td_avmon_snmp_miboid o "
    			+ "left join  td_avmon_snmp_mibfile f  on  o.mibfile_name = f.mib_file_name "+where;
    	int totalRows = jdbcTemplate.queryForInt(cntSql);
    	sql = this.pagination(sql, start, limit);
		@SuppressWarnings("unchecked")
		List<Map<String,String>> list  = jdbcTemplate.queryForList(sql);
		HashMap<String,Object> map = new HashMap<String, Object>();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
    	if(list.size() > 0){
    		map.put("oids", list);
    		map.put("totalRows", totalRows);
    		result.add(map);
    		json = JackJson.fromObjectToJson(result);
    	}else{
    		json = FAILURE;//失败
    	}
    	
    	return json;
	}
	
	/**
	 * 修改oid的Schedule,index属性
	 * @param request
	 * @return
	 */
	public String updateMibOid(HttpServletRequest request){
		String json = "";
		Map<String,String> result = new HashMap<String, String>();
		String schedule = request.getParameter("schedule");
		String group = request.getParameter("group");
		String oid = request.getParameter("oid");
		String mibfile = request.getParameter("mibfile");
		String deviceType = request.getParameter("deviceType");
		String model = request.getParameter("model");
		String sql ="";
		if ("1".equals(model)) {
			sql = "update td_avmon_snmp_mibfile set device_type = '"+deviceType+"' where mib_file_name = '"+mibfile+"'" ;
		}else{
			sql = "update td_avmon_snmp_miboid set schedule='%s' where oid_group ='%s'";
			if (StringUtils.isEmpty(group)) {
				sql = "update td_avmon_snmp_miboid set schedule='%s' where oid_id ='%s'";
				sql = String.format(sql, schedule,oid);
			}else{
				sql = String.format(sql, schedule,group);
			}
		}
		
		try {
			jdbcTemplate.execute(sql);
			logger.debug("=============================updateMibOid=============="+sql);
			result.put("result", SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage());
			result.put("result", FAILURE);
		}
		
		json = JackJson.fromObjectToJson(result);
		logger.debug("================updateOid============"+json);
		return json;
	}

	/**
	 * 导入MIB文件
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public String importMibFile(HttpServletRequest request) throws IOException {

		String result = StringUtil.EMPTY;
		HashMap<String, ArrayList<Map<String, String>>> resultMap = new HashMap<String, ArrayList<Map<String, String>>>();
		ArrayList<Map<String, String>> files = new ArrayList<Map<String, String>>();
		String typeId = request.getParameter("typeId");
		String deviceName = request.getParameter("deviceName");
		String deviceDesc = request.getParameter("deviceDesc");
		String factory = request.getParameter("factory");
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

		// 获取路径
		String templatePath = Config.getInstance().getMibfilesUploadPath();
		File dirPath = new File(templatePath);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		Map<String, String> fileMap;
		for (Iterator<String> it = multipartRequest.getFileNames(); it.hasNext();) {
			String fileName = (String) it.next();
			fileMap = new HashMap<String, String>();
			CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile(fileName);
			if (file != null && file.getSize() != 0) {
				String sep = System.getProperty("file.separator");
				File uploadedFile = new File(dirPath + sep + file.getOriginalFilename());
				FileCopyUtils.copy(file.getBytes(), uploadedFile);

				try {
					insertMibFileTb(file.getOriginalFilename(), typeId,deviceName, deviceDesc, factory);
					MibFileParserDB mibFileParser = new MibFileParserDB(dirPath + sep + file.getOriginalFilename());
					mibFileParser.parseMib(jdbcTemplate);
				} catch (MibLoaderException e) {
					fileMap.put("error", "Mib文件有错");
					logger.error(e.getMessage());
					
				}catch (DataAccessException e1) {
					logger.error(e1.getMessage());
					fileMap.put("error", "数据库插入失败,请重试");
				}
				catch (Exception e2) {
					logger.error(this.getClass().getName()+e2.getMessage());
					fileMap.put("error", "上传失败,请重试");
				}
			}
			
			fileMap.put("url", "");
			fileMap.put("thumbnailUrl", "");
			fileMap.put("name", file.getOriginalFilename());
			fileMap.put("type", "image/jpeg");
			fileMap.put("size", file.getSize() + "");
			fileMap.put("deleteUrl", "");
			fileMap.put("deleteType", "DELETE");
			files.add(fileMap);
		}

		resultMap.put("files", files);
		result = JackJson.fromObjectToJson(resultMap);
		logger.debug(result);
		return result;
	}
	
	/**
	 * 插入mibfile表
	 * @param fileName
	 * @param deviceType
	 * @param deviceName
	 * @param deviceDesc
	 * @param factory
	 * @throws Exception
	 */
	private void insertMibFileTb(String fileName,String deviceType,String deviceName,String deviceDesc,String factory) throws DataAccessException{
		String fileId = UUID.randomUUID().toString().replace("-", "");
		String sql = "INSERT INTO td_avmon_snmp_mibfile(mib_file_id, mib_file_name, device_type, device_name, device_desc,from_factroy, status, create_dt)VALUES ('%s','%s','%s', '%s', '%s','%s', '%s', "+DBUtils.getDBCurrentDateFunction()+")";
		sql = String.format(sql, fileId,fileName,deviceType,deviceName,deviceDesc,factory,"0");
		jdbcTemplate.execute(sql);
	}
	/**
	 * 根据设备ID查询该设备具有的oid,调度,oid_index,status等,
	 * 如果缺少或者没有,就去相应表查找,并插入td_avmon_snmp_schedule
	 * @param deviceId
	 * @param flag 是否是双击之后显示初始化页面
	 * @return
	 */
	public void initSchedule(String deviceId,String flag){
		String sql = "";
		sql = "insert into td_avmon_snmp_schedule(mo_id,oid_id,oid_name,schedule) select d.device_id,o.oid_id,o.oid_name,o.schedule from td_avmon_discovery_device_info d,td_avmon_device_type_info t,td_avmon_snmp_mibfile f,td_avmon_snmp_miboid o where d.device_type=t.id and t.type = f.device_type and f.mib_file_name = o.mibfile_name where d.device_id = '%s' and o.oid_id not exists(select oid_id from td_avmon_snmp_schedule where mo_id = '%s')";
		sql = String.format(sql, deviceId,deviceId);
		jdbcTemplate.execute(sql);
	}
	
	/**
	 * 根据oid等查询分页oid查询设置页面
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public String querySchedule(HttpServletRequest request){
		String json = "[]";
		String oid = request.getParameter("oid");
		String flag = request.getParameter("flag");
		String deviceId = request.getParameter("deviceId");
		List<Map<String,String>> list = new ArrayList<Map<String,String>>(); 
		String sql = "select oid_id as \"oid\",oid_name as \"oidName\", schedule as \"schedule\" from td_avmon_snmp_schedule where mo_Id = '"+deviceId+"'";
		String where = " 1=1 ";
		if (!StringUtils.isEmpty(oid)) {
			where +=" and o.oid_name like'%"+oid+"%'";
			sql += where;
		}
		
		list = jdbcTemplate.queryForList(sql);
		if (list.size() > 0) {
			json = JackJson.fromObjectToJson(list);
		}
		return json;
	}
	
	/**
	 * 修改oid的Schedule,index属性
	 * @param request
	 * @return
	 */
	public String updateOid(HttpServletRequest request){
		String json = "";
		Map<String,String> result = new HashMap<String, String>();
		String deviceId = request.getParameter("deviceId");
		String oid = request.getParameter("oid");
		String schedule = request.getParameter("schedule");
		String oidIndex = request.getParameter("index");
		String group = request.getParameter("group");
		String status = request.getParameter("status");
		String sql = "";
		if ("1".equals(status)) {
			sql = "update td_avmon_snmp_schedule set schedule='%s',oid_index='%s' where mo_id = '%s' and oid_group='%s'";
			sql = String.format(sql, schedule,oidIndex,deviceId,group,status);
		}else{
			sql = "update td_avmon_snmp_schedule set schedule='%s',oid_index='%s' where mo_id = '%s' and oid='%s'";
			sql = String.format(sql, schedule,oidIndex,deviceId,oid);
		}
		//String scheduleSql = "select distinct schedule from td_avmon_snmp_schedule where mo_id = '%s' and oid_group = '%s' and oid_status = '1'";
		
		try {
			jdbcTemplate.execute(sql);
			return this.querySchedule(request);
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			result.put("result", FAILURE);
		}
		
		json = JackJson.fromObjectToJson(result);
		logger.debug("================updateOid============"+json);
		return json;
	}
	
	/**
	 * 启用/禁用oid
	 * @return
	 */
	public String enableDisableOid(HttpServletRequest request,boolean isBatchUpdate){
		String json = "";
		String idlist = "";
		String flag = request.getParameter("flag");
		String moId = request.getParameter("moId");
		StringBuffer sb = new StringBuffer();
		if (!isBatchUpdate) {//如果不是批量更改
			idlist = request.getParameter("ids");
			
		}else{
			List<Map<String,String>> list  = (List<Map<String, String>>) request.getSession().getAttribute("queryList");
			for (int i =0;i<list.size();i++) {
				sb.append(list.get(i).get("oid"));
				if (i<list.size()-1) {
					sb.append("','");
				}
			}
			idlist = sb.toString();
		}
		Map<String,String> resultMap = new HashMap<String, String>();
		
		String sql = "update td_avmon_snmp_schedule set oid_status = '"+flag+"' where oid_id in ('"+idlist+"') and mo_id = '"+moId+"'";
		try {
			logger.debug("=====================enableDisableOid====================="+sql);
			jdbcTemplate.execute(sql);
			resultMap.put("result", SUCCESS);
		} catch (Exception e) {
			logger.error(e.getMessage());
			resultMap.put("result", FAILURE);
		}
		
		json = JackJson.fromObjectHasDateToJson(resultMap);
		logger.debug("==================enableDisableOid========= "+json);
		return json;
	}

	/**
	 * 删除无用的oid
	 * @param request
	 * @return
	 */
	public String deleteOids(HttpServletRequest request) {
		HashMap<String,String> resultMap = new HashMap<String, String>();
		String oids = request.getParameter("ids");
		String deleteSql = "delete from td_avmon_snmp_miboid where oid_id in ('"+oids+"')";
		if (oids.length() >0) {
			if (!isUsedByDevice(oids)) {
				try {
					jdbcTemplate.execute(deleteSql);
					resultMap.put("result", SUCCESS);
				} catch (Exception e) {
					logger.error(this.getClass().getName()+e.getMessage());
					resultMap.put("result", "删除失败!");
				}
			}else{
				resultMap.put("result", "oid正在被使用,不能被删除");
			}
		}
		
		return JackJson.fromObjectToJson(resultMap);
	}
	
	/**
	 * 
	 * @return
	 */
	private boolean isUsedByDevice(String ids){
		boolean result = true;
		String checkSql = "select count(*) from td_avmon_snmp_schedule where oid_id in('"+ids+"')";
		int usecount = jdbcTemplate.queryForInt(checkSql);
		if (usecount == 0) {
			result = false;
		}
		return result;
	}

	public List<Map<String, Object>> getTypeCombox(HttpServletRequest request) {
		String sql ="select distinct caption as \"label\",type_id as \"value\" from td_avmon_mo_type";
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list = jdbcTemplate.queryForList(sql);
		return list;
	}
}
