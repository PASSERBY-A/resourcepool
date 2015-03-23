<html>
<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<head>
<meta charset="utf-8">
<title>简单网络管理_TRAP规则管理</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta http-equiv="X-UA-Compatible" content="IE=9">
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
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery-1.10.0.min.js"></script>
	<script type="text/javascript"
	       src="${ctx}/resources/js/jquery.toastmessage.js"></script>	
	<script type="text/javascript"
		src="${ctx}/resources/js/jqwidgets/globalization/globalize.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jqwidgets/globalization/globalize.culture.zh-CN.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jqwidgets/jqx-all.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.blockUI.js"></script>
	<script type="text/javascript"
	    src="${ctx}/resources/js/common.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery.bxslider.min.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/excanvas.js"></script>
	<script type="text/javascript" src="${ctx}/resources/js/jquery.easy-pie-chart.js"></script>

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
            <li id="changePwd"><a href="#"><span class="AccountSettingIcon"></span><span>修改密码</span></a></li>
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
<%--              <li id="scan" class="moudleSelecter"><a href="${ctx}/discovery/deviceScan">设备扫描</a></li> --%>
             <li id="mib" class="moudleSelecter"><a href="#">MIB管理</a></li> 
                 <ul class="Tab_categoryAuto_sub">
                    <li><a href="${ctx}/discovery/mibmgr">MIB管理</a></li>
                    <li><a href="${ctx}/discovery/mibimport">MIB导入</a></li>              
                 </ul>
             <li id="type" class="moudleSelecter"><a href="${ctx}/discovery/motypeconfig">设备类型管理</a></li>
             
             <li id="mib" class="moudleSelecter"><a href="#">TRAP管理</a></li> 
                 <ul class="Tab_categoryAuto_sub">
                    <li><a href="${ctx}/discovery/trapmgr">告警规则配置</a></li>
                    <li class="Tab_categoryAuto_active moudleSelecter"><a href="${ctx}/trap/traprulemgr">规则管理</a></li>              
                 </ul>
<%--              <li id="trap" class="moudleSelecter"><a href="${ctx}/discovery/trapmgr">TRAP管理</a></li>  --%>
<%--              <li id="trap" class="Tab_categoryAuto_active"><a href="${ctx}/trap/traprulemgr">TRAP规则管理</a></li>     --%>
             </ul>
             </div>
             
             
             </div>
          
          </div>
      

      <!-- content
      ================================================== -->       
          <div class="content">
         <div class="AutoSearchMainNav"><a href="#" title="Go to Home"><i class="icon-home"></i> TRAP规则管理</a></div>
              <div class="NavResource">
        <div class="left marginR8">
            <div class="left LineH30 paddingR10" hidden="true">是否配置规则</div>
            <div class="left marginR20" id='jqxWidget'  hidden="true"></div>
            <div class="left LineH30 marginR20"><input id="oidValue" type="text" placeholder="输入oid" style="width:195px; height:28px;"></div>
            <div id="trapQuery" class="left LineH30 marginR20"><a href="#"  class="Avmon-button">查询</a></div>
            <div id="addRule" class="left LineH30 marginR20"><a href="#"  class="Avmon-button">添加</a></div>
            <div id="deleteRule" class="left LineH30 marginR20"><a href="#"  class="Avmon-button">删除</a></div>
            <div id="startRule" class="left LineH30 marginR20"><a href="#"  class="Avmon-button">启用</a></div>
            <div id="stopRule" class="left LineH30 marginR20"><a href="#"  class="Avmon-button">停用</a></div>
        </div>
      </div>
               
      <!-- table
      ================================================== -->        
            <div class="AvmonMain"  style="font-size: 13px; font-family: Verdana; float: left;">
             <div id="trapGrid">
             </div>
             <div class="blank10"></div>
            </div>   



