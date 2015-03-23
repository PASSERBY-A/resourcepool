package com.hp.avmon.snmp.common.snmp;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import net.percederberg.mibble.MibType;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.snmp.SnmpIndex;
import net.percederberg.mibble.snmp.SnmpObjectType;
import net.percederberg.mibble.value.ObjectIdentifierValue;

import org.snmp4j.event.ResponseEvent;
import org.snmp4j.smi.Counter32;
import org.snmp4j.smi.Counter64;
import org.snmp4j.smi.Gauge32;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.IpAddress;
import org.snmp4j.smi.Null;
import org.snmp4j.smi.OID;
import org.snmp4j.smi.OctetString;
import org.snmp4j.smi.Opaque;
import org.snmp4j.smi.TimeTicks;
import org.snmp4j.smi.Variable;

import com.hp.avmon.snmp.mib.OMMappingInfo;

public class SNMPUtils {
	public static String formatCNString(String paramString) {
		if ((paramString == null) || (paramString.equals(""))
				|| (paramString.equalsIgnoreCase("null")))
			return "";
		try {
			String str = new String(paramString.getBytes("iso8859_1"), "GB2312");
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static boolean canPing(String host) {
		try {
			InetAddress inetAddress = InetAddress.getByName(host);
			return inetAddress.isReachable(2000);
		} catch (Exception e) {

		}
		return false;
	}

	public static String formatDispayMacAddress(String strMac) {
		if ((strMac == null) || (strMac.equals(""))
				|| (strMac.equalsIgnoreCase("null")))
			return "";

		try {
			OctetString octetString = new OctetString(
					strMac.getBytes("iso8859_1"));
			return octetString.toHexString();
		} catch (Exception localException) {
			localException.printStackTrace();
		}
		return "";
	}

	public static String getMibOIDofObject(Object omMappingInfoObj)
			throws Exception {
		try {
			return ((OMMappingInfo) omMappingInfoObj).getMappingOID();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getMibOIDofClass(Class omMappingInfoClass)
			throws Exception {
		try {
			OMMappingInfo obj = (OMMappingInfo) omMappingInfoClass
					.newInstance();
			return obj.getMappingOID();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<MibValueSymbol> getFieldsInMibNode(Class mibClass,
			MibValueSymbol mibValueSymbol) {
		Field[] fields = mibClass.getDeclaredFields();
		List<MibValueSymbol> list = new ArrayList<MibValueSymbol>();
		for (Field field : fields) {
			String str = field.getName();
			MibValueSymbol[] mibValueSymbols = mibValueSymbol.getChildren();
			for (MibValueSymbol mibValue : mibValueSymbols) {
				if (!mibValue.getName().equals(str))
					continue;
				list.add(mibValue);
				break;
			}
		}
		return list;
	}

	public static void checkSNMPErro(ResponseEvent responseEvent) {

	}

	public static String getTableOrGroupIndexOID(Object mappingObj,
			MibValueSymbol mibValueSymbol) throws Exception {

		if (!mibValueSymbol.isTableRow())
			return "0";

		String tabindexOID = "";
		if ((mappingObj instanceof OMMappingInfo)) {
			tabindexOID = ((OMMappingInfo) mappingObj).getTableIndexOID();
			if ((tabindexOID != null) && (!tabindexOID.equals("")))
				return tabindexOID;
		}

		String newOid = "";
		ArrayList list = ((SnmpObjectType) mibValueSymbol.getType()).getIndex();

		if (list != null && list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				SnmpIndex snmpIndex = (SnmpIndex) list.get(i);
				ObjectIdentifierValue identifierValue = (ObjectIdentifierValue) snmpIndex
						.getValue();
				String name = identifierValue.getName();
				newOid = newOid
						+ getFieldValue(mappingObj, name,
								((SnmpObjectType) identifierValue.getSymbol()
										.getType()).getSyntax());
			}
		}
		return newOid;
	}

	public static Variable getFieldValue(Object object, String name,
			MibType mibType) {
		try {
			String str = "get"
					+ name.replaceFirst(name.substring(0, 1),
							name.substring(0, 1).toUpperCase());
			Class classzz = object.getClass();
			Field localField = classzz.getDeclaredField(name);
			if (localField == null)
				return null;
			Method method = classzz.getMethod(str, new Class[0]);

			Object obj = method.invoke(object, new Object[0]);
			return getVariable(mibType, (String) obj);

		} catch (Exception e) {

		}
		return new Null();
	}

	public static void setFieldValue(Object object, String str,
			Variable variable) {
		try {
			String str1 = "set"
					+ str.replaceFirst(str.substring(0, 1), str.substring(0, 1)
							.toUpperCase());
			Class clazz = object.getClass();
			Field field = clazz.getDeclaredField(str);
			if (field == null)
				return;
			
			Class classzz = field.getType();
			Method method = clazz.getMethod(str1,
					new Class[] { classzz });
			
			String str2 = classzz.getName();
			if (str2.equalsIgnoreCase("int")) {
				int i = variable.toInt();
				if ((variable instanceof Null))
					i = 0;
				method.invoke(object, new Object[] { new Integer(i) });
			} else if (str2.equalsIgnoreCase("long")) {
				long l = variable.toLong();
				if ((variable instanceof Null))
					l = 0L;
				method.invoke(object, new Object[] { new Long(l) });
			} else if (str2.equalsIgnoreCase("java.lang.String")) {
				method
						.invoke(object, new Object[] { variable.toString() });
			}
		} catch (Exception localException) {

		}
	}

	public static Variable getVariable(MibType mibType, String obj) {
		if (mibType.hasTag(0, 2)) {
			Integer32 object = new Integer32();
			((Integer32) object).setValue(obj);
			return object;
		}
		if (mibType.hasTag(1, 1)) {
			Counter32 object = new Counter32();
			((Counter32) object).setValue(obj);
			return (Variable) object;
		}
		if (mibType.hasTag(1, 2)) {
			Gauge32 object = new Gauge32();
			((Gauge32) object).setValue(obj);
			return (Variable) object;
		}
		if (mibType.hasTag(0, 4)) {
			OctetString object = new OctetString();
			try {
				byte[] arrayOfByte = obj.getBytes("iso8859_1");
				object = new OctetString(arrayOfByte);
			} catch (Exception localException) {
			}
			return object;
		}
		if (mibType.hasTag(0, 6)) {
			OID object = new OID(obj);
			return object;
		}
		if (mibType.hasTag(1, 0)) {
			IpAddress object = new IpAddress(obj);
			return object;
		}
		if (mibType.hasTag(1, 3)) {
			TimeTicks object = new TimeTicks();
			((TimeTicks) object).setValue(obj);
			return (Variable) object;
		}
		if (mibType.hasTag(1, 4)) {
			Opaque object = new Opaque(obj.getBytes());
			return object;
		}
		if (mibType.hasTag(1, 6)) {
			Counter64 object = new Counter64();
			object.setValue(obj);
			return object;
		}
		return (Variable) new Null();
	}
}
