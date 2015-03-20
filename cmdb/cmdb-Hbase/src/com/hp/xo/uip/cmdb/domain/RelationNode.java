package com.hp.xo.uip.cmdb.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * 关系节点，关系节点包括关系的源、目的节点信息
 *
 */
public class RelationNode extends ConfigItem implements Cloneable{

	private static final long serialVersionUID = 5340870299961164384L;
	private boolean isRelation=true;
	/**
	 * 当 isType=true时
	 * 源ci类型名称
	 */
	private String sourceCiTypeNames;
	/**
	 * 当 isType=true时
	 * 目标ci类型名称
	 */
	private String targetCiTypeNames;
	
	/**
	 * 源ID 
	 */
	private long sourceId;
	/**
	 * 源名称  (查询获得属性)
	 */
	private String sourceName;
	private String sourceLable;
	/**
	 * 源节点类名称
	 */
	private String sourceType;
	
	/**
	 * 目标ID
	 */
	private long targetId;
	
	/**
	 * 目标名称 (查询获得属性)
	 */
	private String targetName;
	private String targetLable;
	/**
	 * 目标节点类名称
	 */
	private String targetType;

	public boolean isRelation() {
		return isRelation;
	}

	public void setRelation(boolean isRelation) {
		this.isRelation = isRelation;
	}

	public long getSourceId() {
		return sourceId;
	}

	public void setSourceId(long sourceId) {
		this.sourceId = sourceId;
	}

	public long getTargetId() {
		return targetId;
	}

	public void setTargetId(long targetId) {
		this.targetId = targetId;
	}

	public String getSourceCiTypeNames() {
		return sourceCiTypeNames;
	}

	public void setSourceCiTypeNames(String sourceCiTypeNames) {
		this.sourceCiTypeNames = sourceCiTypeNames;
	}

	public String getTargetCiTypeNames() {
		return targetCiTypeNames;
	}

	public void setTargetCiTypeNames(String targetCiTypeNames) {
		this.targetCiTypeNames = targetCiTypeNames;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceLable() {
		return sourceLable;
	}

	public void setSourceLable(String sourceLable) {
		this.sourceLable = sourceLable;
	}

	public String getTargetName() {
		return targetName;
	}

	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}

	public String getTargetLable() {
		return targetLable;
	}

	public void setTargetLable(String targetLable) {
		this.targetLable = targetLable;
	}
	
	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getTargetType() {
		return targetType;
	}

	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	public Map<String,String> getMapData(){
		Map<String,String> ma=new HashMap<String,String>();
		ma=getSysMapData();
		ma.put("sourceType", sourceType);
		ma.put("sourceLable", sourceLable);
		ma.put("sourceName", sourceName);
		ma.put("sourceId",String.valueOf(sourceId));
		ma.put("targetId", String.valueOf(targetId));
		ma.put("targetName", targetName);
		ma.put("targetLable", targetLable);
		ma.put("targetType", targetType);
		ma.put("sourceCiTypeNames", sourceCiTypeNames);
		ma.put("targetCiTypeNames", targetCiTypeNames);
		ma.put("isRelation", String.valueOf(isRelation));
		return ma; 
	}
    public RelationNode clone(){
    	RelationNode r=null;
    	try {
			r= (RelationNode) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return r;
    }
    public int hashCode(){
    	return (id+name).hashCode();
    }
}
