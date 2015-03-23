package com.hp.avmon.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hp.avmon.deploy.web.DeployController;

public class SqlManager {
    
    private static final Log logger = LogFactory.getLog(SqlManager.class);
    
    private static Map sqls=new HashMap();
    
    public static <T> String getSql(Class<T> resourceClass,String id){
        String key=resourceClass.getName()+"."+id;
        String sql=(String) sqls.get(key);
        if(sql==null){
            sql=readFromFile(resourceClass,id);
            //sqls.put(key, sql);
        }
        return sql;
    }

    private static <T> String readFromFile(Class<T> resourceClass,String id) {
        //System.out.println(resourceClass.getName());
        String content="";
        InputStream inputstream = null;
        InputStreamReader reader = null;
        BufferedReader br = null;
        try {
	        	String sqlfile = "";
	        	if(DBUtils.isOracle())
	        		sqlfile = id + ".sql";
	        	else if(DBUtils.isPostgreSql())
	        		sqlfile = id + ".pg.sql";
	          else
	          	throw new RuntimeException("未知数据库类型！");
          
            inputstream = resourceClass.getResourceAsStream(sqlfile);
            if(inputstream==null){
                logger.error("Error:can not find sql file "+sqlfile);
                return null;
            }
            reader = new InputStreamReader(inputstream, "utf-8");
            
            br = new BufferedReader(reader);
            String line = null;
            while ((line = br.readLine()) != null) {
                content+=line+"\n";
            }
        } catch (IOException ex2) {
            ex2.printStackTrace();
        } finally {
            if (inputstream != null) {
                try {
                    inputstream.close();
                } catch (IOException ex) {
                }
            }

        }
        
        return content;
    }
    
    public static void main(String[] args)  {
        System.out.println(getSql(SqlManager.class,"test"));
    }

    public static String getSql(Object obj, String id) {
        return getSql(obj.getClass(),id);
    }
    
}
