<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%
String typeName=(String)request.getParameter("typeName");
%>
<HTML>
<HEAD>
<TITLE></TITLE>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>Smart Wizard 2 - Basic Example  - a javascript jQuery wizard control plugin</title>
 		<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/jqueryFormwizard/css/ui-lightness/jquery-ui-1.8.2.custom.css" /> 
 		<script src="js/jquery/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
    <script src="js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jqueryFormwizard/jquery-1.4.2.min.js"></script>		
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jqueryFormwizard/jquery.form.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jqueryFormwizard/jquery.validate.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jqueryFormwizard/bbq.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jqueryFormwizard/jquery-ui-1.8.5.custom.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/resources/jqueryFormwizard/jquery.form.wizard.js"></script>
    <script type="text/javascript"  src="${pageContext.request.contextPath}/resources/jqueryValidation/messages_zh.min.js"></script>
    
    <script type="text/javascript">
			$(function(){
				
				 $("#demoForm").submit(function() {
					// var data = $("form").serialize();
						//alert($("#sourceNodeId").val()+"----"+$("#sourceNode").val()+"---"+$("#sourceType").val());
						//alert($("#targetNodeId").val()+"===="+$("#targetNode").val()+"==="+$("#targetType").val());
						//alert(data);
						var label = $("#confname").val();
						var name = $("#sourceNode").val()+"_"+$("#targetNode").val();
						alert(label+"-------------"+name);
						$.ajax({
							  type: "POST",
							  url: "addRelationCi",
							  data: {
							    label:label,
							    name: name,
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
								  window.parent.closeDialogMethod();
								  if(result=='0'){
									  //afterLoad();
								  }else{
									  alert(message);
								  }
							  }
							  
							});
					 
		            });
				
				$( "#sourceNodeId" ).hide();
				$( "#sourceType" ).hide();
				$( "#sourceNode" ).hide();
				$( "#targetNodeId" ).hide();
				$( "#targetType" ).hide();
				$( "#targetNode" ).hide();
				
				
				
				var formoptions = {
						success: function(data){
							  var message = data.success;
							  var result = data.result;
							  alert(message);
							  window.parent.closeDialogMethod();
							  if(result=='0'){
								  //afterLoad();
							  }else{
								  alert(message);
							  }
							},
						dataType: 'json',
						type: "POST",
						url: "addRelationCi",
						data: {
						    label:$("#confname").val(),
						    name: $("#sourceNode").val()+"_"+$("#targetNode").val(),
						    typeName : "<%=typeName%>",
						    sourceNodeId: $("#sourceNodeId").val(),
						    sourceNode: $("#sourceNode").val(),
						    sourceType: $("#sourceType").val(),
						    targetNodeId: $("#targetNodeId").val(),
						    targetNode: $("#targetNode").val(),
						    targetType: $("#targetType").val()
						  },
						async:false,
						resetForm: true
				 }
				
				$("#demoForm").formwizard({ 
				 	formPluginEnabled: true,
				 	validationEnabled: true,
				 	focusFirstInput : true,
				 	textBack:"上一步",
				 	textNext:"下一步",
				 	textSubmit:"提交",
				 	formOptions :formoptions,
				 	validationOptions : {
				 		rules: {
				 			sourceNameLabel: "required",
				 			tagetNameLabel: "required",
						},
						messages: {
							sourceNameLabel: "必选选择原节点！",
							tagetNameLabel: "必须选择目标节点！"
						}
				 	}
				 }
				);
				//获取关系
				getRelationSelect();
				
				$("#sourceNodeSelected").attr("src","../ciMgr/ciSelected.jsp?isSource=true");

				$("#targetNodeSelected").attr("src","../ciMgr/ciSelected.jsp?isSource=false");
				
				
				// initial call to addVisualization (for visualizing the first step)
				//addVisualization($("#demoForm").formwizard("state").firstStep);

				// bind a callback to the step_shown event
				$("#demoForm").bind("step_shown", function(event, data){
					$.each(data.activatedSteps, function(){
						//alert(this);
						//设置民参股恒
						if(this == "confirmation"){
							$("#instancelabel").val($("#sourceNode").val()+"_"+$("#targetNode").val());
							$("#sourceNameLabel").val($("#sourceNode").val());
							$("#tagetNameLabel").val($("#targetNode").val());
							//设置关系
							$("#relName").val($("#re_select").val());
							$("#confnameLabel").val($("#confname").val());
							$("#name").val($("#sourceNode").val()+"_"+$("#targetNode").val());
							$("lable").val($("#confname").val());
						}
					});
				});
  		});
			
			
			//加载下拉框信息
			  function getRelationSelect(){
				  $.ajax({
					  type: "POST",
					  url: "getRelationList",
					  data: {
					    typeName : "<%=typeName%>"
					  },
					  async:false,
					  dataType: "json",
					  success: function( data, textStatus, jqXHR ) {
						  //colModelData = data;
						  //动态创建select
						  $(data).each(function(){
							  $("#re_select").append("<option value='"+this.name+"'>"+this.label+"</option>");
						  });
						  
					  }
					  
					});
			  }
			  
    </script> 
    <style type="text/css">
			#demoWrapper {
				padding : 1em;
				
				border-style: solid;
			}

			#fieldWrapper {
			}

			#demoNavigation {
				margin-top : 0.5em;
				margin-right : 1em;
				text-align: right;
			}
			
			#data {
				font-size : 0.7em;
			}

			input {
				margin-right: 0.1em;
				margin-bottom: 0.5em;
			}

			.input_field_25em {
				width: 2.5em;
			}

			.input_field_3em {
				width: 3em;
			}

			.input_field_35em {
				width: 3.5em;
			}

			.input_field_12em {
				width: 12em;
			}

			label {
				margin-bottom: 0.2em;
				font-weight: bold;
				font-size: 0.8em;
			}

			label.error {
				color: red;
				font-size: 0.8em;
				margin-left : 0.5em;
			}

			.step span {
				float: right;
				font-weight: bold;
				padding-right: 0.8em;
			}

			.navigation_button {
				width : 70px;
			}
			
			#data {
					overflow : auto;
			}
		</style>
	</head>
  <body>
		<div id="demoWrapper" width="100%">
			<form id="demoForm" method="post" class="bbq" width="100%">
				<div id="fieldWrapper">
				<div class="step" id="first" width="100%">
					<span class="font_normal_07em_black">已选择关系</span><br />
					<label for="firstname">配置项关系</label><br />
					<select name="re_select" id="re_select" style="width: 150px;" class="required" >	
	                   <option value=""></option>
                   </select><br />
                   <label for="firstname">配置项名称</label><br />
					<input type="text" id="confname" name="confname" class="input_field_12em required" disabled="disabled"> <br />
                <br />
				</div>
				<div id="second" class="step">
					<span class="font_normal_07em_black">选择原节点</span><br />
					<div id="sourceNodeDiv" title="标题">
					<!--  -->
     				     <iframe scrolling="auto" id='sourceNodeSelected' frameborder="0"  src="../ciMgr/ciSelected.jsp?isSource=true" style="width:100%;height:330px;"></iframe>
				   </div> 
		        <input type="text" name="sourceNodeId" id="sourceNodeId" class="text ui-widget-content ui-corner-all" /></br>
		        <input type="text" name="sourceType" id="sourceType" class="text ui-widget-content ui-corner-all" /></br>
		        <input type="text" name="sourceNode" id="sourceNode" class="text ui-widget-content ui-corner-all" /></br> 						
				</div>
				<div id="third" class="step">
					<span class="font_normal_07em_black">选择目标节点</span><br />
					<div id="targetNodeDiv" class="easyui-window" title="标题" width="100%">
     				<iframe scrolling="auto" id='targetNodeSelected' frameborder="0"  src="" style="width:100%;height:330px;"></iframe>
				</div>
		<input type="text" name=targetNodeId id="targetNodeId" class="text ui-widget-content ui-corner-all" /></br>		
		<input type="text" name="targetType" id="targetType" class="text ui-widget-content ui-corner-all" /></br>		
		<input type="text" name="targetNode" id="targetNode" class="text ui-widget-content ui-corner-all" /></br>  						
				</div>
				<div id="confirmation" class="step">
					<span class="font_normal_07em_black">预览信息</span><br />
					<label for="releationLabel">关系</label><br />
					<input type="text" id="relName" name="relName" class="input_field_12em" readonly="readonly"> <br />
					
					<label for="releationLabel">配置项名称</label><br />
					<input type="text" id="confnameLabel" name="confnameLabel" class="input_field_12em" readonly="readonly"> <br />
					
					<label for="instanceNameLabel">实例名称</label><br />
					<input type="text" id="instancelabel" name="instancelabel" class="input_field_12em" readonly="readonly"> <br />
					
					<label for="sourceNodeLabel">原节点</label><br />
					<input type="text" id="sourceNameLabel" name="sourceNameLabel" class="input_field_12em required" readonly="readonly"> <br />
					
					<label for="targetNodeLabel">目标节点</label><br />
					<input type="text" id="tagetNameLabel" name="tagetNameLabel" class="input_field_12em required" readonly="readonly"> <br/>
					<input type="text" id="name" name="name" hidden="true">
					<input type="text" id="label" name="label" hidden="true">
				</div>
				</div>
				<div id="demoNavigation"> 							
					<input class="navigation_button" id="back" value="上一步" type="reset" />
					<input class="navigation_button" id="next" value="下一步" type="submit"/>
				</div>
			</form>
			<hr />
			
			<p id="data"></p>
		</div>
    
	</body>
</html>
