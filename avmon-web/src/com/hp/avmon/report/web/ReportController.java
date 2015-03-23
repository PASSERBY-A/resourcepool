package com.hp.avmon.report.web;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
 
@Controller
@RequestMapping("/report/*")
public class ReportController {
	
	private static final Log logger = LogFactory.getLog(ReportController.class);
	
    @RequestMapping("index")
    public String index(ReportController request,Model model) throws IOException {
    	logger.info("KpiAnalysisController.....");
		return "report/index"; 
    }
}
