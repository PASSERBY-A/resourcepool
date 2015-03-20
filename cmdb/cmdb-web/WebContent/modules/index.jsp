<%@ page language="java" pageEncoding="UTF-8"%>

<%
String theme="-gray"; //"","-gray","-access"
request.getSession().setAttribute("theme",theme);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Avmon 1.0</title>
<style type="text/css">
#loading {
	position: absolute;
	left: 40%;
	top: 40%;
	padding: 2px;
	z-index: 20001;
	height: auto;
}

#loading a {
	color: #225588;
}

#loading .loading-indicator {
	background: white;
	color: #444;
	font: bold 13px tahoma, arial, helvetica;
	padding: 10px;
	margin: 0;
	height: auto;
}

#loading-msg {
	font: normal 10px arial, tahoma, sans-serif;
}
</style>
<script type="text/javascript">

	function showMainWindow(theme) {
		window.location.href="${pageContext.request.contextPath}/modules/main.jsp?theme="+theme;
	}

	function afterLoad() {
		try {
			if(navigator.userAgent.indexOf("MSIE")>0) { 
				var WshShell = new ActiveXObject('WScript.Shell')
			    WshShell.SendKeys('{F11}');	
		   	} 
		} catch(ex) {

		} finally{
			
		};
		Ext.Loader.setConfig({
			enabled : true
		});
		
		document.getElementById('loading-msg').innerHTML = '启动程序...';

		Ext.application({
			//autoCreateViewport: true,
			name : 'index',
			appFolder : 'app/..',
			autoCreateViewport : false,
			stores:[],
			launch : function() {

				var loginView = Ext.create('login.view.LoginPanel');
				
				Ext.create('Ext.container.Viewport', {
					layout : 'fit',
					renderTo: Ext.getBody(),
					border : false,
					items : [ loginView ]
				});
				//Ext.avmon.main.topView.getLayout().setActiveItem(Ext.avmon.main.loginView);
				Ext.get('loading').remove();
			}
		});

	}

</script>
</head>
<body onLoad="afterLoad();">
<div id="loading">
<div class="loading-indicator"><img
	src="../resources/images/loading32.gif" width="31" height="31"
	style="margin-right: 8px; float: left; vertical-align: top;" />
Avmon1.0平台监控系统 <br />
<span id="loading-msg">正在加载资源，请稍候...</span></div>
</div>
<div id="bd"><script type="text/javascript">
	document.getElementById('loading-msg').innerHTML = '加载UI组件...';
</script>
<script src="../resources/extjs411g/ext-all.js"></script> <script
	type="text/javascript">
	document.getElementById('loading-msg').innerHTML = '加载UI样式...';
</script>

<link rel="stylesheet" type="text/css"
	href="../resources/extjs411g/resources/css/ext-all-neptune.css" />
<link rel="stylesheet" type="text/css" href="../resources/style.css" />



</div>

</body>
</html>