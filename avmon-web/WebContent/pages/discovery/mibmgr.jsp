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
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxinput.js"></script>
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
    <script type="text/javascript" src="../resources/js/jqwidgets/jqxgrid.columnsresize.js"></script>
    <script type="text/javascript" src="../resources/js/demos.js"></script>
    
    <style type="text/css">
    	.jqx-window-header {background-color:#0096d6 !important;}
    	.widget-title {height: 36px !important;}
    	.widget-title h5 {padding: 12px;}
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
            
            <div class="MainTitle f28px LineH12">简单网络管理</div>
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
             <li id="mib" class="moudleSelecter"><a href="#">MIB管理</a></li> 
                 <ul class="Tab_categoryAuto_sub">
                    <li class="Tab_categoryAuto_active moudleSelecter"><a href="${ctx}/discovery/mibmgr">MIB管理</a></li>
                    <li class="moudleSelecter"><a href="${ctx}/discovery/mibimport">MIB导入</a></li>              
                 </ul>
             <li id="type" class="moudleSelecter"><a href="${ctx}/discovery/motypeconfig">设备类型管理</a></li>
             <li id="mib" class="moudleSelecter"><a href="#">TRAP管理</a></li> 
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
		  <!-- mib管理 -->
		  <div id="mibDiv" class="tabDivs">
		  <div class="widget-box"> 
	     
	     	<div class="widget-title"> <span class="icon"> <i class="icon-align-justify"></i> </span>
	          <h5>MIB管理</h5>
	     	</div>
	     
	        <div class="from-group">                 
	             <div class="from-label">
	             <span class="left paddingR10 LineH45">设备类型</span><div class="left marginR25" style="margin-top:10px" id="digits">
	             		<div style="border: none;" id='dropdownTypeTree'></div>
	             	</div>
	             <span class="left paddingR10 LineH45">OID</span><div class="left marginR25" style="margin-top:10px"><input type="text" id='oid' style="width:160px;"/>
	             	</div>
             	<span class="left paddingR10 LineH45">文件名</span><div class="left marginR25" style="margin-top:10px"><input type="text" id='mibfile' style="width:160px;"/>
             	</div>
	             </div>
	        </div>  
	        
	        <div class="from-group">                 
	             <div class="from-label">
	             <label></label>	
	             <div class="blank10"><input type="hidden" id="model"></div>
	             </div>
	        </div> 
	        <div>
	        <div class="AvmonButtonAreaAuto">
	             <a href="#" id="queryBtn" class="AvmonOverlayButton">查询</a><a href="#" id="deleteBtn" class="AvmonOverlayButton">删除</a>

	        </div>
	        </div> 
	         <div class="blank0"></div>
	      </div>
		  
		  <div style="display: block;">
	            <div class="AvmonMain"  style="font-size: 13px; font-family: Verdana; float: left;">
	             <div id="mibGrid"></div>
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
    
    <script type="text/javascript">
        $(document).ready(function () {
            $("#digits").jqxDropDownButton({ width: 180, height: 25});
            
            var tree = $('#dropdownTypeTree');
            var source = null;
            $.ajax({
                async: false,
                url: "${ctx}/discovery/getTypeTree",
                success: function (data, status, xhr) {
                	if(data){
                		source = jQuery.parseJSON(data);
                	}
                	// console.log(data);
                }
            });
            // tree.jqxTree({ source: source,  height: 300, width: 200 });
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
           $('#dropdownTypeTree').jqxTree({ source: records, width: '300'});
            
           $('#dropdownTypeTree').on('checkChange', function (event) 
        	{
        	   var item = $('#dropdownTypeTree').jqxTree('getCheckedItems');
        	   var selectedValues = "";
        	   $.each(item,function(index,value){
        		   selectedValues+=item[index].id+",";
        	   });
               var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">' +selectedValues + '</div>';
        	   
        	   $("#digits").jqxDropDownButton('setContent', dropDownContent);
        		      
        	}); 
            
            $('#dropdownTypeTree').on('select', function (event) {
                var args = event.args;
                var item = $('#dropdownTypeTree').jqxTree('getItem', args.element);
                var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">' + item.id + '</div>';
                $("#digits").jqxDropDownButton('setContent', dropDownContent);
            });
            $("#dropdownTypeTree").jqxTree({ width: 180, height: 220});
        });
    </script>
    
 <script type="text/javascript">
        $(document).ready(function () {
            //MIBGRID
             var mibUrl = "${ctx}/discovery/getOids";

            // prepare the data
            var mibSource =
            {
            	url: mibUrl,
            	datatype: "json",
                datafields: [
					{ name: 'oid', type: 'string' },
					{ name: 'mibfileName', type: 'string' },
                    { name: 'deviceType', type: 'string' },
                    { name: 'deviceName', type: 'string' },
                    { name: 'oidType', type: 'string' },
                    { name: 'oidName', type: 'string' },
                    { name: 'oidGroup', type: 'string' },
					{ name: 'schedule', type: 'string' },
					{ name: 'ocdt', type: 'date' },
					{ name: 'oidDesc', type: 'string' }
                ],
                async: false,
                cache: false,
                type:'POST',
                id: 'oid',
                root:'oids',
                beforeprocessing: function (data) {
                	if(data.length>0){
                		mibSource.totalrecords = data[0].totalRows;
                	}
                },
	            processdata: function (data) {
	                data.type = $('#digits').val();
	                data.oid = $('#oid').val();
	                data.mibfile = $("#mibfile").val();
	            },
                updaterow: function (rowid, rowdata, commit) {
	            	   var oidGroup = rowdata.oidGroup;
	            	   var schedule = rowdata.schedule;
	            	   var mibfile = rowdata.mibfileName;
	            	   var deviceType = rowdata.deviceType;
	            	   var oid =  rowdata.oid;
	            	   var model = $("#model").val();
	            	   var flag = false;
	            	   $.ajax({
	                        url: "${ctx}/discovery/updateMibOid",
	                        data:{group:oidGroup,schedule:schedule,oid:oid,mibfile:mibfile,deviceType:deviceType,model:model},
	                        success: function (data, status, xhr) {
	                            var items = jQuery.parseJSON(data);
	                            if(items.result=='1'){
	                            	alert('修改失败,请重试');
	                            }else{
	                            	alert("修改成功!");
	                            	commit(true);
	                            	$("#mibGrid").jqxGrid('updatebounddata');
	                            }
	                        }
	                    });
	               },
				sort : function(data) {
					$("#mibGrid").jqxGrid('updatebounddata', 'sort');
				}
            };

             var mibDataAdapter = new $.jqx.dataAdapter(mibSource);

            // initialize MibGrid
            $("#mibGrid").jqxGrid(
            {
                width: '100%',
                source: mibDataAdapter,
                pageable: true,
                virtualmode: true,
                rendergridrows: function (params) {
                    return params.data;
                },
                autoheight: true,
                sortable: true,
                altrows: true,
                enabletooltips: true,
                editable: true,
                columnsresize: true,
                selectionmode: 'checkbox',
                columns: [
				  { text: 'OID', datafield: 'oid',cellsalign: 'left', align: 'left', width: '10%',editable:false  },
				  { text: 'MIB文件', datafield: 'mibfileName', cellsalign: 'left', align: 'left', width: '10%',editable:false },
                  { text: '设备类型', datafield: 'deviceType', cellsalign: 'left', align: 'left', width: '10%', columntype: 'combobox',
                      initeditor: function (row, cellvalue, editor, celltext, cellwidth, cellheight) {
                          var value = editor.val();
                          var comboxSource =
                          {
                              datatype: "json",
                              datafields: [
                                   { name: 'label', type: 'string' },
                                   { name: 'value', type: 'string' }
                              ],
                              url:"${ctx}/discovery/getTypeCombox",
                          };
                          var comboxDataAdapter = new $.jqx.dataAdapter(comboxSource);
                          
                          editor.jqxComboBox({ displayMember: "label", valueMember: "value",autoDropDownHeight:false, source: comboxDataAdapter});
                      },
                      // update the editor's value before saving it.
                      cellvaluechanging: function (row, column, columntype, oldvalue, newvalue) {
                    	  if(oldvalue!=newvalue && newvalue !=""){
                    		  $('#model').val('1');
                    	  }else{
                    		  $('#model').val('0');
                    		  return oldvalue;
                    	  }
                      }
				  },
                  { text: '设备名称', datafield: 'deviceName', align: 'left', cellsalign: 'left', cellsformat: 'c2', width: '12%',editable:false },
                  { text: 'OID类型', datafield: 'oidType', cellsalign: 'left',  align: 'left', width: '8%',editable:false },
                  { text: 'OID名称', datafield: 'oidName', cellsalign: 'left', align: 'left', width: '12%',editable:false },
                  { text: '组', datafield: 'oidGroup', cellsalign: 'left', align: 'left', width: '12%',editable:false },
				  { text: '调度周期', datafield: 'schedule', cellsalign: 'left', align: 'left', width: '12%',columntype: 'textbox',
                      // update the editor's value before saving it.
                      cellvaluechanging: function (row, column, columntype, oldvalue, newvalue) {
                    	  if(oldvalue!=newvalue){
                    		  $('#model').val('0');
                    	  }else{
                    		  return oldvalue;
                    	  }
                      }},  
				  { text: '创建时间', datafield: 'ocdt', cellsalign: 'left', align: 'left', width: '12%',cellsformat: 'S',editable:false },
				  { text: 'OID描述', datafield: 'oidDesc', cellsalign: 'left', align: 'left', width: '18%',editable:false }                       
                ],
                columngroups: [
                    { text: '大类', align: 'center', name: 'oidType' }
                ]
            });
        });
    </script>  
        
    <script type="text/javascript">
            $(document).ready(function () {
			  $("#queryBtn").click(function(){
				  // $('#mibGrid').jqxGrid('gotopage', 0);
				  $("#mibGrid").jqxGrid('updatebounddata');
				  $('#mibGrid').jqxGrid('gotopage', 0);
               });
			  
			  $("#deleteBtn").click(function(){
				   var ids ="";
				   var selectedrowindexs = $("#mibGrid").jqxGrid('getselectedrowindexes');
                     var rowscount = $("#mibGrid").jqxGrid('getdatainformation').rowscount;
                     if (selectedrowindexs.length > 0) {
                    	 for(i=0;i<selectedrowindexs.length;i++){
                    		 var id = $("#mibGrid").jqxGrid('getrowid', selectedrowindexs[i]);
                    		 if(id >= 0 ||id > '-1'){
                    			 ids= ids + id;
                        		 if(i<selectedrowindexs.length - 1){
                        			 ids+="','"
                        		 }
                    		 }
                    	 }
                     }
                         
                    	 if(ids==""||ids.length<= 0){
                    		 alert('添加监控失败,请刷新后重试!');
                    		 return false;
                    	 }
                    	 
                         $.ajax({
              			   url:'${ctx}/discovery/deleteOids',
  	              		   dataType:'json',
  	              		   data:{ids:ids},
  	              		   timeout:'30000',
  	              		   success:function(data){
  	              			   console.log(data.result);
  	              			   if(data.result=="0"){
  	              				  alert('删除成功');
  	  	              			  $("#mibGrid").jqxGrid('updatebounddata');
  	              			   }else{
  	              					alert(data.result);
  	              			   }
  	              		   },
  	              		   failure:function(){
  	              			   alert('删除失败');
  	              		   }
              			});
               });
			  
			  
				$("#oid").jqxInput({ width: '160px', height: '25px'}); 
            
			  });
            
            
          //liqiang modify 20150113
            $('#ShowUserBoxclick').hide();
        </script>

<script src="${ctx}/pages/avmon_back_gif.js"></script>

</body>
</html>
