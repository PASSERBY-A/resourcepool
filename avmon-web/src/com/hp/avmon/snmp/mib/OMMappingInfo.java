package com.hp.avmon.snmp.mib;

import java.io.Serializable;

/**
 * @author litan
 *
 */
public abstract class OMMappingInfo  implements Serializable{
	
	private String nodeName = "";
	private String dbKey = "";
	private String tableIndexOID = "";

	public String getDbKey() {
		return this.dbKey;
	}

	public void setDbKey(String paramString) {
		this.dbKey = paramString;
	}

	public String genKey() {
		return "";
	}

	public abstract String getMappingOID();

	public String getNodeName() {
		return this.nodeName;
	}

	public void setNodeName(String paramString) {
		this.nodeName = paramString;
	}

	public void setTableIndexOID(String paramString) {
		this.tableIndexOID = paramString;
	}

	public String getTableIndexOID() {
		return this.tableIndexOID;
	}
}
