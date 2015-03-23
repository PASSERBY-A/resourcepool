package com.hp.avmon.snmp.mib;

public class Dot1dBasePortEntry extends OMMappingInfo {

	private static final long serialVersionUID = 1L;
	
	
	private int dot1dBasePort;
	private int dot1dBasePortIfIndex;
	private String dot1dBasePortCircuit;
	private long dot1dBasePortDelayExceededDiscards;
	private long dot1dBasePortMtuExceededDiscards;

	public String getMappingOID() {
		return "1.3.6.1.2.1.17.1.4.1";
	}

	public int getDot1dBasePort() {
		return this.dot1dBasePort;
	}

	public void setDot1dBasePort(int paramInt) {
		this.dot1dBasePort = paramInt;
	}

	public int getDot1dBasePortIfIndex() {
		return this.dot1dBasePortIfIndex;
	}

	public void setDot1dBasePortIfIndex(int paramInt) {
		this.dot1dBasePortIfIndex = paramInt;
	}

	public String getDot1dBasePortCircuit() {
		return this.dot1dBasePortCircuit;
	}

	public void setDot1dBasePortCircuit(String paramString) {
		this.dot1dBasePortCircuit = paramString;
	}

	public long getDot1dBasePortDelayExceededDiscards() {
		return this.dot1dBasePortDelayExceededDiscards;
	}

	public void setDot1dBasePortDelayExceededDiscards(long paramLong) {
		this.dot1dBasePortDelayExceededDiscards = paramLong;
	}

	public long getDot1dBasePortMtuExceededDiscards() {
		return this.dot1dBasePortMtuExceededDiscards;
	}

	public void setDot1dBasePortMtuExceededDiscards(long paramLong) {
		this.dot1dBasePortMtuExceededDiscards = paramLong;
	}
	
	public String toString() {
		return "dot1dBasePort=" + this.dot1dBasePort + "|"
				+ "dot1dBasePortIfIndex=" + this.dot1dBasePortIfIndex + "|"
				+ "dot1dBasePortCircuit=" + this.dot1dBasePortCircuit + "|"
				+ "dot1dBasePortDelayExceededDiscards="
				+ this.dot1dBasePortDelayExceededDiscards + "|"
				+ "dot1dBasePortMtuExceededDiscards="
				+ this.dot1dBasePortMtuExceededDiscards + "|";
	}
}
