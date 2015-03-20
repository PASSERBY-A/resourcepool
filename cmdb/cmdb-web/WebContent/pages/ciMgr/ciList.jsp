<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String typeName=(String)request.getParameter("typeName");
String isSource=(String)request.getParameter("isSource");

if(typeName == null)
{
	typeName = "Ci";
	}
String getCiTypeAttr = "getCibyType?typeName="+typeName ;
String addCiTypeAttr = "addCibyType?typeName="+typeName ;
String updateCiTypeAttr = "updateCibyType?typeName="+typeName ;
String delCiTypeAttr = "delCibyType?typeName="+typeName ;
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

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-en.js"
	type="text/javascript"></script>
<script src="js/jquery/jqGrid-4.5.2/jquery.jqGrid.min.js"
	type="text/javascript"></script>
<!-- <script src="metaclass/04-metaclass-attr.js" type="text/javascript"></script> -->
<script src="metaclass/attrEdit.js" type="text/javascript"></script>

	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>


<style type="text/css">

</style>
<script type="text/javascript">
function saveAttr(){	
	$.ajax({
		  type: "POST",
		  url: "addCiTypeAttr",
		  data: {
		    //id: $("#id").val(),
		   // pid:treeNode.parentId,
		  //  label:$("#label").val(),
		   // name: $("#name").val(),
		    derivedFrom : "<%=typeName%>",
		    iconSkin: $("#iconSkin").val()
		  },
		  async:false,
		  dataType: "json",
		  success: function( data, textStatus, jqXHR ) {
			  var message = data.success;
			  var result = data.result;
			  alert(message);
			  if(result=='0'){
				  //afterLoad();
			  }else{
				  alert(message);
			  }
		  }
		  
		});
}


function initGridWidthHeightByPara(width,height){
	   $("#metaclass-attr").setGridWidth($(window).width()*width);　　
		$("#metaclass-attr").setGridWidth(document.body.clientWidth*width);　　
}



