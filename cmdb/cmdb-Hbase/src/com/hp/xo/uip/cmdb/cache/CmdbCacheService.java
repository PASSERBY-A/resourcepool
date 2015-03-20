package com.hp.xo.uip.cmdb.cache;

import java.util.List;
import java.util.Map;

import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.ViewDefine;
import com.hp.xo.uip.cmdb.exception.CmdbException;

public interface CmdbCacheService {
	/**
	 * 模型类 
	 * @param ciTypes
	 */
	public  void setCiTypes(Map<String, Node> ciTypes);
	/**
	 * 模型类集合
	 * @return
	 */
	public  Map<String, Node> getCiTypes();
	/**
	 * 得到类定义
	 * @param ciTypeName
	 * @return
	 */
	public  Node getCiType(String ciTypeName);
	/**
	 * 模型关系类集合
	 * @return
	 */
	public  Map<String, RelationNode> getRelationTypes();
	/**
	 * 模型关系定义
	 * @param relationType
	 * @return
	 */
	public  RelationNode getRelationType(String relationType);
//	public  Map<String, ViewDefine> getViews();
	/**
	 * 得到 关系正向 实例 集合
	 * map<relationName_sourceNodeType_sourceNodeId,List<relationName_relationNodeId;targetNodeType_nodeId>>
	 */
	public  Map<String, List<String>> getRelationIds();
	/**
	 * 得到 关系正向 实例
	 * List<relationName_relationNodeId;targetNodeType_nodeId>
	 */
	public  List<String> getRelationIds(String startRelationNode);
	/**
	 * map<relationName_targetNodeType_targetNodeId,List<relationName_relationNodeId;sourceNodeType_nodeId>>
	 * 得到关系反向实例 集合
	 * @return
	 */
	public  Map<String, List<String>> getRelationIds_reverse();
	/**
	 * 得到关系反向实例
	 * List<relationName_relationNodeId;sourceNodeType_nodeId>
	 * @param startRelationNode : relationName_targetNodeType_targetNodeId
	 * @return
	 */
	public  List<String> getRelationIds_reverse(String startRelationNode);
	
	/**
	 * 得到类实例 集合 
	 * map<nodeType_nodeId,Node>
	 * id - node集合
	 * @return
	 */
	public Map<String, Node> getNodes();
	/**
	 * 得到类实例 
	 * 从map<nodeType_nodeId,Node>中得到实例
	 * @return
	 */
	public Node getNode(String type_id);
	/**
	 * 得到类关系实例 集合 
	 * map<nodeType_nodeId,Node>
	 * id - node集合 
	 * @return
	 */
	public  Map<String, RelationNode> getRelations();
	/**
	 * 得到类关系实例  根据ID
	 * @param type_id
	 * @return
	 */
	public  RelationNode getRelations(String type_id);
	/**
	 * 获得类实例 名称 - ID对应的集合
	 * @return
	 */
	public  Map<String, String> getNodes_name();
	/**
	 * 获得关系实例 名称 - ID对应的集合
	 * @return
	 */
	public  Map<String, String> getRelations_name();
	/**
	 * 根据名称 获得 type_id
	 * @param type_name
	 * @return
	 */
	public  String getNodeId_name(String type_name);
	/**
	 * 根据名称 获得 type_id
	 * @param type_name
	 * @return
	 */
	public  String getRelationId_name(String type_name);
	
	/**
	 * 根据类型获得ci实例列表
	 * @param ciType
	 * @return
	 */
	public List<Node> getCitNodes(String ciType);
	
	public void initCache();	
	public void refrenshNodes() throws CmdbException;
	public void refrenshRelations() throws CmdbException;
	public void refrenshRelationTypes();
	public void refrenshCiTypes();	
	public void refrenshNodesByType(String nodeType) throws CmdbException;
	//---
	public int deleteNode(String ciType, Long ciId);
	public Node insertNode(Node node);
	public Node updateNode(Node node);
	
	public RelationNode insertRelation(RelationNode node);
	public RelationNode updateRelation(RelationNode node);
	public int deleteRelation(String ciType, Long ciId);
	
	public boolean hasRelation(String relationType,String sourceType,long sourceId,String targetType,long targetId);
	public boolean hasRelation(RelationNode rn);
}
