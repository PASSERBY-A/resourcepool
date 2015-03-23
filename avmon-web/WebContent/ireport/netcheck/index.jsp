<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.hp.avmon.ireport.report.ReportMain"%>
<%@page import="java.util.List"%>
<%@page import="org.json.simple.JSONArray"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

	<style type="text/css">
		#loading {
			position: absolute;
			left: 40%;
			top: 40%;
			padding: 2px;
			z-index: 20001;
			height: auto;
		}
		
		#loading a {
			color: #225588;
		}
		
		#loading .loading-indicator {
			background: white;
			color: #444;
			font: bold 13px tahoma, arial, helvetica;
			padding: 10px;
			margin: 0;
			height: auto;
		}
		
		#loading-msg {
			font: normal 10px arial, tahoma, sans-serif;
		}
	</style>
<%
    String reportId = new String(request.getParameter("reportId").getBytes("UTF-8"));    
    request.setAttribute("reportId",reportId);
    
    //获取参数    
    List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
    ReportMain main = new ReportMain(reportId);
    //System.out.println("main>>>>>>>>>>>>>>>>>>>>>>>>>>>" + main);
    Boolean flg = main.isUrlParameter(request, reportId);
    //System.out.println("flg>>>>>>>>>>>>>>>>>>>>>>>>>>>" + flg);
    
    if(!flg){
        list = main.getParameters(reportId);
    }
	//System.out.println(list);
    //查找是否有报表记录，针对无参数报表
    Boolean isRecord = main.isReportRecord(reportId);
    
	request.setAttribute("listJson",JSONArray.toJSONString(list));
	request.setAttribute("isRecord",isRecord);
	
	//设置当前时间
	SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");   
	SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
    request.setAttribute("nowDt",fmt.format(new Date()));
    request.setAttribute("nowTime",fmt2.format(new Date()));
%>	
    <%
		//String reportId = (String) request.getParameter("reportId");
    %>
	<script>
	function afterLoad(){
		Ext.netcheck={};
		Ext.netcheck.reportId='<%=reportId%>';
		/**
		setTimeout(function(){
			Ext.get('loading').remove();
		},1000);
*/
	}
    </script>
</head>
<body onLoad="afterLoad();">

<!-- 	<div id="loading"> -->
<!-- 		<div class="loading-indicator"><img -->
<!-- 			src="../../resources/images/loading32.gif" width="31" height="31" -->
<!-- 			style="margin-right: 8px; float: left; vertical-align: top;" /> -->
<!-- 			正在努力加载，请稍候...<br /> -->
<!-- 			<span id="loading-msg">加载功能组件</span></div> -->
<!-- 		</div> -->
	
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
	    <link rel="stylesheet" type="text/css" href="../../resources/style.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all.gzjs"></script>
	    <script src="../source/js/jquery-1.7.1.min.js" type="text/javascript"></script>
	    <script type="text/javascript" src="app.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>

</body>
</html>