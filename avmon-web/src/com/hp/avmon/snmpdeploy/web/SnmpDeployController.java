package com.hp.avmon.snmpdeploy.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.jfree.util.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.alarm.service.AlarmViewService;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.config.service.AgentManageService;
import com.hp.avmon.deploy.service.DeployService;
import com.hp.avmon.ireport.util.StringUtil;
import com.hp.avmon.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/snmpdeploy/*")
public class SnmpDeployController {
    
    private static final Logger logger = LoggerFactory.getLogger(SnmpDeployController.class);
    

    @Autowired
    private DeployService deployService;
    
   @Autowired
   private AlarmViewService alarmViewService;
   @Autowired
   private AgentManageService agentManageService;
   
   
       
    @RequestMapping(value = "index")
    public String index(Locale locale, Model model) {

        return "/snmpdeploy/index";
        
    }
    
    
    @RequestMapping(value = "/batchdeploy/index")
    public String batchDeploy(Locale locale, Model model) {

        return "/batchdeploy/index";
        
    }
    
    @RequestMapping(value = "moDetail")
    public void getMoDetail(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        //moId="201209112222472247";
        Map mo = deployService.getMoDetail(moId, request);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(mo);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "monitorProperty")
    public void getMonitorProperty(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        String monitorInstanceId=request.getParameter("inst");
        //moId="201209112222472247";
        //monitorInstanceId="I201209251301310";
        Map props = deployService.getMonitorProperty(moId,monitorInstanceId);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(props);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "monitorList")
    public void monitorList(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        String monitorId=request.getParameter("monitorId");
        
        String deploystatus =request.getParameter("deploystatus");
        
        String ipAddress = request.getParameter("ipaddress");

        List list = deployService.getAmpInstanceList(moId,monitorId,deploystatus,ipAddress,null,null);
        String json=JackJson.fromObjectToJson(list);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
//    @RequestMapping(value = "kpiList")
//    public void kpiList(HttpServletRequest request
//            ,PrintWriter writer) throws Exception {
//        
//        String moId=request.getParameter("mo");
//
//        Map map = deployService.getKpiList(moId);
//        //to-anyi:请在这里将list处理成带层级的json格式
//        String json=JackJson.fromObjectToJson(map);
//        
//        writer.write(json);
//        writer.flush();
//        writer.close();
//    }
    
    @RequestMapping(value = "addMonitor")
    public void addMonitor(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        String monitorType=request.getParameter("monitorType");

        Map map = deployService.addMonitor(moId,monitorType);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "syncMem")
    public void syncMem(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String type=request.getParameter("type");
        String userId=Utils.getCurrentUserId(request);
        //同步后台
        Map map = deployService.syncMem(type, request);
        
        //构造告警树
        Log.info("buildAlarmTreeTable ...");
        alarmViewService.buildAlarmTreeTable(userId);
        alarmViewService.buildAlarmTreePath(userId);
        Log.info("buildAlarmTreeTable Done.");
        
        String json=JackJson.fromObjectToJson(map);
        
        if(json==null){
            json="";
        }
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
//    @RequestMapping(value = "saveKpi")
//    public void saveKpi(HttpServletRequest request
//            ,PrintWriter writer) throws Exception {
//        
//        String moId=request.getParameter("mo");
//        String monitorInstanceId=request.getParameter("monitorInstanceId");
//        String kpiCode=request.getParameter("kpiCode");
//        String[] thall=request.getParameterValues("THALL");
//        int n1=Integer.valueOf(thall[0]);
//        int n2=Integer.valueOf(thall[1]);
//        int n3=Integer.valueOf(thall[2]);
//        int n;
//        if(n1>n2){
//            n=n1;
//            n1=n2;
//            n2=n;
//        }
//        if(n1>n3){
//            n=n1;
//            n1=n3;
//            n3=n;
//        }
//        if(n2>n3){
//            n=n2;
//            n2=n3;
//            n3=n;
//        }
//        
//        String optr=request.getParameter("CHECK_OPTR");
//        String accumulateCount=request.getParameter("ACCUMULATE_COUNT");
//        String isParentThreshold=request.getParameter("IS_PARENT_THRESHOLD");
//        if(accumulateCount==null || "".equals(accumulateCount)){
//            accumulateCount="0";
//        }
//        Map map = deployService.saveKpi(moId,monitorInstanceId,kpiCode,optr,accumulateCount,n1,n2,n3,isParentThreshold);
//        //to-anyi:请在这里将list处理成带层级的json格式
//        String json=JackJson.fromObjectToJson(map);
//        
//        writer.write(json);
//        writer.flush();
//        writer.close();
//    }
    
    @RequestMapping(value = "addMo")
    public void addMo(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("id");
        String type=request.getParameter("type");
        String caption=request.getParameter("text");
        String ip=request.getParameter("ip");
        String os=request.getParameter("os");
        String osVersion=request.getParameter("osVersion");
        if(osVersion==null)osVersion="";

        Map map = deployService.addMo(moId,moId,type,caption,ip,os,osVersion,false);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "deleteMonitor")
    public void deleteMonitor(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        String instanceId=request.getParameter("instanceId");

        Map map = deployService.deleteMonitor(moId,instanceId);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
   
    @RequestMapping(value = "deployMonitor")
    public void deployMonitor(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        String instanceIds=request.getParameter("instanceIds");

        Map map = deployService.deployMonitors(moId,instanceIds);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
   
    @RequestMapping(value = "batchDeployMonitor")
    public void batchDeployMonitor(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String items=request.getParameter("items");

        Map map = deployService.batchDeployMonitor(items);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "monitorTypeList")
    public void monitorTypeList(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
       Map map = deployService.getAmpList();
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "deleteMo")
    public void deleteMo(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        //moId="201209112222472247";
        Map mo = deployService.deleteMo(moId);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(mo);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "createMo")
    public void createMo(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        String moType=request.getParameter("moType");
        String moCaption=request.getParameter("caption");
        String agentId=request.getParameter("agentId");
        String parentId = request.getParameter("parentId");
        String protocol = request.getParameter("protocol");
        String businessType=request.getParameter("businessType");
        //moId="201209112222472247";
        //changed by mark start
        Map mo = deployService.createMo(moId,moType,moCaption,parentId,protocol,agentId,businessType,request);
        //changed by mark end
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(mo);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    //modify by mark start
    @RequestMapping(value = "menuTree")
    public void getMenuTree(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
//        String userId=Utils.getCurrentUserId(request);
//        String parentId=request.getParameter("id");
//        if(parentId==null||parentId.equals("")){
//            parentId="root";
//        }
//        String json=deployService.getMoTreeJson(userId,parentId);
//        
//        writer.write(json);
//        writer.flush();
//        writer.close();
	String userId=Utils.getCurrentUserId(request);
    	
    	// 根据父节点取得下属节点信息
        List<Map<String,String>> moTreeList = deployService.getBusinessTree(userId);
        Map<String,String> treeNode = null;
        List<Map<String,String>> treeNodes = new ArrayList<Map<String,String>>();
        String id = request.getParameter("id");
        
        String businessType = request.getParameter("businessType");
        if (null!=businessType&&!"".equals(businessType)) {
        	businessType = new String(businessType.getBytes("ISO-8859-1"),"UTF-8");
		}
        
        String json = null;
        if ("businessTree".equals(id)) {
        	for (int i = 0;i<moTreeList.size(); i++) {
            	treeNode = new HashMap<String, String>();
            	treeNode.put("id", String.valueOf(i));
            	treeNode.put("pid", "0");        	
            	treeNode.put("expanded", "false");
            	treeNode.put("text", moTreeList.get(i).get("text"));
            	treeNode.put("iconCls", "../resources/images/menus/grid.png");
            	
            	treeNodes.add(treeNode);
    		}
        	json = JackJson.fromObjectHasDateToJson(treeNodes);
		}else{
			//add by mark start
			if("0".equals(id)){
				id = "root";
			}
			//json = deployService.getMoTypeTreeJson(userId, id);
			json = deployService.getMoTypeTree(id);
			//add by mark end
		}
        
        writer.write(json);
        writer.flush();
        writer.close();
    	
    }
  //modify by mark end
    
// add by mark start
    
    @RequestMapping(value = "agentTree")
    public void getAgentTree(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
    	String userId=Utils.getCurrentUserId(request);
    	
    	// 根据父节点取得下属节点信息
        Map<String,String> treeNode = null;
        List<Map<String,String>> treeNodes = new ArrayList<Map<String,String>>();
        String id = request.getParameter("id");
        
        String json = null;
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        if (AgentTypes.AGENT_TREE_ROOT_ID.equals(id)) {
            	treeNode = new HashMap<String, String>();
            	treeNode.put("id", AgentTypes.AGENT_MANAGEMENT);
            	treeNode.put("pid", AgentTypes.AGENT_TREE_ROOT_ID);        	
            	treeNode.put("expanded", "false");
            	treeNode.put("text", bundle.getString("agentManagerment"));
            	treeNode.put("iconCls", "icon-hardware");
            	treeNodes.add(treeNode);
            	
            	treeNode = new HashMap<String, String>();
            	treeNode.put("id", AgentTypes.AGENT_DOWNLOAD);
            	treeNode.put("pid", AgentTypes.AGENT_TREE_ROOT_ID);        	
            	treeNode.put("expanded", "true");
            	treeNode.put("leaf", "true");
            	treeNode.put("text", bundle.getString("softwarePackageDownload"));
            	treeNode.put("iconCls", "icon-hardware");
            	treeNodes.add(treeNode);
            	
            	treeNode = new HashMap<String, String>();
            	treeNode.put("id", AgentTypes.AMP_UPDATE);
            	treeNode.put("pid", AgentTypes.AGENT_TREE_ROOT_ID);        	
            	treeNode.put("expanded", "true");
            	treeNode.put("leaf", "true");
            	treeNode.put("text", bundle.getString("ampUpdate"));
            	treeNode.put("iconCls", "icon-hardware");
            	treeNodes.add(treeNode);
            	
            	treeNode = new HashMap<String, String>();
            	treeNode.put("id", AgentTypes.HOST_PING);
            	treeNode.put("pid", AgentTypes.AGENT_TREE_ROOT_ID);        	
            	treeNode.put("expanded", "true");
            	treeNode.put("leaf", "true");
            	treeNode.put("text", bundle.getString("hostPingStatus"));
            	treeNode.put("iconCls", "icon-hardware");
            	treeNodes.add(treeNode);
            	
//            	treeNode = new HashMap<String, String>();
//            	treeNode.put("id", AgentTypes.AGENT_UPDATE);
//            	treeNode.put("pid", AgentTypes.AGENT_TREE_ROOT_ID);        	
//            	treeNode.put("expanded", "true");
//            	treeNode.put("leaf", "true");
//            	treeNode.put("text", "Agent更新");
//            	treeNode.put("iconCls", "icon-hardware");
//            	treeNodes.add(treeNode);
        	json = JackJson.fromObjectHasDateToJson(treeNodes);
		}else if(AgentTypes.AGENT_MANAGEMENT.equals(id)){
			//得到业务系统
			json = deployService.getAgentMgrBizTypes(userId, request);
		
		// 得到业务系统下host类型
		}else{
			json = deployService.getAgentHostTypes(id);
		}
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @SuppressWarnings("unchecked")
	@RequestMapping(value = "getHostPing")
	public void getHostPing(HttpServletRequest request,
			PrintWriter writer) {
//		String userId = Utils.getCurrentUserId(request);

		String json = null;
		Map<String, Object> result = new HashMap<String, Object>();
		result = this.deployService.getHostPing(request);
		json = JackJson.fromObjectHasDateToJson(result);
		if (null != json) {
			writer.write(json);
			writer.flush();
			writer.close();
		}

	}
	@RequestMapping(value = "monitorUpgradeFiles")
	public void GetMonitorUpgradeFiles(HttpServletRequest request,
			PrintWriter writer) {
		String userId = Utils.getCurrentUserId(request);

		String json = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		list = this.deployService.getMonitorUpgradeFiles();
		json = JackJson.fromObjectHasDateToJson(list);
		if (null != json) {
			writer.write(json);
			writer.flush();
			writer.close();
		}

	}
    
    /**
     * 查找Agent Grid信息
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/findAgentGridInfo")
    public void findAgentGridInfo(HttpServletRequest request, PrintWriter writer)  {
        Map map = null;
        try {
            map = deployService.findAgentGridInfo(request);
            String jsonData = JackJson.fromObjectToJson(map);
            //logger.debug("agent grid==="+jsonData);
            writer.write(jsonData);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    /**
     * 升级Agent
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/batchUpgradeAgent")
    public void upgradeAgent(HttpServletRequest request, PrintWriter writer){
        String json = "";
        try {
            json = deployService.batchUpgradeAgent(request);
            writer.write(json);
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }
    
    /**
     * 下发脚本（调度+配置一起下发）成功后为AMP状态为停止运行
     * @param request 包含agentAmpInfo一个json数组兼容单AMP与多AMP脚本下发
     * @param writer
     */
    @RequestMapping(value = "/pushAgentAmpScript")
    public void pushAgentAmpScript(HttpServletRequest request,PrintWriter writer){
        Map map = deployService.pushAgentAmpScript(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("pushAgentAmpScript: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    // add by mark end   
    
    @RequestMapping(value = "moTypeTree")
    public void getMoTypeTree(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String parentId=request.getParameter("node");
        if(parentId==null||parentId.equals("")){
            parentId="root";
        }
        String json=deployService.getMoTypeTree(parentId);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "saveMoProperty")
    public void saveMoProperty(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        String props=request.getParameter("props");
        Map mo = deployService.saveMoProperty(moId,props);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(mo);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    
    @RequestMapping(value = "saveMonitor")
    public void saveMonitor(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        String props=request.getParameter("properties");
        String instanceId=request.getParameter("path");
        String caption=request.getParameter("caption");
        String scheduleType=request.getParameter("scheduleType");
        String schedule1=request.getParameter("schedule1");
        String schedule2=request.getParameter("schedule2");
        String schedule3=request.getParameter("schedule3");
        String schedule4=request.getParameter("schedule4");
        String schedule5=request.getParameter("schedule5");
        String schedule6=request.getParameter("schedule6");
        schedule1=schedule1==null?"*":schedule1;
        schedule2=schedule2==null?"*":schedule2;
        schedule3=schedule3==null?"*":schedule3;
        schedule4=schedule4==null?"*":schedule4;
        schedule5=schedule5==null?"*":schedule5;
        schedule6=schedule6==null?"*":schedule6;
        schedule1=schedule1.equals("")?"*":schedule1;
        schedule2=schedule2.equals("")?"*":schedule2;
        schedule3=schedule3.equals("")?"*":schedule3;
        schedule4=schedule4.equals("")?"*":schedule4;
        schedule5=schedule5.equals("")?"*":schedule5;
        schedule6=schedule6.equals("")?"*":schedule6;
        String schedule=String.format("%s %s %s %s %s %s",
                schedule1,schedule2,schedule3,schedule4,schedule5,schedule6);

        Map mo = deployService.saveMonitor(moId,instanceId,props,caption,scheduleType,schedule);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(mo);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    
    @RequestMapping(value = "agentStatus")
    public void agentStatus(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        //moId="201209112222472247";
        Map mo = deployService.getAgentStatus(moId);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(mo);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "startAgent")
    public void startAgent(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        //moId="201209112222472247";
        Map map = deployService.startAgent(moId);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    
    @RequestMapping(value = "pauseAgent")
    public void pauseAgent(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String moId=request.getParameter("mo");
        //moId="201209112222472247";
        Map map = deployService.pauseAgent(moId);
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(map);
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "agentDownload")
    public void agentDownload(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        
        
        //to-anyi:请在这里将list处理成带层级的json格式
        String json=JackJson.fromObjectToJson(deployService.getDownloadList());
        
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "hostListForPing")
    public void hostList(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        String pageNo = request.getParameter("pageNo");
        String pageSize = request.getParameter("pageSize");
        String s = StringUtil.EMPTY;
        if(StringUtil.isEmpty(pageSize)||StringUtil.isEmpty(pageNo)){
        	s=deployService.getHostListForPing(0,0);
        }else{
        	int pagetNoInt = Integer.valueOf(pageNo);
            int pageSizeInt = Integer.valueOf(pageSize);
            s=deployService.getHostListForPing(pagetNoInt,pageSizeInt);	
        }
        writer.write(s);
        writer.flush();
        writer.close();
    }
    
    /**
     * 获取AMP调度策略串
     * @param request
     * @param writer
     */
    @RequestMapping(value = "/getNormalAmpSchedule")
    public void getNormalAmpSchedule(HttpServletRequest request, PrintWriter writer){
        Map map = agentManageService.getNormalAmpSchedule(request);
        String jsonData = JackJson.fromObjectToJson(map);
        logger.debug("getNormalAmpSchedule: "+jsonData);
        writer.write(jsonData);
        writer.flush();
        writer.close();
    }
    
    
}
