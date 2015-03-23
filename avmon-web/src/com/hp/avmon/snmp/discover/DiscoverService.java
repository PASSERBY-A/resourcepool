package com.hp.avmon.snmp.discover;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.spi.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.hp.avmon.snmp.common.snmp.DeviceInfo;
import com.hp.avmon.snmp.common.snmp.DeviceTypeInfo;
import com.hp.avmon.snmp.common.snmp.SNMPFactory;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;
import com.hp.avmon.webservice.EventInfoServiceManager;



@Service("discoverService")
public class DiscoverService {
	
	private static final Log logger = LogFactory.getLog(EventInfoServiceManager.class);
	
	private static Discover discover  =  Discover.getDiscove();
	
	private DeviceInfoDao deviceInfoDao;
	
	public static List<DeviceTypeInfo> deviceTypeInfoList;
	
	@Autowired
	public void setDeviceInfoDao(@Qualifier("deviceInfoDao")DeviceInfoDao deviceInfoDao) {
		this.deviceInfoDao = deviceInfoDao;
		deviceTypeInfoList = initDeviceTypeInfoList();
		try {
			SNMPFactory.init();
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
			e.printStackTrace();
		}
	}
	
	public List<String> saveDeviceInfo(String ip, List<SNMPTarget> snmpTargetList, boolean isUsePing){
		List<DeviceInfo> list = discover.searchDevice(ip, snmpTargetList, isUsePing);
		List<String> result = new ArrayList<String>();
		result = deviceInfoDao.saveList(list);
		return result;
	}
	
	public List<String> saveDeviceInfo(String startIp, String endIp,
			List<SNMPTarget> snmpTargetList, boolean isUsePing){
		List<String> result = new ArrayList<String>();
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		list = discover.searchDevice(startIp, endIp, snmpTargetList, isUsePing);
		result = deviceInfoDao.saveList(list);
		return result;
	}
	/**
	 * 
	 * @param ipNetList
	 * @param snmpTarget
	 * @param usrPing
	 */
	public List<String> saveDeviceInfo(List<String> ipNetList,
			List<SNMPTarget> snmpTarget, boolean usrPing){
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		List<String> result = new ArrayList<String>();
		list = discover.searchDeviceByNetList(ipNetList, snmpTarget, usrPing);
		result = deviceInfoDao.saveList(list);
		return result;
	}
	
	public List<String> saveDeviceInfoByRoaming(List<String> ipNetList,
			List<SNMPTarget> snmpTargetList, boolean usePing){
		int maxLevel = DiscoverConfig.maxLevel;
		int maxDeviceSize = DiscoverConfig.maxDeviceSize;
		List<DeviceInfo> list = new ArrayList<DeviceInfo>();
		List<String> result = new ArrayList<String>();
		list = discover.searchDeviceByIPRoaming(ipNetList, snmpTargetList, usePing,maxLevel,maxDeviceSize);
		result = deviceInfoDao.saveList(list);
		return result;
	}
	
	public List<DeviceTypeInfo> initDeviceTypeInfoList(){
		
		List<DeviceTypeInfo> typeInfoList =  deviceInfoDao.getDeviceTypeInfoList();
		
		return typeInfoList;
	}
}
