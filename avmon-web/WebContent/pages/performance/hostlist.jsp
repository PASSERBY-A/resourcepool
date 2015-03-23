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
    <!--  
    <a href="${ctx}/main">
      <div class="LOGO"></div>
       </a>
    -->

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
<!-- Content box-04
      ================================================== -->  
    <div id="HOST_HP-UX" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"> <img src="${ctx}/resources/images/HPUnix_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">HP Unix</span><span id="hputotal" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="hpu-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="hpu-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="hpu-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="hpu-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="hpu-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>

            
		<li>
<!-- Content box-05
      ================================================== -->  
    <div id="HOST_LINUX" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/Linux_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">Linux</span><span id="linuxtotal" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="linux-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="linux-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="linux-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="linux-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="linux-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>

		<li>
<!-- Content box-06
      ================================================== -->  
    <div id="HOST_WINDOWS" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/windows_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">Windows</span><span id="wintotal" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="win-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="win-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="win-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="win-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="win-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>      
            </li>    
		<li>
<!-- Content box-07
      ================================================== -->  
    <div id="HOST_SUNOS" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/SOLARIS_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">SOLARIS</span><span id="soltotal" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="sol-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="sol-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="sol-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="sol-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="sol-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>


            
		<li>
<!-- Content box-08
      ================================================== -->  
    <div id="HOST_AIX" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/AIX_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">AIX</span><span class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="aix-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="aix-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="aix-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="aix-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="aix-level1" class="LineH12">0</span></div>
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
 <%@ include file="snmpdashboard.jsp"%> 
<!-- window begin -->
<div style="width: 100%;margin-top: 50px;" id="mainDemoContainer1">
            
            
<div id="window">
                <div id="windowHeader" style="display:none; height:0px !important;">
                </div>
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
                        <div class="left baseBox"><!-- 第一列-->
                        
                          <!-- 第一列—box1
    							  ================================================== -->
                      	  <div class="BaseInfoPerMain">
                        
                      		  <%-- <div class="left"><img src="${ctx}/resources/images/iLo_icon.png" width="32" height="32"></div> --%>
                      		  <div style="overflow-y:auto;width:310px" class="left BasePerBox">
                     			   主机名<p class="fB f14px" id="hostName"></p>
                       				 IP<p id="IPAdd"></p>
                      				  <ul>
                       					 <li><span class="title" >主机厂商</span><span class="content" style="width:190px" id="Manufacturer"></span></li>
                       					 <li><span class="title" >CPU个数</span><span class="content" style="width:190px" id="CpuCount"></span></li>
                       					 <li><span class="title" >CPU型号</span><span class="content" style="width:190px" id="CpuType"></span></li>
                       					 <li><span class="title" >内存</span><span class="content" style="width:190px" id="MemSize"></span></li>
                       					 <li><span class="title">系统</span><span class="content" style="width:190px" id="OSVersion"></span></li>
                        
                      				  </ul>
                       		  </div>
                        
                        
                         </div>
                        
                          <!-- 第一列—box2 -->                        
                        <div class="BaseInfoPerMain">
                        
                       <div class="BaseInfoPerBoxHeader"><b class="Pos-R Icon_ErrorLog Icon16 marginT8"></b><span class="fB">Error Log</span></div>
                         <div class="BaseInfoPerBoxF">
                      		<ul>
                       		 <li><b id="leftLogclass" class="Icon_status_red Icon16 Pos-R marginL8 left"></b><span class="title left darkblue W60" style="width:110px" id="leftLogLabel"></span><span id="leftLogNum" class="content right W20"  style="width:60px;"></span></li>
                       		 <li id="logDiv1"><b id="centerLogclass" class="Icon_status_red Icon16 Pos-R marginL8 left"></b><span class="title left darkblue W60" style="width:110px" id="centerLogLabel"></span><span id="centerLogNum" class="scontent right W20"  style="width:60px"></span></li>
                       		 <li id="logDiv2"><b id="rightLogclass" class="Icon_status_red Icon16 Pos-R marginL8 left"></b><span class="title left darkblue W60" style="width:110px" id="rightLogLabel"></span><span id="rightLogNum" class="content right W20"  style="width:60px"></span></li>
                      		 </ul> 
                         
                         </div>
                        
                        
                        </div>
                        
                          <!-- 第一列—box3 -->
                        <div class="BaseInfoPerMain">
                        
                         <div class="BaseInfoPerBoxHeader"><b class="Pos-R Icon_Network Icon16 marginT8"></b><span class="fB">Network</span></div>
							<div class="BaseInfoPerBoxS">
                            <ul id="networks">
                            
                           <!--  <li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                           -->  <!--<li><b class="Icon_status_green Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                             <li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                            <li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                            <li><b class="Icon_status_green Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                            <li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>                          
                            --> </ul>          
                            </div>
                        </div>
                        </div>
                        
                        <div class="left W10"> <!-- 第二列-->
