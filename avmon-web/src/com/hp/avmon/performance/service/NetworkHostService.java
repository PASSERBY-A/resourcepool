/**
 * 
 */
package com.hp.avmon.performance.service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.performance.store.KpiDataStore;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.SqlManager;

/**
 * @author qinjie
 * 
 */
@Service
public class NetworkHostService {
	private static final Log logger = LogFactory.getLog(NetworkHostService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private KpiDataStore kpiDataStore;

	public List getNetworkUsedInfo(String moId) {

		// 需要确认查询cpu instance的方式是否正确
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultListMap = new HashMap<String, Object>();
		String sql = String.format(
				SqlManager.getSql(NetworkHostService.class, "ilo_kpi_list_cpu"),
				moId);
		result = jdbcTemplate.queryForList(sql);
		return result;
	}
	
	public List getNetworkPortConfigList(String moId) {

		// 需要确认查询cpu instance的方式是否正确
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultListMap = new HashMap<String, Object>();
		String sql = String.format(
				SqlManager.getSql(NetworkHostService.class, "network_kpi_list_port"),
				moId);
		result = jdbcTemplate.queryForList(sql);
		return result;
	}
	public List getNetworkInPortList(String moId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultListMap = new HashMap<String, Object>();
		String sql = String.format(
				SqlManager.getSql(NetworkHostService.class, "network_kpi_list_inport"),
				moId);
		result = jdbcTemplate.queryForList(sql);
		return result;
	}
	
	public List getNetworkOutPortList(String moId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultListMap = new HashMap<String, Object>();
		String sql = String.format(
				SqlManager.getSql(NetworkHostService.class, "network_kpi_list_outport"),
				moId);
		result = jdbcTemplate.queryForList(sql);
		return result;
	}
	
	public Map getNetWorkHostBasicInfo(HttpServletRequest request) {
		// 需要确认查询cpu instance的方式是否正确
		/*
		 * List<Map<String,Object>> result = new
		 * ArrayList<Map<String,Object>>(); Map<String,Object> resultListMap =
		 * new HashMap<String,Object>(); String sql =
		 * String.format(SqlManager.getSql(IloHostService.class,
		 * "ilo_kpi_list_cpu"),moId); result = jdbcTemplate.queryForList(sql);
		 */
		Locale locale = request.getLocale();
		String moId = request.getParameter("moId");
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		/*
		 * String productName = bundle.getString("productName"); String
		 * productSN = bundle.getString("productSN"); String productUUID =
		 * bundle.getString("productUUID"); String tpmStatus =
		 * bundle.getString("tpmStatus");
		 */
		String sysName = null;
		String sysIp = null;
		String sysObjectID = null;
		String sysDescr = null;
		/*
		 * String systemRom = bundle.getString("cpuType"); String iloVersion =
		 * bundle.getString("memSize");
		 */
		// String osVersion = bundle.getString("osVersion");
		// String treeId = request.getParameter("treeId");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map m = new HashMap();
		if (sysName == null) {
			sysName = "sysName";
		}
		m.put("KEY", sysName);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "9200008089"));
		list.add(m);
		m = new HashMap();
		if (sysIp == null) {
			sysIp = "sysIp";
		}
		m.put("KEY", sysIp);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "9200024234"));
		list.add(m);
		if (sysObjectID == null) {
			sysObjectID = "sysObjectID";
		}
		m = new HashMap();
		m.put("KEY", sysObjectID);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "9200008086"));
		list.add(m);

		if (sysDescr == null) {
			sysDescr = "sysDescr";
		}
		m = new HashMap();
		m.put("KEY", sysDescr);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "9200008085"));
		list.add(m);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("basicTotal", list.size());
		map.put("basicData", list);

		return map;

	}

	@SuppressWarnings("unchecked")
	public Object getPowerComm(String moId, ResourceBundle bundle) {
		// 需要确认查询power instance的方式是否正确
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		String generalStatus = bundle.getString("generalStatus");
		String redundantStatus = bundle.getString("redundantStatus");
		String sql = String.format(SqlManager.getSql(NetworkHostService.class,
				"ilo_kpi_list_powerComm"), moId);
		// @SuppressWarnings("unchecked")
		List<Map<String, String>> instanceList = new ArrayList<Map<String, String>>();
		instanceList = jdbcTemplate.queryForList(sql);
		if (instanceList != null && instanceList.size() != 0) {
			// 将查询结果转换为返回类型
			for (Map<String, String> temp : instanceList) {
				Map<String, String> map = new HashMap<String, String>();
				// 电源状态
				map.put("key", generalStatus);
				map.put("value", temp.get("powerStatus"));
				resultList.add(map);
				map = new HashMap<String, String>();
				// 冗余
				map.put("key", redundantStatus);
				map.put("value", temp.get("powerRedundancyStatus"));
				resultList.add(map);
			}
		}

		return resultList;
	}

	

	public List getNetWorkPerformanceList(HttpServletRequest request) {
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		String queryIP = request.getParameter("queryIP");
		String typeId = request.getParameter("typeId");

		if (typeId == null) {
			typeId = "'NETWORK_%'";
		} else {
			typeId = "'" + typeId + "'";
		}
		if (sortdatafield == null)
			sortdatafield = "count";
		if (sortorder == null)
			sortorder = "desc";
		String bizName = request.getParameter("bizName");
		try {
			bizName = URLDecoder.decode(bizName, "utf-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 字符编码和文档编码一致
		/*String where = "";
		if (queryIP != null) {
			where = "where a.caption like '%" + queryIP + "%'";
		}
		String whereString = " attr.value ='" + bizName + "' ";
		String sqlString = SqlManager.getSql(PerformanceService.class,
				"getNetworkPerformanceDataList");
		String sql = String.format(sqlString, typeId, whereString, where);*/
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
			 sqlString=SqlManager.getSql(PerformanceService.class,"getNetworkPerformanceOtherDataList"); 
			 sql=String.format(sqlString,typeId,where);
		}else{
			sqlString=SqlManager.getSql(PerformanceService.class,"getNetworkPerformanceDataList");
			sql=String.format(sqlString,typeId,whereString,where);
		}
		
		String pagesize = request.getParameter("pagesize");
		if (pagesize == null)
			pagesize = "20";
		String pagenum = request.getParameter("pagenum");
		if (pagenum == null)
			pagenum = "0";
		Integer pagenum1 = (Integer.parseInt(pagenum));
		String querySql = "";
		if (DBUtils.isOracle()) {
			querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
			querySql = querySql + " order by " + "\"" + sortdatafield + "\""
					+ " " + sortorder;
		} else {
			querySql = generatPageSqlForJQW(sql, pagesize, pagenum1.toString());
		}
       //System.out.println("network querySql: "+querySql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql);
		return list;
	}

	public int getNetworkPerformanceListCount(HttpServletRequest request) {
		String bizName = request.getParameter("bizName");
		String queryIP = request.getParameter("queryIP");
		String typeId = request.getParameter("typeId");
		if (typeId == null) {
			typeId = "'NETWORK_%'";
		} else {
			typeId = "'" + typeId + "'";
		}
		try {
			bizName = URLDecoder.decode(bizName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}// 字符编码和文档编码一致
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
				sqlString=SqlManager.getSql(PerformanceService.class,"getNetworkPerformanceOtherDataListCount");
				sql=String.format(sqlString,typeId,where);
			}else{
				whereString=" attr.value ='"+bizName+"' ";
				sqlString=SqlManager.getSql(PerformanceService.class,"getNetworkPerformanceDataListCount");
				sql=String.format(sqlString,typeId,whereString,where);
			}
			
		/*String where = "";
		if (queryIP != null) {
			where = "where a.caption like '%" + queryIP + "%'";
		}
		String whereString = " attr.value ='" + bizName + "' ";
		String sqlString = SqlManager.getSql(PerformanceService.class,
				"getNetworkPerformanceDataListCount");
		String sql = String.format(sqlString, typeId, whereString, where);*/
		int count = jdbcTemplate.queryForInt(sql);
		return count;
	}

	public List getNetworkOverViewPerformanceList(HttpServletRequest request) {

		String bizName = request.getParameter("bizName");
		String queryIP = request.getParameter("queryIP");

		try {
			bizName = URLDecoder.decode(bizName, "utf-8");

		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}// 字符编码和文档编码一致
		String whereString=" attr.value ='"+bizName+"' ";
		String where="";
		String sqlString="";
		String sql="";
		String typeId=request.getParameter("typeId");
		if(typeId==null){
			typeId="'NETWORK_%'";
		}
		else{
			typeId="'"+typeId+"'";
		}
		if(queryIP!=null){
			where="where a.caption like '%"+queryIP+"%'";
		}
		String moId=request.getParameter("moId");
		if(moId!=null){
			where="where a.mo_id='"+moId+"'";
		}
		if(bizName.equals("others")){
			 sqlString=SqlManager.getSql(PerformanceService.class,"getNetworkOverViewPerformanceOtherDataList");
			 sql=String.format(sqlString,typeId,where);
		}else{
			 sqlString=SqlManager.getSql(PerformanceService.class,"getNetworkOverViewPerformanceDataList");
			 sql=String.format(sqlString,typeId,whereString,where);
		}
		
		/*String whereString = " attr.value ='" + bizName + "' ";
		String where = "";
		if (queryIP != null) {
			where = "where a.caption like '%" + queryIP + "%'";
		}
		String sqlString = SqlManager.getSql(PerformanceService.class,
				"getNetworkOverViewPerformanceDataList");
		String sql = String.format(sqlString, "'NETWORK_FIREWALL'", whereString,
				where);*/

		List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
		return list;
	}

	// jqw分页
	private String generatPageSqlForJQW(String sql, String limit, String start) {
		Integer limitL = Integer.valueOf(limit);
		// Integer startL = Integer.valueOf(start)+1;
		Integer startL = Integer.valueOf(start);

		// 构造oracle数据库的分页语句
		return DBUtils.paginationforjqw(sql, startL, limitL);
	}

}
