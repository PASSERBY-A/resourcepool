package com.hp.avmon.performance.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.avmon.performance.service.AlarmSearchService;
import com.hp.avmon.performance.service.PerformanceDBService;
import com.hp.avmon.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/performancedb/*")
public class PerformanceDBController {
	
	private static final Log logger = LogFactory.getLog(PerformanceDBController.class);
	

	@Autowired
	private PerformanceDBService performanceService;
	
    @Autowired
    private AlarmSearchService alarmSearchService;

	/**
	 * 获取数据库的基本信息
	 * @param request
	 * @return
	 */
    @RequestMapping(value = "/getBasicInfoDataForDB")
    @ResponseBody
    public Map getBasicInfoDataForDB(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getBasicInfoForDBList(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getBasicInfoDataForDB",e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取数据库状态信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDBStatusInfoData")
    @ResponseBody
    public Map getDBStatusInfoData(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getDBStatusInfoData(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getDBStatusInfoData",e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取数据库SGA信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDBSgaInfoData")
    @ResponseBody
    public Map getDBSgaInfoData(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getDBSgaInfoData(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getDBSgaInfoData",e);
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 获取数据库命中率信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDBHitPercentInfoData")
    @ResponseBody
    public Map getDBHitPercentInfoData(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getDBHitPercentInfoData(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getDBHitPercentInfoData",e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取数据库session率信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDBSessionsPercentInfoData")
    @ResponseBody
    public Map getDBSessionsPercentInfoData(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getDBSessionsPercentInfoData(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getDBSessionsPercentInfoData",e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取数据库事件信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getEventInfoForDBList")
    @ResponseBody
    public Map getEventInfoForDBList(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getEventInfoForDBList(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getEventInfoForDBList",e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取数据库主机负载信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDBHostUsageInfoData")
    @ResponseBody
    public Map getDBHostUsageInfoData(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getDBHostUsageInfoData(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getDBSessionsPercentInfoData",e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取数据库表空间读写操作次数
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDBReadWriteInfoData")
    @ResponseBody
    public Map getDBReadWriteInfoData(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getDBReadWriteInfoData(request);
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getDBReadWriteInfoData",e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取数据库提交回滚率
     * @param request
     * @return
     */
    @RequestMapping(value = "/getDBCommitRollbackInfoData")
    @ResponseBody
    public Map getDBCommitRollbackInfoData(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getDBCommitRollbackInfoData(request);
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getDBCommitRollbackInfoData",e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取数据库表空间信息
     * @param request
     * @return
     */
    @RequestMapping(value = "/getTablspaceInfoForDB")
    @ResponseBody
    public Map getTablspaceInfoForDB(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getTablspaceInfoForDB(request);
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getTablspaceInfoForDB",e);
            e.printStackTrace();
        }
        return map;
    }
    
    /**
     * 获取数据库整体io指标
     * @param request
     * @return
     */
    @RequestMapping(value = "/getPhysicalInfoForDB")
    @ResponseBody
    public Map getPhysicalInfoForDB(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = performanceService.getPhysicalInfoForDB(request);
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getPhysicalInfoForDB",e);
            e.printStackTrace();
        }
        return map;
    }
    
    @RequestMapping("dbKpiChartData")
    public String dbKpiChartData(
			HttpServletResponse response,
			HttpServletRequest request){
		String json = "";
		try {
			String ampInstId = request.getParameter("ampInstId");
			String kpiCode = request.getParameter("kpiCode");
			String moId = request.getParameter("moId");
			
			String startTime = request.getParameter("startTime") + ":00";//":00:00";
			String endTime = request.getParameter("endTime") + ":00";//":00:00";
			
			String grainsize = request.getParameter("grainsize");

			List<Map<String, Object>> kpiValueList = performanceService.getDBKpiChartData(ampInstId,kpiCode,moId,startTime,endTime,grainsize);
			if(kpiValueList == null || kpiValueList.size()<1){
				json = "{\"root\":[{\"time\":\"0\"}],\"moId\":\"" + moId + "\"}";
			}else{
				JSONArray jsonArray = JSONArray.fromObject(kpiValueList);
				json = "{\"root\":" + jsonArray.toString() + ",\"moId\":\"" + moId + "\"}";
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" dynamicKpiChartData",e);
		}
		
		return Utils.responsePrintWrite(response,json,null);
	}
    
    @RequestMapping("getDbKpiChartInstance")
    @ResponseBody
    public List getDbKpiChartInstance(
			HttpServletResponse response,
			HttpServletRequest request){
		//String json = "";
    	List<Map<String, Object>> kpiValueList = new ArrayList<Map<String,Object>>();
		try {
			String ampInstId = request.getParameter("ampInstId");
			String kpiCode = request.getParameter("kpiCode");
			String moId = request.getParameter("moId");
			
			String startTime = request.getParameter("startTime") + ":00";//":00:00";
			String endTime = request.getParameter("endTime") + ":00";//":00:00";
			
			String grainsize = request.getParameter("grainsize");

			kpiValueList = performanceService.getKpiInstance(ampInstId,kpiCode,moId,startTime,endTime,grainsize);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" dynamicKpiChartData",e);
		}
		return kpiValueList;
		//return Utils.responsePrintWrite(response,json,null);
	}
}
