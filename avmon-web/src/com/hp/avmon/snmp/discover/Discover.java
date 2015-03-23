package com.hp.avmon.snmp.discover;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.avmon.snmp.common.snmp.DeviceInfo;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;
import com.hp.avmon.snmp.discover.service.SearchDeviceByIPRoaming;
import com.hp.avmon.snmp.discover.service.SearchNodeService;
import com.hp.avmon.webservice.EventInfoServiceManager;

/**
 * 
 * 
 * @author litan
 * 
 */
public class Discover {
	private static final Log logger = LogFactory.getLog(EventInfoServiceManager.class);
	private static Discover discover;

	public static Discover getDiscove() {
		if (discover == null)
			discover = new Discover();
		return discover;
	}

	
	/**
	 * 
	 * @param ip
	 * @param snmpTargetList
	 * @param isUsePing
	 * @return
	 */
	public List<DeviceInfo> searchDevice(String ip,
			List<SNMPTarget> snmpTargetList, boolean isUsePing) {

		List<DeviceInfo> deviceInfo = new ArrayList<DeviceInfo>();
		try {
			deviceInfo = new SearchNodeService().searchDevice(ip,
					snmpTargetList, isUsePing);

			return removeNullObject(deviceInfo);
		} catch (Exception localException) {
			logger.error(this.getClass().getName()+localException.getMessage());
			localException.printStackTrace();
		} finally {

		}

		return null;
	}

	/**
	 * 192.168.1.100-192.168.1.254
	 * 
	 * @param startIp
	 * @param endIp
	 * @param snmpTargetList
	 * @param isUsePing
	 * @return
	 */
	public List<DeviceInfo> searchDevice(String startIp, String endIp,
			List<SNMPTarget> snmpTargetList, boolean isUsePing) {
		List<DeviceInfo> deviceInfo = new ArrayList<DeviceInfo>();
		try {
			deviceInfo = new SearchNodeService().searchDevice(startIp, endIp,
					snmpTargetList, isUsePing);
			return removeNullObject(deviceInfo);
		} catch (Exception localException) {
			logger.debug(this.getClass().getName()+localException.getMessage());
			localException.printStackTrace();
		} finally {

		}
		return null;
	}

	/**
	 * 192.168.1.0,192.168.2.0 IP
	 * 
	 * @param ipNetList
	 * @param snmpTarget
	 * @param usrPing
	 * @return
	 */
	public List<DeviceInfo> searchDeviceByNetList(List<String> ipNetList,
			List<SNMPTarget> snmpTarget, boolean usrPing) {
		List<DeviceInfo> deviceInfo = new ArrayList<DeviceInfo>();
		try {
			deviceInfo = new SearchNodeService().searchDeviceByNetList(
					ipNetList, snmpTarget, usrPing);
			return removeNullObject(deviceInfo);
		} catch (Exception localException) {
			logger.debug(this.getClass().getName()+localException.getMessage());
			localException.printStackTrace();
		} finally {

		}
		return null;

	}

	/**
	 *  
	 * 
	 * @param ipNetList 192.168.1.0,192.168.2.0
	 * @param snmpTargetList 
	 * @param paramBoolean ping
	 * @param maxLevel   
	 * @param maxDeviceSize
	 * @return
	 */
	public List<DeviceInfo> searchDeviceByIPRoaming(List<String> ipNetList,
			List<SNMPTarget> snmpTargetList, boolean usePing,
			int maxLevel, int maxDeviceSize) {

		List<DeviceInfo> deviceInfo = new ArrayList<DeviceInfo>();
		try {
			deviceInfo = new SearchDeviceByIPRoaming().searchDeviceByIPRoaming(
					ipNetList, snmpTargetList, usePing, maxLevel,
					maxDeviceSize);
			return removeNullObject(deviceInfo);
		} catch (Exception e) {
			logger.debug(this.getClass().getName()+e.getMessage());
			e.printStackTrace();
		} finally {
			
		}
		return null;
	}
	
	private List<DeviceInfo> removeNullObject(List<DeviceInfo> list){
		List<DeviceInfo> returnData =  new ArrayList<DeviceInfo>();
		for(DeviceInfo deviceInfo: list){
			if(deviceInfo.getDeviceIP()!=null || deviceInfo.getDeviceSysOID()!=null){
				returnData.add(deviceInfo);
			}
		}
		return returnData;
	}

}
