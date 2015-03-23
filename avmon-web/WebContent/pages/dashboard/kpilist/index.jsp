<%@ page language="java" pageEncoding="UTF-8"%>

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
    
      var  firstMoId = "${moId}"
    
    	function afterLoad(){
    		Ext.avmon={currentMoId:"${moId}"};
            //reloadModule("${moId}");
    		Ext.get('loading').remove();
    	}
    	
    	function isShow(item){
    		if(window.parent && window.parent.isShow){
    			return window.parent.isShow("performance.kpiListPanel");
    		}
			return false;
    	}
    	
    	function reloadModule(moId){
    		Ext.avmon.currentMoId=moId;
            Ext.getCmp("kpiListGrid").getStore().load({params:{mo:moId},callback: function(records, operation, success) {
                if(records.length>0){
                    Ext.getCmp("kpiListGrid").getSelectionModel().select(0,true);
                }
            }});
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
    <link rel="stylesheet" type="text/css" href="../../resources/extjs420g/resources/css/ext-all<%=request.getSession().getAttribute("theme")%>.css"/>
    <script type="text/javascript" src="../../resources/extjs420g/ext-all.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>
    <link rel="stylesheet" type="text/css" href="../../resources/style.css"/>
    <link rel="stylesheet" type="text/css" href="style.css"/>

	<script type="text/javascript" src="app.js"></script>
</div>

</body>
</html> 