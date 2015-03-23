package com.hp.avmon.snmp.common.snmp.impl;

import java.util.List;
import java.util.Vector;

import net.percederberg.mibble.MibValueSymbol;

import org.snmp4j.PDU;
import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;

import com.hp.avmon.snmp.common.snmp.SNMPUtils;


/**
 * 
 * @author litan
 *
 */
public class RefreshAction {
	public static void reFresh(Object mibMappingbject, Snmp snmp,
			Target target) throws Exception {
		try {
			String oid = SNMPUtils.getMibOIDofObject(mibMappingbject);
			MibValueSymbol mibValueSymbol = SNMPAPIImpl.getInstance()
					.getMibSymbolByOid(oid);
			
			String tabindexOID = SNMPUtils.getTableOrGroupIndexOID(mibMappingbject, mibValueSymbol);
			
			Class classzz = mibMappingbject.getClass();
			
			List<MibValueSymbol> symbolList = SNMPUtils.getFieldsInMibNode(classzz,mibValueSymbol);
			PDU pdu = new PDU();
			MibValueSymbol symbol =  null;
			for (int i = 0; i < symbolList.size(); i++) {
				symbol = (MibValueSymbol) symbolList.get(i);
				String newoid = symbol.getValue()
						.toString() + "." + tabindexOID;
				pdu.add(new VariableBinding(new OID(newoid)));
			}
			ResponseEvent localResponseEvent = snmp.get(pdu,target);
			
			Vector vector = localResponseEvent.getResponse()
					.getVariableBindings();
			for (int j = 0; j < vector.size(); j++) {
				VariableBinding variableBinding = (VariableBinding) vector.get(j);
				String newoid = variableBinding.getOid().toString();
				String mibName = mibValueSymbol.getMib()
						.getSymbolByOid(newoid).getName();
				Variable variable = variableBinding.getVariable();
				if (variable.toString().equalsIgnoreCase("noSuchObject")) {
					throw new Exception();
				}
				SNMPUtils.setFieldValue(mibMappingbject, mibName, variable);
			}
		} catch (Exception localException) {

		}
	}
}
