/**
 * 
 */
package com.hp.avmon.cas;

import java.util.List;
import java.util.Map;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.Cas20ProxyReceivingTicketValidationFilter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hp.avmon.common.Config;

/**
 * @author chenxuep
 *
 */
public class CASTicketValidationFilter extends
		Cas20ProxyReceivingTicketValidationFilter {
	
	private UserService userService;
	
	private JdbcTemplate jdbcTemplate;

	@Override
	protected void initInternal(FilterConfig filterConfig)
			throws ServletException {
		super.initInternal(filterConfig);
		
		WebApplicationContext appContext = WebApplicationContextUtils.getRequiredWebApplicationContext(filterConfig.getServletContext());
		this.userService = appContext.getBean(UserService.class);
		this.jdbcTemplate = appContext.getBean(JdbcTemplate.class);
	}

	/* (non-Javadoc)
	 * @see org.jasig.cas.client.validation.AbstractTicketValidationFilter#onSuccessfulValidation(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.jasig.cas.client.validation.Assertion)
	 */
	@Override
	protected void onSuccessfulValidation(HttpServletRequest request,
			HttpServletResponse response, Assertion assertion) {
		if(this.userService==null) {
			log.warn("CAS can't create/update user, because UserService not found.");
			return;
		}
		
		AttributePrincipal principal = assertion.getPrincipal();
		Map<String, Object> attributes = principal.getAttributes();
		if(attributes==null || attributes.isEmpty()) {
			return;
		}
		
		// 将用户信息更新到本地数据库中
		if(userService.hasUser(principal.getName())) {
			log.debug("User '"+principal.getName()+"' has already exist, so update user information.");
			
			int result = userService.updateUser(principal);
			if(result>0) {
				log.debug("Update user '"+principal.getName()+"' is success.");
			} else {
				log.debug("Update user '"+principal.getName()+"' is fail.");
			}
		} else {
			log.debug("User '"+principal.getName()+"' does not exist, so create it.");
			
			int result = userService.createUser(principal);
			if(result>0) {
				log.debug("Create user '"+principal.getName()+"' is success.");
			} else {
				log.debug("Create user '"+principal.getName()+"' is fail.");
			}
		}
		
		initUser(request, this.jdbcTemplate, principal.getName());
	}
	
	/**
	 * 根据avmon的需要初始化用户信息。
	 * 
	 * @param request
	 * @param jdbcTemplate
	 * @param userId
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void initUser(HttpServletRequest request, JdbcTemplate jdbcTemplate, String userId) {
		String sql=String.format("select * from portal_users where ( USER_ID='%s' or user_id='%s')",userId,userId);
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
        }
	}

}
