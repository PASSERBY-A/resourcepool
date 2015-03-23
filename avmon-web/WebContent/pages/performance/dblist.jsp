<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<!DOCTYPE html>

<head>
<meta charset="utf-8">
<title>数据库视图</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />

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
</style>
</head>

<body onload="myFunction()">


	<!-- 右键菜单 -->

	<div id="Menu" class="AvmonRightC"
		style="z-index: 99999; display: none">
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
	<div
		style="width: 100%; height: 650px; display: none; margin-top: 50px;"
		id="mainDemoContainer">
		<div id="alarmforward">
			<div id="windowHeader">
				<span class="Icon16 Icon_GJQZ"></span><span
					class="f14px white paddingL20">告警前转</span>
			</div>

			<div style="margin: 0px; padding: 0px;">


				<div style="margin: 10px;">
					<div class="left">
						<div class="overlay-El-line">
							<div style="margin-bottom: 4px;">告警主机</div>
							<div>
								<input id="alarm_name" type="text" placeholder=""
									style="width: 195px;" class="">
							</div>
						</div>

						<div class="overlay-El-line">
							<div style="margin-bottom: 4px;">告警指标</div>
							<div>
								<input id="alarm_index" type="text" placeholder=""
									style="width: 195px;" class="">
							</div>
						</div>

						<div class="overlay-El-line">
							<div style="margin-bottom: 4px;">告警标题</div>
							<div>
								<input id="alarm_title" type="text" placeholder=""
									style="width: 195px;" class="">
							</div>
						</div>


						<div class="overlay-El-line">
							<div
								style="margin-bottom: 4px; display: inline-block; width: 195px;">开始时间</div>
							<div>
								<input id="start_time" type="text" placeholder="2014-08-08"
									style="width: 195px;">
							</div>
						</div>

					</div>

					<div class="left">
						<div class="overlay-El-line">
							<div style="margin-bottom: 4px;">告警内容</div>
							<div>
								<textarea id="forward_content" rows="6"
									placeholder="Enter text ..."
									style="width: 280px; height: 144px;"></textarea>
							</div>
							<div class="AVmon-validator-label"></div>
						</div>

						<div class="overlay-El-line">
							<div style="margin-bottom: 4px;">告警级别</div>
							<div>
								<input id="alarm_level" type="text" placeholder=""
									style="width: 195px;" class="">
							</div>
						</div>

					</div>

					<div class="blank0"></div>

				</div>
				<div class="blank10"></div>

				<div class="AvmonButtonArea">

					<div class="left">
						<span id="forwardConfirm" class="AvmonOverlayButton">确定</span><span
							id="forwardCancel" class="AvmonOverlayButton"> 取消</span>
					</div>
					<div class="blank0"></div>

				</div>

			</div>
		</div>
	</div>
	<!-- end -->


	<!-- 认领window -->
	<div
		style="width: 100%; height: 650px; display: none; margin-top: 50px;"
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
						<div id="log" class="AvmonMain" style="float: left; margin: 0px;">
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
									style="width: 195px;">
							</div>
						</div>

					</div>

					<div class="left">
						<div class="overlay-El-line">
							<div style="margin-bottom: 4px;">处理人</div>
							<!--  <div id="digits2" class="jqx-validator-error-element"></div>  -->
							<div>
								<input id="s_aknowledge_by" type="text" placeholder=""
									style="width: 195px;" class="">
							</div>
						</div>

					</div>
					<div class="blank0"></div>
					<div class="left">
						<div class="overlay-El-line">
							<div style="margin-bottom: 4px;">告警内容</div>
							<div>
								<textarea id="s_content_t" rows="6" placeholder=""
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
								<div>
									<div><div id="start_date" class="left"></div>
											<div  class="left">--</div>
									<div id="end_date" class="left"></div></div>
								</div>
							</div>
						</div>
					</div>

				</div>
				<div class="blank10"></div>
				<div class="AvmonButtonArea">
					<div class="left">
						<span id="searchbutton" class="AvmonOverlayButton">查询</span>
						<span id="searchcancel" class="AvmonOverlayButton"> 取消</span>
					</div>
					<div class="blank0"></div>
				</div>
			</div>
		</div>
	</div>


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
    
    <!-- showUserBox ================================================== -->
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
            <!-- Content box-010
      ================================================== -->  
    <div id="DATABASE_ORACLE" class="PerformanceNavBox" style="cursor:pointer"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/oracle_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">Oracle</span><span id="oracleCount" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="oracle-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="oracle-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="oracle-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="oracle-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="oracle-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>    
            </li> 

            
		<li>
<!-- Content box-11
      ================================================== -->  
    <div id="DATABASE_Sqlserver" style="cursor:pointer" class="PerformanceNavBox"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/Sqlserver_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">SQL Server</span><span id="sqlserverCount" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="sqlserver-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="sqlserver-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="sqlserver-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="sqlserver-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="sqlserver-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li>
 

            
		<li>
<!-- Content box-12
      ================================================== -->  
    <div id="DATABASE_DB2" class="PerformanceNavBox" style="cursor:pointer"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/DB2_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">DB2</span><span id="db2Count" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="db2-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="db2-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="db2-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="db2-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="db2-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
            </li> 


            
		<li>
<!-- Content box-13
      ================================================== -->  
    <div id="DATABASE_POSTGRES" class="PerformanceNavBox" style="cursor:pointer"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/PostgreSQL_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">PostgreSQL</span><span id="postgresCount" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="postgres-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="postgres-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="postgres-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="postgres-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="postgres-level1" class="LineH12">0</span></div>
       </div>
       
     </div> 
   </div>   
              
 </li> 

            
		<li>
<!-- Content box-14
      ================================================== -->  
    <div id="DATABASE_MYSQL" class="PerformanceNavBox" style="cursor:pointer"> 
     <div class="left PerformanceNavBoxPhoto"><img src="${ctx}/resources/images/MySQL_icon.png" width="32" height="32"></div>
     <div class="left PerformanceNavBoxtitle">
         <span class="f18px LineH18 inline-block paddingR20">MySQL</span><span id="mysqlCount" class="tooltip-Color-O tooltip-position inline-block" style="top: -6px;">0</span>
       
       <div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-red" style="display: block; position: relative;"></b><span id="mysql-level5" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-orange" style="display: block; position: relative;"></b><span id="mysql-level4" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-yellow" style="display: block; position: relative;"></b><span id="mysql-level3" class="LineH12">0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-blue" style="display: block; position: relative"></b><span id="mysql-level2" class="LineH12" >0</span></div>
         <div class="PerformanceNavBoxStatus w45px" style="margin-top: 6px;"><b class="circle bg-color-green" style="display: block; position: relative;"></b><span id="mysql-level1" class="LineH12">0</span></div>
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
   <!--  <td class="TD_orange"  id="Tooltip01"><a href="物理主机_ViewDetail_V1.html">#</a></td> -->
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
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
   <!--  <td class="TD_orange">&nbsp;</td> -->
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
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
    <!-- <td class="TD_orange">&nbsp;</td> -->
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
   <!--  <td class="TD_orange">&nbsp;</td> -->
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
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
    <!-- <td class="TD_orange">&nbsp;</td> -->
    <td>&nbsp;</td>
     <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
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
   <!--  <td class="TD_green">&nbsp;</td> -->
   <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
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
   <!--  <td class="TD_blue">&nbsp;</td> -->
   <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
   <!--  <td class="TD_yellow">&nbsp;</td> -->
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
   <!--  <td class="TD_orange">&nbsp;</td> -->
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
    <!-- <td class="TD_red">&nbsp;</td> -->
    <td>&nbsp;</td>
     <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
        <td>&nbsp;</td>
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

<!-- --------------------grid double click pop window content start----------------------- -->

<div style="width: 100%;margin-top: 50px;" id="mainDemoContainer"> 
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
       <div onclick="closeFun()" class="Avmon-white-close Icon16 right"></div> 
      </ul> 
      <div style="margin:4px"> 
       <div class="left baseBox">
        <!-- 第一列--> 
        <!-- 第一列—box1
    							  ================================================== --> 
        <div class="BaseInfoPerMain"> 
         <div class="left">
          <img src="${ctx}/resources/images/oracle_icon.png" width="32" height="32" />
         </div> 
         <div class="left BasePerBox"> 
          <p class="fB f14px" id="dbNameVal"></p> 
<!--           <p>IP 192.168.255.255</p>  -->
          <ul> 
           <li><span class="title" id="dbVer">版本信息</span><span class="content" id="dbVerVal"></span></li> 
           <li><span class="title" id="dbOpt">安装选项</span><span class="content" id="dbOptVal"></span></li> 
           <li><span class="title" id="dbBit">数据库位数</span><span class="content" id="dbBitVal"></span></li> 
           <li><span class="title" id="dbFile">归档方式</span><span class="content" id="dbFileVal"></span></li> 
          </ul> 
         </div> 
        </div> 
        <!-- 第一列—box2 --> 
        <div class="BaseInfoPerMain"> 
         <div class="BaseInfoPerBoxHeader">
          <b class="Pos-R Icon_ErrorLog Icon16 marginT8"></b>
          <span class="fB">数据库状态</span>
         </div> 
         <div class="BaseInfoPerBoxF"> 
          <ul> 
           <li><b class="Icon_status_green Icon16 Pos-R marginL8 left" id="dbStatus"></b><span class="title left darkblue W60" style="width:110px">存活状态</span><span class="content" id="dbStatusVal"></span></li> 
           <li><b class="Icon_status_green Icon16 Pos-R marginL8 left" id="dbLisStatus"></b><span class="title left darkblue W60" style="width:110px">监听服务状态</span><span class="content" id="dbLisStatusVal"></span></li> 
           <li><b class="Icon_status_green Icon16 Pos-R marginL8 left" id="dbErrNo"></b><span class="title left darkblue W60" style="width:110px">错误日志</span><span class="content" id="dbErrNoVal"></span></li> 
          </ul> 
         </div> 
        </div> 
        <!-- 第一列—box3 --> 
        <div class="BaseInfoPerMain"> 
         <div class="BaseInfoPerBoxHeader">
          <b class="Pos-R Icon_MeM Icon16 marginT8"></b>
          <span class="fB">SGA配置信息</span>
