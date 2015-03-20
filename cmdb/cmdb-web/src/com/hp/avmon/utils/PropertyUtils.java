package com.hp.avmon.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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

	static String profilepath="ciAttribute.properties";
	
    private static Properties properties = new Properties();

    static {
        // 读取并存放参数
        setProperties("ciAttribute", PropertyUtils.properties);
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
    
    /** 
     * 根据key只更新配置文件数据
     * @param key 键 
     * @param value 键对应的值 
     * @throws IOException 
     */  
    public static void writeData(String key, String value) throws IOException {  
       String filePath = PropertyUtils.class.getResource("/" + profilepath).toString();  
        filePath = filePath.substring(6);  
        Properties prop = new Properties();  
            File file = new File(filePath);  
            if (!file.exists())  
                file.createNewFile();  
            InputStream fis = new FileInputStream(file);  
            prop.load(fis);   
            fis.close();  
            System.out.println(filePath);
            OutputStream fos = new FileOutputStream(filePath);  
            prop.setProperty(key, value);  
            //保存，并加入注释  
            prop.store(fos, "Update '" + key + "' value");              
            fos.close();  
    }
    
    public static void main(String agrs[]){
//    	try {
//			PropertyUtils.writeData("newKey", "swq,swq2,swq3");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//    	System.out.println(PropertyUtils.getProperty("hostAttribute"));
//    	try {
//			setProperty("newKey", "swq,swq2,swq3");
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
    	//System.out.println(PropertyUtils.getProperty("hostAttribute"));
    	//String  temp = "StoreHost";
    	//System.out.print(temp.substring(0,1).toLowerCase()+temp.substring(1,temp.length())+"Attribute");	
    	
//    	int i=0;
//    	int j=0;
//    	for(;i<10;i++){
//    		for(;j<100;j++){
//    			System.out.println(i*100+j);
//    		}
//    	}
//    	System.out.println("---------------------------");
//    	for(int s=0;s<10;s++){
//    		for(int w=0;w<100;w++){
//    			System.out.println(s*100+w);
//    		}
//    	}
    }
}
