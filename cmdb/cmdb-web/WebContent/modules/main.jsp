<%@ page language="java" pageEncoding="UTF-8"%>

<%

//应用显示主题
String theme=""; //"","-gray","-access"
theme=request.getParameter("theme");
if(theme==null){
    theme="";
}
request.getSession().setAttribute("theme",theme);

//获取当前登录用户
String userId="";
String userName="";
boolean hideMenu1=true;  //告警监控
boolean hideMenu2=true;  //实时性能
boolean hideMenu3=true;  //查询与报表
boolean hideMenu4=true;  //配置管理
boolean hideMenu5=true;  //部署管理
boolean hideMenu6=false;  //系统管理
boolean hideMenu7=true;  //性能分析
boolean hideMenu8=true;   //实时状态
boolean hideMenu9=true;   //网络巡检配置
//add by mark start
boolean hideMenu10=true;   //模型编辑
//add by ted. other button hidden
boolean hideMenu11=true;   //建模视图



boolean hideMenu12=false;   //资源管理
boolean hideMenu13=false;   //视图编辑
boolean hideMenu14=false;   //资源管理
boolean hideMenu15=false;   //关系编辑
boolean hideMenu16=false;   //关系实例
boolean hideMenu17=false;   //查询管理

java.util.Map map=(java.util.Map)request.getSession().getAttribute("CURRENT_USER");
if(map!=null){
    userId=(String)map.get("USER_ID");
    userName=(String)map.get("REAL_NAME");
  	
    //获取各主菜单的权限
  	String modules=(String)map.get("modules");

    if(modules==null){
        System.out.println("empty module list!");
        modules="";
    }
    
  	//实时状态
//    if(modules.indexOf(",7,")>=0){
//        hideMenu8=false;
//    }
  	
  	//部署管理-> 建模视图
   /*  if(modules.indexOf(",4,")>=0){
    	//hideMenu11=false;
    } */

  	//配置管理
//    if(modules.indexOf(",3,")>=0){
//        hideMenu4=false;
//   }

}
else{
    System.out.println("user not login!");
}

//获取当前显示的module
String module=(String)request.getParameter("module");

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


var AVMON_GLOBAL_HEADER_IMAGE="main/images/header.png";
var mainFrame_labMenu1_hidden=<%=hideMenu1%>;
var mainFrame_labMenu2_hidden=<%=hideMenu2%>;
var mainFrame_labMenu3_hidden=<%=hideMenu3%>;
var mainFrame_labMenu4_hidden=<%=hideMenu4%>;
var mainFrame_labMenu5_hidden=<%=hideMenu5%>;
var mainFrame_labMenu6_hidden=<%=hideMenu6%>;
var mainFrame_labMenu7_hidden=<%=hideMenu7%>;
var mainFrame_labMenu8_hidden=<%=hideMenu8%>;
var mainFrame_labMenu9_hidden=<%=hideMenu9%>;
//add by mark start
var mainFrame_labMenu10_hidden=<%=hideMenu10%>;
var mainFrame_labMenu11_hidden=<%=hideMenu11%>;
var mainFrame_labMenu12_hidden=<%=hideMenu12%>;
var mainFrame_labMenu13_hidden=<%=hideMenu13%>;
var mainFrame_labMenu14_hidden=<%=hideMenu14%>;
var mainFrame_labMenu15_hidden=<%=hideMenu15%>;
var mainFrame_labMenu16_hidden=<%=hideMenu16%>;
var mainFrame_labMenu17_hidden=<%=hideMenu17%>;
//add by mark end

