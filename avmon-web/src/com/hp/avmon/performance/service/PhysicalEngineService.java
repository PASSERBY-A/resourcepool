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

import com.hp.avmon.performance.store.KpiDataStore;
import com.hp.avmon.performance.store.ResourceStore;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmonserver.entity.KpiEvent;

@Service
public class PhysicalEngineService {
    
    private static final Log logger = LogFactory.getLog(VirtualMachineService.class);
    
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

        Map map = new HashMap();
        map.put("Established", "26");
        map.put("Time_wait", "33");
        map.put("FIN_wait", "5");
        map.put("Close_wait", "7");
        
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
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "5101101006");
        if (tempList != null && tempList.size() > 0) {
        	float value = 0.0f;
        	int thresholdLevel = 1;
            for(KpiEvent kpiEvent : tempList)
            {
            	value += kpiEvent.getNumValue();
            }
            
            value = value/tempList.size();
            BigDecimal bg = new BigDecimal(value);
            double f1 = bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
            map.put("CPU_USR", f1);
            
            if (value>=20.0f && value<40.0f) {
            	thresholdLevel = 2;
			}else if(value>=40 && value<60){
				thresholdLevel = 3;
			}else if(value>=60 && value<80){
				thresholdLevel = 4;
			}else if(value>=80){
				thresholdLevel = 5;
			}
            map.put("CPU_LEVEL", thresholdLevel);
            
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
    public Object getMainEngineMemInfo(String moId) {

        Map basicMap = new HashMap();
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "5101102008");
        if (tempList != null && tempList.size() != 0) {
            basicMap.put("MEM_SYS", tempList.get(0).getValue() + "%");
        } else {
            basicMap.put("MEM_SYS", "0%");
        }
        
        tempList = kpiInfo.getKpiList(moId, "", "5102100010");
        if (tempList != null && tempList.size() != 0) {
            String freeMemSize = tempList.get(0).getStrValue();
            basicMap.put("FREE_MEM", freeMemSize);
        }
        else {
            basicMap.put("FREE_MEM", "");
        }
        
        return basicMap;
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
        List<KpiEvent> tempList = kpiInfo.getKpiList(moId, "", "5101102008");
        if (tempList != null && tempList.size() != 0) {
            map.put("MEM_USR", tempList.get(0).getValue());
            map.put("MEM_LEVEL", tempList.get(0).getThresholdLevel());
        } else {
            map.put("MEM_USR", "0");
            map.put("MEM_LEVEL", "0");
        }

        List<Map> result = new ArrayList<Map>();
        result.add(map);
        
        return result;
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
        List<KpiEvent> tempList = kpiInfo.getKpiHistory(moId, "", "5101105006", "", 5);
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
        //网卡发送流量：5101105006
        List<KpiEvent> tempSendList = kpiInfo.getKpiList(moId, "", "5101105006");
        if (tempSendList != null && tempSendList.size() != 0) {
            for(KpiEvent temp : tempSendList)
            {
                if(temp.getInstance().equals("All")||temp.getInstance().equals("this"))
                {
                    speed = temp.getStrValue();
                    break;
                }
            }
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
        List<KpiEvent> tempList = kpiInfo.getKpiHistory(moId, "", "5101105005", "", 5);
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
        //网卡接受流量：5101105005
        List<KpiEvent> tempSendList = kpiInfo.getKpiList(moId, "", "5101105005");
        if (tempSendList != null && tempSendList.size() != 0) {
            for(KpiEvent temp : tempSendList)
            {
                if(temp.getInstance().equals("All")||temp.getInstance().equals("this"))
                {
                    speed = temp.getStrValue();
                    break;
                }
            }
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
    public Object getMainEngineDiskWrite(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        List<KpiEvent> tempList = kpiInfo.getKpiHistory(moId, "", "5101105005", "", 5);
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
        //磁盘写入流量：5101103005
        List<KpiEvent> tempSendList = kpiInfo.getKpiList(moId, "", "5101103005");
        if (tempSendList != null && tempSendList.size() != 0) {
            for(KpiEvent temp : tempSendList)
            {
                if(temp.getInstance().equals("All")||temp.getInstance().equals("this"))
                {
                    speed = temp.getStrValue();
                    break;
                }
            }
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
    public Object getMainEngineDiskRead(String moId,ResourceBundle bundle) {
    	String noData = bundle.getString("noData");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        List<KpiEvent> tempList = kpiInfo.getKpiHistory(moId, "", "5101105005", "", 5);
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
        //磁盘读取速率：5101103006
        List<KpiEvent> tempSendList = kpiInfo.getKpiList(moId, "", "5101103006");
        if (tempSendList != null && tempSendList.size() != 0) {
            for(KpiEvent temp : tempSendList)
            {
                if(temp.getInstance().equals("All")||temp.getInstance().equals("this"))
                {
                    speed = temp.getStrValue();
                    break;
                }
            }
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
     * 取得活动告警
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map getAlarmList(HttpServletRequest request) throws Exception {

        String path = request.getParameter("treeId");

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        Map m = new HashMap();
        m.put("ID", 1);
        m.put("LEVEL", 0);
        m.put("STATUS", 0);
        m.put("MO_CAPTION", "192.168.0.1");
        m.put("CONTENT", "192.168.0.1 down");
        m.put("OCCUR_TIMES", "2013-3-28 14:57");
        m.put("AMOUNT", 1);
        list.add(m);
        
        m = new HashMap();
        m.put("ID", 2);
        m.put("LEVEL", 1);
        m.put("STATUS", 1);
        m.put("MO_CAPTION", "192.168.0.2");
        m.put("CONTENT", "192.168.0.2 down");
        m.put("OCCUR_TIMES", "2013-3-28 14:57");
        m.put("AMOUNT", 1);
        list.add(m);
        
        m = new HashMap();
        m.put("ID", 3);
        m.put("LEVEL", 2);
        m.put("STATUS", 2);
        m.put("MO_CAPTION", "192.168.0.3");
        m.put("CONTENT", "192.168.0.3 down");
        m.put("OCCUR_TIMES", "2013-3-28 14:57");
        m.put("AMOUNT", 1);
        list.add(m);
        
        m = new HashMap();
        m.put("ID", 4);
        m.put("LEVEL", 3);
        m.put("STATUS", 3);
        m.put("MO_CAPTION", "192.168.0.4");
        m.put("CONTENT", "192.168.0.4 down");
        m.put("OCCUR_TIMES", "2013-3-28 14:57");
        m.put("AMOUNT", 1);
        list.add(m);
        
        m = new HashMap();
        m.put("ID", 5);
        m.put("LEVEL", 4);
        m.put("STATUS", 9);
        m.put("MO_CAPTION", "192.168.0.5");
        m.put("CONTENT", "192.168.0.5 down");
        m.put("OCCUR_TIMES", "2013-3-28 14:57");
        m.put("AMOUNT", 1);
        list.add(m);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("alarmTotal", list.size());
        map.put("alarmData", list);
        return map;
    }
    
    /**
     * GROUP TAB的cpu view信息
     * 
     * @param moId
     * @param condition
     * @return List<Map>
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public List<Map> getNetworkView(String moId){
        
        List<Map> list = new ArrayList<Map>();
        Map m = new HashMap();
        m.put("NETWORK_KBPS", 50);
        
        list.add(m);
        
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
    public List<Map> getDiskView(String moId){
        
        List<Map> list = new ArrayList<Map>();
        Map m = new HashMap();
        m.put("DISK_KBPS", 80);
        
        list.add(m);
        
        return list;
    }
    
    /**
     * 取得活动告警
     * 
     * @param request
     * @return
     * @throws Exception
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public Map getNetworkList(HttpServletRequest request) throws Exception {

        String path = request.getParameter("treeId");

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        Map m = new HashMap();
        m.put("ID", 1);
        m.put("VALUE", "NETWORK_JUMP_1");
        list.add(m);
        
        m = new HashMap();
        m.put("ID", 2);
        m.put("VALUE", "NETWORK_JUMP_2");
        list.add(m);
        
        m = new HashMap();
        m.put("ID", 3);
        m.put("VALUE", "NETWORK_JUMP_3");
        list.add(m);
        
        m = new HashMap();
        m.put("ID", 4);
        m.put("VALUE", "NETWORK_JUMP_4");
        list.add(m);
        
        m = new HashMap();
        m.put("ID", 5);
        m.put("VALUE", "NETWORK_JUMP_5");
        list.add(m);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("networkTotal", list.size());
        map.put("networkData", list);
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
    public Map getDiskList(HttpServletRequest request) throws Exception {

        String path = request.getParameter("treeId");

        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();

        Map m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "81G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "82G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "83G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "84G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "85G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "86G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "77G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "88G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "88G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "88G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "88G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "88G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "88G");
        list.add(m);
        
        m = new HashMap();
        m.put("DATA_STORE", "fxiekd11");
        m.put("SIZE", "100G");
        m.put("FREE", "88G");
        list.add(m);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("diskTotal", list.size());
        map.put("diskData", list);
        return map;
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
            List<KpiEvent> list = kpiInfo.getKpiList(moId, "", "5101103004");
            List<Map> bucketList = new ArrayList();
            for(int i = 0; i < list.size(); i++)
            {
                Map basic = new HashMap();
                basic.put("usage",list.get(i).getStrValue());
                basic.put("name",list.get(i).getInstance());
                basic.put("kpi",list.get(i).getNumValue()/1024);
                basic.put("level",list.get(i).getThresholdLevel());
                basic.put("src","../physicalEngine/littleColumn.jsp?kpi="+list.get(i).getNumValue()/1024+"&level="+list.get(i).getThresholdLevel());
                
                bucketList.add(basic);
            }

            buckets.put("buckets",bucketList);
            
        }
        else{
            logger.warn("moId is null");
        }
        return buckets;  
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
    public Map getBasicInfoList(HttpServletRequest request) throws Exception {

        String treeId = request.getParameter("treeId");
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        
        String moId=treeId;
        
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map m = new HashMap();
        m.put("KEY", bundle.getString("hostName"));
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "5102100006"));
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", bundle.getString("hostCpuModel"));
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "5102100009"));
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", bundle.getString("hostCpuAverageFrequency"));
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "5102100007")+"MHz");
        list.add(m);

        m = new HashMap();
        m.put("KEY", bundle.getString("hostCpuCoresNumber"));
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "5102100008")+"个");
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", bundle.getString("hostPowerStatus"));
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "5102100016"));
        list.add(m);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("basicTotal", list.size());
        map.put("basicData", list);
        
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
    public Map getNetworkInfoData(HttpServletRequest request) throws Exception {

        String treeId = request.getParameter("treeId");
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        
        String moId=treeId;
        
        List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
        Map m = new HashMap();
        m.put("KEY", bundle.getString("theNetworkName"));
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "5102100019"));
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", bundle.getString("hostNetworkCardNumber"));
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "5102100012")+"个");
        list.add(m);
        
        m = new HashMap();
        m.put("KEY", bundle.getString("averageNetworkUsage"));
        m.put("VAL",kpiInfo.getCurrentKpiValue(moId, "5101105004")+"KBps");
        list.add(m);
        
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("basicTotal", list.size());
        map.put("basicData", list);
        
        return map;
    }
    
}
