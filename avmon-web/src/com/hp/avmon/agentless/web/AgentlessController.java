package com.hp.avmon.agentless.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hp.avmon.agentless.service.AgentlessService;

/**
 * @author muzh
 *
 */
@Controller
@RequestMapping("/agentless/*")
public class AgentlessController {
	
	private static final Log logger = LogFactory.getLog(AgentlessController.class);

	@Autowired
	private AgentlessService agentlessService;
	
	@RequestMapping(value = "agentlessimport")
    public String agentlessimport(Locale locale, Model model) {
        return "/agentless/agentlessimport";
    }
	
	@RequestMapping(value = "importMibFile")
    public void importAgentlessFile(HttpServletRequest request,PrintWriter writer){
		
    	Map<String,String> map = new HashMap<String, String>();
    	String result ="";
    	try {
    		if (request instanceof MultipartHttpServletRequest){
    			result = agentlessService.importAgentlessFile(request);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "failure");
		}
    	
        writer.write(result);
        writer.flush();
        writer.close();
    	
    }
}
