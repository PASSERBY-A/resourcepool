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
public class SnmpHostService {
    private static final Log logger = LogFactory.getLog(SnmpHostService.class);
    
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
    public List getSnmpPartitionList(String moId) {
        
    	  List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
          String sql = String.format(SqlManager.getSql(SnmpHostService.class, "snmp_kpi_list_partition"),moId);   
          resultList = jdbcTemplate.queryForList(sql);
          return resultList;
    }
    public List getSnmpFileList(String moId) {
  	    List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(SnmpHostService.class, "snmp_kpi_list_file"),moId);   
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
   }
    public List getSnmpStorageList(String moId) {
  	    List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(SnmpHostService.class, "snmp_kpi_list_storage"),moId);   
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
   }
    public List getSnmpDeviceList(String moId) {
  	    List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(SnmpHostService.class, "snmp_kpi_list_device"),moId);   
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
   }
    
    public List getSnmpRunningSoftwareList(String moId) {
  	    List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(SnmpHostService.class, "snmp_kpi_list_running_software"),moId);   
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
   }
    
    public List getSnmpInstalledSoftwareList(String moId) {
  	    List<Map<String,String>> resultList = new ArrayList<Map<String,String>>();
        String sql = String.format(SqlManager.getSql(SnmpHostService.class, "snmp_kpi_list_installed_software"),moId);   
        resultList = jdbcTemplate.queryForList(sql);
        return resultList;
   }
    
    
 
    
    
   public Map getSnmpBasicInfo(HttpServletRequest request) {
    	//需要确认查询cpu instance的方式是否正确

	 Locale locale = request.getLocale();
	String  moId =request.getParameter("moId");
 	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
/* 	String productName = bundle.getString("productName");
 	String productSN = bundle.getString("productSN");
 	String productUUID = bundle.getString("productUUID");
 	String tpmStatus = bundle.getString("tpmStatus");*/
	String hrSystemUptime=null;
	String hrSystemDate=null;
	String hrSystemInitialLoadDevice=null;
	String hrSystemInitialLoadParameters=null;
	String hrSystemNumUsers=null;
	String hrSystemProcesses=null;
	String hrSystemMaxProcesses=null;
	String hrMemorySize=null;
 /*	String systemRom = bundle.getString("cpuType");
 	String iloVersion = bundle.getString("memSize");*/
 	//String osVersion = bundle.getString("osVersion");
    // String treeId = request.getParameter("treeId");
     List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
     Map m = new HashMap();
     if(hrSystemUptime==null){
    	 hrSystemUptime="hrSystemUptime";
     }
     m.put("KEY", hrSystemUptime);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "9200021742"));
     list.add(m);
     m = new HashMap();
     if(hrSystemDate==null){
    	 hrSystemDate="hrSystemDate";
     }
     m.put("KEY", hrSystemDate);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "9200021491"));
     list.add(m);
     if(hrSystemInitialLoadDevice==null){
    	 hrSystemInitialLoadDevice="hrSystemInitialLoadDevice";
     }
     m = new HashMap();
     m.put("KEY", hrSystemInitialLoadDevice);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "9200021770"));
     list.add(m);
     
     if(hrSystemInitialLoadParameters==null){
    	 hrSystemInitialLoadParameters="hrSystemInitialLoadParameters";
     }
     m = new HashMap();
     m.put("KEY", hrSystemInitialLoadParameters);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "9200021748"));
     list.add(m);
     if(hrSystemNumUsers==null){
    	 hrSystemNumUsers="hrSystemNumUsers";
     }
     m = new HashMap();
     m.put("KEY", hrSystemNumUsers);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "9200021771"));
     list.add(m);
     if(hrSystemProcesses==null){
    	 hrSystemProcesses="hrSystemProcesses";
     }
     m = new HashMap();
     m.put("KEY", hrSystemProcesses);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "9200021772"));
     list.add(m);
     if(hrSystemMaxProcesses==null){
    	 hrSystemMaxProcesses="hrSystemMaxProcesses";
     }
     m = new HashMap();
     m.put("KEY", hrSystemMaxProcesses);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "9200021773"));
     list.add(m);
     if(hrMemorySize==null){
    	 hrMemorySize="hrMemorySize";
     }
     m = new HashMap();
     m.put("KEY", hrMemorySize);
     m.put("VAL",kpiDataStore.getCurrentKpiValue(moId, "9200021774"));
     list.add(m);

	 Map<String, Object> map = new HashMap<String, Object>();
     map.put("basicTotal", list.size());
     map.put("basicData", list);
     
     return map;
      
    }




    //jqw分页
  	private String generatPageSqlForJQW(String sql, String limit, String start) {
  		Integer limitL = Integer.valueOf(limit);
  		// Integer startL = Integer.valueOf(start)+1;
  		Integer startL = Integer.valueOf(start);

  		// 构造oracle数据库的分页语句
  		return DBUtils.paginationforjqw(sql, startL, limitL);
  	}
    
}
