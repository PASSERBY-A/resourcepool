package com.hp.avmon.home.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.common.jackjson.JackJson;

@Service
public class HomeService {
    
    @Autowired
     private JdbcTemplate jdbcTemplate;

    private static final Log logger = LogFactory.getLog(HomeService.class);

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public HashMap getUserMenuTree(String userId) throws IOException{
        HashMap map=new HashMap();
        
        //加载配置菜单 id=3
        String sql=String.format("select module_id,module_url as \"url\",module_name as \"text\",parent_id,'resources/ligerUI/skins/icons/settings.gif' as \"icon\" from PORTAL_MODULES where " +
        		" parent_id like '3%%' and display_flag='show' and id in (select portal_module_id from PORTAL_ROLES_PORTAL_MODULES where portal_role_modules_id in (select portal_role_id from PORTAL_USERS_PORTAL_ROLES a,PORTAL_USERS b where a.portal_user_roles_id=b.id and b.USER_ID='%s')) order by display_order",userId);
        //System.out.println(sql);
        List<HashMap> configMenu=jdbcTemplate.queryForList(sql);

        //加载系统管理菜单 id=1
        sql=String.format("select module_id,module_url as \"url\",module_name as \"text\",parent_id from PORTAL_MODULES where " +
                " parent_id=1 and display_flag='show' and id in (select portal_module_id from PORTAL_ROLES_PORTAL_MODULES where portal_role_modules_id in (select portal_role_id from PORTAL_USERS_PORTAL_ROLES a,PORTAL_USERS b where a.portal_user_roles_id=b.id and b.USER_ID='%s')) order by display_order",userId);
        List<HashMap> systemMenu=jdbcTemplate.queryForList(sql);
        
        map.put("system", JackJson.fromObjectToJson(systemMenu));
        map.put("config", JackJson.fromObjectToJson(configMenu));
        //map.put("alarm", alarmService.getMonTreeJson(userId));

        map.put("report", "[]");
        
        return map;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List getMenuTree(String userId, String parentId, String type) {
        // TODO Auto-generated method stub
        String pid=parentId;
        if(parentId.endsWith("root")){
            if(type.equals("system")){
                pid="1";
            }
            if(type.equals("config")){
                pid="3";
            }
            if(type.equals("report")){
                pid="5";
            }
            if(type.equals("performance")){
                pid="2";
            }
            if(type.equals("polling")){
                pid="9";
            }
        }
        String sql=String.format("select module_id as \"id\"," +
                "module_url as \"url\"," +
                "case when module_url='' or module_url is null then 'false' else 'true' end as \"leaf\"," +
                "module_name as \"text\"," +
                "'true' as \"expanded\"," +
        		"parent_id," +
        		"icon_cls as \"iconCls\" " +
        		"from PORTAL_MODULES where " +
                " parent_id='%s' and display_flag='show' and id in " +
                "(select portal_module_id from PORTAL_ROLES_PORTAL_MODULES where portal_role_modules_id in " +
                "(select portal_role_id from PORTAL_USERS_PORTAL_ROLES a,PORTAL_USERS b where a.portal_user_roles_id=b.id and b.USER_ID='%s')) " +
                "order by display_order",
                pid,userId);
        //System.out.println(sql);
        List<HashMap> list=jdbcTemplate.queryForList(sql);

        return list;
    }
}
