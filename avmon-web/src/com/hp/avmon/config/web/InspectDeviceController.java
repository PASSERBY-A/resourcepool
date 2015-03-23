package com.hp.avmon.config.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.config.service.InspectDeviceService;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.utils.CfgWriter;
import com.hp.avmon.utils.PropertyUtils;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/config/inspectdevice/*")
public class InspectDeviceController {
	
	private static final Log logger = LogFactory.getLog(InspectDeviceController.class);
	
    @Autowired
    private InspectDeviceService inspectDeviceService;
    

	/**
	 * 取得巡检设备信息列表
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getRouteInspectDeviceData")
	public void getRouteInspectDeviceData(
			HttpServletRequest request,
			PrintWriter writer){
		
		try {
			Map map = inspectDeviceService.getRIDeviceList(request);
	        String jsonData = JackJson.fromObjectToJson(map);
	        logger.debug("getRouteInspectDeviceData:" + jsonData);
	        
	        writer.write(jsonData);
	        writer.flush();
	        writer.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得设备类型
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getDeviceTypeData")
	public void getDeviceTypeData(
			HttpServletRequest request,
			PrintWriter writer){
		
		try {
			List<Map> data = inspectDeviceService.getDeviceTypeData(request);
	        String jsonData = JackJson.fromObjectToJson(data);
	        logger.debug("getDeviceTypeData:" + jsonData);
	        
	        writer.write(jsonData);
	        writer.flush();
	        writer.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得设备类型
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getInspectCommandData")
	public void getInspectCommandData(
			HttpServletRequest request,
			PrintWriter writer){
		
		try {
			List<Map> data = inspectDeviceService.getInspectCommandData(request);
	        String jsonData = JackJson.fromObjectToJson(data);
	        logger.debug("getDeviceTypeData:" + jsonData);
	        
	        writer.write(jsonData);
	        writer.flush();
	        writer.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得巡检设备信息列表
	 * 
	 * @param response
	 * @param request
	 * @param writer
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/getRIDeviceCommData")
	public void getRIDeviceCommData(
			HttpServletRequest request,
			PrintWriter writer){
		
		try {
			Map map = inspectDeviceService.getRIDeviceCommList(request);
	        String jsonData = JackJson.fromObjectToJson(map);
	        logger.debug("getRIDeviceCommData:" + jsonData);
	        
	        writer.write(jsonData);
	        writer.flush();
	        writer.close();
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 单击[保存]
	 * 
	 * @param request
	 * @param writer
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/saveRIDeviceAndComm")
	public void saveRIDeviceAndComm(HttpServletRequest request, PrintWriter writer) {		
		Map dataMap = new HashMap();
		
		dataMap.put("ID", MyFunc.nullToString(request.getParameter("ID")));
		dataMap.put("INSPECT_TYPE", MyFunc.nullToString(request.getParameter("INSPECT_TYPE")));
		dataMap.put("DEVICE_TYPE", MyFunc.nullToString(request.getParameter("DEVICE_TYPE")));
		dataMap.put("DEVICE_VERSION", MyFunc.nullToString(request.getParameter("DEVICE_VERSION")));
		dataMap.put("DEVICE_IP", MyFunc.nullToString(request.getParameter("DEVICE_IP")));
		dataMap.put("DEVICE_NM", MyFunc.nullToString(request.getParameter("DEVICE_NM")));
		dataMap.put("USR", MyFunc.nullToString(request.getParameter("USR")));
		dataMap.put("PWD", MyFunc.nullToString(request.getParameter("PWD")));
		dataMap.put("QUIT_MODE1", MyFunc.nullToString(request.getParameter("QUIT_MODE1")));
		dataMap.put("DEPLOY_ENGINE", MyFunc.nullToString(request.getParameter("DEPLOY_ENGINE")));
		dataMap.put("BACKUP1", MyFunc.nullToString(request.getParameter("BACKUP1")));
		dataMap.put("COMMANDS", MyFunc.nullToString(request.getParameter("COMMANDS")));
		
		logger.debug("Commit RIDeviceAndComm basic Data:" + dataMap);
		Gson gson = new Gson();
		List<Map<String, String>> commList = gson.fromJson(MyFunc.nullToString(request.getParameter("COMMANDS")), new TypeToken<List<Map<String, String>>>(){}.getType());
		logger.debug("Commit RIDeviceAndComm comms Data:" + commList);
		
		if (StringUtils.isEmpty(MyFunc.nullToString(dataMap.get("ID")))) {
			// 新增
			dataMap.put("ID", getCurrentTimeString("yyyyMMddHHmmss"));
			
			Map result = inspectDeviceService.insertRIDeviceAndComm(dataMap, commList);
	        String json = JackJson.fromObjectHasDateToJson(result);
	        writer.write(json);
	        writer.flush();
	        writer.close();
		} else {
			// 更新
			Map result = inspectDeviceService.updateRIDeviceAndComm(dataMap, commList);
	        String json = JackJson.fromObjectHasDateToJson(result);
	        writer.write(json);
	        writer.flush();
	        writer.close();
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
	@RequestMapping(value = "/deleteRIDeviceById")
	public void deleteRIDeviceById(HttpServletRequest request, PrintWriter writer) {
        
		String ids = request.getParameter("ids");
		String id[] = ids.split("\\*");
		
		Map result = new HashMap();
		for (int i = 0; i < id.length; i++) {
			result = inspectDeviceService.deleteRIDeviceAndComm(id[i]);
		}

        String json = JackJson.fromObjectHasDateToJson(result);
        writer.write(json);
        writer.flush();
        writer.close();
    }
	
	/**
	 * 导出巡检设备命令相关信息
	 * 
	 * @param request
	 * @param writer
	 * @return
	 * @throws FileNotFoundException 
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/outputRIDeviceComm")
	public void outputRIDeviceComm(HttpServletRequest request, PrintWriter writer) throws FileNotFoundException {

		Map resultMap = new HashMap();
		// 取得数据
		List<Map> resultList = inspectDeviceService.getRIDCommOutputData();
		if (resultList != null && resultList.size() != 0) {
			
			// 读取保存文件的路径
	    	String filePath = PropertyUtils.getProperty("IDCommand.path");

	    	if (!StringUtil.isEmpty(filePath)) {
	            CfgWriter w = new CfgWriter(new File(filePath));
	            w.writeComment("Inspect Device Command Info");
	            
	            // 序号
				Integer sn = 0;
				// 上一条记录的ip
				String tempIp = "";
				
				String deviceNm = null;
	    		String deviceVersion = null; 
	    		String deviceType = null; 
	    		String deviceIp = null; 
	    		String usr = null; 
	    		String pwd = null;
	    		String commCode = null;
	    		String empty = ""; 
	    		String quitMode = null;
	    		
	    		// 循环写入文件中
				for (Map temp : resultList) {
					deviceNm = MyFunc.nullToString(temp.get("DEVICE_NM"));
					deviceVersion = MyFunc.nullToString(temp.get("DEVICE_VERSION"));
					deviceType = MyFunc.nullToString(temp.get("DEVICE_TYPE"));
					deviceIp = MyFunc.nullToString(temp.get("DEVICE_IP"));
					usr = MyFunc.nullToString(temp.get("USR"));
					pwd = MyFunc.nullToString(temp.get("PWD"));
					commCode = MyFunc.nullToString(temp.get("COMM_CODE"));
					quitMode = MyFunc.nullToString(temp.get("QUIT_MODE1"));
					
					if (tempIp.equals(deviceIp)) {
						// 相同ip序号保持一样，不同的IP不能重复
						w.writeProperty(sn, deviceNm, deviceVersion, deviceType, deviceIp, usr, pwd, commCode, empty, quitMode);
					} else {
						if (sn == 0) {
							sn = 1;
						} else {
							sn++;
						}

						w.writeProperty(sn, deviceNm, deviceVersion, deviceType, deviceIp, usr, pwd, commCode, empty, quitMode);
					}
					
					tempIp = deviceIp;
				}
//	            w.writeProperty(1, "Id", "0001", "Id", "0001", "Id", "0001", "Id", "0001", "Id");
//	            w.writeProperty(2, "Id", "0001", "Id", "0001", "Id", "0001", "Id", "0001", "Id");
//	            w.writeProperty(3, "Id", "0001", "Id", "0001", "Id", "0001", "Id", "0001", "Id");

	            w.close();
	            
	            // 检查文件是否生成
	            File affix = new File(filePath);
	            if (affix.exists()) {
	            	resultMap.put("success", true);
	            } else {
	            	resultMap.put("success", false);
	            }	            
	    	}
		} else {
			resultMap.put("success", false);
		}
		
        String json = JackJson.fromObjectHasDateToJson(resultMap);
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
