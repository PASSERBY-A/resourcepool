/**
 * 
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
import com.hp.avmon.performance.service.IloHostService;

/**
 * @author muzh
 *
 */
@Controller
@RequestMapping("/pages/dashboard/iloHost/*")
public class IloHostController {
	private static final Log logger = LogFactory.getLog(IloHostController.class);
	
	@Autowired
    private IloHostService iloHostService;
	
	
	
	@RequestMapping(value = "/getIloHostBasicInfo")
	@ResponseBody
    public Map getIloHostBasicInfo(HttpServletRequest request) throws Exception {
      
       return iloHostService.getIloHostBasicInfo(request);

    } 
	
	//右边栏CPU
	@RequestMapping(value = "/getIloHostCpuInfo")
	@ResponseBody
    public List getIloHostCpuUsage(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return iloHostService.getIloHostCpuInfo(moId);

    } 
	
	//右边栏Storage
	@RequestMapping(value = "/getStorageList")
	@ResponseBody
	public List getStorageList(HttpServletRequest request) throws Exception {

	        String moId = request.getParameter("moId");
	        return iloHostService.getStorageList(moId);

	} 
	
	//右边栏POWER
	@RequestMapping(value = "/getPowerList")
	@ResponseBody
    public List getPowerList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return  iloHostService.getPowerList(moId);
    }
	//右边栏内存
	@RequestMapping(value = "/getMemList")
	@ResponseBody
    public List getMemList(HttpServletRequest request) throws Exception {
      String moId = request.getParameter("moId");
      return  iloHostService.getMemList(moId);

    }
	//右边栏硬盘
	@RequestMapping(value = "/getDiskList")
	@ResponseBody
    public List getDiskList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
       return iloHostService.getDiskList(moId);

    }
	//右边栏网卡
	@RequestMapping(value = "/getNetcardList")
	@ResponseBody
    public List getNetcardList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return iloHostService.getNetcardList(moId);

    }
	
	//右边栏风扇
	@RequestMapping(value = "/getFanList")
	@ResponseBody
    public List getFanList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return iloHostService.getFanList(moId);
 
    }
	//右边栏主板
	@RequestMapping(value = "/getBoardList")
	@ResponseBody
    public List getBoardList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return iloHostService.getMainBoardList(moId);

    }
	
	//机箱
	
	@RequestMapping(value = "/getCaseList")
	@ResponseBody
    public Map getCaseList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return iloHostService.getIloHostCaseInfo(request);
    }
	
	//Bios

	@RequestMapping(value = "/getBiosList")
	@ResponseBody
    public List getBiosList(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        List<Map<String, String>> oldlist = (List<Map<String, String>>) iloHostService.getBiosList(moId,bundle);
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        for(Map<String,String> oldmap : oldlist){
//        	Map<String, String> map = new HashMap<String, String>();
//        	map.put("key", bundle.getString("seriesName"));
//        	map.put("value", oldmap.get("value"));
//            list.add(map);
//            map = new HashMap<String, String>();
//            map.put("key", bundle.getString("lastUpdateTime"));
//            map.put("value", oldmap.get("value"));   
//            list.add(map);
//        }
       return oldlist;
        

    }

	
	@RequestMapping(value = "/getCpuComm")
	@ResponseBody
    public List getCpuComm(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        List<Map<String, String>> oldlist = (List<Map<String, String>>) iloHostService.getCpuComm(moId,bundle);
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        for(Map<String,String> oldmap : oldlist){
//        	Map<String, String> map = new HashMap<String, String>();
//        	map.put("key", "cpu");
//            map.put("value", oldmap.get("value"));
//            list.add(map);
//            map = new HashMap<String, String>();
//            map.put("key", bundle.getString("frequency"));
//            map.put("value", oldmap.get("value"));   
//            list.add(map);
//            map = new HashMap<String, String>();
//            map.put("key", bundle.getString("digit"));
//            map.put("value", oldmap.get("value"));   
//            list.add(map);
//            map = new HashMap<String, String>();
//            map.put("key", bundle.getString("threadCount"));
//            map.put("value", oldmap.get("value"));   
//            list.add(map);
//        } 
       return oldlist;
    } 
	
	@RequestMapping(value = "/getPowerComm")
	@ResponseBody
    public List getPowerComm(HttpServletRequest request, PrintWriter writer) throws Exception {
        String moId = request.getParameter("moId");
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        List<Map<String, String>> oldlist = (List<Map<String, String>>) iloHostService.getPowerComm(moId,bundle);
//        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
//        for(Map<String,String> oldmap : oldlist){
//        	Map<String, String> map = new HashMap<String, String>();
//        	map.put("key", bundle.getString("generalStatus"));
//        	map.put("value", oldmap.get("value"));
//            list.add(map);
//            map = new HashMap<String, String>();
//            map.put("key", bundle.getString("redundantStatus"));
//            map.put("value", oldmap.get("value"));   
//            list.add(map);
//        }
        
        return oldlist;

    } 
	
	@RequestMapping(value = "/getIloPerformanceList")
    @ResponseBody
    public List getHostPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=null;
		 int count=0;
    	 list=iloHostService.getIloPerformanceList(request); 
    	 if(list.size()==0){
			 return list;
	   }
	   count=iloHostService.getIloPerformanceListCount(request);
	   //
	   Map<String, Object> map=new HashMap<String, Object>();
	   map.put("totalcount", count);
	   list.add(map);
       return list;
    }
    
    @RequestMapping(value = "/getIloOverViewPerformanceList")
    @ResponseBody
    public List getHostOverViewPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=iloHostService.getIloOverViewPerformanceList(request); 
       return list;
    }
}
