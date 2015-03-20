<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String typeName=(String)request.getParameter("typeName");
String ciId = (String)request.getParameter("id");
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

<script src="js/jquery/jqGrid-4.5.2/i18n/grid.locale-cn.js"
	type="text/javascript"></script>
<script src="js/jquery/jqGrid-4.5.2/jquery.jqGrid.min.js"
	type="text/javascript"></script>
<!-- <script src="metaclass/04-metaclass-attr.js" type="text/javascript"></script> -->
<script src="metaclass/attrEdit.js" type="text/javascript"></script>

	    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>


<style type="text/css">

</style>
<script src="${pageContext.request.contextPath}/pages/modelView/js/common/common.js" type="text/javascript"></script>
<script type="text/javascript">

	  function afterLoad(){
		  //加载下拉框
		  getRelationSelect();
			
		}
	  
	  //加载下拉框信息
	  function getRelationSelect(){
		  $.ajax({
			  type: "POST",
			  url: "getRelationList",
			  data: {
			    typeName : "<%=typeName%>"
			  },
			  async:false,
			  dataType: "json",
			  success: function( data, textStatus, jqXHR ) {
				  //colModelData = data;
				  //动态创建select
				  $(data).each(function(){
					  $("#re_select").append("<option value='"+this.name+"'>"+this.label+"</option>");
				  });
				  
			  }
			  
			});
	  }
	  
	  //查找关系信息
	  function searchRealtionVinfo(){
		  var selVal = $("#re_select").val();
		  if( selVal== 0){
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
		 //每次获取都要清除其数据
		 $("#gridDiv").empty();
		
		 startCreateJqGrid();
	  }
	  
	//请求后台获取关联ci类型及其数据
	function startCreateJqGrid(){
		var tabFlag = 0;
		$.ajax({
			  type: "POST",
			  url: "getNodeRelationMapL1",
			  data: {
				  startNodeType : "<%=typeName%>",
				  startNodeId : "<%=ciId%>",
				  relationName:$("#re_select").val()
			  },
			  async:false,
			  dataType: "json",
			  success: function( data, textStatus, jqXHR ) {
				 $.each(data,function(name,value) {
					 getGridColModelData(name,value,tabFlag);
					 tabFlag ++;
				});
			  }
			  
			});
			  
	}
    
	//获取colModelData
	function getGridColModelData(typeName,jsonStr,tabFlag){
		//首先请求获得calmodel
		$.ajax({
			  type: "POST",
			  url: "getCitTypeAtrrByNameAll",
			  data: {
			    typeName : typeName
			  },
			  async:false,
			  dataType: "json",
			  success: function( data, textStatus, jqXHR ) {
				  createWebJqGrid(data,typeName,jsonStr,tabFlag);
			  }
			  
			});
	}
	
	//开始创建jqgrid
	function createWebJqGrid(colModelData,typeName,jsonStr,tabFlag){
		var gridDiv = $("#gridDiv");
		var tableId = "metaclass-attr"+tabFlag;
		var pageId = "page-metaclass-attr"+tabFlag;
		var table = $("<table id='"+tableId+"' width = '100%'></table>");
		var page = $("<div id="+pageId+" width = '100%'></div>");
		gridDiv.append(table);
		gridDiv.append(page);//添加table以及page
		
		//changeWidthHeightByPara2(0.99,0.4,tableId);
		//开始创建jqgrid
		jQuery("#"+tableId).jqGrid({
			datatype: 'jsonstring',//设置数据格式为jsonstring  
            datastr : jsonStr,//指定数据  
		   //datatype: "json",
		   // url:"<%=getCiTypeAttr%>",
		    //jsonReader: { repeatitems: false },
		    rowNum: 500,
		    height: 240,
		    width: 905,
		    shrinkToFit:true,
		    scrollOffset: 0,
		    //rowList: [10,20,30],
		    loadonce: true, 
            colModel :colModelData,
		    pager: pageId,
		    hiddengrid:true,
		    gridview: true,
		    viewrecords: true,
		    rownumbers: true,
		    sortable: true,
		    sortname: 'name',
		    sortorder: "asc",
		    caption: typeName+" 关系列表",
		    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" },
		    onSelectRow: function (rowid, status) {
				var id = $("#"+tableId).jqGrid('getGridParam','selrow'); 
	    		var rowDatas = $("#"+tableId).jqGrid('getRowData', id); 
	    		var row = rowDatas["id"]; 
            }
		});
		//initGridWidthHeightByPara2(0.99,0.4,tableId);
		
		jQuery("#"+tableId).jqGrid('navGrid','#'+pageId,
				{edit:false, add:false, del:false, search:true, view:true},
			    { 
			    }, // edit
			    { 
			    }, // add
			    {
			    }, // delete
			    {multipleSearch: false, overlay: false,top :150,left:250,sField:'searchField',sValue:'searchString',sOper: 'searchOper'}, // Search 
			    {   // vew options
			        beforeShowForm: function(form) {
			            $("#"+tableId,form[0]).show();
			        },
			        afterclickPgButtons: function(whichbutton, form, rowid) {
			            $("#"+tableId,form[0]).show();
			        }
			    }
			);
		
		jQuery("#"+tableId).jqGrid('filterToolbar', {
			stringResult: true,
			searchOnEnter: true, 
			defaultSearch: 'cn'
		});
		jQuery("#"+tableId)[0].toggleToolbar(); // Search toolbar hide by default
		
		jQuery("#"+tableId).jqGrid('navButtonAdd', '#'+pageId,{
			caption: "查看关系", 
			title: "查看关系",
			position:"last",
			onClickButton: function() {
				var id = $("#"+tableId).jqGrid('getGridParam','selrow'); 
	    		var rowDatas = $("#"+tableId).jqGrid('getRowData', id); 
	    		var row = rowDatas["id"]; 
	    		if(id == null || id == ""){
	    			$("#alertWindow2").dialog(
	    					{
	    						  title:"提示",
	    						  height:130,
	    						  width:150,
	    						  position:"center",
	    						  modal:true,
	    					});
	    			return;
	    		}
	    		$("#relationViewWindowFrame").attr("src","../ciMgr/relationView.jsp?typeName=<%=typeName%>&id="+id);
	    		$("#relationViewWindow" ).dialog({height:550,width:950,zIndex:500000, position:"top"});
			}
		});
	}
	
</script>
</HEAD>


<body onLoad="afterLoad();" id="htmlBody">
<div style="width: 100%">
<label>关系类型：</label>
<select name="re_select" id="re_select" style="width: 150px;"  class="dept_select">	
	             <option value="0">请选择</option>
           </select>
           <button id="searchRelationBut" class="ui-button ui-widget ui-state-default" onclick="searchRealtionVinfo()" style="padding-right: 0px">查找关系</button>
</div>

<div id = "gridDiv">
<!-- 
<table id="metaclass-attr" width = "100%"></table>
<div id="page-metaclass-attr" width = "100%"></div>
 -->


</div>           

<div id="alertWindow" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="" style="width:100%;height:100%;padding-top: 24px;vertical-align: middle;">
    <label id="labeChage">请选关系！</label>
</div>

<div id="alertWindow2" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="" style="width:100%;height:100%;padding-top: 24px;vertical-align: middle;">
    <label>请选择行！</label>
</div>

<div id="relationViewWindow" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="查询关系" style="width:100%;height:100%;padding-top: 24px;overflow: hidden; top: 4px; left: 4px;">
<iframe scrolling="no" id="relationViewWindowFrame"  frameborder="0"  src="../ciMgr/relationship.jsp?typeName=<%=typeName%>" style="width:100%;height:100%;"></iframe>
</div>
</body>
</HTML>