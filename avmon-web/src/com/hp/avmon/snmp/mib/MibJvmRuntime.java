package com.hp.avmon.snmp.mib;

public class MibJvmRuntime extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jvmRTName;
	private String jvmRTVMName;
	private String jvmRTVMVendor;
	private String jvmRTVMVersion;
	private String jvmRTSpecName;
	private String jvmRTSpecVendor;
	private String jvmRTSpecVersion;
	private String jvmRTManagementSpecVersion;
	private int jvmRTBootClassPathSupport;
	private int jvmRTInputArgsCount;
	private long jvmRTUptimeMs;
	private long jvmRTStartTimeMs;

	public String toString() {
		return "jvmRTName=" + this.jvmRTName + "|" + "jvmRTVMName="
				+ this.jvmRTVMName + "|" + "jvmRTVMVendor="
				+ this.jvmRTVMVendor + "|" + "jvmRTVMVersion="
				+ this.jvmRTVMVersion + "|" + "jvmRTSpecName="
				+ this.jvmRTSpecName + "|" + "jvmRTSpecVendor="
				+ this.jvmRTSpecVendor + "|" + "jvmRTSpecVersion="
				+ this.jvmRTSpecVersion + "|" + "jvmRTManagementSpecVersion="
				+ this.jvmRTManagementSpecVersion + "|"
				+ "jvmRTBootClassPathSupport=" + this.jvmRTBootClassPathSupport
				+ "|" + "jvmRTInputArgsCount=" + this.jvmRTInputArgsCount + "|"
				+ "jvmRTUptimeMs=" + this.jvmRTUptimeMs + "|"
				+ "jvmRTStartTimeMs=" + this.jvmRTStartTimeMs + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.4.1.42.2.145.3.163.1.1.4";
	}

	public String getJvmRTName() {
		return this.jvmRTName;
	}

	public void setJvmRTName(String paramString) {
		this.jvmRTName = paramString;
	}

	public String getJvmRTVMName() {
		return this.jvmRTVMName;
	}

	public void setJvmRTVMName(String paramString) {
		this.jvmRTVMName = paramString;
	}

	public String getJvmRTVMVendor() {
		return this.jvmRTVMVendor;
	}

	public void setJvmRTVMVendor(String paramString) {
		this.jvmRTVMVendor = paramString;
	}

	public String getJvmRTVMVersion() {
		return this.jvmRTVMVersion;
	}

	public void setJvmRTVMVersion(String paramString) {
		this.jvmRTVMVersion = paramString;
	}

	public String getJvmRTSpecName() {
		return this.jvmRTSpecName;
	}

	public void setJvmRTSpecName(String paramString) {
		this.jvmRTSpecName = paramString;
	}

	public String getJvmRTSpecVendor() {
		return this.jvmRTSpecVendor;
	}

	public void setJvmRTSpecVendor(String paramString) {
		this.jvmRTSpecVendor = paramString;
	}

	public String getJvmRTSpecVersion() {
		return this.jvmRTSpecVersion;
	}

	public void setJvmRTSpecVersion(String paramString) {
		this.jvmRTSpecVersion = paramString;
	}

	public String getJvmRTManagementSpecVersion() {
		return this.jvmRTManagementSpecVersion;
	}

	public void setJvmRTManagementSpecVersion(String paramString) {
		this.jvmRTManagementSpecVersion = paramString;
	}

	public int getJvmRTBootClassPathSupport() {
		return this.jvmRTBootClassPathSupport;
	}

	public void setJvmRTBootClassPathSupport(int paramInt) {
		this.jvmRTBootClassPathSupport = paramInt;
	}

	public int getJvmRTInputArgsCount() {
		return this.jvmRTInputArgsCount;
	}

	public void setJvmRTInputArgsCount(int paramInt) {
		this.jvmRTInputArgsCount = paramInt;
	}

	public long getJvmRTUptimeMs() {
		return this.jvmRTUptimeMs;
	}

	public void setJvmRTUptimeMs(long paramLong) {
		this.jvmRTUptimeMs = paramLong;
	}

	public long getJvmRTStartTimeMs() {
		return this.jvmRTStartTimeMs;
	}

	public void setJvmRTStartTimeMs(long paramLong) {
		this.jvmRTStartTimeMs = paramLong;
	}
}
