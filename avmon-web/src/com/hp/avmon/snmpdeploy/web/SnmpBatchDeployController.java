package com.hp.avmon.snmpdeploy.web;

import java.io.PrintWriter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.deploy.service.DeployService;


/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/snmpbatchdeploy/*")
public class SnmpBatchDeployController {
	
	private static final Logger logger = LoggerFactory.getLogger(SnmpBatchDeployController.class);
	

	@Autowired
	private DeployService deployService;
	
	
    @RequestMapping(value = "index")
    public String index(Locale locale, Model model) {

        return "/snmpbatchdeploy/index";
        
    }
    
    @RequestMapping(value = "discoveryList")
    public void discoveryList(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String startIp=request.getParameter("startIp");
        String endIp=request.getParameter("endIp");

        Map map = deployService.getDiscoveryList(startIp,endIp,bundle);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    
    @RequestMapping(value = "deployResult")
    public void deployResult(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("moId");

        Map map = deployService.getDeployResult(moId);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "deployDiscovery")
    public void deployDiscovery(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moIds=request.getParameter("moIds");
        logger.info("Deploy Mos ={}",moIds);
       
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        Map map = deployService.deployDiscoverys(moIds,bundle);
        List<Map> list = (List<Map>) map.get("items");
        if(list.size()>0)
        for(Map m : list){
        	String result = m.get("result").toString();
        	if(result.equalsIgnoreCase("部署成功")){
        		m.put("result", bundle.getString("deploySuccess"));
        	}else if(result.equalsIgnoreCase("部署失败")){
        		m.put("result", bundle.getString("deployFail"));
        	}
        	String msg = m.get("msg").toString();
        	if(msg.indexOf("未知错误") != -1){
        		msg = msg.substring(0, msg.indexOf("未知错误")) + bundle.getString("unknownErrors");
        	}else if (msg.indexOf("下发成功.") != -1){
        		msg = msg.substring(0, msg.indexOf("下发成功.")) + bundle.getString("issuedSuccess");
        	}else if (msg.indexOf("未能创建") != -1){
        		msg = msg.substring(0, msg.indexOf("未能创建")) + bundle.getString("cannotGreat") + msg.substring(msg.indexOf("建")+1, msg.indexOf("的实例")) + bundle.getString("instanceOf");
        	}
        }
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
}
