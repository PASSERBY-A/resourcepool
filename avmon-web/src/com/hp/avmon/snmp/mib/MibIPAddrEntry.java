package com.hp.avmon.snmp.mib;

public class MibIPAddrEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ipAdEntAddr;
	private int ipAdEntIfIndex;
	private String ipAdEntNetMask;
	private String ipAdEntBcastAddr;
	private int ipAdEntReasmMaxSize;

	public String toString() {
		return "ipAdEntAddr=" + this.ipAdEntAddr + "|" + "ipAdEntIfIndex="
				+ this.ipAdEntIfIndex + "|" + "ipAdEntNetMask="
				+ this.ipAdEntNetMask + "|" + "ipAdEntBcastAddr="
				+ this.ipAdEntBcastAddr + "|" + "ipAdEntReasmMaxSize="
				+ this.ipAdEntReasmMaxSize + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.4.20.1";
	}

	public String getIpAdEntAddr() {
		return this.ipAdEntAddr;
	}

	public void setIpAdEntAddr(String paramString) {
		this.ipAdEntAddr = paramString;
	}

	public int getIpAdEntIfIndex() {
		return this.ipAdEntIfIndex;
	}

	public void setIpAdEntIfIndex(int paramInt) {
		this.ipAdEntIfIndex = paramInt;
	}

	public String getIpAdEntNetMask() {
		return this.ipAdEntNetMask;
	}

	public void setIpAdEntNetMask(String paramString) {
		this.ipAdEntNetMask = paramString;
	}

	public String getIpAdEntBcastAddr() {
		return this.ipAdEntBcastAddr;
	}

	public void setIpAdEntBcastAddr(String paramString) {
		this.ipAdEntBcastAddr = paramString;
	}

	public int getIpAdEntReasmMaxSize() {
		return this.ipAdEntReasmMaxSize;
	}

	public void setIpAdEntReasmMaxSize(int paramInt) {
		this.ipAdEntReasmMaxSize = paramInt;
	}
}
