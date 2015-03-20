<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String typeName=(String)request.getParameter("typeName");
if(typeName == null)
{
	typeName = "Ci";
	}
String getCiTypeAttr = "getCiTypeAttr?typeName="+typeName ;
String addCiTypeAttr = "addCiTypeAttr?typeName="+typeName ;
String delCiTypeAttr = "delCiTypeAttr?typeName="+typeName ;
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

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script src="js/jquery/jqGrid-4.5.2/jquery.jqGrid.min.js"
	type="text/javascript"></script>
<!-- <script src="metaclass/04-metaclass-attr.js" type="text/javascript"></script> -->
<script src="metaclass/attrEdit.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
<script src="${pageContext.request.contextPath}/pages/modelView/js/common/common.js" type="text/javascript"></script>

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
		    name : $("#name").val(),
		    label : $("#label").val(),
		    dataType : $("#dataType").val(),
		    attGroup : $("#attGroup").val(),
		    order : $("#order").val(),
		    defValue : $("#defValue").val(),
		    viewMode : $("#viewMode").val(),
		    isRequired: $("#isRequired").val(),
		    recordChange : $("#recordChange").val(),
		    updateMode : $("#updateMode").val()
		  },
		  async:false,
		  dataType: "json",
		  success: function( data, textStatus, jqXHR ) {
			  var message = data.success;
			  var result = data.result;
			  alert(message);
			  if(result=='0'){
				  //afterLoad();
				  jQuery("#metaclass-attr").jqGrid({url: "<%=getCiTypeAttr%>" }).trigger("reloadGrid");
			  }else{
				  alert(message);
			  }
		  }
		  
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
			    //rowList: [10,20,30],
			    loadonce: true,
			    colNames:['属性','属性显示名称','数据类型','属性组','排序标识','默认值','页面显示模式','必填选项','记录变更','更新模式'],
			    colModel:[
			           {name:'name', width:60, hidden:false, editable:true,editrules:{required:true}},
			           {name:'label', width:140, hidden:false, align:'left', editable:true,editrules:{required:true}},
			           {name:'dataType', width:60, hidden:false, editable:true,edittype:"select",editoptions:{value:"String:字符;Number:数字 "}},
			           {name:'attGroup', width:60, hidden:false, editable:true,editrules:{required:true}},
			           {name:'order', width:60, hidden:false, editable:true,editrules:{number:true,required:true},sorttype:'number'},
			           {name:'defValue', width:60, hidden:false, editable:true,editrules:{required:true}},
			           {name:'viewMode', width:60, hidden:false, editable:true,edittype:"select",editoptions:{value:"0:不限制 ;1:界面只读;2:界面不可见 "}},
			           {name:'isRequired', width:60, hidden:false, editable:true,edittype:"select",editoptions:{value:"true:是 ;false:否"}},
			           {name:'recordChange', width:60, hidden:false, editable:true,edittype:"select",editoptions:{value:"true:是 ;false:否"}},
			           {name:'updateMode', width:60, hidden:false, editable:true,edittype:"select",editoptions:{value:"0:不限制 ;1:只手动;2:只自动 "}}
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
			    sortname: 'order',
			    sortorder: "asc",
			    caption: "类属性",
			    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" },
			    editurl:"delCiTypeAttr",
			    loadComplete:function(){
			    	//格式化显示
			    	var grid = $('#metaclass-attr');
			    	 var n = grid.getGridParam('rowNum'); 
			    	 for(i=1;i<=n+1;i++)                                     
			            {
			              var data =grid.getRowData(i);
			                    switch(data.updateMode){
			                        case '0':    grid.setCell(i,"updateMode",'不限制');break;
			                        case '1':  grid.setCell(i,"updateMode",'只手动');break;
			                        case '2': grid.setCell(i,"updateMode",'只自动');break;
			                        default:    grid.setCell(i,"updateMode",'undefine');break;
			                    }
			            }
			    	 
			    	 for(i=1;i<=n+1;i++)                                     
			            {
			    		   var data =grid.getRowData(i);
			                    switch(data.viewMode){
		                        case '0':    grid.setCell(i,"viewMode",'不限制');break;
		                        case '1':  grid.setCell(i,"viewMode",'界面只读');break;
		                        case '2': grid.setCell(i,"viewMode",'界面不可见');break;
		                        default:    grid.setCell(i,"viewMode",'已删除（历史属性）');break;
		                    }
			            }
			    	 for(i=1;i<=n+1;i++)                                     
			            {
			    		   var data =grid.getRowData(i);
			                    switch(data.isRequired){
		                        case 'true':    grid.setCell(i,"isRequired",'是');break;
		                        case 'false':  grid.setCell(i,"isRequired",'否');break;
		                        default:    grid.setCell(i,"isRequired",'未知');break;
		                    }
			            }
			    	 for(i=1;i<=n+1;i++)                                     
			            {
			    		   var data =grid.getRowData(i);
			                    switch(data.recordChange){
		                        case 'true':    grid.setCell(i,"recordChange",'是');break;
		                        case 'false':  grid.setCell(i,"recordChange",'否');break;
		                        default:    grid.setCell(i,"recordChange",'未知');break;
		                    }
			            }
			    	
			    }
			});  

			initGridWidthHeightByPara(0.99,0.4);
			
			jQuery("#metaclass-attr").jqGrid('navGrid','#page-metaclass-attr',
				{edit:true, add:true, del:false, search:true, view:true},
			    {
					url:"<%=addCiTypeAttr%>",
					closeAfterEdit:true,
					afterSubmit:function(response){
						
						idToSelect = response.responseText;
						var res = $.parseJSON(idToSelect);
						var result = res.result;
					    if (result=='0') {
					    	alert("修改成功");
					    	reloadJqGrid();
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
					    	reloadJqGrid();
				    		return [true,res.result,false];
					    }else{
					    	return [false,res.result,false];
					    }
			        },
			        reloadAfterSubmit:true
			    	
			    }, // add
			    {
			    	
			    	
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
				buttonicon: "ui-icon-trash",
				title: "删除选择项",
				onClickButton: function() {
					//alert("click");
					//grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
					var id = $("#metaclass-attr").jqGrid('getGridParam','selrow'); 
		    		var rowDatas = $("#metaclass-attr").jqGrid('getRowData', id); 
		    		var row = rowDatas["name"]; 
		    		
		    		if(id == null || id == ""){
		    			$("#alertWindow").dialog(
		    					{
		    						  title:"提示",
		    						  height:130,
		    						  width:150,
		    						  position:"center",
		    						  modal:true,
		    					});
		    			return;
		    		}
		    		
		    		$("#confirmWindow").dialog(
		    		{
		    			   title:"删除",
						  height:200,
						  width:350,
						  position:"center",
						  modal:true,
						  buttons:{
							  '删除':function(){
								  $.ajax({
						  			  type: "POST",
						  			  url: "<%=delCiTypeAttr%>",
						  			  data: {
						    			attrName: row			  		    
						  			  },
						  			  async:false,
						  			  dataType: "json",
						  			  success: function( data, textStatus, jqXHR ) {
						  				  var message = data.success;
						  				  var result = data.result;
						  				  //alert(message);
						  				  if(result=='0'){
							  				  alert("删除成功");
							  				$("#confirmWindow").dialog("close");
							  				//if( id != null ) jQuery("#metaclass-attr").jqGrid('delGridRow',id,{reloadAfterSubmit:false});
							  				reloadJqGrid();
							  				jQuery("#metaclass-attr").jqGrid({url: "<%=getCiTypeAttr%>" }).trigger("reloadGrid");
						  					  return true;
						  				  }else{
						  					  return false;
						  				  }
						  			  }
						  			  
						  			});
							  },
							  '取消':function(){
								  $(this).dialog("close");
							  }
						  }
		    		}		
		    		
		    		);
		    		
				}
			});
						
		  //jQuery("#metaclass-attr").jqGrid({url: "<%=getCiTypeAttr%>" }).trigger("loadGrid");

		}
	  
	  function reloadJqGrid(){
		  $("#gridDivId").empty();
			var table = $("<table id='metaclass-attr' width = '100%'></table>");
			var page = $("<div id='page-metaclass-attr' width = '100%'></div>");
			$("#gridDivId").append(table);
			$("#gridDivId").append(page);//添加table以及page
			afterLoad();
	  }
