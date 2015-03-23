<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>自动发现</title>
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
    	.AvmonButtonAreaAuto {text-align: right;}
    	.jqx-window-header {background-color:#0096d6 !important;}
    </style>
</head>

<body>

   <div id="wrap">
   
      <!-- Header
      ================================================== -->
      <div id="top" class="top">
         <div class="fixed">
         <!--   <a href="Login.html"><div class="LOGO"></div></a> -->
            <a href="../main"><div class="Avmon_back"></div></a>
            <div class="MainTitle f28px LineH12">自动发现</div>
           <%--  <div class="Profile" id="ShowUserBoxclick" style="cursor:pointer">
            <div class="ProfileIcon"></div>
            <div class="ProfileName ">${userName}</div>
            <div class="ProfileArrow"></div>
            <div class="blank0"></div> --%>
         </div>
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
             <li id="scan" class="Tab_categoryAuto_active moudleSelecter"><a href="${ctx}/discovery/index">设备扫描</a></li>
             <li id="mib" class="moudleSelecter"><a href="#">MIB管理</a></li> 
                 <ul class="Tab_categoryAuto_sub">
                    <li><a href="${ctx}/discovery/mibmgr">MIB管理</a></li>
                    <li><a href="${ctx}/discovery/mibimport">MIB导入</a></li>              
                 </ul>
             <li id="type" class="moudleSelecter"><a href="${ctx}/discovery/motypeconfig">设备类型管理</a></li>
             <li id="trap" class="moudleSelecter"><a href="${ctx}/discovery/trapmgr">TRAP管理</a></li>   
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
	            $('#jqxTabs').jqxTabs({ width: '100%', height: 216, position: 'top',selectionTracker:true,animationType: 'fade',collapsible:true});
	            $('#settings div').css('margin-top', '10px');
	            $("ul.jqx-tabs-title-container").css("width","1600px");
	        });
    	 </script>   
      <!-- NavAvmon
      ================================================== -->             
         <div id="scanDiv" class="tabDivs">     
	     <div id="deviceScan" style="margin: 10px 10px 10px 10px">
	     <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
	          <h5>设备扫描</h5>
	     </div>
	     <!-- tabs begin -->
         
          <div id='jqxTabs'>
            <ul style="width: 800px;">
                <li style="margin-left: 10px;">SNMP</li>
                <li>Other</li>
            </ul>
            <div>
                <div class="from-group">           
	             <div class="from-label">
	             
		              <!-- =======================================query grid begin ================================== -->
		              
		              <script type="text/javascript">
					        $(document).ready(function () {
					        	var data = 
					        	[{"ip": "请输入IP...","authNo":"请输入社区号...","port":"请输入端口号...","version":"请输入版本号..."}];
					        	
					        	var detailsource =
						           {
						               datatype: "json",
						               datafields: [
						                   { name: 'ip', type: 'string' },
						                   { name: 'authNo', type: 'string' },
						                   { name: 'port', type: 'string' },
						                   { name: 'version', type: 'string' }
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
						                row["ip"] = "请输入IP...";
						                row["port"] = "请输入端口号...";
						                row["authNo"] = "请输入社区号...";
						                row["version"] = "请输入版本号...";
						                return row;
						            }
					 	        
						           var dataAdapter = new $.jqx.dataAdapter(detailsource);
						           $("#snmpQueryGrid").jqxGrid(
						           {
						               width: 700,
						               source: dataAdapter,
						               editable: true,
						               selectionmode: 'singlerow',
						               editmode: 'selectedrow',
						               autoheight: true,
						               showtoolbar: true,
						               rendertoolbar: function (toolbar) {
						                    var me = this;
						                    var container = $("<div style='margin: 5px;'></div>");
						                    toolbar.append(container);
						                    container.append('<input id="addrowbutton" type="button" value="Add New Row" />');
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
						                   },
						                   {
						                       text: '版本号', datafield: 'version'
						                   }
						                   
						               ]
						           });
					        });
					    </script>
					    
					    <div id='queryDiv'>
					        <div id="snmpQueryGrid" style="float: left;"></div>
					    </div>
		              <!-- =============================================query grid end=============================== -->
		              
	             </div>
	          	 <div class="blank20"></div>
	          	 <div class="AvmonButtonAreaAuto">
			             <a href="#" id="scanBtn" class="AvmonOverlayButton">开始扫描</a>
			      </div> 
	        	</div>
            </div>
            <div>
                Other type
            </div>
        </div>
          <!-- tabs end -->
	      </div>
	      
	      <!-- 设备查询开始 -->
	       <script type="text/javascript">
		       $(document).ready(function () {
		            $("#deviceQuery").jqxExpander({ width: '98%',expanded: true});
		            $("#deviceScan").jqxExpander({ width: '98%',expanded: false});
		        });
	       </script>
	       <div id='deviceQuery' style="margin: 10px 10px 10px 10px">
            <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
	          <h5>设备查询</h5>
	     	</div>
            <div>
                <div class="from-group">                 
	             <div class="from-label">
	             <label><span class="left paddingL50 paddingR40 LineH40">设备类型</span>
	             	<input type="hidden" id="typeIds"/>
	             	<div class="left" style="margin-top:10px" id="digits">
	             		<div style="border: none;" id='dropdownTypeTree'></div>
	             	</div>
	             </label>
	             <!-- 时间段 start -->
	             <label><span class="left paddingL50 paddingR40 LineH40">开始时间</span>
	             	<div class="left" style="margin-top:10px">
	             		<div id="period"></div>
	             	</div>
	             </label>
	             <!-- 时间段end -->
	             <!-- 监控状态 start -->
	             <label><span class="left paddingL50 paddingR40 LineH40">监控状态</span>
	             	<div class="left" style="margin-top:10px">
	             		<div id="status"></div>
	             	</div>
	             </label>
	             <!-- 监控状态end -->
	             </div>
	        </div>  
	        <div class="AvmonButtonAreaAuto">
	             <a href="#" id="queryBtn" class="AvmonOverlayButton">设备查询</a><a href="#" id="enableWatch" class="AvmonOverlayButton">启用监控</a>
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
        	
        	$("#period").jqxDateTimeInput({ width: '200px', height: '25px',selectionMode: 'range', max: new Date(),formatString: 'yyyy/MM/dd'});
            $("#digits").jqxDropDownButton({ width: 200, height: 25});
            $("#status").jqxDropDownList({ source: statusAdapter,  displayMember: "label", valueMember: "value",dropDownHeight:50, selectedIndex: 0, width: '200', height: '25'});
            
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
           // get the tree items. The first parameter is the item's id. The second parameter is the parent item's id. The 'items' parameter represents 
           // the sub items collection name. Each jqxTree item has a 'label' property, but in the JSON data, we have a 'text' field. The last parameter 
           // specifies the mapping between the 'text' and 'label' fields.  
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
            });
            $("#dropdownTypeTree").jqxTree({ width: 200, height: 220});
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
					{ name: 'createDt', type: 'date' }
                ],
                async: false,
                cache: false,
                type:'POST',
                id: 'deviceId',
                url: url,
                root:"devices",
                beforeprocessing: function (data) {
                	if(data.length>0){
                		source.totalrecords = data[0].totalRows;
                	}
                },
                processdata: function (data) {
                    data.type = $('#typeIds').val();
                    data.period = $('#period').val();
                    data.status = $("#status").val();
                },
				sort : function(data) {
					// update the grid and send a request to the server.
					// $('#jqxgrid').jqxGrid('gotopage', 0);
					$("#jqxgrid").jqxGrid('updatebounddata', 'sort');
					//$("#jqxgrid").jqxGrid('updatebounddata');
				}
            };

            var dataAdapter = new $.jqx.dataAdapter(source);

            // initialize jqxGrid
            $("#jqxgrid").jqxGrid(
            {
            	width:1000,
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
				  { text: '创建时间', datafield: 'createDt', cellsalign: 'left', align: 'left', cellsformat: 'S' }
                ],
                columngroups: [
                    { text: '大类', align: 'center', name: 'moTypeId' }
                ]
            });
            
        });
    </script>  
    
        
    <script type="text/javascript">
            $(document).ready(function () {                
               $("#scanBtn").click(function(){
            	   var queryData = [];
            	   var dates = "[";
            	   var rows = $("#snmpQueryGrid").jqxGrid('getdisplayrows');
            	   $.each(rows,function(i,row){
            		   dates+="{\"ip\":\""+row.ip+"\",\"authNo\":\""+row.authNo+"\"}";
            		   if(i<rows.length-1){
            			   dates+=",";
            		   }
            		   //queryData.push({ip:row.ip,authNo:row.authNo});
            	   });
            	   dates+="]";
            	   
            	   createElements();
                   $("#jqxWidgetWin").css('visibility', 'visible');
            	   
            	   $.ajax({
            		   url:'${ctx}/discovery/scan',
            		   type:'POST',
            		   dataType:'json',
            		   data:{queryParams:dates},
            		   timeout:'400000',
            		   success:function(data){
            			   // console.log(data);
            			   $('#jqxWidgetWin').jqxWindow('close');
            		   },
            		   failure:function(){
            			   alert('扫描失败');
            			   $('#jqxWidgetWin').jqxWindow('close');
            		   }
            	   });
               });
               
               
			  $("#queryBtn").click(function(){
				  $("#jqxgrid").jqxGrid('updatebounddata');
               });
            });
            
            function createElements() {
                $('#eventWindow').jqxWindow({
                    maxHeight: 150, maxWidth: 280, minHeight: 30, minWidth: 250, height: 145, width: 270,
                    resizable: false, isModal: true, modalOpacity: 0.3,
                    okButton: $('#ok'), cancelButton: $('#cancel'),
                    initContent: function () {
                        $('#ok').jqxButton({ width: '65px' });
                        $('#cancel').jqxButton({ width: '65px' });
                        $('#ok').focus();
                    }
                });
                $('#events').jqxPanel({ height: '250px', width: '450px' });
            }
        </script>
        

 <!-- 模态框 ================================================== -->  
    <div style="visibility: hidden;" id="jqxWidgetWin">
        <div style="width: 100%; height: 650px; border: 0px solid #ccc; margin-top: 10px;"
            id="mainDemoContainer">
            <div id="events" style="width: 300px; height: 200px; border-width: 0px;">
            </div>
            <div id="eventWindow">
                <div>Modal Window</div>
                <div>
                    <div>正在扫描请稍候.......</div>
                    <div>
                    <div style="float: right; margin-top: 15px;">
                        <input type="button" id="ok" value="OK" style="margin-right: 10px" />
                        <input type="button" id="cancel" value="Cancel" />
                    </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
   
   <!-- 监控条件设置 -->
   
   <script type="text/javascript">
        var basicDemo = (function () {
            //Adding event listeners
            function _addEventListeners() {
                $('#enableWatch').click(function () {
                    $('#window').jqxWindow('open');
                });
            };

            //Creating the demo window
            function _createWindow() {
                $('#window').jqxWindow({
                    showCollapseButton: false, maxHeight: 491, maxWidth: 700, minHeight: 200, minWidth: 200, height: 398, width: 380,isModal: true, modalOpacity: 0.3,
                    initContent: function () {
                        // $('#window').jqxWindow('focus');
                    	$('#window').jqxWindow('close');
                    }
                });
            };

            return {
                config: {
                    dragArea: null
                },
                init: function () {
                    //Attaching event listeners
                    _addEventListeners();
                    //Adding jqxWindow
                    _createWindow();
                }
            };
        } ());

        $(document).ready(function () {  
        	$("#R1").jqxRadioButton({ checked: true });
            $("#R2").jqxRadioButton({ checked: false });
            $("#R1").bind('change', function (event) {
                var checked = event.args.checked;
                if(!checked){
                	
                }
                alert('R1 checked: ' + checked);
            });
            //Initializing the demo
            basicDemo.init();
            
            
            $("#btnAddWatch").bind('click',function(){
            	if($("#snmppwd").val()==""){
            		 $(".jqx-validator-error-label").show();
            		 return false;
            	}else{
            		$(".jqx-validator-error-label").hide();
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
                    		 alert('添加监控失败,请刷新后重试!');
                    		 return false;
                    	 }
                    	 
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
                         // var commit = $("#jqxgrid").jqxGrid('deleterow', id);
                     }
            		
            		
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
                  <label class="left W50"><div id='R2'>SNMP V3</div></label>
                  </div>
                   <div class="blank10"></div>        
              
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px;">SNMP密码</div>
                  <div><input type="text" id="snmppwd" placeholder="**********" style="width:260px;" class="jqx-validator-error-element"><span class="red VAM paddingL8"></span></div>
                  <div style="display: none;" class="jqx-validator-error-label">请输入SNMP密码</div>                   
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
</body>
</html>
