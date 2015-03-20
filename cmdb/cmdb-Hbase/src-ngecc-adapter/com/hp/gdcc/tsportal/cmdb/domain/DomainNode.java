package com.hp.gdcc.tsportal.cmdb.domain;

import java.util.Collection;

public interface DomainNode extends Node {
	/**
	 * 判断是否在指定域中
	 * @param domainName 域名
	 * @return
	 */
	boolean inDomain(String domainName);
	
	/**
	 * 获取父域
	 * @return
	 */
	DomainNode superDomain();
	
	/**
	 * 获取包括的子域集合	
	 * @return
	 */
	Collection<DomainNode> subDomains();
	
	/**
	 * 获取所有子孙域集合
	 * @return
	 */
	Collection<DomainNode> allSubDomains();
	
	/**
	 * 获取父域名称
	 * @return
	 */
	String parentDomain();
}
