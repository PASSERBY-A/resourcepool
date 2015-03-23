package com.hp.avmon.snmp.discover;

import java.util.List;
import java.util.Vector;

import com.hp.avmon.snmp.common.snmp.DeviceTypeInfo;

public class DiscoverConfig {
	public static boolean isDebug = false;
	public static boolean isIPRoamingAll = true;
	public static int maxDeviceSize=1024;
	public static int maxLevel=8;
	private static List<DeviceTypeInfo> nodeTypeList = new Vector<DeviceTypeInfo>();

	// private static List<ResourceSearchMothType> resourceTypeList = new
	// Vector();

	public static synchronized void addDeviceType(DeviceTypeInfo deviceTypeInfo) {
		nodeTypeList.add(deviceTypeInfo);
	}

	public static synchronized void clearAllDeviceType() {
		nodeTypeList.clear();
	}

	public static List<DeviceTypeInfo> getAllDeviceType() {
		
		return nodeTypeList;
	}
}
