package com.hp.avmon.snmp.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * ��ȡ�����ļ���Ϣ
 * @author litan
 *
 */
public class PropUtil
{
  private static final Logger logger = LoggerFactory.getLogger(PropUtil.class);
  private static final String config = "discover.properties";
  
  private static Map<String, Properties> propertieMap = new HashMap<String, Properties>();

  static {
	  try {
		//logger.info("Start Loading property file \t");
//		Properties p = new Properties();
//		String path  =  PropUtil.getConfPath("conf")+config;
//		p.load(new FileInputStream(new File(path)));
//		propertieMap.put("config", p);
		
		Properties p3 = new Properties();
        p3.load(PropUtil.class.getResourceAsStream("/config.properties"));
        propertieMap.put("jdbc", p3);
        
        Properties p4= new Properties();
        p4.load(PropUtil.class.getResourceAsStream("/discover.properties"));
        propertieMap.put("config", p4);
        
        //logger.info("Load property file success!\t");
	} catch (IOException e) {
		
	}
  }

  public static String getString(String cls, String key) {
	  Properties p = (Properties)propertieMap.get(cls);
	  if (p != null){
		  return p.getProperty(key);
	  }
	  return null;
  }
  
  public static byte getByte(String cls, String key) {
	  Properties p = (Properties)propertieMap.get(cls);
	  if (p != null && !"".equals(p)){
		  return Byte.parseByte(p.getProperty(key));
	  }
	  return -1;
  }

  public static int getInt(String cls, String key) {
	  Properties p = (Properties)propertieMap.get(cls);
	  return Integer.parseInt(p.getProperty(key));
  }
  
  public static short getShort(String cls, String key) {
	  Properties p = (Properties)propertieMap.get(cls);
	  return Short.parseShort(p.getProperty(key));
  }
  
  public static long getLong(String cls, String key) {
	  Properties p = (Properties)propertieMap.get(cls);
	  return Long.parseLong(p.getProperty(key));
  }

  public static boolean getBoolean(String cls, String key) {
	  Properties p = (Properties)propertieMap.get(cls);
	  return Boolean.valueOf(p.getProperty(key)).booleanValue();
  }
  
  public static String getPath(Class cls) {
		ClassLoader loader = cls.getClassLoader();
		String clsName = cls.getName() + ".class";
		Package pack = cls.getPackage();
		String path = "";
		if (pack != null) {
			String packName = pack.getName();
			clsName = clsName.substring(packName.length() + 1);
			if (packName.indexOf(".") < 0)
				path = packName + "/";
			else {
				int start = 0, end = 0;
				end = packName.indexOf(".");
				while (end != -1) {
					path = path + packName.substring(start, end) + "/";
					start = end + 1;
					end = packName.indexOf(".", start);
				}
				path = path + packName.substring(start) + "/";
			}
		}
		java.net.URL url = loader.getResource(path + clsName);
		String realPath = url.getPath();
		int pos = realPath.indexOf("file:");
		if (pos > -1)
			realPath = realPath.substring(pos + 5);
		pos = realPath.indexOf(path + clsName);
		realPath = realPath.substring(0, pos - 5);
		if (realPath.endsWith(".")){
			realPath = realPath.substring(0, realPath.lastIndexOf("/"));
		}
		
		return realPath;
	}
	
	public static String getConfPath(String subFolder){
		String confPath = PropUtil.getPath(PropUtil.class);
		if (confPath.endsWith("lib")) {
			confPath = confPath.substring(0, confPath.length() - 3)
					+ File.separator + subFolder + File.separator;
		} else {
			confPath = confPath + File.separator + subFolder + File.separator;
		}
		
		if(confPath.startsWith("/")){
			confPath.substring(2);
		}
		
		return confPath;
	}
  
  


}