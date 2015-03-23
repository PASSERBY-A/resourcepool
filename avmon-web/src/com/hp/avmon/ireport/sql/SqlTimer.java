package com.hp.avmon.ireport.sql;

import java.util.Timer;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;


/**
 * @author lizhiyu
 */
public class SqlTimer implements ServletContextListener {

    private Timer timer = null; 
    
    public void contextInitialized(ServletContextEvent event) { 
        timer = new Timer(true);
        
        //制定每5分钟扫描一次sqlConfig.xml计划
        MakePlanForExecuteSql();        
        //制定每10分钟扫描一次reportConfig.xml, 定期生成报表记录
        MakePlanForReportRecord();     
        //制定每10分钟扫描一次reportConfig.xml, 定期发送邮件
        MakePlanForReportMail();
        
        
    }   
    
    /**
     * 每5分钟扫描一次sql/sqlConfig.xml, 并执行其配置的sql脚本
     * 
     * @param popStartDate
     */
    private void MakePlanForExecuteSql() {   
        int period = 100;        
        long frequency = period*60*1000;//运行周期(毫秒)
        timer.schedule(new ExecuteSqlTask(), 0, Math.abs(frequency));
        System.out.println("==MakePlanForExecuteSql()==");
    } 
    
    /**
     * 制定每10分钟扫描一次reportConfig.xml, 定期生成报表记录
     * 
     * @param popStartDate
     */
    private void MakePlanForReportRecord() {   
        int period = 100;        
        long frequency = period*60*1000;//运行周期(毫秒)
        timer.schedule(new RecordReportTask(), 0, Math.abs(frequency));
        System.out.println("==MakePlanForReportRecord()==");
    }
    
    /**
     * 制定每10分钟扫描一次reportConfig.xml, 定期发送邮件
     * 
     * @param popStartDate
     */
    private void MakePlanForReportMail() {   
        int period = 10;        
        long frequency = period*60*1000;//运行周期(毫秒)
        timer.schedule(new MailReportTask(), 0, Math.abs(frequency));
        System.out.println("==MakePlanForReportMail()==");
    }
    
    @Override
    public void contextDestroyed(ServletContextEvent arg0) {
        timer.cancel();         
    }
}
