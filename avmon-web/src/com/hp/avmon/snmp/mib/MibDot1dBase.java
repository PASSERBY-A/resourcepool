package com.hp.avmon.snmp.mib;

public class MibDot1dBase extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dot1dBaseBridgeAddress;
	private int dot1dBaseNumPorts;
	private int dot1dBaseType;

	public String toString() {
		return "dot1dBaseBridgeAddress=" + this.dot1dBaseBridgeAddress + "|"
				+ "dot1dBaseNumPorts=" + this.dot1dBaseNumPorts + "|"
				+ "dot1dBaseType=" + this.dot1dBaseType + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.17.1";
	}

	public String getDot1dBaseBridgeAddress() {
		return this.dot1dBaseBridgeAddress;
	}

	public void setDot1dBaseBridgeAddress(String paramString) {
		this.dot1dBaseBridgeAddress = paramString;
	}

	public int getDot1dBaseNumPorts() {
		return this.dot1dBaseNumPorts;
	}

	public void setDot1dBaseNumPorts(int paramInt) {
		this.dot1dBaseNumPorts = paramInt;
	}

	public int getDot1dBaseType() {
		return this.dot1dBaseType;
	}

	public void setDot1dBaseType(int paramInt) {
		this.dot1dBaseType = paramInt;
	}
}
