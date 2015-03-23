<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html> 
<HTML><HEAD><TITLE>iloHostDashboard</TITLE>
<META content="text/html; charset=utf-8" http-equiv="Content-Type">
<STYLE>
.fan{float:left;padding-left:25px;padding-top:5px}
.board{float:left;padding-left:15px;padding-top:10px}
.board1{float:left;padding-left:15px;padding-top:10px}
.netcard{float:left;padding-left:10px;padding-top:10px}
.mem{float:left;padding-left:5px;padding-top:16px}
.mometer{float:left;padding-left:25px}
.mometer1{float:left;padding-left:0px}
.red-mometer{position:relative;height:130px;width:40px;background-image:url('./image/red01.png');}
.red-mometer span{position:absolute;display:block;bottom:0px;width:16px;}
.red-hgheader{height:15px;line-height:15px;color:#FF0000;font-size:12px;font-family:Arial, Helvetica, sans-serif;border-bottom:#f00 1px solid;left:0px;bottom:25px;position:relative;}
.red-hg{height:0px; font-size:0px;background-color:#FF0000;width:6px;left:17px;bottom:23px;position:relative;}
.hot{height:100px;width:47px; position:absolute; top:0; left:0;}
/****add by mark start ***/
.blue-memory{position:relative;height:130px;width:40px;background-image:url('./image/bluemem.png')}
/****add by mark end ***/
.blue-mometer{position:relative;height:130px;width:40px;background-image:url('./image/blue01.png')}
.blue-mometer span{position:absolute;display:block;bottom:0px;width:20px;}
.blue-hgheader{height:15px;line-height:15px;color:#0088ff;font-size:12px;font-family:Arial, Helvetica, sans-serif;border-bottom:#0088ff 1px solid;left:0px;bottom:25px;position:relative;}
.blue-hg{height:0px; font-size:0px;background-color:#0088ff;width:6px;left:17px;bottom:23px;position:relative;}
.left-point{height:7px;width:9px;left:5px;bottom:28px;position:relative;background-image:url('./image/leftPoint.png');float:left;}
.right-point{height:7px;width:9px;left:24px;bottom:28px;position:relative;background-image:url('./image/rightPoint.png');}
/* .power-detail{height:107px;width:164px;background-image:url('./image/power.png');} 
.power-detail font{bottom:-60px;position:relative;font-weight:bolder;padding-left:56px;font-size:20px;}
.power-detail div{bottom:10px;position:relative;padding-left:-20px;}
*/
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
</STYLE>
<script>
var __sto = setTimeout;  
　　window.setTimeout = function(callback,timeout,param)  
　　{  
　　var args = Array.prototype.slice.call(arguments,2);  
　　var _cb = function()  
　　{  
　　callback.apply(null,args);  
　　}  
　　__sto(_cb,timeout);  
　　}  

</script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all.gzjs"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<META name="GENERATOR" content="MSHTML 9.00.8112.16447"></HEAD>
<BODY onLoad="afterLoad();">
<input name="mo" id="mo" value='<%=request.getParameter("mo")%>' type="hidden">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
<link rel="stylesheet" type="text/css" href="../../../resources/style.css"/>

<SCRIPT>
Ext.iloHost = {};
function afterLoad(){
	Ext.iloHost.mo = document.getElementById("mo").value;
	
	setTimeout(function(){
		Ext.get('loading').remove();
	},1000);
	//setInterval(reloadData, 10000); delete by mark for test
}

function reloadData(){
	//alert("11111");
	
	var iloHostViewport = Ext.getCmp("iloHostViewport");
	
	var powerStore = iloHostViewport.down("#powerList").store;
	powerStore.proxy.extraParams={mo:Ext.iloHost.mo};
	powerStore.load();
	
	var cpuStore = iloHostViewport.down("#cpuList").store;
	cpuStore.proxy.extraParams={mo:Ext.iloHost.mo};
	cpuStore.load();
	
	var memStore = iloHostViewport.down("#memList").store;
	memStore.proxy.extraParams={mo:Ext.iloHost.mo};
	memStore.load();
	
	var diskStore = iloHostViewport.down("#diskList").store;
	diskStore.proxy.extraParams={mo:Ext.iloHost.mo};
	diskStore.load();
	
	var netcardStore = iloHostViewport.down("#netcardList").store;
	netcardStore.proxy.extraParams={mo:Ext.iloHost.mo};
	netcardStore.load();
	
	var fanStore = iloHostViewport.down("#fanList").store;
	fanStore.proxy.extraParams={mo:Ext.iloHost.mo};
	fanStore.load();
	
	var boardStore = iloHostViewport.down("#boardList").store;
	boardStore.proxy.extraParams={mo:Ext.iloHost.mo};
	boardStore.load();
	
	var caseStore = iloHostViewport.down("#caseList").store;
	caseStore.proxy.extraParams={mo:Ext.iloHost.mo};
	caseStore.load();
	
	var biosStore = iloHostViewport.down("#biosList").store;
	biosStore.proxy.extraParams={mo:Ext.iloHost.mo};
	biosStore.load();
	
	var cpuCommStore = iloHostViewport.down("#cpuCommList").store;
	cpuCommStore.proxy.extraParams={mo:Ext.iloHost.mo};
	cpuCommStore.load();
	
	var powerCommStore = iloHostViewport.down("#powerCommList").store;
	powerCommStore.proxy.extraParams={mo:Ext.iloHost.mo};
	powerCommStore.load();
}

function reloadModule(moId){
	Ext.iloHost.mo = moId;
	//重新加载dashboard数据
	
	reloadData();
}
</SCRIPT>
<script type="text/javascript" src="app.js"></script>

		<div id="loading">
		<div class="loading-indicator"><img
			src="${pageContext.request.contextPath}/resources/images/loading32.gif" width="31" height="31"
			style="margin-right: 8px; float: left; vertical-align: top;" />
			<spring:message code="loading"/><br />
			<span id="loading-msg"><spring:message code="loadingFunctionalComponents"/></span></div>
		</div>
</BODY></HTML>
