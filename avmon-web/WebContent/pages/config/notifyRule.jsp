<!DOCTYPE html>

<!-- Auto Generated with Sencha Architect -->
<!-- Modifications to this file will be overwritten. -->
<html>
<head>

    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>AlarmAutoCloseRule</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all.gzjs"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css"/>
<script type="text/javascript">
function selectTreeNode(selModel){
	//alert(selModel.selected.items[0].internalId);
	
	editForm.getForm().load({ 
		clientValidation: false, 
		//waitMsg:'Loading...', 
		url: 'loadViewById?viewId=' + selModel.selected.items[0].internalId
	}); 
	
}
</script>
    <script type="text/javascript" src="notifyRuleApp.js"></script>
</head>
<body></body>
</html> 