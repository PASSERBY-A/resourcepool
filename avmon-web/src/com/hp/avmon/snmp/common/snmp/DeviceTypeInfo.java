package com.hp.avmon.snmp.common.snmp;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * �豸������Ϣ
 * @author litan
 *
 */
public class DeviceTypeInfo {
	
	private String id;
	private String vender;
	private String family;
	private String type;
	private String moTypeId;
	private List<String> sysObjectID = new ArrayList<String>();
	private List<String> sysDescr = new ArrayList<String>();
	
	private String logicType = "";
	
	public static final String  isRouterType = "router";
	public static final String  isSwitchType = "switch";
	public static final String  isRouterSwitchType = "routerswitch";
	public static final String  isComputerType = "computer";
	public static final String  unkownType = "unkown";
	
	public static final DeviceTypeInfo UnkownTypeInfo = new DeviceTypeInfo();
	
	static{
		UnkownTypeInfo.setId(UUID.randomUUID().toString().replace("-", ""));
		UnkownTypeInfo.setType(unkownType);
		UnkownTypeInfo.setLogicType(unkownType);
	}

	public String toString() {
		return this.type;
	}
	

	public boolean equals(Object object) {
		if ((object instanceof DeviceTypeInfo)) {
			DeviceTypeInfo deviceTypeInfo = (DeviceTypeInfo) object;
			if (getType().equals(
					deviceTypeInfo.getType()))
				return true;
		}
		return false;
	}

	public boolean equalsLogicType(String logicType) {
		if (getLogicType().equalsIgnoreCase(logicType))
			return true;
		return false;
	}

	public int hashCode() {
		return this.type.hashCode();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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
	
	public void setLogicType(String logicType) {
		this.logicType = logicType;
	}

	public String getLogicType() {
		if(this.getFamily()!=null && this.getFamily().indexOf("Router")>-1){
			return isRouterType;
		}
		if(this.getFamily()!=null && this.getFamily().indexOf("Switch")>-1){//Switches
			return isSwitchType;
		}
		return logicType;
	}

	public List<String> getSysObjectID() {
		return sysObjectID;
	}

	public void setSysObjectID(List<String> sysObjectID) {
		this.sysObjectID = sysObjectID;
	}

	public List<String> getSysDescr() {
		return sysDescr;
	}

	public void setSysDescr(List<String> sysDescr) {
		this.sysDescr = sysDescr;
	}


	public String getMoTypeId() {
		return moTypeId;
	}


	public void setMoTypeId(String moTypeId) {
		this.moTypeId = moTypeId;
	}
	
}