<!--           <span class="tooltip-Color-O tooltip-position inline-block LineH12" style="top: 5px;margin-left: 15px;">5</span> -->
         </div> 
         <div class="BaseInfoPerBoxF"> 
          <ul> 
           <li><span>引导区大小    </span><span class="title darkblue f14px" id="dbFixedSizeVal"></span></li>
           
           <li><span>共享内存区大小    </span><span class="title darkblue f14px" id="dbVariableSizeVal"></span></li>
           <li><span>数据缓冲区大小   </span><span class="title darkblue f14px" id="dbDatabaseBuffersVal"></span></li>
           <li><span>日志缓冲区大小   </span><span class="title darkblue f14px" id="dbRedoBuffersVal"></span></li>
           
<!--            <li><span class="f10px" id="dbFixedSize">引导区大小    </span><span class="content" id="dbFixedSizeVal"></span></li>  -->
<!--            <li><span class="f10px" id="dbVariableSize" >共享内存区大小    </span><span class="content" id="dbVariableSizeVal"></span></li>  -->
<!--            <li><span class="f10px" id="dbDatabaseBuffers">数据缓冲区大小    </span><span class="content" id="dbDatabaseBuffersVal"></span></li>  -->
<!--            <li><span class="f10px" id="dbRedoBuffers">日志缓冲区大小    </span><span class="content" id="dbRedoBuffersVal"></span></li> -->
<!--            <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12" style="top: 5px;margin-left: 15px;">1</span> -->
<!--             <div id="chartContainer" style="width: 100%; height:130px;"></div> </li>  -->
          </ul> 
         </div> 
        </div> 
       </div> 
       <div class="left W10"> 
        <!-- 第二列--> 
        <!--                          <div class="H28"></div>
                          <div class="H28"></div>--> 
        <div style="width:115px;" class="M-Top-300"> 
         <p class="f10px" id="dbLibcacheHitPercent">库缓存get命中率</p> 
         <p class="f10px" id="dbLibcacheHitPercentVal"></p> 
         <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10" /></p> 
         <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10" /></p> 
         <p class="f10px" id="dbGlobalHitPercent">全局缓冲区命中率</p> 
         <p class="f10px" id="dbGlobalHitPercentVal"></p> 
        </div> 
       </div> 
       <div class="left baseBox">
        <!-- 第三列--> 
        <!-- 第三列—box1
    							  ================================================== --> 
        <div class="BaseInfoPerMain"> 
         <div class="BaseInfoPerBoxHeader">
          <b class="Pos-R Icon_Progress Icon16 marginT8"></b>
          <span class="fB">会话信息</span>
         </div> 
         <div class="margin10" style="height:100px;"> 
          <div class="left" style="margin-left:20px; height:1px;"></div> 
          <div class="left W30">
           <span class="Number-Main" id="dbWaitSessionsPerVal"></span>
           <span class="Top-Main">%</span>
           <div style="margin-top: 10px; overflow: hidden; background-color:#eaedf1; border:0px solid #eaedf1;" id="jqxVerticalProgressBar1"></div>
          </div> 
          <div class="left W30">
           <span class="Number-Main" id="dbActiveSessionsPerVal"></span>
           <span class="Top-Main">%</span>
           <div style="margin-top: 10px; overflow: hidden; background-color:#eaedf1; border:0px solid #eaedf1;" id="jqxVerticalProgressBar2"></div>
          </div> 
          <div class="left W30"> 
           <ul> 
            <li><span class="waitcolor"></span><span class="marginR10">待解锁</span></li> 
            <li><span class="progresscolor"></span><span class="marginR10">活动</span></li> 
            <li><span class="currentcolor"></span><span class="marginR10">当前</span></li> 
           </ul> 
          </div> 
         </div> 
        </div> 
        <!-- 第三列—box2 --> 
        <div class="BaseInfoPerMain"> 
         <div class="BaseInfoPerBoxHeader">
          <b class="Pos-R Icon_Timer Icon16 marginT8"></b>
          <span class="fB">等待事件信息</span>
          <span class="tooltip-Color-O tooltip-position inline-block LineH12" style="top: 5px;margin-left: 15px;" id="dbWaitEventCountVal">0</span>
         </div> 
         <div class="BaseInfoPerBoxE"> 
          <ul id="dbWaitEventDetail"> 
           
          </ul> 
         </div> 
        </div> 
        <!-- 第三列—box3 --> 
        <div class="BaseInfoPerMain"> 
         <div class="BaseInfoPerBoxHeader">
          <b class="Pos-R Icon_PC Icon16 marginT8"></b>
          <span class="fB">主机负载信息</span>
<!--           <span class="tooltip-Color-O tooltip-position inline-block LineH12" style="top: 5px;margin-left: 15px;">2</span> -->
         </div> 
         <div class="BaseInfoPerBoxD"> 
          <ul> 
           <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12" style="top: 5px;margin-left: 15px;">1</span> 
            <div style="margin-left:20px;"> 
             <div class="left margin4 W42"> 
<!--               <div id="dbHostOsLoadPer" data-dimension="90" data-width="18" data-fontsize="18" data-percent="0" data-fgcolor="#7fbb00" data-bgcolor="#eaf3dd" data-type="half" data-icon="fa-task"></div>  -->
<!--               <div class="table-center"></div>  -->
              
              <div class="chart">
			      <div class="percentage" id="dbHostOsLoadPer" data-percent="0"></div>
	          </div>
             </div> 
             <div class="left margin10 W42"> 
              <span class="Number-Main" id="dbHostOsLoadVal">0</span>
              <span class="Top-Main">%</span> 
              <span class="Number-title">Current OS Load</span> 
             </div> 
            </div> </li> 
           <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12" style="top: 5px;margin-left: 15px;">2</span> 
            <div style="margin-left:20px;"> 
             <div class="left margin4 W42"> 
<!--               <div id="dbHostCPUUsagePer" data-dimension="90" data-width="18" data-fontsize="18" data-percent="0" data-fgcolor="#7fbb00" data-bgcolor="#eaf3dd" data-type="half" data-icon="fa-task"></div>  -->
<!--               <div class="table-center"></div>  -->
              <div class="chart">
			      <div class="percentage" id="dbHostCPUUsagePer" data-percent="0"></div>
	          </div>
             </div> 
             <div class="left margin10 W42"> 
              <span class="Number-Main" id="dbHostCPUUsagePerVal">0</span>
              <span class="Top-Main">%</span> 
              <span class="Number-title">CPU Usage</span> 
             </div> 
            </div> </li>
            <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12" style="top: 5px;margin-left: 15px;">3</span> 
            <div style="margin-left:20px;"> 
             <div class="left margin4 W42"> 
<!--               <div id="dbHostCPUUtilPer" data-dimension="90" data-width="18" data-fontsize="18" data-percent="0" data-fgcolor="#7fbb00" data-bgcolor="#eaf3dd" data-type="half" data-icon="fa-task"></div>  -->
<!--               <div class="table-center"></div>  -->
              <div class="chart">
			      <div class="percentage" id="dbHostCPUUtilPer" data-percent="0"></div>
	          </div>
             </div> 
             <div class="left margin10 W42"> 
              <span class="Number-Main" id="dbHostCPUUtilVal">0</span>
              <span class="Top-Main">%</span> 
              <span class="Number-title">CPU Utilization</span> 
             </div> 
            </div> </li>
            <li> <span class="tooltip-Color-N tooltip-position inline-block LineH12" style="top: 5px;margin-left: 15px;">4</span> 
            <div style="margin-left:20px;"> 
            
            <span class="f10px" id="dbHostNetworkBytesPer" style="top: 5px;margin-left: 15px;">Network Traffic Volume:    </span>
            <span class="content" id="dbHostNetworkBytesPerVal"></span>
            
