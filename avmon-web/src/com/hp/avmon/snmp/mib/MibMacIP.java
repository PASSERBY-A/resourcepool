package com.hp.avmon.snmp.mib;

public class MibMacIP extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int ipNetToMediaIfIndex;
	private String ipNetToMediaPhysAddress;
	private String ipNetToMediaNetAddress;
	
	
	/**
	 * other :1
	 * invalid : 2
	 * dynamic : 3
	 * static : 4
	 */
	private int ipNetToMediaType;

	public String toString() {
		return "MAC地址:" + this.ipNetToMediaPhysAddress + ",IP地址:"
				+ this.ipNetToMediaNetAddress;
	}

	public String getMappingOID() {
		return "1.3.6.1.2.1.4.22.1";
	}

	public int getIpNetToMediaIfIndex() {
		return this.ipNetToMediaIfIndex;
	}

	public void setIpNetToMediaIfIndex(int paramInt) {
		this.ipNetToMediaIfIndex = paramInt;
	}

	public String getIpNetToMediaPhysAddress() {
		return this.ipNetToMediaPhysAddress;
	}

	public void setIpNetToMediaPhysAddress(String paramString) {
		this.ipNetToMediaPhysAddress = paramString;
	}

	public String getIpNetToMediaNetAddress() {
		return this.ipNetToMediaNetAddress;
	}

	public void setIpNetToMediaNetAddress(String paramString) {
		this.ipNetToMediaNetAddress = paramString;
	}

	public int getIpNetToMediaType() {
		return this.ipNetToMediaType;
	}

	public void setIpNetToMediaType(int paramInt) {
		this.ipNetToMediaType = paramInt;
	}
}
