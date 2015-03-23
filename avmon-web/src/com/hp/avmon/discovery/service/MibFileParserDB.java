/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hp.avmon.discovery.service;

import java.io.File;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import net.percederberg.mibble.Mib;
import net.percederberg.mibble.MibLoader;
import net.percederberg.mibble.MibLoaderException;
import net.percederberg.mibble.MibSymbol;
import net.percederberg.mibble.MibType;
import net.percederberg.mibble.MibValue;
import net.percederberg.mibble.MibValueSymbol;
import net.percederberg.mibble.snmp.SnmpNotificationGroup;
import net.percederberg.mibble.snmp.SnmpNotificationType;
import net.percederberg.mibble.snmp.SnmpObjectGroup;
import net.percederberg.mibble.snmp.SnmpObjectType;
import net.percederberg.mibble.snmp.SnmpTrapType;
import net.percederberg.mibble.snmp.SnmpType;
import net.percederberg.mibble.type.IntegerType;
import net.percederberg.mibble.value.NumberValue;
import net.percederberg.mibble.value.ObjectIdentifierValue;

import org.apache.log4j.Logger;
import org.jdom.CDATA;
import org.jdom.Document;
import org.jdom.Element;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.avmon.snmp.common.DBUtils;

/**
 * 
 * @author wangjung
 */
public class MibFileParserDB {

	private static Logger logger = Logger.getLogger(MibFileParserDB.class);
	private File[] paths = null;
	private File mibFile = null;
	public String mibFileName = "";
	private File outPath = null;

	public MibFileParserDB(String mibFileName) {
		mibFile = new File(mibFileName);
		outPath = new File(mibFile.getParent());
	}

	public MibFileParserDB(String dbcon, String dbtype, String mibFileName) {
		mibFile = new File(mibFileName);
		mibFileName = mibFileName;
		outPath = new File(mibFile.getParent());
	}

	public MibFileParserDB(String[] pathNames, String mibFileName) {
		if (pathNames != null) {
			if (pathNames.length > 0) {
				paths = new File[pathNames.length];
			}
			int pathIndex = 0;
			for (String pathName : pathNames) {
				paths[pathIndex] = new File(pathName);
				pathIndex++;
			}
		}
		mibFile = new File(mibFileName);
		outPath = new File(mibFile.getParent());
	}

	public MibFileParserDB(String[] pathNames, String mibFileName,
			String outPathName) {
		if (pathNames != null) {
			if (pathNames.length > 0) {
				paths = new File[pathNames.length];
			}
			int pathIndex = 0;
			for (String pathName : pathNames) {
				paths[pathIndex] = new File(pathName);
				pathIndex++;
			}
		}

		mibFile = new File(mibFileName);
		outPath = new File(outPathName);
	}

	public void parseMib(JdbcTemplate jdbc) throws Exception {
		MibLoader loader = new MibLoader();
		if (paths != null) {
			if (paths.length > 0) {
				loader.addDirs(paths);
			}
		}
		Mib mib = loader.load(mibFile);
		Element root = new Element("TREENODES");
		Document doc = new Document(root);
		// 构造根节点下的ModuleName节点
		Element mibEle = new Element("MIB");
		mibEle.setAttribute("name", mib.getName());
		// 记录module_name
		root.addContent(mibEle);

		HashMap exportValues = new HashMap();

		logger.info("parsed nodes count:" + mib.getAllSymbols().size());

		// 遍历MIB node, 并构造XML tree
		Iterator iter = mib.getAllSymbols().iterator();
		while (iter.hasNext()) {
			MibSymbol symbol = (MibSymbol) iter.next();
			// log.debug(symbol.getClass().getName());
			// log.debug(symbol);
			if (symbol instanceof MibValueSymbol) {
				MibValueSymbol mibValueSymbol = (MibValueSymbol) symbol;
				try {
					populateMibNode(mibValueSymbol, mibEle, exportValues, jdbc,
							mibFile.getName());
				} catch (NullPointerException e) {
					throw new MibLoaderException(mibFile, mibFile.getName()+"文件有错");
				}
				
			}
		}
		
		jdbc.execute(  
			     new CallableStatementCreator() {  
			        public CallableStatement createCallableStatement(Connection con) throws SQLException {  
			           String storedProc = "{? = call snmpmibprocsync('100')}";// 调用的sql  
			           CallableStatement cs = con.prepareCall(storedProc);  
			           //cs.setString(1, "100");// 设置输入参数的值  
			           cs.registerOutParameter(1,Types.INTEGER);// 注册输出参数的类型  
			           return cs;  
			        }  
			     }, new CallableStatementCallback() {  
			         public Object doInCallableStatement(CallableStatement cs) throws SQLException, DataAccessException {  
			           cs.execute();  
			           return cs.getInt(1);// 获取输出参数的值  
			     }  
			  });  //.execute("call snmpmibprocsync('100')");   
		// 生成XML文件
		// generateXmlFile(doc);
	}

