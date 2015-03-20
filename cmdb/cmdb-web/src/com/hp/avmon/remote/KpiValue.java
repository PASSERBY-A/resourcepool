package com.hp.avmon.remote;

import java.util.Date;

public class KpiValue {
	public String id;
	public long createTime;
	public String description;
	public String hostName;
	public String instance; 
    public String ip;
    public String kpiCode;
    public String kpiName;    
    public String kpiLabel;
    public String unit;
    public Date kpiTime; 
	public String moId;
	public String nodeName;
	public String nodeType;//类型
    public String value="";
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}

	public long getCreateTime() {
		return createTime;
	}
	public void setCreateTime(long createTime) {
		this.createTime = createTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	public String getInstance() {
		return instance;
	}
	public void setInstance(String instance) {
		this.instance = instance;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getKpiCode() {
		return kpiCode;
	}
	public void setKpiCode(String kpiCode) {
		this.kpiCode = kpiCode;
	}
	public Date getKpiTime() {
		return kpiTime;
	}
	public void setKpiTime(Date kpiTime) {
		this.kpiTime = kpiTime;
	}
	public String getMoId() {
		return moId;
	}
	public void setMoId(String moId) {
		this.moId = moId;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getKpiName() {
		return kpiName;
	}
	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}
	public String getKpiLabel() {
		return kpiLabel;
	}
	public void setKpiLabel(String kpiLabel) {
		this.kpiLabel = kpiLabel;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
        
}
