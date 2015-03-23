<!DOCTYPE HTML>
<%@ page language="java" contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html lang="zh">
<head>
<!-- Force latest IE rendering engine or ChromeFrame if installed -->
<!--[if IE]>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<![endif]-->
<meta charset="utf-8">
<title>简单网络管理</title>
<meta name="description"
	content="File Upload widget with multiple file selection, drag&amp;drop support, progress bars, validation and preview images, audio and video for jQuery. Supports cross-domain, chunked and resumable file uploads and client-side image resizing. Works with any server-side platform (PHP, Python, Ruby on Rails, Java, Node.js, Go etc.) that supports standard HTML form file uploads.">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="IE=9">

<!-- CSS -->
<link rel="stylesheet" type="text/css" href="../resources/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="../resources/css/global.css">
<link rel="stylesheet" type="text/css" href="../resources/css/Main.css">
<link rel="stylesheet" type="text/css"
	href="../resources/css/AutoSearch.css">
<link rel="shortcut icon" href="../resources/images/favicon.ico">
<link href="../resources/css/jquery.mCustomScrollbar.css"
	rel="stylesheet" type="text/css" />

<!-- jqwidgets -->
<link rel="stylesheet"
	href="../resources/js/jqwidgets/styles/jqx.base.css" type="text/css" />
<script type="text/javascript"
	src="../resources/js/jquery-1.10.0.min.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxcore.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxdata.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxbuttons.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxmenu.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxcheckbox.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxradiobutton.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxlistbox.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxdropdownlist.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxdropdownbutton.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxgrid.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxgrid.sort.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxgrid.pager.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxgrid.selection.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxgrid.edit.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxwindow.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxpanel.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxtree.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxlistbox.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxcombobox.js"></script>
<script type="text/javascript"
	src="../resources/js/jqwidgets/jqxmenu.js"></script>
<script type="text/javascript" src="../resources/js/demos.js"></script>


<!-- Bootstrap styles -->
<link rel="stylesheet" href="../resources/css/bootstrap.min.css">
<!-- Generic page styles -->
<link rel="stylesheet" href="../resources/css/style.css">
<!-- blueimp Gallery styles -->
<link rel="stylesheet"
	href="../resources/css/blueimp-gallery.min.css">
<!-- CSS to style the file input field as button and adjust the Bootstrap progress bars -->
<link rel="stylesheet" href="../resources/css/jquery.fileupload.css">
<link rel="stylesheet" href="../resources/css/jquery.fileupload-ui.css">
<!-- CSS adjustments for browsers with JavaScript disabled -->
<noscript>
	<link rel="stylesheet"
		href="../resources/css/jquery.fileupload-noscript.css">
</noscript>
<noscript>
	<link rel="stylesheet"
		href="../resources/css/jquery.fileupload-ui-noscript.css">
</noscript>

