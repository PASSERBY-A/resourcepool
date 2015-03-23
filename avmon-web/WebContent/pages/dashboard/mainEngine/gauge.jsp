<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style>
      body {
        text-align: center;
		font-family: Arial;
      }
      
      #g1 {
      width:157px; height:74px;
/*        width:288px; height:162px;
        display: inline-block;
        margin: 0px;
		border: 1px soild #202020;
		box-shadow: 0px 0px 15px #101010;
		margin-top: 0px;
		border-radius: 8px; */
      }
      
      p {
        display: block;
        width: 400px;
        margin: 2em auto;
        text-align: center;
		border-top: 1px soild #CCC;
		border-bottom: 1px soild #CCC;
		background: #333333;
		padding:10px 0px;
		color: #CCC;
		text-shadow: 1px 1px 25px #000000;
		border-radius: 0px 0px 5px 5px;
		box-shadow: 0px 0px 10px #202020;
      }
    </style>
<%
	String cpu = request.getParameter("cpu_user");
	System.out.print("cpu:" + cpu);
	String level = request.getParameter("level");
	if (level == null || level == "") {
		level = "0";
	}
	System.out.print("level:" + level);
%>
    
    
    <script src="${pageContext.request.contextPath}/resources/justguage/raphael.2.1.0.min.js"></script>
    <script src="${pageContext.request.contextPath}/resources/justguage/justgage.1.0.1.min.js"></script>
     <script>
      var g1;
      
      function loadGauge(){
          var level = <%=level%>;
          if(level == "0")
          {
              var g1 = new JustGage({
                  id: "g1", 
                  value: '<%=cpu%>', 
                  min: 0,
                  max: 100,
                  title: "Usage",
                  label: "%",
        			levelColors: [
        			 "#6B8E23"
        				]    
                });
           }else if(level == "1")
           {
               var g1 = new JustGage({
                   id: "g1", 
                   value: '<%=cpu%>', 
                   min: 0,
                   max: 100,
                   title: "Usage",
                   label: "%",
         			levelColors: [
         			 "#1874CD"
         				]    
                 });
            }else if(level == "2")
            {
                var g1 = new JustGage({
                    id: "g1", 
                    value: '<%=cpu%>', 
                    min: 0,
                    max: 100,
                    title: "Usage",
                    label: "%",
          			levelColors: [
          			 "#EEC900"
          				]    
                  });
             }else if(level == "3")
             {
                 var g1 = new JustGage({
                     id: "g1", 
                     value: '<%=cpu%>', 
                     min: 0,
                     max: 100,
                     title: "Usage",
                     label: "%",
           			levelColors: [
           			 "#EE7600"
           				]    
                   });
              }else if(level == "4")
              {
                  var g1 = new JustGage({
                      id: "g1", 
                      value: '<%=cpu%>', 
                      min: 0,
                      max: 100,
                      title: "Usage",
                      label: "%",
            			levelColors: [
            			 "#EE4000"
            				]    
                    });
               }	        
        
      };
    </script>

</head>
<body onLoad="loadGauge();">

	<div id="g1" ></div>


</body>
</html>