package com.hp.avmon.performance.web;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.service.PhysicalEngineService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/dashboard/physicalEngine/*")
public class PhysicalEngineController {
    
    private static final Log logger = LogFactory.getLog(MainEngineController.class);
    
    @Autowired
    private PhysicalEngineService physicalEngineService;
    
//  /**
//   * 取得常规信息
//   * 
//   * @param request
//   * @param writer
//   * @return
//   */
//    @RequestMapping(value = "/basicInfo")
//    public void getHostBasicInfo(HttpServletRequest request, PrintWriter writer) throws Exception {
//      
//        String moId = request.getParameter("mo");
//        String json = JackJson.fromObjectToJson(physicalEngineService.getStoreBasicInfo(moId));
//        
//        writer.write(json);
//        writer.flush();
//        writer.close();
//    }
    
    /**
     * 取得常规信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/basicInfo")
    public void getHostBasicInfo(HttpServletRequest request, PrintWriter writer) throws Exception {
        
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMoBasicInfo(moId));
        
        writer.write(json);
        writer.flush();
        writer.close();
    }  
    
    
    /**
     * 取得活动告警列表
     * 
     * @param response
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/getBasicInfoData")
    public void getBasicInfoData(
            HttpServletRequest request,
            PrintWriter writer){
        try {
            @SuppressWarnings("rawtypes")
            Map map = physicalEngineService.getBasicInfoList(request);
            String jsonData = JackJson.fromObjectToJson(map);
            logger.debug("AlarmData-" + jsonData);
            // 加入check
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 取得网络基本信息
     * 
     * @param response
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/getNetworkInfoData")
    public void getNetworkInfoData(
            HttpServletRequest request,
            PrintWriter writer){
        try {
            @SuppressWarnings("rawtypes")
            Map map = physicalEngineService.getNetworkInfoData(request);
            String jsonData = JackJson.fromObjectToJson(map);
            logger.debug("AlarmData-" + jsonData);
            // 加入check
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    /**
     * 取得网络信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/networkInfo")
    public void getHostNetworks(HttpServletRequest request, PrintWriter writer) throws Exception {
        
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMainEngineNetworks(moId));
        
        writer.write(json);
        writer.flush();
        writer.close();
    }

    
    /**
     * CPU 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/cpuUse")
    public void getCpuUseData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMainEngineCpuInfo(moId));
        logger.debug("cpuUseData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * memory 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/memUse")
    public void getMemUseData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMainEngineMem(moId));
        logger.debug("memUseData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * memory  百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/memBasic")
    public void getMemBasicData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMainEngineMemInfo(moId));
        logger.debug("courseBasicData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    
    /**
     * 网络传输 发送包曲线图
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/networkSend")
    public void getNetworkSendData(HttpServletRequest request, PrintWriter writer) throws Exception {

    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMainEngineNetworkSend(moId,bundle));
        logger.debug("networkSend Data:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * 网络传输 接收包 曲线图
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/networkReceive")
    public void getNetworkReceiveData(HttpServletRequest request, PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMainEngineNetworkReceive(moId,bundle));
        logger.debug("networkReceive Data:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    
    /**
     * 换页文件传输 发送包曲线图
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/diskWrite")
    public void getDiskWriteData(HttpServletRequest request, PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMainEngineDiskWrite(moId,bundle));
        logger.debug("courseSend Data:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * 换页文件传输 接收包 曲线图
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/diskRead")
    public void getDiskReadData(HttpServletRequest request, PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMainEngineDiskRead(moId,bundle));
        logger.debug("courseReceive Data:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * 取得活动告警列表
     * 
     * @param response
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/getAlarmData")
    public void getActiveAlarmData(
            HttpServletRequest request,
            PrintWriter writer){
        try {
            @SuppressWarnings("rawtypes")
            Map map = physicalEngineService.getAlarmList(request);
            String jsonData = JackJson.fromObjectToJson(map);
            logger.debug("AlarmData-" + jsonData);
            // 加入check
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 取得组TAB View状态列表信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/networkView")
    public void getNetworkViewData(HttpServletRequest request, PrintWriter writer) throws Exception {
        // 根据传入的主机类型得到相关主机
        // 再根据主机的警告发生日期过滤
        String moId = request.getParameter("mo");

        String json = JackJson.fromObjectToJson(physicalEngineService.getNetworkView(moId));
        String jsonData = "{record:" + json + "}";
        logger.debug("networkView Data:" + jsonData);
        
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    /**
     * 取得组TAB View状态列表信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/diskView")
    public void getDiskViewData(HttpServletRequest request, PrintWriter writer) throws Exception {
        // 根据传入的主机类型得到相关主机
        // 再根据主机的警告发生日期过滤
        String moId = request.getParameter("mo");

        String json = JackJson.fromObjectToJson(physicalEngineService.getDiskView(moId));
        String jsonData = "{record:" + json + "}";
        logger.debug("diskView Data:" + jsonData);
        
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    /**
     * 取得活动告警列表
     * 
     * @param response
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/getNetworkListData")
    public void getNetworkListData(
            HttpServletRequest request,
            PrintWriter writer){
        try {
            @SuppressWarnings("rawtypes")
            Map map = physicalEngineService.getNetworkList(request);
            String jsonData = JackJson.fromObjectToJson(map);
            logger.debug("NetworkData-" + jsonData);
            // 加入check
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 取得活动告警列表
     * 
     * @param response
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/getDiskListData")
    public void getDiskListData(
            HttpServletRequest request,
            PrintWriter writer){
        try {
            @SuppressWarnings("rawtypes")
            Map map = physicalEngineService.getDiskList(request);
            String jsonData = JackJson.fromObjectToJson(map);
            logger.debug("DiskList-" + jsonData);
            // 加入check
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Disk 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/diskUse")
    public void getDiskUseData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("moId");
        String json = JackJson.fromObjectToJson(physicalEngineService.getMainEngineDisk(moId));
        logger.debug("diskUseData:" + json);

        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    
    
}
