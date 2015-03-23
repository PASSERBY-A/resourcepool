package com.hp.avmon.kpiAnalysis.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
@RequestMapping("/kpiAnalysis/*")
public class KpiAnalysisController {
	
	private static final Log logger = LogFactory.getLog(KpiAnalysisController.class);
	
    @RequestMapping("index")
    public String index(HttpServletRequest request,Model model) throws IOException {
    	logger.info("KpiAnalysisController.....");
		return "kpiAnalysis/index"; 
    }
}
