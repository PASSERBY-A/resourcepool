<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String typeName=(String)request.getParameter("typeName");
%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="keywords" content="注册向导，滚动，表单，jquery" />
<meta name="description" content="Helloweba演示平台，演示XHTML、CSS、jquery、PHP案例和示例" />
<title>jQuery实现一个滚动的分步注册向导</title>
<link rel="stylesheet" type="text/css" href="../css/main.css" />
<style type="text/css">
#wizard {border:5px solid #789;font-size:12px;height:600px;margin:20px auto;width:570px;overflow:hidden;position:relative;-moz-border-radius:5px;-webkit-border-radius:5px;}
#wizard .items{width:20000px; clear:both; position:absolute;}
#wizard .right{float:right;}
#wizard #status{height:35px;background:#123;padding-left:25px !important;}
#status li{float:left;color:#fff;padding:10px 30px;}
#status li.active{background-color:#369;font-weight:normal;}
.input{width:240px; height:18px; margin:10px auto; line-height:20px; border:1px solid #d3d3d3; padding:2px}
.page{padding:20px 30px;width:500px;float:left;}
.page h3{height:42px; font-size:16px; border-bottom:1px dotted #ccc; margin-bottom:20px; padding-bottom:5px}
.page h3 em{font-size:12px; font-weight:500; font-style:normal}
.page p{line-height:24px;}
.page p label{font-size:14px; display:block;}
.btn_nav{height:36px; line-height:36px; margin:120px auto;}
.prev,.next{width:100px; height:32px; line-height:32px; background:url(btn_bg.gif) repeat-x bottom; border:1px solid #d3d3d3; cursor:pointer}
</style>
<!-- Of course we should load the jquery library -->
<script src="js/jquery/jquery.1.4.2.min.js" type="text/javascript"></script>
<script src="js/jquery/scrollable.js" type="text/javascript"></script>


</head>

<body>
<div id="header">
   
</div>

<div id="main">
   <form action="#" method="post">
	<div id="wizard">
		<ul id="status">
			<li class="active"><strong>1.</strong>CI信息</li>
			<li><strong>2.</strong>CI属性</li>
			<li><strong>3.</strong>完成</li>
		</ul>

		<div class="items">
			<div class="page">
               <h3>CI信息<br/><em></em></h3>
               <p><label>显示名称：</label><input type="text" class="input" id="label" name="label" /></p>
               <p><label>CI名称：</label><input type="input" class="input" id="name" name="name" /></p>
               <p><label>数据域：</label><input type="text" class="input" id="domain" name="domain" /></p>
              
               <div class="btn_nav">
                  <input type="button" class="next right" id="goToCiAttribute" value="下一步&raquo;" />
               </div>
            </div>
			<div class="page">
               <p>选择CI类型属性<br/><em></em></p>
               <div id="ciAtrributeDiv" class="easyui-window" closed="true" modal="true" title="标题" style="width:500px;height:250px;">
               <form>
	<fieldset>
	<table id="fieldsetTable">
	<!-- 
	<tr>
	<td><label for="name">配置项名称</label><td>
	<td><input type="text" name="name" id="name" class="text ui-widget-content ui-corner-all" /><td>
	</tr>
	<tr>
	<td><label for="label">显示名称</label><td>
	<td><input type="text" name="label" id="label" class="text ui-widget-content ui-corner-all" /><td>
	</tr>
	<tr>
	<td><label for="parentId">父类</label><td>
	<td><input type="text" name="parentId" id="parentId" class="text ui-widget-content ui-corner-all" /><td>
	</tr>
	<tr>
	<td><label for="derivedFrom">父 类名称</label><td>
	<td><input type="text" name="derivedFrom" id="derivedFrom" class="text ui-widget-content ui-corner-all" /><td>
	</tr>
	 -->	
	</table>
	</fieldset>
	</form>
               <!-- 
               <iframe scrolling="auto" id='ciAttribute' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
                -->
     				
				</div> 
               <div class="btn_nav">
                  <input type="button" class="prev" style="float:left" value="&laquo;上一步" />
                  <input type="button" class="next right" id="goToTargetNode" value="下一步&raquo;" />
               </div>
            </div>
            
			<div class="page">
               <h3>选择目标节点<br/><em>点击确定生成关系。</em></h3>
               <div id="targetNodeDiv" class="easyui-window" closed="true" modal="true" title="标题" style="width:500px;height:250px;">
     				<iframe scrolling="auto" id='targetNodeSelected' frameborder="0"  src="" style="width:100%;height:100%;"></iframe>
				</div>
		<input type="text" name=targetNodeId id="targetNodeId" class="text ui-widget-content ui-corner-all" /></br>		
		<input type="text" name="targetType" id="targetType" class="text ui-widget-content ui-corner-all" /></br>		
		<input type="text" name="targetNode" id="targetNode" class="text ui-widget-content ui-corner-all" /></br> 
               <div class="btn_nav">
                  <input type="button" class="prev" style="float:left" value="&laquo;上一步" />
                  <input type="button" class="next right" id="sub" value="确定" />
               </div>
            </div>
		</div>
	</div>
</form><br />
<br />
<br />

</div>
<div id="footer">
    
</div>
<script type="text/javascript">



$(function(){

	$( "#sourceNodeId" ).hide();
	$( "#sourceType" ).hide();
	$( "#sourceNode" ).hide();
	$( "#targetNodeId" ).hide();
	$( "#targetType" ).hide();
	$( "#targetNode" ).hide();


	$("#wizard").scrollable({
		onSeek: function(event,i){
			$("#status li").removeClass("active").eq(i).addClass("active");
		},
		onBeforeSeek:function(event,i){
			if(i==1){
				var user = $("#user").val();
				if(user==""){
					alert("请输入用户名！");
					return false;
				}
				var pass = $("#pass").val();
				var pass1 = $("#pass1").val();
				if(pass==""){
				    alert("请输入密码！");
					return false;
				}
				if(pass1 != pass){
				    alert("两次密码不一致！");
					return false;
				}
			}
		}
	});
	$("#sub").click(function(){
		var data = $("form").serialize();
		alert(data);
		$.ajax({
			  type: "POST",
			  url: "addRelationCi",
			  data: {
			   label:$("#label").val(),
			    name: $("#name").val(),
			    typeName : "<%=typeName%>",
			    sourceNodeId: $("#sourceNodeId").val(),
			    sourceNode: $("#sourceNode").val(),
			    sourceType: $("#sourceType").val(),
			    targetNodeId: $("#targetNodeId").val(),
			    targetNode: $("#targetNode").val(),
			    targetType: $("#targetType").val()
			  },
			  async:false,
			  dataType: "json",
			  success: function( data, textStatus, jqXHR ) {
				  var message = data.success;
				  var result = data.result;
				  alert(message);
				  if(result=='0'){
					  //afterLoad();
				  }else{
					  alert(message);
				  }
			  }
			  
			});
	});

	var colModelData = []; 
	$("#goToCiAttribute").click(function(){
		//alert("goToCiAttribute");
		//$("#ciAttribute").attr("src","../ciMgr/ciAttribute.jsp?typeName=<%=typeName%>");
		
		 $.ajax({
			  type: "POST",
			  //url: "getCitAttrbyName",
			  url: "getCitTypeAtrrByNameAll",
			  data: {
			    //id: $("#id").val(),
			   // pid:treeNode.parentId,
			  //  label:$("#label").val(),
			   // name: $("#name").val(),
			    typeName : "<%=typeName%>"
			    //iconSkin: $("#iconSkin").val()
			  },
			  async:false,
			  dataType: "json",
			  success: function( data, textStatus, jqXHR ) {
				  //alert(data);
				  colModelData = data;
				  configTableSource();
			  }
			  
			});
		 
		 
		 //遍历数据
		 function configTableSource(){
			 var table1 = $('#fieldsetTable');
			 $(colModelData).each(function(){ 
				 //alert(this.name+" "+this.label); 
				 if(this.label != "id"){
					 var row = $("<tr></tr>"); 
					 var tdName = $("<td></td>");
					 var tdValue = $("<td></td>");
					 tdName.append($("<label>"+this.label+"</label>"));
					 tdValue.append($("<input type='text' name="+this.name+" id="+this.name+" class='text ui-widget-content ui-corner-all' />")); 
					 row.append(tdName);
					 row.append(tdValue);
					 table1.append(row);
				 }
				 
			});
		 }
	});
				
	$("#goToTargetNode").click(function(){
		alert("goToTargetNode");
		$("#targetNodeSelected").attr("src","../ciMgr/ciSelected.jsp?isSource=false");
	});
	
	
});
</script>


</body>
</html>