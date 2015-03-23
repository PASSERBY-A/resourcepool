package com.hp.avmon.snmp.common.snmp;

import java.util.List;



public abstract class SNMPAPI {
	public static final int RowStatusEntryActive = 1;
	public static final int RowStatusEntryAdd = 4;
	public static final int RowStatusEntryDel = 6;
	public static final String MessageNoSuchOID = "SNMPAPI.0";
	public static final String MessageSNMPTimeOut = "SNMPAPI.1";

	public abstract void checkSnmpAgentActive(SNMPTarget paramSNMPTarget);

	public abstract void setOIDValue(String paramString1, String paramString2,
			String paramString3, SNMPTarget paramSNMPTarget);

	public abstract String getOIDValue(String paramString,
			SNMPTarget paramSNMPTarget);

	public abstract long getOIDLongValue(String paramString,
			SNMPTarget paramSNMPTarget);

	public abstract String getNextOIDValue(String paramString,
			SNMPTarget paramSNMPTarget);

	public abstract Object getMibObject(Object mibObject,
			SNMPTarget paramSNMPTarget);

	public abstract void update(Object paramObject, SNMPTarget paramSNMPTarget);

	public abstract List getAllTableData(Class paramClass,
			SNMPTarget paramSNMPTarget);

	public abstract List<List<String>> getAllOIDTableData(
			SNMPTarget paramSNMPTarget, List<String> paramList)
			throws Exception;

	public abstract void addTableRow(Object paramObject,
			SNMPTarget paramSNMPTarget);

	public abstract void delTableRow(Object paramObject,
			SNMPTarget paramSNMPTarget) throws Exception;
}
