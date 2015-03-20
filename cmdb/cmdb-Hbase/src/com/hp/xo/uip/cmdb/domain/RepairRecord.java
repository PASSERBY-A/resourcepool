package com.hp.xo.uip.cmdb.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

public class RepairRecord implements Serializable{

	private static final long serialVersionUID = -3674102737773748124L;

	    //维修单号（选填），损坏配件序列号：（选填），配件类型（选填），
	    //更换配件序列号（选填），维修日期：（必填），
	    //维修类型：分为“硬件更换”、“软件服务”，“技术咨询”（必填），
	    //MA合同号（选填），日期：（必填）
	
	//rowKey: nodeKey_id ,指定表 cmdb_repair 
	private Long id;
	//nodeKey: nodeType_nodeId ,
	private String nodeKey;
	private String repairNum;
	private String damageDeviceNum;
	private String deviceType;
	private String replaceDeviceNum;	
	private Timestamp repairDate;
	private String repairType;
	private String maNum;
	private Timestamp maDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public String getRepairNum() {
		return repairNum;
	}

	public void setRepairNum(String repairNum) {
		this.repairNum = repairNum;
	}

	public String getDamageDeviceNum() {
		return damageDeviceNum;
	}

	public void setDamageDeviceNum(String damageDeviceNum) {
		this.damageDeviceNum = damageDeviceNum;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getReplaceDeviceNum() {
		return replaceDeviceNum;
	}

	public void setReplaceDeviceNum(String replaceDeviceNum) {
		this.replaceDeviceNum = replaceDeviceNum;
	}

	public Timestamp getRepairDate() {
		return repairDate;
	}

	public void setRepairDate(Timestamp repairDate) {
		this.repairDate = repairDate;
	}

	public String getRepairType() {
		return repairType;
	}

	public void setRepairType(String repairType) {
		this.repairType = repairType;
	}

	public String getMaNum() {
		return maNum;
	}

	public void setMaNum(String maNum) {
		this.maNum = maNum;
	}

	public Timestamp getMaDate() {
		return maDate;
	}

	public void setMaDate(Timestamp maDate) {
		this.maDate = maDate;
	}

	public Map<String, String> getMapData() {
		Map<String, String> ma=new HashMap<String,String>();
		ma.put("id", String.valueOf(id));ma.put("nodeKey", nodeKey);
		ma.put("repairNum", repairNum);
		ma.put("damageDeviceNum", damageDeviceNum);
		ma.put("deviceType", deviceType);
		ma.put("replaceDeviceNum", replaceDeviceNum);
		ma.put("repairDate", String.valueOf(repairDate==null?System.currentTimeMillis():repairDate.getTime()));
		ma.put("repairType", repairType);
		ma.put("maNum", maNum);
		ma.put("maDate", String.valueOf(maDate==null?System.currentTimeMillis():maDate.getTime()));
		return ma;
	}

	
}
