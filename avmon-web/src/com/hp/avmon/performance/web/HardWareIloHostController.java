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
@RequestMapping("/performance/iloHost/*")
public class HardWareIloHostController {
	private static final Log logger = LogFactory.getLog(HardWareIloHostController.class);
	
	@Autowired
    private IloHostService iloHostService;
	
/*	@RequestMapping(value = "/getIloHostCpuUsage")
	@ResponseBody
    public List getIloHostCpuUsage(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return iloHostService.getIloHostCpuUsage(moId);

    } */
	
	@RequestMapping(value = "/getPowerList")
	@ResponseBody
    public List getPowerList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return  iloHostService.getPowerList(moId);
    }
	
	@RequestMapping(value = "/getMemList")
	@ResponseBody
    public List getMemList(HttpServletRequest request) throws Exception {
      String moId = request.getParameter("moId");
      return  iloHostService.getMemList(moId);

    }
	
	@RequestMapping(value = "/getDiskList")
	@ResponseBody
    public List getDiskList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
       return iloHostService.getDiskList(moId);

    }
	
	@RequestMapping(value = "/getNetcardList")
	@ResponseBody
    public List getNetcardList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return iloHostService.getNetcardList(moId);

    }
	
	
	@RequestMapping(value = "/getFanList")
	@ResponseBody
    public List getFanList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return iloHostService.getFanList(moId);
 
    }
	
	@RequestMapping(value = "/getBoardList")
	@ResponseBody
    public List getBoardList(HttpServletRequest request) throws Exception {
        String moId = request.getParameter("moId");
        return iloHostService.getMainBoardList(moId);

    }
	
	@RequestMapping(value = "/getCaseList")
	@ResponseBody
    public List getCaseList(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return iloHostService.getCaseList(moId);
        
    }

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
}
