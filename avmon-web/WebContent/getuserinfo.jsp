<%@page import="com.hp.avmon.common.jackjson.JackJson"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
final String ROLE_RESOURCEPOOL = "e3c3847d-be6a-42c8-a848-b3fa7cef7adf";
final String ROLE_ADMIN="1";

String jsoncallback = request.getParameter("jsoncallback");

jsoncallback = jsoncallback==null || "".equals(jsoncallback)?"jsoncallback":jsoncallback;

Map<String,String> map = (Map)request.getSession().getAttribute("CURRENT_USER");

map.remove("email");
map.remove("mobile");
map.remove("office_phone");
map.remove("password");
map.remove("real_name");
map.remove("remark");

 
//map.put("isadmin", request.getSession().getAttribute("isadmin")==null?"false":"true");

Map roleMap=(Map)request.getSession().getAttribute("roleMap");

if(roleMap.get("role_id").toString().contains ("admin"))
{
	map.put("role_id", "admin");
	map.put("isadmin","true");
}
else{
	
	map.put("role_id", "user");
	map.put("isadmin","false");
}
String modules = map.get("modules");
String result=jsoncallback+"("+JackJson.fromObjectToJson(map)+")";

%>  
<%=result%>
