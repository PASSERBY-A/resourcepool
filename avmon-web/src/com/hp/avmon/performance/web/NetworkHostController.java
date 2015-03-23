/**
 * 
 */
package com.hp.avmon.performance.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.avmon.performance.service.NetworkHostService;

/**
 * @author muzh
 *
 */
@Controller
@RequestMapping("/pages/dashboard/network/*")
public class NetworkHostController {
	private static final Log logger = LogFactory.getLog(NetworkHostController.class);
	
	@Autowired
    private NetworkHostService networkHostService;
	
	
	
	@RequestMapping(value = "/getNetWorkHostBasicInfo")
	@ResponseBody
    public Map getNetWorkHostBasicInfo(HttpServletRequest request) throws Exception {
       return networkHostService.getNetWorkHostBasicInfo(request);

    } 
	
	@RequestMapping(value = "/getNetworkPortConfigList")
	@ResponseBody
    public List getNetworkPortConfigList(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return networkHostService.getNetworkPortConfigList(moId);

    } 
	
	@RequestMapping(value = "/getNetworkInPortList")
	@ResponseBody
    public List getNetworkUnUsedList(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return networkHostService.getNetworkUsedInfo(moId);

    } 
	
	@RequestMapping(value = "/getNetworkOutPortList")
	@ResponseBody
    public List getNetworkUsedInfo(HttpServletRequest request) throws Exception {

        String moId = request.getParameter("moId");
        return networkHostService.getNetworkUsedInfo(moId);

    } 
	
	@RequestMapping(value = "/getNetworkPerformanceList")
    @ResponseBody
    public List getNetworkPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=null;
		 int count=0;
    	 list=networkHostService.getNetWorkPerformanceList(request); 
    	 if(list.size()==0){
			 return list;
	   }
	   count=networkHostService.getNetworkPerformanceListCount(request);
	   //
	   Map<String, Object> map=new HashMap<String, Object>();
	   map.put("totalcount", count);
	   list.add(map);
       return list;
    }
    
    @RequestMapping(value = "/getNetworkOverViewPerformanceList")
    @ResponseBody
    public List getNetworkOverViewPerformanceList(HttpServletRequest request) throws Exception{
    	List<Map<String, Object>> list=networkHostService.getNetworkOverViewPerformanceList(request); 
       return list;
    }
}
