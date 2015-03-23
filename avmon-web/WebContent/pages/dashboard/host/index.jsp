<%@ page language="java" pageEncoding="UTF-8"%>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>avmon</title>



<script>


function reloadModule(moId){
	Ext.avmon.moId=moId;

    Ext.MessageBox.show({
        msg: avmon.common.loadingWait,
        progressText: 'Loading...',
        width:300,
        wait:true,
        waitConfig: {interval:200},
        icon:'icon-download'
    });

    setTimeout(function(){
	Ext.avmon.application.refreshAll();
    },10);
    
	setTimeout(function(){
		Ext.MessageBox.hide();
	},1000);
}

function afterLoad(){
	Ext.avmon={moId:"${mo}"};
	
	setTimeout(function(){
		Ext.get('loading').remove();
	},1000);

}

</script>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>

 
 </head>
<style type="text/css">
   .cpuGIcon{ height:50px;
             width:50px;
             background-image: url(./imgs/cpu_good.png);
             text-align:center;
             line-height:50px;
             cursor:pointer;
             font-size:16;
             color:#ffffff;
             background-repeat:no-repeat;
             }
   .cpuBIcon{ height:50px;
             width:50px;
             background-image: url(./imgs/cpu_bad.png);
             line-height:50px;
             background-repeat:no-repeat;
             }
    .blackBor{
        border:2px black solid;
    }  
    .redBor{
        border:2px red solid;
    }   
    .greenBor{
        border:2px green solid;
    }   
    .blueBor{
        border:2px blue solid;
    }   
    .yellowBor{
        border:2px yellow solid;
    }        
    
    
    
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

<body onLoad="afterLoad();">

<div id="loading">
<div class="loading-indicator"><img
	src="../../resources/images/loading32.gif" width="31" height="31"
	style="margin-right: 8px; float: left; vertical-align: top;" />
<spring:message code="loading"/><br />
<span id="loading-msg"><spring:message code="loadingFunctionalComponents"/></span></div>
</div>

    <link rel="stylesheet" type="text/css" href="../../resources/extjs411g/resources/css/ext-all<%=request.getSession().getAttribute("theme")==null?"":request.getSession().getAttribute("theme")%>.css"/>
    <script type="text/javascript" src="../../resources/extjs411g/ext-all.gzjs"></script>
	<script type="text/javascript" src="./example-data.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/resources/extjs411g/locale/ext-lang-<%=request.getSession().getAttribute("browserLang") %>.js"></script>


    <script type="text/javascript" src="./app.js?t=<%=com.hp.avmon.utils.Utils.debugRandomId() %>"></script>
    <script type="text/javascript" src="func.js?t=<%=com.hp.avmon.utils.Utils.debugRandomId() %>"></script>
 	<link rel="stylesheet" type="text/css" href="../../resources/style.css" />
    <link rel="stylesheet" type="text/css" href="style.css"/>
 	<link rel="stylesheet" type="text/css" href="../../resources/shared/example.css" />

</div>

</body>

</html>