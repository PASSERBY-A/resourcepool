package com.hp.avmon.snmp.common.snmp.impl;

import java.util.List;
import java.util.Vector;

import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;

import org.snmp4j.Snmp;
import org.snmp4j.Target;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.Variable;
import org.snmp4j.smi.VariableBinding;
import org.snmp4j.util.DefaultPDUFactory;
import org.snmp4j.util.TableEvent;
import org.snmp4j.util.TableUtils;

import com.hp.avmon.snmp.common.snmp.SNMPUtils;
import com.hp.avmon.snmp.mib.OMMappingInfo;

public class GetAllAction {
	public static List getAllTableData(Snmp snmp, Target target, Class mibClass)
			throws Exception {

		String oid = SNMPUtils.getMibOIDofClass(mibClass);

		MibValueSymbol mibValueSymbol = SNMPAPIImpl.getInstance()
				.getMibSymbolByOid(oid);

		List<MibValueSymbol> mibValueSymbolList = SNMPUtils.getFieldsInMibNode(
				mibClass, mibValueSymbol);

		
		Vector vectorList = new Vector();
		if (mibValueSymbolList != null && mibValueSymbolList.size() > 0) {
			OID[] allOID = new OID[mibValueSymbolList.size()];
			int i = 0;
			for (MibValueSymbol mibValueSymb : mibValueSymbolList) {
				MibValue mibValue = mibValueSymb.getValue();
				allOID[i++] = new OID(mibValue.toString());
			}

			TableUtils tableUtils = new TableUtils(snmp,
					new DefaultPDUFactory());
			List<TableEvent> tableEventList = tableUtils.getTable(target,
					allOID, null, null);
			Object mibObj = null;
			for (TableEvent tableEvent : tableEventList) {
				if (tableEvent.isError()) {

				}
				if (tableEvent.getException() != null) {

				}
				if (tableEvent.getStatus() != 0) {

				}
				if (tableEvent.getColumns() == null
						|| tableEvent.getColumns().length < 1) {

				}

				mibObj = mibClass.newInstance();

				if ((mibObj instanceof OMMappingInfo)) {
					((OMMappingInfo) mibObj).setTableIndexOID(tableEvent
							.getIndex().toString());
				}
				VariableBinding[] variableBindings = tableEvent.getColumns();
				for (int k = 0; k < variableBindings.length; k++){
					try {
						VariableBinding variableBinding = variableBindings[k];
						if (variableBinding == null)
							continue;
						String currentOID = variableBinding.getOid().toString();
						String symbolName = mibValueSymbol.getMib().getSymbolByOid(currentOID).getName();
						Variable variable = variableBinding.getVariable();
						SNMPUtils.setFieldValue(mibObj, symbolName, variable);
					} catch (Exception localException2) {
						localException2.printStackTrace();
					}
				}
				vectorList.add(mibObj);
			}
		}

		return vectorList;
	}

}
