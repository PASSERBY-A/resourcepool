package com.hp.avmon.performance.store;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmonserver.entity.KpiEvent;

@Service
public class KpiDataStoreOracle extends KpiDataStore{
	
    private static final Log logger = LogFactory.getLog(KpiDataStoreOracle.class);
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 构造函数
     */
    public KpiDataStoreOracle() {
    	// 加载连接对象
    	if (jdbcTemplate == null) {
        	jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
    	}
    }


    

    /**
     * 从current表中获取指定kpi的当前值
     * @param moId
     * @param ampInstId
     * @param kpiCode
     * @param instance
     * @return
     */
    @SuppressWarnings("unchecked")
	public KpiEvent getCurrentKpiEvent(String moId, String ampInstId, String kpiCode, String instance) {
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ");
        sb.append(" t1.* ");
    	sb.append(" from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) t1 ");
    	
    	sb.append(" where 1=1 ");
    	if (!StringUtils.isEmpty(moId)) {
        	sb.append(" and t1.MO_ID = '" + moId + "'");
    	}
    	if (!StringUtils.isEmpty(ampInstId)) {
        	sb.append(" and t1.AMP_INST_ID = '" + ampInstId + "'");
    	}
    	if (!StringUtils.isEmpty(kpiCode)) {
        	sb.append(" and t1.KPI_CODE = '" + kpiCode + "'");
    	}
    	if (!StringUtils.isEmpty(instance)) {
        	sb.append(" and t1.INSTANCE = '" + instance + "'");
    	}
    	logger.debug("getCurrentKpiEvent SQL:"+sb.toString());
    	
    	List<Map<String, Object>> querylist = jdbcTemplate.queryForList(sb.toString());
//    	Map<String, Object> temp = jdbcTemplate.queryForMap(sb.toString());
//    	System.out.println(temp);
    	if (querylist != null) {
    		for(Map<String, Object> temp : querylist) {
        		KpiEvent kpi = new KpiEvent();
        		
    			kpi.setAmpInstId(ampInstId);
    			kpi.setKpiCode(kpiCode);
    			kpi.setMoId(moId);
    			kpi.setValue(nullToString(temp.get("VALUE")));
    			kpi.setInstance(instance);
    			kpi.setAgentId(nullToString(temp.get("AGENT_ID")));
    			kpi.setKpiTime(stringToDate(nullToString(temp.get("KPI_TIME")), YYYY_MM_DD_HH_MM_SS));
    			kpi.setNumValue(nullToFloat(temp.get("NUM_VALUE")));
    			kpi.setStrValue(nullToString(temp.get("STR_VALUE")));
    			kpi.setThresholdLevel(nullToInteger(temp.get("THRESHOLD_LEVEL")));
        		
        		return kpi;
    		}
    	}
    	
        return null;
    }

    /**
     * 从current表中获取某个指定KPI所有实例的值
     * @param moId
     * @param ampInstId
     * @param kpiCode
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<KpiEvent> getKpiList(String moId, String ampInstId, String kpiCode) {
    	List<KpiEvent> resultList = new ArrayList<KpiEvent>();
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ");
    	sb.append(" t1.* ");
    	sb.append("  from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) t1 ");
    	
    	sb.append(" where 1=1 ");
    	if (!StringUtils.isEmpty(moId)) {
        	sb.append(" and t1.MO_ID = '" + moId + "'");
    	}
    	if (!StringUtils.isEmpty(ampInstId)) {
        	sb.append(" and t1.AMP_INST_ID = '" + ampInstId + "'");
    	}
    	if (!StringUtils.isEmpty(kpiCode)) {
        	sb.append(" and t1.KPI_CODE = '" + kpiCode + "'");
    	}
    	logger.debug("getKpiList SQL:"+sb.toString());
    	
		List<Map<String, Object>> querylist = jdbcTemplate.queryForList(sb.toString());
		if (querylist != null && querylist.size() != 0) {
			// 将查询结果转换为返回类型
			for(Map<String, Object> temp : querylist) {
				KpiEvent kpi = new KpiEvent();
				kpi.setAmpInstId(ampInstId);
				kpi.setKpiCode(kpiCode);
				kpi.setMoId(moId);
				kpi.setValue(nullToString(temp.get("VALUE")));
				kpi.setInstance(nullToString(temp.get("INSTANCE")));
				kpi.setAgentId(nullToString(temp.get("AGENT_ID")));
				kpi.setKpiTime(stringToDate(nullToString(temp.get("KPI_TIME")), YYYY_MM_DD_HH_MM_SS));
				kpi.setNumValue(nullToFloat(temp.get("NUM_VALUE")));
				kpi.setStrValue(nullToString(temp.get("STR_VALUE")));
				kpi.setThresholdLevel(nullToInteger(temp.get("THRESHOLD_LEVEL")));
				
				resultList.add(kpi);				
			}
		}
    	
		//logger.debug("getKpiList size:" + resultList.size());
    	
        return resultList;
    }
    
    /**
     * 从current表中获取某个对象当前KPI值列表
     * @param moId
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<KpiEvent> getKpiList(String moId) {

    	List<KpiEvent> resultList = new ArrayList<KpiEvent>();
    	
    	StringBuffer sb = new StringBuffer();
    	sb.append(" select ");
        sb.append(" t1.* ");
    	sb.append("  from (select a.mo_id,a.instance,a.kpi_code,b.* from tf_avmon_kpi_value_list a,tf_avmon_kpi_value_current b where a.value_key=b.value_key) t1 ");
    	
    	sb.append(" where 1=1 ");
    	if (!StringUtils.isEmpty(moId)) {
        	sb.append(" and t1.MO_ID = '" + moId + "'");
    	}

    	logger.debug("getKpiList SQL:"+sb.toString());
    	
		List<Map<String, Object>> querylist = jdbcTemplate.queryForList(sb.toString());
		if (querylist != null && querylist.size() != 0) {
			// 将查询结果转换为返回类型
			for(Map<String, Object> temp : querylist) {
				KpiEvent kpi = new KpiEvent();
				kpi.setAmpInstId(nullToString(temp.get("AMP_INST_ID")));
				kpi.setKpiCode(nullToString(temp.get("KPI_CODE")));
				kpi.setMoId(moId);
				kpi.setValue(nullToString(temp.get("VALUE")));
				kpi.setInstance(nullToString(temp.get("INSTANCE")));
				kpi.setAgentId(nullToString(temp.get("AGENT_ID")));
				kpi.setKpiTime(stringToDate(nullToString(temp.get("KPI_TIME")), YYYY_MM_DD_HH_MM_SS));
				kpi.setNumValue(nullToFloat(temp.get("NUM_VALUE")));
				kpi.setStrValue(nullToString(temp.get("STR_VALUE")));
				kpi.setThresholdLevel(nullToInteger(temp.get("THRESHOLD_LEVEL")));
				
				resultList.add(kpi);				
			}
		}
    	
		logger.debug("getKpiList size:" + resultList.size());
    	
        return resultList;
    }
    

}