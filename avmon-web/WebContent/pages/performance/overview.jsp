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
<title>性能管理_Overview</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

<!-- CSS -->
<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico">
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
		
			

<style type="text/css">
/*update 20140911*/

.jqx-fill-state-pressed {
	-moz-box-sizing: content-box;
	box-sizing: content-box;
	border-color: #ebedef;
	background: #1cb162;
}
.top-left {
	margin: 16px 20px 30px 0;
	padding: 0px;
	width: 49%;
	float: left;
}
.top-right {
	margin: 16px 0px 30px 0px;
	padding: 0px;
	width: 48%;
	float: left;
}

.jqx-scrollview {
background-color: transparent !important;
-ms-touch-action: auto !important;
}

.jqx-scrollview-button{
background-color: #aaaeaf;	
	}
	
.jqx-scrollview-button-selected {
background: #0096d6 !important;
}

.jqx-listitem-state-selected, .jqx-dropdownlist-state-selected{
background: #a9dafd !important;	
	}
	
.jqx-progressbar-value{
background: #1cb162 !important;
}	

.jqx-scrollview-button {
width: 28px;
height: 10px;
border-radius: 10px;
-moz-border-radius: 10px;
-webkit-border-radius: 10px;
display: inline-block;
margin: 2px;
cursor: pointer;}	

#jqxSystem .jqx-icon-arrow-down, #jqxSystem .jqx-icon-arrow-down-hover, #jqxSystem .jqx-icon-arrow-down-selected,
#jqxLocation .jqx-icon-arrow-down, #jqxLocation .jqx-icon-arrow-down-hover, #jqxLocation .jqx-icon-arrow-down-selected{
background-image: url('images/icon-down-white.png');
background-repeat: no-repeat;
background-position: center;
}
	
</style>
</head>

