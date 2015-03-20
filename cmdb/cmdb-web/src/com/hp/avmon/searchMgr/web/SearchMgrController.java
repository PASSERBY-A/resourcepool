package com.hp.avmon.searchMgr.web;

import java.io.PrintWriter;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.relMgr.service.RelMgrService;
import com.hp.avmon.searchMgr.service.SearchMgrService;
import com.hp.avmon.common.jackjson.JackJson;

/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/searchMgr/*")
public class SearchMgrController {

	private static final Logger logger = LoggerFactory
			.getLogger(SearchMgrController.class);

	@Autowired
	private RelMgrService classMgrService;
	@Autowired
	private SearchMgrService searchMgrService;
	
	
	
	@RequestMapping(value = "index")
	public String index(Locale locale, Model model) {

		return "/pages/searchMgr/index";

	}
	
	@RequestMapping(value = "propertyConfig")
	public String propertyConfig(){
		return "/pages/searchMgr/propertyConfig";
	}
	
	@RequestMapping(value = "getTypeNameList")
	public void getTypeNameList(HttpServletRequest request, PrintWriter writer) throws Exception{
        String result = "";
        result = searchMgrService.getTypeNameList();
        logger.debug(result);
        writer.write(result);
        writer.flush();
        writer.close();
   }
	
   @RequestMapping(value = "getPropertyInfoList")
  	public void getPropertyInfoList(HttpServletRequest request, PrintWriter writer) throws Exception{
  		String typeName = request.getParameter("typeName");
          String result = "";
          result = searchMgrService.getPropertyInfoList(typeName);
          logger.debug(result);
          writer.write(result);
          writer.flush();
          writer.close();
     }
     
     @RequestMapping(value = "setPropertyInfo")
   	public void setPropertyInfo(HttpServletRequest request, PrintWriter writer) throws Exception{
   		String typeName = request.getParameter("typeName");
           String result = "";
           result = searchMgrService.setPropertyInfo(request);
           logger.debug(result);
           writer.write(result);
           writer.flush();
           writer.close();
      }
     
   
	 @RequestMapping(value = "getCitTypeAtrrByNameAll")
	public void getCitTypeAtrrByNameAll(HttpServletRequest request, PrintWriter writer) throws Exception{
		String typeName = request.getParameter("typeName");
        String result = "";
        result = searchMgrService.getCitTypeAtrrByNameAll(typeName);
        logger.debug(result);
        writer.write(result);
        writer.flush();
        writer.close();
   }
	
	
    @RequestMapping(value = "searchHost")
    public void searchHost(HttpServletRequest request, PrintWriter writer) throws Exception{
           String result = null;
           result = searchMgrService.searchHost(request);
           logger.debug(result);
           writer.write(result);
           writer.flush();
           writer.close();
    }
    
    
    @RequestMapping(value = "searchHostHba")
    public void searchHostHba(HttpServletRequest request, PrintWriter writer) throws Exception{
           String result = null;
           result = searchMgrService.searchHostHba(request);
           logger.debug(result);
           writer.write(result);
           writer.flush();
           writer.close();
    }
    
    
    @RequestMapping(value = "searchSanZone")
    public void searchSanZone(HttpServletRequest request, PrintWriter writer) throws Exception{
           String result = null;
           result = searchMgrService.searchSanZone(request);
           logger.debug(result);
           writer.write(result);
           writer.flush();
           writer.close();
    }
    
    @RequestMapping(value = "searchStorage")
    public void searchStorage(HttpServletRequest request, PrintWriter writer) throws Exception{
           String result = null;
           result = searchMgrService.searchStorage(request);
           logger.debug(result);
           writer.write(result);
           writer.flush();
           writer.close();
    }
    
    @RequestMapping(value = "getSanList")
    public void getSanList(HttpServletRequest request, PrintWriter writer) throws Exception{
        String result = null;
        result = searchMgrService.getSanList(request);
        logger.debug(result);
        writer.write(result);
        writer.flush();
        writer.close();
 }
	
	
}
