package com.hp.avmon.snmp.mib;

public class MibDeviceEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hrDeviceIndex;
	private String hrDeviceType;
	private String hrDeviceDescr;
	private String hrDeviceID;
	private int hrDeviceStatus;
	private long hrDeviceErrors;

	public String toString() {
		return "hrDeviceIndex=" + this.hrDeviceIndex + "|" + "hrDeviceType="
				+ this.hrDeviceType + "|" + "hrDeviceDescr="
				+ this.hrDeviceDescr + "|" + "hrDeviceID=" + this.hrDeviceID
				+ "|" + "hrDeviceStatus=" + this.hrDeviceStatus + "|"
				+ "hrDeviceErrors=" + this.hrDeviceErrors + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.25.3.2.1";
	}

	public int getHrDeviceIndex() {
		return this.hrDeviceIndex;
	}

	public void setHrDeviceIndex(int paramInt) {
		this.hrDeviceIndex = paramInt;
	}

	public String getHrDeviceType() {
		return this.hrDeviceType;
	}

	public void setHrDeviceType(String paramString) {
		this.hrDeviceType = paramString;
	}

	public String getHrDeviceDescr() {
		return this.hrDeviceDescr;
	}

	public void setHrDeviceDescr(String paramString) {
		this.hrDeviceDescr = paramString;
	}

	public String getHrDeviceID() {
		return this.hrDeviceID;
	}

	public void setHrDeviceID(String paramString) {
		this.hrDeviceID = paramString;
	}

	public int getHrDeviceStatus() {
		return this.hrDeviceStatus;
	}

	public void setHrDeviceStatus(int paramInt) {
		this.hrDeviceStatus = paramInt;
	}

	public long getHrDeviceErrors() {
		return this.hrDeviceErrors;
	}

	public void setHrDeviceErrors(long paramLong) {
		this.hrDeviceErrors = paramLong;
	}
}