<body>
<div id="wrap"> 
  
  <!-- Header
      ================================================== -->
  <div id="top" class="top">
    <div class="fixed"> <a href="${ctx}/main">
    <!--   liqiang modify  20150113
      <div class="LOGO"></div>
    -->
      </a> 

		<a href="${ctx}/main">
			<div class="Avmon_back_gif">
				<img id="Avmon_back_img" src="${ctx}/resources/images/back.png" 
					onmouseover="mouseover('${ctx}');" onmouseout="mouseout('${ctx}');">
			</div>
		</a>

      <div class="MainTitle f28px LineH12 left" id="ShowTitleMenuclick" style="cursor:pointer">性能中心</div>
      <!--  
      <div class="Profile" id="ShowUserBoxclick" style="cursor:pointer">
        <div class="ProfileIcon"></div>
        <div class="ProfileName ">${userName}</div>
        <div class="ProfileArrow"></div>
        <div class="blank0"></div>
      </div>
      -->
        <div class="right">
        <div  class="SearchPerformanceIpt"> <b class="ico ico-search"></b>
          <input id="queryip" class="SearchPerformanceformIpt SearchPerformanceIpt-focus" tabindex="1" placeholder="请输入IP或设备名" type="text" maxlength="50" value="" autocomplete="on">
          <a id="querybtn" href="#" class="SearchPerformanceIpt-btn">查询</a> </div>
      </div>
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
  <!-- Content container
      ================================================== -->
  <div id="container">
    <div id="sidebar" style="width:90px;">
      <div class="Tab_performance_Overview">
        <ul id="cate">
          <li id="BIZ"><a href="#"><b class="OP1 Icon48 OverviewP_icon_ShangCeng"></b><span class="OP1">业务系统</span></a></li>
          <li id="MIDDLEWARE" class="Tab_performance_Overview_active"><a href="#"><b class="Icon48 OverviewP_icon_Middleware"></b><span class="">中间件</span></a></li>
          <li id="VM"><a href="#"><b class="Icon48 OverviewP_icon_VM"></b><span>虚拟环境</span></a></li>
          <li id="PVM"><a href="#"><b class="Icon48 OverviewP_icon_physical"></b><span class="">物理环境</span></a></li>
          <li id="HARDWARE"><a href="#"><b class="Icon48 OverviewP_icon_HardWare"></b><span>硬件</span></a></li>
          <li id="ROOM"><a href="#"><b class="OP1 Icon48 OverviewP_icon_ComputerRoom"></b><span>机房</span></a></li>
        </ul>
      </div>
    </div>
    <div class="content" style=" padding:0px 10px 0px 10px;">
      <div>
      <!--   <div class="performanceTitle" id="performanceTitleBoxclick" style="cursor:pointer">
          <div class="performanceTitleName">业务系统名称_01</div>
          <div class="performanceTitleArrow"></div>
           
          <div class="blank0"></div>
        </div> -->
        <div style="margin-top:10px;margin-left:10px"  id='bizList'></div>
      </div>
     
     <!-- query window -->
         <div style="width: 100%; height: 650px; display: none; margin-top: 50px;" id="mainDemoContainer">
            <div id="querywindow">
                <div id="querywindowHeader">
                   <span class="Icon16  R-icon_1_white"></span><span class="f14px white paddingL20">查询结果</span>
                </div>
                <div>

               <div style="margin:10px;">               
                 	<div class="overlay-El-line"> 
                       <div id="jqxWidget" class="AvmonMain"  style="float: left;margin: 0px;">
                          <div id="querygrid"></div>
                       </div>
                 	 </div>
      			  </div>
                </div>
            </div>
        </div>
     
     <!-- window end -->
     
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
    <div id="photoGallery">
      <!--  第一屏数据-->   
    	<div >
        <div class="top-left" id="data1">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="${ctx}/performance/goToDBPerformanceListView"><span class="Avmon-next-arrow left"></span><span class="left">数据库</span></a></div>
            <div id="jqxWidgetData1" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
              <!-- <div class="performanceRedArrow left"></div> -->
              <div class="left">
                <!-- <div>可用性</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>健康度</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
             <!--  <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>告警</div>
                <div>0</div> -->
              </div>
            </div>
          </div> 
          <div class="W50 left">
            <div id="gauge1" style="overflow:hidden; margin-top:0px;margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="dbalarmDeviceCount">0</div>
                <div>告警设备数</div>
              </div>
              <div class="left W50">
                <div id="dbdeviceCount">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget1' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 95% --></span> </div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar1'></div>
            </div>
            <div id='jqxWidget2' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span> <span class="w20 table-right"><!-- 66% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar2'></div>
            </div>
          </div>
        </div>

        <!-- sssssssssssssss -->
        <div class="top-right" id="data2">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="#"><span class="Avmon-next-arrow left"></span><span class="left">中间件</span></a></div>
            <div id="jqxWidgetData2" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
              <!-- <div class="performanceRedArrow left"></div> -->
              <div class="left">
               <!--  <div>可用性</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
             <!--  <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
               <!--  <div>健康度</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
               <!--  <div>告警</div>
                <div>0</div> -->
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id="gauge2" style="float: left; overflow:hidden; margin-top:0px;margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="middlealarmDeviceCount">0</div>
                <div>告警设备数</div>
              </div>
              <div class="left W50">
                <div id="middledeviceCount">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget3' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 85% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar3'></div>
            </div>
            <div id='jqxWidget4' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right"><!-- 46% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar4'></div>
            </div>
          </div>
        </div>
        
         <!-- eeeeeeeeeeee -->
        <div id="data3" style="display:none" class="top-left">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="${ctx}/vcenter/overView"><span class="Avmon-next-arrow left"></span><span class="left">虚拟环境</span></a></div>
            <div id="jqxWidgetData3" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
           <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
              <!-- <div class="performanceRedArrow left"></div> -->
              <div class="left">
               <!--  <div>可用性</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>健康度</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
               <!--  <div>告警</div>
                <div>0</div> -->
              </div>
            </div>
          </div> 
          <div class="W50 left">
            <div id="gauge3" style="float: left; overflow:hidden; margin-top:0px;margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="vmDeviceAlarmCount">0</div>
                <div>告警设备数</div>
              </div>
              <div class="left W50">
                <div id="vmDeviceCount">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget5' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 75% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar5'></div>
            </div>
            <div id='jqxWidget6' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right"><!-- 56% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar6'></div>
            </div>
          </div>
        </div>
        <!-- 4444444444444444444444 -->
        <div id="data4" style="display:none" class="top-right">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="${ctx}/performance/goToHostPerformanceListView"><span class="Avmon-next-arrow left"></span><span class="left">操作系统</span></a></div>
            <div id="jqxWidgetData4" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
             <!--  <div class="performanceRedArrow left"></div> -->
              <div class="left">
               <!--  <div>可用性</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
             <!--  <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
               <!--  <div>健康度</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
             <!--  <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
               <!--  <div>告警</div>
                <div>0</div> -->
              </div>
            </div>
          </div> 
          <div class="W50 left">
            <div id="gauge4" style="float: left; overflow:hidden; margin-top:0px; margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="hostDeviceAlarmCount">0</div>
                <div>告警设备数</div>
              </div>
              <div class="left W50">
                <div id="hostDeviceCount">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget7' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 55% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar7'></div>
            </div>
            <div id='jqxWidget8' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right"><!-- 36% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar8'></div>
            </div>
          </div>
        </div>
        
         <!-- 55555555555555555 -->
        
        <div id="data5" style="display:none" class="top-right">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="#"><span class="Avmon-next-arrow left"></span><span class="left">存储</span></a></div>
            <div id="jqxWidgetData5" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
            <!--   <div class="performanceRedArrow left"></div> -->
              <div class="left">
                <!-- <div>可用性</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
             <!--  <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>健康度</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>告警</div>
                <div>10000</div> -->
              </div>
            </div>
          </div> 
          <div class="W50 left">
            <div id="gauge5" style="float: left; overflow:hidden; margin-top:0px; margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="storgeDeviceAlarmCount">0</div>
                <div>告警设备数</div>
              </div>
              <div class="left W50">
                <div id="storgeDeviceCount">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget7' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 55% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar9'></div>
            </div>
            <div id='jqxWidget8' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right"><!-- 36% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar10'></div>
            </div>
          </div>
        </div>
        
        <!-- 66666666666666 -->
        
        <div id="data6" style="display:none" class="top-right">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="${ctx}/performance/goToNetworkPerformanceListView"><span class="Avmon-next-arrow left"></span><span class="left">网络</span></a></div>
            <div id="jqxWidgetData6" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
           <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
              <!-- <div class="performanceRedArrow left"></div> -->
              <div class="left">
                <!-- <div>可用性</div>
                <div>100%</div> --> 
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>健康度</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>告警</div>
                <div>0</div> -->
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id="gauge6" style="float: left; overflow:hidden; margin-top:0px; margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="networkDeviceAlarmCount">0</div>
                <div>告警设备数</div>
              </div>
              <div class="left W50">
                <div id="networkDeviceCount">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget7' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 55% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar11'></div>
            </div>
            <div id='jqxWidget8' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right"><!-- 36% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar12'></div>
            </div>
          </div>
        </div>
        
        <!-- 777777777777777 -->
         <div id="data7" style="display:none" class="top-right">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="${ctx}/performance/goToHardwarePerformanceListView"><span class="Avmon-next-arrow left"></span><span class="left">HP硬件设备</span></a></div>
            <div id="jqxWidgetData7" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
              <!-- <div class="performanceRedArrow left"></div> -->
              <div class="left">
               <!--  <div>可用性</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
             <!--  <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>健康度</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>告警</div>
                <div>0</div> -->
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id="gauge7" style="float: left; overflow:hidden; margin-top:0px; margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="hardwareDeviceAlarmCount">0</div>
                <div>告警设备数</div>
              </div>
              <div class="left W50">
                <div id="hardwareDeviceCount">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget7' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 55% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar13'></div>
            </div>
            <div id='jqxWidget8' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right"><!-- 36% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar14'></div>
            </div>
          </div>
        </div>
      </div>
      
  <!-- 8888888888888888 -->
        
        <div id="data8" style="display:none" class="top-right">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="#"><span class="Avmon-next-arrow left"></span><span class="left">DELL硬件设备</span></a></div>
            <div id="jqxWidgetData8" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
            <!--   <div class="performanceRedArrow left"></div> -->
              <div class="left">
                <!-- <div>可用性</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
             <!--  <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>健康度</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>告警</div>
                <div>10000</div> -->
              </div>
            </div>
          </div> 
          <div class="W50 left">
            <div id="gauge8" style="float: left; overflow:hidden; margin-top:0px; margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="ipmiDeviceAlarmCount">0</div>
                <div>告警设备数</div>
              </div>
              <div class="left W50">
                <div id="ipmiDeviceCount">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget9' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 55% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar15'></div>
            </div>
            <div id='jqxWidget10' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right"><!-- 36% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar16'></div>
            </div>
          </div>
        </div>
        
     <!-- 99999999999999999999 -->
         <div id="data9" style="display:none" class="top-right">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="${ctx}/performance/goToOtherHardwarePerformanceListView"><span class="Avmon-next-arrow left"></span><span class="left">其他硬件设备</span></a></div>
            <div id="jqxWidgetData9" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
              <!-- <div class="performanceRedArrow left"></div> -->
              <div class="left">
               <!--  <div>可用性</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
             <!--  <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>健康度</div>
                <div>100%</div> -->
              </div>
            </div>
            <div class="performance_box_index right">
              <!-- <div class="performanceGrreenArrow left"></div> -->
              <div class="left">
                <!-- <div>告警</div>
                <div>0</div> -->
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id="gauge9" style="float: left; overflow:hidden; margin-top:0px; margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="otherhardwareDeviceAlarmCount">0</div>
                <div>告警设备数</div>
              </div>
              <div class="left W50">
                <div id="otherhardwareDeviceCount">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget7' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 55% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar17'></div>
            </div>
            <div id='jqxWidget8' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right"><!-- 36% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar18'></div>
            </div>
          </div>
        </div>
      </div>
        
      <!--  第二屏数据-->   
    	<div style="display:none">
        <div class="top-left">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="#"><span class="Avmon-next-arrow left"></span><span class="left">XXX</span></a></div>
            <div id="jqxWidgetData5" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
              <div class="performanceRedArrow left"></div>
              <div class="left">
                <div>可用性</div>
                <div>100%</div>
              </div>
            </div>
            <div class="performance_box_index right">
              <div class="performanceGrreenArrow left"></div>
              <div class="left">
                <div>健康度</div>
                <div>100%</div>
              </div>
            </div>
            <div class="performance_box_index right">
              <div class="performanceGrreenArrow left"></div>
              <div class="left">
                <div>告警</div>
                <div>10000</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id="gauge5" style="overflow:hidden; margin-top:0px;margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div>500</div>
                <div>告警数量</div>
              </div>
              <div class="left W50">
                <div>10000</div>
                <div>总应用</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget9' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right">95%</span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar9'></div>
            </div>
            <div id='jqxWidget10' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right">66%</span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar10'></div>
            </div>
          </div>
        </div>
        <div class="top-right">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="#"><span class="Avmon-next-arrow left"></span><span class="left">YYY</span></a></div>
            <div id="jqxWidgetData6" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
              <div class="performanceRedArrow left"></div>
              <div class="left">
                <div>可用性</div>
                <div>100%</div>
              </div>
            </div>
            <div class="performance_box_index right">
              <div class="performanceGrreenArrow left"></div>
              <div class="left">
                <div>健康度</div>
                <div>100%</div>
              </div>
            </div>
            <div class="performance_box_index right">
              <div class="performanceGrreenArrow left"></div>
              <div class="left">
                <div>告警</div>
                <div>10000</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id="gauge6" style="float: left; overflow:hidden; margin-top:0px;margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div>0</div>
                <div>告警数量</div>
              </div>
              <div class="left W50">
                <div>0</div>
                <div>总应用</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget11' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right">85%</span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar11'></div>
            </div>
            <div id='jqxWidget12' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right">46%</span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar12'></div>
            </div>
          </div>
        </div>
        <div class="top-left">
          <div class="W48 left performance_box_L">
            <div class="performance_title left"><a href="#"><span class="Avmon-next-arrow left"></span><span class="left">ZZZ</span></a></div>
            <div id="jqxWidgetData7" style="border: 1px solid #ffffff;display:none" class="right"></div>
          </div>
          <div class="W50 left performance_box_R">
            <div class="performance_box_index right">
              <div class="performanceRedArrow left"></div>
              <div class="left">
                <div>可用性</div>
                <div>100%</div>
              </div>
            </div>
            <div class="performance_box_index right">
              <div class="performanceGrreenArrow left"></div>
              <div class="left">
                <div>健康度</div>
                <div>100%</div>
              </div>
            </div>
            <div class="performance_box_index right">
              <div class="performanceGrreenArrow left"></div>
              <div class="left">
                <div>告警</div>
                <div>0</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id="gauge7" style="float: left; overflow:hidden; margin-top:0px;margin-left: 6%;"></div>
            <div style="overflow:hidden; margin-top:0px;margin-left: 10%; width:250px;">
              <div class="left W50">
                <div id="">0</div>
                <div>告警设备总数</div>
              </div>
              <div class="left W50">
                <div id="">0</div>
                <div>设备总数</div>
              </div>
            </div>
          </div>
          <div class="W50 left">
            <div id='jqxWidget13' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">健康度</span><span class="w20 table-right"><!-- 75% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar13'></div>
            </div>
            <div id='jqxWidget14' class=" w100 left">
              <div style='margin-top: 10px;'><span class="left w80">可用性</span><span class="w20 table-right"><!-- 56% --></span></div>
              <div style='margin-top: 2px; overflow: hidden;' id='jqxProgressBar14'></div>
            </div>
          </div>
        </div>
      </div>      
    </div>
    </div>
  </div>
