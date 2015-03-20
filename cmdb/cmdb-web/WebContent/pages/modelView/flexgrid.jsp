<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%
String nodeId=(String)request.getParameter("nodeId");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Flexigrid</title>
<link rel="stylesheet" type="text/css" href="css/flexGrid/flexigrid.css">
<script type="text/javascript" src="js/jquery/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="js/jquery/flexGrid/flexigrid.js"></script>
</head>

<body >

<div id="flex1" style="display:none"></div>
<script type="text/javascript">
$("#flex1").flexigrid({
	url: 'getHostCIAttribute',
	dataType: 'json',
	colModel : [
		{display: '主机名', name : 'host_name', width : 280, sortable : true, align: 'left'},
		{display: '类属性1', name : 'att_1', width : 280, sortable : true, align: 'left'},
		{display: '类属性2', name : 'att_2', width : 300, sortable : true, align: 'left'}
		],
	searchitems : [
		{display: '主机名', name : 'host_name'},
		{display: '类属性1', name : 'att_1'},
		{display: '类属性2', name : 'att_1', isdefault: true}
		],
	sortname: "host_name",
	sortorder: "asc",
	usepager: true,
	title: '属性列表',
	useRp: true,
	rp: 25,
	showTableToggleBtn: true,
	width: 650,
	onSubmit: addFormData,
	autoload: true, //自动加载，即第一次发起ajax请求
	height: 220
});

//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit			
function addFormData(){
	//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
	var dt = $('#sform').serializeArray();
	$("#flex1").flexOptions({params : [{"name" : "nodeId", "value" : <%=nodeId%>}], newp : 1});
	return true;
}

function afterLoad(){
	alert("flexgrid:" + <%=nodeId%>);
	$.ajax({  
        type: "POST",  
        url: "getHostCIAttribute",  
        data: "nodeId="+<%=nodeId%>,  
        dataType:"text",  
        success: function(msg){  
 //           if(msg=="success"){  
                $("#flex1").flexAddData(msg);  
 //           }else{  
 //               alert("有错误发生,msg="+msg);  
  //          }  
        },  
        error: function(msg){  
            alert(msg);  
        }  
    });  
	//$('#flex1').flexReload();	
}

</script>
</body>
</html>