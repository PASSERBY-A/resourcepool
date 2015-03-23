package com.hp.avmon.ireport.sql;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimerTask;
import java.util.Date;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.hp.avmon.ireport.util.BizDataUtil;
/**
 * @author lizhiyu
 * 
 */
public class ExecuteSqlTask extends TimerTask {

    
    private static int RUN_PERIOD = 5;//扫描间隔5分钟一次
    
    private static int SCAN_TIMES = -1;//run的次数
    
    private static Date NOW_DATE;//当前日期
    
    private static int NOW_WEEK;//当前日期为星期几,0代表周日,1为周一
    
    private static String NOW_HH;//当前小时数
    
    private static String NOW_MONTH;//当前月
    
    private static String NOW_DAY;//当前日期day
    
    /**
     * 扫描ireport/sqlConfig.xml，并执行其配置的sql脚本
     */
    @Override
    public void run() {        
        SCAN_TIMES++;  
        NOW_DATE = new Date();
        NOW_WEEK = BizDataUtil.getDayOfWeek(NOW_DATE);
        if(NOW_WEEK == 0){NOW_WEEK = 7;}
        NOW_HH = fmtData(BizDataUtil.datefmtHH.format(NOW_DATE));
        NOW_MONTH = fmtData(String.valueOf(BizDataUtil.getCurrentMonth()));
        NOW_DAY = fmtData(String.valueOf(BizDataUtil.getCurrentDay()));
        scanSqlConfigFile();        
    }
    
