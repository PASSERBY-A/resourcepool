package com.hp.avmon.snmp.common.snmp.impl;

import java.io.IOException;

import org.snmp4j.CommunityTarget;
import org.snmp4j.Snmp;
import org.snmp4j.smi.Address;
import org.snmp4j.smi.GenericAddress;
import org.snmp4j.smi.OctetString;
import org.snmp4j.transport.DefaultUdpTransportMapping;

import com.hp.avmon.snmp.common.PropUtil;
import com.hp.avmon.snmp.common.snmp.SNMPTarget;

public class SNMPComunication {
	public static Snmp singleSnmp = null;
	public static Snmp getOIDSnmp = null;

	public static Snmp getSession() {
		try {
			
			DefaultUdpTransportMapping udpTransportMap = new DefaultUdpTransportMapping();
			udpTransportMap.listen();
			singleSnmp = new Snmp(udpTransportMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return singleSnmp;
	}

	public static Snmp getSessionOfGetOID() {
		try {
			DefaultUdpTransportMapping udpTransportMap = new DefaultUdpTransportMapping();
			udpTransportMap.listen();
			getOIDSnmp = new Snmp(udpTransportMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getOIDSnmp;
	}

	public static Snmp getSafeSession() {
		return getSession();
	}

	public static void realeseSnmp(Snmp snmp) {
		if (snmp != null) {
			try {
				snmp.close();
			} catch (IOException e) {
				snmp = null;
			}
		}
	}

	public static CommunityTarget getTarget(SNMPTarget snmpTarget,
			boolean isWrite) {
		CommunityTarget communityTarget = new CommunityTarget();
		communityTarget.setCommunity(new OctetString(
				snmpTarget.getReadCommunity()));
		if (isWrite)
			communityTarget.setCommunity(new OctetString(
					snmpTarget.getWriteCommunity()));
		Address address = GenericAddress.parse("udp:"
				+ snmpTarget.getNodeIP() + "/" + snmpTarget.getPort());
		communityTarget.setAddress(address);
		communityTarget.setRetries(PropUtil.getInt("config",
				"config.snmp.retry.time"));
		communityTarget.setTimeout(PropUtil.getLong("config",
				"config.snmp.timeout"));
		communityTarget.setVersion(snmpTarget.getSnmpVersion());
		
		return communityTarget;
	}
}
