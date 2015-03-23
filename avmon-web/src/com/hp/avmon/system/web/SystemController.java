package com.hp.avmon.system.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hp.avmon.common.sqlgenerator.CreateSqlTools;
import com.hp.avmon.home.service.LicenseService;
import com.hp.avmon.system.bean.PortalBusinessSysBean;
import com.hp.avmon.system.bean.PortalDeptsBean;
import com.hp.avmon.system.bean.PortalMoPropertiesBean;
import com.hp.avmon.system.bean.PortalModulesBean;
import com.hp.avmon.system.bean.PortalRolesBean;
import com.hp.avmon.system.bean.PortalUsersBean;
import com.hp.avmon.system.service.SystemService;
import com.hp.avmon.utils.DBUtils;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.TreeObject;
import com.hp.avmon.utils.Utils;
 
@Controller
@SuppressWarnings("unchecked")
@RequestMapping("/system/*")
public class SystemController {
    
    private static final Log logger = LogFactory.getLog(SystemController.class);
    
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    private SystemService systemService;
    
    @Autowired
    private LicenseService licenseService;
    
    @RequestMapping(value = "main")
    public String index(Locale locale, Model model) {

        return "/system/main";

    }
    
    private String generatPageSql(String sql,int limit,int start){
        
        //构造oracle数据库的分页语句
        StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM (");
        paginationSQL.append("SELECT temp.* ,ROWNUM num FROM ( ");
        paginationSQL.append(sql);
        paginationSQL.append(") temp where ROWNUM <= " + (limit+start-1));//limit*((start-1)/10+1));
        paginationSQL.append(") WHERE num > " + (start-1));
        //System.out.println(paginationSQL.toString());
        return paginationSQL.toString();
    }
    
    private String ganerateTreeJson(List<Map<String, Object>> list,Boolean isCheck){
        Gson gson = new Gson();
        if(!isCheck){
            gson =new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        }
        
        List<TreeObject> treeList = new ArrayList<TreeObject>();
        
        // 填充到树形结构中
        TreeObject obj = new TreeObject();
        for(Map<String, Object> temp : list) {
            obj = new TreeObject();
            
            obj.setText(MyFunc.nullToString(temp.get("text")));
            obj.setId(MyFunc.nullToString(temp.get("id")));
            obj.setPid(MyFunc.nullToString(temp.get("pid")));
            obj.setExpanded("true");
            obj.setChecked(false);
            treeList.add(obj);
        }
        
        TreeObject tree = new TreeObject();
        // 构造监控对象树形菜单
        structureChildNode(tree, treeList, "0");
        
        // 对树形结构进行遍历并构造树形json数据
        String objTreeJson = gson.toJson(tree);
        
        return objTreeJson;
    }
    
    private void structureChildNode(TreeObject tree, List<TreeObject> dataList, String stid) {
        
        for(int i=0; i < dataList.size(); i++) {
            
            if(stid.equals(dataList.get(i).getPid())) {
                structureChildNode(dataList.get(i), dataList, dataList.get(i).getId());
                tree.setChild(dataList.get(i));
            }
        }
    }
    
    @RequestMapping("index")
    public String sysMain(HttpServletRequest request) throws IOException {
        String type=request.getParameter("type");
        if("user".equalsIgnoreCase(type)){
            return "system/users"; 
        }else if("dept".equalsIgnoreCase(type)){
            return "system/depts"; 
        }else if("role".equalsIgnoreCase(type)){
            return "system/roles"; 
        }else if("module".equalsIgnoreCase(type)){
            return "system/modules"; 
        }else if("syncMem".equalsIgnoreCase(type)){
            return "system/syncMem/index"; 
        }else if("license".equalsIgnoreCase(type)){
            return "system/license"; 
        }else if("businessSys".equalsIgnoreCase(type)){
            return "system/businessSys"; 
        }else if("moProperties".equalsIgnoreCase(type)){
            return "system/moProperties"; 
        }else{
            logger.error("SysMenuController type error!");
            return "commons/timeout";
        }
        
    }
    
    @RequestMapping("deptIndex")
    public String findDeptGridInfo(
            HttpServletResponse response,
            HttpServletRequest request){
        String json = "";
        try {
            String sortBy = request.getParameter("sort");
            
            String baseSql = "select d1.id as \"id\",d1.dept_id as \"dept_id\",d1.dept_name as \"dept_name\",d1.parent_id as \"parent_id\",d2.dept_name as \"parent_name\" " +
                    " from PORTAL_DEPTS d1" +
                    " left join PORTAL_DEPTS d2 on d1.parent_id=d2.dept_id ";
            String sql = baseSql + " order by d1.id desc ";
            if(sortBy!=null){
                JSONArray jsonArr = JSONArray.fromObject(sortBy);
                String sort = jsonArr.getJSONObject(0).get("property").toString();
                if("parent_name".equalsIgnoreCase(sort)){
                    sort = "d2.dept_name";
                }else{
                    sort = "d1." + sort;
                }
                sql = baseSql + " order by " + sort + " " + jsonArr.getJSONObject(0).get("direction");
            }
            Integer limit = Integer.valueOf(request.getParameter("limit"));
            Integer start = Integer.valueOf(request.getParameter("start"));
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(DBUtils.pagination(sql, start, limit));
            
            JSONArray jsonArray = JSONArray.fromObject(list);
            String root = jsonArray.toString();
            
            String countSql = "select count(1) from PORTAL_DEPTS";
            int total = jdbcTemplate.queryForInt(countSql);

            json = "{total:" + total +",root:" + root + "}";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findDeptGridInfo",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }
    
    @RequestMapping("saveUpdateDeptInfo")
    public String saveUpdateDeptInfo(
            HttpServletResponse response,
            HttpServletRequest request,
            @ModelAttribute PortalDeptsBean entity){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String updateSql = "";
            
            if(entity.getDept_id()==null||entity.getDept_id().length()<1){//添加数据
                entity.setDept_id(Utils.getUID());
            }
            if(entity.getParent_id()==null||entity.getParent_id().length()<1){//无上级部门
                entity.setParent_id("0");
            }
            
            if(entity.getId()!=null){//更新数据
                updateSql = CreateSqlTools.getUpdateSql(entity);
            }else{//保存新数据
                updateSql = CreateSqlTools.getInsertSql(entity);
            }
            
            systemService.execute(updateSql);
            data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" saveUpdateDeptInfo",e);
            data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }
    
    @RequestMapping("deleteDeptByIds")
    public String deleteDeptByIds(
            HttpServletResponse response,
            HttpServletRequest request){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String ids = request.getParameter("ids");
            
            String childCountSql = "select count(1) from PORTAL_DEPTS where parent_id in(" + ids + ")";
            int childCount = jdbcTemplate.queryForInt(childCountSql);
            if(childCount>0){
                data="{success:false,msg:'"+bundle.getString("deleteFailChildDepartmentExsitCannotDeleted")+"'}";
                return Utils.responsePrintWrite(response,data,null);
            }
            
            String userCountSql = "select count(1) from PORTAL_USERS u " +
                    " left join PORTAL_DEPTS d on u.dept_id=d.id" +
                    " where d.dept_id in(" + ids + ")";
            int userCount = jdbcTemplate.queryForInt(userCountSql);
            if(userCount>0){
                data="{success:false,msg:'"+bundle.getString("deleteFailHavingUsersDepartmentCannotDeleted")+"'}";
                return Utils.responsePrintWrite(response,data,null);
            }
            
            String sql = "delete from PORTAL_DEPTS where dept_id in(" + ids + ")";
            systemService.execute(sql);
            data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" deleteDeptByIds",e);
            data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }
    
    @RequestMapping("findDeptsTreeStore")
    public String findDeptsTreeStore(
            HttpServletResponse response,
            HttpServletRequest request){
        String json = "";
        try {
            String isChecked = request.getParameter("isChecked");
            Boolean checked = false;
            if("true".equals(isChecked)){
                checked = true;
            }
            
            String sql = "select dept_id as \"id\",dept_name as \"text\",parent_id as \"pid\" from PORTAL_DEPTS order by id desc ";
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            
            json = ganerateTreeJson(list,checked);
            json = json.replace("\"children\":[]", "leaf:true");
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findDeptsTreeStore",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }
    
    @RequestMapping("findNameByEntityId")
    public String findNameByEntityId(
            HttpServletResponse response,
            HttpServletRequest request){
        String type = request.getParameter("type");
        String entityId = request.getParameter("entityId");
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String json = "{res:'failed',msg:'"+bundle.getString("loadDepartmentID")+entityId+bundle.getString("informationError")+"'}";
        if("0".equals(entityId)){
            json = "{res:'success',entityName:'',msg:'"+bundle.getString("loadSuccess")+"'}";
            return Utils.responsePrintWrite(response,json,null);
        }
        
        try {
            String entityName = type.toLowerCase() + "_name";
            String sql = "select " + entityName + " as \"" + entityName + "\" from PORTAL_" + type.toUpperCase() + "S " +
                    " where " + type.toLowerCase() + "_id=?";
            String[] args = {entityId};
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args);
            
            if(list.size()>0){
                json = "{res:'success',entityName:'" + list.get(0).get(entityName).toString() + "',msg:'"+bundle.getString("loadSuccess")+"'}";
            }else{
                logger.error(this.getClass().getName()+" findNameByEntityId() " + "加载" + type + " ID为："+entityId+"信息出错!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findNameByEntityId",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }
    
    @RequestMapping("moduleIndex")
    public String findModuleGridInfo(
            HttpServletResponse response,
            HttpServletRequest request){
        String json = "";
        try {
            String sortBy = request.getParameter("sort");
            
            String baseSql = "select d1.id as \"id\",d1.display_flag as \"display_flag\",d1.display_order as \"display_order\",d1.module_id as \"module_id\"," +
                    "d1.module_name as \"module_name\",d1.module_url as \"module_url\",d1.parent_id as \"parent_id\",d1.remark as \"remark\",d1.icon_cls as \"icon_cls\",d2.module_name as \"parent_name\" " +
                    " from PORTAL_MODULES d1" +
                    " left join PORTAL_MODULES d2 on d1.parent_id=d2.module_id ";
            String sql = baseSql + " order by d1.id desc ";
            if(sortBy!=null){
                JSONArray jsonArr = JSONArray.fromObject(sortBy);
                String sort = jsonArr.getJSONObject(0).get("property").toString();
                if("parent_name".equalsIgnoreCase(sort)){
                    sort = "d2.module_name";
                }else{
                    sort = "d1." + sort;
                }
                sql = baseSql + " order by " + sort + " " + jsonArr.getJSONObject(0).get("direction");
            }
            Integer limit = Integer.valueOf(request.getParameter("limit"));
            Integer start = Integer.valueOf(request.getParameter("start"));
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(DBUtils.pagination(sql, start, limit));
            
            JSONArray jsonArray = JSONArray.fromObject(list);
            String root = jsonArray.toString();
            
            String countSql = "select count(1) from PORTAL_MODULES";
            int total = jdbcTemplate.queryForInt(countSql);

            json = "{total:" + total +",root:" + root + "}";
//          System.out.println(root);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findModuleGridInfo",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }

    @RequestMapping("findModulesTreeStore")
    public String findModulesTreeStore(
            HttpServletResponse response,
            HttpServletRequest request){
        String json = "";
        try {
            String isChecked = request.getParameter("isChecked");
            Boolean checked = false;
            if("true".equals(isChecked)){
                checked = true;
            }
            
            String sql = "select module_id as \"id\",module_name as \"text\",parent_id as \"pid\" from PORTAL_MODULES order by id desc ";
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            
            json = ganerateTreeJson(list,checked);
            json = json.replace("\"children\":[]", "leaf:true");
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findModulesTreeStore",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }
    
    @RequestMapping("saveUpdateModuleInfo")
    public String saveUpdateModuleInfo(
            HttpServletResponse response,
            HttpServletRequest request,
            @ModelAttribute PortalModulesBean entity){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String updateSql = "";
            
            if(entity.getModule_id()==null||entity.getModule_id().length()<1){//添加数据
                entity.setModule_id(Utils.getUID());
            }
            if(entity.getParent_id()==null||entity.getParent_id().length()<1){//无上级菜单
                entity.setParent_id("0");
            }
            
            if(entity.getId()!=null){//更新数据
                updateSql = CreateSqlTools.getUpdateSql(entity);
            }else{//保存新数据
                updateSql = CreateSqlTools.getInsertSql(entity);
            }
            
            systemService.execute(updateSql);
            data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" saveUpdateModuleInfo",e);
            data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }
    
    @RequestMapping("deleteModuleByIds")
    public String deleteModuleByIds(
            HttpServletResponse response,
            HttpServletRequest request){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String ids = request.getParameter("ids");
            String moduleIds = request.getParameter("moduleIds");
            
            String childCountSql = "select count(1) from PORTAL_MODULES where parent_id in(" + moduleIds + ")";
            int childCount = jdbcTemplate.queryForInt(childCountSql);
            if(childCount>0){
                data="{success:false,msg:'"+bundle.getString("deleteFailChildMenuExsitCannotDeleted")+"'}";
                return Utils.responsePrintWrite(response,data,null);
            }
            
            String roleCountSql = "select count(1) from PORTAL_ROLES_PORTAL_MODULES  where PORTAL_MODULE_ID in(" + ids + ")";
            int roleCount = jdbcTemplate.queryForInt(roleCountSql);
            if(roleCount>0){
                data="{success:false,msg:'"+bundle.getString("deleteFailAssignRoleMenuCannotDeleted")+"'}";
                return Utils.responsePrintWrite(response,data,null);
            }
            
            String sql = "delete from PORTAL_MODULES where module_id in(" + moduleIds + ")";
            systemService.execute(sql);
            data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" deleteModuleByIds",e);
            data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }
    
    @RequestMapping("roleIndex")
    public String findRoleGridInfo(
            HttpServletResponse response,
            HttpServletRequest request){
        String json = "";
        try {
            String sortBy = request.getParameter("sort");
            
            String baseSql = "select id as \"id\",role_desc as \"role_desc\",role_id as \"role_id\",role_name as \"role_name\" " +
                    " from PORTAL_ROLES ";
                    
            String sql = baseSql + " order by id desc ";
            if(sortBy!=null){
                JSONArray jsonArr = JSONArray.fromObject(sortBy);
                String sort = jsonArr.getJSONObject(0).get("property").toString();
                sql = baseSql + " order by " + sort + " " + jsonArr.getJSONObject(0).get("direction");
            }
            Integer limit = Integer.valueOf(request.getParameter("limit"));
            Integer start = Integer.valueOf(request.getParameter("start"));
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(DBUtils.pagination(sql, start, limit));
            
            String roleModuleSql = "select rm.portal_role_modules_id as \"role_id\",m.module_id as \"module_id\",m.module_name as \"module_name\" from portal_roles_portal_modules rm " + 
                    "left join portal_roles r on rm.portal_role_modules_id=r.id " +
                    "left join portal_modules m on rm.portal_module_id=m.id";
            List<Map<String, Object>> roleModuleList = jdbcTemplate.queryForList(roleModuleSql);
            
            for(Map<String, Object> role : list){
                String roleId = role.get("id").toString();
                String moduleIds = "";
                String moduleName = "";
                for(Map<String, Object> roleModule : roleModuleList){
                    if(roleModule.get("role_id").toString().equals(roleId)){
                        moduleIds = moduleIds + roleModule.get("module_id") + ",";
                        moduleName = moduleName + roleModule.get("module_name") + ",";
                    }
                }
                if(moduleIds.length()>0) moduleIds = moduleIds.substring(0,moduleIds.length()-1);
                if(moduleName.length()>0) moduleName = moduleName.substring(0,moduleName.length()-1);
                
                role.put("module_ids", moduleIds );
                role.put("module_names", moduleName);
            }
            
            JSONArray jsonArray = JSONArray.fromObject(list);
            String root = jsonArray.toString();
            
            String countSql = "select count(1) from PORTAL_ROLES";
            int total = jdbcTemplate.queryForInt(countSql);

            json = "{total:" + total +",root:" + root + "}";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findRoleGridInfo",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }
    
    @RequestMapping("saveUpdateRoleInfo")
    public String saveUpdateRoleInfo(
            HttpServletResponse response,
            HttpServletRequest request,
            @ModelAttribute PortalRolesBean entity){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String updateSql = "";
            String moduleIds = "'" + request.getParameter("module_ids").replace(",", "','") + "'";
            String roleId = "";
            
            if(entity.getRole_id()==null||entity.getRole_id().length()<1){//添加数据
                roleId = Utils.getUID();
                entity.setRole_id(roleId);
            }else{
                roleId = entity.getRole_id();
            }
                        
            if(entity.getId()!=null){//更新数据
                updateSql = CreateSqlTools.getUpdateSql(entity);
            }else{//保存新数据
                updateSql = CreateSqlTools.getInsertSql(entity);
            }
            
            systemService.saveUpdateRoleInfo(updateSql,moduleIds,roleId,entity.getId());
            data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" saveUpdateRoleInfo",e);
            data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }
    
    @RequestMapping("deleteRoleByIds")
    public String deleteRoleByIds(
            HttpServletResponse response,
            HttpServletRequest request){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String ids = request.getParameter("ids");
            
            String userCountSql = "select count(1) from PORTAL_USERS_PORTAL_ROLES where PORTAL_ROLE_ID in(" + ids + ")";
            int userCount = jdbcTemplate.queryForInt(userCountSql);
            if(userCount>0){
                data="{success:false,msg:'"+bundle.getString("deleteFailAssignUserRoleCannotDeleted")+"'}";
                return Utils.responsePrintWrite(response,data,null);
            }
            
            systemService.deleteRole(ids);
            
            data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" deleteRoleByIds",e);
            data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }
    
    @RequestMapping("userIndex")
    public String findUserGridInfo(
            HttpServletResponse response,
            HttpServletRequest request){
        String json = "";
        try {
            String sortBy = request.getParameter("sort");
            
            String baseSql = "select u.id as \"id\",u.USER_ID as \"account\",u.dept_id as \"dept_id\",d.dept_id as \"dept_ids\",u.email as \"email\",u.mobile as \"mobile\"," +
                    " u.office_phone as \"office_phone\",u.password as \"password\",u.real_name as \"real_name\",u.remark as \"remark\",u.user_id as \"user_id\",d.dept_name as \"dept_name\"" +
                    " from portal_users u" +
                    " left join portal_depts d on u.dept_id=d.id ";
            String sql = baseSql + " order by u.id desc ";
            if(sortBy!=null){
                JSONArray jsonArr = JSONArray.fromObject(sortBy);
                String sort = jsonArr.getJSONObject(0).get("property").toString();
                if("dept_name".equalsIgnoreCase(sort)){
                    sort = "d.dept_name";
                }else{
                    sort = "u." + sort;
                }
                sql = baseSql + " order by " + sort + " " + jsonArr.getJSONObject(0).get("direction");
            }
            Integer limit = Integer.valueOf(request.getParameter("limit"));
            Integer start = Integer.valueOf(request.getParameter("start"));
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(DBUtils.pagination(sql,start,limit));
            
            //查询角色
            String userRoleSql = "select u.id as \"user_id\",r.id as \"role_id\",r.role_name as \"role_name\"" +
                    " from portal_users u" + 
                    " left join portal_users_portal_roles m on u.id=m.portal_user_roles_id" + 
                    " left join portal_roles r on m.portal_role_id=r.id";
            List<Map<String, Object>> userRoleList = jdbcTemplate.queryForList(userRoleSql);
            
            for(Map<String, Object> user : list){
                String userId = user.get("id").toString();
                String roleIds = "";
                String roleName = "";
                for(Map<String, Object> userRole : userRoleList){
                    if(userRole.get("user_id").toString().equals(userId)){
                        roleIds = roleIds + userRole.get("role_id") + ",";
                        roleName = roleName + userRole.get("role_name") + ",";
                    }
                }
                if(roleIds.length()>0) roleIds = roleIds.substring(0,roleIds.length()-1);
                if(roleName.length()>0) roleName = roleName.substring(0,roleName.length()-1);
                
                user.put("role_ids", roleIds );
                user.put("role_names", roleName);
            }
            
            JSONArray jsonArray = JSONArray.fromObject(list);
            String root = jsonArray.toString();
            
            String countSql = "select count(1) from PORTAL_USERS";
            int total = jdbcTemplate.queryForInt(countSql);

            json = "{total:" + total +",root:" + root + "}";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findUserGridInfo",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }
    
    @RequestMapping("saveUpdateUserInfo")
    public String saveUpdateUserInfo(
            HttpServletResponse response,
            HttpServletRequest request,
            @ModelAttribute PortalUsersBean entity){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String updateSql = "";
            String roleIds = request.getParameter("roleIds");
            String userId = entity.getUser_id();
            
            String deptId = request.getParameter("dept_ids");
            String deptSql = "select id as \"id\" from portal_depts where dept_id='" + deptId + "'";
            Long deptIdL = jdbcTemplate.queryForLong(deptSql);
            if(deptIdL!=null){
                entity.setDept_id(deptIdL);
            }
            
//          if(entity.getUser_id()==null||entity.getUser_id().length()<1){//添加数据
//              userId = Utils.getUID();
//              entity.setUser_id(userId);
//          }else{
//              userId = entity.getUser_id();
//          }
                        
            if(entity.getId()!=null){//更新数据
                updateSql = CreateSqlTools.getUpdateSql(entity);
            }else{//保存新数据
                String account = entity.getUser_id();
                String accountSql = "select count(1) from portal_users where upper(USER_ID)=upper('" + account + "')";
                int accoutCount = jdbcTemplate.queryForInt(accountSql);
                if(accoutCount>0){
                    data="{success:false,msg:'"+bundle.getString("accountAlreadyExists")+"'}";
                    return Utils.responsePrintWrite(response,data,null);
                }else{
                    updateSql = CreateSqlTools.getInsertSql(entity);
                }
            }
            
            systemService.saveUpdateUserInfo(updateSql,roleIds,userId,entity.getId());
            data="{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" saveUpdateUserInfo",e);
            data="{success:false,msg:'"+bundle.getString("saveFail")+"'}";
        } 
        
        return Utils.responsePrintWrite(response,data,null);
    }
    
    @RequestMapping("findAllCheckBoxRoles")
    public String findAllCheckBoxRoles(
            HttpServletResponse response,
            HttpServletRequest request){
        String json = "";
        try {
            Gson gson = new Gson();
            String sql = "select ROLE_NAME as \"boxLabel\",ID as \"inputValue\",'rols'||ID as \"id\",'rols'||ID as \"name\" from PORTAL_ROLES order by id desc ";
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
            
            json = gson.toJson(list);
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findAllCheckBoxRoles",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }
    
    @RequestMapping("deleteUserByIds")
    public String deleteUserByIds(
            HttpServletResponse response,
            HttpServletRequest request){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String ids = request.getParameter("ids");
            
            systemService.deleteUsers(ids);
            
            data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" deleteUserByIds",e);
            data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }
    
    @RequestMapping("loadLicense")
    public String loadLicense(
            HttpServletResponse response,
            HttpServletRequest request){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String licenseDate = licenseService.getOverDate();
            int licenseMax = licenseService.getMaxMoCount();
                        
            data="{success:true,data:{\"licenseDate\":\"" + licenseDate + "\",\"licenseMax\":" + licenseMax + "}}";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" loadLicense",e);
            data="{success:false,msg:'"+bundle.getString("loadFail")+"',data:{\"licenseDate\":\"\",\"licenseMax\":0}}";
            return Utils.responsePrintWrite(response,data,null);
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }
    
    @RequestMapping("checkRegText")
    public String checkRegText(
            HttpServletResponse response,
            HttpServletRequest request){
        String regText=request.getParameter("regText");
        String targetText=request.getParameter("targetText");
        Pattern pattern=Pattern.compile(regText);
        String data="{regMatched:false}";
        if(pattern.matcher(targetText).matches()){
            data="{regMatched:true}";
        }
        return Utils.responsePrintWrite(response,data,null);
    }
    
    // add by mark start
    @RequestMapping("businessSysIndex")
    public String findbusinessSysGridInfo(
            HttpServletResponse response,
            HttpServletRequest request){
        String json = "";
        try {
            String sortBy = request.getParameter("sort");
            
            String baseSql = "select ID as \"id\",BUSINESSNAME as \"businessname\" from TF_AVMON_BIZ_DICTIONARY ";
            String sql = baseSql + " order by ID desc ";
            if(sortBy!=null){
                JSONArray jsonArr = JSONArray.fromObject(sortBy);
                String sort = jsonArr.getJSONObject(0).get("property").toString();
                if("id".equalsIgnoreCase(sort)){
                    sort = "ID";
                }else if("businessname".equalsIgnoreCase(sort)){
                    sort = "BUSINESSNAME";
                }
                sql = baseSql + " order by " + sort + " " + jsonArr.getJSONObject(0).get("direction");
            }
            Integer limit = Integer.valueOf(request.getParameter("limit"));
            Integer start = Integer.valueOf(request.getParameter("start"));
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(DBUtils.pagination(sql, start, limit));
            
            JSONArray jsonArray = JSONArray.fromObject(list);
            String root = jsonArray.toString();
            
            String countSql = "select count(1) from TF_AVMON_BIZ_DICTIONARY";
            int total = jdbcTemplate.queryForInt(countSql);

            json = "{total:" + total +",root:" + root + "}";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findbusinessSysGridInfo",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }
    
    @RequestMapping("saveUpdateBusinessSysInfo")
    public String saveUpdateBusinessSysInfo(HttpServletResponse response,
            HttpServletRequest request,
            @ModelAttribute PortalBusinessSysBean entity) {

        String data = "";
        String updateSql = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String insFlag = request.getParameter("insFlag");

            if ("0".equals(insFlag)) {
                String accountSql = "select count(1) from TF_AVMON_BIZ_DICTIONARY where ID =upper('"
                        + entity.getId() + "')";
                int accoutCount = jdbcTemplate.queryForInt(accountSql);
                if (accoutCount > 0) {
                    data = "{success:false,msg:'"+bundle.getString("businessSystemIDAlreadyExists")+"'}";
                    return Utils.responsePrintWrite(response, data, null);
                }
                
                //updateSql = CreateSqlTools.getInsertSql(entity);
                updateSql = "INSERT INTO TF_AVMON_BIZ_DICTIONARY (ID,BUSINESSNAME) VALUES ('"+entity.getId()+"','"+entity.getBusinessname()+"')";
                
            } else if ("1".equals(insFlag)) {
                updateSql = CreateSqlTools.getUpdateSql(entity);
            }

            logger.info(updateSql);
            systemService.saveUpdateBusinessSysInfo(updateSql);
            data = "{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName() + " saveUpdateUserInfo", e);
            data = "{success:false,msg:'"+bundle.getString("saveFail")+"'}";
        }

        return Utils.responsePrintWrite(response, data, null);
    }
    
    
    @RequestMapping("deleteBusinessSysByIds")
    public String deleteBusinessSysByIds(
            HttpServletResponse response,
            HttpServletRequest request){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String ids = request.getParameter("ids");
            
            systemService.deleteBusinessSys(ids);
            
            data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" deleteUserByIds",e);
            data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }

    
    @RequestMapping("moPropertiesIndex")
    public String findMoPropertiesGridInfo(
            HttpServletResponse response,
            HttpServletRequest request){
        String json = "";
        try {
            String sortBy = request.getParameter("sort");
            
            String baseSql = "select "
            		+ "type_id	 as \"typeId\","
            		+ "name	 as \"name\","
            		+ "caption	 as \"caption\","
            		+ "class_info	 as \"classInfo\","
            		+ "hide	 as \"hide\","
            		+ "passwd	 as \"passwd\","
            		+ "value_type	 as \"valueType\","
            		+ "order_index	 as \"orderIndex\","
            		+ "default_value	 as \"defaultValue\","
            		+ "nullable	 as \"nullable\","
            		+ "kpi_code	 as \"kpiCode\" "
            		+ "from	td_avmon_mo_type_attribute";
            String sql = baseSql + " order by type_id desc ";
            if(sortBy!=null){
                JSONArray jsonArr = JSONArray.fromObject(sortBy);
                String sort = jsonArr.getJSONObject(0).get("property").toString();
                if("typeId".equalsIgnoreCase(sort)){
                    sort = "type_id";
                }else if("classinfo".equalsIgnoreCase(sort)){
                    sort = "class_info";
                }else if("valuetype".equalsIgnoreCase(sort)){
                    sort = "value_type";
                }else if("orderindex".equalsIgnoreCase(sort)){
                    sort = "order_index";
                }else if("defaultvalue".equalsIgnoreCase(sort)){
                    sort = "default_value";
                }else if("kpicode".equalsIgnoreCase(sort)){
                    sort = "kpi_code";
                }
                sql = baseSql + " order by " + sort + " " + jsonArr.getJSONObject(0).get("direction");
            }
            Integer limit = Integer.valueOf(request.getParameter("limit"));
            Integer start = Integer.valueOf(request.getParameter("start"));
            
            List<Map<String, Object>> list = jdbcTemplate.queryForList(DBUtils.pagination(sql, start, limit));
            
            JSONArray jsonArray = JSONArray.fromObject(list);
            String root = jsonArray.toString();
            
            String countSql = "select count(1) from td_avmon_mo_type_attribute";
            int total = jdbcTemplate.queryForInt(countSql);

            json = "{total:" + total +",root:" + root + "}";
            
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" findMoPropertiesGridInfo",e);
        }
        
        return Utils.responsePrintWrite(response,json,null);
    }
    
    @RequestMapping("saveUpdateMoPropertiesInfo")
    public String saveUpdateMoPropertiesInfo(HttpServletResponse response,
            HttpServletRequest request,
            @ModelAttribute PortalMoPropertiesBean entity) {

        String data = "";
        String updateSql = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        try {
            String insFlag = request.getParameter("insFlag");
            String caption = entity.getCaption();
            String hide = entity.getHide();
            String passwd = entity.getPasswd();
            String valueType = entity.getValueType();
            String classInfo = entity.getClassInfo();
            String orderIndex = entity.getOrderIndex();
            String defauleValue = entity.getDefaultValue();
            String nullable = entity.getNullable();
            String kpiCode = entity.getKpiCode();
            String typeId = entity.getTypeId();
            String name = entity.getName();
            
            if ("1".equals(insFlag)) {
          
                
                updateSql = "update td_avmon_mo_type_attribute set "
                		+ (StringUtils.isEmpty(caption)?"caption=null,":"caption='"+caption+"',")
                		+ (StringUtils.isEmpty(classInfo)?"class_info=null,":"class_info='"+classInfo+"',")
                		+ (StringUtils.isEmpty(hide)?"hide=null,":"hide="+hide+",")
                		+ (StringUtils.isEmpty(passwd)?"passwd=null,":"passwd="+passwd+",")
                		+ (StringUtils.isEmpty(valueType)?"value_type=null,":"value_type="+valueType+",")
                		+ (StringUtils.isEmpty(orderIndex)?"order_index=null,":"order_index="+orderIndex+",")
                		+ (StringUtils.isEmpty(defauleValue)?"default_value=null,":"default_value='"+defauleValue+"',")
                		+ (StringUtils.isEmpty(nullable)?"nullable=null,":"nullable="+nullable+",")
                		+ (StringUtils.isEmpty(classInfo)?"kpi_code=null,":"kpi_code='"+kpiCode+"'")
                		+ "where type_id='"+typeId+"' and name='"+name+"'";
            } else if ("0".equals(insFlag)) {
            	
                String accountSql = "select count(1) from td_avmon_mo_type_attribute where type_id =upper('"
                        + entity.getTypeId() + "') and caption = upper('"+entity.getCaption()+"')";
                int accoutCount = jdbcTemplate.queryForInt(accountSql);
                if (accoutCount > 0) {
                    data = "{success:false,msg:'"+bundle.getString("MopropertiesAlreadyExists")+"'}";
                    return Utils.responsePrintWrite(response, data, null);
                }
            	updateSql = "insert into td_avmon_mo_type_attribute(type_id,name,caption,"
            			+ "class_info,hide,passwd,value_type,order_index,"
            			+ "default_value,nullable,kpi_code)"
                		+ " values("
                		+ (StringUtils.isEmpty(typeId)?null:"'"+typeId+"'")
                		+  ","+(StringUtils.isEmpty(name)?null:"'"+name+"'")
                		+ ","+(StringUtils.isEmpty(caption)?null:"'"+caption+"'")
                		+ ","+(StringUtils.isEmpty(classInfo)?null:"'"+classInfo+"'")
                		+ ","+ (StringUtils.isEmpty(hide)?null:hide)
                		+ ","+ (StringUtils.isEmpty(passwd)?null:passwd)
                		+ ","+(StringUtils.isEmpty(valueType)?null:valueType)
                		+ ","+ (StringUtils.isEmpty(orderIndex)?null:orderIndex)
                		+ ","+(StringUtils.isEmpty(defauleValue)?null:defauleValue) 
                		+ ","+ (StringUtils.isEmpty(nullable)?null:nullable) 
                		+ ","+(StringUtils.isEmpty(kpiCode)?null:"'"+kpiCode+"'")
                		+ ")";
            }

            logger.info("====================updateSql==============="+updateSql);
            systemService.saveUpdateBusinessSysInfo(updateSql);
            data = "{success:true,msg:'"+bundle.getString("saveSuccess")+"'}";

        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName() + " saveUpdateMoPropertiesInfo", e);
            data = "{success:false,msg:'"+bundle.getString("saveFail")+"'}";
        }

        return Utils.responsePrintWrite(response, data, null);
    }
    
    
    @RequestMapping("deleteMoPropertiesByIds")
    public String deleteMoPropertiesByIds(
            HttpServletResponse response,
            HttpServletRequest request){
        String data = "";
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String deleteMoPropertiesSql = "delete from TD_AVMON_MO_TYPE_ATTRIBUTE where type_id ='%s' and name='%s'";
        try {
            String ids = request.getParameter("ids");
            String params[] = ids.split(",");
            for (String param : params) {
				String typeId = param.split("\\|")[0];
				String name = param.split("\\|")[1];
				deleteMoPropertiesSql = String.format(deleteMoPropertiesSql, typeId,name);
				jdbcTemplate.execute(deleteMoPropertiesSql);
			}
        	data="{success:true,msg:'"+bundle.getString("deleteSuccess")+"'}";
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(this.getClass().getName()+" deleteUserByIds",e);
            data="{success:false,msg:'"+bundle.getString("deleteFail")+"'}";
        }
        
        return Utils.responsePrintWrite(response,data,null);
    }
    // add by mark end
    
    // add by mark start 2014-5-22
    @RequestMapping("checkEnglistName")
    public String checkEnglistName(
            HttpServletResponse response,
            HttpServletRequest request){
        String data = "";
        String typeId = request.getParameter("typeId");
        String where =" WHERE 1=1 ";
        if(!StringUtils.isEmpty(typeId)&&typeId != "null"){
        	where += " AND type_id = '"+typeId+"' ";
        }
        
        String name = request.getParameter("name");
        if(!StringUtils.isEmpty(name) && name != "null"){
        	where += " AND name = '"+name+"' ";
        }
        String checkEnglistNameSql = "select count(*) from TD_AVMON_MO_TYPE_ATTRIBUTE " + where;
    	int cnt = jdbcTemplate.queryForInt(checkEnglistNameSql);
        
    	if(cnt > 0){
    		data = "{\"result\":false}";
    	}else{
    		data = "{\"result\":true}";
    	}
        return Utils.responsePrintWrite(response,data,null);
    }
    // add by mark start 2014-5-22
}
