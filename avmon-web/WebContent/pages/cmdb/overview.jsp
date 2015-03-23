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
<title>配置管理</title>
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
				
				<div class="MainTitle f28px LineH12" id="ShowTitleMenuclick" style="cursor:pointer;">配置管理</div>
				<div class="MainTitle" style="padding:12px 0 0 10px" >
                 <div  id="bizList" class="left" style="background-color: #0096d6; color:#ffffff; border:0px;"></div>
                 </div>
                 
		           <div class="TopMenu">
			            <ul>
				            <li><a href="#"><b class="left TopMenu_icon_HNST Icon40"></b><span>资源视图</span></a></li>
				            <li><a href="#"><b class="left TopMenu_icon_HNZYSL Icon40"></b><span>资源配置</span></a></li>
			            </ul>
		            </div>
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
              <li id="ALL" class="Tab_category_Overview_active"><a href="viewManager"><b class="Icon24 Overview_icon_ST"></b><span>视图管理</span></a></li>
              <li id="HARDWARE"><a href="classManager"><b class="Icon24 Overview_icon_ZYSL"></b><span>资源实例</span></a></li>              
              <li id="HOST"><a href="relationshipManager"><b class="Icon24 Overview_icon_GXSL"></b><span>关系实例</span></a></li>
              <li id="DATABASE"><a href="classModelManager"><b class="Icon24 Overview_icon_ZYMX"></b><span>资源模型</span></a></li>
              <li id="NETWORK"><a href="relationshipModelManager"><b class="Icon24 Overview_icon_GXMX"></b><span>关系模型</span></a></li>              
              <li id="MIDDLEWARE"><a href="resourceSearch"><b class="Icon24 Overview_icon_ZYCX"></b><span>资源查询 </span></a></li>
              </ul>
              </div>
            </div>
            
            <div class="content">

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
 
               
	});
 
  </script>

<script src="${ctx}/pages/avmon_back_gif.js"></script>

</body>
</html>
