package com.hp.avmon.ciMgr.web;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.tools.ant.taskdefs.email.EmailAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.EnumMultiset;
import com.hp.avmon.ciMgr.service.CiMgrService;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.modelView.service.ModelViewService;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/ciMgr/*")
public class CiMgrController {

	private static final Logger logger = LoggerFactory
			.getLogger(CiMgrController.class);

	@Autowired
	private CiMgrService ciMgrService;
	
	@Autowired
	private ModelViewService modelViewService;
	
	
	
	@RequestMapping(value = "index")
	public String index(Locale locale, Model model) {

		return "/pages/ciMgr/index";

	}
	
	@RequestMapping(value = "getBaseClasses")
	public void getBaseClasses(HttpServletRequest request,
			PrintWriter writer) {
		
		String id = request.getParameter("id");
		String result = ciMgrService.getBaseClasses(id);
        writer.write(result);
        writer.flush();
        writer.close();
	}
	
    @RequestMapping(value = "getAllClass")
    public void getAllClass(HttpServletRequest request, PrintWriter writer) throws Exception{
           
           String result = null;
           
           result = ciMgrService.getAllClass(request);

           logger.debug(result);
           writer.write(result);
     writer.flush();
     writer.close();
    }

	
	@RequestMapping(value = "addClass")
	public void addClass(HttpServletRequest request,PrintWriter writer) {
		
		String id = request.getParameter("id");
		String pid = request.getParameter("pid");
		String name = request.getParameter("name");
		String label = request.getParameter("label");
		String displayIcon = request.getParameter("iconSkin");
		String derivedFrom = request.getParameter("derivedFrom");
		Map result = ciMgrService.addClass(id,name,pid,displayIcon,label,derivedFrom);
		String json = JackJson.fromObjectToJson(result);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "delClass")
	public void delClass(HttpServletRequest request,PrintWriter writer) {
		
		String id = request.getParameter("id");
		String json = ciMgrService.delClass(id);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除自当前选择的ci实例节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "delCiInfo")
	public void delCiInfo(HttpServletRequest request,PrintWriter writer) {
		
		String id = request.getParameter("id");
		String json = ciMgrService.delCiInfo(request);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "editClass")
	public void editClass(HttpServletRequest request,PrintWriter writer) {
		String id = request.getParameter("id");
		String name = request.getParameter("name");
		String json = ciMgrService.editClass(id,name);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "getCiTypeAttr")
	public void getCiTypeAttr(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String json = ciMgrService.getCiTypeAttrbyName(typeName);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "addCiTypeAttr")
	public void addCiTypeAttr(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("derivedFrom");
		String json = (String) ciMgrService.addCiTypeAttr(typeName);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	
	/**
	 * 根据类型获取所有ci属性列表
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "getCibyType")
	public void getCibyType(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String json = ciMgrService.getCibyType2(typeName);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 添加节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "addCibyType")
	public void addCibyType(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String name = request.getParameter("name");
		String label = request.getParameter("label");
		String json = ciMgrService.addCibyType(typeName,name,label);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 添加ci信息
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "addCiInfoBytype")
	public void addCiInfoBytype(HttpServletRequest request,PrintWriter writer) {
		String json = ciMgrService.addCiInfoBytype(request);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	/**
	 * 删除节点
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "updateCibyType")
	public void updateCibyType(HttpServletRequest request,PrintWriter writer) {
		String typeName = request.getParameter("typeName");
		String name = request.getParameter("name");
		String label = request.getParameter("label");
		String id = request.getParameter("id");
		String ciLabel = request.getParameter("ciLabel");
		Map map = request.getParameterMap();
		String json = ciMgrService.updateCiInfo(request);
        writer.write(json);
        writer.flush();
        writer.close();
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
		String json = ciMgrService.getCiAttr(typeName,ciId);
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
		String json = ciMgrService.updateCiAttr(typeName,ciId,name,value);
        writer.write(json);
        writer.flush();
        writer.close();
	}
	
	
	@RequestMapping(value = "showProperty")
	public void showProperty(HttpServletRequest request,
			PrintWriter writer) {
		
		String typeName = request.getParameter("typeName");
		String result = ciMgrService.showProperty(typeName);
        writer.write(result);
        writer.flush();
        writer.close();
	}

	
	@RequestMapping(value = "getCitAttrbyName")
	public void getCitTypeAtrrByName(HttpServletRequest request,
			PrintWriter writer) {
		
		String typeName = request.getParameter("typeName");
		String result = ciMgrService.getCitAttrbyName(typeName);
        writer.write(result);
        writer.flush();
        writer.close();
	}
	
	@RequestMapping(value = "getCitTypeAtrrByNameAll")
	public void getCitTypeAtrrByNameAll(HttpServletRequest request,
			PrintWriter writer) {
		
		String typeName = request.getParameter("typeName");
		String result = ciMgrService.getCitTypeAtrrByNameAll(typeName);
        writer.write(result);
        writer.flush();
        writer.close();
	}
	
	@RequestMapping(value = "getRelationList")
	public void getRelationList(HttpServletRequest request,
			PrintWriter writer) {
		String result = ciMgrService.getRelationList();
        writer.write(result);
        writer.flush();
        writer.close();
	}
	
	@RequestMapping(value = "getNodeRelationMapL1")
	public void getNodeRelationMapL1(HttpServletRequest request,
			PrintWriter writer) {
		String result = ciMgrService.getNodeRelationMapL1(request);
        writer.write(result);
        writer.flush();
        writer.close();
	}
}
