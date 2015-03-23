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
   
 </head> 
 <body> 
 <input id="dashboardMoId" style="display:none" value="<%=request.getParameter("moId")%>">
 
  <div style="margin:4px; height: 600px;overflow:hidden"> 
   <div class="left baseBox W35">
    <!-- 第一列--> 
    <!-- 第一列—box1
    							  ================================================== --> 
    <div class="BaseInfoPerMain" style="height:96%"> 
     <div class="left">
      <img src="${ctx}/resources/images/Vcenter_icon.png" width="32" height="32" />
     </div> 
     <div class="left BasePerBox" style="height:96%"> 
      <p class="fB f14px">Vcenter</p> 
      <p>IP <span class="contentlong" id="vmIp"></span></p> 
      <ul> 
       <li><span class="titleLong">CPU Model</span><span class="contentlong" id="vmModel"></span></li> 
       <li><span class="titleLong">CPU频率</span><span class="contentlong" id="vmCpuMhz">MHz</span></li> 
<!--        <li><span class="titleLong">CPU内核数</span><span class="contentlong" id="vmCpuCore">个</span></li>  -->
       <li><span class="titleLong">内存大小</span><span class="contentlong" id="vmMem">Mb</span></li> 
       <li><span class="titleLong">CPU</span><span class="contentlong" id="vmCpuNum">个</span></li> 
       <li><span class="titleLong">网卡</span><span class="contentlong" id="vmNet">个</span></li> 
       <li><span class="titleLong">虚拟磁盘</span><span class="contentlong" id="vmDisk">个</span></li> 
       <li><span class="titleLong">所用资源池</span><span class="contentlong" id="vmUsedPool">个</span></li> 
       <li><span class="titleLong">电源状态</span><span class="contentlong" id="vmPower"></span></li> 
      </ul> 
     </div> 
    </div> 
   </div> 
   <div class="left W10"> 
    <!-- 第二列--> 
   </div> 
   <div class="left baseBox">
    <!-- 第三列--> 
    <!-- 第三列—box1
    							  ================================================== --> 
    <div class="BaseInfoPerMain"> 
     <div class="BaseInfoPerBoxHeader">
      <b class="Pos-R Icon_Network Icon16 marginT8"></b>
      <span class="fB">Network</span>
     </div> 
     <div class="BaseInfoPerBoxF marginL10"> 
      <ul> 
<!--        <li><span class="titleLong">网络名</span><span class="contentlong">network-576-network</span></li>  -->
<!--        <li><span class="titleLong">网卡数量</span><span class="contentlong">6个</span></li>  -->
       <li><span class="titleLong">所用网络</span><span class="contentlong" id="vmUsedNet"></span></li> 
       <li><span class="titleLong">平均使用量</span><span class="contentlong" id="vmNetUsedAvg"></span></li> 
      </ul> 
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
         <span id="vmMemUsage"></span>%
        </div> 
        <div class="label">
         内存使用率
        </div> 
       </div> 
      </div> 
      <div class="left margin10 W42"> 
       <span class="Number-Main" id="vmMemLeft"></span>
       <span class="Extra-Main">GB</span> 
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
        <div id="cpupercentage" class="percentage" data-percent="0"></div> 
        <div class="label">
         CPU使用率
        </div> 
       </div> 
      </div> 
      <div class="left margin10 W42"> 
       <span class="Number-Main" id="vmCpuUsage"></span>
       <span class="Top-Main">%</span> 
       <span class="Number-title">CPU使用率</span> 
      </div> 
     </div> 
    </div> 
   </div> 
   <div class="left W10">
    <!-- 第四列--> 
    <div class="statusbox H2"> 
    </div> 
    <div class="H28 statusbox"> 
     <p class="f10px">Write p/s:</p> 
     <p class="f10px" id="vmNetWrite"></p> 
     <p style="margin:6px 6px 1px 6px;"><img src="${ctx}/resources/images/arrow_G_01.gif" width="80" height="10" /></p> 
     <p style="margin:1px 6px 6px 6px;"><img src="${ctx}/resources/images/arrow_R_01.gif" width="80" height="10" /></p> 
     <p class="f10px">Read p/s:</p> 
     <p class="f10px" id="vmNetRead">3272599</p> 
    </div> 
   </div> 
   <div class="left baseBox" style="width:27%;">
    <!-- 第五列--> 
    <div id="organic-tabs" class="BaseInfoPerMain"> 
     <ul id="explore-nav" class="left BaseInfoPerBoxT"> 
      <li id="ex-titleswap"><a rel="titledisk" href="#" class="current"><span>Disk</span></a></li> 
     </ul> 
     <div id="all-list-wrap"> 
      <ul id="titleswap"> 
       <p class="f12px table-right"><span class="marginR4">共</span><span class="darkblue marginR4" id="vmDiskNum">0</span><span>个数据源</span></p> 
       <li> 
       <p>虚拟磁盘平均写入速度（KBps）</p> <p class="darkblue" id="vmDiskRead"></p> <p class="blank10"></p> 
       <p>虚拟磁盘平均读取速度（KBps）</p> <p class="darkblue" id="vmDiskWrite"></p> <p class="blank10"></p> 
       <p>虚拟机磁盘使用量(KBps)</p> <p class="darkblue" id="vmDiskUsage"></p> <p class="blank10"></p> 