</div>
</div>
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
   function show1(oEvent){
     var UserBox1=document.getElementById("ShowUserBoxMain");
     //alert(obj.style.display);
     if(UserBox1.style.display=="block"){
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
 
 
    function show2(oEvent){  
/*      var UserBox2=document.getElementById("performanceMain");
     //alert(obj.style.display);
			
     if(UserBox2.style.display=="block"){
     	  document.getElementById("performanceMain").style.display = "none";
     	}else{
     		document.getElementById("performanceMain").style.display = "block";
     		} */
     
     e = window.event || oEvent;
     if (e.stopPropagation)
     {
         e.stopPropagation();
     }else{
         e.cancelBubble = true;
     }
   }  
   
   function hide(){

     //document.getElementById("performanceMain").style.display = "none";
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
	 // document.getElementById("performanceMain").style.display = "none";
	  document.getElementById("ShowUserBoxMain").style.display = "none";
      document.body.onclick = hide;
	  document.getElementById("ShowUserBoxclick").onclick = show1;
	  document.getElementById("ShowTitleMenuclick").onclick = show3;
	 // document.getElementById("performanceTitleBoxclick").onclick = show2;
	  
   };
  </script> 
<!--   
  
<script type="text/javascript">
            $(document).ready(function () {   
            	//biz select
            
                
             });
            	//end
              /*   var source = [
                    "今天",
                    "本周",
                    "本月",
                    "本年",
		        ];

                // Create a jqxDropDownList
                $("#jqxWidgetData1").jqxDropDownList({ source: source, selectedIndex: 1, width: '70', height: '24',dropDownHeight: '100'});
                $("#jqxWidgetData2").jqxDropDownList({ source: source, selectedIndex: 2, width: '70', height: '24',dropDownHeight: '100'});
                $("#jqxWidgetData3").jqxDropDownList({ source: source, selectedIndex: 3, width: '70', height: '24',dropDownHeight: '100'});
                $("#jqxWidgetData4").jqxDropDownList({ source: source, selectedIndex: 0, width: '70', height: '24',dropDownHeight: '100'});
                $("#jqxWidgetData5").jqxDropDownList({ source: source, selectedIndex: 1, width: '70', height: '24',dropDownHeight: '100'});
                $("#jqxWidgetData6").jqxDropDownList({ source: source, selectedIndex: 1, width: '70', height: '24',dropDownHeight: '100'});
                $("#jqxWidgetData7").jqxDropDownList({ source: source, selectedIndex: 1, width: '70', height: '24',dropDownHeight: '100'});
           */  });
        </script>  -->
