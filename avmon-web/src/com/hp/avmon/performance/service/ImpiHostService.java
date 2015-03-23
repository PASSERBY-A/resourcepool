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

/**
 * @author qinjie
 *
 */
@Service
public class ImpiHostService {
    private static final Log logger = LogFactory.getLog(ImpiHostService.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private KpiDataStore kpiDataStore;
     public List getIpmiHostOther(String moId) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        String virtualFan="virtualFan";
        String enclosureStatus="enclosureStatus";
        String powerMeter="powerMeter";
        String maxVoltageValue="maxVoltageValue";
        String minVoltageValue="minVoltageValue";
        String maxCurrentValue="maxCurrentValue";
        String minCurrentValue="minCurrentValue";
        String mezzTemp="mezzTemp";
        String iocntrollerTemp="iocntrollerTemp";
        String inletAmbientTemp="inletAmbientTemp";
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map m = new HashMap();
        if(virtualFan==null){
        	virtualFan="virtualFan";
        }
        m.put("KEY", virtualFan);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201002011"));
        list.add(m);
        m = new HashMap();
        if(enclosureStatus==null){
        	enclosureStatus="enclosureStatus";
        }
        m.put("KEY", enclosureStatus);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201002012"));
        list.add(m);
        m = new HashMap();
        if(powerMeter==null){
        	powerMeter="powerMeter";
        }
        m.put("KEY", powerMeter);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201002013"));
        list.add(m);
        m = new HashMap();
        if(maxVoltageValue==null){
        	maxVoltageValue="maxVoltageValue";
        }
        m.put("KEY", maxVoltageValue);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201003003"));
        list.add(m);
        m = new HashMap();
        if(minVoltageValue==null){
        	minVoltageValue="minVoltageValue";
        }
        m.put("KEY", minVoltageValue);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201003004"));
        list.add(m);
        m = new HashMap();
        if(maxCurrentValue==null){
        	maxCurrentValue="maxCurrentValue";
        }
        m.put("KEY", maxCurrentValue);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201003005"));
        list.add(m);
        m = new HashMap();
        if(minCurrentValue==null){
        	minCurrentValue="minCurrentValue";
        }
        m.put("KEY", minCurrentValue);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201003006"));
        list.add(m);
        m = new HashMap();
        if(mezzTemp==null){
        	mezzTemp="mezzTemp";
        }
        m.put("KEY", mezzTemp);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201002008"));
        list.add(m);
        m = new HashMap();
        if(iocntrollerTemp==null){
        	iocntrollerTemp="iocntrollerTemp";
        }
        m.put("KEY", iocntrollerTemp);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201002010"));
        list.add(m);
        m = new HashMap();
        if(inletAmbientTemp==null){
        	inletAmbientTemp="inletAmbientTemp";
        }
        m.put("KEY", inletAmbientTemp);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201002004"));
        list.add(m);
        return result;
    }
    public List getIpmiHostCpuTemp(String moId) {
        
    	//需要确认查询cpu instance的方式是否正确
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        Map<String,Object> resultListMap = new HashMap<String,Object>();
        String cpuTemp="cpuTemp";

        /*String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ilo_kpi_list_cpu"),moId);   
        result = jdbcTemplate.queryForList(sql);*/
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map m = new HashMap();
        if(cpuTemp==null){
        	cpuTemp="cpuTemp";
        }
        m.put("KEY", cpuTemp);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201002005"));
        list.add(m);
        return result;
    }
    
   public List getIpmiHostPluginTemp(String moId) {
        
    	//需要确认查询cpu instance的方式是否正确
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        Map<String,Object> resultListMap = new HashMap<String,Object>();
        String dimmTemp="dimmTemp";

        /*String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ilo_kpi_list_cpu"),moId);   
        result = jdbcTemplate.queryForList(sql);*/
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map m = new HashMap();
        if(dimmTemp==null){
        	dimmTemp="dimmTemp";
        }
        m.put("KEY", dimmTemp);
        m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201002006"));
        list.add(m);
        return result;
    }
    
    
    
