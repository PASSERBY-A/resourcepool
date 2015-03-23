<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hp.avmon.ireport.report.ReportMain"%>
	
<%
    String reportId = new String(request.getParameter("reportId").getBytes("UTF-8")); 
    String type = new String(request.getParameter("type").getBytes("UTF-8")); 
    ReportMain main = new ReportMain();

    if(type.equals("html")){
        String params = request.getParameter("params"); 
        if(params.equals("")){
            params = main.getUrlParameters(request,reportId);
        }
        main.selectHtmlReport(request, response, reportId, params);         
        
    }else if(type.equals("pdf")){

        String params = new String(request.getParameter("params").getBytes("UTF-8")); 
        if(params.equals("")){
            params = main.getUrlParameters(request,reportId);
        }
        main.selectPdfReport(response, reportId, params); 
        
    }else if(type.equals("excel")){

        String params = new String(request.getParameter("params").getBytes("UTF-8")); 
        if(params.equals("")){
            params = main.getUrlParameters(request,reportId);
        }
        main.selectExcelReport(response, reportId, params); 
        
    }else if(type.equals("word")){

        String params = new String(request.getParameter("params").getBytes("UTF-8")); 
        if(params.equals("")){
            params = main.getUrlParameters(request,reportId);
        }
        main.selectWordReport(response, reportId, params); 
        
    }
    
%>