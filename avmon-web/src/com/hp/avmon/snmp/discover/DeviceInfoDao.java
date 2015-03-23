package com.hp.avmon.snmp.discover;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.hp.avmon.snmp.common.snmp.DeviceInfo;
import com.hp.avmon.snmp.common.snmp.DeviceTypeInfo;
import com.hp.avmon.utils.DBUtils;

@Service("deviceInfoDao")
public class DeviceInfoDao {
	
	private static final Logger logger = LoggerFactory
			.getLogger(DeviceInfoDao.class);
	
	
	public static final String MONITOR_STATUS_INIT="1";
	public static final String MONITOR_STATUS_MONITORED="2";
	public static final String MONITOR_STATUS_DELETE="3";
	
	private JdbcTemplate jdbcTemplate;
	@Autowired
	public void setDataSource(@Qualifier("jdbcTemplate") JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	
	public String save(DeviceInfo deviceInfo){
		
		if(deviceInfo.getDeviceIP()!=null && deviceInfo.getDeviceName()!=null && isExist(deviceInfo)){
			logger.debug("has exist this device in the database! ip:"+deviceInfo.getDeviceIP());
			return null;
		}
		
		String deviceIP =  deviceInfo.getDeviceIP()==null?"":deviceInfo.getDeviceIP();
		String deviceMAC =  deviceInfo.getDeviceMAC()==null?"":deviceInfo.getDeviceMAC();
		String deviceName =  deviceInfo.getDeviceName()==null?"":deviceInfo.getDeviceName();
		String deviceDesc =  deviceInfo.getDeviceDesc()==null?"":deviceInfo.getDeviceDesc();
		String deviceProtocol =  deviceInfo.getDeviceProtocol()==null?"":deviceInfo.getDeviceProtocol();
		String deviceSysOID =  deviceInfo.getDeviceSysOID()==null?"":deviceInfo.getDeviceSysOID();
		String typeId = "";
		if(deviceInfo.getDeviceType()==null||deviceInfo.getDeviceType().getLogicType()==null){
			typeId="";
		}else{
			typeId =  deviceInfo.getDeviceType().getId();
		}
		String deviceId = UUID.randomUUID().toString().replace("-", "");
		 
		String insertSQL = String.format("insert into td_avmon_discovery_device_info(device_id,device_ip,device_mac,device_name,device_desc,protocol,sysoid,device_type,monitor_status,create_dt) "
				+ "values ('%s','%s','%s','%s','%s','%s','%s','%s','%s',%s)",
				deviceId,
				deviceIP,
				deviceMAC,
				deviceName,
				deviceDesc,
				deviceProtocol,
				deviceSysOID,
				typeId,
				MONITOR_STATUS_INIT,
				DBUtils.getDbSysdateKeyword()
				);
		jdbcTemplate.execute(insertSQL);
		return deviceId;
	}
	
	public List<String> saveList(List<DeviceInfo> deviceInfoList){
		List<String> list = new ArrayList<String>();
		if(deviceInfoList!=null && deviceInfoList.size()>0){
			for(DeviceInfo deviceInfo : deviceInfoList){
				list.add(this.save(deviceInfo));
			}
		}
		return list;
	}
	
	public boolean isExist(DeviceInfo deviceInfo){
		String selectSQL = "select count(1) from td_avmon_discovery_device_info where device_ip='"+deviceInfo.getDeviceIP()+"' "
				+ "and device_name='"+deviceInfo.getDeviceName()+"'";
//		logger.debug("selectSQL:"+selectSQL);
		int returnData = jdbcTemplate.queryForInt(selectSQL);
		if(returnData>0){
			return true;
		}
		return false;
	}
	
	
	public List<DeviceTypeInfo> getDeviceTypeInfoList(){
		
		String selectSQL = "select * from td_avmon_device_type_info where sys_obj_id is not null";
		
		List<Map<String,Object>> returnData=jdbcTemplate.queryForList(selectSQL);
		List<DeviceTypeInfo> typeInfoList = new ArrayList<DeviceTypeInfo>();
        if(returnData!=null && returnData.size()>0){
        	for(Map<String,Object> map : returnData){
        		DeviceTypeInfo typeInfo =  new DeviceTypeInfo();
        		typeInfo.setId((String) map.get("ID"));
	            typeInfo.setVender((String)map.get("VENDER"));
	            typeInfo.setFamily((String)map.get("FAMILY"));
	            typeInfo.setType((String)map.get("TYPE"));
	            typeInfo.getSysObjectID().add((String)map.get("SYS_OBJ_ID"));
	            typeInfo.getSysDescr().add((String)map.get("FAMILY"));
	            typeInfoList.add(typeInfo);
	            DiscoverConfig.addDeviceType(typeInfo);
        	}
        }
		return typeInfoList;
	}
}
