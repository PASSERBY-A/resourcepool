<%@ page language="java" pageEncoding="UTF-8"%>

<!-- Auto Generated with Sencha Architect -->
<!-- Modifications to this file will be overwritten. -->
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
		String mo = (String) request.getParameter("mo");
    %>
    
	<script>
		function afterLoad(){
			Ext.alarmSearch={};
			Ext.alarmSearch.moId='<%=mo%>';
			Ext.alarmSearch.isLevel0_b=1;
			Ext.alarmSearch.isLevel1_b=1;
			Ext.alarmSearch.isLevel2_b=1;
			Ext.alarmSearch.isLevel3_b=1;
			Ext.alarmSearch.isLevel4_b=1;
			Ext.alarmSearch.isHist_alarm_b=0;
			Ext.alarmSearch.isNewalarm_b=0;
			Ext.alarmSearch.isMyalarm_b=0;
			
			Ext.alarmSearch.levelStr='';
		    
			setTimeout(function(){
				Ext.get('loading').remove();
			},1000);
		}
		
		// 从其他页面加载时
		function reloadModule(moId){
			Ext.alarmSearch.moId = moId;

		    Ext.MessageBox.show({
		        msg: avmon.common.loadingWait,
		        progressText: 'Loading...',
		        width:300,
		        wait:true,
		        waitConfig: {interval:200},
		        icon:'icon-download'
		    });

		    setTimeout(function(){
		    	Ext.alarmSearch.application.loadAlarm();
		    },10); 
		    
			setTimeout(function(){
				Ext.MessageBox.hide();
			},1000);
		}
    </script>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
</head>

<style>
	html,body{
	      -webkit-text-size-adjust:none;
	}
</style> 

<body onLoad="afterLoad();">
	<div id="loading">
		<div class="loading-indicator">
			<img src="../../../resources/images/loading32.gif" width="31" height="31"
				style="margin-right: 8px; float: left; vertical-align: top;" />
				<spring:message code="loading"/><br />
			<span id="loading-msg"><spring:message code="loadingFunctionalComponents"/></span></div>
		</div>

	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
	    <link rel="stylesheet" type="text/css" href="../../../resources/style.css"/>
	    <link rel="stylesheet" type="text/css" href="style.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all.gzjs"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>
	    <script type="text/javascript" src="app.js"></script>
	</div>
</body>

</html>