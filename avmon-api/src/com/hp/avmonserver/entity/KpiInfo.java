package com.hp.avmonserver.entity;

import java.io.Serializable;

public class KpiInfo implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -2743716770706785871L;


    /**
     * KPI Code, 比如: 510001
     */
    private String code;  

    /**
     * KPI Name, 比如: cpu_user
     */
    private String name;  
    
    /**
     * KPI 名称
     */
    private String caption;
    
    /**
     * 聚合方法，比如：sum,avg,min,max
     */
    private String aggMethod;
    
    /**
     * 保留小数位数
     */
    private int precision;
    
    /**
     * 单位
     */
    private String unit;
    
    /**
     * 是否为计算性KPI，预留,1:是，0：否
     */
    private int isCalc;
    
    /**
     * 计算方法，预留，比如：CASE,1,运行中,0,停止 或者：cpu_user+cpu_sys
     */
    private String calcMethod;
    
    /**
     * 是否保存到数据库，1：保存，0：不保存
     */
    private int isStore;
    
    /**
     * 聚合周期，以分钟为单位
     */
    private int storePeriod;  // 以分钟为单位
    
    /**
     * 数据类型, 0:string,1:number
     */
    private int dataType;
    
    
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }

    public int getPrecision() {
        return precision;
    }
    public void setPrecision(int precision) {
        this.precision = precision;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public String getCalcMethod() {
        return calcMethod;
    }
    public void setCalcMethod(String calcMethod) {
        this.calcMethod = calcMethod;
    }
    public int getIsStore() {
        return isStore;
    }
    public void setIsStore(int isStore) {
        this.isStore = isStore;
    }

    public int getStorePeriod() {
        return storePeriod;
    }
    public void setStorePeriod(int storePeriod) {
        this.storePeriod = storePeriod;
    }

    

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAggMethod() {
        return aggMethod;
    }
    public void setAggMethod(String aggMethod) {
        this.aggMethod = aggMethod;
    }
    public int getIsCalc() {
        return isCalc;
    }
    public void setIsCalc(int isCalc) {
        this.isCalc = isCalc;
    }
    public void setDataType(int dataType) {
        this.dataType = dataType;
    }
    public int getDataType() {
        return dataType;
    }
}
