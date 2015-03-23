<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>简单网络管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=9">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->
   <link rel="stylesheet" type="text/css"  href="../resources/css/reset.css">
   <link rel="stylesheet" type="text/css"  href="../resources/css/global.css">
   <link rel="stylesheet" type="text/css" href="../resources/css/Main.css">
   <link rel="stylesheet" type="text/css" href="../resources/css/AutoSearch.css">
   <link rel="shortcut icon" href="../resources/images/favicon.ico">
   <link href="../resources/css/jquery.mCustomScrollbar.css" rel="stylesheet" type="text/css" />

  <!-- jqwidgets -->
    <link rel="stylesheet" href="../resources/js/jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="../resources/js/jquery-1.10.0.min.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxdata.js"></script> 
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxradiobutton.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxdropdownbutton.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxgrid.sort.js"></script> 
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxgrid.pager.js"></script> 
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxgrid.edit.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxwindow.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxpanel.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxtree.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxcombobox.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="../resources/js/demos.js"></script>
    
    
<style type="text/css">
.widget-title {height: 36px !important;}
.widget-title h5 {padding: 12px;}
#panelContentpaneltypeTreeDiv{border-color: #f9f9f9;background: #f9f9f9;}
</style> 
    
    
</head>


<body>

   <div id="wrap">
   
      <!-- Header
      ================================================== -->
      <div id="top" class="top">
         <div class="fixed">
			
			<!-- <a href="../main"><div class="LOGO"></div></a> -->
            <a href="../main">
            	<div class="Avmon_back_gif">
            		<img id="Avmon_back_img" src="${ctx}/resources/images/back.png" 
						onmouseover="mouseover('${ctx}');" onmouseout="mouseout('${ctx}');">
            	</div>
            </a>
            
            <div class="MainTitle f28px LineH12">自动发现</div>
            
            <!-- <div class="Profile" id="ShowUserBoxclick" style="cursor:pointer">
            <div class="ProfileIcon"></div>
            <div class="ProfileName ">My Account</div>
            <div class="ProfileArrow"></div>
            <div class="blank0"></div>
         </div> -->
      </div>

      <!-- showUserBox
      ================================================== -->
     <div id="ShowUserBoxMain" style="display:none">
        <div class="ShowUserBoxArrow"></div>
        <div class="ShowUserBox">
          <ul>
            <li id="changePwd"><a href="#"><span class="AccountSettingIcon"></span><span >修改密码</span></a></li>
			<li><a href="${ctx}/logout"><span class="SignOutIcon"></span><span>退出</span></a></li>
            <div class="blank0"></div>
          </ul>
        </div>
      </div>

      </div>
  
      <!-- Content container
      ================================================== --> 
      <div id="container">
      <!-- Sidebar
      ================================================== --> 
	  <div id="sidebarAuto">
         <div>
					<div class="Tab_categoryAuto left">
						<ul>
							<li id="mib" class="moudleSelecter"><a href="${ctx}/discovery/mibmgr">MIB管理</a></li>
							<ul class="Tab_categoryAuto_sub">
								<li class="moudleSelecter"><a
									href="${ctx}/discovery/mibmgr">MIB管理</a></li>
								<li class="moudleSelecter"><a
									href="${ctx}/discovery/mibimport">MIB导入</a></li>
							</ul>
							<li id="type" class="Tab_categoryAuto_active moudleSelecter"><a
								href="${ctx}/discovery/motypeconfig">设备类型管理</a></li>
							<li id="mib" class="moudleSelecter"><a href="${ctx}/discovery/trapmgr">TRAP管理</a></li> 
                 <ul class="Tab_categoryAuto_sub">
                    <li><a href="${ctx}/discovery/trapmgr">告警规则配置</a></li>
                    <li><a href="${ctx}/trap/traprulemgr">规则管理</a></li>              
                 </ul>
						</ul>
					</div>
				</div>
       </div>

      <!-- content
      ================================================== -->       
      <div class="content">
      
      
      <!-- NavAvmon
      ================================================== -->  
      
      
      
      
      <div class="widget-box"> 
     
     <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
          <h5>设备扫描</h5>
        </div>
     
     
       <div class="from-group" style="background-color:#f9f9f9;margin-left:0px;">           
         
         
         
                 
		      	<!-- type moudle -->
		      	<div id="typeDiv" class="tabDivs" style="display: block;">
		            <div class="AvmonMain"  style="font-size: 13px; font-family: Verdana; float: left;">
		             <div id="typeTreeDiv" style="float: left;border-color:#f9f9f9;background: #f9f9f9;"></div>
		             <div id='jqxMenu' style="border-color:#f9f9f9;background: #f9f9f9;">
			            <ul>
			                <li>Add Item</li>
			                <li>Remove Item</li>
			            </ul>
			         </div>
		             <div id="typePropertiesGrid" style="float: left;"></div>
		            </div>
		      	</div>
		      	         
         
         
          <div class="blank0"></div>
            
        </div>
             
        <div class="AvmonButtonAreaAuto">
        
        </div> 
                
      </div>
   		      	
		      	
		      	
          </div>
      </div>
   </div>
   
   
   <script type="text/javascript">
   function show(oEvent){
     var UserBox=document.getElementById("ShowUserBoxMain");
     //alert(obj.style.display);
     if(UserBox.style.display=="block"){
     	  document.getElementById("ShowUserBoxMain").style.display = "none";
     	}else{
     		document.getElementById("ShowUserBoxMain").style.display = "block";
     	}
     
     e = window.event || oEvent;
     if (e.stopPropagation)
     {
         e.stopPropagation();
     }else{
         e.cancelBubble = true;
     }
   }
   function hide(){
     document.getElementById("ShowUserBoxMain").style.display = "none";
   }
   
   window.onload = function(){
   	
	  document.getElementById("ShowUserBoxMain").style.display = "none";
      document.body.onclick = hide;
	  document.getElementById("ShowUserBoxclick").onclick = show;
	  
   };
  </script>
    
  <script>
	  $(document).ready(function () {
		  $(".moudleSelecter").bind('click',function(){
			  var id = $(this).attr("id");
			  $(this).addClass("Tab_category_active");
			  $(".moudleSelecter:not(#"+id+")").removeClass("Tab_category_active");
			  $("#"+id+"Div").show();
			  $(".tabDivs:not(#"+id+"Div"+")").hide();
		  });
	  })
	</script>
    <script type="text/javascript">
            $(document).ready(function () {
            	 var tree = $('#typeTreeDiv');
                 var source = null;
                 $.ajax({
                     async: false,
                     url: "${ctx}/discovery/getTypeTree",
                     success: function (data, status, xhr) {
                         source = jQuery.parseJSON(data);
                     }
                 });
                 tree.on('expand', function (event) {
                     var label = tree.jqxTree('getItem', event.args.element).label;
                     var $element = $(event.args.element);
                     var loader = false;
                     var loaderItem = null;
                     var children = $element.find('ul:first').children();
                     $.each(children, function () {
                         var item = tree.jqxTree('getItem', this);
                         if (item && item.label == 'Loading...') {
                             loaderItem = item;
                             loader = true;
                             return false
                         };
                     });
                     if (loader) {
                         $.ajax({
                             url: loaderItem.value,
                             success: function (data, status, xhr) {
                                 var items = jQuery.parseJSON(data);
                                 tree.jqxTree('addTo', items, $element[0]);
                                 tree.jqxTree('removeItem', loaderItem.element);
                             }
                         });
                     }
                 });
                 
                 tree.on('select', function (event) {
                	 var args = event.args;
                	 $("#typePropertiesGrid").jqxGrid('updatebounddata', 'cells');
                 });
            	
                // create data adapter.
                var dataAdapter = new $.jqx.dataAdapter(source);
                // perform Data Binding.
                dataAdapter.dataBind(); 
                // get the tree items. The first parameter is the item's id. The second parameter is the parent item's id. The 'items' parameter represents 
                // the sub items collection name. Each jqxTree item has a 'label' property, but in the JSON data, we have a 'text' field. The last parameter 
                // specifies the mapping between the 'text' and 'label' fields.  
                var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label'}]);
                $('#typeTreeDiv').jqxTree({ source: records, width: '15%'});
                var contextMenu = $("#jqxMenu").jqxMenu({ width: '120px',  height: '56px', autoOpenPopup: false, mode: 'popup' });
                
                var clickedItem = null;
                
                var attachContextMenu = function () {
                    // open the context menu when the user presses the mouse right button.
                    $("#typeTreeDiv li").on('mousedown', function (event) {
                    	
                        var target = $(event.target).parents('li:first')[0];
                        var rightClick = isRightClick(event);
                        if (rightClick && target != null) {
                            $("#typeTreeDiv").jqxTree('selectItem', target);
                            var scrollTop = $(window).scrollTop();
                            var scrollLeft = $(window).scrollLeft();
                            contextMenu.jqxMenu('open', parseInt(event.clientX) + 5 + scrollLeft, parseInt(event.clientY) + 5 + scrollTop);
                            return false;
                        }
                    });
                }
                attachContextMenu();
                $("#jqxMenu").on('itemclick', function (event) {
                    var item = $.trim($(event.args).text());
                    switch (item) {
                        case "Add Item":
                            var selectedItem = $('#typeTreeDiv').jqxTree('selectedItem');
                            if (selectedItem != null) {
                            	$('#parentId').val(selectedItem.id);
                            	$('#customWindow').jqxWindow('open');
                            	attachContextMenu();
                            }
                            break;
                        case "Remove Item":
                            var selectedItem = $('#typeTreeDiv').jqxTree('selectedItem');
                            if (selectedItem != null) {
                            	$('#eventWindow').jqxWindow('open');
                                attachContextMenu();
                            }
                            break;
                    }
                });
                // disable the default browser's context menu.
                $(document).on('contextmenu', function (e) {
                    if ($(e.target).parents('.jqx-tree').length > 0) {
                        return false;
                    }
                    return true;
                });
                function isRightClick(event) {
                    var rightclick;
                    if (!event) var event = window.event;
                    if (event.which) rightclick = (event.which == 3);
                    else if (event.button) rightclick = (event.button == 2);
                    return rightclick;
                }
                
                
                var customButtonsDemo = (function () {
                    function _addEventListeners() {
                        _addSearchInputEventHandlers();
                        _addSaveBtnHandeler();
                    };
                    function _addSearchInputEventHandlers() {
                        $('#caption').keydown(function () {
                            _searchButtonHandle();
                        });
                        $('#caption').change(function () {
                            _searchButtonHandle();
                        });
                        $('#caption').keyup(function () {
                            _searchButtonHandle();
                        });
                        $(document).mousemove(function () {
                            _searchButtonHandle();
                        });
                    };
                    
                    function _addSaveBtnHandeler(){
                    	$('#saveBtn').click(function(){
                    		//ajax 请求
                        	 $.ajax({
                                 url:"${ctx}/discovery/saveDeviceType",
                                 data:{parentId:$("#parentId").val(),typeId:$("#typeId").val(),caption:$("#caption").val()},
                                 success: function (data, status, xhr) {
                                     var items = jQuery.parseJSON(data);
                                     var selectedItem = $('#typeTreeDiv').jqxTree('selectedItem');
                                     if(items.result=="success"){
                                    	 $('#customWindow').jqxWindow('close');
                                    	 $('#typeTreeDiv').jqxTree('addTo', { id:$("#typeId").val(),label: $("#caption").val() }, selectedItem.element);
                                    	 attachContextMenu();
                                     }else{
                                    	 alert(items.msg);
                                     }
                                 }
                             });
                    	});
                    }
                    function _searchButtonHandle() {
                        if ($('#saveBtn').length > 0) {
                            if ($.trim($('#typeId').val()) != '' && $.trim($('#caption').val()) != '') {
                                $('#saveBtn').jqxButton('disabled', false);
                            } else {
                                $('#saveBtn').jqxButton('disabled', true);
                            }
                        }
                    };
                    function _createElements() {
                        $('#customWindow').jqxWindow({  width: 300, isModal: true ,autoOpen: false,
                            height: 180, resizable: false,
                            cancelButton: $('#cancelButton'),
                            initContent: function () {
                                $('#saveBtn').jqxButton({ width: '80px', disabled: true });
                                $('#cancelButton').jqxButton({ width: '80px', disabled: false });
                            }
                        });
                    };
                    return {
                        init: function () {
                            _createElements();
                            _addEventListeners();
                        }
                    };
                } ());
                
               customButtonsDemo.init();
               
               
               //====================================删除提示框========================================
               function createElements() {
   	            $('#eventWindow').jqxWindow({
   	                maxHeight: 150, maxWidth: 280, minHeight: 30, minWidth: 250, height: 100, width: 270,autoOpen: false,
   	                resizable: false, isModal: true, modalOpacity: 0.3,
   	                okButton: $('#ok'), cancelButton: $('#cancel'),
   	                initContent: function () {
   	                    $('#ok').jqxButton({ width: '65px' });
   	                    $('#cancel').jqxButton({ width: '65px' });
   	                    $('#ok').focus();
   	                }
   	            });
   	            $('#events').jqxPanel({ height: '200px', width: '450px' });
   	      	}
   	      	
   	      	// 删除类型
   	      	function addEventListeners(){
   	      		$('#ok').click(function(){
   	      			$('#eventWindow').jqxWindow('close');
   	      			var selectedItem = $('#typeTreeDiv').jqxTree('selectedItem');
   	      			$.ajax({
   	                     async: false,
   	                     data:{typeId:selectedItem.id},
   	                     url: "${ctx}/discovery/deleteType",
   	                     success: function (data, status, xhr) {
   	                         source = jQuery.parseJSON(data);
   	                         if(source.result=="success"){
   	                        	 alert(source.msg);
   	                        	 $('#typeTreeDiv').jqxTree('removeItem', selectedItem.element);
   	                        	 $('#typeTreeDiv').jqxTree('refresh');
   	                        	 attachContextMenu();
   	                        	$("#typePropertiesGrid").jqxGrid('updatebounddata', 'cells');
   	                         }else{
   	                        	 alert(source.msg);
   	                         }
   	                     },
   	                     failure:function(){
   	                    	 alert('操作超时,请重试!');
   	                     }
   	                 });
   	      		});
   	      	}
         	
 	        addEventListeners();
 	        createElements();
                
 	        //=============================================details===================================
	 	       var detailsource =
	           {
	               datatype: "json",
	               datafields: [
	                   { name: 'name', type: 'string' },
	                   { name: 'value', type: 'string' }
	               ],
	               async: false,
	               url:"${ctx}/discovery/getTypeDetail",
	               processdata: function (data) {
	               		var selectedItem = $('#typeTreeDiv').jqxTree('selectedItem');
	               	 	if (selectedItem != null) {
	               			data.typeId = selectedItem.id;
	               		}
	               },
	               updaterow: function (rowid, rowdata, commit) {
	            	   var typeId = $('#typeTreeDiv').jqxTree('selectedItem').id;
	            	   var name = rowdata.name;
	            	   var value = rowdata.value;
	            	   var flag = false;
	            	   $.ajax({
	            		    async:false,
	            		    type: "GET",
	                    	url:"${ctx}/discovery/updateTypeDetail",
	                    	data:{name:name,value:value,typeId:typeId},
	                    	dataType:"json",
	                    	success:function(data,status,xhr){
	                    		if(data.result=="true"||data.result==true){
	                    			flag = true;
	                    		}else{
	                    			flag = false;
	                    		}
	                    	}
	                    });
	            	   commit(flag);
	               }
	           };
 	        
	 	      	var cellbeginedit = function (row, datafield, columntype, value) {
	                if (row < 3){ 
	                	return false;
	            	}
	            }
	 	      	
	            var cellsrenderer = function (row, column, value, defaultHtml) {
	                if (row < 3) {
	                    var element = $(defaultHtml);
	                    element.css('color', '#999');
	                    return element[0].outerHTML;
	                }
	                return defaultHtml;
	            }
	            
	           var dataAdapter = new $.jqx.dataAdapter(detailsource);
	           $("#typePropertiesGrid").jqxGrid(
	           {
	               width: '84%',
	               source: dataAdapter,
	               selectionmode: 'singlecell',
	               editable: true,
	               autoheight: true,
	               columns: [
	                   {
	                       text: 'name', datafield: 'name', width: '40%',editable:false
	                   },
	                   {
	                       text: 'value', datafield: 'value', width: '60%', columntype: 'combobox',
	                       initeditor: function (row, cellvalue, editor, celltext, cellwidth, cellheight) {
	                           var name = $('#typePropertiesGrid').jqxGrid('getcellvalue', row, "name");
	                           var selectedItem = $('#typeTreeDiv').jqxTree('selectedItem');
	                           
	                           var value = editor.val();
	                           
	                           var comboxSource =
	                           {
	                               datatype: "json",
	                               datafields: [
                                        { name: 'label', type: 'string' },
                                        { name: 'value', type: 'string' }
	                               ],
	                               url:"${ctx}/discovery/getDetailCombox",
	                               data:{name:name,typeId:selectedItem.id}
	                           };
	                           var comboxDataAdapter = new $.jqx.dataAdapter(comboxSource);
	                           
	                           editor.jqxComboBox({ autoDropDownHeight: true, source: comboxDataAdapter });
	                       },cellbeginedit: cellbeginedit, cellsrenderer: cellsrenderer
	                   }
	               ]
	           });
            });
            
    </script>
   		<div id="mainDemoContainer" style="visibility: hidden;">
            <div id="customWindow">
                <div id="customWindowHeader">
                    <span id="captureContainer" style="float: left">监控类型 </span>
                </div>
                <div id="customWindowContent" style="overflow: hidden">
                <input type="hidden" id="parentId">
                    <div style="margin: 10px">
                    	<label for="typeId">类型ID</label>
                        <input type="text" placeholder="类型ID" style="width: 175px; border: 1px solid #aaa" id="typeId" /><br/><br/>
                        <label for="caption">类型名</label>
                        <input type="text" placeholder="类型名" style="width: 175px; border: 1px solid #aaa" id="caption" />
                        <br />
                        <br />
                	</div>
                	<div style="float: right">
                            <input type="button"  value="保存" style="margin-bottom: 5px;" id="saveBtn" />
                            <input type="button" value="取消" id="cancelButton" />
                    </div>
            </div>
        </div>
      	</div>
      	
      	<!-- 确认框 -->
      	<div id="events" style="width: 300px; height: 200px; border-width: 0px;"></div>
      	<div id="eventWindow">
                <div>Modal Window</div>
                <div>
                    <div style="text-align: center;vertical-align: middle;">确认要删除该类型吗?</div>
                    <div>
                    <div style="float: right; margin-top: 15px;">
                        <input type="button" id="ok" value="OK" style="margin-right: 10px" />
                        <input type="button" id="cancel" value="Cancel" />
                    </div>
                    </div>
                </div>
            </div>
      	  
<script src="${ctx}/pages/avmon_back_gif.js"></script>

</body>
</html>