	private void populateMibNode(MibValueSymbol mibValueSymbol,
			Element element, HashMap exportValues, JdbcTemplate jdbc,
			String fileName) {
		String strNodeName = "";
		String strOID = "";
		String strDesc = "";
		String strTrapDesc = "";
		String strSymbol = "";
		int index = 0;
		// log.debug("getType():" + mibValueSymbol.getType());
		// log.debug("getValue():" + mibValueSymbol.getValue());
		// log.debug("toString():" + mibValueSymbol.toString());

		strNodeName = mibValueSymbol.getName();
		// log.debug(mibValueSymbol.getClass().getName());
		MibValue mibValue = mibValueSymbol.getValue();
		// log.debug(mibValue.getClass().getName());
		if (mibValue instanceof ObjectIdentifierValue) {
			ObjectIdentifierValue oid = (ObjectIdentifierValue) mibValue;
			if (oid != null) {
				strOID = oid.toString();
				index = oid.getValue();
			}
			MibType type = mibValueSymbol.getType();

			// SnmpNotificationGroup, SnmpNotificationType, SnmpObjectGroup,
			// SnmpObjectIdentity, SnmpObjectType, SnmpTextualConvention,
			// SnmpTrapType
			// 获取描述信息
			if (type instanceof SnmpNotificationGroup) {
				strDesc = ((SnmpNotificationGroup) type).getDescription();

			}
			if (type instanceof SnmpNotificationType) {
				SnmpNotificationType snmpNotificationType = (SnmpNotificationType) type;
				if (snmpNotificationType != null) {

					strTrapDesc = "";
					for (Object vaiableItem : snmpNotificationType.getObjects()) {
						ObjectIdentifierValue vaiable = (ObjectIdentifierValue) vaiableItem;
						strTrapDesc += ";" + vaiable.getName() + "("
								+ vaiable.toString() + ")";
					}
					if (strTrapDesc.length() > 0)
						strTrapDesc = strTrapDesc.substring(1);
				}
			}
			if (type instanceof SnmpObjectGroup) {
				strDesc = ((SnmpObjectGroup) type).getDescription();

			}
			if (type instanceof SnmpType) {
				strDesc = ((SnmpType) type).getDescription();

			}
			if (type instanceof SnmpObjectType) {
				if (type instanceof SnmpObjectType) {
					SnmpObjectType snmpObjectType = ((SnmpObjectType) type);
					MibType syntaxType = snmpObjectType.getSyntax();
					if (syntaxType instanceof IntegerType) {
						IntegerType integerType = (IntegerType) syntaxType;
						for (MibValueSymbol childMibValueSymbol : integerType
								.getAllSymbols()) {
							strSymbol += ";" + childMibValueSymbol.getName()
									+ "(" + childMibValueSymbol.getValue()
									+ ")";
						}
					}
					if (strSymbol.length() > 0) {
						strSymbol = strSymbol.substring(1);
					}
				}
			}
		} else if (mibValueSymbol.getType() instanceof SnmpTrapType) {
			SnmpTrapType snmpTrapType = (SnmpTrapType) mibValueSymbol.getType();
			if (snmpTrapType != null) {
				ObjectIdentifierValue enterpriseOID = (ObjectIdentifierValue) snmpTrapType
						.getEnterprise();
				NumberValue numberValue = (NumberValue) mibValue;
				index = Integer.parseInt(numberValue.toString());
				strOID = enterpriseOID.toString() + ".0."
						+ numberValue.toString();
				strTrapDesc = "";
				for (Object vaiableItem : snmpTrapType.getVariables()) {
					ObjectIdentifierValue vaiable = (ObjectIdentifierValue) vaiableItem;
					strTrapDesc += ";" + vaiable.getName() + "("
							+ vaiable.toString() + ")";
				}
			}
		} else {
			return;
		}

		if (!exportValues.containsKey(strOID)) {
			exportValues.put(strOID, null);
			Element child = new Element("TREENODE");
			String strOidDesc = new CDATA(strDesc).toString();
			child.setAttribute("name", strNodeName);
			child.setAttribute("oid", strOID);
			child.setAttribute("idx", Integer.toString(index));
			child.setAttribute("desc", strOidDesc);// new
													// CDATA(strDesc).toString());
			child.setAttribute("symbol", strSymbol);
			child.setAttribute("trapoid", strTrapDesc);
			insertDBMibOid(strNodeName, strTrapDesc, strOID, strDesc, jdbc,
					fileName);
			element.addContent(child);
			if (mibValueSymbol.getChildCount() > 0) {
				MibValueSymbol[] childrenMibValueSymbol = mibValueSymbol
						.getChildren();
				for (int i = 0; i < mibValueSymbol.getChildCount(); i++) {
					populateMibNode(childrenMibValueSymbol[i], child,
							exportValues, jdbc, fileName);
				}
			}
		}
	}

