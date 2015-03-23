package com.hp.avmon.ireport.sql;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.ireport.report.ReportMain;
import com.hp.avmon.ireport.util.BizDataUtil;
/**
 * @author lizhiyu
 * 
 */
@RequestMapping("/ireport/*")
public class RecordReportTask extends TimerTask {

    
    private static int RUN_PERIOD = 10;//扫描间隔10分钟一次
    
    private static int SCAN_TIMES = -1;//run的次数
    
    private static Date NOW_DATE;//当前日期
    
    private static int NOW_WEEK;//当前日期为星期几,0代表周日,1为周一    
        
    private JdbcTemplate jdbc;
    
    private ReportMain reportMain;

    private List<HashMap<String, Object>> htmlBean;
    
    private String htmlSql = " select ID as id, REPORT_ID as report_id, TYPE as type, START_TIME as start_time, PATH as path, UPDATED_DT as updated_dt from IREPORT_HTML ";
    
    /**
     * 扫描ireport/reportConfig.xml，并生成html文件
     */
    @Override
    public void run() {        
        SCAN_TIMES++;  
        NOW_DATE = new Date();
        NOW_WEEK = BizDataUtil.getDayOfWeek(NOW_DATE);
        if(NOW_WEEK == 0){NOW_WEEK = 7;}
        scanReportConfigFile();        
    }
    
    /**
     * 扫描ireport/reportConfig.xml
     * @param 
     * @return 
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "static-access" })
    private void scanReportConfigFile() {
        this.htmlBean = this.getJdbc().queryForList(String.format(htmlSql));
        if(this.htmlBean != null && this.htmlBean.size() > 0){
            for(int i=0; i<this.htmlBean.size(); i++ ){
                String reportId = (String)this.htmlBean.get(i).get("report_id");
                this.reportMain = new ReportMain(reportId);
                HashMap<String, Object> map = reportMain.getMainMap().get(reportId);
                try {
                    if(map != null && map.size() > 0){
    
                        if(map.containsKey("html_type") && !map.get("html_type").equals("")
                                && map.containsKey("html_start") && !map.get("html_start").equals("")){
                            String tm = String.valueOf(map.get("html_start"));
                            if(tm != null && tm.length()>19){
                                tm = tm.substring(0,19);
                            }
                            Date start = BizDataUtil.formatHms.parse(tm);
                            if(start.getTime() <= NOW_DATE.getTime()){                            
                                try {
                                    recordRptMaintain(map);//执行
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
     * 报表类型type，可填写day或month或year
                    day日报，表示匹配start-time每天生成一次报表记录
                    week周报，表示匹配start-time每周生成一次报表记录
                    month月报，表示匹配start-time每月生成一次报表记录
     * start-time：   开始生成报表记录时间，格式为yyyy-mm-dd hh:MM:ss
     * @param map report配置信息
     * @return 
     * @throws Exception
     */
    private void recordRptMaintain(HashMap<String, Object> map) throws Exception{
        
        String type = (String)map.get("html_type");
        String tm = String.valueOf(map.get("html_start"));
        if(tm != null && tm.length()>19){
            tm = tm.substring(0,19);
        }
        Date startTime = BizDataUtil.formatHms.parse(tm);
        String hms = BizDataUtil.HHMMSS.format(startTime);
        Date tempNow = BizDataUtil.getCurDateByTime(hms);//今天对应时间点日期
        
        long nowTime = NOW_DATE.getTime();
        long comTimeNow = tempNow.getTime();
        
        //1.每天生成一次报表记录
        if(type.equals("day")){
            if(comTimeNow<=nowTime && Math.abs(nowTime - comTimeNow)/(long)(60*1000) <= RUN_PERIOD){
                generate(map);//---生成
            }
            
        //1.每周生成一次报表记录
        }else if(type.equals("week")){
            int curWk = BizDataUtil.getDayOfWeek(NOW_DATE);//今天为星期几
            int startWk = BizDataUtil.getDayOfWeek(startTime);//开始日期为星期几
            if(curWk == startWk){
                if(comTimeNow<=nowTime && Math.abs(nowTime - comTimeNow)/(long)(60*1000) <= RUN_PERIOD){
                    generate(map);//---生成
                }                
            }
         
        //1.每月生成一次报表记录
        }else if(type.equals("month")){
            if(BizDataUtil.datefmtMon.format(startTime).equals(BizDataUtil.datefmtMon.format(NOW_DATE))
                    && BizDataUtil.datefmtDay.format(startTime).equals(BizDataUtil.datefmtDay.format(NOW_DATE))){
                if(comTimeNow<=nowTime && Math.abs(nowTime - comTimeNow)/(long)(60*1000) <= RUN_PERIOD){
                    generate(map);//---生成
                }                  
            }
        }
    }

    /**
     * 将ireport报表生成html文件，保存到ireport/source/recordHtml/目录下
     * 
     * @param conn 传入数据库连接
     * @param map : 数据库链接信息，报表属性
     * @throws Exception
     */

    public void generate(HashMap<String, Object> map){
        reportMain.generateRptRecordToHtml(map);
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

    public List<HashMap<String, Object>> getHtmlBean() {
        return htmlBean;
    }

    public void setHtmlBean(List<HashMap<String, Object>> htmlBean) {
        this.htmlBean = htmlBean;
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
