package com.hp.gdcc.tsportal.cmdb.domain;

public class CiUpdateAttribute implements CiAction {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2327541087013110126L;
	private long ciId;
	private CiAttribute attribute;
	private CiAttribute newAttribute;
	
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
	
	public CiAttribute getNewAttribute() {
		return newAttribute;
	}

	public void setNewAttribute(CiAttribute newAttribute) {
		this.newAttribute = newAttribute;
	}
	
	public String toString() {
		return "update attribute ciid:" + ciId + " attribute:" + attribute;
	}
}
