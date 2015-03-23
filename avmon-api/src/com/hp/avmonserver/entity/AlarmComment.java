package com.hp.avmonserver.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Map;

public class AlarmComment implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 8570143310903556187L;
    private String alarmId;
    private Date createTime;
    private String createBy;
    private String content;
    private String contentType;
    
    public AlarmComment(Map<String, Object> map) {
        alarmId=(String) map.get("ALARM_ID");
        createTime=(Date) map.get("CREATE_TIME");
        createBy=(String) map.get("CREATE_BY");
        content=(String) map.get("CONTENT");
        contentType=(String) map.get("TYPE");
    }
    
    public String getAlarmId() {
        return alarmId;
    }
    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getCreateBy() {
        return createBy;
    }
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public String getContentType() {
        return contentType;
    }
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