<!--        <p>数据源大小</p>  -->
<!--         <div style="margin-top: 2px;"> -->
<!--          <span class="left w50 f10px darkblue">4192.0 MB</span> -->
<!--          <span class="w50 right f10px table-right gray">8192.0 MB</span> -->
<!--         </div>  -->
<!--         <div style="margin-top: 2px; overflow: hidden;background-color: #daf0fa; border-color: #daf0fa;" id="jqxProgressBar1"></div><span class="f10px gray right">剩余空间</span> <p class="blank10"></p> </li>  -->
      </ul> 
 
     </div> 
     <!-- END List Wrap --> 
    </div> 
   </div> 
  </div> 

<script type="text/javascript">
	$(document).ready(function() {
		var moId = $("#dashboardMoId").val();
	    // Create jqxProgressBar.
	    //$("#jqxProgressBar1").jqxProgressBar({width: '100%',height: 10,value: 23,animationDuration: 1000});
	    //$("#jqxProgressBar2").jqxProgressBar({width: '100%',height: 10,value: 23,animationDuration: 1000});
	
	    $('.percentage').easyPieChart({
	        animate: 1000
	    });   
	    
	    $.getJSON("${ctx}/vcenter/getVmBasicInfo?moId=" + moId,
        function(data) {
	    	$("#vmIp").text(data.vmIp);
	    	$("#vmModel").text(data.vmModel);
	    	$("#vmCpuMhz").text(data.vmCpuMhz);
	    	//$("#vmCpuCore").text(data.vmCpuCore);
	    	$("#vmCpuNum").text(data.vmCpuCore);
	    	$("#vmMem").text(data.vmMem);
	    	$("#vmNet").text(data.vmNet);
	    	$("#vmDisk").text(data.vmDisk);
	    	$("#vmUsedNet").text(data.vmUsedNet);
	    	$("#vmUsedPool").text(data.vmUsedPool);
	    	$("#vmPower").text(data.vmPower);
	    	$("#vmNetUsedAvg").text(data.vmNetUsedAvg);
	    	$("#vmMemUsage").text(data.vmMemUsage);
	    	$("#vmCpuUsage").text(data.vmCpuUsage);
	    	$("#vmNetRead").text(data.vmNetRead);
	    	$("#vmNetWrite").text(data.vmNetWrite);
	    	$("#vmMemLeft").text(data.vmMemLeft);
	    	
	    	$('#mempercentage').attr("data-percent", data.vmMemUsage);
		    $('#mempercentage').data('easyPieChart').update(data.vmMemUsage);
		    $('#cpupercentage').attr("data-percent", data.vmCpuUsage);
		    $('#cpupercentage').data('easyPieChart').update(data.vmCpuUsage);
		    
	    	$("#vmDiskNum").text(data.vmDiskNum);
	    	$("#vmDiskRead").text(data.vmDiskRead);
	    	$("#vmDiskWrite").text(data.vmDiskWrite);
	    	$("#vmDiskUsage").text(data.vmDiskUsage);
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