package com.hp.avmon.ireport.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * 业务数据共通Util
 * 
 * @author common
 */
public class BizDataUtil {

    public static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat datefmt1 = new SimpleDateFormat("yyyy/MM/dd");
    public static SimpleDateFormat datefmtMon = new SimpleDateFormat("MM");
    public static SimpleDateFormat datefmtDay = new SimpleDateFormat("dd");
    public static SimpleDateFormat datefmtHH = new SimpleDateFormat("HH");
    public static SimpleDateFormat formatHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat formatHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
    public static SimpleDateFormat formatUS = new SimpleDateFormat("MMMM dd, yyyy", Locale.US);
    public static SimpleDateFormat formatUSHHmmss = new SimpleDateFormat("MMMM dd, yyyy HH:mm", Locale.US);
    public static SimpleDateFormat datefmt2 = new SimpleDateFormat("MM/dd/yyyy");
    public static SimpleDateFormat datefmt3 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
    public static SimpleDateFormat HHMMSS = new SimpleDateFormat("HH:mm:ss");
    
    /**
     * 取得前后N年分下拉列表Map
     * 
     * @param yearCount
     *            指定年数
     * @return 下拉列表Map
     */
    public static Map<String, String> getYearMap(int yearCount) {

        Map<String, String> yearMap = new LinkedHashMap<String, String>();

        // 日历取得
        Calendar cder = Calendar.getInstance();

        int cYear = cder.get(Calendar.YEAR);

        yearMap.put("", "");
        // 前N年
        for (int i = yearCount; i >= 1; i--) {
            yearMap.put(String.valueOf(cYear - i), String.valueOf(cYear - i));
        }
        // 今年
        yearMap.put(String.valueOf(cYear), String.valueOf(cYear));
        // 后N年
        for (int j = 1; j <= yearCount; j++) {
            yearMap.put(String.valueOf(cYear + j), String.valueOf(cYear + j));
        }

        return yearMap;
    }

    /**
     * 取得月份下拉列表Map
     * 
     * @param yearCount
     *            指定年数
     * @return 下拉列表Map
     */
    public static Map<String, String> getMonthMap() {

        Map<String, String> monthMap = new LinkedHashMap<String, String>();

        // 12月份
        monthMap.put("1", "01");
        monthMap.put("2", "02");
        monthMap.put("3", "03");
        monthMap.put("4", "04");
        monthMap.put("5", "05");
        monthMap.put("6", "06");
        monthMap.put("7", "07");
        monthMap.put("8", "08");
        monthMap.put("9", "09");
        monthMap.put("10", "10");
        monthMap.put("11", "11");
        monthMap.put("12", "12");

        return monthMap;
    }
    
    /**
     * 取得月份下拉列表Map
     * 
     * @param yearCount
     *            指定年数
     * @return 下拉列表Map
     */
    public static Map<String, String> getFullMonthMap() {

        Map<String, String> monthMap = new LinkedHashMap<String, String>();

        // 12月份
        monthMap.put("01", "01");
        monthMap.put("02", "02");
        monthMap.put("03", "03");
        monthMap.put("04", "04");
        monthMap.put("05", "05");
        monthMap.put("06", "06");
        monthMap.put("07", "07");
        monthMap.put("08", "08");
        monthMap.put("09", "09");
        monthMap.put("10", "10");
        monthMap.put("11", "11");
        monthMap.put("12", "12");

        return monthMap;
    }
    
    public static Map<String, String> getYearChineseMap(int yearCount) {

        Map<String, String> yearMap = new LinkedHashMap<String, String>();

        // 日历取得
        Calendar cder = Calendar.getInstance();

        int cYear = cder.get(Calendar.YEAR);

        // 前N年
        for (int i = yearCount; i >= 1; i--) {
            yearMap.put(String.valueOf(cYear - i), String.valueOf(cYear - i)+"年");
        }
        // 今年
        yearMap.put(String.valueOf(cYear), String.valueOf(cYear)+"年");
        // 后N年
        for (int j = 1; j <= yearCount; j++) {
            yearMap.put(String.valueOf(cYear + j), String.valueOf(cYear + j)+"年");
        }

        return yearMap;
    }

