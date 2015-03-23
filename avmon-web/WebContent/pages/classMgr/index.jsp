<!DOCTYPE html>
<HTML>
<HEAD>
<TITLE>MetaClass Management - by zTree & jqGrid</TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<!-- In head section we should include the style sheet for the grid -->
<link rel="stylesheet" type="textcss" media="screen"
	href="css/redmond/jquery-ui-1.10.3.custom.min.css" />
<link rel="stylesheet" type="textcss" media="screen"
	href="css/ui.jqgrid.css" />
<link rel="stylesheet" type="textcss" media="screen"
	href="css/style.css" />
<link rel="stylesheet" type="textcss" media="screen"
	href="css/ztree/demo.css" />

<!-- Of course we should load the jquery library -->
<script src="js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>

<script src="js/jquery/layout/jquery.layout-1.3.0.min.js" type="text/javascript"></script>
<link rel="stylesheet" type="textcss" media="screen"
	href="js/jquery/layout/layout-default-1.3.0.css" />


<!-- Of course we should load the jquery ui library -->
<script src="js/jquery/jquery-ui-1.10.3.custom.min.js"
	type="text/javascript"></script>

<script type="text/javascript"
	src="js/jquery/ztree/jquery.ztree.core-3.5.min.js"></script>
<script type="text/javascript"
	src="js/jquery/ztree/jquery.ztree.exedit-3.5.min.js"></script>
<link rel="stylesheet" href="css/ztree/zTreeStyle.css"
	type="textcss">

<script type="text/javascript" src="metaclass/04-metaclass.js"></script>
<link rel="stylesheet" href="metaclass/metaclass-icon.css" type="textcss">

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-en.js"
	type="text/javascript"></script>
<script src="js/jquery/jqGrid-4.5.2/jquery.jqGrid.min.js"
	type="text/javascript"></script>
<script src="metaclass/04-metaclass-attr.js" type="text/javascript"></script>

<script type="text/javascript">
	$(document).ready(function() {
		$('body').layout({
			applyDefaultStyles: false,//应用默认样式  
			spacing_open:2,//边框的间隙  
			west : {
				size : 250,
				spacing_closed : 10,
				togglerLength_closed : 140,
				togglerAlign_closed : "center", //设<br><br>置"
				togglerTip_closed : "Open & Pin Contents",
				sliderTip : "Slide Open Contents",
				slideTrigger_open : "mouseover"
			},
			east : {
				size : 800,
				spacing_closed : 10,
				togglerLength_closed : 140,
				togglerAlign_closed : "center", //"设<br><br>置"
				togglerTip_closed : "Open & Pin Contents",
				sliderTip : "Slide Open Contents",
				slideTrigger_open : "mouseover"
			}
		});
	});
</script>
</HEAD>


<body>
	<div class="ui-layout-west">
		<div class="flip">
		</div>
		<ul id="treeDemo" class="ztree"></ul>
	</div>
	<div class="ui-layout-center">
		<div class="flip">
			<a id="addLeaf" href="#" title="add leaf node"
				onclick="return false;"> <img
				src="images/edit/comment.add.16.png" alt="add leaf node" /></a> <a
				id="edit" href="#" title="edit name" onclick="return false;"><img
				src="images/edit/comment.edit.16.png" alt="edit name" /></a> <a
				id="remove" href="#" title="remove node" onclick="return false;"><img
				src="images/edit/comment.delete.16.png" alt="remove node" /></a>
		</div>
		<ul id="treeDemo1" class="ztree"></ul>
	</div>
	<div class="ui-layout-east" style="width: 620px">
		<table id="metaclass-attr"></table>
		<div id="page-metaclass-attr"></div>
		<br /> <a href="javascript:void(0)" id="cm1">Get selected id</a>
	</div>
</body></HTML>