<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
 <head> 
  <meta charset="utf-8" /> 
  <title>Vcenter Dashboard</title> 
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
  <meta name="format-detection" content="telephone=no" /> 
  <meta name="apple-mobile-web-app-capable" content="yes" /> 

  <script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxprogressbar.js"></script> 
  <script type="text/javascript" src="${ctx}/resources/js/jquery.easy-pie-chart.js"></script> 
  <script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxdata.js"></script> 	
	

 </head> 
 <body> 
  <input id="moId" style="display:none" value="<%=request.getParameter("moId")%>">

<div style="margin:4px;height: 600px;overflow:hidden"> 
   <div class="left baseBox">
    <!-- 第一列--> 
    <!-- 第一列—box1
    							  ================================================== --> 
    <div class="BaseInfoPerMain"> 
     <div class="left">
      <img src="${ctx}/resources/images/iLo_icon.png" width="32" height="32" />
     </div> 
     <div class="left BasePerBox">
       主机名
      <p class="fB f14px" id="hostName"></p> IP
      <p id="IPAdd"></p> 
      <ul> 
       <li><span class="title">主机厂商</span><span class="content" id="Manufacturer"></span></li> 
       <li><span class="title">CPU个数</span><span class="content" id="CpuCount"></span></li> 
       <li><span class="title">CPU型号</span><span class="content" id="CpuType"></span></li> 
       <li><span class="title">内存</span><span class="content" id="MemSize"></span></li> 
       <li><span class="title">系统</span><span class="content" id="OSVersion"></span></li> 
      </ul> 
     </div> 
    </div> 
    <!-- 第一列—box2 --> 
    <div class="BaseInfoPerMain"> 
     <div class="BaseInfoPerBoxHeader">
      <b class="Pos-R Icon_ErrorLog Icon16 marginT8"></b>
      <span class="fB">Error Log</span>
     </div> 
     <div class="BaseInfoPerBoxF"> 
      <ul> 
       <li><b id="leftLogclass" class="Icon_status_red Icon16 Pos-R marginL8 left"></b><span class="title left darkblue W60" style="width:110px" id="leftLogLabel"></span><span id="leftLogNum" class="content right W20" style="width:60px;"></span></li> 
       <li><b id="centerLogclass" class="Icon_status_red Icon16 Pos-R marginL8 left"></b><span class="title left darkblue W60" style="width:110px" id="centerLogLabel"></span><span id="centerLogNum" class="scontent right W20" style="width:60px"></span></li> 
       <li><b id="rightLogclass" class="Icon_status_red Icon16 Pos-R marginL8 left"></b><span class="title left darkblue W60" style="width:110px" id="rightLogLabel"></span><span id="rightLogNum" class="content right W20" style="width:60px"></span></li> 
      </ul> 
     </div> 
    </div> 
    <!-- 第一列—box3 --> 
    <div class="BaseInfoPerMain"> 
     <div class="BaseInfoPerBoxHeader">
      <b class="Pos-R Icon_Network Icon16 marginT8"></b>
      <span class="fB">Network</span>
     </div> 
     <div class="BaseInfoPerBoxS"> 
      <ul id="networks"> 
       <!--  <li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                           --> 
       <!--<li><b class="Icon_status_green Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                             <li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                            <li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                            <li><b class="Icon_status_green Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>
                            <li><b class="Icon_status_red Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">Rad Hat VirtIo Ethernet...</span><span class="left gray marginL30">82936456.00b/s</span></li>                          
                            --> 
      </ul> 
     </div> 
    </div> 
   </div> 
   <div class="left W10"> 
    <!-- 第二列--> 
    <!--                          <div class="H28"></div>
                          <div class="H28"></div>--> 
    <div style="width:115px;" class="M-Top-300"> 
     <p class="f10px">Accumulated Pkgts Sent:</p> 
     <p class="f10px" id="accSend">0m/s</p> 
     <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10" /></p> 
     <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10" /></p> 
     <p class="f10px">Accumulated Pkgts Received:</p> 
     <p class="f10px" id="accReceived">0m/s</p> 
    </div>  
   </div> 
   <div class="left baseBox">
    <!-- 第三列--> 
    <!-- 第三列—box1
    							  ================================================== --> 
    <div class="BaseInfoPerMain"> 
     <div class="BaseInfoPerBoxHeader">
      <b class="Pos-R Icon_Progress Icon16 marginT8"></b>
      <span class="fB">Process</span>
     </div> 
     <div class="BaseInfoPerBoxF"> 
      <div class="left margin4 W42"> 
       <div class="chart"> 
        <div id="propercentage" class="percentage" data-percent="0">
         <span id="proper">0</span>%
        </div> 
        <div class="label">
         进程百分比
        </div> 
       </div> 
      </div> 
      <div class="left margin10 W42"> 
       <span class="Number-Main" id="processCount">0</span>
       <span class="Extra-Main">个</span> 
       <span class="Number-title">进程数量</span> 
      </div> 
     </div> 
    </div> 
    <!-- 第三列—box2 --> 
    <div class="BaseInfoPerMain"> 
     <div class="BaseInfoPerBoxHeader">
      <b class="Pos-R Icon_MeM Icon16 marginT8"></b>
      <span class="fB">MEM</span>
     </div> 
     <div class="BaseInfoPerBoxF"> 
      <div class="left margin4 W42"> 
       <div class="chart"> 
        <div id="mempercentage" class="percentage" data-percent="0">
         <span id="memper">0</span>%
        </div> 
        <div class="label">
         内存使用率
        </div> 
       </div> 
      </div> 
      <div class="left margin10 W42"> 
       <span class="Number-Main" id="remainMem"></span>
       <span class="Extra-Main"></span> 
       <span class="Number-title">剩余内存</span> 
      </div> 
     </div> 
    </div> 
    <!-- 第三列—box3 --> 
    <div class="BaseInfoPerMain"> 
     <div class="BaseInfoPerBoxHeader">
      <b class="Pos-R Icon_CPU Icon16 marginT8"></b>
      <span class="fB">CPU</span>
     </div> 
     <div class="BaseInfoPerBoxF"> 
      <div class="left margin4 W42"> 
       <div class="chart"> 
        <div id="cpupercent" class="percentage" data-percent="0"></div> 
        <div class="label">
         CPU使用率
        </div> 
       </div> 
      </div> 
      <div class="left margin10 W42"> 
       <span class="Number-Main" id="cpuUse"></span>
       <span class="Top-Main">%</span> 
       <span class="Number-title">CPU使用率</span> 
      </div> 
     </div> 
    </div> 
   </div> 
   <div class="left W10"> 
    <!-- 第四列--> 
    <div class="statusbox H28"> 
     <p class="f10px">Write p/s:</p> 
     <p class="f10px" id="write1">0</p> 
     <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10" /></p> 
     <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10" /></p> 
     <p class="f10px">Read p/s:</p> 
     <p class="f10px" id="read1">0</p> 
    </div> 
    <div class="H28 statusbox"> 
     <p class="f10px">Write p/s:</p> 
     <p class="f10px" id="write2">0</p> 
     <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10" /></p> 
     <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10" /></p> 
     <p class="f10px">Read p/s:</p> 
     <p class="f10px" id="read2">0</p> 
    </div> 
   </div> 
   <div class="left baseBox" style="width:27%;">
    <!-- 第五列--> 
    <div id="organic-tabs" class="BaseInfoPerMain"> 
     <ul id="explore-nav" class="left BaseInfoPerBoxT"> 
      <li id="ex-titleswap"><a rel="titleswap" href="#" class="current"><span>Swap</span></a></li> 
      <li id="ex-titledisk"><a rel="titledisk" href="#"><span>Disk</span></a></li> 
      <li id="ex-titlefile"><a rel="titlefile" href="#"><span>File System</span></a></li> 
     </ul> 
     <div id="all-list-wrap"> 
      <ul id="titleswap"> 
       <li> <p>路径</p> <p class="darkblue" id="swappath"></p> <p class="blank10"></p> <p>使用率</p> 
        <div style="margin-top: 2px;">
         <span class="left w50 f10px darkblue" id="swapfree"></span>
         <span class="w50 right f10px table-right gray" id="swapsize"></span>
        </div> 
        <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="swaputilpro"></div><span class="f10px darkblue" id="swaputil"></span> </li> 
      </ul> 
      <ul id="titledisk"> 
       <!--  <li>
                    <p>名称:</p>
                    <p class="darkblue" id="diskname"></p>
                    <p class="blank10"></p>
                    <p>使用率:</p>
                    <div style='margin-top: 2px;'><span class="left w50 f10px darkblue" id="disksize"></span><span class="w50 right f10px table-right gray" id="swapallsize">8192.0 MB</span></div>
                     <div style='margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;' id='diskPro'></div><span class="f10px darkblue" id="diskutil"></span>
                  </li> --> 
      </ul> 
      <ul id="titlefile"> 
      </ul> 
     </div> 
     <!-- END List Wrap --> 
    </div> 
   </div> 
  </div> 