    public static Map<String, String> getMonthChineseMap() {
        Map<String, String> monthMap = new LinkedHashMap<String, String>();

        // 12月份
        monthMap.put("1", "1月");
        monthMap.put("2", "2月");
        monthMap.put("3", "3月");
        monthMap.put("4", "4月");
        monthMap.put("5", "5月");
        monthMap.put("6", "6月");
        monthMap.put("7", "7月");
        monthMap.put("8", "8月");
        monthMap.put("9", "9月");
        monthMap.put("10", "10月");
        monthMap.put("11", "11月");
        monthMap.put("12", "12月");

        return monthMap;
    }

    public static Map<String, String> getWeekMap() {
        Map<String, String> weekMap = new LinkedHashMap<String, String>();
        weekMap.put("0", "星期日");
        weekMap.put("1", "星期一");
        weekMap.put("2", "星期二");
        weekMap.put("3", "星期三");
        weekMap.put("4", "星期四");
        weekMap.put("5", "星期五");
        weekMap.put("6", "星期六");

        return weekMap;
    }
    
    public static Map<String, String> getQuarterMap(){
        Map<String, String> quarterMap = new LinkedHashMap<String, String>();
        quarterMap.put("1", "第一季度");
        quarterMap.put("2", "第二季度");
        quarterMap.put("3", "第三季度");
        quarterMap.put("4", "第四季度");
        return quarterMap;
    }

