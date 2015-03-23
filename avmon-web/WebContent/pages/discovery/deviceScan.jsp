<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta charset="utf-8">
<title>自动发现</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxtree.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxcombobox.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxdatetimeinput.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxexpander.js"></script>
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxgrid.columnsresize.js"></script>
    <script type="text/javascript" src="../resources/js/demos.js"></script>
    
    <style type="text/css">
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
             <!-- <a href="../main"><div class="LOGO"></div></a>   -->
            
            <a href="../main">
            	<div class="Avmon_back_gif">
            		<img id="Avmon_back_img" src="${ctx}/resources/images/back.png" 
						onmouseover="mouseover('${ctx}');" onmouseout="mouseout('${ctx}');">
            	</div>
            </a>
            
            <div class="MainTitle f28px LineH12">自动发现</div>
            <div class="Profile" id="ShowUserBoxclick" style="cursor:pointer">
            <div class="ProfileIcon"></div>
            <div class="ProfileName ">${userName}</div>
            <div class="ProfileArrow"></div>
            <div class="blank0"></div>
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
             <li id="scan" class="Tab_categoryAuto_active moudleSelecter"><a href="${ctx}/discovery/deviceScan">设备扫描</a></li>
             <li id="scan" class="moudleSelecter"><a href="${ctx}/discovery/deviceMgr">设备管理</a></li>
            </ul>
            </div>
         </div>
       </div>

      <!-- content
      ================================================== -->       
      <div class="content">
      <!-- NavAvmon
      ================================================== -->             
         <div id="scanDiv" class="tabDivs" style="width:100%;">     
	     <div id="deviceScan" style="margin: 10px 10px 10px 10px">
	     <div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
	          <h5>设备扫描</h5><input type="hidden" id="scanParams"><input type="hidden" id="changeFlag">
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
		      			</div> 
		      			<div class="blank0"></div>
				    </div>
	        	</div>
            </div>
        </div>
          <!-- tabs end -->
	      </div>
		 </div>

	      <!-- table
	      ================================================== -->        
	      <div style="margin: 10px 10px 10px 0px;width: 98%">       
            <div id='jqxWidget' class="AvmonMain"  style="width:100%;">
             <div id="jqxgrid"></div>
            </div>     
		  </div>
		  
		  <div class="blank0"></div>
          </div>
      </div>
   </div>
    <!-- 模态框 ================================================== -->  
    <div id="eventWindow">
        <div style="width: 100%; height: 650px; margin-top: 50px;" id="mainDemoContainer">
            <div id="jqxWidgetSet">
                <div id="windowHeader">
                   <span class="Icon16 Icon_search"></span><span class="f14px white paddingL20">设备扫描</span>
                </div>
                <div>
                    <div>正在扫描请稍候.......</div>
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
    
 
 <script type="text/javascript">
        $(document).ready(function () {
        	$("#deviceScan").jqxExpander({ width: '98%',expanded: true});
        	 // Create jqxTabs.
            $('#jqxTabs').jqxTabs({ width: '99.9%', position: 'top',selectionTracker:true,animationType: 'fade',collapsible:true});
            $('#settings div').css('margin-top', '20px');
            $(".jqx-tabs-title-container").css("width","99.9%");
            
            $('#eventWindow').jqxWindow({
        		showCollapseButton: false, maxHeight: 100, maxWidth: 700, minHeight: 100, minWidth: 200, height: 398, width: 380,isModal: false, modalOpacity: 0.2,
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
	           
        <!-- =============================================query grid end=============================== -->
            var url = "${ctx}/discovery/scan";
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
                	$("#changeFlag").val("");
               		//$('#eventWindow').jqxWindow('close');
                	if(data.length>0){
                		source.totalrecords = data[0].totalRows;
                	}
                },
                processdata: function (data) {
                	data.scanParams = $('#scanParams').val();
                	data.mode="scan";
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
                rendered: function (type) {
                    if (type == "full") {
                    	$('#eventWindow').jqxWindow('close');
                    }
                },
                selectionmode: 'singlerow',
                columns: [
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
				  { text: '创建时间', datafield: 'createDt', cellsalign: 'left', align: 'left', cellsformat: 'yyyy-MM-dd HH:mm:ss' }
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
            	   $("#scanParams").val();
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
            	   }
                   $("#jqxgrid").jqxGrid('updatebounddata');
                   
               });
               
            });
            
            //liqiang modify 20150113
            $('#ShowUserBoxclick').hide();
            
        </script>

<script src="${ctx}/pages/avmon_back_gif.js"></script>

</body>
</html>
