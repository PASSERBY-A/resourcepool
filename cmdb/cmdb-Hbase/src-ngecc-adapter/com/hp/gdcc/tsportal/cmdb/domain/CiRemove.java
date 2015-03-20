package com.hp.gdcc.tsportal.cmdb.domain;

public class CiRemove implements CiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5978512653269776175L;

	private long ciId;
	
	private ConfigItem ci;
	
	public ConfigItem getCi() {
		return ci;
	}

	public void setCi(ConfigItem ci) {
		this.ci = ci;
	}

	public long getCiId() {
		return ciId;
	}

	public void setCiId(long ciId) {
		this.ciId = ciId;
	}
	
	public String toString() {
		return "remove ciid:" + ciId;
	}
}