<script type="text/javascript">
        $(document).ready(function () {
                var url = "${ctx}/performance/getAllBiz";
                // prepare the data
                var source =
                {
                    datatype: "json",
                    datafields: [
                        { name: 'id' },
                        { name: 'name' }
                    ],
                    url: url,
                    async: false
                };
                var dataAdapter = new $.jqx.dataAdapter(source);
                $("#bizList").jqxDropDownList({
                    selectedIndex: 0, source: dataAdapter, displayMember: "name", valueMember: "id", width: 200, height: 25
                });
                var index = $("#bizList").jqxDropDownList('getSelectedIndex');
                var item = $("#bizList").jqxDropDownList('getItem', index);
                $('#bizList').on('change', function (event){   
                		    var args = event.args;
                		    if (args) {
                		    var item1 = args.item;
                		    if(item1.label=='其他') {
                	        	item1.label='others';
                	        }
                		    getData(item1);
                   } 
                });
                //var allData;
                function getData(item1){
                    $.getJSON("${ctx}/performance/totalViewData",
    						function(data) {
                    	        
                    	        for(var i=0;i<data.length;i++){
                    	        	
                    	        	if(item1.label==data[i].biz){
                    	        		    //中间件
                    	        			$("#dbalarmDeviceCount").text(data[i].dbkpi1);
                        	        		$("#dbdeviceCount").text(data[i].dbitemcount);
                        	        		$("#middlealarmDeviceCount").text(data[i].middlekpi1);
                        	        		$("#middledeviceCount").text(data[i].middleitemcount);
                        	        		if(data[i].dbitemcount==0){
                        	        			data[i].dbkpi2=0;
                        	        			data[i].dbkpi3=0;
                        	        			$('#gauge1').jqxGauge('setValue', 0);
                        	        		}
                        	        		if(data[i].dbkpi1!=0&&data[i].dbitemcount!=0){
                        	        			$('#gauge1').jqxGauge('setValue', (data[i].dbkpi1/data[i].dbitemcount)*100);
                        	        		}
                        	        		if(data[i].middleitemcount==0){
                        	        			data[i].middlekpi2=0;
                        	        			data[i].middlekpi3=0;
                        	        			$('#gauge2').jqxGauge('setValue', 0);
                        	        		}
                        	        		if(data[i].middlekpi1!=0&&data[i].middleitemcount!=0){
                        	        			$('#gauge1').jqxGauge('setValue', (data[i].middlekpi1/data[i].middleitemcount)*100);
                        	        		}
                        	        		$("#jqxProgressBar1").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].dbkpi2, animationDuration: 1000});
                        	        		$("#jqxProgressBar2").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].dbkpi3, animationDuration: 1000});
                        	        		$("#jqxProgressBar3").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].middlekpi2, animationDuration: 1000});
                        	        		$("#jqxProgressBar4").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].middlekpi3, animationDuration: 1000});
                        	        		  //物理设备
                         	        	    $("#hostDeviceAlarmCount").text(data[i].hostkpi1);
                        	        		$("#hostDeviceCount").text(data[i].hostitemcount);
                        	        		if(data[i].hostitemcount==0){
                        	        			data[i].hostkpi2=0;
                        	        			data[i].hostkpi3=0;
                        	        			$('#gauge4').jqxGauge('setValue', 0);
                        	        		}
                        	        		if(data[i].hostkpi1!=0&&data[i].hostitemcount!=0){
                        	        			$('#gauge4').jqxGauge('setValue', (data[i].hostkpi1/data[i].hostitemcount)*100);
                        	        		}
                        	        		$("#jqxProgressBar7").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].hostkpi2, animationDuration: 1000});
                        	        		$("#jqxProgressBar8").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].hostkpi3, animationDuration: 1000});
                         	        	   
                        	        		$("#storgeDeviceAlarmCount").text(data[i].storekpi1);
                          	        		$("#storgeDeviceCount").text(data[i].storeitemcount);
                          	        		if(data[i].storeitemcount==0){
                          	        			data[i].storekpi2=0;
                          	        			data[i].storekpi3=0;
                          	        			$('#gauge5').jqxGauge('setValue', 0);
                          	        		}
                          	        		if(data[i].storekpi1!=0&&data[i].storeitemcount!=0){
                        	        			$('#gauge5').jqxGauge('setValue', (data[i].storekpi1/data[i].storeitemcount)*100);
                        	        		}
                          	        		$("#jqxProgressBar9").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].storekpi2, animationDuration: 1000});
                          	        		$("#jqxProgressBar10").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].storekpi3, animationDuration: 1000});
                           	        	   
                        	        		
                          	        		$("#networkDeviceAlarmCount").text(data[i].networkkpi1);
                          	        		$("#networkDeviceCount").text(data[i].networkitemcount);
                          	        		if(data[i].networkitemcount==0){
                          	        			data[i].networkkpi2=0;
                          	        			data[i].networkkpi3=0;
                          	        			$('#gauge6').jqxGauge('setValue', 0);
                          	        		}
                          	        		if(data[i].networkkpi1!=0&&data[i].networkitemcount!=0){
                        	        			$('#gauge6').jqxGauge('setValue', (data[i].networkkpi1/data[i].networkitemcount)*100);
                        	        		}
                          	        		$("#jqxProgressBar11").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].networkkpi2, animationDuration: 1000});
                          	        		$("#jqxProgressBar12").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].networkkpi3, animationDuration: 1000});
                          	        	    //硬件Ilo
                          	        	    $("#hardwareDeviceAlarmCount").text(data[i].hardwarekpi1);
                          	        		$("#hardwareDeviceCount").text(data[i].hardwareitemcount);
                          	        		if(data[i].hardwareitemcount==0){
                          	        			data[i].hardwarekpi2=0;
                          	        			data[i].hardwarekpi3=0;
                          	        			$('#gauge7').jqxGauge('setValue', 0);
                          	        		}
                          	        		if(data[i].hardwarekpi1!=0&&data[i].hardwareitemcount!=0){
                        	        			$('#gauge7').jqxGauge('setValue', (data[i].hardwarekpi1/data[i].hardwareitemcount)*100);
                        	        		}
                          	        		$("#jqxProgressBar13").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].hardwarekpi2, animationDuration: 1000});
                          	        		$("#jqxProgressBar14").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].hardwarekpi3, animationDuration: 1000});
                          	        	    //硬件Idrac
                          	        	    $("#ipmiDeviceAlarmCount").text(data[i].HARDWAREKKPI1);
                          	        		$("#ipmiDeviceCount").text(data[i].HARDWAREITEMCOUNT);
                          	        		//$("#ipmiDeviceAlarmCount").text(data[i].hardwarekpi1);
                          	        		//$("#ipmiDeviceCount").text(data[i].hardwareitemcount);
                          	        		
                          	        		$("#jqxProgressBar15").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].HARDWAREKPI2, animationDuration: 1000});
                          	        		$("#jqxProgressBar16").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].HARDWAREKPI3, animationDuration: 1000});
                          	        	    //其他硬件
                          	        	    $("#otherhardwareDeviceAlarmCount").text(data[i].hardwareotherkpi1);
                          	        		$("#otherhardwareDeviceCount").text(data[i].hardwareitemothercount);
                          	        		if(data[i].hardwareitemothercount==0){
                          	        			data[i].hardwareotherkpi2=0;
                          	        			data[i].hardwareotherkpi3=0;
                          	        			$('#gauge9').jqxGauge('setValue', 0);
                          	        		}
                          	        		if(data[i].hardwareotherkpi1!=0&&data[i].hardwareitemothercount!=0){
                        	        			$('#gauge9').jqxGauge('setValue', (data[i].hardwareotherkpi1/data[i].hardwareitemothercount)*100);
                        	        		}
                          	        		$("#jqxProgressBar17").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].hardwareotherkpi2, animationDuration: 1000});
                          	        		$("#jqxProgressBar18").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].hardwareotherkpi3, animationDuration: 1000});
     
                    	        	}
                    	        	if(data[i].biz=='others'){
                    	        		//虚拟机
                    	        		//alert(data[i].veitemcount);
                    	        		$("#vmDeviceAlarmCount").text(data[i].vekpi1);
                    	        		$("#vmDeviceCount").text(data[i].veitemcount);
                    	        		if(data[i].veitemcount==0){
                    	        			data[i].vekpi2=0;
                    	        			data[i].vekpi3=0;
                    	        			$('#gauge3').jqxGauge('setValue', 0);
                    	        		}
                    	        		if(data[i].vekpi1!=0&&data[i].veitemcount!=0){
                    	        			
                    	        			$('#gauge3').jqxGauge('setValue', (data[i].vekpi1/data[i].veitemcount)*100);
                    	        		}
                    	        		$("#jqxProgressBar5").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].vekpi2, animationDuration: 1000});
                    	        		$("#jqxProgressBar6").jqxProgressBar({ showText: true,width: '90%', height: 15, value: data[i].vekpi3, animationDuration: 1000});
                     	        	  
                    	        	}
                    	        	
                    	        }
    						});
                }
                getData(item);
                
                $("ul#cate li").click(function(){
                	var typeId=$(this)[0].id;
                	if(typeId!="BIZ"&&typeId!="ROOM"){
                		$(this).addClass("Tab_performance_Overview_active").siblings().removeClass("Tab_performance_Overview_active"); 
                		    if(typeId=="MIDDLEWARE"){
                			$("#data1").show();
                			$("#data2").show();
                			$("#data3").hide();
                			$("#data4").hide();
                			$("#data5").hide();
                			$("#data6").hide();
                			$("#data7").hide();
                			$("#data8").hide();
                			$("#data9").hide();
                			$("#bizList").show();
                		    }
                    		if(typeId=="VM"){
                    			$("#data1").hide();
                    			$("#data2").hide();
                    			$("#data3").show();
                    			$("#data4").hide();
                    			$("#data5").hide();
                    			$("#data6").hide();
                    			$("#data7").hide();
                    			$("#data8").hide();
                    			$("#data9").hide();
                    			$("#bizList").hide();
                    			
                    		}
                    		
                    		if(typeId=="PVM"){
                    			$("#data1").hide();
                    			$("#data2").hide();
                    			$("#data3").hide();
                    			$("#data4").show();
                    			$("#data5").show();
                    			$("#data6").show();
                    			$("#data7").hide();
                    			$("#data8").hide();
                    			$("#data9").hide();
                    			$("#bizList").show();
                    		}
                    		if(typeId=="HARDWARE"){
                    			$("#data1").hide();
                    			$("#data2").hide();
                    			$("#data3").hide();
                    			$("#data4").hide();
                    			$("#data5").hide();
                    			$("#data6").hide();
                    			$("#data7").show();
                    			$("#data8").show();
                    			$("#data9").show();
                    			$("#bizList").show();
                    		}
                    //data2
                	}
                });
                
               
    	       
                var labels = { visible: true, position: 'inside' };
               //Create jqxGauge
               $('#gauge1').jqxGauge({
                ranges: [{ startValue: 0, endValue: 44, style: { fill: '#37bc29', stroke: '#37bc29' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 44, endValue: 88, style: { fill: '#0077dc', stroke: '#0077dc' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 88, endValue: 132, style: { fill: '#eea025', stroke: '#eea025' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 132, endValue: 176, style: { fill: '#e16725', stroke: '#e16725' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 176, endValue: 220, style: { fill: '#e43e1e', stroke: '#e43e1e' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                ],
                cap: { radius: 0.04 },
                caption: { offset: [0, -25], value: '数据库', position: 'bottom' },
                value: 0,
				width: 180,
				height:132,
				style: { stroke: '#ffffff', 'stroke-width': '1px', fill: '#ffffff' },
				border: {visible: false },
                animationDuration: 3000,
                colorScheme: 'scheme08',
                labels: {visible: false },
                ticksMinor: { interval: 0, size: '2%' },
                ticksMajor: { interval: 10, size: '5%' }
            });

            //Create jqxGauge
            $('#gauge2').jqxGauge({
                ranges: [{ startValue: 0, endValue: 44, style: { fill: '#37bc29', stroke: '#37bc29' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 44, endValue: 88, style: { fill: '#0077dc', stroke: '#0077dc' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 88, endValue: 132, style: { fill: '#eea025', stroke: '#eea025' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 132, endValue: 176, style: { fill: '#e16725', stroke: '#e16725' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 176, endValue: 220, style: { fill: '#e43e1e', stroke: '#e43e1e' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                ],
                cap: { radius: 0.04 },
                caption: { offset: [0, -25], value: '中间件', position: 'bottom' },
                value: 0,
				width: 180,
				height:132,
				style: { stroke: '#ffffff', 'stroke-width': '1px', fill: '#ffffff' },
				border: {visible: false },
                animationDuration: 3000,
                colorScheme: 'scheme08',
                labels: {visible: false },
                ticksMinor: { interval: 0, size: '2%' },
                ticksMajor: { interval: 10, size: '5%' }
            });

            //Create jqxGauge
            $('#gauge3').jqxGauge({
                ranges: [{ startValue: 0, endValue: 44, style: { fill: '#37bc29', stroke: '#37bc29' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 44, endValue: 88, style: { fill: '#0077dc', stroke: '#0077dc' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 88, endValue: 132, style: { fill: '#eea025', stroke: '#eea025' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 132, endValue: 176, style: { fill: '#e16725', stroke: '#e16725' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 176, endValue: 220, style: { fill: '#e43e1e', stroke: '#e43e1e' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                ],
                cap: { radius: 0.04 },
                caption: { offset: [0, -25], value: '虚拟环境', position: 'bottom' },
                value: 0,
				width: 180,
				height:132,
				style: { stroke: '#ffffff', 'stroke-width': '1px', fill: '#ffffff' },
				border: {visible: false },
                animationDuration: 3000,
                colorScheme: 'scheme08',
                labels: {visible: false },
                ticksMinor: { interval: 0, size: '2%' },
                ticksMajor: { interval: 10, size: '5%' }
            });
			

            //Create jqxGauge
            $('#gauge4').jqxGauge({
                ranges: [{ startValue: 0, endValue: 44, style: { fill: '#37bc29', stroke: '#37bc29' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 44, endValue: 88, style: { fill: '#0077dc', stroke: '#0077dc' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 88, endValue: 132, style: { fill: '#eea025', stroke: '#eea025' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 132, endValue: 176, style: { fill: '#e16725', stroke: '#e16725' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 176, endValue: 220, style: { fill: '#e43e1e', stroke: '#e43e1e' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                ],
                cap: { radius: 0.04 },
                caption: { offset: [0, -25], value: '操作系统', position: 'bottom' },
                value: 0,
				width: 180,
				height:132,
				style: { stroke: '#ffffff', 'stroke-width': '1px', fill: '#ffffff' },
				border: {visible: false },
                animationDuration: 3000,
                colorScheme: 'scheme08',
                labels: {visible: false },
                ticksMinor: { interval: 0, size: '2%' },
                ticksMajor: { interval: 10, size: '5%' }
            });


            //Create jqxGauge
            $('#gauge5').jqxGauge({
                ranges: [{ startValue: 0, endValue: 44, style: { fill: '#37bc29', stroke: '#37bc29' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 44, endValue: 88, style: { fill: '#0077dc', stroke: '#0077dc' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 88, endValue: 132, style: { fill: '#eea025', stroke: '#eea025' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 132, endValue: 176, style: { fill: '#e16725', stroke: '#e16725' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 176, endValue: 220, style: { fill: '#e43e1e', stroke: '#e43e1e' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                ],
                cap: { radius: 0.04 },
                caption: { offset: [0, -25], value: '存储', position: 'bottom' },
                value: 0,
				width: 180,
				height:132,
				style: { stroke: '#ffffff', 'stroke-width': '1px', fill: '#ffffff' },
				border: {visible: false },
                animationDuration: 1500,
                colorScheme: 'scheme08',
                labels: {visible: false },
                ticksMinor: { interval: 0, size: '2%' },
                ticksMajor: { interval: 10, size: '5%' }
            });

            //Create jqxGauge
            $('#gauge6').jqxGauge({
                ranges: [{ startValue: 0, endValue: 44, style: { fill: '#37bc29', stroke: '#37bc29' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 44, endValue: 88, style: { fill: '#0077dc', stroke: '#0077dc' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 88, endValue: 132, style: { fill: '#eea025', stroke: '#eea025' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 132, endValue: 176, style: { fill: '#e16725', stroke: '#e16725' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 176, endValue: 220, style: { fill: '#e43e1e', stroke: '#e43e1e' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                ],
                cap: { radius: 0.04 },
                caption: { offset: [0, -25], value: '网络', position: 'bottom' },
                value: 0,
				width: 180,
				height:132,
				style: { stroke: '#ffffff', 'stroke-width': '1px', fill: '#ffffff' },
				border: {visible: false },
                animationDuration: 1500,
                colorScheme: 'scheme08',
                labels: {visible: false },
                ticksMinor: { interval: 0, size: '2%' },
                ticksMajor: { interval: 10, size: '5%' }
            });
			

            //Create jqxGauge
            $('#gauge7').jqxGauge({
                ranges: [{ startValue: 0, endValue: 44, style: { fill: '#37bc29', stroke: '#37bc29' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 44, endValue: 88, style: { fill: '#0077dc', stroke: '#0077dc' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 88, endValue: 132, style: { fill: '#eea025', stroke: '#eea025' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 132, endValue: 176, style: { fill: '#e16725', stroke: '#e16725' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 176, endValue: 220, style: { fill: '#e43e1e', stroke: '#e43e1e' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                ],
                cap: { radius: 0.04 },
                caption: { offset: [0, -25], value: 'HP硬件设备', position: 'bottom' },
                value: 0,
				width: 180,
				height:132,
				style: { stroke: '#ffffff', 'stroke-width': '1px', fill: '#ffffff' },
				border: {visible: false },
                animationDuration: 1500,
                colorScheme: 'scheme08',
                labels: {visible: false },
                ticksMinor: { interval: 0, size: '2%' },
                ticksMajor: { interval: 10, size: '5%' }
            });
            
            //Create jqxGauge
            $('#gauge8').jqxGauge({
                ranges: [{ startValue: 0, endValue: 44, style: { fill: '#37bc29', stroke: '#37bc29' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 44, endValue: 88, style: { fill: '#0077dc', stroke: '#0077dc' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 88, endValue: 132, style: { fill: '#eea025', stroke: '#eea025' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 132, endValue: 176, style: { fill: '#e16725', stroke: '#e16725' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 176, endValue: 220, style: { fill: '#e43e1e', stroke: '#e43e1e' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                ],
                cap: { radius: 0.04 },
                caption: { offset: [0, -25], value: 'DELL硬件设备', position: 'bottom' },
                value: 0,
				width: 180,
				height:132,
				style: { stroke: '#ffffff', 'stroke-width': '1px', fill: '#ffffff' },
				border: {visible: false },
                animationDuration: 1500,
                colorScheme: 'scheme08',
                labels: {visible: false },
                ticksMinor: { interval: 0, size: '7%' },
                ticksMajor: { interval: 10, size: '5%' }
            });
            //Create jqxGauge
            $('#gauge9').jqxGauge({
                ranges: [{ startValue: 0, endValue: 44, style: { fill: '#37bc29', stroke: '#37bc29' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 44, endValue: 88, style: { fill: '#0077dc', stroke: '#0077dc' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 88, endValue: 132, style: { fill: '#eea025', stroke: '#eea025' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 132, endValue: 176, style: { fill: '#e16725', stroke: '#e16725' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                         { startValue: 176, endValue: 220, style: { fill: '#e43e1e', stroke: '#e43e1e' }, startDistance: '5%', endDistance: '5%', endWidth: 13, startWidth: 13 },
                ],
                cap: { radius: 0.04 },
                caption: { offset: [0, -25], value: '其他硬件设备', position: 'bottom' },
                value: 0,
				width: 180,
				height:132,
				style: { stroke: '#ffffff', 'stroke-width': '1px', fill: '#ffffff' },
				border: {visible: false },
                animationDuration: 1500,
                colorScheme: 'scheme08',
                labels: {visible: false },
                ticksMinor: { interval: 0, size: '7%' },
                ticksMajor: { interval: 10, size: '5%' }
            });
           
          
            // set gauge's value.
            
          /*   $('#gauge2').jqxGauge('setValue', 0);
            $('#gauge3').jqxGauge('setValue', 0);
            $('#gauge4').jqxGauge('setValue', 0);
            $('#gauge5').jqxGauge('setValue', 0);
            $('#gauge6').jqxGauge('setValue', 0);
            $('#gauge7').jqxGauge('setValue', 0);
            $('#gauge8').jqxGauge('setValue', 0);
            $('#gauge9').jqxGauge('setValue', 0); */
            
            //query window
            
                var source = {
					datatype: "json",
					datafields : [{
						name : 'moId',
						type : 'string'
					},  {
						name : 'ip',
						type : 'string'
					}, {
						name : 'hostName',
						type : 'string'
					}, {
						name : 'businessSystem',
						type : 'string'
					},
					{
						name : 'typeId',
						type : 'string'
					} ],
					id : 'id'
					//url : '${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmId
				};
				
				function loadGridData(source){
					//getAlarmComments
					var adapter =new $.jqx.dataAdapter(source);
					// initialize jqxGrid
					$("#querygrid").jqxGrid({
						width : 750,
						height : 380,
						source : source,
						//localization : getLocalization(),
						//pageable : true,
						sortable : true,
						altrows : true,
						enabletooltips : true,
						//editable : true,
						//selectionmode : 'multiplecellsadvanced',
						columns : [ {
							text : 'moId',
							datafield : 'moId',
							width : 250,
							hidden:true
							
						},{
							text : 'IP',
							datafield : 'ip',
							width : 250
						}, {
							text : '设备名',
							datafield : 'hostName',
							width : 250
						}, {
							text : '业务系统',
							datafield : 'businessSystem',
							width :200
						},{
							text : 'type',
							datafield : 'typeId',
							hidden:true,
							width :200
						} ]
					});
				}
				
            $('#querybtn').bind('click', function() {
            	$("#querywindow").jqxWindow({
            		showCollapseButton : true,
            		isModal : true,
            		resizable : false,
            		width : 800,
            		maxHeight: 450, 
            		height: 450,
            		initContent : function() {
            			$('#querywindow').jqxWindow('focus');
            		}
            	});
            	var queryip=$("#queryip").val();
            	source.url='${ctx}/performance/queryPerformanceList?queryip='+queryip;
            	loadGridData(source);
            	// show the popup window.
            	/* $.get(ctx+'/updateUserPwd?account=', function(data) {
            	
            		
            	}); */
            	
            	$("#querywindow").jqxWindow('show');
            });

            $('#querygrid').on('rowdoubleclick', function (event) 
            		{ 
            	     var rowid=event.args.rowindex;
        		     var rowdata = $('#querygrid').jqxGrid('getrowdata',rowid);
        		     var moId=rowdata.moId;
        		     var typeId=rowdata.typeId;
						var businessSystem=rowdata.businessSystem;
						
						if(businessSystem==null){
							businessSystem="";
						}
						if(typeId=="HARDWARE_HP"){
							window.location.href='${ctx}/performance/goToHardwarePerformanceListView?moId='+moId+'&biz='+encodeURI(encodeURI(businessSystem));
						}
						if(typeId.indexOf("HOST_")!=-1){
							window.location.href='${ctx}/performance/goToHostPerformanceListView?moId='+moId+'&biz='+encodeURI(encodeURI(businessSystem));
						}
						if(typeId.indexOf("NETWORK_")!=-1){
							window.location.href='${ctx}/performance/goToNetworkPerformanceListView?moId='+moId+'&biz='+encodeURI(encodeURI(businessSystem));
						}
						if(typeId.indexOf("DATABASE_")!=-1){
							window.location.href='${ctx}/performance/goToDBPerformanceListView?moId='+moId+'&biz='+encodeURI(encodeURI(businessSystem));
						}
						
            		   
            });

        });
    </script> 

    
    <script type="text/javascript">
        $(function () {
           // $('#photoGallery').jqxScrollView({ width: '100%', height: 500, buttonsOffset: [0, 0], slideShow: false});
        });
    </script>
    

<script src="${ctx}/pages/avmon_back_gif.js"></script>    
</body>
</html>
