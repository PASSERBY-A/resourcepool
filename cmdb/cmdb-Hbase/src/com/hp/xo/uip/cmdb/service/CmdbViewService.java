package com.hp.xo.uip.cmdb.service;

import java.util.List;
import java.util.Map;

import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.ViewCondition;
import com.hp.xo.uip.cmdb.domain.ViewDefine;

public interface CmdbViewService {
	public ViewDefine insertViewDefine(ViewDefine vd);
	public boolean deleteView(String viewName);
	public ViewDefine updateView(ViewDefine vd);
	public List<ViewDefine> getViewDefineAll();
	public ViewDefine getViewDefineByName(String name);
	
	//得到视图数据
	public ViewDefine getViewDataByName(String name);
	/**
	 * 针对新疆视图优化，过滤zone信息
	 * @param name
	 * @return
	 */
	public ViewDefine getViewDataByNameFilter(String name);
	/**
	 * 针对新疆视图优化，只保留主机和存储相关信息
	 * @param name
	 * @return
	 */
	public ViewDefine getViewDataByNameFilter_host_sto(String name);
	
	public ViewDefine getViewDataByName(String name,Long startNodeId,String startNodeType,String relationName,boolean reverse,int deepth,List<String> typeFilter);
	public ViewDefine getViewDataByName(String viewName,List<ViewCondition> conditions,List<String> typeFilter);
	public void gather(Long nodeId, String nodeType, String relationName,
			Boolean reverse, int deepth, int deepthRecord, List<Node> nodes,
			List<RelationNode> relations,List<String> typeFilter);
	public List<Node> getNodeByRelation(String startNodeType,Long startNodeId,
			String relationName,int deepth,String... typeFilter);
	/**
	 * 根据起始点 返回关系点
	 * @param startNodeType
	 * @param startNodeId
	 * @param relationName
	 * @param reverse
	 * @param deepth
	 * @param typeFilter
	 * @return
	 */
	public List<Node> getNodeByRelation(String startNodeType,Long startNodeId,
			String relationName,Boolean reverse,int deepth,String... typeFilter);
	/**
	 * 批量起始点 关系查询
	 * @param startNodes
	 * @param relationName
	 * @param reverse
	 * @param deepth
	 * @param typeFilter
	 * @return
	 */
	public List<Node> getNodeByRelation(List<Node> startNodes,
			String relationName,Boolean reverse,int deepth,String... typeFilter);
	/**
	 * 通用查询 返回 map<typeName,list<node>>
	 * @param startNodeType
	 * @param startNodeId
	 * @param relationName
	 * @param reverse  null：表示正反双向
	 * @param deepth  表示向下深度
	 * @param typeFilter 类型过滤
	 * @return
	 */	
	public Map<String,List<Node>> getNodeRelationMap(String startNodeType,Long startNodeId,String relationName,Boolean reverse,int deepth,String... typeFilter);
	
	/**
	 * 通用查询 获得第一层 ci
	 * @param startNodeType
	 * @param startNodeId
	 * @param relationName
	 * @return
	 */
	public Map<String,List<Node>> getNodeRelationMapLv1(String startNodeType,Long startNodeId,String relationName);
}
