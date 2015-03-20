package com.hp.xo.uip.cmdb.domain;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


/**
 * 
 */
public class Node extends ConfigItem implements Cloneable{
	
	private static final long serialVersionUID = 8385926419603814619L;
	
	@Override
	public boolean isRelation() {
		return false;
	}
	/**
	 * 属性集 <attName,ciAttribute>
	 */
	private Map<String, CiAttribute> attributes = 
		new LinkedHashMap<String, CiAttribute>();
   
	public Map<String, CiAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, CiAttribute> attributes) {
		this.attributes = attributes;
	}


	public Map<String,String> getMapData(){
		Map<String,String> ma=new LinkedHashMap<String,String>();
		ma=getSysMapData();
		Iterator<String> ite=attributes.keySet().iterator();
		
		while(ite.hasNext()){
			String name=ite.next();
			ma.put(name,String.valueOf(attributes.get(name).getValue()));
		}
		return ma;
	}
	public LinkedHashMap<String,String> getMeta(){
		LinkedHashMap<String,String> ma=new LinkedHashMap<String,String>();
		ma=getSysMeta();
		Iterator<String> ite=attributes.keySet().iterator();
		
		while(ite.hasNext()){
			String name=ite.next();
			if(attributes.get(name).getViewMode()!=3){
			 ma.put(name.toLowerCase(),String.valueOf(attributes.get(name).getLabel()));
			}
		}
		return ma;
	}	
    
	public Node clone() throws CloneNotSupportedException  
    {  
     Node cloned = (Node) super.clone();  
     cloned.createTime = (Timestamp) createTime.clone();
     cloned.updateTime = (Timestamp) updateTime.clone();
     cloned.setAttributes(new LinkedHashMap<String,CiAttribute>());
     if(attributes!=null){
        Collection<CiAttribute> cs= attributes.values();        
        Object cas[]=cs.toArray();
        Arrays.sort(cas);
        for(Object cc:cas){
        	CiAttribute ca=(CiAttribute)cc;
        	cloned.getAttributes().put(ca.getName(), ca.clone());
        }
     }
     return cloned;  
    }  
	public static void main(String arg[]){
		boolean b=true;
		System.out.println("---["+b);
	}

    public int hashCode(){
    	return (id+name).hashCode();
    }
    public void sortAtt(){
    	Map<String, CiAttribute> re = 
			new LinkedHashMap<String, CiAttribute>();
		Collection<CiAttribute> cs= attributes.values();        
	    Object cas[]=cs.toArray();
	    Arrays.sort(cas);
	    for(Object cc:cas){
	     	CiAttribute ca=(CiAttribute)cc;
	     	re.put(ca.getName(), ca.clone());
	    }
	    this.attributes=re;    
    }
}