package com.hp.avmon.snmp.mib;

public class MibOspfStubAreaEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String ospfStubAreaId;
	private int ospfStubTOS;
	private int ospfStubMetric;
	private int ospfStubStatus;
	private int ospfStubMetricType;

	public String toString() {
		return "ospfStubAreaId=" + this.ospfStubAreaId + "|" + "ospfStubTOS="
				+ this.ospfStubTOS + "|" + "ospfStubMetric="
				+ this.ospfStubMetric + "|" + "ospfStubStatus="
				+ this.ospfStubStatus + "|" + "ospfStubMetricType="
				+ this.ospfStubMetricType + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.14.3.1";
	}

	public String getOspfStubAreaId() {
		return this.ospfStubAreaId;
	}

	public void setOspfStubAreaId(String paramString) {
		this.ospfStubAreaId = paramString;
	}

	public int getOspfStubTOS() {
		return this.ospfStubTOS;
	}

	public void setOspfStubTOS(int paramInt) {
		this.ospfStubTOS = paramInt;
	}

	public int getOspfStubMetric() {
		return this.ospfStubMetric;
	}

	public void setOspfStubMetric(int paramInt) {
		this.ospfStubMetric = paramInt;
	}

	public int getOspfStubStatus() {
		return this.ospfStubStatus;
	}

	public void setOspfStubStatus(int paramInt) {
		this.ospfStubStatus = paramInt;
	}

	public int getOspfStubMetricType() {
		return this.ospfStubMetricType;
	}

	public void setOspfStubMetricType(int paramInt) {
		this.ospfStubMetricType = paramInt;
	}
}
