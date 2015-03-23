package com.hp.avmon.config.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.utils.DBUtils;

@Service
public class ThresholdService {
    
	private static final Log logger = LogFactory.getLog(ThresholdService.class);
    
    private JdbcTemplate jdbc;
    
    public ThresholdService(){
        jdbc = SpringContextHolder.getBean("jdbcTemplate");
    }
    
    /**
     * 更新
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map updateThreshold(Map dataMap) {
		String updSql = String.format("update TD_AVMON_KPI_THRESHOLD_VAL " +
				"set " +
				"MO='%s', " +
				"MONITOR_INSTANCE='%s', " +
				"KPI='%s', " +
				"MAX_THRESHOLD='%s', " +
				"MIN_THRESHOLD='%s', " +
				"THRESHOLD='%s', " +
				"CHECK_OPTR='%s', " +
				"ALARM_LEVEL='%s', " +
				"ACCUMULATE_COUNT='%s', " +
				"START_DATE='%s', " +
				"END_DATE='%s', " +
				"CONTENT='%s' " +
				" where ID = '%s'", 
				dataMap.get("MO"),
				dataMap.get("MONITOR_INSTANCE"),
				dataMap.get("KPI"),
				dataMap.get("MAX_THRESHOLD"),
				dataMap.get("MIN_THRESHOLD"),
				dataMap.get("THRESHOLD"),
				dataMap.get("CHECK_OPTR"),
				dataMap.get("ALARM_LEVEL"),
				dataMap.get("ACCUMULATE_COUNT"),
				dataMap.get("START_DATE"),
				dataMap.get("END_DATE"),
				dataMap.get("CONTENT"),
				dataMap.get("ID")
				);
		logger.debug("updateThreshold:"+updSql);
		jdbc.execute(updSql);
		
		String querySql = String.format("select * from TD_AVMON_KPI_THRESHOLD_VAL where ID = '%s' ", dataMap.get("ID"));
		List<Map> list = jdbc.queryForList(querySql);
		Map result = new HashMap();
		if (list != null && list.size() != 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}

		return result;
	}
	
    /**
     * 插入视图
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map insertThreshold(Map dataMap) {
		String insSql = String.format("insert into TD_AVMON_KPI_THRESHOLD_VAL" +
				"(ID, MO, MONITOR_INSTANCE, KPI, MAX_THRESHOLD, MIN_THRESHOLD, THRESHOLD, CHECK_OPTR, ALARM_LEVEL, ACCUMULATE_COUNT, START_DATE, END_DATE, CONTENT)" +
				" values('%s','%s','%s','%s', '%s', '%s','%s','%s','%s','%s','%s','%s','%s')", 
				dataMap.get("ID"),
				dataMap.get("MO"),
				dataMap.get("MONITOR_INSTANCE"),
				dataMap.get("KPI"),
				dataMap.get("MAX_THRESHOLD"),
				dataMap.get("MIN_THRESHOLD"),
				dataMap.get("THRESHOLD"),
				dataMap.get("CHECK_OPTR"),
				dataMap.get("ALARM_LEVEL"),
				dataMap.get("ACCUMULATE_COUNT"),
				dataMap.get("START_DATE"),
				dataMap.get("END_DATE"),
				dataMap.get("CONTENT")
				);
		logger.debug("insertThreshold:"+insSql);
		jdbc.execute(insSql);
		
		String querySql = String.format("select * from TD_AVMON_KPI_THRESHOLD_VAL where ID = '%s'", dataMap.get("ID"));
		List<Map> list = jdbc.queryForList(querySql);
		Map result = new HashMap();
		if (list != null && list.size() != 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}

		return result;
	}
	
    /**
     * 取得活动告警
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getThresholdList(
            HttpServletRequest request) throws Exception {
        String limit = request.getParameter("limit");
        String start = request.getParameter("start");
    	String sql = "select * from TD_AVMON_KPI_THRESHOLD_VAL ";
    	
        String querySql = generatPageSql(sql, limit, start);		
    	logger.debug("getThresholdList:"+querySql);
    	
		List<Map> list = jdbc.queryForList(querySql);
        
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("ThresholdTotal", list.size());
        map.put("ThresholdData", list);
        return map;
    }
    
    private String generatPageSql(String sql, String limit, String start){
		Integer limitL = Integer.valueOf(limit);
		Integer startL = Integer.valueOf(start);
		
		//构造oracle数据库的分页语句
		/*StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(sql);
		paginationSQL.append(" ) temp where ROWNUM <= " + (startL + limitL));//limitL*startL);
		paginationSQL.append(" ) WHERE num > " + (startL));
		return paginationSQL.toString();*/
		return DBUtils.pagination(sql, startL, limitL);
	}
    
    /**
     * 删除视图
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map deleteThreshold(String id) {
		String insSql = String.format("delete from TD_AVMON_KPI_THRESHOLD_VAL where  ID = '%s'", id);
		logger.debug("deleteThreshold:"+insSql);
		jdbc.execute(insSql);
		
		String querySql = String.format("select * from TD_AVMON_KPI_THRESHOLD_VAL where  ID = '%s'", id);
		List<Map> list = jdbc.queryForList(querySql);
		Map result = new HashMap();
		if (list == null || list.size() == 0) {
			result.put("success", true);
		} else {
			result.put("success", false);
		}

		return result;
	}
}
