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
<title>列表视图</title>
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
/*update 20140918*/
/*弹出menu*/

#newWindow{
z-index:20002 !important;
}
.jqx-menu-wrapper{
z-index:18002 !important;
}
#jqxProgressBar1 .jqx-fill-state-pressed { 
background-color:#37bc29 !important; 
} 
#jqxProgressBar2 .jqx-fill-state-pressed { 
background-color:#0077dc !important; 
} 
#jqxProgressBar3 .jqx-fill-state-pressed { 
background-color:#eea025 !important; 
} 
#jqxProgressBar4 .jqx-fill-state-pressed { 
background-color:#e16725 !important; 
} 
#jqxProgressBar5 .jqx-fill-state-pressed { 
background-color:#e43e1e !important; 
} 

#jqxSystem .jqx-icon-arrow-down, #jqxSystem .jqx-icon-arrow-down-hover, #jqxSystem .jqx-icon-arrow-down-selected,
#jqxLocation .jqx-icon-arrow-down, #jqxLocation .jqx-icon-arrow-down-hover, #jqxLocation .jqx-icon-arrow-down-selected{
background-image: url('${ctx}/resources/images/icon-down-white.png');
background-repeat: no-repeat;
background-position: center;
}

#jqxSystem .jqx-icon-arrow-down, #jqxSystem .jqx-icon-arrow-down-hover, #jqxSystem .jqx-icon-arrow-down-selected,
#jqxLocation .jqx-icon-arrow-down, #jqxLocation .jqx-icon-arrow-down-hover, #jqxLocation .jqx-icon-arrow-down-selected{
background-image: url('images/icon-down-white.png');
background-repeat: no-repeat;
background-position: center;
}

.jqx-widget-header {
color:#FFFFFF;
background: #0096d6;
}