<!--              <div class="left margin4 W42">  -->
<!--               <div id="dbHostNetworkBytesPer" data-dimension="90" data-width="18" data-fontsize="18" data-percent="0" data-fgcolor="#7fbb00" data-bgcolor="#eaf3dd" data-type="half" data-icon="fa-task"></div>  -->
<!--               <div class="table-center"></div>  -->
<!--              </div>  -->
<!--              <div class="left margin10 W42">  -->
<!--               <span class="Number-Main" id="dbHostNetworkBytesPerVal">0</span> -->
<!--               <span class="Top-Main">%</span>  -->
<!--               <span class="Number-title">Network Traffic Volume</span>  -->
<!--              </div>  -->
            </div> </li> 
          </ul> 
         </div> 
        </div> 
       </div> 
       <div class="left W10">
        <!-- 第四列--> 
        <div class="statusbox H28"> 
         <p class="f10px" id="dbTablespacePhyrds">表空间读次数</p> 
         <p class="f10px" id="dbTablespacePhyrdsVal"></p> 
         <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10" /></p> 
         <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10" /></p> 
         <p class="f10px" id="dbTablespacePhywrts">表空间写次数</p> 
         <p class="f10px" id="dbTablespacePhywrtsVal"></p> 
        </div> 
        <div class="H28 statusbox" style="margin-top:100%;"> 
         <p class="f10px" id="dbCommitPercent">提交事务比率</p> 
         <p class="f10px" id="dbCommitPercentVal"></p> 
         <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10" /></p> 
         <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10" /></p> 
         <p class="f10px" id="dbRollbackPercent">回滚事务比率</p> 
         <p class="f10px" id="dbRollbackPercentVal"></p> 
        </div> 
       </div> 
       <div class="left baseBox" style="width:27%;">
        <!-- 第五列--> 
        <div class="BaseInfoPerMain2"> 
         <div class="BaseInfoPerBoxHeader2">
          <b class="Pos-R Icon_Table Icon16 marginT8"></b>
          <span class="fB">表空间</span>
          <span class="tooltip-Color-O tooltip-position inline-block LineH12" style="top: 5px;margin-left: 15px;" id="dbTablespaceCountVal">0</span>
         </div> 
         <div class="BaseInfoPerBoxH"> 
          <ul id="dbTablespaceDetail"> 
           
          </ul> 
         </div> 
         <!-- END List Wrap --> 
        </div> 
        <!-- 第五列—box2 --> 
        <div class="BaseInfoPerMain"> 
         <div class="BaseInfoPerBoxHeader2">
          <b class="Pos-R Icon_IO Icon16 marginT8"></b>
          <span class="fB">整体io指标</span>
         </div> 
         <div class="BaseInfoPerBoxF2"> 
          <ul> 
           <li><span class="title darkblue f14px" id="dbPhysicalReadIOPSVal"></span><span>Physical Read Total IO Requests</span></li> 
           <li><span class="title darkblue f14px" id="dbPhysicalWriteIOPSVal"></span><span>Physical Write Total IO Requests</span></li> 
           <li><span class="title darkblue f14px" id="dbPhysicalRedoIOPSVal"></span><span>Redo Writes</span></li> 
          </ul> 
         </div> 
        </div> 
       </div> 
      </div> 
      
      <div> 
	      <!--tab 2-->
	      <div class="KPINav">
	       <div class="left marginR15" id="autoRefresh"><a href="#"  class="Avmon-button"><b class="R-icon_4 Icon16" style="position:relative;top:3px;"></b>自动刷新</a></div>
	        <div class="left" id="manuRefresh"><a href="#"  class="Avmon-button"><b class="R-icon_1 Icon16" style="position:relative;top:3px;"></b>手动刷新</a></div>
	      </div>
	      <div class="KPIlist">
	         <div id="jqxgrid5">Loading</div>
	      </div> 
	      <!-- end -->
	  </div>
	  
      <div> 
       <div class="KPINav"> 
	    <div class="left marginR15">
	     <span class="LineH24 marginR40 left">提醒：KPI指标最多能选择5个。 </span>
	     <span class="LineH24 marginR6 left">MO:</span>
	     <span class="LineH24 marginR40 left" id="hostip"></span>
	     <span id="jqxWidgetData1" class="left marginR40"></span> 
	     <span class=" LineH24 marginR6 left"> Time:</span>
	     <span class="LineH24 marginR6 left"><div id="startDate"></div></span> 
	     <span id="startHour" class="left marginR6"></span>  
	     <span class="LineH24 marginR6 left">To</span> 
	     <span class="LineH24 marginR6 left"><div id="toDate"></div></span>
	     <span id="endHour" class="left marginR15"></span>
	    </div> 
	    <div class="left marginR15" id="queryKpi">
	     <a href="#" class="Avmon-button"><b class="Search_icon2 Icon16" style="position:relative;top:3px;"></b>查询</a>
	    </div> 
	   </div> 
	   <div class="W20 left marginL10"> 
	    <p class="f16px marginB10 marginT10">指标列表</p> 
	    <div id="kpigrid"></div> 
	   </div> 
	   <div class="W76 left marginL20"> 
	    <p class="f16px marginB10 marginT10">KPI历史趋势</p> 
	    <div style="height:90%; width:100%;overflow-y: scroll;"> 
	     <div id="chart1" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:20px 0px;display:block">
	      <span style="margin: 50px auto;display: block;text-align: center;"></span>
	     </div> 
	     <div id="chart2" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block">
	      <span style="margin: 50px auto;display: block;text-align: center;"></span>
	     </div> 
	     <div id="chart3" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block">
	      <span style="margin: 50px auto;display: block;text-align: center;"></span>
	     </div> 
	     <div id="chart4" style="border:1px solid #f4f4f4; width:100%; height:55%; margin:4px 0px;display:block">
	      <span style="margin: 50px auto;display: block;text-align: center;"></span>
	     </div> 
	     <div id="chart5" style="border:1px solid #f4f4f4; width:100%; height:55%; margin:4px 0px;display:block">
	      <span style="margin: 50px auto;display: block;text-align: center;"></span>
	     </div> 
	    </div> 
	   </div> 
	  </div> 
      
      <div class="margin10"> 
	   <div class=" paddingL4"> 
	    <div class="left paddingL4 NavMainBtn"> 
	     <a href="#" class="Avmon-button" id="showWindowButton">高级查询</a> 
	    </div> 
	    <div class="left Padding14 NavMainBtn"> 
	     <a href="#" class="Avmon-button"><span id="batchupdate">批量更新告警状态</span></a> 
	    </div> 
	    <div class="blank20"></div> 
	   </div> 
	   <div id="alarmGrid"> 
	   </div> 
	  </div>
 
     </div> 
    </div> 
   </div> 
  </div>

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
<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxgrid.columnsresize.js"></script>

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

