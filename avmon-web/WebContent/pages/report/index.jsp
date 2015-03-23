<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html>

<!-- Auto Generated with Sencha Architect -->
<!-- Modifications to this file will be overwritten. -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>main</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all.gzjs"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/Main.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/global.css"/>
<!--     <link rel="stylesheet" type="text/css" href="style.css"/> -->
    <script type="text/javascript">
    var nodeIds = []; 
    </script>
    <%
		String userName = request.getSession().getAttribute("userName").toString();
    %>
	<script>	
	function afterLoad(){
		Ext.alarm={};
		Ext.alarm.userName = '<%=userName%>';
	}
    </script>
    <script type="text/javascript" src="app.js"></script>
    
<script src="${pageContext.request.contextPath}/pages/avmon_back_gif.js"></script>
</head>
<body onLoad="afterLoad();"></body>
</html> 