.jqx-grid-column-header, .jqx-grid-pager{
	color:#000000;	
	background: #E8F8FE !important;}

.jqx-tabs-title-selected-top {
color:#0096d6;
}

.jqx-progressbar-value{
background: #0b97d4 !important;
border-color: #0b97d4 !important;
	}	

.jqx-grid-column-menubutton{
background-color: #E8F8FE !important;
}
	
</style>
</head>

<body onload="myFunction()">
   <!-- 右键菜单 -->
			    
			      <div id="Menu" class="AvmonRightC" style="z-index:99999;display:none">
				  <ul>
					<li><span class="AvmonRightC_Icon R-icon_1"></span><span
						id="aknowledge" class="AvmonRightC_Name">认领</span></li>
					<li><span class="AvmonRightC_Icon R-icon_2"></span><span
						id="clear" class="AvmonRightC_Name">清除</span></li>
					<li><span class="AvmonRightC_Icon  R-icon_6"></span><span
						id="forward" class="AvmonRightC_Name">告警前转</span></li>
				  </ul>
			     </div> 
    <!-- jayjay -->
    <!-- 右键相关的菜单 -->
    <!-- 告警前转-->
			      <div style="width: 100%; height: 650px;display:none; margin-top: 50px;" id="mainDemoContainer">
            <div id="alarmforward">
                <div id="windowHeader" >
                    <span class="Icon16 Icon_GJQZ"></span><span class="f14px white paddingL20">告警前转</span>
                </div>
                
                <div style="margin:0px; padding:0px;">
                
                
                <div style="margin:10px;">                
                <div class="left">      
                  <div class="overlay-El-line"> 
                  <div style="margin-bottom:4px;">告警主机</div>
                  <div><input id="alarm_name" type="text" placeholder="" style="width:195px;" class=""></div> 
                  <!-- <div class="jqx-validator-error-label">请输入告警主机</div> -->
                  </div>
                  
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px;">告警指标</div>
                  <div><input id="alarm_index" type="text" placeholder="" style="width:195px;" class=""></div> 
                  <!-- <div class="jqx-validator-error-label">请输入告警指标</div> -->
                  </div>
                  
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px;">告警标题</div>
                 <div><input id="alarm_title" type="text" placeholder="" style="width:195px;" class=""></div> 
                 <!--  <div class="jqx-validator-error-label">请输入位置</div>    -->                
                  </div>
                  
                  
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px; display:inline-block;width:195px;">开始时间</div>
                  <div><input id="start_time" type="text" placeholder="2014-08-08" style="width:195px;"></div> 
                  <!-- <div class="jqx-validator-error-label">请选择开始时间</div>  -->                  
                  </div>                  
                  
                </div>

                <div class="left">  
                  <div class="overlay-El-line"> 
                  <div style="margin-bottom:4px;">告警内容</div>
                  <div><textarea id="forward_content" rows="6" placeholder="Enter text ..." style="width:280px; height:144px;"></textarea></div> 
                  <div class="AVmon-validator-label"></div>
                  </div>  
                  
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px;">告警级别</div>
                   <div><input id="alarm_level" type="text" placeholder="" style="width:195px;" class=""></div> 
                  <!-- <div class="jqx-validator-error-label">请选择告警级别</div> -->
                  </div>
                    
                </div> 
                    
                <div class="blank0"></div>

                </div>
                <div class="blank10"></div>                

                <div class="AvmonButtonArea">
                
                   <div class="left"><span id="forwardConfirm" class="AvmonOverlayButton">确定</span><span id="forwardCancel"  class="AvmonOverlayButton"> 取消</span></div>  
                   <div class="blank0"></div>
                   
                </div>
     
                </div>
            </div>
        </div>
              <!-- end -->


  <!-- 认领window -->
			<div style="width: 100%; height: 650px; display: none; margin-top: 50px;"
				id="mainDemoContainer">
				<div id="renling">
					<div id="windowHeader">
						<span class="Icon16  R-icon_1_white"></span><span id="windowtitle"
							class="f14px white paddingL20">认领</span>
					</div>
					<div>
						<div style="margin: 10px;">
							<div class="left">
								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">分类</div>
									<div id="cate" class="jqx-validator-error-element"></div>
									<!--  <div class="jqx-validator-error-label">请选择Category</div> -->
								</div>
							</div>
							<div class="blank0"></div>
							<div class="left">
								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">详细内容</div>
									<div>
										<textarea id="detailcontent" rows="6" placeholder="检查中..."
											style="width: 430px; height: 70px;">已处理</textarea>
									</div>
									<div class="AVmon-validator-label"></div>
								</div>
							</div>
							<div class="overlay-El-line">
								<div style="margin-bottom: 4px;">日志</div>
								<div id="log" class="AvmonMain"
									style="float: left; margin: 0px;">
									<div id="loggrid"></div>
								</div>
							</div>

						</div>
						<div class="blank10"></div>


						<div class="AvmonButtonArea">

							<div class="left">
								<span id="confirm" class="AvmonOverlayButton">确定</span><span
									id="cancel" class="AvmonOverlayButton">取消</span>
							</div>
							<div class="blank0"></div>

						</div>

					</div>
				</div>
			</div>
			<!-- end -->


 <!--高级查询 window open -->
			<div
				style="width: 100%; height: 350px; display: none; margin-top: 50px;"
				id="mainDemoContainer" style="display:none">
				<div id="searchwindow">
					<div id="windowHeader">
						<span class="Icon16 Icon_search"></span><span
							class="f14px white paddingL20">高级查询</span>
					</div>
					<div>
						<div style="margin: 10px;">
							<div class="left">
								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">设备名称</div>
									<div>
										<!-- <input id="s_moCaption" type="text" placeholder=""
											style="width: 195px;" class="jqx-validator-error-element"> -->
											<input id="s_moCaption" type="text" placeholder=""
											style="width: 195px;" >
									</div>
									<!-- <div class="jqx-validator-error-label">请输入设备名称</div> -->
								</div>

								<!-- <div class="overlay-El-line">
									<div style="margin-bottom: 4px;">业务系统</div>
									<div id="digits1" class="jqx-validator-error-element"></div>
									<div>
										<input id="s_attr_businessSystem" type="text" placeholder=""
											style="width: 195px;" class="">
									</div>
									<div class="jqx-validator-error-label">请输入业务系统</div>
								</div> -->

								<!-- <div class="overlay-El-line">
									<div style="margin-bottom: 4px;">位置</div>
									<div>
										<input id="s_attr_position" type="text" placeholder=""
											style="width: 195px;" class="">
									</div>
									<div class="jqx-validator-error-label">请输入位置</div>
								</div> -->
							</div>

							<div class="left">
								<!-- <div class="overlay-El-line">
									<div style="margin-bottom: 4px;">IP地址</div>
									<div>
										<input id="s_attr_ipaddr" type="text"
											placeholder="" style="width: 195px;"
											class="">
									</div>
									<div class="jqx-validator-error-label">请输入IP地址</div>
								</div> -->

								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">处理人</div>
									<!--  <div id="digits2" class="jqx-validator-error-element"></div>  -->
									<div>
										<input id="s_aknowledge_by" type="text" placeholder=""
											style="width: 195px;" class="">
									</div>
									<!-- <div class="jqx-validator-error-label">请输入处理人</div> -->
								</div>

							</div>


							<div class="blank0"></div>


							<div class="left">
								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">告警内容</div>
									<div>
										<textarea id="s_content_t" rows="6"
											placeholder=""
											style="width: 430px; height: 50px;"></textarea>
									</div>
									<div class="AVmon-validator-label"></div>
								</div>
							</div>
							<div class="left">
								<div class="overlay-El-line">
									<div
										style="margin-bottom: 4px; display: inline-block; width: 217px;">开始时间</div>
									<div style="margin-bottom: 4px; display: inline-block">结束时间</div>
									<div>
										<!-- <input id="s_start_time" type="text" placeholder=""
											style="width: 210px;">--<input id="s_end_time"
											type="text" placeholder="" style="width: 206px;">  -->
											<div><div id="start_date" class="left"></div>
											<div  class="left">--</div><div id='end_date' class="left"></div></div>
											
											 <!-- <div class="left" id='start_date'> --  --><!-- <div class="left" id='end_date'>   -->
									</div>
								</div>
							</div>

						</div>
						<div class="blank10"></div>
						<div class="AvmonButtonArea">
							<div class="left">
								<span id="search" class="AvmonOverlayButton">查询</span><span
									id="searchcancel" class="AvmonOverlayButton"> 取消</span>
							</div>
							<div class="blank0"></div>
						</div>
					</div>
				</div>
			</div>

			<!-- end -->




          
                       
                   
    
    
    <!-- 右键相关的菜单end -->
<div id="wrap"> 
  
  <!-- Header
      ================================================== -->
 <%--      <div id="top" class="top">
    <div class="fixed">
      <a href="${ctx}/main"><div class="LOGO"></div></a>
                                <a href="${ctx}/performance/overview"><div class="Avmon_back"></div></a>
  
      <div class="MainTitle f28px LineH12 left">性能中心</div>
      <div class="left Padding10"><div id="bizList" class="left" style="background-color: #0096d6; color:#ffffff; border:0px;"></div></div>
      
      <div class="right">
      <div class="Profile" id="ShowUserBoxclick" style="cursor:pointer">
      <div class="ProfileIcon"></div>
      <div class="ProfileName ">${userName}</div>
      <div class="ProfileArrow"></div>
      <div class="blank0"></div>
       </div>
      </div>
      
      <div class="right">
      <div  class="SearchPerformanceIpt"> <b class="ico ico-search"></b>
      <input id="ipaddress" class="SearchPerformanceformIpt SearchPerformanceIpt-focus" tabindex="1" type="text" maxlength="50" value="" autocomplete="on">
      <a id="queryIP" href="#" class="SearchPerformanceIpt-btn">查询</a> </div>
      </div>
      </div>
  
  </div>
  
  </div> --%>
<div id="top" class="top">
    <div class="fixed"> 
  <%--   <a href="${ctx}/main">
      <div class="LOGO"></div>
      </a> --%> 
      
		<a href="${ctx}/performance/overview">
			<div class="Avmon_back_gif">
				<img id="Avmon_back_img" src="${ctx}/resources/images/back.png" 
					onmouseover="mouseover('${ctx}');" onmouseout="mouseout('${ctx}');">
			</div>
		</a>
		
      <div class="MainTitle f28px LineH12 left" id="ShowTitleMenuclick" style="cursor:pointer">性能中心</div>
      <div class="left Padding10"><div id="bizList" class="left" style="background-color: #0096d6; color:#ffffff; border:0px;"></div></div>

<!-- 
	修改：各子系统的账户信息去掉，所有的登出统一在首页操作
	BY：	lixinqi
	DATA：20150202
 -->
<%-- <div class="right">
      <div class="Profile" id="ShowUserBoxclick" style="cursor:pointer">
        <div class="ProfileIcon"></div>
        <div class="ProfileName ">${userName}</div>
        <div class="ProfileArrow"></div>
        <div class="blank0"></div>
      </div> 
</div> --%>


<div class="right">
       <div  class="SearchPerformanceIpt"> <b class="ico ico-search"></b>
        <input id="ipaddress" class="SearchPerformanceformIpt SearchPerformanceIpt-focus" tabindex="1" type="text" maxlength="50" value="" autocomplete="on">
        <a id="queryIP" href="#" class="SearchPerformanceIpt-btn">查询</a>
      </div>
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

    <div class="content" style=" padding:2px;">
    
<div>
		<ul  class="bxslider">
			


            
		<li>
<!-- Content box-05
      ================================================== -->  
    <div id="NETWORK_FIREWALL" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/Firewall_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">NETWORK FIREWALL</span><span id="networktotal" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="network-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="network-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="network-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="network-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="network-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>
   		<li>
<!-- Content box-06
      ================================================== -->  
    <div id="NETWORK_SWITCH" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/Switch_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">NETWORK SWITCH</span><span id="networktotal1" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="network1-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="network1-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="network1-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="network1-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="network1-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>
    	<li>
<!-- Content box-06
      ================================================== -->  
    <div id="NETWORK_LOADBALANCER" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/JHFZ_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">LOADBALANCER</span><span id="networktotal2" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="network2-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="network2-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="network2-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="network2-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="network2-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>
       	<li>
<!-- Content box-06
      ================================================== -->  
    <div id="NETWORK_ROUTER" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/LYQ_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">NETWORK ROUTER</span><span id="networktotal3" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="network3-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="network3-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="network3-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="network3-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="network3-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>
            
            	<li>
<!-- Content box-06
      ================================================== -->  
    <div id="NETWORK_UNKNOWN" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/unknown.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">UNKNOWN</span><span id="networktotal4" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="network4-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="network4-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="network4-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="network4-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="network4-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>

		</ul>
</div>
     
      <div id="jqxgrid"> </div> 
      <div id="qqqq" style="display:none"> 
      <!-- qinjie -->
      
       <div style="width:100%; height:410px;">
      
      
      <table id="overviewtable" width="100%" border="0" cellspacing="0" cellpadding="0" class="Performance_tabl">
    <tr>
    <td></td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
     <td>&nbsp;</td>
   <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
     <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
    <tr>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
   <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
     <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
   <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
     <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
   <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
     <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
<tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  <tr>
      <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
  
  
</table>

      
      </div>

      </div>
       
      <div class="blank0"></div> 
      <div class="PerformanceBottom">
      <ul id="datacenter">
         <li id="all"><a href="#"><span class="Icon16 viewA"></span><span class="listname">全局视图</span></a></li>
         <li id="list" class="active"><a href="#"><span class="Icon16 viewL"></span><span class="listname">列表视图</span></a></li>
      </ul>
      </div>
      
    </div>
  </div>
</div>
</div>

<!-- window begin -->
 <div style="width: 100%;margin-top: 50px;" id="mainDemoContainer">
        <div id="window">
          <div id="windowHeader" style="display:none; height:0px !important;"> </div>
          <div style="overflow: hidden;" id="windowContent">
            <div id="tab">
              <ul style="margin-left: 30px;">
                <li>详细信息</li>
                <li>KPI视图</li>
                <li>KPI趋势查询</li>
                <li>告警列表</li>
                <div id="closeIcon" onclick="closeFun()" class="Avmon-white-close Icon16 right"></div>
              </ul>
              <div style="margin:4px">
                <div class="left baseBox2"><!-- 第一列--> 
                  
                  <!-- 第一列—box1
    							  ================================================== -->
                  <div class="BaseInfoPerMain" style="height:70px;">
                    <div class="left"><img src="${ctx}/resources/images/CiSCO_icon.png" width="32" height="32"></div>
                    <div class="left BasePerBoxilo" style="height:60px">
                      <p class="fB f14px" id="sysName"></p>
                      <p id="sysIP"></p>
                      <ul>
                        <li><span class="title">设备型号</span><span class="content" id="sysObjectID"></span></li>
                        <li><span class="title">软件版本</span><span class="content" id="sysDescr"></span></li>
                      </ul>
                    </div>
                    <div class="blank0"></div>
                  </div>
                  
                  <!-- 第二列—网络设备正面
    							  ================================================== -->
                 <table width="100%" border="0" cellspacing="0" cellpadding="0" style="border:1px solid #a7a7a7">
  <tr>
    <td width="5%" style="border:1px solid #a7a7a7;background-color:#eeeeee; vertical-align:top"><div class="BasePerBoxilo-left-box"></div>
    <div class="Network_BG"></div></td>
    <td> <div class="Network_content" id="Network_content">
                             <!--  <dl>
        					    <dt><span class="black">001</span></dt>
                                <dd>
                                <b class="Network_available"></b>
								</dd>
        					  </dl>
                              <dl>
        					    <dt><span class="red">007</span></dt>
                                <dd>
                                <b class="Network_unavailable"></b>
								</dd>
        					  </dl> -->
                        </div>
     </td>
    <td width="140px" style="border:1px solid #a7a7a7;background-color:#eeeeee; vertical-align:top"><div style=" margin-top:10px; margin-left:20px; margin-right:20px;">
                          <div style="margin-top:26px;">
                          <div class="inline-block marginB15"><b class="Network_available"></b><span class="marginL8 fB" style="vertical-align: top;">已使用</span></div>
                          <div class="inline-block"><b class="Network_unavailable"></b><span class="marginL8 fB" style="vertical-align: top;">未使用</span></div>
                          
                          </div>
                          <div class="right" style="margin-right:16px"><img src="${ctx}/resources/images/CiSCO_icon.png" width="32" height="32"></div>
                        </div></td>
  </tr>
</table>
                 
   <div class="blank20"></div>                              
                                  
  <div id="organic-tabs" class="BaseInfoPerMain" style="height:320px;">
					
    		<ul id="explore-nav-Network">
                <li id="ex-titleport"><a rel="titleport" href="#" class="current"><span>端口配置信息</span></a></li>
                <li id="ex-titleportimport"><a rel="titleportimport" href="#"><span>端口出流量</span></a></li>
                <li id="ex-titleportexport"><a rel="titleportexport" href="#"><span>端口入流量</span></a></li>
            </ul>
    		
    		<div id="all-list-wrap" style="width:100%;">
    		
    			<ul id="titleport" style="overflow-y: hidden;padding:4px 1px;">
    			   <div id="portConfigGrid"> </div>
    			</ul>
        		 
        		 <ul id="titleportimport" style="overflow-y: hidden;padding:4px 1px;">
    			
    			<div id="portChart1" style="height: 241px;">
								<center ></center>
							</div>
        		 </ul>
        		 
        		 <ul id="titleportexport" style="overflow-y: hidden;padding:4px 1px;">
                  
                  <div id="portChart2" style="height: 241px;">
								<center ></center>
							</div>
        		 </ul>
        		 
        		 
    		 </div> <!-- END List Wrap -->
		 
		 </div>                                
                                  
                  
                  
                  
                </div>
                 
              </div>
              <div> 
                           <!--section two-->
                           <div class="KPINav">
        	                  <div class="left marginR15" id="autoRefresh"><a href="#"  class="Avmon-button"><b class="R-icon_4 Icon16" style="position:relative;top:3px;"></b>自动刷新</a></div>
         	                  <div class="left" id="manuRefresh"><a href="#"  class="Avmon-button"><b class="R-icon_1 Icon16" style="position:relative;top:3px;"></b>手动刷新</a></div>
                          </div>
                          <div class="KPIlist">
                             <div id="KPIlist">Loading</div>
                            <!--  <H3>Group Name</H3>
                             <dl>
         						 <dt><b class="R-icon_3 Icon16" style="position:relative;top:3px;"></b>005-syslog error message(005)</dt>
         						 <dd>
         						    <div id="jqxgrid5">aaaaaaaaaaaaaaa </div>
         						 </dd>
        					 </dl>
                             <dl>
         						 <dt><b class="R-icon_3 Icon16" style="position:relative;top:3px;"></b>013-Host swap information(013)</dt>
         						 <dd>
         						    <div id="jqxgrid3"> bbbbbbbbbbbbbbb</div>
         						 </dd>
        					 </dl>          -->                
                          
                          </div> 
                          <!-- end -->
                        </div>
                        
                        <div>
                             <div class="KPINav">
                          	  <div class="left marginR15"><span class="LineH24 marginR40 left">提醒：KPI指标最多能选择5个。
                          	  </span><span class="LineH24 marginR6 left">Host:</span><span class="LineH24 marginR40 left" id="hostip">
                          	  </span><span id="jqxWidgetData1" class="left marginR40"></span> <span class=" LineH24 marginR6 left">
                          	  Time:</span><span class="LineH24 marginR6 left"><div id="startDate"></div></span>
                          	  <span id="startHour" class="left marginR6"></span>
                          	  <!-- <input type="text" placeholder="2014-08-08" style="width:120px; height:26px;"> -->
                          	  <!-- <span id="jqxWidgetData2" class="left marginR6"></span> -->
                          	  <span class="LineH24 marginR6 left">To</span>
                          	  <span class="LineH24 marginR6 left"><div id="toDate"></div>
                          	  <!-- <input type="text" placeholder="2014-08-08" style="width:120px; height:26px;"> --></span><span id="endHour" class="left marginR15"></span></div>
        	                  <div class="left marginR15" id="queryKpi"><a href="#"  class="Avmon-button"><b class="Search_icon2 Icon16" style="position:relative;top:3px;"></b>查询</a></div>

                          </div>
                          <div>
                      		   <div class="W20 left marginL10">
                                  <p class="f16px marginB10 marginT10">指标列表</p>
                      		      <div id="kpigrid" ></div>
                       		  </div>
                       		  <div class="W76 left marginL20">
                                  <p class="f16px marginB10 marginT10">KPI历史趋势</p>
                                  <div style="height:90%; width:100%;overflow-y: scroll;">
                                  <div id="chart1" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:20px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart2" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart3" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart4" style="border:1px solid #f4f4f4; width:100%; height:55%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart5" style="border:1px solid #f4f4f4; width:100%; height:55%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  </div>
                                  
                                  
                              </div>  
                          </div>                      
                         </div>
                        
                        <div  class="margin10">
                       <!--    <p>右键菜单
                             </p> -->
                           <div class=" paddingL4">
					     <div class="left paddingL4 NavMainBtn">
							<a href="#" class="Avmon-button" id="showWindowButton">高级查询</a>
						</div>
						<div class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><span id="batchupdate">批量更新告警状态</span></a>
						</div> 
					<div class="blank20"></div>
						
					    </div>
                             <div id="alarmGrid"> </div>

                         </div>
                       
                    </div>
                 
                </div>
            </div>

            
        </div>
<!-- window end -->
</body>
</html>
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
            <%-- <script type="text/javascript" src="${ctx}/resources/js/jquery.circliful.min.js"></script> --%>
            <script type="text/javascript" src="${ctx}/resources/js/excanvas.js"></script>
            <script type="text/javascript" src="${ctx}/resources/js/jquery.easy-pie-chart.js"></script>

<script type="text/javascript">
$('.bxslider').bxSlider({
 minSlides: 3,
 maxSlides: 6,
 moveSlides: 1,
 slideWidth: 320,
 slideMargin: 10,
 infiniteLoop: false,
 hideControlOnEnd: true,
});
        $(document).ready(function () {
        	//公共部分
         	var alarmIdList = [];
			var moId = "";
			var alarmId = "";
        	var networktotal=0;
        	var networktotal1=0;
        	var networktotal2=0;
        	var networktotal3=0;
        	var networktotal4=0;
        	var hostip="";
        	var num=1;
        	var batchFilter="";
        	var isClickSearch=false;
        	 //获取url参数
            $.getUrlParam = function(name){
                var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
                var r = location.search.substr(1).match(reg);
              if (r!=null) return unescape(r[2]); return null;
              };
            $("ul#datacenter li").click(function(){
            	$(this).addClass("active").siblings().removeClass("active"); 
    	        typeId=$(this)[0].id;
    	        if(typeId=="all"){
    	        	$("#jqxgrid").hide();
    	        	$("#qqqq").show();
    	        }
    	        else{
    	        	$("#qqqq").hide();
    	        	$("#jqxgrid").show();
    	        }
            });
       	     var hours = ['00:00', '01:00', '02:00', '03:00',
                 '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00','11:00','12:00','13:00',
                 '14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00'
               ];
       	   var grainsizes = [
             	               "原始采集数据",
                                "按小时采集",
                                "按天采集",
            		        ];
            //end
            
        	document.onreadystatechange = doCompleteLoad;//当页面加载状态改变的时候执行这个方法.
        	function doCompleteLoad()
        	{
        	setTimeout(function(){
        		regClick();
 			},5000);
            function regClick(){
            	 if(document.readyState == "complete"){ //当页面加载状态为完全结束时进入
            		 //双击显示dashboard的代码
                     $("#overviewtable").find("td").each(function(){ 
                 	       	if($(this).attr("class")!=undefined){
                 	       		$("#"+$(this).attr("id")).click(function () {
                       		 moId=$(this).attr("moId");
                       		 $("#hostip").text(hostip);
                       		 loadNestGrid(moId);
                   

                       	  $.getJSON("${ctx}/pages/dashboard/network/getNetWorkHostBasicInfo?moId="+moId,
                					function(data) {
                  	    	 $("#sysName").text(data.basicData[0].VAL);
                	    		 $("#sysIp").text("IP: "+data.basicData[1].VAL);
                	    		 $("#sysObjectID").text(data.basicData[2].VAL);
                	    		 $("#sysDescr").text(data.basicData[3].VAL);
                  	       });
                          $.getJSON("${ctx}/pages/dashboard/network/getNetworkInPortList?moId="+moId ,
                					function(data) {
                  	    	 var $html;
                  	    	 if(data.length>0){
                  	    		  for(var i=0;i<data.length;i++){
                  	    			  if(data[i].ifOutOctets==0&&data[i].ifInOctets==0){
                  	    			  $html=$('<dl><dt><span class="red">'+data[i].ifName+'</span></dt><dd><b class="Network_unavailable"></b></dd> </dl>'); 
                  	    			  $("#Network_content").append($html);
                  	    			  }
                  	    			  else{
                  	    				  $html=$('<dl><dt><span class="black">'+data[i].ifName+'</span></dt><dd><b class="Network_available"></b></dd> </dl>'); 
                      	    			  $("#Network_content").append($html);
                  	    			  }
                  	    		  } 
                  	    	 }
                  	     });
                  	     
                  	   $.jqx._jqxChart.prototype.colorSchemes.push({ name: 'myScheme', colors:['#2cc185','#f06050','#e74c39','#f39c12','#8e44ad'] });
                         $.jqx._jqxChart.prototype.colorSchemes.push({ name: 'levelScheme', colors:['#37bc29','#0077dc','#eea025','#e16725','#e43e1e'] });
                         //port1 chart
                         var source1 =
                     		        {
                     		            datatype: "json",
                     		            datafields: [
                     		                {name: 'total' },
                     		                {name: 'value'}
                     		            ],
                     		            url: '${ctx}/pages/dashboard/network/getNetworkInPortList?moId='+moId
                     		        };		
                     		        var dataAdapter1 = new $.jqx.dataAdapter(source1, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source1.url + '" : ' + error);} });
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
                     		                    /*  formatFunction: function (value) {
                     		                    	  if(value.length<12){
                     		                    		 return value;
                     		                    	 }
                     		                    	 var firstPart=value.substr(0, 12);
                     		                         return firstPart+"<br>"+value.substr(12); 
                     		                    },  */
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
                     		                        //click: myEventHandler,
                     		                        seriesGapPercent: 5,
                     		                        columnsGapPercent: 25,
                     		                        valueAxis:
                     		                        {
                     		                            unitInterval: 100,
                     		                            displayValueAxis: true,
                     		                            description: '告警数量'
                     		                        },
                     		                        series: [
                     		                               { dataField: 'total', displayText: 'OutPort'}//, color: 'yellow'}  
                     		                        ]
                     		                    }

                     		                ]
                     		        };
                     	        // setup the chart
                     	       $('#portChart1').jqxChart(settings1); 
                     		   //end
                     		   
                     		   //prot2 chart
                     		   var source2 =
                     		        {
                     		            datatype: "json",
                     		            datafields: [
                     		                {name: 'total' },
                     		                {name: 'value'}
                     		            ],
                     		            url: '${ctx}/pages/dashboard/network/getNetworkOutPortList?moId='+moId
                     		        };		
                     		        var dataAdapter2 = new $.jqx.dataAdapter(source2, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source2.url + '" : ' + error);} });
                     		        var records2=dataAdapter2.records;
                     		        var settings2 = {
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
                     		            source: dataAdapter2,
                     		            categoryAxis: {
                     		                    dataField: 'value',
                     		                    unitInterval: 1,
                     		                    tickMarksInterval: 1,
                     		                    gridLinesInterval: 1,
                     		                    showGridLines: false,
                     		                    //textRotationAngle: -45,
                     		                    //axisSize:1,
                     		                    /* formatFunction: function (value) {
                     		                    	  if(value.length<12){
                     		                    		 return value;
                     		                    	 }
                     		                    	 var firstPart=value.substr(0, 12);
                     		                         return firstPart+"<br>"+value.substr(12); 
                     		                    },  */
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
                     		                        //click: myEventHandler,
                     		                        seriesGapPercent: 5,
                     		                        columnsGapPercent: 25,
                     		                        valueAxis:
                     		                        {
                     		                            unitInterval: 100,
                     		                            displayValueAxis: true,
                     		                            description: '告警数量'
                     		                        },
                     		                        series: [
                     		                                { dataField: 'total', displayText: 'InPort'}//, color: 'yellow'}  
                     		                        ]
                     		                    }

                     		                ]
                     		        };
                     	        // setup the chart
                     	       $('#portChart2').jqxChart(settings2); 
                     		   //end

                           //portConfigGrid
                  	   var portConfigSource = {
          						datatype : "json",
          						datafields : [ {
          							name : 'ID',
          							type : 'string'
          						}, {
          							name : 'MO_ID',
          							type : 'string'
          						},
          						{
          							name : 'type',
          							type : 'string'
          						},
          						 {
          							name : 'ifIndex',
          							type : 'string'
          						}, {
          							name : 'ifName',
          							type : 'string'
          						}, {
          							name : 'ifAlias',
          							type : 'string'
          						}, {
          							name : 'ifType',
          							type : 'String'
          						},
          						{
          							name : 'ifSpeed',
          							type : 'String'
          						},
          						{
          							name : 'ifPhysAddress',
          							type : 'string'
          						}, {
          							name : 'ipAdEntAddr',
          							type : 'string'
          						}, {
          							name : 'ipAdEntNetMask',
          							type : 'string'
          						},{
          							name : 'ifDescr',
          							type : 'string'
          						}, {
          							name : 'ifOutOctets',
          							type : 'string'
          						},
          						{
          							name : 'ifInOctets',
          							type : 'string'
          						}
          						],
          						beforeprocessing : function(data) {
          							if (data.length == 0) {
          								portConfigSource.totalrecords = 0;
          							} else {
          								portConfigSource.totalrecords = data[data.length - 1].totalcount;
          							}

          						},
          						
          						sort : function() {
          							// update the grid and send a request to the server.
          							$("#portConfigGrid").jqxGrid(
          									'updatebounddata');
          						},
          						id : 'id',
          						url : '${ctx}/pages/dashboard/network/getNetworkPortConfigList?moId='+moId
          					};
          					loadPortConfigGridData(portConfigSource);
          					////end
                       	    var source = {
               						datatype : "json",
               						datafields : [ {
               							name : 'ID',
               							type : 'string'
               						}, {
               							name : 'MO_ID',
               							type : 'string'
               						}, 
               						 {
               							name : 'KPI_CODE',
               							type : 'string'
               						}, {
               							name : 'LEVEL',
               							type : 'string'
               						}, {
               							name : 'STATUS',
               							type : 'string'
               						}, {
               							name : 'AMOUNT',
               							type : 'String'
               						},
               						{
               							name : 'OCCUR_TIMES',
               							type : 'String'
               						},
               						{
               							name : 'MO_CAPTION',
               							type : 'string'
               						}, {
               							name : 'CONTENT',
               							type : 'string'
               						}, {
               							name : 'AMOUNT',
               							type : 'string'
               						}

               						],
               						beforeprocessing : function(data) {
               							if (data.length == 0) {
               								source.totalrecords = 0;
               							} else {
               								source.totalrecords = data[data.length - 1].totalcount;
               							}

               						},
               						updaterow : function(rowid, rowdata, commit) {
               							var url = '${ctx}/pages/alarm/aknowledge?content='
               									+ rowdata.attr_result
               									+ '&alarmId=' + rowdata.id;
               							$.ajax({
               								cache : false,
               								dataType : 'json',
               								url : url,
               								success : function(data, status,
               										xhr) {
               									//alert("success");
               								},
               								error : function(jqXHR, textStatus,
               										errorThrown) {
               									//alert(errorThrown);
               									//commit(false);
               								}
               							});
               							commit(true);
               						},
               						sort : function() {
               							// update the grid and send a request to the server.
               							$("#alarmGrid").jqxGrid(
               									'updatebounddata');
               						},
               						id : 'id',
               						url : '${ctx}/performance/getAlarmData?moId='+moId
               					};

               					function loadGridData(source) {

               						var statusimagerenderer = function(row,
               								datafield, value) {
               							if(value!=0){
               								return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/confirmed.gif"/>';
               							}
               							return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/newalarm.gif"/>';
               						};
               						var levelimagerenderer = function(row,
               								datafield, value) {
               							return '<img style="margin-left: 10px;padding-top:5px" height="15" width="15" src="${ctx}/resources/images/level' + value + '.png"/>';
               						};
               						var dataAdapter = new $.jqx.dataAdapter(
               								source);
               						$("#alarmGrid").jqxGrid({
               							width : '100%',
               							height : '80%',
               							source : dataAdapter,
               							columnsresize : true,
               							localization : getLocalization(),
               							pageable : true,
               							pagesize : 20,
               							//filterable: true,
               							sortable : true,
               							//page
               							virtualmode : true,

               							 rendergridrows : function() {
               								return dataAdapter.records;
               							}, 
               							selectionmode : 'checkbox',
               							altrows : true,
               							enabletooltips : true,
               							columns : [ {
               								text : '编号',
               								datafield : 'ID',
               								width : 10,
               								hidden : true
               							}, {
               								text : 'MO_ID',
               								datafield : 'mo_id',
               								width : 10,
               								hidden : true
               							}, {
               								text : 'KPI_CODE',
               								datafield : 'KPI_CODE',
               								width : 10,
               								hidden : true
               							}, {
               								text : '级别',
               								datafield : 'LEVEL',
               								width : 40,
               								cellsrenderer : levelimagerenderer
               							}, {
               								text : '状态',
               								datafield : 'STATUS',
               								width : 40,
               								cellsrenderer : statusimagerenderer
               							}, {
               								text : '主机名',
               								datafield : 'MO_CAPTION',
               								width : 150
               							},
               							 {
               								text : '告警内容',
               								datafield : 'CONTENT',
               								minwidth : 150
               							},{
               								text : '时间',
               								datafield : 'OCCUR_TIMES',
               								minwidth : 50
               							}, {
               								text : '次数',
               								datafield : 'AMOUNT',
               								minwidth : 20
               							} ]
               						});
               					}
               					
               					loadGridData(source);
               					//右键相关的 在这里
               					
               					//end
               			 var kpilistsource =
                               {
               			    datatype: "json",
                               datafields:
                               [
                                   { name: 'kpi', type: 'string'},
                                   { name: 'displayKpi', type: 'string'}
                               ],
                               id : 'kpi',
               				url : '${ctx}/config/kpiListNew?moId='+moId
                               
                            };
                            var kpilistdataAdapter = new $.jqx.dataAdapter(kpilistsource);

                            $("#kpigrid").jqxGrid(
                           {
                               width: '100%',
               				   height:400,
                               source: kpilistdataAdapter,
                               sortable : true,
                               //editable: true,
                               selectionmode : 'checkbox',
               				altrows : true,
                               columns: [
                                 { text: 'KPI', editable: 'available', datafield: 'kpi', width: '90%',hidden:true},
                                 { text: 'KPI', editable: 'available', datafield: 'displayKpi', width: '90%'} 
                               ]
                           }); 
                       	   $('#window').jqxWindow('open');
                 	       		});
                 	      } 
                 	 }); 
            	//双击部分的代码end 
                 	} 
             }
        	}
        
            //拦截部分的代码           
        	$("#jqxWidgetData1").jqxDropDownList({ source: grainsizes, selectedIndex: 0,  width: '130px', height: '25px',dropDownHeight: '90' });
            $("#startDate").jqxDateTimeInput({width: '160px', readonly: true,height: '25px',formatString: 'yyyy-MM-dd'});
        	$("#toDate").jqxDateTimeInput({width: '160px', readonly: true,height: '25px',formatString: 'yyyy-MM-dd'});
        	$("#startHour").jqxDropDownList({ source: hours, selectedIndex: 0,  width: '70px', height: '25px' });
        	$("#endHour").jqxDropDownList({ source: hours, selectedIndex: 0,  width: '70px', height: '25px' });
        	$("#queryKpi").click(function() {
        		//queryKpi begin
         		var textRotationAngle=0;
        		var rowindexes = $('#kpigrid').jqxGrid('getselectedrowindexes');
        		if(rowindexes.length==0){
       			 $("#newWindow").remove();
	        		   $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>请选择一条KPI！ </div></div>');
	                   $('#newWindow').jqxWindow({isModal : true, height: 80, width: 150});
	                   return;  
       		  }
        		 if(rowindexes.length>5){
        			 $('#kpigrid').jqxGrid('clearselection');
  			        $("#newWindow").remove();
         		   $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>KPI最多只能选择5条记录！ </div></div>');
                    $('#newWindow').jqxWindow({ isModal : true,height: 80, width: 150});
                    return;
         		   }
        		 var startHourTemp =$('#startHour').jqxDropDownList('getSelectedItem');
   	            var endHourTemp =$('#endHour').jqxDropDownList('getSelectedItem');
                	var grainsize = $("#jqxWidgetData1").jqxDropDownList('getSelectedIndex')+1;
      	    	    var startTime = $('#startDate').jqxDateTimeInput('getText');
      	     	    var endTime = $('#toDate').jqxDateTimeInput('getText');
      	     	    startTime=startTime+" "+startHourTemp.label;
          	        endTime=endTime+" "+endHourTemp.label;
          	       for(var i=0;i<rowindexes.length;i++){
             			var row = $("#kpigrid").jqxGrid('getrowdata',rowindexes[i]);
             			var yAxis=row.displayKpi;
             			var kpiCode=row.kpi;
              			//var insAndcode=row.kpi.split("/");
              			//var ampInstId=insAndcode[0];
              			//var kpiCode=insAndcode[1];
             			
             			var baseUnit='';
             			$("#chart"+(i+num)).show();
             			 if(grainsize==3){
             				baseUnit='day';
             				textRotationAngle=-75;
             			}
             			else if(grainsize==2){
             				baseUnit='hour';
             				textRotationAngle=-75;
             			}
             			else{
             				baseUnit='minute';
             				textRotationAngle=-75;
             			}
             			loadTrendChart("",kpiCode,startTime,endTime,moId,grainsize,"chart"+(i+num),yAxis,baseUnit);
             			
             		}
          	     function loadTrendChart(ampInstId,kpiCode,startTime,endTime,moId,grainsize,id,yAxis,baseUnit){
                     var ampInstId="";
            		   var url='${ctx}/config/dynamicKpiChartData?ampInstId='+ampInstId+'&kpiCode='+kpiCode+'&startTime='+startTime+'&endTime='+endTime+'&moId='+moId+'&grainsize='+grainsize;
         			  //////////////
                     var data1 = new Array();
                     var datafields = new Array();
                     var instanceTemp;
                     var count=0;
                       $.blockUI({
                		    message: 'Loading...',
                		    css: { 
                		    width:	'10%',
                		    left: '40%',
          		            border: 'none', 
          		            padding: '15px', 
          		            backgroundColor: '#000', 
          		            '-webkit-border-radius': '10px', 
          		            '-moz-border-radius': '10px', 
          		            opacity: .5, 
          		            color: '#fff' },
          		            onBlock: function() { 
          		            	  $.getJSON(url,function(data) {
          		            		 $("#chart1").empty();
          		            		$("#chart2").empty();
          		            		$("#chart3").empty();
          		            		$("#chart4").empty();
          		            		$("#chart5").empty();
          		             	for (var key in data[0]) 
                              	{
                              	   count++;
                              	}
          		             	if(count>=0&&count<=3){
                               	    var row = {};
                        	           instanceTemp="instance";
                        	           row["dataField"] = instanceTemp;
                        	           row["displayText"] ="All";
                        	           data1[0] = row;
                        	        var row1 = {};
                        	       row1["name"] = "instance";
                      	           datafields[0] = row1;
                                 } else{
                                	 var count1=0;

                        	           for (var key in data[0]) 
                                      	{
                        	        	   var row = {};
                            	           var row1 = {};
                        	        	   count1++;
                  		                if(count1<=count-2){
                  		                  instanceTemp="instance"+i;
                           	              row["dataField"] = key;
                           	              row["displayText"] =key;
                           	              data1.push(row);
                  		             	
                  		             	  row1["name"] = key;
                        	               datafields.push(row1);
                  		                }
                                      	}  
                                 }
                                var row2 = {"name":"time"};
                                datafields[count-2]=row2;
                               var trendsource =
                               {
                                   datatype: "json",
                                  /*  datafields: [
                                       { name: 'time' },
                                       { name: 'instance' }
                                   ], */
                                   datafields:datafields,
                                   url:url 
                               };

                               var trenddataAdapter = new $.jqx.dataAdapter(trendsource, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
                               var months = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
                               var settings = {
                                       title: "",
                                       description: "",
                                       enableAnimations: true,
                                       showLegend: true,
                                       padding: { left: 5, top: 5, right: 11, bottom: 5 },
                                       titlePadding: { left: 10, top: 0, right: 0, bottom: 10 },
                                       source: trenddataAdapter,
                                       xAxis:
                                           {
                                               dataField: 'time',
                                               formatFunction: function (value) {
                                                   return months[value.getMonth()] + '-' + value.getDate()+' '+value.getHours()+':'+value.getMinutes()+':'+value.getSeconds();
                                               },
                                               toolTipFormatFunction: function (value) {
                                                   return value.getFullYear() + '-' + months[value.getMonth()] + '-' + value.getDate()+' '+value.getHours()+':'+value.getMinutes()+':'+value.getSeconds();
                                               },
                                               showTickMarks: true,
                                               type: 'date',
                                               baseUnit: baseUnit,
                                               tickMarksInterval: 1,
                                               tickMarksColor: '#888888',
                                               unitInterval: 1,
                                               textRotationAngle: textRotationAngle,
                                               showGridLines: false,
                                               gridLinesInterval: 1,
                                               gridLinesColor: '#888888',
                                             
                                               valuesOnTicks: true
                                           },
                                       colorScheme: 'scheme01',
                                       seriesGroups:
                                           [
                                               {
                                                   type: 'stackedline',
                                                   valueAxis:
                                                   {
                                                    
                                                       minValue: 0,
                                                       description: yAxis,
                                                       tickMarksColor: '#888888'
                                                   },
                                                   /* series: [
                                                           { dataField: 'instance', displayText: 'kpi trend' }
                                                       ] */
                                                       series:data1
                                               }
                                           ]
                                   };
                                 
                                   $('#'+id).jqxChart(settings);
          		            	$.unblockUI();
          		            	  });
           	            } 
                       }); 
     		        }
          	      //queryKpi end
        		
        	    });
        	 //拦截部分的代码end
        	 
        	//dashboard部分的告警右键处理 
			$("#alarmGrid").on('rowclick',
					function(event) {
						if (event.args.rightclick) {
							$('#alarmGrid').jqxGrid('clearselection');
							$("#alarmGrid").jqxGrid('selectrow',event.args.rowindex);
							var scrollTop = $(window).scrollTop();
							var scrollLeft = $(window).scrollLeft();
							var contextMenu = $("#Menu").jqxMenu({
									width : 120,
									//height : 300,
									autoOpenPopup : false,
									mode : 'popup'
								  }); 
			            
			                 $("#alarmGrid").on('contextmenu', function() {
				             return false;
			                 });  
							 contextMenu.jqxMenu('open',parseInt(event.args.originalEvent.clientX)+ 5+ scrollLeft,
								            parseInt(event.args.originalEvent.clientY)+ 5+ scrollTop); 
							return false;
						}
				});
			//dashboard部分的告警右键处理 end
			
			//右键菜单弹出功能部分js
			// 右键选择菜单.
			$("#Menu").on('itemclick',
							function(event) {
								var args = event.args;
								var rowindex = $("#alarmGrid").jqxGrid('getselectedrowindex');
								var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
								alarmId=rowdata.ID;
								if ($.trim($(args).text()) == $("#aknowledge").text()) {
									//editrow = rowindex; 
									$("#renling")
											.jqxWindow(
													{
														showCollapseButton : true,
														isModal : true,
														resizable : false,
														maxHeight : 496,
														height : 496,
														width : 500,
														initContent : function() {
															$('#renling')
																	.jqxWindow(
																			'focus');
														}
													});
									// show the popup window.
									$("#renling")
											.jqxWindow(
													'show');
									logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmId;
									loadLogGridData(logsource);
								}else if($.trim($(args).text()) == $("#clear").text()){
					    	    var rowindex = $("#alarmGrid").jqxGrid('getselectedrowindex');
					    	    
					    	        alarmIdList= new Array();
									if(rowindex<0){
										$().toastmessage('showToast', {
								            text     : '请选择一条更新记录！',
								            sticky   : false,
								            position : 'top-center',
								            type     : 'warning',
								            closeText: ''
								        });
										return;
									}
									var data = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
									alarmId=data.ID;
									alarmIdList.push(data.ID);
									$("#windowtitle").text("清除");
									$("#renling").jqxWindow(
													{
														showCollapseButton : true,
														isModal : true,
														resizable : false,
														maxHeight : 496,
														height : 496,
														width : 500,
														initContent : function() {
															$(
																	'#renling')
																	.jqxWindow(
																			'focus');
														}

													});
									$('#renling').jqxWindow(
											'show');	
									logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmId;
									batchFilter='alarmIdList='+alarmId;
									loadLogGridData(logsource);
									
					             }
                                 else if ($.trim($(args).text()) == $("#forward").text()) {
									$("#alarmforward").jqxWindow({
													 showCollapseButton: true, isModal:true,resizable: false, height: 392, width: 600,
									                    initContent: function () {
									                        $('#alarmforward').jqxWindow('focus');
									                    }
												}); 
									//var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
									// show the popup window.
									var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
									$("#alarm_name").val(rowdata.hostName==null?"":rowdata.hostName);
				                    $("#alarm_index").val(rowdata.lastname==null?"":rowdata.lastname);
				                    $("#forward_content").val(rowdata.content_t==null?"":rowdata.content_t);
				                    $("#alarm_title").val(rowdata.title==null?"":rowdata.title);
				                    $("#start_time").val(rowdata.firstOccurTime==null?"":rowdata.firstOccurTime);
				                    $("#alarm_level").val(rowdata.level==null?"":rowdata.level);
				                    alarmId=rowdata.id;
				                    $("#alarmforward").jqxWindow('show');
								}    
							});
			// end
			
			//renling start
			//renling log grid
			
			//batchupdate
							$("#"+batchupdate).click(function() {
									alarmIdList=[];
									                var rowindexes = $('#'+alarmGridId).jqxGrid('getselectedrowindexes');

									                ///var rowindexes = $('#'+alarmGridId).jqxGrid('getselectedrowindexes');
									                if(isClickSearch==false&&((rowindexes.length==0||rowindexes[0]==-1))){
									                	$().toastmessage('showToast', {
												            text     : '请选择一条更新记录！',
												            sticky   : false,
												            position : 'top-center',
												            type     : 'warning',
												            closeText: ''
												        });
									                	return;
									                }
									           
									                if(isClickSearch==false&&(rowindexes.length>0)){
									                	for (var i = 0; i < rowindexes.length; i++) {
															var data = $('#'+alarmGridId).jqxGrid('getrowdata',rowindexes[i]);
															
															alarmIdList.push(data.ID);
														}
									                	
									                	$("#windowtitle").text("清除");
														$("#renling").jqxWindow(
																		{
																			showCollapseButton : true,
																			isModal : true,
																			resizable : false,
																			maxHeight : 496,
																			height : 496,
																			width : 500,
																			initContent : function() {
																				$(
																						'#renling')
																						.jqxWindow(
																								'focus');
																			}

																		});
														$('#renling').jqxWindow(
																'show');
														logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmIdList[0];
														loadLogGridData(logsource);
														
									                }
									                //if(isClickSearch==true&&(alarmIdList.length>0)){
									                	if(isClickSearch==true){
									                	$("#windowtitle").text("清除");
														$("#renling").jqxWindow(
																		{
																			showCollapseButton : true,
																			isModal : true,
																			resizable : false,
																			maxHeight : 496,
																			height : 496,
																			width : 500,
																			initContent : function() {
																				$(
																						'#renling')
																						.jqxWindow(
																								'focus');
																			}

																		});
														$('#renling').jqxWindow(
																'show');
														logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmIdList[0];
														loadLogGridData(logsource);
									                	return;
									                }
												});
								//end
								
			var cates = [ "意见处理", "知识" ];
			$('#cate').jqxDropDownList({
				selectedIndex : 0,
				source : cates,
				autoDropDownHeight : true,
				width : 430,
				height : 20
			});
			$('#cate').on('select', function(event) {
				/*  var index = event.args.index;
				  $("#numericInput").jqxNumberInput({ cates: cates[index] });
				 */});
			var logsource = {
				datatype: "json",
				datafields : [ {
					name : 'createBy',
					type : 'string'
				}, {
					name : 'createTime',
					type : 'date'
				}, {
					name : 'content',
					type : 'string'
				}, ],
				id : 'id'
				
			};
			function loadLogGridData(logsource){
				//getAlarmComments
				var datalogAdapter =new $.jqx.dataAdapter(logsource);
				// initialize jqxGrid
				$("#loggrid").jqxGrid({
					width : 428,
					height : 150,
					source : datalogAdapter,
					localization : getLocalization(),
					//pageable : true,
					sortable : true,
					altrows : true,
					enabletooltips : true,
					//editable : true,
					//selectionmode : 'multiplecellsadvanced',
					columns : [ {
						text : '处理人',
						datafield : 'createBy',
						width : 160
					}, {
						text : '处理时间',
						datafield : 'createTime',
						cellsformat: 'yyyy-MM-dd HH:mm:ss',
						width : 136
					}, {
						text : '详细内容',
						datafield : 'content',
						width : 130
					}, ]
				});
			}
			
			var editrow = -1;
			$("#confirm").click(function() {
				if ($("#windowtitle").text() == '认领') {
					var rowindex = $('#'+alarmGridId).jqxGrid('getselectedrowindex');
					var row = $('#'+alarmGridId).jqxGrid('getrowdata',rowindex);
					row.attr_actorUser = '${userId}';
					row.attr_result = $("#detailcontent").val();
					row.status=1;
					$('#'+alarmGridId).jqxGrid('updaterow',rowindex,row);
					$("#renling").jqxWindow('hide');
				 } else {
				 
			        var alarmIds="";
					
					if(alarmIdList.length>0){ 
						alarmIds=alarmIdList.join(",");
						batchFilter='alarmIdList='+alarmIds;
					
					}
					alarmIds=alarmIdList.join(",");
					//alert(alarmIds);
					//batchFilter='alarmIdList='+alarmIds;
			       
			     
			        
			               $.post('${ctx}/pages/alarm/batchClose?'+batchFilter,
						      {
			            	   //batchFilter:batchFilter
						      },
						      function (data) 
						      {
						    	  $("#renling").jqxWindow('hide');
								  $('#'+alarmGridId).jqxGrid('updatebounddata');
								  $('#'+alarmGridId).jqxGrid('clearselection');
								  batchFilter="";
								  alarmIdList = [];
								
							} 
						
						    ); 
						   
				}

			});

			$("#cancel").click(function() {
				$("#renling").jqxWindow('hide');
			});
			//end
			
			

       
           
            //show searchwindows
             $('#showWindowButton').click(function() {
									$('#searchwindow').jqxWindow('open');
								});
								$("#searchwindow").jqxWindow({
									showCollapseButton : true,
									autoOpen : false,
									isModal : true,
									resizable : false,
									height : 350,
									width : 500,
									initContent : function() {
										$('#searchwindow').jqxWindow('focus');
									}
								});
             
			$("#start_date").jqxDateTimeInput({ width: '200px',readonly: true, height: '18px',max: new Date(),formatString: 'yyyy/MM/dd'});
			$("#end_date").jqxDateTimeInput({ width: '200px', readonly: true,height: '18px', max: new Date(),formatString: 'yyyy/MM/dd'});
			//search start
			
			
			$("#searchcancel").click(function() {
				$("#searchwindow").jqxWindow('close');
			});
			$("#tab_close").click(function() {
				$('#mainSplitter').jqxSplitter('collapse');
				$('#alarmGrid').jqxGrid({
					height : gridHeight
				});
			});

			//end					
             
            //弹出
            
        	$("#window").jqxWindow({
				showCollapseButton : false,
				autoOpen : false,
				isModal : true,
				resizable : false,
				maxHeight: '95%',
				maxWidth: '99%', 
				minHeight: '95%', 
				minWidth: '99%', 
				height: '95%', 
				width: '99%',
				/* height : 850,
				width : 3000, */
				initContent : function() {
					$('#tab').jqxTabs({ height: '100%', width:  '100%' });
					$('#window').jqxWindow('focus');
					// $('#Progress').circliful();
				}
			});
            //loadNestGrid
             $("#autoRefresh").click(function() {
            	                     setInterval(loadNestGrid(moId),20000);
								});
             $("#manuRefresh").click(function() {
                 loadNestGrid(moId);
			 });
        	 function loadNestGrid(moId){
        		 $.getJSON('${ctx}/performance/getGroups?moId='+moId,
        					function(data) {
        			         showGroupGrid(data);
        	 	    	 
        		    });
        		  function showGroupGrid(gridData){
        		   var data = new Array();

        	       for (var i = 0; i < gridData.length; i++) {
        	           var row = {};
        	           row["moId"] = gridData[i][0];
        	           row["groupName"] = gridData[i][1];
        	           data[i] = row;
        	       }
        		   var source =
        	       {
        	           localdata: data,
        	           datatype: "array",
        	           datafields:
        	           [
        	               { name: 'moId' },
        	               { name: 'groupName'}
        	               
        	           ]
        	       };

        	       var nestedGrids = new Array();
        	       /*  */
        	       var innerGridData;
        	       var data1= new Array();

        	       // create nested grid.
        	       var initrowdetails = function (index, parentElement, gridElement, record) {
        	    	   
        	    	    $.getJSON('${ctx}/performance/getInnderGrid?groupName='+record.groupName,
        	    	   				function(data) {
        	    	    	   innerGridData=data;
        	    	    	   var datafieldsArray =new Array();
        	    	    	   var datafields=innerGridData.fieldsNames;
        	    	    	   for(var i=0;i<datafields.length;i++){
        	    	    		   var obj=new Object();
        	    	    		   obj.name=datafields[i];
        	    	    		   obj.type='string';
        	    	    		   datafieldsArray[i]=obj;
        	    	    	   }
        	    	    	   var datacolumns=innerGridData.columModle;
        	    	           data1=innerGridData.data;
        	    	           var grid = $($(parentElement).children()[0]);
        	    	           nestedGrids[index] = grid;
        	    	           var orderssource =
        	    	           {
        	    	        		   localdata: data1,
        	    	                   datatype: "array",
        	    	                   datafields:datafieldsArray,
        	    	               async: false
        	    	           }; 
        	    	           if (grid != null) {
        	    	               grid.jqxGrid(
        	    	        		       {
        	    	        		           width: 1200,
        	    	        		           height: 200,
        	    	        		           columnsresize : true,
        	    	        		           source: orderssource,
        	    	        		           columns: datacolumns
        	    	        		       });
        	    	           }
        	    	    });  
        	       };
        	       //end
        	       
        	      // creage jqxgrid
        	       $("#KPIlist").jqxGrid(
        	       {
        	           width: 1300,
        	           height: 500,
        	           source: source,
        	           rowdetails: true,
        	           rowsheight: 35,

        	           initrowdetails: initrowdetails,
        	           rowdetailstemplate: { rowdetails: "<div id='grid' style='margin: 10px;'></div>", rowdetailsheight: 220, rowdetailshidden: true },
        	           columns: [{  text: 'Group Name', datafield: 'groupName', width: '100%' }]
        	       });
        		  }
        		 }
        	    //end
        	    //loadPortConfigGridData
               function loadPortConfigGridData(portConfigSource) {
						var portConfigDataAdapter = new $.jqx.dataAdapter(
								portConfigSource);
						$("#portConfigGrid").jqxGrid({
							width : '98%',
							height : '95%',
							source : portConfigDataAdapter,
							columnsresize : true,
							localization : getLocalization(),
							pageable : true,
							pagesize : 10,
							//filterable: true,
							sortable : true,
							//page
							virtualmode : true,

							rendergridrows : function() {
								return portConfigDataAdapter.records;
							},
							//selectionmode : 'checkbox',
							altrows : true,
							enabletooltips : true,
							columns : [ {
								text : '编号',
								datafield : 'ID',
								width : 10,
								hidden : true
							}, {
								text : 'MO_ID',
								datafield : 'mo_id',
								width : 10,
								hidden : true
							}, {
								text : 'type',
								datafield : 'type',
								width : 10,
								hidden : true
							},
							{
								text : '端口索引',
								datafield : 'ifIndex',
								width : 80
								
							}, {
								text : '端口名称',
								datafield : 'ifName',
								width : 100
								//cellsrenderer : levelimagerenderer
							}, {
								text : '端口别名',
								datafield : 'ifAlias',
								width : 100
								//cellsrenderer : statusimagerenderer
							}, {
								text : '端口类型',
								datafield : 'ifType',
								width : 100
							},
							 {
								text : '端口带宽',
								datafield : 'ifSpeed',
								minwidth : 100
							},{
								text : '端口物理地址',
								datafield : 'ifPhysAddress',
								minwidth : 100
							}, {
								text : '端口IP',
								datafield : 'ipAdEntAddr',
								minwidth : 100
							} , {
								text : '子网掩码',
								datafield : 'ipAdEntNetMask',
								minwidth : 120
							},{
								text : '端口描述',
								datafield : 'ifDescr',
								minwidth : 100
							}]
						});
					}
        	    //end
           
              function loadGridData(source) {
						var statusimagerenderer = function(row,
								datafield, value) {
							if(value!=0){
								return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/confirmed.gif"/>';
							}
							return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/newalarm.gif"/>';
						};
						var levelimagerenderer = function(row,
								datafield, value) {
							//alert("level is:"+value);
							return '<img style="margin-left: 10px;padding-top:5px" height="15" width="15" src="${ctx}/resources/images/level' + value + '.png"/>';
						};
						var dataAdapter = new $.jqx.dataAdapter(
								source);
						$("#alarmGrid").jqxGrid({
							width : '100%',
							height : '80%',
							source : dataAdapter,
							columnsresize : true,
							localization : getLocalization(),
							pageable : true,
							pagesize : 20,
							//filterable: true,
							sortable : true,
							//page
							virtualmode : true,

							rendergridrows : function() {
								return dataAdapter.records;
							},
							selectionmode : 'checkbox',
							altrows : true,
							enabletooltips : true,
							columns : [ {
								text : '编号',
								datafield : 'ID',
								width : 10,
								hidden : true
							}, {
								text : 'MO_ID',
								datafield : 'mo_id',
								width : 10,
								hidden : true
							}, {
								text : 'type',
								datafield : 'type',
								width : 10,
								hidden : true
							},
							{
								text : 'KPI_CODE',
								datafield : 'KPI_CODE',
								width : 10,
								hidden : true
							}, {
								text : '级别',
								datafield : 'LEVEL',
								width : 40,
								cellsrenderer : levelimagerenderer
							}, {
								text : '状态',
								datafield : 'STATUS',
								width : 40,
								cellsrenderer : statusimagerenderer
							}, {
								text : '主机名',
								datafield : 'MO_CAPTION',
								width : 150
							},
							 {
								text : '告警内容',
								datafield : 'CONTENT',
								minwidth : 150
							},{
								text : '时间',
								datafield : 'OCCUR_TIMES',
								minwidth : 50
							}, {
								text : '次数',
								datafield : 'AMOUNT',
								minwidth : 20
							} , {
								text : '处理结果',
								datafield : 'attr_result',
								minwidth : 120
							},{
								text : '处理人',
								datafield : 'attr_actorUser',
								minwidth : 80
							}]
						});
					}
        	   $('#jqxgrid').on('rowdoubleclick', function (event) {
        		 var rowid=event.args.rowindex;
        		 var rowdata = $('#jqxgrid').jqxGrid('getrowdata',rowid);
        		 moId=rowdata.mo_id;
        		 hostip=rowdata.hostName;
        		 $("#chart1").hide();
           		 $("#chart2").hide();
           		 $("#chart3").hide();
           		 $("#chart4").hide();
           		 $("#chart5").hide();
           		 //$('#kpigrid').jqxGrid('clearselection');
        		 $("#hostip").text(rowdata.hostName);
        		 loadNestGrid(moId);
        	     $.getJSON("${ctx}/pages/dashboard/network/getNetWorkHostBasicInfo?moId="+moId,
      					function(data) {
        	    	 $("#sysName").text(data.basicData[0].VAL);
      	    		 $("#sysIp").text("IP: "+data.basicData[1].VAL);
      	    		 $("#sysObjectID").text(data.basicData[2].VAL);
      	    		 $("#sysDescr").text(data.basicData[3].VAL);
        	     });
        	   
        	     $.getJSON("${ctx}/pages/dashboard/network/getNetworkInPortList?moId="+moId ,
      					function(data) {
        	    	 var $html;
        	    	 if(data.length>0){
        	    		  for(var i=0;i<data.length;i++){
        	    			  if(data[i].ifOutOctets==0&&data[i].ifInOctets==0){
        	    			  $html=$('<dl><dt><span class="red">'+data[i].ifName+'</span></dt><dd><b class="Network_unavailable"></b></dd> </dl>'); 
        	    			  $("#Network_content").append($html);
        	    			  }
        	    			  else{
        	    				  $html=$('<dl><dt><span class="black">'+data[i].ifName+'</span></dt><dd><b class="Network_available"></b></dd> </dl>'); 
            	    			  $("#Network_content").append($html);
        	    			  }
        	    		  } 
        	    	 }
        	     });
        	   $.jqx._jqxChart.prototype.colorSchemes.push({ name: 'myScheme', colors:['#2cc185','#f06050','#e74c39','#f39c12','#8e44ad'] });
               $.jqx._jqxChart.prototype.colorSchemes.push({ name: 'levelScheme', colors:['#37bc29','#0077dc','#eea025','#e16725','#e43e1e'] });
               //port1 chart
               var source1 =
           		        {
           		            datatype: "json",
           		            datafields: [
           		                {name: 'total' },
           		                {name: 'value'}
           		            ],
           		            url: '${ctx}/pages/dashboard/network/getNetworkInPortList?moId='+moId
           		        };		
           		        var dataAdapter1 = new $.jqx.dataAdapter(source1, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source1.url + '" : ' + error);} });
           		        var settings1 = {
           		            title: "Top5分端口入流量",
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
           		                    dataField: 'portName',
           		                    unitInterval: 1,
           		                    tickMarksInterval: 1,
           		                    gridLinesInterval: 1,
           		                    showGridLines: false,
           		                    //textRotationAngle: -45,
           		                    //axisSize:1,
           		                    /*  formatFunction: function (value) {
           		                    	  if(value.length<12){
           		                    		 return value;
           		                    	 }
           		                    	 var firstPart=value.substr(0, 12);
           		                         return firstPart+"<br>"+value.substr(12); 
           		                    },  */
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
           		                        //click: myEventHandler,
           		                        seriesGapPercent: 5,
           		                        columnsGapPercent: 25,
           		                        valueAxis:
           		                        {
           		                            unitInterval: 100,
           		                            displayValueAxis: true,
           		                            description: '分端口入流量'
           		                        },
           		                        series: [
           		                               { dataField: 'ifInOctets', displayText: 'OutPort'}//, color: 'yellow'}  
           		                        ]
           		                    }

           		                ]
           		        };
           	        // setup the chart
           	       $('#portChart1').jqxChart(settings1); 
           		   //end
           		   
           		   //prot2 chart
           		   var source2 =
           		        {
           		            datatype: "json",
           		            datafields: [
           		                {name: 'total' },
           		                {name: 'value'}
           		            ],
           		            url: '${ctx}/pages/dashboard/network/getNetworkOutPortList?moId='+moId
           		        };		
           		        var dataAdapter2 = new $.jqx.dataAdapter(source2, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source2.url + '" : ' + error);} });
           		        var records2=dataAdapter2.records;
           		        var settings2 = {
           		            title: "Top5分端口出流量",
           		            description: "",
           		            showLegend: true,
           		            enableAnimations: true,
           		            showToolTips:true,
           		            //renderEngine:'HTML5',
           		            showBorderLine: false,
           		           // backgroundColor: '#339933',
           		            padding: { left: 5, top: 5, right: 5, bottom: 5 },
           		            titlePadding: { left: 90, top: 0, right: 0, bottom: 10 },
           		            source: dataAdapter2,
           		            categoryAxis: {
           		                    dataField: 'portName',
           		                    unitInterval: 1,
           		                    tickMarksInterval: 1,
           		                    gridLinesInterval: 1,
           		                    showGridLines: false,
           		                    //textRotationAngle: -45,
           		                    //axisSize:1,
           		                    /* formatFunction: function (value) {
           		                    	  if(value.length<12){
           		                    		 return value;
           		                    	 }
           		                    	 var firstPart=value.substr(0, 12);
           		                         return firstPart+"<br>"+value.substr(12); 
           		                    },  */
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
           		                        //click: myEventHandler,
           		                        seriesGapPercent: 5,
           		                        columnsGapPercent: 25,
           		                        valueAxis:
           		                        {
           		                            unitInterval: 100,
           		                            displayValueAxis: true,
           		                            description: '分端口出流量'
           		                        },
           		                        series: [
           		                                { dataField: 'ifOutOctets', displayText: 'InPort'}//, color: 'yellow'}  
           		                        ]
           		                    }

           		                ]
           		        };
           	        // setup the chart
           	       $('#portChart2').jqxChart(settings2); 
           		   //end

                 //portConfigGrid
        	   var portConfigSource = {
						datatype : "json",
						datafields : [ {
							name : 'ID',
							type : 'string'
						}, {
							name : 'MO_ID',
							type : 'string'
						},
						{
							name : 'type',
							type : 'string'
						},
						 {
							name : 'ifIndex',
							type : 'string'
						}, {
							name : 'ifName',
							type : 'string'
						}, {
							name : 'ifAlias',
							type : 'string'
						}, {
							name : 'ifType',
							type : 'String'
						},
						{
							name : 'ifSpeed',
							type : 'String'
						},
						{
							name : 'ifPhysAddress',
							type : 'string'
						}, {
							name : 'ipAdEntAddr',
							type : 'string'
						}, {
							name : 'ipAdEntNetMask',
							type : 'string'
						},{
							name : 'ifDescr',
							type : 'string'
						}, {
							name : 'ifOutOctets',
							type : 'string'
						},
						{
							name : 'ifInOctets',
							type : 'string'
						}
						],
						beforeprocessing : function(data) {
							if (data.length == 0) {
								portConfigSource.totalrecords = 0;
							} else {
								portConfigSource.totalrecords = data[data.length - 1].totalcount;
							}

						},
						
						sort : function() {
							// update the grid and send a request to the server.
							$("#portConfigGrid").jqxGrid(
									'updatebounddata');
						},
						id : 'id',
						url : '${ctx}/pages/dashboard/network/getNetworkPortConfigList?moId='+moId
					};
					loadPortConfigGridData(portConfigSource);
					////end
        	    var source = {
						datatype : "json",
						datafields : [ {
							name : 'ID',
							type : 'string'
						}, {
							name : 'MO_ID',
							type : 'string'
						},
						{
							name : 'type',
							type : 'string'
						},
						 {
							name : 'KPI_CODE',
							type : 'string'
						}, {
							name : 'LEVEL',
							type : 'string'
						}, {
							name : 'STATUS',
							type : 'string'
						}, {
							name : 'AMOUNT',
							type : 'String'
						},
						{
							name : 'OCCUR_TIMES',
							type : 'String'
						},
						{
							name : 'MO_CAPTION',
							type : 'string'
						}, {
							name : 'CONTENT',
							type : 'string'
						}, {
							name : 'AMOUNT',
							type : 'string'
						},{
							name : 'attr_result',
							type : 'string'
						}, {
							name : 'attr_actorUser',
							type : 'string'
						}

						],
						beforeprocessing : function(data) {
							if (data.length == 0) {
								source.totalrecords = 0;
							} else {
								source.totalrecords = data[data.length - 1].totalcount;
							}

						},
						updaterow : function(rowid, rowdata, commit) {
						console.log(rowdata.ID);
							var url = '${ctx}/pages/alarm/aknowledge?content='
									+ rowdata.attr_result
									+ '&alarmId=' + rowdata.ID;
							$.ajax({
								cache : false,
								dataType : 'json',
								url : url,
								success : function(data, status,
										xhr) {
									//alert("success");
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
									//alert(errorThrown);
									//commit(false);
								}
							});
							commit(true);
						},
						sort : function() {
							// update the grid and send a request to the server.
							$("#alarmGrid").jqxGrid(
									'updatebounddata');
						},
						id : 'id',
						url : '${ctx}/performance/getAlarmData?moId='+moId
					};
					loadGridData(source);
					$("#search").click(
							function() {
								var s_moCaption = $(
										"#s_moCaption")
										.val();
								var s_content_t = $(
										"#s_content_t")
										.val();
								var s_attr_ipaddr = $(
										"#s_attr_ipaddr")
										.val();
								var s_aknowledge_by = $(
										"#s_aknowledge_by")
										.val();
								var s_attr_businessSystem = $(
										"#s_attr_businessSystem")
										.val();
								var s_attr_position = $(
										"#s_attr_position")
										.val();
								var s_start_time = $('#start_date').jqxDateTimeInput('getText');  
							 	var s_end_time = $('#end_date').jqxDateTimeInput('getText'); 
							 	
								source.url = '${ctx}/performance/getAlarmDataByCriteria?s_moCaption='
										+ s_moCaption
										+ '&s_content_t='
										+ s_content_t
										+ '&s_attr_ipaddr='
										+ s_attr_ipaddr
										+ '&s_aknowledge_by='
										+ s_aknowledge_by
										+ '&s_attr_businessSystem='
										+ s_attr_businessSystem
										+ '&s_attr_position='
										+ s_attr_position
										 + '&s_start_time='
										+ s_start_time 
										 + '&s_end_time='
										+ s_end_time+'&moId='+moId;
								       $.getJSON(
										'${ctx}/performance/getAlarmDataByCriteria?s_moCaption='
										+ s_moCaption
										+ '&s_content_t='
										+ s_content_t
										+ '&s_attr_ipaddr='
										+ s_attr_ipaddr
										+ '&s_aknowledge_by='
										+ s_aknowledge_by
										+ '&s_attr_businessSystem='
										+ s_attr_businessSystem
										+ '&s_attr_position='
										+ s_attr_position
										 + '&s_start_time='
										+ s_start_time 
										 + '&s_end_time='
										+ s_end_time+'&moId='+moId,
										function(data) {
													alarmIdList= [];
													for(var i=0;i<data.length-1;i++){
														alarmIdList.push(data[i].id);
													}
													isClickSearch=true;
										}); 
								     loadGridData(source);
										
								$("#searchwindow").jqxWindow(
										'close');
							});
					//右键相关的 在这里
					
					//end
					
			 var kpisource =
                {
			    datatype: "json",
                datafields:
                [
                    { name: 'kpi', type: 'string'},
                    { name: 'displayKpi', type: 'string'}
                ],
                id : 'kpi',
				url : '${ctx}/config/kpiListNew?moId='+moId
                
             };
             var kpidataAdapter = new $.jqx.dataAdapter(kpisource);

             // initialize jqxGrid4
             $("#kpigrid").jqxGrid(
            {
                width: '100%',
				height:'85%',
                source: kpidataAdapter,
                sortable : true,
                //editable: true,
                selectionmode : 'checkbox',
				altrows : true,
                columns: [
                  { text: 'KPI', editable: 'available', datafield: 'kpi', width: '90%',hidden:true},
                  { text: 'KPI', editable: 'available', datafield: 'displayKpi', width: '90%'} 
                ]
            }); 
        	   $('#window').jqxWindow('open');
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
			
			var url = "${ctx}/performance/getAllBiz";
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
            
            $('#bizList').on('change', function (event){     
            		    var args = event.args;
            		    if (args) {
            		    var item1 = args.item;
            		    if(item1.label=='其他') {
            	        	item1.label='others';
            	        }
            		    hostsource.url = "${ctx}/pages/dashboard/network/getNetworkPerformanceList?bizName="+encodeURI(encodeURI(item1.label));
            		    loadGrid(hostsource);
            		    //getData(item1);
            		    var url='${ctx}/pages/dashboard/network/getNetworkOverViewPerformanceList?bizName='+encodeURI(encodeURI(item1.label));
				        loadHostOverView(url);
            		    loadLevelAlarmData(item1.label);
               } 
            });
            
            var index2 = $("#bizList").jqxDropDownList('getSelectedIndex');
            var item2 = $("#bizList").jqxDropDownList('getItem', index2);
            if(item2.label=='其他') {
            	item2.label='others';
            }
            $(".PerformanceNavBox").click(function(){
            	
            	  var index2 = $("#bizList").jqxDropDownList('getSelectedIndex');
                  var item2 = $("#bizList").jqxDropDownList('getItem', index2);
                  if(item2.label=='其他') {
                  	item2.label='others';
                  }
            	 var typeId=$(this).attr("id");
            	 hostsource.url = "${ctx}/pages/dashboard/network/getNetworkPerformanceList?typeId="+typeId+"&bizName="+encodeURI(encodeURI(item2.label));
     		     loadGrid(hostsource);
     		     var url='${ctx}/pages/dashboard/network/getNetworkOverViewPerformanceList?typeId='+typeId+"&bizName="+encodeURI(encodeURI(item2.label));
           	     loadHostOverView(url);
    		    
             });
            function loadLevelAlarmData(bizName){
         	$.getJSON("${ctx}/performance/getAllLevelAlarmDataByType?typeId=NETWORK&bizName="+encodeURI(encodeURI(bizName)),
					function(data) {
         		$("#network-level1").text("0");
         		$("#network-level2").text("0");
         		$("#network-level3").text("0");
         		$("#network-level4").text("0");
         		$("#network-level5").text("0");
         		$("#network1-level1").text("0");
         		$("#network1-level2").text("0");
         		$("#network1-level3").text("0");
         		$("#network1-level4").text("0");
         		$("#network1-level5").text("0");
         		$("#network2-level1").text("0");
         		$("#network2-level2").text("0");
         		$("#network2-level3").text("0");
         		$("#network2-level4").text("0");
         		$("#network2-level5").text("0");
         		$("#network3-level1").text("0");
         		$("#network3-level2").text("0");
         		$("#network3-level3").text("0");
         		$("#network3-level4").text("0");
         		$("#network3-level5").text("0");
         		$("#network4-level1").text("0");
         		$("#network4-level2").text("0");
         		$("#network4-level3").text("0");
         		$("#network4-level4").text("0");
         		$("#network4-level5").text("0");
         		networktotal=0;
         		networktotal1=0;
         		networktotal2=0;
         		networktotal3=0;
         		networktotal4=0;
         		
 
       		  for(var i=0;i<data.length;i++){
       			if(data[i].type=="NETWORK_FIREWALL"){
       				if(data[i].grade==1){
       					$("#network-level1").text(data[i].count);
       					networktotal+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#network-level2").text(data[i].count);
       					networktotal+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#network-level3").text(data[i].count);
       					networktotal+=data[i].count;
       				}
       				if(data[i].grade==4){
       					$("#network-level4").text(data[i].count);
       					networktotal+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#network-level5").text(data[i].count);
       					networktotal+=data[i].count;
       				}
       			}	
       			if(data[i].type=="NETWORK_SWITCH"){
       				if(data[i].grade==1){
       					$("#network1-level1").text(data[i].count);
       					networktotal1+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#network1-level2").text(data[i].count);
       					networktotal1+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#network1-level3").text(data[i].count);
       					networktotal1+=data[i].count;
       				}
       				if(data[i].grade==4){
       					$("#network1-level4").text(data[i].count);
       					networktotal1+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#network1-level5").text(data[i].count);
       					networktotal1+=data[i].count;
       				}
       			}	
       			if(data[i].type=="NETWORK_LOADBALANCER"){
       				if(data[i].grade==1){
       					$("#network2-level1").text(data[i].count);
       					networktotal2+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#network2-level2").text(data[i].count);
       					networktotal2+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#network2-level3").text(data[i].count);
       					networktotal2+=data[i].count;
       				}
       				if(data[i].grade==4){
       					$("#network2-level4").text(data[i].count);
       					networktotal2+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#network2-level5").text(data[i].count);
       					networktotal2+=data[i].count;
       				}
       			}
       			if(data[i].type=="NETWORK_ROUTER"){
       				if(data[i].grade==1){
       					$("#network3-level1").text(data[i].count);
       					networktotal3+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#network3-level2").text(data[i].count);
       					networktotal3+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#network3-level3").text(data[i].count);
       					networktotal3+=data[i].count;
       				}
       				if(data[i].grade==4){
       					$("#network3-level4").text(data[i].count);
       					networktotal3+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#network3-level5").text(data[i].count);
       					networktotal3+=data[i].count;
       				}
       			}
       			if(data[i].type=="NETWORK_UNKNOWN"){
       				if(data[i].grade==1){
       					$("#network4-level1").text(data[i].count);
       					networktotal4+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#network4-level2").text(data[i].count);
       					networktotal4+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#network4-level3").text(data[i].count);
       					networktotal4+=data[i].count;
       				}
       				if(data[i].grade==4){
       					$("#network4-level4").text(data[i].count);
       					networktotal4+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#network4-level5").text(data[i].count);
       					networktotal4+=data[i].count;
       				}
       			}
       		}
		    $("#networktotal").text(networktotal);
		    $("#networktotal1").text(networktotal1);
		    $("#networktotal2").text(networktotal2);
		    $("#networktotal3").text(networktotal3);
		    $("#networktotal4").text(networktotal4);
			});
            }
            loadLevelAlarmData(item2.label);

     	    var url;
            if($.getUrlParam('moId')!=null){
            	url = "${ctx}/pages/dashboard/network/getNetworkPerformanceList?bizName="+encodeURI(encodeURI(item2.label))+'&moId='+$.getUrlParam('moId');
     		}
            //
             url = "${ctx}/pages/dashboard/network/getNetworkPerformanceList?bizName="+encodeURI(encodeURI(item2.label));
     			  var hostsource = {
     				datatype : "json",
     				datafields : [ {
     					name : 'MO_ID',
     					type : 'string'
     				}, {
     					name : 'hostName',
     					type : 'string'
     				}, {
     					name : 'count',
     					type : 'string'
     				}, {
     					name : 'status',
     					type : 'string'
     				}, {
     					name : 'deviceType',
     					type : 'string'
     				}  ],
     				
     				beforeprocessing : function(data) {
     					if (data.length == 0) {
     						hostsource.totalrecords = 0;
     					} else {
     						hostsource.totalrecords = data[data.length - 1].totalcount;
     					}
     				},
     			
     				sort : function() {
     					$("#jqxgrid").jqxGrid(
     							'updatebounddata');
     				},
     				id : 'id',
     				url : url
     			};
            
     			 if($.getUrlParam('biz')==""){
     				 var items = $("#bizList").jqxDropDownList('getItems'); 
 		    	     for(var i=0;i<items.length;i++){
 		    	    	 if("others"==items[i].label){
 		    	    		$("#bizList").jqxDropDownList('selectItem',items[i]);
 		    	    	 }
 		    	    }
   		            hostsource.url = "${ctx}/pages/dashboard/network/getNetworkPerformanceList?bizName=others"+"&moId="+$.getUrlParam('moId');
   				    loadGrid(hostsource);
   				    var url='${ctx}/pages/dashboard/network/getNetworkOverViewPerformanceList?bizName=others'+"&moId="+$.getUrlParam('moId');
   			        loadHostOverView(url);
   			        
   		            }
   		         else if($.getUrlParam('moId')!=null){
   		        	 var items = $("#bizList").jqxDropDownList('getItems'); 
		    	       for(var i=0;i<items.length;i++){
		    	    	 if(decodeURI($.getUrlParam('biz'))==items[i].label){
		    	    		$("#bizList").jqxDropDownList('selectItem',items[i]);
		    	    	 }
		    	       }   
       	    	    hostsource.url = "${ctx}/pages/dashboard/network/getNetworkPerformanceList?bizName="+$.getUrlParam('biz')+"&moId="+$.getUrlParam('moId');
       	    	    loadGrid(hostsource);
  				    var url='${ctx}/pages/dashboard/network/getNetworkOverViewPerformanceList?bizName='+$.getUrlParam('biz')+"&moId="+$.getUrlParam('moId');
  				    loadHostOverView(url);
       	          }
     			
		     var cpurenderer = function(row,
						datafield, value) {
		    	var res = '<div id=' + row + '></div>';
		        setTimeout(
		            function(){         
		               $('#' + row).jqxProgressBar({
			  			    value: value,
			  			    showText:true,
			  			    width: 80,
			  			    height: 15
			  			    //theme:'myScheme1'
			  			});
		            },
		            200); 
		    	    return res;
				};
			    var syslogrenderer = function(row,
						datafield, value) {
			    	if(value=="NO"){
						return '<img style="margin-left: 10px;padding-top:5px" height="18" width="18" src="${ctx}/resources/images/status_green.png"/>';
					}
					return '<img style="margin-left: 10px;padding-top:5px" height="18" width="18" src="${ctx}/resources/images/status_red.png"/>';
				
				};
				
				var memrenderer = function(row,
							datafield, value) {
			     	var html = '<div  id=' + 'mem'+row + '></div>';
			        setTimeout(
			            function(){         
			               $('#mem' + row).jqxProgressBar({
				  			    value: value,
				  			    showText:true,
				  			    width: 80,
				  			    height: 15
				  			   // theme:'myScheme1'
				  			});
			            },
			            200); 
			    	    return html; 
								
					};
				 var swaprenderer = function(row,
								datafield, value) {
				    	var res = '<div id=' + 'swap'+ row + '></div>';
				        setTimeout(
				            function(){         
				               $('#swap' + row).jqxProgressBar({
					  			    value: value,
					  			    showText:true,
					  			    width: 80,
					  			    height: 15,
					  			    theme:'myScheme1'
					  			});
				            },
				            200); 
				    	    return res;
					};	
		   loadGrid(hostsource);
		   $("#queryIP").click(function() {
				var ipaddress = $("#ipaddress").val();
				hostsource.url = '${ctx}/pages/dashboard/network/getNetworkPerformanceList?queryIP='+ ipaddress+"&bizName="+encodeURI(encodeURI(item.label));
				loadGrid(hostsource);
				var url='${ctx}/pages/dashboard/network/getNetworkOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label))+'&queryIP='+ipaddress;
				loadHostOverView(url);
			});
		function randx(){
   			return parseInt(Math.random()*27);
   	    };
     	function randy(){
			return parseInt(Math.random()*30);
	    };
	    var x;
	    var y;
	    var count;
	    var index = $("#bizList").jqxDropDownList('getSelectedIndex');
        var item = $("#bizList").jqxDropDownList('getItem', index);
        if(item.label=='其他') {
        	item.label='others';
        }
       /*    */ 
        function loadHostOverView(url){
       	if(url==undefined){
       		if($.getUrlParam('moId')==null){
       			url='${ctx}/pages/dashboard/network/getNetworkOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label));
       		}else{
       			url='${ctx}/pages/dashboard/network/getNetworkOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label))+'&moId='+$.getUrlParam('moId');
       		}
        }

       	$.getJSON(url,function(data) {
       		 $("#overviewtable").find("td").each(function(){ 
            	if($(this).attr("class")!=undefined){
            		
               		$(this).removeAttr("class");
               		$(this).removeAttr("id");
               		$(this).removeAttr("moId");
               		$(this).removeAttr("protocal");	}
             });  
      
       		for(var j=0;j<data.length;j++){
       			 x=randx();
       			 y=randy();
       			 count=j;
       			 if($("#overviewtable tr:eq("+x+") td:eq("+y+")").attr("id")==undefined){
       			
       				 if(data[j].maxlevel=="5"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_red").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
           			 } if(data[j].maxlevel=="4"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_orange").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
           			 } if(data[j].maxlevel=="3"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_yellow").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
               			 
           			 } if(data[j].maxlevel=="2"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_blue").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
               			 
           			 } if(data[j].maxlevel=="1"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_green").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
           			 }
           			if(data[j].maxlevel=="0"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_default").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
           			 }  
       			 }
       			 else{
       				 if(data[j].maxlevel=="5"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_red").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
           			 } if(data[j].maxlevel=="4"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_orange").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
               			 
           			 } if(data[j].maxlevel=="3"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_yellow").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
               			 
           			 } if(data[j].maxlevel=="2"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_blue").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
               			 
           			 } if(data[j].maxlevel=="1"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_green").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
           			 }
           			 if(data[j].maxlevel=="0"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_default").attr('id','Tooltip'+j).attr('moId',data[j].mo_id);
           			 } 
       			 }
       
       			 createToolTip(data[count],'Tooltip'+(count--));
       				
       		}
       		
       	});
       }
       loadHostOverView();
        
       function createToolTip(data,id){
   	   var content="<b>"+data.hostName+"</b><br /><b>设备状态:</b>"
   		+data.status+"<br /><b>设备型号:</b>"+data.deviceType;
   		$("#"+id).jqxTooltip({ content: content, 
   			 position: 'mouse', name: 'movieTooltip'});
     	}
       $("#forwardConfirm").click(function() {
			$.getJSON("${ctx}/pages/alarm/forwardAlarm?alarmId="+ alarmId,
					function(data) {
						//alert(data);
						
					});
			alarmId="";
			$("#alarmforward").jqxWindow('hide');
		});
		
		$("#forwardCancel").click(function() {
			$("#alarmforward").jqxWindow('hide');
		});
	  function loadGrid(hostsource){
             var hostdataAdapter = new $.jqx.dataAdapter(
            		hostsource, {
            		    loadComplete: function (records) {
 
            		    }
            		}); 
             
             $("#jqxgrid").jqxGrid({
				width : '100%',
				height : 450,
				
				source : hostdataAdapter,
				localization : getLocalization(),
				columnsresize : true,
				pageable : true,
				pagesize : 15,
				//filterable: true,
				sortable : true,
				//page
				virtualmode : true,
				rendergridrows : function() {
					return hostdataAdapter.records;
				},
				//selectionmode : 'checkbox',
				altrows : true,
				enabletooltips : true,
				columns : [ {
					text : 'IP/主机名',
					datafield : 'hostName',
					width : 250
				}, {
					text : '告警数',
					datafield : 'count',
					width : 150
				},  {
					text : '设备状态',
					datafield : 'status',
					//cellsrenderer : swaprenderer,
					minwidth : 180
				}, {
					text : '设备型号',
					datafield : 'deviceType',
					//cellsrenderer : swaprenderer,
					minwidth : 180
				}]
			  });
             $('#jqxgrid').on('bindingcomplete', function (event) {
             });
		    }
        });
        function closeFun(){	
     	   $('#window').jqxWindow('close');
       }
        $('.percentage').easyPieChart({
            animate: 1000
});

