package com.hp.avmon.alarm.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.utils.MyFunc;
import com.hp.avmon.utils.SqlManager;
import com.hp.avmon.utils.TreeObject;
import com.hp.avmon.utils.Utils;
import com.hp.avmonserver.entity.MO;

@Service
public class AlarmViewService {
    
    private static volatile List<Map> viewItemList=null;
    
    private static volatile HashMap<String,MO> monitorObjectMap=null;
    
    public static HashMap<String, MO> getMonitorObjectMap() {
        return monitorObjectMap;
    }

    public static void setMonitorObjectMap(HashMap<String, MO> monitorObjectMap) {
        AlarmViewService.monitorObjectMap = monitorObjectMap;
    }
    @Autowired
    private JdbcTemplate jdbc;
    @Autowired
    private MoViewService moViewService;
    
    private static final Log logger = LogFactory.getLog(AlarmViewService.class);
    
    public AlarmViewService(){
       /* jdbc=SpringContextHolder.getBean("jdbcTemplate");
        moViewService=SpringContextHolder.getBean("moViewService");
        
        reloadViewItemList();*/
        
    }

    public static List<Map> getViewItemList(String userId) {
        return viewItemList;
    }

    public static void setViewItemList(List<Map> viewItemList) {
        AlarmViewService.viewItemList = viewItemList;
    }

    private List<Map> reloadViewItemList() {
        moViewService.reloadMoChild();
        
        viewItemList=new LinkedList<Map>();
        monitorObjectMap=new HashMap<String,MO>();
        //root
        /*
        HashMap m=new HashMap();
        m.put("pid","0");
        m.put("id", "1");
        m.put("text","公共视图");
        viewItemList.add(m);
        m=new HashMap();
        m.put("pid","0");
        m.put("id", "2");
        m.put("text","组内视图");
        viewItemList.add(m);
        m=new HashMap();
        m.put("pid","0");
        m.put("id", "3");
        m.put("text","个人视图");
        viewItemList.add(m);
        */
        HashMap m=null;
        
        String sql=String.format("select view_type,create_by,view_id,view_name,parent_id,case when is_dir=1 then 'true' else 'false' end as expanded,filters,filters_rule from TD_AVMON_VIEW");
        List<Map> list=jdbc.queryForList(sql);
        for(Map map:list){
            m=new HashMap();
            m.put("pid",map.get("PARENT_ID"));
            m.put("id", map.get("VIEW_ID"));
            m.put("text",map.get("VIEW_NAME"));
            m.put("expanded", map.get("EXPANDED"));
            m.put("viewType",map.get("VIEW_TYPE"));
            m.put("createBy", map.get("CREATE_BY"));
            viewItemList.add(m);
            String filter=((String)map.get("FILTERS"));
            if(filter!=null && filter.length()>0){
                
                _reloadViewItemList(viewItemList,(String)map.get("VIEW_ID"),filter,(String)map.get("FILTERS_RULE"));
            }
            else{
                //m.put("icon", "resources/images/montype/DIR.png");
            }
        }
        
        //load monitorObjectMap
        
        sql=String.format("select a.mo_id,a.type_id,a.caption,b.name,b.value from TD_AVMON_MO_INFO a left join TD_AVMON_MO_INFO_ATTRIBUTE b on a.mo_id=b.mo_id order by mo_id ");
        List<Map<String, Object>> listMo=jdbc.queryForList(sql);
        String lastMo="";
        MO mo=null;
        for(Map map:listMo){
            if(lastMo.equals(map.get("MO_ID"))){
                if(mo!=null){
                    mo.setAttr((String) map.get("NAME"), (String) map.get("VALUE"));
                }
            }
            else{
                mo=new MO();
                mo.setCaption((String) map.get("CAPTION"));
                mo.setMoId((String) map.get("MO_ID"));
                mo.setType((String) map.get("TYPE_ID"));
                mo.setAttr((String) map.get("NAME"), (String) map.get("VALUE"));
                monitorObjectMap.put(mo.getMoId(), mo);
                lastMo=(String) map.get("MO_ID");
            }
        }
        
        logger.info(monitorObjectMap.keySet().size() + " Monitor Objects loaded.");
        
        return viewItemList;
    }

