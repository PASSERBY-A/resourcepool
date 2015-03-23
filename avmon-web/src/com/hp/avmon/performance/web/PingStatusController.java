package com.hp.avmon.performance.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.service.PingStatusService;

@Controller
@RequestMapping("/dashboard/pingStatus/*")
public class PingStatusController {
	
    private static final Log logger = LogFactory.getLog(PingStatusController.class);
    
    @Autowired
    private PingStatusService pingStatusService;
	
	@RequestMapping(value = "/getHostPing")
	public void getHostPing(HttpServletRequest request,
			PrintWriter writer) {
//		String userId = Utils.getCurrentUserId(request);

		String json = null;
		Map<String, Object> result = new HashMap<String, Object>();
		result = this.pingStatusService.getHostPing(request);
		json = JackJson.fromObjectHasDateToJson(result);
		if (null != json) {
			writer.write(json);
			writer.flush();
			writer.close();
		}

	}
	
	@RequestMapping(value = "batchSavePingHost")
	public void batchSavePingHost(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = "";
		moId = request.getParameter("moid");
		String ip = request.getParameter("ip");
		String pingTime = request.getParameter("pingtime");
		Map<String,Boolean> attr = pingStatusService.savePinghost(moId, ip, pingTime);
		String json = JackJson.fromObjectToJson(attr);
//		Map mo = snmpDeployService.saveMoProperty(moId, props);
//		String json = JackJson.fromObjectToJson(mo);
//
		writer.write(json);
		writer.flush();
		writer.close();
	}
}
