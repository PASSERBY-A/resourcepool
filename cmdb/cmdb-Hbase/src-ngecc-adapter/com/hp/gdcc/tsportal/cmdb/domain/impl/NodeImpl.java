package com.hp.gdcc.tsportal.cmdb.domain.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import com.hp.gdcc.tsportal.cmdb.domain.CiAttribute;
import com.hp.gdcc.tsportal.cmdb.domain.ConfigItem;
import com.hp.gdcc.tsportal.cmdb.domain.Node;
import com.hp.gdcc.tsportal.cmdb.domain.NodeVisitor;
import com.hp.gdcc.tsportal.cmdb.domain.RelationNode;
import com.hp.gdcc.tsportal.cmdb.domain.RelationVisitor;
import com.hp.gdcc.tsportal.cmdb.domain.SimpleNode;
import com.hp.gdcc.tsportal.cmdb.model.NodeManager;

public class NodeImpl implements Node {
	private Logger log = Logger.getLogger(this.getClass());
	
	private ConfigItem entity;
	protected NodeManager manager;
	private String domain;
	
	public NodeImpl(NodeManager manager, ConfigItem entity) {
		this.manager = manager;
		this.entity = entity;
		init();
	}
	
	private void init() {
		Object value = entity.getAttributeValue(Node.ATTR_KEY_DOMAIN);
		this.domain = null==value?null:String.valueOf(value);
		if(this.domain != null && 
				("null".equalsIgnoreCase(this.domain) ||
						"Domain".equalsIgnoreCase(this.domain)))
			this.domain = null;
	}
	
	@Override
	public Collection<Node> children() {
		//包括ci类型
		Collection<Node> children = manager.getNodesByType(id()); 
		return children==null ? new ArrayList<Node>():children; 
	}

	@Override
	public Collection<Node> gather(String relationType, boolean reverse,
			NodeVisitor condition) {
		return manager.gather(this, relationType, reverse, -1, condition);
	}

	@Override
	public Collection<Node> gather(String relationType, boolean reverse,
			String targetType) {
		return manager.gather(this, relationType, reverse, -1, targetType);
	}

	@Override
	public Node gatherSingle(String relationType, boolean reverse,
			NodeVisitor condition) {
		return manager.gatherSingle(this, relationType, reverse,
				-1, condition);
	}

	@Override
	public Node gatherSingle(String relationType, boolean reverse,
			String targetType) {
		return manager.gatherSingle(this, relationType, reverse,
				-1, targetType);
	}

	@Override
	public CiAttribute getMyAttribute(String name) {
		return entity.getAttribute(name);
	}

	@Override
	public RelationNode getSourceRelation(Node source, String relationType) {
		return manager.getRelation(source.id(), this.id(), relationType);
	}

	@Override
	public RelationNode getTargetRelation(Node target, String relationType) {
		return manager.getRelation(this.id(), target.id(), relationType);
	}

	@Override
	public Collection<RelationNode> inRelations(String relationType) {
		return manager.getInRelations(id(), relationType);
	}

	@Override
	public boolean isDeriveFrom(String typeName) {
		if(null == typeName)
			return false;
		if(typeName.equals(name()))
			return true;
		else if(null == parent())
			return false;
		else
			return parent().isDeriveFrom(typeName);
	}

	@Override
	public boolean isDeriveFrom(Node typeNode) {
		if(typeNode == null) {
			throw new RuntimeException("input param typeNode is null");
		}
		return isDeriveFrom(typeNode.name());
	}

	@Override
	public boolean isTypeNode() {
		return entity.isType();
	}

	@Override
	public String label() {
		return entity.label();
	}

	@Override
	public Collection<CiAttribute> myAttributes() {
		return entity.attributes();
	}

	@Override
	public String name() {
		return entity.name();
	}

	@Override
	public Collection<Node> offspring() {
		return manager.getAllNodesByType(id());
	}

	@Override
	public Collection<RelationNode> outRelations(String relationType) {
		return manager.getOutRelations(id(), relationType);
	}

