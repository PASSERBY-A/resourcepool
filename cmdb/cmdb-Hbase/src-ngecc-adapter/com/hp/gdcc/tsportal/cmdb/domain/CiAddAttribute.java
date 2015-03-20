package com.hp.gdcc.tsportal.cmdb.domain;

public class CiAddAttribute implements CiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8247846241134735089L;
	
	private long ciId;
	private CiAttribute attribute;
	
	public long getCiId() {
		return ciId;
	}

	public void setCiId(long ciId) {
		this.ciId = ciId;
	}

	public CiAttribute getAttribute() {
		return attribute;
	}

	public void setAttribute(CiAttribute attribute) {
		this.attribute = attribute;
	}
	
	public String toString() {
		return "add attribute ciid:" + ciId + " attribute:" + attribute;
	}
}
