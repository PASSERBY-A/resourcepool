package com.hp.avmon.snmp.common.snmp.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

/**
 *  OID value
 * 
 *  @author litan
 * 
 */
public class GetOIDValueAction {

	private static final Logger logger = LoggerFactory.getLogger(GetOIDValueAction.class);

	public static Variable getValue(String oid, Snmp snmp, Target target)
			throws Exception {
		try {
			PDU pdu = new PDU();
			pdu.add(new VariableBinding(new OID(oid)));
			ResponseEvent responseEvent = snmp.get(pdu, target);

			PDU pdu1 = responseEvent.getResponse();
			if (pdu1.size() > 0) {
				VariableBinding variableBinding = pdu1.get(0);

				Variable variable = variableBinding.getVariable();
				String str = variable.toString();
				if ((str.equalsIgnoreCase("noSuchInstance"))
						|| (str.equalsIgnoreCase("No such name"))
						|| (str.equalsIgnoreCase("noSuchObject"))) {
					
//					logger.info("No such object");
//					throw new Exception();
					return null;
				}
				return variable;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
