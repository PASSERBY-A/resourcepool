<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>设备管理</title>
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
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxtabs.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxcalendar.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxnumberinput.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxdatetimeinput.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxtree.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxcombobox.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxdatetimeinput.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxexpander.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxgrid.columnsresize.js"></script>
    <script type="text/javascript" src="../resources/js/demos.js"></script>
    
    <style type="text/css">
    	.AvmonButtonAreaAuto {text-align: right;float:left;margin-left:80px}
    	.jqx-window-header {background-color:#0096d6 !important;}
    	.from-label label {float:left}
    	
    	.AvmonButtonAreaAuto {text-align: right;background-color:inherit;border:none}
    	.AvmonOverlayButton {border:none}
    	.jqx-window-header {background-color:#0096d6 !important;}
    	.jqx-expander-content {overflow: auto;}
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
            
            <%-- <div class="Profile" id="ShowUserBoxclick" style="cursor:pointer">
            <div class="ProfileIcon"></div>
            <div class="ProfileName ">${userName}</div>
            <div class="ProfileArrow"></div>
            <div class="blank0"></div>
         </div> --%>
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
             <!-- <li id="scan" class="moudleSelecter"><a href="${ctx}/discovery/deviceScan">设备扫描</a></li> -->
             <li id="scan" class="Tab_categoryAuto_active moudleSelecter"><a href="${ctx}/discovery/deviceMgr">设备管理</a></li>
            </ul>
            </div>
         </div>
       </div>

      <!-- content
      ================================================== -->       
      <div class="content">
       <script type="text/javascript">
	        $(document).ready(function () {
	            // Create jqxTabs.
	           // $('#jqxTabs').jqxTabs({ width: '100%', height: 216, position: 'top',selectionTracker:true,animationType: 'fade',collapsible:true});
	            $('#settings div').css('margin-top', '10px');
	            $("ul.jqx-tabs-title-container").css("width","1600px");
	        });
    	 </script>   

	     <!-- tabs begin -->
        
	      
	      <!-- 设备查询开始 -->
	       <script type="text/javascript">
		       $(document).ready(function () {
		            $("#deviceQuery").jqxExpander({ width: '98%',expanded: true});
			        $("#deviceScan").jqxExpander({ width: '98%',expanded: false});
			        $('#jqxTabs').jqxTabs({ width: '99.9%', position: 'top',selectionTracker:true,animationType: 'fade',collapsible:true});
		            $('#settings div').css('margin-top', '20px');
		            $(".jqx-tabs-title-container").css("width","99.9%");
		            
		            $('#eventWindow').jqxWindow({
		        		showCollapseButton: false, maxHeight: 100, maxWidth: 700, minHeight: 100, minWidth: 200, height: 398, width: 380,isModal: true, modalOpacity: 0.2,
		                initContent: function () {
		                  	$('#eventWindow').jqxWindow('close');
		                  }
		            });
		            
		            <!-- =======================================query grid begin ================================== -->
		        	var data = 
		        	[{"ip": "","authNo":"public","port":"161"}];
		        	
		        	var detailsource =
			           {
			               datatype: "json",
			               datafields: [
			                   { name: 'ip', type: 'string' },
			                   { name: 'authNo', type: 'string' },
			                   { name: 'port', type: 'string' }
			               ],
			               localdata:data,
			               datatype: "local",
			               addrow: function (rowid, rowdata, position, commit) {
			                    commit(true);
			                },
			               updaterow: function (rowid, rowdata, commit) {
			            	   commit(true);
			               }
			           };
		        	
			        	var generaterow = function () {
			                var row = {};
			                row["ip"] = "";
			                row["port"] = "161";
			                row["authNo"] = "public";
			                return row;
			            }
		 	        
			           var dataAdapter = new $.jqx.dataAdapter(detailsource);
			           $("#snmpQueryGrid").jqxGrid(
			           {
			               width: 600,
			               source: dataAdapter,
			               editable: true,
			               selectionmode: 'singlerow',
			               editmode: 'click',
			               autoheight: true,
			               showtoolbar: true,
			               rendertoolbar: function (toolbar) {
			                    var me = this;
			                    var container = $("<div style='margin: 5px;'></div>");
			                    toolbar.append(container);
			                    container.append('<input id="addrowbutton" type="button" value="Add New Row"/>');
			                    container.append('<input style="margin-left: 5px;" id="deleterowbutton" type="button" value="Delete Selected Row" />');
			                    $("#addrowbutton").jqxButton();
			                    $("#deleterowbutton").jqxButton();
			                    // create new row.
			                    $("#addrowbutton").on('click', function () {
			                        var datarow = generaterow();
			                        var commit = $("#snmpQueryGrid").jqxGrid('addrow', null, datarow);
			                    });
			                    // delete row.
			                    $("#deleterowbutton").on('click', function () {
			                        var selectedrowindex = $("#snmpQueryGrid").jqxGrid('getselectedrowindex');
			                        var rowscount = $("#snmpQueryGrid").jqxGrid('getdatainformation').rowscount;
			                        if (selectedrowindex >= 0 && selectedrowindex < rowscount) {
			                            var id = $("#snmpQueryGrid").jqxGrid('getrowid', selectedrowindex);
			                            var commit = $("#snmpQueryGrid").jqxGrid('deleterow', id);
			                        }
			                    });
			                },
			               columns: [
			                   {
			                       text: 'IP地址', datafield: 'ip'
			                   },
			                   {
			                       text: '端口号', datafield: 'port'
			                   },
			                   {
			                       text: '社区号', datafield: 'authNo'
			                   }
			               ]
			           });
		        });
	       </script>
	       
	        <div id="deviceScan" style="margin: 10px 10px 10px 10px">
	     <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
	          <h5>设备扫描</h5><input type="hidden" id="scanParams">
	          <input type="hidden" id="changeFlag">
	          <input type="hidden" id="ids">
	          <input type="hidden" id="mode">
 	     </div>
	     <!-- tabs begin -->
         
          <div id='jqxTabs'>
            <ul style="width: 600px;">
                <li style="margin-left: 10px;">SNMP</li>
            </ul>
            <div>
                <div>           
				    <div id='queryDiv' style="padding:10px;">
				        <div id="snmpQueryGrid" class="left"></div>
				        <div class="AvmonButtonAreaAuto left"  style="margin-top: 20px;">
		             		<a href="#" id="scanBtn" class="AvmonOverlayButton">开始扫描</a>
		             		<a href="#" id="enableWatch" class="AvmonOverlayButton enableWatchBtn">启用监控</a>
		      			</div> 
		      			<div class="blank0"></div>
				    </div>
	        	</div>
            </div>
        </div>
          <!-- tabs end -->
	      </div>
	       <div id='deviceQuery' style="margin: 10px 10px 10px 10px">
            <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
	          <h5>设备查询</h5>
	     	</div>
            <div>
                <div class="left">                 
	             <div class="from-label">
	             <label><span class="left paddingL30 paddingR10 LineH45">设备类型</span>
	             	<input type="hidden" id="typeIds"/>
	             	<input type="hidden" id="isQueryed" value="0"/>
	             	<div class="left" style="margin-top:10px" id="digits">
	             		<div style="border: none;" id='dropdownTypeTree'></div>
	             	</div>
	             </label>
	             <!-- IP start -->
	             <label><span class="left paddingL60 paddingR10 LineH45">IP地址</span>
	             	<div class="left" style="margin-top:10px">
	             		<input type="text" id="ip" name="ip" style="height:17px"/>
	             	</div>
	             </label>
	             <!-- IP end -->
	             <div class="blank10"></div>
	             <!-- 时间段 start -->
	             <label><span class="left paddingL30 paddingR10 LineH45">开始时间</span>
	             	<div class="left" style="margin-top:10px">
	             		<div id="from"></div>
	             	</div>
	             </label>
	             <!-- 结束时间 start -->
	             <label><span class="left paddingL45 paddingR10 LineH45">结束时间</span>
	             	<div class="left" style="margin-top:10px">
	             		<div id="to"></div>
	             	</div>
	             </label>
	             <!-- 结束时间 end -->
	             
	             <!-- 时间段end -->
	             <div class="blank10"></div>
	             <!-- 监控状态 start -->
	             <label><span class="left paddingL30 paddingR10 LineH45">监控状态</span>
	             	<div class="left" style="margin-top:10px">
	             		<div id="status"></div>
	             	</div>
	             </label>
	             <!-- 监控状态end -->
	             
	             
	             <div class="left paddingL60" style="margin: 13px  0px 0px 0px;;">
	             <a href="#" id="queryBtn" class="AvmonOverlayButton">设备查询</a><a href="#" id="enableWatch" class="AvmonOverlayButton enableWatchBtn">启用监控</a>
	        	</div>
	             </div>
	        </div>  
            </div>
        </div>
         <!-- 设备查询结束 -->
         
	      <!-- table
	      ================================================== -->        
	      <div>       
            <div id='jqxWidget' class="AvmonMain"  style="font-size: 13px; font-family: Verdana; float: left; width: 98%">
             <div id="jqxgrid"></div>
            </div>     
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
    
 <!--  <script>
	  $(document).ready(function () {
		  $(".moudleSelecter").bind('click',function(){
			  var id = $(this).attr("id");
			  $(this).addClass("Tab_category_active");
			  $(".moudleSelecter:not(#"+id+")").removeClass("Tab_category_active");
			  $("#"+id+"Div").show();
			  $(".tabDivs:not(#"+id+"Div"+")").hide();
		  });
	  })
	</script> -->
    
    <script type="text/javascript">
        $(document).ready(function () {
        	// var statusSource = ["Affogato","Americano"];
        	
        	var statusData = [{"value":"","label":"全部"},{"value":"0","label":"未监控"},{"value":"1","label":"已监控"}];
        	var statusSource={
        			datatype: "json",
                    datafields: [
                        { name: 'value' },
                        { name: 'label' }
                    ],
                    localData:statusData
        	}
        	 var statusAdapter = new $.jqx.dataAdapter(statusSource);
        	
        	$("#from").jqxDateTimeInput({ width: '180px', height: '25px'});
        	$("#from").val('');
        	$("#to").jqxDateTimeInput({ width: '180px', height: '25px'});
        	$("#to").val('');
            $("#digits").jqxDropDownButton({ width: '180px', height: '25'});
            $("#status").jqxDropDownList({ source: statusAdapter,  displayMember: "label", valueMember: "value",dropDownHeight:75, selectedIndex: 0, width: '180'});
            
            var tree = $('#dropdownTypeTree');
            var source = null;
            $.ajax({
                async: false,
                url: "${ctx}/discovery/getTypeTree",
                success: function (data, status, xhr) {
                	if(data){
                		source = jQuery.parseJSON(data);
                	}
                }
            });
            
            tree.on('expand', function (event) {
                var label = tree.jqxTree('getItem', event.args.element).label;
                var pid = tree.jqxTree('getItem', event.args.element).id;
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
                        url: "${ctx}/discovery/getTypeTree",
                        data:{parentId:pid},
                        success: function (data, status, xhr) {
                            var items = jQuery.parseJSON(data);
                            tree.jqxTree('addTo', items, $element[0]);
                            tree.jqxTree('removeItem', loaderItem.element);
                        }
                    });
                }
            });
       	
           // create data adapter.
           var dataAdapter = new $.jqx.dataAdapter(source);
           // perform Data Binding.
           dataAdapter.dataBind(); 
           var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label'}]);
           $('#dropdownTypeTree').jqxTree({ source: records, hasThreeStates: true, checkboxes: true,width: '300px'});
            
           $('#dropdownTypeTree').on('checkChange', function (event) 
        	{
        	   var item = $('#dropdownTypeTree').jqxTree('getCheckedItems');
        	   var selectedValues = "";
        	   var selectedIds = "";
        	   $.each(item,function(index,value){
        		   selectedValues+=item[index].label;
        		   selectedIds+=item[index].id;
        		   if(index < item.length-1){
        			   selectedValues+=",";
        			   selectedIds+="','";
        		   }
        	   });
               var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">' +selectedValues + '</div>';
        	   
        	   $("#digits").jqxDropDownButton('setContent', dropDownContent);
        	   $("#typeIds").val(selectedIds);
        		      
        	}); 
            
            $('#dropdownTypeTree').on('select', function (event) {
                var args = event.args;
                var item = $('#dropdownTypeTree').jqxTree('getItem', args.element);
                var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">' + item.label + '</div>';
                $("#digits").jqxDropDownButton('setContent', dropDownContent);
                $("#typeIds").val(item.id);
            });
            $("#dropdownTypeTree").jqxTree({ width: 180, height: 220});
        });
    </script>
 
 <script type="text/javascript">
        $(document).ready(function () {
            var url = "${ctx}/discovery/getDevices";
            // prepare the data
            var source =
            {
            	datatype: "json",
                datafields: [
                    { name: 'deviceId', type: 'string' },
                    { name: 'ip', type: 'string' },
                    { name: 'deviceName', type: 'string' },
                    { name: 'protocol', type: 'string' },
                    { name: 'status', type: 'string' },
					{ name: 'vender', type: 'string' },
					{ name: 'family', type: 'string' },
					{ name: 'type', type: 'string' },
					{ name: 'moTypeId', type: 'string' },
					{ name: 'desc', type: 'string' },
					{ name: 'createDt', type: 'date' },
					{ name: 'link', type: 'string' }
                ],
                async: false,
                cache: false,
                type:'POST',
                id: 'deviceId',
                url: url,
                root:"devices",
                beforeprocessing: function (data) {
                	$("#changeFlag").val("");
                	if(data.length>0){
                		source.totalrecords = data[0].totalRows;
                	}
                },
                processdata: function (data) {
                    data.type = $('#typeIds').val();
                    // data.period = $('#period').val();
                    data.from = $('#from').val();
                    data.to = $('#from').val();
                    data.status = $("#status").val();
                    data.ip = $("#ip").val();
                    
                    data.scanParams = $('#scanParams').val();
                	data.mode=$('#mode').val();//"scan";
                	data.changepageFlag = $('#changeFlag').val();
                    
                },
				sort : function(data) {
					$("#jqxgrid").jqxGrid('updatebounddata', 'sort');
				}
            };

            var dataAdapter = new $.jqx.dataAdapter(source);

            // initialize jqxGrid
            $("#jqxgrid").jqxGrid(
            {
            	width:'100%',
                source: dataAdapter,                
                pageable: true,
                virtualmode: true,
                rendergridrows: function (params) {
                    return params.data;
                },
                autoheight: true,
                sortable: true,
                altrows: true,
                enabletooltips: true,
                columnsresize: true,
                selectionmode: 'checkbox',
                columns: [
				  { text: '设备ID', columntype: 'checkbox', datafield: 'deviceId',hidden:true },
                  { text: 'IP', datafield: 'ip', cellsalign: 'left', align: 'left'},
                  { text: '设备名', datafield: 'deviceName', align: 'left', cellsalign: 'left', cellsformat: 'c2'},
                  { text: '协议', datafield: 'protocol', cellsalign: 'left',  align: 'left'},
                  { text: '状态', datafield: 'status', cellsalign: 'left', align: 'left',cellsrenderer : function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
                      if (value ==1) {
                          return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #008000;">' + '已监控' + '</span>';
                      }
                      else {
                          return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #ff0000;">' + '未监控' + '</span>';
                      }
                  }},
				  { text: '厂商', datafield: 'vender', cellsalign: 'left', align: 'left' },  
				  { text: '系列', datafield: 'family', cellsalign: 'left', align: 'left'},  
				  { text: '型号', datafield: 'type', cellsalign: 'left', align: 'left'},
				  { text: '大类', datafield: 'moTypeId', cellsalign: 'left', align: 'left' }, 
				  { text: '描述', datafield: 'desc', cellsalign: 'left', align: 'left' },
				  { text: '创建时间', datafield: 'createDt', cellsalign: 'left', align: 'left', cellsformat: 'yyyy-MM-dd HH:mm:ss' },
				  { text: '编辑', datafield: 'link', cellsalign: 'left', align: 'left',sortable:false}
                ],
                columngroups: [
                    { text: '大类', align: 'center', name: 'moTypeId' }
                ]
            });
            
            $("#jqxgrid").on("pagechanged", function (event) {
            	 $('#jqxgrid').jqxGrid('clearselection');
            });
            
            $('.enableWatchBtn').click(function () {
            	$("#ids").val("");
            	var ids = "";
        		var id = "";
        		
        		 var selectedrowindexs = $("#jqxgrid").jqxGrid('getselectedrowindexes');
                 var rowscount = $("#jqxgrid").jqxGrid('getdatainformation').rowscount;
                 if (selectedrowindexs.length > 0) {
                	 for(i=0;i<selectedrowindexs.length;i++){
                		 var id = $("#jqxgrid").jqxGrid('getrowid', selectedrowindexs[i]);
                		 if(id >= 0 ||id > '-1'){
                			 ids= ids + id;
                    		 if(i<selectedrowindexs.length - 1){
                    			 ids+="','"
                    		 }
                		 }
                	 }
                     
               	 if(ids==""){
               		 alert('请选择要启用监控的设备');
               		 return false;
               	 }else{
               		$("#ids").val(ids);
               		$('#window').jqxWindow('open');
               	 }
                 }
                 else{
                	 alert('请选择要启用监控的设备');
                 }
            });
            
            $("#queryBtn").click(function(){
				  $("#mode").val("query");
				  source.url = "${ctx}/discovery/getDevices";
				  $("#jqxgrid").jqxGrid('updatebounddata');
				  $('#jqxgrid').jqxGrid('clearselection');
             });
            
            <!-- =============================================query grid end=============================== -->
	        $("#scanBtn").click(function(){
            	   $("#scanParams").val("");
            	   $("#mode").val("scan");
            	   var queryData = [];
            	   var dates = "[";
            	   var rows = $("#snmpQueryGrid").jqxGrid('getdisplayrows');
            	   var flag = false;
            	   $.each(rows,function(i,row){
            		   dates+="{\"ip\":\""+row.ip+"\",\"authNo\":\""+row.authNo+"\"}";
            		   if(i<rows.length-1){
            			   dates+=",";
            		   }
            		   if(row.ip != "" && row.ip != null){
            			   flag = true;
            		   }
            		   //queryData.push({ip:row.ip,authNo:row.authNo});
            	   });
            	   dates+="]";
            	   if(!flag){
            		   alert("请输入扫描条件!");
            		   return false; 
            	   }else{
            		   $('#eventWindow').jqxWindow('open');
            		   $("#scanParams").val(dates);
            		   $("#changeFlag").val("1");
            		   source.url = "${ctx}/discovery/scan";
            	   }
            	   
                   $("#jqxgrid").jqxGrid('updatebounddata');
                   $('#eventWindow').jqxWindow('close');
               });
        });
    </script>  
    
        