 public Map getImpiBasicInfo(HttpServletRequest request) {
    	//需要确认查询cpu instance的方式是否正确

	 Locale locale = request.getLocale();
	String  moId =request.getParameter("moId");
 	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
/* 	String productName = bundle.getString("productName");
 	String productSN = bundle.getString("productSN");
 	String productUUID = bundle.getString("productUUID");
 	String tpmStatus = bundle.getString("tpmStatus");*/
	String productName=null;
	String boardSerialNumber=null;
	String boardManufacturer=null;
	String managementAccess=null;
 /*	String systemRom = bundle.getString("cpuType");
 	String iloVersion = bundle.getString("memSize");*/
 	//String osVersion = bundle.getString("osVersion");
    // String treeId = request.getParameter("treeId");
     List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
     Map m = new HashMap();
     if(productName==null){
    	 productName="productName";
     }
     m.put("KEY", productName);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "5201002045"));
     list.add(m);
     m = new HashMap();
     if(boardSerialNumber==null){
    	 boardSerialNumber="boardSerialNumber";
     }
     m.put("KEY", boardSerialNumber);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201003001"));
     list.add(m);
     if(boardManufacturer==null){
    	 boardManufacturer="boardManufacturer";
     }
     m = new HashMap();
     m.put("KEY", boardManufacturer);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201003002"));
     list.add(m);
     
     if(managementAccess==null){
    	 managementAccess="managementAccess";
     }
     m = new HashMap();
     m.put("KEY", managementAccess);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "8201003007"));
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
        //需要确认查询power instance的方式是否正确
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ilo_kpi_list_power"),moId); 
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }
    
    /**
     * ipmi sel
     * 
     * @param moId
     * @return
     */
    public int getSelCount(String moId) {
        //需要确认查询power instance的方式是否正确
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ipmi_kpi_selcount"),moId); 
        int count = jdbcTemplate.queryForInt(sql);
        return count;
    }
    
    public List getSelList(String moId) {
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ipmi_kpi_sel"),moId); 
        List list = jdbcTemplate.queryForList(sql);
        return list;
    }
    public List getSelTypeList(String moId) {
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ipmi_kpi_sel"),moId); 
        List list = jdbcTemplate.queryForList(sql);
        List list2 =new ArrayList();
        for(int i=0;i<list.size();i++){
        	Map m=(Map)list.get(i);
        	m.put("id", i+1);
        	m.put("selType", m.get("selType"));
        	list2.add(m);
        }
        return list2;
    }

    /**
     * 内存
     * @param moId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getMemList(String moId) {
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ipmi_kpi_list_mem"),moId);   
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }

    /**
     * 存储
     * @param moId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getDiskList(String moId) {
        
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ilo_kpi_list_disk"),moId);  
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }

    /**网卡
     * 
     * @param moId
     * @return
     */
    public List getNetcardList(String moId) {
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ilo_kpi_list_netcard"),moId);   
        resultList = jdbcTemplate.queryForList(sql);
        
        return resultList;
    }

    /**
     * 获取风扇运行参数
     * @param moId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getFanList(String moId) {
        
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ilo_kpi_list_fan"),moId);   
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }

    /**
     * 主板插槽
     * @param moId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List getMainBoardList(String moId) {
        List<Map<String,Object>> resultList = new ArrayList<Map<String,Object>>();
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ilo_kpi_list_mainboard"),moId); 
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }

    /**
     * 机箱状态
     * @param moId
     * @return
     */
    public List getCaseList(String moId) {
        List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ilo_kpi_list_case"),moId);  
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
    }

    
    // add by mark start
    /**
     * 主板插槽信息
     * @param moId
     * @return
     */
    @SuppressWarnings("unchecked")
    public Object getBiosList(String moId,ResourceBundle bundle) {
        List<Map<String, String>> instanceList = new ArrayList<Map<String,String>>();
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        /*String seriesName = bundle.getString("seriesName");
        String lastUpdateTime = bundle.getString("lastUpdateTime");
        String biosStatus="biosStatus";
        String uidStatus="uidStatus";
        try{
        	biosStatus = bundle.getString("biosStatus");
        	uidStatus = bundle.getString("uidStatus");
        }catch(Exception e){
        	
        }*/
        
        String isPowerOn = "isPowerOn";
        String isPowerFault = "isPowerFault";
        String isPowerOverload="isPowerOverload";
        String uidlightStatus="uidlightStatus";
        /*String powerDown="powerDown";
        String powerUp="powerUp";
        String hardReset="hardReset";*/
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ipmi_kpi_list_bios"),moId);
        instanceList = jdbcTemplate.queryForList(sql);
        if (instanceList != null && instanceList.size() != 0) {
            // 将查询结果转换为返回类型
            for(Map<String, String> temp : instanceList) {
                Map<String, String> map = new HashMap<String, String>();
                map.put("key", isPowerOn);
                map.put("value", temp.get("isPowerOn"));

                resultList.add(map);
                map = new HashMap<String, String>();
                map.put("key", isPowerFault);
                map.put("value", temp.get("isPowerFault"));   
                resultList.add(map);    
                map = new HashMap<String, String>();
                map.put("key", isPowerOverload);
                map.put("value", temp.get("isPowerOverload"));   
                resultList.add(map);     
                map = new HashMap<String, String>();
                map.put("key", uidlightStatus);
                map.put("value", temp.get("uidlightStatus"));   
                resultList.add(map);   
               /* map = new HashMap<String, String>();
                map.put("key", powerDown);
                map.put("value", temp.get("powerDown"));   
                resultList.add(map);     
                map = new HashMap<String, String>();
                map.put("key", powerUp);
                map.put("value", temp.get("powerUp"));   
                resultList.add(map);     
                map = new HashMap<String, String>();
                map.put("key", hardReset);
                map.put("value", temp.get("hardReset"));   
                resultList.add(map);     */
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
    public Object getCpuComm(String moId,ResourceBundle bundle) {
    	/*String frequency = bundle.getString("frequency");
    	String digit = bundle.getString("digit");
    	String threadCount = bundle.getString("threadCount");
    	String cpuStatus = bundle.getString("threadCount");*/
    	String systemboardTemp ="systemboardTemp";
    	String vrmStatus="vrmStatus";
        String enclosureStatus="enclosureStatus";
    	String healthledStatus="healthledStatus";
        List<Map<String, String>> commList = new ArrayList<Map<String, String>>();
        String sql = String.format(SqlManager.getSql(ImpiHostService.class, "ipmi_kpi_list_cpuComm"),moId);   
        List<Map<String, String>> instanceList = jdbcTemplate.queryForList(sql);
        if (instanceList != null && instanceList.size() != 0) {
            // 将查询结果转换为返回类型
            for (Map<String, String> temp : instanceList) {
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("key", systemboardTemp);
                map.put("value",temp.get("systemboardTemp"));
                commList.add(map);
                map = new HashMap<String, String>();                        
                map.put("key", vrmStatus);
                map.put("value",temp.get("vrmStatus"));
                commList.add(map);
                map = new HashMap<String, String>();
                map.put("key", enclosureStatus);
                map.put("value",temp.get("enclosureStatus"));
                commList.add(map);
                map = new HashMap<String, String>();
                map.put("key", healthledStatus);
                map.put("value",temp.get("healthledStatus"));
                commList.add(map);
            }
        }
        return commList;
    }
    // add by mark end 2013-10-22
    
    // add by mark start 2013-10-23
    @SuppressWarnings("unchecked")
    public Object getPowerComm(String moId,ResourceBundle bundle) {
        // 需要确认查询power instance的方式是否正确
        List<Map<String, String>> resultList = new ArrayList<Map<String, String>>();
        String generalStatus = bundle.getString("generalStatus");
        String redundantStatus = bundle.getString("redundantStatus");
        String sql = String.format(SqlManager.getSql(ImpiHostService.class,"ilo_kpi_list_powerComm"), moId);
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
  

    
}