    private void _reloadViewItemList(List<Map> targetList,String pid, String filter, String filterRule) {
        if(filter==null || filterRule==null) return ;
        List<Map> list=moViewService.getMoByFilter(filter,filterRule);
        for(Map map:list){
            HashMap m=new HashMap();
            m.put("pid",pid);
            m.put("id", map.get("MO_ID"));
            m.put("text",map.get("CAPTION"));
            m.put("leaf", "true");
            
            Map attr=(Map) map.get("attr");
            if(attr!=null){
                String os=(String) attr.get("os");
                if(os!=null){
                    m.put("iconCls", "icon-"+os+"");
                    //System.out.println("resources/images/montype/"+os+".png");
                }
                else{
                    m.put("iconCls", "icon-UNKNOWN");
                }
            }
            else{
                m.put("iconCls", "icon-UNKNOWN");
            }

            targetList.add(m);
        }
    }

    public List<String> getAllMoChildrenByParent(String mo) {
        List<String> list=new LinkedList<String>();
        list.add(mo);
        for(Map map:viewItemList){
            String id=(String)map.get("id");
            String pid=(String)map.get("pid");
            if(id.equals(pid)){
                continue;
            }
            if(pid.equals(mo)){
                list.addAll(getAllMoChildrenByParent(id));
            }
        }
        return list;
    }
    
    public void buildAlarmTreeTable(String userId){
        
        //get all tree view nodes for alarm, this method need clear and rebuild
        List<Map> listAlarmTreeNodes=reloadViewItemList();
        
        //build alarm view-mo map table=STG_AVMON_ALARM_VIEW_MO_MAP
        String sql="truncate table STG_AVMON_ALARM_VIEW_MO_MAP";
        jdbc.execute(sql);
        for(Map map:listAlarmTreeNodes){
            String leaf=(String) map.get("leaf");
            String viewId=(String) map.get("id");
            if(leaf==null || !leaf.equals("true")){
                List<String> moList=getAllMoChildrenByParent(viewId);
                for(String moId:moList){
                    sql=String.format("insert into STG_AVMON_ALARM_VIEW_MO_MAP(view_id,mo_id) values('%s','%s')",viewId,moId);
                    jdbc.execute(sql);
                }
            }
        }
        
        //build alarm tree table=STG_AVMON_ALARM_TREE
        sql="truncate table STG_AVMON_ALARM_TREE";
        jdbc.execute(sql);
        for(Map map:listAlarmTreeNodes){
            String pid=(String) map.get("pid");
            if("1".equals(pid)||"2".equals(pid)||"3".equals(pid)){
                pid="root";
            }
            sql=String.format("insert into STG_AVMON_ALARM_TREE(id,text,pid,iconcls,leaf,expanded,is_instance,agent_status,view_type,create_by) " +
            		"values('%s','%s','%s','%s','%s','%s',%d,%d,'%s','%s')",
                    map.get("id"),
                    map.get("text"),
                    pid,
                    MyFunc.nullToString(map.get("iconCls")),
                    MyFunc.nullToString(map.get("leaf")),
                    MyFunc.nullToString(map.get("expanded")),
                    0,
                    0,map.get("viewType"),map.get("createBy")
                    );
            try{
                jdbc.execute(sql);
            }catch(Exception e){
               
                logger.error("SQL="+sql);
            }
        }
        //update alarm count for each node
        sql="update STG_AVMON_ALARM_TREE a set alarm_count=(select sum(alarm_count) from STG_AVMON_AVAILABLE_SUMMARY b where a.id=b.id) ";
        jdbc.execute(sql);
        sql="update STG_AVMON_ALARM_TREE a set alarm_count=(select sum(c.alarm_count) from STG_AVMON_ALARM_VIEW_MO_MAP b,STG_AVMON_AVAILABLE_SUMMARY c where b.mo_id=c.id and b.view_id=a.id)";
        jdbc.execute(sql);
        
    }
    
