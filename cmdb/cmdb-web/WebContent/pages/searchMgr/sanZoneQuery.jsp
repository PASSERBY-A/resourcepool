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

var colModelValues = [];

$(document).ready(function(){
	 getSanSelect();
	
});

//加载下拉框信息
function getSanSelect(){
	  $.ajax({
		  type: "POST",
		  url: "getSanList",
		  data: {
		    typeName : "San"
		  },
		  async:false,
		  dataType: "json",
		  success: function( data, textStatus, jqXHR ) {
			  //colModelData = data;
			  //动态创建select
			  $(data).each(function(){
				  $("#san_select").append("<option value='"+this.name+"'>"+this.label+"</option>");
			  });
			  
		  }
		  
		});
}

	  function afterLoad(){
		  
		  //加载显示列属性
		  $.ajax({
			  type: "POST",
			  url: "getCitTypeAtrrByNameAll",
			  data: {
			    typeName : "<%=typeName%>"
			  },
			  async:false,
			  dataType: "json",
			  success: function( data, textStatus, jqXHR ) {
				  colModelValues = data;
			  }
			  
			});
		  
		  changeWidthHeightByPara(0.99,0.3);
		  
			jQuery("#metaclass-attr").jqGrid({
			    datatype: "json",
			    url:"searchSanZone",
			    postData: {
			    	sanSwitchID : $("#san_select").val(),
			    	zoneName:$("#zoneName").val(),
			    	zoneConfig:$("#zoneConfig").val(),
			    	isDownload:"no",
			    	typeName : "<%=typeName%>"
				  },
			    jsonReader: { repeatitems: false },
			    rowNum: 50,
			    ExpandColClick:true,
			    //width: 800,
			    scrollOffset: 0,
			    rowList: [50,100,150],
			    loadonce: true,
			    autowidth: true,
			    colModel:colModelValues,
			    pager: "#page-metaclass-attr",
			    gridview: true,
			    viewrecords: true,
			    rownumbers: true,
			    sortable: true,
			    sortname: 'name',
			    sortorder: "asc",
			    caption: "主机信息",
			    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" },
			    subGrid : true, //显示内部Grid（次Grid），单击行展开嵌套Grid  
	            subGridRowExpanded: function(subgrid_id, row_id) { //Grid内部嵌套Grid 
	            	var rowDatas = $("#metaclass-attr").jqGrid('getRowData', row_id);
	            	var hosthba = rowDatas["hosthba"]; 
	            	var storagenetport = rowDatas["storagenetport"]; 
	                var subgrid_table_id, pager_id;   
	                subgrid_table_id = subgrid_id+"_t";   
	                pager_id = "p_"+subgrid_table_id;   
	                $("#"+subgrid_id).html("<table id='"+subgrid_table_id+"' width='100%' tabindex='0' cellspacing='0' cellpadding='0' border='0' role='grid' aria-multiselectable='false' aria-labelledby='gbox_metaclass-attr' class='ui-jqgrid-btable'></table>");   
	               // $("#"+subgrid_table_id).append($("<label>"+row+"</label>"));
	               var tr1 = $("<tr class='ui-jqgrid-labels ui-sortable'><th class='ui-state-default ui-th-column ui-th-ltr'>主机HBA关联</th><th class='ui-state-default ui-th-column ui-th-ltr'>存储关联</th></tr>");
	               var tr2 = $("<tr class='ui-widget-content jqgrow ui-row-ltr ui-state-highlight'></tr>");
	               tr2.append("<td>"+hosthba+"</td>");
	               tr2.append("<td>"+storagenetport+"</td>");
	               $("#"+subgrid_table_id).append(tr1);
	               $("#"+subgrid_table_id).append(tr2);
	            }, 
			});  

			initGridWidthHeightByPara(0.99,0.3);
			
			jQuery("#metaclass-attr").jqGrid('navGrid','#page-metaclass-attr',
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
				title: "选择显示列",
				onClickButton: function() {
					//alert("click");
					grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
				}
			});
			
			//添加下载按钮
			grid.jqGrid('navButtonAdd', '#page-metaclass-attr',{
				caption: "下载", 
				title: "下载",
				position:"last",
				onClickButton: function() {
					
					 var node_columnString = "";
					  for (var one in colModelValues)
					     {
					        for(var key in colModelValues[one])
					         {
					        	if(key == "name"){
					        		var temp = colModelValues[one][key];
					        		if(temp == "ciLabel"){
					        			temp = "label";
					        		}
					        		node_columnString += temp + ";";
					        	}
					        	
					         }
					     }
					$.ajax({
						  type: "POST",
						  url:"searchSanZone",
						  data: {
							    sanSwitchID : $("#san_select").val(),
						    	zoneName:$("#zoneName").val(),
						    	zoneConfig:$("#zoneConfig").val(),
						    	node_column:node_columnString,
						    	isDownload:"yes",
						    	typeName : "<%=typeName%>"
						  },
						  async:false,
						  dataType: "json",
						  success: function( data, textStatus, jqXHR ) {
							  var openUrl= "<%=basePath%>"+"cmdbDownServlet?down_type=direct&file_path="+data.filePath;
							 // window.open(openUrl, "newwindow"); 
							  $("#downloadIframe").attr("src",openUrl);
						  }
						  
						});
					
					
				}
			});
						
		  //jQuery("#metaclass-attr").jqGrid({url: "getCiTypeProperty" }).trigger("loadGrid");

		}
	  
	  function reloadGridByPara(){
		//  jQuery("#metaclass-attr").jqGrid('setGridParam',{ 
		//	    url:"searchSanZone",
		//	    postData: {
		//	    	sanSwitchID : $("#san_select").val(),
		//	    	zoneName:$("#zoneName").val(),
		//	    	zoneConfig:$("#zoneConfig").val(),
		//	    	typeName : "<%=typeName%>"
		//		  }
		 // });
		//  jQuery("#metaclass-attr").jqGrid().trigger("reloadGrid");
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
<div style="width: 100%">
<table width="1000">
<tr>
<td>San交换机ID:</td>
<td>
<select name="san_select" id="san_select" style="width: 150px;" >	
	  <option value="">所有</option>
</select>
</td>
<td>Zone名称:</td>
<td><input type="text" name="zoneName" id="zoneName" class="text ui-widget-content ui-corner-all" /></td>
<td>Zone配置:</td>
<td><input type="text" name="zoneConfig" id="zoneConfig" class="text ui-widget-content ui-corner-all" /></td>
<td><button id="searchBut" class="ui-button ui-widget ui-state-default" style="padding-right: 0px" onclick="reloadGridByPara()">查询</button></td>
<td></td>
</tr>

</table>	
</div>
<div style="width: 100%" id="gridDivId">
<table id="metaclass-attr" style="width: 100%"></table>
			<div id="page-metaclass-attr" style="width: 100%"></div>
<iframe scrolling="no" id='downloadIframe' frameborder="0"  src="../ciMgr/relationship.jsp?typeName=<%=typeName%>" style="width:700px;height:380px;"></iframe>
</div>
</body>
</HTML>