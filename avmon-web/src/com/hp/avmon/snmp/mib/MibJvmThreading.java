package com.hp.avmon.snmp.mib;

public class MibJvmThreading extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long jvmThreadCount;
	private long jvmThreadDaemonCount;
	private long jvmThreadPeakCount;
	private long jvmThreadTotalStartedCount;
	private int jvmThreadContentionMonitoring;
	private int jvmThreadCpuTimeMonitoring;
	private long jvmThreadPeakCountReset;

	public String toString() {
		return "jvmThreadCount=" + this.jvmThreadCount + "|"
				+ "jvmThreadDaemonCount=" + this.jvmThreadDaemonCount + "|"
				+ "jvmThreadPeakCount=" + this.jvmThreadPeakCount + "|"
				+ "jvmThreadTotalStartedCount="
				+ this.jvmThreadTotalStartedCount + "|"
				+ "jvmThreadContentionMonitoring="
				+ this.jvmThreadContentionMonitoring + "|"
				+ "jvmThreadCpuTimeMonitoring="
				+ this.jvmThreadCpuTimeMonitoring + "|"
				+ "jvmThreadPeakCountReset=" + this.jvmThreadPeakCountReset
				+ "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.4.1.42.2.145.3.163.1.1.3";
	}

	public long getJvmThreadCount() {
		return this.jvmThreadCount;
	}

	public void setJvmThreadCount(long paramLong) {
		this.jvmThreadCount = paramLong;
	}

	public long getJvmThreadDaemonCount() {
		return this.jvmThreadDaemonCount;
	}

	public void setJvmThreadDaemonCount(long paramLong) {
		this.jvmThreadDaemonCount = paramLong;
	}

	public long getJvmThreadPeakCount() {
		return this.jvmThreadPeakCount;
	}

	public void setJvmThreadPeakCount(long paramLong) {
		this.jvmThreadPeakCount = paramLong;
	}

	public long getJvmThreadTotalStartedCount() {
		return this.jvmThreadTotalStartedCount;
	}

	public void setJvmThreadTotalStartedCount(long paramLong) {
		this.jvmThreadTotalStartedCount = paramLong;
	}

	public int getJvmThreadContentionMonitoring() {
		return this.jvmThreadContentionMonitoring;
	}

	public void setJvmThreadContentionMonitoring(int paramInt) {
		this.jvmThreadContentionMonitoring = paramInt;
	}

	public int getJvmThreadCpuTimeMonitoring() {
		return this.jvmThreadCpuTimeMonitoring;
	}

	public void setJvmThreadCpuTimeMonitoring(int paramInt) {
		this.jvmThreadCpuTimeMonitoring = paramInt;
	}

	public long getJvmThreadPeakCountReset() {
		return this.jvmThreadPeakCountReset;
	}

	public void setJvmThreadPeakCountReset(long paramLong) {
		this.jvmThreadPeakCountReset = paramLong;
	}
}
