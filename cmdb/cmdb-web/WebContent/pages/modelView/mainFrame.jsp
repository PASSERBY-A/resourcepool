<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String viewName="";
String viewType="";
String htmlType="default.html";
if((String)request.getParameter("viewTypeName") != null && (String)request.getParameter("viewTypeName") != ""){
	viewName=(String)request.getParameter("viewTypeName");
}
viewType=(String)request.getParameter("typeName");
if (viewType != null && viewType.equals("0") )
	htmlType = "showTopo.html";
if (viewType != null && viewType.equals("1") )
	htmlType = "show3d.html";

System.out.println(viewName+"__"+viewType+"__"+htmlType);
%>

<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<!-- In head section we should include the style sheet for the grid -->
<link rel="stylesheet" type="text/css" media="screen"
	href="css/redmond/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/style.css" />
<link rel="stylesheet" type="text/css" media="screen"
	href="css/ztree/demo.css" />

<!-- Of course we should load the jquery library -->
<script src="js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>

<script src="js/jquery/layout/jquery.layout-1.3.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="js/jquery/layout/layout-default-1.3.0.css" />

<!-- Of course we should load the flex library -->
<link rel="stylesheet" href="style.css">
<script language="javascript" src="Charts/FusionCharts.js"></script>

<!-- Of course we should load the jquery ui library -->
<script src="js/jquery/jquery-ui-1.10.3.custom.min.js"
	type="text/javascript"></script>

<script type="text/javascript"
	src="js/jquery/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="js/jquery/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<link rel="stylesheet" href="css/ztree/zTreeStyle.css"
	type="text/css">

<link rel="stylesheet" href="metaclass/metaclass-icon.css" type="text/css">

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script src="js/jquery/jqGrid-4.5.2/jquery.jqGrid.min.js"
	type="text/javascript"></script>
<script src="metaclass/04-metaclass-attr.js" type="text/javascript"></script>

	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>


<style type="text/css">
.ui-tabs .ui-tabs-panel {
	background-color: rgb(252, 253, 253);
	height:100%;
}


</style>
<script type="text/javascript">

$(function() {
    $( "#tabs" ).tabs();
});

</script>
<script type="text/javascript">
	$(document).ready(function() {
		$('#physicalTopologyFrame')[0].height = $('body')[0].parentNode.offsetHeight;
	});
</script>
</HEAD>
<body>
	<div id="tabs-1" >  
		<iframe id="physicalTopologyFrame" src="../flex/<%=htmlType%>?viewName=<%=viewName%>" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
	</div>
</body>
</HTML>