</script>
</HEAD>


<body onLoad="afterLoad();">
<div style="width: 100%" id="gridDivId">
<table id="metaclass-attr" style="width: 100%"></table>
			<div id="page-metaclass-attr" style="width: 100%"></div>
</div>
<!-- 
			<table id="metaclass-attr" width = "600"></table>
			<div id="page-metaclass-attr" width = "600"></div>
 -->

			<div id="attrEdit-form" title="类属性编辑">
	<p class="validateTips">All form fields are required.</p>

	<form>
	<fieldset>
		<label for="name">属性</label>
		<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" /></br>
		<label for="label">显示名称</label>
		<input type="text" name="label" id="label" class="text ui-widget-content ui-corner-all" /></br>
		<label for="dataType">数据类型</label>
		<input type="text" name="dataType" id="dataType" class="text ui-widget-content ui-corner-all" /></br>
		<label for="attGroup">属性组</label>
		<input type="text" name="attGroup" id="attGroup" class="text ui-widget-content ui-corner-all" /></br>
				<label for="order">排序标识</label>
		<input type="text" name="order" id="order" class="text ui-widget-content ui-corner-all" /></br>
				<label for="defValue">默认值</label>
		<input type="text" name="defValue" id="defValue" class="text ui-widget-content ui-corner-all" /></br>
				<label for="viewMode">页面显示模式</label>
		<input type="text" name="viewMode" id="viewMode" class="text ui-widget-content ui-corner-all" /></br>
				<label for="isRequired">必填选项</label>
		<input type="text" name="isRequired" id="isRequired" class="text ui-widget-content ui-corner-all" /></br>
						<label for="recordChange">记录变更</label>
		<input type="text" name="recordChange" id="recordChange" class="text ui-widget-content ui-corner-all" /></br>
				<label for="updateMode">更新模式</label>
		<input type="text" name="updateMode" id="updateMode" class="text ui-widget-content ui-corner-all" /></br>
		<label for="iconSkin">iconSkin</label>
		<select name="iconSkin" id="iconSkin">
			<option id="icon_hardware">icon_hardware</option>
			<option id="icon_network">icon_network</option>
		</select>
	</fieldset>
	</form>
</div>
	<div id="alertWindow" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="" style="width:100%;height:100%;padding-top: 24px;vertical-align: middle;">
    <label>请选择行！</label>
</div>

</div>
	<div id="confirmWindow" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="" style="width:100%;height:100%;padding-top: 24px;vertical-align: middle;">
    删除所选记录？
</div>
</body></HTML>