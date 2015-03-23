package com.hp.avmon.snmp.mib;

public class MibSoftwareInstallEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hrSWInstalledIndex;
	private String hrSWInstalledName;
	private String hrSWInstalledID;
	private int hrSWInstalledType;
	private String hrSWInstalledDate;

	public String toString() {
		return "hrSWInstalledIndex=" + this.hrSWInstalledIndex + "|"
				+ "hrSWInstalledName=" + this.hrSWInstalledName + "|"
				+ "hrSWInstalledID=" + this.hrSWInstalledID + "|"
				+ "hrSWInstalledType=" + this.hrSWInstalledType + "|"
				+ "hrSWInstalledDate=" + this.hrSWInstalledDate + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.25.6.3.1";
	}

	public int getHrSWInstalledIndex() {
		return this.hrSWInstalledIndex;
	}

	public void setHrSWInstalledIndex(int paramInt) {
		this.hrSWInstalledIndex = paramInt;
	}

	public String getHrSWInstalledName() {
		return this.hrSWInstalledName;
	}

	public void setHrSWInstalledName(String paramString) {
		this.hrSWInstalledName = paramString;
	}

	public String getHrSWInstalledID() {
		return this.hrSWInstalledID;
	}

	public void setHrSWInstalledID(String paramString) {
		this.hrSWInstalledID = paramString;
	}

	public int getHrSWInstalledType() {
		return this.hrSWInstalledType;
	}

	public void setHrSWInstalledType(int paramInt) {
		this.hrSWInstalledType = paramInt;
	}

	public String getHrSWInstalledDate() {
		return this.hrSWInstalledDate;
	}

	public void setHrSWInstalledDate(String paramString) {
		this.hrSWInstalledDate = paramString;
	}
}
