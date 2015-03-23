package com.hp.avmon.snmp.common.snmp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.avmon.snmp.common.snmp.impl.SNMPAPIImpl;

public class SNMPFactory {
	private static final Logger logger = LoggerFactory
			.getLogger(SNMPFactory.class);
	
	private static SNMPAPI snmpapi;

	public static void init()
			throws Exception {
		if (snmpapi != null)
			return;
		try {
			snmpapi = SNMPAPIImpl.getInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void loadMib(String paramString) throws Exception {
//		 SNMPAPIImpl.getInstance().loadMib(paramString);
	}

	public static SNMPAPI getSNMPAPI() throws Exception {
		if (snmpapi == null)
			logger.debug("SNMPAPI is NULL, Please check call init() first.");
		return snmpapi;
	}
}
