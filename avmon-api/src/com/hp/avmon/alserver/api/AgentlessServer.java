package com.hp.avmon.alserver.api;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import com.hp.avmon.alserver.entity.AlTask;
import com.hp.avmon.utils.AvmonUtils;
import com.hp.avmonserver.entity.Alarm;
import com.hp.avmonserver.entity.AlarmComment;
import com.hp.avmonserver.entity.AvmonServerConfig;
import com.hp.avmonserver.entity.KpiEvent;
import com.hp.avmonserver.entity.MO;
import com.hp.avmonserver.entity.MOStatus;

public class AgentlessServer {

	IAlServerRemoteService remoteService = null;

	private String serverUrl;



	public void init(String serverUrl) {
		this.serverUrl = serverUrl;
		try {
			//AvmonUtils.systemOut("AvmonServerInitserverUrl:"+ serverUrl);
			remoteService = (IAlServerRemoteService) Naming.lookup(this.serverUrl);
		} catch (Exception e) {
			//AvmonUtils.systemOut("serverUrl=" + serverUrl);
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage()+",serverUrl=" + serverUrl);
			//e.printStackTrace();
		}
	}

	public boolean checkRemoteService() throws Exception {
		if (remoteService == null) {
			init(serverUrl);
		} else {
			try {
				// AvmonUtils.systemOut("Test serverUrl="+serverUrl);
				remoteService.testHeartBeat();
				// AvmonUtils.systemOut("Test Avmon Server OK.");

			} catch (Exception e) {
				// AvmonUtils.systemOut("Test Avmon Server Fault.");
				// AvmonUtils.systemOut("AvmonServer exception: " +
				// e.getMessage());
				remoteService = null;
				init(serverUrl);

			}
		}
		// init(serverUrl);
		if (remoteService == null) {
			// throw new
			// Exception("Error:Init AvmonServer fault with "+serverUrl);
			AvmonUtils.systemOut("Error:Init AvmonServer fault with "
					+ serverUrl);
			return false;
		}
		return true;
	}

	public void heartbeat(String executorId) {
		try {
			if (checkRemoteService()) {
				remoteService.heartbeat(executorId);
			} else {
				return;
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
	}

	public AlTask getNewTask(String os,String executorId) {
		try {
			if (checkRemoteService()) {
				return remoteService.getNewTask(os,executorId);
			} else {
				return null;
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return null;
	}
	
	public void reportTaskResult(AlTask task) {
		try {
			if (checkRemoteService()) {
				remoteService.reportTaskResult(task);
			} 
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
	}
	
	public String executeCommand(String moId,String rmpId,String command,String[] params) {
		try {
			if (checkRemoteService()) {
				return remoteService.executeCommand(moId, rmpId, command, params);
			} else {
				return null;
			}
		} catch (Exception e) {
			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
			e.printStackTrace();
			remoteService = null;
		}
		return null;
	}

	
//
//	public String deployAmpPackage(String agentId, String ampInstId) {
//		try {
//			if (checkRemoteService()) {
//				return remoteService.deployAmpPackage(agentId, ampInstId);
//			} else {
//				return "44:Avmon-Server Connection Error";
//			}
//		} catch (Exception e) {
//			AvmonUtils.systemOut("AvmonServer exception: " + e.getMessage());
//			e.printStackTrace();
//			remoteService = null;
//		}
//		return "99:Unknown Error";
//	}

}
