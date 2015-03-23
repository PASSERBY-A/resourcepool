<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<!DOCTYPE html>

<head>
<meta charset="utf-8">
<title>Vcenter视图</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />


<!-- CSS -->
<link rel="stylesheet" type="text/css"  href="${ctx}/resources/css/reset.css">
<link rel="stylesheet" type="text/css"  href="${ctx}/resources/css/global.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/Main.css">
<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico">
<link rel="stylesheet"
	href="${ctx}/resources/js/jqwidgets/styles/jqx.base.css"
	type="text/css" />
	
<!-- jqwidgets -->
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
<%-- 
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxcore.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxwindow.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxdata.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxtree.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxexpander.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxmenu.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxcheckbox.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxlistbox.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxradiobutton.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxdropdownlist.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxpanel.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxtabs.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxgrid.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxgrid.sort.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxgrid.pager.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxgrid.selection.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxgrid.edit.js"></script> --%>
<%-- <script type="text/javascript" src="${ctx}/resources/js/demos.js"></script> --%>
<script type="text/javascript" src="${ctx}/resources/js/jquery.bxslider.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.blockUI.js"></script>
<style type="text/css">
/*update 20140918*/

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

#jqxVerticalProgressBar1 .jqx-fill-state-pressed {
background: #c8d8a5 !important;
} 


#jqxVerticalProgressBar2 .jqx-fill-state-pressed {
background: #7fbb00 !important;
}	

.jqx-tabs-titleContentWrapper{
margin-top: 0px !important;
}
.bx-wrapper .bx-viewport {
    border: 2px solid #fff;
}
/* #vmViewList { */
/*     height:500px !important; */
/* } */
</style>
</head>

<body>