<!--                          <div class="H28"></div>
                          <div class="H28"></div>-->
                     <%--      <div style="width:115px;" class="M-Top-300">
                          <p class="f10px">Accumulated Pkgts Sent:</p>
                          <p class="f10px" id="accSend">0m/s</p>
                          <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10"></p>
                          <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10"></p>
                          <p class="f10px">Accumulated Pkgts Received:</p>
                          <p class="f10px" id="accReceived">0m/s</p>
                          </div>  --%> 
                        </div>
                        
                        <div class="left baseBox"><!-- 第三列-->

                           <!-- 第三列—box1
    							  ================================================== -->
              
                         
                                	<div class="BaseInfoPerMain">
                        	 <div class="BaseInfoPerBoxHeader"><b class="Pos-R Icon_Progress Icon16 marginT8"></b><span class="fB">Process</span></div>
                        	 <div class="BaseInfoPerBoxF">
                      		 <div class="left margin4 W42">


                                 <div class="chart">
            					    <div id="propercentage" class="percentage" data-percent="0"><span id="proper">0</span>%</div>
                					    <div class="label">进程百分比</div>
          						    </div>

                             </div>
                             <div class="left margin10 W42">
                             <span class="Number-Main" id="processCount">0</span><span class="Extra-Main">个</span>
                             <span class="Number-title">进程数量</span>
                          	</div>
                       		</div>
                         </div>
                        
                          <!-- 第三列—box2 -->                        
                      
                        <div class="BaseInfoPerMain">
                         <div class="BaseInfoPerBoxHeader"><b class="Pos-R Icon_MeM Icon16 marginT8"></b><span class="fB">MEM</span></div>
						  <div class="BaseInfoPerBoxF">
                      		 <div class="left margin4 W42">                             
                             
                                 <div class="chart">
            					    <div id="mempercentage" class="percentage" data-percent="0"><span id="memper">0</span>%</div>
                					    <div class="label">内存使用率</div>
          						    </div>
                             
                             </div>
                             <div class="left margin10 W42">
                             <span class="Number-Main" id="remainMem"></span><span class="Extra-Main"></span>
                             <span class="Number-title">剩余内存</span>
                          </div>
                        </div>
                        </div>
                        
                          <!-- 第三列—box3 -->
                      
                         <div class="BaseInfoPerMain">
                        
                         <div class="BaseInfoPerBoxHeader"><b class="Pos-R Icon_CPU Icon16 marginT8"></b><span class="fB">CPU</span></div>
						  <div class="BaseInfoPerBoxF">
                      		 <div class="left margin4 W42">
                                 <div class="chart">
            					    <div id="cpupercent" class="percentage" data-percent="0"></div>
                					    <div class="label">CPU使用率</div>
          						    </div>
                             </div>
                             <div class="left margin10 W42">
                             <span class="Number-Main" id="cpuUse"></span><span class="Top-Main">%</span>
                             <span class="Number-title">CPU使用率</span>
                          </div>
                        </div>
                        </div>
                        </div>
                        
                        <div class="left W10">
                        
                        <!-- 第四列-->
                          <div class="statusbox H28">
                          <p class="f10px" style="display:none"  id="writeText2"></p>
                          <p class="f10px" id="write2"></p>
                          <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10"></p>
                          <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10"></p>
                          <p class="f10px" style="display:none"  id="readText2"></p>
                          <p class="f10px" id="read2"></p>
                          </div>
                          <div class="H28 statusbox">
                          <p class="f10px" style="display:none" id="writeText1"></p>
                          <p class="f10px" id="write1"></p>
                          <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10"></p>
                          <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10"></p>
                          <p class="f10px" style="display:none"  id="readText1"></p>
                          <p class="f10px" id="read1"></p>
                          </div> 
                        </div>
                        
                        <div class="left baseBox" style="width:27%;"><!-- 第五列-->
  

    <div id="organic-tabs" class="BaseInfoPerMain">
					
    		<ul id="explore-nav" class="left BaseInfoPerBoxT">
                <li id="ex-titleswap"><a rel="titleswap" href="#" class="current"><span>Swap</span></a></li>
                <li id="ex-titledisk"><a rel="titledisk" href="#"><span>Disk</span></a></li>
                <li id="ex-titlefile"><a rel="titlefile" href="#"><span>File System</span></a></li>
            </ul>
    		
    		<div id="all-list-wrap">
    		
    			<ul id="titleswap">
                 <!--   <li>
                    <p>路径</p>
                    <p class="darkblue" id="swappath"></p>
                    <p class="blank10"></p>
                    <p>已使用</p>
                    <div style='margin-top: 2px;'><span class="left w50 f10px darkblue" id="swapfree"></span><span class="w50 right f10px table-right gray" id="swapsize"></span></div>
                    <div style='margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;' id='swaputilpro'></div><span class="f10px darkblue" id="swaputil"></span>
                  </li>  -->
    			                       
    			</ul>
        		 
        		 <ul id="titledisk">
    			 <!--  <li>
                    <p>名称:</p>
                    <p class="darkblue" id="diskname"></p>
                    <p class="blank10"></p>
                    <p>使用率:</p>
                    <div style='margin-top: 2px;'><span class="left w50 f10px darkblue" id="disksize"></span><span class="w50 right f10px table-right gray" id="swapallsize">8192.0 MB</span></div>
                     <div style='margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;' id='diskPro'></div><span class="f10px darkblue" id="diskutil"></span>
                  </li> -->
        		 </ul>
        		 
        		 <ul id="titlefile">
    				 
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
                                  <div style="height:95%; width:100%;overflow-y: scroll;display:block">
                                  <div id="chart1" style="border:1px solid #f4f4f4; width:100%; height:40%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart2" style="border:1px solid #f4f4f4; width:100%; height:40%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart3" style="border:1px solid #f4f4f4; width:100%; height:40%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart4" style="border:1px solid #f4f4f4; width:100%; height:40%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart5" style="border:1px solid #f4f4f4; width:100%; height:40%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
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
        	var hputotal=0;
        	var wintotal=0;
        	var linuxtotal=0;
        	var sunostotal=0;
        	var hostip="hostip";
        	var alarmGridId="alarmGrid";
        	var KPIlist="KPIlist";
            var jqxWidgetData="jqxWidgetData1";
            var startDate="startDate";
            var toDate="toDate";
            var startHour="startHour";
            var endHour="endHour";
            var queryKpi="queryKpi";
            var kpigrid="kpigrid";
            var num=1;
            var window="window";
            var batchupdate="batchupdate";
            var typeId;
            var alarmType="";
            var batchFilter="";
            var isClickSearch=false;
            //获取url参数
            $.getUrlParam = function(name){
                var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
                var r = location.search.substr(1).match(reg);
              if (r!=null) return unescape(r[2]); return null;
              };
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
			/* Object.prototype.count = (
        		    Object.prototype.hasOwnProperty('__count__')
        		  ) ? function () {
        		    return this.__count__;
        		  } : function () {
        		    var count = 0;
        		    for (var i in this) if (this.hasOwnProperty(i)) {
        		      count ++;
        		    }
        		    return count;
        		  };    */
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
        	regClick();
        	function regClick(){
        		 //双击显示dashboard的代码
                $("#overviewtable").find("td").each(function(){ 
            	       	if($(this).attr("class")!=undefined){
            	       $("#"+$(this).attr("id")).click(function () {
            	    	  
            	    	   /**
            	    	   dashboard.html在这里
            	    	   **/
            	    	   moId=$(this).attr("moId");
                     		 var protocal=$(this).attr("protocal");
                  		   if(protocal=='SNMP'){
                  			 alarmGridId="alarmGridsnmp";
                  			 KPIlist="KPIlistsnmp";
                  			 hostip="hostipsnmp";
                  			 jqxWidgetData="jqxWidgetData11";
                  			 startDate="startDatesnmp";
                  			 toDate="toDatesnmp";
                  	         startHour="startHoursnmp";
                  	         endHour="endHoursnmp";
                  	         queryKpi="queryKpisnmp";
                  	         kpigrid="kpigridsnmp";
                  	         num=10;
                  	         window="windowsnmp";
                  	         batchupdate="batchupdatesnmp";
                  		   }else{
                  			 alarmGridId="alarmGrid";
                  			 KPIlist="KPIlist";
                  			 hostip="hostip";
                  			 jqxWidgetData="jqxWidgetData1";
                  			 startDate="startDate";
                  			 toDate="toDate";
                  	         startHour="startHour";
                  	         endHour="endHour";
                  	         queryKpi="queryKpii";
                  	         kpigrid="kpigrid";
                  	         num=10;
                  	         window="window";
                  	         batchupdate="batchupdate";
                  		    }
								 $.getJSON("${ctx}/performance/getBasicInfoData?moId="+moId,
                    					function(data) {
                      	    		 $("#snmpName").text(data.basicData[0].VAL);
                          	    	 $("#snmpIp").text(data.basicData[1].VAL);
                          	    	
                      	     });
                  	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpHostBasicInfo?moId="+moId,
                      					function(data) {
                        	    		$("#hrSystemUptime").text(data.basicData[0].VAL);
                        	    		$("#hrSystemDate").text(data.basicData[1].VAL);
                        	    		$("#hrSystemInitialLoadDevice").text(data.basicData[2].VAL);
                        	    		$("#hrSystemInitialLoadParameters").text(data.basicData[3].VAL);
                        	    		$("#hrSystemNumUsers").text(data.basicData[4].VAL);
                        	    		$("#hrSystemProcesses").text(data.basicData[5].VAL);
                        	    		$("#hrSystemMaxProcesses").text(data.basicData[6].VAL);
                        	    		$("#hrMemorySize").text(data.basicData[7].VAL);
                        	     });
                   	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpPartitiontList?moId="+moId,
                       					function(data) {
                   	        	  $("#partitiontList").empty();
                          			  var $htmlp;
                          			  var $htmlli;
                          			  if(data.length==0){
                          				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个分区</span></p>');  
                                    		$("#partitiontList").append($htmlp);
                                    		return;
                          			  }else{
                    
                          				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span class="marginR6">个分区</span></p>');  
                          				  $("#partitiontList").append($htmlp);
                          				  for(var j=0;j<data.length;j++){
                          				  $htmlli=$( ' <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;width: 26px;">'+j+1+'</span>"> '+
                          				  ' <div class="left BaseInfoSNMP" style="margin-left:4px;"> <ul><li><span class="title">索引号</span><span class="content darkblue">'+data[j].hrPartitionIndex+'</span></li>'
                          				  +' <li><span class="title">标识</span><span class="content darkblue">'+data[j].hrPartitionLabel+'</span></li>'
                          				  +' <li><span class="title">分区ID</span><span class="content darkblue">'+data[j].hrPartitionID+'</span></li>'
                          				  +'  <li><span class="title">分区大小</span><span class="content darkblue">'+data[j].hrPartitionSize+'</span></li>'
                          				 +'  <li><span class="title">文件系统索引</span><span class="content darkblue">'+data[j].hrPartitionFSIndex+'</span></li>'
                          				  +'</ul></div> <div class="blank0"></div></li>');
                          				  $("#partitiontList").append($htmlli);
                          				 }
                          			  }	
                             	    	
                         	     });
                   	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpFileList?moId="+moId,
                       					function(data) {
                       	                          $("#fileList").empty();
                       	               			  var $htmlp;
                       	               			  var $htmlli;
                       	               			  if(data.length==0){
                       	               				 $htmlp=  $(' <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个文件系统</span></p>');  
                       	                         		$("#fileList").append($htmlp);
                       	                         		return;
                       	               			  }else{
                       	         
                       	               				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span class="marginR6">个文件系统</span></p>');  
                       	               				  $("#fileList").append($htmlp);
                       	               				  for(var j=0;j<data.length;j++){
                       	               				  $htmlli=$( ' <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;width: 26px;">'+j+1+'</span>"'+
                       	               				  '  <div class="left BaseInfoSNMP" style="margin-left:4px;"><ul>  <li><span class="title">索引号</span><span class="content darkblue">'+data[j].hrFSIndex+'</span></li>'
                       	               				  +'<li><span class="title">MOUNT点</span><span class="content darkblue">'+data[j].hrFSMountPoint+'</span></li>'
                       	               				  +'  <li><span class="title">类型</span><span class="content darkblue">'+data[j].hrFSType+'</span></li>'
                       	               				 +'   <li><span class="title">存储所应号</span><span class="content darkblue">'+data[j].hrFSStorageIndex+'</span></li>'
                       	               				 +'   <li><span class="title">上次全备份时间</span><span class="content darkblue">'+data[j].hrFSLastFullBackupDate+'</span></li>'
                       	               				  +'</ul></div> <div class="blank0"></div></li>');
                       	               				  $("#fileList").append($htmlli);
                       	               				 }
                       	               			  }	          	                          
                       	                          
                       	                          
                             	    	
                         	     });
                   	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpStorageList?moId="+moId,
                       					function(data) {
                   	        	  $("#storageList").empty();
                          			  var $htmlp;
                          			  var $htmlli;
                          			  if(data.length==0){
                          				 $htmlp=  $(' <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个存储</span></p>');  
                                    		$("#storageList").append($htmlp);
                                    		return;
                          			  }else{
                    
                          				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span class="marginR6">个存储</span></p>');  
                          				  $("#storageList").append($htmlp);
                          				  for(var j=0;j<data.length;j++){
                          				  $htmlli=$( ' <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;width: 26px;">'+j+1+'</span>"'+
                          				  '   <div class="left BaseInfoSNMP" style="margin-left:4px;">  <ul><li><span class="title">索引号</span><span class="content darkblue">'+data[j].hrStorageIndex+'</span></li>'
                          				  +' <li><span class="title">类型</span><span class="content darkblue">'+data[j].hrStorageType+'</span></li>'
                          				  +'  <li><span class="title">描述</span><span class="content darkblue">'+data[j].hrStorageDescr+'</span></li>'
                          				 +'   <li><span class="title">空间大小</span><span class="content darkblue">'+data[j].hrStorageSize+'</span></li>'
                          				 +'   <li><span class="title">已用空间</span><span class="content darkblue">'+data[j].hrStorageUsed+'</span></li>'
                          				  +'</ul></div> <div class="blank0"></div></li>');
                          				  $("#storageList").append($htmlli);
                          				 }
                          			  }	      
                             	    	
                         	     });
                   	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpDeviceList?moId="+moId,
                    					function(data) {
                   	        	 $("#deviceList").empty();
                         			  var $htmlp;
                         			  var $htmlli;
                         			  if(data.length==0){
                         				 $htmlp=  $(' <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个设备</span></p>');  
                                   		$("#deviceList").append($htmlp);
                                   		return;
                         			  }else{
                                     
                         				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span class="marginR6">个设备</span></p>');  
                         				  $("#deviceList").append($htmlp);
                         				  for(var j=0;j<data.length;j++){
                         				  $htmlli=$( ' <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;width: 26px;">'+j+1+'</span>"'+
                         				  '   <div class="left BaseInfoSNMP" style="margin-left:4px;">  <ul><li><span class="title">索引号</span><span class="content darkblue">'+data[j].hrDeviceIndex+'</span></li>'
                         				  +' <li><span class="title">类型</span><span class="content darkblue">'+data[j].hrDeviceType+'</span></li>'
                         				  +'  <li><span class="title">描述</span><span class="content darkblue">'+data[j].hrDeviceDescr+'</span></li>'
                         				 +'   <li><span class="title">状态</span><span class="content darkblue">'+data[j].hrDeviceStatus+'</span></li>'
                         				 +'   <li><span class="title">错误信息</span><span class="content darkblue">'+data[j].hrDeviceErrors+'</span></li>'
                         				  +'</ul></div> <div class="blank0"></div></li>');
                         				  $("#deviceList").append($htmlli);
                         				 }
                         			  }	      
                          	    	
                      	         });
									    var runningsource = {
                  	 				datatype: "json",
                  	 				datafields : [ {
                  	 					name : 'hrSWRunIndex',
                  	 					type : 'string'
                  	 				}, {
                  	 					name : 'hrSWRunName',
                  	 					type : 'string'
                  	 				}, {
                  	 					name : 'hrSWRunID',
                  	 					type : 'string'
                  	 				},{
                  	 					name : 'hrSWRunPath',
                  	 					type : 'string'
                  	 				}, {
                  	 					name : 'hrSWRunParameters',
                  	 					type : 'string'
                  	 				},{
                  	 					name : 'hrSWRunType',
                  	 					type : 'string'
                  	 				}, {
                  	 					name : 'hrSWRunStatus',
                  	 					type : 'string'
                  	 				}, {
                  	 					name : 'hrSWRunPerfCPU',
                  	 					type : 'string'
                  	 				}, {
                  	 					name : 'hrSWRunPerfMem',
                  	 					type : 'string'
                  	 				}],
                  	 				url:"${ctx}/pages/dashboard/snmpHost/getSnmpRunningSoftwareList?moId="+moId,
                  	 				id : 'id'
                  	 				
                  	 			};
									
                  	 			function loadRunningGridData(runningsource){
                  	 				//getAlarmComments
                  	 				var datarunningAdapter =new $.jqx.dataAdapter(runningsource);
                  	 				// initialize jqxGrid
                  	 				$("#runningGrid").jqxGrid({
                  	 					width: '99%',
                  	 					height: '94%',
                  	 					source : datarunningAdapter,
                  	 					localization : getLocalization(),
                  	 					//pageable : true,
                  	 					sortable : true,
                  	 					altrows : true,
                  	 					enabletooltips : true,
                  	 					//editable : true,
                  	 					//selectionmode : 'multiplecellsadvanced',
                  	 					columns : [ {
                  	 						text : '索引号',
                  	 						datafield : 'hrSWRunIndex',
                  	 						width : 160
                  	 					}, {
                  	 						text : '名称',
                  	 						datafield : 'hrSWRunName',
                  	 						//cellsformat: 'yyyy-MM-dd HH:mm:ss',
                  	 						width : 136
                  	 					}, {
                  	 						text : 'ID',
                  	 						datafield : 'hrSWRunID',
                  	 						width : 130
                  	 					}, {
                  	 						text : '路径',
                  	 						datafield : 'hrSWRunPath',
                  	 						width : 160
                  	 					}, {
                  	 						text : '参数',
                  	 						datafield : 'hrSWRunParameters',
                  	 						//cellsformat: 'yyyy-MM-dd HH:mm:ss',
                  	 						width : 136
                  	 					}, {
                  	 						text : '类型',
                  	 						datafield : 'hrSWRunType',
                  	 						width : 130
                  	 					},
                  	 					 {
                  	 						text : '状态',
                  	 						datafield : 'hrSWRunStatus',
                  	 						width : 160
                  	 					}, {
                  	 						text : 'CPU信息',
                  	 						datafield : 'hrSWRunPerfCPU',
                  	 						//cellsformat: 'yyyy-MM-dd HH:mm:ss',
                  	 						width : 136
                  	 					}, {
                  	 						text : '内存信息',
                  	 						datafield : 'hrSWRunPerfMem',
                  	 						width : 130
                  	 					}]
                  	 				});
                  	 			}
                  	 			loadRunningGridData(runningsource);
									//////////
                  	 		     var installedsource = {
                       	 				datatype: "json",
                       	 				datafields : [ {
                       	 					name : 'hrSWRunIndex',
                       	 					type : 'string'
                       	 				}, {
                       	 					name : 'hrSWRunName',
                       	 					type : 'string'
                       	 				}, {
                       	 					name : 'hrSWRunID',
                       	 					type : 'string'
                       	 				},{
                       	 					name : 'hrSWRunPath',
                       	 					type : 'string'
                       	 				}, {
                       	 					name : 'hrSWRunParameters',
                       	 					type : 'string'
                       	 				},{
                       	 					name : 'hrSWRunType',
                       	 					type : 'string'
                       	 				}, {
                       	 					name : 'hrSWRunStatus',
                       	 					type : 'string'
                       	 				}, {
                       	 					name : 'hrSWRunPerfCPU',
                       	 					type : 'string'
                       	 				}, {
                       	 					name : 'hrSWRunPerfMem',
                       	 					type : 'string'
                       	 				}],
                       	 				url:"${ctx}/pages/dashboard/snmpHost/getSnmpInstalledSoftwareList?moId="+moId,
                       	 				id : 'id'
                       	 				
                       	 			};
                       	 			function loadInstalledGridData(installedsource){
                       	 				//getAlarmComments
                       	 				var dataInstalledAdapter =new $.jqx.dataAdapter(installedsource);
                       	 				// initialize jqxGrid
                       	 				$("#installedGrid").jqxGrid({
                       	 					width: '99%',
                       	 					height: '94%',
                       	 					source : dataInstalledAdapter,
                       	 					localization : getLocalization(),
                       	 					//pageable : true,
                       	 					sortable : true,
                       	 					altrows : true,
                       	 					enabletooltips : true,
                       	 					//editable : true,
                       	 					//selectionmode : 'multiplecellsadvanced',
                       	 					columns : [ {
                       	 						text : '索引号',
                       	 						datafield : 'hrSWInstalledIndex',
                       	 						width : 160
                       	 					}, {
                       	 						text : '名称',
                       	 						datafield : 'hrSWInstalledName',
                       	 						cellsformat: 'yyyy-MM-dd HH:mm:ss',
                       	 						width : 136
                       	 					}, {
                       	 						text : 'ID',
                       	 						datafield : 'hrSWInstalledID',
                       	 						width : 130
                       	 					},{
                       	 						text : '类型',
                       	 						datafield : 'hrSWInstalledType',
                       	 						width : 130
                       	 					},
                       	 					 {
                       	 						text : '日期',
                       	 						datafield : 'hrSWInstalledDate',
                       	 						width : 160
                       	 					}, {
                       	 						text : '上一次修改时间',
                       	 						datafield : 'hrSWInstalledLastChange',
                       	 						width : 136
                       	 					}, {
                       	 						text : '上一次更新时间',
                       	 						datafield : 'hrSWInstalledLastUpdateTime',
                       	 						width : 130
                       	 					}]
                       	 				});
                       	 			}
                       	 			loadInstalledGridData(installedsource);
										 //SNMP END
                     		 $("#hostip").text(hostip);
                     		 loadNestGrid(moId);
                     		 
                     	     $.getJSON("${ctx}/performance/getBasicInfoData?moId="+moId,
                   					function(data) {
                     	    		 $("#hostName").text(data.basicData[0].VAL);
                         	    	 $("#IPAdd").text(data.basicData[1].VAL);
                         	    	 $("#Manufacturer").text(data.basicData[2].VAL);
                         	    	 $("#CpuCount").text(data.basicData[3].VAL);
                         	    	 $("#CpuType").text(data.basicData[4].VAL);
                         	    	 $("#MemSize").text(data.basicData[5].VAL);
                         	    	 $("#OSVersion").text(data.basicData[6].VAL);	
                     	     });
                     	     $.getJSON("${ctx}/performance/networkInfo?moId="+moId,
                    					function(data) {
                     	    	 $("#networks").empty();
                      	    	 for(var i=0;i<data.root.length;i++){
                                 	  //if(data.root[i].STATUS=="UP"){
                                 		  var $htmlLi =$('<li><b class="Icon_status_green Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">'+data.root[i].NAME+'</span><span class="left gray marginL30">'+data.root[i].IO+'</span></li>');  
                                 		  $("#networks").append($htmlLi);
                                           
                                 	 // }
                                   /*  else{
                                 		  var $htmlLi =$('<li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">'+data.root[i].NAME+'</span><span class="left gray marginL30">'+data.root[i].IO+'</span></li>');  
                                 		  $("#networks").append($htmlLi);
                                 	  }  */
                                 	  }
                                   }); 
                     	     $.getJSON("${ctx}/performance/getLogError?moId="+moId,
                    					function(data) {
                      	      $("#centerLogLabel").text(data.centerLogLabel);
                      	      $("#rightLogLabel").text(data.rightLogLabel);
                      	      $("#leftLogLabel").text(data.leftLogLabel);
                      	      if(typeId=="HOST_WINDOWS"){
                      	    	 
                          	      if(data.leftLogNum==0){
                          	    	  $("#leftLogclass").attr("class","Icon_status_green Icon16 Pos-R left");
                          	      }
                         	          $("#leftLogNum").text(data.leftLogNum);
                      	      }
                      	      else{
                      	    	  if(data.centerLogNum==0){
                          	    	  $("#centerLogclass").attr("class","Icon_status_green Icon16 Pos-R left");
                          	      }
                          	      if(data.leftLogNum==0){
                          	    	  $("#leftLogclass").attr("class","Icon_status_green Icon16 Pos-R left");
                          	      }
                          	      if(data.rightLogNum==0){
                          	    	  $("#rightLogclass").attr("class","Icon_status_green Icon16 Pos-R left");
                          	      }
                          	      $("#centerLogNum").text(data.centerLogNum);
                         	          $("#rightLogNum").text(data.rightLogNum);
                         	          $("#leftLogNum").text(data.leftLogNum); 
                      	      }
                      	      
                      	    
                     	     });
							     
                     	   $.getJSON("${ctx}/performance/courseBasic?moId="+moId,
             					function(data) {
                	    	 
             	    	 $('#processCount').text(data.USR_PROC_NUM);
             	    	 $('#propercentage').attr("data-percent",data.USR_PROC_Percent);
             	    	 $('#proper').text(data.USR_PROC_Percent);
             	    	 var chart = window.chart = $('#propercentage').data('easyPieChart');
             	    	 chart.update(data.USR_PROC_Percent);
              	     }); 
						   	
            	        $.getJSON("${ctx}/performance/memBasic?moId="+moId,
          					function(data) {
             	       $('#remainMem').text(data.FREE_MEM);
             	       if(data.MEM_SYS.length==0){
             	      	 $('#memper').text("0"); 
             	        }else{
             	    	 $('#memper').text(data.MEM_SYS.substring(0,data.MEM_SYS.length-1));
             	       }

             	        var chart = window.chart = $('#mempercentage').data('easyPieChart');
             	       var updateData=parseInt(data.MEM_SYS.substring(0,data.MEM_SYS.length-1));
             	       chart.update(updateData);
           	        }); 
            	     
            	     
            	    $.getJSON("${ctx}/performance/cpuUse?moId="+moId,
          					function(data) {
          	    	 $('#cpuUse').text(data[0].CPU_USR);
          	    	 var chart = window.chart = $('#cpupercent').data('easyPieChart');
        	    	     chart.update(data[0].CPU_USR);
           	        }); 

						  $.getJSON("${ctx}/performance/skipFileBasic?moId="+moId,
      					function(data) {
        		 $("#titleswap").empty();
        	    if(typeId=="HOST_WINDOWS"){
        	    	
        	    	var $htmlli;	
           			
           			$htmlli=$( '<li><p class="blank10"></p><p>剩余虚拟内存</p>'+
           							 ' <div style="margin-top: 2px;"><span class="left w50 f10px darkblue" id="swapfree">'
           							 +'</span><span class="w50 right f10px table-right gray" id="swapsize">'
           							 +'</span></div>'
           							 + ' <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="swaputilpro"> </div>虚拟内存使用率:<span class="f10px darkblue" id="swaputil">'
           							+' </span></li>'
           							); 
           		    $("#titleswap").append($htmlli);
           		    $('#swapsize').text("虚拟内存大小:"+data.SWAP_SIZE);
           		    $('#swapfree').text(data.SWAP_NAME+"MB");
           		    $('#swaputil').text(data.SWAP_UTIL);
          			$("#swaputilpro").jqxProgressBar({ width: '100%', height: 10, value: parseInt(data.SWAP_UTIL.substring(0,data.SWAP_UTIL.length-1)), animationDuration: 1000});
            	    
        	    }
        	    else{  
       			var $htmlli;	
       			
       			$htmlli=$( '<li><p>路径</p> <p class="darkblue" id="swappath"></p><p class="blank10"></p><p>剩余</p>'+
       							 ' <div style="margin-top: 2px;"><span class="left w50 f10px darkblue" id="swapfree">'
       							 +'</span><span class="w50 right f10px table-right gray" id="swapsize">'
       							 +'</span></div>'
       							 + ' <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="swaputilpro"> </div>使用率:<span class="f10px darkblue" id="swaputil">'
       							+' </span></li>'
       							); 
       		    $("#titleswap").append($htmlli);
       		    $('#swappath').text(data.SWAP_NAME);
       		    $('#swapsize').text(data.SWAP_SIZE);
       		    $('#swapfree').text(data.SWAP_FREE+"MB");
       		    $('#swaputil').text(data.SWAP_UTIL);
      			$("#swaputilpro").jqxProgressBar({ width: '100%', height: 10, value: parseInt(data.SWAP_UTIL.substring(0,data.SWAP_UTIL.length-1)), animationDuration: 1000});
        	    }
       	        }); 
					$.getJSON("${ctx}/performance/diskUse?moId="+moId,
                    					function(data) {
                      	    	 $("#titledisk").empty();
 
                        			  var $htmlli;
                        		
                        			  if(data.buckets.length==0){
                                  		return;
                        			  }else{
                        				for(var j=0;j<data.buckets.length;j++){
                        					 //console.log(data.buckets[j].name);
                        					 $htmlli=$( '<li><p>磁盘名称</p><p class="darkblue">'+data.buckets[j].name+'</p><p class="blank10"></p><p>磁盘繁忙比率</p>'+
                        							 '<p class="darkblue">'+data.buckets[j].usage+'</p><p class="blank10"></p><p>读写速率</p><p class="darkblue">'+data.buckets[j].rwrate+'</p>'+
                        							'<p class="blank10"></p><p>每秒传输字节数</p><p class="darkblue">'+data.buckets[j].tranBytes+'</p><p class="blank10"></p></li>'); 
                        				  $("#titledisk").append($htmlli);
                        				 // $("#diskPro"+(j+1)).jqxProgressBar({ width: '100%', height: 10, value: parseInt(data.buckets[j].usage.substring(0,data.buckets[j].usage.length-1)), animationDuration: 1000});
                        				 }
                        			  }	 
                    						
                     	        });
                     	    
                    	      $.getJSON("${ctx}/performance/fileSysUse?moId="+moId,
                  					function(data) {
                    	      $("#titlefile").empty();
                    	      if(typeId=='HOST_WINDOWS'){
                    	    	  if(data.buckets.length==0){
                              		return;
                    			  }else{
                    				for(var j=0;j<data.buckets.length;j++){
                    					
                    					 $htmlli=$( '<li><p>磁盘名称</p><p class="darkblue">'+data.buckets[j].name+'</p><p class="blank10"></p><p>磁盘驱动器读或写入请求所用的时间的百分比</p>'+
                    							 '<p class="darkblue">'+data.buckets[j].timePercent+'</p><p class="blank10"></p><p>逻辑磁盘驱动器上总的可用空闲空间的百分比</p><p class="darkblue">'+data.buckets[j].freePercent+'</p>'+
                    							'<p class="blank10"></p><p>逻辑磁盘驱动器上已使用的空间</p><p class="darkblue">'+data.buckets[j].used+'</p><p class="blank10"></p></li>'); 
                    				
                    				  $("#titlefile").append($htmlli);
                    				 }
                    			  }	 
                    	      }
                    	      else{
                              if(data.buckets.length==0){
                            		return;
                  			  }else{
                                 for(var i=0;i<data.buckets.length;i++){
                               		 /*  var $htmlLi =$(' <li><p>路径</p><p class="darkblue" id="filepath">'+data.buckets[i].name+'</p> <p class="blank10"></p> <p>使用率</p> <div style=”margin-top: 2px;">'+
                               				  '<!--<span class="left w50 f10px darkblue" id="filesize">MB</span>--></div> <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="FilePro'+(i+1)+'"></div><span class="f10px darkblue" id=fileutil">'
                               		  +data.buckets[i].usage+'</span></li>');  */ 
                               		 var $htmlLi =$('<li><p>路径</p><p class="darkblue">'+data.buckets[i].name+'</p>'+
                               				 '<p class="blank10"></p><p>使用率</p><div style="margin-top: 2px;"><span class="left w50 f10px darkblue">已使用:'+data.buckets[i].usage+'</span><span class="w50 right f10px table-right gray">总大小:'+data.buckets[i].total+'MB</span></div>'+
                               				 ' <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa;border-color: #daf0fa;" id="FilePro'+(i+1)+'"></div></li>'); 
                               		//<span class="f10px darkblue">'+data.buckets[i].usage+'</span>
                               		$("#titlefile").append($htmlLi);
                               		$("#FilePro"+(i+1)).jqxProgressBar({ width: '100%', height: 10, value: parseInt(data.buckets[i].usage.substring(0,data.buckets[i].usage.length-1)), animationDuration: 1000});
                    				 
                               	  }
                                     
                  			  }
                    	      }
                   	        }); 
								   $.getJSON("${ctx}/performance/networkSend?moId="+moId,
                   					function(data) {
                   					$('#accSend').text(data.speed);
                    	        });  
                     	    $.getJSON("${ctx}/performance/networkReceive?moId="+moId,
                   					function(data) {
                     	    	$('#accReceived').text(data.speed); 
                   						
                    	        });  
                     	  $.getJSON("${ctx}/performance/diskRead?moId="+moId,
             					function(data) {
               	    	if(typeId=="HOST_WINDOWS"){
               	    		$('#read1').text(data.speed+' bytes/s'); 
               	    		$('#readText1').text("R/W");  
               	    	}else{
               	    		
               	    	}			
              	        });  
               	    $.getJSON("${ctx}/performance/diskWrite?moId="+moId,
             					function(data) {
               	    	//Math.floor(59897.232565684);
               	    	if(typeId=="HOST_WINDOWS"){
               	    		//$('#write1').text(Math.floor(data.speed)+' p/s'); 
               	    	}
               	    	else{
               	    		$('#read1').text(Math.floor(data.speed_text)+' bytes/s'); 
               	    		$('#readText1').text("R/W");  
               	    		$('#readText1').show(); 
               	    	}
               	    	  	
             						
              	        }); 
               	    $.getJSON("${ctx}/performance/skipfilePagein?moId="+moId,
             					function(data) {
               	
               	    	if(typeId=="HOST_WINDOWS"){
               	    		if(data.speed!=""){
               	    			$('#read2').text(data.speed+' p/s'); 
               	    		}
               	    		$('#readText2').text("R/W Pages"); 
               	    		$('#readText2').show();
               	    	} 	
                        else{
                        	
                        	if(data.speed!=""){
                        		$('#read2').text(data.speed+' p/s');
                        	}
                            $('#readText2').text("PageIn:"); 
                            $('#readText2').show();
                        	
               	    	}
             						
              	        }); 
               	    $.getJSON("${ctx}/performance/skipfilePageout?moId="+moId,
             					function(data) {
               	    	if(typeId=="HOST_WINDOWS"){
               	    		
               	    		$('#writeText2').hide();
               	    		$('#write2').empty();
               	    	}
               	    	else{
               	    		
               	    		if(data.speed!=""){
               	    		$('#write2').text(data.speed+' p/s'); 
               	    		}
                        	$('#writeText2').text("PageOut:"); 
                        	$('#writeText2').show();
               	    	}
               	    	
              	        }); 
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
             									
             									//commit(false);
             								}
             							});
             							commit(true);
             						},
             						sort : function() {
             							// update the grid and send a request to the server.
             							$("#"+alarmGridId).jqxGrid(
             									'updatebounddata');
             						},
             						id : 'id',
             						url : '${ctx}/performance/getAlarmData?moId='+moId
             					};

                     	       function loadGridData(source,alarmGridId){ 

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
             						$("#"+alarmGridId).jqxGrid({
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
             								datafield : 'MO_ID',
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
             					
                     	    loadGridData(source,alarmGridId);


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

                          $("#"+kpigrid).jqxGrid(
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


                  	       $('#'+window).jqxWindow('open');
            	       		});
            	      } 
            	 }); 
        	}
        	function doCompleteLoad()
        	{
        		
        		initFun();
                function initFun(){
                
            	 if(document.readyState == "complete"){ //当页面加载状态为完全结束时进入
            		
                   
                    //queryKpi begin
                 	$("#"+queryKpi).click(function() {
                 	
                 		
                   		var textRotationAngle=0;
                   		var rowindexes = $('#'+kpigrid).jqxGrid('getselectedrowindexes');
                   		
                   		if(rowindexes.length==0){
                  			 $("#newWindow").remove();
           	        		   $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>请选择一条KPI！ </div></div>');
           	                   $('#newWindow').jqxWindow({isModal : true, height: 80, width: 150});
           	                  
           	                   return;  
                  		   }
                  		  if(rowindexes.length>5){
                  			 //rowindexes=0;
                  			 $('#'+kpigrid).jqxGrid('clearselection');
           			     $("#newWindow").remove();
                  		     $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>KPI最多只能选择5条记录！ </div></div>');
                             $('#newWindow').jqxWindow({ isModal : true,height: 80, width: 150});
                            
                             return;
                  		   }
                  		    var startHourTemp =$('#'+startHour).jqxDropDownList('getSelectedItem');
           	                var endHourTemp =$('#'+endHour).jqxDropDownList('getSelectedItem');
           	            
                        	var grainsize = $("#"+jqxWidgetData).jqxDropDownList('getSelectedIndex')+1;
              	    	    var startTime = $('#'+startDate).jqxDateTimeInput('getText');
              	     	    var endTime = $('#'+toDate).jqxDateTimeInput('getText');
              	     	    startTime=startTime+" "+startHourTemp.label;
                  	        endTime=endTime+" "+endHourTemp.label;
                  	        for(var i=0;i<rowindexes.length;i++){
                			var row = $("#"+kpigrid).jqxGrid('getrowdata',rowindexes[i]);
                			var yAxis=row.displayKpi;
                			var kpiCode=row.kpi;
                 			//var insAndcode=row.kpi.split("/");
                 			//var ampInstId=insAndcode[0];
                 			//var kpiCode=insAndcode[1];
                			
                			var baseUnit='';
                			
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
                			$("#chart"+(i+num)).show();
                		
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
                   		         
                        		            	/* 	 $("#chart1").empty();
                          		            		$("#chart2").empty();
                          		            		$("#chart3").empty();
                          		            		$("#chart4").empty();
                          		            		$("#chart5").empty();
                          		            		$("#chart10").empty();
                          		            		$("#chart11").empty();
                          		            		$("#chart12").empty();
                          		            		$("#chart13").empty();
                          		            		$("#chart14").empty();   */
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
                 	  
    

                 	} 
             }
        	}
        
            //拦截部分的代码      
        	 $("#"+jqxWidgetData).jqxDropDownList({ source: grainsizes, selectedIndex: 0,  width: '130px', height: '25px',dropDownHeight: '90' });
             $("#"+startDate).jqxDateTimeInput({width: '160px', readonly: true,height: '25px',formatString: 'yyyy-MM-dd'});
             $("#"+toDate).jqxDateTimeInput({width: '160px', readonly: true,height: '25px',formatString: 'yyyy-MM-dd'});
             $("#"+startHour).jqxDropDownList({ source: hours, selectedIndex: 0,  width: '70px', height: '25px' });
             $("#"+endHour).jqxDropDownList({ source: hours, selectedIndex: 0,  width: '70px', height: '25px' });
             
             
             //queryKpi 
       
        	 //拦截部分的代码end
        	 
        	//dashboard部分的告警右键处理 
			$("#"+alarmGridId).on('rowclick',
					function(event) {
						if (event.args.rightclick) {
							$('#'+alarmGridId).jqxGrid('clearselection');
							$("#"+alarmGridId).jqxGrid('selectrow',event.args.rowindex);
							var scrollTop = $(window).scrollTop();
							var scrollLeft = $(window).scrollLeft();
							var contextMenu = $("#Menu").jqxMenu({
									width : 120,
									//height : 300,
									autoOpenPopup : false,
									mode : 'popup'
								  }); 
			            
			                 $("#"+alarmGridId).on('contextmenu', function() {
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
								var rowindex = $("#"+alarmGridId).jqxGrid('getselectedrowindex');
								var rowdata = $('#'+alarmGridId).jqxGrid('getrowdata',rowindex);
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
					    	    var rowindex = $("#"+alarmGridId).jqxGrid('getselectedrowindex');
					    	    
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
									var data = $('#'+alarmGridId).jqxGrid('getrowdata',rowindex);
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
									var rowdata = $('#'+alarmGridId).jqxGrid('getrowdata',rowindex);
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
				} ],
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
					} ]
				});
			}
			loadLogGridData(logsource);
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
					//alarmIds=alarmIdList.join(",");
					//alert(alarmIds);
					batchFilter='alarmIdList='+alarmIds;
			       
			        //return;
			        
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
						    /*  $.post('${ctx}/pages/alarm/batchClose',
						      {
						    	  alarmIdList:alarmIds
						      },
						      function (data) 
						      {
						    	  $("#renling").jqxWindow('hide');
								  $('#alarmGrid').jqxGrid('updatebounddata');
								  $('#alarmGrid').jqxGrid('clearselection');
								  alarmIdList = [];
								  getAllAlertGrade("","");
								  loadListBoxData(listBoxSource);
							} */
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
             $('#showWindowButtonsnmp').click(function() {
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
				$('#'+alarmGridId).jqxGrid({
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
        	$("#windowsnmp").jqxWindow({
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
					$('#tabsnmp').jqxTabs({ height: '100%', width:  '100%' });
					$('#windowsnmp').jqxWindow('focus');
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
             $("#autoRefreshsnmp").click(function() {
                 setInterval(loadNestGrid(moId),20000);
			 });
             $("#manuRefreshsnmp").click(function() {
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
        	 
        	       $("#"+KPIlist).jqxGrid(
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
            
            
              function loadGridData(source,alarmGridId) {
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
						$("#"+alarmGridId).jqxGrid({
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
								datafield : 'MO_ID',
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
        		 moId=rowdata.MO_ID;
        		 // hostip=rowdata.hostName;
        		 typeId=rowdata.type;
        		 $("#chart1").hide();
                 $("#chart2").hide();
                 $("#chart3").hide();
                 $("#chart4").hide();
                 $("#chart5").hide();
                 $("#chart10").hide();
                 $("#chart11").hide();
                 $("#chart12").hide();
                 $("#chart13").hide();
                 $("#chart14").hide();   
        		 var protocal=rowdata.protocal;
        		 if(protocal=='SNMP'){
        			 alarmGridId="alarmGridsnmp";
        			 KPIlist="KPIlistsnmp";
        			 hostip="hostipsnmp";
        			 jqxWidgetData="jqxWidgetData11";
        			 startDate="startDatesnmp";
        			 toDate="toDatesnmp";
        	         startHour="startHoursnmp";
        	         endHour="endHoursnmp";
        	         queryKpi="queryKpisnmp";
        	         kpigrid="kpigridsnmp";
        	         num=10;
        	         window="windowsnmp";
        	         batchupdate="batchupdatesnmp";
        	         //$('#'+kpigrid).jqxGrid('clearselection');
        		 }else{
        			 alarmGridId="alarmGrid";
        			 KPIlist="KPIlist";
        			 hostip="hostip";
        			 jqxWidgetData="jqxWidgetData1";
        			 startDate="startDate";
        			 toDate="toDate";
        	         startHour="startHour";
        	         endHour="endHour";
        	         queryKpi="queryKpi";
        	         kpigrid="kpigrid";
        	         num=1;
        	         window="window";
        	         batchupdate="batchupdate";
        	        // $('#kpigrid').jqxGrid('clearselection');
        		 }
        		  //拦截部分的代码     
             
              	$("#"+jqxWidgetData).jqxDropDownList({ source: grainsizes, selectedIndex: 0,  width: '130px', height: '25px',dropDownHeight: '90' });
                $("#"+startDate).jqxDateTimeInput({width: '160px', readonly: true,height: '25px',formatString: 'yyyy-MM-dd'});
              	$("#"+toDate).jqxDateTimeInput({width: '160px', readonly: true,height: '25px',formatString: 'yyyy-MM-dd'});
              	$("#"+startHour).jqxDropDownList({ source: hours, selectedIndex: 0,  width: '70px', height: '25px' });
              	$("#"+endHour).jqxDropDownList({ source: hours, selectedIndex: 0,  width: '70px', height: '25px' });
        	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpHostBasicInfo?moId="+moId,
           					function(data) {
             	    		$("#hrSystemUptime").text(data.basicData[0].VAL);
             	    		$("#hrSystemDate").text(data.basicData[1].VAL);
             	    		$("#hrSystemInitialLoadDevice").text(data.basicData[2].VAL);
             	    		$("#hrSystemInitialLoadParameters").text(data.basicData[3].VAL);
             	    		$("#hrSystemNumUsers").text(data.basicData[4].VAL);
             	    		$("#hrSystemProcesses").text(data.basicData[5].VAL);
             	    		$("#hrSystemMaxProcesses").text(data.basicData[6].VAL);
             	    		$("#hrMemorySize").text(data.basicData[7].VAL);
             	     });
        	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpPartitiontList?moId="+moId,
            					function(data) {
        	        	  $("#partitiontList").empty();
               			  var $htmlp;
               			  var $htmlli;
               			  if(data.length==0){
               				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个分区</span></p>');  
                         		$("#partitiontList").append($htmlp);
                         		return;
               			  }else{
         
               				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span class="marginR6">个分区</span></p>');  
               				  $("#partitiontList").append($htmlp);
               				  for(var j=0;j<data.length;j++){
               				  $htmlli=$( ' <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;width: 26px;">'+j+1+'</span>"> '+
               				  ' <div class="left BaseInfoSNMP" style="margin-left:4px;"> <ul><li><span class="title">索引号</span><span class="content darkblue">'+data[j].hrPartitionIndex+'</span></li>'
               				  +' <li><span class="title">标识</span><span class="content darkblue">'+data[j].hrPartitionLabel+'</span></li>'
               				  +' <li><span class="title">分区ID</span><span class="content darkblue">'+data[j].hrPartitionID+'</span></li>'
               				  +'  <li><span class="title">分区大小</span><span class="content darkblue">'+data[j].hrPartitionSize+'</span></li>'
               				 +'  <li><span class="title">文件系统索引</span><span class="content darkblue">'+data[j].hrPartitionFSIndex+'</span></li>'
               				  +'</ul></div> <div class="blank0"></div></li>');
               				  $("#partitiontList").append($htmlli);
               				 }
               			  }	
                  	    	
              	     });
        	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpFileList?moId="+moId,
            					function(data) {
            	                          $("#fileList").empty();
            	               			  var $htmlp;
            	               			  var $htmlli;
            	               			  if(data.length==0){
            	               				 $htmlp=  $(' <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个文件系统</span></p>');  
            	                         		$("#fileList").append($htmlp);
            	                         		return;
            	               			  }else{
            	         
            	               				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span class="marginR6">个文件系统</span></p>');  
            	               				  $("#fileList").append($htmlp);
            	               				  for(var j=0;j<data.length;j++){
            	               				  $htmlli=$( ' <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;width: 26px;">'+j+1+'</span>" '+
            	               				  '  <div class="left BaseInfoSNMP" style="margin-left:4px;"><ul>  <li><span class="title">索引号</span><span class="content darkblue">'+data[j].hrFSIndex+'</span></li>'
            	               				  +'<li><span class="title">MOUNT点</span><span class="content darkblue">'+data[j].hrFSMountPoint+'</span></li>'
            	               				  +'  <li><span class="title">类型</span><span class="content darkblue">'+data[j].hrFSType+'</span></li>'
            	               				 +'   <li><span class="title">存储所应号</span><span class="content darkblue">'+data[j].hrFSStorageIndex+'</span></li>'
            	               				 +'   <li><span class="title">上次全备份时间</span><span class="content darkblue">'+data[j].hrFSLastFullBackupDate+'</span></li>'
            	               				  +'</ul></div> <div class="blank0"></div></li>');
            	               				  $("#fileList").append($htmlli);
            	               				 }
            	               			  }	          	                          
            	                          
            	                          
                  	    	
              	     });
        	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpStorageList?moId="+moId,
            					function(data) {
        	        	  $("#storageList").empty();
               			  var $htmlp;
               			  var $htmlli;
               			  if(data.length==0){
               				 $htmlp=  $(' <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个存储</span></p>');  
                         		$("#storageList").append($htmlp);
                         		return;
               			  }else{
         
               				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span class="marginR6">个存储</span></p>');  
               				  $("#storageList").append($htmlp);
               				  for(var j=0;j<data.length;j++){
               				  $htmlli=$( ' <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;width: 26px;">'+j+1+'</span>"'+
               				  '   <div class="left BaseInfoSNMP" style="margin-left:4px;">  <ul><li><span class="title">索引号</span><span class="content darkblue">'+data[j].hrStorageIndex+'</span></li>'
               				  +' <li><span class="title">类型</span><span class="content darkblue">'+data[j].hrStorageType+'</span></li>'
               				  +'  <li><span class="title">描述</span><span class="content darkblue">'+data[j].hrStorageDescr+'</span></li>'
               				 +'   <li><span class="title">空间大小</span><span class="content darkblue">'+data[j].hrStorageSize+'</span></li>'
               				 +'   <li><span class="title">已用空间</span><span class="content darkblue">'+data[j].hrStorageUsed+'</span></li>'
               				  +'</ul></div> <div class="blank0"></div></li>');
               				  $("#storageList").append($htmlli);
               				 }
               			  }	      
                  	    	
              	     });
        	         $.getJSON("${ctx}/pages/dashboard/snmpHost/getSnmpDeviceList?moId="+moId,
         					function(data) {
        	        	 $("#deviceList").empty();
              			  var $htmlp;
              			  var $htmlli;
              			  if(data.length==0){
              				 $htmlp=  $(' <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span class="marginR6">个设备</span></p>');  
                        		$("#deviceList").append($htmlp);
                        		return;
              			  }else{
                        
              				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span class="marginR6">个设备</span></p>');  
              				  $("#deviceList").append($htmlp);
              				  for(var j=0;j<data.length;j++){
              				  $htmlli=$( ' <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12 left" style="top:0px;margin-left: 15px;text-align: center;width: 26px;">'+j+1+'</span>"'+
              				  '   <div class="left BaseInfoSNMP" style="margin-left:4px;">  <ul><li><span class="title">索引号</span><span class="content darkblue">'+data[j].hrDeviceIndex+'</span></li>'
              				  +' <li><span class="title">类型</span><span class="content darkblue">'+data[j].hrDeviceType+'</span></li>'
              				  +'  <li><span class="title">描述</span><span class="content darkblue">'+data[j].hrDeviceDescr+'</span></li>'
              				 +'   <li><span class="title">状态</span><span class="content darkblue">'+data[j].hrDeviceStatus+'</span></li>'
              				 +'   <li><span class="title">错误信息</span><span class="content darkblue">'+data[j].hrDeviceErrors+'</span></li>'
              				  +'</ul></div> <div class="blank0"></div></li>');
              				  $("#deviceList").append($htmlli);
              				 }
              			  }	      
               	    	
           	         });
        	         
        	         var runningsource = {
        	 				datatype: "json",
        	 				datafields : [ {
        	 					name : 'hrSWRunIndex',
        	 					type : 'string'
        	 				}, {
        	 					name : 'hrSWRunName',
        	 					type : 'string'
        	 				}, {
        	 					name : 'hrSWRunID',
        	 					type : 'string'
        	 				},{
        	 					name : 'hrSWRunPath',
        	 					type : 'string'
        	 				}, {
        	 					name : 'hrSWRunParameters',
        	 					type : 'string'
        	 				},{
        	 					name : 'hrSWRunType',
        	 					type : 'string'
        	 				}, {
        	 					name : 'hrSWRunStatus',
        	 					type : 'string'
        	 				}, {
        	 					name : 'hrSWRunPerfCPU',
        	 					type : 'string'
        	 				}, {
        	 					name : 'hrSWRunPerfMem',
        	 					type : 'string'
        	 				}],
        	 				url:"${ctx}/pages/dashboard/snmpHost/getSnmpRunningSoftwareList?moId="+moId,
        	 				id : 'id'
        	 				
        	 			};
        	 			function loadRunningGridData(runningsource){
        	 				//getAlarmComments
        	 				var datarunningAdapter =new $.jqx.dataAdapter(runningsource);
        	 				// initialize jqxGrid
        	 				$("#runningGrid").jqxGrid({
        	 					width: '99%',
        	 					height: '94%',
        	 					source : datarunningAdapter,
        	 					localization : getLocalization(),
        	 					//pageable : true,
        	 					sortable : true,
        	 					altrows : true,
        	 					enabletooltips : true,
        	 					//editable : true,
        	 					//selectionmode : 'multiplecellsadvanced',
        	 					columns : [ {
        	 						text : '索引号',
        	 						datafield : 'hrSWRunIndex',
        	 						width : 160
        	 					}, {
        	 						text : '名称',
        	 						datafield : 'hrSWRunName',
        	 						//cellsformat: 'yyyy-MM-dd HH:mm:ss',
        	 						width : 136
        	 					}, {
        	 						text : 'ID',
        	 						datafield : 'hrSWRunID',
        	 						width : 130
        	 					}, {
        	 						text : '路径',
        	 						datafield : 'hrSWRunPath',
        	 						width : 160
        	 					}, {
        	 						text : '参数',
        	 						datafield : 'hrSWRunParameters',
        	 						//cellsformat: 'yyyy-MM-dd HH:mm:ss',
        	 						width : 136
        	 					}, {
        	 						text : '类型',
        	 						datafield : 'hrSWRunType',
        	 						width : 130
        	 					},
        	 					 {
        	 						text : '状态',
        	 						datafield : 'hrSWRunStatus',
        	 						width : 160
        	 					}, {
        	 						text : 'CPU信息',
        	 						datafield : 'hrSWRunPerfCPU',
        	 						//cellsformat: 'yyyy-MM-dd HH:mm:ss',
        	 						width : 136
        	 					}, {
        	 						text : '内存信息',
        	 						datafield : 'hrSWRunPerfMem',
        	 						width : 130
        	 					}]
        	 				});
        	 			}
        	 			loadRunningGridData(runningsource);
        	 			//////////
        	 		     var installedsource = {
             	 				datatype: "json",
             	 				datafields : [ {
             	 					name : 'hrSWRunIndex',
             	 					type : 'string'
             	 				}, {
             	 					name : 'hrSWRunName',
             	 					type : 'string'
             	 				}, {
             	 					name : 'hrSWRunID',
             	 					type : 'string'
             	 				},{
             	 					name : 'hrSWRunPath',
             	 					type : 'string'
             	 				}, {
             	 					name : 'hrSWRunParameters',
             	 					type : 'string'
             	 				},{
             	 					name : 'hrSWRunType',
             	 					type : 'string'
             	 				}, {
             	 					name : 'hrSWRunStatus',
             	 					type : 'string'
             	 				}, {
             	 					name : 'hrSWRunPerfCPU',
             	 					type : 'string'
             	 				}, {
             	 					name : 'hrSWRunPerfMem',
             	 					type : 'string'
             	 				}],
             	 				url:"${ctx}/pages/dashboard/snmpHost/getSnmpInstalledSoftwareList?moId="+moId,
             	 				id : 'id'
             	 				
             	 			};
             	 			function loadInstalledGridData(installedsource){
             	 				//getAlarmComments
             	 				var dataInstalledAdapter =new $.jqx.dataAdapter(installedsource);
             	 				// initialize jqxGrid
             	 				$("#installedGrid").jqxGrid({
             	 					width: '99%',
             	 					height: '94%',
             	 					source : dataInstalledAdapter,
             	 					localization : getLocalization(),
             	 					//pageable : true,
             	 					sortable : true,
             	 					altrows : true,
             	 					enabletooltips : true,
             	 					//editable : true,
             	 					//selectionmode : 'multiplecellsadvanced',
             	 					columns : [ {
             	 						text : '索引号',
             	 						datafield : 'hrSWInstalledIndex',
             	 						width : 160
             	 					}, {
             	 						text : '名称',
             	 						datafield : 'hrSWInstalledName',
             	 						cellsformat: 'yyyy-MM-dd HH:mm:ss',
             	 						width : 136
             	 					}, {
             	 						text : 'ID',
             	 						datafield : 'hrSWInstalledID',
             	 						width : 130
             	 					},{
             	 						text : '类型',
             	 						datafield : 'hrSWInstalledType',
             	 						width : 130
             	 					},
             	 					 {
             	 						text : '日期',
             	 						datafield : 'hrSWInstalledDate',
             	 						width : 160
             	 					}, {
             	 						text : '上一次修改时间',
             	 						datafield : 'hrSWInstalledLastChange',
             	 						width : 136
             	 					}, {
             	 						text : '上一次更新时间',
             	 						datafield : 'hrSWInstalledLastUpdateTime',
             	 						width : 130
             	 					}]
             	 				});
             	 			}
             	 			loadInstalledGridData(installedsource);
     
        		//qinjie }
        		 //SNMP END
        		 $("#forwardConfirm").click(function() {
									$.getJSON("${ctx}/pages/alarm/forwardAlarm?alarmId="+ alarmId,
											function(data) {
												
												
											});
									alarmId="";
									$("#alarmforward").jqxWindow('hide');
								});
								
								$("#forwardCancel").click(function() {
									$("#alarmforward").jqxWindow('hide');
								});
        	     //dashboard部分的告警右键处理 
          		 $("#"+alarmGridId).on('rowclick',
          					function(event) {
          						if (event.args.rightclick) {
          							$('#'+alarmGridId).jqxGrid('clearselection');
          							$("#"+alarmGridId).jqxGrid('selectrow',event.args.rowindex);
          							var scrollTop = $(window).scrollTop();
          							var scrollLeft = $(window).scrollLeft();
          							var contextMenu = $("#Menu").jqxMenu({
          									width : 120,
          									//height : 300,
          									autoOpenPopup : false,
          									mode : 'popup'
          								  }); 
          			            
          			                 $("#"+alarmGridId).on('contextmenu', function() {
          				             return false;
          			                 });  
          							 contextMenu.jqxMenu('open',parseInt(event.args.originalEvent.clientX)+ 5+ scrollLeft,
          								            parseInt(event.args.originalEvent.clientY)+ 5+ scrollTop); 
          							return false;
          						}
          				});
          			//dashboard部分的告警右键处理 end
        		 $("#"+hostip).text(rowdata.hostName);
        		 loadNestGrid(moId);
        	     $.getJSON("${ctx}/performance/getBasicInfoData?moId="+moId,
      					function(data) {
        	    		 $("#hostName").text(data.basicData[0].VAL);
            	    	 $("#IPAdd").text(data.basicData[1].VAL);
            	    	 $("#Manufacturer").text(data.basicData[2].VAL);
            	    	 $("#CpuCount").text(data.basicData[3].VAL);
            	    	 $("#CpuType").text(data.basicData[4].VAL);
            	    	 $("#MemSize").text(data.basicData[5].VAL);
            	    	 $("#OSVersion").text(data.basicData[6].VAL);	
        	     });
        	     $.getJSON("${ctx}/performance/networkInfo?moId="+moId,
       					function(data) {
        	    	 $("#networks").empty();
                      for(var i=0;i<data.root.length;i++){
                    	  //if(data.root[i].STATUS=="UP"){
                    		  var $htmlLi =$('<li><b class="Icon_status_green Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">'+data.root[i].NAME+'</span><span class="left gray marginL30">'+data.root[i].IO+'</span></li>');  
                    		  $("#networks").append($htmlLi);
                              
                    	  //}
                    	 /*  else{
                    		  var $htmlLi =$('<li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">'+data.root[i].NAME+'</span><span class="left gray marginL30">'+data.root[i].IO+'b/s</span></li>');  
                    		  $("#networks").append($htmlLi);
                    	  } */
                    	  }
                      }); 
        	     $.getJSON("${ctx}/performance/getLogError?moId="+moId,
       					function(data) {
         	      $("#centerLogLabel").text(data.centerLogLabel);
         	      $("#rightLogLabel").text(data.rightLogLabel);
         	      $("#leftLogLabel").text(data.leftLogLabel);
         	      if(typeId=="HOST_WINDOWS"){
         	    	 $("#logDiv1").hide();
         	    	 $("#logDiv2").hide();
             	      if(data.leftLogNum==0){
             	    	  $("#leftLogclass").attr("class","Icon_status_green Icon16 Pos-R left");
             	      }
            	      $("#leftLogNum").text(data.leftLogNum);
         	      }
         	      else{
         	    	 $("#logDiv1").show();
         	    	 $("#logDiv2").show();
         	    	  if(data.centerLogNum==0){
             	    	  $("#centerLogclass").attr("class","Icon_status_green Icon16 Pos-R left");
             	      }
             	      if(data.leftLogNum==0){
             	    	  $("#leftLogclass").attr("class","Icon_status_green Icon16 Pos-R left");
             	      }
             	      if(data.rightLogNum==0){
             	    	  $("#rightLogclass").attr("class","Icon_status_green Icon16 Pos-R left");
             	      }
             	      $("#centerLogNum").text(data.centerLogNum);
            	          $("#rightLogNum").text(data.rightLogNum);
            	          $("#leftLogNum").text(data.leftLogNum); 
         	      }
         	      
         	    
        	     });
        	    
       	     $.getJSON("${ctx}/performance/courseBasic?moId="+moId,
    					function(data) {
       	    	 
    	    	 $('#processCount').text(data.USR_PROC_NUM);
    	    	 $('#propercentage').attr("data-percent",data.USR_PROC_Percent);
    	    	 $('#proper').text(data.USR_PROC_Percent);
    	    	 var chart = window.chart = $('#propercentage').data('easyPieChart');
    	    	 chart.update(data.USR_PROC_Percent);
     	     }); 
    	     
    	 
    	     /* $.getJSON("${ctx}/performance/memUse?moId="+moId,
  					function(data) {
    	    	    
     	     $('#MEM').attr("data-percent",data[0].MEM_USR);
  			 $('#MEM').circliful();    
   	         }); */
    	     
    	     $.getJSON("${ctx}/performance/memBasic?moId="+moId,
  					function(data) {
     	     $('#remainMem').text(data.FREE_MEM);
     	     if(data.MEM_SYS.length==0){
     	    	 $('#memper').text("0"); 
     	     }else{
     	    	 $('#memper').text(data.MEM_SYS.substring(0,data.MEM_SYS.length-1));
     	     }
     	      var chart = window.chart = $('#mempercentage').data('easyPieChart');
     	      var updateData=parseInt(data.MEM_SYS.substring(0,data.MEM_SYS.length-1));
     	      chart.update(updateData);
   	        }); 
    	     
    	     
    	    $.getJSON("${ctx}/performance/cpuUse?moId="+moId,
  					function(data) {
  	    	 $('#cpuUse').text(data[0].CPU_USR);
  	    	 var chart = window.chart = $('#cpupercent').data('easyPieChart');
	    	 chart.update(data[0].CPU_USR);
   	        }); 
        	    
        	 $.getJSON("${ctx}/performance/skipFileBasic?moId="+moId,
      					function(data) {
        		 $("#titleswap").empty();
        	    if(typeId=="HOST_WINDOWS"){
        	    	
        	    	var $htmlli;	
           			
           			$htmlli=$( '<li><p class="blank10"></p><p>剩余虚拟内存</p>'+
           							 ' <div style="margin-top: 2px;"><span class="left w50 f10px darkblue" id="swapfree">'
           							 +'</span><span class="w50 right f10px table-right gray" id="swapsize">'
           							 +'</span></div>'
           							 + ' <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="swaputilpro"> </div>虚拟内存使用率:<span class="f10px darkblue" id="swaputil">'
           							+' </span></li>'
           							); 
           		    $("#titleswap").append($htmlli);
           		    $('#swapsize').text("虚拟内存大小:"+data.SWAP_SIZE);
           		    $('#swapfree').text(data.SWAP_NAME+"MB");
           		    $('#swaputil').text(data.SWAP_UTIL);
          			$("#swaputilpro").jqxProgressBar({ width: '100%', height: 10, value: parseInt(data.SWAP_UTIL.substring(0,data.SWAP_UTIL.length-1)), animationDuration: 1000});
            	    
        	    }
        	    else{  
       			var $htmlli;	
       			
       			$htmlli=$( '<li><p>路径</p> <p class="darkblue" id="swappath"></p><p class="blank10"></p><p>剩余</p>'+
       							 ' <div style="margin-top: 2px;"><span class="left w50 f10px darkblue" id="swapfree">'
       							 +'</span><span class="w50 right f10px table-right gray" id="swapsize">'
       							 +'</span></div>'
       							 + ' <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="swaputilpro"> </div>使用率:<span class="f10px darkblue" id="swaputil">'
       							+' </span></li>'
       							); 
       		    $("#titleswap").append($htmlli);
       		    $('#swappath').text(data.SWAP_NAME);
       		    $('#swapsize').text(data.SWAP_SIZE);
       		    $('#swapfree').text(data.SWAP_FREE+"MB");
       		    $('#swaputil').text(data.SWAP_UTIL);
      			$("#swaputilpro").jqxProgressBar({ width: '100%', height: 10, value: parseInt(data.SWAP_UTIL.substring(0,data.SWAP_UTIL.length-1)), animationDuration: 1000});
        	    }
       	        }); 
        	    $.getJSON("${ctx}/performance/diskUse?moId="+moId,
      					function(data) {
        	    	 $("#titledisk").empty();
          			  
          			  var $htmlli;
          			 
          			  if(data.buckets.length==0){
                    		return;
          			  }else{
          				for(var j=0;j<data.buckets.length;j++){
          					
          					 $htmlli=$( '<li><p>磁盘名称</p><p class="darkblue">'+data.buckets[j].name+'</p><p class="blank10"></p><p>磁盘繁忙比率</p>'+
          							 '<p class="darkblue">'+data.buckets[j].usage+'</p><p class="blank10"></p><p>读写速率</p><p class="darkblue">'+data.buckets[j].rwrate+'</p>'+
          							'<p class="blank10"></p><p>每秒传输字节数</p><p class="darkblue">'+data.buckets[j].tranBytes+'</p><p class="blank10"></p></li>'); 
          				
          				 /*  $htmlli=$( '<li><p>名称:</p> <p class="darkblue" id="diskname">'+data.buckets[j].name+'</p> '+
          				  '   <p class="blank10"></p><p>使用率:</p><div style="margin-top: 2px;"> '+
          				  '</div> <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="diskPro'+(j+1)+'">'
          				  +'</div><span class="f10px darkblue" id="diskutil">'+data.buckets[j].usage+'</span></li> ');
          				  */ $("#titledisk").append($htmlli);
          				 // $("#diskPro"+(j+1)).jqxProgressBar({ width: '100%', height: 10, value: parseInt(data.buckets[j].usage.substring(0,data.buckets[j].usage.length-1)), animationDuration: 1000});
          				 }
          			  }	 
      						
       	        }); 
        	  /*   $.getJSON("${ctx}/performance/diskBasic?moId="+moId,
      					function(data) {
      			 $('#diskpath').text(data.FDISK_NAME);
       			 $('#disksize').text(data.FDISK_TOTAL);
       			 $('#diskutil').text(data.FDISK_CAPABILITY);
      						
       	        });  */
        	    
        	    $.getJSON("${ctx}/performance/fileSysUse?moId="+moId,
      					function(data) {
        	      $("#titlefile").empty();
        	      if(typeId=='HOST_WINDOWS'){
        	    	  if(data.buckets.length==0){
                  		return;
        			  }else{
        				for(var j=0;j<data.buckets.length;j++){
        					
        					 $htmlli=$( '<li><p>磁盘名称</p><p class="darkblue">'+data.buckets[j].name+'</p><p class="blank10"></p><p>磁盘驱动器读或写入请求所用的时间的百分比</p>'+
        							 '<p class="darkblue">'+data.buckets[j].timePercent+'</p><p class="blank10"></p><p>逻辑磁盘驱动器上总的可用空闲空间的百分比</p><p class="darkblue">'+data.buckets[j].freePercent+'</p>'+
        							'<p class="blank10"></p><p>逻辑磁盘驱动器上已使用的空间</p><p class="darkblue">'+data.buckets[j].used+'</p><p class="blank10"></p></li>'); 
        				
        				  $("#titlefile").append($htmlli);
        				 }
        			  }	 
        	      }
        	      else{
                  if(data.buckets.length==0){
                		return;
      			  }else{
                     for(var i=0;i<data.buckets.length;i++){
                   		 /*  var $htmlLi =$(' <li><p>路径</p><p class="darkblue" id="filepath">'+data.buckets[i].name+'</p> <p class="blank10"></p> <p>使用率</p> <div style=”margin-top: 2px;">'+
                   				  '<!--<span class="left w50 f10px darkblue" id="filesize">MB</span>--></div> <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="FilePro'+(i+1)+'"></div><span class="f10px darkblue" id=fileutil">'
                   		  +data.buckets[i].usage+'</span></li>');  */ 
                   		 var $htmlLi =$('<li><p>路径</p><p class="darkblue">'+data.buckets[i].name+'</p>'+
                   				 '<p class="blank10"></p><p>使用率</p><div style="margin-top: 2px;"><span class="left w50 f10px darkblue">已使用:'+data.buckets[i].usage+'</span><span class="w50 right f10px table-right gray">总大小:'+data.buckets[i].total+'MB</span></div>'+
                   				 ' <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa;border-color: #daf0fa;" id="FilePro'+(i+1)+'"></div></li>'); 
                   		//<span class="f10px darkblue">'+data.buckets[i].usage+'</span>
                   		$("#titlefile").append($htmlLi);
                   		$("#FilePro"+(i+1)).jqxProgressBar({ width: '100%', height: 10, value: parseInt(data.buckets[i].usage.substring(0,data.buckets[i].usage.length-1)), animationDuration: 1000});
        				 
                   	  }
                         
      			  }
        	      }
       	        }); 
        	    
        	    $.getJSON("${ctx}/performance/networkSend?moId="+moId,
      					function(data) {
      					$('#accSend').text(data.speed);
       	        });  
        	    $.getJSON("${ctx}/performance/networkReceive?moId="+moId,
      					function(data) {
        	    	$('#accReceived').text(data.speed); 
      						
       	        });  
        	    $.getJSON("${ctx}/performance/diskRead?moId="+moId,
     					function(data) {
       	    	if(typeId=="HOST_WINDOWS"){
       	    		$('#read1').text(data.speed+' bytes/s'); 
       	    		$('#readText1').text("R/W");  
       	    		$('#readText1').show();
       	    	}else{
       	    		
       	    	}			
      	        });  
       	    $.getJSON("${ctx}/performance/diskWrite?moId="+moId,
     					function(data) {
       	    	//Math.floor(59897.232565684);
       	    	if(typeId=="HOST_WINDOWS"){
       	    		//$('#write1').text(Math.floor(data.speed)+' p/s'); 
       	    	}
       	    	else{
       	    		$('#read1').text(Math.floor(data.speed_text)+' bytes/s'); 
       	    		$('#readText1').text("R/W");  
       	    		$('#readText1').show(); 
       	    	}
       	    	  	
     						
      	        }); 
       	    $.getJSON("${ctx}/performance/skipfilePagein?moId="+moId,
     					function(data) {
       	
       	    	if(typeId=="HOST_WINDOWS"){
       	    		if(data.speed!=""){
       	    			$('#read2').text(data.speed+' p/s'); 
       	    		}
       	    		$('#readText2').text("R/W Pages"); 
       	    		$('#readText2').show();
       	    	} 	
                else{
                	
                	if(data.speed!=""){
                		$('#read2').text(data.speed+' p/s');
                	}
                    $('#readText2').text("PageIn:"); 
                    $('#readText2').show();
                	
       	    	}
     						
      	        }); 
       	    $.getJSON("${ctx}/performance/skipfilePageout?moId="+moId,
     					function(data) {
       	    	if(typeId=="HOST_WINDOWS"){
       	    		
       	    		$('#writeText2').hide();
       	    		$('#write2').empty();
       	    	}
       	    	else{
       	    		
       	    		if(data.speed!=""){
       	    		$('#write2').text(data.speed+' p/s'); 
       	    		}
                	$('#writeText2').text("PageOut:"); 
                	$('#writeText2').show();
       	    	}
       	    	
      	        }); 
        	 
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
						//console.log(rowdata.ID);
							var url = '${ctx}/pages/alarm/aknowledge?content='
									+ rowdata.attr_result
									+ '&alarmId=' + rowdata.ID;
							$.ajax({
								cache : false,
								dataType : 'json',
								url : url,
								success : function(data, status,
										xhr) {
									
								},
								error : function(jqXHR, textStatus,
										errorThrown) {
									
									//commit(false);
								}
							});
							commit(true);
						},
						sort : function() {
							// update the grid and send a request to the server.
							$("#"+alarmGridId).jqxGrid(
									'updatebounddata');
						},
						id : 'id',
						url : '${ctx}/performance/getAlarmData?moId='+moId
					};
        	    loadGridData(source,alarmGridId);
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
								       loadGridData(source,alarmGridId);
										
								$("#searchwindow").jqxWindow(
										'close');
							});
					//右键相关的 在这里
					
					//end
					
					//init grid for tab3
					
					
		     //
		     
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
             $("#"+kpigrid).jqxGrid(
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
            
        	   $('#'+window).jqxWindow('open');
        	 });
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
            		    //item1.value
            		    loadLevelAlarmData(item1.label);
            		    hostsource.url = "${ctx}/performance/getHostPerformanceList?bizName="+encodeURI(encodeURI(item1.label));
            		    loadGrid(hostsource);
            		    //getData(item1);
            		    var url='${ctx}/performance/getHostOverViewPerformanceList?bizName='+encodeURI(encodeURI(item1.label));
				        loadHostOverView(url);
				        regClick();
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
            	 typeId=$(this).attr("id");
            	 hostsource.url = "${ctx}/performance/getHostPerformanceList?typeId="+typeId+"&bizName="+encodeURI(encodeURI(item2.label));
            	 var url='${ctx}/performance/getHostOverViewPerformanceList?typeId='+typeId+"&bizName="+encodeURI(encodeURI(item2.label));
            	
            	 loadHostOverView(url);
     		     loadGrid(hostsource);
             });
            function loadLevelAlarmData(bizName){
         	$.getJSON("${ctx}/performance/getAllLevelAlarmDataByType?typeId=HOST&bizName="+encodeURI(encodeURI(bizName)),
					function(data) {
         		$("#hpu-level1").text("0");
         		$("#hpu-level2").text("0");
         		$("#hpu-level3").text("0");
         		$("#hpu-level4").text("0");
         		$("#hpu-level5").text("0");
         		$("#linux-level1").text("0");
         		$("#linux-level2").text("0");
         		$("#linux-level3").text("0");
         		$("#linux-level4").text("0");
         		$("#linux-level5").text("0");

         		$("#win-level1").text("0");
         		$("#win-level2").text("0");
         		$("#win-level3").text("0");
         		$("#win-level4").text("0");
         		$("#win-level5").text("0");
         		$("#sol-level1").text("0");
         		$("#sol-level2").text("0");
         		$("#sol-level3").text("0");
         		$("#sol-level4").text("0");
         		$("#sol-level5").text("0");
         		wintotal=0;
         		sunostotal=0;
         		linuxtotal=0;
         		hputotal=0;
 
       		  for(var i=0;i<data.length;i++){
       			if(data[i].type=="HOST_HP-UX"){
       				if(data[i].grade==1){
       					$("#hpu-level1").text(data[i].count);
       					hputotal+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#hpu-level2").text(data[i].count);
       					hputotal+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#hpu-level3").text(data[i].count);
       					hputotal+=data[i].count;
       				}
       				if(data[i].grade==4){
       					$("#hpu-level4").text(data[i].count);
       					hputotal+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#hpu-level5").text(data[i].count);
       					hputotal+=data[i].count;
       				}
       			
       			}
       			if(data[i].type=="HOST_LINUX"){
       				if(data[i].grade==1){
       					$("#linux-level1").text(data[i].count);
       					 linuxtotal+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#linux-level2").text(data[i].count);
       					linuxtotal+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#linux-level3").text(data[i].count);
       					linuxtotal+=data[i].count;
       				}
       				if(data[i].grade==4){
       					$("#linux-level4").text(data[i].count);
       					linuxtotal+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#linux-level5").text(data[i].count);
       					linuxtotal+=data[i].count;
       				}
       				
       			}
       			if(data[i].type=="HOST_WINDOWS"){
       				if(data[i].grade==1){
       					$("#win-level1").text(data[i].count);
       					wintotal+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#win-level2").text(data[i].count);
       					wintotal+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#win-level3").text(data[i].count);
       					wintotal+=data[i].count;
       				}
       				if(data[i].GRADE==4){
       					$("#win-level4").text(data[i].count);
       					wintotal+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#win-level5").text(data[i].count);
       					wintotal+=data[i].count;
       				}
       				
       			}
       			if(data[i].type=="HOST_SUNOS"){
       				if(data[i].grade==1){
       					$("#sol-level1").text(data[i].count);
       					sunostotal+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#sol-level2").text(data[i].count);
       					sunostotal+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#sol-level3").text(data[i].count);
       					sunostotal+=data[i].count;
       				}
       				if(data[i].grade==4){
       					$("#sol-level4").text(data[i].count);
       					sunostotal+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#sol-level5").text(data[i].count);
       					sunostotal+=data[i].count;
       				}
       				
       			}
       			
       		}
       		$("#wintotal").text(wintotal);
   			$("#soltotal").text(sunostotal);
   			$("#linuxtotal").text(linuxtotal);
				$("#hputotal").text(hputotal);
				  
			});
            }
            loadLevelAlarmData(item2.label);
            var url;
            if($.getUrlParam('moId')!=null){
                url = "${ctx}/performance/getHostPerformanceList?bizName="+encodeURI(encodeURI(item2.label))+'&moId='+$.getUrlParam('moId');
            }
            //
             url = "${ctx}/performance/getHostPerformanceList?bizName="+encodeURI(encodeURI(item2.label));
     			// prepare the data
     		var hostsource = {
     				datatype : "json",
     				datafields : [ {
     					name : 'MO_ID',
     					type : 'string'
     				},
     				{
     					name : 'type',
     					type : 'string'
     				},
     				{
     					name : 'hostName',
     					type : 'string'
     				}, {
     					name : 'count',
     					type : 'string'
     				},  {
     					name : 'status',
     					type : 'string'
     				},{
     					name : 'cpuUsg',
     					type : 'string'
     				}, {
     					name : 'memUsg',
     					type : 'string'
     				}, {
     					name : 'swap',
     					type : 'string'
     				},{
     					name : 'syslog',
     					type : 'string'
     				} ,{
     					name : 'runtime',
     					type : 'string'
     				} ,{
     					name : 'protocal',
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
     			//var item = $("#bizList").jqxDropDownList('getItemByValue', "业务系统分类"); not working
     		      if($.getUrlParam('biz')==""){
     		    	     var items = $("#bizList").jqxDropDownList('getItems'); 
     		    	     for(var i=0;i<items.length;i++){
     		    	    	 if("others"==items[i].label){
     		    	    		$("#bizList").jqxDropDownList('selectItem',items[i]);
     		    	    	 }
     		    	    }
     		            hostsource.url = "${ctx}/performance/getHostPerformanceList?bizName=others"+"&moId="+$.getUrlParam('moId');
     				   
     		            loadGrid(hostsource);
     				    var url='${ctx}/performance/getHostOverViewPerformanceList?bizName=others'+"&moId="+$.getUrlParam('moId');
     			        loadHostOverView(url);
     			        
     		            }
     		      else if($.getUrlParam('moId')!=null){
     		    	  var items = $("#bizList").jqxDropDownList('getItems'); 
  		    	       for(var i=0;i<items.length;i++){
  		    	    	 if(decodeURI($.getUrlParam('biz'))==items[i].label){
  		    	    		$("#bizList").jqxDropDownList('selectItem',items[i]);
  		    	    	 }
  		    	       }
     		    	  hostsource.url = "${ctx}/performance/getHostPerformanceList?bizName="+$.getUrlParam('biz')+"&moId="+$.getUrlParam('moId');
         	    	    loadGrid(hostsource);
    				    var url='${ctx}/performance/getHostOverViewPerformanceList?bizName='+$.getUrlParam('biz')+"&moId="+$.getUrlParam('moId');
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
			    	if(value=="OK"||value=="NO"){
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
				var index = $("#bizList").jqxDropDownList('getSelectedIndex');
		        var item = $("#bizList").jqxDropDownList('getItem', index);
		        if(item.label=='其他') {
		        	item.label='others';
		        }
				hostsource.url = '${ctx}/performance/getHostPerformanceList?queryIP='+ ipaddress+"&bizName="+encodeURI(encodeURI(item.label));
				loadGrid(hostsource);
				var url='${ctx}/performance/getHostOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label))+'&queryIP='+ipaddress;
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
   
        function loadHostOverView(url){
       	if(url==undefined){
       		if($.getUrlParam('moId')==null){
       			url='${ctx}/performance/getHostOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label));
       		}else{
       			url='${ctx}/performance/getHostOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label))+'&moId='+$.getUrlParam('moId');
       		  
       		}
        }

       	$.getJSON(url,function(data) {
       		 $("#overviewtable").find("td").each(function(){ 
            	if($(this).attr("class")!=undefined){
           		$(this).removeAttr("class");
           		$(this).removeAttr("id");
           		$(this).removeAttr("moId");
           		$(this).removeAttr("protocal");
           		}
             });  
       		/* $("#overviewtable").find("td").each(function(){ 
            	if($(this).attr("class")!=undefined){
           		console.log($(this));
           		}
             });   */
       		for(var j=0;j<data.length;j++){
       			 x=randx();
       			 y=randy();
       			 count=j;
                 if($("#overviewtable tr:eq("+x+") td:eq("+y+")").attr("id")==undefined){
       				 
       				 if(data[j].maxlevel=="5"){
       					
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_red").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
           			 } if(data[j].maxlevel=="4"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_orange").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
           			 } if(data[j].maxlevel=="3"){
           				
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_yellow").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
               			 
           			 } if(data[j].maxlevel=="2"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_blue").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
               			 
           			 } if(data[j].maxlevel=="1"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_green").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
           			 }
           			if(data[j].maxlevel=="0"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+y+")").addClass("TD_default").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
           			 }  
       			 }
       			 else{
       				 if(data[j].maxlevel=="5"){
       				
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_red").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
           			 } if(data[j].maxlevel=="4"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_orange").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
               			 
           			 } if(data[j].maxlevel=="3"){
           			
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_yellow").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
               			 
           			 } if(data[j].maxlevel=="2"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_blue").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
               			 
           			 } if(data[j].maxlevel=="1"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_green").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
           			 }
           			 if(data[j].maxlevel=="0"){
           				 $("#overviewtable tr:eq("+x+") td:eq("+(y+1)+")").addClass("TD_default").attr('id','Tooltip'+j).attr('moId',data[j].mo_id).attr('protocal',data[j].protocal);
           			 } 
       			 }
       				 createToolTip(data[count],'Tooltip'+(count--));
       			
       		}
       	});
       }
       loadHostOverView();
        
       function createToolTip(data,id){
   	    var content="<b>"+data.hostName+"</b><br/><b>CPU(%):</b>"+data.cpuUsg+"<br ><b>MEM(%):</b>"
   		+data.memUsg+"<br ><b>Swap(%):</b>"+data.swap+"<br ><b>Syslog:</b>"+data.syslog+"<br ><b>runtime:</b>"+data.runtime;
   		$("#"+id).jqxTooltip({ content: content, 
   			 position: 'mouse'});
     	}

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
				columnsresize : false,
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
					width : 200
				}, {
					text : '告警数',
					datafield : 'count',
					width : 100
				}, 
				 {
					text : '设备状态',
					datafield : 'status',
					//cellsrenderer : swaprenderer,
					minwidth : 100
				},
				{
					text : 'CPU使用率(%)',
					datafield : 'cpuUsg',
					cellsrenderer : cpurenderer,
					width : 180
				}, {
					text : 'MEM使用率(%)',
					datafield : 'memUsg',
					cellsrenderer : memrenderer,
					width : 180
				}, {
					text : 'Swap使用率(%)',
					datafield : 'swap',
					cellsrenderer : swaprenderer,
					minwidth : 180
				}, 
				{
					text : 'Syslog',
					datafield : 'syslog',
					cellsrenderer : syslogrenderer,
					minwidth : 80
				},
				{
					text : '持续运行时长',
					datafield : 'runtime',
					minwidth : 80
				},
				{
					text : '采集方式',
					datafield : 'protocal',
					minwidth : 80
				}]
			  });
             $('#jqxgrid').on('bindingcomplete', function (event) {
             });
		    }
        });
        function closeFun(){	
     	   $('#window').jqxWindow('close');
       }
        function closeFun1(){	
      	   $('#windowsnmp').jqxWindow('close');
         }
        $('.percentage').easyPieChart({
            animate: 1000
         });
