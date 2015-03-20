<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<%
String typeName=(String)request.getParameter("typeName");
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

<script type="text/javascript" src="metaclass/04-metaclass.js"></script>
<link rel="stylesheet" href="metaclass/metaclass-icon.css" type="text/css">

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-cn.js"
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

	
</script>
  <style>
  .ui-menu { width: 150px; }
  .ui-widget {font-size:11px}
  </style>
</HEAD>
<body>
	<div class="ui-layout-west">
		<div class="flip">
			 <a
				id="property" href="#" title="属性详细" onclick="return false;"><img
				src="images/edit/comment.edit.16.png" alt="property" /></a>
			 <a
				id="refreshbtn" href="#" title="刷新" onclick="return false;"><img
				src="images/edit/refresh.16.png" alt="刷新" /></a>
		</div>
		<ul id="treeDemo1" class="ztree" style="height: 93%;"></ul>
		<div id="dialog-form" title="新增CI类型">
	<p class="validateTips">All form fields are required.</p>

	<form>
	<fieldset>
		<label for="name">配置项名称</label>
		<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" /></br>
		<label for="label">显示名称</label>
		<input type="text" name="label" id="label" class="text ui-widget-content ui-corner-all" /></br>
		<label for="parentId">父类</label>
		<input type="text" name="parentId" id="parentId" class="text ui-widget-content ui-corner-all" /></br>
		<label for="derivedFrom">父 类名称</label>
		<input type="text" name="derivedFrom" id="derivedFrom" class="text ui-widget-content ui-corner-all" /></br>
		<label for="iconSkin">iconSkin</label>
		<select name="iconSkin" id="iconSkin">
			<option id="icon_hardware">icon_hardware</option>
			<option id="icon_network">icon_network</option>
			<option id="icon_server">icon_server</option>
			<option id="icon_storage">icon_storage</option>
			<option id="icon_printer">icon_printer</option>
			<option id="icon_computer">icon_computer</option>
			<option id="icon_aix">icon_aix</option>
			<option id="icon_linux">icon_linux</option>
			<option id="icon_hpux">icon_hpux</option>
			<option id="icon_redhat">icon_redhat</option>
			<option id="icon_freebsd">icon_freebsd</option>
			<option id="icon_solaris">icon_solaris</option>
			<option id="icon_windows">icon_windows</option>
			<option id="icon_macos">icon_macos</option>
			<option id="icon_database">icon_database</option>
			<option id="icon_db2">icon_db2</option>
			<option id="icon_mysql">icon_mysql</option>
			<option id="icon_middleware">icon_middleware</option>
			<option id="icon_jboss">icon_jboss</option>
			<option id="icon_tomcat">icon_tomcat</option>
			<option id="icon_vm">icon_vm</option>
			<option id="icon_app">icon_app</option>
			<option id="icon_unknown">icon_unknown</option>
		</select>
	</fieldset>
	</form>
</div>

<div id="basicProperty-form" title="模型基础属性">

	<form>
	<fieldset>
		<label for="properyId">配置项ID</label>
		<input type="text" name="properyId" id="properyId" class="text ui-widget-content ui-corner-all" /></br>
		<label for="propertyName">配置项名称</label>
		<input type="text" name="propertyName" id="propertyName" class="text ui-widget-content ui-corner-all" /></br>
		<label for="propertyLabel">显示名称</label>
		<input type="text" name="propertyLabel" id="propertyLabel" class="text ui-widget-content ui-corner-all" /></br>
		<label for="icon">图标名称</label>
		<input type="text" name="icon" id="icon" class="text ui-widget-content ui-corner-all" /></br>
				<label for="derivedFrom">父类名称</label>
		<input type="text" name="derivedFrom" id="derivedFrom" class="text ui-widget-content ui-corner-all" /></br>
				<label for="parentId">父类ID</label>
		<input type="text" name="parentId" id="parentId" class="text ui-widget-content ui-corner-all" /></br>
				<label for="path">类路径</label>
		<input type="text" name="path" id="path" class="text ui-widget-content ui-corner-all" /></br>
				<label for="updateTime">更新时间</label>
		<input type="text" name="updateTime" id="updateTime" class="text ui-widget-content ui-corner-all" /></br>
						<label for="createTime">创建时间</label>
		<input type="text" name="createTime" id="createTime" class="text ui-widget-content ui-corner-all" /></br>
				<label for="isType">是否类型</label>
		<input type="text" name="isType" id="isType" class="text ui-widget-content ui-corner-all" /></br>
						<label for="exchangedId">外部系统 关联ID</label>
		<input type="text" name="exchangedId" id="exchangedId" class="text ui-widget-content ui-corner-all" /></br>
						<label for="version">资源的版本</label>
		<input type="text" name="version" id="version" class="text ui-widget-content ui-corner-all" /></br>
						<label for="propertyDomain">数据域</label>
		<input type="text" name="propertyDomain" id="propertyDomain" class="text ui-widget-content ui-corner-all" /></br>
								<label for="accessUsers">可访问的用户列表</label>
		<input type="text" name="accessUsers" id="accessUsers" class="text ui-widget-content ui-corner-all" /></br>
								<label for="accessRoles">可访问的角色列表</label>
		<input type="text" name="accessRoles" id="accessRoles" class="text ui-widget-content ui-corner-all" /></br>
								<label for="updateMode">更新模式</label>
		<input type="text" name="updateMode" id="updateMode" class="text ui-widget-content ui-corner-all" /></br>
	</fieldset>
	</form>
</div>

	</div>
	<div class="ui-layout-center" style="width: 100%">
<!--				<table id="metaclass-attr" width = "600"></table>
			<div id="page-metaclass-attr" width = "600"></div>  --> 
	<iframe id = "ciListFrame" src="../ciMgr/attribute.jsp?typeName=<%=typeName%>"  width="100%" height="100%" frameborder="0" scrolling="no"></iframe>	 	
	</div>
</body></HTML>