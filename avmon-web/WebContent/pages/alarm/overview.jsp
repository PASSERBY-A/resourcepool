<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ page import="java.util.Map"%>
<%Map menuMap=(Map)request.getSession().getAttribute("menuMap");
%>
<html>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>告警中心</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- CSS -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/jquery.toastmessage.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/Main.css">
<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico">
<link rel="stylesheet"
	href="${ctx}/resources/js/jqwidgets/styles/jqx.base.css"
	type="text/css" />
</head>
<body>
	<div id="wrap">
		<!-- Header
      ================================================== -->
		<div id="top" class="top">
			<div class="fixed">
				<%-- <a href="${ctx}/main"><div class="LOGO"></div></a> --%> 
				
				<a href="${ctx}/main">
					<div class="Avmon_back_gif">
						<img id="Avmon_back_img" src="${ctx}/resources/images/back.png" 
							onmouseover="mouseover('${ctx}');" onmouseout="mouseout('${ctx}');">
					</div>
				</a>
				
				<div class="MainTitle f28px LineH12" id="ShowTitleMenuclick" style="cursor:pointer; padding:12px 0 0 10px">告警数据总览</div>
				
				<!--  <div class="left Padding10"> 
				<div  id="bizList" class="left" style="background-color: #0096d6; color:#ffffff; border:0px;"></div>
				 </div>  -->
				<div class="MainTitle" style="padding:12px 0 0 10px" >
                 <div  id="bizList" class="left" style="background-color: #0096d6; color:#ffffff; border:0px;"></div>
                 </div>
				<%-- <div class="Profile" id="ShowUserBoxclick" style="cursor: pointer">
					<div class="ProfileIcon"></div>
					<div class="ProfileName ">${userName}</div>
					<div class="ProfileArrow"></div>
					<div class="blank0"></div>
				</div> --%>
			</div>
			<!-- showUserBox
      ================================================== -->
			<div id="ShowUserBoxMain" style="display: none">
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
		
		 <!-- showTitleMenu
       ================================================== -->
  
  <div id="ShowTitleMenu" style="display:none">
    <div class="showTitleMenuBoxArrowdown"></div>
    <div class="showTitleMenuBox">
      <ul>
        <%if(menuMap.get("ALARM_CENTER_MODULE").equals(0)){%>
        <li><a href="${ctx}/pages/alarm/alermOverView"><span class="SettingIcon"></span><span>告警中心</span></a></li>
        <%}%>
        <%if(menuMap.get("PERFORMANCE_MODULE").equals(0)){%>
        <li><a href="${ctx}/performance/overview"><span class="SettingIcon"></span><span>性能中心</span></a></li>
        <%}%>
        <%if(menuMap.get("DISCOVERY_MODULE").equals(0)){%>
        <li><a href="${ctx}/discovery/deviceScan"><span class="SettingIcon"></span><span>自动发现</span></a></li>
        <%}%>
        <%if(menuMap.get("NETWORK_MODULE").equals(0)){%>
        <li><a href="${ctx}/discovery/mibmgr"><span class="SettingIcon"></span><span>简单网络管理</span></a></li>
        <%}%>
        <%if(menuMap.get("REPORT_MODULE").equals(0)){%>
        <li><a href="${ctx}/report/index"><span class="SettingIcon"></span><span>报表查询</span></a></li>
        <%}%>
        <%if(menuMap.get("PERFORMANCE_ANALYZE_MODULE").equals(0)){%>
        <li><a href="${ctx}/kpiAnalysis/index"><span class="SettingIcon"></span><span>性能分析</span></a></li>
        <%}%>
        <%if(menuMap.get("AGENT_MODULE").equals(0)){%>
        <li><a href="${ctx}/deploy/index"><span class="SettingIcon"></span><span>Agent管理</span></a></li>
        <%}%>
        <%if(menuMap.get("CONFIG_MODULE").equals(0)){%>
        <li><a href="${ctx}/config/main"><span class="SettingIcon"></span><span>配置管理</span></a></li>
        <%}%>
        <%if(menuMap.get("RESOURCE_MODULE").equals(0)){%>
        <li><a href="${ctx}/equipmentCenter/index"><span class="SettingIcon"></span><span>资源管理</span></a></li>
        <%}%>
        <%if(menuMap.get("SYSTEM_MODULE").equals(0)){%>
        <li><a href="${ctx}/system/main?type=system"><span class="SettingIcon"></span><span>系统管理</span></a></li>
        <%}%>
         <%if(menuMap.get("AGENTLESS_MODULE").equals(0)){%>
         <li><a href="${ctx}/agentless/agentlessimport"><span class="SettingIcon"></span><span>Agentless管理</span></a></li>
         <%}%>
        <div class="blank0"></div>
      </ul>
    </div>
  </div>
		 <!-- RangSelectionData
       ================================================== -->
  
      <div id="RangSelectData" style="display:none">
       <div class="RangSelectDataArrow"></div>
        <div class="RangSelectData">
        <div id='startDate'>
        </div>
        </div>
      </div>

		<!-- Content container
      ================================================== -->
	<div id="container">
      
            <div id="sidebar" style="width:140px;">
             <div class="Tab_category_Overview"> 
              <ul id="datacenter">
              <li id="ALL" class="Tab_category_Overview_active"><a href="#"><b class="Icon24 Overview_icon_All_white"></b><span>全部</span></a></li>
              <li id="HARDWARE"><a href="#"><b class="Icon24 Overview_icon_HardWare"></b><span>硬件设备</span></a></li>              
              <li id="HOST"><a href="#"><b class="Icon24 Overview_icon_OS"></b><span>操作系统</span></a></li>
              <li id="DATABASE"><a href="#"><b class="Icon24 Overview_icon_Database"></b><span>数据库</span></a></li>
              <li id="NETWORK"><a href="#"><b class="Icon24 Overview_icon_Network"></b><span>网络设备</span></a></li>              
              <li id="MIDDLEWARE"><a href="#"><b class="Icon24 Overview_icon_Middleware"></b><span>中间件</span></a></li>
              <li id="STORAGE"><a href="#"><b class="Icon24 Overview_icon_Storage"></b><span>存储设备</span></a></li>
              <li id="VE"><a href="#"><b class="Icon24 Overview_icon_VM"></b><span>虚拟环境</span></a></li>
             <!--  <li id="ALL"><a href="#"><b class="Icon24 Overview_icon_System"></b><span>业务系统</span></a></li>   -->                       
              </ul>
              </div>
            </div>
            
            <div class="content">
						<!-- NavAvmon 以下是2014年8月14日，根据Comments Updated.
      ================================================== -->
						<div class="NavAvmon" style="padding: 2px;">

			

					<!-- <div class="left paddingL4">
						<div class="left Padding14 NavMainBtn">
							<b class="Icon16 Overview_icon_clock"></b><span
								style="padding-left: 22px;line-height: 18px;vertical-align: top;"> 
								<div class="inline-block"><div class="left" id='startDate'>
            </div> </div> 至
								 <div class="inline-block"><div class="left" id='endDate'>
            </div></div>
							</span>
						</div>

					</div> -->

                  <div class="left paddingL4">
                  <div class="left Padding14 NavMainBtn"><b class="Icon16 Overview_icon_clock"  style="margin-top: -2px;"></b>
                  <span style="padding-left:22px;"><div class="inline-block">从</div>
                  <div id="sdate" class="inline-block"></div><div class="inline-block">至</div> 
                  <div id="edate" class="inline-block"></div></span></div>  
                  </div> 

			      <div class="right paddingL4">
								<div class="left NavMainBtn paddingL15 paddingR15">
									<span>按时间段查询</span></a>
								</div>
								<div id="1" class="left Padding14 NavMainBtn period">
									<a href="#" class="Avmon-button"><span>今天</span></a>
								</div>
								<div id="2" class="left Padding14 NavMainBtn period">
									<a href="#" class="Avmon-button"><span>近一周</span></a>
								</div>
								<div id="3" class="left Padding14 NavMainBtn period">
									<a href="#" class="Avmon-button"><span>近二周</span></a>
								</div>
								<div id="4" class="left Padding14 NavMainBtn period">
									<a href="#" class="Avmon-button"><span>近一月</span></a>
								</div>
							    <!--<div id="5" class="left Padding14 NavMainBtn period">
									<a href="#" class="Avmon-button"><span>全部</span></a>
								</div>  -->
								 <div class="left Padding14 NavMainBtn"><a href="#"  class="Avmon-button">
								 <span id="ShowDataclick" style="cursor:pointer">时间段</span></a>
								 </div> 
							</div>
						</div>
                       <div  class="right Padding10"><a href="${ctx}/pages/alarm/alarm"><span class="Avmon-next-arrow left"></span><span class="left f16px">告警处理</span></a></div>
                       <div class=" blank0"></div>
                
						<div class="top-left" >
							<div id="chartContainer1" style="height: 241px;cursor:pointer">
								<center >数据加载中...</center>
							</div>
							<div id="nodata1" class="AlarmCenterLogo" style="display:none;height:241px; line-height:241px">
							<span>No data to display</span></div>

						</div>

						<div class="top-right">
							<div id="chartContainer2" style="height: 241px ;cursor:pointer">
								<center >数据加载中...</center>
							</div>
							<div id="nodata2" class="AlarmCenterLogo" style="display:none;height:241px; line-height:241px">
							<span>No data to display</span></div>
						</div>

						<div class="top-left">
							<div id="chartContainer4" style="height: 241px ;cursor:pointer">
								<center>数据加载中...</center>
							</div>
							<div id="nodata3" class="AlarmCenterLogo" style="display:none;height:241px; line-height:241px">
							<span>No data to display</span></div>
						</div>

						<div class="top-right">
							<div id="chartContainer3" style="height: 241px ;cursor:pointer">
								<center>数据加载中...</center>
							</div>
							<div id="nodata4" class="AlarmCenterLogo" style="display:none;height:241px; line-height:241px">
							<span>No data to display</span></div>
						</div>
						<%-- <div class="right Padding10">
							<a href="${ctx}/pages/alarm/alarm"><span class="Avmon-next-arrow left"></span><span
								class="left f16px">告警中心详细</span></a>
						</div> --%>


					</div>
				</div>

			</div>
