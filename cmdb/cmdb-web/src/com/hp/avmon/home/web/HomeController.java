package com.hp.avmon.home.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
   @Autowired
    private JdbcTemplate jdbcTemplate;

   @Autowired
   private HomeService homeService;

   
   @Autowired
   private SystemService systemService;
   
   @Autowired
   private LicenseService licenseService;
   
   
    /**
     * 主页
     * @throws IOException 
     * @throws ServletException 
     */
    @RequestMapping(value = "/")
    public void index(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        // 转到登录页面
        logger.debug("go to login.jsp !!!");
        //return main(session,model);
        //request.getRequestDispatcher("modules/index.jsp").forward(request, response);
        response.sendRedirect("modules/index.jsp");

    }
    
    @RequestMapping("/logout")
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        // 转到登录页面
        request.getSession().removeAttribute(Config.CURRENT_USER);
        logger.debug("logout !!!");
        response.sendRedirect("modules/index.jsp");
    }

    @RequestMapping(value = "/login")
    public void login(HttpServletRequest request,PrintWriter writer) {
        Map map=new HashMap();
        String errorMsg="";
        String userId=request.getParameter("userId");
        String password=request.getParameter("password");
        logger.debug("user {} is logging ...",userId);
        try {
            if (StringUtils.isBlank(userId)) {
                errorMsg+="用户名不能为空.\n";
            }
            else
            if (StringUtils.isBlank(password)) {
                errorMsg+="密码不能为空.\n";
            }
            else{
                String sql=String.format("select * from portal_users where ( USER_ID='%s' or user_id='%s') and password='%s'",userId,userId,password);
                List list=jdbcTemplate.queryForList(sql);
                if(list.size()>0){
                    Map userMap=(Map) list.get(0);
                    //获取当前用户的权限
                    sql=String.format("select d.module_id from PORTAL_USERS a,PORTAL_USERS_PORTAL_ROLES b," +
                    		"PORTAL_ROLES_PORTAL_MODULES c,PORTAL_MODULES d where a.id=b.PORTAL_USER_ROLES_ID and " +
                    		"b.PORTAL_ROLE_ID=c.PORTAL_ROLE_MODULES_ID and c.PORTAL_MODULE_ID=d.id and " +
                    		"a.USER_ID='%s'",userId);
                    list=jdbcTemplate.queryForList(sql);
                    String modules=",";
                    if(list.size()>0){
                        for(Map m: (List<Map>)list){
                            modules+=m.get("MODULE_ID")+",";
                        }
                    }
                    userMap.put("modules", modules);
                    
                    request.getSession().setAttribute(Config.CURRENT_USER, userMap);
//                    onLoginSuccess(userId);
                    
                    logger.info("{}登陆成功", userMap.get("USER_ID"));
                    map.put("userId", userMap.get("USER_ID"));
                    map.put("userName", userMap.get("REAL_NAME"));
                    
                }
                else {
                    errorMsg+="用户名或密码错误！\n";
                }
            }
        } catch (Exception e) {
            logger.error("Exception: {}", e);
        }
        if(!errorMsg.equals("")){
            map.put("errorMsg", errorMsg);
        }else{
        	//1、判断license cpu
//        	boolean licenseCpu = licenseService.isRgsCodeRigthForRegis();
//        	if(!licenseCpu){
//        		map.put("errorMsg", "License错误！");
// 		    }else{
// 		    	//2、判断license日期
// 	        	boolean licenseDate = licenseService.isDateValidLicense();
// 	        	if(!licenseDate){
// 	        		map.put("errorMsg", "License过期！");
// 	 		    }
// 		    }
        }
        String json=JackJson.fromObjectToJson(map);
        writer.write(json);
        writer.flush();
        writer.close();
    }

    private void onLoginSuccess(String userId) {
        // TODO Auto-generated method stub
        //alarmViewService.buildAlarmTreeTable(userId);
        //alarmViewService.buildAlarmTreePath(userId);
    }

    /**
     * 主页面
     * @throws IOException 
     */
    @RequestMapping("/main/index")
    public String main(HttpSession session,Model model) throws IOException {

        Map baseUser = (Map) session.getAttribute(Config.CURRENT_USER);
        if(baseUser==null){
            return "login";
        }
        else{
            HashMap map=homeService.getUserMenuTree((String)baseUser.get("USER_ID"));
            model.addAttribute("menu",map);
        }
        
        return "main/index"; 
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
    

    @RequestMapping("/test")
    public String test() {
        return "test/test";
    }

    @RequestMapping("/dev")
    public String dev() {
        return "dev";
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
    
    @RequestMapping("/updateUserPwd")
    public String updateUserPwd(
			HttpServletResponse response,
			HttpServletRequest request){
		String data = "";
		try {
			String account = request.getParameter("account");
			String oldPwd = request.getParameter("oldPassword");
			String newPwd = request.getParameter("password");
			String sql=String.format("select * from portal_users where USER_ID='%s' and password='%s'",account,oldPwd);
            List list=jdbcTemplate.queryForList(sql);
            if(list.size()>0){
            	String updatePwdSql = String.format("update portal_users set password='%s' where USER_ID='%s'",newPwd,account);
            	systemService.execute(updatePwdSql);
            	data="{success:true,msg:'修改成功!'}";
            }
            else {
            	data="{success:false,msg:'原始密码错误!'}";
            	return Utils.responsePrintWrite(response,data,null);
            }
			
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(this.getClass().getName()+" updateUserPwd",e);
			data="{success:false,msg:'系统异常!'}";
		}
		
		return Utils.responsePrintWrite(response,data,null);
	}
    
    @RequestMapping(value = "/getRegCode")
    public void getRegCode(HttpServletRequest request,PrintWriter writer) {
        Map map=new HashMap();
        try {
        	String regCode = licenseService.getServerCpuIdFromFile();
        	map.put("regCode", regCode);
        } catch (Exception e) {
            logger.error("Exception: {}", e);
        }
        
        String json=JackJson.fromObjectToJson(map);
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    @RequestMapping(value = "/registLicense")
    public void registLicense(HttpServletRequest request,PrintWriter writer) {
        String data = "";
        try {
        	String license = request.getParameter("license");
        	licenseService.saveOrUpdateLicense(license);
        	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + license);
        	data="{success:true,msg:'注册成功!'}";
        } catch (Exception e) {
            logger.error("Exception: {}", e);
            data="{success:false,msg:'注册失败!'}";
        }
        
        writer.write(data);
        writer.flush();
        writer.close();
    }
}
