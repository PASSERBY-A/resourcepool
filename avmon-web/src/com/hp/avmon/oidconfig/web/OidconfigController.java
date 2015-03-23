package com.hp.avmon.oidconfig.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.deploy.service.DeployService;
import com.hp.avmon.snmp.service.SnmpService;
import com.hp.avmon.snmpdeploy.service.SnmpDeployService;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.TreeObject;
import com.hp.avmon.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/oidconfig/*")
public class OidconfigController {

	@Autowired
	private DeployService deployService;
	
	@Autowired
	private SnmpService snmpService;
	
	@Autowired
	private SnmpDeployService snmpDeployService;	
	
	
	@RequestMapping(value = "index")
	public String index(Locale locale, Model model) {

		return "/config/snmp/oidconfig/index";

	}

	@RequestMapping(value = "/batchdeploy/index")
	public String batchDeploy(Locale locale, Model model) {

		return "/batchdeploy/index";

	}

	@RequestMapping(value = "moDetail")
	public void getMoDetail(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		// moId="201209112222472247";
		Map mo = snmpDeployService.getMoDetail(moId, request);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(mo);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "monitorProperty")
	public void getMonitorProperty(HttpServletRequest request,
			PrintWriter writer) throws Exception {

		String moId = request.getParameter("mo");
		String monitorInstanceId = request.getParameter("inst");
		// moId="201209112222472247";
		// monitorInstanceId="I201209251301310";
		Map props = deployService.getMonitorProperty(moId, monitorInstanceId);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(props);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "monitorList")
	public void monitorList(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String monitorId = request.getParameter("monitorId");
		
		String deploystatus =request.getParameter("deploystatus");
		
		String ipAddress = request.getParameter("ipaddress");

		List list = deployService.getAmpInstanceList(moId, monitorId,deploystatus,ipAddress,null,null);
		String json = JackJson.fromObjectToJson(list);

		writer.write(json);
		writer.flush();
		writer.close();
	}


	@RequestMapping(value = "addMonitor")
	public void addMonitor(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String monitorType = request.getParameter("monitorType");

		Map map = deployService.addMonitor(moId, monitorType);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "addMo")
	public void addMo(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("id");
		String type = request.getParameter("type");
		String caption = request.getParameter("text");
		String ip = request.getParameter("ip");
		String os = request.getParameter("os");
		String osVersion = request.getParameter("osVersion");
		if (osVersion == null)
			osVersion = "";

		Map map = deployService.addMo(moId, moId, type, caption, ip, os,
				osVersion, false);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "deleteMonitor")
	public void deleteMonitor(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String instanceId = request.getParameter("instanceId");

		Map map = deployService.deleteMonitor(moId, instanceId);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "deployMonitor")
	public void deployMonitor(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String instanceIds = request.getParameter("instanceIds");

		Map map = deployService.deployMonitors(moId, instanceIds);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "batchDeployMonitor")
	public void batchDeployMonitor(HttpServletRequest request,
			PrintWriter writer) throws Exception {

		String items = request.getParameter("items");

		Map map = deployService.batchDeployMonitor(items);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "monitorTypeList")
	public void monitorTypeList(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		Map map = deployService.getAmpList();
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(map);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "deleteMo")
	public void deleteMo(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		// moId="201209112222472247";
		Map mo = snmpDeployService.deleteMo(moId);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(mo);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "createMo")
	public void createMo(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String moType = request.getParameter("moType");
		String moCaption = request.getParameter("caption");
		String agentId = request.getParameter("agentId");
		String businessType = request.getParameter("businessType");
		//businessType = AgentTypes.BIZMAP.get(businessType);
		businessType = this.snmpService.getBusinessNameById(businessType);
		// moId="201209112222472247";
		Map mo = snmpDeployService.createMo(moId, moType, moCaption, agentId,businessType, request);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(mo);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "menuTree")
	public void getMenuTree(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String userId = Utils.getCurrentUserId(request);
		String parentId = request.getParameter("parentId");
		String queryFlag = request.getParameter("queryFlag");
		String json = "{}";
		if ("1".equals(queryFlag)) {
			json = snmpDeployService.getMoTreeJsonByName(userId,parentId);
		}else{
			 String businessType = null;
		        
	        if(null!= parentId && parentId.indexOf("*") > 0){
	        	businessType = parentId.split("\\*")[1];
	        }
	        if (null!=businessType&&!"".equals(businessType)) {
	        	//businessType = AgentTypes.BIZMAP.get(businessType);
	        	if("666656".equals(businessType)){
	        		businessType = "数据中心";
	        	}else{
	        		businessType = this.snmpService.getBusinessNameById(businessType);	
	        	}
			}
			if (parentId == null || parentId.equals("")) {
				parentId = "root";
			}else{
				parentId = parentId.split("\\*")[0];
			}
			//String json = deployService.getMoTreeJson(userId, parentId);
			//add by mark start
			json = snmpDeployService.getMoTreeJson(userId, parentId,businessType);
			//add by mark end
		}
		
       
		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "moTypeTree")
	public void getMoTypeTree(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String parentId = request.getParameter("node");
		if (parentId == null || parentId.equals("")) {
			parentId = "root";
		}
		String json = snmpDeployService.getMoTypeTree(parentId);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	@RequestMapping(value = "saveMoProperty")
	public void saveMoProperty(HttpServletRequest request, PrintWriter writer)
			throws Exception {

		String moId = request.getParameter("mo");
		String props = request.getParameter("props");
		Map mo = snmpDeployService.saveMoProperty(moId, props);
		// to-anyi:请在这里将list处理成带层级的json格式
		String json = JackJson.fromObjectToJson(mo);

		writer.write(json);
		writer.flush();
		writer.close();
	}

	/**
	 * 加载树形菜单
	 * 
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 * @throws Exception 
	 * @throws ServletException 
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "sysMenuTree")
    public void getSysMenuTree(HttpServletRequest request, PrintWriter writer) throws Exception {
        
        String userId = Utils.getCurrentUserId(request);
        
        // 根据父节点取得下属节点信息
        List<Map<String,String>> moTreeList = snmpService.getBusinessTree(userId);
        String id = request.getParameter("id");
        String pid = request.getParameter("pid");
        
        String businessType = request.getParameter("businessType");
        
        if (null!=businessType&&!"".equals(businessType)) {
        	businessType = new String(businessType.getBytes("ISO-8859-1"),"UTF-8");
		}
        
        String json = null;
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        if ("businessTree".equals(id)) {
//        	for (Map<String, String> map : moTreeList) {
//        		String bizName = map.get("text");
//        		if("数据中心".equals(bizName)){
//        			moTreeList.remove(map);
//        			break;
//        		}
//        	}
        	int nodeCount = 0;
        	String bizName = null;
        	
        	if(null != moTreeList && moTreeList.size() > 0){
        		for (Map<String, String> map : moTreeList) {
            		bizName = map.get("text");
            		map.put("id", map.get("id"));
            		map.put("pid", "businessTree");
            		map.put("expanded", "false");
            		map.put("iconCls", "icon-alarm-node");
            		nodeCount = snmpService.getBusinessNodeCount(bizName);
            		//根节点计数，临时屏蔽
            		map.put("text", map.get("text"));
//            		map.put("text", map.get("text")+"("+nodeCount+")");
    			}
        	}
        	
        	Map<String, String> otherMap = new HashMap<String, String>();
        	otherMap.put("id", "666656");
        	otherMap.put("pid", "businessTree");
        	otherMap.put("expanded", "false");
        	otherMap.put("iconCls", "icon-alarm-node");
    		int otherNodeCount = snmpService.getOtherBusinessNodeCount();
    		//根节点计数，临时屏蔽
    		otherMap.put("text", bundle.getString("dataCenter"));
//    		otherMap.put("text", bundle.getString("dataCenter")+"("+otherNodeCount+")");
    		moTreeList.add(otherMap);
    		
        	json = JackJson.fromObjectHasDateToJson(moTreeList);
		}else{
			//add   start
			json = snmpDeployService.getMoTypeTree(userId,id,pid);
			//add   end
		}
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
	

	/**
	 * 转换树形菜单基础数据
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<TreeObject> getTreeObjVo(List<Map> objData){
		List<TreeObject> treeList = new ArrayList<TreeObject>();
		TreeObject obj = new TreeObject();
		
		for (Map temp : objData) {
			obj = new TreeObject();
			
			obj.setText(MyFunc.nullToString(temp.get("text")));
			obj.setId(MyFunc.nullToString(temp.get("id")));
			obj.setPid(MyFunc.nullToString(temp.get("pid")));
			obj.setMoId(MyFunc.nullToString(temp.get("moId")));
			
			treeList.add(obj);
		}
		
		return treeList;
	}
}
