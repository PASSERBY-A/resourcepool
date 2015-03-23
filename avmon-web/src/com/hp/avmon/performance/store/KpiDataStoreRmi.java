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
import com.hp.avmonserver.api.AvmonServer;
import com.hp.avmonserver.api.CacheServer;
import com.hp.avmonserver.entity.KpiEvent;

@Service
public class KpiDataStoreRmi extends KpiDataStore{
	
    private static final Log logger = LogFactory.getLog(KpiDataStoreRmi.class);

    @Autowired
    private AvmonServer avmonServer=null;
    
    /**
     * 构造函数
     */
    public KpiDataStoreRmi() {

    	if(avmonServer==null){
    		avmonServer=SpringContextHolder.getBean("avmonServer");
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
        return avmonServer.getKpiEvent(moId,kpiCode,instance);
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

        
    	List<KpiEvent> list=avmonServer.getKpiEventList(moId,kpiCode);
    	if(list==null){
    		list=new ArrayList();
    	}
    	return list;
    }
    
    /**
     * 从current表中获取某个对象当前KPI值列表
     * @param moId
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<KpiEvent> getKpiList(String moId) {
    	List<KpiEvent> list=avmonServer.getKpiEventList(moId);
    	if(list==null){
    		list=new ArrayList();
    	}
    	return list;
    }
    
	
}