<%@ page language="java" pageEncoding="UTF-8"%>
<%@ include file="../commons/commonInclude.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<script type="text/javascript">

$(document).ready(function(){
	 $("#showLabel").hide();
	 $("#textId").hide();

});


      function startSync(){
    	  
    	
    	 var opFlag = getChecked();
    	 changeStatus(true);
    	  $.ajax({
    		  type: "POST",
    		  url: "startSyncOperation",
    		  data: {
    			  opFlag: opFlag
    		  },
    		  async:true,
    		  success: function( data, textStatus, jqXHR ) {
   			      if(opFlag){
   			    	alert("请预览同步数据");
   			      }else{
   			    	alert("数据同步成功！");
   			      }
   			      changeStatus(false);
                  $("#textId").text(data);
    		  }
    		  
    		});
      }
      
      function changeStatus(flag){
    	  if(flag){
    		  $("#showLabel").show();
    		  $("#textId").text("");
    		  $("#textId").hide();
    		  $("#subLabel").attr("disabled","disabled");
    	  }else{
    		  $("#showLabel").hide();
    		  $("#subLabel").removeAttr("disabled");
    		  $("#textId").show();
    	  }
      }
      
      function getChecked(){
    	 var checkedVal = $("input[name=ck]:checked","#syncForm").val();
    	 var opFlag = (checkedVal ==1?false:true);
    	 if(opFlag){
    		 $("#showLabel").text("数据同步中请稍等......");
    	 }else{
    		 $("#showLabel").text("加载中请稍等......");
    	 }
    	 return opFlag;
      }
</script>
<title>Insert title here</title>
</head>
<body>
<H1>
<form id="syncForm">
<div style="width: 100%">
<input type="radio" name="ck" value="1" checked="checked">开始同步
<input type="radio" name="ck" value="0">开始预览<br>
<input type="button" id="subLabel" onclick="startSync();" value="提交"><br>
<label id="showLabel" style="width: 300px">数据同步中请稍等......</label><br>
<textarea id="textId" style="width: 900px;height: 500px;">
</textarea>
</div>


</form>

</H1>
</body>
</html>