/* $('.percentage-light').easyPieChart({
            barColor: function(percent) {
                            percent /= 100;
                            return "rgb(" + Math.round(255 * (1-percent)) + ", " + Math.round(255 * percent) + ", 0)";
            },
            trackColor: '#666',
            scaleColor: false,
            lineCap: 'butt',
            lineWidth: 15,
            animate: 1000,
});

$('.updateEasyPieChart').on('click', function(e) {
e.preventDefault();
$('.percentage, .percentage-light').each(function() {
            var newValue = Math.round(100*Math.random());
            $(this).data('easyPieChart').update(newValue);
            $('span', this).text(newValue);
});
}); */

        $("#explore-nav li a").click(function() {
            // Figure out current list via CSS class
            var curList = $("#explore-nav li a.current").attr("rel");
            
            // List moving to
            var $newList = $(this);
            
            // Set outer wrapper height to height of current inner list
            var curListHeight = $("#all-list-wrap").height();
            $("#all-list-wrap").height(curListHeight);
            
            // Remove highlighting - Add to just-clicked tab
            $("#explore-nav li a").removeClass("current");
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
            function show3(oEvent){  
                var UserBox3=document.getElementById("ShowTitleMenu");

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
            function hide(){
              document.getElementById("ShowUserBoxMain").style.display = "none";
            }
        </script>

<script src="${ctx}/pages/avmon_back_gif.js"></script>