<div id="wrap"> 
  
  <!-- Header
      ================================================== -->
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
	
      <div class="MainTitle f28px LineH12 left">性能中心</div>
      <div class="left Padding10">
        <div id="jqxSystem" class="left" style="background-color: #0096d6; color:#ffffff; border:0px;"></div>
      </div>
      
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
        <div  style="display:none" class="SearchPerformanceIpt"> <b class="ico ico-search"></b>
          <input id="ipaddress" class="SearchPerformanceformIpt SearchPerformanceIpt-focus" tabindex="1" type="text" maxlength="50" value="" autocomplete="on">
          <a id="queryIP" href="#" class="SearchPerformanceIpt-btn">查询</a> </div>
      </div>
    </div>
    
    <!-- showUserBox
      ================================================== -->
    <div id="ShowUserBoxMain" style="display:none">
      <div class="ShowUserBoxArrow"></div>
      <div class="ShowUserBox">
        <ul>
          <li><a href="#"><span class="AccountSettingIcon"></span><span>个人设置</span></a></li>
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
            <!-- Content box-26
      ================================================== -->
            <div id="vcenter" class="PerformanceNavBox" style="cursor:pointer">
              <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/Vcenter_icon.png" width="32" height="32"></div>
              <div class="left PerformanceNavBoxtitle"> <span class="f18px LineH18 inline-block paddingR20">Vcenter</span><span id="vcenterCount" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="vcenter-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="vcenter-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="vcenter-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="vcenter-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="vcenter-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>    
          </li>
          <li> 
            <!-- Content box-27
      ================================================== -->
            <div id="xen" class="PerformanceNavBox" style="cursor:pointer">
              <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/Xen_icon.png" width="32" height="32"></div>
              <div class="left PerformanceNavBoxtitle"> <span class="f18px LineH18 inline-block paddingR20">Xen</span><span id="xenCount" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="xen-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="xen-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="xen-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="xen-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="xen-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
          </li>
          <li> 
            <!-- Content box-28
      ================================================== -->
            <div id="kvm" class="PerformanceNavBox" style="cursor:pointer">
              <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/KVM_icon.png" width="32" height="32"></div>
              <div class="left PerformanceNavBoxtitle"> <span class="f18px LineH18 inline-block paddingR20">KVM</span><span id="kvmCount" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="kvm-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="kvm-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="kvm-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="kvm-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="kvm-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>  
          </li>
		  
		  <li> 
            <!-- Content box-28
      ================================================== -->
            <div id="hyperv" class="PerformanceNavBox" style="cursor:pointer">
              <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/KVM_icon.png" width="32" height="32"></div>
              <div class="left PerformanceNavBoxtitle"> <span class="f18px LineH18 inline-block paddingR20">Hyper-v</span><span id="hypervCount" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="hyperv-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="hyperv-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="hyperv-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="hyperv-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="hyperv-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div> 
          </li>
        </ul>
      </div>
      <div class="blank0"></div>
      
        <div id="vmViewList">
        Loading
		</div>
		
		<div id="vmViewGrid" style="display:none">
		
			<div style="width:100%; height:410px;"> 
		    <table id="overviewtable" width="100%" border="0" cellspacing="0" cellpadding="0" class="Performance_tabl"> 
		     <tbody>
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
		     </tbody>
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
   
   function hide(){

     document.getElementById("ShowUserBoxMain").style.display = "none";
	 
   }
   
   window.onload = function(){
	  document.getElementById("ShowUserBoxMain").style.display = "none";
      document.body.onclick = hide;
	  document.getElementById("ShowUserBoxclick").onclick = show1;
	  
   };
  </script> 

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
</script> 
<script type="text/javascript">
function loadAlarmCount(queryIp){//需要修改
	$("#vcenter-level1").text(0);
	$("#vcenter-level2").text(0);
	$("#vcenter-level3").text(0);
	$("#vcenter-level4").text(0);
	$("#vcenter-level5").text(0);
	
	$("#xen-level1").text(0);
	$("#xen-level2").text(0);
	$("#xen-level3").text(0);
	$("#xen-level4").text(0);
	$("#xen-level5").text(0);
	
	$("#kvm-level1").text(0);
	$("#kvm-level2").text(0);
	$("#kvm-level3").text(0);
	$("#kvm-level4").text(0);
	$("#kvm-level5").text(0);
	
	$("#hyperv-level1").text(0);
	$("#hyperv-level2").text(0);
	$("#hyperv-level3").text(0);
	$("#hyperv-level4").text(0);
	$("#hyperv-level5").text(0);
	
	
	vcentertotal = 0;
	xentotal = 0;
	kvmtotal = 0;
	hypervtotal= 0;
	$("#vcenterCount").text(0);
    $("#xenCount").text(0);
    $("#kvmCount").text(0);
    $("#hypervCount").text(0);
	
    var paramUrl = "${ctx}/vcenter/getVmAlarmData";
    
	$.getJSON(paramUrl,
    function(data) {
        for (var i = 0; i < data.length; i++) {
        	var dataType = data[i].type;
        	var dataGrade = data[i].grade;
        	var dataCount = data[i].count;
            if (dataType == "amp_vcenter") {////////////////////////////////////需要修改datatype
                if (dataGrade == 1) {
                    $("#vcenter-level1").text(dataCount);
                    vcentertotal += dataCount;
                }
                if (dataGrade == 2) {
                    $("#vcenter-level2").text(dataCount);
                    vcentertotal += dataCount;
                }
                if (dataGrade == 3) {
                    $("#vcenter-level3").text(dataCount);
                    vcentertotal += dataCount;
                }
                if (dataGrade == 4) {
                    $("#vcenter-level4").text(dataCount);
                    vcentertotal += dataCount;
                }
                if (dataGrade == 5) {
                    $("#vcenter-level5").text(dataCount);
                    vcentertotal += dataCount;
                }
            }
            if (dataType == "amp_xen") {
                if (dataGrade == 1) {
                    $("#xen-level1").text(dataCount);
                    xentotal += dataCount;
                }
                if (dataGrade == 2) {
                    $("#xen-level2").text(dataCount);
                    xentotal += dataCount;
                }
                if (dataGrade == 3) {
                    $("#xen-level3").text(dataCount);
                    xentotal += dataCount;
                }
                if (dataGrade == 4) {
                    $("#xen-level4").text(dataCount);
                    xentotal += dataCount;
                }
                if (dataGrade == 5) {
                    $("#xen-level5").text(dataCount);
                    xentotal += dataCount;
                }

            }
            if (dataType == "amp_kvm") {
                if (dataGrade == 1) {
                    $("#kvm-level1").text(dataCount);
                    kvmtotal += dataCount;
                }
                if (dataGrade == 2) {
                    $("#kvm-level2").text(dataCount);
                    kvmtotal += dataCount;
                }
                if (dataGrade == 3) {
                    $("#kvm-level3").text(dataCount);
                    kvmtotal += dataCount;
                }
                if (dataGrade == 4) {
                    $("#kvm-level4").text(dataCount);
                    kvmtotal += dataCount;
                }
                if (dataGrade == 5) {
                    $("#kvm-level5").text(dataCount);
                    kvmtotal += dataCount;
                }

            }
            if (dataType == "amp_hyperv") {
                if (dataGrade == 1) {
                    $("#hyperv-level1").text(dataCount);
                    hypervtotal += dataCount;
                }
                if (dataGrade == 2) {
                    $("#hyperv-level2").text(dataCount);
                    hypervtotal += dataCount;
                }
                if (dataGrade == 3) {
                    $("#hyperv-level3").text(dataCount);
                    hypervtotal += dataCount;
                }
                if (dataGrade == 4) {
                    $("#hyperv-level4").text(dataCount);
                    hypervtotal += dataCount;
                }
                if (dataGrade == 5) {
                    $("#hyperv-level5").text(dataCount);
                    hypervtotal += dataCount;
                }

            }
            
            $("#vcenterCount").text(vcentertotal);
            $("#xenCount").text(xentotal);
            $("#kvmCount").text(kvmtotal);
            $("#hypervCount").text(hypervtotal);
        }

    });
}
function randx() {
    return parseInt(Math.random() * 27);
};
function randy() {
    return parseInt(Math.random() * 30);
};
var x;
var y;
var count;
function createToolTip(data, id) {//需要定制修改
    var content = id + "<p><b>" + data.hostName + "</b><br/><b>Type:</b>" + data.hostType + "<br /><b>Mem:</b>" + data.memUsage + "<br /><b>Cpu:</b>" + data.cpuUsage;
    $("#" + id).jqxTooltip({
        content: content,
        position: 'mouse',
        name: 'movieTooltip'
    });
}
function initGrid(data) {
    $("#overviewtable").find("td").each(function() {
        if ($(this).attr("class") != undefined) {
            $(this).removeClass();
        }
        if($(this).attr("id")!=undefined) {
        	$(this).attr("id","-1");
        	$("#" + $(this).attr("id")).jqxTooltip('destroy');
        }
    });

    for (var j = 0; j < data.length; j++) {
        x = randx();
        y = randy();
        count = j;
         if ($("#overviewtable tr:eq(" + x + ") td:eq(" + y + ")").attr("id") == undefined||$("#overviewtable tr:eq(" + x + ") td:eq(" + y + ")").attr("id") == "-1") {
            if (data[j].maxlevel == "5") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + y + ")").addClass("TD_red").attr('id', 'Tooltip' + j);
            } else if (data[j].maxlevel == "4") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + y + ")").addClass("TD_orange").attr('id', 'Tooltip' + j);

            } else if (data[j].maxlevel == "3") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + y + ")").addClass("TD_yellow").attr('id', 'Tooltip' + j);

            } else if (data[j].maxlevel == "2") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + y + ")").addClass("TD_blue").attr('id', 'Tooltip' + j);

            } else if (data[j].maxlevel == "1") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + y + ")").addClass("TD_green").attr('id', 'Tooltip' + j);
            } else {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + y + ")").addClass("TD_default").attr('id', 'Tooltip' + j);
            }
        } else {
            if (data[j].maxlevel == "5") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + (y + 1) + ")").addClass("TD_red").attr('id', 'Tooltip' + j);
            } else if (data[j].maxlevel == "4") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + (y + 1) + ")").addClass("TD_orange").attr('id', 'Tooltip' + j);

            } else if (data[j].maxlevel == "3") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + (y + 1) + ")").addClass("TD_yellow").attr('id', 'Tooltip' + j);

            } else if (data[j].maxlevel == "2") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + (y + 1) + ")").addClass("TD_blue").attr('id', 'Tooltip' + j);

            } else if (data[j].maxlevel == "1") {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + (y + 1) + ")").addClass("TD_green").attr('id', 'Tooltip' + j);
            } else {
                $("#overviewtable tr:eq(" + x + ") td:eq(" + (y + 1) + ")").addClass("TD_default").attr('id', 'Tooltip' + j);
            }
        }
        setTimeout(function() {
            createToolTip(data[count], 'Tooltip' + (count--));
        },
        1000);
    }
}
        $(document).ready(function () {
        	
        	//两个大视图切换
        	$("ul#datacenter li").click(function() {
                $(this).addClass("active").siblings().removeClass("active");
                typeId = $(this)[0].id;
                if (typeId == "all") {
                    $("#vmViewList").hide();
                    $("#vmViewGrid").show();
                    
                } else {
                    $("#vmViewGrid").hide();
                    $("#vmViewList").show();
                }
            });
        	
        	//加载grid整体视图
        	var paramUrl = "/avmon-web/vcenter/getOverViewList?vmType=amp_vcenter";//默认加载vcenter
            $.getJSON(paramUrl,
            	    function(data) {	
                initGrid(data);           	
            });
        	//默认加载vcenterView界面
        	$('#vmViewList').load('../pages/vcenter/vcenterView.jsp');
        	//加载告警分类级别数量
            loadAlarmCount();
            
        	$(".PerformanceNavBox").click(function() {
        		var typeId=$(this).attr("id");
        		if(typeId=="vcenter"){
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
                        	var paramUrlVcenter = "/avmon-web/vcenter/getOverViewList?vmType=amp_vcenter";
                			$.getJSON(paramUrlVcenter,
                            	    function(data) {	
                                initGrid(data); 
                                $.unblockUI();
                            });
                			$('#vmViewList').load('../pages/vcenter/vcenterView.jsp');
                        }
                    });
        		}else if(typeId="xen"){
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
                        	var paramUrlVcenter = "/avmon-web/vcenter/getOverViewList?vmType=amp_xen";
                			$.getJSON(paramUrlVcenter,
                            	    function(data) {	
                                initGrid(data); 
                                $.unblockUI();
                            });
                			$('#vmViewList').load('../pages/vcenter/xenView.jsp');
                        }
                    });
        		}else if(typeId="kvm"){
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
                        	var paramUrlVcenter = "/avmon-web/vcenter/getOverViewList?vmType=amp_kvm";
                			$.getJSON(paramUrlVcenter,
                            	    function(data) {	
                                initGrid(data); 
                                $.unblockUI();
                            });
                			$('#vmViewList').load('../pages/vcenter/kvmView.jsp');
                        }
                    });
        		}else if(typeId="hyperv"){
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
                        	var paramUrlVcenter = "/avmon-web/vcenter/getOverViewList?vmType=amp_hyperv";
                			$.getJSON(paramUrlVcenter,
                            	    function(data) {	
                                initGrid(data); 
                                $.unblockUI();
                            });
                			$('#vmViewList').load('../pages/vcenter/hypervView.jsp');
                        }
                    });
        		}
                //需要添加新类型
            });
        	$("#queryIP").click(function() {//右上角查询框
        		       	
//         		dbsource.url = '${ctx}/performance/getDBPerformanceList?queryIP='+ ipaddress+"&bizName="+encodeURI(encodeURI(itemQueryIp.label));
//         		loadGrid(dbsource);
//         		var url='${ctx}/performance/getDBOverViewPerformanceList?bizName='+encodeURI(encodeURI(itemQueryIp.label))+'&queryIP='+ipaddress;
//         		loadDbOverView(url);
//         		loadAlarmCount(ipaddress);
        	});
        	
        });
    </script> 

<script src="${ctx}/pages/avmon_back_gif.js"></script>

</body>
</html>