<script type="text/javascript">
	$(document).ready(function() {
		var moId = $("#moId").val();

		$('.percentage').easyPieChart({
            animate: 1000
        });
		
		$.getJSON("${ctx}/performance/getBasicInfoData?moId=" + moId,
		function(data) {
		    $("#hostName").text(data.basicData[0].VAL);
		    $("#IPAdd").text(data.basicData[1].VAL);
		    $("#Manufacturer").text(data.basicData[2].VAL);
		    $("#CpuCount").text(data.basicData[3].VAL);
		    $("#CpuType").text(data.basicData[4].VAL);
		    $("#MemSize").text(data.basicData[5].VAL);
		    $("#OSVersion").text(data.basicData[6].VAL);
		});
		$.getJSON("${ctx}/performance/networkInfo?moId=" + moId,
		function(data) {
		    $("#networks").empty();
		    for (var i = 0; i < data.root.length; i++) {
		        var $htmlLi = $('<li><b class="Icon_status_green Icon16 Pos-R  marginL8 left"></b><span class="left darkblue W80">' + data.root[i].NAME + '</span><span class="left gray marginL30">' + data.root[i].IO + '</span></li>');
		        $("#networks").append($htmlLi);
		    }
		});
		$.getJSON("${ctx}/performance/getLogError?moId=" + moId,
		function(data) {
		    $("#centerLogLabel").text(data.centerLogLabel);
		    $("#rightLogLabel").text(data.rightLogLabel);
		    $("#leftLogLabel").text(data.leftLogLabel);
		    if (data.centerLogNum == 0) {
		        $("#centerLogclass").attr("class", "Icon_status_green Icon16 Pos-R left");
		    }
		    if (data.leftLogNum == 0) {
		        $("#leftLogclass").attr("class", "Icon_status_green Icon16 Pos-R left");
		    }
		    if (data.rightLogNum == 0) {
		        $("#rightLogclass").attr("class", "Icon_status_green Icon16 Pos-R left");
		    }
		    $("#centerLogNum").text(data.centerLogNum);
		    $("#rightLogNum").text(data.rightLogNum);
		    $("#leftLogNum").text(data.leftLogNum);

		});

		$.getJSON("${ctx}/performance/courseBasic?moId=" + moId,
		function(data) {
		    $('#proper').text(data.USR_PROC_NUM);
		    $('#propercentage').attr("data-percent", data.USR_PROC_Percent);
		    var chart = window.chart = $('#propercentage').data('easyPieChart');
		    chart.update(data.USR_PROC_Percent);
		});

		$.getJSON("${ctx}/performance/memBasic?moId=" + moId,
		function(data) {
		    $('#remainMem').text(data.FREE_MEM);
		    if (data.MEM_SYS.length == 0) {
		        $('#memper').text("0");
		    } else {
		        $('#memper').text(data.MEM_SYS.substring(0, data.MEM_SYS.length - 1));
		    }
		    var chart = window.chart = $('#mempercentage').data('easyPieChart');
		    var updateData = parseInt(data.MEM_SYS.substring(0, data.MEM_SYS.length - 1));
		    chart.update(updateData);
		});

		$.getJSON("${ctx}/performance/cpuUse?moId=" + moId,
		function(data) {
		    $('#cpuUse').text(data[0].CPU_USR);
		    var chart = window.chart = $('#cpupercent').data('easyPieChart');
		    chart.update(data[0].CPU_USR);
		});

		$.getJSON("${ctx}/performance/skipFileBasic?moId=" + moId,
		function(data) {
		    $('#swappath').text(data.SWAP_NAME);
		    $('#swapsize').text(data.SWAP_SIZE);
		    $('#swapfree').text(data.SWAP_FREE + "MB");
		    $('#swaputil').text(data.SWAP_UTIL);
		    $("#swaputilpro").jqxProgressBar({
		        width: '100%',
		        height: 10,
		        value: parseInt(data.SWAP_UTIL.substring(0, data.SWAP_UTIL.length - 1)),
		        animationDuration: 1000
		    });
		});


		$.getJSON("${ctx}/performance/diskUse?moId=" + moId,
		function(data) {
		    $("#titledisk").empty();

		    var $htmlli;

		    if (data.buckets.length == 0) {
		        return;
		    } else {
		        for (var j = 0; j < data.buckets.length; j++) {
		            $htmlli = $('<li><p>磁盘名称</p><p class="darkblue">' + data.buckets[j].name + '</p><p class="blank10"></p><p>磁盘繁忙比率</p>' + '<p class="darkblue">' + data.buckets[j].usage + '</p><p class="blank10"></p><p>读写速率</p><p class="darkblue">' + data.buckets[j].rwrate + '</p>' + '<p class="blank10"></p><p>每秒传输字节数</p><p class="darkblue">' + data.buckets[j].tranBytes + '</p><p class="blank10"></p></li>');
		        
		            $("#titledisk").append($htmlli);
		        }
		    }

		});

		$.getJSON("${ctx}/performance/fileSysUse?moId=" + moId,
		function(data) {
		    $("#titlefile").empty();
		    for (var i = 0; i < data.buckets.length; i++) {
		        var $htmlLi = $('<li><p>路径</p><p class="darkblue">' + data.buckets[i].name + '</p>' + '<p class="blank10"></p><p>使用率</p><div style="margin-top: 2px;"><span class="left w50 f10px darkblue">' + data.buckets[i].used + '</span><span class="w50 right f10px table-right gray">' + data.buckets[i].total + 'M</span></div>' + ' <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa;border-color: #daf0fa;" id="FilePro' + (i + 1) + '"></div><span class="f10px darkblue">' + data.buckets[i].usage + '</span></li>');

		        $("#titlefile").append($htmlLi);
		        $("#FilePro" + (i + 1)).jqxProgressBar({
		            width: '100%',
		            height: 10,
		            value: parseInt(data.buckets[i].usage.substring(0, data.buckets[i].usage.length - 1)),
		            animationDuration: 1000
		        });

		    }
		});

		$.getJSON("${ctx}/performance/networkSend?moId=" + moId,
		function(data) {
		    $('#accSend').text(data.speed);
		});
		$.getJSON("${ctx}/performance/networkReceive?moId=" + moId,
		function(data) {
		    $('#accReceived').text(data.speed);

		});
		$.getJSON("${ctx}/performance/diskRead?moId=" + moId,
		function(data) {
		    $('#read1').text(data.speed);
		});
		$.getJSON("${ctx}/performance/diskWrite?moId=" + moId,
		function(data) {
		    $('#write1').text(Math.floor(data.speed));

		});
		$.getJSON("${ctx}/performance/skipfilePagein?moId=" + moId,
		function(data) {
		    $('#read2').text(data.speed);

		});
		$.getJSON("${ctx}/performance/skipfilePageout?moId=" + moId,
		function(data) {
		    $('#write2').text(data.speed);
		});

		$("#explore-nav li a").click(function() {
            // Figure out current list via CSS class
            var curList = $("#explore-nav li a.current").attr("rel");
            
            // List moving to
            var $newList = $(this);
            
            // Set outer wrapper height to height of current inner list
            var curListHeight = $("#all-list-wrap").height();
            $("#all-list-wrap").height(curListHeight);
            
            // Remove highlighting - Add to just-clicked tab
            $("#explore-nav li a").removeClass("current");
            $newList.addClass("current");
            
            // Figure out ID of new list
            var listID = $newList.attr("rel");
            
            if (listID != curList) {
                // Fade out current list
                $("#"+curList).fadeOut(0, function() {
                    
                    // Fade in new list on callback
                    $("#"+listID).fadeIn();
                    
                    // Adjust outer wrapper to fit new list snuggly
                    var newHeight = $("#"+listID).height();
                    $("#all-list-wrap").animate({
                        height: newHeight
                    });
                
                });
                
            }        
            
            // Don't behave like a regular link
            return false;
        });
	});
</script>  
 </body>
</html>