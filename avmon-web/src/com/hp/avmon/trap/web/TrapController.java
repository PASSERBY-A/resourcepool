package com.hp.avmon.trap.web;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.trap.service.TrapService;

/**
 * @author muzh
 *
 */
@Controller
@RequestMapping("/trap/*")
@SuppressWarnings({"rawtypes","unchecked"})
public class TrapController {
	
	private static final Log logger = LogFactory.getLog(TrapController.class);

	@Autowired
	private TrapService trapService;
	
	/**
	 * 根据flag类型查询trap列表
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/trapList")
	@ResponseBody
	public List trapList(HttpServletResponse response,
			HttpServletRequest request) {
		List list=null;
		
		try {
			String oidValue = request.getParameter("oidValue");
			String flagType = request.getParameter("flagType");
			String sortName = request.getParameter("sortdatafield"); 
			String sortOrder = request.getParameter("sortorder");

			String pagesize = request.getParameter("pagesize");
			if(pagesize==null)
				pagesize="20";
			String pagenum = request.getParameter("pagenum");
			if(pagenum==null)
				pagenum="0";
			Integer pagenum1 = (Integer.parseInt(pagenum) + 1);
			
			list = trapService.getTrapList(flagType,sortName,sortOrder,pagesize, pagenum1.toString(), oidValue);
			if(list.size()==0){
				return list;
			}
			int totalcount=trapService.getTrapListCount(flagType,oidValue);
			Map<String, Object> map=new HashMap<String, Object>();
			map.put("totalcount", totalcount);
			list.add(map);
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" trapList()",e);
		}
		return list;
	}
	
	/**
	 * 获取oid下拉列表值
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/oidList")
	@ResponseBody
	public List getOidListByKey(HttpServletResponse response,
			HttpServletRequest request) {
		List list=null;
		
		try {
			String trapKey = request.getParameter("trapKey");
			list = trapService.getOidListByKey(trapKey);	
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getOidListByKey()",e);
		}
		return list;
	}
	
	/**
	 * 保存trap配置信息
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/saveTrap")
	public void saveOid(HttpServletResponse response,
			HttpServletRequest request,PrintWriter writer) {
		Map<String,String> map = new HashMap<String, String>();
		String trapKey = request.getParameter("trapKey");
		String oidList = request.getParameter("oidList");
		String alarmTitle = request.getParameter("alarmTitle");
		String alarmGrade = request.getParameter("alarmGrade");
		String alarmTime = request.getParameter("alarmTime");
		String alarmType = request.getParameter("alarmType");
		String alarmContent = request.getParameter("alarmContent");
		try {
			map.put("result", "success");
			trapService.saveTrap(trapKey,oidList,alarmTitle,alarmGrade,alarmTime,alarmType,alarmContent);	
		} catch (Throwable e) {
			map.put("result", "failure");
			e.printStackTrace();
			logger.error(this.getClass().getName()+" saveTrap()",e);
		}
		String json=JackJson.fromObjectToJson(map);
        writer.write(json);
        writer.flush();
        writer.close();
	} 
	
	/**
	 * 获取已配置oid规则列表
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getOidRule")
	@ResponseBody
	public Map getOidRule(HttpServletResponse response,
			HttpServletRequest request) {
		Map m=null;
		
		try {
			String trapKey = request.getParameter("trapKey");
			m = trapService.getOidRule(trapKey);	
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getOidRule()",e);
		}
		return m;
	}
	
	@RequestMapping(value = "traprulemgr")
    public String trapmgr(Locale locale, Model model) {
        return "/discovery/traprulemgr";
    }
	
	/**
	 * 获取手动配置trap原始oid rule下拉列表值
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/ruleOidList")
	@ResponseBody
	public List getRuleOidList(HttpServletResponse response,
			HttpServletRequest request) {
		List list=null;
		
		try {
			list = trapService.getRuleOidList();	
		} catch (Throwable e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" getRuleOidList()",e);
		}
		return list;
	}
	
	/**
	 * 删除trap配置信息
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/deleteTrap")
	public void deleteTrap(HttpServletResponse response,
			HttpServletRequest request,PrintWriter writer) {
		Map<String,String> map = new HashMap<String, String>();
		String trapKeys = request.getParameter("trapKeys");
		try {
			map.put("result", "success");
			trapService.deleteTrap(trapKeys);	
		} catch (Throwable e) {
			map.put("result", "failure");
			e.printStackTrace();
			logger.error(this.getClass().getName()+" deleteTrap()",e);
		}
		String json=JackJson.fromObjectToJson(map);
        writer.write(json);
        writer.flush();
        writer.close();
	} 
	
	/**
	 * 启用trap配置信息
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/startTrap")
	public void startTrap(HttpServletResponse response,
			HttpServletRequest request,PrintWriter writer) {
		Map<String,String> map = new HashMap<String, String>();
		String trapKeys = request.getParameter("trapKeys");
		try {
			map.put("result", "success");
			trapService.startTrap(trapKeys);	
		} catch (Throwable e) {
			map.put("result", "failure");
			e.printStackTrace();
			logger.error(this.getClass().getName()+" startTrap()",e);
		}
		String json=JackJson.fromObjectToJson(map);
        writer.write(json);
        writer.flush();
        writer.close();
	} 
	/**
	 * 停用trap配置信息
	 * @param response
	 * @param request
	 */
	@RequestMapping(value = "/stopTrap")
	public void stopTrap(HttpServletResponse response,
			HttpServletRequest request,PrintWriter writer) {
		Map<String,String> map = new HashMap<String, String>();
		String trapKeys = request.getParameter("trapKeys");
		try {
			map.put("result", "success");
			trapService.stopTrap(trapKeys);	
		} catch (Throwable e) {
			map.put("result", "failure");
			e.printStackTrace();
			logger.error(this.getClass().getName()+" stopTrap()",e);
		}
		String json=JackJson.fromObjectToJson(map);
        writer.write(json);
        writer.flush();
        writer.close();
	} 
}
