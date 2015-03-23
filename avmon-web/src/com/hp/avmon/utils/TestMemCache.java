package com.hp.avmon.utils;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.store.KpiDataStoreMemcached;
import com.hp.avmonserver.entity.KpiEvent;

public class TestMemCache {
	
    private static KpiDataStoreMemcached kpiDataStoreMemcached = null;
    ApplicationContext context;
    public TestMemCache(){
    	if(null==context){
    		context = new  ClassPathXmlApplicationContext("applicationContext.xml");
        	kpiDataStoreMemcached = (KpiDataStoreMemcached) context.getBean("kpiDataStoreMemcached");	
    	}
    }
    
	
	/**
	 * 
	 * @param moId
	 * @param kpiCode
	 * @param instance
	 * @return
	 */
	public String getCurrentKpiEvent(String moId,String ampInstId,String kpiCode,String instance) {
		KpiEvent event = kpiDataStoreMemcached.getCurrentKpiEvent(moId, ampInstId, kpiCode, instance);
		if(null != event){
			String value = event.getValue(); 
			if(null != value){
				return value;
			}else{
				return null;
			}
		}else{
			return null;
		}
	}
	
	/**
	 * 根据MoId查询kpiValue
	 * @param moId
	 * @return
	 */
	public String getKpiValuesByMoId(String moId){
		String result = null;
		List<KpiEvent> list = kpiDataStoreMemcached.getKpiList(moId);
		result = JackJson.fromObjectToJson(list);
		return result;
	} 
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
//		ServletContext servletContext = null; 
//		WebApplicationContext wac =WebApplicationContextUtils. 
//				getWebApplicationContext(servletContext); 
//		
//		ApplicationContext con = SpringContextHolder.getApplicationContext();
		
		TestMemCache test = new TestMemCache();
		String moId = "cecd193e-ef21-4fbf-aac6-aafb0a57caf9";
		String result = test.getKpiValuesByMoId(moId);
		System.out.println(result);
		System.out.println("======================================================");
		result = (String) test.getCurrentKpiEvent(moId,"5201002024","5201002024","ethernet_2");
		System.out.println(result);
	}

}
