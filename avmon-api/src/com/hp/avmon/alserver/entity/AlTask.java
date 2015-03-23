package com.hp.avmon.alserver.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class AlTask implements Serializable{
	

    public static final int STATUS_NEW = 0;
    public static final int STATUS_PROCESSING = 1;
    public static final int STATUS_DONE = 2;
    
    public static final int RESULT_SUCCESS=2;
    public static final int RESULT_FAULT=9;
    public static final int RESULT_UNKNOWN=0;
    
    public static final int TYPE_SCHEDULE_TASK=0;
    public static final int TYPE_INSTANT_CMD=1;

    
    
	private String taskId;
	private String command;
	private String[] commandParams;
	private String runAt;
	private String encoding;
	private String moId;
	
	private String executorId;
	
	private int type;
	private int status;
	private int result;
	private Date startTime;
	private Date doneTime;
	private String resultMessage;
	
	public String getMoId() {
		return moId;
	}

	public void setMoId(String moId) {
		this.moId = moId;
	}
	
	public String getRunAt() {
		return runAt;
	}


	public void setRunAt(String runAt) {
		this.runAt = runAt;
	}


	public String getEncoding() {
		return encoding;
	}


	public void setEncoding(String encoding) {
		this.encoding = encoding;
	}
	
	public String[] getCommandParams() {
		return commandParams;
	}


	public void setCommandParams(String[] commandParams) {
		this.commandParams = commandParams;
	}
	private String rmpId;
	private String scheduleId;
	public String getRmpId() {
		return rmpId;
	}


	public String getResultMessage() {
		return resultMessage;
	}


	public void setResultMessage(String resultMessage) {
		this.resultMessage = resultMessage;
	}


	public void setRmpId(String rmpId) {
		this.rmpId = rmpId;
	}


	public String getScheduleId() {
		return scheduleId;
	}


	public void setScheduleId(String scheduleId) {
		this.scheduleId = scheduleId;
	}

	


	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getExecutorId() {
		return executorId;
	}


	public void setExecutorId(String executorId) {
		this.executorId = executorId;
	}
	
	
	public AlTask(){
		type=TYPE_SCHEDULE_TASK;
		status=STATUS_NEW;
		result=RESULT_UNKNOWN;
		executorId=null;
	}
	
	
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public Date getStartTime() {
		return startTime;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public Date getDoneTime() {
		return doneTime;
	}
	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	
	public String toString(){
		return String.format("(taskid=%s,executorId=%s,status=%d,result=%d,runAt=%s,encoding=%s,command=%s)",taskId,executorId,status,result,runAt,encoding,command);
	}
	
}
