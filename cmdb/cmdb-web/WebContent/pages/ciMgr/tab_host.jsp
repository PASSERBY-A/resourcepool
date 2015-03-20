<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String ip=(String)request.getParameter("ip");
String port=(String)request.getParameter("port");
String project=(String)request.getParameter("project");
String user=(String)request.getParameter("user");
String pwd=(String)request.getParameter("pwd");
String kpicode=(String)request.getParameter("kpicode");
String ciname=(String)request.getParameter("ciname");
if (ip == null || "".equals(ip)) {
	ip = "16.159.216.151";
	port = "8080";
	project = "avmon-web";
	user = "admin";
	pwd = "admin";
	kpicode = "1002001001";
	ciname = "750app1b";
}
%>

<html>
<head>
<title>CSS3 & jQuery folder tabs - demo</title>
<style>
  body {
      width: 99%;
      height:90%
      margin: 100px auto 0 auto;
      font-family: Arial, Helvetica;	
      font-size: small;
      background: #444;
  }

/* ------------------------------------------------- */

#tabs {
  overflow: hidden;
  width: 100%;
  margin: 0;
  padding: 0;
  list-style: none;
}

#tabs li {
  float: left;
  margin: 0 .5em 0 0;
}

#tabs a {
  position: relative;
  background: #ddd;
  background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#ddd));
  background-image: -webkit-linear-gradient(top, #fff, #ddd);
  background-image: -moz-linear-gradient(top, #fff, #ddd);
  background-image: -ms-linear-gradient(top, #fff, #ddd);
  background-image: -o-linear-gradient(top, #fff, #ddd);
  background-image: linear-gradient(to bottom, #fff, #ddd);  
  padding: .7em 3.5em;
  float: left;
  text-decoration: none;
  color: #444;
  text-shadow: 0 1px 0 rgba(255,255,255,.8);
  -webkit-border-radius: 5px 0 0 0;
  -moz-border-radius: 5px 0 0 0;
  border-radius: 5px 0 0 0;
  -moz-box-shadow: 0 2px 2px rgba(0,0,0,.4);
  -webkit-box-shadow: 0 2px 2px rgba(0,0,0,.4);
  box-shadow: 0 2px 2px rgba(0,0,0,.4);
}

#tabs a:hover,
#tabs a:hover::after,
#tabs a:focus,
#tabs a:focus::after {
  background: #fff;
}

#tabs a:focus {
  outline: 0;
}

#tabs a::after {
  content:'';
  position:absolute;
  z-index: 1;
  top: 0;
  right: -.5em;  
  bottom: 0;
  width: 1em;
  background: #ddd;
  background-image: -webkit-gradient(linear, left top, left bottom, from(#fff), to(#ddd));
  background-image: -webkit-linear-gradient(top, #fff, #ddd);
  background-image: -moz-linear-gradient(top, #fff, #ddd);
  background-image: -ms-linear-gradient(top, #fff, #ddd);
  background-image: -o-linear-gradient(top, #fff, #ddd);
  background-image: linear-gradient(to bottom, #fff, #ddd);  
  -moz-box-shadow: 2px 2px 2px rgba(0,0,0,.4);
  -webkit-box-shadow: 2px 2px 2px rgba(0,0,0,.4);
  box-shadow: 2px 2px 2px rgba(0,0,0,.4);
  -webkit-transform: skew(10deg);
  -moz-transform: skew(10deg);
  -ms-transform: skew(10deg);
  -o-transform: skew(10deg);
  transform: skew(10deg);
  -webkit-border-radius: 0 5px 0 0;
  -moz-border-radius: 0 5px 0 0;
  border-radius: 0 5px 0 0;  
}

#tabs #current a {
  background: #fff;
  z-index: 3;
}

#tabs #current a::after {
  background: #fff;
  z-index: 3;
}

/* ------------------------------------------------- */

#content {
    background: #fff;
    padding: 2em;

    position: relative;
    z-index: 2;	
    -moz-border-radius: 0 5px 5px 5px;
    -webkit-border-radius: 0 5px 5px 5px;
    border-radius: 0 5px 5px 5px;
    -moz-box-shadow: 0 -2px 3px -2px rgba(0, 0, 0, .5);
    -webkit-box-shadow: 0 -2px 3px -2px rgba(0, 0, 0, .5);
    box-shadow: 0 -2px 3px -2px rgba(0, 0, 0, .5);
}

/* ------------------------------------------------- */

#about {
    color: #999;
}

#about a {
    color: #eee;
}

</style>
</head>

<body>

<ul id="tabs">
    <li><a href="#" name="tab1">KPI 列表</a></li>
    <li><a href="#" name="tab2">KPI 仪表盘</a></li>
    <li><a href="#" name="tab3">告警</a></li>

</ul>

<div id="content"> 
    <div id="tab1">
		<iframe id="kpi" name="kpi" width="100%" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"
		src="http://<%=ip%>:<%=port%>/<%=project%>/dashboard/kpilist/index?a=1&moId=1Fk8Nr6Uzd8WB4In1Qu9XCg5Jo2RvaY&mo=1Fk8Nr6Uzd8WB4In1Qu9XCg5Jo2RvaY&agentId=&theme=-gray&browserLang=zh_CN&usr=<%=user%>&pwd=<%=pwd%>&kpiCode=<%=kpicode%>&ciName=<%=ciname%>"></iframe>
    </div>
    <div id="tab2">
		<iframe id="kpid" width="100%" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"
		src=""></iframe>
    </div>
    <div id="tab3">
		<iframe id="alert" width="100%" frameborder="0" scrolling="no" marginwidth="0" marginheight="0"
		 src=""></iframe>
    </div>
</div>

<br><br>
<script src="js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>

<script src="js/jquery/jquery-ui-1.10.3.custom.min.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/redmond/jquery-ui-1.10.3.custom.min.css" />
<script>
$(document).ready(function() {
    $("#content").find("[id^='tab']").hide(); // Hide all content
    $("#tabs li:first").attr("id","current"); // Activate the first tab
    $("#content #tab1").fadeIn(); // Show first tab's content
    
    $('#tabs a').click(function(e) {
        e.preventDefault();
        if ($(this).closest("li").attr("id") == "current"){ //detection for current tab
         return;       
        }
        else{             
          $("#content").find("[id^='tab']").hide(); // Hide all content
          $("#tabs li").attr("id",""); //Reset id's
          $(this).parent().attr("id","current"); // Activate this
          $('#' + $(this).attr('name')).fadeIn(); // Show content for the current tab
          if ($(this).attr('name') == "tab2" && $("#kpid").attr('src') == "") {
              var url2 = "http://<%=ip%>:<%=port%>/<%=project%>/pages/dashboard/mainEngine/index.jsp?a=1&moId=xovm06-16.159.216.106&mo=xovm06-16.159.216.106&agentId=&theme=-gray&browserLang=zh_CN&usr=<%=user%>&pwd=<%=pwd%>&kpiCode=<%=kpicode%>&ciName=<%=ciname%>";
              $("#kpid").attr('src',url2);
          }
          if ($(this).attr('name') == "tab3" && $("#alert").attr('src') == "") {
              var url3 = "http://<%=ip%>:<%=port%>/<%=project%>/pages/dashboard/alarmSearch/index.jsp?a=1&moId=&mo=&agentId=&theme=-gray&browserLang=zh_CN&usr=<%=user%>&pwd=<%=pwd%>&kpiCode=<%=kpicode%>&ciName=<%=ciname%>";
              $("#alert").attr('src',url3);
          }
        }
    });
    /**/
    $("#content").height($(document).height()-120);
    $("#kpi").height($(document).height()-120);
    $("#kpid").height($(document).height()-120);
    $("#alert").height($(document).height()-120);
   
    //alert($(window).height()); //浏览器当前窗口可视区域高度 
    //alert($(document).height()); //浏览器当前窗口文档的高度 
    //alert($(document.body).height());//浏览器当前窗口文档body的高度 
    //alert($(document.body).outerHeight(true));//

});
/*
$("#kpi").load(function(){  
	var hg =  $(document).height();

    $(this).height(hg-120);  
}); 

$("#kpid").load(function(){  
	var hg =  $(document).height();

    $(this).height(hg-120);  
}); 

$("#alert").load(function(){  
	var hg =  $(document).height();

    $(this).height(hg-120);  
}); 
*/
</script>

</body>
</html>