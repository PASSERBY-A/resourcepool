package com.hp.gdcc.tsportal.cmdb.domain;

public interface RelationVisitor extends NodeVisitor {
	
	boolean accept(RelationNode relation, Node node);
	
}
