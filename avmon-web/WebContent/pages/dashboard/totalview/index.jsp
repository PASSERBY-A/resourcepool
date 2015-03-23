<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="/pages/inc/head.jsp" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>main</title>

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

    <script>
    
    	function afterLoad(){
    		if(!Ext.avmon){
    			Ext.avmon = {};
    		}
    		Ext.avmon={currentMoId:"${moId}"};
    		Ext.get('loading').remove();
    	}
    	
    	function isShow(item){
    		if(window.parent && window.parent.isShow){
    			return window.parent.isShow(item);
    		}
			return false;
    	}
    	
    	
    	function onShowPage(){
    		var win = Ext.deviceListWindow;
    		if(win){
    			win.hide();
    		}
    	}
    	
    	function reloadModule(moId){
    		//alert(moId);
    		Ext.avmon.currentMoId=moId;
            Ext.getCmp("resourceListGrid").getStore().load({params:{id:moId},callback: function(records, operation, success) {

            }});
    	}
    	
    	function showDeviceList(deviceType,businessSystem){

    		var win = Ext.deviceListWindow;

    		if(!win){
    		    win=Ext.create('widget.deviceListWindow');
    		    Ext.deviceListWindow=win;
    		    //win.needReload=true;
    		}
			
    		win.show();
    		win.maximize();
    		//alert(deviceType);
    		//alert(businessSystem);
    		var p=win.down("#deviceListPanel");
    		if(deviceType=="host"){
    			//alert('start');
	    		p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../../pages/dashboard/devicestatus/?a=1&biz='+encodeURI(encodeURI(businessSystem))+'"></iframe>',
	    				false,function(){
	    						//alert('done with host');
	    					});
    		}
    		if(deviceType=='storage'){
	    		p.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../../dashboard/resourcelist/index?moId=STORAGE&biz='+encodeURI(encodeURI(businessSystem))+'"></iframe>',
	    				false,function(){    	
	    						//alert('done  with storage');
	    					});
    		}
    	}
    </script>
    <%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
    
    
</head>

<body onLoad="afterLoad();">

<div id="loading">
<div class="loading-indicator"><img
	src="../../resources/images/loading32.gif" width="31" height="31"
	style="margin-right: 8px; float: left; vertical-align: top;" />
<spring:message code="loading"/><br />
<span id="loading-msg"><spring:message code="loadingFunctionalComponents"/></span></div>
</div>

    <link rel="stylesheet" type="text/css" href="../../resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
    <script type="text/javascript" src="../../resources/extjs411g/ext-all.gzjs"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>
    <link rel="stylesheet" type="text/css" href="../../resources/style.css"/>
    <link rel="stylesheet" type="text/css" href="style.css"/>
	<script>
		if(Ext.isIE){
			loadjscssfile("style_ie.css","css");
		}
	</script>

	<script type="text/javascript" src="app.js"></script>
</div>

</body>
</html> 