<!-- 规则设置窗口 -->
		<div style="width: 100%; height: 650px; margin-top: 50px;" id="mainDemoContainer">
            <div id="window">
                <div id="windowHeader">
                   <span class="Icon16  R-icon_1_white"></span><span class="f14px white paddingL20">规则设置</span>
                </div>
                
                <div>
                
                
                <div style="margin:10px;">                
                
                    
                <div class="blank0"></div>               
                
                 <div style="margin:10px;">                
                
                    
                <div class="blank0"></div>               
                
                 <div class="overlay-El-line"> 
                       <div id="jqxWidget1" class="AvmonMain"  style="float: left;margin: 0px;">
                          <div id="configGrid">
                          </div>
                       </div>
                  </div>
                
                </div>
                
                </div>
                <div class="blank0"></div>                
                
                <div class="AvmonButtonArea" style="margin-top:0px;">
                
                   <div id="saveButton" class="left"><a href="#" class="AvmonOverlayButton">保存</a></div>  
                   <div class="blank0"></div>
                   
                </div>
                
                  <div style="margin:10px;">               
                 <div class="overlay-El-line"> 
                 <p>OID参考</p>
                       <div id="jqxWidget2" class="AvmonMain"  style="float: left;margin: 0px;">
                          <div id="oidValueGrid">
                          </div>
                       </div>
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
            

            
        });
    </script>  
 
<script type="text/javascript">
            $(document).ready(function () {                
                var source = [
                    "全部",
                    "已配",
                    "未配",
		        ];

                // Create a jqxDropDownList
                //$("#jqxWidget").jqxDropDownList({ source: source, selectedIndex: 2, width: '100', height: '25'});
            });
        </script> 
 

<script type="text/javascript">
//trap main js ==================================================================================================================================================================
var editUrl = "";
var oidUrl = "${ctx}/trap/oidList?trapKey=";
var oidSource = {
    datatype: "json",
    datafields: [{
        name: 'oid'
    },
    {
        name: 'oidValue'
    }],
    url: oidUrl,
    async: false
};
var oidDataAdapter = new $.jqx.dataAdapter(oidSource);

var oidList = "";
var oidValueObj = {};
var oidValueSourceArr;
var oidComboBoxList;
var trapKey = "";

function editClick(row) {
	oidComboBoxList = new Array;
	oidValueSourceArr = new Array;
	oidList = ";";
	trapKey = "";
	
    var rowData = $("#trapGrid").jqxGrid('getrowdata', row);
    trapKey = rowData.trap_key;
    oidSource.url = oidUrl + trapKey;
    oidDataAdapter.dataBind();
    
    $.each(oidDataAdapter.recordids,function(n,value) {
    	oidComboBoxList[oidComboBoxList.length] = "{" + value.oid + "}";
    	oidValueSourceArr[oidValueSourceArr.length] = ["{" + value.oid + "}",value.oidValue];
    	oidList = oidList+value.oid+";";
    });
    
    //若是未配置规则的trap则初始化规则grid数据
    if(rowData.flag==0){
    	configGridSource.localdata = [["AlarmTitle",""],["AlarmContent",""],["AlarmTime",""],["AlarmGrade",""],["AlarmType",""]];
    	configGridDataAdapter.dataBind();
    }
    //若是已配置规则的trap则需要加载规则grid数据
    if(rowData.flag==1){
    	
    	$.getJSON("${ctx}/trap/getOidRule?trapKey=" + trapKey,
        function(data) {
    		configGridSource.localdata = [["AlarmTitle",data.alarmTitle],["AlarmContent",data.alarmContent],["AlarmTime",data.alarmTime],["AlarmGrade",data.alarmGrade],["AlarmType",data.alarmType]];
        	configGridDataAdapter.dataBind();
        });
    	
    }
    
    oidValueSource.localdata = oidValueSourceArr;
	oidValueDataAdapter.dataBind();
    
    $('#window').jqxWindow('open');
}

var configGridSource =
{
    localdata: [],
    datafields: [
        { name: 'configName', type: 'string', map: '0'},
        { name: 'configRule', type: 'string', map: '1' }
    ],
    datatype: "array"
};
var configGridDataAdapter = new $.jqx.dataAdapter(configGridSource);

var oidValueSource =
{
    localdata: [],
    datafields: [
        { name: 'OID', type: 'string', map: '0'},
        { name: 'Value', type: 'string', map: '1' }
    ],
    datatype: "array"
};
var oidValueDataAdapter = new $.jqx.dataAdapter(oidValueSource);

