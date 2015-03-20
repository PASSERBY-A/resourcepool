<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String typeName=(String)request.getParameter("typeName");
String ciId = (String)request.getParameter("ciId");
if(typeName == null)
{
	typeName = "Ci";
	}
String getCiRecord = "getCiRecord?typeName="+typeName+"&ciId="+ciId;
String addCiRecord = "addCiRecord?typeName="+typeName+"&ciId="+ciId ;
String updateCiRecord = "updateCiRecord?typeName="+typeName+"&ciId="+ciId ;
String delCiRecord = "delCiRecord?typeName="+typeName+"&ciId="+ciId ;
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

<script type="text/javascript" src="metaclass/04-metaclass.js"></script>
<link rel="stylesheet" href="metaclass/metaclass-icon.css" type="text/css">

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-cn.js"
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


	  function afterLoad(){
		    changeWidthHeight();
			jQuery("#metaclass-attr").jqGrid({
			    datatype: "json",
			    url:"<%=getCiRecord%>",
			    jsonReader: { repeatitems: false },
			    rowNum: 10,
			    height: 240,
			    width: 800,
			    scrollOffset: 0,
			    //rowList: [10,20,30],
			    loadonce: false,
			    colNames:['id','nodeKey','维修单号','损坏配件序列号','配件类型','更换配件序列号','维修日期','维修类型','MA合同号','日期'],
			    colModel:[
						{name:'id', width:140, hidden:true, align:'left', editable:true},
						{name:'nodeKey', width:60, hidden:true, editable:true}, 
			           {name:'repairNum', width:60, hidden:false, editable:true},
			           {name:'damageDeviceNum', width:140, hidden:false, align:'left', editable:true},
			           {name:'deviceType', width:140, hidden:false, align:'left', editable:true},
			           {name:'replaceDeviceNum', width:140, hidden:false, align:'left', editable:true},
			           {name:'repairDate', width:140, hidden:false, align:'left', editable:true},
			           {name:'repairType', width:140, hidden:false, align:'left', editable:true},
			           {name:'maNum', width:140, hidden:false, align:'left', editable:true},
			           {name:'maDate', width:140, hidden:false, align:'left', editable:true}
			           
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
			    caption: "属性列表",
			    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" },
			    editurl:"saveOrUpdateCiTypeAttr"
			});  
			
			initGridWidthHeight();　

			jQuery("#metaclass-attr").jqGrid('navGrid','#page-metaclass-attr',
				{edit:true, add:true, del:true, search:true, view:true},
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
						
		  //jQuery("#metaclass-attr").jqGrid({url: "<%=getCiRecord%>" }).trigger("loadGrid");

		}
	  
</script>
</HEAD>


<body onLoad="afterLoad();">
			<table id="metaclass-attr" width = "100%"></table>
			<div id="page-metaclass-attr" width = "100%"></div>
</div>
			

</body></HTML>