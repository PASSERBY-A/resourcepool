package com.hp.avmon.snmp.mib;

public class MibIfEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ifIndex;
	private String ifDescr;
	private int ifType;
	private int ifMtu;
	private long ifSpeed;
	private String ifPhysAddress;
	private int ifAdminStatus;
	private int ifOperStatus;
	private long ifLastChange;
	private long ifInOctets;
	private long ifInUcastPkts;
	private long ifInNUcastPkts;
	private long ifInDiscards;
	private long ifInErrors;
	private long ifInUnknownProtos;
	private long ifOutOctets;
	private long ifOutUcastPkts;
	private long ifOutNUcastPkts;
	private long ifOutDiscards;
	private long ifOutErrors;
	private long ifOutQLen;
	private String ifSpecific;

	public String toString() {
		return "ifIndex=" + this.ifIndex + "|" + "ifDescr=" + this.ifDescr
				+ "|" + "ifType=" + this.ifType + "|" + "ifMtu=" + this.ifMtu
				+ "|" + "ifSpeed=" + this.ifSpeed + "|" + "ifPhysAddress="
				+ this.ifPhysAddress + "|" + "ifAdminStatus="
				+ this.ifAdminStatus + "|" + "ifOperStatus="
				+ this.ifOperStatus + "|" + "ifLastChange=" + this.ifLastChange
				+ "|" + "ifInOctets=" + this.ifInOctets + "|"
				+ "ifInUcastPkts=" + this.ifInUcastPkts + "|"
				+ "ifInNUcastPkts=" + this.ifInNUcastPkts + "|"
				+ "ifInDiscards=" + this.ifInDiscards + "|" + "ifInErrors="
				+ this.ifInErrors + "|" + "ifInUnknownProtos="
				+ this.ifInUnknownProtos + "|" + "ifOutOctets="
				+ this.ifOutOctets + "|" + "ifOutUcastPkts="
				+ this.ifOutUcastPkts + "|" + "ifOutNUcastPkts="
				+ this.ifOutNUcastPkts + "|" + "ifOutDiscards="
				+ this.ifOutDiscards + "|" + "ifOutErrors=" + this.ifOutErrors
				+ "|" + "ifOutQLen=" + this.ifOutQLen + "|" + "ifSpecific="
				+ this.ifSpecific + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.2.2.1";
	}

	public int getIfIndex() {
		return this.ifIndex;
	}

	public void setIfIndex(int paramInt) {
		this.ifIndex = paramInt;
	}

	public String getIfDescr() {
		return this.ifDescr;
	}

	public void setIfDescr(String paramString) {
		this.ifDescr = paramString;
	}

	public int getIfType() {
		return this.ifType;
	}

	public void setIfType(int paramInt) {
		this.ifType = paramInt;
	}

	public int getIfMtu() {
		return this.ifMtu;
	}

	public void setIfMtu(int paramInt) {
		this.ifMtu = paramInt;
	}

	public long getIfSpeed() {
		return this.ifSpeed;
	}

	public void setIfSpeed(long paramLong) {
		this.ifSpeed = paramLong;
	}

	public String getIfPhysAddress() {
		return this.ifPhysAddress;
	}

	public void setIfPhysAddress(String paramString) {
		this.ifPhysAddress = paramString;
	}

	public int getIfAdminStatus() {
		return this.ifAdminStatus;
	}

	public void setIfAdminStatus(int paramInt) {
		this.ifAdminStatus = paramInt;
	}

	public int getIfOperStatus() {
		return this.ifOperStatus;
	}

	public void setIfOperStatus(int paramInt) {
		this.ifOperStatus = paramInt;
	}

	public long getIfLastChange() {
		return this.ifLastChange;
	}

	public void setIfLastChange(long paramLong) {
		this.ifLastChange = paramLong;
	}

	public long getIfInOctets() {
		return this.ifInOctets;
	}

	public void setIfInOctets(long paramLong) {
		this.ifInOctets = paramLong;
	}

	public long getIfInUcastPkts() {
		return this.ifInUcastPkts;
	}

	public void setIfInUcastPkts(long paramLong) {
		this.ifInUcastPkts = paramLong;
	}

	public long getIfInNUcastPkts() {
		return this.ifInNUcastPkts;
	}

	public void setIfInNUcastPkts(long paramLong) {
		this.ifInNUcastPkts = paramLong;
	}

	public long getIfInDiscards() {
		return this.ifInDiscards;
	}

	public void setIfInDiscards(long paramLong) {
		this.ifInDiscards = paramLong;
	}

	public long getIfInErrors() {
		return this.ifInErrors;
	}

	public void setIfInErrors(long paramLong) {
		this.ifInErrors = paramLong;
	}

	public long getIfInUnknownProtos() {
		return this.ifInUnknownProtos;
	}

	public void setIfInUnknownProtos(long paramLong) {
		this.ifInUnknownProtos = paramLong;
	}

	public long getIfOutOctets() {
		return this.ifOutOctets;
	}

	public void setIfOutOctets(long paramLong) {
		this.ifOutOctets = paramLong;
	}

	public long getIfOutUcastPkts() {
		return this.ifOutUcastPkts;
	}

	public void setIfOutUcastPkts(long paramLong) {
		this.ifOutUcastPkts = paramLong;
	}

	public long getIfOutNUcastPkts() {
		return this.ifOutNUcastPkts;
	}

	public void setIfOutNUcastPkts(long paramLong) {
		this.ifOutNUcastPkts = paramLong;
	}

	public long getIfOutDiscards() {
		return this.ifOutDiscards;
	}

	public void setIfOutDiscards(long paramLong) {
		this.ifOutDiscards = paramLong;
	}

	public long getIfOutErrors() {
		return this.ifOutErrors;
	}

	public void setIfOutErrors(long paramLong) {
		this.ifOutErrors = paramLong;
	}

	public long getIfOutQLen() {
		return this.ifOutQLen;
	}

	public void setIfOutQLen(long paramLong) {
		this.ifOutQLen = paramLong;
	}

	public String getIfSpecific() {
		return this.ifSpecific;
	}

	public void setIfSpecific(String paramString) {
		this.ifSpecific = paramString;
	}
}

