package com.hp.gdcc.tsportal.cmdb.service;

import java.util.Collection;

import com.hp.gdcc.tsportal.cmdb.domain.AlertDefine;
import com.hp.gdcc.tsportal.cmdb.domain.CacheUpdateListener;
import com.hp.gdcc.tsportal.cmdb.domain.Domain;
import com.hp.gdcc.tsportal.cmdb.domain.DomainNode;
import com.hp.gdcc.tsportal.cmdb.domain.KpiDefine;
import com.hp.gdcc.tsportal.cmdb.domain.Node;
import com.hp.gdcc.tsportal.cmdb.domain.NodeVisitor;
import com.hp.gdcc.tsportal.cmdb.domain.RelationNode;
import com.hp.gdcc.tsportal.cmdb.domain.RelationVisitor;
import com.hp.gdcc.tsportal.cmdb.model.CiStore;

/**
 * CMDB数据访问服务接口，接口中返回的数据对象为已构建关系后的Node对象
 * @author cheng zhi-peng
 *
 */
public interface CmdbService {

	/**
	 * 取得当前缓存的CiStore
	 * @return CiStore对象
	 */
	CiStore getStore();
	
	/**
	 * 将指定扩展节点集合（包括类型，节点组，实例节点）全部展开为实例节点集合
	 * @param xNodeIds 扩展节点ID集合
	 * @return Collection<Long> 集合元素为CIID
	 */
	Collection<Long> expand(Collection<Long> xNodeIds);
	
	/**
	 * 判断指定节点ID是否在扩展节
	 * 点集合中（包括类型，节点组，实例节点）
	 * @param nodeId 指定的节点ID
	 * @param xNodeIds 扩展节点ID集合
	 * @return boolean
	 */
	boolean xIn(Long nodeId, Collection<Long> xNodeIds);
	
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
	
	/**
	 * 根据kpi代码返回kpi定义
	 * @param kpiCode
	 * @return KpiDefine对象
	 */
	KpiDefine getKpiDefine(long kpiCode);
	
	/**
	 * 根据节点，kpi名称返回kpi定义
	 * @param kpiName
	 * @return KpiDefine对象
	 */
	KpiDefine getKpiDefine(long nodeId, String kpiName);
	
	/**
	 * 取得指定节点的所有kpi定义
	 * @param nodeId
	 * @return KpiDefine对象集合
	 */
	Collection<KpiDefine> getKpisByNode(long nodeId);
	
	/**
	 * 取得指定节点的所有kpi定义包括上级节点的kpi定义
	 * @param nodeId
	 * @return KpiDefine对象集合
	 */
	Collection<KpiDefine> getAllKpisByNode(long nodeId);
	
	/**
	 * 获取所有Kpi定义集合
	 * @return KpiDefine对象集合
	 */
	Collection<KpiDefine> getAllKpiDefines();
	
	/**
	 * 取得所有KQI定义集合
	 * @return KpiDefine对象集合
	 */
	Collection<KpiDefine> getAllKqis();
	
	/**
	 * 根据alert代码返回alert定义
	 * @param kpiid
	 * @return AlertDefine对象
	 */
	AlertDefine getAlertDefine(long alertCode);
	
	/**
	 * 根据节点，alert名称返回alert定义
	 * @param kpiName
	 * @return AlertDefine对象
	 */
	AlertDefine getAlertDefine(long nodeId, String alertName);
	
	/**
	 * 取得指定节点的所有alert定义
	 * @param nodeId
	 * @return AlertDefine对象集合
	 */
	Collection<AlertDefine> getAlertsByNode(long nodeId);
	
	/**
	 * 取得指定节点的所有alert定义包括上级节点的alert定义
	 * @param nodeId
	 * @return AlertDefine对象集合
	 */
	Collection<AlertDefine> getAllAlertsByNode(long nodeId);	
	
	/**
	 * 获取所有告警定义
	 * @return AlertDefine对象集合
	 */
	Collection<AlertDefine> getAllAlertDefines();
	
	/**
	 * 获取数据域定义
	 * @param name
	 * @return Domain对象
	 */
	Domain getDomain(String name);
	
	/**
	 * 判断指定节点ID是否在数据域中
	 * @param nodeId
	 * @param domains
	 * @return boolean
	 */
	boolean isBelongToDomain(long nodeId, Collection<String> domains);
	
	/**
	 * 获取指定数据域集合下的节点集合，其中的数据域包括子孙域
	 * @param domains
	 * @return Node对象集合
	 */
	Collection<Node> getNodesByDomains(Collection<String> domains);
		
	/**
	 * 获取指定数据域下的节点集合，数据域包括子孙域
	 * @param domains
	 * @return Node对象集合
	 */
	Collection<Node> getNodesByDomain(String domain);
	
	/**
	 * 获取所有数据域集合
	 * @return Domain对象集合
	 */
	Collection<Domain> getAllDomains();
	
	/**
	 * 获取节点所属数据域，以及数据域的所有父域
	 * @param nodeId
	 * @return Domain对象集合
	 */
	Collection<Domain> getDomainsByNode(long nodeId);
	
	/**
	 * 获取CMDB根节点
	 * @return Node对象
	 */
	Node rootNode();
	
	/**
	 * 获取关系根节点
	 * @return Node对象
	 */
	Node rootRelation();
	
	/**
	 * 获取数据域根节点
	 * @return Node对象
	 */
	Node rootDomain();
	
	/**
	 * 获取Ci根节点
	 * @return Node对象
	 */
	Node rootCi();
	
	/**
	 * 获取CommonCi根节点
	 * @return Node对象
	 */
	Node rootCommonCi();
	
	/**
	 * 获取告警定义根节点
	 * @return Node对象
	 */
	Node rootAlert();
	
	/**
	 * 获取kpi定义根节点
	 * @return Node对象
	 */
	Node rootKpi();
	
	/**
	 * 获取DomainNode
	 * @return DomainNode对象
	 */
	DomainNode getDomainNode(String domainName);
	
	/**
	 * 获取指定域(域实例)下的所有子域(域实例)
	 * @param domainName 域名称
	 * @return DomainNode对象集合
	 */
	Collection<DomainNode> getSubDomains(String domainName);
	
	/**
	 * 判断指定名称的domain中是否包含指定的节点id
	 * @param nodeId
	 * @param domain 域名称
	 * @return boolean
	 */
	boolean inDomain(long nodeId, String domain);
	
	/**
	 * 注册Cache更新 listener
	 * @param listener 
	 */
	void addCacheUpdateListener(CacheUpdateListener listener);
	
	/**
	 * 注销Cache更新 listener
	 * @param listener
	 */
	void removeCacheUpdateListener(CacheUpdateListener listener);
	
	void updateXmlCache(String xml);
	
	String getXmlCache();
}
