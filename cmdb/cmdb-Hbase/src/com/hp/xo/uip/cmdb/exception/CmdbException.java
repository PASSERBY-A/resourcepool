package com.hp.xo.uip.cmdb.exception;

public class CmdbException extends Exception{

	public CmdbException(Exception e) {
		super(e);
	}

	public CmdbException(String str) {
        super(new Exception(str));
	}

	private static final long serialVersionUID = -777633405530505499L;

}
