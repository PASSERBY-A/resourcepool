package com.hp.xo.uip.cmdb.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ViewCondition implements Comparable,Serializable{
	
	private static final long serialVersionUID = 8060051222195658439L;
	//条件：起始查找节点，关系类型名，
	//遍历方向，遍历查找深度（可不限），
	//遍历方式（为广度优先，深度优先），遍历条件过滤
	/**
	 * 条件ID
	 */
	private Long oid;
	/**
	 * 条件名称
	 */
	private String name;
	
	/**
	 * 关联视图 ID
	 */
	private Long viewOid;
	//--------------------------------
	/**
	 * 起始节点
	 */
	private Long startNodeId=null;
	
	/**
	 * 起始节点类型， 从已查询得到的节点中按类型选择起始点
	 */
	private String startNodeType=null;
	/**
	 * 条件执行顺序
	 */
	private String order;
	
	/**
	 * 关系名称
	 */
	private String relationName;
	/**
	 * ture 为反方向，默认正向false, null为双向
	 */
	private Boolean reverse=null;
	/**
	 * 遍历深度，默认不限
	 */
	private int deepth=0;
	
	/**
	 * 遍历方式，默认为广度优先，false为深度优先
	 */
//	private Boolean breadthFirst=true;
	
	/**
	 * 关系遍历 需要的类型
	 */
	private String typeFilter;
	
	
	public Long getStartNodeId() {
		return startNodeId;
	}

	public void setStartNodeId(Long startNodeId) {
		this.startNodeId = startNodeId;
	}

	public String getRelationName() {
		return relationName;
	}

	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}

	public Boolean getReverse() {
		return reverse;
	}

	public void setReverse(Boolean reverse) {
		this.reverse = reverse;
	}

	public int getDeepth() {
		return deepth;
	}

	public void setDeepth(int deepth) {
		this.deepth = deepth;
	}

//	public Boolean getBreadthFirst() {
//		return breadthFirst;
//	}
//
//	public void setBreadthFirst(Boolean breadthFirst) {
//		this.breadthFirst = breadthFirst;
//	}

	public Long getOid() {
		return oid;
	}

	public void setOid(Long oid) {
		this.oid = oid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public Long getViewOid() {
		return viewOid;
	}

	public void setViewOid(Long viewOid) {
		this.viewOid = viewOid;
	}

	public String getStartNodeType() {
		return startNodeType;
	}

	public void setStartNodeType(String startNodeType) {
		this.startNodeType = startNodeType;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getTypeFilter() {
		return typeFilter;
	}

	public void setTypeFilter(String typeFilter) {
		this.typeFilter = typeFilter;
	}

	@Override
	public int compareTo(Object obj) {
		ViewCondition vc=(ViewCondition)obj;
		return order.compareToIgnoreCase(vc.getOrder());
	}


	public static void main(String arg[]){
		ViewCondition vc1=new ViewCondition();
		vc1.setOrder("11");
		ViewCondition vc2=new ViewCondition();
		vc2.setOrder("22");
		List li=new ArrayList();
		li.add(vc2);li.add(vc1);
		Collections.sort(li);
		System.out.print(li);
	}
}
