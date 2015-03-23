package com.hp.avmonserver.entity;

import java.io.Serializable;

public class MOStatus implements Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7524612884707618621L;

    private String moId;
    
    private int activeAlarmCount;
    private int level0AlarmCount;
    private int level1AlarmCount;
    private int level2AlarmCount;
    private int level3AlarmCount;
    private int level4AlarmCount;
    private int userAlarmCount;
    private int newAlarmCount;
    public String getMoId() {
        return moId;
    }
    public void setMoId(String moId) {
        this.moId = moId;
    }
    public int getActiveAlarmCount() {
        return activeAlarmCount;
    }
    public void setActiveAlarmCount(int activeAlarmCount) {
        this.activeAlarmCount = activeAlarmCount;
    }
    public int getLevel0AlarmCount() {
        return level0AlarmCount;
    }
    public void setLevel0AlarmCount(int level0AlarmCount) {
        this.level0AlarmCount = level0AlarmCount;
    }
    public int getLevel1AlarmCount() {
        return level1AlarmCount;
    }
    public void setLevel1AlarmCount(int level1AlarmCount) {
        this.level1AlarmCount = level1AlarmCount;
    }
    public int getLevel2AlarmCount() {
        return level2AlarmCount;
    }
    public void setLevel2AlarmCount(int level2AlarmCount) {
        this.level2AlarmCount = level2AlarmCount;
    }
    public int getLevel3AlarmCount() {
        return level3AlarmCount;
    }
    public void setLevel3AlarmCount(int level3AlarmCount) {
        this.level3AlarmCount = level3AlarmCount;
    }
    public int getLevel4AlarmCount() {
        return level4AlarmCount;
    }
    public void setLevel4AlarmCount(int level4AlarmCount) {
        this.level4AlarmCount = level4AlarmCount;
    }
    public int getUserAlarmCount() {
        return userAlarmCount;
    }
    public void setUserAlarmCount(int userAlarmCount) {
        this.userAlarmCount = userAlarmCount;
    }
    public int getNewAlarmCount() {
        return newAlarmCount;
    }
    public void setNewAlarmCount(int newAlarmCount) {
        this.newAlarmCount = newAlarmCount;
    }
    public static long getSerialversionuid() {
        return serialVersionUID;
    }
    
}
