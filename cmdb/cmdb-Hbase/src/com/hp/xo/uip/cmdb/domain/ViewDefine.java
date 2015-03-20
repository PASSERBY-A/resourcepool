package com.hp.xo.uip.cmdb.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ViewDefine implements Serializable{

	
	private static final long serialVersionUID = 3348519575096792513L;
	/**
	 * 系统对象ID
	 */
	private Long oid;
	/**
	 * 名称（英文） 
	 */
    private String name;
    /**
     * 显示名称（中文）
     */
    private String title;
    /**
     * flex 展示定义xml
     */
    private String content;
    private String comments;
    private String creator;
    private Timestamp createTime;
    private String updator;
    private Timestamp updateTime; 
    
    private int viewType=0;
    
    private long timer=1000*60*2;
    /**
	 * 返回值保留的类型
	 */
	private String valueFilter;
     /**
     * 视图 条件定义
     */
    private List<ViewCondition> conditions=new ArrayList<ViewCondition>();
    
    /**
     * 条件查询结果 ，所有节点ID
     */
    private List<Long> nodes=new ArrayList<Long>();

    /**
     * 条件查询结果 ，按节点类型分类节点
     */
    private Map<String,List<Node>> nodesMap=new HashMap<String,List<Node>>();
    
    /**
     * 条件查询结果 ，所有关系ID
     */
    private List<Long> relationNodes=new ArrayList<Long>();
    
    /**
     * 条件查询结果 ，按节点类型分类关系
     */
    private Map<String,List<RelationNode>> relationNodesMap=new HashMap<String, List<RelationNode>>();
    
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getCreator() {
		return creator;
	}
	public void setCreator(String creator) {
		this.creator = creator;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public String getUpdator() {
		return updator;
	}
	public void setUpdator(String updator) {
		this.updator = updator;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public List<ViewCondition> getConditions() {
		return conditions;
	}
	public void setConditions(List<ViewCondition> conditions) {
		this.conditions = conditions;
	}

	public List<Long> getNodes() {
		return nodes;
	}
	public void setNodes(List<Long> nodes) {
		this.nodes = nodes;
	}
	public List<Long> getRelationNodes() {
		return relationNodes;
	}
	public void setRelationNodes(List<Long> relationNodes) {
		this.relationNodes = relationNodes;
	}
	public Long getOid() {
		return oid;
	}
	public void setOid(Long oid) {
		this.oid = oid;
	}
	public Map<String, List<Node>> getNodesMap() {
		return nodesMap;
	}
	public void setNodesMap(Map<String, List<Node>> nodesMap) {
		this.nodesMap = nodesMap;
	}
	public Map<String, List<RelationNode>> getRelationNodesMap() {
		return relationNodesMap;
	}
	public void setRelationNodesMap(Map<String, List<RelationNode>> relationNodesMap) {
		this.relationNodesMap = relationNodesMap;
	}
    
    public long getTimer() {
		return timer;
	}
	public void setTimer(long timer) {
		this.timer = timer;
	}
	public void putNode(Node n){
    	if(isExistNodeId(n.getId()))return;
    	nodes.add(n.getId());
    	if(nodesMap.get(n.getDerivedFrom())!=null){
    		nodesMap.get(n.getDerivedFrom()).add(n);
    	}else{
    		List<Node> li=new ArrayList<Node>();
    		li.add(n);
    		nodesMap.put(n.getDerivedFrom(),li);
    	}
    };
    
    private boolean isExistNodeId(Long id){
    	return nodes.contains(id);
//    	for(Long l:nodes){
//    		if(l==id){
//    			return true;
//    		}
//    	}
//    	return false;
    }
    private boolean isExistRelationId(Long id){
    	return relationNodes.contains(id);
    }
    public void putRelation(RelationNode r){
    	if(isExistRelationId(r.getId()))return;
    	
    	relationNodes.add(r.getId());
    	if(relationNodesMap.get(r.getDerivedFrom())!=null){
    		relationNodesMap.get(r.getDerivedFrom()).add(r);
    	}else{
    		List<RelationNode> li=new ArrayList<RelationNode>();
    		li.add(r);
    		relationNodesMap.put(r.getDerivedFrom(),li);
    	}
    }
 
	public String getValueFilter() {
		return valueFilter;
	}

	public void setValueFilter(String valueFilter) {
		this.valueFilter = valueFilter;
	}
	public int getViewType() {
		return viewType;
	}
	public void setViewType(int viewType) {
		this.viewType = viewType;
	}    
    
}
