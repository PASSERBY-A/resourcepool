package com.hp.avmon.snmp.common.snmp;

import java.util.ArrayList;
import java.util.List;

import com.hp.avmon.snmp.mib.Dot1dBasePortEntry;
import com.hp.avmon.snmp.mib.Dot1dTpFdbEntry;
import com.hp.avmon.snmp.mib.MibIPAddrEntry;
import com.hp.avmon.snmp.mib.MibIPRouterEntry;
import com.hp.avmon.snmp.mib.MibIfEntry;
import com.hp.avmon.snmp.mib.MibMacIP;

/**
 *  
 * @author litan
 * 
 */ 
public class DeviceInfo {
	private String deviceIP = "";
	private String deviceMAC = "";
	private String deviceName = "";
	private String deviceProtocol = "";
	private String deviceDesc = "";
	private String deviceSysOID = "";
	private SNMPTarget snmpTarget = null;
	private DeviceTypeInfo deviceType;
	private List<MibIPAddrEntry> ipaddressList = new ArrayList<MibIPAddrEntry>();
	private List<MibIPRouterEntry> routerTableList = new ArrayList<MibIPRouterEntry>();
	private List<MibIfEntry> ifTableList = new ArrayList<MibIfEntry>();
	private List<Dot1dTpFdbEntry> dTpFdbTableList = new ArrayList<Dot1dTpFdbEntry>();
	private List<Dot1dBasePortEntry> basePortList = new ArrayList<Dot1dBasePortEntry>();
	private List<MibMacIP> mibMacIPList = new ArrayList<MibMacIP>();
	private List<String> deviceMacList = new ArrayList<String>();
//	private List<PortInfo> portInfoList = new Vector();
	public static final String DeviceProtocolSNMP = "SNMP";
	public static final String DeviceProtocolPing = "Ping";
	public String getDeviceIP() {
		return deviceIP;
	}
	public void setDeviceIP(String deviceIP) {
		this.deviceIP = deviceIP;
	}
	public String getDeviceMAC() {
		return deviceMAC;
	}
	public void setDeviceMAC(String deviceMAC) {
		this.deviceMAC = deviceMAC;
	}
	public String getDeviceName() {
		return deviceName;
	}
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	public String getDeviceProtocol() {
		return deviceProtocol;
	}
	public void setDeviceProtocol(String deviceProtocol) {
		this.deviceProtocol = deviceProtocol;
	}
	public String getDeviceDesc() {
		return deviceDesc;
	}
	public void setDeviceDesc(String deviceDesc) {
		this.deviceDesc = deviceDesc;
	}
	public String getDeviceSysOID() {
		return deviceSysOID;
	}
	public void setDeviceSysOID(String deviceSysOID) {
		this.deviceSysOID = deviceSysOID;
	}
	public SNMPTarget getSnmpTarget() {
		return snmpTarget;
	}
	public void setSnmpTarget(SNMPTarget snmpTarget) {
		this.snmpTarget = snmpTarget;
	}
	public List<MibIPAddrEntry> getIpaddressList() {
		return ipaddressList;
	}
	public void setIpaddressList(List<MibIPAddrEntry> ipaddressList) {
		this.ipaddressList = ipaddressList;
	}
	public List<MibIPRouterEntry> getRouterTableList() {
		return routerTableList;
	}
	public void setRouterTableList(List<MibIPRouterEntry> routerTableList) {
		this.routerTableList = routerTableList;
	}
	public List<MibIfEntry> getIfTableList() {
		return ifTableList;
	}
	public void setIfTableList(List<MibIfEntry> ifTableList) {
		this.ifTableList = ifTableList;
	}
	public List<Dot1dTpFdbEntry> getdTpFdbTableList() {
		return dTpFdbTableList;
	}
	public void setdTpFdbTableList(List<Dot1dTpFdbEntry> dTpFdbTableList) {
		this.dTpFdbTableList = dTpFdbTableList;
	}
	public List<Dot1dBasePortEntry> getBasePortList() {
		return basePortList;
	}
	public void setBasePortList(List<Dot1dBasePortEntry> basePortList) {
		this.basePortList = basePortList;
	}
	public List<MibMacIP> getMibMacIPList() {
		return mibMacIPList;
	}
	public void setMibMacIPList(List<MibMacIP> mibMacIPList) {
		this.mibMacIPList = mibMacIPList;
	}
	public List<String> getDeviceMacList() {
		return deviceMacList;
	}
	public void setDeviceMacList(List<String> deviceMacList) {
		this.deviceMacList = deviceMacList;
	}
	public static String getDeviceprotocolsnmp() {
		return DeviceProtocolSNMP;
	}
	public static String getDeviceprotocolping() {
		return DeviceProtocolPing;
	}
	public DeviceTypeInfo getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(DeviceTypeInfo deviceType) {
		this.deviceType = deviceType;
	}
}
