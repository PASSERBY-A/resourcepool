package com.hp.avmon.performance.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.performance.service.VmListService;
import com.hp.avmon.utils.Utils;

@Controller
//@RequestMapping("/pages/dashboard/vmList/*")
@RequestMapping("/vcenter/*")
public class VmListController {
	
    private static final Log logger = LogFactory.getLog(VmListController.class);
    
    @Autowired
    private VmListService vmListService;
	
	@RequestMapping(value = "/getPhysicalMachineList")
	public void getPhysicalMachineList(HttpServletRequest request,PrintWriter writer) {
//		String userId = Utils.getCurrentUserId(request);
		String id = request.getParameter("id");
		String mo = request.getParameter("mo");
		String moId = request.getParameter("moId");

		String json = null;
		Map<String, Object> result = new HashMap<String, Object>();
		result = vmListService.getPhysicalMachineList(request);
		json = JackJson.fromObjectHasDateToJson(result);
		if (null != json) {
			writer.write(json);
			writer.flush();
			writer.close();
		}

	}
	
	@RequestMapping(value = "/getVirtualMachineList")
	public void getVirtualMachineList(HttpServletRequest request,PrintWriter writer) {
//		String userId = Utils.getCurrentUserId(request);

		String json = null;
		Map<String, Object> result = new HashMap<String, Object>();
		result = vmListService.getVirtualMachineList(request);
		json = JackJson.fromObjectHasDateToJson(result);
		if (null != json) {
			writer.write(json);
			writer.flush();
			writer.close();
		}

	}
	
	@RequestMapping(value = "/getStorageList")
	public void getStorageList(HttpServletRequest request,PrintWriter writer) {
//		String userId = Utils.getCurrentUserId(request);

		String json = null;
		Map<String, Object> result = new HashMap<String, Object>();
		result = vmListService.getStorageList(request);
		json = JackJson.fromObjectHasDateToJson(result);
		if (null != json) {
			writer.write(json);
			writer.flush();
			writer.close();
		}
	}
	
	@RequestMapping(value = "/overView")
    public String  overview(HttpServletRequest request) throws Exception {
    	return "vcenter/vmOverView";
    }
	
	@RequestMapping(value = "/vmTree")
    public void getMenuTree(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
    	Locale locale = request.getLocale();
    	ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String userId=Utils.getCurrentUserId(request);
        String parentId=request.getParameter("id");
//        String checkbox = request.getParameter("checkBox");
        if(parentId==null||parentId.equals("")){
            parentId="VMHOST";
        }
//        String parentId="VMHOST";
        String checkbox="false";
        String json=vmListService.getVmTreeJson(userId,parentId,checkbox,bundle);

        if("true".equalsIgnoreCase(checkbox)){
        	json = json.replace("\"leaf\":\"true\"", "\"leaf\":\"true\",\"checked\":false");
        }
//        logger.debug(json);
        writer.write(json);
        writer.flush();
        writer.close();
    }
	
	@RequestMapping(value = "/getVmBasicInfo")
    @ResponseBody
    public Map getVmBasicInfo(
            HttpServletRequest request){
    	Map map =new HashMap();
        try {
             map = vmListService.getVmBasicInfo(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getVmBasicInfo",e);
            e.printStackTrace();
        }
        return map;
    }
	
	@RequestMapping(value = "/getOverViewList")
    @ResponseBody
    public List getOverViewList(
            HttpServletRequest request){
    	List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
        try {
        	list = vmListService.getOverViewList(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getOverViewList",e);
            e.printStackTrace();
        }
        return list;
    }
	
	@RequestMapping(value = "/getVmAlarmData")
    @ResponseBody
    public List getVmAlarmData(
            HttpServletRequest request){
    	List<Map<String, Object>> list =new ArrayList<Map<String, Object>>();
        try {
        	list = vmListService.getVmAlarmData(request);   
        } catch (Throwable e) {
        	logger.error(this.getClass().getName()+"getVmAlarmData",e);
            e.printStackTrace();
        }
        return list;
    }
}
