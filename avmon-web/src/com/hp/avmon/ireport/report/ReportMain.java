package com.hp.avmon.ireport.report;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JExcelApiExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.j2ee.servlets.ImageServlet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import com.hp.avmon.common.Config;
import com.hp.avmon.common.SpringContextHolder;
import com.hp.avmon.ireport.util.BizDataUtil;
import com.hp.avmon.ireport.util.EmailUtil;

/**
 * @author lizhiyu
 */
public class ReportMain {
    
    private static HashMap<String, HashMap<String, Object>> mainMap = new HashMap<String, HashMap<String, Object>>();
    
    private static String urlParams = null;

    private static final Log logger = LogFactory.getLog(ReportMain.class);    
    
    private Locale locale;
    
    @Autowired
    private JdbcTemplate jdbc;
    
    private List<HashMap<String, Object>> reportMgtBean;
    
    private List<HashMap<String, Object>> dataSourceBean;
    
    private List<HashMap<String, Object>> htmlBean;
    
    private List<HashMap<String, Object>> emailBean;
    
    private String mgtSql = " select ID as id, REPORT_ID as report_id, REPORT_NAME as report_name, DATASOURCE_ID as datasource_id, TEMPLATE as template, MENU as menu, UPDATED_DT as updated_dt from IREPORT_MGT ";
    
    private String dataSourceSql = " select ID as id, TITLE as title, DRIVER as driver, URL as url, USER_NAME as user_name, PASSWORD as password, UPDATED_DT as updated_dt from IREPORT_DATASOURCE ";
    
    private String htmlSql = " select ID as id, REPORT_ID as report_id, TYPE as type, START_TIME as start_time, PATH as path, UPDATED_DT as updated_dt from IREPORT_HTML ";
    
    private String mailSql = " select ID as id, REPORT_ID as report_id, START_TIME as start_time, PERIOD as period, EMAIL as email, HOST as host, UPDATED_DT as updated_dt,IS_ATTACHMENT as is_attachment,ATTACHMENT_TYPE as attachment_type from IREPORT_EMAIL ";
    
    public ReportMain() {
        
    }
    
    @SuppressWarnings({ "unchecked" })
    public ReportMain(String reportId) {
        if(reportId!=null && !reportId.equals("")){
            
            this.reportMgtBean = this.getJdbc().queryForList(mgtSql + " where REPORT_ID = '"+reportId+"'");
            this.htmlBean = this.getJdbc().queryForList(htmlSql+" where REPORT_ID = '"+reportId+"'");
            this.emailBean = this.getJdbc().queryForList(mailSql+" where REPORT_ID = '"+reportId+"'");
            
            if(this.reportMgtBean != null && this.reportMgtBean.size() > 0){
                String dataSourceId = (String) this.reportMgtBean.get(0).get("datasource_id");
                this.dataSourceBean = this.getJdbc().queryForList(dataSourceSql+" where ID = '"+dataSourceId+"'");
            }
            ReportMain.setMainMap(getReportConfiguration(reportId));
            logger.debug("===iReport Constructor");
        }
    }

    /**
     * 读取reportConfig中report配置信息
     * @param reportId
     */
    public HashMap<String, HashMap<String, Object>> getReportConfiguration(String reportId){
        HashMap<String, Object> map = new HashMap<String, Object>();
        //if(!mainMap.containsKey(reportId)){

            if(this.reportMgtBean != null && this.reportMgtBean.size() > 0
                    && this.dataSourceBean != null && this.dataSourceBean.size() > 0){
                
                map.put("reportId", reportId);
                map.put("reportName", this.reportMgtBean.get(0).get("report_name"));
                map.put("template", Config.getInstance().getReportTemplatePath()+reportId+"/");
                map.put("menu", this.reportMgtBean.get(0).get("menu"));
                map.put("driver", this.dataSourceBean.get(0).get("driver"));
                map.put("url", this.dataSourceBean.get(0).get("url"));
                map.put("userName", this.dataSourceBean.get(0).get("user_name"));
                map.put("password", this.dataSourceBean.get(0).get("password"));
                
                if(this.htmlBean != null && this.htmlBean.size() > 0){

                    map.put("html_type", this.htmlBean.get(0).get("type"));
                    map.put("html_start", this.htmlBean.get(0).get("start_time"));
                    map.put("html_path", Config.getInstance().getReportHtmlPath()+reportId+"/");
                }
                if(this.emailBean != null && this.emailBean.size() > 0){

                    map.put("mail_start", this.emailBean.get(0).get("start_time"));
                    map.put("mail_period", this.emailBean.get(0).get("period"));
                    map.put("mail_email", this.emailBean.get(0).get("email"));
                    map.put("mail_host", this.emailBean.get(0).get("host"));
                    map.put("is_attachment", this.emailBean.get(0).get("is_attachment"));
                    map.put("attachment_type", this.emailBean.get(0).get("attachment_type"));
                }
                mainMap.put(reportId, map);                
            }
        //}
        
        System.out.println(map.get("template"));
        return mainMap;
    }
    
