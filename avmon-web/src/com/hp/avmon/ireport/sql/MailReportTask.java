package com.hp.avmon.ireport.sql;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.ireport.report.ReportMain;
import com.hp.avmon.ireport.util.BizDataUtil;
/**
 * @author lizhiyu
 * 
 */
public class MailReportTask extends TimerTask {

    
    private static int RUN_PERIOD = 10;//扫描间隔5分钟一次
    
    private static int SCAN_TIMES = -1;//run的次数
    
    private static Date NOW_DATE;//当前日期
    
    private static int NOW_WEEK;//当前日期为星期几,0代表周日,1为周一
    
    private static String NOW_HH;//当前小时数
    
    private static String NOW_MONTH;//当前月
    
    private static String NOW_DAY;//当前日期day    
    
    @Autowired
    private JdbcTemplate jdbc;
    
    private ReportMain reportMain;

    private List<HashMap<String, Object>> emailBean;
    
    private String mailSql = " select ID as id, REPORT_ID as report_id, "
    		+ "START_TIME as start_time, PERIOD as period, EMAIL as email, HOST as host, UPDATED_DT as updated_dt,"
    		+ "IS_ATTACHMENT as is_attachment,ATTACHMENT_TYPE as attachment_type "
    		+ "from IREPORT_EMAIL ";
    
    /**
     * 扫描ireport/reportConfig.xml，并执行其配置的发送邮件功能
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
        scanReportConfig();        
    }
    
    /**
     * 扫描ireport/reportConfig.xml
     * @param 
     * @return 
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "static-access" })
    private void scanReportConfig() {

        this.emailBean = this.getJdbc().queryForList(mailSql);
        if(this.emailBean != null && this.emailBean.size() > 0){
            for(int i=0; i<this.emailBean.size(); i++ ){
                String reportId = (String)this.emailBean.get(i).get("report_id");
                this.reportMain = new ReportMain(reportId);
                HashMap<String, Object> map = reportMain.getMainMap().get(reportId);
                try {
                    if(map != null && map.size() > 0){
    
                        if(map.containsKey("mail_start") && !map.get("mail_start").equals("")
                                && map.containsKey("mail_period") && !map.get("mail_period").equals("")
                                     && map.containsKey("mail_email") && !map.get("mail_email").equals("")
                                         && map.containsKey("mail_host") && !map.get("mail_host").equals("")){
                            String tm = String.valueOf(map.get("mail_start"));
                            if(tm != null && tm.length()>19){
                                tm = tm.substring(0,19);
                            }
                            Date startTime = BizDataUtil.formatHms.parse(tm);
                            if(startTime.getTime() <= NOW_DATE.getTime()){                            
                                try {
                                    mailMaintain(map);//执行
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }                                              
                        }                        
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
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

    private void mailMaintain(HashMap<String, Object> map) throws Exception{
        
        //1. period为*,*,*,*,*时依据start-time启动时执行一次，为,,,,或*或 空  时不执行
        String period = (String)map.get("mail_period");
        if(!(period.equals(",,,,") || period.equals("*") || period.equals(""))){
            if(period.equals("*,*,*,*,*") && SCAN_TIMES == 0){
                sendMail(map);//---执行
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
                                                sendMail(map);//---执行
                                            }

                                            if(tpTime2.getTime()<=nowTime && Math.abs(nowTime - tpTime2.getTime())/(long)(60*1000) <= RUN_PERIOD){
                                                sendMail(map);//---执行
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
    
    private void sqlTimeMaintainSub(HashMap<String, Object> map, String month, String day, String clock, String min, String week, 
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
                            sendMail(map);//---执行
                        }
                        
                        if(tpTime2.getTime()<=nowTime && Math.abs(nowTime - tpTime2.getTime())/(long)(60*1000) < RUN_PERIOD){
                            sendMail(map);//---执行
                        }
                    }                                 
                }else{
                    Date tpTime = BizDataUtil.getCurDateByTime(clkTemp+":"+min+":00");//当天clock+min日期
                    if(tpTime.getTime()<=nowTime && Math.abs(nowTime - tpTime.getTime())/(long)(60*1000) < RUN_PERIOD){
                        sendMail(map);//---执行
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
                        sendMail(map);//---执行
                    }
                    
                    if(tpTime2.getTime()<=nowTime && Math.abs(nowTime - tpTime2.getTime())/(long)(60*1000) < RUN_PERIOD){
                        sendMail(map);//---执行
                    }
                }                                 
            }else{
                Date tpTime = BizDataUtil.getCurDateByTime(clock+":"+min+":00");//当天clock+min日期
                if(tpTime.getTime()<=nowTime && Math.abs(nowTime - tpTime.getTime())/(long)(60*1000) < RUN_PERIOD){
                    sendMail(map);//---执行
                }
            }  
        }   
    }

    /**
     * 传入 邮件配置信息，发送邮件
     * 
     * @param conn 传入数据库连接
     * @param map : 数据库链接信息, 邮件配置信息
     * @throws Exception
     */
    public void sendMail(HashMap<String, Object> map){

        this.reportMain.sendReportToEmail(map);
    }
    
    public String nullToEmpty(String str) {
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

    public ReportMain getReportMain() {
        return reportMain;
    }

    public void setReportMain(ReportMain reportMain) {
        this.reportMain = reportMain;
    }

    public List<HashMap<String, Object>> getEmailBean() {
        return emailBean;
    }

    public void setEmailBean(List<HashMap<String, Object>> emailBean) {
        this.emailBean = emailBean;
    }

    public JdbcTemplate getJdbc() {
        if(jdbc == null){
            this.jdbc=SpringContextHolder.getBean("jdbcTemplate");
        }
        return jdbc;
    }

    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc=SpringContextHolder.getBean("jdbcTemplate");
    }
}
