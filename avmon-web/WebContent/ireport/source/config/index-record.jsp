<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hp.avmon.ireport.report.ReportMain"%>
<%@page import="java.util.List"%>

	
<%
    String reportId = new String(request.getParameter("reportId").getBytes("UTF-8"));   
    request.setAttribute("reportId",reportId);
    
    //列举报表记录
    ReportMain main = new ReportMain();
    List<String> listHtml = main.getReportRecord(reportId);

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html >
<head>
    <title></title>
    <script src="../js/jquery-1.7.1.min.js" type="text/javascript"></script>
    <script type="text/javascript">    

	    function showReport(htmlName){
	    	var reportId = '<%=request.getAttribute("reportId")%>';
            
            var url='index-record-show.jsp?reportId='+reportId+'&htmlName='+htmlName;
	    	window.location=url;  
	    }  
	    
    </script>
    
    <style type="text/css">
        body{ font-size:12px;}
		
    </style>
</head>
<body style="padding:6px; overflow:hidden;">
    <form name="form1" method="post"  id="form1">				
		
		<div id="ireportDiv" align="center" style="width:100%;height:700px; overflow-y:scroll; text-align:left;">		
			<%
			    for(int i=0; i<listHtml.size(); i++){ 
					%>
				      <p>      
				          <a href="#" onclick="showReport('<%=listHtml.get(i)%>');"><%=listHtml.get(i)%></a>
				      </p>
				    <%			       
			    }
			%>
		</div>

    </form>
</body>
</html>