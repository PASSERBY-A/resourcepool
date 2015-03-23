<%@ page language="java" pageEncoding="UTF-8"%>

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
		
		.hand-msg {cursor:pointer}
	</style>
	
	<%
		String mo = (String) request.getParameter("mo");
		String os = (String) request.getParameter("os");
    %>
	<script>
	function afterLoad(){
		Ext.alarm={};
		Ext.alarm.moId='<%=mo%>';
		Ext.alarm.searchTreeId = '';
		Ext.alarm.os='<%=os%>';
		
		
		setTimeout(function(){
			Ext.get('loading').remove();
		},1000);

	}
	
	function checkChild(node, checked){
		node.expand();  
	    node.checked = checked;  
	    node.eachChild(function (child) {  
	        child.set('checked', checked);  
	        checkChild(child,checked);  
	    });  
	}

    var normalAMPNullSource = null;

    </script>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
</head>
<body onLoad="afterLoad();">
	<div id="loading">
		<div class="loading-indicator"><img
			src="../../../resources/images/loading32.gif" width="31" height="31"
			style="margin-right: 8px; float: left; vertical-align: top;" />
			<spring:message code="loading"/><br />
			<span id="loading-msg"><spring:message code="loadingFunctionalComponents"/></span></div>
		</div>
	
	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
	    <link rel="stylesheet" type="text/css" href="../../../resources/style.css"/>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all.gzjs"></script>
	    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>
	    <script type="text/javascript" src="app.js"></script>
	</div>
</body>
</html>