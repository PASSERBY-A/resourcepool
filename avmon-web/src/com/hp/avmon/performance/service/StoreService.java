package com.hp.avmon.performance.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.performance.store.KpiDataStore;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmonserver.api.AvmonServer;
import com.hp.avmonserver.entity.KpiEvent;

@Service
public class StoreService {
    
    private static final Log logger = LogFactory.getLog(StoreService.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private AvmonServer avmonServer;
    
    
    @Autowired
    private KpiDataStore kpiDataStore;
    
	/**
	 * 取得常规信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getStoreBasicInfo(String moId) {
        
        Map map=new HashMap();
        //get ci
    	String tempSql = SqlManager.getSql(StoreService.class, "store_BasicInfo");
        String sql = String.format(tempSql, moId);
    	
        List list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0){
            Map m = (Map) list.get(0);
            map.putAll(m);
        }

        map.put("RUNRATE", getKpiValue(moId,"wk_cfg_1035","cpu_speed","/") );
        map.put("RUNTIME", getKpiValue(moId,"wk_cfg_1035","live_time","/") );
        map.put("USERCOUNT", 0);
        map.put("ALARMCOUNT", 0);

        return map;
    }
    
    private Object getKpiValue(String moId, String ampInstId, String kpiCode, String instance) {
        KpiEvent event = kpiDataStore.getCurrentKpiEvent(moId,ampInstId,kpiCode,instance);
        if(event!=null){
            return event.getStrValue();
        }
        else{
            return "";
        }
    }
    
	/**
	 * 取得【最新告警】列表
	 * 
	 * @param moId
	 * @return List<Map>
	 */
    @SuppressWarnings("rawtypes")
	public Object getStoreNewAlarm(String moId){
    	String tempSql = SqlManager.getSql(StoreService.class, "store_NewAlarm");
        String sql = String.format(tempSql, moId);
        List list = jdbcTemplate.queryForList(sql);
        return list;
    }
    
	/**
	 * 取得【网口信息】列表
	 * 
	 * @param moId
	 * @return List<Map>
	 */
    @SuppressWarnings("rawtypes")
	public Object getStoreNetworks(String moId) {
    	String tempSql = SqlManager.getSql(StoreService.class, "store_Network");
        String sql = String.format(tempSql, moId, moId, moId);
        List list = jdbcTemplate.queryForList(sql);
        return list;
    }
    
	/**
	 * 取得【组信息】列表
	 * 
	 * @param moId
	 * @return List<Map>
	 */
    @SuppressWarnings("rawtypes")
	public Object getStoreGroupInfo(String moId) {
    	String tempSql = SqlManager.getSql(StoreService.class, "store_GroupInfo");
        String sql = String.format(tempSql, moId, moId, moId);
        List list = jdbcTemplate.queryForList(sql);
        return list;
    }
    
	/**
	 * 取得【盘信息】列表
	 * 
	 * @param moId
	 * @return List<Map>
	 */
    @SuppressWarnings("rawtypes")
	public Object getStoreDiskInfo(String moId) {
    	String tempSql = SqlManager.getSql(StoreService.class, "store_DiskInfo");
        String sql = String.format(tempSql, moId, moId, moId);
        List list = jdbcTemplate.queryForList(sql);
        return list;
    }
    
	/**
	 * GROUP TAB的cpu view信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getGroupView(String moId){
    	String tempSql = SqlManager.getSql(StoreService.class, "store_GroupView");
    	String sql = String.format(tempSql, moId, moId);
        logger.info("getCpuViewList SQL:" + sql);
		List<Map> kpiList = jdbcTemplate.queryForList(sql);
        return kpiList;
    }
    
	/**
	 * GROUP TAB的cpu view信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getPortView(String moId){
    	String tempSql = SqlManager.getSql(StoreService.class, "store_PortView");
    	String sql = String.format(tempSql, moId, moId, moId);
        logger.info("getCpuViewList SQL:" + sql);
		List<Map> kpiList = jdbcTemplate.queryForList(sql);
        return kpiList;
    }
    
	/**
	 * GROUP TAB的cpu view信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getDiskView(String moId){
    	String tempSql = SqlManager.getSql(StoreService.class, "store_DiskView");
    	String sql = String.format(tempSql, moId, moId);
        logger.info("getCpuViewList SQL:" + sql);
		List<Map> kpiList = jdbcTemplate.queryForList(sql);
        return kpiList;
    }
    
	/**
	 * CPU 曲线信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getStoreCpuInfo(String moId) {

    	int dataPoints = 5;
    	String tempSql1 = "SELECT  " +
    			"'DKA' as \"type\", " +
    			"to_char(TIME, 'HH24:MI') as \"time\", " +
    			"max(num_value) as \"value\" " +
    			"FROM tf_avmon_kpi_value " +
    			"WHERE mo_id = '%s' AND KPI_CODE = '%s' AND AMP_INST_ID = '%s' " +
    			"and substr(PATH,  -3) = 'DKA' " +
    			"GROUP BY TIME";
    	
    	String tempSql2 = "SELECT  " +
    			"'FIBRE' as \"type\", " +
    			"to_char(TIME, 'HH24:MI') as \"time\", " +
    			"max(num_value) as \"value\" " +
    			"FROM tf_avmon_kpi_value " +
    			"WHERE mo_id = '%s' AND KPI_CODE = '%s' AND AMP_INST_ID = '%s' " +
    			"and substr(PATH, -5) = 'FIBRE' " +
    			"GROUP BY TIME ";
    
        String sql1 = String.format(tempSql1, moId, "xp_cpu_avg", "wk_xpcpu_1001");
        String sql2 = String.format(tempSql2, moId, "xp_cpu_avg", "wk_xpcpu_1001");
        
        List<Map> list1 = jdbcTemplate.queryForList(sql1);
        List<Map> list2 = jdbcTemplate.queryForList(sql2);
        
        List line1 = new ArrayList();
        if (list1 != null && list1.size() >= dataPoints) {
        	int stepVal = list1.size() - dataPoints;
        	for (int i = stepVal; i < list1.size(); i++) {
        		Map result = new HashMap();
        		
        		result.put("time", list1.get(i).get("time"));
        		result.put("usage1", list1.get(i).get("value"));
        		result.put("usage2", list2.get(i).get("value"));
        		
        		line1.add(result);
        	}
        }
 
        Map map = new HashMap();
        map.put("history", line1);
        
        return map;
    }
    
    /**
	 * iops曲线图
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getIopsInfo(String moId) {
        String tempSql = SqlManager.getSql(StoreService.class, "store_IopsInfo");
        String sql = String.format(tempSql, moId, moId, moId);
        
        List<Map> result = jdbcTemplate.queryForList(sql);
        
    	return result;
    }
}
