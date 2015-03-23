package com.hp.avmon.deploy.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;

public class DeployMonitorThread implements Callable<Map<String,String>> {

	private String moId;
	private String inst;
	
	DeployService service = new DeployService();
	
	public DeployMonitorThread(String moId,String inst){
		this.moId = moId;
		this.inst = inst;
	}
	@Override
	public Map<String,String> call() {
		Map<String,String> map = new HashMap<String,String>();
		map = service.deployMonitor(moId, inst);
		return map;
	}
}
