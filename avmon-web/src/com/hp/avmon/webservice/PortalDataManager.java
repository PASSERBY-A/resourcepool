package com.hp.avmon.webservice;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.SqlManager;

@Controller
@RequestMapping("/portal/WidgetService/*")
public class PortalDataManager {
	
	private static final Log log = LogFactory.getLog(PortalDataManager.class);
	
    private JdbcTemplate jdbc;
    
    /**
     * 构造函数
     */
    public PortalDataManager() {
    	// 加载连接对象
    	jdbc = SpringContextHolder.getBean("jdbcTemplate");
    }
    
    /**
     * 读取当前活动告警数量
     * 
     * return map  各个级别对应的个数 LEVEL0:10,LEVEL1:20
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @RequestMapping(value = "alarmCount")
	public void alarmCount(HttpServletRequest request, 
    		PrintWriter writer) {
    	Map<Object, Object> resultMap = new HashMap<Object, Object>();
   	 	// 获取sql
    	String alarmSql = String.format(SqlManager.getSql(PortalDataManager.class, "getAlarmCnt"));
 		List<Map> list = jdbc.queryForList(alarmSql);
 		
 		if (list != null && list.size() != 0) {
 			for (Map temp : list) {
 				resultMap.put(temp.get("CURRENT_GRADE"), temp.get("ALARM_CNT"));
 			}
 			
 			if (!resultMap.containsKey("LEVEL0")) {
 				resultMap.put("LEVEL0", 0);
 			}
 			if (!resultMap.containsKey("LEVEL1")) {
 				resultMap.put("LEVEL1", 0);
 			}
 			if (!resultMap.containsKey("LEVEL2")) {
 				resultMap.put("LEVEL2", 0);
 			}
 			if (!resultMap.containsKey("LEVEL3")) {
 				resultMap.put("LEVEL3", 0);
 			}
 			if (!resultMap.containsKey("LEVEL4")) {
 				resultMap.put("LEVEL4", 0);
 			}
 		} else {
 			// 各个级别告警为0
 			resultMap.put("LEVEL0", 0);
 			resultMap.put("LEVEL1", 0);
 			resultMap.put("LEVEL2", 0);
 			resultMap.put("LEVEL3", 0);
 			resultMap.put("LEVEL4", 0);
 		}

 		// 总告警数量
 		int total = 0;
 		total = MyFunc.nullToInteger(resultMap.get("LEVEL0")) 
 				+ MyFunc.nullToInteger(resultMap.get("LEVEL1"))
 				+ MyFunc.nullToInteger(resultMap.get("LEVEL2"))
 				+ MyFunc.nullToInteger(resultMap.get("LEVEL3"))
 				+ MyFunc.nullToInteger(resultMap.get("LEVEL4"));
 		resultMap.put("TOTAL", total);
 		
 		String json = JackJson.fromObjectToJson(resultMap);
 		log.info("PortalDataManager alarmCount:" + json);
 		
		writer.write(json);
        writer.flush();
        writer.close();
    }
    
    /**
     * 读取当前当前监控对象个数
     * 
     * return map  各种对象的数量 HOST_CNT:50,DB_CNT:2
     */
    @RequestMapping(value = "performanceCount")
    public void performanceCount(HttpServletRequest request, 
    		PrintWriter writer) {
   	 	Map<String, Object> resultMap = new HashMap<String, Object>();
   	 	// 获取sql
   	 	String hostSql = String.format(SqlManager.getSql(PortalDataManager.class, "getHostCnt"), "'HOST_%'");
   	 	String dbSql = String.format(SqlManager.getSql(PortalDataManager.class, "getDbCnt"));
   	 	String hwSql = String.format(SqlManager.getSql(PortalDataManager.class, "getHardwareCnt"));
   	 	String netSql = String.format(SqlManager.getSql(PortalDataManager.class, "getNetworkCnt"));
   	 	String vmSql = String.format(SqlManager.getSql(PortalDataManager.class, "getVmwareCnt"));
   	 
   	 	int HOST_CNT = jdbc.queryForInt(hostSql);
   	 	int DB_CNT = jdbc.queryForInt(dbSql);
   	 	int HW_CNT = jdbc.queryForInt(hwSql);
   	 	int NET_CNT = jdbc.queryForInt(netSql);
   	 	int VM_CNT = jdbc.queryForInt(vmSql);
   	 	
   	 	// 操作系统
   	 	resultMap.put("HOST_CNT", HOST_CNT);
   	 	// 数据库
   	 	resultMap.put("DB_CNT", DB_CNT);
   	 	// 硬件 
   	 	resultMap.put("HW_CNT", HW_CNT);
   	 	// 网络
   	 	resultMap.put("NET_CNT", NET_CNT);
   	 	// 虚拟机
   	 	resultMap.put("VM_CNT", VM_CNT);
   	 	
   	 	int total = 0;
		total = MyFunc.nullToInteger(resultMap.get("HOST_CNT")) 
				+ MyFunc.nullToInteger(resultMap.get("DB_CNT"))
				+ MyFunc.nullToInteger(resultMap.get("HW_CNT"))
				+ MyFunc.nullToInteger(resultMap.get("NET_CNT"))
				+ MyFunc.nullToInteger(resultMap.get("VM_CNT"));
		resultMap.put("TOTAL", total);

		String json = JackJson.fromObjectToJson(resultMap);
		log.info("PortalDataManager performanceCount:" + resultMap);
		
		writer.write(json);
        writer.flush();
        writer.close();
    }
}