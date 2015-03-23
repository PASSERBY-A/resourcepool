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
<title>关系资源管理</title>
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
              <li id="ALL"><a href="viewManager"><b class="Icon24 Overview_icon_ST"></b><span>视图管理</span></a></li>
              <li id="HARDWARE"><a href="classManager"><b class="Icon24 Overview_icon_ZYSL"></b><span>资源实例</span></a></li>              
              <li id="HOST"><a href="relationshipManager"><b class="Icon24 Overview_icon_GXSL"></b><span>关系实例</span></a></li>
              <li id="DATABASE"><a href="classModelManager"><b class="Icon24 Overview_icon_ZYMX"></b><span>资源模型</span></a></li>
              <li id="NETWORK"  class="Tab_category_Overview_active" ><a href="relationshipModelManager"><b class="Icon24 Overview_icon_GXMX"></b><span>关系模型</span></a></li>              
              <li id="MIDDLEWARE"><a href="resourceSearch"><b class="Icon24 Overview_icon_ZYCX"></b><span>资源查询 </span></a></li>
              </ul>
              </div>
            </div>
            
            <div class="content">
           			  
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
       
        $('.content').html('<iframe id = "ciListFrame" src="http://16.159.216.146:8888/cmdb-web/pages/relMgr/index.jsp"  width="100%" height="100%" frameborder="0" scrolling="yes" />	');

	});
 
  </script>

<script src="${ctx}/pages/avmon_back_gif.js"></script>

</body>
</html>
