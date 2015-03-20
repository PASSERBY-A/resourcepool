package com.hp.avmon.utils;

import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.Collator;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import com.hp.avmon.common.jackjson.JackJson;


public class MyFunc {

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Comparator comp = Collator.getInstance(Locale.CHINA);
        String a="张三";
        String b="李四";
        String c="王二";
        String d="冀永强";
        System.out.format("%d %d %d",comp.compare(b, c),comp.compare(c, a),comp.compare(d, a));

        
    }

    public static void returnJson(PrintWriter writer,String result){
        returnJson(writer,result,new String[]{});
    }
    
    public static void returnJson(PrintWriter writer,String result,String errorMessage){
        if(errorMessage!=null){
            returnJson(writer,result,new String[]{"errorMessage",errorMessage});
        }
        else{
            returnJson(writer,result,new String[]{});
        }
    }
    
    public static void returnJson(PrintWriter writer,String result,Object [] msg){
        HashMap map=new HashMap();
        map.put("result", result);
        if(msg!=null){
            for(int i=0;i<msg.length-1;i++,i++){
                map.put(msg[i], msg[i+1]);
            }
        }
        String json=JackJson.fromObjectToJson(map);
        writer.write(json);
        writer.flush();
        writer.close();
    }
    
    public static String generateTimeId(){
        Calendar c = Calendar.getInstance();
        Date date = c.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssms");
        return sdf.format(date);
    }
    
    
    /**
     * Gets the current time string.
     * 
     * @param pattern
     *            the pattern
     * @return the current time string
     */
    public static String getCurrentTimeString(String pattern) {
        java.util.Date now = Calendar.getInstance().getTime();
        Format formatter = new SimpleDateFormat(pattern);
        return formatter.format(now);
    }
    
    
    public static String formatTime(String pattern,Date d) {
        Format formatter = new SimpleDateFormat(pattern);
        return formatter.format(d);
    }
    
    public static String formatTime(Date d) {
        return formatTime("yyyy-MM-dd HH:mm:ss",d);
    }
    
	/**
	 * 字符串类型转换
	 * 
	 * @param str
	 * @return
	 */
    public static String nullToString(Object str) {
		if (str == null) {
			return "";
		}
		return String.valueOf(str);
	}
    
	/**
	 * 字符串类型转换
	 * 
	 * @param str
	 * @return
	 */
    public static Short nullToShort(Object str) {
		if (str == null) {
			return 0;
		}
		return Short.valueOf(nullToString(str));
	}
    
	/**
	 * 字符串编码类型转换
	 * 
	 * @param str
	 * @return
	 */
    public static String enCodeStr(String str) {
        try {
          return new String(str.getBytes("iso-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
    }
    
	/**
	 * 分页显示数据
	 * 
	 * @param str
	 * @return
	 */
    public static String generatPageSql(String sql, String limit, String start){
		Integer limitL = Integer.valueOf(limit);
		Integer startL = Integer.valueOf(start);
		
		//构造oracle数据库的分页语句
		StringBuffer paginationSQL = new StringBuffer(" SELECT * FROM ( ");
		paginationSQL.append(" SELECT temp.* ,ROWNUM num FROM ( ");
		paginationSQL.append(sql);
		paginationSQL.append(" ) temp where ROWNUM <= " + (startL + limitL));//limitL*startL);
		paginationSQL.append(" ) WHERE num > " + (startL));
		
		return paginationSQL.toString();
	}
    
    public static String returnNull(Object str) {
		if (str==null||"".equals(str)||"null".equalsIgnoreCase(String.valueOf(str))) {
			return null;
		}
		return String.valueOf(str);
	}

    public static int nullToInteger(Object object) {
        if (object == null) {
            return 0;
        }
        return Integer.valueOf(nullToString(object));
    }
}
