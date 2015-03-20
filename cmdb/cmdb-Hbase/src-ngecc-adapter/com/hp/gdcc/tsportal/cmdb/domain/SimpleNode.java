package com.hp.gdcc.tsportal.cmdb.domain;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hp.itis.core2.commdata.TypeCaster;

/**
 * 对ConfigItem的简单包装，此类实现可序列化接口，主要为前台Flex调用提供
 * @author chengczh
 *
 */
public class SimpleNode implements Serializable {

	private static final long serialVersionUID = 413548021881167174L;
	private long nodeId;
	private String name;
	private String label;
	private long parentId;
	private boolean isType;
	private String domain;
	private String parentName;
	private Map<String, Object> properties = new LinkedHashMap<String, Object>();
	private Map<String, String> propertyLabels = new LinkedHashMap<String, String>();

	public SimpleNode() {
		
	}
	
	public SimpleNode(SimpleNode sNode) {
		update(sNode);
	}
	
	public SimpleNode(ConfigItem ci) {
		this.nodeId = ci.id();
		this.name = ci.name();
		this.parentId = ci.parentId();
		this.label = ci.label();
		this.isType = ci.isType();
		this.parentName = ci.derivedFrom();
	}
	
	public void update(SimpleNode sNode) {
		nodeId = sNode.nodeId;
		name = sNode.name;
		label = sNode.label;
		parentId = sNode.parentId;
		isType = sNode.isType;
		domain = sNode.domain;
		parentName = sNode.parentName;
		properties.clear();
		properties.putAll(sNode.properties);
	}
	
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public boolean getIsType() {
		return isType;
	}
	public void setIsType(boolean isType) {
		this.isType = isType;
	}
	public String getDomain() {
		return domain;
	}
	public void setDomain(String domain) {
		this.domain = domain;
	}
	public Map<String, Object> getProperties() {
		return properties;
	}
	public void setProperties(Map<String, Object> properties) {
		this.properties = properties;
	}
	public <T> T getProperty(String name, T defValue) {
		return TypeCaster.cast(properties.get(name), defValue);
	}
	public void setProperty(String name, Object v) {
		properties.put(name, v);
	}
	
	public Map<String, String> getPropertyLabels() {
		return propertyLabels;
	}

	public void setPropertyLabels(Map<String, String> propertyLabels) {
		this.propertyLabels = propertyLabels;
	}
	
	public String getPropertyLabel(String name) {
		return propertyLabels.get(name);
	}
	
	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String toString() {
		return "<id:" + nodeId + ">" + 
			"<name:" + name + ">" +
			"<parentId:" + parentId + ">" +
			"<parentName:" + parentName + ">";
	}
}
