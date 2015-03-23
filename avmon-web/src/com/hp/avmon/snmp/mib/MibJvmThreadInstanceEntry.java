package com.hp.avmon.snmp.mib;

public class MibJvmThreadInstanceEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String jvmThreadInstIndex;
	private long jvmThreadInstId;
	private String jvmThreadInstState;
	private long jvmThreadInstBlockCount;
	private long jvmThreadInstBlockTimeMs;
	private long jvmThreadInstWaitCount;
	private long jvmThreadInstWaitTimeMs;
	private long jvmThreadInstCpuTimeNs;
	private String jvmThreadInstName;
	private String jvmThreadInstLockName;
	private String jvmThreadInstLockOwnerPtr;

	public String toString() {
		return "jvmThreadInstIndex=" + this.jvmThreadInstIndex + "|"
				+ "jvmThreadInstId=" + this.jvmThreadInstId + "|"
				+ "jvmThreadInstState=" + this.jvmThreadInstState + "|"
				+ "jvmThreadInstBlockCount=" + this.jvmThreadInstBlockCount
				+ "|" + "jvmThreadInstBlockTimeMs="
				+ this.jvmThreadInstBlockTimeMs + "|"
				+ "jvmThreadInstWaitCount=" + this.jvmThreadInstWaitCount + "|"
				+ "jvmThreadInstWaitTimeMs=" + this.jvmThreadInstWaitTimeMs
				+ "|" + "jvmThreadInstCpuTimeNs=" + this.jvmThreadInstCpuTimeNs
				+ "|" + "jvmThreadInstName=" + this.jvmThreadInstName + "|"
				+ "jvmThreadInstLockName=" + this.jvmThreadInstLockName + "|"
				+ "jvmThreadInstLockOwnerPtr=" + this.jvmThreadInstLockOwnerPtr
				+ "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.4.1.42.2.145.3.163.1.1.3.10.1";
	}

	public String getJvmThreadInstIndex() {
		return this.jvmThreadInstIndex;
	}

	public void setJvmThreadInstIndex(String paramString) {
		this.jvmThreadInstIndex = paramString;
	}

	public long getJvmThreadInstId() {
		return this.jvmThreadInstId;
	}

	public void setJvmThreadInstId(long paramLong) {
		this.jvmThreadInstId = paramLong;
	}

	public String getJvmThreadInstState() {
		return this.jvmThreadInstState;
	}

	public void setJvmThreadInstState(String paramString) {
		this.jvmThreadInstState = paramString;
	}

	public long getJvmThreadInstBlockCount() {
		return this.jvmThreadInstBlockCount;
	}

	public void setJvmThreadInstBlockCount(long paramLong) {
		this.jvmThreadInstBlockCount = paramLong;
	}

	public long getJvmThreadInstBlockTimeMs() {
		return this.jvmThreadInstBlockTimeMs;
	}

	public void setJvmThreadInstBlockTimeMs(long paramLong) {
		this.jvmThreadInstBlockTimeMs = paramLong;
	}

	public long getJvmThreadInstWaitCount() {
		return this.jvmThreadInstWaitCount;
	}

	public void setJvmThreadInstWaitCount(long paramLong) {
		this.jvmThreadInstWaitCount = paramLong;
	}

	public long getJvmThreadInstWaitTimeMs() {
		return this.jvmThreadInstWaitTimeMs;
	}

	public void setJvmThreadInstWaitTimeMs(long paramLong) {
		this.jvmThreadInstWaitTimeMs = paramLong;
	}

	public long getJvmThreadInstCpuTimeNs() {
		return this.jvmThreadInstCpuTimeNs;
	}

	public void setJvmThreadInstCpuTimeNs(long paramLong) {
		this.jvmThreadInstCpuTimeNs = paramLong;
	}

	public String getJvmThreadInstName() {
		return this.jvmThreadInstName;
	}

	public void setJvmThreadInstName(String paramString) {
		this.jvmThreadInstName = paramString;
	}

	public String getJvmThreadInstLockName() {
		return this.jvmThreadInstLockName;
	}

	public void setJvmThreadInstLockName(String paramString) {
		this.jvmThreadInstLockName = paramString;
	}

	public String getJvmThreadInstLockOwnerPtr() {
		return this.jvmThreadInstLockOwnerPtr;
	}

	public void setJvmThreadInstLockOwnerPtr(String paramString) {
		this.jvmThreadInstLockOwnerPtr = paramString;
	}
}
