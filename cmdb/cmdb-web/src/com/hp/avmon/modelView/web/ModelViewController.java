package com.hp.avmon.modelView.web;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.modelView.service.ModelViewService;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.TreeObject;
import com.hp.avmon.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/modelView/*")
public class ModelViewController {

	private static final Logger logger = LoggerFactory
			.getLogger(ModelViewController.class);


	
	@Autowired
	private ModelViewService modelViewService;
	

	
	
	@RequestMapping(value = "index")
	public String index(Locale locale, Model model) {

		return "/pages/modelView/index";

	}
	
	@RequestMapping(value = "syncIndex")
	public String syncIndex(){
		return "/pages/modelView/syncIndex";
	}
	
	/**
	 * 数据同步 Avmon 到Hbase
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "startSyncOperation")
	public void startSyncOperation(HttpServletRequest request, PrintWriter writer) throws Exception {
		Boolean syncFlag=Boolean.parseBoolean(request.getParameter("opFlag").toString());
		String console = modelViewService.getSyncDataInfo(syncFlag);
		writer.write(console);
        writer.flush();
        writer.close();
        logger.debug("startSyncOperation:" + console);
    }
	
	
	
	/**
	 * 
	 * 获取设备关系列表
	 * @param request
	 * @param writer
	 * @throws Exception
	 */
	@RequestMapping(value = "getNodeByRelation")
	public void getNodeByRelation(HttpServletRequest request, PrintWriter writer) throws Exception {
		long startNodeId = Long.parseLong(request.getParameter("startNodeId"));
		String relationName = request.getParameter("relationName").toString();
		String typeFilter[] = new String[2];
		String startNodeType = request.getParameter("startNodeType").toString();
		if(startNodeType.equals("San")){
			typeFilter[0] = "San";
			typeFilter[1] = "SanZoneSet";
		}else if(startNodeType.equals("Host")){
			typeFilter[0] = "Host";
			typeFilter[1] = "HostDisk";
		}else{
			
		}
		String json = JackJson.fromObjectToJson(modelViewService.getNodeByRelation(startNodeType,startNodeId,relationName,1,typeFilter));
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getNodeByRelation" + json);
	}
	
    @RequestMapping(value = "testRMI")
    public void getHostBasicInfo(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
    	modelViewService.test();
    }
    
    @RequestMapping(value = "getHostCI")
    public void getHostCI(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
    	String json = JackJson.fromObjectToJson(modelViewService.getHostCI("HostDisk"));
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getHostCI:" + json);
    }
    
    @RequestMapping(value = "getHostCIAttribute")
    public void getHostCIAttribute(HttpServletRequest request, PrintWriter writer) throws Exception {
    	String nodeId=(String)request.getParameter("nodeId");
    	String json = JackJson.fromObjectToJson(modelViewService.getHostCIAttribute(nodeId));
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getHostCIAttribute:" + json);
    }
    
    @RequestMapping(value = "getHostList")
    public void getHostList(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
    	String json = JackJson.fromObjectToJson(modelViewService.getHostList());
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getHostList:" + json);
    }
    
    @RequestMapping(value = "getTreeStructure")
    public void getTreeStructure(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
//    	String json = JackJson.fromObjectToJson(modelViewService.getTreeStructure());
    	String json = JackJson.fromObjectToJson(modelViewService.getMainPageTreeStructure());
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getTreeStructure:" + json);
    }
    
    @RequestMapping(value = "getHostListJqxgrid")
    public void getHostListJqxgrid(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
    	String json = JackJson.fromObjectToJson(modelViewService.getHostListJqxgrid());
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getHostListJqxgrid:" + json);
    }
    
    @RequestMapping(value = "getHostAttributeJqx")
    public void getHostAttributeJqx(HttpServletRequest request, PrintWriter writer) throws Exception {
    	String nodeId=(String)request.getParameter("nodeId");
    	String json = JackJson.fromObjectToJson(modelViewService.getHostAttributeJqx(nodeId));
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getHostListJqxgrid:" + json);
    }
    
    @RequestMapping(value = "getHostListJqgrid")
    public void getHostListJqgrid(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
    	String json = modelViewService.getHostListJqgrid(request);
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getHostListJqgrid:" + json);
    }
    
    @RequestMapping(value = "getCiNameAllListSource")
    public void getCiNameAllListSource(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
    	String json = modelViewService.getAlCiListJqgrid(request);
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getHostListJqgrid:" + json);
    }
    
    
    @RequestMapping(value = "getCibyType")
    public void getCibyType(HttpServletRequest request, PrintWriter writer) throws Exception {
    	
    	String json = modelViewService.getCibyType2(request.getParameter("typeName"));
        writer.write(json);
        writer.flush();
        writer.close();
    	logger.debug("getCibyType:" + json);
    }
    
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "getCiAttr")
	public void getCiAttr(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String ciId = request.getParameter("ciId");
		String json = modelViewService.getCiAttr(typeName,ciId);
        writer.write(json);
        writer.flush();
        writer.close();
	}
    
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "addCibyType")
	public void addCibyType(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String name = request.getParameter("name");
		String label = request.getParameter("label");
		String json = modelViewService.addCibyType(typeName,name,label);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "updateCiAttr")
	public void updateCiAttr(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String ciId = request.getParameter("ciId");
		String name= request.getParameter("name");
		String value = request.getParameter("value");
		String json = modelViewService.updateCiAttr(typeName,ciId,name,value);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "getCiRecord")
	public void getCiRecord(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String ciId = request.getParameter("ciId");
		String json = modelViewService.getCiRecord(typeName,ciId);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "addCiRecord")
	public void addCiRecord(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String ciId = request.getParameter("ciId");
		String repairNum = request.getParameter("repairNum");
		String damageDeviceNum = request.getParameter("damageDeviceNum");
		String deviceType = request.getParameter("deviceType");
		String replaceDeviceNum = request.getParameter("replaceDeviceNum");
		String repairDate = request.getParameter("repairDate");
		String repairType = request.getParameter("repairType");
		String maNum = request.getParameter("maNum");
		String maDate = request.getParameter("maDate");
		String json = modelViewService.addCiRecord(typeName,ciId,repairNum,damageDeviceNum,deviceType,replaceDeviceNum,repairDate,repairType,maNum,maDate);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "updateCiRecord")
	public void updateCiRecord(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String ciId = request.getParameter("ciId");
		String id = request.getParameter("id");
		String nodeKey = request.getParameter("nodeKey");
		String repairNum = request.getParameter("repairNum");
		String damageDeviceNum = request.getParameter("damageDeviceNum");
		String deviceType = request.getParameter("deviceType");
		String replaceDeviceNum = request.getParameter("replaceDeviceNum");
		String repairDate = request.getParameter("repairDate");
		String repairType = request.getParameter("repairType");
		String maNum = request.getParameter("maNum");
		String maDate = request.getParameter("maDate");
		String json = modelViewService.updateCiRecord(typeName,ciId,id,nodeKey,repairNum,damageDeviceNum,deviceType,replaceDeviceNum,repairDate,repairType,maNum,maDate);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "getCiChange")
	public void getCiChange(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String ciId = request.getParameter("ciId");
		String json = modelViewService.getCiChange(typeName,ciId);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "addCiChange")
	public void addCiChange(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String ciId = request.getParameter("ciId");
		String sequence = request.getParameter("sequence");
		String cdate = request.getParameter("cdate");
		String content = request.getParameter("content");
		String operator = request.getParameter("operator");
		String json = modelViewService.addCiChange(typeName,ciId,sequence,cdate,content,operator);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	@RequestMapping(value = "getCitAttrbyName")
	public void getCitTypeAtrrByName(HttpServletRequest request,
			PrintWriter writer) {
		
		String typeName = request.getParameter("typeName");
		String result = modelViewService.getCitAttrbyName(typeName);
        writer.write(result);
        writer.flush();
        writer.close();
	}
	
}