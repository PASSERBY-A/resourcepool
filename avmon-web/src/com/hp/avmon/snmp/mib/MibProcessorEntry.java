package com.hp.avmon.snmp.mib;

public class MibProcessorEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hrDeviceIndex;
	private String hrProcessorFrwID;
	private int hrProcessorLoad;

	public String toString() {
		return "hrDeviceIndex=" + this.hrDeviceIndex + "|"
				+ "hrProcessorFrwID=" + this.hrProcessorFrwID + "|"
				+ "hrProcessorLoad=" + this.hrProcessorLoad + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.25.3.3.1";
	}

	public String getHrProcessorFrwID() {
		return this.hrProcessorFrwID;
	}

	public void setHrProcessorFrwID(String paramString) {
		this.hrProcessorFrwID = paramString;
	}

	public int getHrProcessorLoad() {
		return this.hrProcessorLoad;
	}

	public void setHrProcessorLoad(int paramInt) {
		this.hrProcessorLoad = paramInt;
	}

	public void setHrDeviceIndex(int paramInt) {
		this.hrDeviceIndex = paramInt;
	}

	public int getHrDeviceIndex() {
		return this.hrDeviceIndex;
	}
}