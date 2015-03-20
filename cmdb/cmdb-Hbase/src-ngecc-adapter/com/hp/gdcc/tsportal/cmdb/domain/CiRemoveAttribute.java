package com.hp.gdcc.tsportal.cmdb.domain;

public class CiRemoveAttribute implements CiAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2599844856239152815L;
	
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
		return "remove attribute ciid:" + ciId + " attribute:" + attribute;
	}
}
