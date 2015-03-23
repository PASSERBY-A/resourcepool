package com.hp.avmon.discovery.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.deploy.web.DeployController;
import com.hp.avmon.discovery.service.DiscoveryService;

@Controller
@RequestMapping("/discovery/*")
public class DiscoveryController {
	
	private static final Logger logger = LoggerFactory.getLogger(DeployController.class);
	
    @Autowired
    private DiscoveryService discoveryService;
	
    @RequestMapping(value = "deviceMgr")
    public String index(Locale locale, Model model) {
    	logger.debug("==========="+DiscoveryController.class+"=========index===============");
        return "/discovery/deviceMgr";
    }
    
    @RequestMapping(value = "deviceScan")
    public String deviceScan(Locale locale, Model model) {
    	logger.debug("==========="+DiscoveryController.class+"=========index===============");
        return "/discovery/deviceScan";
    }
    
    @RequestMapping(value = "motypeconfig")
    public String motypeconfig(Locale locale, Model model) {
    	logger.debug("==========="+DiscoveryController.class+"=========motypeconfig===============");
        return "/discovery/motypeconfig";
    }
    
    @RequestMapping(value = "mibmgr")
    public String mibmgr(Locale locale, Model model) {
    	logger.debug("==========="+DiscoveryController.class+"=========mibmgr===============");
        return "/discovery/mibmgr";
    }
    
    
    @RequestMapping(value = "mibimport")
    public String mibconfig(Locale locale, Model model) {
    	logger.debug("==========="+DiscoveryController.class+"=========mibimport===============");
        return "/discovery/mibimport";
    }
    
    @RequestMapping(value = "trapmgr")
    public String trapmgr(Locale locale, Model model) {
    	logger.debug("==========="+DiscoveryController.class+"=========trapmgr===============");
        return "/discovery/trapmgr";
    }
    
    
    @RequestMapping(value = "scan")
    public void scan(HttpServletRequest request,PrintWriter writer){
    	String result = "";
    	try {
    		result = discoveryService.scan(request);
    		if(result.equals("1")){
    			result="[]";
    		}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
//			map.put("result", "failure");
		}
    	
//        String json=JackJson.fromObjectToJson(map);
        logger.debug(this.getClass()+"====scan==mo==="+result);
        writer.write(result);
        writer.flush();
        writer.close();
    	
    }
    
    @RequestMapping(value = "getDevices")
    public void getDevices(HttpServletRequest request,PrintWriter writer){
    	
    	Map<String,String> map = new HashMap<String, String>();
    	String result ="";
    	try {
    		result = discoveryService.getDevices(request);
    		if(result.equals("1")){
    			result="[]";
    		}
		} catch (Exception e) {
			e.printStackTrace();
			map.put("result", "failure");
		}
    	
//        String json=JackJson.fromObjectToJson(map);
//        logger.debug(this.getClass()+"====getDevices====="+json);
        logger.debug(this.getClass()+"====getDevices====="+result);
        writer.write(result);
        writer.flush();
        writer.close();
    	
    }
    
    @RequestMapping(value = "getDeviceOids")
    public void getDeviceOids(HttpServletRequest request,PrintWriter writer){
    	
    	Map<String,String> map = new HashMap<String, String>();
    	String result ="";
    	try {
    		result = discoveryService.getDeviceOids(request);
    		if(result.equals("1")){
    			result="[]";
    		}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			map.put("result", "failure");
		}
    	
//        String json=JackJson.fromObjectToJson(map);
//        logger.debug(this.getClass()+"====getDevices====="+json);
        logger.debug(this.getClass()+"====getOids====="+result);
        writer.write(result);
        writer.flush();
        writer.close();
    	
    }
    
    
    @RequestMapping(value = "getTypeTree")
    public void getTypeTree(HttpServletRequest request,PrintWriter writer){
    	
    	Map<String,String> map = new HashMap<String, String>();
    	String result ="";
    	try {
    		result = discoveryService.getTypeTreeJson(request);
    		if(!result.equals("1")){
    			map.put("result", "success");
    			map.put("types", result);
    		}else{
    			map.put("result", "failure");
    		}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			map.put("result", "failure");
		}
    	
//        String json=JackJson.fromObjectToJson(map);
//        logger.debug(this.getClass()+"====getDevices====="+json);
        logger.debug(this.getClass()+"====getDevices====="+result);
        writer.write(result);
        writer.flush();
        writer.close();
    	
    }
    
    /**
     * 保存修改DeviceType详细信息
     * @param request
     * @param writer
     */
    @RequestMapping(value="updateDeviceType")
    public void updateDeviceType(HttpServletRequest request,PrintWriter writer){
    	Map<String,String> map = new HashMap<String, String>();
    	String result ="";
    	try {
    		result = discoveryService.updateDeviceType(request);
    		if(!result.equals("1")){
    			map.put("result", "success");
    			map.put("types", result);
    		}else{
    			map.put("result", "failure");
    		}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			map.put("result", "failure");
		}
    	
		writer.write(result);
		writer.flush();
		writer.close();
    }
    
    /**
     * 
     * @param request
     * @param writer
     */
    @RequestMapping(value="saveDeviceType")
    public void saveDeviceType(HttpServletRequest request,PrintWriter writer){
    	Map<String,String> map = new HashMap<String, String>();
    	String result ="";
    	try {
    		result = discoveryService.saveDeviceType(request);
    		if(!result.equals("1")){
    			map.put("result", "success");
    			map.put("types", result);
    		}else{
    			map.put("result", "failure");
    		}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			map.put("result", "failure");
		}
    	
		writer.write(result);
		writer.flush();
		writer.close();
    }
    