<style type="text/css">
.from-group {float: left}
.widget-title {height: 36px !important;}
.widget-title h5 {padding: 12px;}
</style>
</head>
<body>

	<div id="wrap">

 <!-- Header
      ================================================== -->
      <div id="top" class="top">
         <div class="fixed">
			
			<!-- <a href="../main"><div class="LOGO"></div></a> -->
            <a href="../main">
            	<div class="Avmon_back_gif">
            		<img id="Avmon_back_img" src="${ctx}/resources/images/back.png" 
						onmouseover="mouseover('${ctx}');" onmouseout="mouseout('${ctx}');">	
            	</div>
            </a>
            
            <div class="MainTitle f28px LineH12">简单网络管理</div>
            
            <!-- <div class="Profile" id="ShowUserBoxclick" style="cursor:pointer">
            <div class="ProfileIcon"></div>
            <div class="ProfileName ">My Account</div>
            <div class="ProfileArrow"></div>
            <div class="blank0"></div>
         </div> -->
      </div>

      <!-- showUserBox
      ================================================== -->
     <div id="ShowUserBoxMain" style="display:none">
        <div class="ShowUserBoxArrow"></div>
        <div class="ShowUserBox">
          <ul>
            <li id="changePwd"><a href="#"><span class="AccountSettingIcon"></span><span >修改密码</span></a></li>
			<li><a href="${ctx}/logout"><span class="SignOutIcon"></span><span>退出</span></a></li>
            <div class="blank0"></div>
          </ul>
        </div>
      </div>

      </div>
  

		<!-- Content container
      ================================================== -->
		<div id="container">
			<!-- Sidebar
      ================================================== -->
			<div id="sidebarAuto">
				<div>
					<div class="Tab_categoryAuto left">
						<ul>
							<li id="mib" class="moudleSelecter"><a href="#">MIB管理</a></li>
							<ul class="Tab_categoryAuto_sub">
								<li class="moudleSelecter"><a
									href="${ctx}/discovery/mibmgr">MIB管理</a></li>
								<li class="Tab_categoryAuto_active moudleSelecter"><a
									href="${ctx}/discovery/mibimport">MIB导入</a></li>
							</ul>
							<li id="type" class="moudleSelecter"><a
								href="${ctx}/discovery/motypeconfig">设备类型管理</a></li>
							<li id="mib" class="moudleSelecter"><a href="#">TRAP管理</a></li> 
                 <ul class="Tab_categoryAuto_sub">
                    <li><a href="${ctx}/discovery/trapmgr">告警规则配置</a></li>
                    <li><a href="${ctx}/trap/traprulemgr">规则管理</a></li>              
                 </ul>
						</ul>
					</div>
				</div>
			</div>

			<!-- content
      ================================================== -->
			<div class="content">

				<!-- NavAvmon
      ================================================== -->
				<!-- mib管理 -->
				<div id="mibDiv" class="tabDivs">
					<div class="widget-box">

						<div class="widget-title">
							<span class="icon"> <i class="icon-align-justify"></i>
							</span>
							<h5>MIB导入</h5>
						</div>

					<div style="display: block;">
						<div class="AvmonMain" style="font-size: 13px; font-family: Verdana; float: left;">
							<div class="container" style="margin-left: 0px;width:100%;">
								<!-- The file upload form used as target for the file upload widget -->
								<form id="fileupload" enctype="multipart/form-data">
									<!-- Redirect browsers with JavaScript disabled to the origin page -->
									<!-- <noscript>
										<input type="hidden" name="redirect"
											value="https://blueimp.github.io/jQuery-File-Upload/">
									</noscript> -->
									<div class="from-label">
											<span class="left paddingL30 paddingR10 LineH45">设备类型</span>
											<div class="left" style="margin-top: 10px" id="digits">
													<div style="border: none;" id='dropdownTypeTree'></div>
												</div>
												<input type="hidden" id="typeId" name="typeId" />
											<span class="left paddingL30 paddingR10 LineH45">设备名称</span>
											<div class="left" style="margin-top: 10px">
													<input type="text" id='deviceName' name='deviceName'/>
											</div>
											<div class="blank10"></div>
											<span class="left paddingL30 paddingR10 LineH45">生产厂商</span>
											<div class="left" style="margin-top: 10px">
													<input type="text" id='factory' name='factory'/>
											</div>
											<span class="left paddingL30 paddingR10 LineH45">设备描述</span>
											<div class="left" style="margin-top: 10px">
													<input type="text" id='deviceDesc' name='deviceDesc'/>
											</div>
											<div class="blank10">
											</div>
									</div>
									<!-- The fileupload-buttonbar contains buttons to add/delete files and start/cancel the upload -->
									<div class="row fileupload-buttonbar">
										<div class="col-lg-7">
											<!-- The fileinput-button span is used to style the file input field as button -->
											<a href="#" class="AvmonOverlayButton fileinput-button">添加文件...<input type="file" name="files[]" multiple></a>
											
											<button type="submit" id="uploadBtn" class="AvmonOverlayButton start">
											    <span>开始上传</span>
											</button>
											<button type="reset" class="AvmonOverlayButton cancel">
												<span>取消上传</span>
											</button>
											<!-- The global file processing state -->
											<span class="fileupload-process"></span>
										</div>
										<!-- The global progress state -->
										<div class="col-lg-5 fileupload-progress fade">
											<!-- The global progress bar -->
											<div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100">
												<div class="progress-bar progress-bar-success"
													style="width: 0%;"></div>
											</div>
											<!-- The extended global progress state -->
											<div class="progress-extended">&nbsp;</div>
										</div>
									</div>
									<!-- The table listing the files available for upload/download -->
									<div style="overflow-y: scroll; height:310px;margin-right: 20px;">
									<table role="presentation" class="table table-striped" width="100%">
										<tbody class="files"></tbody>
									</table>
									</div>
								</form>
								<br>
							</div>
							<!-- The blueimp Gallery widget -->
							<div id="blueimp-gallery"
								class="blueimp-gallery blueimp-gallery-controls" data-filter=":even">
								<div class="slides"></div>
								<h3 class="title"></h3>
								<a class="prev">‹</a> <a class="next">›</a> <a class="close">×</a>
								<a class="play-pause"></a>
								<ol class="indicator"></ol>
							</div>
						</div>
					</div>
						<div class="AvmonButtonAreaAuto" style="clear: both;"/></div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<script type="text/javascript">
        $(document).ready(function () {
            $("#digits").jqxDropDownButton({ width: 180, height: 29});
            
            var tree = $('#dropdownTypeTree');
            var source = null;
            $.ajax({
                async: false,
                url: "${ctx}/discovery/getTypeTree",
                success: function (data, status, xhr) {
                	if(data){
                		source = jQuery.parseJSON(data);
                	}
                }
            });
            // tree.jqxTree({ source: source,  height: 300, width: 200 });
            tree.on('expand', function (event) {
                var label = tree.jqxTree('getItem', event.args.element).label;
                var pid = tree.jqxTree('getItem', event.args.element).id;
                var $element = $(event.args.element);
                var loader = false;
                var loaderItem = null;
                var children = $element.find('ul:first').children();
                $.each(children, function () {
                    var item = tree.jqxTree('getItem', this);
                    if (item && item.label == 'Loading...') {
                        loaderItem = item;
                        loader = true;
                        return false
                    };
                });
            });
       	
           // create data adapter.
           var dataAdapter = new $.jqx.dataAdapter(source);
           // perform Data Binding.
           dataAdapter.dataBind(); 
           // get the tree items. The first parameter is the item's id. The second parameter is the parent item's id. The 'items' parameter represents 
           // the sub items collection name. Each jqxTree item has a 'label' property, but in the JSON data, we have a 'text' field. The last parameter 
           // specifies the mapping between the 'text' and 'label' fields.  
           var records = dataAdapter.getRecordsHierarchy('id', 'parentid', 'items', [{ name: 'text', map: 'label'}]);
           $('#dropdownTypeTree').jqxTree({ source: records,width: '300px'});
            
            
            $('#dropdownTypeTree').on('select', function (event) {
                var args = event.args;
                var item = $('#dropdownTypeTree').jqxTree('getItem', args.element);
                var dropDownContent = '<div style="position: relative; margin-left: 3px; margin-top: 5px;">' + item.label + '</div>';
                $("#digits").jqxDropDownButton('setContent', dropDownContent);
                $("#typeId").val(item.id);
                $('#fileupload').fileupload('enable');
            });
            $("#dropdownTypeTree").jqxTree({ width: 180, height: 220});
            
            // 验证信息
            //console.log($(".start").length);
            $(".fileinput-button").bind('click',function(){
            	 if(!$("#typeId").val()){
             		alert("请选择设备类型!");
             		$('#fileupload').fileupload('disable');
             	}
            });
        });
    </script>
    
	<!-- The template to display files available for upload -->
	<script id="template-upload" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-upload fade">
        <td>
            <span class="preview"></span>
        </td>
        <td>
            <p class="name">{%=file.name%}</p>
            <strong class="error text-danger"></strong>
        </td>
        <td>
            <p class="size">Processing...</p>
            <div class="progress progress-striped active" role="progressbar" aria-valuemin="0" aria-valuemax="100" aria-valuenow="0"><div class="progress-bar progress-bar-success" style="width:0%;"></div></div>
        </td>
        <td>
            {% if (!i && !o.options.autoUpload) { %}
                <button class="AvmonOverlayButton start" disabled>
                    <span>开始</span>
                </button>
            {% } %}
            {% if (!i) { %}
                <button class="AvmonOverlayButton cancel">
                    <span>取消</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
	<!-- The template to display files available for download -->
	<script id="template-download" type="text/x-tmpl">
{% for (var i=0, file; file=o.files[i]; i++) { %}
    <tr class="template-download fade">
        <td>
            <span class="preview">
                {% if (file.thumbnailUrl) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" data-gallery><img src="{%=file.thumbnailUrl%}"></a>
                {% } %}
            </span>
        </td>
        <td>
            <p class="name">
                {% if (file.url) { %}
                    <a href="{%=file.url%}" title="{%=file.name%}" download="{%=file.name%}" {%=file.thumbnailUrl?'data-gallery':''%}>{%=file.name%}</a>
                {% } else { %}
                    <span>{%=file.name%}</span>
                {% } %}
            </p>
            {% if (file.error) { %}
                <div><span class="label label-danger">Error</span> {%=file.error%}</div>
            {% } %}
        </td>
        <td>
            <span class="size">{%=o.formatFileSize(file.size)%}</span>
        </td>
        <td>
            {% if (file.deleteUrl) { %}
                <button class="AvmonOverlayButton delete" data-type="{%=file.deleteType%}" data-url="{%=file.deleteUrl%}"{% if (file.deleteWithCredentials) { %} data-xhr-fields='{"withCredentials":true}'{% } %}>
                    <span>删除</span>
                </button>
                <input type="checkbox" name="delete" value="1" class="toggle">
            {% } else { %}
                <button class="AvmonOverlayButton cancel">
                    <span>移除</span>
                </button>
            {% } %}
        </td>
    </tr>
{% } %}
</script>
	<!-- The jQuery UI widget factory, can be omitted if jQuery UI is already included -->
	<script src="../resources/js/vendor/jquery.ui.widget.js"></script>
	<!-- The Templates plugin is included to render the upload/download listings -->
	<script src="../resources/js/tmpl.min.js"></script>
	<!-- The Load Image plugin is included for the preview images and image resizing functionality -->
	<script
		src="../resources/js/load-image.all.min.js"></script>
	<!-- The Canvas to Blob plugin is included for image resizing functionality -->
	<script
		src="../resources/js/canvas-to-blob.min.js"></script>
	<!-- Bootstrap JS is not required, but included for the responsive demo navigation -->
	<script
		src="../resources/js/bootstrap.min.js"></script>
	<!-- blueimp Gallery script -->
	<script
		src="../resources/js/jquery.blueimp-gallery.min.js"></script>
	<!-- The Iframe Transport is required for browsers without support for XHR file uploads -->
	<script src="../resources/js/jquery.iframe-transport.js"></script>
	<!-- The basic File Upload plugin -->
	<script src="../resources/js/jquery.fileupload.js"></script>
	<!-- The File Upload processing plugin -->
	<script src="../resources/js/jquery.fileupload-process.js"></script>
	<!-- The File Upload image preview & resize plugin -->
	<script src="../resources/js/jquery.fileupload-image.js"></script>
	<!-- The File Upload audio preview plugin -->
	<script src="../resources/js/jquery.fileupload-audio.js"></script>
	<!-- The File Upload video preview plugin -->
	<script src="../resources/js/jquery.fileupload-video.js"></script>
	<!-- The File Upload validation plugin -->
	<script src="../resources/js/jquery.fileupload-validate.js"></script>
	<!-- The File Upload user interface plugin -->
	<script src="../resources/js/jquery.fileupload-ui.js"></script>
	<!-- The main application script -->
	<script src="../resources/js/main.js"></script>
	<!-- The XDomainRequest Transport is included for cross-domain file deletion for IE 8 and IE 9 -->
	<!--[if (gte IE 8)&(lt IE 10)]>
<script src="js/cors/jquery.xdr-transport.js"></script>
<![endif]-->
<script src="${ctx}/pages/avmon_back_gif.js"></script>
 <script type="text/javascript">
   function show(oEvent){
     var UserBox=document.getElementById("ShowUserBoxMain");
     //alert(obj.style.display);
     if(UserBox.style.display=="block"){
     	  document.getElementById("ShowUserBoxMain").style.display = "none";
     	}else{
     		document.getElementById("ShowUserBoxMain").style.display = "block";
     	}
     
     e = window.event || oEvent;
     if (e.stopPropagation)
     {
         e.stopPropagation();
     }else{
         e.cancelBubble = true;
     }
   }
   function hide(){
     document.getElementById("ShowUserBoxMain").style.display = "none";
   }
   
   window.onload = function(){
   	
	  document.getElementById("ShowUserBoxMain").style.display = "none";
      document.body.onclick = hide;
	  document.getElementById("ShowUserBoxclick").onclick = show;
	  
   };
  </script>
</body>
</html>
