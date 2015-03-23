package com.hp.avmonserver.entity;


import java.io.Serializable;
import java.util.Date;

public class KpiEvent implements Serializable{

    
    /**
     * KPI事件唯一ID
     */
    private String id;
    
    /**
     * KPI Code
     */
    private String kpiCode;

    /**
     * 监控对象ID
     */
    private String moId;
    
    /**
     * Agent ID
     */
    private String agentId;
    

    /**
     * 采集包实例ID
     */
    private String ampInstId;
    
    /**
     * kpi值对应的实例，比如进程名，网卡，磁盘
     */
    private String instance;
    
    /**
     * 采集包编号
     */
    private String ampId;

    /**
     * 如果该事件产生告警，则填写对应的告警ID
     */
    private String alarmId;
    
    /**
     * KPI原始值
     */
    private String value;
    
    /**
     * 转换后的数值型值
     */
    private float numValue;
    
    /**
     * 转换后的字符型值，已加上单位，或进行了处理
     */
    private String strValue;
    
    /**
     * KPI采集事件
     */
    private Date kpiTime;
    
    /**
     * 阀值校验时对应的告警级别
     */
    private int thresholdLevel;
    
    /**
     * 阀值超限时，所对应的相应阀值
     */
    private String threshold;
    
    /**
     * 描述
     */
    private String description;
    
    /**
     * 数据批次
     */
    private String dataGroup;
    
    private String ip;
    
    private String hostName;

    public String getDataGroup() {
        return dataGroup;
    }

    public void setDataGroup(String dataGroup) {
        this.dataGroup = dataGroup;
    }

    /**
     * 记录创建时间，内部使用
     */
    private long createTime;
     
    /**
     * 内部使用
     */
    private boolean newValue;
    
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    public Date getKpiTime() {
        return kpiTime;
    }

    public void setKpiTime(Date kpiTime) {
        this.kpiTime = kpiTime;
    }

    public KpiEvent(){
        thresholdLevel=0;
        threshold="";
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        if(instance==null || instance.equals("")){
            this.instance="All";
        }
        else{
            this.instance = instance;
        }
    }

    public String getAmpInstId() {
        return ampInstId;
    }

    public void setAmpInstId(String ampInstId) {
        this.ampInstId = ampInstId;
    }

    public String getAmpId() {
        return ampId;
    }

    public void setAmpId(String ampId) {
        this.ampId = ampId;
    }

    public float getNumValue() {
        return numValue;
    }

    public void setNumValue(float numValue) {
        this.numValue = numValue;
    }

    public String getStrValue() {
        return strValue;
    }

    public void setStrValue(String strValue) {
        this.strValue = strValue;
    }


    public String getKpiCode() {
        return kpiCode;
    }

    public void setKpiCode(String kpiCode) {
        this.kpiCode = kpiCode;
    }

    public String getMoId() {
        return moId;
    }

    public void setMoId(String moId) {
        this.moId = moId;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("[moId=%s,AmpInstId=%s,kpiCode=%s,path=%s,time=%s,value=%s]", 
                this.getMoId(),
                this.getAmpInstId(),
                this.getKpiCode(),
                this.getInstance(),
                this.getKpiTime(),
                this.getValue()
                );
    }
    

    public boolean isNewValue() {
        return newValue;
    }

    public void setNewValue(boolean newValue) {
        this.newValue = newValue;
    }

    public int getThresholdLevel() {
        return thresholdLevel;
    }

    public void setThresholdLevel(int thresholdLevel) {
        this.thresholdLevel = thresholdLevel;
    }

    public String getThreshold() {
        return threshold;
    }

    public void setThreshold(String threshold) {
        this.threshold = threshold;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }
	
}
