<%@ page language="java" contentType="text/html; charset=UTF-8"%><%
	if (request.getHeader("x-requested-with") != null
			&& request.getHeader("x-requested-with").equalsIgnoreCase(
					"XMLHttpRequest")) {
		response.setStatus(200);
		response.setHeader("session-status","pagenotfind");
	} else {
	%><!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<title><spring:message code="pageNotExist"/></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css">
.STYLE1 {
    color:#0000FF; 
    font-weight:bold; 
    font-size:25px
    font-weight: bold;
}
 
.STYLE2{
    font-size:15px;
    line-height:25px
}
</style>
<script type="text/javascript">
	function getRootWin() {
		var win = window;
		while (win != win.parent) {
			win = win.parent;
		}
		return win;
	}
</script>
</head>
<body style="margin-top:100px;">
    <div style="margin:0 auto; width:650px;">
        <p>
            <h3 class="STYLE1"><spring:message code="sorryPageNotExist"/></h3>
        </p>
        <br />
        <p>
            <h2 class="STYLE2">
                1、<spring:message code="checkTheAddressYouEnter"/>
            </h2>
        </p>
        <p>
            <h2 class="STYLE2">
                2、<spring:message code="pass"/><a href="javascript:getRootWin().location='${pageContext.request.contextPath}'"><spring:message code="homePage"/></a><spring:message code="toBrowse"/></h2>
        </p>
    </div>
</body>
</html><%}%>