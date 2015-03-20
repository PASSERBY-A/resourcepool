package com.hp.gdcc.tsportal.cmdb.domain;

/**
 * 关系节点，关系节点包括关系的源、目的节点信息
 * @author chengczh
 *
 */
public interface RelationNode extends Node{
	
	String RT_RELATION = "Relation";
	String RT_DERIVE_FROM = "DeriveFrom";
	String RT_DEPEND_ON = "DependOn";
	String RT_POINT_TO = "PointTo";
	String RT_BELONG_TO = "BelongTo";
	String RT_INSTALLED_ON = "InstalledOn";
	String RT_LOCATE_ON = "LocateOn";
	
	/**
	 * 一个关联关系的源节点
	 * @return
	 */
	Node source();
	
	/**
	 * 一个关联关系的目的节点
	 * @return
	 */
	Node target();
	
	/**
	 * 返回关系是否是有向关系，默认情况下为有向
	 * @return boolean
	 */
	boolean directed();
}
