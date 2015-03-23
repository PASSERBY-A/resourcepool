package com.hp.avmon.cmdb.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pages/cmdb/*")
public class CmdbController {
	
	@RequestMapping(value = "/overView")
	public String overView(HttpServletRequest request) {
		return "cmdb/overview";
	}
	
	@RequestMapping(value = "/cmdbView")
	public String cmdbView(HttpServletRequest request) {
		return "cmdb/cmdbview";
	}
	
	@RequestMapping(value = "/viewManager")
	public String viewManager(HttpServletRequest request) {
		return "cmdb/viewmanager";
	}
	
	@RequestMapping(value = "/classManager")
	public String classManager(HttpServletRequest request) {
		return "cmdb/classmanager";
	}
	@RequestMapping(value = "/relationshipManager")
	public String relationshipManager(HttpServletRequest request) {
		return "cmdb/relationshipmanager";
	}	
	 
	@RequestMapping(value = "/classModelManager")
	public String classModelManager(HttpServletRequest request) {
		return "cmdb/classmodelmanager";
	}	
	
	@RequestMapping(value = "/relationshipModelManager")
	public String relationshipModelManager(HttpServletRequest request) {
		return "cmdb/relationshipmodelmanager";
	}

	@RequestMapping(value = "/resourceSearch")
	public String resourceSearch(HttpServletRequest request) {
		return "cmdb/resourcesearch";
	}
	
	
	
}
