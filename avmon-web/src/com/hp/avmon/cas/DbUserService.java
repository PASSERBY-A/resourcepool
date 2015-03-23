/**
 * 
 */
package com.hp.avmon.cas;

import java.security.Principal;
import java.util.Map;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @author chenxuep
 *
 */
@Component
public class DbUserService implements UserService, InitializingBean {
	
	private static final String SQL_HAS_USER = "SELECT count(ID) FROM PORTAL_USERS u WHERE u.USER_ID=?";
	
	private static final String SQL_CREATE_USER = "INSERT INTO PORTAL_USERS u (u.ID, u.USER_ID, u.REAL_NAME, u.EMAIL, u.MOBILE)"+
			"VALUES(?, ?, ?, ?, ?)";
	
	private static final String SQL_UPDATE_USER = "UPDATE PORTAL_USERS u SET u.EMAIL=?, u.MOBILE=? WHERE u.USER_ID=?";
	
	private static final String SQL_NEXT_USERID = "SELECT USER_SEQ.NEXTVAL FROM dual";
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	/* (non-Javadoc)
	 * @see com.hp.avmon.cas.UserService#hasUser(java.lang.String)
	 */
	@Override
	public boolean hasUser(String username) {
		int count = jdbcTemplate.queryForInt(SQL_HAS_USER, new Object[]{username});
		return count>0;
	}

	/* (non-Javadoc)
	 * @see com.hp.avmon.cas.UserService#createUser(java.security.Principal)
	 */
	@Override
	public int createUser(Principal principal) {
		AttributePrincipal user = (AttributePrincipal)principal;
		Map<String, Object> attributes = user.getAttributes();
		long nextUserID = getNextUserID();
		return jdbcTemplate.update(SQL_CREATE_USER, new Object[]{nextUserID,
				user.getName(), user.getName(), attributes.get("email"), attributes.get("phone")});
	}

	/* (non-Javadoc)
	 * @see com.hp.avmon.cas.UserService#updateUser(java.security.Principal)
	 */
	@Override
	public int updateUser(Principal principal) {
		AttributePrincipal user = (AttributePrincipal)principal;
		Map<String, Object> attributes = user.getAttributes();
		return jdbcTemplate.update(SQL_UPDATE_USER, new Object[]{
			attributes.get("email"), attributes.get("phone"), user.getName()	
		});
	}

	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.jdbcTemplate, "JdbcTemplate can not be null.");
	}
	
	/**
	 * @return
	 */
	private long getNextUserID() {
		return this.jdbcTemplate.queryForInt(SQL_NEXT_USERID);
	}

}
