<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>河南资源池管理平台</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">
<META http-equiv="X-UA-Compatible" content="IE=9">

<!-- CSS -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/login.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/Main.css">
<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico">
<!-- jqwidgets -->
<link rel="stylesheet"
	href="${ctx}/resources/js/jqwidgets/styles/jqx.base.css"
	type="text/css" />
<script type="text/javascript"
	src="${ctx}/resources/js/jquery-1.10.0.min.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jqwidgets/jqx-all.js"></script>
<style type="text/css">
/*update 20140918*/
/*弹出menu*/

#newWindow{
z-index:20002 !important;
}
</style>
</head>

<body class="Avmon-login" style="padding-top:0px;">
	<section class="main" id="mainBg"
		style="background-color: rgb(191, 242, 252);">
		<div class="main-inner"
			style="background-image: url(${ctx}/resources/images/AvmonLogin.png); background-position: 50% 0%; background-repeat: no-repeat;">
		</div>

		<!--登录框-->
		<form name="login_form" action="${ctx}/login" method="POST">
			<div id="loginBlock" class="login">
				<div class="login_title">登录</div>
				<div class="login_title_line"></div>
				<div class=error>
					<p>${errorMsg}</p>
				</div>
				<div class="loginFormAvmonIpt">
					<b class="ico ico-uid"></b> <input
						class="formIpt loginFormAvmonIpt-focus" name="userId" tabindex="1"
						type="text" placeholder="请输入帐号" maxlength="50" value=""
						autocomplete="on">
				</div>
				<!-- <div class=error><p>您的用户名或密码不正确</p></div> -->
				<div class="loginFormAvmonIpt">
					<b class="ico ico-Pwd"></b> <input
						class="formIpt loginFormAvmonIpt-focus" name="password"
						tabindex="2" type="password" placeholder="用户密码" maxlength="50"
						value="" autocomplete="on">
				</div>
				<div class="loginFormAvmonCheckInner">
					<label> <input tabindex="3" title="在本地保存密码" type="checkbox">在本地保存密码
					</label>
				</div>
				<div class="loginFormAvmonBtn">
					<input type="submit" class="Login_btn" value="登录"></input>
				</div>

				<div id="reg" class="loginFormAvmonLicense_BG">
					<b class="ico ico-arrow"></b><a href="#">License注册</a>
				</div>
			</div>
		</form>
	</section>
	<footer>
		<div class="footer-inner">
			<p>Copyright © 2014 Hewlett-Packard Development Company, L.P.</p>
		</div>
	</footer>
</body>
<!-- 注册window -->
<div
	style="width: 100%; height: 300px; display: none; margin-top: 50px;"
	id="mainDemoContainer">
	<div id="regcode">
		<div id="windowHeader">
			<span class="Icon16  R-icon_1_white"></span><span id="windowtitle"
				class="f14px white paddingL20">注册License</span>
		</div>
		<div>

			<div class="left">
				<div class="overlay-El-line">
					<div style="margin-bottom: 4px;">初始码</div>
					<div class="jqx-validator-error-element">
						<input id="initcode" placeholder="" style="width: 250px;"></input>
					</div>
				</div>
			</div>
			<div class="blank0"></div>
			<div class="left">
				<div class="overlay-El-line">
					<div style="margin-bottom: 4px;">注册码</div>
					<div>

						<input id="regcode1" placeholder="" value="" style="width: 250px;"></input>
					</div>
					<div class="AVmon-validator-label"></div>
				</div>
			</div>

			<div class="AvmonButtonArea">

				<div class="left">
					<span id="regconfirm" class="AvmonOverlayButton">注册</span><span
						id="cancel" class="AvmonOverlayButton">取消</span>
				</div>
				<div class="blank0"></div>

			</div>
		</div>
	</div>
</div>
<!-- end -->
<!-- Javascript -->
<script type="text/javascript">
	$(document).ready(function() {
		   $('#reg').bind('click', function() {
			 $.get('${ctx}/getRegCode', function(data) {
				$("#initcode").val(data.regCode);
			}); 
			$("#regcode").jqxWindow({
				showCollapseButton : true,
				isModal : true,
				resizable : false,
				width : 300,
				initContent : function() {
					$('#regcode').jqxWindow('focus');
				}
			});
			// show the popup window.
			$("#regcode").jqxWindow('show');
		});
		$('#cancel').bind('click', function() {
			$("#regcode").jqxWindow('hide');
		});
		$('#regconfirm').bind('click', function() {
			if($("#regcode1").val().length==0){
				$("#newWindow").remove();
			    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">请输入注册码!</span> </div></div>');
		        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
				return;
			}
			
			 $.post('${ctx}/registLicense',
				      {
				      license:$("#regcode1").val()
				      },
				      function (data) 
				      {
				    	    $("#newWindow").remove();
						    $(document.body).append('<div id="newWindow" style="z-index:20001"><div>提示</div><div><span style="color:red">注册成功!</span> </div></div>');
					        $('#newWindow').jqxWindow({ isModal : true,height: 100, width: 200});
					  }
				    ); 
			/*  $.get('${ctx}/registLicense?license='+$("#regcode1").val(), function(data) {
				// $("#initcode").val(data.regCode);
			});  */
			$("#regcode").jqxWindow('hide');
		});
	});
</script>
</html>