    private String targetNodePath = "";
    /**
     * 构造树节点的路径
     * 
     * @param userId
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void buildAlarmTreePath(String userId) {
        
        String nodeDataSql = "SELECT * FROM STG_AVMON_ALARM_TREE ";
        List<Map> allNodeDataList = jdbc.queryForList(nodeDataSql);
        
        List<TreeObject> treeObjDataList = getTreeObjVo(allNodeDataList);
        for (TreeObject temp : treeObjDataList) {
        	targetNodePath = "";
        	
    		TreeObject tree = new TreeObject();
    		// 传入匹配节点的父节点，递归找出上级目录结构
    		structureChildNode(tree, treeObjDataList, temp.getPid());

    		String nodePath = "/root/" + targetNodePath + temp.getId();
    		//String nodePath = "/root" + targetNodePath + "/" + targetNodeId;
    		
    		//logger.debug("nodePath: " + nodePath);
    		// 更新节点的path
    		String where = "'" + temp.getPid() + "*" + temp.getId() + "'";
    		String sql="update STG_AVMON_ALARM_TREE a set PATH = " + "'" + nodePath + "'" + " where a.pid||'*'||a.id = " + where;
    		jdbc.execute(sql);
        }
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
			
			obj.setId(MyFunc.nullToString(temp.get("ID")));
			obj.setPid(MyFunc.nullToString(temp.get("PID")));
			
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
				//targetNodePath = targetNodePath + "/" + dataList.get(i).getId();
				targetNodePath = dataList.get(i).getId() + "/" + targetNodePath;
				structureChildNode(dataList.get(i), dataList, dataList.get(i).getPid());
			}
		}
	}
	
    /**
     * 根据父节点取得下属节点信息
     * 
     * @param userId
     * @param parentId
     * @return
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public List<Map> getMoTree(String userId, String parentId) {
        parentId = Utils.decodeString(parentId);
        String a[] = parentId.split("\\*");
        if(a.length > 1) {
            parentId = a[1];
        }

    	String tempSql = SqlManager.getSql(AlarmViewService.class, "getTreeByPid");
    	String sql = String.format(tempSql, parentId, userId, userId);
        
        //logger.debug("MoTree:" + sql);
        List<Map> list = jdbc.queryForList(sql);
 
        for(Map map : list) {
            
            String id = (String) map.get("id");
            id = Utils.encodeString(id);
            String pid = (String) map.get("pid");
            pid = Utils.encodeString(pid);

            map.put("id", id);
            map.put("pid", pid);
        }
        
        return list;
    }
    
    public List<Map> getAllMoTree(String userId, String parentId){

        /*String sql=String.format("select a.pid||'*'||a.id as \"id\"," +
                "a.text as \"text\"," +
                "a.id as \"moId\"," +
                "a.alarm_count as \"alarm_count\"," +
                "case when a.agent_status=2 then 'icon-pause' when b.status=1 then iconCls else iconCls||'-b' end as \"iconCls\"," +
                "a.leaf as \"leaf\"," +
                "a.expanded as \"expanded\"," +
                "b.last_update_time as \"agentLastUpdateTime\"," +
                "case when a.agent_status=2 then '暂停' when b.status=1 then '正在运行' else '未知' end as \"agentStatus\"," +
                "a.qtip as \"qtip\" " +
                "from STG_AVMON_ALARM_TREE a " +
                "left join (select id,to_char(last_update_time,'YYYY-MM-DD HH24:MI:SS') as last_update_time, case when last_update_time>(sysdate-1/24) then 1 else 0 end as status from STG_AVMON_AVAILABLE_SUMMARY) b " +
                "on a.id=b.id " +
                "order by nlssort(a.text ,'NLS_SORT=SCHINESE_STROKE_M') ");*/
        //logger.info("AllMoTree:" + sql);
        String sql = SqlManager.getSql(AlarmViewService.class, "getAllMoTree");
        List<Map> list = jdbc.queryForList(sql);
 
        return list;
    }
    
    /**
     * 取得所有节点的告警数量
     * 
     * @param userId
     * @param parentId
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getAllTreeCurrent(String userId, String parentId){

    	String tempSql = SqlManager.getSql(AlarmViewService.class, "getAllTreeCurrent");
    	String sql = String.format(tempSql);
    	
//        logger.info("AllMoTree:" + sql);
        List<Map> list = jdbc.queryForList(sql);
 
        return list;
    }
    
    /**
     * 取得STG_AVMON_ALARM_TREE表中树的基础数据
     * 
     * 
     * @return
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
	public List<Map> getAllTreeNodeData(){

    	String tempSql = SqlManager.getSql(AlarmViewService.class, "getAllTreeNodeData");
    	String sql = String.format(tempSql);
    	
        logger.info("getAllTreeNode:" + sql);
        List<Map> list = jdbc.queryForList(sql);

        return list;
    }
    
    @SuppressWarnings({ "rawtypes" })
	public Map getTreeIdByMoId(String userId, String moId){
    	moId = Utils.decodeString(moId);

        String sql = String.format("select a.pid||'*'||a.id as \"id\"" +
                " , a.path as \"path\"" +
                " from STG_AVMON_ALARM_TREE a " +
                " where a.id = '%s'", 
                moId);
        logger.debug("getTreeIdByMoId:" + sql);
        Map resultMap = jdbc.queryForMap(sql);
        
        return resultMap;
    }
}
