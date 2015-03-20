package com.hp.gdcc.tsportal.cmdb.domain;

import java.util.Collection;
import java.util.Date;

import com.hp.itis.core2.vars.IVars;
/**
 * 配置项
 * @author chengczh
 *
 */
public interface ConfigItem extends IVars {

	/**
	 * CI ID
	 * @return
	 */
	long id();
	
	/**
	 * CI Name
	 * @return
	 */
	String name();
	
	/**
	 * CI 显示名称
	 * @return
	 */
	String label();
	
	/**
	 * 更新时间
	 * @return
	 */
	Date updateTime();
	
	/**
	 * 创建时间
	 * @return
	 */
	Date createTime();
	
	/**
	 * 继承自CI的name
	 */
	String derivedFrom();
	
	/**
	 * 继承自CI的id
	 * @return
	 */
	long parentId();
	
	/**
	 * CI的继承路径
	 * @return
	 */
	String path();
	
	/**
	 * 是否是CI类型
	 * @return
	 */
	boolean isType();
	
	/**
	 * 是否是关系
	 * @return
	 */
	boolean isRelation();
	
	/**
	 * 关系CI时，源CIID
	 * @return
	 */
	long sourceId();
	
	/**
	 * 关系CI时，目的CIID
	 * @return
	 */
	long targetId();
	
	/**
	 * 对应OneCMDB中的CiId
	 * @return
	 */
	String oneCmdbId();
	
	/**
	 * 对应外部CMDB的CiId
	 * @return
	 */
	String exchangedId();
	
	/**
	 * 取得当前CI定义的所有属性
	 * @return
	 */
	Collection<CiAttribute> attributes();
	
	/**
	 * 根据属性名获取属性定义，包括继承属性
	 * @return
	 */
	CiAttribute getAttribute(String name);
	
	/**
	 * 根据属性类型获取属性定义，包括继承属性
	 * @return
	 */
	Collection<CiAttribute> getAttributesByType(String typeName);
	
	/**
	 * 根据属性名获取属性值，包括继承属性
	 * @return
	 */
	Object getAttributeValue(String name);
	
	/**
	 * 根据属性名获取属性值，包括继承属性
	 * @return
	 */
	Collection<Object> getAttributeValues(String name);
	
	/**
	 * 从当前节点提取属性，并尝试通过setter方法复值给指定pojo对象
	 * @param pojo
	 */
	void extract(Object pojo);
	
	/**
	 * 以默认类型从当前节点创建并填充对象
	 * @return
	 */
	Object extract();
	
}