	@Override
	public Collection<Node> parents() {
		return manager.getParents(id());
	}
	
	@Override
	public Node parent() {
		return manager.getNode(entity.parentId());
	}
		
	@Override
	public Collection<Node> sourceNeighbors(String relationType) {
		Set<Node> neighbors = new LinkedHashSet<Node>();
		Collection<RelationNode> relations = inRelations(relationType);
		if(relations == null)
			return neighbors;
		for(RelationNode relation : relations) {
			log.debug(relation.source());
			neighbors.add(relation.source());
		}
		return neighbors;
	}

	@Override
	public Collection<Node> targetNeighbors(String relationType) {
		Set<Node> neighbors = new LinkedHashSet<Node>();
		Collection<RelationNode> relations = outRelations(relationType);
		if(relations == null)
			return neighbors;
		for(RelationNode relation : relations) {
			neighbors.add(relation.target());
		}
		return neighbors;
	}

	@Override
	public void visit(NodeVisitor visitor) {
		manager.visit(this, visitor);	
	}

	@Override
	public void visit(RelationVisitor visitor, String relationType,
			boolean reverse) {
		manager.visit(this, relationType, reverse, visitor);
		
	}

	@Override
	public Collection<CiAttribute> attributes() {
		return entity.attributes();
	}

	@Override
	public String derivedFrom() {
		return entity.derivedFrom();
	}

	@Override
	public void extract(Object pojo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object extract() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public CiAttribute getAttribute(String name) {
		return entity.getAttribute(name);
	}

	@Override
	public Object getAttributeValue(String name) {
		return entity.getAttributeValue(name);
	}

	@Override
	public Collection<Object> getAttributeValues(String name) {
		return entity.getAttributeValues(name);
	}

	@Override
	public Collection<CiAttribute> getAttributesByType(String typeName) {
		return entity.getAttributesByType(typeName);
	}

	@Override
	public long id() {
		return entity.id();
	}

	@Override
	public boolean isRelation() {
		return entity.isRelation();
	}

	@Override
	public boolean isType() {
		return entity.isType();
	}

	@Override
	public Date updateTime() {
		return entity.updateTime();
	}
	
	@Override
	public Date createTime() {
		return entity.createTime();
	}
	
	@Override
	public Object get(String key) {
		return entity.get(key);
	}

	@Override
	public String path() {
		return entity.path();
	}

	@Override
	public long parentId() {
		return entity.parentId();
	}
	
	public String toString() {
		return "<id:" + entity.id() + ">" + 
			"<name:" + entity.name() + ">" +
			"<deriveFrom:" + entity.derivedFrom() + ">" +
			"<domain:" + domain() + ">" ;
	}

	@Override
	public long sourceId() {
		return entity.sourceId();
	}

	@Override
	public long targetId() {
		return entity.targetId();
	}

	@Override
	public SimpleNode getSimpleNode() {
		SimpleNode sNode = new SimpleNode();
		sNode.setNodeId(id());
		sNode.setName(name());
		sNode.setLabel(label());
		sNode.setParentId(parentId());
		sNode.setIsType(isType());
		sNode.setDomain(domain());
		for(CiAttribute attr : attributes()) {
			sNode.getProperties().put(attr.name(), attr.value());
			sNode.getPropertyLabels().put(attr.name(), attr.label());
		}
		return sNode;
	}

	@Override
	public String domain() {
		return this.domain;
	}
	
	@Override
	public Collection<String> domains() {
		if(null == this.domain) {
			Collection<String> tmp = new ArrayList<String>();
			tmp.add(manager.ROOT_DOMAIN_INS);
			return tmp;
		}
		Collection<String> all = manager.getDomainParents(this.domain);
		if(null != this.domain)
			all.add(this.domain);
		return all;
	}

	@Override
	public String exchangedId() {
		return entity.exchangedId();
	}

	@Override
	public String oneCmdbId() {
		return entity.oneCmdbId();
	}
}
