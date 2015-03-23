<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE html> 
<HTML><HEAD><TITLE>iloHostDashboard</TITLE>
<META content="text/html; charset=utf-8" http-equiv="Content-Type">
<STYLE>
.power{float:left;padding-left:25px;padding-top:2px;}
.fan{float:left;padding-left:25px;padding-top:5px}
.board{float:left;padding-left:55px;padding-top:10px}
.board1{float:left;padding-left:15px;padding-top:10px}
.netcard{float:left;padding-left:10px;padding-top:10px}
.mem{float:left;padding-left:5px;padding-top:16px}
.mometer{float:left;padding-left:25px}
.mometer1{padding-left:0px}
.red-mometer{position:relative;height:154px;width:47px;background-image:url('./image/red.png');}
.red-mometer span{position:absolute;display:block;bottom:0px;width:20px;}
.red-hgheader{height:15px;line-height:15px;color:#FF0000;font-size:12px;font-family:Arial, Helvetica, sans-serif;border-bottom:#f00 1px solid;left:0px;bottom:25px;position:relative;}
.red-hg{height:0px; font-size:0px;background-color:#FF0000;width:7px;left:20px;bottom:26px;position:relative;}
.hot{height:100px;width:47px; position:absolute; top:0; left:0;}
.blue-mometer{position:relative;height:154px;width:47px;background-image:url('./image/blue.png')}
.blue-mometer span{position:absolute;display:block;bottom:0px;width:20px;}
.blue-hgheader{height:15px;line-height:15px;color:#0088ff;font-size:12px;font-family:Arial, Helvetica, sans-serif;border-bottom:#0088ff 1px solid;left:0px;bottom:25px;position:relative;}
.blue-hg{height:0px; font-size:0px;background-color:#0088ff;width:7px;left:20px;bottom:26px;position:relative;}
.left-point{height:7px;width:9px;left:11px;bottom:30px;position:relative;background-image:url('./image/leftPoint.png');float:left;}
.right-point{height:7px;width:9px;left:28px;bottom:30px;position:relative;background-image:url('./image/rightPoint.png');}
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
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/ext-all-debug.gzjs"></script>

<META name="GENERATOR" content="MSHTML 9.00.8112.16447"></HEAD>
<BODY onLoad="afterLoad();">
<!-- <div class="mometer"> -->
<!-- <div id="mometer" class="red-mometer"> -->
<!-- <div id="hot" class="hot"></div>     -->
<!-- <span>         -->
<!-- <div id="Hgheader" class="red-hgheader">0℃</div>   -->
<!-- <div id="Hg" class="red-hg" style="height:0px"></div>     -->
<!-- </span> -->
<!-- </div> -->
<!-- <div style="height:7px;width:9px;left:11px;bottom:30px;position:relative;background-image:url('leftPoint.png');float:left"></div> -->
<!-- <div style="height:7px;width:9px;left:28px;bottom:30px;position:relative;background-image:url('rightPoint.png');"></div> -->
<!-- <div style="width:47px;">xxxx</div> -->
<!-- </div> -->
<!-- <div class="mometer"> -->
<!-- <div id="mometer1" class="blue-mometer"> -->
<!-- <div id="hot1" class="hot"></div>     -->
<!-- <span>         -->
<!-- <div id="Hgheader1" class="blue-hgheader">0℃</div>         -->
<!-- <div id="Hg1" class="blue-hg" style="height:30px"></div>     -->
<!-- </span> -->
<!-- </div> -->
<!-- <div style="height:7px;width:9px;left:11px;bottom:30px;position:relative;background-image:url('leftPoint.png');float:left"></div> -->
<!-- <div style="height:7px;width:9px;left:28px;bottom:30px;position:relative;background-image:url('rightPoint.png');"></div> -->
<!-- <div style="width:47px;">yyyy</div> -->
<!-- </div> -->


<!-- <div id="loading"> -->
<!-- 	<div class="loading-indicator"> -->
<!-- 		<img src="../../../resources/images/loading32.gif" width="31" height="31" -->
<!-- 			style="margin-right: 8px; float: left; vertical-align: top;" /> -->
<!-- 			正在努力加载，请稍候...<br /> -->
<!-- 		<span id="loading-msg">加载功能组件</span> -->
<!-- 	</div> -->
<!-- </div> -->
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
<link rel="stylesheet" type="text/css" href="../../../resources/style.css"/>
<script type="text/javascript" src="app.js"></script>
<SCRIPT>
function afterLoad(){
//Ext.get('loading').remove();
/**
Ext.define('User', {  
	extend: 'Ext.data.Model',  
	fields: [  
		{ name: 'id', type: 'int' },  
		{ name: 'name', type: 'string' },  
		{ name: 'active', type: 'boolean', convert: function (v) { return v === 't'; } }  
	]  
});  

Ext.create('Ext.grid.Panel', {  
	title: 'Users',  
	width: 400,  
	store: Ext.create('Ext.data.Store', {  
		model: 'User',  
		data: [  
			{ id: 1, name: 'name 1', active: 'f' },  
			{ id: 2, name: 'name 2', active: 't' },  
			{ id: 3, name: 'name 3', active: 't' }  
		]  
	}),  
	columns: [  
		{ header: 'id', dataIndex: 'id' },  
		{ header: 'name', dataIndex: 'name', flex: 1 },  
		{ header: 'active', dataIndex: 'active', xtype: 'booleancolumn', trueText: 'Yes', falseText: 'No' }  
	],  
	renderTo: 'xxx'  
});  
*/

}
</SCRIPT>
</BODY></HTML>
