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

import com.google.zxing.client.result.ProductParsedResult;
import com.hp.avmon.performance.store.KpiDataStore;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.MyString;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmonserver.entity.KpiEvent;

/**
 * @author qinjie
 * 
 */
@Service
public class IloHostService {
	private static final Log logger = LogFactory.getLog(IloHostService.class);

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private KpiDataStore kpiDataStore;

	public List getIloHostCpuInfo(String moId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultListMap = new HashMap<String, Object>();
		String sql = String.format(
				SqlManager.getSql(IloHostService.class, "ilo_kpi_list_cpu"),
				moId);
		result = jdbcTemplate.queryForList(sql);
		List<KpiEvent> list=kpiDataStore.getKpiList(moId,null,"5201001002");
		Map<String,String> map=new HashMap<String,String>();
		if(list.size()>0){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getInstance().contains("cpu")){
				map.put(list.get(i).getInstance().substring(
						list.get(i).getInstance().indexOf("-")+1), 
						list.get(i).getStrValue());
			}
		}
		}
		if(map.keySet().size()>0){
			
		for(String key:map.keySet()){
				for(int j=0;j<result.size();j++){
				if(result.get(j).get("cpu").equals(key)){
					result.get(j).put("cpuTemp", map.get(key));
				}
			}
		}
		}
		return result;
	}

	public List getStorageList(String moId) {
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		Map<String, Object> resultListMap = new HashMap<String, Object>();
		String sql = String
				.format(SqlManager.getSql(IloHostService.class,
						"ilo_kpi_list_storage"), moId);
		result = jdbcTemplate.queryForList(sql);
	
		return result;
	}

	public Map getIloHostBasicInfo(HttpServletRequest request) {
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
		String productName = null;
		String productSN = null;
		String productUUID = null;
		String tpmStatus = null;
		String systemRom = null;
		String iloVersion = null;
		/*
		 * String systemRom = bundle.getString("cpuType"); String iloVersion =
		 * bundle.getString("memSize");
		 */
		// String osVersion = bundle.getString("osVersion");
		// String treeId = request.getParameter("treeId");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map m = new HashMap();
		if (productName == null) {
			productName = "productName";
		}
		m.put("KEY", productName);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201002045"));
		list.add(m);
		m = new HashMap();
		if (productSN == null) {
			productSN = "productSN";
		}
		m.put("KEY", productSN);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201002046"));
		list.add(m);
		if (productUUID == null) {
			productUUID = "productUUID";
		}
		m = new HashMap();
		m.put("KEY", productUUID);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201002047"));
		list.add(m);

		if (tpmStatus == null) {
			tpmStatus = "tpmStatus";
		}
		m = new HashMap();
		m.put("KEY", tpmStatus);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201002050"));
		list.add(m);

		if (systemRom == null) {
			systemRom = "systemRom";
		}

		m = new HashMap();
		m.put("KEY", systemRom);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201002051"));
		list.add(m);

		if (iloVersion == null) {
			iloVersion = "iloVersion";
		}
		m = new HashMap();
		m.put("KEY", iloVersion);
		// m.put("VAL",MyString.toVolumeString(kpiDataStore.getCurrentKpiNumValue(moId,
		// "5201002053")));
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201002053"));
		list.add(m);

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("basicTotal", list.size());
		map.put("basicData", list);

		return map;

	}

	public Map getIloHostCaseInfo(HttpServletRequest request) {
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
		String alarm = null;
		String caseTemp = null;
		String vrmStatus = null;
		String vrmTtlStatus = null;
		String fanTtlStatus = null;
		String fanTtlRedundency = null;
		String tempTtlStatus = null;
		/*
		 * String systemRom = bundle.getString("cpuType"); String iloVersion =
		 * bundle.getString("memSize");
		 */
		// String osVersion = bundle.getString("osVersion");
		// String treeId = request.getParameter("treeId");
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map m = new HashMap();
		if (alarm == null) {
			alarm = "alarm";
		}
		m.put("KEY", alarm);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201001011"));
		list.add(m);
		m = new HashMap();
		if (caseTemp == null) {
			
			caseTemp = "caseTemp";
		}
		m.put("KEY", caseTemp);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201001010"));
		list.add(m);
		if (vrmStatus == null) {
			vrmStatus = "vrmStatus";
		}
		m = new HashMap();
		m.put("KEY", vrmStatus);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201001051"));
		list.add(m);

		if (vrmTtlStatus == null) {
			vrmTtlStatus = "vrmTtlStatus";
		}
		m = new HashMap();
		m.put("KEY", vrmTtlStatus);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201001052"));
		list.add(m);

		if (fanTtlStatus == null) {
			fanTtlStatus = "fanTtlStatus";
		}

		m = new HashMap();
		m.put("KEY", fanTtlStatus);
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201001053"));
		list.add(m);

		if (fanTtlRedundency == null) {
			fanTtlRedundency = "fanTtlRedundency";
		}
		m = new HashMap();
		m.put("KEY", fanTtlRedundency);
		// m.put("VAL",MyString.toVolumeString(kpiDataStore.getCurrentKpiNumValue(moId,
		// "5201002053")));
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201001054"));
		list.add(m);
		
		if (tempTtlStatus == null) {
			tempTtlStatus = "tempTtlStatus";
		}
		m = new HashMap();
		m.put("KEY", tempTtlStatus);
		// m.put("VAL",MyString.toVolumeString(kpiDataStore.getCurrentKpiNumValue(moId,
		// "5201002053")));
		m.put("VAL", kpiDataStore.getCurrentKpiValue(moId, "5201001055"));
		list.add(m);


		Map<String, Object> map = new HashMap<String, Object>();
		map.put("basicTotal", list.size());
		map.put("basicData", list);

		return map;

	}

	/**
	 * 电源总体状况
	 * 
	 * @param moId
	 * @return
	 */
	public List getPowerList(String moId) {
		// 需要确认查询power instance的方式是否正确
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		String sql = String.format(
				SqlManager.getSql(IloHostService.class, "ilo_kpi_list_power"),
				moId);
		resultList = jdbcTemplate.queryForList(sql);
		//查询电源温度指标
		List<KpiEvent> list=kpiDataStore.getKpiList(moId,null,"5201001002");
		Map<String,String> map=new HashMap<String,String>();
		if(list.size()>0){
		for(int i=0;i<list.size();i++){
			if(list.get(i).getInstance().contains("p/s")){
				map.put(list.get(i).getInstance().substring(
						list.get(i).getInstance().indexOf("-")+1), 
						list.get(i).getStrValue());
			}
		}
		}
		if(map.keySet().size()>0){
			//15-p/s_2 14-p/s_1
		for(String key:map.keySet()){
				for(int j=0;j<resultList.size();j++){
				if(resultList.get(j).get("power").replace("power_supply", "p/s").equals(key)){
					resultList.get(j).put("temp", map.get(key));
				}
			}
		}
		}
		return resultList;
	}

	/**
	 * 内存
	 * 
	 * @param moId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getMemList(String moId) {

		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		String sql = String.format(
				SqlManager.getSql(IloHostService.class, "ilo_kpi_list_mem"),
				moId);
		resultList = jdbcTemplate.queryForList(sql);
		return resultList;
	}

	/**
	 * 存储
	 * 
	 * @param moId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getDiskList(String moId) {

		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		String sql = String.format(
				SqlManager.getSql(IloHostService.class, "ilo_kpi_list_disk"),
				moId);
		resultList = jdbcTemplate.queryForList(sql);
		return resultList;
	}

	/**
	 * 网卡
	 * 
	 * @param moId
	 * @return
	 */
	public List getNetcardList(String moId) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		String sql = String
				.format(SqlManager.getSql(IloHostService.class,
						"ilo_kpi_list_netcard"), moId);
		resultList = jdbcTemplate.queryForList(sql);

		return resultList;
	}

	/**
	 * 获取风扇运行参数
	 * 
	 * @param moId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getFanList(String moId) {

		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String sql = String.format(
				SqlManager.getSql(IloHostService.class, "ilo_kpi_list_fan"),
				moId);
		resultList = jdbcTemplate.queryForList(sql);
		return resultList;
	}

	/**
	 * 主板插槽
	 * 
	 * @param moId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List getMainBoardList(String moId) {
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
		String sql = String.format(SqlManager.getSql(IloHostService.class,
				"ilo_kpi_list_mainboard"), moId);
		resultList = jdbcTemplate.queryForList(sql);
		return resultList;
	}

	/**
	 * 机箱状态
	 * 
	 * @param moId
	 * @return
	 */
	public List getCaseList(String moId) {
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		String sql = String.format(
				SqlManager.getSql(IloHostService.class, "ilo_kpi_list_case"),
				moId);
		resultList = jdbcTemplate.queryForList(sql);
		return resultList;
	}

	// add by mark start
	/**
	 * 主板插槽信息
	 * 
	 * @param moId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getBiosList(String moId, ResourceBundle bundle) {
		List<Map<String, String>> instanceList = new ArrayList<Map<String, String>>();
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		String seriesName = bundle.getString("seriesName");
		String lastUpdateTime = bundle.getString("lastUpdateTime");
		String biosStatus = "biosStatus";
		String uidStatus = "uidStatus";
		try {
			biosStatus = bundle.getString("biosStatus");
			uidStatus = bundle.getString("uidStatus");
		} catch (Exception e) {

		}

		String sql = String.format(
				SqlManager.getSql(IloHostService.class, "ilo_kpi_list_bios"),
				moId);
		instanceList = jdbcTemplate.queryForList(sql);
		if (instanceList != null && instanceList.size() != 0) {
			// 将查询结果转换为返回类型
			for (Map<String, String> temp : instanceList) {
				Map<String, String> map = new HashMap<String, String>();
				map.put("key", seriesName);
				map.put("value", temp.get("biosSeries"));

				resultList.add(map);
				map = new HashMap<String, String>();
				map.put("key", lastUpdateTime);
				map.put("value", temp.get("lastUpdateTime"));
				resultList.add(map);
				map = new HashMap<String, String>();
				map.put("key", biosStatus);
				map.put("value", temp.get("biosStatus"));
				resultList.add(map);
				map = new HashMap<String, String>();
				map.put("key", uidStatus);
				map.put("value", temp.get("uidStatus"));
				resultList.add(map);
			}
		}

		return resultList;
	}

	// add by mark end 2013-9-25
	// add by mark start 2013-10-22
	/**
	 * 
	 * @param moId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Object getCpuComm(String moId, ResourceBundle bundle) {
		String frequency = bundle.getString("frequency");
		String digit = bundle.getString("digit");
		String threadCount = bundle.getString("threadCount");
		String cpuStatus = bundle.getString("threadCount");

		List<Map<String, String>> commList = new ArrayList<Map<String, String>>();
		String sql = String
				.format(SqlManager.getSql(IloHostService.class,
						"ilo_kpi_list_cpuComm"), moId);
		
		List<Map<String, String>> instanceList = jdbcTemplate.queryForList(sql);
		//System.out.println("instanceList is:" + instanceList);
		if (instanceList != null && instanceList.size() != 0) {

			// 将查询结果转换为返回类型
			for (Map<String, String> temp : instanceList) {

				HashMap<String, String> map = new HashMap<String, String>();
				map.put("key", "cpu");
				map.put("value", temp.get("cpu"));
				commList.add(map);
				map = new HashMap<String, String>();
				map.put("key", frequency);
				map.put("value", temp.get("cpuFreq"));
				commList.add(map);
				map = new HashMap<String, String>();
				map.put("key", digit);
				map.put("value", temp.get("cpuBit"));
				commList.add(map);
				map = new HashMap<String, String>();
				map.put("key", threadCount);
				map.put("value", temp.get("cpuCore"));
				commList.add(map);
				map = new HashMap<String, String>();
				map.put("key", cpuStatus);
				map.put("value", temp.get("cpuStatus"));
				commList.add(map);
			}
		}
		//System.out.println("commList is:" + commList);
		return commList;
	}

	// add by mark end 2013-10-22

	// add by mark start 2013-10-23
	@SuppressWarnings("unchecked")
	public Object getPowerComm(String moId, ResourceBundle bundle) {
		// 需要确认查询power instance的方式是否正确
		List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
		String generalStatus = bundle.getString("generalStatus");
		String redundantStatus = bundle.getString("redundantStatus");
		String sql = String.format(SqlManager.getSql(IloHostService.class,
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

	// add by mark end 2013-10-23

	public List getIloPerformanceList(HttpServletRequest request) {
		String sortdatafield = request.getParameter("sortdatafield");
		String sortorder = request.getParameter("sortorder");
		String queryIP = request.getParameter("queryIP");
		String typeId = request.getParameter("typeId");

		if (typeId == null) {
			typeId = "'HARDWARE_HP'";
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
			 sqlString=SqlManager.getSql(PerformanceService.class,"getIloPerformanceOtherDataList"); 
			 sql=String.format(sqlString,typeId,where);
		}else{
			sqlString=SqlManager.getSql(PerformanceService.class,"getIloPerformanceDataList");
			sql=String.format(sqlString,typeId,whereString,where);
		}
		
/*		String where = "";
		if (queryIP != null) {
			where = "where a.caption like '%" + queryIP + "%'";
		}
		String whereString = " attr.value ='" + bizName + "' ";
		String sqlString = SqlManager.getSql(PerformanceService.class,
				"getIloPerformanceDataList");
		String sql = String.format(sqlString, typeId, whereString, where);*/
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

		//System.out.println("ilo sql @@@@@@@@@@@@@@is:" + querySql);
		List<Map<String, Object>> list = jdbcTemplate.queryForList(querySql);
		return list;
	}

	public int getIloPerformanceListCount(HttpServletRequest request) {
		String bizName = request.getParameter("bizName");
		String queryIP = request.getParameter("queryIP");
		String typeId = request.getParameter("typeId");
		if (typeId == null) {
			typeId = "'HARDWARE_HP'";
		} else {
			typeId = "'" + typeId + "'";
		}
		try {
			bizName = URLDecoder.decode(bizName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}// 字符编码和文档编码一致
		
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
			 sqlString=SqlManager.getSql(PerformanceService.class,"getIloPerformanceOtherDataListCount"); 
			 sql=String.format(sqlString,typeId,where);
		}else{
			sqlString=SqlManager.getSql(PerformanceService.class,"getIloPerformanceDataListCount");
			sql=String.format(sqlString,typeId,whereString,where);
		}
		
		/*String where = "";
		if (queryIP != null) {
			where = "where a.caption like '%" + queryIP + "%'";
		}
		String whereString = " attr.value ='" + bizName + "' ";
		String sqlString = SqlManager.getSql(PerformanceService.class,
				"getIloPerformanceDataListCount");
		String sql = String.format(sqlString, typeId, whereString, where);*/
		int count = jdbcTemplate.queryForInt(sql);
		return count;
	}

	public List getIloOverViewPerformanceList(HttpServletRequest request) {

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
		/*if(typeId==null){
			typeId="'HARDWARE_HP'";
		}
		else{
			typeId="'"+typeId+"'";
		}*/
		if(queryIP!=null){
			where="where a.caption like '%"+queryIP+"%'";
		}
		String moId=request.getParameter("moId");
		if(moId!=null){
			where="where a.mo_id='"+moId+"'";
		}
		
		if(bizName.equals("others")){
			 sqlString=SqlManager.getSql(PerformanceService.class,"getIloOverViewPerformanceOtherDataList");
			 sql=String.format(sqlString,"'HARDWARE_HP'",where);
		}else{
			 sqlString=SqlManager.getSql(PerformanceService.class,"getIloOverViewPerformanceDataList");
			 sql=String.format(sqlString,"'HARDWARE_HP'",whereString,where);
		}
		
		/*String whereString = " attr.value ='" + bizName + "' ";
		String where = "";
		if (queryIP != null) {
			where = "where a.caption like '%" + queryIP + "%'";
		}
		String sqlString = SqlManager.getSql(PerformanceService.class,
				"getIloOverViewPerformanceDataList");
		String sql = String.format(sqlString, "'HARDWARE_HP'", whereString,
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