    /**
     * 构造JasperPrint
     * @param reportId
     * @return
     */
    public JasperPrint setJasperPrint(String reportId, String params,Locale locale) {
    	System.out.println("ReportMain setJsperPrint reportId>>>>>>>>>>>>>>>>>>>>>>>>>" + reportId  );
    	System.out.println("ReportMain setJsperPrint mainMap>>>>>>>>>>>>>>>>>>>>>>>>>" + mainMap  );
        HashMap<String, Object> map = mainMap.get(reportId);
        System.out.println("ReportMain setJsperPrint " + reportId  + " map>>>>>>>>>>>>>>>>>>>" + map);
        logger.debug("ReportMain setJsperPrint reportId>>>>>>>>>>>>>>>>>>>>>>>>>" + reportId);
        logger.debug("ReportMain setJsperPrint mainMap>>>>>>>>>>>>>>>>>>>>>>>>>" + mainMap  );
        logger.debug("ReportMain setJsperPrint " + reportId  + " map>>>>>>>>>>>>>>>>>>>" + map.toString());
        
        JasperPrint print = null;
        if(map != null && map.size() != 0){
            try {
                Connection conn = null;
                // 加载连接数据库的驱动类
//                System.out.println("Repoain setJsperPrint password>>>>>>>>>>>>>>>>>>>" + String.valueOf(map.get("password")));
//                System.out.println("ReportMrtMain setJsperPrint driver>>>>>>>>>>>>>>>>>>>" + String.valueOf(map.get("driver")));
//                System.out.println("ReportMain setJsperPrint url>>>>>>>>>>>>>>>>>>>" + String.valueOf(map.get("url")));
//                System.out.println("ReportMain setJsperPrint userName>>>>>>>>>>>>>>>>>>>" + String.valueOf(map.get("userName")));
//                System.out.println("ReportMain setJsperPrint template>>>>>>>>>>>>>>>>>>>" + String.valueOf(map.get("template")));
                
                Class.forName(String.valueOf(map.get("driver"))); 
                conn = DriverManager.getConnection(String.valueOf(map.get("url")), String.valueOf(map.get("userName")),
                        String.valueOf(map.get("password")));
        
                // 装载jasper文件application                
                File path = new File((String)map.get("template"));
                String fileName = "";
                File[] files = path.listFiles();                

                if (files == null) 
                    return null; 
                for (int i = 0; i < files.length; i++) {
                    if(files[i].getName().indexOf("jasper") != -1 && files[i].getName().indexOf("sub.jasper") == -1){
                        fileName = files[i].getName();
                        break;
                    }
                }      
                System.out.println("ReportMain setJsperPrint reportFile>>>>>>>>>>>>>>>>>>>" + String.valueOf(map.get("template"))+fileName);
                logger.debug("ReportMain setJsperPrint reportFile>>>>>>>>>>>>>>>>>>>" + String.valueOf(map.get("template"))+fileName);
                File reportFile = new File((String)map.get("template")+fileName);
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>params" + params);
                logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>params" + params);
                Map<String,Object> parameters = null;//定义参数map
                if(!nullToEmpty(params).equals("")){
                    parameters = new HashMap<String,Object>();
//                    String[] parArr = params.split(",");
                    String[] parArr = params.split(";");
                    if(parArr.length==2&&(parArr[0].indexOf("commonReportDate")>-1||parArr[1].indexOf("commonReportDate")>-1)){//若传过来的参数是commonReportDate则拆分成FromDate，ToDate
                    	String reportType = parArr[0].split("\\|")[1];
                    	// add by mark start 转码 
                    	reportType = new String(reportType.getBytes("ISO-8859-1"),"UTF-8");
                    	// add by mark end
                    	String commonReportDate = parArr[1].split("\\|")[2];
                    	
                    	Date reportDate = BizDataUtil.format.parse(commonReportDate);
                    	Date fromDate = null;
                    	Date toDate = null;
                    	String reportTypeParam = "";
                    	if("日报".equals(reportType)||"0".equals(reportType)){//日报 
                    		fromDate = reportDate;
                    		toDate = BizDataUtil.format.parse(BizDataUtil.getDayBefore(commonReportDate, 1));
                    		reportTypeParam = "DAY";
                    	}else if("周报".equals(reportType)||"1".equals(reportType)){//周报
                    		int weekDay = reportDate.getDay();
                    		if(weekDay == 0){
                    			weekDay = 7;
                    		}
                    		fromDate = BizDataUtil.format.parse(BizDataUtil.getDayBefore(commonReportDate, 1-weekDay));
                			toDate = BizDataUtil.format.parse(BizDataUtil.getDayBefore(commonReportDate, 7-weekDay+1));
                			reportTypeParam = "WEEK";
                    	}else if("月报".equals(reportType)||"2".equals(reportType)){//月报
                    		int month = reportDate.getMonth() + 1;
                    		Calendar c = Calendar.getInstance();
                            c.setTime(reportDate);
                    		c.get(Calendar.YEAR);

                    		Calendar cal = Calendar.getInstance();
                    		cal.set(Calendar.YEAR,c.get(Calendar.YEAR));
                    		cal.set(Calendar.MONTH, month);
                    		cal.set(Calendar.DAY_OF_MONTH, 1);
                    		cal.add(Calendar.DAY_OF_MONTH, -1);
                    		toDate = BizDataUtil.format.parse(BizDataUtil.format.format(cal.getTime()));
                    		
                    		Calendar c1 = Calendar.getInstance(); 
                            c1.setTime(toDate);
                    		c1.add(Calendar.DATE, 1);
                    		toDate = c1.getTime();
                    		
                    		cal.set(Calendar.DAY_OF_MONTH, 1);  
                    		fromDate = BizDataUtil.format.parse(BizDataUtil.format.format(cal.getTime()));
                    		reportTypeParam = "MONTH";
                    	}
                    	
                    	String fromDateS = BizDataUtil.format.format(fromDate);
                    	String toDateS = BizDataUtil.format.format(toDate);
                    	
                		parameters.put("FromDate", fromDateS); 
                		parameters.put("ToDate", toDateS);
                		parameters.put("ReportType", reportTypeParam);
                		parameters.put("REPORT_LOCALE", locale);
                    }else{
                    	for(int i=0; i<parArr.length; i++){
                        	System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>parArr"+i + ">>" + parArr[i]);
                        	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>parArr"+i + ">>" + parArr[i]);
                        	if(parArr[i].indexOf("commonReportDate")>-1){
                        		//若传过来的参数是commonReportDate则拆分成FromDate，ToDate
                        		String reportType = "0";//parArr[0].split("\\|")[1];
                        		for(int j=0; j<parArr.length; j++){
                        			if(parArr[j].indexOf("_reportType")>-1){
                        				reportType = parArr[j].split("\\|")[1];
                        				break;
                        			}
                        		}
                            	
                            	// add by mark start 转码 
                            	reportType = new String(reportType.getBytes("ISO-8859-1"),"UTF-8");
                            	// add by mark end
                            	String commonReportDate = parArr[i].split("\\|")[2];
                            	
                            	Date reportDate = BizDataUtil.format.parse(commonReportDate);
                            	Date fromDate = null;
                            	Date toDate = null;
                            	String reportTypeParam = "";
                            	if("日报".equals(reportType)||"0".equals(reportType)){//日报 
                            		fromDate = reportDate;
                            		toDate = BizDataUtil.format.parse(BizDataUtil.getDayBefore(commonReportDate, 1));
                            		reportTypeParam = "DAY";
                            	}else if("周报".equals(reportType)||"1".equals(reportType)){//周报
                            		int weekDay = reportDate.getDay();
                            		if(weekDay == 0){
                            			weekDay = 7;
                            		}
                            		fromDate = BizDataUtil.format.parse(BizDataUtil.getDayBefore(commonReportDate, 1-weekDay));
                        			toDate = BizDataUtil.format.parse(BizDataUtil.getDayBefore(commonReportDate, 7-weekDay+1));
                        			reportTypeParam = "WEEK";
                            	}else if("月报".equals(reportType)||"2".equals(reportType)){//月报
                            		int month = reportDate.getMonth() + 1;
                            		Calendar c = Calendar.getInstance();
                                    c.setTime(reportDate);
                            		c.get(Calendar.YEAR);

                            		Calendar cal = Calendar.getInstance();
                            		cal.set(Calendar.YEAR,c.get(Calendar.YEAR));
                            		cal.set(Calendar.MONTH, month);
                            		cal.set(Calendar.DAY_OF_MONTH, 1);
                            		cal.add(Calendar.DAY_OF_MONTH, -1);
                            		toDate = BizDataUtil.format.parse(BizDataUtil.format.format(cal.getTime()));
                            		
                            		Calendar c1 = Calendar.getInstance(); 
                                    c1.setTime(toDate);
                            		c1.add(Calendar.DATE, 1);
                            		toDate = c1.getTime();
                            		
                            		cal.set(Calendar.DAY_OF_MONTH, 1);  
                            		fromDate = BizDataUtil.format.parse(BizDataUtil.format.format(cal.getTime()));
                            		reportTypeParam = "MONTH";
                            	}
                            	
                            	String fromDateS = BizDataUtil.format.format(fromDate);
                            	String toDateS = BizDataUtil.format.format(toDate);
                            	
                        		parameters.put("FromDate", fromDateS); 
                        		parameters.put("ToDate", toDateS);
                        		parameters.put("ReportType", reportTypeParam);
                        		parameters.put("REPORT_LOCALE", locale);
                            }else if(parArr[i].indexOf("_reportType")>-1){
                            	continue;
                            }else{
                            	if(!parArr[i].equals("")){
                                    String[] subArr = parArr[i].split("\\|");//parArr[i].split(":");
                                    System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>subArr length: " + subArr.length);
                                    logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>subArr length: " + subArr.length);
                                    if(subArr.length == 3){
                                        String type = subArr[1];
                                        String val = nullToEmpty(subArr[2]);
                                        Object value = "";
                                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>subArr type: " + type);
                                        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>subArr val: " + val);
                                        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>subArr type: " + type);
                                        logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>subArr val: " + val);
                                        if(!val.equals("")){

                                            if(type.indexOf("Date") != -1){
                                                value = BizDataUtil.format.parse(val);
                                            }else if(type.indexOf("Time") != -1 && type.indexOf("Timestamp") < 0){
                                                value = Time.valueOf(val);
                                            }else if(type.indexOf("Boolean") != -1){
                                                value = Boolean.valueOf(val);
                                            }else if(type.indexOf("Integer") != -1){
                                                value = Integer.valueOf(val);
                                            }else if(type.indexOf("Double") != -1){
                                                value = Double.parseDouble(val);
                                            }else if(type.indexOf("Number") != -1){
                                                NumberFormat nf = NumberFormat.getInstance();                                        
                                                value = nf.format(val);
                                            }else if(type.indexOf("Byte") != -1){
                                                value = val.getBytes();
                                            }else if(type.indexOf("Timestamp") != -1){
                                                value = Timestamp.valueOf(val);
                                            }else if(type.indexOf("Float") != -1){
                                                value = Float.valueOf(val);
                                            }else if(type.indexOf("Long") != -1){
                                                value = Long.valueOf(val);
                                            }else if(type.indexOf("Short") != -1){
                                                value = Short.valueOf(val);
                                            }else if(type.indexOf("BigDecimal") != -1){
                                                value = new BigDecimal(val);
                                            }else{
                                                value = val;
                                            }
                                            
                                            parameters.put(subArr[0], value); //添加参数                                         
                                        }                           
                                    }else{
                                    	parameters.put(subArr[0], ""); 
                                    }
                                }
                            }
                        }
                    }
                    
                }                
                System.out.println("ReportMain setJsperPrint parameters>>>>>>>>>>>>>>>>>>>" + parameters);                              
                System.out.println("ReportMain setJsperPrint reportFile.getPath()>>>>>>>>>>>>>>>>>>>" + reportFile.getPath());
                System.out.println("ReportMain setJsperPrint conn>>>>>>>>>>>>>>>>>>>" + conn);
                logger.debug("ReportMain setJsperPrint parameters>>>>>>>>>>>>>>>>>>>" + parameters);
                logger.debug("ReportMain setJsperPrint reportFile.getPath()>>>>>>>>>>>>>>>>>>>" + reportFile.getPath());
                logger.debug("ReportMain setJsperPrint conn>>>>>>>>>>>>>>>>>>>" + conn);
                try {
					print = JasperFillManager.fillReport(reportFile.getPath(), parameters, conn);
				} catch (JRException e) {
					e.printStackTrace();
				}
                
                System.out.println("ReportMain setJsperPrint print>>>>>>>>>>>>>>>>>>>" + print);
                logger.debug("ReportMain setJsperPrint print>>>>>>>>>>>>>>>>>>>" + print);
                
                if(conn != null){
                    conn.close();
                }
            } catch (Exception e) {
            	System.out.println("ReportMain setJsperPrint print>>>>>>>>>>>>>>>>>>>" + e);
            	logger.error(this.getClass().getName()+e.getMessage());
                e.printStackTrace();
            }
        }
        System.out.println("ReportMain setJsperPrint print>>>>>>>>>>>>>>>>>>>end");
        logger.debug("ReportMain setJsperPrint print>>>>>>>>>>>>>>>>>>>end");
        return print;
    }
    
