package com.hp.xo.uip.cmdb.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class ConfigItem implements Serializable{
    
	private static final long serialVersionUID = -8012511728715143000L;
	/**
     * 配置项ID
     */
	protected Long id;
	/**
	 * 配置项名称
	 */
	protected String name;
	/**
	 * 显示名称
	 */
	protected String label;
	/**
	 * 图标名称
	 * （选择标准定义见web目录下  data/index.xml & data/tree_index.xml）
	 */
	protected String icon;
	/**
	 * 父 类名称（继承自）
	 */
	protected String derivedFrom;
	/**
	 * 父类ci ID
	 */
	protected Long parentId;
	/**
	 * 类路径 
	 */
	protected String path;
	/**
	 * 更新时间
	 */
	protected Timestamp updateTime=new Timestamp(new Date().getTime());
	
	/**
	 * 创建时间
	 */
	protected Timestamp createTime=new Timestamp(new Date().getTime());
	/**
	 * 是否类型,即类模型定义
	 */
	protected boolean isType;
	/**
	 * 外部系统 关联ID
	 */
	protected String exchangedId;
	
	/**
	 * 资源的版本，开始时为1，以后递增
	 */
	protected Integer version;
	/**
	 * 资源所属的数据域
	 */
	protected String domain;
	/**
	 * 资源可访问的用户列表，以逗号隔开，默认为all，即所有用户都可以访问
	 */
	protected String accessUsers;
	/**
	 * 资源可访问的角色列表，以逗号隔开，默认为all，即所有角色都可以访问
	 */
	protected String accessRoles;
	/**
	 * 更新模式
	 * 0:不限制    1：只手动   2：只自动
	 */
	protected int updateMode=0;
	
	 /**
     * 同步状态 ， 0：无变化， 1：新增，2：更新，3：删除
     */
	private int syncStatus=0;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getDerivedFrom() {
		return derivedFrom;
	}

	public void setDerivedFrom(String derivedFrom) {
		this.derivedFrom = derivedFrom;
	}

	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}


	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getAccessUsers() {
		return accessUsers;
	}

	public void setAccessUsers(String accessUsers) {
		this.accessUsers = accessUsers;
	}

	public String getAccessRoles() {
		return accessRoles;
	}

	public void setAccessRoles(String accessRoles) {
		this.accessRoles = accessRoles;
	}

	public boolean getIsType() {
		return isType;
	}

	public void setIsType(boolean isType) {
		this.isType = isType;
	}

	public String getExchangedId() {
		return exchangedId;
	}

	public void setExchangedId(String exchangedId) {
		this.exchangedId = exchangedId;
	}
	public int getSyncStatus() {
		return syncStatus;
	}

	public void setSyncStatus(int syncStatus) {
		this.syncStatus = syncStatus;
	}
	public Map<String,String> getSysMapData(){
		Map<String,String> ma=new LinkedHashMap<String,String>();
		ma.put("id",String.valueOf(id));
		ma.put("name",name);ma.put("label", label);
		ma.put("icon", icon);ma.put("derivedFrom", derivedFrom);
		ma.put("parentId", String.valueOf(parentId));
		ma.put("path", path);
		ma.put("updateTime", String.valueOf(updateTime==null?System.currentTimeMillis():updateTime.getTime()));
		ma.put("createTime", String.valueOf(createTime==null?System.currentTimeMillis():createTime.getTime()));
		ma.put("isType", String.valueOf(isType));
		ma.put("exchangedId", exchangedId);ma.put("version", String.valueOf(version));
		ma.put("domain", domain);ma.put("accessUsers", accessUsers);
		ma.put("accessRoles", accessRoles);ma.put("updateMode", String.valueOf(updateMode));
		return ma;
    }
	public LinkedHashMap<String,String> getSysMeta(){
		LinkedHashMap<String,String> ma=new LinkedHashMap<String,String>();
		ma.put("id","ID");
		ma.put("name","名称");ma.put("label", "显示名称");
		ma.put("icon", "图标");ma.put("derivedFrom", "类名称");
		ma.put("parentId", "父ID");
		ma.put("path", "类路径");
		ma.put("updateTime", "更新时间");
		ma.put("createTime", "创建时间");
		ma.put("isType", "是否类型");
		ma.put("exchangedId", "外部ID");ma.put("version", "版本");
		ma.put("domain", "域");ma.put("accessUsers", "可访问用户");
		ma.put("accessRoles", "可访问角色");ma.put("updateMode", "更新模式");
		return ma;
    }
	
	public abstract Map<String,String> getMapData();
	public abstract boolean isRelation();
	
}