<!--     <script type="text/javascript">
            $(document).ready(function () {                
			  $("#queryBtn").click(function(){
				  $("#mode").val("query");
				  $("#jqxgrid").jqxGrid('updatebounddata');
               });
            });
        </script> -->
        
   <!-- 监控条件设置 -->
   
   <script type="text/javascript">
        $(document).ready(function () {  
        	$("#R1").jqxRadioButton({ checked: true });
            //Initializing the demo
            $('#window').jqxWindow({
              showCollapseButton: false, maxHeight: 491, maxWidth: 700, minHeight: 200, minWidth: 200, height: 398, width: 380,isModal: true, modalOpacity: 0.3,
              initContent: function () {
              	$('#window').jqxWindow('close');
              }
          });
            
            $("#btnAddWatch").bind('click',function(){
            	if($("#snmppwd").val()==""){
            		 $(".jqx-validator-error-label").show();
            		 return false;
            	}else{
            		$(".jqx-validator-error-label").hide();
            		var ids = $("#ids").val();
                    	 
                         $.ajax({
              			   url:'${ctx}/discovery/addWatch',
  	              		   dataType:'json',
  	              		   data:{ids:ids},
  	              		   timeout:'400000',
  	              		   success:function(data){
  	              			   alert(data.result);
  	              			   // console.log(data);
  	              			   $('#window').jqxWindow('close');
  	              			  $("#jqxgrid").jqxGrid('updatebounddata');
  	              		   },
  	              		   failure:function(){
  	              			   alert('添加监控失败');
  	              			   $('#window').jqxWindow('close');
  	              		   }
              		});
                   }
            });
        });
    </script>
   
   <div id="jqxWidgetSet">
        <div style="width: 100%; height: 650px; margin-top: 50px;" id="mainDemoContainer">
            <div id="window">
                <div id="windowHeader">
                   <span class="Icon16 Icon_search"></span><span class="f14px white paddingL20">监控条件设置</span>
                </div>
                <div>
                <div style="margin:10px;"> 
                  <div class="overlay-El-line"> 
                  <div style="margin-bottom:4px;">版本</div>
                  <label class="left W50"><div id='R1'>SNMP V2</div></label>
                  </div>
                   <div class="blank10"></div>        
              
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px;">SNMP社区号</div>
                  <div><input type="text" id="snmppwd" placeholder="**********" style="width:260px;" class="jqx-validator-error-element"><span class="red VAM paddingL8"></span></div>
                  <div style="display: none;" class="jqx-validator-error-label">请输入SNMP社区号</div>                   
                  </div>
                </div>
                <div class="AvmonButtonArea">                                    
                   <div class="left"><a href="#" id="btnAddWatch"  class="AvmonOverlayButton">应用</a></div> 
                   <div class="left"><span class="gray VAM">输入框后 “*“为必填选项</span></div>  
                   <div class="blank0"></div>
                </div>
                </div>
            </div>
        </div>
    </div>
    
    <!-- 修改调度策略 begin -->
    <div style="width: 100%; height: 650px; margin-top: 50px;" id="mainDemoContainer">
            <div id="oidwindow">
                <div id="windowHeader">
                   <span class="Icon16  R-icon_1_white"></span><span class="f14px white paddingL20">OID管理</span>
                </div>
                <div>
                <div style="margin:10px;">                
          		       <div class="overlay-El-line"> 
          		          <p><span class="paddingR8">设备名称</span><span id="deviceName"></span></p> 
      					 </div>
                         <div class="blank0"><input type="hidden" id="moId"></div>    
        		         <div class="overlay-El-line">
      		     			 <div class="left LineH20 paddingR10 marginT2">OID</div>
      			   		     <div class="left LineH20 marginR20"><input type="text" id="oid" name="oid" placeholder="OID" style="width:230px; height:20px;padding:0"></div>
      			   		     <div class="left LineH20 paddingR10 marginT2">名称</div>
      			   		     <div class="left LineH20 marginR20"><input type="text" id="oidName" name="oidName" placeholder="OID名称" style="width:230px; height:20px;padding:0"></div>
      		     			 <div class="left LineH20 marginR20 marginT2"><a href="#" id="queryOidBtn" class="Avmon-button">查询</a></div>
      		     			 <div class="blank0"></div>  
    		  		     </div>
         		         <div class="blank10"></div>  
         		         <div class="overlay-El-line">
                             <div class="left LineH20 marginR10"><a href="#" id="enableBtn" class="Avmon-button">启用</a></div>
                             <div class="left LineH20 marginR10"><a href="#" id="disableBtn" class="Avmon-button">禁用</a></div>
							 <div class="left LineH20 marginR10"><a href="#" id="batchEnableBtn" class="Avmon-button">批量启用</a></div>
                             <div class="left LineH20 marginR10"><a href="#" id="batchDisableBtn" class="Avmon-button">批量禁用</a></div>
    		  		     </div>               
                </div>
                <div class="blank0"></div>                
               <div style="margin:10px;">               
                 	<div class="overlay-El-line"> 
                       <div id="jqxWidget" class="AvmonMain"  style="float: left;margin: 0px;">
                          <div id="oidgrid"></div>
                       </div>
                 	 </div>
      			  </div>
                </div>
            </div>
        </div>
        
        <script type="text/javascript">
        	var oidSource;
        	$(document).ready(function(){
        		//var url = "${ctx}/discovery/getDeviceOids?moId=";

                // prepare the data
                oidSource =
                {
                	//url: url,
                    datatype: "json",
                    datafields: [
                        { name: 'oid', type: 'string' },
                        { name: 'oidName', type: 'string' },
                        { name: 'oidIndex', type: 'string' },
                        { name: 'schedule', type: 'string' },
                        { name: 'oidGroup', type: 'string' },
                        { name: 'status', type: 'string' }
                    ],
                    async: false,
                    cache: false,
                    type:'POST',
                    id: 'oid',
                    root:'oids',
                    beforeprocessing: function (data) {
                    	$('#oidgrid').jqxGrid('clearselection');
                    	if(data.length>0){
                    		oidSource.totalrecords = data[0].totalRows;
                    	}
                    },
                    processdata: function (data) {
                        data.oid = $('#oid').val();
                        data.oidName = $('#oidName').val();
                        
                    },
    				sort : function(data) {
    					$("#oidgrid").jqxGrid('updatebounddata', 'sort');
    				}
                };

                var oidDataAdapter = new $.jqx.dataAdapter(oidSource);

                // initialize jqxGrid
                $("#oidgrid").jqxGrid(
                {
                    width: '100%',
    				height:300,
                    source: oidDataAdapter,                
                    pageable: true,
                    virtualmode: true,
                    rendergridrows: function (params) {
                        return params.data;
                    },
                    sortable: true,
                    altrows: true,
                    enabletooltips: true,
                    editable: true,
                    columnsresize: true,
                    selectionmode: 'checkbox',
                    columns: [
    				  { text: 'OID', datafield: 'oid', width: '20%',editable: false},
                      { text: '名称', datafield: 'oidName', width: '20%',editable: false },
                      { text: 'index', datafield: 'oidIndex', cellsalign: 'left', align: 'left', width: '10%' },
                      { text: '调度', datafield: 'schedule', align: 'left', cellsalign: 'left', cellsformat: 'c2', width: '18%' },
                      { text: '组', datafield: 'oidGroup', cellsalign: 'left',  align: 'left', width: '20%',editable: false },
                      { text: '启用状态', datafield: 'status', cellsalign: 'left', align: 'left', width: '12%',cellsrenderer : function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
                          if (value ==1) {
                              return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #008000;">' + '已启用' + '</span>';
                          }
                          else {
                              return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #ff0000;">' + '未启用' + '</span>';
                          }
                      },editable: false },            
                    ],
                    columngroups: [
                        { text: '组', align: 'center', name: 'oidGroup' }
                    ]
                });
        		
        		$('#oidwindow').jqxWindow({
                    showCollapseButton: true, maxHeight: 496, height: 496,isModal: true, width: 950,
                    initContent: function () {
                        $('#oidwindow').jqxWindow('close');
                    }
                });
        		
        		$("#queryOidBtn").bind('click',function(){
        			var paginginformation = $('#oidgrid').jqxGrid('getpaginginformation');
            		// The page's number.
            		var pagenum = paginginformation.pagenum;
            		console.log(pagenum);
            		if(pagenum!=0){
            			$('#oidgrid').jqxGrid('gotopage', 0);
            		}else{
            			$("#oidgrid").jqxGrid('updatebounddata');
            		}
            		if($("#oid").val()!=""||$("#oidName").val()!=""){
            			$("#isQueryed").val("1");
            		}else{
            			$("#isQueryed").val("0");
            		}
        		});
        		
        		$("#enableBtn").bind('click',function(){
        			 var ids = checkSelectedIds();
                     console.log('ids:'+ids);
                   	 if(ids==""){
                   		 alert('请选择要启用的oid!');
                   		 return false;
                   	 }else{
                   		$.ajax({
                  		   url:'${ctx}/discovery/enableDisableOid',
                  		   type:'POST',
                  		   dataType:'json',
                  		   data:{ids:ids,flag:"1",moId:$("#moId").val()},
                  		   success:function(data){
                  			  var result = data.result;
                  			  if(result=="0"){
                  				alert('启用成功');
                     			$("#oidgrid").jqxGrid('updatebounddata');
                  			  }else{
                  				alert('启用失败,请联系管理员');
                  			  }
                  		   },
                  		   failure:function(){
                  			    alert('启用失败,请联系管理员');
                  		   }
                  	   });
                   	 }
        		});
        		
				$("#disableBtn").bind('click',function(){
					var ids = checkSelectedIds();
                     // console.log('ids:'+ids);
                   	 if(ids==""){
                   		 alert('请选择要禁用的oid!');
                   		 return false;
                   	 }else{
                   		$.ajax({
                  		   url:'${ctx}/discovery/enableDisableOid',
                  		   type:'POST',
                  		   dataType:'json',
                  		   data:{ids:ids,flag:"0",moId:$("#moId").val()},
                  		   success:function(data){
                  			  var result = data.result;
                  			  if(result=="0"){
                  				 alert('禁用成功!');
                     			 $("#oidgrid").jqxGrid('updatebounddata');
                  			  }else{
                  				alert('禁用失败,请联系管理员!');
                  			  }
                  		   },
                  		   failure:function(){
                  			   alert('禁用失败!');
                  		   }
                  	   });
                   	 }
        		});
        	});
        	
        	$("#batchEnableBtn").bind('click',function(){
   			 	var isQueryed = $("#isQueryed").val();
                console.log('isQueryed:'+isQueryed);
              	 if(isQueryed!="1"){
              		 alert('请先筛选要启用的oid!');
              		 return false;
              	 }else{
              		$.ajax({
             		   url:'${ctx}/discovery/batchEnableDisableOid',
             		   type:'POST',
             		   dataType:'json',
             		   data:{flag:"1",moId:$("#moId").val()},
             		   success:function(data){
             			  var result = data.result;
             			  if(result=="0"){
             				alert('启用成功');
                			$("#oidgrid").jqxGrid('updatebounddata');
             			  }else{
             				alert('启用失败,请联系管理员');
             			  }
             		   },
             		   failure:function(){
             			    alert('启用失败,请联系管理员');
             		   }
             	   });
              	 }
   			});
        	
        	$("#batchDisableBtn").bind('click',function(){
        		var isQueryed = $("#isQueryed").val();
                console.log('isQueryed:'+isQueryed);
               	 if(isQueryed!="1"){
               		 alert('请选择要禁用的oid!');
               		 return false;
               	 }else{
               		$.ajax({
              		   url:'${ctx}/discovery/batchEnableDisableOid',
              		   type:'POST',
              		   dataType:'json',
              		   data:{flag:"0",moId:$("#moId").val()},
              		   success:function(data){
              			  var result = data.result;
              			  if(result=="0"){
              				 alert('禁用成功!');
                 			 $("#oidgrid").jqxGrid('updatebounddata');
              			  }else{
              				alert('禁用失败,请联系管理员!');
              			  }
              		   },
              		   failure:function(){
              			   alert('禁用失败!');
              		   }
              	   });
               	 }
    		});
        	
        	function checkSelectedIds(){
        		var ids = "";
    			var selectedrowindexs = $("#oidgrid").jqxGrid('getselectedrowindexes');
                var rowscount = $("#oidgrid").jqxGrid('getdatainformation').rowscount;
                if (selectedrowindexs.length > 0) {
               	for(i=0;i<selectedrowindexs.length;i++){
               		 var id = $("#oidgrid").jqxGrid('getrowid', selectedrowindexs[i]);
               		 if(id >= 0 ||id > '-1'){
               			 ids= ids + id;
                   		 if(i<selectedrowindexs.length - 1){
                   			 ids+="','"
                   		 }
               		 }
               	 }
        		}
                return ids;
        	}
        	
        	function configSchedule(deviceId,deviceName,status){
        		if(status!='1'){
        			alert('设备尚未监控,不能修改调度!');
        			return false;
        		}
        		$("#isQueryed").val("0");
        		var url = "${ctx}/discovery/getDeviceOids?moId="+deviceId;
        		oidSource.url=url;
        		$("#oidgrid").jqxGrid('updatebounddata');
        		var paginginformation = $('#oidgrid').jqxGrid('getpaginginformation');
        		// The page's number.
        		var pagenum = paginginformation.pagenum;
        		// console.log(pagenum);
        		if(pagenum!=0||pagenum!=1){
        			$('#oidgrid').jqxGrid('gotopage', 0);
        		}
        		
        		$("#deviceName").html(deviceName);
        		$("#moId").val(deviceId);        		
        		$('#oidwindow').jqxWindow('open');
        	}
        
        </script>
        
        
    <!-- 修改调度策略 end -->
    
<script src="${ctx}/pages/avmon_back_gif.js"></script>

</body>
</html>
