package com.hp.avmon.snmp.mib;

public class MibDiskInfoEntry extends OMMappingInfo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String diskName;
	private String diskType;
	private int sectorPerCluster;
	private int bytesPerSector;
	private int totalSpaceinBytes;
	private int freeSpaceinBytes;

	public String toString() {
		return "diskName=" + this.diskName + "|" + "diskType=" + this.diskType
				+ "|" + "sectorPerCluster=" + this.sectorPerCluster + "|"
				+ "bytesPerSector=" + this.bytesPerSector + "|"
				+ "totalSpaceinBytes=" + this.totalSpaceinBytes + "|"
				+ "freeSpaceinBytes=" + this.freeSpaceinBytes + "|";
	}

	public String getMappingOID() {
		return "1.3.6.1.4.1.2162.2.1.1";
	}

	public String getDiskName() {
		return this.diskName;
	}

	public void setDiskName(String paramString) {
		this.diskName = paramString;
	}

	public String getDiskType() {
		return this.diskType;
	}

	public void setDiskType(String paramString) {
		this.diskType = paramString;
	}

	public int getSectorPerCluster() {
		return this.sectorPerCluster;
	}

	public void setSectorPerCluster(int paramInt) {
		this.sectorPerCluster = paramInt;
	}

	public int getBytesPerSector() {
		return this.bytesPerSector;
	}

	public void setBytesPerSector(int paramInt) {
		this.bytesPerSector = paramInt;
	}

	public int getTotalSpaceinBytes() {
		return this.totalSpaceinBytes;
	}

	public void setTotalSpaceinBytes(int paramInt) {
		this.totalSpaceinBytes = paramInt;
	}

	public int getFreeSpaceinBytes() {
		return this.freeSpaceinBytes;
	}

	public void setFreeSpaceinBytes(int paramInt) {
		this.freeSpaceinBytes = paramInt;
	}
}
