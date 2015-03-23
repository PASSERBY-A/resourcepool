package com.hp.avmon.utils;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import sun.misc.BASE64Decoder;

public class AvmonUtils {

    private static int counter=0;
    

    
    public static String generateTimeId(){
        counter=(counter++)%100;
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
        return sdf.format(date)+counter;
    }
    
    public static int generateTimeIntId(){
    	String timeId =  generateTimeId();
    	int length = timeId.length();
    	;
    	String  time =  timeId.substring(length-9);
    	int intTimeId = Integer.parseInt(time);
    	
        return intTimeId;
    }
    public static long getTime(){
        return new Date().getTime();
    }
    
    public static long formatLong(Object o){
        if(o==null) return 0;
        if(o instanceof BigDecimal){
            return ((BigDecimal)o).intValue();
        }
        else if( o instanceof String){
            return Long.valueOf((String) o);
        }
        else {
            String s=String.valueOf(o);
            return Long.valueOf(s);
        }
        //return 0;
    }
    
    public static float toFloat(Object o){
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
        //return 0;
    }

    public static Object toString(Object s) {
        if(s==null)return "";
        return String.valueOf(s);
    }
    
    public static String fullErrorMessage(Throwable cause,int rowLimit){
        String ss=String.format("Exception in thread '%s' %s\n",
                Thread.currentThread().getName(),
                cause);
        int n=0;
        for(StackTraceElement te:cause.getStackTrace()){
            //if(te.getClassName().indexOf("com.hp.")>=0){
                if(te.getFileName()==null){
                    ss+=String.format("\t|- %s.%s(Unknown Source)\n",te.getClassName(),te.getMethodName());
                }
                else{
                    ss+=String.format("\t|- %s.%s(%s:%d)\n",te.getClassName(),te.getMethodName(),te.getFileName(),te.getLineNumber());
                }
            //}
            n++;
            if(rowLimit>0 && n>=rowLimit){
            	break;
            }
        }
        return ss;
    }
    
    public static String fullErrorMessage(Throwable cause){
        return fullErrorMessage(cause,0);
    }


    public static String base64Encode(String str){
        return "BASE64"+new sun.misc.BASE64Encoder().encode(str.getBytes());
    }

    public static String base64Decode(String str){
        if(str.startsWith("BASE64")){
            String s=str.substring(6);
            BASE64Decoder base64=new BASE64Decoder();
            try {
                return new String(base64.decodeBuffer(s));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return str;
    }
    
    public static void systemOut(String str){
        systemOut(str,"ERROR");
    }
    public static void systemOut(String str,String level){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.format("%s [%s] %s\n", sdf.format(new Date()),level,str);
    }
    

}