function loadController(name){
	var app=Ext.avmon.application;
	if (!app.controllers.containsKey(name)) {
		//console.log("creating controller "+name);
		controller = Ext.create(
				name, {
					application : app,
					id : name
				});
		//console.log("create controll ok.");
		app.controllers.add(controller);
		controller.onLaunch();
		controller.init();
		return controller;
	}
}

	function showMainWindow() {
		var c=loadController("main.controller.MainFrame");
    	//console.log("create main application done.");
    	Ext.avmon.main.frame=Ext.create("main.view.MainFrame");
		//c.init2();
        Ext.avmon.main.topView.removeAll();
        Ext.avmon.main.topView.add(Ext.avmon.main.frame);
       // */
	}
	
	function createPerformancePanel() {
		var c=loadController("performance.controller.MenuTree");
    	return Ext.create("performance.view.PerformancePanel");
	}

	function afterLoad() {
		Ext.Loader.setConfig({
			enabled : true
		});

		if (!Ext.avmon) {
			Ext.avmon={};
		}
		Ext.apply(Ext.avmon,{
				main : {},
				config : {},
				alarm : {},
				performance : {},
				system : {},
				report : {},
				loginUserName : '<%=userName%>',
				loginUserId : '<%=userId%>'
			}
		);

		/*定义加载方法*/
		Ext.require([ 'Ext.app.Application', 'Ext.app.Controller' ]);
		/*
		Ext.app.Controller.implement({
			//MVC 加载模型
			loadModel : function() {
			},
			//MVC 加载视图
			loadView : function() {
			},
			getApplication : function() {
				return this.application;
			}
		});
		*/
		Ext.app.Application
				.implement({
					//MVC 加载控制器 param {String/Array} controllers
					loadModule : function(controllers) {
						var me = this;
						console.dir(controllers);
						var controllers = Ext.Array.from(controllers), ln = controllers.length, i, controller;
						for (i = 0; i < ln; i++) {
							var name = controllers[i];
							if (!this.controllers.containsKey(name)) {
								console.log("creating controller "+name);
								controller = Ext.create(
										name, {
											application : this,
											id : name
										});
								console.log("create controll ok.");
								this.controllers.add(controller);
								// 优先加载模型 
								//controller.loadModel();
								//controller.init(this);
								//controller.onLaunch(this);
								//动态构建视图 & 绑定模型数据
								//controller.loadView();
								return controller;

							}
						}
					}
				});

		document.getElementById('loading-msg').innerHTML = '加载主界面...';

		document.getElementById('loading-msg').innerHTML = '启动程序...';

		Ext.avmon.application=Ext.application({
			//autoCreateViewport: true,
			name : 'index',
			appFolder : 'app/..',
			autoCreateViewport : false,
			stores:[],
			launch : function() {
				
				Ext.avmon.application=this;
				
				var mainFrame=null;
				
				
				<% if("perf".equals(module)){ %>
					loadController("performance.controller.MenuTree");
	    			mainFrame=Ext.create("performance.view.PerformancePanel");					
				<% } else if("report".equals(module)){ %>
					loadController("main.controller.MainFrame");
					mainFrame=Ext.create("main.view.MainFrame")
					//if(Ext.avmon.mainMenuClick){
					//	Ext.avmon.mainMenuClick(null,{id:"labelPollingCenter"});
					//}
					Ext.getCmp("topPanel").hide();
					mainFrame=Ext.getCmp("tabReport");
				<% } else { %>
					loadController("main.controller.MainFrame");
					mainFrame=Ext.create("main.view.MainFrame")
				<% } %>

				Ext.avmon.main.topView = Ext.create('Ext.container.Viewport', {
					layout : 'fit',
					//renderTo: Ext.getBody(),
					border : false,
					items : [ mainFrame ]
				});
				//Ext.avmon.main.topView.getLayout().setActiveItem(Ext.avmon.main.loginView);
				Ext.get('loading').remove();
			}
		});

	}
	
    function enterAlarm(moId){
    	//Ext.getCmp('tabAlarm').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../pages/alarm/index.jsp?mo='+moId+'&t='+(new Date()).valueOf()+'"></iframe>');
    	//Ext.getCmp('tabPanel').setActiveTab(Ext.getCmp('tabAlarm'));
    	//alert(moId);
    	Ext.avmon.subMenuClick(null,{id:"tabMenu11"});
    }
    function isShow(moduleName){
    	//console.log("check isShow "+moduleName+" = "+Ext.avmon.currentActiveModule);
		return Ext.avmon.currentActiveModule==moduleName;
	}
    
</script>
</head>
<body onLoad="afterLoad();">
<div id="loading">
<div class="loading-indicator"><img
	src="../resources/images/loading32.gif" width="31" height="31"
	style="margin-right: 8px; float: left; vertical-align: top;" />
Avmon1.0平台监控系统 <br />
<span id="loading-msg">正在加载UI组建，请稍候...</span></div>
</div>
<div id="bd"><script type="text/javascript">
	document.getElementById('loading-msg').innerHTML = '加载UI组件...';
</script>
<script src="../resources/extjs411g/ext-all.js"></script> <script
	type="text/javascript">
	document.getElementById('loading-msg').innerHTML = '加载UI样式...';
</script>

<script src="performance/plugins.js?t=<%=com.hp.avmon.utils.Utils.debugRandomId() %>"></script>


<link rel="stylesheet" type="text/css"
	href="../resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css" />
<link rel="stylesheet" type="text/css" href="../resources/style.css" />

<link rel="stylesheet" type="text/css" href="main/style.css" />


</div>

</body>
</html>