$("#explore-nav-Network li a").click(function() {
    
    // Figure out current list via CSS class
    var curList = $("#explore-nav-Network li a.current").attr("rel");
    
    // List moving to
    var $newList = $(this);
    
    // Set outer wrapper height to height of current inner list
    var curListHeight = $("#all-list-wrap").height();
    $("#all-list-wrap").height(curListHeight);
    
    // Remove highlighting - Add to just-clicked tab
    $("#explore-nav-Network li a").removeClass("current");
    $newList.addClass("current");
    
    // Figure out ID of new list
    var listID = $newList.attr("rel");
    
    if (listID != curList) {
        
        // Fade out current list
        $("#"+curList).fadeOut(0, function() {
            
            // Fade in new list on callback
            $("#"+listID).fadeIn();
            
            // Adjust outer wrapper to fit new list snuggly
            var newHeight = $("#"+listID).height();
            $("#all-list-wrap").animate({
                height: newHeight
            });
        
        });
        
    }        
    
    // Don't behave like a regular link
    return false;
});

    </script>    
 <script type="text/javascript">
            function myFunction(){
         	  document.getElementById("ShowUserBoxMain").style.display = "none";
              document.body.onclick = hide;
              document.getElementById("ShowUserBoxclick").onclick = show1;
              document.getElementById("ShowTitleMenuclick").onclick = show3;
            };
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
            function show1(oEvent){
              var UserBox1=document.getElementById("ShowUserBoxMain");
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
            function hide(){
              document.getElementById("ShowUserBoxMain").style.display = "none";
            }
        </script>

<script src="${ctx}/pages/avmon_back_gif.js"></script>





