<%@ page language="java" pageEncoding="UTF-8"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD><TITLE>Avmon2.0系统登录</TITLE>
<META content="text/html; charset=utf-8" http-equiv="Content-Type">
<STYLE>
body {
	background-color:#F0F0F0;
}
.errorInfo {
	font-family: Arial, Helvetica, sans-serif, 微软雅黑;
	color:#f00;
}
.title {
	height:72px;
	font-family: Arial, Helvetica, sans-serif, 微软雅黑;
	font-size:30px;
	color:#666;
}
.login {
	vertical-align:bottom;
	height:28px;
	font-family:Verdana, Geneva, sans-serif, 微软雅黑;
	font-size:12px;
	color:#666;
}
.login_a {
	height:28px;
	font-family:Verdana, Geneva, sans-serif, 微软雅黑;
	font-size:12px;
	color:#666;
}
.login_c {
	vertical-align:middle;
	text-align:center;
	height:60px;
	font-family:微软雅黑, Verdana, Geneva, sans-serif;
	font-size:12px;
	color:#666;
}
.login_d {
	vertical-align:top;
	height:8px;
	font-family:微软雅黑,Verdana, Geneva, sans-seri;
	font-size:12px;
	color:#666;

}
.btn1_mouseout {
	text-align:center;
	padding:2px 0px 2px 0px;
	width:100px;
	font-family:微软雅黑, Arial, Helvetica, sans-serif;
	font-size:12px;
	font-weight:bold;
	BORDER: #666666 1px solid;
	FONT-SIZE:12px;
 	background-color:#888888;
	CURSOR:hand;
	COLOR:#FFF;
}
.btn1_mouseover {
	text-align:center;
	padding:2px 0px 2px 0px;
	width:100px;
	font-family:微软雅黑, Arial, Helvetica, sans-serif;
	font-size:12px;
	font-weight:bold;
	BORDER: #dddddd 1px solid;
	FONT-SIZE:12px;
 	background-color:#555555;
	CURSOR:pointer,hand;
	COLOR:#DDD;
}
.btn1_mousedown {
	text-align:center;
	padding:2px 0px 2px 0px;
	width:100px;
	font-family:微软雅黑, Arial, Helvetica, sans-serif;
	font-size:12px;
	font-weight:bold;
	BORDER: #dddddd 1px solid;
	FONT-SIZE:12px;
 FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#888888, EndColorStr=#555555);
	CURSOR:hand;
	COLOR:#DDD;
}
.btn1_mouseup {
	text-align:center;
	width:100px;
	font-family:Arial, Helvetica, sans-serif,微软雅黑;
	font-size:12px;
	font-weight:bold;
	padding:2px 0px 2px 0px;
	BORDER: #666666 1px solid;
	FONT-SIZE:12px;
 FILTER: progid:DXImageTransform.Microsoft.Gradient(GradientType=0, StartColorStr=#999999, EndColorStr=#666666);
	CURSOR:hand;
	COLOR:#FFF;
}
.login_banner{
	background:url(${pageContext.request.contextPath}/resources/images/loginBK.png) repeat-x;
	border-color:#999;
	border-width:1px;
	}
a:link {
	color:#666
}
a:hover {
	color:#999
}
a:active {
	color:#666
}
a:visited {
	color:#666
}
</STYLE>

<SCRIPT>
function afterLoad(){document.getElementById("email").focus();}
</SCRIPT>

<META name="GENERATOR" content="MSHTML 9.00.8112.16447"></HEAD>
<BODY onLoad="afterLoad();" background="${pageContext.request.contextPath}/resources/images/login_a.png">
<DIV style="height: 200px;"></DIV>
<FORM method="post" name="form1" action="${pageContext.request.contextPath}/main/login">
<TABLE border="1" cellSpacing="0" cellPadding="0" width="450"  align="center" height="280">
  <TBODY>
  <TR>
    <TD>
      <TABLE border="0" cellSpacing="0" cellPadding="0" width="484" align="center" class="login_banner">
        <TBODY>
        <TR>
          <TD colspan="2" class="title"><IMG align="absbottom" src="${pageContext.request.contextPath}/resources/images/login_banner.png"></TD>
          </TR>
        <TR>
          <TD width="135" class="login">&nbsp;</TD>
          <TD width="349" class="login">用户ID:</TD></TR>
        <TR>
          <TD class="login_a">&nbsp;</TD>
          <TD class="login_a"><LABEL><INPUT id="userid" name="userid" value="admin" 
            size="30" type="text"></LABEL></TD></TR>
        <TR>
          <TD class="login_d">&nbsp;</TD>
          <TD class="login_d"></TD></TR>
        <TR>
          <TD class="login">&nbsp;</TD>
          <TD class="login">密码:</TD></TR>
        <TR>
          <TD class="login">&nbsp;</TD>
          <TD class="login"><INPUT id="password" name="password" size="30" 
            type="password" value="" ></TD></TR>
        <TR>
          <TD colspan="2" class="login_c"> &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
          <BUTTON onMouseUp="this.className='btn1_mouseup'" 
            class="btn1_mouseout" onMouseOver="this.className='btn1_mouseover'" 
            title="Login" onMouseOut="this.className='btn1_mouseout'" 
            onmousedown="this.className='btn1_mousedown'" 
            type="submit">登&nbsp;&nbsp;&nbsp;录</BUTTON>    
            &nbsp;&nbsp;&nbsp;     
          <BUTTON onMouseUp="this.className='btn1_mouseup'" 
            class="btn1_mouseout" onMouseOver="this.className='btn1_mouseover'" 
            title="Login" onMouseOut="this.className='btn1_mouseout'" 
            onmousedown="this.className='btn1_mousedown'" 
            type="reset">重&nbsp;&nbsp;&nbsp;置</BUTTON>               
        <TR>
      <TD height="44" colSpan="2" align="left" class="login_d"><SPAN 
            class="errorInfo">${errorInfo}</SPAN></TD></TR></TBODY></TABLE></TD></TR>
</TBODY></TABLE></FORM></BODY></HTML>
