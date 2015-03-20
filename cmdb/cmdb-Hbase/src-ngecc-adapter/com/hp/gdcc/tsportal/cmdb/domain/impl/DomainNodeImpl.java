package com.hp.gdcc.tsportal.cmdb.domain.impl;

import java.util.ArrayList;
import java.util.Collection;

import com.hp.gdcc.tsportal.cmdb.domain.ConfigItem;
import com.hp.gdcc.tsportal.cmdb.domain.DomainNode;
import com.hp.gdcc.tsportal.cmdb.domain.Node;
import com.hp.gdcc.tsportal.cmdb.domain.NodeVisitor;
import com.hp.gdcc.tsportal.cmdb.model.NodeManager;

public class DomainNodeImpl extends NodeImpl implements DomainNode {
	private String parentDomain;
	
	public DomainNodeImpl(NodeManager manager, ConfigItem entity){
		super(manager, entity);
		init();
	}
	
	private void init() {
		Object value = getAttributeValue("parentDomain");
		this.parentDomain = null==value?null:String.valueOf(value);
		if(this.parentDomain == null)
			this.parentDomain = domain();
	}
	@Override
	public Collection<DomainNode> subDomains() {
		Collection<DomainNode> children = manager.getSubDomains(name()); 
		return children==null ? new ArrayList<DomainNode>():children; 
	}

	@Override
	public DomainNode superDomain() {
		return manager.getDomainNode(parentDomain);
	}
	
	@Override
	public String parentDomain() {
		return parentDomain;
	}

	@Override
	public boolean inDomain(String domainName) {
		if(null == domainName)
			return false;
		if(domainName.equals(name()))
			return true;
		else if(null == superDomain())
			return false;
		else
			return superDomain().inDomain(domainName);
	}

	@Override
	public Collection<DomainNode> allSubDomains() {
		final Collection<DomainNode> allSubs = new ArrayList<DomainNode>();
		Node rootDomain = manager.rootDomain();
		if(rootDomain != null) {
			rootDomain.visit(new NodeVisitor() {

				@Override
				public boolean accept(Node node) {
					DomainNode domain = manager.getDomainNode(node.name());
					if(domain != null && domain.inDomain(name()))
						allSubs.add(domain);
					return true;
				}

			});
		}
		return allSubs;
	}
}
