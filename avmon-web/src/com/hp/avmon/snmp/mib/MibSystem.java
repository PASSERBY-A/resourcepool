package com.hp.avmon.snmp.mib;

public class MibSystem extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sysDescr;
	private String sysObjectID;
	private long sysUpTime;
	private String sysContact;
	private String sysName;
	private String sysLocation;
	private int sysServices;

	public String toString() {
		return "sysDescr=" + this.sysDescr + "|" + "sysObjectID="
				+ this.sysObjectID + "|" + "sysUpTime=" + this.sysUpTime + "|"
				+ "sysContact=" + this.sysContact + "|" + "sysName="
				+ this.sysName + "|" + "sysLocation=" + this.sysLocation + "|"
				+ "sysServices=" + this.sysServices + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.1";
	}

	public String getSysDescr() {
		return this.sysDescr;
	}

	public void setSysDescr(String paramString) {
		this.sysDescr = paramString;
	}

	public String getSysObjectID() {
		return this.sysObjectID;
	}

	public void setSysObjectID(String paramString) {
		this.sysObjectID = paramString;
	}

	public long getSysUpTime() {
		return this.sysUpTime;
	}

	public void setSysUpTime(long paramLong) {
		this.sysUpTime = paramLong;
	}

	public String getSysContact() {
		return this.sysContact;
	}

	public void setSysContact(String paramString) {
		this.sysContact = paramString;
	}

	public String getSysName() {
		return this.sysName;
	}

	public void setSysName(String paramString) {
		this.sysName = paramString;
	}

	public String getSysLocation() {
		return this.sysLocation;
	}

	public void setSysLocation(String paramString) {
		this.sysLocation = paramString;
	}

	public int getSysServices() {
		return this.sysServices;
	}

	public void setSysServices(int paramInt) {
		this.sysServices = paramInt;
	}
}
