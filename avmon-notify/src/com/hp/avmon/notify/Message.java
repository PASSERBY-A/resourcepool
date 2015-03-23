package com.hp.avmon.notify;

import java.util.Date;

public class Message {
    private String phoneNo;
    private String content;
    private Date createTime;
    private Date planSendTime;
    private Date sentTime;
    private String alarmId;
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getPlanSendTime() {
        return planSendTime;
    }
    public void setPlanSendTime(Date planSendTime) {
        this.planSendTime = planSendTime;
    }
    public Date getSentTime() {
        return sentTime;
    }
    public void setSentTime(Date sentTime) {
        this.sentTime = sentTime;
    }
    public String getAlarmId() {
        return alarmId;
    }
    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }
    public int getSendFlag() {
        return sendFlag;
    }
    public void setSendFlag(int sendFlag) {
        this.sendFlag = sendFlag;
    }
    public int getMessageType() {
        return messageType;
    }
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSource() {
        return source;
    }
    public void setSource(String source) {
        this.source = source;
    }
    private int sendFlag;
    private int messageType;
    private String title;
    private String source;
}