function changeWidthHeightByPara(width,height){
	  $(function(){　　 
			$(window).resize(function(){　　
				$("#metaclass-attr").setGridWidth($(window).width()*width);　　
				$("#metaclass-attr").setGridWidth(document.body.clientWidth*width);　　
			});　　

});
}


	  function afterLoad(){
			
		  changeWidthHeightByPara(0.99,0.4);
		  
			jQuery("#metaclass-attr").jqGrid({
			    datatype: "json",
			    url:"<%=getCiTypeAttr%>",
			    jsonReader: { repeatitems: false },
			    rowNum: 500,
			    height: 240,
			    width: 800,
			    scrollOffset: 0,
			    rowList: [10,20,30],
			    loadonce: true,
			    colNames:['id','name','label'],
			    colModel:[
						{name:'id', width:60, hidden:false, editable:true},
			           {name:'name', width:60, hidden:false, editable:true},
			           {name:'label', width:140, hidden:false, align:'left', editable:true}
//			           {name:'attrtype', width:100, align:'left',
//			        	   formatter:'select', editoptions:{value:'0:Dimen;1:Ext'},
//			               stype: 'select',
//			               searchoptions: { value: ':Any;0:Dimen;1:Ext' }
//			           },
//			           {name:'displaytype', width:100, align:'left',
//			        	   formatter:'select', editoptions:{value:'0:String;1:Number;2:Select'},
//			               stype: 'select',
//			               searchoptions: { value: ':Any;0:String;1:Number;2:Select' }
//			           },
//			           {name:'seq', width:80, align:"left", search:false, editable:true}
			    ],
			    pager: "#page-metaclass-attr",
			    gridview: true,
			    viewrecords: true,
			    rownumbers: true,
			    sortable: true,
			    sortname: 'name',
			    sortorder: "asc",
			    caption: "实例列表",
			    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" },
			    editurl:"saveOrUpdateCiTypeAttr",
			    onSelectRow: function (rowid, status) {
			    	var id = $("#metaclass-attr").jqGrid('getGridParam','selrow'); 
		    		var rowDatas = $("#metaclass-attr").jqGrid('getRowData', id); 
		    		var ciId = rowDatas["id"]; 
		    		var ciName = rowDatas["name"]; 
		    		var ciType = "<%=typeName%>";
		    		if("<%=isSource%>" == "true")
					{
		    			window.parent.selectSourceNode(ciId,ciName,ciType);
					}else
					{
						window.parent.selectTargetNode(ciId,ciName,ciType);
						}
					
                }
			});  

			initGridWidthHeightByPara(0.99,0.4);
			
			jQuery("#metaclass-attr").jqGrid('navGrid','#page-metaclass-attr',
				{edit:false, add:false, del:false, search:true, view:true},
			    { 
					url:"<%=updateCiTypeAttr%>",
					closeAfterEdit:true,
					afterSubmit:function(response){
						idToSelect = response.responseText;
						var res = $.parseJSON(idToSelect);
						
						var result = res.result;
					    if (result=='0') {
					    	alert("修改成功");
				    		return [true,res.result,false];
					    }else{
					    	return [false,res.result,false];
					    }
					},
					reloadAfterSubmit:true
					
			    }, // edit
			    { 
			    	url:"<%=addCiTypeAttr%>",
			    	closeAfterAdd:true,
			        afterSubmit: function(response) {
			        	var res = $.parseJSON(response.responseText);
			        	var result = res.result;
					    if (result=='0') {
					    	alert("新增成功");
				    		return [true,res.result,false];
					    }else{
					    	return [false,res.result,false];
					    }
			        },
			        reloadAfterSubmit:true
			    	
			    }, // add
			    {
			    	url:"delClass",
			    	afterSubmit: function(response) {
			        	var res = $.parseJSON(response.responseText);
			        	if(res.msg=="success"){
			        		alert("删除成功");
			        		return [true,res.msg,false];
			        	}else{
			        		return [false,res.msg,false];
			        	}
			        },
			        reloadAfterSubmit:true
			    	
			    }, // delete
			    {multipleSearch: false, overlay: false,top :150,left:250,sField:'searchField',sValue:'searchString',sOper: 'searchOper'}, // Search 
			    {   // vew options
			        beforeShowForm: function(form) {
			            $("metaclass-attr",form[0]).show();
			        },
			        afterclickPgButtons: function(whichbutton, form, rowid) {
			            $("metaclass-attr",form[0]).show();
			        }
			    }
			);

			jQuery("#metaclass-attr").jqGrid('filterToolbar', {
				stringResult: true,
				searchOnEnter: true, 
				defaultSearch: 'cn'
			});
			jQuery("#metaclass-attr")[0].toggleToolbar(); // Search toolbar hide by default


			var grid = $('#metaclass-attr');
			grid.jqGrid('navButtonAdd', '#page-metaclass-attr',{
				caption: "", 
				buttonicon: "ui-icon-calculator",
				title: "Choose Columns",
				onClickButton: function() {
			    	var id = $("#metaclass-attr").jqGrid('getGridParam','selrow'); 
		    		var rowDatas = $("#metaclass-attr").jqGrid('getRowData', id); 
		    		var ciId = rowDatas["id"]; 
		    		var ciName = rowDatas["name"]; 
		    		
					if("<%=isSource%>" == "true")
					{
						alert("isSource"+"<%=isSource%>");
						window.parent.ciSelect(ciId,ciName);
						}else
						{
							}
					//var ciListFrame = $("#ciListFrame",window.parent.document);
					
					
				}
			});
						
		  //jQuery("#metaclass-attr").jqGrid({url: "<%=getCiTypeAttr%>" }).trigger("loadGrid");
		
		}
</script>
</HEAD>


<body onLoad="afterLoad();">
			<table id="metaclass-attr" width = "600"></table>
			<div id="page-metaclass-attr" width = "600"></div>
			<div id="attrEdit-form" title="类属性编辑">
			<table id="ci-attr" width = "600"></table>
			<div id="page-ci-attr" width = "600"></div>
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
		</select>
	</fieldset>
	</form>
</div>
			

</body></HTML>