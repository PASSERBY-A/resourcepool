<!DOCTYPE html>
<%@ page language="java" pageEncoding="UTF-8"%>
<HTML>
<HEAD>
<TITLE>模型管理</TITLE>
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

		$("#openXXXIframe").hide();
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
			<a id="addLeaf" href="#" title="添加类模型"
				onclick="return false;"> 
				<img src="images/edit/comment.add.16.png" alt="add leaf node" />
			</a> 
			<a
				id="edit" href="#" title="编辑类模型" onclick="return false;"><img
				src="images/edit/comment.edit.16.png" alt="edit name" />
			</a> 
			<a
				id="remove" href="#" title="移除模型" onclick="return false;"><img
				src="images/edit/comment.delete.16.png" alt="remove node" />
			
            </a>
			<a
				id="property" href="#" title="基础属性查看" onclick="return false;">
				<img
				src="images/edit/edit.16.gif" alt="basic property" />
			</a>
		</div>
		<ul id="treeDemo1" class="ztree" style="height: 93%;"></ul>
		<table id="property" width = "600"></table>
		<div id="dialog-form" title="CI类型编辑">
	<p class="validateTips">All form fields are required.</p>

	<form>
	<fieldset>
		<label id="nameLabel" for="name">配置项名称</label>
		<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" /></br>
		<label for="label">显示名称</label>
		<input type="text" name="label" id="label" class="text ui-widget-content ui-corner-all" /></br>
		<label for="domain">数据域</label>
		<input type="text" name="domain" id="domain" class="text ui-widget-content ui-corner-all" /></br>
		<label id="editLabel" for="name" >是否编辑</label>
		<input type="text" name="isEdit" id="isEdit" class="text ui-widget-content ui-corner-all" /></br>
		<label for="iconSkin">图标名称</label>
		<select name="iconSkin" id="iconSkin">
			<option id="icon_hardware">hardware</option>
			<option id="icon_network">network</option>
			<option id="icon_server">server</option>
			<option id="icon_storage">storage</option>
			<option id="icon_printer">printer</option>
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
	<div class="ui-layout-center" style="width: 620px">
<!--				<table id="metaclass-attr" width = "600"></table>
			<div id="page-metaclass-attr" width = "600"></div>  --> 
	<iframe id = "ciListFrame" src="../classMgr/attribute.jsp"  width="100%" height="100%" frameborder="0" scrolling="no"></iframe>	 	
	</div>
	<div id="openRoleDiv" class="easyui-window" hidden="true" closed="true" modal="true" title="标题" style="width:700px;height:500px;">
     <iframe scrolling="auto" id='openXXXIframe' frameborder="0"  src="../classMgr/ciType.jsp?" style="width:100%;height:100%;"></iframe>
</div>	
</body></HTML>