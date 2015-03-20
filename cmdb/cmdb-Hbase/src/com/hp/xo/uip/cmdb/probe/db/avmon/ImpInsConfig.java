package com.hp.xo.uip.cmdb.probe.db.avmon;

import java.util.HashMap;
import java.util.Map;

public class ImpInsConfig {	
	/**
	 * 0:toValue instance拼字符串作value 1:toCi instance做CI
	 */
	private int insOpt = 0;
	private String instanceType;
	//map<kpiName,attName>
	private Map<String,String> insAttMap=new HashMap<String,String>();
	//map<kpiName,ImpInsConfig> instance级联
	private Map<String,ImpInsConfig> cascadeIns=new HashMap<String,ImpInsConfig>(); 
	public String getInstanceType() {
		return instanceType;
	}
	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}
	public Map<String, String> getInsAttMap() {
		return insAttMap;
	}
	public void setInsAttMap(Map<String, String> insAttMap) {
		this.insAttMap = insAttMap;
	}
	public int getInsOpt() {
		return insOpt;
	}
	public void setInsOpt(int insOpt) {
		this.insOpt = insOpt;
	}
    
}
