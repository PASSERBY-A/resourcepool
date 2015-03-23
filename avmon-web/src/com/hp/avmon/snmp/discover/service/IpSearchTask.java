package com.hp.avmon.snmp.discover.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.avmon.snmp.common.snmp.DeviceInfo;
import com.hp.avmon.snmp.common.snmp.DeviceTypeInfo;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;

/**
 * 
 * @author litan
 *
 */
public class IpSearchTask implements Runnable {
	
	private static final Logger logger = LoggerFactory
			.getLogger(IpSearchTask.class);
	
	String ip;
	List<SNMPTarget> snmpTargetList;
	List<DeviceInfo> rsList;
	boolean isUsePing;

	public IpSearchTask(String ip, List<SNMPTarget> paramList,
			List<DeviceInfo> rsList, boolean isUsePing) {
		this.snmpTargetList = paramList;
		this.ip = ip;
		this.rsList = rsList;
		this.isUsePing = isUsePing;
	}

	public void run() {
		try {
			DeviceInfo deviceInfo = new SearchBasicDeviceInfoService()
					.getDeviceBasicInfo(this.ip, this.snmpTargetList,
							this.isUsePing);
			if (deviceInfo != null && deviceInfo.getDeviceSysOID()!=null) {
				SearchTypeService service = new SearchTypeService();
				DeviceTypeInfo deviceTypeInfo  = service.matchDeviceType(deviceInfo);
				
				deviceInfo.setDeviceType(deviceTypeInfo);
				synchronized (rsList) {
					if (!this.rsList.contains(deviceInfo))
						this.rsList.add(deviceInfo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
