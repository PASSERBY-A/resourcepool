package com.hp.avmonserver.api;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hp.avmonserver.entity.Alarm;
import com.hp.avmonserver.entity.AlarmComment;
import com.hp.avmonserver.entity.AvmonServerConfig;
import com.hp.avmonserver.entity.KpiEvent;
import com.hp.avmonserver.entity.KpiInfo;
import com.hp.avmonserver.entity.MO;
import com.hp.avmonserver.entity.MOStatus;



public interface IRemoteService extends java.rmi.Remote{
    
    public String deployAmpPackage(String agentId,String ampInstId) throws RemoteException;

    public String deployAmpSchedule(String agentId,String ampInstId,String nodeKey,String kpiCode,String instance,String schedule) throws RemoteException;

    public String deployAmpSchedule(String agentId,String ampInstId) throws RemoteException;

    public String deployAmpConfig(String agentId,String ampInstId) throws RemoteException;

    public String startAgent(String agentId) throws RemoteException;

    public String stopAgent(String agentId) throws RemoteException;

    public String startAmp(String agentId,String ampInstId) throws RemoteException;

    public String stopAmp(String agentId,String ampInstId) throws RemoteException;

    public String operationAgentByStatus(String agentId,String ampId,String status) throws RemoteException;

    public String updateCache(String cacheType) throws RemoteException;

    public String createMo(MO mo) throws RemoteException;

    public String removeMo(String moId) throws RemoteException;
    
    public String queryVMHostList(String agentId,String ampInstId) throws RemoteException;
    
    public String sendCmdToAgent(String agentId, String ampInstId,String cmdCode) throws RemoteException ;
    
    public boolean newAlarm(Alarm alarm) throws RemoteException;
    
    public String updateAlarm(Alarm alarm) throws RemoteException;
    
    public String closeAlarm(Alarm alarm) throws RemoteException;
    
	public Alarm getAlarmById(String alarmId)throws RemoteException;

    public String addAlarmComments(String alarmId, String userId, String content, String contentType)throws RemoteException;

    public MO getMoById(String moId) throws RemoteException;

    public void heartBeat() throws RemoteException;
    
    public String  deployScheduleTask(String agentId) throws RemoteException;
    
    public List<byte[]> sendInstallFileContent(String id) throws RemoteException;
    
    public String testFileSend() throws RemoteException;

    public String upgradeAgent(String agentId)throws RemoteException;

    public Map execute(String operation, Map params)throws RemoteException;

    public boolean registerServer(AvmonServerConfig serverInstance) throws RemoteException;

    public AvmonServerConfig queryAlarmServer()throws RemoteException;

    public List<AvmonServerConfig> getAvmonServerList()throws RemoteException;

    public String serverUpdateCache(String cacheType)throws RemoteException;

	public List<String> queryNewAlarmIdList(Date d) throws RemoteException;
	
	public KpiEvent getKpiEvent(String moId, String kpiCode, String instance)throws RemoteException;

	public List<KpiEvent> getKpiEventList(String moId, String kpiCode)throws RemoteException;

	public List<KpiEvent> getKpiEventList(String moId)throws RemoteException;

	public void exchangeAlarms(String avmonServerId, List<Alarm> alarmList)throws RemoteException;

	public void exchangeKpiEvents(String avmonServerId, List<KpiEvent> kpiList)throws RemoteException;

	public List<KpiEvent> fetchKpiEvents(String avmonServerId)throws RemoteException;

	public List<Alarm> fetchAlarms(String avmonServerId)throws RemoteException;

	public boolean applyMoOwner(String avmonServerId, String moId)throws RemoteException;

	public void stopServer()throws RemoteException;

	public Map<String, AvmonServerConfig> getMoServerMap()throws RemoteException;
	
	//interface for junhui

	public List<Alarm> getHighAlertByCiList(List<String> ciNameList,String ciKpiCode)throws RemoteException;
	
	public List<Alarm> getAlertByCi(String ciName,String ciKpiCode,Date beginTime,Date endTime)throws RemoteException;

	public List<Alarm> getAlertByCi(String ciName,String ciKpiCode,int rows)throws RemoteException;
	
	public List<KpiInfo> getKpiDefine()throws RemoteException;
	
	public KpiEvent getCurKpi(String ciName,String ciKpiCode,String viewKpiCode)throws RemoteException;
	
	public List<KpiEvent> getHisKpi(String ciName,String ciKpiCode,String viewKpiCode,Date beginTime,Date endTime)throws RemoteException;

	public int getAlertCountByCi(String ciName, String kpiCode) throws RemoteException;
	
	public Map<String,Integer> getAlertLevelCountByCi(String ciName, String kpiCode) throws RemoteException;
	
	public List<KpiEvent> getCurInstanceKpi(String ciName,String ciKpiCode,String viewKpiCode)throws RemoteException;
	
	//end for junhui
}