$(document).ready(function() {
	
	//获取url参数
    $.getUrlParam = function(name) {
	    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
	    var r = location.search.substr(1).match(reg);
	    if (r != null) return unescape(r[2]);
	    return null;
	};
	
	var agentId = "";
	var typeId = "";
	var moId = "";
    var alarmId = "";
    var hostip = "";
    var oracletotal = 0;
    var db2total = 0;
    var sqlservertotal = 0;
    var postgrestotal = 0;
    var mysqltotal = 0;
    var batchFilter="";
    var isClickSearch=false;
	var alarmIdList = [];
    $("ul#datacenter li").click(function() {
        $(this).addClass("active").siblings().removeClass("active");
        typeId = $(this)[0].id;
        if (typeId == "all") {
            $("#jqxgrid").hide();
            $("#qqqq").show();
        } else {
            $("#qqqq").hide();
            $("#jqxgrid").show();
        }
    });
    var hours = ['00:00', '01:00', '02:00', '03:00', '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00', '11:00', '12:00', '13:00', '14:00', '15:00', '16:00', '17:00', '18:00', '19:00', '20:00', '21:00', '22:00', '23:00'];
    var grainsizes = ["Original Date", "By Hour", "By Day", ];
    
    // showCollapseButton: true, maxHeight: '95%', maxWidth: '99%', minHeight: '95%', minWidth: '99%', height: '95%', width: '99%', isModal: true, modalOpacity: 0.7,
    $("#window").jqxWindow({
        showCollapseButton: false,
        autoOpen: false,
        isModal: true,
        resizable: false,
        maxHeight: '95%',
        maxWidth: '99%',
        minHeight: '95%',
        minWidth: '99%',
        height: '95%',
        width: '99%',

        initContent: function() {
            $('#tab').jqxTabs({
                height: '100%',
                width: '100%'
            });
            $('#window').jqxWindow('focus');
            // $('#Progress').circliful();
        }
    });
    var moId = "";

    $('#jqxgrid').on('rowdoubleclick',
    function(event) {
        var rowid = event.args.rowindex;
        var rowdata = $('#jqxgrid').jqxGrid('getrowdata', rowid);
        moId = rowdata.MO_ID;
        agentId = rowdata.agentId;
        typeId = rowdata.typeId;

        //hostip=rowdata.hostName;
		$("#hostip").text(moId);
		loadNestGrid(moId);
        
        //db basic info
        $.getJSON("${ctx}/performancedb/getBasicInfoDataForDB?moId=" + moId,
        function(data) {
            /**
       	    	    $("#dbVer").text(data.basicData[1].KEY);
       	    	    $("#dbOpt").text(data.basicData[2].KEY);
       	    	    $("#dbBit").text(data.basicData[3].KEY);
       	    	    $("#dbFile").text(data.basicData[4].KEY);
       	    	    
       	    	    $("#dbNameVal").text(data.basicData[0].VAL);
    	    	    $("#dbVerVal").text(data.basicData[1].VAL);
    	    	    $("#dbOptVal").text(data.basicData[2].VAL);
    	    	    $("#dbBitVal").text(data.basicData[3].VAL);
    	    	    $("#dbFileVal").text(data.basicData[4].VAL);
      				*/
            $("#dbNameVal").text(data.dbName);
            $("#dbVerVal").text(data.dbVer);
            $("#dbOptVal").text(data.dbOpt);
            $("#dbBitVal").text(data.dbBit);
            $("#dbFileVal").text(data.dbFile);
        });
        //db status info
        $.getJSON("${ctx}/performancedb/getDBStatusInfoData?moId=" + moId,
        function(data) {
        	//$("#dbStatusVal").text(data.dbStatus);
            //$("#dbLisStatusVal").text(data.dbLisStatus);
            //$("#dbErrNoVal").text(data.dbErrNo);
            if(data.dbStatus!=""&&data.dbStatus!="OPEN"){
	            $("#dbStatus").removeClass();
	            $("#dbStatus").addClass("Icon_status_red Icon16 Pos-R marginL8 left");
            }
            if(data.dbLisStatus!=""&&data.dbLisStatus!="UP"){
	            $("#dbLisStatus").removeClass();
	            $("#dbLisStatus").addClass("Icon_status_red Icon16 Pos-R marginL8 left");
            }
            if(data.dbErrNo!=""){
	            $("#dbErrNo").removeClass();
	            $("#dbErrNo").addClass("Icon_status_red Icon16 Pos-R marginL8 left");
            }
        });
        //db sga info
        $.getJSON("${ctx}/performancedb/getDBSgaInfoData?moId=" + moId,
        function(data) {
            $("#dbFixedSizeVal").text(data.dbFixedSize);
            $("#dbVariableSizeVal").text(data.dbVariableSize);
            $("#dbDatabaseBuffersVal").text(data.dbDatabaseBuffers);
            $("#dbRedoBuffersVal").text(data.dbRedoBuffers);
        });
        //db hit_percent info
        $.getJSON("${ctx}/performancedb/getDBHitPercentInfoData?moId=" + moId,
        function(data) {
            $("#dbLibcacheHitPercentVal").text(data.dbLibcacheHitPercent);
            $("#dbGlobalHitPercentVal").text(data.dbGlobalHitPercent);
        });
        //db session_percent info
        $.getJSON("${ctx}/performancedb/getDBSessionsPercentInfoData?moId=" + moId,
        function(data) {
            $("#dbWaitSessionsPerVal").text(data.dbWaitSessionsPer);
            $("#dbActiveSessionsPerVal").text(data.dbActiveSessionsPer);
//             $('#jqxVerticalProgressBar1').jqxProgressBar({
//                 value: data.dbWaitSessionsPer,
//                 orientation: 'vertical',
//                 layout: 'reverse',
//                 width: 40,
//                 height: 50,
//                 animationDuration: 3000
//             });
//             $('#jqxVerticalProgressBar2').jqxProgressBar({
//                 value: data.dbActiveSessionsPer,
//                 orientation: 'vertical',
//                 layout: 'reverse',
//                 width: 40,
//                 height: 50,
//                 animationDuration: 3000
//             });
            $('#jqxVerticalProgressBar1').jqxProgressBar('actualValue', data.dbWaitSessionsPer);
            $('#jqxVerticalProgressBar2').jqxProgressBar('actualValue', data.dbActiveSessionsPer); 
        });
        //db event info
        $.getJSON("${ctx}/performancedb/getEventInfoForDBList?moId=" + moId,
        function(data) {
            $("#dbWaitEventCountVal").text(data.total);
            $("#dbWaitEventDetail").empty();
            for (var i = 0; i < data.root.length; i++) {
                var tempHtml = '<li> <span class="tooltip-Color-N tooltip-position inline-block LineH12" style="top: 18px;margin-left: 15px;">' + (i + 1) + '</span> <div style="margin-left:40px;height: 100%;"> <p>事件名称</p> <p class="darkblue">' + data.root[i].dbWaitEvent + '</p> <p class="blank10"></p> <div class="left W30"> <div class=" f10px darkblue">' + data.root[i].dbWaitNumber + '</div> <div class="f10px">等待次数</div></div> <div class="left W30 marginL8"> <div class=" f10px darkblue">' + data.root[i].dbWaitTimeouttimes + '</div> <div class="f10px">超时次数 </div> </div> <div class="left W30 marginL8"> <div class=" f10px darkblue">' + data.root[i].dbWaitTimeout + 'ms</div> <div class="f10px">等待时间 </div> </div> </div> </li>'
                var $htmlLi = $(tempHtml);
                $("#dbWaitEventDetail").append($htmlLi);
            }
        });
        //db host info
        $.getJSON("${ctx}/performancedb/getDBHostUsageInfoData?moId=" + moId,
        function(data) {
            $("#dbHostOsLoadVal").text(data.dbHostOsLoad);
            $('#dbHostOsLoadPer').attr("data-percent", data.dbHostOsLoad);
            $('#dbHostOsLoadPer').data('easyPieChart').update(data.dbHostOsLoad);
            //$('#dbHostOsLoadPer').circliful();

            $("#dbHostCPUUsagePerVal").text(data.dbHostCPUUsagePer);
            $('#dbHostCPUUsagePer').attr("data-percent", data.dbHostCPUUsagePer);
            $('#dbHostCPUUsagePer').data('easyPieChart').update(data.dbHostCPUUsagePer);

            $("#dbHostCPUUtilVal").text(data.dbHostCPUUtil);
            $('#dbHostCPUUtilPer').attr("data-percent", data.dbHostCPUUtil);
            $('#dbHostCPUUtilPer').data('easyPieChart').update(data.dbHostCPUUtil);

            $("#dbHostNetworkBytesPerVal").text(data.dbHostNetworkBytesPer);//+"bytes/s"
            //$('#dbHostNetworkBytesPer').attr("data-percent", data.dbHostNetworkBytesPer);
            //$('#dbHostNetworkBytesPer').circliful();
        });
        //db readwrite info
        $.getJSON("${ctx}/performancedb/getDBReadWriteInfoData?moId=" + moId,
        function(data) {
            $("#dbTablespacePhyrdsVal").text(data.dbTablespacePhyrds);
            $("#dbTablespacePhywrtsVal").text(data.dbTablespacePhywrts);
        });
        //db commitrollback info
        $.getJSON("${ctx}/performancedb/getDBCommitRollbackInfoData?moId=" + moId,
        function(data) {
            $("#dbCommitPercentVal").text(data.dbCommitPercent);
            $("#dbRollbackPercentVal").text(data.dbRollbackPercent);
        });
        //db tablespace info
        $.getJSON("${ctx}/performancedb/getTablspaceInfoForDB?moId=" + moId,
        function(data) {
            $("#dbTablespaceCountVal").text(data.total);
            $("#dbTablespaceDetail").empty();
            for (var i = 0; i < data.root.length; i++) {
                var tempHtml = '<li> <p>名称</p> <div><div class="left W70"><p class="darkblue">' + data.root[i].dbTablespacenName + 
                '</p></div><div class="right W30"><b class="'+(data.root[i].dbTablespacenStatus=="ONLINE"?'Icon_status_green':'Icon_status_red')+
                ' Icon16 Pos-R marginL8 left"></b><span class="title left darkblue" style="width:60px">' + 
                data.root[i].dbTablespacenStatus + '</span></div></div> <p class="blank10"></p> <p>使用率</p> <div style="margin-top: 2px;"><span class="left w50 f10px darkblue">' + 
                data.root[i].dbTablespaceMegsAlloc + '</span><span class="w50 right f10px table-right gray">' + data.root[i].dbTablespaceMegsUsed + 
                '</span></div> <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="jqxProgressBar' + (i + 1) + 
                '"></div><span class="f10px darkblue">' + data.root[i].dbTablespacePctUsed+'%' + '</span> <div class="blank10"></div> </li>'
                var $htmlLi = $(tempHtml);
                $("#dbTablespaceDetail").append($htmlLi);
            }
            for (var i = 0; i < data.root.length; i++) {
                var barId = '#jqxProgressBar' + (i + 1);
                $(barId).jqxProgressBar({ width: '100%', height: 10, value: data.root[i].dbTablespacePctUsed, animationDuration: 1000});
//                 $(barId).jqxProgressBar({
//                     value: data.root[i].dbTablespacePctUsed+50,
//                     orientation: 'vertical',
//                     layout: 'reverse',
//                     width: 40,
//                     height: 50,
//                     animationDuration: 3000
//                 });
            }
        });
        //db io info
        $.getJSON("${ctx}/performancedb/getPhysicalInfoForDB?moId=" + moId,
        function(data) {
            $("#dbPhysicalReadIOPSVal").text(data.dbPhysicalReadIOPS);//+"/s"
            $("#dbPhysicalWriteIOPSVal").text(data.dbPhysicalWriteIOPS);
            $("#dbPhysicalRedoIOPSVal").text(data.dbPhysicalRedoIOPS);
        });
        //db dashboard data info init end==========================================================================================================================================
        	
        //弹出dashboard
        $('#window').jqxWindow('open');
        	
        //tab 4 db alarm start===========================================================================================================================================================
        var source = {
            datatype: "json",
            datafields: [{
                name: 'ID',
                type: 'string'
            },
            {
                name: 'MO_ID',
                type: 'string'
            },
            {
                name: 'KPI_CODE',
                type: 'string'
            },
            {
                name: 'LEVEL',
                type: 'string'
            },
            {
                name: 'STATUS',
                type: 'string'
            },
            {
                name: 'AMOUNT',
                type: 'String'
            },
            {
                name: 'OCCUR_TIMES',
                type: 'String'
            },
            {
                name: 'MO_CAPTION',
                type: 'string'
            },
            {
                name: 'CONTENT',
                type: 'string'
            },
            {
                name: 'AMOUNT',
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
            updaterow: function(rowid, rowdata, commit) {
                var url = '${ctx}/pages/alarm/aknowledge?content=' + rowdata.attr_result + '&alarmId=' + rowdata.id;
                $.ajax({
                    cache: false,
                    dataType: 'json',
                    url: url,
                    success: function(data, status, xhr) {
                        //alert("success");
                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        //alert(errorThrown);
                        //commit(false);
                    }
                });
                commit(true);
            },
            sort: function() {
                // update the grid and send a request to the server.
                $("#alarmGrid").jqxGrid('updatebounddata');
            },
            id: 'id',
            url: '${ctx}/performance/getAlarmData?moId=' + moId
        };

        function loadGridData(source) {
            var statusimagerenderer = function(row, datafield, value) {
                if (value != 0) {
                    return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/confirmed.gif"/>';
                }
                return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/newalarm.gif"/>';
            };
            var levelimagerenderer = function(row, datafield, value) {
                return '<img style="margin-left: 10px;padding-top:5px" height="15" width="15" src="${ctx}/resources/images/level' + value + '.png"/>';
            };
            var dataAdapter = new $.jqx.dataAdapter(source);
            $("#alarmGrid").jqxGrid({
                width: '100%',
                height: '90%',
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
                    text: '编号',
                    datafield: 'ID',
                    width: 10,
                    hidden: true
                },
                {
                    text: 'MO_ID',
                    datafield: 'MO_ID',
                    width: 10 ,
                    hidden: true
                },
                {
                    text: 'KPI_CODE',
                    datafield: 'KPI_CODE',
                    width: 10,
                    hidden: true
                },
                {
                    text: '级别',
                    datafield: 'LEVEL',
                    width: 40,
                    cellsrenderer: levelimagerenderer
                },
                {
                    text: '状态',
                    datafield: 'STATUS',
                    width: 40,
                    cellsrenderer: statusimagerenderer
                },
                {
                    text: '主机名',
                    datafield: 'MO_CAPTION',
                    width: 150
                },
                {
                    text: '告警内容',
                    datafield: 'CONTENT',
                    minwidth: 150
                },
                {
                    text: '时间',
                    datafield: 'OCCUR_TIMES',
                    minwidth: 50
                },
                {
                    text: '次数',
                    datafield: 'AMOUNT',
                    minwidth: 20
                }]
            });
        }
        loadGridData(source);
        //db alarm end===========================================================================================================================================================
        
        //拦截部分的代码           
        $("#jqxWidgetData1").jqxDropDownList({
            source: grainsizes,
            selectedIndex: 0,
            width: '130px',
            height: '25px',
            dropDownHeight: '90'
        });
        $("#startDate").jqxDateTimeInput({
            width: '160px',
            readonly: true,
            height: '25px',
            formatString: 'yyyy-MM-dd'
        });
        $("#toDate").jqxDateTimeInput({
            width: '160px',
            readonly: true,
            height: '25px',
            formatString: 'yyyy-MM-dd'
        });
        $("#startHour").jqxDropDownList({
            source: hours,
            selectedIndex: 0,
            width: '70px',
            height: '25px'
        });
        $("#endHour").jqxDropDownList({
            source: hours,
            selectedIndex: 0,
            width: '70px',
            height: '25px'
        });
        $("#queryKpi").click(function() {
        	$('#chart1').hide();
        	$('#chart2').hide();
        	$('#chart3').hide();
        	$('#chart4').hide();
        	$('#chart5').hide();
//         	Array.prototype.deleteElementByValue = function(varElement)
//         	{
//         		var arrayObject = new Array();
//         	    for (var i=0; i<this.length; i++)
//         	    {
//         	        // 严格比较，即类型与数值必须同时相等。
//         	        if (this[i] != varElement)
//         	        {
//         	        	arrayObject.push(this[i]);
//         	        }
//         	    }
//         	    return arrayObject;
//         	}
            var textRotationAngle = 0;
            var rowindexes = $('#kpigrid').jqxGrid('getselectedrowindexes');
//             rowindexes = rowindexes.deleteElementByValue(-1);
            if (rowindexes.length == 0) {
                $("#newWindow").remove();
                $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>请选择一条KPI！ </div></div>');
                $('#newWindow').jqxWindow({
                    isModal: true,
                    height: 80,
                    width: 150
                });
                return;
            }
            
            if (rowindexes.length > 5) {
            	$('#kpigrid').jqxGrid('clearselection');
                $("#newWindow").remove();
                $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>KPI最多只能选择5条记录！ </div></div>');
                $('#newWindow').jqxWindow({
                    isModal: true,
                    height: 80,
                    width: 150
                });
                return;
            }

            var grainsize = $("#jqxWidgetData1").jqxDropDownList('getSelectedIndex') + 1;
            var startTime = $('#startDate').jqxDateTimeInput('getText');
            var endTime = $('#toDate').jqxDateTimeInput('getText');
            var startHour = $("#startHour").jqxDropDownList('getSelectedItem');
            var endHour = $("#endHour").jqxDropDownList('getSelectedItem');
            startTime = startTime + " " + startHour.label;
            endTime = endTime + " " + endHour.label;
            for (var i = 0; i < rowindexes.length; i++) {
                var row = $("#kpigrid").jqxGrid('getrowdata', rowindexes[i]);
                var yAxis = row.displayKpi;
                var insAndcode = row.kpi.split("/");
                var ampInstId = insAndcode[0];
                var kpiCode = insAndcode[1];

                var baseUnit = '';
                $("#chart" + (i + 1)).show();
                if (grainsize == 3) {
                    baseUnit = 'day';
                    textRotationAngle = -75;
                } else {
                    baseUnit = 'hour';
                    textRotationAngle = -75;
                }
                loadTrendChart(ampInstId, kpiCode, startTime, endTime, moId, grainsize, "chart" + (i + 1), yAxis, baseUnit);
            }

            function loadTrendChart(ampInstId, kpiCode, startTime, endTime, moId, grainsize, id, yAxis, baseUnit) {
            	
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
                    	$.getJSON('${ctx}/performancedb/getDbKpiChartInstance?ampInstId=' + ampInstId + '&kpiCode=' + kpiCode + 
                        		'&startTime=' + startTime + '&endTime=' + endTime + '&moId=' + moId + '&grainsize=' + grainsize,
               	        function(data) {
                        	var datafieldsArr = new Array;
                        	datafieldsArr[0] = {name: 'time'};

                        	var seriesArr = new Array;
                        	
        	       	        $.each(data , function(n, val) { 
        						//datafieldsArr[datafieldsArr.length] = {name:val.instance};
        						//seriesArr[seriesArr.length] = {dataField: val.instance,displayText: val.instance};
        						datafieldsArr[datafieldsArr.length] = {name:'instance'+n};
        						seriesArr[seriesArr.length] = {dataField: 'instance'+n,displayText: val.instance};
        	       	  		});
               	        	
               	        	var trendsource = {
               	                    datatype: "json",
               	                    datafields: datafieldsArr
//               	                     	[{
//               	                         name: 'time'
//               	                     },
//               	                     {
//               	                         name: 'instance0'
//               	                     },
//               	                     {
//               	                         name: 'instance1'
//               	                     }]
               	                ,
               	                    url: '${ctx}/performancedb/dbKpiChartData?ampInstId=' + ampInstId + '&kpiCode=' + kpiCode + 
               	                    		'&startTime=' + startTime + '&endTime=' + endTime + '&moId=' + moId + '&grainsize=' + grainsize
               	                };

               	                var trenddataAdapter = new $.jqx.dataAdapter(trendsource, {
               	                    async: false,
               	                    autoBind: true,
               	                    loadError: function(xhr, status, error) {
               	                        alert('Error loading "' + trendsource.url + '" : ' + error);
               	                    }
               	                });
               	                var months = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
               	                // prepare jqxChart settings
               	                var settings = {
               	                    title: "",
               	                    description: "",
               	                    enableAnimations: true,
               	                    showLegend: true,
               	                    padding: {
               	                        left: 5,
               	                        top: 10,
               	                        right: 20,
               	                        bottom: 5
               	                    },
               	                    titlePadding: {
               	                        left: 10,
               	                        top: 0,
               	                        right: 0,
               	                        bottom: 10
               	                    },
               	                    source: trenddataAdapter,
               	                    xAxis: {
               	                        dataField: 'time',
//               	                         formatFunction: function(value) {
//               	                            return months[value.getMonth()] + '-' + value.getDate()+' '+value.getHours()+':'+value.getMinutes()+':'+value.getSeconds();
//               	                         },
//               	                         toolTipFormatFunction: function(value) {
//               	                            return value.getFullYear() + '-' + months[value.getMonth()] + '-' + value.getDate()+' '+value.getHours()+':'+value.getMinutes()+':'+value.getSeconds()+" ";
//               	                         },
               	                        showTickMarks: true,
               	                        type: 'basic',
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
               	                    seriesGroups: [{
               	                        type: 'line',//'stackedline',
               	                        valueAxis: {
               	                            //unitInterval: 500,
               	                            /*   minValue: 0,
               	                                            maxValue: 4500, */
               	                            minValue: 0,
               	                            description: yAxis,
               	                            tickMarksColor: '#888888'
               	                        },
               	                        series: seriesArr
//               	                         	[{
//               	                             dataField: 'instance0',
//               	                             displayText: yAxis
//               	                         },{
//               	                             dataField: 'instance1',
//               	                             displayText: yAxis
//               	                         }]
               	                    }]
               	                };
               	                // setup the chart
               	                //$('#' + id).show();
               	                $('#' + id).jqxChart(settings);
               	                $.unblockUI();
               	        });
                    }
                });
            }

        });
        //拦截部分的代码end
        
        //dashboard弹出右键处理菜单===========================================
        $("#alarmGrid").on('rowclick',
        function(event) {
            if (event.args.rightclick) {
                $('#alarmGrid').jqxGrid('clearselection');
                $("#alarmGrid").jqxGrid('selectrow', event.args.rowindex);
                var scrollTop = $("#alarmGrid").scrollTop();
                var scrollLeft = $("#alarmGrid").scrollLeft();
                var contextMenu = $("#Menu").jqxMenu({ width: '120px', autoOpenPopup: false, mode: 'popup', popupZIndex: 999999});
                $("#alarmGrid").on('contextmenu',
                function() {
                    return false;
                });
                contextMenu.jqxMenu('open', parseInt(event.args.originalEvent.clientX) + 5 + scrollLeft, parseInt(event.args.originalEvent.clientY) + 5 + scrollTop);                        
                return false;
            }
        });
        //dashboard弹出右键处理菜单end========================================
     
        //右键选择菜单.
        $("#Menu").on('itemclick',
        function(event) {
            var args = event.args;
            var rowindex = $("#alarmGrid").jqxGrid('getselectedrowindex');
            var rowdata = $('#alarmGrid').jqxGrid('getrowdata', rowindex);
            alarmId = rowdata.ID;
            if ($.trim($(args).text()) == $("#aknowledge").text()) {
                //editrow = rowindex; 
                $("#renling").jqxWindow({
                    showCollapseButton: true,
                    isModal: true,
                    resizable: false,
                    maxHeight: 496,
                    height: 496,
                    width: 500,
                    initContent: function() {
                        $('#renling').jqxWindow('focus');
                    }
                });
                // show the popup window.
                $("#renling").jqxWindow('show');
                logsource.url = '${ctx}/pages/alarm/getAlarmComments?alarmId=' + alarmId;
                loadLogGridData(logsource);
            } else if ($.trim($(args).text()) == $("#clear").text()) {
                var rowindex = $("#alarmGrid").jqxGrid('getselectedrowindex');

                alarmIdList = new Array();
                if (rowindex < 0) {
                    $().toastmessage('showToast', {
                        text: '请选择一条更新记录！',
                        sticky: false,
                        position: 'top-center',
                        type: 'warning',
                        closeText: ''
                    });
                    return;
                }
                var data = $('#alarmGrid').jqxGrid('getrowdata', rowindex);
                alarmId = data.ID;
                alarmIdList.push(data.ID);
                $("#windowtitle").text("清除");
                $("#renling").jqxWindow({
                    showCollapseButton: true,
                    isModal: true,
                    resizable: false,
                    maxHeight: 496,
                    height: 496,
                    width: 500,
                    initContent: function() {
                        $('#renling').jqxWindow('focus');
                    }

                });
                $('#renling').jqxWindow('show');
                logsource.url = '${ctx}/pages/alarm/getAlarmComments?alarmId=' + alarmId;
                batchFilter='alarmIdList='+alarmId;
                loadLogGridData(logsource);

            } else if ($.trim($(args).text()) == $("#forward").text()) {
                $("#alarmforward").jqxWindow({
                    showCollapseButton: true,
                    isModal: true,
                    resizable: false,
                    height: 392,
                    width: 600,
                    initContent: function() {
                        $('#alarmforward').jqxWindow('focus');
                    }
                });
                //var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
                // show the popup window.
                var rowdata = $('#alarmGrid').jqxGrid('getrowdata', rowindex);
                $("#alarm_name").val(rowdata.hostName == null ? "": rowdata.hostName);
                $("#alarm_index").val(rowdata.lastname == null ? "": rowdata.lastname);
                $("#forward_content").val(rowdata.content_t == null ? "": rowdata.content_t);
                $("#alarm_title").val(rowdata.title == null ? "": rowdata.title);
                $("#start_time").val(rowdata.firstOccurTime == null ? "": rowdata.firstOccurTime);
                $("#alarm_level").val(rowdata.level == null ? "": rowdata.level);
                alarmId = rowdata.id;
                $("#alarmforward").jqxWindow('show');
            }
        });
        // end
        
        //点击高级查询弹出查询条件窗口
        $('#showWindowButton').click(function() {
            $('#searchwindow').jqxWindow('open');
        });
        $("#searchwindow").jqxWindow({
            showCollapseButton: true,
            autoOpen: false,
            isModal: true,
            resizable: false,
            height: 350,
            width: 500,
            initContent: function() {
                $('#searchwindow').jqxWindow('focus');
            }
        });
        $("#start_date").jqxDateTimeInput({ width: '200px',readonly: true, height: '18px',max: new Date(),formatString: 'yyyy/MM/dd'});
		$("#end_date").jqxDateTimeInput({ width: '200px', readonly: true,height: '18px', max: new Date(),formatString: 'yyyy/MM/dd'});
		//search start
		
		$("#searchbutton").click(function() {
            var s_moCaption = $("#s_moCaption").val();
            var s_content_t = $("#s_content_t").val();
            var s_attr_ipaddr = $("#s_attr_ipaddr").val();
            var s_aknowledge_by = $("#s_aknowledge_by").val();
            var s_attr_businessSystem = $("#s_attr_businessSystem").val();
            var s_attr_position = $("#s_attr_position").val();
            var s_start_time = $('#start_date').jqxDateTimeInput('getText');
            var s_end_time = $('#end_date').jqxDateTimeInput('getText');

            source.url = '${ctx}/performance/getAlarmDataByCriteria?s_moCaption=' + s_moCaption + '&s_content_t=' + s_content_t + '&s_attr_ipaddr=' + s_attr_ipaddr + '&s_aknowledge_by=' + s_aknowledge_by + '&s_attr_businessSystem=' + s_attr_businessSystem + '&s_attr_position=' + s_attr_position + '&s_start_time=' + s_start_time + '&s_end_time=' + s_end_time + '&moId=' + moId;
            $.getJSON('${ctx}/performance/getAlarmDataByCriteria?s_moCaption=' + s_moCaption + '&s_content_t=' + s_content_t + '&s_attr_ipaddr=' + s_attr_ipaddr + '&s_aknowledge_by=' + s_aknowledge_by + '&s_attr_businessSystem=' + s_attr_businessSystem + '&s_attr_position=' + s_attr_position + '&s_start_time=' + s_start_time + '&s_end_time=' + s_end_time + '&moId=' + moId,
            function(data) {
                alarmIdList = [];
                for (var i = 0; i < data.length - 1; i++) {
                    alarmIdList.push(data[i].id);
                }
                isClickSearch = true;
            });
            loadGridData(source);

            $("#searchwindow").jqxWindow('close');
        });
		$("#searchcancel").click(function() {
			$("#searchwindow").jqxWindow('close');
		});
		//alarm tab高级查询结束=====================================
		//批量更新告警状态==========================================
		$("#batchupdate").click(function() {
		    var rowindexes = $('#alarmGrid').jqxGrid('getselectedrowindexes');
		    if (isClickSearch == false && ((rowindexes.length == 0 || rowindexes[0] == -1))) {
		        $("#newWindow").remove();
		        $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>请选择一条记录！ </div></div>');
		        $('#newWindow').jqxWindow({
		            isModal: true,
		            height: 80,
		            width: 150
		        });
		        return;
		    }
		
		    if (isClickSearch == false && (rowindexes.length > 0)) {
		        for (var i = 0; i < rowindexes.length; i++) {
		            var data = $('#alarmGrid').jqxGrid('getrowdata', rowindexes[i]);
		            alarmIdList.push(data.id);
		        }
		        $("#windowtitle").text("清除");
		        $("#renling").jqxWindow({
		            showCollapseButton: true,
		            isModal: true,
		            resizable: false,
		            maxHeight: 496,
		            height: 496,
		            width: 500,
		            initContent: function() {
		                $('#renling').jqxWindow('focus');
		            }
		
		        });
		        $('#renling').jqxWindow('show');
		        logsource.url = '${ctx}/pages/alarm/getAlarmComments?alarmId=' + alarmIdList[0];
		        loadLogGridData(logsource);
		    }
		    if (isClickSearch == true && (alarmIdList.length > 0)) {
		        $("#windowtitle").text("清除");
		        $("#renling").jqxWindow({
		            showCollapseButton: true,
		            isModal: true,
		            resizable: false,
		            maxHeight: 496,
		            height: 496,
		            width: 500,
		            initContent: function() {
		                $('#renling').jqxWindow('focus');
		            }
		
		        });
		        $('#renling').jqxWindow('show');
		        logsource.url = '${ctx}/pages/alarm/getAlarmComments?alarmId=' + alarmIdList[0];
		        loadLogGridData(logsource);
		        return;
		    }
		});	
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
		//批量更新告警状态结束==========================================
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
			
		// tab 2 creage jqxgrid
	    //loadNestGrid
		$("#autoRefresh").click(function() {
		    setInterval(loadNestGrid(moId), 1000);
		});
		$("#manuRefresh").click(function() {
		    loadNestGrid(moId);
		});
		function loadNestGrid(moId) {
		    $.getJSON('${ctx}/performance/getGroups?moId=' + moId,
		    function(data) {
		        showGroupGrid(data);
		
		    });
		    function showGroupGrid(gridData) {
		        var data = new Array();
		
		        for (var i = 0; i < gridData.length; i++) {
		            var row = {};
		            row["moId"] = gridData[i][0];
		            row["groupName"] = gridData[i][1];
		            data[i] = row;
		        }
		        var source = {
		            localdata: data,
		            datatype: "array",
		            datafields: [{
		                name: 'moId'
		            },
		            {
		                name: 'groupName'
		            }
		
		            ]
		        };
		
		        var nestedGrids = new Array();
		        var innerGridData;
		        var data1 = new Array();
		
		        // create nested grid.
		        var initrowdetails = function(index, parentElement, gridElement, record) {
		
		            $.getJSON('${ctx}/performance/getInnderGrid?groupName=' + record.groupName,
		            function(data) {
		                innerGridData = data;
		                var datafieldsArray = new Array();
		                var datafields = innerGridData.fieldsNames;
		                for (var i = 0; i < datafields.length; i++) {
		                    var obj = new Object();
		                    obj.name = datafields[i];
		                    obj.type = 'string';
		                    datafieldsArray[i] = obj;
		                }
		                var datacolumns = innerGridData.columModle;
		                data1 = innerGridData.data;
		                var grid = $($(parentElement).children()[0]);
		                nestedGrids[index] = grid;
		                var orderssource = {
		                    localdata: data1,
		                    datatype: "array",
		                    datafields: datafieldsArray,
		                    async: false
		                };
		                if (grid != null) {
		                    grid.jqxGrid({
		                        width: 1200,
		                        height: 200,
		                        columnsresize: true,
		                        source: orderssource,
		                        columns: datacolumns
		                    });
		                }
		            });
		        };
		        //end
		        // creage jqxgrid
		        $("#jqxgrid5").jqxGrid({
		            width: 1300,
		            height: 500,
		            source: source,
		            rowdetails: true,
		            rowsheight: 35,
		
		            initrowdetails: initrowdetails,
		            rowdetailstemplate: {
		                rowdetails: "<div id='grid' style='margin: 10px;'></div>",
		                rowdetailsheight: 220,
		                rowdetailshidden: true
		            },
		            columns: [{
		                text: 'Group Name',
		                datafield: 'groupName',
		                width: '100%'
		            }]
		        });
		    }
		}
		//tab 2 end============================================================================
			
		//tab 3 KPI趋势查询=====================================================================
		var kpisource = {
            datatype: "json",
            datafields: [{
                name: 'kpi',
                type: 'string'
            },
            {
                name: 'displayKpi',
                type: 'string'
            }],
            id: 'kpi',
            //url: '${ctx}/config/dbkpiList?moId=' + moId 
            url: '${ctx}/config/dbkpiList?moId=' + agentId + '&typeId=' + typeId

        };
        var kpidataAdapter = new $.jqx.dataAdapter(kpisource);

        // initialize jqxGrid4
        $("#kpigrid").jqxGrid({
            width: '100%',
            height: '85%',
            source: kpidataAdapter,
            sortable: true,
            //editable: true,
            selectionmode: 'checkbox',
            altrows: true,
            columns: [{
                text: 'KPI',
                editable: 'available',
                datafield: 'kpi',
                width: '90%',
                hidden: true
            },
            {
                text: 'KPI',
                editable: 'available',
                datafield: 'displayKpi',
                width: '90%'
            }]
        });	
    });

    //biz system combox
    var url = "${ctx}/performance/getAllBiz";
    var bizsource = {
        datatype: "json",
        datafields: [{
            name: 'id'
        },
        {
            name: 'name'
        }],
        url: url,
        async: false
    };
    var dataAdapter = new $.jqx.dataAdapter(bizsource);
    $("#bizList").jqxDropDownList({
        selectedIndex: 0,
        source: dataAdapter,
        displayMember: "name",
        valueMember: "id",
        width: 120
        //,height: 25
    });

    //biz system change grid change
    var index = $("#bizList").jqxDropDownList('getSelectedIndex');
    var item = $("#bizList").jqxDropDownList('getItem', index);
    if(item.label=='其他') {
    	item.label='others';
    }
    $('#bizList').on('change',
    function(event) {
    	$("#ipaddress").val("");
        var args = event.args;
        if (args) {
        	
            var item1 = args.item;
            if(item1.label=='其他') {
	        	item1.label='others';
	        }
            dbsource.url = "${ctx}/performance/getDBPerformanceList?bizName=" + encodeURI(encodeURI(item1.label));
            loadGrid(dbsource);
            loadAlarmCount();
            
            var url='${ctx}/performance/getDBOverViewPerformanceList?bizName='+encodeURI(encodeURI(item1.label));
	        loadDbOverView(url);
            
            //getData(item1);
        }
    });

    $(".PerformanceNavBox").click(function() {
        var indexbiz = $("#bizList").jqxDropDownList('getSelectedIndex');
        var itembiz = $("#bizList").jqxDropDownList('getItem', indexbiz);
        if(itembiz.label=='其他') {
        	itembiz.label='others';
        }
        var typeId = $(this).attr("id");
        dbsource.url = "${ctx}/performance/getDBPerformanceList?typeId=" + typeId + "&bizName=" + encodeURI(encodeURI(itembiz.label));
        loadGrid(dbsource);
    });

    $("#queryIP").click(function() {
    	var indexQueryIp = $("#bizList").jqxDropDownList('getSelectedIndex');
        var itemQueryIp = $("#bizList").jqxDropDownList('getItem', indexQueryIp);
        if(itemQueryIp.label=='其他') {
        	itemQueryIp.label='others';
        }
		var ipaddress = $("#ipaddress").val();
		dbsource.url = '${ctx}/performance/getDBPerformanceList?queryIP='+ ipaddress+"&bizName="+encodeURI(encodeURI(itemQueryIp.label));
		loadGrid(dbsource);
		var url='${ctx}/performance/getDBOverViewPerformanceList?bizName='+encodeURI(encodeURI(itemQueryIp.label))+'&queryIP='+ipaddress;
		loadDbOverView(url);
		loadAlarmCount(ipaddress);
	});
    
    function loadAlarmCount(queryIp){
    	$("#oracle-level1").text(0);
    	$("#oracle-level2").text(0);
    	$("#oracle-level3").text(0);
    	$("#oracle-level4").text(0);
    	$("#oracle-level5").text(0);
    	
    	$("#sqlserver-level1").text(0);
    	$("#sqlserver-level2").text(0);
    	$("#sqlserver-level3").text(0);
    	$("#sqlserver-level4").text(0);
    	$("#sqlserver-level5").text(0);
    	
    	$("#db2-level1").text(0);
    	$("#db2-level2").text(0);
    	$("#db2-level3").text(0);
    	$("#db2-level4").text(0);
    	$("#db2-level5").text(0);
    	
    	$("#postgres-level1").text(0);
    	$("#postgres-level2").text(0);
    	$("#postgres-level3").text(0);
    	$("#postgres-level4").text(0);
    	$("#postgres-level5").text(0);
    	
    	$("#mysql-level1").text(0);
    	$("#mysql-level2").text(0);
    	$("#mysql-level3").text(0);
    	$("#mysql-level4").text(0);
    	$("#mysql-level5").text(0);
    	
    	oracletotal = 0;
    	sqlservertotal = 0;
    	db2total = 0;
    	postgrestotal= 0;
    	mysqltotal = 0;
    	$("#oracleCount").text(0);
        $("#sqlserverCount").text(0);
        $("#db2Count").text(0);
        $("#postgresCount").text(0);
        $("#mysqlCount").text(0);
    	
    	var indexbiz = $("#bizList").jqxDropDownList('getSelectedIndex');
        var itembiz = $("#bizList").jqxDropDownList('getItem', indexbiz);
        if(itembiz.label=='其他') {
        	itembiz.label='others';
        }
        var paramUrl = "${ctx}/performance/getAllLevelAlarmDataByType?typeId=DATABASE&bizName=" + encodeURI(encodeURI(itembiz.label));
        if(queryIp!=undefined){
    		paramUrl = paramUrl + "&queryIp=" + queryIp;
       	}
        
    	$.getJSON(paramUrl,
	    function(data) {
	        for (var i = 0; i < data.length; i++) {
	        	var dataType = data[i].type;
	        	var dataGrade = data[i].grade;
	        	var dataCount = data[i].count;
	            if (dataType == "DATABASE_ORACLE") {
	                if (dataGrade == 1) {
	                    $("#oracle-level1").text(dataCount);
	                    oracletotal += dataCount;
	                }
	                if (dataGrade == 2) {
	                    $("#oracle-level2").text(dataCount);
	                    oracletotal += dataCount;
	                }
	                if (dataGrade == 3) {
	                    $("#oracle-level3").text(dataCount);
	                    oracletotal += dataCount;
	                }
	                if (dataGrade == 4) {
	                    $("#oracle-level4").text(dataCount);
	                    oracletotal += dataCount;
	                }
	                if (dataGrade == 5) {
	                    $("#oracle-level5").text(dataCount);
	                    oracletotal += dataCount;
	                }
	            }
	            if (dataType == "DATABASE_Sqlserver") {
	                if (dataGrade == 1) {
	                    $("#sqlserver-level1").text(dataCount);
	                    sqlservertotal += dataCount;
	                }
	                if (dataGrade == 2) {
	                    $("#sqlserver-level2").text(dataCount);
	                    sqlservertotal += dataCount;
	                }
	                if (dataGrade == 3) {
	                    $("#sqlserver-level3").text(dataCount);
	                    sqlservertotal += dataCount;
	                }
	                if (dataGrade == 4) {
	                    $("#sqlserver-level4").text(dataCount);
	                    sqlservertotal += dataCount;
	                }
	                if (dataGrade == 5) {
	                    $("#sqlserver-level5").text(dataCount);
	                    sqlservertotal += dataCount;
	                }
	
	            }
	            if (dataType == "DATABASE_DB2") {
	                if (dataGrade == 1) {
	                    $("#db2-level1").text(dataCount);
	                    db2total += dataCount;
	                }
	                if (dataGrade == 2) {
	                    $("#db2-level2").text(dataCount);
	                    db2total += dataCount;
	                }
	                if (dataGrade == 3) {
	                    $("#db2-level3").text(dataCount);
	                    db2total += dataCount;
	                }
	                if (dataGrade == 4) {
	                    $("#db2-level4").text(dataCount);
	                    db2total += dataCount;
	                }
	                if (dataGrade == 5) {
	                    $("#db2-level5").text(dataCount);
	                    db2total += dataCount;
	                }
	
	            }
	            if (dataType == "DATABASE_POSTGRES") {
	                if (dataGrade == 1) {
	                    $("#postgres-level1").text(dataCount);
	                    postgrestotal += dataCount;
	                }
	                if (dataGrade == 2) {
	                    $("#postgres-level2").text(dataCount);
	                    postgrestotal += dataCount;
	                }
	                if (dataGrade == 3) {
	                    $("#postgres-level3").text(dataCount);
	                    postgrestotal += dataCount;
	                }
	                if (dataGrade == 4) {
	                    $("#postgres-level4").text(dataCount);
	                    postgrestotal += dataCount;
	                }
	                if (dataGrade == 5) {
	                    $("#postgres-level5").text(dataCount);
	                    postgrestotal += dataCount;
	                }
	
	            }
	            if (dataType == "DATABASE_MYSQL") {
	                if (dataGrade == 1) {
	                    $("#mysql-level1").text(dataCount);
	                    mysqltotal += dataCount;
	                }
	                if (dataGrade == 2) {
	                    $("#mysql-level2").text(dataCount);
	                    mysqltotal += dataCount;
	                }
	                if (dataGrade == 3) {
	                    $("#mysql-level3").text(dataCount);
	                    mysqltotal += dataCount;
	                }
	                if (dataGrade == 4) {
	                    $("#mysql-level4").text(dataCount);
	                    mysqltotal += dataCount;
	                }
	                if (dataGrade == 5) {
	                    $("#mysql-level5").text(dataCount);
	                    mysqltotal += dataCount;
	                }
	
	            }
	            
	            $("#oracleCount").text(oracletotal);
	            $("#sqlserverCount").text(sqlservertotal);
	            $("#db2Count").text(db2total);
	            $("#postgresCount").text(postgrestotal);
	            $("#mysqlCount").text(mysqltotal);
	        }
	
	    });
    }
    loadAlarmCount();
    
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

    var url = "${ctx}/performance/getDBPerformanceList?bizName=" + encodeURI(encodeURI(item.label));
    // prepare the data
    var dbsource = {
        datatype: "json",
        datafields: [{
            name: 'MO_ID',
            type: 'string'
        },
        {
            name: 'agentId',
            type: 'string'
        },
        {
            name: 'typeId',
            type: 'string'
        },
        {
            name: 'hostName',
            type: 'string'
        },
        {
            name: 'count',
            type: 'string'
        },
        {
            name: 'monitor',
            type: 'string'
        },
        {
            name: 'dbstatus',
            type: 'string'
        },
        {
            name: 'Alertlog',
            type: 'string'
        },
        {
            name: 'get',
            type: 'string'
        },
        {
            name: 'pin',
            type: 'string'
        },
        {
            name: 'mem',
            type: 'string'
        },
        {
            name: 'global',
            type: 'string'
        }],

        beforeprocessing: function(data) {
            if (data.length == 0) {
                dbsource.totalrecords = 0;
            } else {
                dbsource.totalrecords = data[data.length - 1].totalcount;
            }
        },

        sort: function() {
            $("#jqxgrid").jqxGrid('updatebounddata');
        },
        id: 'id',
        url: url
    };
    
    if ($.getUrlParam('biz') == "") {
        var items = $("#bizList").jqxDropDownList('getItems');
        for (var i = 0; i < items.length; i++) {
            if ("others" == items[i].label) {
                $("#bizList").jqxDropDownList('selectItem', items[i]);
            }
        }
        dbsource.url = "${ctx}/performance/getDBPerformanceList?bizName=others" + "&moId=" + $.getUrlParam('moId');
        loadGrid(dbsource);
        var url = '${ctx}/performance/getDBOverViewPerformanceList?bizName=others' + "&moId=" + $.getUrlParam('moId');
        loadDbOverView(url);

    } else if ($.getUrlParam('moId') != null) {
        var items = $("#bizList").jqxDropDownList('getItems');
        for (var i = 0; i < items.length; i++) {
            if (decodeURI($.getUrlParam('biz')) == items[i].label) {
                $("#bizList").jqxDropDownList('selectItem', items[i]);
            }
        }
        dbsource.url = "${ctx}/performance/getDBPerformanceList?bizName=" + $.getUrlParam('biz') + "&moId=" + $.getUrlParam('moId');
        loadGrid(dbsource);
        var url = '${ctx}/performance/getDBOverViewPerformanceList?bizName=' + $.getUrlParam('biz') + "&moId=" + $.getUrlParam('moId');
        loadDbOverView(url);
    }
    
    
    
    var dataAdapter = new $.jqx.dataAdapter(dbsource, {
        loadComplete: function(records) {}
    });
    $("#jqxgrid").jqxGrid({
        width: '100%',
        height: 450,

        source: dataAdapter,
        localization: getLocalization(),
        columnsresize: true,
        pageable: true,
        pagesize: 15,
        //filterable: true,
        sortable: true,
        //page
        virtualmode: true,
        rendergridrows: function() {
            return dataAdapter.records;
        },
        //selectionmode : 'checkbox',
        altrows: true,
        enabletooltips: true,
        columns: [{
            text: 'MO_ID',
            datafield: 'MO_ID',
            hidden: true,
            width: 20
        },
        {
            text: 'IP/主机名',
            datafield: 'hostName',
            width: 300
        },
        {
            text: '告警数',
            datafield: 'count',
            width: 80
        },
        {
            text: '监听状态',
            datafield: 'monitor',
            width: 80
        },
        {
            text: '数据库状态',
            datafield: 'dbstatus',
            width: 80
        },
        {
            text: '错误日志状态',
            datafield: 'Alertlog',
            minwidth: 80
        },
        {
            text: '库缓存get命中率',
            datafield: 'get',
            minwidth: 80
        },
        {
            text: '库缓存的pin命中率',
            datafield: 'pin',
            minwidth: 80
        },
        {
            text: '共享内存使用百分比',
            datafield: 'mem',
            minwidth: 80
        },
        {
            text: 'Global_Hit_Percent',
            datafield: 'global',
            minwidth: 80
        }]
    });

    function loadGrid(dbsource) {
    	dataAdapter.dataBind();
//        $('#jqxgrid').on('bindingcomplete',
//        function(event) {});
    }
});
function closeFun(){	
	$('#window').jqxWindow('close');
}
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
        $("#" + curList).fadeOut(0,
        function() {

            // Fade in new list on callback
            $("#" + listID).fadeIn();

            // Adjust outer wrapper to fit new list snuggly
            var newHeight = $("#" + listID).height();
            $("#all-list-wrap").animate({
                height: newHeight
            });

        });

    }

    // Don't behave like a regular link
    return false;
});

