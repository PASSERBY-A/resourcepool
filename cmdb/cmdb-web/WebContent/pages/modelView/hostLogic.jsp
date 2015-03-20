<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String typeName=(String)request.getParameter("typeName");
String ciId = (String)request.getParameter("ciId");
if(typeName == null)
{
	typeName = "Ci";
}
String getNodeByRelation = "getNodeByRelation?startNodeType="+typeName+"&startNodeId="+ciId+"&relationName=linked";
String addCiRecord = "addCiRecord?typeName="+typeName+"&ciId="+ciId ;
String updateCiRecord = "updateCiRecord?typeName="+typeName+"&ciId="+ciId ;
String delCiRecord = "delCiRecord?typeName="+typeName+"&ciId="+ciId ;

if(typeName.toLowerCase().equals("host")){
	typeName = "HostDisk";
}
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
<script src="js/common/common.js" type="text/javascript"></script>
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
<!-- 
<script type="text/javascript" src="metaclass/04-metaclass.js"></script>
 -->

<link rel="stylesheet" href="metaclass/metaclass-icon.css" type="text/css">

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script src="js/jquery/jqGrid-4.5.2/jquery.jqGrid.min.js"
	type="text/javascript"></script>
<!-- <script src="metaclass/04-metaclass-attr.js" type="text/javascript"></script> -->
<script src="metaclass/attrEdit.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
<script src="${pageContext.request.contextPath}/pages/modelView/js/common/common.js" type="text/javascript"></script>


<script type="text/javascript">

var colModelData = []; 
      //初始化数据表格
	  function initjqGridDataLogic(){
    	  
		  $.ajax({
			  type: "POST",
			    url:"getCitAttrbyName",
			  //url: "getCiNameAllListSource",
			  data: {
			    //id: $("#id").val(),
			   // pid:treeNode.parentId,
			  //  label:$("#label").val(),
			   // name: $("#name").val(),
			    typeName : "<%=typeName%>"
			    //iconSkin: $("#iconSkin").val()
			  },
			  async:false,
			  dataType: "json",
			  success: function( data, textStatus, jqXHR ) {
				  //alert(data);
				  colModelData = data;
			  }
			  
			});
		  
		  changeWidthHeightByPara(0.99,0.4);
		  
		  jQuery("#metaclass-attr").jqGrid({
			    datatype: "json",
			    url:"<%=getNodeByRelation%>",
			    jsonReader: { repeatitems: false },
			    rowNum: 20,
			    //height: 240,
			    //width: 800,
			    scrollOffset: 0,
			    //rowList: [10,20,30],
			    loadonce: false,
			    colModel:colModelData,
			    subGridWidth:30,
			    pager: "#page-metaclass-attr",
			    gridview: true,
			    viewrecords: true,
			    rownumbers: true,
			    sortable: true,
			    sortname: 'name',
			    sortorder: "asc",
			    caption: "关系列表",
			    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" }
			    //,
			    //editurl:"saveOrUpdateCiTypeAttr"
			});  
		  initGridWidthHeightByPara(0.99,0.4);

		  jQuery("#metaclass-attr").jqGrid('navGrid','#page-metaclass-attr',
					{edit:false, add:false, del:false, search:false, view:true},
				    { 
						url:"<%=updateCiRecord%>",
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
				    	url:"<%=addCiRecord%>",
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
					//alert("click");
					grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
					//window.open("page.html", "newwindow","height=100, width=400, top=0,left=0,toolbar=no, menubar=no, scrollbars=no, resizable=no,location=no,status=no"); 
				}
			});

		}
	  
</script>
</HEAD>
<body onLoad="initjqGridDataLogic();">
			<table id="metaclass-attr" width = "600"></table>
			<div id="page-metaclass-attr" width = "600"></div>
</div>
			

</body>
</HTML>