    /**
     * 获得设备类型详情
     * @param request
     * @param writer
     */
    @RequestMapping(value="getTypeDetail")
    public void getTypeDetail(HttpServletRequest request,PrintWriter writer){
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	
    	String result ="";
		list = discoveryService.getTypeDetail(request);
		if(list.size()>0){
			result = JackJson.fromObjectToJson(list);
		}else{
			result = "[]";
		}
    	
		writer.write(result);
		writer.flush();
		writer.close();
    }
    
    /**
     * 删除监控对象类型
     * @param request
     * @param writer
     */
    @RequestMapping(value="deleteType")
    public void deleteType(HttpServletRequest request,PrintWriter writer){
    	Map<String,String> map = new HashMap<String, String>();
    	String result ="";
    	try {
    		result = discoveryService.deleteType(request);
    		if(!result.equals("1")){
    			map.put("result", "success");
    			map.put("types", result);
    		}else{
    			map.put("result", "failure");
    		}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			map.put("result", "failure");
		}
    	
		writer.write(result);
		writer.flush();
		writer.close();
    }
    
    /**
     * 获得设备类型属性下拉菜单
     * @param request
     * @param writer
     */
    @RequestMapping(value="getDetailCombox")
    public void getDetailCombox(HttpServletRequest request,PrintWriter writer){
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	
    	String result ="";
		list = discoveryService.getDetailCombox(request);
		if(list.size()>0){
			result = JackJson.fromObjectToJson(list);
		}else{
			result = "[]";
		}
    	
		writer.write(result);
		writer.flush();
		writer.close();
    }
    
    /**
     * 获得设备类型属性下拉菜单
     * @param request
     * @param writer
     */
    @RequestMapping(value="getTypeCombox")
    public void getTypeCombox(HttpServletRequest request,PrintWriter writer){
    	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
    	
    	String result ="";
		list = discoveryService.getTypeCombox(request);
		if(list.size()>0){
			result = JackJson.fromObjectToJson(list);
		}else{
			result = "[]";
		}
    	logger.debug(result);
		writer.write(result);
		writer.flush();
		writer.close();
    }
    
    /**
     * 单独更新type属性
     * @param request
     * @param writer
     */
    @RequestMapping(value="updateTypeDetail")
    public void updateTypeDetail(HttpServletRequest request,PrintWriter writer){
    	Map<String, Object> msg = new HashMap<String, Object>();
    	msg = discoveryService.updateTypeDetail(request);
		writer.write(JackJson.fromObjectToJson(msg));
		writer.flush();
		writer.close();
    }
    
    /**
     * 
     * @param request
     * @param writer
     */
    @RequestMapping(value="addWatch")
    public void addWatch(HttpServletRequest request,PrintWriter writer){
    	Map<String, String> result = new HashMap<String, String>();
    	result = discoveryService.addWatch(request);
    	writer.write(JackJson.fromObjectToJson(result));
		writer.flush();
		writer.close();
    }
    
    
    @RequestMapping(value = "getOids")
    public void getOids(HttpServletRequest request,PrintWriter writer){
    	
    	Map<String,String> map = new HashMap<String, String>();
    	String result ="";
    	try {
    		result = discoveryService.getOids(request);
    		if(result.equals("1")){
    			result="[]";
    		}
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			map.put("result", "failure");
		}
    	
//        String json=JackJson.fromObjectToJson(map);
//        logger.debug(this.getClass()+"====getDevices====="+json);
        logger.debug(this.getClass()+"====getOids====="+result);
        writer.write(result);
        writer.flush();
        writer.close();
    	
    }
    
    /**
     * 
     * @param request
     * @param writer
     */
    @RequestMapping(value="updateMibOid")
    public void updateMibOid(HttpServletRequest request,PrintWriter writer){
    	String result = "";
    	result = discoveryService.updateMibOid(request);
    	logger.debug("================updateMibOid==============="+result);
    	writer.write(result);
		writer.flush();
		writer.close();
    }
    
    /**
     * 
     * @param request
     * @param writer
     */
    @RequestMapping(value="deleteOids")
    public void deleteOids(HttpServletRequest request,PrintWriter writer){
    	String result = "";
    	result = discoveryService.deleteOids(request);
    	writer.write(result);
		writer.flush();
		writer.close();
    }
    
    
    @RequestMapping(value = "importMibFile")
    public void importMibFile(HttpServletRequest request,PrintWriter writer){
    	
    	Map<String,String> map = new HashMap<String, String>();
    	String result ="";
    	try {
    		result = discoveryService.importMibFile(request);
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			map.put("result", "failure");
		}
    	
        logger.debug(this.getClass()+"====importMibFile====="+result);
        writer.write(result);
        writer.flush();
        writer.close();
    	
    }
    
    /**
     * 启用禁用oid状态
     * @param request
     * @param writer
     */
    @RequestMapping(value="enableDisableOid")
    public void enableDisableOid(HttpServletRequest request,PrintWriter writer){
    	String result = "";
    	result = discoveryService.enableDisableOid(request,false);
    	writer.write(result);
		writer.flush();
		writer.close();
    }
    
    /**
     * 启用禁用oid状态
     * @param request
     * @param writer
     */
    @RequestMapping(value="batchEnableDisableOid")
    public void batchEnableDisableOid(HttpServletRequest request,PrintWriter writer){
    	String result = "";
    	result = discoveryService.enableDisableOid(request,true);
    	writer.write(result);
		writer.flush();
		writer.close();
    }
    
}