    /*
     * 生成HTML报表显示
     */
    public void selectHtmlReport(HttpServletRequest request, HttpServletResponse response, String reportId, String params) {
    	System.out.println("selectHtmlReport come in 1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
    	logger.debug("selectHtmlReport come in 1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        response.setContentType("text/html;charset=utf-8");
        try {
        	Locale locale = request.getLocale();
        	System.out.println("selectHtmlReport come in 2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        	logger.debug("selectHtmlReport come in 2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            JasperPrint jasperPrint  = this.setJasperPrint(reportId, params,locale);
            logger.debug("selectHtmlReport come in 3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            if(jasperPrint != null){
                JRHtmlExporter exporter = new JRHtmlExporter();
                PrintWriter out = response.getWriter();
                ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);      
                //exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, TEMP_PATH+reportId+"?image=");
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "rptTemplate?image=");
                
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, Config.getInstance().getReportTemplatePath()+reportId+"/"); 
                //exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, getWebinfPath()+"/ireport/rptTemplate"+"//");   
                request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
                
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); // 删除记录最下面的空行
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 删除多余的ColumnHeader
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 显示边框
                exporter.exportReport();
            
            }
            
       } catch (Exception e) {
           e.printStackTrace();
       }
       //return null;
    }

    /*
     * 导出EXCEL报表
     */
    public void selectExcelReport( HttpServletResponse response, String reportId, String params) {
        try {
            JasperPrint jasperPrint  = this.setJasperPrint(reportId, params,locale);

            if(jasperPrint != null){
                // 输出流
                ByteArrayOutputStream oStream = new ByteArrayOutputStream();

                // 构造输出对象
                JExcelApiExporter exporter = new JExcelApiExporter();
                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "./image?image=");
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); // 删除记录最下面的空行
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 删除多余的ColumnHeader
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 显示边框
                exporter.exportReport();

                // excel文件名
                String fileName = mainMap.get(reportId).get("reportName") + ".xls";
                response.reset();
                response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8")
                        + "\"");
                // 写输出流
                byte[] bytes = oStream.toByteArray();
                if (bytes != null && bytes.length > 0) {
                    response.setContentType("application/vnd.ms-excel;charset=utf-8");
                    response.setContentLength(bytes.length);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                } else {
                }    
                oStream.close();               
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*
     * 导出PDF报表
     */
    public void selectPdfReport( HttpServletResponse response, String reportId, String params){
        try {
            JasperPrint jasperPrint  = this.setJasperPrint(reportId, params,locale);   
            
            if(jasperPrint != null){
                ByteArrayOutputStream oStream = new ByteArrayOutputStream();
                JRExporter exporter = new JRPdfExporter();   
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);  
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);              

                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "./image?image=");
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); // 删除记录最下面的空行
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 删除多余的ColumnHeader
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 显示边框
                exporter.exportReport();

                // pdf文件名
                String fileName = mainMap.get(reportId).get("reportName") + ".pdf";
                response.reset();
                response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8")
                        + "\"");
                // 写输出流
                byte[] bytes = oStream.toByteArray();
                if (bytes != null && bytes.length > 0) {
                    response.setContentType("application/vnd.ms-pdf;charset=utf-8");
                    response.setContentLength(bytes.length);
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                } else {
                }      
                oStream.close();          
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /*
     * 生成报表文件
     */
    public void generateReportFile(String reportId, String fileType,String params){
    	FileOutputStream fos=null;
        try {
            JasperPrint jasperPrint  = this.setJasperPrint(reportId, params,locale);   
            
            if(jasperPrint != null){
                ByteArrayOutputStream oStream = new ByteArrayOutputStream();
                JRExporter exporter = new JRPdfExporter();   //JExcelApiExporter //JRRtfExporter
                if("doc".equalsIgnoreCase(fileType)){
                	exporter = new JRRtfExporter();
                }else if("xls".equalsIgnoreCase(fileType)){
                	exporter = new JExcelApiExporter();
                }
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);  
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);              

                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "./image?image=");
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); // 删除记录最下面的空行
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 删除多余的ColumnHeader
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 显示边框
                //exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "GB2312");
                exporter.exportReport();

                // 报表文件名
                String fileName = mainMap.get(reportId).get("reportName")+ "_" + BizDataUtil.formatHHmmss.format(new Date()) + "." + fileType;
                String filePath = Config.getInstance().getReportTemplatePath() + File.separator + reportId + File.separator + fileName;
                // 写输出流
                byte[] bytes = oStream.toByteArray();
                if (bytes != null && bytes.length > 0) {
                    //写文件
                    
                    File reportFile = new File(filePath);

                    if (!reportFile.getParentFile().exists()) {  
                    	reportFile.getParentFile().mkdirs();
                    }  
                    if(!reportFile.exists()){
                    	reportFile.createNewFile();
                    	logger.debug("文件"+reportFile.getPath()+"已创建");
                    }
                    fos=new FileOutputStream(reportFile);
                    fos.write(bytes);
                    fos.flush();
                    logger.debug("文件内容写入完毕");                            
                } else {
                }   
                
                oStream.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally{   
            try{   
                fos.close();   
            }   
            catch(IOException iex){}   
        }  
    }

    /*
     * 导出word报表
     */
    public void selectWordReport( HttpServletResponse response, String reportId, String params){
    	logger.debug("selectWordReport come in 1>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        try {
        	logger.debug("selectWordReport come in 2>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            JasperPrint jasperPrint  = this.setJasperPrint(reportId, params,locale);
            logger.debug("selectWordReport come in 3>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
            
            if(jasperPrint != null){
                ByteArrayOutputStream oStream = new ByteArrayOutputStream();
                JRExporter exporter = new JRRtfExporter();   
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);  
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);              

                exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "./image?image=");
                exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); // 删除记录最下面的空行
                exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 删除多余的ColumnHeader
                exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 显示边框
                exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "GB2312");
                exporter.exportReport();

                // word文件名
                String fileName = mainMap.get(reportId).get("reportName") + ".doc";
                response.reset();
                response.setContentType("application/msword;charset=utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "utf-8")
                        + "\"");
                
                // 写输出流
                byte[] bytes = oStream.toByteArray();
                if (bytes != null && bytes.length > 0) {
                    //response.setContentType("application/vnd.ms-word;charset=utf-8");
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();
                } else {
                }        
                oStream.close();
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 获取报表中设置的参数
     * @param reportId
     * @return
     */
    public List<HashMap<String, String>> getParameters(String reportId){
        List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>(); 
        String tmpXml = "";
        
        try {
            
            HashMap<String, Object> map = mainMap.get(reportId);
            File file=new File((String)map.get("template"));
            File[] files = file.listFiles();
            if (files == null) 
                return null; 
            for (int i = 0; i < files.length; i++) {
                if(files[i].getName().indexOf("jrxml") != -1 && files[i].getName().indexOf("sub.jrxml") == -1){
                    tmpXml = files[i].getName();
                    break;
                }
            }    
            
            if(!tmpXml.equals("") && tmpXml.indexOf("jrxml") != -1){

                File fxml = new File(Config.getInstance().getReportTemplatePath() + reportId +"/"+ tmpXml); 
                SAXReader readxml = new SAXReader(); 
                org.dom4j.Document docxml = readxml.read(fxml); 
                Element rootxml = docxml.getRootElement(); 
                List parNote = rootxml.elements("parameter");
                Element fooxml; 
                for (@SuppressWarnings("rawtypes")
                Iterator j = parNote.iterator(); j.hasNext();) {
                    fooxml = (Element) j.next();
                    String name = fooxml.attribute("name").getText();
                    String type = fooxml.attribute("class").getText();
                    if(name == null){continue;}
                    Element defEle = fooxml.element("defaultValueExpression"); 
                    String defaultValue = null;
                    if(defEle!=null){
                    	defaultValue = nullToEmpty(defEle.elementText("defaultValueExpression"));
                        defaultValue = defaultValue.replace("<![CDATA[", "");
                        defaultValue = defaultValue.replace("]]>", "");
                    }
                    HashMap<String, String> params = new HashMap<String, String>(); 
                    params.put("NAME", name);
                    params.put("TYPE", type);
                    params.put("DEFAULT", defaultValue);
                    resultList.add(params);    
                }
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return resultList;
    }
    
    /**
     * 获取URL中设置的参数
     * @param reportId
     * @return
     */
    public String getUrlParameters(HttpServletRequest request, String reportId){
        
        StringBuffer params = new StringBuffer(); 
        List<HashMap<String, String>> resultList = getParameters(reportId);
        int c = 0;    
        if(urlParams != null){
            
            String[] pms = urlParams.split("&");
            for(int i=0; i<pms.length; i++){
                String[] pm = pms[i].split("=");
                if(pm.length > 1){
                    if(!pm[0].equals("reportId")){

                        for(int k=0; k<resultList.size(); k++ ){
                            HashMap<String, String> map = resultList.get(k);
                            if(pm[0].equals(map.get("NAME"))){
                                if(c == 0){
                                    params.append(pm[0]+"|"+map.get("TYPE")+"|"+pm[1]);                                
                                }else{
                                    params.append(","+pm[0]+"|"+map.get("TYPE")+"|"+pm[1]);  
                                }
                                c++;
                            }
                        }
                    }
                }
            }             
        }       
        return params.toString();
    }

    
    /**
     * 获取报表中设置的参数, 如果URL中带有参数，则使用URL中的参数
     * @param reportId
     * @return
     */
    public Boolean isUrlParameter(HttpServletRequest request, String reportId){
        Boolean flg = false;
        List<HashMap<String, String>> resultList = new ArrayList<HashMap<String, String>>(); 
        String tmpXml = "";
        
        try {       

            HashMap<String, Object> map = mainMap.get(reportId);
            File file=new File((String)map.get("template"));
            File[] files = file.listFiles();
            if (files == null) 
                return null; 
            for (int i = 0; i < files.length; i++) {
                if(files[i].getName().indexOf("jrxml") != -1 && files[i].getName().indexOf("sub.jrxml") == -1){
                    tmpXml = files[i].getName();
                    break;
                }
            }    
            
            if(!tmpXml.equals("") && tmpXml.indexOf("jrxml") != -1){

                File fxml = new File(Config.getInstance().getReportTemplatePath() +reportId +"/"+ tmpXml); 
                SAXReader readxml = new SAXReader(); 
                org.dom4j.Document docxml = readxml.read(fxml); 
                Element rootxml = docxml.getRootElement(); 
                List parNote = rootxml.elements("parameter");
                Element fooxml; 
                for (@SuppressWarnings("rawtypes")
                Iterator j = parNote.iterator(); j.hasNext();) {
                    fooxml = (Element) j.next();
                    String name = fooxml.attribute("name").getText();
                    String type = fooxml.attribute("class").getText();
                    if(name == null){continue;}
                    Element defEle = fooxml.element("defaultValueExpression"); 
                    String defaultValue = null;
                    if(defEle!=null){
                    	defaultValue = nullToEmpty(defEle.elementText("defaultValueExpression"));
                        defaultValue = defaultValue.replace("<![CDATA[", "");
                        defaultValue = defaultValue.replace("]]>", "");
                    }
                    HashMap<String, String> params = new HashMap<String, String>(); 
                    params.put("NAME", name);
                    params.put("TYPE", type);
                    params.put("DEFAULT", defaultValue);
                    resultList.add(params);    
                }
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }  
        //URL是否带了参数            
        urlParams = request.getQueryString();
        if(urlParams != null && urlParams.indexOf("&") != -1){

            try {
                urlParams = new String(urlParams.getBytes("ISO-8859-1"),"UTF-8");
                urlParams.replaceAll("20%", " ");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            List<String> prmList = new ArrayList<String>();
            String[] pms = urlParams.split("&");
            for(int i=0; i<pms.length; i++){
                String[] pm = pms[i].split("=");
                if(pm.length > 0 && !pm[0].equals("reportId")){
                    prmList.add(pm[0]);
                }             
            }
            if(prmList.size() > 0 && prmList.containsAll(resultList)){
                flg = true;
            }       
        }
        return flg;
    }

    /**
     * 将ireport报表生成html文件，保存到ireport/source/recordHtml/目录下, 只针对没有参数及表格报表
     * 
     * @param conn 传入数据库连接
     * @param map : 数据库链接信息，报表属性
     * @throws Exception
     */
    public void generateRptRecordToHtml(HashMap<String, Object> map){
        FileOutputStream fos=null;  
        String reportId = (String)map.get("reportId");
        List<HashMap<String, String>> list = getParameters(reportId);
        if(list == null || list.size() == 0){
            try {
                JasperPrint jasperPrint  = this.setJasperPrint(reportId, null,locale);   
                
                if(jasperPrint != null){
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    //PrintWriter out = response.getWriter();
                    ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    //exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);      
                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, Config.getInstance().getReportTemplatePath()+reportId+"?image=");
                    
                    exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,Boolean.TRUE);
                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, Config.getInstance().getReportTemplatePath()+reportId+"/");  
                    //request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
                    
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
                    exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); // 删除记录最下面的空行
                    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 删除多余的ColumnHeader
                    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 显示边框
                    exporter.exportReport();

                    // 写输出流
                    byte[] bytes = oStream.toByteArray();
                    if (bytes != null && bytes.length > 0) {
                        
                        //装载jasper
                        File file = new File((String)map.get("template"));
                        String fileName = "";
                        File[] files = file.listFiles();                

                        if (files == null) 
                            return; 
                        for (int i = 0; i < files.length; i++) {
                            //需要的是主报表模板，而不是子报表
                            if(files[i].getName().indexOf("jasper") != -1 && files[i].getName().indexOf("sub.jasper") == -1){
                                fileName = (String)map.get("template")+files[i].getName();
                                break;
                            }
                        }  
                        
                        //写文件
                        String txt = new String(bytes);
                        if(txt.indexOf("chart") == -1 && fileName.indexOf(".jasper") != -1){
                            String path = Config.getInstance().getReportTemplatePath()+reportId+"/"+reportId+BizDataUtil.formatHHmmss.format(new Date())+".html";
                            File hfile = new File(path);

                            if (!hfile.getParentFile().exists()) {  
                                hfile.getParentFile().mkdirs();
                            }  
                            if(!hfile.exists()){
                                hfile.createNewFile();
                                logger.debug("文件"+hfile.getPath()+"已创建");
                            }
                            fos=new FileOutputStream(hfile);
                            fos.write(bytes);
                            fos.flush();
                            logger.debug("文件内容写入完毕");                            
                        }
                    } else {
                    }     
                }
                
            } catch (Exception ex) {
            	logger.error(this.getClass().getName()+ex.getMessage());
                ex.printStackTrace();
            }      
            finally{   
                try{   
                    fos.close();   
                }   
                catch(IOException iex){
                	logger.error(this.getClass().getName()+iex.getMessage());
                }   
            }         
        }
    }

    /**
     * 传入 邮件配置信息，发送邮件
     * 
     * @param conn 传入数据库连接
     * @param map : 数据库链接信息，报表属性
     * @throws Exception
     */
    public void sendReportToEmail(HashMap<String, Object> map){

        String reportId = (String)map.get("reportId");
        List<HashMap<String, String>> list = getParameters(reportId);
        if(list == null || list.size() == 0){
            try {
                JasperPrint jasperPrint  = this.setJasperPrint(reportId, null,locale);   
                
                if(jasperPrint != null){
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    //PrintWriter out = response.getWriter();
                    ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            
                    exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, "GB2312");
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    //exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, oStream);      
                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "cid:"+Config.getInstance().getReportTemplatePath()+reportId+"?image=");
                    
                    exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,Boolean.TRUE);
                    exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME, Config.getInstance().getReportTemplatePath()+reportId+"/");  
                    //request.getSession().setAttribute(ImageServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,jasperPrint);
                    
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
                    exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); // 删除记录最下面的空行
                    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);// 删除多余的ColumnHeader
                    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);// 显示边框
                    exporter.exportReport();

                    // 写输出流
                    byte[] bytes = oStream.toByteArray();
                    if (bytes != null && bytes.length > 0) {
                        
                        String content = new String(bytes);
                        //处理报表生成的图片片段
                        Map<String,String> image=new HashMap<String,String>(); 
                        if(content.indexOf("img") != -1){
                            String[] istr = content.split("<img src=");
                            for(int i=0; i<istr.length; i++){
                                if(istr[i].indexOf("alt") != -1){
                                    String[] st = istr[i].split("alt");
                                        if(st.length > 0){
                                            String path = Config.getInstance().getReportTemplatePath();
                                            if(path.substring(0,1).equals("/")){
                                                path = path.substring(1,path.length());
                                            }
                                            
                                            String[] st2 = st[0].split("style");
                                            if(st2.length > 0){
                                            	logger.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + st2[0]);
//                                                result = result.replace(st2[0], "'cid:"+lists.get(j)+"' ");    
                                            	image.put(st2[0].split("cid:")[1].substring(0,st2[0].split("cid:")[1].length()-2), 
                                            			Config.getInstance().getReportTemplatePath()+reportId+"/"
                                            				+st2[0].split("image=")[1].substring(0,st2[0].split("image=")[1].length()-2));
                                            }
                                        }                        
                                }
                            }            
                        }
                        
                        content = replaceImgeTagEmail(content, reportId);

                        //内嵌了多少张图片，如果没有，则new一个不带值的Map  
                        List<String> listImg = getHtmlImg(reportId);
                        for(int m=0; m<listImg.size(); m++){
                            image.put(listImg.get(m), Config.getInstance().getReportTemplatePath()+reportId+"/"+listImg.get(m));                             
                        } 
                        
                        //发送附件
                        Map<String,String> attachment=new HashMap<String,String>();
                        if("Y".equalsIgnoreCase(String.valueOf(map.get("is_attachment")))){
                        	String fileType = String.valueOf(map.get("attachment_type"));
                        	if(fileType==null||fileType.length()<1){ fileType="pdf"; }
                        	generateReportFile(reportId, fileType,null);//生成报表附件文件
                            String fileName = mainMap.get(reportId).get("reportName") + "_" + BizDataUtil.formatHHmmss.format(new Date()) + "." + fileType;
                            String filePath = Config.getInstance().getReportTemplatePath() + File.separator + reportId + File.separator + fileName;
                        	attachment.put(fileName, filePath);
                        }
                        EmailUtil emailUtil = new EmailUtil();
                        emailUtil.setSubject((String)map.get("reportName")); 
                        emailUtil.autoSend(content, map, image,attachment);
                        logger.debug("邮件发送完毕");
                        oStream.close();
                    } else {
                    }                
                }
                
            } catch (Exception ex) {
                ex.printStackTrace();
            }      
        }
    }      
    
    /**
     * 判断是否有报表记录，针对无参数报表
     * 
     * @param reportId
     */
    public Boolean isReportRecord(String reportId){
        Boolean flg = false;
        File hdir = new File(Config.getInstance().getReportHtmlPath()+reportId);
        if(hdir.exists() && hdir.isDirectory()){//判断目录的存在性      
        	logger.debug("目录存在！");     
            if (hdir.listFiles().length > 0){
                flg = true;
            }
        }        
        return flg;        
    }
    
    /**
     * 获取定期生成的报表记录
     * 
     * @param reportId
     */
    public List<String> getReportRecord(String reportId){
        List<String> list = new ArrayList<String>();
        File hdir = new File(Config.getInstance().getReportHtmlPath()+reportId);
        if(hdir.exists() && hdir.isDirectory()){//判断目录的存在性       
            File[] t = hdir.listFiles();
            for(int i=0; i<t.length; i++){
                list.add(t[i].getName());//存取文件名
            }
        }  
        return list;
    }
    
    /**
     * 读取html文件显示
     * 
     * @param reportId, htmlName
     */
    public String showRecordHtml(String reportId, String htmlName){
        
        File hdir = new File(Config.getInstance().getReportHtmlPath()+reportId+"/"+htmlName);
        StringBuffer sb = new StringBuffer();
 
        try {  
            FileInputStream inStream = new FileInputStream(hdir);  
            byte[] b = new byte[1024];  
            int i = inStream.read(b);  
            while (i > 0) {  
                sb.append(new String(b));
                i = inStream.read(b);  
            }  
            inStream.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        String result = sb.toString();
        if(result.indexOf("a {text-decoration: none}") != -1){
            result = result.replace("a {text-decoration: none}", "");
        }
        result = replaceImgeTag(result, reportId);
        return result;
    }
    
    /**
     * 更改image的标签为html标签
     * 
     * @param reportId, html
     */
    public String replaceImgeTag(String result, String reportId){
        if(result.indexOf("img") != -1){
            String[] istr = result.split("<img src=");
            List<String> lists = getHtmlImg(reportId);
            int j = lists.size()-1;
            for(int i=0; i<istr.length; i++){
                if(istr[i].indexOf("alt") != -1){
                    String[] st = istr[i].split("alt");
                    if(j >= 0){
                        if(st.length > 0 && lists != null){
                            String[] st2 = st[0].split("style");
                            if(st2.length > 0){
                                
                                result = result.replace(st2[0], "'"+Config.getInstance().getReportTemplatePath()+reportId+"/"+lists.get(j)+"' ");     
                                j--;                                
                            }
                        }                        
                    }
                }
            }            
        }
        return result;
    }
    
    /**
     * 更改image的标签为html标签for email
     * 
     * @param reportId, html
     */
    public String replaceImgeTagEmail(String result, String reportId){
        if(result.indexOf("img") != -1){
            String[] istr = result.split("<img src=");
            List<String> lists = getHtmlImg(reportId);
            int j = lists.size()-1;
            for(int i=0; i<istr.length; i++){
                if(istr[i].indexOf("alt") != -1){
                    String[] st = istr[i].split("alt");
                    if(j >= 0){
                        if(st.length > 0 && lists != null){
                            String path = Config.getInstance().getReportTemplatePath();
                            if(path.substring(0,1).equals("/")){
                                path = path.substring(1,path.length());
                            }
                            
                            String[] st2 = st[0].split("style");
                            if(st2.length > 0){

                                result = result.replace(st2[0], "'cid:"+lists.get(j)+"' ");     
                                j--;                            
                            }
                        }                        
                    }
                }
            }            
        }
        return result;
    }
    
    /**
     * 获取jasper中的img文件
     * 
     * @param reportId, htmlName
     */
    @SuppressWarnings("rawtypes")
	public List<String> getHtmlImg(String reportId){
        
        List<String> resultList = new LinkedList<String>(); 
        String tmpXml = "";
        
        try {
            
            HashMap<String, Object> map = mainMap.get(reportId);
            File file=new File((String)map.get("template"));
            File[] files = file.listFiles();
            if (files == null) 
                return null; 
            for (int i = 0; i < files.length; i++) {
                if(files[i].getName().indexOf("jrxml") != -1 && files[i].getName().indexOf("sub.jrxml") == -1){
                    tmpXml = files[i].getName();
                    break;
                }
            }    
            
            if(!tmpXml.equals("") && tmpXml.indexOf("jrxml") != -1){

                File fxml = new File(Config.getInstance().getReportTemplatePath() + reportId +"/"+ tmpXml); 
                SAXReader readxml = new SAXReader(); 
                org.dom4j.Document docxml = readxml.read(fxml); 
                Element rootxml = docxml.getRootElement(); 
                List title = rootxml.elements("title");
                for (Iterator t = title.iterator(); t.hasNext();) {
                    Element titleE = (Element) t.next();
                    List band = titleE.elements("band");
                    for (Iterator e = band.iterator(); e.hasNext();) {
                        Element bandE = (Element) e.next();
                        List image = bandE.elements("image");                    
                        Element fooxml; 
                        for (Iterator j = image.iterator(); j.hasNext();) {
                            fooxml = (Element) j.next();                        
                            String defaultValue = fooxml.elementText("imageExpression");
                            defaultValue = defaultValue.replace("<![CDATA[", "");
                            defaultValue = defaultValue.replace("]]>", "");
                            if(defaultValue.length() > 2){
                                defaultValue = defaultValue.substring(1, defaultValue.length()-1);
                            }
                            resultList.add(defaultValue);    
                        }                       
                    }                
                } 
                
//                List background = rootxml.elements("background");
//                for (Iterator t = background.iterator(); t.hasNext();) {
//                    Element backgroundE = (Element) t.next();
//                    List band = backgroundE.elements("band");
//                    for (Iterator e = band.iterator(); e.hasNext();) {
//                        Element bandE = (Element) e.next();
//                        List image = bandE.elements("image");                    
//                        Element fooxml; 
//                        for (Iterator j = image.iterator(); j.hasNext();) {
//                            fooxml = (Element) j.next();                        
//                            String defaultValue = fooxml.elementText("imageExpression");
//                            defaultValue = defaultValue.replace("<![CDATA[", "");
//                            defaultValue = defaultValue.replace("]]>", "");
//                            if(defaultValue.length() > 2){
//                                defaultValue = defaultValue.substring(1, defaultValue.length()-1);
//                            }
//                            resultList.add(defaultValue);    
//                        }                       
//                    }                
//                }
            }            
        } catch (Exception e) {
            e.printStackTrace();
        }   
        return resultList;
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

    public String getWebinfPath() {

        String pt = this.getClass().getClassLoader().getResource("/").getPath(); 
        if(pt.indexOf("WEB-INF") > -1){
            pt = pt.substring(0, pt.indexOf("WEB-INF") );
        }
        return pt;
    }
    public String getWebinfPath2() {

        String pt = this.getClass().getClassLoader().getResource("/").getPath(); 
        if(pt.indexOf("WEB-INF") > -1){
            pt = pt.substring(0, pt.indexOf("WEB-INF") );
        }
        return pt;
    }

    public List<HashMap<String, Object>> getReportMgtBean() {
        return reportMgtBean;
    }

    public void setReportMgtBean(List<HashMap<String, Object>> reportMgtBean) {
        this.reportMgtBean = reportMgtBean;
    }

    public List<HashMap<String, Object>> getDataSourceBean() {
        return dataSourceBean;
    }

    public void setDataSourceBean(List<HashMap<String, Object>> dataSourceBean) {
        this.dataSourceBean = dataSourceBean;
    }

    public List<HashMap<String, Object>> getHtmlBean() {
        return htmlBean;
    }

    public void setHtmlBean(List<HashMap<String, Object>> htmlBean) {
        this.htmlBean = htmlBean;
    }

    public List<HashMap<String, Object>> getEmailBean() {
        return emailBean;
    }

    public void setEmailBean(List<HashMap<String, Object>> emailBean) {
        this.emailBean = emailBean;
    }

    public static HashMap<String, HashMap<String, Object>> getMainMap() {
        return mainMap;
    }

    public static void setMainMap(HashMap<String, HashMap<String, Object>> mainMap) {
        ReportMain.mainMap = mainMap;
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
