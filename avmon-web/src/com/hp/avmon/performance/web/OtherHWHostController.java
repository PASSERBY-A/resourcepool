/**
 * 其他厂商硬件
 */
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
import com.hp.avmon.performance.service.ImpiHostService;
import com.hp.avmon.performance.service.OtherHWHostService;

/**
 * 
 *
 */
@Controller
@RequestMapping("/pages/dashboard/otherHwHost/*")
public class OtherHWHostController {
	private static final Log logger = LogFactory.getLog(OtherHWHostController.class);
	
	@Autowired
    private ImpiHostService impiHostService;
	
	@Autowired
    private OtherHWHostService otherHWHostService;
	
	@RequestMapping(value = "/getSelCount")
	@ResponseBody
    public Map getSelCount(HttpServletRequest request) throws Exception {
		Map map=new HashMap();
		String moId=request.getParameter("moId");
		map.put("count", impiHostService.getSelCount(moId));
        return map;
    } 
	
	@RequestMapping(value = "/getSelList")
	@ResponseBody
    public List getSelList(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> list = null;
		int count = 0;
		String moId = request.getParameter("moId");
		list = impiHostService.getSelList(moId);
		if (list.size() == 0) {
			return list;
		}
		count = impiHostService.getSelCount(moId);
		//
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("totalcount", count);
		list.add(map);
		return list;
    } 
	
	@RequestMapping(value = "/getSelTypeList")
	@ResponseBody
    public List getSelTypeList(HttpServletRequest request) throws Exception {
		List<Map<String, Object>> list = null;
		String moId = request.getParameter("moId");
		list = impiHostService.getSelTypeList(moId);
		return list;
    } 
	
	
	@RequestMapping(value = "/getIpmiHostBasicInfo")
	@ResponseBody
    public Map getIloHostBasicInfo(HttpServletRequest request) throws Exception {
       return impiHostService.getImpiBasicInfo(request);

    } 
	
	//右边栏CPU温度
	@RequestMapping(value = "/getIpmiHostCpuTemp")
	@ResponseBody
    public List getIpmiHostCpuTemp(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return impiHostService.getIpmiHostCpuTemp(moId);

    } 
	

	//右边栏插槽温度
	@RequestMapping(value = "/getIpmiHostPluginTemp")
	@ResponseBody
    public List getIpmiHostPluginTemp(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return impiHostService.getIpmiHostPluginTemp(moId);

    } 
	
	//右边栏其他
    @RequestMapping(value = "/getIpmiHostOther")
	@ResponseBody
     public List getIpmiHostOther(HttpServletRequest request) throws Exception {

	        String moId = request.getParameter("moId");
	        return impiHostService.getIpmiHostOther(moId);

	    } 
	

	//右边栏内存
	@RequestMapping(value = "/getMemList")
	@ResponseBody
    public List getMemList(HttpServletRequest request) throws Exception {
      String moId = request.getParameter("moId");
      return  impiHostService.getMemList(moId);

    }
	//Bios
	@RequestMapping(value = "/getBiosList")
	@ResponseBody
    public List getBiosList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        List<Map<String, String>> oldlist = (List<Map<String, String>>) impiHostService.getBiosList(moId,bundle);
       return oldlist;
    }

	@RequestMapping(value = "/getCpuComm")
	@ResponseBody
    public List getCpuComm(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        List<Map<String, String>> oldlist = (List<Map<String, String>>) impiHostService.getCpuComm(moId,bundle);
       return oldlist;
    } 
	
	
	@RequestMapping(value = "/getIloPerformanceList")
    @ResponseBody
    public List getHostPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=null;
		 int count=0;
    	 list=otherHWHostService.getOtherHWPerformanceList(request); 
    	 if(list.size()==0){
			 return list;
	   }
	   count=otherHWHostService.getOtherHWPerformanceListCount(request);
	   //
	   Map<String, Object> map=new HashMap<String, Object>();
	   map.put("totalcount", count);
	   list.add(map);
       return list;
    }
    
    @RequestMapping(value = "/getIloOverViewPerformanceList")
    @ResponseBody
    public List getHostOverViewPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=otherHWHostService.getOtherHWOverViewPerformanceList(request); 
       return list;
    }
	
}
