<!DOCTYPE html>

<!-- Auto Generated with Sencha Architect -->
<!-- Modifications to this file will be overwritten. -->
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>AlarmAutoCloseRule</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css"/>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all.gzjs"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>
	<script type="text/javascript">
	function downLoadFile(filePath){
		document.forms[0].action="downLoadConfigFile"; 
		document.forms[0].filePath.value = filePath;
		document.forms[0].submit();
	}
	</script>
    <script type="text/javascript" src="routeDeviceConfigApp.js"></script>
</head>
<body>
<form name="form0" action="/euv/downLoadDoc.do" method="post" target="_self">
	<input type="hidden" name="filePath" id ="filePath" value=""/>
</form>
</body>
</html> 