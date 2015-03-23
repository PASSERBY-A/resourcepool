package com.hp.avmon.snmp.discover.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.avmon.snmp.common.snmp.DeviceInfo;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;
import com.hp.avmon.snmp.common.snmp.StartEndIP;

/**
 * 
 * @author litan
 *
 */
public class SearchNodeService {
	private static final Logger logger = LoggerFactory
			.getLogger(SearchNodeService.class);
	
	
	private List<DeviceInfo> deviceInfoList = new ArrayList<DeviceInfo>();

	/**
	 * @param ipString
	 * @param snmpTargetList
	 * @param pingFlag
	 * @return
	 */
	public List<DeviceInfo> searchDevice(String ipString,
			List<SNMPTarget> snmpTargetList, boolean pingFlag) {
		List<String> list = new ArrayList<String>();
		list.add(ipString);
		
		return searchDeviceByNetList(list, snmpTargetList, pingFlag);
	}

	public List<DeviceInfo> searchDeviceByNetList(List<String> ipList,
			List<SNMPTarget> snmpTargetList, boolean pingFlag) {
		List<StartEndIP> startEndIpList = new ArrayList<StartEndIP>();
//		if (ipList.size()==1 
//				&& ipList.get(0).matches("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))")
//				) {
//			return searchDeviceByIPList(ipList, snmpTargetList, pingFlag);
//		}
		for(String ip0 : ipList){
			String lastStr =  ip0.substring(ip0.lastIndexOf(".")+1);
			
			if ("0".equals(lastStr)){
				int i = ip0.lastIndexOf("0");
				String ipHead = ip0.substring(0, i);
				String startIp = ipHead + "1";
				String endIp = ipHead + "254";
				StartEndIP startEndIP = new StartEndIP();
				startEndIP.setStartIP(startIp);
				startEndIP.setEndIP(endIp);
				startEndIpList.add(startEndIP);
			}else if(ipList.size()==1 
					&& ipList.get(0).matches("((?:(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(?:25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d))))")){
				return searchDeviceByIPList(ipList, snmpTargetList, pingFlag);
			}else{
				continue;
			}
				
			
			
		}
		return searchDevice(startEndIpList, snmpTargetList, pingFlag);
	}

	/**
	 * @param startIP
	 * @param endIP
	 * @param paramList
	 * @param pingFlag
	 * @return
	 */
	public List<DeviceInfo> searchDevice(String startIP,
			String endIP, List<SNMPTarget> targetList,
			boolean pingFlag) {
		
		List<StartEndIP> list = new ArrayList<StartEndIP>();
		StartEndIP startEndIP = new StartEndIP();
		startEndIP.setStartIP(startIP);
		startEndIP.setEndIP(endIP);
		list.add(startEndIP);
		
		return searchDevice(list, targetList, pingFlag);
	}

	public List<DeviceInfo> searchDevice(List<StartEndIP> startEndIPList,
			List<SNMPTarget> snmpTargetList, boolean pingFlag) {
		List<String> list = new ArrayList<String>();
		for(StartEndIP startEndIP : startEndIPList){
			
			String startIp = startEndIP.getStartIP();
			int i = startIp.lastIndexOf(".");
			String str2 = startIp.substring(i + 1);
			int j = Integer.parseInt(str2);
			String str3 = startIp.substring(0, i + 1);
			
			String endIp = startEndIP.getEndIP();
			int k = endIp.lastIndexOf(".");
			String str5 = endIp.substring(k + 1);
			int m = Integer.parseInt(str5);
			for (int n = j; n <= m; n++)
				list.add(str3 + n);
		}
		return searchDeviceByIPList(list, snmpTargetList, pingFlag);
	}

	public List<DeviceInfo> searchDeviceByIPList(List<String> ipList,
			List<SNMPTarget> snmpTargetList, boolean pingFlag) {
		int ipCount = ipList.size();
		if (ipCount == 0)
			return this.deviceInfoList;
		int exeCount = ipCount;
		int snmpThreadPoolSize = 10;
		
		if (exeCount > snmpThreadPoolSize)
			exeCount = snmpThreadPoolSize;
		
//		ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(exeCount,
//				exeCount, 30L, TimeUnit.SECONDS, new LinkedBlockingQueue());
		
		ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newCachedThreadPool();

		logger.debug("starting execute the IpSearchTask");
		for(String ip : ipList){
			threadPoolExecutor.execute(new IpSearchTask(ip, snmpTargetList,
					this.deviceInfoList, pingFlag));
		}
		threadPoolExecutor.shutdown();
		try {
			for(boolean shutdown=false ;!shutdown;shutdown=threadPoolExecutor.awaitTermination(1, TimeUnit.SECONDS))
				;
		} catch (InterruptedException e) {
			logger.debug("execute the IpSearchTask error: "+e.getMessage());
			e.printStackTrace();
		}
		return this.deviceInfoList;
	}
}
