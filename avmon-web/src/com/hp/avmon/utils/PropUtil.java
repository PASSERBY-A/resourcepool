package com.hp.avmon.utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.hp.avmon.utils.AvmonUtils;

/**
 * 获取配置文件信息
 * @author litan
 *
 */
public class PropUtil
{
  private static final Logger logger = LoggerFactory.getLogger(PropUtil.class);
  private static final String config = "/config.properties";
  
  private static Map<String, Properties> propertieMap = new HashMap<String, Properties>();

  static {
	  try {
		//logger.info("Start Loading property file \t");
		Properties p = new Properties();
		p.load(PropUtil.class.getResourceAsStream(config));
		propertieMap.put("config", p);
        
	} catch (IOException e) {
	    logger.error(AvmonUtils.fullErrorMessage(e));
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
  public static int getInt(String cls, String key,int defaultValue) {
	  Properties p = (Properties)propertieMap.get(cls);
	  if(p.getProperty(key)==null) return defaultValue;
	  return Integer.parseInt(p.getProperty(key));
  }

}