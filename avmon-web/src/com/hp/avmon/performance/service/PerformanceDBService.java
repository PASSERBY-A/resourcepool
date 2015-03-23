package com.hp.avmon.performance.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.performance.store.KpiDataStore;
import com.hp.avmon.utils.Constants;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmonserver.api.AvmonServer;
import com.hp.avmonserver.entity.KpiEvent;

@Service
public class PerformanceDBService {

	private static final Log logger = LogFactory.getLog(PerformanceDBService.class);

	@Autowired
	private AvmonServer avmonServer;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	@Qualifier("kpiDataStore")
	private KpiDataStore kpiDataStore;

	private static List<KpiEvent> kpiValueList = new ArrayList<KpiEvent>();
	private static Map kpiNameCache = new HashMap();
	private static Map kpiTypeCache = new HashMap();

	public PerformanceDBService() {

	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getBasicInfoForDBList(HttpServletRequest request)
			throws Exception {
		String moId = request.getParameter("moId");
		/**
		Locale locale = request.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		
		String dbName = bundle.getString("dbName"); 
		String dbVer = bundle.getString("dbVer"); 
		String dbOpt = bundle.getString("dbOpt"); 
		String dbBit = bundle.getString("dbBit"); 
		String dbFile = bundle.getString("dbFile"); 		

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map m = new HashMap();
		m.put("KEY", dbName);
		m.put("VAL", kpiDataStore.getCurrentKpiStringValue(moId, "2102016001"));
		list.add(m);
		m = new HashMap();
		m.put("KEY", dbVer);
		m.put("VAL", kpiDataStore.getCurrentKpiStringValue(moId, "2102016002"));
		list.add(m);

		m = new HashMap();
		m.put("KEY", dbOpt);
		m.put("VAL", kpiDataStore.getCurrentKpiStringValue(moId, "2102016003"));
		list.add(m);

		m = new HashMap();
		m.put("KEY", dbBit);
		m.put("VAL", kpiDataStore.getCurrentKpiStringValue(moId, "2102016004"));
		list.add(m);

		m = new HashMap();
		m.put("KEY", dbFile);
		m.put("VAL", kpiDataStore.getCurrentKpiStringValue(moId, "2102016005"));
		list.add(m);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("basicTotal", list.size());
		map.put("basicData", list);

		return map;
		*/
		Map m = new HashMap();
		m.put("dbName", kpiDataStore.getCurrentKpiStringValue(moId, "2102016001"));
		m.put("dbVer", kpiDataStore.getCurrentKpiStringValue(moId, "2102016002"));
		m.put("dbOpt", kpiDataStore.getCurrentKpiStringValue(moId, "2102016003"));
		m.put("dbBit", kpiDataStore.getCurrentKpiStringValue(moId, "2102016004"));
		m.put("dbFile", kpiDataStore.getCurrentKpiStringValue(moId, "2102016005"));
		return m;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getDBStatusInfoData(HttpServletRequest request) throws Exception {
		String moId = request.getParameter("moId");
		/**
		Locale locale = request.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		String dbStatus = bundle.getString("dbStatus");
		String dbLisStatus = bundle.getString("dbLisStatus");
		String dbErrNo = bundle.getString("dbErrNo");
		
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map m = new HashMap();
		m.put("KEY", dbStatus);
		m.put("VAL", kpiDataStore.getCurrentKpiStringValue(moId, "2101020001"));
		list.add(m);
		m = new HashMap();
		m.put("KEY", dbLisStatus);
		m.put("VAL", kpiDataStore.getCurrentKpiStringValue(moId, "2102023001"));
		list.add(m);

		m = new HashMap();
		m.put("KEY", dbErrNo);
		m.put("VAL", kpiDataStore.getCurrentKpiStringValue(moId, "2102024002"));
		list.add(m);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("basicTotal", list.size());
		map.put("basicData", list);

		return map;
		*/
		Map m = new HashMap();
		m.put("dbStatus", kpiDataStore.getCurrentKpiStringValue(moId, "2101020001"));
		m.put("dbLisStatus", kpiDataStore.getCurrentKpiStringValue(moId, "2102023001"));
		m.put("dbErrNo", kpiDataStore.getCurrentKpiStringValue(moId, "2102024002"));
		return m;
	}
	
	public Map getDBSgaInfoData(HttpServletRequest request) throws Exception {
		String moId = request.getParameter("moId");
		Map m = new HashMap();
		m.put("dbFixedSize", kpiDataStore.getCurrentKpiStringValue(moId, "2102016006"));
		m.put("dbVariableSize", kpiDataStore.getCurrentKpiStringValue(moId, "2102016007"));
		m.put("dbDatabaseBuffers", kpiDataStore.getCurrentKpiStringValue(moId, "2102016008"));
		m.put("dbRedoBuffers", kpiDataStore.getCurrentKpiStringValue(moId, "2102016009"));
		return m;
	}

	public Map getDBHitPercentInfoData(HttpServletRequest request) throws Exception {
		String moId = request.getParameter("moId");
		Map m = new HashMap();
		m.put("dbLibcacheHitPercent", kpiDataStore.getCurrentKpiStringValue(moId, "2101001002"));
		m.put("dbGlobalHitPercent", kpiDataStore.getCurrentKpiStringValue(moId, "2101001005"));
		return m;
	}
	
	public Map getDBSessionsPercentInfoData(HttpServletRequest request) throws Exception {
		String moId = request.getParameter("moId");
		
		float totalSessionCount = kpiDataStore.getCurrentKpiNumValue(moId, "2101008001");
		float waitSessionCount = kpiDataStore.getCurrentKpiNumValue(moId, "2101008002");
		float activeSessionCount = kpiDataStore.getCurrentKpiNumValue(moId, "2101008003");
		float waitPer = (totalSessionCount!=0?(waitSessionCount/totalSessionCount):0)*100;
		float activePer = (totalSessionCount!=0?(activeSessionCount/totalSessionCount):0)*100;
		BigDecimal waitPerb = new BigDecimal(waitPer);  
		float waitPerf = waitPerb.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue();  
		BigDecimal activePerb = new BigDecimal(activePer);  
		float activePerf = activePerb.setScale(2,BigDecimal.ROUND_HALF_UP).floatValue(); 
		Map m = new HashMap();
		m.put("dbWaitSessionsPer", waitPerf);
		m.put("dbActiveSessionsPer", activePerf);
		return m;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getEventInfoForDBList(HttpServletRequest request)
			throws Exception {
		String moId = request.getParameter("moId");
		Map map=new HashMap();
        
        if(moId!=null){
            String sql=SqlManager.getSql(this, "db_event_list");
            sql=sql.replaceAll("\\{MO_ID\\}", moId);
            List list=jdbcTemplate.queryForList(sql);
            map.put("root",list);
            map.put("total", list.size());
        }
        else{
        	map.put("root",null);
            map.put("total", 0);
        }
        return map;
	}
	
	public Map getDBHostUsageInfoData(HttpServletRequest request) throws Exception {
		String moId = request.getParameter("moId");
		Map m = new HashMap();
		m.put("dbHostOsLoad", kpiDataStore.getCurrentKpiStringValue(moId, "2102025004"));
		m.put("dbHostCPUUsagePer", kpiDataStore.getCurrentKpiStringValue(moId, "2102025005"));
		m.put("dbHostCPUUtil", kpiDataStore.getCurrentKpiStringValue(moId, "2102025006"));
		m.put("dbHostNetworkBytesPer", kpiDataStore.getCurrentKpiStringValue(moId, "2102025007"));
		return m;
	}
	
	public Map getDBReadWriteInfoData(HttpServletRequest request) throws Exception {
		String moId = request.getParameter("moId");
		Map m = new HashMap();
		m.put("dbTablespacePhyrds", kpiDataStore.getCurrentKpiStringValue(moId, "2101004001"));
		m.put("dbTablespacePhywrts", kpiDataStore.getCurrentKpiStringValue(moId, "2101004002"));
		return m;
	}
	
	public Map getDBCommitRollbackInfoData(HttpServletRequest request) throws Exception {
		String moId = request.getParameter("moId");
		Map m = new HashMap();
		m.put("dbCommitPercent", kpiDataStore.getCurrentKpiStringValue(moId, "2101009004"));
		m.put("dbRollbackPercent", kpiDataStore.getCurrentKpiStringValue(moId, "2101009005"));
		return m;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getTablspaceInfoForDB(HttpServletRequest request)
			throws Exception {
		String moId = request.getParameter("moId");
		Map map=new HashMap();
        
        if(moId!=null){
            String sql=SqlManager.getSql(this, "db_tablespace_list");
            sql=sql.replaceAll("\\{MO_ID\\}", moId);
            List list=jdbcTemplate.queryForList(sql);
            map.put("root",list);
            map.put("total", list.size());
        }
        else{
        	map.put("root",null);
            map.put("total", 0);
        }
        return map;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getPhysicalInfoForDB(HttpServletRequest request)
			throws Exception {
		String moId = request.getParameter("moId");
		Map m = new HashMap();
		m.put("dbPhysicalReadIOPS", kpiDataStore.getCurrentKpiStringValue(moId, "2102025001"));
		m.put("dbPhysicalWriteIOPS", kpiDataStore.getCurrentKpiStringValue(moId, "2102025002"));
		m.put("dbPhysicalRedoIOPS", kpiDataStore.getCurrentKpiStringValue(moId, "2102025003"));
		return m;
	}
	
//	@SuppressWarnings({ "unchecked" })
//	public List<Map<String, Object>> getDBKpiChartData(String ampInstId,
//			String kpiCode, String moId, String startTime, String endTime,String grainsize) {
//		List<Map<String, Object>> kpiValueList = null;
//		try {
//			
//			List<Map<String, Object>> kpiInstanceList = getKpiInstance(ampInstId,kpiCode,moId,startTime,endTime,grainsize);
//			
//			String ins1 = "";
//			String ins2 = "";
//			String differ = "";
//			String tableName = "";
//			String valueColum = "num_value";
//			if("1".equals(grainsize)){
//				valueColum = "num_value";
//				tableName = "tf_avmon_kpi_value";
//			}else if("2".equals(grainsize)){
//				valueColum = "avg_value";
//				tableName = "tf_avmon_kpi_value_hourly";
//			}else if("3".equals(grainsize)){
//				valueColum = "avg_value";
//				tableName = "tf_avmon_kpi_value_daily";
//			}else{
//				tableName = "tf_avmon_kpi_value";
//			}
//			String snmpDiffer = "amp_inst_id='%s' and kpi_code = '%s' ";
//			int dbType = DBUtils.getDbType();
//			if (Constants.DB_POSTGRESQL==dbType) {
//				 differ = " as a ";
//			}else{
//				differ = " ";
//			}
//			
//			for(int i=0;i<kpiInstanceList.size();i++){
//				ins1 = ins1 + "sum(instance" + i + ") as \"instance" + i + "\",";
//				ins2 = ins2 + "case when instance='" + kpiInstanceList.get(i).get("instance").toString() + "' then "+valueColum+" else 0 end as instance" + i + ",";
//			}
//			String kpiValueSql = 
//				"select " +  
//				ins1 +
//				"to_char(time,'yyyy-mm-dd HH24:mi') as \"time\" "+
//				"from( " +
//				"select " + 
//				ins2 +
//				"kpi_time as time " +
//				// modify by mark start 2013-9-28
//				"from  " + tableName + 
//				// modify by mark end 2013-9-28
//				" where " +
//				// modify by mark start 2014-3-12
//				snmpDiffer +
//				// modify by mark end 2014-3-12
//				"and mo_id='%s' "+
//				"and kpi_time between to_date('%s','YYYY-MM-DD HH24:MI:SS') and  to_date('%s','YYYY-MM-DD HH24:MI:SS' )) a " +
//				differ +
//				"group by time " +
//				"order by time";
//			kpiValueSql = String.format(kpiValueSql,ampInstId,kpiCode,moId,startTime,endTime);	
//			kpiValueList = jdbcTemplate.queryForList(kpiValueSql);
//		} catch (Exception e) {
//			e.printStackTrace();
//			logger.error(this.getClass().getName()+" getDBKpiChartData",e);
//		}
//		logger.debug("kpiValueList----------------------------------------------------"+kpiValueList);
//		return kpiValueList;	
//	}
	
	public List<Map<String, Object>> getKpiInstance(String ampInstId,
			String kpiCode, String moId, String startTime, String endTime,String grainsize) {
		String baseSql = StringUtil.EMPTY;
		String getKpiInstanceSql = StringUtil.EMPTY;
		String type = StringUtil.EMPTY;
		List<Map<String, Object>> kpiInstanceList = new ArrayList<Map<String,Object>>();
		try {
			if("1".equals(grainsize))
			{
				baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
						"where amp_inst_id='%s' and kpi_code = '%s' " + 
						"and mo_id='%s' " + 
						"and kpi_time  between to_date('%s','YYYY-MM-DD HH24:MI:SS') and to_date('%s','YYYY-MM-DD HH24:MI:SS') ";
			}
			else if("2".equals(grainsize))
			{
				baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_hourly " + 
						"where amp_inst_id='%s' and kpi_code = '%s' " + 
						"and mo_id='%s' " + 
						"and kpi_time  between to_date('%s','YYYY-MM-DD HH24:MI:SS') and to_date('%s','YYYY-MM-DD HH24:MI:SS') ";
			}
			else if("3".equals(grainsize))
			{
				baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value_daily " + 
						"where amp_inst_id='%s' and kpi_code = '%s' " + 
						"and mo_id='%s' " + 
						"and kpi_time  between to_date('%s','YYYY-MM-DD HH24:MI:SS') and to_date('%s','YYYY-MM-DD HH24:MI:SS') ";
			}else{
				baseSql = "select distinct instance as \"instance\" from tf_avmon_kpi_value " + 
						"where amp_inst_id='%s' and kpi_code = '%s' " + 
						"and mo_id='%s' " + 
						"and kpi_time  between to_date('%s','YYYY-MM-DD HH24:MI:SS') and to_date('%s','YYYY-MM-DD HH24:MI:SS') ";
			}
			
			getKpiInstanceSql = String.format(baseSql,ampInstId,kpiCode,moId,startTime,endTime);
			kpiInstanceList = jdbcTemplate.queryForList(getKpiInstanceSql);
			return kpiInstanceList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getKpiInstance",e);
		}
		return kpiInstanceList;
	}
	
	@SuppressWarnings({ "unchecked" })
	public List<Map<String, Object>> getDBKpiChartData(String ampInstId,
			String kpiCode, String moId, String startTime, String endTime,String grainsize) {
		List<Map<String, Object>> kpiValueList = null;
		try {
			
			List<Map<String, Object>> kpiInstanceList = getKpiInstance(ampInstId,kpiCode,moId,startTime,endTime,grainsize);
			
			String ins1 = "";
			String ins2 = "";
			String differ = "";
			String tableName = "";
			String valueColum = "num_value";
			if("1".equals(grainsize)){
				valueColum = "num_value";
				tableName = "tf_avmon_kpi_value";
			}else if("2".equals(grainsize)){
				valueColum = "avg_value";
				tableName = "tf_avmon_kpi_value_hourly";
			}else if("3".equals(grainsize)){
				valueColum = "avg_value";
				tableName = "tf_avmon_kpi_value_daily";
			}else{
				tableName = "tf_avmon_kpi_value";
			}
			String snmpDiffer = "amp_inst_id='%s' and kpi_code = '%s' ";
			int dbType = DBUtils.getDbType();
			if (Constants.DB_POSTGRESQL==dbType) {
				 differ = " as a ";
			}else{
				differ = " a ";
			}
			
			for(int i=0;i<kpiInstanceList.size();i++){
				String kpiInstance = kpiInstanceList.get(i).get("instance").toString();
				
				ins1 = ins1 + "sum(\"instance" + i + "\") as \"instance" + i + "\",";
				
				ins2 = ins2 + "case when instance='" + kpiInstance + 
						"' then "+valueColum+" else 0 end as \"instance"+i+"\",";
			}
			String kpiValueSql = 
				"select " +  
				ins1 +
				"to_char(time,'yyyy-mm-dd HH24:mi') as \"time\" "+
				"from( " +
				"select " + 
				ins2 +
				"kpi_time as time " +
				// modify by mark start 2013-9-28
				"from  " + tableName + 
				// modify by mark end 2013-9-28
				" where " +
				// modify by mark start 2014-3-12
				snmpDiffer +
				// modify by mark end 2014-3-12
				"and mo_id='%s' "+
				"and kpi_time between to_date('%s','YYYY-MM-DD HH24:MI:SS') and  to_date('%s','YYYY-MM-DD HH24:MI:SS' )) " +
				differ +
				"group by time " +
				"order by time";
			kpiValueSql = String.format(kpiValueSql,ampInstId,kpiCode,moId,startTime,endTime);	
			kpiValueList = jdbcTemplate.queryForList(kpiValueSql);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getDBKpiChartData",e);
		}
		logger.debug("kpiValueList----------------------------------------------------"+kpiValueList);
		return kpiValueList;	
	}
}
