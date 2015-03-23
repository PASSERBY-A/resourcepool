package com.hp.avmonserver.schedule.service;

import java.rmi.RemoteException;
import java.util.List;

import com.hp.avmonserver.schedule.entity.Task;

public interface IRemoteScheduleService extends java.rmi.Remote{
	
	/**
	 * 增加调度任务
	 * @param taskLis
	 * @return
	 */
	public String addTrigger(List<Task> taskLis) throws RemoteException;
	
	/**
	 * 删除调度任务
	 * @param taskList
	 * @return
	 */
	public String removeTrigdger(List<Task> taskList) throws RemoteException;
	
	/**
	 * 暂停调度任务
	 * @param taskList
	 * @return
	 */
	public String pauseTrigger(List<Task> taskList) throws RemoteException;
	
	/**
	 * 恢复调度任务
	 * @param taskList
	 * @return
	 */
	public String resumeTrigger(List<Task> taskList) throws RemoteException;
	
	/**
	 * 重新载入所有调度任务
	 * @return
	 */
	public String restartAll() throws RemoteException;
	  
	/**
	 * 停止所有调度任务
	 * @return
	 */
	public String stopAll() throws RemoteException;

}
