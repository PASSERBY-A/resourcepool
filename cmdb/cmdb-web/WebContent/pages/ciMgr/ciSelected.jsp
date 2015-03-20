<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String isSource=(String)request.getParameter("isSource");
%>
<HTML>
<HEAD>
<TITLE>类管理</TITLE>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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


<!-- Of course we should load the jquery ui library -->
<script src="js/jquery/jquery-ui-1.10.3.custom.min.js"
	type="text/javascript"></script>
	<!--<script src="js/jquery/ui/jquery.ui.dialog.js"></script>-->
	<!--<script src="js/jquery/ui/jquery.ui.button.js"></script>-->

<script type="text/javascript"
	src="js/jquery/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="js/jquery/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<link rel="stylesheet" href="css/ztree/zTreeStyle.css"
	type="text/css">

<script type="text/javascript" src="metaclass/ci-04-metaclass.js"></script>
<link rel="stylesheet" href="metaclass/metaclass-icon.css" type="text/css">

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-en.js"
	type="text/javascript"></script>
<script src="js/jquery/jqGrid-4.5.2/jquery.jqGrid.min.js"
	type="text/javascript"></script>

<script src="metaclass/dialog.js" type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('body').layout({
			applyDefaultStyles: false,//应用默认样式  
			spacing_open:2,//边框的间隙  
			west : {
				size : 200,
				spacing_closed : 10,
				togglerLength_closed : 140,
				togglerAlign_closed : "center", //设<br><br>置"
				togglerTip_closed : "Open & Pin Contents",
				sliderTip : "Slide Open Contents",
				slideTrigger_open : "mouseover"
			},
			east : {
				size : 900,
				spacing_closed : 10,
				togglerLength_closed : 140,
				togglerAlign_closed : "center", //"设<br><br>置"
				togglerTip_closed : "Open & Pin Contents",
				sliderTip : "Slide Open Contents",
				slideTrigger_open : "mouseover"
			}
		});
	});
	function selectSourceNode(ciId,ciName,ciType)
	{
		//alert(ciId+ciName+ciType);
		//var sourceType = $("#sourceType",window.opener.document);
		//window.top.sayHello();
		//window.opener.getSourceNode(ciId,ciName,ciType);
		$("#sourceNodeId",window.parent.document).attr("value",ciId);
		$("#sourceNode",window.parent.document).attr("value",ciName);
		$("#sourceType",window.parent.document).attr("value",ciType);
		//window.Owner.abc("abc");
		
		};
		function selectTargetNode(ciId,ciName,ciType)
		{
			//alert(ciId+ciName+ciType);
			//var sourceType = $("#targetType",window.opener.document);
			$("#targetNodeId",window.parent.document).attr("value",ciId);
			$("#targetNode",window.parent.document).attr("value",ciName);
			$("#targetType",window.parent.document).attr("value",ciType);
			//window.Owner.abc("abc");
			
			};
	function getNodeType(){
		//alert("test");
		return "<%=isSource%>";
	}
</script>
  <style>
  .ui-menu { width: 150px; }
  .ui-widget {font-size:11px}
  
  
  body{
margin:0;
padding:0;
}
#left{
top:40px;
left:40px;
width:150px;
height:200px;

float:left;
margin:0;
}

#right{
width:850px;
height:500px;

float:left;
margin:0;
}

  
  </style>
</HEAD>
<body>
<div id="content">
  <div id="left">
    <ul id="treeDemo1" class="ztree"></ul>
  </div>
  <div id="right">
	 <iframe id = "ciListFrame" src="ciList.jsp?isSource=<%=isSource%>"  width="100%" height="500px" frameborder="0" scrolling="no"></iframe>
  </div>
</div>
</body>
</body></HTML>