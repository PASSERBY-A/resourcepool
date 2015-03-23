package com.hp.avmon.snmp.mib;

public class MibSNMP extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private long snmpInPkts;
	private long snmpOutPkts;
	private long snmpInBadVersions;
	private long snmpInBadCommunityNames;
	private long snmpInBadCommunityUses;
	private long snmpInASNParseErrs;
	private long snmpInTooBigs;
	private long snmpInNoSuchNames;
	private long snmpInBadValues;
	private long snmpInReadOnlys;
	private long snmpInGenErrs;
	private long snmpInTotalReqVars;
	private long snmpInTotalSetVars;
	private long snmpInGetRequests;
	private long snmpInGetNexts;
	private long snmpInSetRequests;
	private long snmpInGetResponses;
	private long snmpInTraps;
	private long snmpOutTooBigs;
	private long snmpOutNoSuchNames;
	private long snmpOutBadValues;
	private long snmpOutGenErrs;
	private long snmpOutGetRequests;
	private long snmpOutGetNexts;
	private long snmpOutSetRequests;
	private long snmpOutGetResponses;
	private long snmpOutTraps;
	private int snmpEnableAuthenTraps;

	public String toString() {
		return "snmpInPkts=" + this.snmpInPkts + "|" + "snmpOutPkts="
				+ this.snmpOutPkts + "|" + "snmpInBadVersions="
				+ this.snmpInBadVersions + "|" + "snmpInBadCommunityNames="
				+ this.snmpInBadCommunityNames + "|"
				+ "snmpInBadCommunityUses=" + this.snmpInBadCommunityUses + "|"
				+ "snmpInASNParseErrs=" + this.snmpInASNParseErrs + "|"
				+ "snmpInTooBigs=" + this.snmpInTooBigs + "|"
				+ "snmpInNoSuchNames=" + this.snmpInNoSuchNames + "|"
				+ "snmpInBadValues=" + this.snmpInBadValues + "|"
				+ "snmpInReadOnlys=" + this.snmpInReadOnlys + "|"
				+ "snmpInGenErrs=" + this.snmpInGenErrs + "|"
				+ "snmpInTotalReqVars=" + this.snmpInTotalReqVars + "|"
				+ "snmpInTotalSetVars=" + this.snmpInTotalSetVars + "|"
				+ "snmpInGetRequests=" + this.snmpInGetRequests + "|"
				+ "snmpInGetNexts=" + this.snmpInGetNexts + "|"
				+ "snmpInSetRequests=" + this.snmpInSetRequests + "|"
				+ "snmpInGetResponses=" + this.snmpInGetResponses + "|"
				+ "snmpInTraps=" + this.snmpInTraps + "|" + "snmpOutTooBigs="
				+ this.snmpOutTooBigs + "|" + "snmpOutNoSuchNames="
				+ this.snmpOutNoSuchNames + "|" + "snmpOutBadValues="
				+ this.snmpOutBadValues + "|" + "snmpOutGenErrs="
				+ this.snmpOutGenErrs + "|" + "snmpOutGetRequests="
				+ this.snmpOutGetRequests + "|" + "snmpOutGetNexts="
				+ this.snmpOutGetNexts + "|" + "snmpOutSetRequests="
				+ this.snmpOutSetRequests + "|" + "snmpOutGetResponses="
				+ this.snmpOutGetResponses + "|" + "snmpOutTraps="
				+ this.snmpOutTraps + "|" + "snmpEnableAuthenTraps="
				+ this.snmpEnableAuthenTraps + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.11";
	}

	public long getSnmpInPkts() {
		return this.snmpInPkts;
	}

	public void setSnmpInPkts(long paramLong) {
		this.snmpInPkts = paramLong;
	}

	public long getSnmpOutPkts() {
		return this.snmpOutPkts;
	}

	public void setSnmpOutPkts(long paramLong) {
		this.snmpOutPkts = paramLong;
	}

	public long getSnmpInBadVersions() {
		return this.snmpInBadVersions;
	}

	public void setSnmpInBadVersions(long paramLong) {
		this.snmpInBadVersions = paramLong;
	}

	public long getSnmpInBadCommunityNames() {
		return this.snmpInBadCommunityNames;
	}

	public void setSnmpInBadCommunityNames(long paramLong) {
		this.snmpInBadCommunityNames = paramLong;
	}

	public long getSnmpInBadCommunityUses() {
		return this.snmpInBadCommunityUses;
	}

	public void setSnmpInBadCommunityUses(long paramLong) {
		this.snmpInBadCommunityUses = paramLong;
	}

	public long getSnmpInASNParseErrs() {
		return this.snmpInASNParseErrs;
	}

	public void setSnmpInASNParseErrs(long paramLong) {
		this.snmpInASNParseErrs = paramLong;
	}

	public long getSnmpInTooBigs() {
		return this.snmpInTooBigs;
	}

	public void setSnmpInTooBigs(long paramLong) {
		this.snmpInTooBigs = paramLong;
	}

	public long getSnmpInNoSuchNames() {
		return this.snmpInNoSuchNames;
	}

	public void setSnmpInNoSuchNames(long paramLong) {
		this.snmpInNoSuchNames = paramLong;
	}

	public long getSnmpInBadValues() {
		return this.snmpInBadValues;
	}

	public void setSnmpInBadValues(long paramLong) {
		this.snmpInBadValues = paramLong;
	}

	public long getSnmpInReadOnlys() {
		return this.snmpInReadOnlys;
	}

	public void setSnmpInReadOnlys(long paramLong) {
		this.snmpInReadOnlys = paramLong;
	}

	public long getSnmpInGenErrs() {
		return this.snmpInGenErrs;
	}

	public void setSnmpInGenErrs(long paramLong) {
		this.snmpInGenErrs = paramLong;
	}

	public long getSnmpInTotalReqVars() {
		return this.snmpInTotalReqVars;
	}

	public void setSnmpInTotalReqVars(long paramLong) {
		this.snmpInTotalReqVars = paramLong;
	}

	public long getSnmpInTotalSetVars() {
		return this.snmpInTotalSetVars;
	}

	public void setSnmpInTotalSetVars(long paramLong) {
		this.snmpInTotalSetVars = paramLong;
	}

	public long getSnmpInGetRequests() {
		return this.snmpInGetRequests;
	}

	public void setSnmpInGetRequests(long paramLong) {
		this.snmpInGetRequests = paramLong;
	}

	public long getSnmpInGetNexts() {
		return this.snmpInGetNexts;
	}

	public void setSnmpInGetNexts(long paramLong) {
		this.snmpInGetNexts = paramLong;
	}

	public long getSnmpInSetRequests() {
		return this.snmpInSetRequests;
	}

	public void setSnmpInSetRequests(long paramLong) {
		this.snmpInSetRequests = paramLong;
	}

	public long getSnmpInGetResponses() {
		return this.snmpInGetResponses;
	}

	public void setSnmpInGetResponses(long paramLong) {
		this.snmpInGetResponses = paramLong;
	}

	public long getSnmpInTraps() {
		return this.snmpInTraps;
	}

	public void setSnmpInTraps(long paramLong) {
		this.snmpInTraps = paramLong;
	}

	public long getSnmpOutTooBigs() {
		return this.snmpOutTooBigs;
	}

	public void setSnmpOutTooBigs(long paramLong) {
		this.snmpOutTooBigs = paramLong;
	}

	public long getSnmpOutNoSuchNames() {
		return this.snmpOutNoSuchNames;
	}

	public void setSnmpOutNoSuchNames(long paramLong) {
		this.snmpOutNoSuchNames = paramLong;
	}

	public long getSnmpOutBadValues() {
		return this.snmpOutBadValues;
	}

	public void setSnmpOutBadValues(long paramLong) {
		this.snmpOutBadValues = paramLong;
	}

	public long getSnmpOutGenErrs() {
		return this.snmpOutGenErrs;
	}

	public void setSnmpOutGenErrs(long paramLong) {
		this.snmpOutGenErrs = paramLong;
	}

	public long getSnmpOutGetRequests() {
		return this.snmpOutGetRequests;
	}

	public void setSnmpOutGetRequests(long paramLong) {
		this.snmpOutGetRequests = paramLong;
	}

	public long getSnmpOutGetNexts() {
		return this.snmpOutGetNexts;
	}

	public void setSnmpOutGetNexts(long paramLong) {
		this.snmpOutGetNexts = paramLong;
	}

	public long getSnmpOutSetRequests() {
		return this.snmpOutSetRequests;
	}

	public void setSnmpOutSetRequests(long paramLong) {
		this.snmpOutSetRequests = paramLong;
	}

	public long getSnmpOutGetResponses() {
		return this.snmpOutGetResponses;
	}

	public void setSnmpOutGetResponses(long paramLong) {
		this.snmpOutGetResponses = paramLong;
	}

	public long getSnmpOutTraps() {
		return this.snmpOutTraps;
	}

	public void setSnmpOutTraps(long paramLong) {
		this.snmpOutTraps = paramLong;
	}

	public int getSnmpEnableAuthenTraps() {
		return this.snmpEnableAuthenTraps;
	}

	public void setSnmpEnableAuthenTraps(int paramInt) {
		this.snmpEnableAuthenTraps = paramInt;
	}
}
