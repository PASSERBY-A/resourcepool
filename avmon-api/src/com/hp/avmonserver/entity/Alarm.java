package com.hp.avmonserver.entity;

import java.io.Serializable;
import java.util.Date;

public class Alarm implements Serializable{

    private static final long serialVersionUID = -7478677254514008841L;
    
    public boolean isFetched() {
        return fetched;
    }

    public void setFetched(boolean fetched) {
        this.fetched = fetched;
    }

    public static final int STATUS_UNKNOWN = -1;
    public static final int STATUS_NEW = 0;
    public static final int STATUS_AKNOWLEDGED = 1;
    public static final int STATUS_FORWARD = 2;
    public static final int STATUS_CLEAR = 9;
    
    public static final int GRADE_CRITICAL = 5;
    public static final int GRADE_MAJOR = 4;
    public static final int GRADE_MINOR = 3;
    public static final int GRADE_WARN = 2;
    public static final int GRADE_INFO = 1;
    
    public static final int MAX_CONTENT_LENGTH = 3900;
    
    String id;
    String source; //original mo
    String sourceType; // AGENT,SIM,AUTO
    String moId; //monitor target object
    String code; //alarm code
    String kpiCode;
    String instance;


    String originalContent;
    
    String title;
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    String content;
    Date firstOccurTime;
    Date lastOccurTime;
    Date aknowledgeTime;
    Date forwardTime;
    String aknowledgeBy;
    String forwardBy;
    
    String sourceIp;
    
    String solution;
    String solutionType;
    String closeBy;
    Date closeTime;
    
    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }

    String taskId;
    
    int occurTimes;
    int grade; //系统中默认级别分为5级，级别的值从0-4从小到大排列
    int status; //
    
    String additionalInfo; //json format for additional information.
    
    String agentIp;
    
    public String getAgentIp() {
        return agentIp;
    }

    public void setAgentIp(String agentIp) {
        this.agentIp = agentIp;
    }

    boolean fetched;
    
    public Alarm(){
        this.fetched=false;
    }
    
    public String getAdditionalInfo() {
        return additionalInfo;
    }
    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public String getSourceType() {
        return sourceType;
    }
    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }
    public String getMoId() {
        return moId;
    }
    public void setMoId(String moId) {
        this.moId = moId;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getKpiCode() {
        return kpiCode;
    }

    public void setKpiCode(String kpiCode) {
        this.kpiCode = kpiCode;
    }


    public String getInstance() {
        return instance;
    }

    public void setInstance(String instance) {
        this.instance = instance;
    }

    public String getOriginalContent() {
        return originalContent;
    }
    public void setOriginalContent(String originalContent) {
        this.originalContent = originalContent;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getFirstOccurTime() {
        return firstOccurTime;
    }
    public void setFirstOccurTime(Date firstOccurTime) {
        this.firstOccurTime = firstOccurTime;
    }
    public Date getLastOccurTime() {
        return lastOccurTime;
    }
    public void setLastOccurTime(Date lastOccurTime) {
        this.lastOccurTime = lastOccurTime;
    }
    public Date getAknowledgeTime() {
        return aknowledgeTime;
    }
    public void setAknowledgeTime(Date aknowledgeTime) {
        this.aknowledgeTime = aknowledgeTime;
    }
    public Date getForwardTime() {
        return forwardTime;
    }
    public void setForwardTime(Date forwardTime) {
        this.forwardTime = forwardTime;
    }

    public String getAknowledgeBy() {
        return aknowledgeBy;
    }
    public void setAknowledgeBy(String aknowledgeBy) {
        this.aknowledgeBy = aknowledgeBy;
    }
    public String getForwardBy() {
        return forwardBy;
    }
    public void setForwardBy(String forwardBy) {
        this.forwardBy = forwardBy;
    }

    public String getTaskId() {
        return taskId;
    }
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }
    public int getOccurTimes() {
        return occurTimes;
    }
    public void setOccurTimes(int occurTimes) {
        this.occurTimes = occurTimes;
    }

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public int getStatus() {
        return status;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getSolutionType() {
        return solutionType;
    }

    public void setSolutionType(String solutionType) {
        this.solutionType = solutionType;
    }

    public String getCloseBy() {
        return closeBy;
    }

    public void setCloseBy(String closeBy) {
        this.closeBy = closeBy;
    }

    public Date getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(Date closeTime) {
        this.closeTime = closeTime;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toString(){
        return String.format("moid=%s|sourcetype=%s|id=%s|code=%s|ori-content=%s|status=%d|fctime=%s",this.moId, this.sourceType,this.id,this.code,this.originalContent,this.getStatus(),this.getFirstOccurTime().toString());
    }
    
}
