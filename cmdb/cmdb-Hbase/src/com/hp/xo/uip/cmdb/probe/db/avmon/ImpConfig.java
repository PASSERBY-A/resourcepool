package com.hp.xo.uip.cmdb.probe.db.avmon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class ImpConfig {
	private String formTypes;
	private String toType;

	// map<kpiName,attName>
	private Map<String, String> attMap = new HashMap<String, String>();

	private List<ImpInsConfig> insConfLi = new ArrayList<ImpInsConfig>();

	public String getFormTypes() {
		return formTypes;
	}

	public void setFormTypes(String formTypes) {
		this.formTypes = formTypes;
	}

	public String getToType() {
		return toType;
	}

	public void setToType(String toType) {
		this.toType = toType;
	}

	public Map<String, String> getAttMap() {
		return attMap;
	}

	public void setAttMap(Map<String, String> attMap) {
		this.attMap = attMap;
	}

	public List<ImpInsConfig> getInsConfLi() {
		return insConfLi;
	}

	public void setInsConfLi(List<ImpInsConfig> insConfLi) {
		this.insConfLi = insConfLi;
	}

	public static void main(String arg[]){
		List<ImpConfig> li=new ArrayList<ImpConfig>();
		for(int i=0;i<2;i++){
		  ImpConfig cc=new ImpConfig();
		  cc.setFormTypes("HOST_HP-UX");
		  cc.setToType("Host");
		  Map<String,String> ma=new HashMap<String,String>();
		  ma.put("","");
		  cc.setAttMap(ma);
		}
		XStream x=new XStream(new DomDriver());
		x.alias("ImpConfig", ImpConfig.class);
		x.alias("ImpInsConfig", ImpInsConfig.class);
		
	}
}
