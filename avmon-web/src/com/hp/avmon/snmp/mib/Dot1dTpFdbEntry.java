package com.hp.avmon.snmp.mib;

public class Dot1dTpFdbEntry extends OMMappingInfo {
	private static final long serialVersionUID = 1L;

	private String dot1dTpFdbAddress;
	private int dot1dTpFdbPort;
	private int dot1dTpFdbStatus;

	public String toString() {
		return "dot1dTpFdbAddress=" + this.dot1dTpFdbAddress + "|"
				+ "dot1dTpFdbPort=" + this.dot1dTpFdbPort;
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.17.4.3.1";
	}

	public String getDot1dTpFdbAddress() {
		return this.dot1dTpFdbAddress;
	}

	public void setDot1dTpFdbAddress(String paramString) {
		this.dot1dTpFdbAddress = paramString;
	}

	public int getDot1dTpFdbPort() {
		return this.dot1dTpFdbPort;
	}

	public void setDot1dTpFdbPort(int paramInt) {
		this.dot1dTpFdbPort = paramInt;
	}

	public int getDot1dTpFdbStatus() {
		return this.dot1dTpFdbStatus;
	}

	public void setDot1dTpFdbStatus(int paramInt) {
		this.dot1dTpFdbStatus = paramInt;
	}
}
