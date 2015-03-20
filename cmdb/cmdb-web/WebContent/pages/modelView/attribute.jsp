<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%
String typeName=(String)request.getParameter("typeName");
if(typeName == null)
{
	typeName = "Ci";
	}
String getCiTypeAttr = "getCibyType?typeName="+typeName ;
String addCiTypeAttr = "addCibyType?typeName="+typeName ;
String updateCiTypeAttr = "updateCibyType?typeName="+typeName ;
String delCiTypeAttr = "delCibyType?typeName="+typeName ;

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
<script type="text/javascript"  src="${pageContext.request.contextPath}/resources/jqueryValidation/jquery.validate.min.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/resources/jqueryValidation/additional-methods.min.js"></script>
<script type="text/javascript"  src="${pageContext.request.contextPath}/resources/jqueryValidation/messages_zh.min.js"></script>

<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>


<style type="text/css">

</style>
<script src="${pageContext.request.contextPath}/pages/modelView/js/common/common.js" type="text/javascript"></script>
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

var colModelData = []; 
	  function afterLoad(){
		  
		 // $("#tabs").tabs();
		 
		  changeWidthHeightByPara(0.99,0.4);
		  
		  $.ajax({
			  type: "POST",
			  //url: "getCitAttrbyName",
			  url: "getCitTypeAtrrByNameAll",
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
		  
		  
			jQuery("#metaclass-attr").jqGrid({
			    datatype: "json",
			    url:"<%=getCiTypeAttr%>",
			    jsonReader: { repeatitems: false },
			    rowNum: 500,
			    height: 240,
			    //width: 800,
			    shrinkToFit:true,
			    scrollOffset: 0,
			    rowNum: 50,
			    rowList: [50,100,150],
			    loadonce: true,
			    colModel:colModelData,
//			    colNames:['id','name','label'],
//			    colModel:[
//						{name:'id', width:60, hidden:false, editable:true},
//			           {name:'name', width:60, hidden:false, editable:true},
//			           {name:'label', width:140, hidden:false, align:'left', editable:true}
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
//			    ],
			    pager: "#page-metaclass-attr",
			    gridview: true,
			    viewrecords: true,
			    rownumbers: true,
			    sortable: true,
			    sortname: 'name',
			    sortorder: "asc",
			    pginput:true,
			    pagerpos:"center",
			    multiselect: true,
			    toppager:false,
			    toolbar:[true,"top"],
			    caption: "实例列表",
			    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" },
			    editurl:"saveOrUpdateCiTypeAttr",
			    onSelectRow: function (rowid, status) {
					var id = $("#metaclass-attr").jqGrid('getGridParam','selrow'); 
		    		var rowDatas = $("#metaclass-attr").jqGrid('getRowData', id); 
		    		var row = rowDatas["id"]; 
					//grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
					//$("#openXXXIframe").attr("src","../ciMgr/ciAttribute.jsp?typeName=<%=typeName%>&ciId="+row);
					//$("#openRoleDiv" ).dialog({height:450,width:750}
					//		);
                },
                gridComplete: function(){  
                    //在Grid的第一列（Actions）中添加按钮E、S、C，添加增、删、查、改按钮；  
                    var ids = jQuery("#metaclass-attr").jqGrid('getDataIDs');  
                    for(var i=0;i < ids.length;i++){  
                    	var cl = ids[i];
                    	var rowDatas = $("#metaclass-attr").jqGrid('getRowData', cl); 
    		    		var row = rowDatas["id"]; 
                          
                       // be = "<input style='height:22px;width:20px;' type='button' value='查看关系' onclick=\"jQuery('#metaclass-attr').editRow('"+cl+"');\" />";    
                       var viewAttr = "<input type='button' class='ui-button ui-widget ui-state-default' value='查看属性' onclick=\"jQuery('#openXXXIframe').attr('src','../ciMgr/ciAttribute.jsp?typeName=<%=typeName%>&ciId="+row+"');$('#openRoleDiv' ).dialog({height:450,width:750});\" />";
                        jQuery("#metaclass-attr").jqGrid('setRowData',ids[i],{op:viewAttr});  
                    }  
                }
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
			    	url:"<%=addCiTypeAttr%>",
			    	closeAfterAdd:true,
			        afterSubmit: function(response) {
			        	var res = $.parseJSON(response.responseText);
			        	var result = res.result;
			        	var returnNode = res.node;
					    if (result=='0') {
					    	alert("新增成功");
					    	reloadJqGrid();
							 var openUrl= "ciAttribute.jsp?typeName=";
							 openUrl=openUrl+"<%=typeName%>";
							 openUrl=openUrl+"&ciId=";
							 openUrl=openUrl+returnNode.id;
							//window.open(openUrl, "newwindow"); 
							$("#openXXXIframe").attr("src","../ciMgr/ciAttribute.jsp?typeName=<%=typeName%>&ciId="+returnNode.id);
							$("#openRoleDiv" ).dialog(
									);
				    		return [true,res.result,false];
					    }else{
					    	return [false,res.result,false];
					    }
			        },
			        reloadAfterSubmit:true
			    	
			    }, // add
			    {
			    	url:"delCiInfo?typeName=<%=typeName%>",
			    	position:"center",
			    	modal:true,
			    	afterSubmit: function(response) {
			        	var res = $.parseJSON(response.responseText);
			        	if(res.result==0){
			        		alert("删除成功");
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
				title: "创建信息",
				onClickButton: function() {
					var id = $("#metaclass-attr").jqGrid('getGridParam','selrow'); 
		    		var rowDatas = $("#metaclass-attr").jqGrid('getRowData', id); 
		    		var row = rowDatas["id"]; 
					//grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
					//$("#openXXXIframe").attr("src","../ciMgr/ci.jsp?typeName=<%=typeName%>");
					//$("#openRoleDiv" ).dialog({height:800,width:650});
					openCreateCiDialog();
					
//					 var openUrl= "ciAttribute.jsp?typeName=";
//					 openUrl=openUrl+"<%=typeName%>";
//					 openUrl=openUrl+"&ciId=";
//					 openUrl=openUrl+row;
//					window.open(openUrl, "newwindow"); 
				}
			});
			
			//添加查找关系列表
			grid.jqGrid('navButtonAdd', '#page-metaclass-attr',{
				caption: "查看关系", 
				title: "查看关系",
		        buttonicon:"ui-icon-key",
				onClickButton: function() {
					var id = $("#metaclass-attr").jqGrid('getGridParam','selrow'); 
		    		var rowDatas = $("#metaclass-attr").jqGrid('getRowData', id); 
		    		var row = rowDatas["id"]; 
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
		    		$("#relationViewWindowFrame").attr("src","../ciMgr/relationView.jsp?typeName=<%=typeName%>&id="+id);
		    		$("#relationViewWindow" ).dialog({height:550,width:950,modal:true});
					//grid.jqGrid('columnChooser');
					//$( "#attrEdit-form" ).dialog( "open" );
					//$("#openXXXIframe").attr("src","../ciMgr/ci.jsp?typeName=<%=typeName%>");
					//$("#openRoleDiv" ).dialog({height:800,width:650});
					//openCreateCiDialog();
					
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
				buttonicon:"ui-icon-arrowthickstop-1-s",
			//	position:"last",
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
					
					 var openUrl= "<%=basePath%>"+"cmdbDownServlet?down_type=source&node_type=<%=typeName%>";
					window.open(openUrl, "newwindow"); 
				}
			});
			
		  //jQuery("#metaclass-attr").jqGrid({url: "<%=getCiTypeAttr%>" }).trigger("loadGrid");
		
			//chagedownloadUrl();
		}
	  
	  function chagedownloadUrl(){
		  
		 // var url = "<%=basePath%>"+"cmdbDownServlet?down_type=source&node_type=<%=typeName%>&node_column="+node_columnString;
		 var url = "<%=basePath%>"+"cmdbDownServlet?down_type=source&node_type=<%=typeName%>";
		  $("#downloadUrl").attr("href",url);
	  }
	  
	//下载数据
	  function downloadDataSource(){
		  alert( $("#downloadUrl").attr("href"));
	  }
	
	
	function openCreateCiDialog(){
		
		//configTableSource();
		
		//createTabPage();
		
		$("#openRoleDiv2" ).dialog(
				  {
					  title:"创建资源信息",
					  height:580,
					  width:540,
					  position:"center",
					  modal:true,
					  buttons:{
						  '确定':function(){
							  if ($("#createAttrForm").validate(
									  ).form() == true) { //手动验证
							       submitCiInfo();
							       gTab = new Array();
								   $("#createAttrForm").empty();
	                            }
							 
							  
						  },
						  '取消':function(){
							  jQuery("#metaclass-attr").jqGrid().trigger("relodGrid");
							  gTab = new Array();
							  $("#createAttrForm").empty();
							  $(this).dialog("close");
						  }
					  },
					  close:function(){
						  gTab = new Array();
						  $("#createAttrForm").empty();
					  },
					  open:function(){
						  createTabPage();
					  }
					  }
		  
		  );
		
	}
	
	function configJsonItem(){
		var attrJson = "";
		var keyString = "";
		var valueString = "";
		$("#openRoleDiv2").find("input").each(function(){
			var nameLabe = $(this).attr("name");
			var valueLabel = $(this).val();
			if(nameLabe != "name" && nameLabe != "label" && nameLabe != "domain" && valueLabel != "" && valueLabel != null){
				keyString +=nameLabe+","
				valueString+=valueLabel+",";
			}
			//attrJson += "'"+name+"':"+"'"+value+"'"+",";
			//alert($(this).attr("name")+"-----"+$(this).val());
			
		});
		attrJson = keyString.substr(0,keyString.length-1)+"|"+valueString.substr(0,valueString.length-1);
		return attrJson;
	}
	
	
	function submitCiInfo(){
		var attribute = configJsonItem();
		$.ajax({
			  type: "POST",
			  url: "addCiInfoBytype",
			  data: {
			    label:$("#label1").val(),
			    name: $("#name1").val(),
			    domain:$("#domain").val(),
			    typeName : "<%=typeName%>",
			    attribute:attribute
			  },
			  async:false,
			  dataType: "json",
			  success: function( data, textStatus, jqXHR ) {
				  var message = data.success;
				  var result = data.result;
				  alert(message);
				  reloadJqGrid();
				  jQuery("#metaclass-attr").jqGrid().trigger("reloadGrid");
				  $("#openRoleDiv2" ).dialog("close");
				  if(result=='0'){
					  
				  }else{
					  alert(message);
				  }
			  }
			  
			});
	}
	
	//存储group与tab的对应
	var gTab = new Array();
	//判断当前群组是否已经存在
	
	function judgeGroup(groupName){
		var retu = true;
		var temp = 0;
		for(var i=0;i< gTab.length;i++){
			if(gTab[i].group != groupName){
				temp ++;
			}
		}
		if(temp == gTab.length){
			retu = false
		}
		return retu;
	}
	
	//获取tabbody id
	function getTabbodyId(groupName){
		var tabId = "";
		for(var i=0;i< gTab.length;i++){
			if(gTab[i].group == groupName){
				tabId = gTab[i].tabId
				break;
			}
		}
		return tabId;
	}
	
	
	function createTabPage(){
		var tabDiv = $("<div id='tabs' style='width: 100%;height: 100%'></div>");
		//动态创建ui组件
		var ul = $("<ul></ul>");
		var idL = 0;
		$(colModelData).each(function(){ 
			
			// alert(this.name+" "+this.value+" "+this.hidden); 
			//alert(this.isRequired);
			var groupT = (typeof(this.group) == "undefined") || this.group == null || this.group == ""?"未分组":this.group;
              
            var isExitGroup = judgeGroup(groupT);
            
            if(isExitGroup){
            	var tabId = getTabbodyId(groupT);
            	//添加当前输入组件
      			 if(this.label != "id" && this.name != "op" && !this.hidden){
      				 var row = $("<tr class='FormData'></tr>"); 
      				 var tdName = $("<td class='CaptionTD'></td>");
      				 var tdValue = $("<td class='DataTD'>&nbsp;</td>");
      				 tdName.append(this.label);
      				 var vaTemp = (this.value == null || this.value == "")?"":this.value;
      				 var vaReq = this.isRequired == true?"required":"";
      				 tdValue.append($("<input type='text' name="+this.name+" id="+this.name+" value='"+vaTemp+"' class='"+vaReq+" FormElement ui-widget-content ui-corner-all' />")); 
      				 row.append(tdName);
      				 row.append(tdValue);
      				 $("#"+tabId).append(row);
      			 }
            }else{
            	idL = idL+1;
            	//添加li组件
    			var liG = $("<li><a href='#tabs-"+idL+"'>"+groupT+"</a></li>");
    			ul.append(liG);
    			
    			//添加div组件
    			var uiDiv = $("<div id='tabs-"+idL+"'></div>");
    			var uitaBle = $("<table class='EditTable' cellspacing='0' cellpadding='0' border='0'></table>");
    			var tbody = $("<tbody id='"+"fieldsetTable"+idL+"'></tbody>");
    		  //添加当前输入组件
   			 if(this.label != "id" && this.name != "op" && !this.hidden){
   				 var row = $("<tr class='FormData'></tr>"); 
   				 var tdName = $("<td class='CaptionTD'></td>");
   				 var tdValue = $("<td class='DataTD'>&nbsp;</td>");
   				 tdName.append(this.label);
   				 var vaTemp = (this.value == null || this.value == "")?"":this.value;
   				 var vaReq = this.isRequired == true?"required":"";
   				 tdValue.append($("<input type='text' name="+this.name+" id="+this.name+" value='"+vaTemp+"' class='"+vaReq+" FormElement ui-widget-content ui-corner-all' />")); 
   				 row.append(tdName);
   				 row.append(tdValue);
   				 tbody.append(row);
   				 
   				var obj = new Object();
   	   			obj.group = groupT;
   	   			obj.tabId = "fieldsetTable"+idL;
   	   			gTab.push(obj);
   			 }
   			
   			uitaBle.append(tbody);
   			//表格添加到tab div中
   			uiDiv.append(uitaBle);
   			//添加tab到div中
   			tabDiv.prepend(ul);
   			tabDiv.append(uiDiv);
   			$("#createAttrForm").append(tabDiv);
            }
			 
		});
		//添加固定三个元素
		addThreeRow(getTabbodyId("未分组"))
		$("#tabs").tabs();
	}
	
	function addThreeRow(tabId){
		//先自动添加三个属性
		 var row1 = $("<tr class='FormData'></tr>"); 
		 var tdName1 = $("<td class='CaptionTD'></td>");
		 var tdValue1 = $("<td class='DataTD'>&nbsp;</td>");
		 tdName1.append("显示名称*");
		 tdValue1.append($("<input type='text' name='label1' id='label1' class='required FormElement ui-widget-content ui-corner-all' />"));
		 row1.append(tdName1);
		 row1.append(tdValue1);
		 $("#"+tabId).append(row1);
		 
		 var row2 = $("<tr class='FormData'></tr>"); 
		 var tdName2 = $("<td class='CaptionTD'></td>");
		 var tdValue2 = $("<td class='DataTD'>&nbsp;</td>");
		 tdName2.append("CI名称*");
		 tdValue2.append($("<input type='text' name='name1' id='name1' class='required FormElement ui-widget-content ui-corner-all' />")); 
		 row2.append(tdName2);
		 row2.append(tdValue2);
		 $("#"+tabId).append(row2);
		 
		 
		 var row3 = $("<tr class='FormData'></tr>"); 
		 var tdName3 = $("<td class='CaptionTD'></td>");
		 var tdValue3 = $("<td class='DataTD'>&nbsp;</td>");
		 tdName3.append("数据域*");
		 tdValue3.append($("<input type='text' name='domain' id='domain' class='required FormElement ui-widget-content ui-corner-all' />")); 
		 row3.append(tdName3);
		 row3.append(tdValue3);
		 $("#"+tabId).append(row3);
		
	}
	//遍历数据
		 function configTableSource(){

			 var table1 = $('#fieldsetTable');
			 //先自动添加三个属性
			 var row1 = $("<tr class='FormData'></tr>"); 
			 var tdName1 = $("<td class='CaptionTD'></td>");
			 var tdValue1 = $("<td class='DataTD'>&nbsp;</td>");
			 tdName1.append("显示名称*");
			 tdValue1.append($("<input type='text' name='label1' id='label1' class='required FormElement ui-widget-content ui-corner-all' />"));
			 row1.append(tdName1);
			 row1.append(tdValue1);
			 table1.append(row1);
			 
			 var row2 = $("<tr class='FormData'></tr>"); 
			 var tdName2 = $("<td class='CaptionTD'></td>");
			 var tdValue2 = $("<td class='DataTD'>&nbsp;</td>");
			 tdName2.append("CI名称*");
			 tdValue2.append($("<input type='text' name='name1' id='name1' class='required FormElement ui-widget-content ui-corner-all' />")); 
			 row2.append(tdName2);
			 row2.append(tdValue2);
			 table1.append(row2);
			 
			 
			 var row3 = $("<tr class='FormData'></tr>"); 
			 var tdName3 = $("<td class='CaptionTD'></td>");
			 var tdValue3 = $("<td class='DataTD'>&nbsp;</td>");
			 tdName3.append("数据域*");
			 tdValue3.append($("<input type='text' name='domain' id='domain' class='required FormElement ui-widget-content ui-corner-all' />")); 
			 row3.append(tdName3);
			 row3.append(tdValue3);
			 table1.append(row3);
			 
			 $(colModelData).each(function(){ 

				// alert(this.name+" "+this.value+" "+this.hidden); 
				//alert(this.isRequired);
				 if(this.label != "id" && this.name != "op" && !this.hidden){
					 var row = $("<tr class='FormData'></tr>"); 
					 var tdName = $("<td class='CaptionTD'></td>");
					 var tdValue = $("<td class='DataTD'>&nbsp;</td>");
					 tdName.append(this.label);
					 var vaTemp = (this.value == null || this.value == "")?"":this.value;
					 var vaReq = this.isRequired == true?"required":"";
					 tdValue.append($("<input type='text' name="+this.name+" id="+this.name+" value='"+vaTemp+"' class='"+vaReq+" FormElement ui-widget-content ui-corner-all' />")); 
					 row.append(tdName);
					 row.append(tdValue);
					 table1.append(row);
				 }
				 
			});
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
<style type="text/css">
.ui-layout-center {
	background-color: #435b87;
	padding: 0px 8px 0px 0px;
}
.ui-tabs .ui-tabs-panel {
	background-color: rgb(252, 253, 253);
	height:100%;
}
</style>

<body onLoad="afterLoad();">

<div style="width: 100%" id="gridDivId">
<table id="metaclass-attr" style="width: 100%"></table>
			<div id="page-metaclass-attr" style="width: 100%"></div>
</div>

			
			<!-- 
			<table id="metaclass-attr" width = "100%"></table>
			<div id="page-metaclass-attr" width = "100%"></div>
			<input type="button" value="下载数据" onclick="downloadDataSource()" style="padding-right: 0">
			<a id="downloadUrl" href="http://127.0.0.1:8080/cmdb-web/cmdbDownServlet?down_type=view&view_name=view_fabric_54&node_type=Host">下载</a>
			 -->
			
			
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
<div id="openRoleDiv2" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="" style="width:100%;height:100%;padding-top: 24px;overflow: hidden; top: 4px; left: 4px;" dir="ltr" tabindex="-1" role="dialog" aria-labelledby="edithdmetaclass-attr">
      <form method="post" id="createAttrForm" action="">
      <!-- 
      <table class="EditTable" cellspacing="0" cellpadding="0" border="0">
               <tbody id="fieldsetTable">
               </tbody>
               </table>
       -->
               
<!-- 
<iframe scrolling="no" id='openXXXIframe' frameborder="0"  src="../ciMgr/relationship.jsp?typeName=<%=typeName%>" style="width:600px;height:730px;"></iframe>
 -->
     </form>
</div>	

<div id="openRoleDiv" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="查询属性" style="width:100%;height:100%;padding-top: 24px;overflow: hidden; top: 4px; left: 4px;">
<iframe scrolling="no" id='openXXXIframe' frameborder="0"  src="../ciMgr/relationship.jsp?typeName=<%=typeName%>" style="width:700px;height:380px;"></iframe>
</div>			
<div id="alertWindow" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="" style="width:100%;height:100%;padding-top: 24px;vertical-align: middle;">
    <label>请选择行！</label>
</div>

<div id="relationViewWindow" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="查询关系" style="width:100%;height:100%;padding-top: 24px;overflow: hidden; top: 4px; left: 4px;">
<iframe scrolling="no" id="relationViewWindowFrame"  frameborder="0"  src="../ciMgr/relationship.jsp?typeName=<%=typeName%>" style="width:100%;height:100%;"></iframe>
</div>

</body></HTML>