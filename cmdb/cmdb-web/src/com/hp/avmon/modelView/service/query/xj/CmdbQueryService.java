package com.hp.avmon.modelView.service.query.xj;

import java.util.List;

import com.hp.xo.uip.cmdb.domain.Node;
import com.hp.xo.uip.cmdb.exception.CmdbException;

public interface CmdbQueryService {
//	switch - zone - hba&host/storagePort&storage
//	host 列表 - hba (根据 hostName-ip 等查找)
//	          - disk -vg/storage 
//	hba  列表 -host
//	          -zone
//	存储  - 主机 - ibm设备链接的主机， emc设备链接的主机	          
//	通用查询
	/**
     * MACHINE_NET_IP=系统网络接口IP地址
     * CSComputerName=主机名
     * Host:10.238.159.70:7604875155558999
     * @return
     * @throws CmdbException 
     * 主机-磁盘
     * 主机-hba-zone-storageNetPort-storage
     */
	public List<Node> getHost(String cscomputername,String machine_net_ip) throws CmdbException;
	
	/**
	 * 查询hba卡
	 * 系统属性  name:10.238.230.29_/dev/fcd2, id:7604875155349990, rowKey:HostHba_7604875155349990
	 * 扩展属性 举例：
	 * HBA卡名称	 HBA_NAME : /dev/fcd0
   	   HBA卡位置	HBA_LOCATION:4
	   HBA卡端口速率 HBA_SPEED:	2Gb
	   HBA卡所属主机名 HBA_HOSTNAME:	xjscm02
	   HBA卡端口号 HBA_WWPN:	0x5001438001312666
	 * @throws CmdbException 
	 */
	public List<Node> getHostHba(String hba_hostname,String hba_wwpn,String hba_name) throws CmdbException; 
	
	/**
     * 获得zone 及关联信息
     * @param zoneName
     * @param zoneConfig
     * @return
     * @throws CmdbException
     */
	public List<Node> getSanZone(Long sanSwitchID,String zoneName,String zoneConfig) throws CmdbException;

	/**
	 * 获得存储以及关联信息
	 * @param storageName
	 * @return
	 * @throws CmdbException
	 */
	public List<Node> getStorage(String storageName) throws CmdbException;
}
