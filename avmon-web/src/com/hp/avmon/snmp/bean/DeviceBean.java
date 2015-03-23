package com.hp.avmon.snmp.bean;

public class DeviceBean{
	private String deviceId;
	private String ip;
	private String deviceName;
	private String protocol;
	private String status;
	private String vender;
	private String family;
	private String type;
	private String moTypeId;
	private String desc;
	
	public DeviceBean(String deviceId, String ip, String deviceName,
			String desc, String protocol, String status, String vender,
			String family, String type, String moTypeId) {
		super();
		this.deviceId = deviceId;
		this.ip = ip;
		this.deviceName = deviceName;
		this.desc = desc;
		this.protocol = protocol;
		this.status = status;
		this.vender = vender;
		this.family = family;
		this.type = type;
		this.moTypeId = moTypeId;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	public String getVender() {
		return vender;
	}
	public void setVender(String vender) {
		this.vender = vender;
	}
	public String getFamily() {
		return family;
	}
	public void setFamily(String family) {
		this.family = family;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMoTypeId() {
		return moTypeId;
	}
	public void setMoTypeId(String moTypeId) {
		this.moTypeId = moTypeId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
}
