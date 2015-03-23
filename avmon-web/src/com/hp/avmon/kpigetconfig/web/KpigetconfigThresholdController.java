package com.hp.avmon.kpigetconfig.web;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.config.service.ThresholdService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/kpigetconfig/threshold/*")
public class KpigetconfigThresholdController {
	
	private static final Log logger = LogFactory.getLog(KpigetconfigThresholdController.class);
	
    @Autowired
    private ThresholdService thresholdService;
    
	/**
	 * 单击[保存]
	 * 
	 * @param request
	 * @param writer
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/saveThreshold")
	public void saveMonitorView(HttpServletRequest request, PrintWriter writer) {		
		Map dataMap = new HashMap();
		dataMap.put("ID", MyFunc.nullToString(request.getParameter("ID")));
		dataMap.put("MO", MyFunc.nullToString(request.getParameter("MO")));
		dataMap.put("MONITOR_INSTANCE", MyFunc.nullToString(request.getParameter("MONITOR_INSTANCE")));
		dataMap.put("KPI", MyFunc.nullToString(request.getParameter("KPI")));
		dataMap.put("MAX_THRESHOLD", MyFunc.nullToString(request.getParameter("MAX_THRESHOLD")));
		dataMap.put("MIN_THRESHOLD", MyFunc.nullToString(request.getParameter("MIN_THRESHOLD")));
		dataMap.put("THRESHOLD", MyFunc.nullToString(request.getParameter("THRESHOLD")));
		dataMap.put("CHECK_OPTR", MyFunc.nullToString(request.getParameter("CHECK_OPTR")));
		dataMap.put("ALARM_LEVEL", MyFunc.nullToString(request.getParameter("ALARM_LEVEL")));
		dataMap.put("ACCUMULATE_COUNT", MyFunc.nullToString(request.getParameter("ACCUMULATE_COUNT")));
		dataMap.put("START_DATE", MyFunc.nullToString(request.getParameter("START_DATE")));
		dataMap.put("END_DATE", MyFunc.nullToString(request.getParameter("END_DATE")));
		dataMap.put("CONTENT", MyFunc.nullToString(request.getParameter("CONTENT")));
		
		logger.debug("Commit Threshold Data:" + dataMap);
		if (StringUtils.isEmpty(MyFunc.nullToString(dataMap.get("ID")))) {
			dataMap.put("ID", getCurrentTimeString("yyyyMMddHHmmss"));
			
			Map result = thresholdService.insertThreshold(dataMap);
	        String json = JackJson.fromObjectHasDateToJson(result);
	        writer.write(json);
	        writer.flush();
	        writer.close();
		} else {
			Map result = thresholdService.updateThreshold(dataMap);
	        String json = JackJson.fromObjectHasDateToJson(result);
	        writer.write(json);
	        writer.flush();
	        writer.close();
		}
	}
	
	/**
	 * 取得KPI阀值
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getThresholdData")
	public void getThresholdData(
			HttpServletRequest request,
			PrintWriter writer){
		
		try {
			Map map = thresholdService.getThresholdList(request);
	        String jsonData = JackJson.fromObjectToJson(map);
	        logger.debug("getThresholdData:" + jsonData);
	        
	        writer.write(jsonData);
	        writer.flush();
	        writer.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 删除
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/deleteThresholdById")
	public void deleteThresholdById(HttpServletRequest request, PrintWriter writer) {
        
		String ids = request.getParameter("ids");
		String id[] = ids.split("\\*");
		
		Map result = new HashMap();
		for (int i = 0; i < id.length; i++) {
			result = thresholdService.deleteThreshold(id[i]);
		}

        String json = JackJson.fromObjectHasDateToJson(result);
        writer.write(json);
        writer.flush();
        writer.close();
    }
	
    /**
     * Gets the current time string.
     * 
     * @param pattern
     *            the pattern
     * @return the current time string
     */
    public static String getCurrentTimeString(String pattern) {
        java.util.Date now = Calendar.getInstance().getTime();
        Format formatter = new SimpleDateFormat(pattern);
        return formatter.format(now);
    }
}
