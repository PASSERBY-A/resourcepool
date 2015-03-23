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
import com.hp.avmonserver.api.CacheServer;
import com.hp.avmonserver.entity.KpiEvent;

@Service
public class KpiDataStoreMemcached extends KpiDataStore{
	
    private static final Log logger = LogFactory.getLog(KpiDataStoreMemcached.class);
	
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CacheServer cacheServer=null;
    
    /**
     * 构造函数
     */
    public KpiDataStoreMemcached() {
    	// 加载连接对象
    	if (jdbcTemplate == null) {
        	jdbcTemplate = SpringContextHolder.getBean("jdbcTemplate");
    	}
    	
    	if(cacheServer==null){
    	    cacheServer=SpringContextHolder.getBean("cacheServer");
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
        String key=moId+"."+instance+"."+kpiCode;
        return (KpiEvent)cacheServer.get(key);
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
        
        String sql=String.format("select * from TF_AVMON_KPI_VALUE_LIST where mo_id='%s' and kpi_code='%s'",moId,kpiCode);
        
        
        List<Map<String, Object>> querylist = jdbcTemplate.queryForList(sql);
        if (querylist != null && querylist.size() != 0) {
            // 将查询结果转换为返回类型
            for(Map<String, Object> map : querylist) {
                String key=map.get("MO_ID")+"."+map.get("INSTANCE")+"."+map.get("KPI_CODE");
                KpiEvent event=(KpiEvent) cacheServer.get(key);
                if(event!=null){
                    resultList.add(event);          
                }
            }
        }
        
        logger.debug("getKpiList2 size:" + resultList.size());
        
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
    	
    	String sql=String.format("select * from TF_AVMON_KPI_VALUE_LIST where mo_id='%s'",moId);
    	
    	//logger.debug(sql);
    	
		List<Map<String, Object>> querylist = jdbcTemplate.queryForList(sql);
		if (querylist != null && querylist.size() != 0) {
			// 将查询结果转换为返回类型
			for(Map<String, Object> map : querylist) {
				String key=map.get("MO_ID")+"."+map.get("INSTANCE")+"."+map.get("KPI_CODE");
				KpiEvent event=(KpiEvent) cacheServer.get(key);
				if(event!=null){
				    resultList.add(event);			
				}
			}
		}
    	
		logger.debug("getKpiList size:" + resultList.size());
    	
        return resultList;
    }
    
	
}