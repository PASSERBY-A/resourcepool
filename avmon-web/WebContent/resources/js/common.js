
$('#changePwd').bind('click', function() {
	$("#changePwdWin").jqxWindow({
		showCollapseButton : true,
		isModal : true,
		resizable : false,
		width : 300,
		initContent : function() {
			$('#changePwdWin').jqxWindow('focus');
		}
	});
	// show the popup window.
	$("#changePwdWin").jqxWindow('show');
});

$('#cancelChange').bind('click', function(
		) {
	$("#changePwdWin").jqxWindow('hide');
});
$('#confirmChange').bind('click', function() {
	
	var oldPassword= $("#oldPassword").val();
	var password=$("#password").val();
	$.get(ctx+'/updateUserPwd?account='+account+'&oldPassword='+oldPassword+'&password='+password, function(data) {
		if(data.success!=null){
			//alert(data.success);
			$().toastmessage('showToast', {
	            text     : '修改密码成功',
	            sticky   : false,
	            position : 'top-center',
	            type     : 'error',
	            closeText: ''
	        });
			$("#changePwdWin").jqxWindow('hide');
		}
		else if(data.error!=null){
			 $().toastmessage('showToast', {
		            text     : '原始密码错误',
		            sticky   : false,
		            position : 'top-center',
		            type     : 'error',
		            closeText: ''
		        });
			//$("#changePwdWin").jqxWindow('hide');
		}
		else{
			 $().toastmessage('showToast', {
		            text     : '系统错误',
		            sticky   : false,
		            position : 'top-center',
		            type     : 'error',
		            closeText: ''
		        });
			//$("#changePwdWin").jqxWindow('hide');
		}
	});
	
});
