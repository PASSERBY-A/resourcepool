package com.hp.avmon.performance.web;

import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.service.DashboardService;
import com.hp.avmon.performance.service.PerformanceService;
import com.hp.avmon.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/dashboard/*")
public class DashboardController {
	
	private static final Log logger = LogFactory.getLog(DashboardController.class);
	
    @Autowired
    private DashboardService dashboardService;
    
	@Autowired
	private PerformanceService performanceService;
	
    @RequestMapping(value = "kpilist/index")
    public String dashboardKpiList(HttpServletRequest request,Model model) {
        String moId=request.getParameter("moId");
        //String type=request.getParameter("type");
        model.addAttribute("moId", moId);
        return "/dashboard/kpilist/index";
    }
    
    @RequestMapping(value = "resourcelist/index")
    public String dashboardResourceList(HttpServletRequest request,Model model) {
        String moId=request.getParameter("moId");
        //String type=request.getParameter("type");
        model.addAttribute("moId", moId);
        return "/dashboard/resourcelist/index";
    }
    
    @RequestMapping(value = "totalview/index")
    public String totalViewList(HttpServletRequest request,Model model) {
        String moId=request.getParameter("moId");
        //String type=request.getParameter("type");
        model.addAttribute("moId", moId);
        return "/dashboard/totalview/index";
    }
    
    @RequestMapping(value = "host/index")
    public String dashboard(@RequestParam("moId") String mo,Locale locale, Model model) {
        model.addAttribute("mo", mo );
        model.addAttribute("cpuCount",128);
        return "/dashboard/host/index";
    }
    
    @RequestMapping(value = "host/alldata")
    public void getHostAllData(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String userId=Utils.getCurrentUserId(request);
        String moId=request.getParameter("mo");

        String json=dashboardService.getHostDashboardJson(moId);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "host/cpu")
    public void getHostCpuData(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String userId=Utils.getCurrentUserId(request);
        String moId=request.getParameter("mo");

        String json=JackJson.fromObjectToJson(dashboardService.getHostCpuInfo(moId));
        
        
        writer.write(json);
        writer.flush();
        writer.close();
    }   
    
    @RequestMapping(value = "host/basicInfo")
    public void getHostBasicInfo(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        String moId=request.getParameter("mo");
        String json=JackJson.fromObjectToJson(dashboardService.getHostBasicInfo(moId));
        writer.write(json);
        writer.flush();
        writer.close();
    }  
    
    @RequestMapping(value = "host/mem")
    public void getHostMemInfo(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        String moId=request.getParameter("mo");
        String json=JackJson.fromObjectToJson(dashboardService.getHostMemInfo(moId));
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "host/swap")
    public void getHostSwapInfo(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        String moId=request.getParameter("mo");
        String json=JackJson.fromObjectToJson(dashboardService.getHostSwapInfo(moId));
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "host/newAlarm")
    public void getHostNewAlarm(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        String moId=request.getParameter("mo");
        String json=JackJson.fromObjectToJson(dashboardService.getHostNewAlarm(moId));
        writer.write(json);
        writer.flush();
        writer.close();
    }  
    
    @RequestMapping(value = "host/lvs")
    public void getHostLvs(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        String moId=request.getParameter("mo");
        String json=JackJson.fromObjectToJson(dashboardService.getHostLvs(moId));
        writer.write(json);
        writer.flush();
        writer.close();
    }  
    
    @RequestMapping(value = "host/networks")
    public void getHostNetworks(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        String moId=request.getParameter("mo");
        String json=JackJson.fromObjectToJson(dashboardService.getHostNetworks(moId));
        writer.write(json);
        writer.flush();
        writer.close();
    }  
    @RequestMapping(value = "host/keyProcess")
    public void getHostKeyProcess(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        String moId=request.getParameter("mo");
        String json=JackJson.fromObjectToJson(dashboardService.getHostKeyProcess(moId));
        writer.write(json);
        writer.flush();
        writer.close();
    }  
    
    
    @RequestMapping(value = "host/diskIo")
    public void getHostDiskIo(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        String moId=request.getParameter("mo");
        String json=JackJson.fromObjectToJson(dashboardService.getHostDiskIo(moId));
        writer.write(json);
        writer.flush();
        writer.close();
    }  
    
    @RequestMapping(value = "vmList/index")
    public String vmList(@RequestParam("moId") String mo,Locale locale, Model model) {
        model.addAttribute("mo", mo );
        model.addAttribute("cpuCount",128);
        return "/dashboard/vmList/index";
    }
    //pingStatus
    @RequestMapping(value = "pingStatus/index")
    public String pingStatus(@RequestParam("moId") String mo,Locale locale, Model model) {
        model.addAttribute("mo", mo );
        return "/dashboard/pingStatus/index";
    }
}