$(document).ready(function() {
	$("#oidValue").val("");
	$('#window').jqxWindow({
        showCollapseButton: true, maxHeight: 496, height: 496, width: 700,autoOpen: false,
        initContent: function () {
            $('#window').jqxWindow('focus');
        }
    });
	
    //grid localization
    var getLocalization = function() {
        var localizationobj = {};
        localizationobj.pagergotopagestring = "当前页:";
        localizationobj.pagershowrowsstring = "每页显示:";
        localizationobj.pagerrangestring = " 总共 ";
        localizationobj.pagernextbuttonstring = "后页";
        localizationobj.pagerpreviousbuttonstring = "前页";
        localizationobj.sortascendingstring = "正序";
        localizationobj.sortdescendingstring = "倒序";
        localizationobj.sortremovestring = "清除排序";
        localizationobj.firstDay = 1;
        localizationobj.percentsymbol = "%";
        localizationobj.currencysymbol = "￥";
        localizationobj.currencysymbolposition = "before";
        localizationobj.decimalseparator = ".";
        localizationobj.thousandsseparator = ",";
        localizationobj.emptydatastring = "暂没有数据";

        return localizationobj;
    };
    var source = {
        datatype: "json",
        datafields: [{
            name: 'trap_key',
            type: 'string'
        },
        {
            name: 'oid_list',
            type: 'string'
        },
        {
            name: 'alarm_content',
            type: 'string'
        },
        {
            name: 'alarm_title',
            type: 'string'
        },
        {
            name: 'alarm_time',
            type: 'string'
        },
        {
            name: 'alarm_grade',
            type: 'string'
        },
        {
            name: 'alarm_type',
            type: 'string'
        },
        {
            name: 'flag',
            type: 'string'
        },
        {
            name: 'trap_status',
            type: 'string'
        }

        ],
        beforeprocessing: function(data) {
            if (data.length == 0) {
                source.totalrecords = 0;
            } else {
                source.totalrecords = data[data.length - 1].totalcount;
            }

        },
//         updaterow: function(rowid, rowdata, commit) {
//             var url = '${ctx}/pages/alarm/aknowledge?content=' + rowdata.attr_result + '&alarmId=' + rowdata.id;
//             $.ajax({
//                 cache: false,
//                 dataType: 'json',
//                 url: url,
//                 success: function(data, status, xhr) {
//                     //alert("success");
//                 },
//                 error: function(jqXHR, textStatus, errorThrown) {
//                     //alert(errorThrown);
//                 }
//             });
//             commit(true);
//         },
        sort: function() {
            // update the grid and send a request to the server.
            $("#trapGrid").jqxGrid('updatebounddata');
        },
        id: 'id',
        url: '${ctx}/trap/trapList?flagType=1'
    };

    var dataAdapter = new $.jqx.dataAdapter(source, {
        loadComplete: function(records) {}
    });
    var editrenderer = function(row, datafield, value) {
        return '<a herf="#" style="cursor: pointer;" onclick="editClick(' + row + ')">编辑规则</a>';
    };
    var flagrenderer = function(row, datafield, value) {
        if (value != 0) {
            return '已配置';
        }
        return '未配置';
    };
    var statusrenderer = function(row, datafield, value) {
        if (value != 0) {
            return '停用';
        }
        return '启用';
    };
    $("#trapGrid").jqxGrid({
    	width: '99%',
        source: dataAdapter,
        columnsresize: true,
        localization: getLocalization(),
        pageable: true,
        pagesize: 20,
        //filterable: true,
        sortable: true,
        //page
        virtualmode: true,
        rendergridrows: function() {
            return dataAdapter.records;
        },
        selectionmode: 'checkbox',
        altrows: true,
        enabletooltips: true,
        columns: [{
            text: 'TrapKey',
            datafield: 'trap_key',
            cellsalign: 'left',
            align: 'left',
            width: '10%'
        },
        {
            text: 'OidList',
            datafield: 'oid_list',
            cellsalign: 'left',
            align: 'left',
            width: '20%'
        },
        {
            text: '告警标题',
            datafield: 'alarm_title',
            cellsalign: 'left',
            align: 'left',
            width: '10%'
        },
        {
            text: '告警内容',
            datafield: 'alarm_content',
            cellsalign: 'left',
            align: 'left',
            width: '10%'
        },
        {
            text: '告警时间',
            datafield: 'alarm_time',
            cellsalign: 'left',
            align: 'left',
            width: '10%'
        },
        {
            text: '告警等级',
            datafield: 'alarm_grade',
            cellsalign: 'left',
            align: 'left',
            width: '10%'
        },
        {
            text: '告警类别',
            datafield: 'alarm_type',
            cellsalign: 'left',
            align: 'left',
            width: '10%'
        },
        {
            text: '配置状态',
            datafield: 'flag',
            cellsalign: 'left',
            align: 'left',
            width: '5%',
            cellsrenderer: flagrenderer
        },
        {
            text: '是否启用',
            datafield: 'trap_status',
            cellsalign: 'left',
            align: 'left',
            width: '5%',
            cellsrenderer: statusrenderer
        },
        {
            text: '规则',
            cellsalign: 'left',
            align: 'left',
            width: '8%',
            cellsrenderer: editrenderer
        }]
    });

    $("#configGrid").jqxGrid(
    {
        width: '100%',
        source: configGridDataAdapter,
        selectionmode: 'singlecell',
        editable: true,
        pageable: false,
        height:160,
        columns: [
	        {text: '名称', datafield: 'configName', width: '50%', editable: false},
            {
                text: 'OID表达式', datafield: 'configRule', width: '50%', columntype: 'combobox',
                createeditor: function (row, column, editor) {
                    // assign a new data source to the combobox.
                    var list = oidComboBoxList;
                    editor.jqxComboBox({ autoDropDownHeight: true, source: list, promptText: "Please Choose:" });
                },
                // update the editor's value before saving it.
                cellvaluechanging: function (row, column, columntype, oldvalue, newvalue) {
                    // return the old value, if the new value is empty.
                    //if (newvalue == "") return oldvalue;
                }
            }
        ]
    });
    
    // initialize jqxGrid
    $("#oidValueGrid").jqxGrid(
    {
        width: '100%',
		autoheight: true,
        source: oidValueDataAdapter,                
        pageable: true,
        sortable: true,
        altrows: true,
        enabletooltips: true,
        editable: false,
        selectionmode: 'multiplecellsadvanced',
        columns: [
		  { text: 'OID',  datafield: 'OID', width:'50%' },
          { text: 'Value', datafield: 'Value', width:'50%' },                   
        ]
    });

    $("#saveButton").click(function() {
    	//保存前遍历规则grid的规则字段，验证每一个规则的正确性
    	var rows = $('#configGrid').jqxGrid('getrows');
    	var alarmTitle = "";
    	var alarmGrade = "";
    	var alarmTime = "";
    	var alarmType = "";
    	var alarmContent = "";
    	var checkFlag = false;
    	$.each(rows , function(rowNum, data) { 
    		var configName = data.configName;
    		var configRule = data.configRule;

    		if(data.configRule){
    			
        		if(rowNum==0){
        			alarmTitle = configRule;
        		}else if(rowNum==1){
        			alarmContent = configRule;
        		}else if(rowNum==2){
        			alarmTime = configRule;
        		}else if(rowNum==3){
        			alarmGrade = configRule;
        		}else if(rowNum==4){
        			alarmType = configRule;
        		}
        		
        		$.each(oidValueSourceArr , function(n, val) { 
            		var oid = val[0];
            		var oidValue = val[1];
            		configRule = configRule.replace(new RegExp(oid,"gm"),oidValue);
        		});
        		configRule = configRule.replace(/\ /g,"");
        		var reg = /\w*{{1}[^+]*}{1}\w*/; 
        		if(reg.test(configRule)){
        			
        			if($('#newWindow1').length<1){
        				$(document.body).append('<div id="newWindow1" style="z-index:90001"><div>Warning</div><div id="warn1">'+configName+'的OID表达式有误，请检查！ </div></div>');
	        			$('#newWindow1').jqxWindow({isModal : true, height: 80, width: 150});
	        		}else{
	        			$('#warn1').text(configName+'的OID表达式有误，请检查！ ');
	    				$('#newWindow1').jqxWindow('open');
	    				
	    			}
        			checkFlag = false;
        			return false;
        			//alert(configName+"的OID表达式有误，请检查！");
        			
        		}
        		checkFlag = true;
    		}else{
    			if($('#newWindow2').length<1){
    				$(document.body).append('<div id="newWindow2" style="z-index:90001"><div>Warning</div><div id="warn2">'+configName+'的OID表达式为空！ </div></div>');
    				$('#newWindow2').jqxWindow({isModal : true, height: 80, width: 150});
    			}else{
    				$('#warn2').text(configName+'的OID表达式为空！ ');
    				$('#newWindow2').jqxWindow('open');
    			}
    			//alert(configName+"的OID表达式为空！");
    			checkFlag = false;
    			return false;
    		}
    		
		});
    	if(checkFlag){
    		$.ajax({
     		   url:'${ctx}/trap/saveTrap',
     		   type:'POST',
     		   dataType:'json',
     		   data:{
     			     trapKey:trapKey,
     			     oidList:oidList,
     			     alarmTitle:alarmTitle,
     			     alarmGrade:alarmGrade,
     			     alarmTime:alarmTime,
     			     alarmType:alarmType,
     			     alarmContent:alarmContent
     		   },
     		   success:function(data){
     		       source.url='${ctx}/trap/trapList?flagType=1';
     		       dataAdapter.dataBind();
     			   $('#window').jqxWindow('close');
     		   },
     		   failure:function(){
     			   if($('#newWindow3').length<1){
     			       $(document.body).append('<div id="newWindow3" style="z-index:20001"><div>Warning</div><div>保存失败！ </div></div>');
     			       $('#newWindow3').jqxWindow({isModal : true, height: 80, width: 150});
      			   }else{
	    			   $('#newWindow3').jqxWindow('open');
	    		   }
     		   }
     	   });
    	}
    	
    });
    
    $("#trapQuery").click(function() {
    	$.blockUI({
            message: 'Loading...',
            css: {
                width: '10%',
                left: '40%',
                border: 'none',
                padding: '15px',
                backgroundColor: '#000',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: .5,
                color: '#fff'
            },
            onBlock: function() {
            	var oidValue = $("#oidValue").val();
            	var selectIndex = $("#jqxWidget").jqxDropDownList('getSelectedIndex');
            	source.url='${ctx}/trap/trapList?flagType='+selectIndex+'&oidValue='+oidValue;
            	dataAdapter.dataBind();
                $.unblockUI();
            }
        });
    });
    
    $("#addRule").click(function() {
    	oidComboBoxList = new Array;
    	oidValueSourceArr = new Array;
    	oidList = "";
    	trapKey = "";
    	
        //var rowData = $("#trapGrid").jqxGrid('getrowdata', row);
        //trapKey = rowData.trap_key;
        oidSource.url = "${ctx}/trap/ruleOidList";
        oidDataAdapter.dataBind();
        
        $.each(oidDataAdapter.recordids,function(n,value) {
        	oidComboBoxList[oidComboBoxList.length] = "{" + value.oid + "}";
        	oidValueSourceArr[oidValueSourceArr.length] = ["{" + value.oid + "}",value.oidValue];
        	//oidList = oidList+value.oid+";";
        });
        
       	configGridSource.localdata = [["AlarmTitle",""],["AlarmContent",""],["AlarmTime",""],["AlarmGrade",""],["AlarmType",""]];
       	configGridDataAdapter.dataBind();
        
        
        oidValueSource.localdata = oidValueSourceArr;
    	oidValueDataAdapter.dataBind();
        
        $('#window').jqxWindow('open');
    });
    
    $("#deleteRule").click(function() {
    	editUrl = "${ctx}/trap/deleteTrap";
    	var rowindexes = $('#trapGrid').jqxGrid('getselectedrowindexes');
    	var trapKeys = "";
    	for(var row in rowindexes){
    		var data = $('#trapGrid').jqxGrid('getrowdata', row);
    		trapKeys = trapKeys + "'" + data.trap_key + "',"
    	}
    	if(trapKeys.length>0){
    		trapKeys=trapKeys.substring(0,trapKeys.length-1);
    		//调用删除方法
    		$('#eventWindow').jqxWindow('open');
    	}else{
    		if($('#newWindow3').length<1){
			       $(document.body).append('<div id="newWindow3" style="z-index:20001"><div>Warning</div><div>请选择要删除的规则！ </div></div>');
			       $('#newWindow3').jqxWindow({isModal : true, height: 80, width: 150});
			   }else{
  			   $('#newWindow3').jqxWindow('open');
  		   }
    	}
    });
    
    $("#startRule").click(function() {
    	editUrl = "${ctx}/trap/startTrap";
    	var rowindexes = $('#trapGrid').jqxGrid('getselectedrowindexes');
    	var trapKeys = "";
    	for(var row in rowindexes){
    		var data = $('#trapGrid').jqxGrid('getrowdata', row);
    		trapKeys = trapKeys + "'" + data.trap_key + "',"
    	}
    	if(trapKeys.length>0){
    		trapKeys=trapKeys.substring(0,trapKeys.length-1);
    		//调用删除方法
    		$('#eventWindow').jqxWindow('open');
    	}else{
    		if($('#newWindow3').length<1){
			       $(document.body).append('<div id="newWindow3" style="z-index:20001"><div>Warning</div><div>请选择要启用的规则！ </div></div>');
			       $('#newWindow3').jqxWindow({isModal : true, height: 80, width: 150});
			   }else{
  			   $('#newWindow3').jqxWindow('open');
  		   }
    	}
    });
    
    $("#stopRule").click(function() {
    	editUrl = "${ctx}/trap/stopTrap";
    	var rowindexes = $('#trapGrid').jqxGrid('getselectedrowindexes');
    	var trapKeys = "";
    	for(var row in rowindexes){
    		var data = $('#trapGrid').jqxGrid('getrowdata', row);
    		trapKeys = trapKeys + "'" + data.trap_key + "',"
    	}
    	if(trapKeys.length>0){
    		trapKeys=trapKeys.substring(0,trapKeys.length-1);
    		//调用删除方法
    		$('#eventWindow').jqxWindow('open');
    	}else{
    		if($('#newWindow3').length<1){
			       $(document.body).append('<div id="newWindow3" style="z-index:20001"><div>Warning</div><div>请选择要停用的规则！ </div></div>');
			       $('#newWindow3').jqxWindow({isModal : true, height: 80, width: 150});
			   }else{
  			   $('#newWindow3').jqxWindow('open');
  		   }
    	}
    });
    
    
    
    $('#eventWindow').jqxWindow({
        maxHeight: 150,
        maxWidth: 280,
        minHeight: 30,
        minWidth: 250,
        height: 100,
        width: 270,
        autoOpen: false,
        resizable: false,
        isModal: true,
        modalOpacity: 0.3,
        okButton: $('#ok'),
        cancelButton: $('#cancel'),
        initContent: function() {
            $('#ok').jqxButton({
                width: '65px'
            });
            $('#cancel').jqxButton({
                width: '65px'
            });
            $('#ok').focus();
        }
    });
    $('#events').jqxPanel({
        height: '200px',
        width: '450px'
    });
    $('#ok').click(function() {
        $('#eventWindow').jqxWindow('close');

        var rowindexes = $('#trapGrid').jqxGrid('getselectedrowindexes');
        var trapKeys = "";
        for (var row in rowindexes) {
            var data = $('#trapGrid').jqxGrid('getrowdata', rowindexes[row]);
            trapKeys = trapKeys + "'" + data.trap_key + "',"
        }
        if (trapKeys.length > 0) {
            trapKeys = trapKeys.substring(0, trapKeys.length - 1);
        }
        $.blockUI({
            message: 'Loading...',
            css: {
                width: '10%',
                left: '40%',
                border: 'none',
                padding: '15px',
                backgroundColor: '#000',
                '-webkit-border-radius': '10px',
                '-moz-border-radius': '10px',
                opacity: .5,
                color: '#fff'
            },
            onBlock: function() {
            	$.ajax({
                    url: editUrl,//'${ctx}/trap/deleteTrap',
                    type: 'POST',
                    dataType: 'json',
                    data: {
                        trapKeys: trapKeys
                    },
                    success: function(data) {
                        source.url = '${ctx}/trap/trapList?flagType=1';
                        dataAdapter.dataBind();
                    },
                    failure: function() {
                        if ($('#newWindow3').length < 1) {
                            $(document.body).append('<div id="newWindow3" style="z-index:20001"><div>Warning</div><div>操作失败！ </div></div>');
                            $('#newWindow3').jqxWindow({
                                isModal: true,
                                height: 80,
                                width: 150
                            });
                        } else {
                            $('#newWindow3').jqxWindow('open');
                        }
                    }
                });
                $.unblockUI();
            }
        });
    });
});
</script>  
  
<script src="${ctx}/pages/avmon_back_gif.js"></script>

<!-- 确认框 -->
<div id="events" style="width: 300px; height: 200px; border-width: 0px;"></div>
<div id="eventWindow">
	<div>Modal Window</div>
	<div>
		<div style="text-align: center;vertical-align: middle;">确认要执行该操作吗?</div>
		<div>
			<div style="float: right; margin-top: 15px;">
				<input type="button" id="ok" value="OK" style="margin-right: 10px" />
				<input type="button" id="cancel" value="Cancel" />
			</div>
		</div>
	</div>
</div>  
</body>
</html>