    /**
     * 扫描ireport/sqlConfig.xml
     * @param 
     * @return 
     * @throws Exception
     */
    private void scanSqlConfigFile() {
        

        String pt = getWebinfPath();       
        
        try {
            
            File f=new File(pt+"ireport/sqlConfig.xml"); 
            SAXReader reader = new SAXReader(); 
            org.dom4j.Document doc = reader.read(f); 
            Element root = doc.getRootElement(); 
            Element foo; 
            for (@SuppressWarnings("rawtypes")
            Iterator i = root.elementIterator("item"); i.hasNext();) { 
                foo = (Element) i.next(); 
                
                String start = foo.elementText("start-time");
                String period = foo.elementText("period");
                String file = foo.elementText("sql-file");
                String driver = foo.elementText("driver");
                String url = foo.elementText("url");
                if(start == null || start.equals("")){continue;}
                if(period == null || period.equals("")){continue;}
                if(file == null || file.equals("")){continue;}
                if(driver == null || driver.equals("")){continue;}
                if(url == null || url.equals("")){continue;}
                
                //读取ireport/sqlConfig.xml中每个sql文件配置            
                HashMap<String, String> map = new HashMap<String, String>();
                map.put("start", start);
                map.put("period", period);
                map.put("file", file);
                map.put("driver", driver);
                map.put("url", url);
                map.put("user", nullToEmpty(foo.elementText("user")));
                map.put("password", nullToEmpty(foo.elementText("password")));
                
                //s1: 如果start为空，则不启动
                if(!start.equals("")){
                    //s2: 如果start为*则服务器启动时启动
                    if(start.equals("*")){                        
                        sqlTimeMaintain(map);//执行
                    }else{
                        //s3: 如果start为具体时间，则在时间启动
                        Date startTime = BizDataUtil.formatHms.parse(start);
                        if(startTime.getTime() <= NOW_DATE.getTime()){                            
                            sqlTimeMaintain(map);//执行
                        }
                    }      
                } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    /**
     * 根据sql执行周期定制是否执行脚本
     * 
     * 1. period为*,*,*,*,*时依据start-time启动时执行一次，为,,,,或*或 空  时不执行
     * 2. 月数字间有空格时在每年对应月执行。月有值时优先考虑日忽略周，如果日和周都为为空则不执行, 为*时指定月每天都执行
     * 3. 日数字间有空格时在每月对应天执行。日有值时如果时为空或*，则不执行
     * 4. 时数字间有空格时则每天有2个以上的时间点执行，时有值时如果分为空或*则取00
     * 5. 分数字间有空格时，如果后面全为*或空，则以分钟为周期执行(5 RUN_PERIOD)
     * 6. 周有值时，如果日有值或日月都有值，则忽略周；如果时空或*则不执行。 周数字间有空格时，在对应周执行
     * 
     * @param map SQL配置信息
     * @return 
     * @throws Exception
     */

    private void sqlTimeMaintain(HashMap<String, String> map) throws Exception{
        
        //1. period为*,*,*,*,*时依据start-time启动时执行一次，为,,,,或*或 空  时不执行
        String period = map.get("period");
        if(!(period.equals(",,,,") || period.equals("*") || period.equals(""))){
            if(period.equals("*,*,*,*,*") && SCAN_TIMES == 0){
                execute(map);//---执行
            }else{
                
                String[] pArr = period.split(",");
                if(pArr.length == 5){

                    for(int i=0; i<pArr.length; i++){
                        pArr[i] = pArr[i].trim();
                    }   
                    String month = fmtData(pArr[3]);
                    String day = fmtData(pArr[2]);
                    String clock = fmtData(pArr[1]);
                    String min = fmtData(pArr[0]);
                    String week = pArr[4];                    
                   
                    String[] monthArr=null;
                    String[] dayArr=null;
                    String[] clockArr=null;
                    String[] minArr=null;
                    String[] weekArr=null;
                    
                    if(month.indexOf(" ") != -1){monthArr = month.split(" "); }
                    if(day.indexOf(" ") != -1){dayArr = day.split(" "); }
                    if(clock.indexOf(" ") != -1){clockArr = clock.split(" ");}
                    if(min.indexOf(" ") != -1){minArr = min.split(" ");}
                    if(week.indexOf(" ") != -1){weekArr = week.split(" ");}
                    
                    if(month.equals("") || month.equals("*")){
                        if(day.equals("") || day.equals("*")){                         
                            if(clock.equals("") || clock.equals("*")){
                                if(week.equals("") || week.equals("*")){
                                    if(!(min.equals("") || min.equals("*"))){
                                        if(minArr != null && minArr.length > 1){//分有空格
                                            
                                            long nowTime = NOW_DATE.getTime();
                                            Date tpTime = BizDataUtil.getCurDateByTime(NOW_HH+":"+minArr[0].trim()+":00");//当天clock+min日期
                                            Date tpTime2 = BizDataUtil.getCurDateByTime(NOW_HH+":"+minArr[1].trim()+":00");//当天clock+min日期
                                            if(tpTime.getTime()<=nowTime && Math.abs(nowTime - tpTime.getTime())/(long)(60*1000) < RUN_PERIOD){
                                                execute(map);//---执行
                                            }

                                            if(tpTime2.getTime()<=nowTime && Math.abs(nowTime - tpTime2.getTime())/(long)(60*1000) <= RUN_PERIOD){
                                                execute(map);//---执行
                                            }
                                        }                                   
                                    }                             
                                }else{
                                    if(weekArr != null && weekArr.length > 1){
                                        for(int k=0; k<weekArr.length; k++){
                                            String wkTemp = weekArr[k].trim();
                                            if(Integer.valueOf(wkTemp) == NOW_WEEK){                                               
                                                week = wkTemp;
                                                if(!(clock.equals(""))){
                                                    if(clock.equals("*")){
                                                        clock = NOW_HH;
                                                    }
                                                    sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                            monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                                }                                         
                                            }                                            
                                        }
                                    }else{
                                        if(Integer.valueOf(week) == NOW_WEEK){     
                                            if(!(clock.equals(""))){
                                                if(clock.equals("*")){
                                                    clock = NOW_HH;
                                                }
                                                sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                        monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                            } 
                                        }
                                    }
                                }  
                            }else{
                                if(week.equals("") || week.equals("*")){
                                    sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                            monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                 
                                }else{
                                    if(weekArr != null && weekArr.length > 1){
                                        for(int k=0; k<weekArr.length; k++){
                                            String wkTemp = weekArr[k].trim();
                                            if(Integer.valueOf(wkTemp) == NOW_WEEK){                                               
                                                week = wkTemp;
                                                sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                        monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                             
                                            }                                            
                                        }
                                    }else{
                                        if(Integer.valueOf(week) == NOW_WEEK){
                                            sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                    monthArr, dayArr, clockArr, minArr, weekArr);//---clock   
                                        }
                                    }
                                }       
                            }
                        }else{
                            //day不为空时优先考虑day, 忽略week
                            if(dayArr != null && dayArr.length > 1){
                                String dayTm = "";
                                for(int h=0; h<dayArr.length; h++){
                                    dayTm = fmtData(dayArr[h].trim());
                                    if(dayTm.equals(NOW_DAY)){
                                        if(!(clock.equals(""))){
                                            if(clock.equals("*")){
                                                clock = NOW_HH;
                                            }
                                            sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                    monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                        }
                                    }
                                }
                            }else{
                                if(day.equals(NOW_DAY)){
                                    if(!(clock.equals(""))){
                                        if(clock.equals("*")){
                                            clock = NOW_HH;
                                        }
                                        sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                           
                                    }
                                }                                    
                            }    
                        }
                    }else{
                        //月有值时优先考虑日忽略周，如果日和周都为为空则不执行, 为*时指定月每天都执行
                        if(monthArr != null && monthArr.length > 1){

                            String monTm = "";
                            for(int k=0; k<monthArr.length; k++){
                                monTm = monthArr[k].trim();
                                if(fmtData(monTm).equals(NOW_MONTH)){
                                    
                                    if(!day.equals("") && (week.equals("") || week.equals("*"))){//考虑日忽略周
                                        if(dayArr != null && dayArr.length > 1){
                                            String dayTm = "";
                                            for(int h=0; h<dayArr.length; h++){
                                                dayTm = dayArr[h].trim();
                                                if(fmtData(dayTm).equals(NOW_DAY)){
                                                    day = NOW_DAY;
                                                    if(!(clock.equals(""))){
                                                        if(clock.equals("*")){
                                                            clock = NOW_HH;
                                                        }
                                                        sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                                monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                                    }
                                                }
                                            }                                           
                                        }else{
                                            if(day.equals(NOW_DAY) || day.equals("*")){
                                                day = NOW_DAY;
                                                if(!(clock.equals(""))){
                                                    if(clock.equals("*")){
                                                        clock = NOW_HH;
                                                    }
                                                    sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                            monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                                }                                  
                                            }
                                        }                                        
                                    }else{//考虑周
                                        if(!week.equals("")){
                                            
                                            if(weekArr != null && weekArr.length > 1){
                                                String wkTemp = "";
                                                for(int j=0; j<weekArr.length; j++){
                                                    wkTemp = weekArr[j].trim();
                                                    if(Integer.valueOf(wkTemp) == NOW_WEEK){
                                                        week = wkTemp;
                                                        if(!(clock.equals(""))){
                                                            if(clock.equals("*")){
                                                                clock = NOW_HH;
                                                            }
                                                            sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                                    monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                                        }                                        
                                                    }                                            
                                                }
                                            }else{
                                                if(Integer.valueOf(week) == NOW_WEEK || week.equals("*")){
                                                    week = String.valueOf(NOW_WEEK);
                                                    if(!(clock.equals(""))){
                                                        if(clock.equals("*")){
                                                            clock = NOW_HH;
                                                        }
                                                        sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                                monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                                    }
                                                }
                                            }                                            
                                        }
                                    }                                    
                                }
                            }                             
                        }else{
                            if(month.equals(NOW_MONTH)){
                                
                                if(!day.equals("") && (week.equals("") || week.equals("*"))){//考虑日忽略周
                                    if(dayArr != null && dayArr.length > 1){
                                        String dayTm = "";
                                        for(int h=0; h<dayArr.length; h++){
                                            dayTm = dayArr[h].trim();
                                            if(fmtData(dayTm).equals(NOW_DAY)){
                                                day = NOW_DAY;
                                                if(!(clock.equals(""))){
                                                    if(clock.equals("*")){
                                                        clock = NOW_HH;
                                                    }
                                                    sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                            monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                                }
                                            }
                                        }                                           
                                    }else{
                                        if(day.equals(NOW_DAY) || day.equals("*")){
                                            day = NOW_DAY;
                                            if(!(clock.equals(""))){
                                                if(clock.equals("*")){
                                                    clock = NOW_HH;
                                                }
                                                sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                        monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                            }                                  
                                        }
                                    }                                        
                                }else{//考虑周
                                    if(!week.equals("")){
                                        
                                        if(weekArr != null && weekArr.length > 1){
                                            String wkTemp = "";
                                            for(int j=0; j<weekArr.length; j++){
                                                wkTemp = weekArr[j].trim();
                                                if(Integer.valueOf(wkTemp) == NOW_WEEK){
                                                    week = wkTemp;
                                                    if(!(clock.equals(""))){
                                                        if(clock.equals("*")){
                                                            clock = NOW_HH;
                                                        }
                                                        sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                                monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                                    }                                        
                                                }                                            
                                            }
                                        }else{
                                            if(Integer.valueOf(week) == NOW_WEEK || week.equals("*")){
                                                week = String.valueOf(NOW_WEEK);
                                                if(!(clock.equals(""))){
                                                    if(clock.equals("*")){
                                                        clock = NOW_HH;
                                                    }
                                                    sqlTimeMaintainSub(map, month, day, clock, min, week, 
                                                            monthArr, dayArr, clockArr, minArr, weekArr);//---clock                                            
                                                }
                                            }
                                        }                                            
                                    }
                                }                                  
                            }                            
                        }                       
                    }
                }
            }            
        }        
    }
    
    private void sqlTimeMaintainSub(HashMap<String, String> map, String month, String day, String clock, String min, String week, 
            String[] monthArr, String[] dayArr, String[] clockArr, String[] minArr, String[] weekArr){
        long nowTime = NOW_DATE.getTime();
        if(min.equals("") || min.equals("*")){    
            min = "00";
        }
        //-RUN_PERIOD/2 <= 运行时间 < RUN_PERIOD/2
        if(clockArr != null && clockArr.length > 1){
            for(int i=0; i<clockArr.length; i++){
                String clkTemp = fmtData(clockArr[i].trim());
                if(minArr != null && minArr.length > 1){//分有空格
                    int diffMin = Math.abs(Integer.valueOf(minArr[1].trim()) - Integer.valueOf(minArr[0].trim()));
                    //int remd = diffMin %  RUN_PERIOD;
                    if(diffMin >= RUN_PERIOD){//以空格隔开的数字间差不小于5分钟
                        
                        Date tpTime = BizDataUtil.getCurDateByTime(clkTemp+":"+minArr[0].trim()+":00");//当天clock+min日期
                        Date tpTime2 = BizDataUtil.getCurDateByTime(clkTemp+":"+minArr[1].trim()+":00");//当天clock+min日期
                        if(tpTime.getTime()<=nowTime && Math.abs(nowTime - tpTime.getTime())/(long)(60*1000) < RUN_PERIOD){
                            execute(map);//---执行
                        }
                        
                        if(tpTime2.getTime()<=nowTime && Math.abs(nowTime - tpTime2.getTime())/(long)(60*1000) < RUN_PERIOD){
                            execute(map);//---执行
                        }
                    }                                 
                }else{
                    Date tpTime = BizDataUtil.getCurDateByTime(clkTemp+":"+min+":00");//当天clock+min日期
                    if(tpTime.getTime()<=nowTime && Math.abs(nowTime - tpTime.getTime())/(long)(60*1000) < RUN_PERIOD){
                        execute(map);//---执行
                    }
                }                                              
            }
        }else{
            if(minArr != null && minArr.length > 1){//分有空格
                int diffMin = Math.abs(Integer.valueOf(minArr[1].trim()) - Integer.valueOf(minArr[0].trim()));
                //int remd = diffMin %  RUN_PERIOD;
                if(diffMin >= RUN_PERIOD){
                    
                    Date tpTime = BizDataUtil.getCurDateByTime(clock+":"+minArr[0].trim()+":00");//当天clock+min日期
                    Date tpTime2 = BizDataUtil.getCurDateByTime(clock+":"+minArr[1].trim()+":00");//当天clock+min日期
                    if(tpTime.getTime()<=nowTime && Math.abs(nowTime - tpTime.getTime())/(long)(60*1000) < RUN_PERIOD){
                        execute(map);//---执行
                    }
                    
                    if(tpTime2.getTime()<=nowTime && Math.abs(nowTime - tpTime2.getTime())/(long)(60*1000) < RUN_PERIOD){
                        execute(map);//---执行
                    }
                }                                 
            }else{
                Date tpTime = BizDataUtil.getCurDateByTime(clock+":"+min+":00");//当天clock+min日期
                if(tpTime.getTime()<=nowTime && Math.abs(nowTime - tpTime.getTime())/(long)(60*1000) < RUN_PERIOD){
                    execute(map);//---执行
                }
            }  
        }   
    }

    /**
     * 读取 SQL 文件，获取 SQL 语句
     * 
     * @param sqlFile
     *            SQL 脚本文件
     * @return List<sql> 返回所有 SQL 语句的 List
     * @throws Exception
     */

    private List<String> loadSql(String sqlFile) throws Exception {

        List<String> sqlList = new ArrayList<String>();
        try {
            InputStream sqlFileIn = new FileInputStream(sqlFile);
            StringBuffer sqlSb = new StringBuffer();
            byte[] buff = new byte[1024];
            int byteRead = 0;
            while ((byteRead = sqlFileIn.read(buff)) != -1) {
                sqlSb.append(new String(buff, 0, byteRead));
            }

            // Windows 下换行是 \r\n, Linux下是 \n
            String[] sqlArr = sqlSb.toString().split(";");
            for (int i = 0; i < sqlArr.length; i++) {
                // String sql = sqlArr[i].replaceAll("--.*", "").trim();
                String sql = sqlArr[i].trim();
                if (!sql.equals("")) {
                    sqlList.add(sql);
                }
            }
            sqlFileIn.close();
            return sqlList;
        } catch (Exception ex) {
            throw new Exception("SQL脚本"+sqlFile+"有误");
        }
    }

    /**
     * 传入连接来执行 SQL 脚本文件，这样可与其外的数据库操作同处一个事物中
     * 
     * @param conn
     *            传入数据库连接
     * @param map
     *            : 数据库链接信息
     * @throws Exception
     */

    public void execute(HashMap<String, String> map){

        Statement stmt = null;
        String sqlFile = getSqlFilePath(map.get("file"));

        List<String> sqlList;
        try {
            sqlList = loadSql(sqlFile);
            Connection conn = null;
            // 加载连接数据库的驱动类
            Class.forName(String.valueOf(map.get("driver")));
            conn = DriverManager.getConnection(String.valueOf(map.get("url")), String.valueOf(map.get("user")),
                    String.valueOf(map.get("password")));
            stmt = conn.createStatement();
            for (String sql : sqlList) {
                stmt.addBatch(sql);
            }
            int[] rows = stmt.executeBatch();
            System.out.println("Row count:" + Arrays.toString(rows));
    
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getWebinfPath() {

        String pt = this.getClass().getClassLoader().getResource("/").getPath();
        if (pt.indexOf("WEB-INF") > -1) {
            pt = pt.substring(0, pt.indexOf("WEB-INF"));
        }
        return pt;
    }

    /**     * 
     * @param fileName, eg: sql/sample.sql
     * @return
     */
    public String getSqlFilePath(String fileName) {

        String pt = getWebinfPath();
        return pt + "ireport/" + fileName;
    }
    
    private String nullToEmpty(String str) {
        String result = "";
        if (str != null) {
            if (!"null".equals(str)) {
                result = str;
            }
        }
        return result;
    }

    /**
     * 小于10时在前面加0
     */
    public String fmtData(String data){
        if(data.length() == 1){
            if(!data.equals("*")){
                data = "0"+data;
            }            
        }
        return data;
    }
}
