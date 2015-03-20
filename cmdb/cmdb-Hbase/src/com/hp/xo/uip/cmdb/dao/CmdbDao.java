package com.hp.xo.uip.cmdb.dao;

import java.util.List;
import java.util.Map;

import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.domain.RelationNode;
import com.hp.xo.uip.cmdb.exception.CmdbException;

public interface CmdbDao {
	/**
	 * 新增 节点操作  （类&实例）
	 * @param node
	 * @return
	 */
	public Node insertNode(Node node) throws CmdbException;
	public int deleteNode(String typeName, Long ciId) throws CmdbException;
	public Node updateNode(Node node) throws CmdbException;

	
	/**
	 * 得到类实例 通过类名称 ，实例名称
	 * @param name
	 * @return
	 */	
//	public Node getCiByName(String typeName,String ciName);
	
	/**
	 * 得到类 通过类ID（类id唯一）
	 * @return
	 */
//	public Node getCiTypeById(long typeId);
	/**
	 * 得到类实例 通过实例ID（实例id唯一）
	 * @param ciId
	 * @return
	 */
	public Node getCiByCiId(String typeName,Long ciId) throws CmdbException;
	public List<Node> getCi() throws CmdbException;
	/**
	 * 得到类所有实例 通过类名称(类名唯一)
	 * @param name
	 * @return
	 */
	public List<Node> getCiByTypeName(String typeName) throws CmdbException;;
	/**
	 * 得到类所有实例 通过类ID（类id唯一）
	 * @return
	 */
//	public List<Node> getCiByTypeId(long typeId);

	//----------关系操作  关系为特殊指定属性的一类ci
	
	/**
	 * 新增 节点操作  （关系类&关系实例）
	 * @param node
	 * @return
	 */
	public RelationNode insertRelation(RelationNode relationNode) throws CmdbException;
	public int deleteRelation(String typeName,Long ciId) throws CmdbException;
	public RelationNode updateRelation(RelationNode relationNode) throws CmdbException;
 	
	/**
	 * 得到所有关系Ci（实例）
	 * @return
	 */
	public List<RelationNode> getRelationCi() throws CmdbException;
	
	/**
	 * 得到关系类实例 通过类名称 ，实例名称
	 * @param name
	 * @return
	 */	
//	public RelationNode getRelationCiByName(String typeName,String ciName);
	
	/**
	 * 得到关系类 通过类ID（类id唯一）
	 * @param typeId
	 * @return
	 */
//	public RelationNode getRelationTypeById(Long ciId);
	/**
	 * 得到关系类实例 通过实例ID（实例id唯一）
	 * @param ciId
	 * @return
	 */
	public RelationNode getRelationCiByCiId(String typeName,long ciId) throws CmdbException;
	
	/**
	 * 得到关系类所有实例 通过类名称(类名唯一)
	 * @param name
	 * @return
	 */
	public List<RelationNode> getRelationCiByTypeName(String typeName) throws CmdbException;
	/**
	 * 得到关系类所有实例 通过类ID（类id唯一）
	 * @param typeId
	 * @return
	 */
//	public List<RelationNode> getRelationCiByTypeId(long typeId);
	/**
	 * 初始化 默认属性
	 */
	public void initAttDef();
	
	public void insertNodeCis(List<Node> nodes) throws CmdbException;
	public void insertRelationCis(List<RelationNode> nodes) throws CmdbException;
	
	/**
	 * 条件查询
	 * @param typeName 填写类型 可大幅缩小查询范围
	 * @param conditions 条件，组成见实现类描述
	 * @param isRelation 是否关系类
	 * @return
	 * @throws CmdbException
	 */
	public List getCiByCondition(String typeName,List<Map> conditions,boolean isRelation) throws CmdbException;
	
}
