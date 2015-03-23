package com.hp.avmon.snmp.mib;

public class MibDiskAndMemoEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hrStorageIndex;
	private String hrStorageType;
	private String hrStorageDescr;
	private int hrStorageAllocationUnits;
	private int hrStorageSize;
	private int hrStorageUsed;
	private long hrStorageAllocationFailures;

	public String toString() {
		return "hrStorageIndex=" + this.hrStorageIndex + "|" + "hrStorageType="
				+ this.hrStorageType + "|" + "hrStorageDescr="
				+ this.hrStorageDescr + "|" + "hrStorageAllocationUnits="
				+ this.hrStorageAllocationUnits + "|" + "hrStorageSize="
				+ this.hrStorageSize + "|" + "hrStorageUsed="
				+ this.hrStorageUsed + "|" + "hrStorageAllocationFailures="
				+ this.hrStorageAllocationFailures + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.25.2.3.1";
	}

	public int getHrStorageIndex() {
		return this.hrStorageIndex;
	}

	public void setHrStorageIndex(int paramInt) {
		this.hrStorageIndex = paramInt;
	}

	public String getHrStorageType() {
		return this.hrStorageType;
	}

	public void setHrStorageType(String paramString) {
		this.hrStorageType = paramString;
	}

	public String getHrStorageDescr() {
		return this.hrStorageDescr;
	}

	public void setHrStorageDescr(String paramString) {
		this.hrStorageDescr = paramString;
	}

	public int getHrStorageAllocationUnits() {
		return this.hrStorageAllocationUnits;
	}

	public void setHrStorageAllocationUnits(int paramInt) {
		this.hrStorageAllocationUnits = paramInt;
	}

	public int getHrStorageSize() {
		return this.hrStorageSize;
	}

	public void setHrStorageSize(int paramInt) {
		this.hrStorageSize = paramInt;
	}

	public int getHrStorageUsed() {
		return this.hrStorageUsed;
	}

	public void setHrStorageUsed(int paramInt) {
		this.hrStorageUsed = paramInt;
	}

	public long getHrStorageAllocationFailures() {
		return this.hrStorageAllocationFailures;
	}

	public void setHrStorageAllocationFailures(long paramLong) {
		this.hrStorageAllocationFailures = paramLong;
	}
}