<!-- window -->
		<div style="width: 100%; height: 300px; display: none; margin-top: 50px;" id="mainDemoContainer">
	<div id="changePwdWin">
		<div id="windowHeader">
			<span class="Icon16  R-icon_1_white"></span><span id="windowtitle"
				class="f14px white paddingL20">修改密码</span>
		</div>
		<div>

            <div class="left">
				<div class="overlay-El-line">
					<div style="margin-bottom: 4px;">用户名</div>
					<div class="jqx-validator-error-element">
						<input disabled id="account" placeholder="" value="${userId}" style="width: 250px;height:28px;line-height:20px;"></input>
					</div>
				</div>
			</div>
			<div class="left">
				<div class="overlay-El-line">
					<div style="margin-bottom: 4px;">原始密码</div>
					<div class="jqx-validator-error-element">
						<input id=oldPassword placeholder="" type="password" style="width: 250px;"></input>
					</div>
				</div>
			</div>
			<div class="blank0"></div>
			<div class="left">
				<div class="overlay-El-line">
					<div style="margin-bottom: 4px;">新密码</div>
					<div>
						<input id="password" placeholder="" type="password" style="width: 250px;"></input>
					</div>
					<!-- <div class="AVmon-validator-label"></div> -->
				</div>
			</div>
	       <div class="blank0"></div>
			<div class="AvmonButtonArea">

				<div class="left">
					<span id="confirmChange" class="AvmonOverlayButton">确定</span><span
						id="cancelChange" class="AvmonOverlayButton">取消</span>
				</div>
				<div class="blank0"></div>

			</div>
		</div>
	</div>
   </div>
   <!-- end -->
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
		        src="${ctx}/resources/js/common.js"></script>
			<script type="text/javascript">
	$(document).ready(function() {  
		var period="";
		var typeId="";
		var s_start_date="";
		var s_end_date=""; 
		var url = "${ctx}/pages/alarm/getAllBiz";
		var item;
        var bizsource =
        {
            datatype: "json",
            datafields: [
                { name: 'id' },
                { name: 'name' }
            ],
            url: url,
            async: false
        };
        var bizdataAdapter = new $.jqx.dataAdapter(bizsource);
        $("#bizList").jqxDropDownList({
            selectedIndex: 0, source: bizdataAdapter, displayMember: "name", valueMember: "id", width: '125px', height: '25px'
        });
        var index = $("#bizList").jqxDropDownList('getSelectedIndex');
        item = $("#bizList").jqxDropDownList('getItem', index);
        if(item.label=='所有') {
        	item.label='ALL';
        }
        if(item.label=='其他') {
        	item.label='others';
        }
        $('#bizList').on('change', function (event){     
        		    var args = event.args;
        		    if (args) {
        		    var item1 = args.item;
        		    item=item1;
        		    if(item.label=='所有') {
        	        	item.label='ALL';
        	        }
        	        if(item.label=='其他') {
        	        	item.label='others';
        	        }
        		    reloadChart(typeId,period,s_start_date,s_end_date,item);
           } 
        });
    
		//{name:"scheme28",colors:["#37bc29","#0077dc","#eea025","#e16725","#e43e1e"]}]
		//{name:"scheme29",colors:["#2cc185","#f06050","#e74c39","#f39c12","#8e44ad"]}] 原始配色
		//$.jqx._jqxChart.prototype.colorSchemes.push({ name: 'myScheme', colors:['#7fd13b','#f49ac1','#ec008c','#0072bc','#8e44ad'] });
		$.jqx._jqxChart.prototype.colorSchemes.push({ name: 'myScheme', colors:['#2cc185','#f06050','#e74c39','#f39c12','#8e44ad'] });
		$.jqx._jqxChart.prototype.colorSchemes.push({ name: 'levelScheme', colors:['#37bc29','#0077dc','#eea025','#e16725','#e43e1e'] });
 
		var source1 =
	        {
	            datatype: "json",
	            datafields: [
	                {name: 'total' },
	                {name: 'value'},
	                {name: 'hostname'}
	            ],
	            url: '${ctx}/pages/alarm/getTopTenAlarmData?bizName='+encodeURI(encodeURI(item.label))
	        };		
	        var dataAdapter1 = new $.jqx.dataAdapter(source1, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error);} });
	        var records1=dataAdapter1.records;
	        var settings1 = {
	            title: "Top5设备告警信息 ",
	            description: "",
	            showLegend: true,
	            enableAnimations: true,
	            showToolTips:true,
	            //renderEngine:'HTML5',
	            showBorderLine: false,
	           // backgroundColor: '#339933',
	            padding: { left: 5, top: 5, right: 5, bottom: 5 },
	            titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
	            source: dataAdapter1,
	            categoryAxis: {
	                    dataField: 'value',
	                    unitInterval: 1,
	                    tickMarksInterval: 1,
	                    gridLinesInterval: 1,
	                    showGridLines: false,
	                    //textRotationAngle: -45,
	                    //axisSize:1,
	                     formatFunction: function (value) {
	                       	var firstPart="";
	                    	console.log(value.length);  
	                    	if(value.length<8){
	                    		console.log(value);  
	                    		 return value;
	                    	 } 
	                    	else {
	                    		  if(typeof(value)=="string"){
	                    			  firstPart=value.substr(0, 8);
		                    		  return firstPart+"<br>"+value.substr(8); 
	                    		  }
	                    		  
	                    	 }
	                    }, 
	                    toolTipFormatFunction:function(value){
	                    	return value;
	                    }, 
	                    showTickMarks: true
	                },
	            colorScheme: 'myScheme',
	            seriesGroups:
	                [
	                    {
	                        type: 'column',
	                        click: myEventHandler,
	                        seriesGapPercent: 5,
	                        columnsGapPercent: 25,
	                        valueAxis:
	                        {
	                            unitInterval: 100,
	                            displayValueAxis: true,
	                            description: '告警数量'
	                        },
	                        series: [
	                               // { dataField: 'total', displayText: '服务器'}//, color: 'yellow'}  
	                               { dataField: 'total', displayText: '服务器'}//, color: 'yellow'}  
	                        ]
	                    }

	                ]
	        };
	        function myEventHandler(e) {
	        	 records1=dataAdapter1.records;
	        	console.log(records1);
	        	  for (var i = 0; i < records1.length; i++) {
	                  if (e.elementValue == records1[i].total) {
	                	  
	                	  window.location.href="${ctx}/pages/alarm/alarm?caption="+records1[i].hostname+"&typeId="+typeId+"&period="+period+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date+"&bizName="+encodeURI(encodeURI(item.label));
	                      break;
	                  };
	              }; 
	        };
        
        // setup the chart
       $('#chartContainer1').jqxChart(settings1); 
        //chart2
        
         var source2 =
        {
            datatype: "json",
            datafields: [
                { name: 'grade' },
                { name: 'percent'}
            ],
            url: '${ctx}/pages/alarm/getAlarmLevelData?bizName='+encodeURI(encodeURI(item.label))
        };

        var dataAdapter2 = new $.jqx.dataAdapter(source2, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
        var records2=dataAdapter2.records;
        var settings2 = {
            title: "告警级别数据汇总",
            description: "",
            enableAnimations: true,
            showLegend: true,
            showBorderLine: false,
            //backgroundColor: '#46b1de',
            legendLayout: { left: 330, top: 45, width: 300, height: 200, flow: 'vertical' },
            padding: { left: 1, top: 5, right: 300, bottom: 5 },
            titlePadding: { left: 1, top: 0, right: 0, bottom: 10 },
            source: dataAdapter2,
            colorScheme: 'levelScheme',
            seriesGroups:
                [
                    {
                        type: 'pie',
                        click: myEventHandler1,
                        showLabels: true,
                        series:
                            [
                                { 
                                    dataField: 'percent',
                                    displayText: 'grade',
                                    labelRadius: 100,
                                    initialAngle: 15,
                                    radius: 80,
                                    centerOffset: 0,
                                    formatFunction: function (value) {
                                        if (isNaN(value))
                                            return value;
                                        return parseFloat(value) + '%';
                                    },
                                    toolTipFormatFunction: function (value) {
                                        var records = dataAdapter2.records;
                                        for (var i = 0; i < records.length; i++) {
                                            if (value == records[i].percent) {
                                                return records[i].grade + ":" + records[i].percent+"%";
                                                break;
                                            };
                                        }; 
                                    }
                                }
                                
                            ]
                    }
                ]
        };
   
        function myEventHandler1(e) {
            records2=dataAdapter2.records;
        	  for (var i = 0; i < records2.length; i++) {
                  if(e.elementValue == records2[i].percent){
                  	  var level;
                     	 if(records2[i].grade.indexOf("一级")>=0){
                     		level=1;
                     	  }
                     	 else if(records2[i].grade.indexOf("二级")>=0){
                     		level=2;
                     	  }
                     	else if(records2[i].grade.indexOf("三级")>=0){
                     		level=3;
                     	  }
                     	else if(records2[i].grade.indexOf("四级")>=0){
                     		level=4;
                     	  }
                     	else if(records2[i].grade.indexOf("五级")>=0){
                     		level=5;
                     	  }
                     	window.location.href="${ctx}/pages/alarm/alarm?level="+level+
          			  "&isHandle=0&period="+period+"&typeId="+typeId+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date
          			+"&bizName="+encodeURI(encodeURI(item.label));
                      break;
                  }
              }; 
        };
        // setup the chart
        $('#chartContainer2').jqxChart(settings2); 
        // prepare chart data as an array
         var source3 =
        {
            datatype: "json",
            datafields: [
                { name: 'typeId' },
                { name: 'percent'}
            ],
            url: '${ctx}/pages/alarm/getAlarmDataByType?bizName='+encodeURI(encodeURI(item.label))
        };

        var dataAdapter3 = new $.jqx.dataAdapter(source3, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
        var records3=dataAdapter3.records;
        // prepare jqxChart settings
        var settings3 = {
            title: "告警设备分布",
            description: "",
            enableAnimations: true,
            showLegend: true,
            showBorderLine: false,
            //backgroundColor: '#46b1de',
            //legendPosition: { left: 330, top: 100, width: 300, height: 200,flow: 'vertical' },
            legendLayout: { left: 330, top: 100, width: 300, height: 200, flow: 'vertical' },
            padding: { left: 1, top: 5, right: 300, bottom: 5 },
            titlePadding: { left: 0, top: 0, right: 0, bottom: 10 },
            source: dataAdapter3,
            colorScheme: 'myScheme',
            seriesGroups:
                [
                    {
                        type: 'donut',
                        showLabels: true,
                        click: myEventHandler2,
                        series:
                            [
                                { 
                                    dataField: 'percent',
                                    displayText: 'typeId',
                                    labelRadius: 100,
                                    initialAngle: 15,
                                    radius: 80,
                                    innerRadius: 50,
                                    centerOffset: 0,
                                    //radius: 80,
                                    //centerOffset: 0,
                                    formatFunction: function (value) {
                                        if (isNaN(value))
                                            return value;
                                        return parseFloat(value) + '%';
                                    },
                                }
                            ]
                    }
                ]
        };

        // setup the chart
      $('#chartContainer3').jqxChart(settings3); 
      function myEventHandler2(e) {
    	  records3=dataAdapter3.records;
    	  for (var i = 0; i < records3.length; i++) {
              if (e.elementValue == records3[i].percent) {
            	  window.location.href="${ctx}/pages/alarm/alarm?typeId="+records3[i].typeId+"&period="+period+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date
            	  +"&bizName="+encodeURI(encodeURI(item.label));
                  break;
              };
          }; 
    };
        //chart4
        var source4 =
        {
            datatype: "json",
            datafields: [
                {name: 'total' },
                {name: 'kpiName'}
            ],
            url: '${ctx}/pages/alarm/getTopTenKpiAlarmData?bizName='+encodeURI(encodeURI(item.label))
        };		
        var dataAdapter4 = new $.jqx.dataAdapter(source4, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error);} });
        var records4=dataAdapter4.records;
        var settings4 = {
            title: "Top5 KPI告警信息 ",
            description: "",
            showLegend: true,
            enableAnimations: true,
            showToolTips:true,
            //renderEngine:'HTML5',
            showBorderLine: false,
           // backgroundColor: '#339933',
            padding: { left: 5, top: 5, right: 5, bottom: 5 },
            titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
            source: dataAdapter4,
            categoryAxis: {
                    dataField: 'kpiName',
                    unitInterval: 1,
                    tickMarksInterval: 1,
                    gridLinesInterval: 1,
                    showGridLines: false,
                    //textRotationAngle: -45,
                    //axisSize:1,
                     formatFunction: function (value) {
                    	 var firstPart=value.substr(0, 8);
                         return firstPart+"<br>"+value.substr(8);
                    },
                    toolTipFormatFunction:function(value){
                    	return value;
                    }, 
                    showTickMarks: true
                },
            colorScheme: 'myScheme',
            seriesGroups:
                [
                    {
                        type: 'column',
                        click: myEventHandler3,
                        seriesGapPercent: 5,
                        columnsGapPercent: 25,
                        valueAxis:
                        {
                            unitInterval: 500,
                            displayValueAxis: true,
                            description: '告警数量'
                        },
                        series: [
                                //{ dataField: 'total', displayText: 'KPI'}//, color: 'yellow'}  
                                { dataField: 'total', displayText: 'KPI'}//, color: 'yellow'}  
                        ]
                    }

                ]
        };
        function myEventHandler3(e) {
      	  records4=dataAdapter4.records;
      	  for (var i = 0; i < records4.length; i++) {
                if (e.elementValue == records4[i].total) {
              	  window.location.href="${ctx}/pages/alarm/alarm?kpi="+records4[i].kpiName+"&period="+period+"&typeId="+typeId+"&s_start_date="+s_start_date+"&s_end_date="+s_end_date
              	+"&bizName="+encodeURI(encodeURI(item.label));
                    break;
                };
            }; 
      };
        // setup the chart
        $('#chartContainer4').jqxChart(settings4); 
        //reloadChart 
        function reloadChart(typeId,period,s_start_date,s_end_date,item){
        	//chart1
	        source2.url='${ctx}/pages/alarm/getAlarmLevelData?typeId='+typeId+
	        		 '&period='+period+'&s_start_date='+s_start_date+'&s_end_date='+s_end_date+'&bizName='+encodeURI(encodeURI(item.label));
	        dataAdapter2 = new $.jqx.dataAdapter(source2,
	                {
	                    async: false,
	                    autoBind: true,
	                });
	        settings2.source=dataAdapter2;
	        $('#chartContainer2').jqxChart(settings2);
	        if(settings2.source.records.length==0){
	        	$('#chartContainer2').hide();
	        	$('#nodata2').show();
	        }
	         else{
	        	$('#chartContainer2').show();
	        	$('#nodata2').hide();
	        	$('#chartContainer2').jqxChart(settings2);
	        } 
	        source1.url='${ctx}/pages/alarm/getTopTenAlarmData?typeId='+typeId+'&period='+period+'&s_start_date='+s_start_date+'&s_end_date='+s_end_date+'&bizName='+encodeURI(encodeURI(item.label));
	        dataAdapter1 = new $.jqx.dataAdapter(source1,
	                {
	                    async: false,
	                    autoBind: true,
	                });
	        settings1.source=dataAdapter1;
	        $('#chartContainer1').jqxChart(settings1);
	        if(settings1.source.records.length==0){
	        	$('#chartContainer1').hide();
	        	$('#nodata1').show();
	        } 
	        else{
	        	$('#chartContainer1').show();
	        	$('#nodata1').hide();
	        	$('#chartContainer1').jqxChart(settings1);
	        }
	        source3.url='${ctx}/pages/alarm/getAlarmDataByType?typeId='+typeId+'&period='+period+'&s_start_date='+s_start_date+'&s_end_date='+s_end_date+'&bizName='+encodeURI(encodeURI(item.label));
	        dataAdapter3 = new $.jqx.dataAdapter(source3,
	                {
	                    async: false,
	                    autoBind: true,
	                });
	        settings3.source=dataAdapter3;
	        $('#chartContainer3').jqxChart(settings3);  
	        if(settings3.source.records.length==0){
	        	$('#chartContainer3').hide();
	        	$('#nodata4').show();
	        } 
	        else{
	        	$('#chartContainer3').show();
	        	$('#nodata4').hide();
	        	$('#chartContainer3').jqxChart(settings3);
	        }
	        source4.url='${ctx}/pages/alarm/getTopTenKpiAlarmData?typeId='+typeId+'&period='+period+'&s_start_date='+s_start_date+'&s_end_date='+s_end_date+'&bizName='+encodeURI(encodeURI(item.label));
	        dataAdapter4 = new $.jqx.dataAdapter(source4,
	                {
	                    async: false,
	                    autoBind: true,
	                });
	        settings4.source=dataAdapter4;
	        $('#chartContainer4').jqxChart(settings4); 
	        if(settings4.source.records.length==0){
	        	$('#chartContainer4').hide();
	        	$('#nodata3').show(); 
	        } 
	        else{
	        	$('#chartContainer4').show();
	        	$('#nodata3').hide();
	        	$('#chartContainer4').jqxChart(settings4);
	        }
        }
        //###################for date
          $("#startDate").jqxDateTimeInput({ width: '215px', readonly: true,height: '18px',selectionMode: 'range', max: new Date(),formatString: 'yyyy/MM/dd'});
          
          $('#startDate').on('change', function (event) {
        	     if(typeId==undefined){
					typeId="ALL";
				 }
        	     //if(period==undefined){
        	    	 period="6";//specially for date query no sense
 				// } 
                 var text = $('#startDate').jqxDateTimeInput('getText');
                 s_start_date=text.substring(0,10);
                 s_end_date=text.substring(13);
                 $("#sdate").text(s_start_date);
                 $("#edate").text(s_end_date);
                 reloadChart(typeId,period,s_start_date,s_end_date,item);
          });
        //date period
        $("#ShowDataclick").click(function(){
             if($("#RangSelectData").is(":hidden")){
            	 $("#RangSelectData").show();
             	  }else{
            	 $("#RangSelectData").hide();
              }
		});
        //#################################change tab
         $("ul#datacenter li").click(function(){
        	$(this).addClass("Tab_category_Overview_active").siblings().removeClass("Tab_category_Overview_active"); 
        	        typeId=$(this)[0].id;
        	        reloadChart(typeId,5,"","",item);
             });
             $('.period').bind('click',
                 function()
                  {
  					period=$(this).attr("id");
  					if(period==1){
  					    $("#sdate").text(GetDateStr(0));
  						$("#edate").text(GetDateStr(0));
  					}
  					else if(period==2){
  						$("#sdate").text(GetDateStr(-7));
  						$("#edate").text(GetDateStr(0));
  					}
  					else if(period==3){
  						$("#sdate").text(GetDateStr(-14));
  						$("#edate").text(GetDateStr(0));
  					}
  					else{
  						var dt = new Date(); 
  						dt.setMonth(dt.getMonth() - 1);
  						var m=dt.getMonth()+1;
  						if(m<10)m="0"+m;
  						$("#sdate").text(dt.getFullYear()+"-"+m+"-"+dt.getDate());
  						$("#edate").text(GetDateStr(0));
  					}
  					if(typeId==undefined){
  						typeId="ALL";
  					}
  					reloadChart(typeId,period,"","",item);
                 }
             );
            function GetDateStr(AddDayCount) {
            	    var dd = new Date();
            	    dd.setDate(dd.getDate()+AddDayCount);
            	    var m=dd.getMonth()+1;
            	    if(m<10)m="0"+m;
				    return dd.getFullYear()+"-"+m+"-"+dd.getDate();
            }
            var dt = new Date(); 
			dt.setMonth(dt.getMonth() - 1);
			var m=dt.getMonth()+1;
			if(m<10)m="0"+m; 
			$("#sdate").text(dt.getFullYear()+"-"+m+"-"+dt.getDate());
			$("#edate").text(GetDateStr(0));
               
	});
    function show(oEvent){
     var UserBox=document.getElementById("ShowUserBoxMain");
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
   function show3(oEvent){  
	     var UserBox3=document.getElementById("ShowTitleMenu");
	     //alert(obj.style.display);
				
	     if(UserBox3.style.display=="block"){
	     	  document.getElementById("ShowTitleMenu").style.display = "none";
	     	}else{
	     		document.getElementById("ShowTitleMenu").style.display = "block";
	     		}
	     
	     e = window.event || oEvent;
	     if (e.stopPropagation)
	     {
	         e.stopPropagation();
	     }else{
	         e.cancelBubble = true;
	     }
	   }  
   window.onload = function(){
	  document.getElementById("ShowUserBoxMain").style.display = "none";
      document.body.onclick = hide;
	  document.getElementById("ShowUserBoxclick").onclick = show; 
	  document.getElementById("ShowTitleMenuclick").onclick = show3;
	  document.onmousedown = OnDocumentClick;
   };
   function OnDocumentClick()                     //点击其它时关闭颜色对话框
   {
      

   }
   var account='${userId}';
   var ctx='${ctx}';
  </script>

<script src="${ctx}/pages/avmon_back_gif.js"></script>

</body>
</html>
