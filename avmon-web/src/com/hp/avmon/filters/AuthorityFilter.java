package com.hp.avmon.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.hp.avmon.common.Config;

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

	@Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        HttpServletResponse res = (HttpServletResponse)response;
        String base = req.getContextPath();
        String uri = req.getRequestURI();
        if(uri.startsWith("/avmon-web/pages")) {
        	if(req.getSession().getAttribute(Config.CURRENT_USER) == null){
        		res.sendRedirect(base+"/main");
        	}
        	else{
        		  chain.doFilter(request, response);
        	}
        }
        else{
        	 chain.doFilter(request, response);
        }
    
    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {
        
    }
}
