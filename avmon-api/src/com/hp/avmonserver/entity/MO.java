package com.hp.avmonserver.entity;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class MO implements Serializable{
    
    private static final long serialVersionUID = -7478677254514008843L;
    
    private String moId;
    private String agentId;
    private String caption;
    private String type;
    private String parentId;
    private String description;
    private String protocalMethod;


	private Map attr;

    
    
    public String getProtocalMethod() {
		return protocalMethod;
	}
	public void setProtocalMethod(String protocalMethod) {
		this.protocalMethod = protocalMethod;
	}
    public String getAgentId() {
        return agentId;
    }
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    public String getMoId() {
        return moId;
    }
    public void setMoId(String moId) {
        this.moId = moId;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    
    public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Map getAttr(){
        return attr;
    }
    
    public String getAttr(String attrName) {
        if(attr==null) return "";
        
        return (String) attr.get(attrName);
    }
    
    public void setAttr(String attrName,String attrValue) {
        if(attr==null){
            attr=new HashMap();
        }
        attr.put(attrName, attrValue);
        
    }
    
    public String toString(){
    	return String.format("moId=%s,type=%s,caption=%s,protocalMethod=%s",moId,type,caption,protocalMethod);
    }
}
