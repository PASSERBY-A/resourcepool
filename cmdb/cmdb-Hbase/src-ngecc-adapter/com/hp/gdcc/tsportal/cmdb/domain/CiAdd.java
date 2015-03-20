package com.hp.gdcc.tsportal.cmdb.domain;

public class CiAdd implements CiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1414533253140036938L;

	private long ciId;
	private ConfigItem ci;
	
	public long getCiId() {
		return ciId;
	}

	public void setCiId(long ciId) {
		this.ciId = ciId;
	}

	public ConfigItem getCi() {
		return ci;
	}

	public void setCi(ConfigItem ci) {
		this.ci = ci;
	}
	
	public String toString() {
		return "add ciid:" + ciId + ", ci:" + ci;
	}
}
