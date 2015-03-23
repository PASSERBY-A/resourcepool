package com.hp.avmon.utils;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;

/**
 * 共同配置文件参数读取
 *
 * @author AnYi
 * @version 1.00 2013/02/27
 */
public final class PropertyUtils {

    private static Properties properties = new Properties();

    static {
        // 读取并存放参数
        setProperties("config", PropertyUtils.properties);
    }

    /**
     * 从指定的配置文件读取相关的参数
     *
     * @param path 指定路径的properties文件名
     * @param properties 存放参数的容器
     * @throws MissingResourceException properties文件不存在的场合
     */
    private static void setProperties(String path, Properties properties)
       throws MissingResourceException {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(path);
        @SuppressWarnings("rawtypes")
		Enumeration cursor = resourceBundle.getKeys();

        String encodingName = null;
        String encodingValue = null;

        while (cursor.hasMoreElements()) {
            // 从配置文件中，循环读取参数信息
            encodingName = (String) cursor.nextElement();
            encodingValue = resourceBundle.getString(encodingName);
            // 将参数信息存放
            properties.setProperty(encodingName, encodingValue);
        }
    }

    private PropertyUtils() {
    }

    /**
     * 从配置文件读取指定key
     * @param key 
     * @return value
     */
    public static String getProperty(String key) {
        return PropertyUtils.properties.getProperty(key);
    }

    /**
     * 从配置文件读取指定key
     *
     * @param key 
     * @param defaultValue 
     * @return value
     */
    public static String getProperty(String key, String defaultValue) {
        return PropertyUtils.properties.getProperty(key, defaultValue);
    }
}
