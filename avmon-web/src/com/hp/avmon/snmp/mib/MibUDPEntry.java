package com.hp.avmon.snmp.mib;

/**
 * 
 * @author litan
 * 
 */
public class MibUDPEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String udpLocalAddress;
	private int udpLocalPort;

	public String toString() {
		return "udpLocalAddress=" + this.udpLocalAddress + "|"
				+ "udpLocalPort=" + this.udpLocalPort + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.7.5.1";
	}

	public String getUdpLocalAddress() {
		return this.udpLocalAddress;
	}

	public void setUdpLocalAddress(String paramString) {
		this.udpLocalAddress = paramString;
	}

	public int getUdpLocalPort() {
		return this.udpLocalPort;
	}

	public void setUdpLocalPort(int paramInt) {
		this.udpLocalPort = paramInt;
	}
}