	public void insertDBMibOid(String oidname, String oidtype, String oid,
			String oiddesc, JdbcTemplate jdbc, String fileName) {
		String iSQL;

		if (oidname.toUpperCase().indexOf("TRAP") > 0
				|| oiddesc.toUpperCase().indexOf(" TRAP ") > 0) {
			iSQL = "insert into td_avmon_snmp_miboid(oid_id,oid_type,oid_name,oid_desc,mibfile_name,create_dt,status,schedule) values('"
					+ oid
					+ "','trap','"
					+ oidname
					+ "','"
					+ toHtml(oiddesc)
					+ "','"
					+ fileName
					+ "',"
					+ DBUtils.getDBCurrentDateFunction()
					+ ",'0','0 0,5,10,15,20,25,30,35,40,45,50,55 * * * *')";
		} else {
			iSQL = "insert into td_avmon_snmp_miboid(oid_id,oid_type,oid_name,oid_desc,mibfile_name,create_dt,status,schedule) values('"
					+ oid
					+ "','kpi','"
					+ oidname
					+ "','"
					+ toHtml(oiddesc)
					+ "','"
					+ fileName
					+ "',"
					+ DBUtils.getDBCurrentDateFunction()
					+ ",'0','0 0,5,10,15,20,25,30,35,40,45,50,55 * * * *')";
		}
		try {
			jdbc.execute(iSQL);
		} catch (Exception e) {
			logger.error(this.getClass().getName()+e.getMessage());
		}
	}

//	private void generateXmlFile(Document doc) throws IOException {
//		String strOutPath = outPath.getPath() + "/xml";
//		// 如果xml文件夹不存在，则先建立xml文件夹
//		File xmlDir = new File(strOutPath);
//		if (!xmlDir.exists()) {
//			xmlDir.mkdir();
//		}
//		// 输出的XML文件名为mib文件名 + '.xml'
//		String strXMLFileName = mibFile.getName() + ".xml";
//		String xmlFullName = strOutPath + "/" + strXMLFileName;
//		File outFile = new File(xmlFullName);
//		FileWriter fileWriter = new FileWriter(outFile);
//		XMLOutputter outp = new XMLOutputter();
//		outp.output(doc, fileWriter);
//		log.debug("Successfully outpu XML file:" + xmlFullName);
//	}
	
    public String toHtml(String s)  
    {  
        if (s == null) s = "";  
        if (s != null && !s.equals("")){
        	s =  s.replace("&", "&amp;")
		          .replace("<", "&lt;")
		          .replace(">", "&gt;")
		          .replace("\t", "&nbsp;&nbsp;&nbsp;&nbsp;")
		          .replace("\r\n", " ")
		          .replace("\n", " ")
		          //.replace("  ", "&nbsp;&nbsp;")
		          .replace("'", "&#39;")
		          .replace("\\", "&#92;")
//		          .replace("'", "''")
		          .replace("`", "''");
        } 
        return s;  
    }  
}
