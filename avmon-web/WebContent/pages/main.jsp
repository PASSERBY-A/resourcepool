<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.Map"%>
<%    
response.setHeader("Pragma","No-cache");    
response.setHeader("Cache-Control","no-cache");    
response.setDateHeader("Expires", -10);   

response.addHeader("Cache-Control", "no-store, must-revalidate"); 
response.addHeader("Expires", "Thu, 01 Jan 1970 00:00:01 GMT");

/* String  webURL="http://localhost:8080/avmon-web/";
String  ziyuanchiURL = "http://localhost:9999/resourcepool-web";  */ 


/* String  webURL="http://16.159.216.146:8888/web";
String  ziyuanchiURL = "http://16.159.216.146:8888/resourcepool-web"; */ 

String  webURL="http://localhost:8080/avmon-web";
String  ziyuanchiURL = "http://localhost:9999/resourcepool-web";


%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />


<%
	Map menuMap=(Map)request.getSession().getAttribute("menuMap");
	Map map = (Map)request.getSession().getAttribute("CURRENT_USER");
	String isadmin = (String)map.get("isadmin");
	String deptId = map.get("dept_id").toString();
	
%>


<%
	if(session.getAttribute("webURL")==null)
	{
		session.setAttribute("webURL",webURL);
		response.sendRedirect(ziyuanchiURL+"/weblogin.jsp?web="+webURL);
	}
%>

<html>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>河南移动资源池管理平台</title>
<META HTTP-EQUIV="pragma" CONTENT="no-cache"> 
<META HTTP-EQUIV="Cache-Control" CONTENT="no-store, must-revalidate"> 
<META HTTP-EQUIV="expires" CONTENT="Wed, 26 Feb 1997 08:21:57 GMT"> 
<META HTTP-EQUIV="expires" CONTENT="0"> 
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<!-- CSS -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/global.css">




<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/global.css">
	<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/jquery.toastmessage.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/bootmetro.css">
<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico">
<link rel="stylesheet"
	href="${ctx}/resources/js/jqwidgets/styles/jqx.base.css"
	type="text/css" />

<!-- Le fav and touch icons -->
<link rel="shortcut icon" href="ico/favicon.ico">
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="ico/apple-touch-icon-57-precomposed.png">
<style type="text/css">
/*update 20140918*/
/*弹出menu*/

#newWindow{
z-index:20002 !important;
}
</style>
</head>

