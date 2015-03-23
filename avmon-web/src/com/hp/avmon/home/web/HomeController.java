package com.hp.avmon.home.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hp.avmon.alarm.service.AlarmViewService;
import com.hp.avmon.common.Config;
import com.hp.avmon.common.jackjson.JackJson;
import com.hp.avmon.home.service.HomeService;
import com.hp.avmon.home.service.LicenseService;
import com.hp.avmon.system.service.SystemService;
import com.hp.avmon.utils.Utils;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {

	private static final Logger logger = LoggerFactory
			.getLogger(HomeController.class);
	private static String userId = null;
	private static String password = null;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private HomeService homeService;

	@Autowired
	private AlarmViewService alarmViewService;

	@Autowired
	private SystemService systemService;

	@Autowired
	private LicenseService licenseService;

	/**
	 * 主页
	 * 
	 * @throws IOException
	 * @throws ServletException
	 */
	@RequestMapping(value = "/")
	public String index(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		// 转到登录页面
		logger.debug("go to login.jsp!!!");
		return "redirect:/main";
	}

	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		// 转到登录页面
		request.getSession().invalidate();
		request.getSession().removeAttribute(Config.CURRENT_USER);
		logger.debug("logout !!!");
		return "redirect:/main ";
	}

	public boolean checkLogin(HttpServletRequest request){
		// 转到登录页面
		if(request.getSession().getAttribute(Config.CURRENT_USER)!=null)
			return true;
		return false;
	}
	
	// 4A单点登录时跳入此方法
	// 4A单点登录URL举例：http://localhost:8080/avmon-web/checkAiuapToken?token=token&appAcctId=appAcctId&flag=flag
	@RequestMapping(value = "/checkAiuapToken")
	public String checkAiuapToken(HttpServletRequest request){
		String token = request.getParameter("token");
		String appAcctId = request.getParameter("appAcctId");
		String flag = request.getParameter("flag");
		// 回调4A验证token有效性
		// 待4A环境
		// 解析4A返回的结果（XML格式）。若通过，将从账号(APPACCTID)赋予userId。
		// 使用同伟的解析XML的模板
		userId = "admin";
		String sql = String.format("select * from portal_users where ( USER_ID='%s' or user_id='%s')", userId, userId);
		List list = jdbcTemplate.queryForList(sql);
		Map userMap = (Map) list.get(0);
		password = (String) userMap.get("password");
		
		return login(request, null);
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(HttpServletRequest request, PrintWriter writer) {
		if(checkLogin(request)==true){
			return "redirect:/main";
		}
		String errorMsg = "";
		/*String userId = request.getParameter("userId");
		
		
		String password = request.getParameter("password");*/
		if(userId == null){	//若userId==null，则代表用户是直接访问的Avmon。否则代表用户是从4A登录访问的Avmon。
			userId = request.getParameter("userId");
			password = request.getParameter("password");
		}
		logger.debug("user {} is logging ...", userId);
		Locale locale = request.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		try {
			if (StringUtils.isBlank(userId)) {
				// errorMsg+="用户名不能为空.\n";
				//map.put("errorMsg", bundle.getString("userNameCannotEmpty"));
				 request.setAttribute("errorMsg", "密码不能为空");
				 return "forward:/main";
			} else if (StringUtils.isBlank(password)){
				// errorMsg+="密码不能为空.\n";
				request.setAttribute("errorMsg", "密码不能为空");
				return "forward:/main";
				//map.put("errorMsg", bundle.getString("passwordCannotEmpty"));
			} else {
				 try {
					  //1、判断license cpu 
					  boolean licenseCpu =licenseService.isRgsCodeRigthForRegis(); 
					  if(!licenseCpu){
						  request.setAttribute("errorMsg", "License错误！"); 
						  return "forward:/main";
					  }else{ 
						  //2、判断license日期 
						  boolean licenseDate = licenseService.isDateValidLicense(); 
						  if(!licenseDate){
							  request.setAttribute("errorMsg", "License过期！"); 
						  	  return "forward:/main";
						  }
					  }
				  }catch (Exception e) {
					  request.setAttribute("errorMsg", "License认证失败，请联系管理员"); 
					  return "forward:/main";
				  }


				String sql = String
						.format("select * from portal_users where ( USER_ID='%s' or user_id='%s') and password='%s'",
								userId, userId, password);
				List list = jdbcTemplate.queryForList(sql);
				if (list.size() > 0) {
					Map userMap = (Map) list.get(0);
					// 获取当前用户的权限
					sql = String
							.format("select d.module_id from PORTAL_USERS a,PORTAL_USERS_PORTAL_ROLES b,"
									+ "PORTAL_ROLES_PORTAL_MODULES c,PORTAL_MODULES d where a.id=b.PORTAL_USER_ROLES_ID and "
									+ "b.PORTAL_ROLE_ID=c.PORTAL_ROLE_MODULES_ID and c.PORTAL_MODULE_ID=d.id and "
									+ "a.USER_ID='%s'", userId);
					list = jdbcTemplate.queryForList(sql);
					String modules = ",";
					if (list.size() > 0) {
						for (Map m : (List<Map>) list) {
							modules += m.get("MODULE_ID") + ",";
						}
					}
					userMap.put("modules", modules);
					Map menuMap=licenseService.getUserMainMenuMap(userId);
					Map roleMap=licenseService.queryRoleAndDepByUserId(userId);
					System.out.println(menuMap);
					System.out.println(roleMap);
					request.getSession().setAttribute(Config.CURRENT_USER,userMap);
					logger.info("{}登陆成功", userMap.get("REAL_NAME"));
					request.getSession().setAttribute("userName", userMap.get("REAL_NAME"));
					request.getSession().setAttribute("userId", userMap.get("USER_ID"));
					request.getSession().setAttribute("theme", "-gray");
				    request.getSession().setAttribute("browserLang", "zh_CN");
                    request.getSession().setAttribute("menuMap", menuMap);
                    request.getSession().setAttribute("roleMap", roleMap);
					return "redirect:/main";
				} else {
					// errorMsg+="用户名或密码错误！\n";
					/*map.put("errorMsg",
							bundle.getString("userNameOrPasswordError"));*/
					request.setAttribute("errorMsg","用户名密码错误，请重新输入");
					return "forward:/main"; 
				}
			}
		} catch (Exception e) {
			logger.error("Exception: {}", e);
		}

		/*
		 * String json=JackJson.fromObjectToJson(map); writer.write(json); //
		 * logger.debug(json); writer.flush(); writer.close();
		 */
		
		return "redirect:/main";
	}

	/**
	 * 主页面
	 * 
	 * @throws IOException
	 */
	@RequestMapping("/main")
	public String main(HttpSession session, Model model) throws IOException {
		Map baseUser = (Map) session.getAttribute(Config.CURRENT_USER);
		if (baseUser == null) {
			return "login";
		} else {
			/*HashMap map = homeService.getUserMenuTree((String) baseUser
					.get("USER_ID"));*/
			//model.addAttribute("menu", map);
		}
		return "main";
	}

	/**
	 * 头部
	 */
	@RequestMapping("/header")
	public String header() {
		return "header";
	}

	/**
	 * 欢迎界面
	 */
	@RequestMapping("/welcome")
	public String welcome() {
		return "welcome";
	}

	@RequestMapping("/sysMag")
	public String sysMag() {
		return "/user/main";
	}

	@RequestMapping("/dashboard-designer")
	public String dashboardDesigner() {
		return "designer/dashboard";
	}

	@RequestMapping("/updateUserPwd")
	@ResponseBody
	public Map updateUserPwd(
			HttpServletRequest request) {
		//String data = "";
		Map data = new HashMap();
		Locale locale = request.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		try {
			String account = request.getParameter("account");
			String oldPwd = request.getParameter("oldPassword");
			String newPwd = request.getParameter("password");
			String sql = String
					.format("select * from portal_users where USER_ID='%s' and password='%s'",
							account, oldPwd);
			List list = jdbcTemplate.queryForList(sql);
			if (list.size() > 0) {
				String updatePwdSql = String
						.format("update portal_users set password='%s' where USER_ID='%s'",
								newPwd, account);
				systemService.execute(updatePwdSql);
			/*	data = "{success:true,msg:'"
						+ bundle.getString("modifySuccess") + "'}";*/
				data.put("success", bundle.getString("modifySuccess"));
			} else {
			/*	data = "{success:false,msg:'"
						+ bundle.getString("originalPasswordError") + "'}";*/
				data.put("error", bundle.getString("originalPasswordError"));
				//return Utils.responsePrintWrite(response, data, null);
			}

		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName() + " updateUserPwd", e);
			/*data = "{success:false,msg:'" + bundle.getString("systemException")
					+ "'}";*/
			data.put("syserr", bundle.getString("originalPasswordError"));
		}
		return data;

		//return Utils.responsePrintWrite(response, data, null);
	}

	@RequestMapping(value = "/getRegCode")
	@ResponseBody
	public Map getRegCode(HttpServletRequest request) {
		Map map = new HashMap();
		try {
			String regCode = licenseService.getServerCpuIdFromFile();
			map.put("regCode", regCode);
		} catch (Exception e) {
			logger.error("Exception: {}", e);
		}
        return map;
		/*String json = JackJson.fromObjectToJson(map);
		writer.write(json);
		writer.flush();
		writer.close();*/
	}
	
	
	@RequestMapping(value = "main/menuTree")
    public void getMenuTree(HttpServletRequest request
            ,PrintWriter writer) throws Exception {
        
        String userId=Utils.getCurrentUserId(request);
        String type=request.getParameter("type");
        String parentId=request.getParameter("id");
        if(parentId==null||parentId.equals("")){
            parentId="root";
        }
        Object obj=homeService.getMenuTree(userId,parentId,type);
        String json=JackJson.fromObjectToJson(obj);
        //System.out.println(json);
        writer.write(json);
        writer.flush();
        writer.close();
    }  

	@RequestMapping(value = "/registLicense")
	public void registLicense(HttpServletRequest request, PrintWriter writer) {
		String data = "";
		Locale locale = request.getLocale();
		ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
		try {
			String license = request.getParameter("license");
			licenseService.saveOrUpdateLicense(license,bundle);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"
					+ license);
			data = "{success:true,msg:'"
					+ bundle.getString("registrationSuccessful") + "'}";
		} catch (Exception e) {
			logger.error("Exception: {}", e);
			data = "{success:false,msg:'"
					+ bundle.getString("registrationFailed") + "'}";
		}

		writer.write(data);
		writer.flush();
		writer.close();
	}
}
