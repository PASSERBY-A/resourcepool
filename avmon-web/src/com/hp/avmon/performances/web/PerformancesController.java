package com.hp.avmon.performances.web;

import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.service.PerformanceService;
import com.hp.avmon.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/performances/*")
public class PerformancesController {
	
	private static final Log logger = LogFactory.getLog(PerformancesController.class);
	

	@Autowired
	private PerformanceService performanceService;
	
	
//    @RequestMapping(value = "index")
//    public void index(@RequestParam("mo") String mo,Locale locale, Model model) {
//
//        model.addAttribute("mo", mo );
//        
//    }
	@RequestMapping(value = "index")
	public String index(Locale locale, Model model) {

		return "/performances/index";

	}

    

    
	 
    @RequestMapping(value = "menuTree")
    public void getMenuTree(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String userId=Utils.getCurrentUserId(request);
        String parentId=request.getParameter("id");
        String checkbox = request.getParameter("checkBox");
        if(parentId==null||parentId.equals("")){
            parentId="root";
        }
        String json=performanceService.getMoTreeJson(userId,parentId,checkbox,bundle);

        if("true".equalsIgnoreCase(checkbox)){
        	json = json.replace("\"leaf\":\"true\"", "\"leaf\":\"true\",\"checked\":false");
        }
//        logger.debug(json);
        writer.write(json);
        writer.flush();
        writer.close();
    }    
    
    @RequestMapping(value = "performanceSummaryList")
    public void performanceSummaryList(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String userId=Utils.getCurrentUserId(request);
        String parentId=request.getParameter("id");
        String businessSystem=request.getParameter("biz");
        if(businessSystem!=null){
            if(businessSystem.equals("null")){
                businessSystem="";
            }
            else{
                businessSystem=URLDecoder.decode(businessSystem,"UTF-8");
            }
        }
        if(parentId==null||parentId.equals("")){
            parentId="root";
        }
        Map map=performanceService.getPerformanceSummaryList(parentId,businessSystem,bundle);
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "getMoPath")
    public void getMoPath(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("moId");
        
        Map map=performanceService.getMoPath(moId);
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    
    @RequestMapping(value = "totalViewData")
    public void totalViewData(HttpServletRequest request,PrintWriter writer) throws Exception {
        
       /* Map map=performanceService.totalViewData(request);
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();*/
    }
    
    @RequestMapping(value = "kpiValueList")
    public void kpiValueList(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String userId=Utils.getCurrentUserId(request);
        String moId=request.getParameter("mo");
        String monitorInstanceId=request.getParameter("monitorInstanceId");
        String kpiId=request.getParameter("kpiId");
        
        String json= performanceService.kpiList(moId,monitorInstanceId,kpiId); 
        //String json=JackJson.fromObjectToJson(map);
        if(json==null)json="";
        writer.write(json);
        writer.flush();
        writer.close();
    }    

    /**
     * 获得kpi信息分类
     * @param request
     * @param writer
     * @throws Exception
     */
    @RequestMapping(value = "getGroups")
    public void getGroups(HttpServletRequest request,PrintWriter writer) throws Exception {
        
        /*String json= performanceService.getGroups(request); 
        //String json=JackJson.fromObjectToJson(map);
        if(json==null)json="";
        writer.write(json);
        writer.flush();*/

    }
    
    /**
     * 获得kpi信息分类
     * @param request
     * @param writer
     * @throws Exception
     */
    @RequestMapping(value = "getInnderGrid")
    public void getInnderGrid(HttpServletRequest request,PrintWriter writer) throws Exception {
    	/*Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String userId=Utils.getCurrentUserId(request);
        String groupName=request.getParameter("groupName");
        String json= performanceService.getInnderGrid(groupName,userId,bundle); 
        logger.debug("getInnderGrid"+json);
        if(json==null)json="";
        writer.write(json);
        writer.flush();*/

    }
    
}
