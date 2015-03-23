package com.hp.avmon.performance.web;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.performance.service.DeviceStatusService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/dashboard/devicestatus/*")
public class DeviceStatusController {
	
	private static final Log logger = LogFactory.getLog(DeviceStatusController.class);
	
    @Autowired
    private DeviceStatusService deviceStatusService;
    
	/**
	 * 取得主机状态列表信息
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
    @RequestMapping(value = "/mainEngine")
    public void getMainEngineData(HttpServletRequest request, PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
    	String others = bundle.getString("othersDevice");
    	// 根据传入的主机类型得到相关主机
    	// 再根据主机的警告发生日期过滤
        String moId = request.getParameter("mo");
        String condition = request.getParameter("condition");
        String type = "";
        if (!StringUtil.isEmpty(request.getParameter("biz"))) {
        	type = URLDecoder.decode(request.getParameter("biz"), "UTF-8");
        }

        String json = JackJson.fromObjectToJson(deviceStatusService.getMainEngineList(moId, condition, type,others));
        String jsonData = "{record:" + json + "}";
        logger.info("getMainEngineData:" + jsonData);
        
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
	/**
	 * 字符串编码类型转换
	 * 
	 * @param str
	 * @return
	 */
	private String enCodeStr(String str) {
        try {
          return new String(str.getBytes("iso-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
