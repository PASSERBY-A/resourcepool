package com.hp.avmon.config.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
@RequestMapping("/kpiCompare/*")
public class KpiCompareController {
	
	private static final Log logger = LogFactory.getLog(KpiCompareController.class);
	
    @RequestMapping("index")
    public String sysMain(HttpServletRequest request,Model model) throws IOException {
    	String type=request.getParameter("type");
    	model.addAttribute("moId", request.getParameter("moId"));
    	if("kpiCompare".equalsIgnoreCase(type)){
    		return "kpiCompare/index"; 
    	}else{
    		logger.error("KpiCompareController type error!");
    		return "commons/timeout";
    	}
        
    }
    	
}
