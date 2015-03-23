package com.hp.avmon.config.web;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.Config;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.config.service.MonitorViewService;



/**
 * Handles requests for the application home page.
 */
@Controller
@RequestMapping("/pages/config/monitorview/*")
public class MonitorViewController {
	
	private static final Log logger = LogFactory.getLog(MonitorViewController.class);
	
    @Autowired
    private MonitorViewService monitorViewService;
    
	/**
	 * 加载树形菜单
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception 
	 */
	@RequestMapping(value = "menuTree")
    public void getMenuTree(HttpServletRequest request, PrintWriter writer) throws Exception {
        
        @SuppressWarnings("rawtypes")
		List<Map> moTreeList = monitorViewService.getViewTree(request);
        String json = JackJson.fromObjectHasDateToJson(moTreeList);
        writer.write(json);
        writer.flush();
        writer.close();
    }
	
	/**
	 * 加载过滤条件[字段]
	 * 
	 * @param request
	 * @param writer
	 * @throws Exception 
	 */
	@RequestMapping(value = "filterField")
    public void getFilterField(HttpServletRequest request, PrintWriter writer) throws Exception {
        
        @SuppressWarnings("rawtypes")
		List<Map> moTreeList = monitorViewService.getFilterField(request);
        
        String json = JackJson.fromObjectHasDateToJson(moTreeList);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    /**
     * 根据request信息获取用户信息
     * 
     * @param request
     * @param writer
     * @throws Exception
     */
    private String getCurrentUserId(HttpServletRequest request) {
        @SuppressWarnings("rawtypes")
		Map currUser = (Map)request.getSession().getAttribute(Config.CURRENT_USER);
        
        String userId = "unknown";
        if(currUser != null){
            userId = (String) currUser.get("USER_ID");
            if(userId == null){
                userId = "unknown";
            }
        }
        
        return userId;
    }
    
	/**
	 * 在[view managerment]点击创建目录时
	 * 
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "/addFolderTreeView")
	public void addFolderTreeView(HttpServletRequest request, PrintWriter writer) {
        
		String caption = request.getParameter("caption");
		String parentId = request.getParameter("parentId");
		String viewId = "V" + getCurrentTimeString("yyyyMMddHHmmss");
		Short isDir = new Short("1");
		String createBy = getCurrentUserId(request);
		String defaultCls = "";
		
		String viewType = request.getParameter("viewType");
		
		
		// 往TD_AVMON_VIEW表插入数据
		@SuppressWarnings("rawtypes")
		Map result = monitorViewService.insertView(viewId, caption, parentId, isDir, createBy, defaultCls, viewType);
        String json = JackJson.fromObjectHasDateToJson(result);
        writer.write(json);
        writer.flush();
        writer.close();
    }
	
	/**
	 * 在[view managerment]点击创建节点时
	 * 
	 * @param request
	 * @param writer
	 */
	@RequestMapping(value = "/addTreeView")
	public void addTreeView(HttpServletRequest request, PrintWriter writer) {
        
		String caption = request.getParameter("caption");
		String parentId = request.getParameter("parentId");
		String viewId = "V" + getCurrentTimeString("yyyyMMddHHmmss");
		Short isDir = new Short("0");
		String createBy = getCurrentUserId(request);
		String defaultCls = "icon-plugin";
		
		String viewType = request.getParameter("viewType");
		
		// 往TD_AVMON_VIEW表插入数据
		@SuppressWarnings("rawtypes")
		Map result = monitorViewService.insertView(viewId, caption, parentId, isDir, createBy, defaultCls, viewType);
        String json = JackJson.fromObjectHasDateToJson(result);
        writer.write(json);
        writer.flush();
        writer.close();
    }
	
	/**
	 * 删除选择的节点
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
	@RequestMapping(value = "/delTreeViewById")
	public void delTreeViewById(HttpServletRequest request, PrintWriter writer) {
        
		String viewId = request.getParameter("viewId");
		
		// 往TD_AVMON_MON_TYPE表删除数据
		@SuppressWarnings("rawtypes")
		Map result = monitorViewService.deleteView(viewId,getCurrentUserId(request));
        String json = JackJson.fromObjectHasDateToJson(result);
        writer.write(json);
        writer.flush();
        writer.close();
    }
	
	/**
	 * 单击[左边树形节点]，进行读取
	 * 
	 * @param request
	 * @param writer
	 * @return
	 */
	@RequestMapping(value = "/editMonitorView")
	public void editMonitorView(HttpServletRequest request, PrintWriter writer) {
		
		String viewId = request.getParameter("viewId");
		
		@SuppressWarnings("rawtypes")
		Map result = monitorViewService.editView(viewId);
		
        String json = JackJson.fromObjectHasDateToJson(result);
        writer.write(json);
        writer.flush();
        writer.close();
		
	}
	
	/**
	 * 单击[左边树形节点]，进行读取
	 * 
	 * @param request
	 * @param writer
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value = "/saveMonitorView")
	public void saveMonitorView(HttpServletRequest request, PrintWriter writer) throws UnsupportedEncodingException {
		
		String viewId = request.getParameter("viewId");
		String viewNm = request.getParameter("viewNm");
		String filters = "";
		if (request.getParameter("filters") != null) {
			filters = URLDecoder.decode(request.getParameter("filters"), "UTF-8");
		}		
		String filterRule = request.getParameter("filterRule");
		
		@SuppressWarnings("rawtypes")
		Map result = monitorViewService.saveView(viewId, viewNm, filters, filterRule,getCurrentUserId(request));
		
        String json = JackJson.fromObjectHasDateToJson(result);
        writer.write(json);
        writer.flush();
        writer.close();
		
	}
	
    /**
     * Gets the current time string.
     * 
     * @param pattern
     *            the pattern
     * @return the current time string
     */
    public static String getCurrentTimeString(String pattern) {
        java.util.Date now = Calendar.getInstance().getTime();
        Format formatter = new SimpleDateFormat(pattern);
        return formatter.format(now);
    }
}
