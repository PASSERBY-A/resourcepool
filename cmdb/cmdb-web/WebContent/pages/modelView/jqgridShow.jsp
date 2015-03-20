<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/commonInclude.jsp" %>
<!DOCTYPE html>

<%
String typeName=(String)request.getParameter("typeName");
if(typeName == null)
{
	typeName = "Ci";
}
String rootViewName = (String)request.getParameter("rootViewName");

String getCiTypeAttr = "getCibyType?typeName="+typeName+"&rootViewName="+rootViewName ;
String addCiTypeAttr = "addCibyType?typeName="+typeName ;
String updateCiTypeAttr = "updateCibyType?typeName="+typeName ;
String delCiTypeAttr = "delCibyType?typeName="+typeName ;

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<HTML>
<HEAD>
<TITLE></TITLE>

<script type="text/javascript">
function getTypeName(){
	return "<%=typeName%>";
}

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

var colModelData = []; 
	  function afterLoad(){

		  $("#openRoleDiv").hide();
		  
		  
			$.ajax({
				  type: "POST",
				  url: "getCiNameAllListSource",
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
			
			changeWidthHeight();
			
			jQuery("#metaclass-attr").jqGrid({
			    datatype: "json",
			    url:"<%=getCiTypeAttr%>",
			    jsonReader: { repeatitems: false },
			    rowNum: 20,
			    height: 240,
			    //width: 800,
			    //autowidth:true,
			    shrinkToFit:true,
			    scrollOffset: 0,
			    //rowList: [10,20,30],
			    loadonce: true,
			    //colNames:['id','name','label'],
			    colModel:colModelData,
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
		    		var row = rowDatas["id"]; 
					//grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
					$("#openXXXIframe").attr("src","../modelView/ciAttribute.jsp?typeName=<%=typeName%>&ciId="+row);
					$("#openRoleDiv" ).dialog(
						{height:450,width:850}
							);
                }
			});  
			
			initGridWidthHeight();

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
			        	var returnNode = res.node;
					    if (result=='0') {
					    	alert("新增成功");
							 var openUrl= "ciAttribute.jsp?typeName=";
							 openUrl=openUrl+"<%=typeName%>";
							 openUrl=openUrl+"&ciId=";
							 openUrl=openUrl+returnNode.id;
							window.open(openUrl, "newwindow"); 
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
		    		var row = rowDatas["id"]; 
					grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
					//$("#openXXXIframe").attr("src","../ciMgr/ci.jsp?typeName=<%=typeName%>");
					//$("#openXXXIframe" ).dialog();
					
					
//					 var openUrl= "ciAttribute.jsp?typeName=";
//					 openUrl=openUrl+"<%=typeName%>";
//					 openUrl=openUrl+"&ciId=";
//					 openUrl=openUrl+row;
//					window.open(openUrl, "newwindow"); 
				}
			});
			
			//添加下载按钮
			grid.jqGrid('navButtonAdd', '#page-metaclass-attr',{
				caption: "下载", 
				title: "下载",
				position:"last",
				onClickButton: function() {
					var id = $("#metaclass-attr").jqGrid('getGridParam','selrow'); 
		    		var rowDatas = $("#metaclass-attr").jqGrid('getRowData', id); 
		    		var row = rowDatas["id"]; 
		    		//$("#relationViewWindowFrame").attr("src","../ciMgr/relationView.jsp?typeName=<%=typeName%>&id="+id);
		    		//$("#relationViewWindow" ).dialog({height:550,width:950,modal:true});
					//grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
					//$("#openXXXIframe").attr("src","../ciMgr/ci.jsp?typeName=<%=typeName%>");
					//$("#openRoleDiv" ).dialog({height:800,width:650});
					//openCreateCiDialog();
					
					// var openUrl= "<%=basePath%>"+"cmdbDownServlet?down_type=source&node_type=<%=typeName%>";
					
					chagedownloadUrl();
				}
			});
						
		  //jQuery("#metaclass-attr").jqGrid({url: "<%=getCiTypeAttr%>" }).trigger("loadGrid");
		
			
		}
	  
	  function chagedownloadUrl(){
		  var node_columnString = "";
		  for (var one in colModelData)
		     {
		        for(var key in colModelData[one])
		         {
		        	if(key == "name"){
		        		node_columnString += colModelData[one][key] + ";";
		        	}
		        	
		         }
		     }
		  var url = "<%=basePath%>"+"cmdbDownServlet?down_type=view&view_name=<%=rootViewName%>&node_type=<%=typeName%>&node_column="+node_columnString;
		 //var url = "<%=basePath%>"+"cmdbDownServlet?down_type=view&view_name=<%=rootViewName%>&node_type=<%=typeName%>";
		 //window.open(url, "newwindow"); 
		 $("#downloadIframe").attr("src",url);
		 // $("#downloadUrl").attr("href",url);
	  }
	  
	  //下载数据
	  function downloadDataSource(){
		  alert( $("#downloadUrl").attr("href"));
	  }
	  
	  
</script>

</HEAD>


<body onLoad="afterLoad();">
<div>

</div>
			<table id="metaclass-attr" width = "100%"></table>
			<!--  
			<input type="button" value="下载数据" onclick="downloadDataSource()" style="padding-right: 0">
			<a id="downloadUrl" href="http://127.0.0.1:8080/cmdb-web/cmdbDownServlet?down_type=view&view_name=view_fabric_54&node_type=Host">下载</a>
			-->
			
			
			<div id="page-metaclass-attr" width = "100%"></div>
			<div id="attrEdit-form" title="类属性编辑">
			<table id="ci-attr" width = "100%"></table>
			<div id="page-ci-attr" width = "100%"></div>
</div>
<div id="openRoleDiv" class="easyui-window" hidden="true" closed="true" modal="true" title="" style="width:100%;height:100%;vertical-align: middle;" >
     <iframe scrolling="auto" id='openXXXIframe' frameborder="0"  src="../ciMgr/relationship.jsp?typeName=<%=typeName%>" style="width:100%;height:100%;"></iframe>
</div>			
<iframe scrolling="no" id='downloadIframe' frameborder="0"  src="../ciMgr/relationship.jsp?typeName=<%=typeName%>" style="width:700px;height:380px;"></iframe>
</body></HTML>