package com.hp.avmon.performance.web;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.service.StoreService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/dashboard/store/*")
public class StoreController {
	
	private static final Log logger = LogFactory.getLog(DeviceStatusController.class);
	
    @Autowired
    private StoreService storeService;
    
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
        String json = JackJson.fromObjectToJson(storeService.getStoreBasicInfo(moId));
        
        writer.write(json);
        writer.flush();
        writer.close();
    }  
    
	/**
	 * 取得最新告警列表
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @RequestMapping(value = "/newAlarm")
    public void getHostNewAlarm(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(storeService.getStoreNewAlarm(moId));
        
        writer.write(json);
        writer.flush();
        writer.close();
    }  
    
    /**
	 * 取得网络接口列表
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @RequestMapping(value = "/networks")
    public void getHostNetworks(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(storeService.getStoreNetworks(moId));
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    /**
	 * 取得组信息列表
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @RequestMapping(value = "/groupInfo")
    public void getGroupInfo(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(storeService.getStoreGroupInfo(moId));
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    /**
	 * 取得盘信息列表
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @RequestMapping(value = "/diskInfo")
    public void getDiskInfo(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(storeService.getStoreDiskInfo(moId));
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    /**
	 * CPU曲线图
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @RequestMapping(value = "/cpu")
    public void getHostCpuData(HttpServletRequest request, PrintWriter writer) throws Exception {

        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(storeService.getStoreCpuInfo(moId));
        logger.debug("cpuData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    } 
    
    /**
	 * iops曲线图
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @RequestMapping(value = "/iops")
    public void getIops(HttpServletRequest request, PrintWriter writer) throws Exception {
        String moId = request.getParameter("mo");
        String json = JackJson.fromObjectToJson(storeService.getIopsInfo(moId));
        logger.debug("iopsData:" + json);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
//    /**
//	 * delay曲线图
//	 * 
//	 * @param request
//	 * @param writer
//	 * @return
//	 */
//    @RequestMapping(value = "/delay")
//    public void getDelay(HttpServletRequest request, PrintWriter writer) throws Exception {
//        String moId=request.getParameter("mo");
//        String json=JackJson.fromObjectToJson(storeService.getHostMemInfo(moId));
//        
//        writer.write(json);
//        writer.flush();
//        writer.close();
//    }
    
//	/**
//	 * 取得cpu View状态列表信息
//	 * 
//	 * @param request
//	 * @param writer
//	 * @return
//	 */
//    @RequestMapping(value = "/cpuView")
//    public void getCpuViewData(HttpServletRequest request, PrintWriter writer) throws Exception {
//    	// 根据传入的主机类型得到相关主机
//    	// 再根据主机的警告发生日期过滤
//        String moId = request.getParameter("mo");
//
//        String json = JackJson.fromObjectToJson(storeService.getGroupCpuView(moId));
//        String jsonData = "{record:" + json + "}";
//        logger.info("getCpuViewData:" + jsonData);
//        
//        writer.write(jsonData);
//        writer.flush();
//        writer.close();
//    }
    
	/**
	 * 取得组TAB View状态列表信息
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @RequestMapping(value = "/groupView")
    public void getGroupViewData(HttpServletRequest request, PrintWriter writer) throws Exception {
    	// 根据传入的主机类型得到相关主机
    	// 再根据主机的警告发生日期过滤
        String moId = request.getParameter("mo");

        String json = JackJson.fromObjectToJson(storeService.getGroupView(moId));
        String jsonData = "{record:" + json + "}";
        logger.debug("groupViewData:" + jsonData);
        
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
	/**
	 * 取得前端口TAB View状态列表信息
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @RequestMapping(value = "/portView")
    public void getPortViewData(HttpServletRequest request, PrintWriter writer) throws Exception {
    	// 根据传入的主机类型得到相关主机
    	// 再根据主机的警告发生日期过滤
        String moId = request.getParameter("mo");

        String json = JackJson.fromObjectToJson(storeService.getPortView(moId));
        String jsonData = "{record:" + json + "}";
        logger.debug("getCpuViewData:" + jsonData);
        
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }	
    
    /**
	 * 取得盘TAB View状态列表信息
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

        String json = JackJson.fromObjectToJson(storeService.getDiskView(moId));
        String jsonData = "{record:" + json + "}";
        logger.debug("getCpuViewData:" + jsonData);
        
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
       
}
