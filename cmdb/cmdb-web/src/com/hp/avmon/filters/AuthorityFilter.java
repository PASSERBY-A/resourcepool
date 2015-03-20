package com.hp.avmon.filters;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;

import com.hp.avmon.common.Config;
import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.ireport.util.StringUtil;

/**
 * url访问过滤器
 * 
 * 
 * @author yan1
 *
 */
public class AuthorityFilter implements Filter  {

    @Override
    public void destroy() {
        
    }

    @SuppressWarnings("rawtypes")
	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String base = req.getContextPath();
        String uri = req.getRequestURI();
        
        if(uri.endsWith("/modules/index.jsp") 
//        		|| uri.endsWith("avmon-web/") 
//        		|| uri.endsWith("login.jsp") 
//        		|| uri.endsWith("login.do")
        		//视图展示不验证 -qiaoju
        		|| uri.endsWith("modelView/index.jsp")
        		|| uri.endsWith("modelView/mainFrame.jsp")
        		|| uri.indexOf("pages/ciMgr")>0
        		) {
        	// 直接进入登录页面
            chain.doFilter(request, response);
        } else {
            if(req.getSession().getAttribute(Config.CURRENT_USER) == null) {
                String usr = req.getParameter("usr");
                String pwd = req.getParameter("pwd");
            	if (!StringUtil.isEmpty(usr) && !StringUtil.isEmpty(pwd)) {
            		// 查询用户是否正确
            		String sql = String.format("select * from portal_users where ( USER_ID='%s' or user_id='%s') and password='%s'",usr,usr,pwd);
            		JdbcTemplate jdbc = SpringContextHolder.getBean("jdbcTemplate");

            		List list = jdbc.queryForList(sql);
                    if(list.size() > 0) {
                    	// 将用户信息存储、允许跳转
                        req.getSession().setAttribute(Config.CURRENT_USER, (Map) list.get(0));
                        chain.doFilter(request, response);
                    } else {
                    	// 重新登录
                    	res.sendRedirect(base + "/modules/index.jsp");
                    }
            	} else {
            		// 重新登录
                	res.sendRedirect(base + "/modules/index.jsp");
            	}
            } else {
            	// 用户信息已经存在、允许跳转
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }
}
