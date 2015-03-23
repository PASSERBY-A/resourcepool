package com.hp.avmon.snmp.mib;

public class MibJvmOS extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jvmOSName;
	private String jvmOSArch;
	private String jvmOSVersion;
	private int jvmOSProcessorCount;

	public String toString() {
		return "jvmOSName=" + this.jvmOSName + "|" + "jvmOSArch="
				+ this.jvmOSArch + "|" + "jvmOSVersion=" + this.jvmOSVersion
				+ "|" + "jvmOSProcessorCount=" + this.jvmOSProcessorCount + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.4.1.42.2.145.3.163.1.1.6";
	}

	public String getJvmOSName() {
		return this.jvmOSName;
	}

	public void setJvmOSName(String paramString) {
		this.jvmOSName = paramString;
	}

	public String getJvmOSArch() {
		return this.jvmOSArch;
	}

	public void setJvmOSArch(String paramString) {
		this.jvmOSArch = paramString;
	}

	public String getJvmOSVersion() {
		return this.jvmOSVersion;
	}

	public void setJvmOSVersion(String paramString) {
		this.jvmOSVersion = paramString;
	}

	public int getJvmOSProcessorCount() {
		return this.jvmOSProcessorCount;
	}

	public void setJvmOSProcessorCount(int paramInt) {
		this.jvmOSProcessorCount = paramInt;
	}
}
