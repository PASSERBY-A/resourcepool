package com.hp.gdcc.tsportal.cmdb.domain;

public class CiUpdate implements CiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6369982487984875396L;

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
		return "update ciid:" + ciId + ", ci:" + ci;
	}
}
