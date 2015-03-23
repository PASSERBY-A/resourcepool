package com.hp.avmon.snmp.mib;

public class MibIP extends OMMappingInfo {

	private static final long serialVersionUID = 1L;
	private int ipForwarding;
	private int ipDefaultTTL;
	private long ipInReceives;
	private long ipInHdrErrors;
	private long ipInAddrErrors;
	private long ipForwDatagrams;
	private long ipInUnknownProtos;
	private long ipInDiscards;
	private long ipInDelivers;
	private long ipOutRequests;
	private long ipOutDiscards;
	private long ipOutNoRoutes;
	private int ipReasmTimeout;
	private long ipReasmReqds;
	private long ipReasmOKs;
	private long ipReasmFails;
	private long ipFragOKs;
	private long ipFragFails;
	private long ipFragCreates;
	private long ipRoutingDiscards;

	public String toString() {
		return "ipForwarding=" + this.ipForwarding + "|" + "ipDefaultTTL="
				+ this.ipDefaultTTL + "|" + "ipInReceives=" + this.ipInReceives
				+ "|" + "ipInHdrErrors=" + this.ipInHdrErrors + "|"
				+ "ipInAddrErrors=" + this.ipInAddrErrors + "|"
				+ "ipForwDatagrams=" + this.ipForwDatagrams + "|"
				+ "ipInUnknownProtos=" + this.ipInUnknownProtos + "|"
				+ "ipInDiscards=" + this.ipInDiscards + "|" + "ipInDelivers="
				+ this.ipInDelivers + "|" + "ipOutRequests="
				+ this.ipOutRequests + "|" + "ipOutDiscards="
				+ this.ipOutDiscards + "|" + "ipOutNoRoutes="
				+ this.ipOutNoRoutes + "|" + "ipReasmTimeout="
				+ this.ipReasmTimeout + "|" + "ipReasmReqds="
				+ this.ipReasmReqds + "|" + "ipReasmOKs=" + this.ipReasmOKs
				+ "|" + "ipReasmFails=" + this.ipReasmFails + "|"
				+ "ipFragOKs=" + this.ipFragOKs + "|" + "ipFragFails="
				+ this.ipFragFails + "|" + "ipFragCreates="
				+ this.ipFragCreates + "|" + "ipRoutingDiscards="
				+ this.ipRoutingDiscards + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.4";
	}

	public int getIpForwarding() {
		return this.ipForwarding;
	}

	public void setIpForwarding(int paramInt) {
		this.ipForwarding = paramInt;
	}

	public int getIpDefaultTTL() {
		return this.ipDefaultTTL;
	}

	public void setIpDefaultTTL(int paramInt) {
		this.ipDefaultTTL = paramInt;
	}

	public long getIpInReceives() {
		return this.ipInReceives;
	}

	public void setIpInReceives(long paramLong) {
		this.ipInReceives = paramLong;
	}

	public long getIpInHdrErrors() {
		return this.ipInHdrErrors;
	}

	public void setIpInHdrErrors(long paramLong) {
		this.ipInHdrErrors = paramLong;
	}

	public long getIpInAddrErrors() {
		return this.ipInAddrErrors;
	}

	public void setIpInAddrErrors(long paramLong) {
		this.ipInAddrErrors = paramLong;
	}

	public long getIpForwDatagrams() {
		return this.ipForwDatagrams;
	}

	public void setIpForwDatagrams(long paramLong) {
		this.ipForwDatagrams = paramLong;
	}

	public long getIpInUnknownProtos() {
		return this.ipInUnknownProtos;
	}

	public void setIpInUnknownProtos(long paramLong) {
		this.ipInUnknownProtos = paramLong;
	}

	public long getIpInDiscards() {
		return this.ipInDiscards;
	}

	public void setIpInDiscards(long paramLong) {
		this.ipInDiscards = paramLong;
	}

	public long getIpInDelivers() {
		return this.ipInDelivers;
	}

	public void setIpInDelivers(long paramLong) {
		this.ipInDelivers = paramLong;
	}

	public long getIpOutRequests() {
		return this.ipOutRequests;
	}

	public void setIpOutRequests(long paramLong) {
		this.ipOutRequests = paramLong;
	}

	public long getIpOutDiscards() {
		return this.ipOutDiscards;
	}

	public void setIpOutDiscards(long paramLong) {
		this.ipOutDiscards = paramLong;
	}

	public long getIpOutNoRoutes() {
		return this.ipOutNoRoutes;
	}

	public void setIpOutNoRoutes(long paramLong) {
		this.ipOutNoRoutes = paramLong;
	}

	public int getIpReasmTimeout() {
		return this.ipReasmTimeout;
	}

	public void setIpReasmTimeout(int paramInt) {
		this.ipReasmTimeout = paramInt;
	}

	public long getIpReasmReqds() {
		return this.ipReasmReqds;
	}

	public void setIpReasmReqds(long paramLong) {
		this.ipReasmReqds = paramLong;
	}

	public long getIpReasmOKs() {
		return this.ipReasmOKs;
	}

	public void setIpReasmOKs(long paramLong) {
		this.ipReasmOKs = paramLong;
	}

	public long getIpReasmFails() {
		return this.ipReasmFails;
	}

	public void setIpReasmFails(long paramLong) {
		this.ipReasmFails = paramLong;
	}

	public long getIpFragOKs() {
		return this.ipFragOKs;
	}

	public void setIpFragOKs(long paramLong) {
		this.ipFragOKs = paramLong;
	}

	public long getIpFragFails() {
		return this.ipFragFails;
	}

	public void setIpFragFails(long paramLong) {
		this.ipFragFails = paramLong;
	}

	public long getIpFragCreates() {
		return this.ipFragCreates;
	}

	public void setIpFragCreates(long paramLong) {
		this.ipFragCreates = paramLong;
	}

	public long getIpRoutingDiscards() {
		return this.ipRoutingDiscards;
	}

	public void setIpRoutingDiscards(long paramLong) {
		this.ipRoutingDiscards = paramLong;
	}
}
