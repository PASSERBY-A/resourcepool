package com.hp.avmon.utils;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class TreeObject {
	@Expose
    private String text;

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Expose
	private String iconCls;
	
	public String getIconCls() {
		return iconCls;
	}

	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}

	@Expose
	private String icon;

    public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Expose
	private String url;

    public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Expose
	private List<TreeObject> children = new ArrayList<TreeObject>();

	public List<TreeObject> getChildren() {
		return children;
	}

	public void setChildren(List<TreeObject> children) {
		this.children = children;
	}

	public void setChild(TreeObject child) {
		this.getChildren().add(child);
	}
	
	@Expose
	private String id;
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Expose
	private String pid;
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	@Expose
	private String isDirFlag;

	public String getIsDirFlag() {
		return isDirFlag;
	}

	public void setIsDirFlag(String isDirFlag) {
		this.isDirFlag = isDirFlag;
	}
	
	@Expose
	private String moId;
	
//	@Expose
//	private Boolean expanded;
//
//	public Boolean getExpanded() {
//		return expanded;
//	}
//
//	public void setExpanded(Boolean expanded) {
//		this.expanded = expanded;
//	}
	
	public String getMoId() {
		return moId;
	}

	public void setMoId(String moId) {
		this.moId = moId;
	}

	@Expose
	private String expanded="true";

    public String getExpanded() {
        return expanded;
    }

    public void setExpanded(String expanded) {
        this.expanded = expanded;
    }
    
    private Boolean checked;

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	private String hostName;
	private String hostStatus;
	private String enableFlag;

	public String getHostName() {
		return hostName;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public String getHostStatus() {
		return hostStatus;
	}

	public void setHostStatus(String hostStatus) {
		this.hostStatus = hostStatus;
	}

	public String getEnableFlag() {
		return enableFlag;
	}

	public void setEnableFlag(String enableFlag) {
		this.enableFlag = enableFlag;
	}
	
	private String objStatus;//对象状态，1：增加，0：删除

	public String getObjStatus() {
		return objStatus;
	}

	public void setObjStatus(String objStatus) {
		this.objStatus = objStatus;
	}
	
	private String objType;
	private String hostIp;

	public String getObjType() {
		return objType;
	}

	public void setObjType(String objType) {
		this.objType = objType;
	}

	public String getHostIp() {
		return hostIp;
	}

	public void setHostIp(String hostIp) {
		this.hostIp = hostIp;
	}
	
}