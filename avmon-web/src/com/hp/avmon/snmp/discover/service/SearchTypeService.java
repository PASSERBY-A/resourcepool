package com.hp.avmon.snmp.discover.service;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.avmon.snmp.common.snmp.DeviceInfo;
import com.hp.avmon.snmp.common.snmp.DeviceTypeInfo;
import com.hp.avmon.snmp.common.snmp.SNMPFactory;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;
import com.hp.avmon.snmp.discover.DiscoverConfig;
import com.hp.avmon.snmp.mib.MibSoftwareRunEntry;
import com.hp.avmon.snmp.mib.MibSystem;

public class SearchTypeService {
	
	private static final Logger logger = LoggerFactory
			.getLogger(SearchTypeService.class);
	
	public DeviceTypeInfo matchDeviceType(DeviceInfo deviceInfo) {
		List<DeviceTypeInfo> deviceTypeInfoList = DiscoverConfig.getAllDeviceType();
		Iterator<DeviceTypeInfo> iterator = deviceTypeInfoList.iterator();
		DeviceTypeInfo deviceTypeInfo;
		
		logger.debug("需要匹配的原sysOID："+deviceInfo.getDeviceSysOID());
		//通过OIDCharacter类型查查设备类型
		while (iterator.hasNext()) {
			deviceTypeInfo = iterator.next();
			List<String> sysObjIdList = deviceTypeInfo.getSysObjectID();
			if (sysObjIdList!=null && sysObjIdList.size()>0) {
				for(String typeOID : sysObjIdList){
					String sysObjId = deviceInfo.getDeviceSysOID();
					
//					logger.debug("需要匹配的原sysOID："+deviceInfo.getDeviceSysOID());
//					logger.debug("Type table sys_obj_id ："+typeOID);
					// modify by mark 2014-12-2 start 删除掉  || (sysObjId.indexOf(typeOID) >= 0)
					if ((typeOID!=null && !typeOID.equals(""))&& (sysObjId.equals(typeOID))){
					// modify by mark 2014-12-2 end
						logger.debug("has got the type");
						return deviceTypeInfo;
					}
				}
			}
		}
		//通过描述查找设备类型
 		while (iterator.hasNext()) {
			deviceTypeInfo = iterator.next();
			List<String> typeOIDDescList = deviceTypeInfo.getSysDescr();
			if (typeOIDDescList!=null && typeOIDDescList.size()>0) {
				for(String typeDesc : typeOIDDescList){
					if ((typeDesc!=null && !typeDesc.equals(""))&& (deviceInfo.getDeviceDesc().toLowerCase().indexOf(typeDesc) >= 0))
						return deviceTypeInfo;
				}
			}
		}
 		DeviceTypeInfo deviceType =  new DeviceTypeInfo();
		//通过具体的路由器或交换机的具体oid分析出设备类型
		if ((deviceInfo.getDeviceProtocol().equalsIgnoreCase("SNMP"))
				&& (deviceInfo.getSnmpTarget() != null)) {
			SNMPTarget snmpTarget   = deviceInfo.getSnmpTarget();
			String routerOidValue = null;
			String switchTypeOidValue = null;
			
			MibSystem mibSystem = null;
			
			try {
				 mibSystem = (MibSystem) SNMPFactory.getSNMPAPI()
							.getMibObject(new MibSystem(), snmpTarget);
				 
				routerOidValue = SNMPFactory.getSNMPAPI().getOIDValue(".1.3.6.1.2.1.4.1.0", snmpTarget);
			} catch (Exception e) {
				//other type
				logger.error("get the device type error.step : routerOid");
				e.printStackTrace();
			}
			deviceType.setId(UUID.randomUUID().toString().replace("-", ""));
			deviceType.setFamily(mibSystem.getSysDescr());
			deviceType.getSysDescr().add(mibSystem.getSysDescr());
			deviceType.getSysObjectID().add(mibSystem.getSysObjectID());
			try {
				switchTypeOidValue = SNMPFactory.getSNMPAPI().getOIDValue(".1.3.6.1.2.1.17.2.1.0", snmpTarget);
			} catch (Exception e) {
				logger.error("get the device type error.step : switch");
				e.printStackTrace();
			}
			if ((switchTypeOidValue != null) && (!switchTypeOidValue.equalsIgnoreCase("noSuchObject"))
					&& (!switchTypeOidValue.equalsIgnoreCase("noSuchInstance"))) {
				if ((routerOidValue != null) && (routerOidValue.equals("1"))){
					deviceType.setType(DeviceTypeInfo.isRouterType);
					deviceType.setLogicType(DeviceTypeInfo.isRouterType);
					DiscoverConfig.addDeviceType(deviceType);
					return deviceType;
				}
				deviceType.setType(DeviceTypeInfo.isSwitchType);
				deviceType.setLogicType(DeviceTypeInfo.isSwitchType);
				DiscoverConfig.addDeviceType(deviceType);
				return deviceType;
			}
			if ((routerOidValue != null) && (routerOidValue.equals("1"))){
				deviceType.setType(DeviceTypeInfo.isRouterType);
				deviceType.setLogicType(DeviceTypeInfo.isRouterType);
				DiscoverConfig.addDeviceType(deviceType);
				return deviceType;
			}
			
			try {
				List list = SNMPFactory.getSNMPAPI().getAllTableData(
						MibSoftwareRunEntry.class, snmpTarget);
				if (list!=null && list.size() > 0){
					deviceType.setType(DeviceTypeInfo.isComputerType);
					deviceType.setLogicType(DeviceTypeInfo.isComputerType);
					DiscoverConfig.addDeviceType(deviceType);
					return deviceType;
				}
			} catch (Exception e) {
				logger.error("get the device type error.step : computerType");
				e.printStackTrace();
			}
		}
		DeviceTypeInfo.UnkownTypeInfo.setFamily(deviceType.getFamily());
		DeviceTypeInfo.UnkownTypeInfo.getSysObjectID().addAll(deviceType.getSysObjectID());
		return DeviceTypeInfo.UnkownTypeInfo;
	}
}
