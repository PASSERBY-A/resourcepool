package com.hp.xo.uip.cmdb.domain;

public interface RelationVisitor extends NodeVisitor {
	
	boolean accept(RelationNode relation, Node node);
	
}
