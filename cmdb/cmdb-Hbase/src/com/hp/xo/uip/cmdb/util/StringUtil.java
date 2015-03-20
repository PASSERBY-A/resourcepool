package com.hp.xo.uip.cmdb.util;

import org.apache.commons.codec.binary.StringUtils;

public class StringUtil extends StringUtils{
	public static String null2Empty(String inParam) {
		if(inParam == null || "null".equalsIgnoreCase(inParam)) {
			return "";
		} else {
			return inParam;
		}
	}
	
	public static boolean isNullOrEmpty(String inParam) {
		if(inParam == null || "null".equalsIgnoreCase(inParam) ||
				inParam.length() == 0) 
			return true;
		else
			return false;
	}
	
	public static String newStr(byte[] bytes){
		return new String(bytes);
	}
}
