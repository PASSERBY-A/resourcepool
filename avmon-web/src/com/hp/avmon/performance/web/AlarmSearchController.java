package com.hp.avmon.performance.web;

import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.service.AlarmSearchService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/dashboard/alarmSearch/*")
public class AlarmSearchController {
	
	private static final Log logger = LogFactory.getLog(AlarmSearchController.class);
	
    @Autowired
    private AlarmSearchService alarmSearchService;
    

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
			Map map = alarmSearchService.getAlarmList(request);
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
}
