package com.hp.xo.uip.cmdb.domain;

import java.io.Serializable;

public class CiAttribute implements Cloneable,Serializable,Comparable{
	
	private static final long serialVersionUID = 6006503935823521002L;
	/**
	 * 所属CI ID
	 */
	private long ciId;
	/**
	 * 属性 name（英文）
	 */
	private String name;
	/**
	 * 属性显示名称（中文）
	 */
	private String label;
	/**
	 * 数据类型
	 */
	private String dataType;

	/**
	 * 更新模式
	 * 0:不限制    1：只手动   2：只自动 
	 */
	private int updateMode=0;
	
	/**
	 * 属性值
	 */
	private String value;
	
	/**
	 * 属性组
	 */
	private String attGroup;
	
	/**
	 * 排序标识
	 */
	private int order=0;
	
	/**
	 * 默认值
	 */
	private String defValue;
	
	/**
	 * 对应对象
	 */
	//private String relateObjType;
	//private String relateObjAtt;
	
	
	/**
	 * 0:不限制，1：界面只读, 2:界面不可见 ,3:已删除（历史属性）
	 */
	private int viewMode=0;
	
	/**
	 * 必填选项
	 */
	private boolean isRequired; 
	
	/**
	 * 记录变更
	 */
	private boolean recordChange;
	
    /**
     * 预留属性		
     */
	private String reserved1 = "";
	private String reserved2 = "";
	private String reserved3 = "";
	private String reserved4 = "";
	public long getCiId() {
		return ciId;
	}
	public void setCiId(long ciId) {
		this.ciId = ciId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		if(name!=null)
		this.name = name.toLowerCase();
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	public int getUpdateMode() {
		return updateMode;
	}
	public void setUpdateMode(int updateMode) {
		this.updateMode = updateMode;
	}
	
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getReserved1() {
		return reserved1;
	}
	public void setReserved1(String reserved1) {
		this.reserved1 = reserved1;
	}
	public String getReserved2() {
		return reserved2;
	}
	public void setReserved2(String reserved2) {
		this.reserved2 = reserved2;
	}
	public String getReserved3() {
		return reserved3;
	}
	public void setReserved3(String reserved3) {
		this.reserved3 = reserved3;
	}
	public String getReserved4() {
		return reserved4;
	}
	public void setReserved4(String reserved4) {
		this.reserved4 = reserved4;
	}
	public String getAttGroup() {
		return attGroup;
	}
	public void setAttGroup(String attGroup) {
		this.attGroup = attGroup;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	public String getDefValue() {
		return defValue;
	}
	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}
	public int getViewMode() {
		return viewMode;
	}
	public void setViewMode(int viewMode) {
		this.viewMode = viewMode;
	}
	public boolean isRequired() {
		return isRequired;
	}
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	public boolean isRecordChange() {
		return recordChange;
	}
	public void setRecordChange(boolean recordChange) {
		this.recordChange = recordChange;
	}
    
    public CiAttribute clone(){
    	CiAttribute ci=null;
    	try {
			ci=(CiAttribute) super.clone();	
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return ci;
    }
	@Override
	public int compareTo(Object arg0) {
		CiAttribute other=(CiAttribute)arg0;
		int re=0;
		if(this.order>other.order){
			re=1;
		}else if(this.order<other.order){
			re=-1;
		}
		return re;
	}	
}
