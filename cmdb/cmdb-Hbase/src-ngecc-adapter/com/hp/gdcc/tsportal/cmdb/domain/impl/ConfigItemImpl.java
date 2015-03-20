package com.hp.gdcc.tsportal.cmdb.domain.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.HashMap;

import com.hp.gdcc.tsportal.cmdb.domain.CiAttribute;
import com.hp.gdcc.tsportal.cmdb.domain.ConfigItem;

public class ConfigItemImpl implements ConfigItem {
	private long id;
	private String name;
	private String label;
	private String derivedFrom;
	private long parentId;
	private String path;
	private Date updateTime;
	private Date createTime;
	private boolean isType;
	private boolean isRelation;
	private long sourceId;
	private long targetId;
	//private String oneCmdbId;
	private String exchangedId;
	
	private Map<String, CiAttributeImpl> attributes = 
		new HashMap<String, CiAttributeImpl>();
	
	public void addAttribute(CiAttributeImpl attribute) {
		if (attributes.containsKey(attribute.name())) {
			CiAttributeImpl attr = attributes.get(attribute.name());
			attr.addValue(attribute.value());
		} else {
			attributes.put(attribute.name(), attribute);
		}
	}
	
	public ConfigItemImpl(long id, String name, String derivedFrom) {
		this.id = id;
		this.name = name;
		this.derivedFrom = derivedFrom;
		this.createTime = new Date();
		this.updateTime = new Date();
	}
	
	@Override
	public Collection<CiAttribute> attributes() {
		return new ArrayList<CiAttribute>(attributes.values());
	}

	@Override
	public Date createTime() {
		return createTime;
	}

	@Override
	public String derivedFrom() {
		return derivedFrom;
	}

	@Override
	public CiAttribute getAttribute(String name) {
		if(null == name)
			return null;
		if("oneCmdbId".equals(name))
			return new CiAttributeImpl(id, "oneCmdbId", null, false, exchangedId);
		if("exchangedId".equals(name))
			return new CiAttributeImpl(id, "exchangeId", null, false, exchangedId);
		if("eid".equals(name))
			return new CiAttributeImpl(id, "eid", null, false, exchangedId);
		return attributes.get(name);
	}

	@Override
	public Object getAttributeValue(String name) {
		CiAttribute attr = attributes.get(name); 
		return null==attr?null:attr.value();
	}

	@Override
	public Collection<Object> getAttributeValues(String name) {
		CiAttribute attr = attributes.get(name); 
		return null==attr?null:attr.values();
	}

	@Override
	public Collection<CiAttribute> getAttributesByType(String typeName) {
		//TODO 
		return null;
	}

	@Override
	public long id() {
		return id;
	}

	@Override
	public String label() {
		return label;
	}

	@Override
	public String name() {
		return name;
	}

	@Override
	public Date updateTime() {
		return updateTime;
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
	
	public String toString() {
		return (name + " extends " + derivedFrom);
	}
	
	@Override
	public Object get(String key) {
		if("name".equals(key))
			return name;
		else if("label".equals(key))
			return label;
		else if("id".equals(key))
			return id;
		else {
			return getAttributeValue(key);
		}
	}

	@Override
	public boolean isRelation() {
		return isRelation;
	}

	@Override
	public boolean isType() {
		return isType;
	}
	
	public void setIsType(boolean isType) {
		this.isType = isType;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setIsRelation(boolean isRelation) {
		this.isRelation = isRelation;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String path() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public long parentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	@Override
	public long sourceId() {
		return sourceId;
	}
	
	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}
	
	@Override
	public long targetId() {
		return targetId;
	}
	
	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	@Override
	public String exchangedId() {
		return exchangedId;
	}

	@Override
	public String oneCmdbId() {
		return exchangedId;
	}

	public void setOneCmdbId(String oneCmdbId) {
		if(null != oneCmdbId && oneCmdbId.length()>0)
			this.exchangedId = oneCmdbId;
	}

	public void setExchangedId(String exchangedId) {
		if(null != exchangedId && exchangedId.length()>0)
			this.exchangedId = exchangedId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDerivedFrom(String derivedFrom) {
		this.derivedFrom = derivedFrom;
	}
}
