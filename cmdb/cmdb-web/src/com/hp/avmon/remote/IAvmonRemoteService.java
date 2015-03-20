package com.hp.avmon.remote;

import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

public interface IAvmonRemoteService {
	public void test() throws RemoteException;

	public List<Alert> getHighAlertByCiList(List<String> ciNames, String kpiCode)
			throws RemoteException;

	public List<Alert> getHighAlertByCiList_single(List<String> ciNames,
			String kpiCode) throws RemoteException;

	public List<Alert> getAlertByCi(String ciName, String kpiCode,
			Timestamp start, Timestamp end) throws RemoteException;

	public List<Alert> getAlertByCi(String ciName, String kpiCode, int days)
			throws RemoteException;

	public List<Alert> getRecentAlertByCi(String ciName, String kpiCode,
			int rows) throws RemoteException;

	public List<KpiDefine> getKpiDefine() throws RemoteException;

	public KpiValue getCurKpi(String ciName, String ciKpiCode,
			String viewKpiCode) throws RemoteException;

	public List<KpiValue> getCurKpi(List<String> ciName, String ciKpiCode,
			String viewKpiCode) throws RemoteException;

	public List<KpiValue> getHisKpi(String ciName, String ciKpiCode,
			String viewKpiCode, Timestamp beginTime, Timestamp endTime)
			throws RemoteException;

	public List<Alert> getHighAlertByView_single(String viewName)
			throws RemoteException;

	public List<KpiValue> getViewCurKpi(String ciName, String ciKpiCode,
			String ciType) throws RemoteException;

	/**
	 * 
	 得到特定功能 应用的kpi 功能1：得到主机通断kpiValue CiName: 主机名称（批量） CiKpiCode: exchangeId
	 * (1002001001) viewFunc: osPing
	 * 
	 * 功能2：得到主机主备状态kpiValue CiName: 主机名称（批量） CiKpiCode: exchangeId (1002001001)
	 * viewFunc: bakStatus
	 * 
	 * @param ciNames
	 * @param ciKpiCode
	 * @param viewFunc
	 * @return
	 * @throws RemoteException
	 */
	public List<KpiValue> getViewFuncCurKpi(List<String> ciNames,
			String ciKpiCode, String viewFunc) throws RemoteException;
	
	/**
	* 获得指定设备的告警总数
	**/
	public int getAlertCountByCi(String ciName, String kpiCode) throws RemoteException;
	/**
	* 获得指定设备的各级别 告警数量
	   Map<级别，数量>
	**/
	public Map<String,Integer> getAlertLevelCountByCi(String ciName, String kpiCode) throws RemoteException;
	/**
	* 获得指定设备的多实例kpi列表
	 * @throws RemoteException 
	**/
	public List<KpiValue> getCurInstanceKpi(String ciName,String ciKpiCode,String viewKpiCode) throws RemoteException;

}
