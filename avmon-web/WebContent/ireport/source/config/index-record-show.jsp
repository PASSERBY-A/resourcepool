<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hp.avmon.ireport.report.ReportMain"%>
<%@page import="java.util.List"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.IOException"%>
<%@page import="net.sf.jasperreports.engine.JasperPrint"%>

	
<%
    String reportId = new String(request.getParameter("reportId").getBytes("UTF-8"));  
    String htmlName = new String(request.getParameter("htmlName").getBytes("UTF-8"));  
    request.setAttribute("reportId",reportId);    
    
    //读取html显示
    ReportMain main = new ReportMain();
    String file = main.showRecordHtml(reportId, htmlName);
    request.setAttribute("file",file);   
    
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
    <title></title>
    <script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script type="text/javascript">    


        var reportId = '<%=request.getAttribute("reportId")%>';
    
	    $().ready(function() {

    		$('#buttonBar').show();	
		});   
	    
	    function exportToPdf(){
			if(reportId != '' && reportId != null){
	    		//导出
		       	var url  = "report-html.jsp?reportId="+reportId+"&type=pdf&params=";
		       	window.location=url;  
			}	    	
	    }
		
	    function exportToWord(){
			if(reportId != '' && reportId != null){
	    		//导出
		       	var url  = "report-html.jsp?reportId="+reportId+"&type=word&params=";
		       	window.location=url;  
			}	    	
	    }
		
	    function exportToexcel(){
			if(reportId != '' && reportId != null){
	    		//导出
		       	var url = "report-html.jsp?reportId="+reportId+"&type=excel&params=";
		       	window.location=url;  	 
			}	    	
	    }
    </script>
    
    <style type="text/css">
        body{ font-size:12px;}
        .ireport_btn_export{
			background:url(../image/export.png) no-repeat center left !important;
		}
        .ireport_btn_export_pdf{
			background:url(../image/exportPdf.png) no-repeat center left !important;
		}
		.ireport_List_control{
			height:27px;
			border:1px #ccc solid;
			background:url(../image/List_button_area_background.png) repeat-x top left;
			position:relative;
			width:99.8%;
		}
		.parameterTbl{
			margin-top:5px;
			margin-bottom:5px;
		}
		
		.parameterTbl .input_name{
			padding-left:14px;
			font-size:12px;
			font-weight:bold;
			text-align: right;
		}
		
    </style>
</head>
<body style="padding:6px; overflow:hidden;">
    <form name="form1" method="post"  id="form1">
			
		<div id="index_parameter" >
		    <table class="parameterTbl" id="parameterTable"> 
			</table>
		</div>
		
		<div id="buttonBar" >    
			<input type="button" name="button" id="exportPdfId" onclick="exportToPdf();" value="Export PDF"/>	
			<input type="button" name="button" id="exportWordId" onclick="exportToWord();" value="Export Word">	
			<input type="button" name="button" id="exportExcelId" onclick="exportToexcel();" value="Export Excel">	
		</div>		
		
		<div id="ireportHtmlDiv" align="center" style="width:100%;height:700px; overflow-y:scroll; ">
		    <%=request.getAttribute("file")%>
		</div>

    </form>
</body>
</html>