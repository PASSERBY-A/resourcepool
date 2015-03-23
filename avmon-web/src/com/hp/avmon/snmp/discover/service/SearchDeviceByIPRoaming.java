package com.hp.avmon.snmp.discover.service;

import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.avmon.snmp.common.snmp.DeviceInfo;
import com.hp.avmon.snmp.common.snmp.DeviceTypeInfo;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;
import com.hp.avmon.snmp.discover.DiscoverConfig;
import com.hp.avmon.snmp.mib.MibMacIP;

public class SearchDeviceByIPRoaming {

	public static final int MAX_LEVEL = 8;
	public static final int MAX_DEVICE_COUNT = 1024;

	private List<DeviceInfo> allRoamedDeviceList = new Vector<DeviceInfo>();
	
	private static final Logger logger = LoggerFactory
			.getLogger(SearchDeviceByIPRoaming.class);
	

	private boolean isDeviceExist(String ip, String mac) {

		if (allRoamedDeviceList != null) {
			for (DeviceInfo deviceInfo : allRoamedDeviceList) {
				if (deviceInfo.getDeviceIP().equals(ip)
						&& deviceInfo.getDeviceMAC().equals(mac))
					return true;
			}
		}

		return false;
	}

	public List<DeviceInfo> searchDeviceByIPRoaming(List<String> ipList,
			List<SNMPTarget> targetList, boolean usePing, int maxLevel,
			int maxDeviceSize) {
		if (maxLevel >= MAX_LEVEL)
			maxLevel = MAX_LEVEL;
		if (maxDeviceSize >= MAX_DEVICE_COUNT)
			maxDeviceSize = MAX_DEVICE_COUNT;

		List<DeviceInfo> deviceInfoList = new SearchNodeService()
				.searchDeviceByNetList(ipList, targetList, usePing);

		this.allRoamedDeviceList.addAll(deviceInfoList);

		for (int i = 1; i <= maxLevel; i++) {
			if (this.allRoamedDeviceList.size() >= maxDeviceSize)
				break;
			deviceInfoList = getOtherDeviceByRouter(deviceInfoList, targetList,
					usePing);
			this.allRoamedDeviceList.addAll(deviceInfoList);
		}

		return this.allRoamedDeviceList;
	}

	private List<DeviceInfo> getOtherDeviceByRouter(
			List<DeviceInfo> deviceInfoList, List<SNMPTarget> snmpTargetList,
			boolean usePing) {

		Vector<DeviceInfo> result = new Vector<DeviceInfo>();

		Vector<String> ipList = new Vector<String>();

		for (DeviceInfo deviceInfo : deviceInfoList) {
			if ((!DiscoverConfig.isIPRoamingAll)
					&& (!deviceInfo.getDeviceType().equalsLogicType(
							DeviceTypeInfo.isRouterType))
					&& (!deviceInfo.getDeviceType().equalsLogicType(
							DeviceTypeInfo.isRouterSwitchType)))
				continue;

			List<MibMacIP> mibMacIPList = deviceInfo.getMibMacIPList();
			for (MibMacIP mibMacIP : mibMacIPList) {

				if (mibMacIP.getIpNetToMediaType() == 2)
					continue;

				String mediaNetAddress = mibMacIP.getIpNetToMediaNetAddress();
				String mediaPhysAddress = mibMacIP.getIpNetToMediaPhysAddress();

				if ((isDeviceExist(mediaNetAddress, mediaPhysAddress))
						|| (ipList.contains(mediaNetAddress)))
					continue;
				ipList.add(mediaNetAddress);
			}
		}

		List<DeviceInfo> list = new SearchNodeService().searchDeviceByIPList(
				ipList, snmpTargetList, usePing);

		for (DeviceInfo deviceInfo : list) {
			if (this.allRoamedDeviceList.contains(deviceInfo))
				continue;
			result.add(deviceInfo);
		}

		return result;
	}
}