function randx() {
    return parseInt(Math.random() * 27);
};
function randy() {
    return parseInt(Math.random() * 30);
};
var x;
var y;
var count;
function createToolTip(data, id) {
    var content = "<b>" + data.hostName + "</b><br/><b>Monitor:</b>" + data.monitor + "<br /><b>DB_Status:</b>" + data.dbstatus + "<br /><b>Alertlog:</b>" + data.Alertlog + "<br /><b>Get:</b>" + data.get + "<br /><b>Pin:</b>" + data.pin + "<br /><b>Mem:</b>" + data.mem + "<br /><b>global:</b>" + data.global;
    $("#" + id).jqxTooltip({
        content: content,
        position: 'mouse',
        name: 'movieTooltip'
    });
}
function loadDbOverView(url){
	var index = $("#bizList").jqxDropDownList('getSelectedIndex');
    var item = $("#bizList").jqxDropDownList('getItem', index);
    if(item.label=='其他') {
    	item.label='others';
    }
	var paramUrl = "${ctx}/performance/getDBOverViewPerformanceList?bizName=" + encodeURI(encodeURI(item.label));;
	if(url!=undefined){
		paramUrl = url;
   	}else{
  		if($.getUrlParam('moId')==null){
  			paramUrl='${ctx}/performance/getDBOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label));
  		}else{
  			paramUrl='${ctx}/performance/getDBOverViewPerformanceList?bizName='+encodeURI(encodeURI(item.label))+'&moId='+$.getUrlParam('moId');
  		}
   	}
	$.getJSON(paramUrl,
    function(data) {
		$("#overviewtable").find("td").each(function(){ 
        	if($(this).attr("class")!=undefined){
       			$(this).removeClass();
			}
        }); 
        for (var j = 0; j < data.length; j++) {
            x = randx();
            y = randy();
            count = j;
            setTimeout(function() {
                createToolTip(data[count], 'Tooltip' + count--);
            },
            500);

            if ($("#overviewtable tr:eq(" + x + ") td:eq(" + y + ")").addClass("TD_red").attr("id") == undefined) {
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

        }
    });
}

$(document).ready(function() {
    
    loadDbOverView();
    $('.percentage').easyPieChart({
		animate: 1000
	});
    
    $('#jqxVerticalProgressBar1').jqxProgressBar({
        value: 0,
        orientation: 'vertical',
        layout: 'reverse',
        width: 40,
        height: 50,
        animationDuration: 3000
    });
    $('#jqxVerticalProgressBar2').jqxProgressBar({
        value: 0,
        orientation: 'vertical',
        layout: 'reverse',
        width: 40,
        height: 50,
        animationDuration: 3000
    });
});

function myFunction() {
    document.getElementById("ShowUserBoxMain").style.display = "none";
    document.body.onclick = hide;
    document.getElementById("ShowUserBoxclick").onclick = show1;
};

function show1(oEvent) {
    var UserBox1 = document.getElementById("ShowUserBoxMain");
    if (UserBox1.style.display == "block") {
        document.getElementById("ShowUserBoxMain").style.display = "none";
    } else {
        document.getElementById("ShowUserBoxMain").style.display = "block";
    }

    e = window.event || oEvent;
    if (e.stopPropagation) {
        e.stopPropagation();
    } else {
        e.cancelBubble = true;
    }
}
function hide() {
    document.getElementById("ShowUserBoxMain").style.display = "none";
}
</script>

<script src="${ctx}/pages/avmon_back_gif.js"></script>





