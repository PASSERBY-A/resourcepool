package com.hp.avmon.snmp.mib;

/**
 * 
 * @author litan
 * 
 */
public class MibTCPConnEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int tcpConnState;
	private String tcpConnLocalAddress;
	private int tcpConnLocalPort;
	private String tcpConnRemAddress;
	private int tcpConnRemPort;

	public String toString() {
		return "tcpConnState=" + this.tcpConnState + "|"
				+ "tcpConnLocalAddress=" + this.tcpConnLocalAddress + "|"
				+ "tcpConnLocalPort=" + this.tcpConnLocalPort + "|"
				+ "tcpConnRemAddress=" + this.tcpConnRemAddress + "|"
				+ "tcpConnRemPort=" + this.tcpConnRemPort + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.6.13.1";
	}

	public int getTcpConnState() {
		return this.tcpConnState;
	}

	public void setTcpConnState(int paramInt) {
		this.tcpConnState = paramInt;
	}

	public String getTcpConnLocalAddress() {
		return this.tcpConnLocalAddress;
	}

	public void setTcpConnLocalAddress(String paramString) {
		this.tcpConnLocalAddress = paramString;
	}

	public int getTcpConnLocalPort() {
		return this.tcpConnLocalPort;
	}

	public void setTcpConnLocalPort(int paramInt) {
		this.tcpConnLocalPort = paramInt;
	}

	public String getTcpConnRemAddress() {
		return this.tcpConnRemAddress;
	}

	public void setTcpConnRemAddress(String paramString) {
		this.tcpConnRemAddress = paramString;
	}

	public int getTcpConnRemPort() {
		return this.tcpConnRemPort;
	}

	public void setTcpConnRemPort(int paramInt) {
		this.tcpConnRemPort = paramInt;
	}
}
