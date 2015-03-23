<!DOCTYPE html>

<!-- Auto Generated with Sencha Architect -->
<!-- Modifications to this file will be overwritten. -->
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>KpiHistory</title>
<!--     <link rel="stylesheet" type="text/css" href="../resources/style.css"/> -->
<%--     <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/> --%>
<%--     <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all.gzjs"></script> --%>
    
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all.gzjs"></script>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/style.css"/>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>
    
    <script type="text/javascript">
		function reloadModule(moId){
			//var gridStore = Ext.getCmp("KpiGrid_grid").store;
			//var proxy = gridStore.proxy;
			//var extraParams = proxy.extraParams;
			Ext.getCmp("KpiGrid_grid").store.proxy.url = 'kpiList?moId=' + moId;
			
			Ext.getDom("moId").value = moId;
			
			Ext.getCmp("KpiGrid_grid").store.load();
			
			//alert(Ext.getCmp("kpiHistoryHostIp"));
			/**
			var storeFields = ["time"];
			var chart = Ext.getCmp("KpiHistoryChart_chart");
			var chartStorex = Ext.create('Ext.data.JsonStore', {
		        fields: storeFields,
		        data: []
		    }); 
			
			try{
				var lines = chart.series.items;
				Ext.Array.each(lines, function(line) { line.hideAll(); })
			}catch(error){}
			
	    	chart.store = chartStorex;
	    	chart.redraw();
	    	*/
			//Ext.getCmp("kpiHistoryHostIp").setText(moId);
		}
	</script>
	<script type="text/javascript" src="dynamicKpiHistoryApp.js"></script>
</head>
<body>
<%-- <input name="moId" id="moId" type="hidden" value="<%=request.getParameter("moId")%>"> --%>
<%
System.out.println("moId="+request.getAttribute("moId"));
%>
<input name="moId" id="moId" type="hidden" value="${moId}">
</body>
</html> 