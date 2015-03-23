package com.hp.avmon.snmp.mib;

public class MibSoftwareRunEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int hrSWRunIndex;
	private String hrSWRunName;
	private String hrSWRunID;
	private String hrSWRunPath;
	private String hrSWRunParameters;
	private int hrSWRunType;
	private int hrSWRunStatus;

	public String toString() {
		return "hrSWRunIndex=" + this.hrSWRunIndex + "|" + "hrSWRunName="
				+ this.hrSWRunName + "|" + "hrSWRunID=" + this.hrSWRunID + "|"
				+ "hrSWRunPath=" + this.hrSWRunPath + "|"
				+ "hrSWRunParameters=" + this.hrSWRunParameters + "|"
				+ "hrSWRunType=" + this.hrSWRunType + "|" + "hrSWRunStatus="
				+ this.hrSWRunStatus + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.25.4.2.1";
	}

	public int getHrSWRunIndex() {
		return this.hrSWRunIndex;
	}

	public void setHrSWRunIndex(int paramInt) {
		this.hrSWRunIndex = paramInt;
	}

	public String getHrSWRunName() {
		return this.hrSWRunName;
	}

	public void setHrSWRunName(String paramString) {
		this.hrSWRunName = paramString;
	}

	public String getHrSWRunID() {
		return this.hrSWRunID;
	}

	public void setHrSWRunID(String paramString) {
		this.hrSWRunID = paramString;
	}

	public String getHrSWRunPath() {
		return this.hrSWRunPath;
	}

	public void setHrSWRunPath(String paramString) {
		this.hrSWRunPath = paramString;
	}

	public String getHrSWRunParameters() {
		return this.hrSWRunParameters;
	}

	public void setHrSWRunParameters(String paramString) {
		this.hrSWRunParameters = paramString;
	}

	public int getHrSWRunType() {
		return this.hrSWRunType;
	}

	public void setHrSWRunType(int paramInt) {
		this.hrSWRunType = paramInt;
	}

	public int getHrSWRunStatus() {
		return this.hrSWRunStatus;
	}

	public void setHrSWRunStatus(int paramInt) {
		this.hrSWRunStatus = paramInt;
	}
}
