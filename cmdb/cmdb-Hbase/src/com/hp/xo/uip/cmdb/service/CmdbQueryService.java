package com.hp.xo.uip.cmdb.service;

import java.util.Collection;

import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.NodeVisitor;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.domain.RelationVisitor;

public interface CmdbQueryService {
	/**
	 * 取得节点（类型，实例，关系）
	 * @param nodeId 节点id
	 * @return Node对象
	 */
	Node getNode(long nodeId);
	
	/**
	 * 获得指定类型的所有下级节点，包括实例和类型
	 * @param typeId 类型节点id
	 * @return Node集合
	 */
	Collection<Node> getNodesByType(long typeId);
	
	/**
	 * 获得指定类型的所有下级节点，包括实例和类型
	 * @param typeName 类型节点名称
	 * @return Node对象
	 */
	Collection<Node> getNodesByType(String typeName);
	
	/**
	 * 取得匹配指定属性的第一个节点
	 * @param property 属性名
	 * @param value 属性值
	 * @return Node对象
	 */
	Node getNodeByProperty(String property, Object value);
	
	/**
	 * 取得匹配指定属性的所有节点
	 * @param property 属性名
	 * @param value 属性值
	 * @return Node集合
	 */
	Collection<Node> getNodesByProperty(String property, Object value);
	
	/**
	 * 获得指定名称的节点
	 * @param name
	 * @return Node对象
	 */
	Node getNode(String name);
	
	/**
	 * 获取指定类型的所有子孙实例节点
	 * @param typeId
	 * @return Node集合
	 */
	Collection<Node> getAllNodesByType(long typeId);
	
	/**
	 * 获取指定类型的所有子孙实例节点
	 * @param typeName
	 * @return Node集合
	 */
	Collection<Node> getAllNodesByType(String typeName);
	
	/**
	 * 获取指定类型的所有子孙类型节点
	 * @param typeName
	 * @return Node集合
	 */
	Collection<Node> getAllTypeNodesByType(long typeId);
	
	/**
	 * 获取指定类型的所有子孙类型节点
	 * @param typeName
	 * @return Node集合
	 */
	Collection<Node> getAllTypeNodesByType(String typeName);
	
	/**
	 * 获取Root CI下的所有子孙类型节点
	 * @param typeName
	 * @return Node集合
	 */
	Collection<Node> getAllTypeNodes();
	
	/**
	 * 获取指定节点的所有父类型节点
	 * @param typeId
	 * @return Node集合
	 */
	Collection<Node> getParents(long typeId);
	
	// 关系
	
	
	/**
	 * 从指定跟节点开始根据类型进行遍历
	 * @param rootNode, 遍历的起始节点
	 * @param visitor, NodeVisitor对象，为遍历条件
	 */
	void visit(Node rootNode, NodeVisitor visitor);
	
	/**
	 * 从指定跟节点开始根据关系进行图遍历,缺省为广度优先遍历, 遍历深度为不限深度
	 * @param startNode 起始查找节点
	 * @param relationType 关系类型名
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param visitor RelationVisitor对象，为遍历条件
	 */
	void visit(Node startNode, String relationType, boolean reverse, RelationVisitor visitor);
		
	/**
	 * 从指定跟节点开始根据关系进行图遍历
	 * @param startNode 起始查找节点
	 * @param relationType 关系类型名
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param deepth 遍历查找深度，1为直接关系, -1为不限深度
	 * @param 遍历方式，true为广度优先，false为深度优先
	 * @param visitor RelationVisitor对象，遍历条件
	 */
	void visit(Node startNode, String relationType, boolean reverse, int deepth, boolean breadthFirst, RelationVisitor visitor);
	
	/**
	 * 从指定节点开始根据关系进行遍历, 返回满足condition的节点集合
	 * 缺省为广度优先遍历
	 * @param startNode 起始查找节点
	 * @param relationType 关系类型名
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param deepth 遍历查找深度，1为直接关系, -1为不限深度
	 * @param condition NodeVisitor对象
	 * @return Node集合
	 */
	Collection<Node> gather(Node startNode, String relationType, boolean reverse, int deepth, NodeVisitor condition);
	
	/**
	 * 从指定节点开始根据关系进行遍历返回指定节点类型的节点集合
	 * 缺省为广度优先遍历
	 * @param startNode 起始查找节点
	 * @param relationType 关系类型名
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param deepth 遍历查找深度，1为直接关系, -1为不限深度
	 * @param targetType 目标节点类型，null或为空串时表示任意类型
	 * @return Node集合
	 */
	Collection<Node> gather(Node startNode, String relationType, boolean reverse, int deepth, String targetType);
	Collection<Node> gatherId(Long startNode, String relationType, boolean reverse, int deepth, String targetType);
	
	/**
	 * 从指定节点开始根据关系进行遍历返回满足condition的第一个节点
	 * 缺省为广度优先遍历
	 * @param startNode 起始查找节点
	 * @param relationType 关系类型名
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param deepth 遍历查找深度，1为直接关系, -1为不限深度
	 * @param condition NodeVisitor对象，遍历条件
	 * @return Node对象
	 */
	Node gatherSingle(Node startNode, String relationType, boolean reverse, int deepth, NodeVisitor condition);
	
	/**
	 * 从指定节点开始根据关系进行遍历返回指定节点类型的第一个节点
	 * 缺省为广度优先遍历
	 * @param startNode 起始查找节点
	 * @param relationType
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param deepth 遍历查找深度，1为直接关系, -1为不限深度
	 * @param targetType 目标节点类型，null或为空串时表示任意类型
	 * @return Node对象
	 */
	Node gatherSingle(Node startNode, String relationType, boolean reverse, int deepth, String targetType);
	
	/**
	 * 判断指定的node1和node2之间是否有满足参数条件的关系
	 * 缺省为广度优先遍历
	 * @param node1 起始节点
	 * @param node2 结束节点
	 * @param relationType 关系类型
	 * @param reverse 遍历方向，true时为反向遍历，即target->source
	 * @param deepth 遍历查找深度，1为直接关系, -1为不限深度
	 * @return boolean
	 */
	boolean hasRelation(Node startNode, Node endNode, String relationType, boolean reverse, int deepth);
	
	
	/**
	 * 
	 * 获取指定节点间的唯一关系
	 * @param sourceId 源节点id
	 * @param targetId 目标节点id
	 * @param relationType 关系类型名称
	 * @return RelationNode对象
	 */
	RelationNode getRelation(long sourceId, long targetId, String relationType);
	
	/**
	 * 获取指定节点的出方向关系集合
	 * @param nodeId
	 * @param relationType 关系类型名称，null表示所有类型关系
	 * @return RelationNode集合
	 */
	Collection<RelationNode> getOutRelations(long nodeId, String relationType);
	
	/**
	 * 获取指定节点的入方向关系集合
	 * @param nodeId
	 * @param relationType 关系类型名称，null表示所有类型关系
	 * @return RelationNode集合
	 */
	Collection<RelationNode> getInRelations(long nodeId, String relationType);
	
	/**
	 * 获取所有关系
	 * @return RelationNode集合
	 */
	Collection<RelationNode> getAllRelations();
	
	/**
	 * 获取所有关系
	 * @param relationType 关系类型名称
	 * @return RelationNode集合
	 */
	Collection<RelationNode> getAllRelations(String relationType);


//todo 需补充KpiDefine AlertDefine Domain	 

}
