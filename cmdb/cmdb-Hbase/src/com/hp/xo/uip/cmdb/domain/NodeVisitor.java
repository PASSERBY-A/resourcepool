package com.hp.xo.uip.cmdb.domain;

public interface NodeVisitor {
	int DEEPTH_INFINITE = -1;
	
	/**
	 * 判断指定Node是否符合遍历条件
	 * @param node
	 * @return boolean
	 */
	boolean accept(Node node);
	
}
