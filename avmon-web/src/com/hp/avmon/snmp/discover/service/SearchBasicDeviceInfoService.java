package com.hp.avmon.snmp.discover.service;

import java.net.InetAddress;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.avmon.snmp.common.snmp.DeviceInfo;
import com.hp.avmon.snmp.common.snmp.SNMPFactory;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;
import com.hp.avmon.snmp.common.snmp.SNMPUtils;
import com.hp.avmon.snmp.mib.MibIfEntry;
import com.hp.avmon.snmp.mib.MibMacIP;
import com.hp.avmon.snmp.mib.MibSystem;

public class SearchBasicDeviceInfoService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SearchBasicDeviceInfoService.class);
	
	public DeviceInfo getDeviceBasicInfo(String ipString,
			List<SNMPTarget> snmpTargetList, boolean pingFlag) {
		DeviceInfo deviceInfo=null;
		try {
			MibSystem mibSystem = null;
			for(SNMPTarget snmpTarget : snmpTargetList){
				SNMPTarget target = new SNMPTarget();
				target.setNodeIP(ipString);
				target.setPort(snmpTarget.getPort());
				target.setReadCommunity(snmpTarget.getReadCommunity());
				target.setSnmpVersion(snmpTarget.getSnmpVersion());
				target.setWriteCommunity(snmpTarget.getWriteCommunity());
				
				try {
					mibSystem = (MibSystem) SNMPFactory.getSNMPAPI()
							.getMibObject(new MibSystem(), target);
					if(mibSystem.getSysObjectID()==null || "".equals(mibSystem.getSysObjectID())){
//						System.out.println("mibSystem:"+mibSystem);
						continue;
//						return null;
					}
					deviceInfo = new DeviceInfo();
					deviceInfo.setDeviceDesc(mibSystem.getSysDescr());
					deviceInfo.setDeviceIP(ipString);
					deviceInfo.setDeviceName(mibSystem.getSysName());
					deviceInfo.setDeviceProtocol("SNMP");
					deviceInfo.setDeviceSysOID(mibSystem.getSysObjectID());
					deviceInfo.setSnmpTarget(target);
					getMainMAC(deviceInfo);
					getInterfaceInfo(deviceInfo);
					getMacIPInfo(deviceInfo);
					getDocFPTableInfo(deviceInfo);
					return deviceInfo;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			if ((pingFlag) && (SNMPUtils.canPing(ipString))) {
				InetAddress address = InetAddress.getByName(ipString);
				
				String hostName = address.getCanonicalHostName();
				deviceInfo = new DeviceInfo();
				deviceInfo.setDeviceIP(ipString);
				deviceInfo.setDeviceName(hostName);
				deviceInfo.setDeviceProtocol("Ping");
				return deviceInfo;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private void getMainMAC(DeviceInfo deviceInfo) {
		try {
			
			String str = SNMPFactory.getSNMPAPI().getOIDValue("1.3.6.1.2.1.17.1.1.0", deviceInfo.getSnmpTarget());
			
			if (str!=null && !str.equals("")){
//				System.out.println("str:"+str);
//				str = SNMPUtils.formatDispayMacAddress(str);
				deviceInfo.setDeviceMAC(str);
				deviceInfo.getDeviceMacList().add(str);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private void getInterfaceInfo(DeviceInfo deviceInfo) {
		try {
			List<MibIfEntry> list = (List<MibIfEntry>)SNMPFactory.getSNMPAPI().getAllTableData(
					MibIfEntry.class, deviceInfo.getSnmpTarget());
			
			for(MibIfEntry mibIfEntry : list){
				String str = mibIfEntry.getIfPhysAddress();
				str = SNMPUtils.formatDispayMacAddress(str);
				mibIfEntry.setIfPhysAddress(str);
				deviceInfo.getIfTableList().add(mibIfEntry);
				if ((str.equals(""))
						|| (deviceInfo.getDeviceMacList().contains(str)))
					continue;
				deviceInfo.getDeviceMacList().add(str);
			}
		} catch (Exception e) {
		}
	}

	@SuppressWarnings("unchecked")
	private void getMacIPInfo(DeviceInfo deviceInfo) {
		try {
			List<MibMacIP> list = SNMPFactory.getSNMPAPI().getAllTableData(
					MibMacIP.class, deviceInfo.getSnmpTarget());
			for(MibMacIP mibMacIP : list){
				if (mibMacIP.getIpNetToMediaType() == 2)
					continue;
//				String macAddress = SNMPUtils.formatDispayMacAddress(mibMacIP.getIpNetToMediaPhysAddress());
				String macAddress = mibMacIP.getIpNetToMediaPhysAddress();
//				System.out.println("macAddress old:"+mibMacIP.getIpNetToMediaPhysAddress());
//				System.out.println("macAddress new:"+macAddress);
				mibMacIP.setIpNetToMediaPhysAddress(macAddress);
				if (macAddress.equals(""))
					continue;
				deviceInfo.getMibMacIPList().add(mibMacIP);
			}
		} catch (Exception e) {
		}
	}
	
	private void getDocFPTableInfo(DeviceInfo paramDeviceInfo){
		
	}

}
