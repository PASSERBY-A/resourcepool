<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String nodeId=(String)request.getParameter("nodeId");
String typeName=(String)request.getParameter("typeName");
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

<script type="text/javascript" src="metaclass/04-metaclass.js"></script>
<link rel="stylesheet" href="metaclass/metaclass-icon.css" type="text/css">

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script src="js/jquery/jqGrid-4.5.2/jquery.jqGrid.min.js"
	type="text/javascript"></script>
<script src="metaclass/04-metaclass-attr.js" type="text/javascript"></script>

	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>


<style type="text/css">
.ui-layout-center {
	background-color: #435b87;
	padding: 0px 8px 0px 0px;
}
.ui-tabs .ui-tabs-panel {
	background-color: rgb(252, 253, 253);
	height:100%;
}
</style>
<script type="text/javascript">

$(function() {
    $( "#tabs" ).tabs();
  });

	  function afterLoad(){
		}
</script>
</HEAD>


<body onLoad="afterLoad();">
<div id="tabs">
		  <ul>
			<li><a href="#tabs-1">配置属性</a></li>
			<li><a href="#tabs-2">维修记录</a></li>
			<li><a href="#tabs-3">逻辑关系</a></li>
			<li><a href="#tabs-4">配置变更记录</a></li>
		  </ul>
		  		  <div id="tabs-1">
		  		  <iframe id = "propertyFrame" src="../modelView/ciAttribute.jsp?typeName=<%=typeName%>&ciId=<%=nodeId%>"  width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
			
		  </div>
		  		  <div id="tabs-2">
			<iframe id = "maintainFrame" src="../modelView/hostRecord.jsp?typeName=<%=typeName%>&ciId=<%=nodeId%>" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
		  </div>
		  		  <div id="tabs-3">
            <!-- <iframe id = "propertyFrame" src="../modelView/flexgrid.jsp?nodeId=<%=nodeId%>"  width="700" height="400" frameborder="0" scrolling="no"></iframe> -->			
			<iframe id = "logicFrame" src="../modelView/hostLogic.jsp?typeName=<%=typeName%>&ciId=<%=nodeId%>" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
		  </div>
		  <div id="tabs-4">
			<iframe id = "historyFrame" src="../modelView/history.jsp?typeName=<%=typeName%>&ciId=<%=nodeId%>" width="100%" height="100%" frameborder="0" scrolling="auto"></iframe>
		  </div>
</div>

</body></HTML>