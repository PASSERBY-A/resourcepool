package com.hp.avmon.timer;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.utils.PropertyUtils;

public class AutoDeleteKpiData {   
	private static final Logger log = LoggerFactory.getLogger(AutoDeleteKpiData.class);
    
    public void run() {

    	// 清除tf_avmon_kpi_value2个月前的数据
    	// deleteKpiData();
    	// 清除tf_avmon_kpi_value_current2天以前的数据
//    	deleteKpiCurrentData();
    }
    
    /**
     * 清除tf_avmon_kpi_value2个月前的数据
     * 
     */
    public void deleteKpiData() {
    	int days = MyFunc.nullToInteger(PropertyUtils.getProperty("saveKpiData.days"));
    	
    	if (days != 0) {
    		Calendar calendar = Calendar.getInstance();
        	calendar.setTime(new Date());
        	// 保留最近两个月数据
        	calendar.add(Calendar.DATE, -days);
        	Date tmpDate = calendar.getTime();
        	String beforeDate = MyFunc.dateToString(tmpDate, "yyyy-MM-dd");
        	// 获取JDBC
            JdbcTemplate jdbc = (JdbcTemplate) SpringContextHolder.getBean("jdbcTemplate");
            
            // 取得所有的表分区
            String partitionsSql = "select TABLE_NAME,PARTITION_NAME,TABLESPACE_NAME from user_tab_partitions where table_name='TF_AVMON_KPI_VALUE'";
    		@SuppressWarnings("unchecked")
			List<Map<String, Object>> partitionslist = jdbc.queryForList(partitionsSql);
    		if (partitionslist != null && partitionslist.size() != 0) {
    			StringBuilder dateSql = new StringBuilder();
    			for (Map<String, Object> temp : partitionslist) {
    				dateSql = new StringBuilder();
    				// 取得每个表分区的KPI_TIME
    				dateSql.append("select to_char(kpi_time, 'yyyy-mm-dd') as kpi_time from TF_AVMON_KPI_VALUE partition (");
    				String partitionNm = MyFunc.nullToString(temp.get("PARTITION_NAME"));
    				dateSql.append(partitionNm);
    				dateSql.append(") where rownum <=1 ");
//    				dateSql.append(" and to_char(kpi_time, 'yyyy-mm-dd') = '" + beforeDate + "'");
    				
    				@SuppressWarnings("unchecked")
					List<Map<String, Object>> datelist = jdbc.queryForList(dateSql.toString());
    				log.info("deleteKpiData sql1:" + dateSql.toString());
    				
    				if (datelist != null && datelist.size() != 0) {
    					String queryDate = MyFunc.nullToString(datelist.get(0).get("KPI_TIME"));
    					if (beforeDate.equals(queryDate)) {
    						log.info("deleteKpiData date:" + queryDate);
    						
    						// 删除表分区
    			            String sql = "alter table TF_AVMON_KPI_VALUE drop partition " + partitionNm + " update global indexes";
    			            
    			            jdbc.execute(sql);
    			            log.info("deleteKpiData sql2:" + sql);
    			            break;
    					}
    				}
     			}
    		}
    	}
    }
	
    /**
     * 清除tf_avmon_kpi_value_current2天以前的数据
     * 
     */
    public void deleteKpiCurrentData() {
    	int days = MyFunc.nullToInteger(PropertyUtils.getProperty("saveKpiCurrentData.days"));
    	
    	if (days != 0) {
    		Calendar calendar = Calendar.getInstance();
        	calendar.setTime(new Date());
        	// 保留最近两个月数据
        	calendar.add(Calendar.DATE, -days);
        	Date tmpDate = calendar.getTime();
        	String beforeDate = MyFunc.dateToString(tmpDate, "yyyy-MM-dd");
        	// 获取JDBC
            JdbcTemplate jdbc = (JdbcTemplate) SpringContextHolder.getBean("jdbcTemplate");
            
            // 取得所有的表分区
            String partitionsSql = "select TABLE_NAME,PARTITION_NAME,TABLESPACE_NAME from user_tab_partitions where table_name='TF_AVMON_KPI_VALUE_CURRENT'";
    		@SuppressWarnings("unchecked")
			List<Map<String, Object>> partitionslist = jdbc.queryForList(partitionsSql);
    		if (partitionslist != null && partitionslist.size() != 0) {
    			StringBuilder dateSql = new StringBuilder();
    			for (Map<String, Object> temp : partitionslist) {
    				dateSql = new StringBuilder();
    				// 取得每个表分区的KPI_TIME
    				dateSql.append("select to_char(kpi_time, 'yyyy-mm-dd') as kpi_time from tf_avmon_kpi_value_current partition (");
    				String partitionNm = MyFunc.nullToString(temp.get("PARTITION_NAME"));
    				dateSql.append(partitionNm);
    				dateSql.append(") where rownum <=1 ");
//    				dateSql.append(" and to_char(kpi_time, 'yyyy-mm-dd') = '" + beforeDate + "'");
    				
    				@SuppressWarnings("unchecked")
					List<Map<String, Object>> datelist = jdbc.queryForList(dateSql.toString());
    				log.info("deleteKpiCurrentData sql1:" + dateSql.toString());
    				
    				if (datelist != null && datelist.size() != 0) {
    					String queryDate = MyFunc.nullToString(datelist.get(0).get("KPI_TIME"));
    					if (beforeDate.equals(queryDate)) {
    						
    						log.info("deleteKpiCurrentData date:" + queryDate);
    						// 删除表分区
    			            String sql = "alter table tf_avmon_kpi_value_current drop partition " + partitionNm + " update global indexes";
    			            
    			            jdbc.execute(sql);
    			            log.info("deleteKpiCurrentData sql2:" + sql);
    			            break;
    					}
    				}
     			}
    		}
    	}
    }
} 