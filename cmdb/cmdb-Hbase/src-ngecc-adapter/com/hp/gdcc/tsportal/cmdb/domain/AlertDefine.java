package com.hp.gdcc.tsportal.cmdb.domain;

public class AlertDefine extends SimpleNode {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3972171825699492381L;
	
	public static final int SEV_UNKNOWN = -2;
	public static final int SEV_NORMAL = -1;
	public static final int SEV_INFO = 0;
	public static final int SEV_WARNING = 1;
	public static final int SEV_MINOR = 2;
	public static final int SEV_MAJOR = 3;
	public static final int SEV_CRITICAL  = 4;
	
	public static String getSeverityName(int code) {
		switch(code) {
			case SEV_NORMAL: return "normal";
			case SEV_INFO: return "info";
			case SEV_WARNING: return "warning";
			case SEV_MINOR: return "minor";
			case SEV_MAJOR: return "major";
			case SEV_CRITICAL: return "critical";
			default: return "unknown";
		}
	}
	
	public AlertDefine() {
		
	}
	
	public AlertDefine(SimpleNode sNode) {
		update(sNode);
	}
	
	public void update(SimpleNode sNode) {
		super.update(sNode);
	}
	
	public long getTypeId() {
		return getProperty("typeId", 0L);
	}
	public void setTypeId(long typeId) {
		setProperty("typeId", typeId);
	}
	public String getTypeName() {
		return getProperty("nodeType", (String)null);
	}
	public void setTypeName(String typeName) {
		setProperty("nodeType", typeName);
	}
	public long getAlertCode() {
		return getProperty("alertCode", 0L);
	}
	public void setAlertCode(long alertCode) {
		setProperty("alertCode", alertCode);
	}

	public boolean isEnable() {
		return getProperty("enable", false);
	}
	public void setEnable(boolean enable) {
		setProperty("enable", enable);
	}
	public String getDescription() {
		return getProperty("description", (String)null);
	}
	public void setDescription(String description) {
		setProperty("description", description);
	}
		
	@Override
	public String toString() {
		return "<alertCode:" + getAlertCode() + ">" + 
			"<alertName:" + getName() + ">" +
			"<ref nodeid:" + getNodeId() + ">";
	}
}