<body>
	<div id="wrap">
		<!-- Header
      ================================================== -->
		<div id="nav-bar" class="header">
			<div id="header-container">
				<div class="LOGO"></div>
				<div class="Profile" id="ShowUserBoxclick" style="cursor: pointer">
					<div class="ProfileIcon"></div>
					<div class="ProfileName ">
						${userName}</div>
					<div class="ProfileArrow"></div>
					<div class="blank0"></div>
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
		<div class="metro panorama">
			<div class="panorama-sections">
			
			<!-- menu begin -->
		<%
				Boolean b = Boolean.valueOf(isadmin);
				if(b)
				{
					
					%>
											<div class="panorama-section">
					<div style="margin-top: 15px;height: 314px;padding-top: 1px;width: 645px;background-image:url('${ctx}/resources/images/ziyuanchi650X454.png');margin-left: 5px;" 
							onmousedown="javascript:window.location.href='<%=ziyuanchiURL%>?webURL=<%=webURL%>'">  
									 <div id="tu1" style="background-color: rgba(0, 0, 0, 0.7);min-width: 162px; height: 144px; max-width: 162px; margin-top: 170px;"></div> 
									 <div id="tu2" style="background-color: rgba(0, 0, 0, 0.7);min-width: 162px; height: 144px; max-width: 162px; margin-top: -144px;margin-left: 161px;"></div> 
		 							 <div id="tu3" style="background-color: rgba(0, 0, 0, 0.7);min-width: 162px; height: 144px; max-width: 162px;margin-top: -144px;margin-left: 323px;"></div>
									 <div id="tu4" style="background-color: rgba(0, 0, 0, 0.7);min-width: 160px; height: 144px; max-width: 158px;margin-top: -144px;margin-left: 485px;"></div>  
					</div> 
		 			<div >
						<a class="tile square image bg-color-redlight" href="#" style="width: 329px;margin-top: 10px;">
						<img src="${ctx}/resources/images/RLGL150X150.png">
							<div class="textover-wrapper"> <div class="text2"> 配置管理 </div></div>
						</a>
						<a class="tile square image bg-color-brown" href="#" style="width:305px;margin-top: 10px"> <img src="${ctx}/resources/images/App150X150.png" >
							<div class="textover-wrapper bg-color-brown">
								<div class="text2">流程</div>
							</div>
						</a> 
					</div>  
				</div> 
				
				
				<div class="panorama-section">
                        <h2></h2>
                        <div class="tile-column-span-2">
                           <a class="tile wide imagetext wideimage bg-color-purple" href="${ctx}/pages/alarm/alermOverView">
                              <img src="${ctx}/resources/images/AlarmCenter.png">
                              <div class="textover-wrapper transparent">
                                 <div class="text2 w45px" style="margin-top: 6px;"><B class="circle bg-color-red"></B><p id="grade5">0</p></div>
                                 <div class="text2 w45px" style="margin-top: 6px;"><B class="circle bg-color-orange"></B><p id="grade4">0</p></div>
                                 <div class="text2 w45px" style="margin-top: 6px;"><B class="circle bg-color-yellow"></B><p id="grade3">0</p></div>
                                 <div class="text2 w45px" style="margin-top: 6px;"><B class="circle bg-color-blue"></B><p id="grade2">0</p></div>
                                 <div class="text2 w45px" style="margin-top: 6px;"><B class="circle bg-color-green"></B><p id="grade1">0</p></div>
                               </div>
                           </a>
                          <a class="tile square image bg-color-BlueGreen" href="${ctx}/discovery/deviceScan">
                          <img src="${ctx}/resources/images/zdfx150X150.png">
                              <div class="textover-wrapper bg-color-BlueGreen">
                                 <div class="text2">自动发现</div>
                              </div>
                           </a>
                          <a class="tile square image bg-color-redlight" href="${ctx}/discovery/mibmgr">
                          <img src="${ctx}/resources/images/NetworkPhoto.png">
                              <div class="textover-wrapper">
                              <img src="${ctx}/resources/images/Network24X24.png">
                                 <div class="text2">简单网络管理</div>
                              </div>
                           </a>
                             <a class="tile square image bg-color-brown" href="${ctx}/deploy/index">
                          <img src="${ctx}/resources/images/bs150X150.png">
                              <div class="textover-wrapper bg-color-brown">
                              <img src="${ctx}/resources/images/bs24.png">
                                 <div class="text2">Agent管理</div>
                              </div>
                           </a>     
                          <a class="tile square image bg-color-Green" href="${ctx}/config/main">
                          <img src="${ctx}/resources/images/pzgl150X150.png">
                              <div class="textover-wrapper bg-color-Green">
                                 <div class="text2">监控配置</div>
                              </div>
                           </a>
                        </div>
                       
                        <div class="tile-column-span-2">
                                           
                           <a class="tile wide imagetext wideimage bg-color-redlight" href="${ctx}/performance/overview">
                              <img src="${ctx}/resources/images/DataCenter2.png">
                              <div class="textover-wrapper bg-color-brown">
                              <img src="${ctx}/resources/images/storage24X24.png">
                                 <div class="text2">性能管理</div>
                               </div>
                           </a>
                         <!-- add here -->        
                         <a class="tile square image bg-color-bluelight" href="${ctx}/report/index">
                          <img src="${ctx}/resources/images/List150X150.png">
                              <div class="textover-wrapper bg-color-bluelight">                           
                                 <div class="text2">报表查询</div>
                              </div>
                           </a> 
                             
                          <a class="tile square image bg-color-bluelight" href="${ctx}/kpiAnalysis/index">
                          <img src="${ctx}/resources/images/performanceCenter2.png">
                              <div class="textover-wrapper bg-color-bluelight">
                              <img src="${ctx}/resources/images/ALL-ICON-white.png">
                                 <div class="text2">性能分析</div>
                              </div>
                           </a>
                          <a class="tile square image bg-color-orange" href="${ctx}/equipmentCenter/index">
                          <img src="${ctx}/resources/images/Resource150X150.png">
                              <div class="textover-wrapper bg-color-orange">
                                 <div class="text2">资源管理</div>
                              </div>
                           </a>
                            
                          <a class="tile square image bg-color-BlueGreen" href="${ctx}/system/main?type=system">
                          <img src="${ctx}/resources/images/SyS2150X150.png">
                              <div class="textover-wrapper bg-color-BlueGreen">
                             <img src="${ctx}/resources/images/System-ICON-white.png"> 
                                 <div class="text2">系统管理</div>
                              </div>
                           </a>  
                         
                         <!-- add end -->    
                       </div>                        
                     </div>  
						
					<%
				}
				else
				{
					%>
						
				<div class="panorama-section">
                        <h2></h2>
                        <div class="tile-column-span-4">
                           <a class="tile wide imagetext wideimage bg-color-purple" style="width:630px; height:310px;" 
                           		onmousedown="javascript:window.location.href='<%=ziyuanchiURL%>?webURL=<%=webURL%>'">
                              <img src="${ctx}/resources/images/ziyuanchi650X454.png">
                              <div class="textover-wrapper transparent" style="width:630px; height:50px; padding:30px 20px;">
                                 <div class="left marginR40" style="width:170px">
                                 <div class="circle bg-color-green left" style="margin-top:4px;"></div>
                                 <div class="left f18px" style="padding-left:16px;">正在运行的实例</div>
                                 <div class="blank10"></div>
                                 <div class="f28px" style="padding-left:16px;" id="vmrunningcount">0</div>
                                 </div>
                                 <div class="left marginR40" style="width:170px" >
                                 <div class="circle bg-color-red left" style="margin-top:4px;" ></div>
                                 <div class="left f18px" style=" padding-left:16px;">已停止的实例</div>
                                 <div class="blank10"></div>
                                 <div class="f28px" style="padding-left:16px;" id="vmstopedcount">0</div>
                                 </div>
                                 <div class="left marginR40" style="width:170px">
                                 <div class="circle bg-color-blue left" style="margin-top:4px;"></div>
                                 <div class="left f18px" style=" padding-left:16px;">总实例</div>
                                 <div class="blank10"></div>
                                 <div class="f28px" style="padding-left:16px;" id="xunjicount">0</div>
                                 </div>
                               </div>
                           </a>
                          <div class="tile-column-span-2"  style="width:320px">
                           <a class="tile wide image bg-color-redlight" href="#">
                              <img src="${ctx}/resources/images/RLGL150X150.png">
                              <div class="textover-wrapper bg-color-redlight">
                                 <div class="text2">配置管理</div>
                               </div>
                           </a>
                         </div> 
                          <div class="tile-column-span-2" style="width:320px">
                           <a class="tile wide image wideimage bg-color-brown" href="#">
                              <img src="${ctx}/resources/images/App150X150.png">
                              <div class="textover-wrapper bg-color-brown">
                                 <div class="text2">流程</div>
                               </div>
                           </a>
                         </div> 
                        </div>
 </div>  
									


					<%					
				}
		
				 
		
		%>

               
						
						
   
					
					
	
			
			<!-- end --> 
			
   
   
   
   
   <%-- --%>
			</div>
		</div>
		<a id="panorama-scroll-prev" href="#"></a> <a
			id="panorama-scroll-next" href="#"></a>
		<div id="panorama-scroll-prev-bkg"></div>
		<div id="panorama-scroll-next-bkg"></div>


	</div>

    
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-1.9.0/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery-1.10.0.min.js"></script>
		
	<script type="text/javascript"
		src="${ctx}/resources/js/min/bootstrap.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/bootmetro-panorama.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/bootmetro-pivot.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.toastmessage.js"></script>	

	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.mousewheel.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.touchSwipe.min.js"></script>
	<script type="text/javascript"
	src="${ctx}/resources/js/jqwidgets/jqx-all.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/common.js"></script>

	<%--  <script type="text/javascript" src="${ctx}/resources/js/holder.js"></script> --%>
	<script src="${ctx}/resources/js/modernizr-2.6.2.min.js"></script>


	<script type="text/javascript">
		$('.panorama').panorama({
			showscrollbuttons : true,
			keyboard : true,
			parallax : true
		});
		$('#pivot').pivot();
	</script>

	<script type="text/javascript">
	
	function alarmCenter(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		
	}
	function discover(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	function network(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	function agent(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	function config(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	function performance(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	function performanceAnalyze(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	function resource(data){
		
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	
	function report(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	
	function agentless(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	
	function system(data){
		if(data==1){
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">未注册该模块License,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
		else{
			$("#newWindow").remove();
		    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">没有权限访问该模块,请联系系统管理员!</span> </div></div>');
	        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
		}
	}
	 var account='${userId}';
	 var ctx='${ctx}';
	// $().toastmessage('showSuccessToast', "添加成功1111");
	 $.getJSON("${ctx}/pages/alarm/getAllAlertGrade",function(data){
	      for(var i=0;i<data.gradeData.length;i++){
	    	  if(data.gradeData[i].grade==1){
	    		  $("#grade1").text(data.gradeData[i].count);  
	    	  }
	    	  else if(data.gradeData[i].grade==2){
	    		  $("#grade2").text(data.gradeData[i].count);  
	    	  }
	    	  else if(data.gradeData[i].grade==3){
	    		  $("#grade3").text(data.gradeData[i].count);  
	    	  }
	    	  else if(data.gradeData[i].grade==4){
	    		  $("#grade4").text(data.gradeData[i].count);  
	    	  }
	    	  else if(data.gradeData[i].grade==5){
	    		  $("#grade5").text(data.gradeData[i].count);  
	    	  }
	    }  
	   });
	   
		function show(oEvent) {
			var UserBox = document.getElementById("ShowUserBoxMain");
			//alert(obj.style.display);
			if (UserBox.style.display == "block") {
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

		window.onload = function() {

			document.getElementById("ShowUserBoxMain").style.display = "none";
			document.body.onclick = hide;
			document.getElementById("ShowUserBoxclick").onclick = show;

		};
	</script>


	<script type="text/javascript">
	
	
	var isadmin=<%=isadmin%>;
	
	var contextPath = '${pageContext.request.contextPath}';
	
	$(document).ready(function() {
		
			var u = '<%=ziyuanchiURL%>/api.action?command=computeresource&response=json&listAll=true&cmsz=yes&jsoncallback=callback';
			
			<%
				if(b)
				{
					
					%>
						$.ajax({
							type : 'GET',
							url : u,
							dataType : 'jsonp',
							async:false,
							jsonp : "callback",
							jsonpCallback:"callback", 
							success : function(data) {
								
								
								
								//alert(data.listcapacityresponse.capacity[0].type);
								//1,0,2,6
								var capacityObj = data.listcapacityresponse.capacity;
								 
								if(isadmin)
								{
										showPie(capacityObj);
										//$('#vmcount').hide();
								}
								else
								{
									$('#tu1').hide();
									$('#tu2').hide();
									$('#tu3').hide();
									$('#tu4').hide();
									
									//alert("user_"+<%=deptId%>);
									//alert(data)
								 
								}
									
							},
							error : function() {
								alert('获取图表信息失败!');
							}
							
						});  
					
					<%
					
					
				}
				else
				{
					
					%>
			 		var u1 = '<%=ziyuanchiURL%>/api.action?command=accountresource&response=json&listAll=true&cmsz=yes&jsoncallback=callback';
					
					$.ajax({
						type : 'GET',
						url : u1,
						dataType : 'jsonp',
						async:false,
						jsonp : "callback",
						jsonpCallback:"callback",
						success : function(data) {
							if(!isadmin)
							{
								var str = data.listcapacityresponse.capacity;
								//var capacityObj = data.listcapacityresponse.capacity;
								//alert(capacityObj.length);
								var obj = eval(str);
								//alert(obj[0].vmrunningcount);
								$('#vmrunningcount').html(obj[0].vmrunningcount);
								$('#vmstopedcount').html(obj[0].vmstopedcount);
								$('#xunjicount').html(obj[0].xunjicount);
							}
						},
						error : function() {
								alert('获取图表信息失败!');
							}
					});  
					
					<%
					
				}
			
			
			
			%>
			
			
			
	});
	
	</script>
	
 
 
 
 	
	

<script src="${pageContext.request.contextPath}/resources/js/highcharts/highcharts.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/highcharts/highcharts-theme.js"></script>
<script src="${pageContext.request.contextPath}/resources/js/highcharts/highcharts-pie.js"></script>	 
	 
	 

</body>
</html>
