package com.hp.avmon.snmp.common.snmp;

public class SNMPTarget {
	public String nodeIP = "127.0.0.1";
	public String readCommunity = "public";
	public String writeCommunity = "private";
	public int port = 161;
	public static final int VERSION1 = 0;
	public static final int VERSION2C = 1;
	public static final int VERSION3 = 3;
	public int snmpVersion = 1;
	
	
	public String getNodeIP() {
		return nodeIP;
	}
	public void setNodeIP(String nodeIP) {
		this.nodeIP = nodeIP;
	}
	public String getReadCommunity() {
		return readCommunity;
	}
	public void setReadCommunity(String readCommunity) {
		this.readCommunity = readCommunity;
	}
	public String getWriteCommunity() {
		return writeCommunity;
	}
	public void setWriteCommunity(String writeCommunity) {
		this.writeCommunity = writeCommunity;
	}
	
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	public int getSnmpVersion() {
		return snmpVersion;
	}
	public void setSnmpVersion(int snmpVersion) {
		this.snmpVersion = snmpVersion;
	}
	
	
}
