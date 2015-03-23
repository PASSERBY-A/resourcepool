package com.hp.avmon.snmp.common.snmp.impl;

import java.util.List;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibValueSymbol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.smi.UdpAddress;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.hp.avmon.snmp.common.PropUtil;
import com.hp.avmon.snmp.common.snmp.SNMPAPI;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;
/**
 * 
 * @author litan
 *
 */
public class SNMPAPIImpl extends SNMPAPI {

	private static final Logger logger = LoggerFactory
			.getLogger(SNMPAPIImpl.class);

	private static SNMPAPIImpl impl;

	private MibLoader mibload = new MibLoader();

	public static SNMPAPIImpl getInstance() throws Exception {
		if (impl == null) {
			impl = new SNMPAPIImpl();
			impl.init();
		}
		return impl;
	}

	private void init() throws Exception {
		try {
			this.mibload.addResourceDir("mibs");
			this.mibload.load("RFC1213-MIB");
			this.mibload.load("HOST-RESOURCES-MIB");
			this.mibload.load("BRIDGE-MIB");
//			SNMPAPIImpl.startTrapServer();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
	}

	public static void startTrapServer() {
		try {
			logger.debug("Starting SNMP Trap Receiver......");
			UdpAddress udpAddress = new UdpAddress(PropUtil.getInt("config",
					"config.snmp.trap.port"));
			DefaultUdpTransportMapping udpTransportMapping = new DefaultUdpTransportMapping(
					udpAddress);
			Snmp snmp = new Snmp(udpTransportMapping);
			// snmp.addCommandResponder();
			snmp.listen();
			logger.debug("Started SNMP TRAP Receiver OK!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("Staring SNMP TRAP Erro:" + e.getMessage());
		}
	}

	@Override
	public void checkSnmpAgentActive(SNMPTarget snmpTarget) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOIDValue(String paramString1, String paramString2,
			String paramString3, SNMPTarget paramSNMPTarget) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getOIDValue(String oid, SNMPTarget snmpTarget) {
		try {
			Snmp snmp = SNMPComunication.getSessionOfGetOID();
			CommunityTarget communityTarget = SNMPComunication.getTarget(
					snmpTarget, false);
			if (communityTarget.getRetries() == 0) {
				communityTarget.setRetries(1);
			}
			String str = GetOIDValueAction.getValue(oid, snmp, communityTarget)
					.toString();
			return str;
		} catch (Exception e) {
//			e.printStackTrace();
		}
		return null;
	}

	@Override
	public long getOIDLongValue(String oid, SNMPTarget snmpTarget) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getNextOIDValue(String oid, SNMPTarget snmpTarget) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object getMibObject(Object mibMappingObject, SNMPTarget snmpTarget) {
		Snmp snmp = SNMPComunication.getSession();
	    try {
			RefreshAction.reFresh(mibMappingObject, snmp, SNMPComunication.getTarget(snmpTarget, false));
		} catch (Exception e) {
			e.printStackTrace();
		}
	    return mibMappingObject;
	}

	@Override
	public void update(Object paramObject, SNMPTarget paramSNMPTarget) {
		// TODO Auto-generated method stub

	}

	@Override
	public List getAllTableData(Class mibClazz, SNMPTarget SNMPTarget) {
		
		Snmp snmp = SNMPComunication.getSafeSession();
		try {
			List listData = GetAllAction.getAllTableData(snmp,
					SNMPComunication.getTarget(SNMPTarget, false), mibClazz);
			return listData;
		} catch (Exception e) {

		} finally {
			SNMPComunication.realeseSnmp(snmp);
		}
		return null;
	}

	@Override
	public List<List<String>> getAllOIDTableData(SNMPTarget snmpTarget,
			List<String> paramList) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addTableRow(Object paramObject, SNMPTarget snmpTarget) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delTableRow(Object paramObject, SNMPTarget snmpTarget)
			throws Exception {
		// TODO Auto-generated method stub

	}

	// ///////////////////begin special ...tanlin
	public MibValueSymbol getMibSymbol(String name) throws Exception {
		Mib[] mibs = this.mibload.getAllMibs();
		MibValueSymbol mibValueSymbol = null;
		for (int i = 0; i < mibs.length; i++) {
			mibValueSymbol = (MibValueSymbol) mibs[i].getSymbol(name);
			if (mibValueSymbol != null)
				break;
		}
		if (mibValueSymbol == null) {

		}
		return mibValueSymbol;
	}

	public MibValueSymbol getMibSymbolByOid(String oid) throws Exception {
		if (oid.startsWith("."))
			oid = oid.replaceFirst(".", "");
		Mib[] mibs = this.mibload.getAllMibs();
		MibValueSymbol mibValueSymbol = null;
		for (int i = 0; i < mibs.length; i++) {
			mibValueSymbol = mibs[i].getSymbolByValue(oid);
			if (mibValueSymbol != null)
				break;
		}
		if (mibValueSymbol == null) {

		}
		return mibValueSymbol;
	}
	// /////////end
}
