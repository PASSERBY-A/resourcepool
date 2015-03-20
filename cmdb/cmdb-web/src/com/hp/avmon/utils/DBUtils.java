package com.hp.avmon.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.math.BigDecimal;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;




public class DBUtils {
    
    private static final Logger log = LoggerFactory.getLogger(DBUtils.class);

    private static JdbcTemplate jdbcTemplate=null;

    


    public static void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        DBUtils.jdbcTemplate = jdbcTemplate;
    }

//    public static JdbcTemplate getJdbcMemory() {
//        return jdbcMemory;
//    }
//
//    public static void setJdbcMemory(JdbcTemplate jdbcMemory) {
//        MemDB.jdbcMemory = jdbcMemory;
//    }



    
    public static String formatStr(Object value){
        String str=String.valueOf(value);
        if(value==null){
            return "''";
        }
        else{
            String s=(String) str;
            s=s.replaceAll("'", "''");
            str=s;
        }
        //System.out.println(value+"  --->  "+str);
        if(value instanceof String){
            if(str.equals("null")){
                return "''";
            }
            return new String("'"+str+"'");
        }
        else{
            return new String(str);
        }
    }
    
//    public static int formatInt(Object o){
//        if(o==null) return 0;
//        if(o instanceof BigDecimal){
//            return ((BigDecimal)o).intValue();
//        }
//        else if( o instanceof String){
//            if("".equals(o)){
//                return 0;
//            }
//            return Integer.valueOf((String) o);
//        }
//        else {
//            String s=String.valueOf(o);
//            return Integer.valueOf(s);
//        }
//        //return 0;
//    }

    public static Object formatDate(Object o) {
        if(o==null){
            return "null";
        }
        String s=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format((Date)o);
        return "TIMESTAMP '"+s+"'";
    }
    
    public static String formatDateOracle(Object o)
    {
        if(o == null)
        {
            return "null";
        } else
        {
            String s = (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format((Date)o);
            return String.format("to_date('%s','yyyy-MM-dd HH24:mi:ss')", new Object[] {
                s
            });
        }
    }

    public static double formatDouble(Object o) {
        if(o==null) return 0.0;
        if(o instanceof BigDecimal){
            return ((BigDecimal)o).doubleValue();
        }
        else if( o instanceof String){
            return Double.valueOf((String) o);
        }
        else {
            String s=String.valueOf(o);
            return Double.valueOf(s);
        }
    }

    public static float formatFloat(Object o) {
        if(o==null) return 0;
        if(o instanceof BigDecimal){
            return ((BigDecimal)o).floatValue();
        }
        else if( o instanceof String){
            return Float.valueOf((String) o);
        }
        else {
            String s=String.valueOf(o);
            return Float.valueOf(s);
        }
    }

    public static int toInt(Object o){
        if(o==null) return 0;
        if(o instanceof BigDecimal){
            return ((BigDecimal)o).intValue();
        }
        else if( o instanceof String){
            return Integer.valueOf((String) o);
        }
        return 0;
    }
}
