package com.hp.gdcc.tsportal.cmdb.domain;

import java.util.Collection;

/**
 * 基于ConfigItem构建的Node节点，Node主要体现出关系信息
 * @author chengczh
 *
 */
public interface Node extends ConfigItem {
	String ATTR_KEY_DOMAIN = "domain"; 
	
	/**
	 * 取得当前CI定义的所有属性，不包括继承属性
	 * @return
	 */
	Collection<CiAttribute> myAttributes();
	
	/**
	 * 根据属性名获取属性定义，不包括继承属性
	 * @return
	 */
	CiAttribute getMyAttribute(String name);
	
	/**
	 * CI 名称 
	 * @return
	 */
	String name();
	
	/**
	 * CI 显示名称
	 * @return
	 */
	String label();
		
	/**
	 * 是否为CI类型标识
	 * @return
	 */
	boolean isTypeNode();
	
	/**
	 * 判断节点是否为指定类型
	 * @param typeName
	 * @return
	 */
	boolean isDeriveFrom(String typeName);
	
	/**
	 * 判断节点是否为指定类型
	 * @param typeNode
	 * @return
	 */
	boolean isDeriveFrom(Node typeNode);
	
	/**
	 * 获取父节点类型
	 * @return
	 */
	Node parent();
	
	/**
	 * 获取数据域标识
	 * @return
	 */
	String domain();
	
	/**
	 * 获取节点所属数据域，以及数据域的所有父域
	 * @return
	 */
	Collection<String> domains();
	
	/**
	 * 获取父节点类型的集合
	 * @return
	 */
	Collection<Node> parents();
	
	/**
	 * 取得孩子节点集合
	 * @return
	 */
	Collection<Node> children();
	
	/**
	 * 取得所有子孙节点的集合
	 * @return
	 */
	Collection<Node> offspring();
	
	/**
	 * 取得当前节点的所有指定类型来源关系（入边）
	 * @return
	 */
	Collection<RelationNode> inRelations(String relationType);
	
	/**
	 * 取得当前节点的所有指定类型目标关系（出边）
	 * @return
	 */
	Collection<RelationNode> outRelations(String relationType);
	
	
	/**
	 * 取得当前节点的所有指定关系类型来源邻居（入边）
	 * @return
	 */
	Collection<Node> sourceNeighbors(String relationType);
	
	/**
	 * 取得当前节点的所有指定关系类型目标邻居
	 * @return
	 */
	Collection<Node> targetNeighbors(String relationType);
	
	/**
	 * 取得以当前节点为目标指定节点为源的指定类型关系，null则表示没有关系
	 * @param source
	 * @param relationType
	 * @return
	 */
	RelationNode getSourceRelation(Node source, String relationType);
	
	/**
	 * 取得以当前节点为源指定节点为目标的指定类型关系，null则表示没有关系
	 * @param target
	 * @param relationType
	 * @return
	 */
	RelationNode getTargetRelation(Node target, String relationType);
	
	/**
	 * 从当前节点开始遍历所有子孙节点
	 * @param visitor
	 */
	void visit(NodeVisitor visitor);
	
	/**
	 * 从当前节点开始遍历所有关联节点
	 * @param visitor
	 * @param 关系类别名称
	 * @param 遍历方向，true时为反向遍历，即target->source
	 */
	void visit(RelationVisitor visitor, String relationType, boolean reverse);

	/**
	 * 从当前节点开始根据关系进行遍历返回满足condition的节点集合
	 * @param relationType 关系类型名
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param condition
	 * @return
	 */
	Collection<Node> gather(String relationType, boolean reverse, NodeVisitor condition);
	
	
	/**
	 * 从当前节点开始根据关系进行遍历返回指定节点类型的节点集合
	 * @param relationType 关系类型名
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param targetType 目标节点类型，null或为空串时表示任意类型
	 * @return
	 */
	Collection<Node> gather(String relationType, boolean reverse, String targetType);
	
	/**
	 * 从当前节点开始根据关系进行遍历返回满足condition的第一个节点
	 * @param relationType 关系类型名
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param condition
	 * @return
	 */
	Node gatherSingle(String relationType, boolean reverse, NodeVisitor condition);
	
	/**
	 * 从当前节点开始根据关系进行遍历返回指定节点类型的第一个节点
	 * @param relationType
	 * @param 遍历方向，true时为反向遍历，即target->source
	 * @param targetType 目标节点类型，null或为空串时表示任意类型
	 * @return
	 */
	Node gatherSingle(String relationType, boolean reverse, String targetType);
	
	/**
	 * 获取可序列化的简单节点对象
	 * @return
	 */
	SimpleNode getSimpleNode();
}