package com.hp.xo.uip.cmdb.dao;

import java.util.List;

import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.ViewCondition;
import com.hp.xo.uip.cmdb.domain.ViewDefine;

public interface CmdbViewDao {
	public ViewDefine insertViewDefine(ViewDefine vd);
	public boolean deleteView(String viewName);
	public ViewDefine updateView(ViewDefine vd);
	public List<ViewDefine> getViewDefineAll();
	public ViewDefine getViewDefineByName(String name);
	
	//得到视图数据
	public ViewDefine getViewDataByName(String name);
	public ViewDefine getViewDataByName(String name,Long startNodeId,String startNodeType,String relationName,Boolean reverse,int deepth,List<String> typeFilter);
	public ViewDefine getViewDataByName(String viewName,List<ViewCondition> conditions,List<String> typeFilter);
	public void gather(Long nodeId, String nodeType, String relationName,
			Boolean reverse, int deepth, int deepthRecord, List<Node> nodes,
			List<RelationNode> relations,List<String> typeFilter);
}
