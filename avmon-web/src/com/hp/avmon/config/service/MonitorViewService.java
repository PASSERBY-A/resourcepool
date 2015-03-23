package com.hp.avmon.config.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.alarm.service.AlarmViewService;
import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.TreeObject;
import com.hp.avmon.utils.Utils;

@Service
public class MonitorViewService {
    
	private static final Log logger = LogFactory.getLog(MonitorViewService.class);
    
    private JdbcTemplate jdbc;
    
    @Autowired
    private AlarmViewService alarmViewService;
    
    public MonitorViewService(){
        jdbc = SpringContextHolder.getBean("jdbcTemplate");
    }
    
    /**
     * 根据父节点取得下属节点信息
     * 
     * @param userId
     * @param parentId
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getViewTree(HttpServletRequest request) {
    	String userId = Utils.getCurrentUserId(request);
        String parentId = request.getParameter("id");
        if(StringUtils.isEmpty(parentId)){
            parentId = "root";
        }
        parentId = Utils.decodeString(parentId);
        
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String pause = bundle.getString("pause");
        String running = bundle.getString("running");
        String unknown = bundle.getString("unknown");

    	String tempSql = SqlManager.getSql(MonitorViewService.class, "getTreeByPid").replace("暂停", pause).replace("正在运行", running).replace("未知", unknown);
    	String sql = String.format(tempSql, parentId, userId, userId);
        
        logger.debug("getViewTree:" + sql);
        List<Map> list = jdbc.queryForList(sql);

        return list;
    }
    
    /**
     * 根据父节点取得下属节点信息
     * 
     * @param userId
     * @param parentId
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getFilterField(HttpServletRequest request) {
        Locale locale = request.getLocale();
        ResourceBundle bundle = ResourceBundle.getBundle("messages",locale);
        String objectType = bundle.getString("objectType");
        String objectName = bundle.getString("objectName");

    	String sql = " select " + 
				    " TYPE_ID || '-' || CAPTION as \"METADATA_ID\", "+
				    " TYPE_ID || '-' || CAPTION as \"METADATA_NM\" "+
				    " from TD_AVMON_MO_TYPE_ATTRIBUTE "+
				    " order by TYPE_ID || '-' || CAPTION ";

        List<Map> list = jdbc.queryForList(sql);
        Map map=new HashMap();
        map.put("METADATA_ID", "MO_TYPE");
        map.put("METADATA_NM", objectType);
        list.add(0,map);
        map=new HashMap();
        map.put("METADATA_ID", "MO_NAME");
        map.put("METADATA_NM", objectName);
        list.add(0,map);
        return list;
    }
    
    private void updateAlarmTree(String userId){
    	alarmViewService.buildAlarmTreeTable(userId);
		alarmViewService.buildAlarmTreePath(userId);
    }
    
    /**
     * 插入视图
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings("rawtypes")
	public Map insertView(String viewId, String caption, String parentId, Short isDir, String createBy, String defaultCls,String viewType) {
		String insSql = "";
		if(viewType==null||viewType.length()<1){
			insSql = String.format("insert into TD_AVMON_VIEW(VIEW_ID, VIEW_NAME, PARENT_ID, IS_DIR, FILTERS, FILTERS_RULE, CREATE_BY, view_type)" +
					"select '%s','%s','%s','%d', '', '','%s',view_type from TD_AVMON_VIEW where view_id='%s'",
					viewId, caption, parentId, isDir, createBy, parentId);
		}else{
			insSql = String.format("insert into TD_AVMON_VIEW(VIEW_ID, VIEW_NAME, PARENT_ID, IS_DIR, FILTERS, FILTERS_RULE, CREATE_BY, view_type)" +
					" values('%s','%s','%s','%d', '', '','%s','%s')", 
					viewId, caption, parentId, isDir, createBy, viewType);
		}
		
		jdbc.execute(insSql);
		
		String querySql = String.format("select * from TD_AVMON_VIEW where VIEW_ID = '%s'", viewId);
		Map result = jdbc.queryForMap(querySql);
		result.put("success", true);
		
		updateAlarmTree(createBy);
		
		return result;
	}
	
    /**
     * 删除视图
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map deleteView(String viewId,String userId) {
		String delSql = String.format("delete from TD_AVMON_VIEW where PARENT_ID = '%s'", viewId);
		jdbc.execute(delSql);
		String insSql = String.format("delete from TD_AVMON_VIEW where VIEW_ID = '%s'", viewId);
		jdbc.execute(insSql);
		
		String querySql = String.format("select * from TD_AVMON_VIEW where VIEW_ID = '%s'", viewId);
		List<Map> list = jdbc.queryForList(querySql);
		Map result = new HashMap();
		if (list == null || list.size() == 0) {
			updateAlarmTree(userId);
			result.put("result", "success");
		} else {
			result.put("result", "failure");
		}

		return result;
	}
	
    private String targetNodePid = null;
    private String targetNodePath = "";
	
    /**
     * 保存视图
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map saveView(String viewId, String viewNm, String filters, String filterRule, String userId) {
		String updSql = String.format("update TD_AVMON_VIEW set VIEW_NAME='%s',FILTERS='%s',FILTERS_RULE='%s'  where VIEW_ID = '%s'", 
				viewNm,
				filters,
				filterRule,
				viewId);
		jdbc.execute(updSql);
		
		// 构造树节点path
		String querySql = String.format("select * from TD_AVMON_VIEW ", viewId);
		List<Map> list = jdbc.queryForList(querySql);
		Map result = new HashMap();
		if (list != null && list.size() != 0) {
			result.put("result", "success");
			
			for (Map temp : list) {
				if (MyFunc.nullToString(temp.get("VIEW_ID")).equals(viewId)) {
					targetNodePid = MyFunc.nullToString(temp.get("PARENT_ID"));
					break;
				}
			}
			
			// 取得数据
    		List<TreeObject> treeObjDataList = getTreeObjVo(list);
    		TreeObject tree = new TreeObject();
    		// 传入匹配节点的父节点，递归找出上级目录结构
    		structureChildNode(tree, treeObjDataList, targetNodePid);

    		String nodePath = "/root/" + targetNodePath + viewId;
    		logger.info("targetNodePath: " + nodePath);

			//map.put("message", "/root/root*V20121129114115/V20121129114115*10.209.83.121");
    		result.put("message", nodePath);
		} else {
			result.put("result", "failure");
		}
		updateAlarmTree(userId);
		
        //构造告警树
		logger.info("buildAlarmTreeTable ...");
        alarmViewService.buildAlarmTreeTable(userId);
        alarmViewService.buildAlarmTreePath(userId);
        logger.info("buildAlarmTreeTable Done.");
        
		return result;
	}

	/**
	 * 转换树形菜单基础数据
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public List<TreeObject> getTreeObjVo(List<Map> objData){
		List<TreeObject> treeList = new ArrayList<TreeObject>();
		TreeObject obj = new TreeObject();
		
		for (Map temp : objData) {
			obj = new TreeObject();
			
			obj.setId(MyFunc.nullToString(temp.get("VIEW_ID")));
			obj.setPid(MyFunc.nullToString(temp.get("PARENT_ID")));
			
			treeList.add(obj);
		}
		
		return treeList;
	}
	
	/**
	 * 根据传入的子节点开始，遍历父节点
	 * 
	 * @param tree
	 * @param dataList
	 * @param stid
	 * @return
	 * @throws IOException
	 * @throws ServletException 
	 */
	public void structureChildNode(TreeObject tree, List<TreeObject> dataList, String stid) {
		
		for(int i=0; i < dataList.size(); i++) {
			if(stid.equals(dataList.get(i).getId())) {
				targetNodePath = dataList.get(i).getId() + "/" + targetNodePath;
				structureChildNode(dataList.get(i), dataList, dataList.get(i).getPid());
			}
		}
	}
	
    /**
     * 删除视图
     * 
     * @param userId
     * @param parentId
     * @return
     */
	@SuppressWarnings("rawtypes")
	public Map editView(String viewId) {		
		String querySql = String.format("select " +
				"t.view_id as \"VIEW_ID\"," +
				"t.view_name AS \"VIEW_NAME\"," +
				"t.parent_id as \"PARENT_ID\"," +
				"t.is_dir as \"IS_DIR\"," +
				"t.filters as \"FILTERS\"," +
				"t.filters_rule as \"FILTERS_RULE\"," +
				"t.create_by as \"CREATE_BY\"," +
				"t.view_type as \"VIEW_TYPE\"" +
				" from TD_AVMON_VIEW t where VIEW_ID = '%s'", viewId);
		Map result = jdbc.queryForMap(querySql);

		return result;
	}
}
