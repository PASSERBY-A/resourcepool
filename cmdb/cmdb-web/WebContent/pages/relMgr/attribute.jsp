<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String typeName=(String)request.getParameter("typeName");
if(typeName == null)
{
	typeName = "RelationCi";
	}
String getCiTypeAttr = "getCibyType?typeName="+typeName ;
String addCiTypeAttr = "addCibyType?typeName="+typeName ;
String updateCiTypeAttr = "updateCibyType?typeName="+typeName ;
String delCiTypeAttr = "delCibyType?typeName="+typeName ;
String path = request.getContextPath();
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
		  url: "addRelationCi",
		  data: {
		   label:$("#label").val(),
		    name: $("#name").val(),
		    typeName : "<%=typeName%>",
		    sourceNodeId: $("#sourceNodeId").val(),
		    sourceNode: $("#sourceNode").val(),
		    sourceType: $("#sourceType").val(),
		    targetNodeId: $("#targetNodeId").val(),
		    targetNode: $("#targetNode").val(),
		    targetType: $("#targetType").val()
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


	  function afterLoad(){
		  changeWidthHeightByPara(0.99,0.4);
		  
			jQuery("#metaclass-attr").jqGrid({
			    datatype: "json",
			    url:"<%=getCiTypeAttr%>",
			    jsonReader: { repeatitems: false },
			    rowNum: 50,
			    height: 240,
			    width: 800,
			    scrollOffset: 0,
			    rowList: [50,100,150],
			    loadonce: true,
			    colNames:['id','名称','显示名称','源节点名称','源节点ID','源节点类名称','目标节点名称','目标节点ID','目标节点类名称'],
			    colModel:[
						{name:'id', width:60, hidden:true, editable:false},
			           {name:'name', width:60, hidden:false, editable:false},
			           {name:'label', width:140, hidden:false, align:'left', editable:true},
			           {name:'sourceName', width:80, hidden:false, align:'left', editable:true},
			           {name:'sourceId', width:80, hidden:false, align:'left', editable:true},
			           {name:'sourceType', width:80, hidden:false, align:'left', editable:true},
			           {name:'targetName', width:80, hidden:false, align:'left', editable:true},
			           {name:'targetId', width:80, hidden:false, align:'left', editable:true},
			           {name:'targetType', width:80, hidden:false, align:'left', editable:true}
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
			    editurl:"saveOrUpdateCiTypeAttr"
			});  

			initGridWidthHeightByPara(0.99,0.4);
			
			jQuery("#metaclass-attr").jqGrid('navGrid','#page-metaclass-attr',
				{edit:true, add:false, del:true, search:true, view:true},
			    { 
					url:"<%=updateCiTypeAttr%>",
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
			    	
			    }, // add
			    {
			    	url:"delClass?typeName=<%=typeName%>",
			    	position:"center",
			    	modal:true,
			    	afterSubmit: function(response) {
			        	var res = $.parseJSON(response.responseText);
			        	if(res.result==0){
			        		alert("删除成功!");
			        		reloadJqGrid();
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
				buttonicon: "ui-icon-plusthick",
				title: "创建关系",
				onClickButton: function() {
					//var id = $("#metaclass-attr").jqGrid('getGridParam','selrow'); 
		    		//var rowDatas = $("#metaclass-attr").jqGrid('getRowData', id); 
		    		//var row = rowDatas["id"]; 
					//grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
					 //var openUrl= "ciAttribute.jsp?typeName=";
					 //openUrl=openUrl+"<%=typeName%>";
					 //openUrl=openUrl+"&ciId=";
					 //openUrl=openUrl+row;
					//window.open(openUrl, "newwindow"); 
					$("#relationViewWindow" ).dialog(
							
									{ 
										height: 650,
										width:1120,
										close:function(){
											$("#openXXXIframeRela").attr("src","");
											
											$("#openXXXIframeRela").attr("src","../relMgr/relationship2.jsp?typeName=<%=typeName%>");
										}
								     }
									
							
							);
				}
			});
						
		  //jQuery("#metaclass-attr").jqGrid({url: "<%=getCiTypeAttr%>" }).trigger("loadGrid");
			
			$( "#openRoleDiv" ).hide();
		}
	  
	  //供relationship页面调用,完成添加关系后关闭窗口并刷新数据显示
	  function closeDialogMethod(){
		  $( "#relationViewWindow" ).dialog("close");
		  reloadJqGrid();
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

			<div id="attrEdit-form" title="关系实例编辑">
	<p class="validateTips">All form fields are required.</p>

	<form>
	<fieldset>
		<label for="id">关系ID</label>
		<input type="text" name="id" id="id" class="text ui-widget-content ui-corner-all" /></br>
		<label for="name">关系名</label>
		<input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" /></br>
				<label for="label">关系显示名</label>
		<input type="text" name="label" id="label" class="text ui-widget-content ui-corner-all" /></br>
		<label for="sourceNodeId">起始节点ID</label>
		<input type="text" name="sourceNodeId" id="sourceNodeId" class="text ui-widget-content ui-corner-all" /></br>
		<label for="sourceType">起始节点类型</label>
		<input type="text" name="sourceType" id="sourceType" class="text ui-widget-content ui-corner-all" /></br>
		<label for="sourceNode">起始节点</label>
		<input type="text" name="sourceNode" id="sourceNode" class="text ui-widget-content ui-corner-all" /></br>
		<label for="targetNodeId">目标节点ID</label>
		<input type="text" name=targetNodeId id="targetNodeId" class="text ui-widget-content ui-corner-all" /></br>		
		<label for="targetType">目标节点类型</label>
		<input type="text" name="targetType" id="targetType" class="text ui-widget-content ui-corner-all" /></br>		
		<label for="targetNode">目标节点</label>
		<input type="text" name="targetNode" id="targetNode" class="text ui-widget-content ui-corner-all" /></br>		
	</fieldset>
	</form>
</div>
<div id="openRoleDiv" class="easyui-window" hidden="true" closed="true" modal="true" title="标题" style="width:700px;height:400px;">
     <iframe scrolling="auto" id='openXXXIframe' frameborder="0"  src="../relMgr/relationship.jsp?typeName=<%=typeName%>" style="width:300px;height:400px;"></iframe>
</div> 		

<div id="relationViewWindow" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="创建关系" style="width:100%;height:100%;padding-top: 24px;overflow: hidden; top: 4px; left: 4px;">
<iframe scrolling="no" id="openXXXIframeRela"  frameborder="0"  src="../relMgr/relationship2.jsp?typeName=<%=typeName%>" style="width:100%;height:100%;"></iframe>
</div>	

</body></HTML>