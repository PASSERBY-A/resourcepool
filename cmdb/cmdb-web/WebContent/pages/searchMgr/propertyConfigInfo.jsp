<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String typeName=(String)request.getParameter("typeName");

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
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


<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>

<script src="${pageContext.request.contextPath}/pages/modelView/js/common/common.js" type="text/javascript"></script>

<script type="text/javascript" src="js/selector/common.js"></script>
<script type="text/javascript" src="js/selector/SelectorView.js"></script>
<script type="text/javascript" src="js/selector/TableView.js"></script>
<script type="text/javascript" src="js/selector/PagerView.js"></script>
<script type="text/javascript" src="js/selector/jquery.js"></script>
<script type="text/javascript" src="js/selector/jquery-ui-1.7.2.custom.min.js"></script>
<style type="text/css">
/************* PagerView **************/
.PagerView{
	font-size: 12px;
	font-family: tahoma, arial;
	color: #333;
	text-align: center;
	padding: 8px;
	line-height: 100%;
}
.PagerView span{
	color: #999;
	margin: 0 1px;
	padding: 3px 6px;
	border: 1px solid #ccc;
}
.PagerView span.on{
	color: #fff;
	font-weight: bold;
	border: 1px solid #666;
	background: #39f;
}
.PagerView a{
	color: #00f;
	text-decoration: none;
}
.PagerView a span{
	color: #33f;
	border: 1px solid #66c;
}

/************* TableView **************/
.TableView .datagrid_meta{
	line-height: 22px;
}
.TableView .datagrid_meta .title{
	font-size: 13px;
	font-weight: bold;
}
.TableView .datagrid_meta .filter input{
	margin: 0 4px;
	padding: 1px;
	border: 1px solid #ccc;
}
.TableView table{
	font-size: 12px;
	border-collapse: collapse;
}
.TableView div.datagrid_div{
	border: 1px solid #999;
}
.TableView table.datagrid{
	border-collapse: separate;
	width: 100%;
}
.TableView table.datagrid th{
	text-align: left;
	background: #ddd;
	padding: 1px;
}
.TableView table.datagrid td{
	padding: 2px;
}
.TableView table.datagrid tr.odd{
    background: #fff;
}
.TableView table.datagrid tr.even{
    background: #eee;
}
.TableView table.datagrid tr.marked{
    background: #ee9;
}
.TableView table.datagrid tr:hover,
.TableView table.datagrid tr.hover{
    background: #9cf;
}

/************* SelectorView **************/
.SelectorView .selector_table{
	width: 100%;
}
.SelectorView .selector_table .src,
.SelectorView .selector_table .dst{
	width: 50%;
}
.SelectorView .selector_table table.datagrid tr:hover,
.SelectorView .selector_table table.datagrid tr.hover{
	cursor: pointer;
}
</style>
<script type="text/javascript">

//加载下拉框信息
function getPropertySelect(){
	  $.ajax({
		  type: "POST",
		  url: "getTypeNameList",
		  data: {
		    typeName : ""
		  },
		  async:false,
		  dataType: "json",
		  success: function( data, textStatus, jqXHR ) {
			  //colModelData = data;
			  //动态创建select
			  $(data).each(function(){
				  $("#property_select").append("<option value='"+this.name+"'>"+this.label+"</option>");
			  });
			  
		  }
		  
		});
}

var sel = null;
function afterLoad(){
	
	getPropertySelect();
	
	sel = new SelectorView('sel_div');
	sel.src.header = {
	//	id			: 'Id',
			name		: '配置项',
			label		: '配置项名称'
	};
	sel.dst.header = {
	//	id			: 'Id',
			name		: '配置项',
			label		: '配置项名称'
	};
	sel.src.dataKey = 'name';
	sel.src.title = '可选';

	sel.dst.dataKey = 'name';
	sel.dst.title = '已选';
	sel.src.display.pager = true;
	sel.dst.display.pager = true;
	sel.src.display.filter = true;
	sel.dst.display.filter = true;
	sel.src.pager.size = 10;
	sel.dst.pager.size = 10;
//	sel.src.add({name: 'None', name_cn: '幽灵', text: 'None'});
//	sel.src.add({name: 'Tom', name_cn: '汤姆', text: 'Tomcat'});
//	sel.src.add({name: 'None11111', name_cn: '幽灵111111', text: 'None11111'});
//	sel.src.add({name: 'Tom1111', name_cn: '汤姆1111111', text: 'None11111'});
	sel.render();
}




/**
 * 根据选择的类型加载所有属性信息
 */
function loadPropertyInfo(){
	var selval = $("#property_select").val();
	//当没有选择时全部清空
    if(selval == 0){
    	sel.src.clear();
		sel.dst.clear();
		return;
	}
	
	$.ajax({
		  type: "POST",
		  url: "getPropertyInfoList",
		  data: {
		    typeName : selval
		  },
		  async:false,
		  dataType: "json",
		  success: function( data, textStatus, jqXHR ) {
			//  $("#sel_div").empty();
			  //动态创建select
			 // initSelectorView(data);
			  sel.src.clear();
			  sel.dst.clear();
			  sel.src.addRange(data.all);
			  sel.dst.addRange(data.configed);
		  }
		  
		});
}

/**
 * 提交属性到后台
 */
function submitProperty(){
	var selKeys = "";
	var seled = sel.dst.getDataSource();
	
	if($("#property_select").val() == 0){
		
		alert("请选择类型！");
		return;
	}
	
	if(seled == null || seled == ""){
		alert("已选属性为空无法提交！");
		return;
	}
	$(seled).each(function(){
		selKeys+=this.name+",";
	});
	$.ajax({
		  type: "POST",
		  url: "setPropertyInfo",
		  data: {
			typeAttrName : $("#property_select").val(),
		    selKeys:selKeys
		  },
		  async:false,
		  dataType: "json",
		  success: function( data, textStatus, jqXHR ) {
			  var issuccess = data.issuccess;
			  if(issuccess){
				  alert("更新成功！");
			  }else{
				  alert("更新失败！请联系管理员！");
			  }
		  }
		  
		});
}


</script>
</HEAD>


<body onLoad="afterLoad();">

	
<div id="sel_div" style="width: 100%;height: 100%">
</div>

<div style="width: 100%">
<table>
<tr>
<td>选择需要配置的属性:</td>
<td>
<select name="property_select" id="property_select" style="width: 150px;" onchange="loadPropertyInfo()" >	
	  <option value="0">请选择</option>
</select>
</td>

<td><button  class='ui-button ui-widget ui-state-default'  onclick="submitProperty()">提交属性</button></td>
<td>

</td>

</table>
</div>			
</body>
</HTML>