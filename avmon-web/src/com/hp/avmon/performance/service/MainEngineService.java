package com.hp.avmon.performance.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.performance.store.KpiDataStore;
import com.hp.avmon.performance.store.ResourceStore;
import com.hp.avmon.utils.MyString;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmonserver.entity.KpiEvent;

@Service
public class MainEngineService {
    
    private static final Log logger = LogFactory.getLog(MainEngineService.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired @Qualifier("kpiDataStore")
    private KpiDataStore kpiInfo;
    
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
    	String tempSql = SqlManager.getSql(MainEngineService.class, "store_BasicInfo");
        String sql = String.format(tempSql, moId);
    	
        List list = jdbcTemplate.queryForList(sql);
        if(list != null && list.size() > 0){
            Map m = (Map) list.get(0);
            map.putAll(m);
        }

        map.put("USERCOUNT", 0);
        map.put("ALARMCOUNT", 0);

        return map;
    }
    
    
	/**
	 * 【网口信息】
	 * 
	 * @param moId
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineNetworks(String moId) {

        Map map=new HashMap();
        
        if(moId!=null){
            String sql=SqlManager.getSql(this, "host_kpi_list_network");
            sql=sql.replaceAll("\\{MO_ID\\}", moId);
            List list=jdbcTemplate.queryForList(sql);
            map.put("root",list);
            map.put("total", list.size());
            logger.warn(sql);
        }
        else{
            logger.warn("moId is null");
        }
        return map;   
    }
    
    
	/**
	 * CPU 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineCpuInfo(String moId) {
        
        Map map = new HashMap();
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "1001011005");
        if (tempList != null && tempList.size() != 0) {
        	map.put("CPU_USR", tempList.get(0).getNumValue());
        	map.put("CPU_LEVEL", tempList.get(0).getThresholdLevel());
        } else {
        	map.put("CPU_USR", "0");
        	map.put("CPU_LEVEL", "1");
        	
        }

        List<Map> result = new ArrayList<Map>();
        result.add(map);
        
        return result;
    }
    
	/**
	 * memory 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineMem(String moId) {

        Map map = new HashMap();
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "1001010001");
        if (tempList != null && tempList.size() != 0) {
        	map.put("MEM_USR", tempList.get(0).getValue());
        	map.put("MEM_LEVEL", tempList.get(0).getThresholdLevel());
        } else {
        	map.put("MEM_USR", "0");
        	map.put("MEM_LEVEL", "1");
        }

        List<Map> result = new ArrayList<Map>();
        result.add(map);
        
        return result;
    }
    
	/**
	 * memory 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineMemInfo(String moId) {

    	Map basicMap = new HashMap();
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "1001010001");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("MEM_SYS", tempList.get(0).getValue() + "%");
        } else {
        	basicMap.put("MEM_SYS", "0%");
        }
        
        tempList = kpiInfo.getKpiList(moId, "", "1001010005");
        if (tempList != null && tempList.size() != 0) {
        	String freeMemSize = tempList.get(0).getStrValue();
        	if(freeMemSize.toUpperCase().indexOf("MB")>-1)
        	{
        		basicMap.put("FREE_MEM", tempList.get(0).getNumValue() + " MB");
        	}
        	else if(freeMemSize.toUpperCase().indexOf("KB")>-1)
        	{
        		Float f=tempList.get(0).getNumValue();
        		basicMap.put("FREE_MEM", f*1024 + " KB");
        	}
        	else if(freeMemSize.toUpperCase().indexOf("GB")>-1)
        	{
        		Float f=tempList.get(0).getNumValue();
        		basicMap.put("FREE_MEM", f/1024 + " GB");
        	}
        	else
        	{
        		basicMap.put("FREE_MEM", tempList.get(0).getNumValue() + " MB");
        	}
        }
        else {
            basicMap.put("FREE_MEM", "");
        }
        
        return basicMap;
    }
    
	/**
	 * 进程  百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineCourse(String moId) {

        Map map = new HashMap();
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "1001012001");
        if (tempList != null && tempList.size() != 0) {
        	map.put("COURSE_USR", tempList.get(0).getValue());
        } else {
        	map.put("COURSE_USR", "");
        }

        List<Map> result = new ArrayList<Map>();
        result.add(map);
        
        return result;
    }
    
	/**
	 * 进程  百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineCourseInfo(String moId) {
        Map basicMap = new HashMap();
        
        List<KpiEvent> tempList = new ArrayList<KpiEvent>();
        // add by mark start 2013-12-24
        String sql = "select TYPE_ID AS \"TYPE_ID\" from td_avmon_mo_info t where t.mo_id = '"+moId+"'";
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        list = jdbcTemplate.queryForList(sql);
        String moType = StringUtil.EMPTY;
        if(list != null &&list.size() > 0){
        	moType = list.get(0).get("TYPE_ID");
        	if("HOST_WINDOWS".equals(moType)){
        		tempList = kpiInfo.getKpiList(moId, "", "1701010009");
        	}else{
        		tempList = kpiInfo.getKpiList(moId, "", "1001008002");
        	}
        }
        
        List<KpiEvent> usrProcPercentList = kpiInfo.getKpiList(moId, "", "1001008004");//
        String usrProcPercent = "0";
        // add by mark end 2013-12-24
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("USR_PROC_NUM", tempList.get(0).getValue());
        	if(usrProcPercentList != null && usrProcPercentList.size() > 0){
        		usrProcPercent = usrProcPercentList.get(0).getValue();
        	}
        	
        	basicMap.put("USR_PROC_USAGE",usrProcPercent + "%");
        	basicMap.put("USR_PROC_Percent", usrProcPercent);
        	basicMap.put("USR_PROC_Level", tempList.get(0).getThresholdLevel());
        } else {
        	basicMap.put("USR_PROC_NUM", "0");
        	basicMap.put("USR_PROC_USAGE", "0%");
        	basicMap.put("USR_PROC_Percent", "0");
        	basicMap.put("USR_PROC_Level", "1");
        }
        
        return basicMap;
    }
    
    
	/**
	 * 进程  百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMPLogList(String moId) {

    	Map map=new HashMap();
        
        if(moId!=null){
            String sql=SqlManager.getSql(this, "host_kpi_list_mplog");
            sql=sql.replaceAll("\\{MO_ID\\}", moId);
            List list=jdbcTemplate.queryForList(sql);
            map.put("root",list);
            map.put("total", list.size());
            logger.warn(sql);
        }
        else{
            logger.warn("moId is null");
        }
        return map;
    }    
    
	/**
	 * 换页文件 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineSkipFile(String moId) {
        
        Map map = new HashMap();
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "1001013003");
        if (tempList != null && tempList.size() != 0) {
        	map.put("SKIP_FILE_USE", tempList.get(0).getValue());
        	map.put("SKIP_LEVEL", tempList.get(0).getThresholdLevel());
        } else {
        	map.put("SKIP_FILE_USE", "0");
        	map.put("SKIP_LEVEL", "1");
        }

        List<Map> result = new ArrayList<Map>();
        result.add(map);
        
        return result;
    }
    
	/**
	 * 换页文件 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineSkipFileInfo(String moId) {

        Map basicMap = new HashMap();
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "1001013003");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("SWAP_UTIL", tempList.get(0).getValue() + " %");
        } else {
        	basicMap.put("SWAP_UTIL", "");
        }
        
        tempList = kpiInfo.getKpiList(moId, "", "1001013001");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("SWAP_NAME", tempList.get(0).getValue());
        } else {
        	basicMap.put("SWAP_NAME", "");
        }
        
        tempList = kpiInfo.getKpiList(moId, "", "1001013002");
        if (tempList != null && tempList.size() != 0) {
        	String swapSize = tempList.get(0).getStrValue();
        	if(swapSize.toUpperCase().indexOf("MB")>-1)
        	{
        		basicMap.put("SWAP_SIZE", tempList.get(0).getNumValue() + " MB");
        	}
        	else if(swapSize.toUpperCase().indexOf("KB")>-1)
        	{
        		Float f=tempList.get(0).getNumValue();
        		basicMap.put("SWAP_SIZE", f*1024 + " KB");
        	}
        	else if(swapSize.toUpperCase().indexOf("GB")>-1)
        	{
        		Float f=tempList.get(0).getNumValue();
        		basicMap.put("SWAP_SIZE", f/1024 + " GB");
        	}
        	else
        	{
        		basicMap.put("SWAP_SIZE", tempList.get(0).getNumValue() + " MB");
        	}
        	
        	
        } else {
        	basicMap.put("SWAP_SIZE",  "");
        }
        
        return basicMap;
    }
    
	/**
	 * Disk 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineDisk(String moId) {
        Map map=new HashMap();
        Map buckets = new HashMap();
        if(moId!=null){
            String sql=SqlManager.getSql(this, "host_kpi_list_disk");
            sql=sql.replaceAll("\\{MO_ID\\}", moId);
            List<Map> list=jdbcTemplate.queryForList(sql);
            List<Map> bucketList = new ArrayList();
            for(int i = 0; i < list.size(); i++)
            {
             	Map basic = new HashMap();
             	basic.put("usage",list.get(i).get("BUSY_RATE"));
             	basic.put("name",list.get(i).get("DISK_NAME"));
             	basic.put("kpi",list.get(i).get("BUSY_RATE_VALUE"));
             	basic.put("level",list.get(i).get("DS_LEVEL"));
             	basic.put("src","../mainEngine/tinyColumn.jsp?kpi="+list.get(i).get("BUSY_RATE_VALUE")+"&level="+list.get(i).get("DS_LEVEL"));
             	
             	bucketList.add(basic);
            }

            buckets.put("buckets",bucketList);
            logger.warn(sql);
        }
        else{
            logger.warn("moId is null");
        }
        return buckets;   
    }
    
	/**
	 * Disk 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineDiskInfo(String moId) {

    	Map basicMap = new HashMap();
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "10010050");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("FDISK_CAPABILITY", tempList.get(0).getValue() + "%");
        } else {
        	basicMap.put("FDISK_CAPABILITY", "0%");
        }
        
        tempList = kpiInfo.getKpiList(moId, "", "10010049");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("FDISK_NAME", tempList.get(0).getValue());
        } else {
        	basicMap.put("FDISK_NAME", "");
        }
        
        tempList = kpiInfo.getKpiList(moId, "", "10010052");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("FDISK_TOTAL", tempList.get(0).getValue() + "G");
        } else {
        	basicMap.put("FDISK_TOTAL",  "0");
        }
        
        return basicMap;
    }
    
	/**
	 * 多桶信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMultipleBucketInfo(String moId) {
    	Map returnMap = new HashMap();

    	List bucketList = new ArrayList();
    	Map childMap_0 = new HashMap();
    	Map childMap_1 = new HashMap();
    	Map childMap_2 = new HashMap();
    	
        if(moId!=null){
            String sql=SqlManager.getSql(this, "host_kpi_list_disk");
            sql=sql.replaceAll("\\{MO_ID\\}", moId);
            List diskList=jdbcTemplate.queryForList(sql);
            for(int i = 0; i < diskList.size(); i++)
            {
            	Object disk = diskList.get(i);
            	logger.debug("MainEngineService:" + disk);
            	String json = JackJson.fromObjectToJson(disk);
            	
            	childMap_0.put("imgId", "1");
            	childMap_0.put("bucketId", "1");
            	childMap_0.put("src", "../mainEngine/images/graphbackground.png");
            	childMap_0.put("class", "graphbackground");
            	childMap_0.put("width", "102");
            	childMap_0.put("height", "180");
            	childMap_0.put("top", "0");
            	
            	childMap_1.put("imgId", "2");
            	childMap_1.put("bucketId", "1");
            	childMap_1.put("src", "../mainEngine/images/graphbar.png");
            	childMap_1.put("class", "graphbar");
            	childMap_1.put("width", "102");
            	childMap_1.put("height", "180");
            	childMap_1.put("top", json);
            	
            	childMap_2.put("imgId", "3");
            	childMap_2.put("bucketId", "1");
            	childMap_2.put("src", "../mainEngine/images/graphforeground.png");
            	childMap_2.put("class", "graphforeground");
            	childMap_2.put("width", "102");
            	childMap_2.put("height", "180");
            	childMap_2.put("top", "0");
            	
            	List tempList = new ArrayList();
            	tempList.add(childMap_0);
            	tempList.add(childMap_1);
            	tempList.add(childMap_2);
            	
            	Map basicMap = new HashMap();
                basicMap.put("bucketId",  "1");
                basicMap.put("name", "bucket_1");
                basicMap.put("images", tempList);
                
                bucketList.add(basicMap);
            }
            returnMap.put("buckets", bucketList);    
        }
        
        return returnMap;
    }    
    
    
    
	/**
	 * file system 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineFileSys(String moId) {
        
        Map map=new HashMap();
        
        if(moId!=null){
            String sql=SqlManager.getSql(this, "host_kpi_list_lv");
            sql=sql.replaceAll("\\{MO_ID\\}", moId);
            List<Map> list=jdbcTemplate.queryForList(sql);
            List<Map> bucketList = new ArrayList();
            for(int i = 0; i < list.size(); i++)
            {
             	Map basic = new HashMap();
             	basic.put("usage",list.get(i).get("FS_PERCENT"));
             	basic.put("name",list.get(i).get("FS_PATH"));
             	basic.put("kpi",list.get(i).get("FS_USAGE"));
             	basic.put("level",list.get(i).get("FS_LEVEL"));
             	basic.put("src","../mainEngine/tinyColumn.jsp?kpi="+list.get(i).get("FS_USAGE")+"&level="+list.get(i).get("FS_LEVEL"));
             	
             	bucketList.add(basic);
            }
            map.put("buckets",bucketList);
            logger.warn(sql);
        }
        else{
            logger.warn("moId is null");
        }
        return map;  
    }
    
	/**
	 * file system 百分比信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineFileSysInfo(String moId) {

    	Map basicMap = new HashMap();
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "10010050");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("FDISK_CAPABILITY", tempList.get(0).getValue() + "%");
        } else {
        	basicMap.put("FDISK_CAPABILITY", "0%");
        }
        
        tempList = kpiInfo.getKpiList(moId, "", "10010051");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("FDISK_MOUNT", tempList.get(0).getValue());
        } else {
        	basicMap.put("FDISK_MOUNT", "");
        }
        
        tempList = kpiInfo.getKpiList(moId, "", "10010052");
        if (tempList != null && tempList.size() != 0) {
        	basicMap.put("FDISK_TOTAL", tempList.get(0).getValue() + "G");
        } else {
        	basicMap.put("FDISK_TOTAL",  "0");
        }
        
        return basicMap;
    }
    
	/**
	 * 网络传输 发送包 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineNetworkSend(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        List<KpiEvent> tempList = kpiInfo.getKpiHistory(moId, "", "1001003004", "", 5);
        Map map = new HashMap();
        Map m = new HashMap();
        List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);
        String speed="";
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }
        //网卡发送流量：1001003004
        List<KpiEvent> tempSendList = kpiInfo.getKpiList(moId, "", "1001003004");
        if (tempSendList != null && tempSendList.size() != 0) {
        	float f=(float) 0.0;
        	for(KpiEvent temp : tempSendList)
        	{
        		f+=temp.getNumValue();
        	}
        	BigDecimal big = new BigDecimal(f);
        	speed = big + "";
        } else {
        	speed = noData;
        }
        map.put("speed", speed);
        m = new HashMap();
        m.put("speed_text", speed);
        map.put("content", m);
        
        logger.debug("Network> "+map);
        
        return map;
    }
    
	/**
	 * 网络传输 接收包 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineNetworkReceive(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        List<KpiEvent> tempList = kpiInfo.getKpiHistory(moId, "", "10010025", "", 5);
        Map map = new HashMap();
        Map m = new HashMap();
        List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);

        //add by TED. default value 0 while the db is empty
        String speed="";
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }
        //网卡接受流量：1001003003 单位bps
        List<KpiEvent> tempSendList = kpiInfo.getKpiList(moId, "", "1001003003");
        if (tempSendList != null && tempSendList.size() != 0) {
        	float f=(float) 0.0;
        	for(KpiEvent temp : tempSendList)
        	{
        		f+=temp.getNumValue();
        	}
        	BigDecimal big = new BigDecimal(f);
        	speed = big + "";
        } else {
        	speed = noData;
        }
        map.put("speed", speed);
        
        m = new HashMap();
        //m.put("speed_text", "35 k/s");
        m.put("speed_text", speed);
        map.put("content", m);
        
        return map;
    }
    
	/**
	 * 换页文件 接收包 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineSkipfilePagein(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        List<KpiEvent> tempList = kpiInfo.getKpiHistory(moId, "", "1001010002", "", 5);
        Map map = new HashMap();
        Map m = new HashMap();
        List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);
        String speed="";
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }
      //Pagein：1001010002 单位p/s
        List<KpiEvent> tempSendList = kpiInfo.getKpiList(moId, "", "1001010002");
        if (tempSendList != null && tempSendList.size() != 0) {
        	 speed = tempSendList.get(0).getValue();
        } else {
        	speed = noData;
        }
        map.put("speed", speed);
        m = new HashMap();
        m.put("speed_text", speed);
        map.put("content", m);
        
        logger.debug("aaaaaaaaaaaa> "+map);
        
        return map;
        
    }
    
	/**
	 * 换页文件 接收包 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineSkipfilePageout(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        List<KpiEvent> tempList = kpiInfo.getKpiHistory(moId, "", "1001010003", "", 5);
        Map map = new HashMap();
        Map m = new HashMap();
        List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);
        String speed="";
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }
      //PageOut：1001010003 单位p/s
        List<KpiEvent> tempSendList = kpiInfo.getKpiList(moId, "", "1001010003");
        if (tempSendList != null && tempSendList.size() != 0) {
        	 speed = tempSendList.get(0).getValue();
        } else {
        	speed = noData;
        }
        map.put("speed", speed);
        m = new HashMap();
        m.put("speed_text", speed);
        map.put("content", m);
        
        logger.debug("SWAP>>> "+map);
        
        return map;
    	
    }
    
	/**
	 * 换页文件 接收包 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineDiskWrite(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
    	SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        List<KpiEvent> tempList = kpiInfo.getKpiHistory(moId, "", "1001010002", "", 5);
        Map map = new HashMap();
        Map m = new HashMap();
        List history = new ArrayList();
        for (KpiEvent kpi : tempList) {
        	m = new HashMap();
        	
        	m.put("time", df.format(kpi.getKpiTime()));
        	m.put("usage", kpi.getValue());
        	history.add(m);
        }
        
        map.put("history", history);
        String speed="";
        if(history.size()>0){
            speed=(String) ((Map)history.get(0)).get("usage");
        }
      //R/W：1001014003 单位K/s
        List<KpiEvent> tempSendList = kpiInfo.getKpiList(moId, "", "1001014003");
        if (tempSendList != null && tempSendList.size() != 0) {
        	float f=(float) 0.0;
        	for(KpiEvent temp : tempSendList)
        	{
        		f+=temp.getNumValue();
        	}
        	BigDecimal big = new BigDecimal(f);
        	speed = big + "";
        } else {
        	speed = noData;
        }
        map.put("speed", speed);
        m = new HashMap();
        m.put("speed_text", speed);
        map.put("content", m);
        
        return map;
    	
    }
    
	/**
	 * 换页文件 接收包 信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getMainEngineDiskRead(String moId) {
        Map map = new HashMap();
        List history = new ArrayList();

        Map m = new HashMap();
        m.put("time", "14:24");
        m.put("usage", 12);
        history.add(m);
        
        m = new HashMap();
        m.put("time", "14:25");
        m.put("usage", 1);
        history.add(m);
        
        m = new HashMap();
        m.put("time", "14:26");
        m.put("usage", 30);
        history.add(m);
        
        m = new HashMap();
        m.put("time", "14:27");
        m.put("usage", 22);
        history.add(m);
        
        m = new HashMap();
        m.put("time", "14:28");
        m.put("usage", 60);
        history.add(m);
        
        m = new HashMap();
        m.put("time", "14:29");
        m.put("usage", 80);
        history.add(m);
        
        m = new HashMap();
        m.put("time", "14:30");
        m.put("usage", 40);
        history.add(m);
        
        m = new HashMap();
        m.put("time", "14:31");
        m.put("usage", 53);
        history.add(m);
        
        m = new HashMap();
        m.put("time", "14:32");
        m.put("usage", 22);
        history.add(m);
        
        map.put("history", history);
        map.put("speed", 125);
        m = new HashMap();
        m.put("speed_text", "125 c/s");
        map.put("content", m);
        
        return map;
    }
    
    /**
     * 取得活动告警
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getBasicInfoList(HttpServletRequest request) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
    	String hostName = bundle.getString("hostName");
    	String hostAddr = bundle.getString("hostAddr");
    	String manufacturer = bundle.getString("manufacturer");
    	String cpuCount = bundle.getString("cpuCount");
    	String cpuType = bundle.getString("cpuType");
    	String memSize = bundle.getString("memSize");
    	String osVersion = bundle.getString("osVersion");
        String treeId = request.getParameter("treeId");
        
        String moId=treeId;
        
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map m = new HashMap();
        m.put("KEY", hostName);
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "1002001001"));
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", hostAddr);
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "1002001019"));
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", manufacturer);
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "1002001003"));
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", cpuCount);
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "1002001004"));
        list.add(m);

        m = new HashMap();
        m.put("KEY", cpuType);
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "1002001007"));
        list.add(m);

        m = new HashMap();
        m.put("KEY", memSize);
        
        m.put("VAL",MyString.toVolumeString(kpiInfo.getCurrentKpiNumValue(moId, "1002001009")*1024*1024));
        list.add(m);

        m = new HashMap();
        m.put("KEY", osVersion);
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "1002001012"));
        list.add(m);

		Map<String, Object> map = new HashMap<String, Object>();
        map.put("basicTotal", list.size());
        map.put("basicData", list);
        
        return map;
    }


    public Object getMoBasicInfo(String moId) {
        // TODO Auto-generated method stub
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        Map<String, Object> map = new HashMap<String, Object>();
        if(typeId.equals("HOST_WINDOWS")){
            map.put("hostImage", "SYS_Windows.png");
        }else if(typeId.equals("HOST_LINUX")){
            map.put("hostImage", "SYS_Linux.png");
        }else if(typeId.equals("HOST_SUNOS")){
            map.put("hostImage", "SYS_Solaris.png");
        }else if(typeId.equals("HOST_AIX")){
            map.put("hostImage", "SYS_Aix.png");
        }else if(typeId.equals("HOST_HP-UX")){
            map.put("hostImage", "SYS_Unix.png");
        }else {
            map.put("hostImage", "SYS_Others.png");
        }
        return map;
    }
    
    /**
     * 取得活动告警
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Map getAlarmList(String  moId, Boolean isLogError) throws Exception {

    	String treeId = moId;

        String limit = "25";
        String start = "0";
        
        String tempSql = SqlManager.getSql(AlarmSearchService.class, "getAlarmSearchList");
        StringBuilder sb = new StringBuilder();
        sb.append(" and t1.MO_ID='" + treeId + "'");
        //add by yuanpeng get logErrorAlarm
        if(isLogError)
        {
            sb.append(" and t1.KPI_CODE in ('1001005001','1001006001','1001007001')");
        }
//        
        String sql = String.format(tempSql, sb.toString());
//        String querySql = MyFunc.generatPageSql(tempSql, limit, start);
        
        //logger.debug("AlarmSearchService getAlarmList sql: " + querySql);
		List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        
		Map<String, Object> map = new HashMap<String, Object>();
        map.put("alarmTotal", list.size());
        map.put("alarmData", list);
        return map;
    }
    
    
    
	/**
	 * 取得日志错误信息
	 * 
	 * @param moId
	 * @param condition
	 * @return List<Map>
	 */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object getLogError(String moId) {

    	Map map=new HashMap();
    	// modify by mark start 2013-12-18
    	List<Map<String,Object>> syslogList = new ArrayList<Map<String,Object>>();
    	syslogList = this.getAlarmListByKpiCode(moId, "1001005001");
    	// modify by mark start 2013-12-18 end
    	map.put("leftLogLabel", "syslog");
    	if (syslogList != null && syslogList.size() != 0) {
        	map.put("leftLogNum", syslogList.size());
        }else {
        	map.put("leftLogNum", "0");
        }
    	// modify by mark start 2013-12-18
    	List<Map<String,Object>> rclogList = new ArrayList<Map<String,Object>>();
    	rclogList = this.getAlarmListByKpiCode(moId, "1001007001");
    	// modify by mark start 2013-12-18 end
    	map.put("rightLogLabel", "rclog");
    	if (rclogList != null && rclogList.size() != 0) {
        	map.put("rightLogNum", rclogList.size());
        }else {
        	map.put("rightLogNum", "0");
        } 
        String typeId=ResourceStore.getMoPropValue(moId,"typeId");
        if(typeId.equals("HOST_HP-UX")){
        	//syslog mplog rclog
        	// modify by mark start 2013-12-18 start
        	List<Map<String,Object>> mplogList = new ArrayList<Map<String,Object>>();
        	mplogList = this.getAlarmListByKpiCode(moId, "1001034004");
        	// modify by mark end 2013-12-18 start
        	map.put("centerLogLabel", "mplog");
        	if (mplogList != null && mplogList.size() != 0) {
            	map.put("centerLogNum", mplogList.size());
            }else {
            	map.put("centerLogNum", "0");
            } 
        }else {
        	//syslog maillog rclog
        	// modify by mark start 2013-12-18 start
        	List<Map<String,Object>> maillogList = new ArrayList<Map<String,Object>>();
        	maillogList = this.getAlarmListByKpiCode(moId, "1001006001");
//        	List<KpiEvent> maillogList = kpiInfo.getKpiList(moId, "", "1001006001");
        	// modify by mark start 2013-12-18 end
        	map.put("centerLogLabel", "maillog");
        	if (maillogList != null && maillogList.size() != 0) {
            	map.put("centerLogNum", maillogList.size());
            }else {
            	map.put("centerLogNum", "0");
            }
        }
        return map;
    }
    
    /**
     * 取得活动告警
     * 
     * @param request
     * @return
     * @throws Exception
     */
	public List<Map<String,Object>> getAlarmListByKpiCode(String  moId, String kpiCode) {

    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        String tempSql = SqlManager.getSql(AlarmSearchService.class, "getAlarmSearchList");
        StringBuilder sb = new StringBuilder();
        sb.append(" and t1.MO_ID='" + moId + "'");
        //add by yuanpeng get logErrorAlarm
        if(kpiCode != null)
        {
            sb.append(" and t1.KPI_CODE = '"+kpiCode+"'");
        }
        
        String sql = String.format(tempSql, sb.toString());
		list = jdbcTemplate.queryForList(sql);
		
		if(null==list ||list.size()==0){
			return null;
		}
        return list;
    }
    
}
