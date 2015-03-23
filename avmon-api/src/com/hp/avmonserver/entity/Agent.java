package com.hp.avmonserver.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Agent implements Serializable{
    //*
    public final static int HOST_STATUS_UNKNOWN=0;
    public final static int HOST_STATUS_RUNNING=1;
    public final static int HOST_STATUS_UNREACHABLE=3;
 
    public final static int AGENT_STATUS_UNKNOWN=0;
    public final static int AGENT_STATUS_RUNNING=1;
    public final static int AGENT_STATUS_USER_PAUSE=2;
    public final static int AGENT_STATUS_UNREACHABLE=3;
    
    public final static int PING_RESULT_UP=1;
    public final static int PING_RESULT_DOWN=0;
    
    public final static int ALARM_FLAG_WAIT=0;
    public final static int ALARM_FLAG_SENT=1;

     
    private String agentId;
    private String agentVersion;
    private String os;
    private String osVersion;
    private String ip;
    private String gatewayIp;
    private Date lastUpdateTime;     //预留
    private Date lastHeartbeatTime;  //最后心跳时间，取avmonserver的当前时间
    private int agentStatus;  //0:未知，1：正在运行  2:暂停  3:不可达  4:
    private int agentHostStatus;
    private int pingTimes;
    private int alarmFlag;
    private String hostName;
    
    /*内部使用变量*/
    private long lastSaveHeatbeatTime=0;
    

    public long getLastSaveHeatbeatTime() {
        return lastSaveHeatbeatTime;
    }
    public void setLastSaveHeatbeatTime(long lastSaveHeatbeatTime) {
        this.lastSaveHeatbeatTime = lastSaveHeatbeatTime;
    }
    public int getPingTimes() {
        return pingTimes;
    }
    public void setPingTimes(int pingTimes) {
        this.pingTimes = pingTimes;
    }
    public int getAlarmFlag() {
        return alarmFlag;
    }
    public void setAlarmFlag(int alarmFlag) {
        this.alarmFlag = alarmFlag;
    }
    public int getAgentHostStatus() {
        return agentHostStatus;
    }
    public void setAgentHostStatus(int agentHostStatus) {
        this.agentHostStatus = agentHostStatus;
    }
    private Date lastPingTime;
    private int lastPingResult;
    public String getAgentId() {
        return agentId;
    }
    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }
    public String getAgentVersion() {
        return agentVersion;
    }
    public void setAgentVersion(String agentVersion) {
        this.agentVersion = agentVersion;
    }
    public String getOs() {
        return os;
    }
    public void setOs(String os) {
        this.os = os;
    }
    public String getOsVersion() {
        return osVersion;
    }
    public void setOsVersion(String osVersion) {
        this.osVersion = osVersion;
    }
    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getGatewayIp() {
		return gatewayIp;
	}
	public void setGatewayIp(String gatewayIp) {
		this.gatewayIp = gatewayIp;
	}
	public Date getLastUpdateTime() {
        return lastUpdateTime;
    }
    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    public Date getLastHeartbeatTime() {
        return lastHeartbeatTime;
    }
    public void setLastHeartbeatTime(Date lastHeartbeatTime) {
        this.lastHeartbeatTime = lastHeartbeatTime;
    }
    public int getAgentStatus() {
        return agentStatus;
    }
    public void setAgentStatus(int agentStatus) {
        this.agentStatus = agentStatus;
    }
    public Date getLastPingTime() {
        return lastPingTime;
    }
    public void setLastPingTime(Date lastPingTime) {
        this.lastPingTime = lastPingTime;
    }
    public int getLastPingResult() {
        return lastPingResult;
    }
    public void setLastPingResult(int lastPingResult) {
        this.lastPingResult = lastPingResult;
    }
    public String getHostName() {
		return hostName;
	}
	public void setHostName(String hostName) {
		this.hostName = hostName;
	}
	//*/
    public String toString(){
        String s="";
        s+=String.format("\tAgent ID=%s\n",this.agentId);
        s+=String.format("\tAgent Status=%s\n",this.agentStatus);
        s+=String.format("\tAgent Version=%s\n",this.agentVersion);
        s+=String.format("\tHostStatus=%s\n",this.agentHostStatus);
        s+=String.format("\tos=%s\n",this.os);
        s+=String.format("\tosVersion=%s\n",this.osVersion);
        s+=String.format("\tAgent IP=%s\n",this.ip);
        s+=String.format("\tGateway ID=%s\n",this.gatewayIp);
        s+=String.format("\tlastUpdateTime=%s\n",this.lastUpdateTime);
        s+=String.format("\tlastHeartbeatTime=%s\n",this.lastHeartbeatTime);
        return s;
    }
    
}
