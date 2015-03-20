package com.hp.gdcc.tsportal.cmdb.domain;

import java.util.Collection;

public interface CiAttribute {
	/**
	 * 所属CI ID
	 * @return
	 */
	long ciId();
	
	/**
	 * 属性 name
	 * @return
	 */
	String name();
	
	/**
	 * 属性显示名称
	 * @return
	 */
	String label();
	
	/**
	 * 数据类型
	 * @return
	 */
	String dataType();
	
	/**
	 * 关系类型CI ID
	 * @return
	 */
	String refType();
	
	/**
	 * 关联的CI ID
	 * @return
	 */
	long refCiId();
	
	/**
	 * 是否是关系属性
	 * @return
	 */
	boolean isReference();
	
	/**
	 * 属性值，当同一属性对应多个属性值时，返回第一值
	 * 当属性值为null时，返回null
	 * @return
	 */
	Object value();
	
	/**
	 * 属性值集合，当属性仅有一个值时，返回的集合中只有一个值
	 * 当属性值为null时，返回的集合size为0
	 * @return
	 */
	Collection<Object> values();
	
	/**
	 * 是否允许编辑
	 * @return
	 */
	boolean allowEdit();
	
	/**
	 * 字典来源
	 * @return
	 */
	String fromDict();
	
	/**
	 * 预留
	 * @return
	 */
	String reserved1();
	/**
	 * 预留
	 * @return
	 */
	String reserved2();
	/**
	 * 预留
	 * @return
	 */
	String reserved3();
	/**
	 * 预留
	 * @return
	 */
	String reserved4();
}
