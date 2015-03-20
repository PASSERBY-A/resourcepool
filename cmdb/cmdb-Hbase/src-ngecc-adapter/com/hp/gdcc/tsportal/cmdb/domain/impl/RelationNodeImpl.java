package com.hp.gdcc.tsportal.cmdb.domain.impl;

import com.hp.gdcc.tsportal.cmdb.domain.ConfigItem;
import com.hp.gdcc.tsportal.cmdb.domain.Node;
import com.hp.gdcc.tsportal.cmdb.domain.RelationNode;
import com.hp.gdcc.tsportal.cmdb.model.NodeManager;

public class RelationNodeImpl extends NodeImpl implements RelationNode {
	private ConfigItem entity;
	
	public RelationNodeImpl(NodeManager manager, ConfigItem entity){
		super(manager, entity);
		this.entity = entity;
	}


	@Override
	public boolean directed() {
		return true;
	}


	@Override
	public Node source() {
		return manager.getNode(entity.sourceId());
	}


	@Override
	public Node target() {
		return manager.getNode(entity.targetId());
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
	public String toString() {
		return "<id:" + entity.id() + ">" + 
			"<name:" + entity.name() + ">" +
			"<deriveFrom:" + entity.derivedFrom() + ">" +
			"<sourceId:" + sourceId() + ">"  +
			"<targetId:" + targetId() + ">";
	}
}
