package com.hp.avmonserver.entity;

import java.io.Serializable;

public class AmpInstance implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -5518935831562176377L;
    
    private String ampId;
    private String moId;
    private String ampInstanceId;
    public String getAmpId() {
        return ampId;
    }
    public void setAmpId(String ampId) {
        this.ampId = ampId;
    }
    public String getMoId() {
        return moId;
    }
    public void setMoId(String moId) {
        this.moId = moId;
    }
    public String getAmpInstanceId() {
        return ampInstanceId;
    }
    public void setAmpInstanceId(String ampInstanceId) {
        this.ampInstanceId = ampInstanceId;
    }
    public String getAgentId() {
        return agentId;
    }
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public String getSchedule() {
        return schedule;
    }
    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }
    public int getIsParentSchedule() {
        return isParentSchedule;
    }
    public void setIsParentSchedule(int isParentSchedule) {
        this.isParentSchedule = isParentSchedule;
    }
    public int getEnableFlag() {
        return enableFlag;
    }
    public void setEnableFlag(int enableFlag) {
        this.enableFlag = enableFlag;
    }
    public int getIsParentThreshold() {
        return isParentThreshold;
    }
    public void setIsParentThreshold(int isParentThreshold) {
        this.isParentThreshold = isParentThreshold;
    }
    public String getCaption() {
        return caption;
    }
    public void setCaption(String caption) {
        this.caption = caption;
    }
    private String agentId;
    private String version;
    private String schedule;
    private int isParentSchedule;
    private int enableFlag;
    private int isParentThreshold;
    private String caption;
    
}