    /**
     * 获取上个月
     * 
     * @return 年月
     */
    public String getPreYearMonth() {

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1); // 得到前一个月
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int monNum = IntLen(month);
        String yearMonth = "";
        if (monNum == 1) {
            yearMonth = year + "0" + month;
        } else {
            yearMonth = year + "" + month;
        }
        return yearMonth;
    }
    
    /**
     * 获取当前月第一天日期
     * 
     * @return Date
     */
    public static Date getFirstDayOfMonth(){
        Calendar   c   =   Calendar.getInstance(); 
        c.setTime(new Date());
        int value = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, value);
        return c.getTime();
    }
    
    /**
     * 获取当前月最后一天日期
     * 
     * @return Date
     */
    public static Date getLastDayOfMonth(){
        Calendar   c   =   Calendar.getInstance(); 
        c.setTime(new Date());
        int value = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, value);
        return c.getTime();
    }
    
    /**
     * 获取下个月月最后一天日期
     * 
     * @return Date
     */
    public static Date getNextMonthEnd() {
        Calendar lastDate = Calendar.getInstance();
        lastDate.add(Calendar.MONTH, 1);// 加一个月
        lastDate.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        lastDate.roll(Calendar.DATE, -1);// 日期回滚一天，也就是本月最后一天
        return lastDate.getTime();
    }
    
    /**
     * 获取当前月第一天指定时间的日期
     * 
     * @return Date
     */
    public static Date getFirstDayOfMonthOfTime(String time){
        Calendar   c   =   Calendar.getInstance(); 
        c.setTime(new Date());
        int value = c.getActualMinimum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, value);
        
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH )+1; 
        int day = c.get(Calendar.DAY_OF_MONTH);
        int monNum = IntLen(month);
        int dayNum = IntLen(day);
        String monStr = "";
        String dayStr = "";
        if (monNum == 1) {
            monStr = "0" + month;
        } else {
            monStr = "" + month;
        }
        if (dayNum == 1) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        String zcYc = year + "-" + monStr + "-" + dayStr + " "+time ;
        Date d = null;
        try {
            d = formatHms.parse(zcYc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
    
    /**
     * 获取当前月最后一天指定时间的日期
     * 
     * @return Date
     */
    public static Date getLastDayOfMonthOfTime(String time){
        Calendar   c   =   Calendar.getInstance(); 
        c.setTime(new Date());
        int value = c.getActualMaximum(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, value);
        
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH )+1; 
        int day = c.get(Calendar.DAY_OF_MONTH);
        int monNum = IntLen(month);
        String monStr = "";
        if (monNum == 1) {
            monStr = "0" + month;
        } else {
            monStr = "" + month;
        }
        String zcYc = year + "-" + monStr + "-" + day + " "+time ;
        Date d = null;
        try {
            d = formatHms.parse(zcYc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }
    
    /**
     * 获取当前月前5个月第一天日期
     * 
     * @return String
     */
    public static String getFirstFiveDayOfMonth() {
        int i = -5; /* 这里的i初始值，你可以根据自己的需要来改变，也可以变成变量 */
        Calendar calendar = Calendar.getInstance();
        /*
         * i是正数就是后i月，i是负数就是前i月，i是几就加几月，这里的例子是前5个月
         */
        calendar.add(Calendar.DATE, i); // 得到某一天
        calendar.add(Calendar.MONTH, i); // 得到某一个月

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;// 这里月要加1
        int monNum = IntLen(month);
        String monStr = "";
        if (monNum == 1) {
            monStr = "0" + month;
        } else {
            monStr = "" + month;
        }

        /* 下面的是根据我自己需要用到的日期格式加的一些小处理 */
        String zcYc = year + "-" + monStr + "-01";
        return zcYc;
    }  
    
    // 上月第一天  
    public static Date getPreviousMonthFirst(){    
  
       Calendar lastDate = Calendar.getInstance();  
       lastDate.set(Calendar.DATE,1);//设为当前月的1号  
       lastDate.add(Calendar.MONTH,-1);//减一个月，变为上月的1号  
       //lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天  
       return lastDate.getTime();   
    }
    
    /**
     * 获取当前年
     * 
     * @return year
     */
    public static int getCurrentYear(){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }
    
    /**
     * 获取当前月
     * 
     * @return year
     */
    public static int getCurrentMonth(){
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH )+1; 
        return month;
    }
    
    /**
     * 获取当天day
     * 
     * @return year
     */
    public static int getCurrentDay(){
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH); 
        return day;
    }
    
    /**
     * 获取本周指定星期指定时间点对应时间的日期
     * 
     * @return year
     */
    public static Date getWeekTimeDate(int week, String time){

        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + week);
        
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH )+1; 
        int day = c.get(Calendar.DAY_OF_MONTH);
        int monNum = IntLen(month);
        String monStr = "";
        if (monNum == 1) {
            monStr = "0" + month;
        } else {
            monStr = "" + month;
        }
        String zcYc = year + "-" + monStr + "-" + day + " "+time ;
        Date d = null;
        try {
            d = formatHms.parse(zcYc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }    
    
    /**
     * 获取本周指定星期对应时间的日期
     * 
     * @return year
     */
    public static String getWeekTimeDate(int week){

        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + week);
        
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH )+1; 
        int day = c.get(Calendar.DAY_OF_MONTH);
        int monNum = IntLen(month);
        int dayNum = IntLen(day);
        String monStr = "";
        String dayStr = "";
        if (monNum == 1) {
            monStr = "0" + month;
        } else {
            monStr = "" + month;
        }
        if (dayNum == 1) {
            dayStr = "0" + day;
        } else {
            dayStr = "" + day;
        }
        String zcYc = year + "/" + monStr + "/" + dayStr;
        return zcYc;
    }    
    
    /**
     * 获取下周指定星期指定时间点对应时间的日期
     * 
     * @return year
     */
    public static Date getNextWeekTimeDate(int week, String time) {  

        int mondayPlus = getMondayPlus();  
        GregorianCalendar currentDate = new GregorianCalendar();
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 + week - 1);  
        //Date monday = currentDate.getTime();  
        
        int year = currentDate.get(Calendar.YEAR);
        int month = currentDate.get(Calendar.MONTH)+1;  
        int day = currentDate.get(Calendar.DAY_OF_MONTH);
        int monNum = IntLen(month);
        String monStr = "";
        if (monNum == 1) {
            monStr = "0" + month;
        } else {
            monStr = "" + month;
        }
        String zcYc = year + "-" + monStr + "-" + day + " "+time ;
        Date d = null;
        try {
            d = formatHms.parse(zcYc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;  
    }  

    /**
     * 获取当天指定时间点对应时间的日期
     * 
     * @return year
     */
    public static Date getCurDateByTime(String time){

        Calendar c = Calendar.getInstance();
        
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH )+1; 
        int day = c.get(Calendar.DAY_OF_MONTH);
        int monNum = IntLen(month);
        String monStr = "";
        if (monNum == 1) {
            monStr = "0" + month;
        } else {
            monStr = "" + month;
        }
        String zcYc = year + "-" + monStr + "-" + day + " "+time ;
        Date d = null;
        try {
            d = formatHms.parse(zcYc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }     

    /**
     * 获取几天后指定时间点对应时间的日期
     * 
     * @return year
     */
    @SuppressWarnings("static-access")
    public static Date getNextDateByTime(int countDay, String time){

        Calendar c = Calendar.getInstance();
        c.setTime(new Date()); 
        c.add(c.DATE, countDay);         
        
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH )+1; 
        int day = c.get(Calendar.DAY_OF_MONTH);
        int monNum = IntLen(month);
        String monStr = "";
        if (monNum == 1) {
            monStr = "0" + month;
        } else {
            monStr = "" + month;
        }
        String zcYc = year + "-" + monStr + "-" + day + " "+time ;
        Date d = null;
        try {
            d = formatHms.parse(zcYc);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return d;
    }    
    
    /**
     * 获取昨天
     * 
     * @return year
     */
    public static Date getLastDate(){

        Calendar c = Calendar.getInstance();
        c.roll(java.util.Calendar.DAY_OF_YEAR,-1); 
        
        return c.getTime();
    }    
    
    /**
     * 获取本周年，月,第几周
     * 
     * @return year
     */
    public static Map<String, Object> getYearMonthWeekNum(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        int curDay = c.get(Calendar.DAY_OF_WEEK);
        if(curDay == 1) curDay = 8;
        c.add(Calendar.DATE,-(curDay-2));
        
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("year", c.get(Calendar.YEAR));
        params.put("month", c.get(Calendar.MONTH)+1);
        params.put("weekNum", c.get(Calendar.WEEK_OF_YEAR));
        return params;
    }
    
    /**
     * 获取上周的年，月,第几周
     * 
     * @return year
     */
    public static Map<String, Object> getPreYearMonthWeekNum(){
        Calendar c = Calendar.getInstance();
        c.setTime(getPreviousWeekSunday());
        int curDay = c.get(Calendar.DAY_OF_WEEK);
        if(curDay == 1) curDay = 8;
        c.add(Calendar.DATE,-(curDay-2));
        
        Map<String, Object> params = new HashMap<String,Object>();
        params.put("year", c.get(Calendar.YEAR));
        params.put("month", c.get(Calendar.MONTH)+1);
        params.put("weekNum", c.get(Calendar.WEEK_OF_YEAR));
        return params;
    }
    
    /**
     * 得到本周周二
     * 
     * @return yyyy-MM-dd
     */
    public static String getTuesdayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 2);
        return format.format(c.getTime());
    }

    /**
     * 得到本周周五
     * 
     * @return yyyy-MM-dd
     */
    public static String getFridayOfThisWeek() {
        Calendar c = Calendar.getInstance();
        int day_of_week = c.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0)
            day_of_week = 7;
        c.add(Calendar.DATE, -day_of_week + 5);
        SimpleDateFormat df1 = new SimpleDateFormat("MMMM dd,yyyy",Locale.US);
        return df1.format(c.getTime());
    }
    
    /** 
     * 获得上周星期日的日期 
     *  
     * @return 
     */  
    public static Date getPreviousWeekSunday() {  
        int weeks = 0;  
        weeks--;  
        int mondayPlus = getMondayPlus();  
        GregorianCalendar currentDate = new GregorianCalendar();  
        currentDate.add(GregorianCalendar.DATE, mondayPlus + weeks);  
        Date monday = currentDate.getTime();         
        return monday;  
    } 

  
    /** 
     * 获得上周星期一的日期 
     *  
     * @return 
     */ 
    public static Date getPreviousWeekMonday() {  
        int weeks = 0;  
        weeks--;   
        int mondayPlus = getMondayPlus();  
        GregorianCalendar currentDate = new GregorianCalendar();  
        currentDate.add(GregorianCalendar.DATE, mondayPlus + 7 * weeks);  
        Date monday = currentDate.getTime();  
        return monday;  
    }
    
    /** 
     * 获得当前日期与本周日相差的天数 
     *  
     * @return 
     */  
    public static int getMondayPlus() {  
        Calendar cd = Calendar.getInstance();  
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......  
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1  
        if (dayOfWeek == 0) {  
            return -6;  
        } else {  
            return 1 - dayOfWeek;  
        }  
    }  
    
    /** 
     * 获得当月某天日期
     *  
     * @return 
     */  
    public static Date getDateOfSomeday(int day, String time) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH )+1; 
        String someDay = year+"-"+month+"-"+day+" "+time;
        Date dt = null;
        try {
            dt = formatHms.parse(someDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }
    
    /** 
     * 获得当月某天日期
     *  
     * @return 
     */  
    public static Date getDateOfSomeday(int day) {
        Calendar calendar = Calendar.getInstance();
        //int year = calendar.get(Calendar.YEAR);
        //int month = calendar.get(Calendar.MONTH )+1; 
        calendar.set(Calendar.DAY_OF_MONTH, day);
        return calendar.getTime();
    }
    
    /** 
     * 获得下个月某天日期
     *  
     * @return 
     */  
    public static Date getDateOfSomedayNtMonth(int day, String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,1);//加一个月  
        calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天  
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH )+1;         
        String someDay = year+"-"+month+"-"+day+" "+time;
        Date dt = null;
        try {
            dt = formatHms.parse(someDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }
    
    /** 
     * 获得下下个月某天日期
     *  
     * @return 
     */  
    public static Date getDateOfSomeday2NtMonth(int day, String time) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,2);//加两个月  
        calendar.set(Calendar.DATE, 1);//把日期设置为当月第一天  
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH )+1;         
        String someDay = year+"-"+month+"-"+day+" "+time;
        Date dt = null;
        try {
            dt = formatHms.parse(someDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dt;
    }
    
    /** 
     * 获指定日期为星期几
     *  
     * @return 0代表周日,1为周一
     */  
    public static int getDayOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        //get day of week 
        int day = calendar.get(Calendar.DAY_OF_WEEK);

        return day - Calendar.SUNDAY;
    }
    
    /**
     * 获取数字位数
     * 
     * @return num
     */
    public static int IntLen(int num) {
        int sum = 0;
        while (num >= 10) {
            num /= 10;
            sum++;
        }
        sum++;
        return sum;
    }
    
    /**
     * 获得指定日期的前N天
     * 
     * @param specifiedDay
     * @return
     * @throws Exception
     */
    public static String getDayBefore(String specifiedDay,int n) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + n);

        String dayBefore = new SimpleDateFormat("yyyy-MM-dd").format(c
                .getTime());
        return dayBefore;
    }


}
