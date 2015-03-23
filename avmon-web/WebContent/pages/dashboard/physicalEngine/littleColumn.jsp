<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="${pageContext.request.contextPath}/resources/jquery-1.9.0/jquery-1.9.0.min.js"></script>
<style>

		#grapharea { position: relative; float: left; /*margin-left: 2em;*/ }

		#graphcontainer { position: relative; width: 45px; height: 80px; overflow: hidden; }

		#graphbackground, #graphforeground { position: absolute; }

		#graphbar { position: absolute; top: 430px; }

		#p25 { top: 365px; }

		#p50 { top: 278px; }

		#p75 { top: 191px; } 

		#p100 { top: 105px; }
    </style>
    
<%
	String kpi = request.getParameter("kpi");
	if (kpi == null) {
		kpi = "0";
	}
	System.out.print("kpi:" + kpi);
	String level = request.getParameter("level");
	if (level == null || level == "") {
		level = "1";
	}
	System.out.print("level:" + level);
	
%>
    
	
     <script>
	    function loadColumn()
	    {		    
	        var ZEROPOS = 66;// 160*(355/430)
	        var kpi = <%=kpi%>;
	        var level = <%=level%>;
	        //display the full green bucket while kpi<0
	        if(kpi == -1)
	        {
				var percent = 100;
				$("#graphbar").attr("src","images/graphbar_green.png");
				$("#graphbar").animate({ top: 0 + "px" }, 1500); 
			}
	        else
	        {
	        	var percent = kpi / 100;
		        val = (percent == 0) ? (ZEROPOS+50) : ZEROPOS - (percent * ZEROPOS);

				if(level=="4")
				{
					$("#graphbar").attr("src","images/graphbar_orange.png");
				}else if(level=="1")
				{
					$("#graphbar").attr("src","images/graphbar_green.png");
				}else if(level=="2")
				{
					$("#graphbar").attr("src","images/graphbar_blue.png");
				}else if(level=="3")
				{
					$("#graphbar").attr("src","images/graphbar_yellow.png");
				}else if(level=="5")
				{
					$("#graphbar").attr("src","images/graphbar_red.png");
				}
				$("#graphbar").animate({ top: val + "px" }, 1500); 
		      }
	        			
			        
	    }
     
    </script>
</head>
<body onLoad="loadColumn();">
	        <div id="grapharea">
	        <div id="graphcontainer" >
		        <img src="images/graphbackground.png" id="graphbackground" width="45" height="80" alt="" />
		        <img src="" id="graphbar" width="45" height="80" alt="" />
		        <img src="images/graphforeground.png" id="graphforeground" width="45" height="80" alt="" />
	        </div>
        </div>
</body>
</html>