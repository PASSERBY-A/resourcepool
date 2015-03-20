<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>

<script type="text/javascript">
	$(document).ready(function() {
		$('body').layout({
			applyDefaultStyles: true,//应用默认样式  
			spacing_open:2//边框的间隙  
		});
		SetCwinHeight();
	});
	function SetCwinHeight(){
		
		var iframeid=document.getElementById("ciListFrame"); //iframe id
		/*
	  	if (document.getElementById){
	   		if (iframeid && !window.opera){
	    		if (iframeid.contentDocument && iframeid.contentDocument.body.offsetHeight){
	     			iframeid.height = iframeid.contentDocument.body.offsetHeight;
	    		}else if(iframeid.Document && iframeid.Document.body.scrollHeight){
	     			iframeid.height = iframeid.Document.body.scrollHeight;
	    		}
	   		}
		}
		*/
		iframeid.height = iframeid.clientHeight;
	}
</script>

</HEAD>

<body style="height: 100%;">
	<div class="ui-layout-west">
		<ul id="treeDemo" class="ztree" style="height: 97%;" ></ul>
	</div>
	<div class="ui-layout-center">
		<iframe id="ciListFrame" width="100%" height="100%" src="../modelView/mainFrame.jsp" frameborder="0" scrolling="no"></iframe>
	</div>
</body>
</HTML>