/**
 * 
 */
package com.hp.avmon.system.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * @author muzh
 *
 */
@Service
@SuppressWarnings("unchecked")
public class SystemService {
	
	private static final Log logger = LogFactory.getLog(SystemService.class);
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public void execute(String sql){
		try {
			jdbcTemplate.execute(sql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + "execute sql:[" + sql + "]", e);
			throw e;
		}	
		
	}

	public void deleteRole(String ids) {
		try {
			String[] deleteSql = new String[2];
			
			//删除角色权限
			String deleteRoleModuleSql = "delete from PORTAL_ROLES_PORTAL_MODULES where PORTAL_ROLE_MODULES_ID in(" + ids + ")";
			
			//删除角色
			String deleteRoleSql = "delete from PORTAL_ROLES where ID in(" + ids + ")";
			
			deleteSql[0] = deleteRoleModuleSql;
			deleteSql[1] = deleteRoleSql;
			jdbcTemplate.batchUpdate(deleteSql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + "deleteRole ", e);
			throw e;
		}
	}

	public void saveUpdateRoleInfo(String updateSql, String moduleIds, String roleId,Long id) {
		try {
			//1、更新或插入role
			jdbcTemplate.update(updateSql);
			//2、获取protal_module表id
			String moduleIdSql = "select id as \"id\" from PORTAL_MODULES where MODULE_ID in(" + moduleIds + ")";
			List<Map<String,Object>> moduleIdList = jdbcTemplate.queryForList(moduleIdSql);
			if(id==null){//新建role
				//3、获取portal_role表id
				String roleIdSql = "select id as \"id\" from PORTAL_ROLES where ROLE_ID='" + roleId + "'";
				List<Map<String,Object>> roleIdList = jdbcTemplate.queryForList(roleIdSql);
				if(roleIdList.size()==1){
					id = Long.valueOf(roleIdList.get(0).get("id").toString());
				}
			}else{
				//3、删除PORTAL_ROLES_PORTAL_MODULES表PORTAL_ROLE_MODULES_ID为id的数据
				String deleteRoleModuleSql = "delete from PORTAL_ROLES_PORTAL_MODULES where PORTAL_ROLE_MODULES_ID=" + id;
				jdbcTemplate.update(deleteRoleModuleSql);
			}
			//4、保存PORTAL_ROLES_PORTAL_MODULES表
			if(id!=null){
				for(Map<String,Object> moduleIdMap : moduleIdList){
					Long moduleId = Long.valueOf(moduleIdMap.get("id").toString());
					String insertRoleModuleSql = "insert into PORTAL_ROLES_PORTAL_MODULES(PORTAL_ROLE_MODULES_ID,PORTAL_MODULE_ID) " +
							"values(" + id + "," + moduleId + ")";
					jdbcTemplate.update(insertRoleModuleSql);
				}
			}
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + "saveUpdateRoleInfo ", e);
			throw e;
		}
	}

	public void saveUpdateUserInfo(String updateSql, String roleIds,String userId, Long id) throws RuntimeException{
		// TODO Auto-generated method stub
		try {
			//1、更新或插入user
			jdbcTemplate.update(updateSql);
			if(id==null){//新建user
				//2、获取portal_user表id
				String userIdSql = "select id as \"id\" from PORTAL_USERS where USER_ID='" + userId + "'";
				List<Map<String,Object>> userIdList = jdbcTemplate.queryForList(userIdSql);
				if(userIdList.size()==1){
					id = Long.valueOf(userIdList.get(0).get("id").toString());
				}
			}else{
				//2、删除用户原有的所有角色
				String deleteUserRoleSql = "delete from PORTAL_USERS_PORTAL_ROLES where PORTAL_USER_ROLES_ID=" + id;
				jdbcTemplate.update(deleteUserRoleSql);
			}
			//3、保存PORTAL_USERS_PORTAL_ROLES表
			if(id!=null){
				String[] roleArr = roleIds.split(",");
				for(String roleIdS : roleArr){
					Long roleId = Long.valueOf(roleIdS);
					String insertUserRoleSql = "insert into PORTAL_USERS_PORTAL_ROLES(PORTAL_USER_ROLES_ID,PORTAL_ROLE_ID) " +
							"values(" + id + "," + roleId + ")";
					jdbcTemplate.update(insertUserRoleSql);
				}
			}
			
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + "saveUpdateUserInfo ", e);
			throw e;
		}
	}

	public void deleteUsers(String ids) {
		// TODO Auto-generated method stub
		try {
			String[] deleteSql = new String[2];
			
			//删除用户角色
			String deleteUserRoleSql = "delete from PORTAL_USERS_PORTAL_ROLES where PORTAL_USER_ROLES_ID in(" + ids + ")";
			
			//删除用户
			String deleteUserSql = "delete from PORTAL_USERS where ID in(" + ids + ")";
			
			deleteSql[0] = deleteUserRoleSql;
			deleteSql[1] = deleteUserSql;
			jdbcTemplate.batchUpdate(deleteSql);
		} catch (DataAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + "deleteUsers ", e);
			throw e;
		}
	}

	// add by mark start
	/**
	 * 保存或者更新业务系统
	 * @param updateSql
	 */
	public void saveUpdateBusinessSysInfo(String updateSql) {
		try {
			//1、更新或插入user
			jdbcTemplate.update(updateSql);
			
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error(this.getClass().getName() + "saveUpdateBusinessSysInfo ", e);
			throw e;
		}
		
	}

	/**
	 * 批量删除业务系统
	 * @param ids
	 */
	public void deleteBusinessSys(String ids) {
		// TODO Auto-generated method stub
				try {
					String[] deleteSql = new String[2];
					
					//删除用户角色
					String deleteBusinessSysSql = "delete from TF_AVMON_BIZ_DICTIONARY where ID in(" + ids + ")";
					jdbcTemplate.update(deleteBusinessSysSql);
				} catch (DataAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					logger.error(this.getClass().getName() + "deleteBusinessSys ", e);
					throw e;
				}
	}
	// add by mark end
}
