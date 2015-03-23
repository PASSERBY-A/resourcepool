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
import com.hp.avmon.performance.service.MainEngineService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/dashboard/mainEngine/*")
public class MainEngineController {
    private static final Log logger = LogFactory.getLog(MainEngineController.class);
    
    @Autowired
    private MainEngineService mainEngineService;
    
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
        String json = JackJson.fromObjectToJson(mainEngineService.getMoBasicInfo(moId));
        
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
            Map map = mainEngineService.getBasicInfoList(request);
            String jsonData = JackJson.fromObjectToJson(map);
//            logger.debug("AlarmData-" + jsonData);
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
        
        String moId = request.getParameter("treeId"/*"mo"*/);
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineNetworks(moId));
        
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
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineCpuInfo(moId));
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
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineMem(moId));
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
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineMemInfo(moId));
        logger.debug("courseBasicData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * 进程  百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/courseUse")
    public void getCourseUseData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineCourse(moId));
        logger.debug("courseUseData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * 进程  百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/courseBasic")
    public void getCourseBasicData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineCourseInfo(moId));
        logger.debug("courseBasicData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * 换页文件 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/skipFileUse")
    public void getSkipFileUseData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineSkipFile(moId));
        logger.debug("skipFileUseData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 

    /**
     * 换页文件 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/skipFileBasic")
    public void getSkipFileBasicData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineSkipFileInfo(moId));
        logger.debug("skipFileBasicData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
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
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineDisk(moId));
        logger.debug("diskUseData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 

    
    /**
     * Disk 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/diskBasic")
    public void getDiskBasicData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineDiskInfo(moId));
        logger.debug("diskBasicData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * 多桶信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/multipleBucket")
    public void getMultipleBucket(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMultipleBucketInfo(moId));
        logger.debug("multipleBucket:" + json);
        
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
            String moId = request.getParameter("mo");
            Map map = mainEngineService.getAlarmList(moId,false);
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
     * 取得活动告警列表
     * 
     * @param response
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/getLogAlarmData")
    public void getLogAlarmData(
            HttpServletRequest request,
            PrintWriter writer){
        try {
            @SuppressWarnings("rawtypes")
            String moId = request.getParameter("mo");
            Boolean isLogError = true;
            Map map = mainEngineService.getAlarmList(moId,isLogError);
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
     * 取得活动告警列表
     * 
     * @param response
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/getMPLogData")
    public void getMPLogData(
            HttpServletRequest request,
            PrintWriter writer){
        try {
            @SuppressWarnings("rawtypes")
            String moId = request.getParameter("mo");
            String json = JackJson.fromObjectToJson(mainEngineService.getMPLogList(moId));
            
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    
    /**
     * file system 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/fileSysUse")
    public void getFileSysUseData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineFileSys(moId));
        logger.debug("fileSysUseData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
     * file system 百分比信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/fileSysBasic")
    public void getFileSysBasicData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineFileSysInfo(moId));
        logger.debug("fileSysBasicData:" + json);
        
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
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineNetworkSend(moId,bundle));
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
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineNetworkReceive(moId,bundle));
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
    @RequestMapping(value = "/skipfilePagein")
    public void getSkipfilePageinData(HttpServletRequest request, PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineSkipfilePagein(moId,bundle));
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
    @RequestMapping(value = "/skipfilePageout")
    public void getSkipfilePageoutData(HttpServletRequest request, PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineSkipfilePageout(moId,bundle));
        logger.debug("courseReceive Data:" + json);
        
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
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineDiskWrite(moId,bundle));
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

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getMainEngineDiskRead(moId));
        logger.debug("courseReceive Data:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    /**
     * 取得日志错误信息
     * 
     * @param request
     * @param writer
     * @return
     */
    @RequestMapping(value = "/getLogError")
    public void getLogError(HttpServletRequest request, PrintWriter writer) throws Exception {
        
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(mainEngineService.getLogError(moId));
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
}
