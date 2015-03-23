package com.hp.avmon.alserver.api;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hp.avmon.alserver.entity.AlTask;
import com.hp.avmonserver.entity.Alarm;
import com.hp.avmonserver.entity.AlarmComment;
import com.hp.avmonserver.entity.AvmonServerConfig;
import com.hp.avmonserver.entity.KpiEvent;
import com.hp.avmonserver.entity.MO;
import com.hp.avmonserver.entity.MOStatus;



public interface IAlServerRemoteService extends java.rmi.Remote{
    

    public void heartbeat(String executorId) throws RemoteException;
        
    public void reportTaskResult(AlTask task) throws RemoteException;
    
    public String executeCommand(String moId,String rmpId,String command,String[] params) throws RemoteException;

    public List<AlTask> getAllTasks() throws RemoteException;

	public void testHeartBeat() throws RemoteException;

	public AlTask getNewTask(String os,String executorId) throws RemoteException;
    
}
