package com.hp.avmon.snmp.mib;

public class MibJvmMemory extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long jvmMemoryPendingFinalCount;
	private int jvmMemoryGCVerboseLevel;
	private int jvmMemoryGCCall;
	private long jvmMemoryHeapInitSize;
	private long jvmMemoryHeapUsed;
	private long jvmMemoryHeapCommitted;
	private long jvmMemoryHeapMaxSize;
	private long jvmMemoryNonHeapInitSize;
	private long jvmMemoryNonHeapUsed;
	private long jvmMemoryNonHeapCommitted;
	private long jvmMemoryNonHeapMaxSize;

	public String toString() {
		return "jvmMemoryPendingFinalCount=" + this.jvmMemoryPendingFinalCount
				+ "|" + "jvmMemoryGCVerboseLevel="
				+ this.jvmMemoryGCVerboseLevel + "|" + "jvmMemoryGCCall="
				+ this.jvmMemoryGCCall + "|" + "jvmMemoryHeapInitSize="
				+ this.jvmMemoryHeapInitSize + "|" + "jvmMemoryHeapUsed="
				+ this.jvmMemoryHeapUsed + "|" + "jvmMemoryHeapCommitted="
				+ this.jvmMemoryHeapCommitted + "|" + "jvmMemoryHeapMaxSize="
				+ this.jvmMemoryHeapMaxSize + "|" + "jvmMemoryNonHeapInitSize="
				+ this.jvmMemoryNonHeapInitSize + "|" + "jvmMemoryNonHeapUsed="
				+ this.jvmMemoryNonHeapUsed + "|"
				+ "jvmMemoryNonHeapCommitted=" + this.jvmMemoryNonHeapCommitted
				+ "|" + "jvmMemoryNonHeapMaxSize="
				+ this.jvmMemoryNonHeapMaxSize + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.4.1.42.2.145.3.163.1.1.2";
	}

	public long getJvmMemoryPendingFinalCount() {
		return this.jvmMemoryPendingFinalCount;
	}

	public void setJvmMemoryPendingFinalCount(long paramLong) {
		this.jvmMemoryPendingFinalCount = paramLong;
	}

	public int getJvmMemoryGCVerboseLevel() {
		return this.jvmMemoryGCVerboseLevel;
	}

	public void setJvmMemoryGCVerboseLevel(int paramInt) {
		this.jvmMemoryGCVerboseLevel = paramInt;
	}

	public int getJvmMemoryGCCall() {
		return this.jvmMemoryGCCall;
	}

	public void setJvmMemoryGCCall(int paramInt) {
		this.jvmMemoryGCCall = paramInt;
	}

	public long getJvmMemoryHeapInitSize() {
		return this.jvmMemoryHeapInitSize;
	}

	public void setJvmMemoryHeapInitSize(long paramLong) {
		this.jvmMemoryHeapInitSize = paramLong;
	}

	public long getJvmMemoryHeapUsed() {
		return this.jvmMemoryHeapUsed;
	}

	public void setJvmMemoryHeapUsed(long paramLong) {
		this.jvmMemoryHeapUsed = paramLong;
	}

	public long getJvmMemoryHeapCommitted() {
		return this.jvmMemoryHeapCommitted;
	}

	public void setJvmMemoryHeapCommitted(long paramLong) {
		this.jvmMemoryHeapCommitted = paramLong;
	}

	public long getJvmMemoryHeapMaxSize() {
		return this.jvmMemoryHeapMaxSize;
	}

	public void setJvmMemoryHeapMaxSize(long paramLong) {
		this.jvmMemoryHeapMaxSize = paramLong;
	}

	public long getJvmMemoryNonHeapInitSize() {
		return this.jvmMemoryNonHeapInitSize;
	}

	public void setJvmMemoryNonHeapInitSize(long paramLong) {
		this.jvmMemoryNonHeapInitSize = paramLong;
	}

	public long getJvmMemoryNonHeapUsed() {
		return this.jvmMemoryNonHeapUsed;
	}

	public void setJvmMemoryNonHeapUsed(long paramLong) {
		this.jvmMemoryNonHeapUsed = paramLong;
	}

	public long getJvmMemoryNonHeapCommitted() {
		return this.jvmMemoryNonHeapCommitted;
	}

	public void setJvmMemoryNonHeapCommitted(long paramLong) {
		this.jvmMemoryNonHeapCommitted = paramLong;
	}

	public long getJvmMemoryNonHeapMaxSize() {
		return this.jvmMemoryNonHeapMaxSize;
	}

	public void setJvmMemoryNonHeapMaxSize(long paramLong) {
		this.jvmMemoryNonHeapMaxSize = paramLong;
	}
}

