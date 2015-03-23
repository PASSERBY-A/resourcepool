<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
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
background-image: url('${ctx}/resources/images/icon-down-white.png');
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

.jqxSwitchButton {
	height: 26px;
	line-height: 18px;
	background-color: #0096d6;
	border: 1px solid #dddddd;
	border-radius: 20px;
}
.chartContainer {
	background: url('${ctx}/resources/images/temperature-bg.png') no-repeat center center;
	width: 68px;
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
        <div id="top" class="top">
    <div class="fixed">
    <!-- 
      <a href="${ctx}/main"><div class="LOGO"></div></a>
       -->
      
                                <a href="${ctx}/performance/overview"><div class="Avmon_back"></div></a>
  
      <div class="MainTitle f28px LineH12 left">性能中心</div>
      <div class="left Padding10"><div id="jqxSystem" class="left" style="background-color: #0096d6; color:#ffffff; border:0px;"></div></div>
      
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

    <div class="content" style=" padding:2px;">
    
<div>
		<ul  class="bxslider">
			
            	<li>
<!-- Content box-04
      ================================================== -->  
    <div id="HOST_HP-UX" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/iLo_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">iLo环境</span><span id="ilototal" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="ilo-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="ilo-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="ilo-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="ilo-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="ilo-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>

            
	<%-- 	<li>
<!-- Content box-05
      ================================================== -->  
    <div id="HOST_LINUX" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/iDrac_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">iDrac环境</span><span id="linuxtotal" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="linux-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="linux-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="linux-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="linux-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="linux-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li> --%>

		<%-- <li>
<!-- Content box-06
      ================================================== -->  
    <div id="HOST_WINDOWS" style="cursor:pointer;" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/iMana_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">iMana环境</span><span id="wintotal" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="win-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="win-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="win-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="win-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="win-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>      
            </li>     --%>
<%-- 		<li>
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
              
            </li> --%>


            
		
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
  <!-- Overlay—detail
      ================================================== -->
      
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
                <div class="left baseBox1"><!-- 第一列--> 
                  
                  <!-- 第一列—box1
                  
    							  ================================================== -->
                  <div class="BaseInfoPerMain">
                    <div class="left"><img src="${ctx}/resources/images/iLo_icon.png" width="32" height="32"></div>
                    <div class="left BasePerBoxilo">
                      <!-- <p class="fB f14px" >ProLiantDL380eGen8</p> -->
                      <!-- <p >IP 192.168.255.255</p> -->
                      <ul>
                        <li><span class="title" >产品名称</span><span class="content" id="pName"></span></li>
                        <li><span class="title" >TPM状态</span><span class="content" id="pStatus"></span></li>
                        <li><span class="title" >产品序列号</span><span class="content" id="pSN"></span></li>
                        <li><span class="title" >System ROM</span><span class="content" id="rom"></span></li>
                        <li><span class="title" >产品cUUID</span><span class="content" id="pUUID"></span></li>
                        <li><span class="title" >ILO 版本号</span><span class="content" id="iloVersion"></span></li>
                      </ul>
                    </div>
                  </div>
                  
                  <!-- 第二列—服务器正面
    							  ================================================== -->
                  <div class="BaseInfoPerMain">
                    <div class="left" style="width:32px; height:32px;"></div>
                    <div class="left BasePerBoxilo">
                      <div class="left BasePerBoxilo-left">
                        <div class="BasePerBoxilo-left-box"></div>
                        <div style=" margin-top:20px; margin-left:10px;"><img src="${ctx}/resources/images/VGA.png" width="20" height="40"></div>
                      </div>
                      <div class="left BasePerBoxilo-center">
                        <div class="left W75 margin10">
                          <div>
                            <div class="left marginR15 w100"><b class="Pos-R Icon_BIOS Icon16 marginT8"></b><span class=" marginL8 LineH20 fB">BIOS</span></div>
                            <div class="left W100">
                              <ul>
                                <li><span class="title">BIOS系列</span><span class="content" id="bios1"></span></li>
                                <li><span class="title">更新时间</span><span class="content" id="lastdate"></span></li>
                              </ul>
                            </div>
                            <div class="clear"></div>
                          </div>
                          <div class="">
                            <div class="left marginR15 w100"><b class="Pos-R Icon_Remote Icon16 marginT8"></b><span class=" marginL8 LineH30 fB">Remote console</span></div>
                            <div class="left W100">
                              <div class="left marginR8 marginT2">用户名</div>
                              <div class="left marginR10">
                                <input disabled name="" type="text" style="width:106px;">
                              </div>
                              <div class="left marginR8 marginT2">密码</div>
                              <div class="left marginR10">
                                <input disabled name="" type="text" style="width:106px;">
                              </div>
                              <div  class="left marginT2"><a  href="#" class="Avmon-button">登录</a></div>
                            </div>
                            <div class="clear"></div>
                          </div>
                        </div>
                        <div class="left W10 margin10">
                          <div class=""><b class="Pos-R Icon_Power Icon16 marginT8"></b><span class="marginL8 LineH30 fB">电源</span></div>
                          <div class="marginT8"><b id="biosPowerStatus" class="Icon_status_green Icon16 Pos-R marginL8 left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px" id="power_status">正常</span>
                            <div class="clear"></div>
                          </div>
                          <div class="marginT10 marginL25">
                            <div id="jqxSwitchButton" style="cursor:pointer"></div>
                          </div>
                        </div>
                      </div>
                      <div class="left BasePerBoxilo-right">
                        <div class="BasePerBoxilo-right-box"></div>
                        <div style=" margin-top:10px; margin-left:10px;"> <img src="${ctx}/resources/images/USB.png" width="20" height="10">
                          <div style="margin-top:10px;"><span>UID</span><span style="margin-left:2px;"><img id="uidStatus" src="${ctx}/resources/images/status_blue.png" width="16" height="16"></span></div>
                          <div style="margin-top:10px;"><img src="${ctx}/resources/images/Ilo-logo.png" width="22" height="60"></div>
                        </div>
                      </div>
                    </div>
                  </div>
                  
                  <!-- 第三列—服务器背面
    							  ================================================== -->
                  <div class="BaseInfoPerMain">
                    <div class="left" style="width:32px; height:32px;"></div>
                    <div class="left BasePerBoxilo">
                      <div class="left BasePerBoxilo-left-back">
                        <div class="BasePerBoxilo-left-box-back"></div>
                        <div class="BasePerBoxilo-left-box-back" style="bottom:4px; position:absolute"></div>
                      </div>
                      <div class="left BasePerBoxilo-center-back">
                        <div class="left marginR6">
                          <div class="margin10"><b class="Pos-R Icon_PC Icon16 marginT8"></b><span class="marginL8 fB">机箱</span></div>
                          <div id="gauge1" class="left"></div>
                          <div class="left margin10">
                            <div id="caseTemp" class="Number-Main"></div>
                            <div class="Extra-Main">℃</div>
                            <div class="Number-title">当前温度</div>
                            <div class="marginT10"><b id="tempalarm" class="Icon_status_red Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">过热</span></div>
                          </div>
                        </div>
                        <div class="left marginR15">
                          <div class="margin10"><b class="Pos-R Icon_Power Icon16 marginT8"></b><span class="marginL8 fB">VRM整体状态</span></div>
                          <div class="left margin10">
                            <div class=" marginL25"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div>
                          </div>
                        </div>
                         <div class="left marginR15">
                          <div class="margin10"><b class="Pos-R Icon_Fan Icon16 marginT8"></b><span class="marginL8 fB">风扇整体状态</span></div>
                          <div class="left margin10">
                            <div class=" marginL25"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div>
                          </div>
                        </div>
                         <div class="left marginR15">
                          <div class="margin10"><b class="Pos-R Icon_Temperature Icon16 marginT8"></b><span class="marginL8 fB">温度整体状态</span></div>
                          <div class="left margin10">
                            <div class=" marginL25"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div>
                          </div>
                        </div>
                      </div>
                      <div class="left BasePerBoxilo-right-back">
                        <div class="BasePerBoxilo-right-box-back"></div>
                        <div class="BasePerBoxilo-left-box-back" style="bottom:4px; position:absolute"></div>
                      </div>
                    </div>
                  </div>
                </div>
                <div class="left baseBox" style="width:27%;"><!-- 第五列-->
                  
                  <div id="organic-tabs" class="BaseInfoPerMain">
                    <ul id="explore-nav-Ilo" class="left BaseInfoPerBoxT">
                      <li id="ex-titlesCpu"><a rel="titlesCpu" href="#" class="current"><span>CPU</span></a></li>
                      <li id="ex-titleStorage"><a rel="titleStorage" href="#"><span>Storage</span></a></li>
                      <li id="ex-titleMotherboard"><a rel="titleMotherboard" href="#"><span>Mainboard</span></a></li>
                      <li id="ex-titleMemory"><a rel="titleMemory" href="#"><span>Memory</span></a></li>
                      <li id="ex-titleFans"><a rel="titleFans" href="#"><span>Fans</span></a></li>
                      <li id="ex-titleNetwork"><a rel="titleNetwork" href="#"><span>Network</span></a></li>
                      <li id="ex-titlePower"><a rel="titlePower" href="#"><span>Power</span></a></li>
                    </ul>
                    <div id="all-list-wrap">
                      <ul id="titlesCpu">
                        <!-- <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">1</span><span>个CPU</span></p>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_CPU Icon16"></b></div>
                          <div class="left"><span class="fB">CPU1</span>
                            <p class="gray"><span class="marginR2">频率</span><span class="marginR8">2533MHz</span><span  class="marginR8">64bit</span><span>4线程</span></p>
                            <div>
                              <div id="gauge3" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main">99.1</div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                                <div class="marginT10 left"><b class="Icon_status_red Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">过热</span></div>
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>
                          <li>
                          <div class="left marginR2"><b class="Pos-R Icon_CPU Icon16"></b></div>
                          <div class="left"><span class="fB">CPU1</span>
                            <p class="gray"><span class="marginR2">频率</span><span class="marginR8">2533MHz</span><span  class="marginR8">64bit</span><span>4线程</span></p>
                            <div>
                              <div id="gauge3" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main">99.1</div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                                <div class="marginT10 left"><b class="Icon_status_red Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">过热</span></div>
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li> -->
                      </ul>
                      <ul id="titleStorage">
                        <!--  <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">1</span><span>个Storage</span></p>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Storage Icon16"></b></div>
                          <div class="left"> <span class="fB">Storage</span>
                            <div>
                              <div id="gauge4" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main">49.1</div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                                <div class="marginT10 left"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div>
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>  -->
                      </ul>
                      <ul id="titleMotherboard">
                       <!--  <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">5</span><span>个Motherboard</span></p>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Motherboard Icon16"></b></div>
                          <div class="left"> <span class="fB">Motherboard1</span>
                            <p class="blank10"></p>
                            <p><span class="marginR10">插槽类型</span><span class="darkblue">other</span></p>
                            <p><span class="marginR10">插槽宽度</span><span class="darkblue">8X</span></p>
                          </div>
                          <div class="blank0"></div>
                        </li> -->

                      </ul>
                      <ul id="titleMemory">
                        <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">1</span><span>个Memory</span></p>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_MeM Icon16"></b></div>
                          <div class="left"> <span class="fB" id="label">PROC_12567_DRMM3</span>
                            <p class="gray"><span class="marginR2">大小</span><span class="marginR8">2048MB</span><span  class="marginR8">速度</span><span>1333MHz</span></p>
                          </div>
                          <div class="blank0"></div>
                        </li>
                      </ul>
                      <ul id="titleFans">
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Fan Icon16"></b></div>
                          <div class="left"><span class="fB">Fan</span>
                            <div>
                              <div class="left marginT10 marginR20">
									<p><span class="marginR8 f14px">转速</span><span class="darkblue f14px">60%</span></p>
                                    <p><span class="marginR8 f14px">位置</span><span class="darkblue f14px">系统</span></p>
                              </div>
                              
                              <div class="left marginT10 ">
                                  <b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:60px">工作正常</span>
                                  <div class="blank10"></div>
                                  <div id="jqxSwitchButton1" class="marginL25" style="cursor:pointer"></div>
                              
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>
                      </ul>
                      <ul id="titleNetwork">
                        <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">5</span><span>个Network</span></p>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Motherboard Icon16"></b></div>
                          <div class="left"> <span class="fB">Network</span>
                            <p class="blank10"></p>
                            <p><span class="marginR10">网卡类型</span><span class="darkblue">Ethernet</span></p>
                            <p><span class="marginR10">MAC</span><span class="darkblue">d4-85-64-55-bd-c0</span></p>
                          </div>
                          <div class="blank0"></div>
                        </li>
                        
                       
                        
                       
                      </ul>
                      
                      <ul id="titlePower">
                        <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">1</span><span>个Power</span></p>
                        <li>
                          <div class="left marginR2"><b class="Pos-R Icon_Power Icon16"></b></div>
                          <div class="left"> <span class="fB">Power</span>
                            <div>
                              <div id="gauge6" class="left marginT10"></div>
                              <div class="left margin10">
                                <div class="Number-Main">49.1</div>
                                <div class="Extra-Main">℃</div>
                                <div class="Number-title">当前温度</div>
                                <div class="marginT10 left"><b class="Icon_status_red Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">过热</span></div>
                              </div>
                              
                               <div class="left marginT10 marginL10 ">
                                  <b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:60px">工作正常</span>
                                  <div class="blank10"></div>
                                  <div id="jqxSwitchButton2" class="marginL25" style="cursor:pointer"></div>
                              
                              </div>
                            </div>
                          </div>
                          <div class="blank0"></div>
                        </li>
                      </ul>
                      
                    </div>
                    <!-- END List Wrap --> 
		 
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
                             <div id="jqxgrid5">Loading</div>
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
                                  <div id="chart1" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:20px 0px;display:none"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart2" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:none"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart3" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:none"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart4" style="border:1px solid #f4f4f4; width:100%; height:55%; margin:4px 0px;display:none"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart5" style="border:1px solid #f4f4f4; width:100%; height:55%; margin:4px 0px;display:none"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
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
            <script type="text/javascript" src="${ctx}/resources/js/jquery.circliful.min.js"></script>


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
        	var ilototal=0;
        	
        	var hostip="";

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
       	               "Original Date",
                          "By Hour",
                          "By Day",
      		        ];
            //end
            
        	document.onreadystatechange = doCompleteLoad;//当页面加载状态改变的时候执行这个方法.
        	function doCompleteLoad()
        	{
        	setTimeout(function(){
        		regClick();
 			},2000);
            function regClick(){
            	 if(document.readyState == "complete"){ //当页面加载状态为完全结束时进入
            		 //双击显示dashboard的代码
                     $("#overviewtable").find("td").each(function(){ 
                 	       	if($(this).attr("class")!=undefined){
                 	       		$("#"+$(this).attr("id")).click(function () {
                       		 moId=$(this).attr("moId");
                       		 $("#hostip").text(hostip);
                       		 loadNestGrid(moId);
                    		 //*
                    		 $.getJSON("${ctx}/pages/dashboard/iloHost/getIloHostBasicInfo?moId="+moId,
                  					function(data) {
                    			$("#pName").text(data.basicData[0].VAL);
                    			$("#pSN").text(data.basicData[1].VAL);
                    			$("#pUUID").text(data.basicData[2].VAL);
                    			$("#pStatus").text(data.basicData[3].VAL);
                    			$("#rom").text(data.basicData[4].VAL);
                    			$("#iloVersion").text(data.basicData[5].VAL);
                    	     });
                    		 $.getJSON("${ctx}/pages/dashboard/iloHost/getBiosList?moId="+moId,
                   					function(data) {
                    			 $("#bios1").text(data[0].value);
                     			 $("#lastdate").text(data[1].value);
                     			 if(data[2].value!="NA"){
                     				 $("#biosPowerStatus").attr('class','Icon_status_red Icon16 Pos-R marginL8 left');
                     			 }
                     			if(data[3].value!="NA"){
                    				 $("#biosPowerStatus").attr('class','Icon_status_red Icon16 Pos-R marginL8 left');
                    			 }
                     			 //$("#power_status").text(data[2].value);
                     			  //$("#uidStatus").attr(data[2].value);
                    			 
                     	     });
                    		 //机箱
                    		 $.getJSON("${ctx}/pages/dashboard/iloHost/getCaseList?moId="+moId,
                    					function(data) {
                    			   $('#gauge1').jqxLinearGauge({
                    	                orientation: 'vertical',
                    	                labels:{ position: 'far', interval: 50, offset: 0, visible: true },
                    	                ticksMajor:{visible: false},
                    	                ticksMinor:{visible: false},
                    	                max: 100,
                    					min:0,
                    					width: 70,
                    	                height: 85,
                    					animationDuration: 300,
                    					background: {visible: false} ,
                    	                pointer: { size: '10%' },
                    	                colorScheme: 'scheme02'
                    	            });
                    	            $('#gauge1').jqxLinearGauge('value',30);
                     			 
                      	     });
                    		//右边栏CPU
                    		 $.getJSON("${ctx}/pages/dashboard/iloHost/getIloHostCpuInfo?moId="+moId,
                 					function(data) {
                    			 $("#titlesCpu").empty();
                    			  var $htmlp;
                    			  var $htmlli;
                    			  if(data.length==0){
                    				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个CPU</span></p>');  
                              		$("#titlesCpu").append($htmlp);
                              		return;
                    			  }else{
                    				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个CPU</span></p>');  
                    				  //for();
                    				  $htmlli=$( '<li><div class="left marginR2"><b class="Pos-R Icon_CPU Icon16"></b></div><div class="left"><span class="fB">CPU1</span><p class="gray"><span class="marginR2">频率</span><span class="marginR8">2533MHz</span><span  class="marginR8">64bit</span><span>4线程</span></p><div><div id="gauge3" class="left marginT10"></div> <div class="left margin10"><div class="Number-Main">99.1</div><div class="Extra-Main">℃</div><div class="Number-title">当前温度</div><div class="marginT10 left"><b class="Icon_status_red Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">过热</span></div></div></div> </div><div class="blank0"></div></li>' );
                    				  $("#titlesCpu").append($htmlli);
                    			  }	
                    			  $('#gauge3').jqxLinearGauge({
                    	                orientation: 'vertical',
                    	                labels:{ position: 'far', interval: 50, offset: 0, visible: false },
                    	                ticksMajor:{visible: false},
                    	                ticksMinor:{visible: false},
                    	                max: 100,
                    					min:0,
                    					width: 70,
                    	                height: 85,
                    					animationDuration: 3000,
                    					background: {visible: false} ,
                    	                pointer: { size: '10%' },
                    	                colorScheme: 'scheme02'
                    	            });
                    	            $('#gauge3').jqxLinearGauge('value',50);
                   	         });
                    		//右边栏POWER
                    		 $.getJSON("${ctx}/pages/dashboard/iloHost/getPowerList?moId="+moId,
                  					function(data) {
                    			 $('#gauge6').jqxLinearGauge({
                    	                orientation: 'vertical',
                    	                labels:{ position: 'far', interval: 50, offset: 0, visible: false },
                    	                ticksMajor:{visible: false},
                    	                ticksMinor:{visible: false},
                    	                max: 100,
                    					min:0,
                    					width: 70,
                    	                height: 85,
                    					animationDuration: 300,
                    					background: {visible: false} ,
                    	                pointer: { size: '10%' },
                    	                colorScheme: 'scheme02'
                    	            });
                    	            $('#gauge6').jqxLinearGauge('value',80);
                    	         });
                    		 //内存
                    		 $.getJSON("${ctx}/pages/dashboard/iloHost/getMemList?moId="+moId,
                   					function(data) {
                    		  $("#titleMemory").empty();
                   			  var $htmlp;
                   			  var $htmlli;
                   			  if(data.length==0){
                   				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个Memory</span></p>');  
                             		$("#titleMemory").append($htmlp);
                             		return;
                   			  }else{
                   				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span>个CPU</span></p>');  
                   				  $("#titleMemory").append($htmlp);
                   				  for(var j=0;j<data.length;j++){
                   				  $htmlli=$( '<li><div class="left marginR2"><b class="Pos-R Icon_MeM Icon16"></b></div><div class="left"> '+
                   				  '<span class="fB" id="label">'+data[j].label+'</span> <p class="gray"><span class="marginR2">大小</span><span class="marginR8">'+data[j].size+'</span><span  class="marginR8">速度</span><span>'
                   				  +data[j].speed+'</span></p></div><div class="blank0"></div></li>');
                   				 
                   				  $("#titleMemory").append($htmlli);
                   				 }
                   			  }	
                     	         });
                    		 /* $.getJSON("${ctx}/pages/dashboard/iloHost/getDiskList?moId="+moId,
                    					function(data) {
                     			 
                      	         }); */
                      		 $.getJSON("${ctx}/pages/dashboard/iloHost/getBoardList?moId="+moId,
                        					function(data) {
                      			 $("#titleMotherboard").empty();
                     			  var $htmlp;
                     			  var $htmlli;
                     			  if(data.length==0){
                     				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个MainBoard</span></p>');  
                               		 $("#titleMotherboard").append($htmlp);
                               		return;
                     			  }else{
                     				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span>个MainBoard</span></p>');  
                     				  $("#titleMotherboard").append($htmlp);
                     				  for(var j=0;j<data.length;j++){
                     				  $htmlli=$('<li><div class="left marginR2"><b class="Pos-R Icon_Motherboard Icon16"></b></div><div class="left"> <span class="fB">Mainboard</span>'+
                     				  ' <p class="blank10"></p><p><span class="marginR10">插槽类型</span><span class="darkblue">'+data[j].type+'</span></p>'
                     				  +'<p><span class="marginR10">插槽宽度</span><span class="darkblue">'+data[j].width+'</span></p></div><div class="blank0"></div></li> ');
                     				  $("#titleMotherboard").append($htmlli);
                     				 }
                     			  }	

                          	   });

                    		 $.getJSON("${ctx}/pages/dashboard/iloHost/getStorageList?moId="+moId,
                 					function(data) {
                    			 $("#titleStorage").empty();
                      			  var $htmlp;
                      			  var $htmlli;
                      			  if(data.length==0){
                      				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个Storage</span></p>');  
                                		$("#titleStorage").append($htmlp);
                                		return;
                      			  }else{
                      				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span>个Storage</span></p>');  
                      				  $("#titleStorage").append($htmlp);
                      				  for(var j=0;j<data.length;j++){
                      				  $htmlli=$(' <li><div class="left marginR2"><b class="Pos-R Icon_Storage Icon16"></b></div><div class="left"> <span class="fB">'+data[j].storage+'</span>'+
                      				  '<div><div id="gauge4" class="left marginT10"></div><div class="left margin10"><div class="Number-Main">'+data[j].storageCrrntTemp+'</div><div class="Number-title">当前温度</div>'
                      				  +'<div class="marginT10 left"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div></div> </div></div><div class="blank0"></div></li>');
                      				  $("#titleStorage").append($htmlli);
                      				 }
                      			  }	
                      			 $('#gauge4').jqxLinearGauge({
                                     orientation: 'vertical',
                                     labels:{ position: 'far', interval: 50, offset: 10, visible: false },
                                     ticksMajor:{visible: false},
                                     ticksMinor:{visible: false},
                                     max: 100,
                     				min:0,
                     				width: 70,
                                     height: 85,
                     				animationDuration: 300,
                     				background: {visible: false} ,
                                     pointer: { size: '10%' },
                                     colorScheme: 'scheme02'
                                 });
                                 $('#gauge4').jqxLinearGauge('value',100);
                   	              });
                    		
                            
                                $.getJSON("${ctx}/pages/dashboard/iloHost/getNetcardList?moId="+moId,
                 					function(data) {
                    			 $("#titleNetwork").empty();
                      			  var $htmlp;
                      			  var $htmlli;
                      			  if(data.length==0){
                      				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个Network</span></p>');  
                                		$("#titleNetwork").append($htmlp);
                                		return;
                      			  }else{
                      				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span>个Network</span></p>');  
                      				  $("#titleNetwork").append($htmlp);
                      				  for(var j=0;j<data.length;j++){
                      				  $htmlli=$( '<li><div class="left marginR2"><b class="Pos-R Icon_Motherboard Icon16"></b></div> '+
                      				  ' <div class="left"> <span class="fB">'+data[j].nic+'</span><p class="blank10"></p>'+
                      				  '<p><span class="marginR10">网卡类型</span><span class="darkblue">'+data[j].type+'</span></p>'+
                      				  +'<p><span class="marginR10">MAC</span><span class="darkblue">'+data[j].mac+'</span></p></div><div class="blank0"></div>'); 				 
                      				  $("#titleNetwork").append($htmlli);
                      				 }
                      			  }	
                  			 
                   	         });
                    		 $.getJSON("${ctx}/pages/dashboard/iloHost/getFanList?moId="+moId,
                  					function(data) {
                    			 $("#titleFans").empty();
                    			 
                     			  var $htmlli;
                     				  for(var j=0;j<data.length;j++){
                     				 $htmlli=$('<li><div class="left marginR2"><b class="Pos-R Icon_Fan Icon16"></b></div><div class="left"><span class="fB">'+data[j].name+'</span>'+
                     						 '<div><div class="left marginT10 marginR20"><p><span class="marginR8 f14px">转速</span><span class="darkblue f14px">'+data[j].speed+'</span></p><p><span class="marginR8 f14px">位置</span><span class="darkblue f14px">系统</span></p> </div>'+
                     						 '<div class="left marginT10 "><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:60px">工作正常</span>'+
                     						 ' <div class="blank10"></div><div id="jqxSwitchButton1" class="marginL25" style="cursor:pointer"></div>'+
                     						 ' </div></div> </div><div class="blank0"></div></li>');
                     				  $("#titleFans").append($htmlli);	 
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
               				url : '${ctx}/config/kpiList?moId='+moId
                               
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
        		var textRotationAngle=0;
        		var rowindexes = $('#kpigrid').jqxGrid('getselectedrowindexes');
        		if(rowindexes.length==0){
       			 $("#newWindow").remove();
	        		   $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>请选择一条KPI！ </div></div>');
	                   $('#newWindow').jqxWindow({isModal : true, height: 80, width: 150});
	                   return;
       		  }
       		  if(rowindexes.length>5){
			        $("#newWindow").remove();
       		   $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>KPI最多只能选择5条记录！ </div></div>');
                  $('#newWindow').jqxWindow({ isModal : true,height: 80, width: 150});
                  return;
       		   }
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
    	        		
    	        		
    	        		
    	        		var grainsize = $("#jqxWidgetData1").jqxDropDownList('getSelectedIndex')+1;
    	        		var startTime = $('#startDate').jqxDateTimeInput('getText');
    	        		var endTime = $('#toDate').jqxDateTimeInput('getText');
    	        		var startHour =$("#startHour").jqxDropDownList('getSelectedItem');
    	        	    var endHour =$("#endHour").jqxDropDownList('getSelectedItem');
    	        	    startTime=startTime+" "+startHour.label;
    	        	    endTime=endTime+" "+endHour.label;
    	        		for(var i=0;i<rowindexes.length;i++){
    	        			var row = $("#kpigrid").jqxGrid('getrowdata',rowindexes[i]);
    	        			var yAxis=row.displayKpi;
    	        			var insAndcode=row.kpi.split("/");
    	        			var ampInstId=insAndcode[0];
    	        			var kpiCode=insAndcode[1];
    	        			
    	        			var baseUnit='';
    	        			$("#chart"+(i+1)).show();
    	        			if(grainsize==3){
    	        				baseUnit='day';
    	        				textRotationAngle=0;
    	        			}
    	        			else{
    	        				baseUnit='hour';
    	        				textRotationAngle=-75;
    	        			}
    	        			loadTrendChart(ampInstId,kpiCode,startTime,endTime,moId,grainsize,"chart"+(i+1),yAxis,baseUnit);
    	        			$.unblockUI();
    	        		}
    	            } 
        		}); 
   		       
        		function loadTrendChart(ampInstId,kpiCode,startTime,endTime,moId,grainsize,id,yAxis,baseUnit){
                    var trendsource =
                    {
                        datatype: "json",
                        datafields: [
                            { name: 'time' },
                            { name: 'instance' }
                        ],
                        url: '${ctx}/config/dynamicKpiChartData?ampInstId='+ampInstId+'&kpiCode='+kpiCode+'&startTime='+startTime+'&endTime='+endTime+'&moId='+moId+'&grainsize='+grainsize+"'"
                    };

                    var trenddataAdapter = new $.jqx.dataAdapter(trendsource, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
                    var months = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];

                    // prepare jqxChart settings
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
                                    //return  value.getFullYear() + '-' + months[value.getMonth()] + '-' + value.getDate();
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
                               /*  minValue: '01/01/2012',
                                maxValue: '01/31/2012', */
                                valuesOnTicks: true
                            },
                        colorScheme: 'scheme01',
                        seriesGroups:
                            [
                                {
                                    type: 'stackedline',
                                    valueAxis:
                                    {
                                        //unitInterval: 500,
                                      /*   minValue: 0,
                                        maxValue: 4500, */
                                        minValue: 0,
                                        description: yAxis,
                                        tickMarksColor: '#888888'
                                    },
                                    series: [
                                            { dataField: 'instance', displayText: 'kpi trend' }
                                        ]
                                }
                            ]
                    };
                    // setup the chart
                    $('#'+id).jqxChart(settings);
        		    }
        		
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
								$("#batchupdate").click(function() {
									                /*  alert(isClickSearch);
									                alert(alarmIdList.length);  */
									                var rowindexes = $('#alarmGrid').jqxGrid('getselectedrowindexes');
									                if(isClickSearch==false&&((rowindexes.length==0||rowindexes[0]==-1))){
									                	
									              			 $("#newWindow").remove();
									       	        		   $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>请选择一条记录！ </div></div>');
									       	                   $('#newWindow').jqxWindow({ isModal : true,height: 80, width: 150});
									       	                   return;
									              		  
									                	return;
									                }
									                
									                if(isClickSearch==false&&(rowindexes.length>0)){
									                	for (var i = 0; i < rowindexes.length; i++) {
															var data = $('#alarmGrid').jqxGrid('getrowdata',rowindexes[i]);
															alarmIdList.push(data.id);
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
									                if(isClickSearch==true&&(alarmIdList.length>0)){
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
																				$('#renling').jqxWindow('focus');
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
									var rowindex = $("#alarmGrid").jqxGrid('getselectedrowindex');
									var row = $("#alarmGrid").jqxGrid('getrowdata',rowindex);
									row.attr_actorUser = '${userId}';
									row.attr_result = $("#detailcontent").val();
									row.status=1;
									$('#alarmGrid').jqxGrid('updaterow',rowindex,row);
									$("#renling").jqxWindow('hide');
								 } else {
								  
							        var alarmIds="";
							        alarmIds=alarmIdList.join(",");
										     $.post('${ctx}/pages/alarm/batchClose',
										      {
										    	  alarmIdList:alarmIds
										      },
										      function (data) 
										      {
										    	  $("#renling").jqxWindow('hide');
												  $('#alarmGrid').jqxGrid('updatebounddata');
												  
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
			var isClickSearch=false;
			
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
        	       $("#jqxgrid5").jqxGrid(
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
        		 hostip=rowdata.hostName;
        		 $("#hostip").text(rowdata.hostName);
        		 loadNestGrid(moId);
        		 //*
        		 $.getJSON("${ctx}/pages/dashboard/iloHost/getIloHostBasicInfo?moId="+moId,
      					function(data) {
        			$("#pName").text(data.basicData[0].VAL);
        			$("#pSN").text(data.basicData[1].VAL);
        			$("#pUUID").text(data.basicData[2].VAL);
        			$("#pStatus").text(data.basicData[3].VAL);
        			$("#rom").text(data.basicData[4].VAL);
        			$("#iloVersion").text(data.basicData[5].VAL);
        	     });
        		 $.getJSON("${ctx}/pages/dashboard/iloHost/getBiosList?moId="+moId,
       					function(data) {
        			 $("#bios1").text(data[0].value);
         			 $("#lastdate").text(data[1].value);
         			 if(data[2].value!="NA"){
         				 $("#biosPowerStatus").attr('class','Icon_status_red Icon16 Pos-R marginL8 left');
         			 }
         			if(data[3].value!="NA"){
        				 $("#biosPowerStatus").attr('class','Icon_status_red Icon16 Pos-R marginL8 left');
        			 }
         			 //$("#power_status").text(data[2].value);
         			 //$("#uidStatus").attr(data[2].value);
         	     });
        		 //机箱
        		 $.getJSON("${ctx}/pages/dashboard/iloHost/getCaseList?moId="+moId,
        					function(data) {
        			   $('#gauge1').jqxLinearGauge({
        	                orientation: 'vertical',
        	                labels:{ position: 'far', interval: 50, offset: 0, visible: true },
        	                ticksMajor:{visible: false},
        	                ticksMinor:{visible: false},
        	                max: 100,
        					min:0,
        					width: 70,
        	                height: 85,
        					animationDuration: 300,
        					background: {visible: false} ,
        	                pointer: { size: '10%' },
        	                colorScheme: 'scheme02'
        	            });
        	            $('#gauge1').jqxLinearGauge('value',30);
         			 
          	     });
        		//右边栏CPU
        		 $.getJSON("${ctx}/pages/dashboard/iloHost/getIloHostCpuInfo?moId="+moId,
     					function(data) {
        			 $("#titlesCpu").empty();
        			  var $htmlp;
        			  var $htmlli;
        			  if(data.length==0){
        				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个CPU</span></p>');  
                  		$("#titlesCpu").append($htmlp);
                  		return;
        			  }else{
        				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个CPU</span></p>');  
        				  //for();
        				  $htmlli=$( '<li><div class="left marginR2"><b class="Pos-R Icon_CPU Icon16"></b></div><div class="left"><span class="fB">CPU1</span><p class="gray"><span class="marginR2">频率</span><span class="marginR8">2533MHz</span><span  class="marginR8">64bit</span><span>4线程</span></p><div><div id="gauge3" class="left marginT10"></div> <div class="left margin10"><div class="Number-Main">99.1</div><div class="Extra-Main">℃</div><div class="Number-title">当前温度</div><div class="marginT10 left"><b class="Icon_status_red Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">过热</span></div></div></div> </div><div class="blank0"></div></li>' );
        				  $("#titlesCpu").append($htmlli);
        			  }	
        			  $('#gauge3').jqxLinearGauge({
        	                orientation: 'vertical',
        	                labels:{ position: 'far', interval: 50, offset: 0, visible: false },
        	                ticksMajor:{visible: false},
        	                ticksMinor:{visible: false},
        	                max: 100,
        					min:0,
        					width: 70,
        	                height: 85,
        					animationDuration: 3000,
        					background: {visible: false} ,
        	                pointer: { size: '10%' },
        	                colorScheme: 'scheme02'
        	            });
        	            $('#gauge3').jqxLinearGauge('value',50);
       	         });
        		//右边栏POWER
        		 $.getJSON("${ctx}/pages/dashboard/iloHost/getPowerList?moId="+moId,
      					function(data) {
        			 $('#gauge6').jqxLinearGauge({
        	                orientation: 'vertical',
        	                labels:{ position: 'far', interval: 50, offset: 0, visible: false },
        	                ticksMajor:{visible: false},
        	                ticksMinor:{visible: false},
        	                max: 100,
        					min:0,
        					width: 70,
        	                height: 85,
        					animationDuration: 300,
        					background: {visible: false} ,
        	                pointer: { size: '10%' },
        	                colorScheme: 'scheme02'
        	            });
        	            $('#gauge6').jqxLinearGauge('value',80);
        	         });
        		 //内存
        		 $.getJSON("${ctx}/pages/dashboard/iloHost/getMemList?moId="+moId,
       					function(data) {
        		  $("#titleMemory").empty();
       			  var $htmlp;
       			  var $htmlli;
       			  if(data.length==0){
       				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个Memory</span></p>');  
                 		$("#titleMemory").append($htmlp);
                 		return;
       			  }else{
       				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span>个CPU</span></p>');  
       				  $("#titleMemory").append($htmlp);
       				  for(var j=0;j<data.length;j++){
       				  $htmlli=$( '<li><div class="left marginR2"><b class="Pos-R Icon_MeM Icon16"></b></div><div class="left"> '+
       				  '<span class="fB" id="label">'+data[j].label+'</span> <p class="gray"><span class="marginR2">大小</span><span class="marginR8">'+data[j].size+'</span><span  class="marginR8">速度</span><span>'
       				  +data[j].speed+'</span></p></div><div class="blank0"></div></li>');
       				 
       				  $("#titleMemory").append($htmlli);
       				 }
       			  }	
         	         });
        		 /* $.getJSON("${ctx}/pages/dashboard/iloHost/getDiskList?moId="+moId,
        					function(data) {
         			 
          	         }); */
          		 $.getJSON("${ctx}/pages/dashboard/iloHost/getBoardList?moId="+moId,
            					function(data) {
          			 $("#titleMotherboard").empty();
         			  var $htmlp;
         			  var $htmlli;
         			  if(data.length==0){
         				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个MainBoard</span></p>');  
                   		 $("#titleMotherboard").append($htmlp);
                   		return;
         			  }else{
         				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span>个MainBoard</span></p>');  
         				  $("#titleMotherboard").append($htmlp);
         				  for(var j=0;j<data.length;j++){
         				  $htmlli=$('<li><div class="left marginR2"><b class="Pos-R Icon_Motherboard Icon16"></b></div><div class="left"> <span class="fB">Mainboard</span>'+
         				  ' <p class="blank10"></p><p><span class="marginR10">插槽类型</span><span class="darkblue">'+data[j].type+'</span></p>'
         				  +'<p><span class="marginR10">插槽宽度</span><span class="darkblue">'+data[j].width+'</span></p></div><div class="blank0"></div></li> ');
         				  $("#titleMotherboard").append($htmlli);
         				 }
         			  }	

              	   });

        		 $.getJSON("${ctx}/pages/dashboard/iloHost/getStorageList?moId="+moId,
     					function(data) {
        			 $("#titleStorage").empty();
          			  var $htmlp;
          			  var $htmlli;
          			  if(data.length==0){
          				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个Storage</span></p>');  
                    		$("#titleStorage").append($htmlp);
                    		return;
          			  }else{
          				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span>个Storage</span></p>');  
          				  $("#titleStorage").append($htmlp);
          				  for(var j=0;j<data.length;j++){
          				  $htmlli=$(' <li><div class="left marginR2"><b class="Pos-R Icon_Storage Icon16"></b></div><div class="left"> <span class="fB">'+data[j].storage+'</span>'+
          				  '<div><div id="gauge4" class="left marginT10"></div><div class="left margin10"><div class="Number-Main">'+data[j].storageCrrntTemp+'</div><div class="Number-title">当前温度</div>'
          				  +'<div class="marginT10 left"><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:40px">正常</span></div></div> </div></div><div class="blank0"></div></li>');
          				  $("#titleStorage").append($htmlli);
          				 }
          			  }	
          			 $('#gauge4').jqxLinearGauge({
                         orientation: 'vertical',
                         labels:{ position: 'far', interval: 50, offset: 10, visible: false },
                         ticksMajor:{visible: false},
                         ticksMinor:{visible: false},
                         max: 100,
         				min:0,
         				width: 70,
                         height: 85,
         				animationDuration: 300,
         				background: {visible: false} ,
                         pointer: { size: '10%' },
                         colorScheme: 'scheme02'
                     });
                     $('#gauge4').jqxLinearGauge('value',100);
       	              });
        		
                
                    $.getJSON("${ctx}/pages/dashboard/iloHost/getNetcardList?moId="+moId,
     					function(data) {
        			 $("#titleNetwork").empty();
          			  var $htmlp;
          			  var $htmlli;
          			  if(data.length==0){
          				 $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">0</span><span>个Network</span></p>');  
                    		$("#titleNetwork").append($htmlp);
                    		return;
          			  }else{
          				  $htmlp=  $('<p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4">'+data.length+'</span><span>个Network</span></p>');  
          				  $("#titleNetwork").append($htmlp);
          				  for(var j=0;j<data.length;j++){
          				  $htmlli=$( '<li><div class="left marginR2"><b class="Pos-R Icon_Motherboard Icon16"></b></div> '+
          				  ' <div class="left"> <span class="fB">'+data[j].nic+'</span><p class="blank10"></p>'+
          				  '<p><span class="marginR10">网卡类型</span><span class="darkblue">'+data[j].type+'</span></p>'+
          				  +'<p><span class="marginR10">MAC</span><span class="darkblue">'+data[j].mac+'</span></p></div><div class="blank0"></div>'); 				 
          				  $("#titleNetwork").append($htmlli);
          				 }
          			  }	
      			 
       	         });
        		 $.getJSON("${ctx}/pages/dashboard/iloHost/getFanList?moId="+moId,
      					function(data) {
        			 $("#titleFans").empty();
        			 
         			  var $htmlli;
         				  for(var j=0;j<data.length;j++){
         				 $htmlli=$('<li><div class="left marginR2"><b class="Pos-R Icon_Fan Icon16"></b></div><div class="left"><span class="fB">'+data[j].name+'</span>'+
         						 '<div><div class="left marginT10 marginR20"><p><span class="marginR8 f14px">转速</span><span class="darkblue f14px">'+data[j].speed+'</span></p><p><span class="marginR8 f14px">位置</span><span class="darkblue f14px">系统</span></p> </div>'+
         						 '<div class="left marginT10 "><b class="Icon_status_green Icon16 Pos-R left"></b><span class="title left darkblue marginL10 marginT2" style="width:60px">工作正常</span>'+
         						 ' <div class="blank10"></div><div id="jqxSwitchButton1" class="marginL25" style="cursor:pointer"></div>'+
         						 ' </div></div> </div><div class="blank0"></div></li>');
         				  $("#titleFans").append($htmlli);	 
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
				url : '${ctx}/config/kpiList?moId='+moId
                
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
            		    
            		    hostsource.url = "${ctx}/pages/dashboard/iloHost/getIloPerformanceList?bizName="+encodeURI(encodeURI(item1.label));
            		    loadGrid(hostsource);
            		    //getData(item1);
            		    var url='${ctx}/pages/dashboard/iloHost/getIloOverViewPerformanceList?bizName='+encodeURI(encodeURI(item1.label));
				        loadHostOverView(url);
            		    loadLevelAlarmData(item1.label);
               } 
            });
            
            var index2 = $("#bizList").jqxDropDownList('getSelectedIndex');
            var item2 = $("#bizList").jqxDropDownList('getItem', index2);
            $(".PerformanceNavBox").click(function(){
            	 //alert($(this).attr("id"));
            	 var typeId=$(this).attr("id");
            	 hostsource.url = "${ctx}/pages/dashboard/iloHost/getIloPerformanceList?typeId="+typeId+"&bizName="+encodeURI(encodeURI(item2.label));
     		     loadGrid(hostsource);
             });
            function loadLevelAlarmData(bizName){
         	$.getJSON("${ctx}/performance/getAllLevelAlarmDataByType?typeId=HARDWARE&bizName="+encodeURI(encodeURI(bizName)),
					function(data) {
         		$("#ilo-level1").text("0");
         		$("#ilo-level2").text("0");
         		$("#ilo-level3").text("0");
         		$("#ilo-level4").text("0");
         		$("#ilo-level5").text("0");
         		/* $("#linux-level1").text("0");
         		$("#linux-level2").text("0");
         		$("#linux-level3").text("0");
         		$("#linux-level4").text("0");
         		$("#linux-level5").text("0"); 

         		$("#win-level1").text("0");
         		$("#win-level2").text("0");
         		$("#win-level3").text("0");
         		$("#win-level4").text("0");
         		$("#win-level5").text("0");
         		wintotal=0;
         		linuxtotal=0;*/
         		ilototal=0;
 
       		  for(var i=0;i<data.length;i++){
       			if(data[i].type=="HARDWARE_ILO"){
       				if(data[i].grade==1){
       					$("#ilo-level1").text(data[i].count);
       					ilototal+=data[i].count;
       				}
       				if(data[i].grade==2){
       					$("#ilo-level2").text(data[i].count);
       					ilototal+=data[i].count;
       				}
       				if(data[i].grade==3){
       					$("#ilo-level3").text(data[i].count);
       					ilototal+=data[i].count;
       				}
       				if(data[i].grade==4){
       					$("#ilo-level4").text(data[i].count);
       					ilototal+=data[i].count;
       				}
       				if(data[i].grade==5){
       					$("#ilo-level5").text(data[i].count);
       					ilototal+=data[i].count;
       				}
       			
       			}
       			/* if(data[i].type=="HOST_LINUX"){
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
       				
       			} */
       			/* if(data[i].type=="HOST_WINDOWS"){
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
       				
       			} */

       		}
       		//$("#wintotal").text(wintotal);
   			//$("#soltotal").text(sunostotal);
   			//$("#linuxtotal").text(linuxtotal);
				$("#ilototal").text(ilototal);
				  
			});
            }
            loadLevelAlarmData(item2.label);
            	var url = "${ctx}/pages/dashboard/iloHost/getIloPerformanceList?bizName="+encodeURI(encodeURI(item2.label));
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
     				},{
     					name : 'hostName',
     					type : 'string'
     				}, {
     					name : 'count',
     					type : 'string'
     				}, {
     					name : 'status',
     					type : 'string'
     				}, {
     					name : 'version',
     					type : 'string'
     				}, {
     					name : 'powerstatus',
     					type : 'string'
     				},{
     					name : 'fanstatus',
     					type : 'string'
     				} ,{
     					name : 'temstatus',
     					type : 'string'
     				} ,{
     					name : 'vrmstatus',
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
				hostsource.url = '${ctx}/pages/dashboard/iloHost/getIloPerformanceList?queryIP='+ ipaddress+"&bizName="+encodeURI(encodeURI(item.label));
				loadGrid(hostsource);
				var url='${ctx}/pages/dashboard/iloHost/getIloOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label))+'&queryIP='+ipaddress;
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
       /*    */ 
        function loadHostOverView(url){
       	if(url==undefined){
       		url='${ctx}/pages/dashboard/iloHost/getIloOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label));
       	}
       	
       	
       	$.getJSON(url,function(data) {
       		 $("#overviewtable").find("td").each(function(){ 
            	if($(this).attr("class")!=undefined){
           		$(this).removeClass();		}
             });  
       		
       		for(var j=0;j<data.length;j++){
       			 x=randx();
       			 y=randy();
       			 count=j;
       			 setTimeout(function(){
       					  createToolTip(data[count],'Tooltip'+(count--));
       			},5000);

       			 if($("#overviewtable tr:eq("+x+") td:eq("+y+")").attr("id")==undefined){
       				 //alert('Tooltip'+j);
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
       		}
       	});
       }
       loadHostOverView();
        
       function createToolTip(data,id){
   	   var content="<b>"+data.hostName+"</b><br/><b>设备状态:</b>"+data.status+"<br /><b>iLo版本号:</b>"
   		+data.version+"<br /><b>电源总体状态:</b>"+data.powerstatus+"<br /><b>风扇整体状态:</b>"+data.fanstatus+"<br /><b>温度整体状态:</b>"+data.temstatus+"<br /><b>vrm整体状态:</b>"+data.vrmstatus;
   		$("#"+id).jqxTooltip({ content: content, 
   			 position: 'mouse', name: 'movieTooltip'});
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
					text : 'IP/设备名',
					datafield : 'hostName',
					width : 180
				},
				{
					text : '类型',
					datafield : 'type',
					hidden:true,
					width : 100
				},{
					text : '告警数',
					datafield : 'count',
					width : 150
				}, {
					text : '设备状态',
					datafield : 'status',
					//cellsrenderer : cpurenderer,
					width : 100
				}, {
					text : 'ILO版本号',
					datafield : 'version',
					//cellsrenderer : memrenderer,
					width : 100
				}, {
					text : '总体电源状态',
					datafield : 'powerstatus',
					//cellsrenderer : swaprenderer,
					minwidth : 100
				}, 
				{
					text : '风扇整体状态',
					datafield : 'fanstatus',
					//cellsrenderer : syslogrenderer,
					minwidth : 80
				},
				{
					text : '温度整体状态',
					datafield : 'temstatus',
					minwidth : 80
				},{
					text : 'vrm整体状态',
					datafield : 'vrmstatus',
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
        $("#explore-nav-Ilo li a").click(function() {
            
            // Figure out current list via CSS class
            var curList = $("#explore-nav-Ilo li a.current").attr("rel");
            
            // List moving to
            var $newList = $(this);
            
            // Set outer wrapper height to height of current inner list
            var curListHeight = $("#all-list-wrap").height();
            $("#all-list-wrap").height(curListHeight);
            
            // Remove highlighting - Add to just-clicked tab
            $("#explore-nav-Ilo li a").removeClass("current");
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
            function hide(){
              document.getElementById("ShowUserBoxMain").style.display = "none";
            }
        </script>