/**
 * 
 */
package com.hp.avmon.cas;

import java.security.Principal;

/**
 * @author chenxuep
 *
 */
public interface UserService {
	
	boolean hasUser(String username);
	
	int createUser(Principal principal);
	
	int updateUser(Principal principal);

}
