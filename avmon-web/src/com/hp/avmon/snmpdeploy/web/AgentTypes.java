package com.hp.avmon.snmpdeploy.web;

import java.util.HashMap;

public class AgentTypes {
	public static final String AGENT_TREE_ROOT_ID = "agentTree";
	public static final String AGENT_MANAGEMENT = String.valueOf("Agent管理".hashCode());
	public static final String AGENT_DOWNLOAD = String.valueOf("Agent下载".hashCode());
	public static final String AMP_UPDATE = String.valueOf("AMP更新".hashCode());
	public static final String AGENT_UPDATE = String.valueOf("Agent更新".hashCode());
	public static final String HOST_PING = "HOST_PING";
	
	public static final String MO_HOST_PARENT_ID = "HOST";
	
	public static HashMap<String, String> BIZMAP;  
	  
    static {  
    	BIZMAP = new HashMap<String, String>();  
    	BIZMAP.put(String.valueOf("网管门户".hashCode()),"网管门户");  
    	BIZMAP.put(String.valueOf("管控平台".hashCode()),"管控平台");
    	BIZMAP.put(String.valueOf("话务网管".hashCode()),"话务网管");
    	BIZMAP.put(String.valueOf("数据网管".hashCode()),"数据网管");
    	BIZMAP.put(String.valueOf("综合分析".hashCode()),"综合分析");
    	BIZMAP.put(String.valueOf("PBOSS".hashCode()),"PBOSS");
    	BIZMAP.put(String.valueOf("EOMS".hashCode()),"EOMS");
    	BIZMAP.put(String.valueOf("维护支撑".hashCode()),"维护支撑");
    	BIZMAP.put(String.valueOf("投诉平台".hashCode()),"投诉平台");
    	BIZMAP.put(String.valueOf("SOC".hashCode()),"SOC");
    	BIZMAP.put(String.valueOf("传输网管".hashCode()),"传输网管");
    	BIZMAP.put(String.valueOf("南方基地".hashCode()),"南方基地");
    	BIZMAP.put(String.valueOf("综合资管".hashCode()),"综合资管");
    	BIZMAP.put(String.valueOf("测试机".hashCode()),"测试机");
    	BIZMAP.put(String.valueOf("综合资源".hashCode()),"综合资源");
    	BIZMAP.put(String.valueOf("其他".hashCode()),"其他");
    	
    }  
	
}
