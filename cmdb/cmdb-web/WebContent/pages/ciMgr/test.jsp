<!DOCTYPE html>
<html>
<head>
<script src="js/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>
</script>
<script src="js/jquery/jquery-ui-1.10.3.custom.min.js"
	type="text/javascript"></script>
<link rel="stylesheet" type="text/css" media="screen"
	href="css/redmond/jquery-ui-1.10.3.custom.min.css" />
<script>
$(document).ready(function(){
	$("#dialog-form").dialog({
		autoOpen:true,
		modal:true,     
		  height:280,
		  width:540,
		buttons:{login:function(){
			alert('you click the login button');
			}
		}
	});
});   
	function open_dialog(){  
		$("#dialog-form").dialog("open"); 
	}

</script>
</head>
<body>
<div id="dialog-form" class="ui-widget ui-widget-content ui-corner-all ui-jqdialog jqmID1" hidden="true" closed="true" modal="true" title="aaa" style="width:100%;height:100%;padding-top: 24px; top: 4px; left: 4px;"  >  
<p id="login_tip"></p> 
<form id="login-form"> 
<fieldset> username:<input type="text" name="name" id="name" class="ui-widget-content" />
<br> password:<input type="password" name="password" id="password" value="" class="ui-widget-content" /> 
<br> password:<input type="password" name="password" id="password" value="" class="ui-widget-content" /> 
<br> password:<input type="password" name="password" id="password" value="" class="ui-widget-content" /> 
<br> password:<input type="password" name="password" id="password" value="" class="ui-widget-content" /> 
<br> password:<input type="password" name="password" id="password" value="" class="ui-widget-content" /> 
<br> password:<input type="password" name="password" id="password" value="" class="ui-widget-content" /> 
<br> password:<input type="password" name="password" id="password" value="" class="ui-widget-content" /> 
</fieldset>
</form>
</div>  <input type="button" value="login" onclick="open_dialog();"/>
</body>
</html>