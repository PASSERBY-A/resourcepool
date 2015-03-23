<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%@ page import="java.util.Map"%>
<%Map menuMap=(Map)request.getSession().getAttribute("menuMap");
%>
<html>
<!DOCTYPE html>
<head>
<meta charset="utf-8">
<title>告警中心</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- CSS -->
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/reset.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/global.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/jquery.toastmessage.css">
<link rel="stylesheet" type="text/css"
	href="${ctx}/resources/css/Main.css">
<link rel="shortcut icon" href="${ctx}/resources/images/favicon.ico">
<link href="${ctx}/resources/css/jquery.mCustomScrollbar.css"
	rel="stylesheet" type="text/css" />
<!-- jqwidgets -->
 <link rel="stylesheet"
	href="${ctx}/resources/js/jqwidgets/styles/jqx.base.css"
	type="text/css" /> 

	
</head>

<body>
	<div id="wrap">
		<!-- Header
      ================================================== -->
		<div id="top" class="top">
			<div class="fixed">
			<!-- 
				<a href="${ctx}/main"><div class="LOGO"></div></a>
			 -->
			  
				<a href="${ctx}/pages/alarm/alermOverView">
					<div class="Avmon_back_gif">
						<img id="Avmon_back_img" src="${ctx}/resources/images/back.png" 
							onmouseover="mouseover('${ctx}');" onmouseout="mouseout('${ctx}');">	
					</div>
				</a>
				
				<div class="MainTitle f28px LineH12" id="ShowTitleMenuclick" style="cursor:pointer">告警中心</div>
				
				<!-- 
					修改：各子系统的账户信息去掉，所有的登出统一在首页操作
					BY：	lixinqi
					DATA：20150202 
				-->
				<%-- <div class="Profile" id="ShowUserBoxclick" style="cursor: pointer">
					<div class="ProfileIcon"></div>
					<div class="ProfileName ">${userName}</div>
					<div class="ProfileArrow"></div>
					<div class="blank0"></div>
				</div> --%>
			</div>
       <!-- showbottonBox
    ================================================== -->
    <div id="ShowbuttonBoxMain" style="display:none">
      <div class="ShowbuttonBoxArrow"></div>
      <div class="ShowbuttonBox">
        <ul>
          <li><a href="#"><span id="myAlarm">我的告警</span></a></li>
          <li><a href="#"><span id="newAlarm">新告警</span></a></li>
          <li><a href="#"><span id="deletedAlarms">已删除告警</span></a></li>
          <div class="blank0"></div>
        </ul>
      </div>
    </div>
     </div>
			<!-- showUserBox
      ================================================== -->
			<div id="ShowUserBoxMain" style="display: none">
				<div class="ShowUserBoxArrow"></div>
				<div class="ShowUserBox">
					<ul>
						<li id="changePwd"><a href="#"><span class="AccountSettingIcon"></span><span >修改密码</span></a></li>
						<li><a href="${ctx}/logout"><span class="SignOutIcon"></span><span>退出</span></a></li>
						<div class="blank0"></div>
					</ul>
				</div>
			</div>
			<div id="container">
			
			 <!-- showTitleMenu
       ================================================== -->
  
   <div id="ShowTitleMenu" style="display:none">
    <div class="showTitleMenuBoxArrowdown"></div>
    <div class="showTitleMenuBox">
      <ul>
        <%if(menuMap.get("ALARM_CENTER_MODULE").equals(0)){%>
        <li><a href="${ctx}/pages/alarm/alermOverView"><span class="SettingIcon"></span><span>告警中心</span></a></li>
        <%}%>
        <%if(menuMap.get("PERFORMANCE_MODULE").equals(0)){%>
        <li><a href="${ctx}/performance/overview"><span class="SettingIcon"></span><span>性能中心</span></a></li>
        <%}%>
        <%if(menuMap.get("DISCOVERY_MODULE").equals(0)){%>
        <li><a href="${ctx}/discovery/deviceScan"><span class="SettingIcon"></span><span>自动发现</span></a></li>
        <%}%>
        <%if(menuMap.get("NETWORK_MODULE").equals(0)){%>
        <li><a href="${ctx}/discovery/mibmgr"><span class="SettingIcon"></span><span>简单网络管理</span></a></li>
        <%}%>
        <%if(menuMap.get("REPORT_MODULE").equals(0)){%>
        <li><a href="${ctx}/report/index"><span class="SettingIcon"></span><span>报表查询</span></a></li>
        <%}%>
        <%if(menuMap.get("PERFORMANCE_ANALYZE_MODULE").equals(0)){%>
        <li><a href="${ctx}/kpiAnalysis/index"><span class="SettingIcon"></span><span>性能分析</span></a></li>
        <%}%>
        <%if(menuMap.get("AGENT_MODULE").equals(0)){%>
        <li><a href="${ctx}/deploy/index"><span class="SettingIcon"></span><span>Agent管理</span></a></li>
        <%}%>
        <%if(menuMap.get("CONFIG_MODULE").equals(0)){%>
        <li><a href="${ctx}/config/main"><span class="SettingIcon"></span><span>配置管理</span></a></li>
        <%}%>
        <%if(menuMap.get("RESOURCE_MODULE").equals(0)){%>
        <li><a href="${ctx}/equipmentCenter/index"><span class="SettingIcon"></span><span>资源管理</span></a></li>
        <%}%>
        <%if(menuMap.get("SYSTEM_MODULE").equals(0)){%>
        <li><a href="${ctx}/system/main?type=system"><span class="SettingIcon"></span><span>系统管理</span></a></li>
        <%}%>
         <%if(menuMap.get("AGENTLESS_MODULE").equals(0)){%>
         <li><a href="${ctx}/agentless/agentlessimport"><span class="SettingIcon"></span><span>Agentless管理</span></a></li>
         <%}%>
        <div class="blank0"></div>
      </ul>
    </div>
  </div>
			
			<!-- Sidebar
      ================================================== -->
			<div id="sidebar">
				<div class="SearchAvmonIpt">
					<b class="ico ico-search"></b> <input id="ipaddress"
						class="formIpt SearchAvmonIpt-focus" tabindex="1" type="text"
						placeholder="查询" maxlength="50" value="" autocomplete="on">
					<a id="query" href="#" class="SearchAvmonIpt-btn">查询</a>
				</div>
				  <div class="overlay-El-line"> 
          <!--  <div style="margin-bottom:4px;">显示方式</div> -->
           <!-- <div  class="blank4" > </div> -->
           <div id="radioId" style="display:none">
           <label class="left W50"><div id="R1" >IP地址</div></label>
           <label class="left W50"><div id="R2" >设备名</div></label>
          </div>
      </div> 
      
      <div class="blank10"></div>  
				<div class="line-item-separator"></div>
				<div>
					<div class="Tab_category left">
	
			 <ul id="datacenter">
              <li id="ALL" class="Tab_category_active"><a href="#" title="全部"><b class="Icon24 Overview_icon_All_white"></b></a></li>
              <li id="HARDWARE"><a href="#" title="硬件设备"><b class="Icon24 Overview_icon_HardWare"></b></a></li>              
              <li id="HOST"><a href="#" title="操作系统"><b class="Icon24 Overview_icon_OS"></b></a></li>
              <li id="DATABASE"><a href="#" title="数据库"><b class="Icon24 Overview_icon_Database"></b></a></li>
              <li id="NETWORK"><a href="#" title="网络设备"><b class="Icon24 Overview_icon_Network"></b></a></li>              
              <li id="MIDDLEWARE"><a href="#" title="中间件"><b class="Icon24 Overview_icon_Middleware"></b></a></li>
              <li id="STORAGE"><a href="#" title="存储设备"><b class="Icon24 Overview_icon_Storage"></b></a></li>
              <li id="VE"><a href="#" title="虚拟环境"><b class="Icon24 Overview_icon_VM"></b></a></li>
              <!-- <li><a href="#" title="业务系统"><b class="Icon24 Overview_icon_System"></b></a></li>    -->
                     </ul>
					</div>
					<div id="serverIps" class="content_1 Tab_category_sub left">数据加载中....</div>
				</div>

			</div>
			<!-- content
      ================================================== -->
			<div class="content">
				<div class="NavAvmon">
					<!-- NavAvmon
      ================================================== -->
					<div class="left paddingL4">
						<div id="level5" class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><img
								src="${ctx}/resources/images/level5.png" width="10" height="10"><span
								id="grade5" class="paddingL4 w40px">0</span></a>
						</div>
						<div id="level4" class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><img
								src="${ctx}/resources/images/level4.png" width="10" height="10"><span
								id="grade4" class="paddingL4 w40px">0</span></a>
						</div>
						<div id="level3" class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><img
								src="${ctx}/resources/images/level3.png" width="10" height="10"><span
								id="grade3" class="paddingL4 w40px">0</span></a>
						</div>
						<div id="level2" class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><img
								src="${ctx}/resources/images/level2.png" width="10" height="10"><span
								id="grade2" class="paddingL4 w40px">0</span></a>
						</div>
						<div id="level1" class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><img
								src="${ctx}/resources/images/level1.png" width="10" height="10"><span
								id="grade1" class="paddingL4 w40px">0</span></a>
						</div>
					</div>
					<div class="right paddingL4">
						<div class="left paddingL4 NavMainBtn">
							<a href="#" class="Avmon-button" id="showWindowButton">高级查询</a>
						</div>
						<div class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><span id="batchupdate">批量更新告警状态</span></a>
						</div>
					
						<div class="left Padding14 NavMainBtn"><a href="#" id="ShowbuttonBoxMainClick" style="cursor:pointer"><img src="${ctx}/resources/images/More.png" width="20" height="20"></a></div>
						
					</div>
				</div>
				<!-- table
      ================================================== -->
				<div id="mainSplitter">
					<div id="alarmGrid" class="splitter-panel">
						<%-- <img src='${ctx}/resources/images/ajax-loader.gif' /> --%>
					</div>
					<div id="historyPanel" class="splitter-panel"
						style="position: relative;display:none">
						<!-- <div style="position:relative"> -->
						<div id="tab_close" class="Avmon-position-close">
							<span class="Avmon-close Icon16"></span>
						</div>

						<div id='jqxTabs' style="padding-top: 10px">
							<ul>
								<li style="margin-left: 30px;">告警信息</li>
								<li>历史告警</li>
							</ul>
							<div id="content1">
								<%-- <img src='${ctx}/resources/images/ajax-loader.gif' /> --%>
								<!-- 告警信息 -->
								<div style="margin: 10px;">
									<div>设备信息</div>
									<table width="100%" border="0" cellspacing="0" cellpadding="0"
										class="Avmon_tabl_None">
										<tr>
											<td><span class="Avmon-table-photo"><img
													src="${ctx}/resources/images/photo1.png" width="60"
													height="60"></span></td>
											<td width="98%"><table width="100%" border="0"
													cellpadding="0" cellspacing="0" class="Avmon_tabl">
													<tr>
														<th class="W20">设备名称</th>
														<th class="W10">IP地址</th>
														<th class="W10">业务系统</th>
														<th class="W10">位置</th>
														<th class="W10">用途</th>
														<th class="W10">管理员</th>
													</tr>
													<tr>
														<td id="hostName" class="W20">loading</td>
														<td id="attr_ipaddr" class="W10">loading</td>
														<td id="attr_businessSystem" class="W10">loading</td>
														<td id="attr_position" class="W10">loading</td>
														<td id="attr_yongtu" class="W10">loading</td>
														<td id="attr_actorUser" class="W10">loading</td>
													</tr>
												</table></td>
										</tr>
									</table>
									<div class="blank10"></div>
									<div>告警信息</div>
									<table width="100%" border="0" cellpadding="0" cellspacing="0"
										class="Avmon_tabl">
										<thead>
											<tr>
												<th class="W10">告警级别</th>
												<th class="W10">告警状态</th>
												<th class="W10">告警内容</th>
												<!--  <th class="W10">告警原始内容</th> -->
												<th class="W10">首次告警时间</th>
												<th class="W10">发生次数</th>
												<th class="W10">最后告警时间</th>
												<th class="W10">认领时间</th>
												<th class="W10">认领人</th>
												<th class="W10">告警来源</th>
											</tr>
										</thead>
										<tr>
											<td id="level" class="W10">loading</td>
											<td id="status" class="W10">loading</td>
											<td id="content_t" class="W20">loading</td>
											<!--  <td id="content" class="W10">loading</td> -->
											<td id="firstOccurTime" class="W10">loading</td>
											<td id="occurTimes" class="W10">loading</td>
											<td id="lastOccurTime" class="W10">loading</td>
											<td id="aknowledgeTime" class="W10">loading</td>
											<td id="attr_actorUser1" class="W10">loading</td>
											<td id="source" class="W10">loading</td>
										</tr>
									</table>
									 <div class="blank10"></div>
            <div>处理意见</div>
            <table id="processRes" width="100%" border="0" cellpadding="0" cellspacing="0" class="Avmon_tabl">
         
            </table>
            </td>
            </tr>
            </table>
           <div class="blank10"></div>
								</div>
							</div>
							<div id="content2">
								<%-- <img src='${ctx}/resources/images/ajax-loader.gif' /> --%>
								<div id="historyGrid"></div>
							</div>
						</div>
                        <div id="deletedAlarmGrid" style="display:none"></div>
					</div>
				</div>
			</div>
			<!-- 右键菜单 -->
			<div id="Menu" class="AvmonRightC" style="display:none">
				<ul>
					<li><span class="AvmonRightC_Icon R-icon_1"></span><span
						id="aknowledge" class="AvmonRightC_Name">认领</span></li>
					<li><span class="AvmonRightC_Icon R-icon_2"></span><span
						id="clear" class="AvmonRightC_Name">清除</span></li>
					<li><span class="AvmonRightC_Icon R-icon_3"></span><span
						id="detail" class="AvmonRightC_Name">详情</span></li>
					<li><span class="AvmonRightC_Icon R-icon_4"></span><span
						id="history" class="AvmonRightC_Name">历史告警</span></li>
					<li><span class="AvmonRightC_Icon  R-icon_5"></span><span
						id="performance" class="AvmonRightC_Name">实时性能</span></li>
					<li><span class="AvmonRightC_Icon  R-icon_6"></span><span
						id="forward" class="AvmonRightC_Name">告警前转</span></li>
				</ul>
			</div>
			<!-- jayjay -->
       <div style="width: 100%; height: 650px;display:none; margin-top: 50px;" id="mainDemoContainer">
            <div id="alarmforward">
                <div id="windowHeader" >
                    <span class="Icon16 Icon_GJQZ"></span><span class="f14px white paddingL20">告警前转</span>
                </div>
                
                <div style="margin:0px; padding:0px;">
                
                
                <div style="margin:10px;">                
                <div class="left">      
                  <div class="overlay-El-line"> 
                  <div style="margin-bottom:4px;">告警主机</div>
                  <div><input id="alarm_name" type="text" placeholder="" style="width:195px;" class=""></div> 
                  <!-- <div class="jqx-validator-error-label">请输入告警主机</div> -->
                  </div>
                  
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px;">告警指标</div>
                  <div><input id="alarm_index" type="text" placeholder="" style="width:195px;" class=""></div> 
                  <!-- <div class="jqx-validator-error-label">请输入告警指标</div> -->
                  </div>
                  
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px;">告警标题</div>
                 <div><input id="alarm_title" type="text" placeholder="" style="width:195px;" class=""></div> 
                 <!--  <div class="jqx-validator-error-label">请输入位置</div>    -->                
                  </div>
                  
                  
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px; display:inline-block;width:195px;">开始时间</div>
                  <div><input id="start_time" type="text" placeholder="2014-08-08" style="width:195px;"></div> 
                  <!-- <div class="jqx-validator-error-label">请选择开始时间</div>  -->                  
                  </div>                  
                  
                </div>

                <div class="left">  
                  <div class="overlay-El-line"> 
                  <div style="margin-bottom:4px;">告警内容</div>
                  <div><textarea id="forward_content" rows="6" placeholder="Enter text ..." style="width:280px; height:144px;"></textarea></div> 
                  <div class="AVmon-validator-label"></div>
                  </div>  
                  
                  <div class="overlay-El-line">
                  <div style="margin-bottom:4px;">告警级别</div>
                   <div><input id="alarm_level" type="text" placeholder="" style="width:195px;" class=""></div> 
                  <!-- <div class="jqx-validator-error-label">请选择告警级别</div> -->
                  </div>
                    
                </div> 
                    
                <div class="blank0"></div>

                </div>
                <div class="blank10"></div>                

                <div class="AvmonButtonArea">
                
                   <div class="left"><span id="forwardConfirm" class="AvmonOverlayButton">确定</span><span id="forwardCancel"  class="AvmonOverlayButton"> 取消</span></div>  
                   <div class="blank0"></div>
                   
                </div>
     
                </div>
            </div>
        </div>
       
			<!-- end -->
			<!--高级查询 window open -->
			<div
				style="width: 100%; height: 650px; display: none; margin-top: 50px;"
				id="mainDemoContainer">
				<div id="window">
					<div id="windowHeader">
						<span class="Icon16 Icon_search"></span><span
							class="f14px white paddingL20">高级查询</span>
					</div>
					<div>
						<div style="margin: 10px;">
							<div class="left">
								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">设备名称</div>
									<div>
										<!-- <input id="s_moCaption" type="text" placeholder=""
											style="width: 195px;" class="jqx-validator-error-element"> -->
											<input id="s_moCaption" type="text" placeholder=""
											style="width: 195px;" >
									</div>
									<!-- <div class="jqx-validator-error-label">请输入设备名称</div> -->
								</div>

								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">业务系统</div>
									<!-- <div id="digits1" class="jqx-validator-error-element"></div> -->
									<div>
										<input id="s_attr_businessSystem" type="text" placeholder=""
											style="width: 195px;" class="">
									</div>
									<!-- <div class="jqx-validator-error-label">请输入业务系统</div> -->
								</div>

								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">位置</div>
									<div>
										<input id="s_attr_position" type="text" placeholder=""
											style="width: 195px;" class="">
									</div><!-- 
									<div class="jqx-validator-error-label">请输入位置</div> -->
								</div>
							</div>

							<div class="left">
								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">IP地址</div>
									<div>
										<input id="s_attr_ipaddr" type="text"
											placeholder="" style="width: 195px;"
											class="">
									</div>
									<!-- <div class="jqx-validator-error-label">请输入IP地址</div> -->
								</div>

								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">处理人</div>
									<!--  <div id="digits2" class="jqx-validator-error-element"></div>  -->
									<div>
										<input id="s_aknowledge_by" type="text" placeholder=""
											style="width: 195px;" class="">
									</div>
									<!-- <div class="jqx-validator-error-label">请输入处理人</div> -->
								</div>

							</div>


							<div class="blank0"></div>


							<div class="left">
								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">告警内容</div>
									<div>
										<textarea id="s_content_t" rows="6"
											placeholder=""
											style="width: 430px; height: 50px;"></textarea>
									</div>
									<div class="AVmon-validator-label"></div>
								</div>
							</div>
							<div class="left">
								<div class="overlay-El-line">
									<div
										style="margin-bottom: 4px; display: inline-block; width: 217px;">开始时间</div>
									<div style="margin-bottom: 4px; display: inline-block">结束时间</div>
									<div>
										<!-- <input id="s_start_time" type="text" placeholder=""
											style="width: 210px;">--<input id="s_end_time"
											type="text" placeholder="" style="width: 206px;">  -->
											<div><div id="start_date" class="left"></div>
											<div  class="left">--</div><div id='end_date' class="left"></div></div>
											
											 <!-- <div class="left" id='start_date'> --  --><!-- <div class="left" id='end_date'>   -->
									</div>
								</div>
							</div>

						</div>
						<div class="blank10"></div>
						<div class="AvmonButtonArea">
							<div class="left">
								<span id="search" class="AvmonOverlayButton">查询</span><span
									id="searchcancel" class="AvmonOverlayButton"> 取消</span>
							</div>
							<div class="blank0"></div>
						</div>
					</div>
				</div>
			</div>

			<!-- end -->
			<!-- window -->
		<div style="width: 100%; height: 300px; display: none; margin-top: 50px;" id="mainDemoContainer1">
	<div id="changePwdWin">
		<div id="windowHeader">
			<span class="Icon16  R-icon_1_white"></span><span id="updatepwdtitle"
				class="f14px white paddingL20">修改密码</span>
		</div>
		<div>

            <div class="left">
				<div class="overlay-El-line">
					<div style="margin-bottom: 4px;">用户名</div>
					<div class="jqx-validator-error-element">
						<input disabled id="account" placeholder="" value="${userId}" style="width: 250px;height:28px;line-height:20px;"></input>
					</div>
				</div>
			</div>
			<div class="left">
				<div class="overlay-El-line">
					<div style="margin-bottom: 4px;">原始密码</div>
					<div class="jqx-validator-error-element">
						<input id=oldPassword placeholder="" type="password" style="width: 250px;"></input>
					</div>
				</div>
			</div>
			<div class="blank0"></div>
			<div class="left">
				<div class="overlay-El-line">
					<div style="margin-bottom: 4px;">新密码</div>
					<div>
						<input id="password" placeholder="" type="password" style="width: 250px;"></input>
					</div>
					<!-- <div class="AVmon-validator-label"></div> -->
				</div>
			</div>
	       <div class="blank0"></div>
			<div class="AvmonButtonArea">

				<div class="left">
					<span id="confirmChange" class="AvmonOverlayButton">确定</span><span
						id="cancelChange" class="AvmonOverlayButton">取消</span>
				</div>
				<div class="blank0"></div>

			</div>
		</div>
	</div>
   </div>
   <!-- end -->
			<!-- 认领window -->
			<div style="width: 100%; height: 650px; display: none; margin-top: 50px;"
				id="mainDemoContainer">
				<div id="renling">
					<div id="windowHeader">
						<span class="Icon16  R-icon_1_white"></span><span id="windowtitle"
							class="f14px white paddingL20">认领</span>
					</div>
					<div>
						<div style="margin: 10px;">
							<div class="left">
								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">分类</div>
									<div id="cate" class="jqx-validator-error-element"></div>
									<!--  <div class="jqx-validator-error-label">请选择Category</div> -->
								</div>
							</div>
							<div class="blank0"></div>
							<div class="left">
								<div class="overlay-El-line">
									<div style="margin-bottom: 4px;">详细内容</div>
									<div>
										<textarea id="detailcontent" rows="6" placeholder="检查中..."
											style="width: 430px; height: 70px;">已处理</textarea>
									</div>
									<div class="AVmon-validator-label"></div>
								</div>
							</div>
							<div class="overlay-El-line">
								<div style="margin-bottom: 4px;">日志</div>
								<div id="log" class="AvmonMain"
									style="float: left; margin: 0px;">
									<div id="loggrid"></div>
								</div>
							</div>

						</div>
						<div class="blank10"></div>


						<div class="AvmonButtonArea">

							<div class="left">
								<span id="confirm" class="AvmonOverlayButton">确定</span><span
									id="cancel" class="AvmonOverlayButton">取消</span>
							</div>
							<div class="blank0"></div>

						</div>

					</div>
				</div>
			</div>
			<!-- end -->
			<!-- alarm forward -->
			
        </div>
		</div>
		<!-- Content container
      ================================================== -->
		

	<!-- end -->
		   <script type="text/javascript"
	src="${ctx}/resources/js/jquery-1.10.0.min.js"></script>
	<script type="text/javascript"
		src="${ctx}/resources/js/jquery.toastmessage.js"></script>	
<%-- <script type="text/javascript"
	src="${ctx}/resources/js/jqwidgets/globalization/globalize.js"></script>
<script type="text/javascript"
	src="${ctx}/resources/js/jqwidgets/globalization/globalize.culture.zh-CN.js"></script> --%>
<script type="text/javascript"
	src="${ctx}/resources/js/jqwidgets/jqx-all.js"></script>
<script type="text/javascript"
		src="${ctx}/resources/js/common.js"></script>
		<script type="text/javascript">
	
		function show1(oEvent){
		     var UserBox1=document.getElementById("ShowUserBoxMain");
		     if(UserBox1.style.display=="block"){
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
		 
			 function show2(oEvent){  
			     var UserBox2=document.getElementById("ShowbuttonBoxMain");
			     if(UserBox2.style.display=="block"){
			     	  document.getElementById("ShowbuttonBoxMain").style.display = "none";
			     	}else{
			     		document.getElementById("ShowbuttonBoxMain").style.display = "block";
			     		}
			     
			     e = window.event || oEvent;
			     if (e.stopPropagation)
			     {
			         e.stopPropagation();
			     }else{
			         e.cancelBubble = true;
			     }
			   }  
			function hide() {

				document.getElementById("ShowbuttonBoxMain").style.display = "none";
			     document.getElementById("ShowUserBoxMain").style.display = "none";
			}
			function show3(oEvent){  
			     var UserBox3=document.getElementById("ShowTitleMenu");
			     //alert(obj.style.display);
						
			     if(UserBox3.style.display=="block"){
			     	  document.getElementById("ShowTitleMenu").style.display = "none";
			     	}else{
			     		document.getElementById("ShowTitleMenu").style.display = "block";
			     		}
			     
			     e = window.event || oEvent;
			     if (e.stopPropagation)
			     {
			         e.stopPropagation();
			     }else{
			         e.cancelBubble = true;
			     }
			   }  
			window.onload = function() {
				 document.getElementById("ShowbuttonBoxMain").style.display = "none";
				  document.getElementById("ShowUserBoxMain").style.display = "none";
			      document.body.onclick = hide;
				  document.getElementById("ShowUserBoxclick").onclick = show1;
				  document.getElementById("ShowbuttonBoxMainClick").onclick = show2;
				  document.getElementById("ShowTitleMenuclick").onclick = show3;
			};
			   var account='${userId}';
			   var ctx='${ctx}';
		</script>
		<script type="text/javascript">
		setInterval("initLoad()",600000);
		function initLoad(){
			getAllAlertGrade("","");
			loadListBoxData(listBoxSource);
		}
		// prepare the data
		var listBoxSource = {
			datatype : "json",
			datafields : [ {
				name : 'mo'
			}, {
				name : 'attr_ipaddr'
			} ],
			// id: 'id',
			url : '${ctx}/pages/alarm/getAllMoData'
		};
		
		/*  $('#jqxButton').on('click', function () {
		      $("#serverIps").jqxListBox('addItem',   { label: '点击更多', value: 'hahahahahahah '} );
		    
		  });
		 $('#jqxButton1').on('click', function () {
			 //alert(11111);
		      $("#serverIps").jqxListBox('removeAt',   20 );
		    
		  }); */
        var alarmType="";
        var batchFilter="";
        var moPage=1;
        loadListBoxData(listBoxSource);
		function loadListBoxData(listBoxSource) {
			var dataAdapter = new $.jqx.dataAdapter(
					listBoxSource);
			
			// Create a jqxListBox
			$("#serverIps").jqxListBox(
							{
								source : dataAdapter,
								displayMember : "attr_ipaddr",
								valueMember : "mo",
								width : 160,
								height : 520,
								scrollBarSize : 5,
								renderer : function(index, label,value) {
									var typeId="";
									var ipaddress="";
									var table="";
									if(label.indexOf(")")==-1){
										//alert("index:"+index+" label:"+label+" value:"+value);
										 table = "<div style='height: 20px; float: left;'><span style='float: left; padding-left:20px; font-size: 13px; font-family: Verdana Arial;'>"
											+ label
											+ "</span></div>";
									}
		                          	else{
									 typeId = label.substr(label.lastIndexOf(")") + 1); 
			                        // alarmType=typeId;
									 ipaddress = label.substr(0,label.lastIndexOf(")") + 1);
									 table = "<div style='height: 20px; float: left;'><img width='16' height='16' style='float: left; margin-right: 5px;' src='${ctx}/resources/images/"+typeId+".png'/><span style='float: left; font-size: 13px; font-family: Verdana Arial;'>"
										+ ipaddress
										+ "</span></div>";
		                          	}
									//var table ="<div style='height: 15px; float: left;'><span style='float: left;'>"+label+"</span></div>";
									return table;
								}
							});

		}
		//load alarm level
		function getAllAlertGrade(typeId,moId){
			$.getJSON("${ctx}/pages/alarm/getAllAlertGrade?typeId="+typeId+"&moId="+moId,
					function(data) {
				$("#grade1").text("0");
				$("#grade2").text("0");
				$("#grade3").text("0");
				$("#grade4").text("0");
				$("#grade5").text("0");
						for ( var i = 0; i < data.gradeData.length; i++) {
							if (data.gradeData[i].grade == 1) {
								$("#grade1")
										.text(
												data.gradeData[i].count);
							} else if (data.gradeData[i].grade == 2) {
								$("#grade2")
										.text(
												data.gradeData[i].count);
							} else if (data.gradeData[i].grade == 3) {
								$("#grade3")
										.text(
												data.gradeData[i].count);
							} else if (data.gradeData[i].grade == 4) {
								$("#grade4")
										.text(
												data.gradeData[i].count);
							} else if (data.gradeData[i].grade == 5) {
								$("#grade5")
										.text(
												data.gradeData[i].count);
							}
						}
					});
		}
		getAllAlertGrade("","");
			$(document).ready(function() {
				                var gridHeight = 530;
								var alarmIdList = [];
								var moId = "";
								var kpiInfo = "";
								var alarmId = "";
								var tempTypeId="";
								//setInterval("getAllAlertGrade()",10000);
								
								 $('#R1').jqxRadioButton({ checked: true});
						         $('#R2').jqxRadioButton();
						         $("#R1").on('change', function (event) {
						                var checked = event.args.checked;
						                if(checked){
						                	 listBoxSource.url = '${ctx}/pages/alarm/getAllMoData?typeId='
													+ tempTypeId+"&isIp=1";
		                                        loadListBoxData(listBoxSource);
						                }
						               
						         });
						         $("#R2").on('change', function (event) {
						                var checked = event.args.checked;
						                if(checked){
						                listBoxSource.url = '${ctx}/pages/alarm/getAllMoData?typeId='
											+ tempTypeId+"&isIp=0";
                                        loadListBoxData(listBoxSource);
						                }
						         });
								//grid localization
								var getLocalization = function() {
									var localizationobj = {};
									localizationobj.pagergotopagestring = "当前页:";
									localizationobj.pagershowrowsstring = "每页显示:";
									localizationobj.pagerrangestring = " 总共 ";
									localizationobj.pagernextbuttonstring = "后页";
									localizationobj.pagerpreviousbuttonstring = "前页";
									localizationobj.sortascendingstring = "正序";
									localizationobj.sortdescendingstring = "倒序";
									localizationobj.sortremovestring = "清除排序";
									localizationobj.firstDay = 1;
									localizationobj.percentsymbol = "%";
									localizationobj.currencysymbol = "￥";
									localizationobj.currencysymbolposition = "before";
									localizationobj.decimalseparator = ".";
									localizationobj.thousandsseparator = ",";
									localizationobj.emptydatastring = "暂没有数据";

									return localizationobj;
								};
								$("#start_date").jqxDateTimeInput({ width: '200px',readonly: true, height: '18px',max: new Date(),formatString: 'yyyy/MM/dd'});
								$("#end_date").jqxDateTimeInput({ width: '200px', readonly: true,height: '18px', max: new Date(),formatString: 'yyyy/MM/dd'});
								$("#deletedAlarms").click(function(){
									deletedAlarmSource.url = '${ctx}/pages/alarm/getHistoryAlarmData?moId='+moId;
									$('#mainSplitter').jqxSplitter('expand');
									$('#alarmGrid').jqxGrid({height : gridHeight - 250});
									$('#historyPanel').show();
									$('#deletedAlarmGrid').show();
									$('#jqxTabs').hide();
									loadDeletedAlarmGridData(deletedAlarmSource);
								});
								//query
								
								$("#query").click(function() {
									var ipaddress = $("#ipaddress").val();
									listBoxSource.url = '${ctx}/pages/alarm/queryMoData?ipaddress='+ ipaddress;

									loadListBoxData(listBoxSource);
								});
								//forward
								
								$("#forwardConfirm").click(function() {
									$.getJSON("${ctx}/pages/alarm/forwardAlarm?alarmId="+ alarmId,
											function(data) {
												//alert(data);
												
											});
									alarmId="";
									$("#alarmforward").jqxWindow('hide');
								});
								
								$("#forwardCancel").click(function() {
									$("#alarmforward").jqxWindow('hide');
								});
								
								//end
								//batchupdate
							
								$("#batchupdate").click(function() {
									               
							                       /*  var rowindexes = $('#alarmGrid').jqxGrid('getselectedrowindexes');
	                                                alert(rowindexes.length);
 */
									                var rowindexes = $('#alarmGrid').jqxGrid('getselectedrowindexes');
									               // alert(isClickSearch);
									               // alert(rowindexes.length);
									                //alert(rowindexes[0]);
									                if(isClickSearch==false&&((rowindexes.length==0||rowindexes[0]==-1))){
									                	$().toastmessage('showToast', {
												            text     : '请选择一条更新记录！',
												            sticky   : false,
												            position : 'top-center',
												            type     : 'warning',
												            closeText: ''
												        });
									                	return;
									                }
									                
									                if(isClickSearch==false&&(rowindexes.length>0)){
									                	for (var i = 0; i < rowindexes.length; i++) {
															var data = $('#alarmGrid').jqxGrid('getrowdata',rowindexes[i]);
															alarmIdList.push(data.id);
														}
									                	//alert("alarmIdList is:"+alarmIdList.length);
									                	$("#windowtitle").text("清除");
														$("#renling").jqxWindow(
																		{
																			showCollapseButton : true,
																			isModal : true,
																			resizable : false,
																			maxHeight : 496,
																			height : 496,
																			width : 500,
																			initContent : function() {
																				$(
																						'#renling')
																						.jqxWindow(
																								'focus');
																			}

																		});
														$('#renling').jqxWindow(
																'show');
														logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmIdList[0];
														loadLogGridData(logsource);
														 getAllAlertGrade("","");
														 loadListBoxData(listBoxSource);
									                }
									                //if(isClickSearch==true&&(alarmIdList.length>0)){
									                	if(isClickSearch==true){
									                	$("#windowtitle").text("清除");
														$("#renling").jqxWindow(
																		{
																			showCollapseButton : true,
																			isModal : true,
																			resizable : false,
																			maxHeight : 496,
																			height : 496,
																			width : 500,
																			initContent : function() {
																				$(
																						'#renling')
																						.jqxWindow(
																								'focus');
																			}

																		});
														$('#renling').jqxWindow(
																'show');
														logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmIdList[0];
														loadLogGridData(logsource);
														getAllAlertGrade("","");
														loadListBoxData(listBoxSource);
									                	return;
									                }
												});
								//end
								//renling log grid
								var cates = [ "意见处理", "知识" ];
								$('#cate').jqxDropDownList({
									selectedIndex : 0,
									source : cates,
									autoDropDownHeight : true,
									width : 430,
									height : 20
								});
								$('#cate').on('select', function(event) {
									/*  var index = event.args.index;
									  $("#numericInput").jqxNumberInput({ cates: cates[index] });
									 */});
								var logsource = {
									datatype: "json",
									datafields : [ {
										name : 'createBy',
										type : 'string'
									}, {
										name : 'createTime',
										type : 'date'
									}, {
										name : 'content',
										type : 'string'
									} ],
									id : 'id'
									//url : '${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmId
								};
								
								function loadLogGridData(logsource){
									//getAlarmComments
									var datalogAdapter =new $.jqx.dataAdapter(logsource);
									// initialize jqxGrid
									$("#loggrid").jqxGrid({
										width : 428,
										height : 150,
										source : datalogAdapter,
										localization : getLocalization(),
										//pageable : true,
										sortable : true,
										altrows : true,
										enabletooltips : true,
										//editable : true,
										//selectionmode : 'multiplecellsadvanced',
										columns : [ {
											text : '处理人',
											datafield : 'createBy',
											width : 160
										}, {
											text : '处理时间',
											datafield : 'createTime',
											cellsformat: 'yyyy-MM-dd HH:mm:ss',
											width : 136
										}, {
											text : '详细内容',
											datafield : 'content',
											width : 130
										}]
									});
								}
								
								var editrow = -1;
								$("#confirm").click(function() {
								
									 
													if ($("#windowtitle").text() == '认领') {
														var rowindex = $("#alarmGrid").jqxGrid('getselectedrowindex');
														var row = $("#alarmGrid").jqxGrid('getrowdata',rowindex);
														row.attr_actorUser = '${userId}';
														row.attr_result = $("#detailcontent").val();
														row.status=1;
														$('#alarmGrid').jqxGrid('updaterow',rowindex,row);
														$("#renling").jqxWindow('hide');
													 } else {
													  /* 	 $.getJSON("${ctx}/pages/alarm/batchClose?alarmIdList="+ alarmIdList,
																		function(data) {
														                    $("#renling").jqxWindow('hide');
																			$('#alarmGrid').jqxGrid('updatebounddata');
																			alarmIdList = new Array();
																			//$('#alarmGrid').jqxGrid('refreshdata');
														}); */
												        var alarmIds="";
														//alert("alarmIdList:"+alarmIdList);
														if(alarmIdList.length>1){
															alarmIds=alarmIdList.join(",");
															batchFilter='alarmIdList='+alarmIds;
														
														}
														alarmIds=alarmIdList.join(",");
																										        
												               $.post('${ctx}/pages/alarm/batchClose?'+batchFilter,
															      {
												            	   //batchFilter:batchFilter
															      },
															      function (data) 
															      {
															    	  $("#renling").jqxWindow('hide');
																	  $('#alarmGrid').jqxGrid('updatebounddata');
																	  $('#alarmGrid').jqxGrid('clearselection');
																	  batchFilter="";
																	  alarmIdList = [];
																	  getAllAlertGrade("","");
																	  loadListBoxData(listBoxSource);
																} 
															    /*  $.post('${ctx}/pages/alarm/batchClose',
															      {
															    	  alarmIdList:alarmIds
															      },
															      function (data) 
															      {
															    	  $("#renling").jqxWindow('hide');
																	  $('#alarmGrid').jqxGrid('updatebounddata');
																	  $('#alarmGrid').jqxGrid('clearselection');
																	  alarmIdList = [];
																	  getAllAlertGrade("","");
																	  loadListBoxData(listBoxSource);
																} */
															    ); 
															   
													}

												});

								$("#cancel").click(function() {
									$("#renling").jqxWindow('hide');
								});
								//end
								//history data grid
								var url = "${ctx}/pages/alarm/getPastRecordsData";
								// prepare the data
								var historysource = {
									datatype : "json",
									datafields : [ {
										name : 'content_t',
										type : 'string'
									}, {
										name : 'firstOccurTime',
										type : 'string'
									}, {
										name : 'occurTimes',
										type : 'string'
									}, {
										name : 'clearing_time',
										type : 'string'
									}, {
										name : 'attr_result',
										type : 'string'
									}, {
										name : 'attr_actorUser',
										type : 'string'
									} ],
									beforeprocessing : function(data) {
										if (data.length == 0) {
											historysource.totalrecords = 0;
										} else {
											historysource.totalrecords = data[data.length - 1].totalcount;
										}

									},
									sort : function() {
										// update the grid and send a request to the server.
										$("#alarmGrid").jqxGrid(
												'updatebounddata');
									},
									id : 'id',
									url : url
								};
								function loadHistoryGridData(historysource) {
									var dataAdapter = new $.jqx.dataAdapter(
											historysource);
									$("#historyGrid").jqxGrid({
										width : '100%',
										height : 230,
										source : dataAdapter,
										localization : getLocalization(),
										columnsresize : true,
										pageable : true,
										pagesize : 10,
										//filterable: true,
										sortable : true,
										//page
										virtualmode : true,
										rendergridrows : function() {
											return dataAdapter.records;
										},
										//selectionmode : 'checkbox',
										altrows : true,
										enabletooltips : true,
										columns : [ {
											text : '告警内容',
											datafield : 'content_t',
											width : 250
										}, {
											text : '发生时间',
											datafield : 'firstOccurTime',
											width : 150
										}, {
											text : '发生次数',
											datafield : 'occurTimes',
											width : 180
										}, {
											text : '处理时间',
											datafield : 'clearing_time',
											width : 120
										}, {
											text : '处理结果',
											datafield : 'attr_result',
											minwidth : 120
										}, {
											text : '处理人',
											datafield : 'attr_actorUser',
											minwidth : 80
										} ]
									});
								}
								//end
								//deletedAlarms
								var deletedAlarmUrl = "${ctx}/pages/alarm/getHistoryAlarmData";
								// prepare the data
								var deletedAlarmSource = {
									datatype : "json",
									datafields : [ {
										name : 'level',
										type : 'string'
									}, {
										name : 'status',
										type : 'string'
									}, {
										name : 'moCaption',
										type : 'string'
									}, {
										name : 'title',
										type : 'string'
									}, {
										name : 'content_t',
										type : 'string'
									}, {
										name : 'firstOccurTime',
										type : 'string'
									},{
										name : 'occurTimes',
										type : 'string'
									},{
										name : 'clearing_time',
										type : 'string'
									},{
										name : 'attr_result',
										type : 'string'
									},{
										name : 'attr_actorUser',
										type : 'string'
									},{
										name : 'attr_businessSystem',
										type : 'string'
									},{
										name : 'attr_position',
										type : 'string'
									} 
									,{
										name : 'attr_usage',
										type : 'string'
									} ],
									beforeprocessing : function(data) {
										if (data.length == 0) {
											deletedAlarmSource.totalrecords = 0;
										} else {
											deletedAlarmSource.totalrecords = data[data.length - 1].totalcount;
										}

									},
									sort : function() {
										// update the grid and send a request to the server.
										$("#deletedAlarmGrid").jqxGrid(
												'updatebounddata');
									},
									id : 'id',
									url : deletedAlarmUrl
								};
								function loadDeletedAlarmGridData(deletedAlarmSource) {
									var statusimagerenderer = function(row,
											datafield, value) {
										if(value==9){
											return '<img style="margin-left: 10px;padding-top:5px" height="20" width="22" src="${ctx}/resources/images/close.gif"/>';
										}
										return '<img style="margin-left: 10px;padding-top:5px" height="20" width="22" src="${ctx}/resources/images/newalarm.gif"/>';
									};
									var levelimagerenderer = function(row,
											datafield, value) {
										return '<img style="margin-left: 10px;padding-top:5px" height="15" width="15" src="${ctx}/resources/images/level' + value + '.png"/>';
									};
									var deleteddataAdapter = new $.jqx.dataAdapter(
											deletedAlarmSource);
									$("#deletedAlarmGrid").jqxGrid({
										width : '100%',
										height : 250,
										source : deleteddataAdapter,
										localization : getLocalization(),
										columnsresize : true,
										pageable : true,
										pagesize : 10,
										//filterable: true,
										sortable : true,
										//page
										virtualmode : true,

										rendergridrows : function() {
											return deleteddataAdapter.records;
										},
										showtoolbar: true,
										rendertoolbar: function (toolbar) {
											 
								              var container = $("<div style='margin: 5px;'></div>");
								              var span = $("<span id='con' style='float: left; margin-top: 5px; margin-right: 4px;'>48小时内删除的告警 </span>");
								              if(toolbar[0].innerText.length==0){
								            	  toolbar.append(container);
									              container.append(span);
								              }
								         },
										//selectionmode : 'checkbox',
										altrows : true,
										enabletooltips : true,
										columns : [ {
											text : '级别',
											datafield : 'level',
											width : 40,
											cellsrenderer : levelimagerenderer
										}, {
											text : '状态',
											datafield : 'status',
											width : 40,
											cellsrenderer : statusimagerenderer
										}, {
											text : '主机',
											datafield : 'moCaption',
											width : 80
										}, {
											text : '告警标题',
											datafield : 'title',
											width : 80
										}, {
											text : '告警内容',
											datafield : 'content_t',
											minwidth : 80
										}, {
											text : '发生时间',
											datafield : 'firstOccurTime',
											minwidth : 80
										} 
										, {
											text : '发生次数',
											datafield : 'occurTimes',
											minwidth : 20
										}
										, {
											text : '清除时间',
											datafield : 'clearing_time',
											minwidth : 80
										}, {
											text : '处理结果',
											datafield : 'attr_result',
											minwidth : 40
										}, {
											text : '处理人',
											datafield : 'attr_actorUser',
											minwidth : 80
										}, {
											text : '业务系统',
											datafield : 'attr_businessSystem',
											minwidth : 80
										}, {
											text : '地址',
											datafield : 'attr_position',
											minwidth : 80
										}
										, {
											text : '使用',
											datafield : 'attr_usage',
											minwidth : 80
										}]
									});
								}
								
								
								
								//end
								//search start
								var isClickSearch=false;
								$("#search").click(
									
												function() {
													isClickSearch=true;
													var s_moCaption = $(
															"#s_moCaption")
															.val();
													var s_content_t = $(
															"#s_content_t")
															.val();
													var s_attr_ipaddr = $(
															"#s_attr_ipaddr")
															.val();
													var s_aknowledge_by = $(
															"#s_aknowledge_by")
															.val();
													var s_attr_businessSystem = $(
															"#s_attr_businessSystem")
															.val();
													var s_attr_position = $(
															"#s_attr_position")
															.val();
													var s_start_time = $('#start_date').jqxDateTimeInput('getText');  
												 	var s_end_time = $('#end_date').jqxDateTimeInput('getText'); 
												 	//alert(isClickSearch);
													source.url = '${ctx}/pages/alarm/getActiveAlarmDataByCriteria?s_moCaption='
															+ s_moCaption
															+ '&s_content_t='
															+ encodeURI(encodeURI(s_content_t))
															+ '&s_attr_ipaddr='
															+ s_attr_ipaddr
															+ '&s_aknowledge_by='
															+ encodeURI(encodeURI(s_aknowledge_by))
															+ '&s_attr_businessSystem='
															+ encodeURI(encodeURI(s_attr_businessSystem))
															+ '&s_attr_position='
															+ s_attr_position
															 + '&s_start_time='
															+ s_start_time 
															 + '&s_end_time='
															+ s_end_time;
													/* $.getJSON(
															'${ctx}/pages/alarm/getActiveAlarmDataByCriteria?s_moCaption='
															+ s_moCaption
															+ '&isBatchUpdate='
															+ isClickSearch
															+ '&s_content_t='
															+ s_content_t
															+ '&s_attr_ipaddr='
															+ s_attr_ipaddr
															+ '&s_aknowledge_by='
															+ s_aknowledge_by
															+ '&s_attr_businessSystem='
															+ s_attr_businessSystem
															+ '&s_attr_position='
															+ s_attr_position
															 + '&s_start_time='
															+ s_start_time 
															 + '&s_end_time='
															+ s_end_time,
															function(data) {
																       
																		alarmIdList= [];
																		for(var i=0;i<data.length-1;i++){
																			alarmIdList.push(data[i].id);
																		}
																		isClickSearch=true;
															}); */
															batchFilter='s_moCaption='
																+ s_moCaption
																+ '&isBatchUpdate='
																+ isClickSearch
																+ '&s_content_t='
																+ s_content_t
																+ '&s_attr_ipaddr='
																+ s_attr_ipaddr
																+ '&s_aknowledge_by='
																+ s_aknowledge_by
																+ '&s_attr_businessSystem='
																+ s_attr_businessSystem
																+ '&s_attr_position='
																+ s_attr_position
																 + '&s_start_time='
																+ s_start_time 
																 + '&s_end_time='
																+ s_end_time;
													loadGridData(source);

													$("#window").jqxWindow(
															'close');
												});
								$("#searchcancel").click(function() {
									$("#window").jqxWindow('close');
								});
								$("#tab_close").click(function() {
									$('#mainSplitter').jqxSplitter('collapse');
									$('#alarmGrid').jqxGrid({
										height : gridHeight
									});
								});

								//end
								$('#showWindowButton').click(function() {
									$('#window').jqxWindow('open');
								});
								$("#window").jqxWindow({
									showCollapseButton : true,
									autoOpen : false,
									isModal : true,
									resizable : false,
									height : 450,
									width : 510,
									initContent : function() {
										$('#window').jqxWindow('focus');
									}
								});
								$('#jqxTabs').jqxTabs({
									width : '100%',
									height : 250
								});
								$('#jqxTabs').on('selected',
												function(event) {
													var pageIndex = event.args.item + 1;
													if (pageIndex == 1) {
													
														var url = '${ctx}/pages/alarm/getAlarmDetail?alarmId='
																+ alarmId;
														$.get(url, function(
																data) {
															
													$(
															"#hostName")
															.text(
																	data.alarm.hostName == null ? ""
																			: data.alarm.hostName);
													$(
															"#attr_ipaddr")
															.text(
																	data.alarm.attr_ipaddr == null ? ""
																			: data.alarm.attr_ipaddr);
													$(
															"#attr_businessSystem")
															.text(
																	data.alarm.attr_businessSystem == null ? ""
																			: data.alarm.attr_businessSystem);
													$(
															"#attr_position")
															.text(
																	data.alarm.attr_position == null ? ""
																			: data.alarm.attr_position);
													$(
															"#attr_yongtu")
															.text(
																	"");
													$(
															"#attr_actorUser")
															.text(
																	data.alarm.attr_actorUser == null ? ""
																			: data.alarm.attr_actorUser);
													$(
															"#level")
															.text(
																	data.alarm.level == null ? ""
																			: data.alarm.level);
													$(
															"#status")
															.text(
																	data.alarm.status == null ? ""
																			: data.alarm.status);
													$(
															"#content_t")
															.text(
																	data.alarm.content_t == null ? ""
																			: data.alarm.content_t);
													$(
															"#firstOccurTime")
															.text(
																	data.alarm.firstOccurTime == null ? ""
																			: data.alarm.firstOccurTime);
													$(
															"#occurTimes")
															.text(
																	data.alarm.occurTimes == null ? ""
																			: data.alarm.occurTimes);
													$(
															"#lastOccurTime")
															.text(
																	data.alarm.lastOccurTime == null ? ""
																			: data.alarm.lastOccurTime);
													$(
															"#aknowledgeTime")
															.text(
																	data.alarm.aknowledgeTime == null ? ""
																			: data.alarm.aknowledgeTime);
													$(
															"#attr_actorUser1")
															.text(
																	data.alarm.attr_actorUser == null ? ""
																			: data.alarm.attr_actorUser);
													$(
															"#source")
															.text(
																	data.alarm.source == null ? ""
																			: data.alarm.source);
															/*  moId = "";
															 kpiInfo = "";
															 alarmId = ""; */
															$("#processRes").empty();
															 if(data.comments.length>0){
																 $("#processRes").append("<thead><tr><th class='W30'>处理人</th> <th class='W30'>处理时间</th> <th class='W30'>处理结果</th> </tr></thead>")
																for(var n=0;n<data.comments.length;n++){
																	 $("#processRes").append("<tr  align='left'>"
														                    
																	           +"<td class='W30'>"+data.comments[n].createBy+"</td>"
												                                +"<td class='W30'>"+getSmpFormatDateByLong(data.comments[n].createTime,true)+"</td>"
												                                +"<td class='W30'>"+data.comments[n].content+"</td>"
												                           +"</tr>");     
																}
															 }else{
																 $("#processRes").append("<thead><tr><th class='W30'>处理人</th> <th class='W30'>处理时间</th> <th class='W30'>处理结果</th> </tr></thead>"); 
															 }
															
														});
														
														
														
													} else {
														source.url = '${ctx}/pages/alarm/getPastRecordsData?moId='
																+ moId
																+ '&kpiInfo='
																+ kpiInfo;
														loadHistoryGridData(source);
														/* moId = "";
														kpiInfo = "";
														alarmId = ""; */

													}
												});
								

								
							/* 	$.getJSON(
												"${ctx}/pages/alarm/getActiveAlarmDataByCriteriaForCount?isNewalarm=1",
												function(data) {
													$("#newalarmcount").text(
															data[0].count);
												});
								$.getJSON(
												"${ctx}/pages/alarm/getActiveAlarmDataByCriteriaForCount?isMyalarm=1",
												function(data) {
													$("#myalarmcount").text(
															data[0].count);
												}); */

								
								$("ul#datacenter li").click(function() {$(this)
															.addClass(
																	"Tab_category_active")
															.siblings()
															.removeClass(
																	"Tab_category_active");
												   var typeId = $(this)[0].id;
												   tempTypeId=typeId;
												   getAllAlertGrade(typeId,"");
													if (typeId == "ALL") {
														$("#radioId").hide();
														listBoxSource.url = '${ctx}/pages/alarm/getAllMoData';
														source.url = '${ctx}/pages/alarm/getAllActiveAlarmDataList';
														
													} else {
														var isIp="0";
														if(typeId=="HOST"){
															$("#radioId").show();
															 isIp="1";
														}
														else{
															$("#radioId").hide();
														}
														listBoxSource.url = '${ctx}/pages/alarm/getAllMoData?typeId='
																+ typeId+"&isIp="+isIp;
														source.url = '${ctx}/pages/alarm/getAllActiveAlarmDataList?typeId='
																+ typeId;
													}
													loadListBoxData(listBoxSource);
													//$("#serverIps").jqxListBox('addItem', 'qinjie');
													loadGridData(source);
												});

								
					
								

								$('#serverIps').on('select',function(event) {
									$('#alarmGrid').jqxGrid('clearselection');
													var args = event.args;
													if (args) {
														// index represents the item's index.                          
														//var index = args.index;
														var item = args.item;
														// get item's label and value.
														var label = item.label;
														var moId = item.value;
														if(moId==undefined){
                                                        	moId="";
                                                         }
														if(label.indexOf(")")==-1){
															 $.getJSON('${ctx}/pages/alarm/getAllMoData?pagesize=20&pagenum='+moPage,
												            function(data) {		
																	$("#serverIps").jqxListBox('removeAt', moPage*20 ); 
																    for(var i=0;i<data.length;i++){
																		$("#serverIps").jqxListBox('addItem',{ label: data[i].attr_ipaddr, value: data[i].mo} );
																	} 
																	 moPage++;
																	 if(data.length>0){
																		$("#serverIps").jqxListBox('addItem',{ label: '点击加载更多', value: ''} );
																	} 
																	
												             }); 
															
								                         }
														
								                        else{
								                        	source.url = '${ctx}/pages/alarm/getAllActiveAlarmDataByMoId?moId='
																+ moId;
								                        	getAllAlertGrade(alarmType,moId);
															loadGridData(source);
							                           	}
														
                                                       
													
													}
												});
								$('#mainSplitter').jqxSplitter({
									showSplitBar : false,
									splitBarSize : 15,
									width : '100%',
									height : gridHeight,
									orientation : 'horizontal',
									panels : [ {
										size : 280,
										collapsible : false
									}, {
										size : gridHeight - 280,
										collapsed : true,
										collapsible : true
									} ]
								});
								// initialize jqxGrid
								// prepare the data
								var source = {
									datatype : "json",
									datafields : [ {
										name : 'id',
										type : 'string'
									}, {
										name : 'mo',
										type : 'string'
									}, {
										name : 'kpiInfo',
										type : 'string'
									}, {
										name : 'level',
										type : 'string'
									}, {
										name : 'status',
										type : 'string'
									}, {
										name : 'attr_businessSystem',
										type : 'String'
									}, {
										name : 'hostName',
										type : 'string'
									}, {
										name : 'attr_ipaddr',
										type : 'string'
									}, {
										name : 'title',
										type : 'string'
									}, {
										name : 'firstOccurTime',
										type : 'string'
									}, {
										name : 'lastOccurTime',
										type : 'string'
									}, {
										name : 'occurTimes',
										type : 'string'
									}, {
										name : 'aknowledgeTime',
										type : 'string'
									}, {
										name : 'attr_result',
										type : 'string'
									}, {
										name : 'attr_actorUser',
										type : 'string'
									}, {
										name : 'content_t',
										type : 'string'
									},
									{
										name : 'typeId',
										type : 'string'
									}

									],
									beforeprocessing : function(data) {
										
										if (data.length == 0) {
											source.totalrecords = 0;
										} else {
											//alert(data[data.length - 1].totalcount);
										
											source.totalrecords = data[data.length - 1].totalcount;
										}

									},
									updaterow : function(rowid, rowdata, commit) {
										var url = '${ctx}/pages/alarm/aknowledge?content='
												+ rowdata.attr_result
												+ '&alarmId=' + rowdata.id;
										$.ajax({
											cache : false,
											dataType : 'json',
											url : url,
											success : function(data, status,
													xhr) {
												//alert("success");
											},
											error : function(jqXHR, textStatus,
													errorThrown) {
												//alert(errorThrown);
												//commit(false);
											}
										});
										commit(true);
									},
									sort : function() {
										// update the grid and send a request to the server.
										$("#alarmGrid").jqxGrid(
												'updatebounddata');
									},
									id : 'id',
									url : '${ctx}/pages/alarm/${sourceurl}'
								};
								$('#alarmGrid').on('rowdoubleclick', function (event) {
								     $('#alarmGrid').jqxGrid('clearselection');
									$("#alarmGrid").jqxGrid('selectrow',event.args.rowindex);
									showDetailDataForGrid(event.args.rowindex);
								 });
								function loadGridData(source) {
									var statusimagerenderer = function(row,
											datafield, value) {
										if(value!=0){
											return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/confirmed.gif"/>';
										}
										return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/newalarm.gif"/>';
									};
									var levelimagerenderer = function(row,
											datafield, value) {
										return '<img style="margin-left: 10px;padding-top:5px" height="15" width="15" src="${ctx}/resources/images/level' + value + '.png"/>';
									};
									var dataAdapter = new $.jqx.dataAdapter(
											source);
									$("#alarmGrid").jqxGrid({
										width : '100%',
										height : gridHeight,
										source : dataAdapter,
										columnsresize : true,
										localization : getLocalization(),
										pageable : true,
										pagesize : 20,
										//filterable: true,
										sortable : true,
										//page
										virtualmode : true,

										rendergridrows : function() {
										
										   // alert("datas:"+dataAdapter.records);
										   
											return dataAdapter.records;
											
										},
										selectionmode : 'checkbox',
										altrows : true,
										enabletooltips : true,
										columns : [ {
											text : '编号',
											datafield : 'id',
											width : 10,
											hidden : true
										}, {
											text : 'MOID',
											datafield : 'mo',
											width : 10,
											hidden : true
										}, {
											text : 'typeId',
											datafield : 'typeId',
											width : 10,
											hidden : true
										},{
											text : 'kpiInfo',
											datafield : 'kpiInfo',
											width : 10,
											hidden : true
										}, {
											text : '级别',
											datafield : 'level',
											width : 40,
											cellsrenderer : levelimagerenderer
										}, {
											text : '状态',
											datafield : 'status',
											width : 40,
											cellsrenderer : statusimagerenderer
										}, {
											text : '业务系统',
											datafield : 'attr_businessSystem',
											width : 80
										}, {
											text : '设备名',
											datafield : 'hostName',
											width : 80
										}, {
											text : 'IP地址',
											datafield : 'attr_ipaddr',
											minwidth : 100 
										}, {
											text : '告警标题',
											datafield : 'title',
											minwidth : 180
										}, {
											text : '开始时间',
											datafield : 'firstOccurTime',
											minwidth : 150
										}, {
											text : '最后时间',
											datafield : 'lastOccurTime',
											minwidth : 150
										}, {
											text : '次数',
											datafield : 'occurTimes',
											minwidth : 20
										}, {
											text : '告警内容',
											datafield : 'content_t',
											minwidth : 120
										}, {
											text : '处理结果',
											datafield : 'attr_result',
											minwidth : 50
										}, {
											text : '处理人',
											datafield : 'attr_actorUser',
											minwidth : 30
										}]
									});
								}
								
								loadGridData(source);
								// create context menu
								var contextMenu = $("#Menu").jqxMenu({
									width : 120,
									//height : 300,
									autoOpenPopup : false,
									mode : 'popup'
								});

								$("#alarmGrid").on('contextmenu', function() {
									return false;
								});
                                function showDetailDataForGrid(rowIndex){
                                	$('#historyPanel').show();
									$('#alarmGrid').jqxGrid({height : gridHeight - 250});
									
									var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowIndex);
									var url = '${ctx}/pages/alarm/getAlarmDetail?alarmId='+ rowdata.id;
									moId = rowdata.mo;
									kpiInfo = rowdata.kpiInfo;
									alarmId = rowdata.id;
									$.get(url,function(data) {
														$("#hostName").text(data.alarm.hostName == null ? "": data.alarm.hostName);
														$("#attr_ipaddr").text(
																		data.alarm.attr_ipaddr == null ? ""
																				: data.alarm.attr_ipaddr);
														$("#attr_businessSystem").text(
																		data.alarm.attr_businessSystem == null ? ""
																				: data.alarm.attr_businessSystem);
														$("#attr_position").text(data.alarm.attr_position == null ? "": data.alarm.attr_position);
														$("#attr_yongtu").text("");
														$("#attr_actorUser").text(data.alarm.attr_actorUser == null ? "": data.alarm.attr_actorUser);
														$("#level").text(data.alarm.level == null ? "": data.alarm.level);
														$("#status").text(data.alarm.status == null ? ""
																				: data.alarm.status);
														$("#content_t").text(data.alarm.content_t == null ? ""
																				: data.alarm.content_t);
														$("#firstOccurTime").text(data.alarm.firstOccurTime == null ? ""
																				: data.alarm.firstOccurTime);
														$("#occurTimes").text(
																		data.alarm.occurTimes == null ? ""
																				: data.alarm.occurTimes);
														$("#lastOccurTime").text(
																		data.alarm.lastOccurTime == null ? ""
																				: data.alarm.lastOccurTime);
														$("#aknowledgeTime").text(
																		data.alarm.aknowledgeTime == null ? ""
																				: data.alarm.aknowledgeTime);
														$("#attr_actorUser1").text(
																		data.alarm.attr_actorUser == null ? ""
																				: data.alarm.attr_actorUser);
														$("#source").text(data.alarm.source == null ? ""
																				: data.alarm.source);
														$("#processRes").empty();
														 if(data.comments.length>0){
															 $("#processRes").append("<thead><tr><th class='W30'>处理人</th> <th class='W30'>处理时间</th> <th class='W30'>处理结果</th> </tr></thead>")
															for(var n=0;n<data.comments.length;n++){
																 $("#processRes").append("<tr  align='left'>"
													                    
																           +"<td class='W30'>"+data.comments[n].createBy+"</td>"
											                                +"<td class='W30'>"+getSmpFormatDateByLong(data.comments[n].createTime,true)+"</td>"
											                                +"<td class='W30'>"+data.comments[n].content+"</td>"
											                           +"</tr>");     
															}
														 }else{
															 $("#processRes").append("<thead><tr><th class='W30'>处理人</th> <th class='W30'>处理时间</th> <th class='W30'>处理结果</th> </tr></thead>"); 
														 }
														
													});
									$('#deletedAlarmGrid').hide();
									$('#jqxTabs').show();
									$('#mainSplitter').jqxSplitter('expand');
                                }
								// 右键选择菜单.
								$("#Menu").on('itemclick',
												function(event) {
													var args = event.args;
													var rowindex = $("#alarmGrid").jqxGrid('getselectedrowindex');
													var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
													alarmId=rowdata.id;
													if ($.trim($(args).text()) == $("#aknowledge").text()) {
														//editrow = rowindex; 
														$("#renling").jqxWindow(
																		{
																			showCollapseButton : true,
																			isModal : true,
																			resizable : false,
																			maxHeight : 496,
																			height : 496,
																			width : 500,
																			initContent : function() {
																				$('#renling')
																						.jqxWindow(
																								'focus');
																			}
																		});
														// show the popup window.
														$("#renling").jqxWindow('show');
														logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmId;
														
														batchFilter='alarmIdList='+alarmId;
														loadLogGridData(logsource);
													} else if ($.trim($(args).text()) == $("#detail").text()) {
														showDetailDataForGrid(rowindex);
													}
													else if ($.trim($(args).text()) == $("#performance").text()) {
														var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
														moId = rowdata.mo;
														var typeId=rowdata.typeId;
														var attr_businessSystem=rowdata.attr_businessSystem;
														
														if(attr_businessSystem==null){
															attr_businessSystem="";
														}
														if(typeId=="HARDWARE_HP"){
															window.location.href='${ctx}/performance/goToHardwarePerformanceListView?moId='+moId+'&biz='+encodeURI(encodeURI(attr_businessSystem));
														}
														if(typeId=="HARDWARE_OTHER"){
															window.location.href='${ctx}/performance/goToOtherHardwarePerformanceListView?moId='+moId+'&biz='+encodeURI(encodeURI(attr_businessSystem));
														}
														if(typeId.indexOf("HOST_")!=-1){
															window.location.href='${ctx}/performance/goToHostPerformanceListView?moId='+moId+'&biz='+encodeURI(encodeURI(attr_businessSystem));
														}
														if(typeId.indexOf("NETWORK_")!=-1){
															window.location.href='${ctx}/performance/goToNetworkPerformanceListView?moId='+moId+'&biz='+encodeURI(encodeURI(attr_businessSystem));
														}
														if(typeId.indexOf("DATABASE_")!=-1){
															window.location.href='${ctx}/performance/goToDBPerformanceListView?moId='+moId+'&biz='+encodeURI(encodeURI(attr_businessSystem));
														}
														
														}else if ($.trim($(args).text()) == $("#history").text()) {
														$('#historyPanel').show();
														$('#alarmGrid').jqxGrid({height : gridHeight - 250});
														
														//$('#jqxTabs').jqxTabs('select', 1);
														
														 var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
														moId = rowdata.mo;
														kpiInfo = rowdata.kpiInfo;
														alarmId = rowdata.id;
														source.url = '${ctx}/pages/alarm/getPastRecordsData?moId='
															+ moId
															+ '&kpiInfo='
															+ kpiInfo;
													    loadHistoryGridData(source);
													    /* moId = "";
													    kpiInfo = "";
													    alarmId = ""; */
													    $('#deletedAlarmGrid').hide();
														$('#jqxTabs').show();
														//$('#jqxTabs').jqxTabs('select', 0);
														$('#mainSplitter').jqxSplitter('expand'); 
														try
														  {
															$('#jqxTabs').jqxTabs('select', 1);
														  }
														catch(err)
														  {
														 
														  console.log(err);
														  }
														
												
										          }else if($.trim($(args).text()) == $("#clear").text()){
										    	    var rowindex = $("#alarmGrid").jqxGrid('getselectedrowindex');
										    	    
										    	        alarmIdList= new Array();
														if(rowindex<0){
															$().toastmessage('showToast', {
													            text     : '请选择一条更新记录！',
													            sticky   : false,
													            position : 'top-center',
													            type     : 'warning',
													            closeText: ''
													        });
															return;
														}
														var data = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
														alarmId=data.id;
														alarmIdList.push(data.id);
														$("#windowtitle").text("清除");
														$("#renling").jqxWindow(
																		{
																			showCollapseButton : true,
																			isModal : true,
																			resizable : false,
																			maxHeight : 496,
																			height : 496,
																			width : 500,
																			initContent : function() {
																				$(
																						'#renling')
																						.jqxWindow(
																								'focus');
																			}

																		});
														$('#renling').jqxWindow(
																'show');	
														logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmId;
														batchFilter='alarmIdList='+alarmId;
														loadLogGridData(logsource);
														
										          }
                                                  else if ($.trim($(args).text()) == $("#forward").text()) {
														$("#alarmforward").jqxWindow({
																		 showCollapseButton: true, isModal:true,resizable: false, height: 392, width: 600,
														                    initContent: function () {
														                        $('#alarmforward').jqxWindow('focus');
														                    }
																	}); 
														//var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
														// show the popup window.
														var rowdata = $('#alarmGrid').jqxGrid('getrowdata',rowindex);
														$("#alarm_name").val(rowdata.hostName==null?"":rowdata.hostName);
									                    $("#alarm_index").val(rowdata.lastname==null?"":rowdata.lastname);
									                    $("#forward_content").val(rowdata.content_t==null?"":rowdata.content_t);
									                    $("#alarm_title").val(rowdata.title==null?"":rowdata.title);
									                    $("#start_time").val(rowdata.firstOccurTime==null?"":rowdata.firstOccurTime);
									                    $("#alarm_level").val(rowdata.level==null?"":rowdata.level);
									                    alarmId=rowdata.id;
									                    $("#alarmforward").jqxWindow('show');
													}    
												});

								$("#alarmGrid").on('rowclick',
												function(event) {
													if (event.args.rightclick) {
														$('#alarmGrid')
																.jqxGrid(
																		'clearselection');
														$("#alarmGrid")
																.jqxGrid(
																		'selectrow',
																		event.args.rowindex);
														var scrollTop = $(
																window)
																.scrollTop();
														var scrollLeft = $(
																window)
																.scrollLeft();
														contextMenu
																.jqxMenu(
																		'open',
																		parseInt(event.args.originalEvent.clientX)
																				+ 5
																				+ scrollLeft,
																		parseInt(event.args.originalEvent.clientY)
																				+ 5
																				+ scrollTop);
														return false;
													}
												});

								function loadLevelData(level) {
									source.url = '${ctx}/pages/alarm/getActiveAlarmDataByCriteria?level='
											+ level + "&pagenum=0";
									
									isClickSearch=true;
									if(isClickSearch==true){
										/* $.getJSON(
												'${ctx}/pages/alarm/getActiveAlarmDataByCriteria?level='+ level + "&pagenum=0"
												+ '&isBatchUpdate='
												+ isClickSearch,
												function(data) {
															alarmIdList= [];
															for(var i=0;i<data.length-1;i++){
																alarmIdList.push(data[i].id);
															}
															isClickSearch=true;
															
												}); */
										batchFilter='level='+ level;
									}
									loadGridData(source);
									$('#alarmGrid').jqxGrid('refresh');
								}
								$("#level1").click(function() {
									$('#alarmGrid').jqxGrid('clearselection');
									loadLevelData(1);
									$('#mainSplitter').jqxSplitter('collapse');
									$('#alarmGrid').jqxGrid({
										height : gridHeight
									});
								});
								$("#level2").click(function() {
									$('#alarmGrid').jqxGrid('clearselection');
									loadLevelData(2);
									$('#mainSplitter').jqxSplitter('collapse');
									$('#alarmGrid').jqxGrid({
										height : gridHeight
									});
								});
								$("#level3").click(function() {
									$('#alarmGrid').jqxGrid('clearselection');
									loadLevelData(3);
									$('#mainSplitter').jqxSplitter('collapse');
									$('#alarmGrid').jqxGrid({
										height : gridHeight
									});
								});
								$("#level4").click(function() {
									$('#alarmGrid').jqxGrid('clearselection');
									loadLevelData(4);
									$('#mainSplitter').jqxSplitter('collapse');
									$('#alarmGrid').jqxGrid({
										height : gridHeight
									});
								});
								$("#level5").click(function() {
									$('#alarmGrid').jqxGrid('clearselection');
									loadLevelData(5);
									$('#mainSplitter').jqxSplitter('collapse');
									$('#alarmGrid').jqxGrid({
										height : gridHeight
									});
								});
								$("#newAlarm").click(
												function() {
													$('#alarmGrid').jqxGrid(
															'clearselection');
													source.url = '${ctx}/pages/alarm/getActiveAlarmDataByCriteria?isNewalarm=1';
													isClickSearch=true;
													if(isClickSearch==true){
													/* 	$.getJSON('${ctx}/pages/alarm/getActiveAlarmDataByCriteria?isNewalarm=1'+ '&isBatchUpdate='+ isClickSearch,
																function(data) {
																			alarmIdList= [];
																			for(var i=0;i<data.length-1;i++){
																				alarmIdList.push(data[i].id);
																			} 
																			
																
																						
																					});*/
													batchFilter='isNewalarm=1';
													isClickSearch=true;
													loadGridData(source);
													}
												});
								$("#myAlarm").click(
												function() {
													$('#alarmGrid').jqxGrid(
															'clearselection');
													source.url = '${ctx}/pages/alarm/getActiveAlarmDataByCriteria?isMyalarm=1';
													isClickSearch=true;
													if(isClickSearch==true){
														/* $.getJSON('${ctx}/pages/alarm/getActiveAlarmDataByCriteria?isMyalarm=1'+ '&isBatchUpdate='+ isClickSearch,
																function(data) {
																			alarmIdList= [];
																			for(var i=0;i<data.length-1;i++){
																				alarmIdList.push(data[i].id);
																			}
																			isClickSearch=true;
																
																						
																					}); */
														batchFilter='isMyalarm=1';
													}
													loadGridData(source);
												});
							});
							
							//扩展Date的format方法   
						    Date.prototype.format = function (format) {  
						        var o = {  
						            "M+": this.getMonth() + 1,  
						            "d+": this.getDate(),  
						            "h+": this.getHours(),  
						            "m+": this.getMinutes(),  
						            "s+": this.getSeconds(),  
						            "q+": Math.floor((this.getMonth() + 3) / 3),  
						            "S": this.getMilliseconds()  
						        }
						        if (/(y+)/.test(format)) {  
						            format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));  
						        }  
						        for (var k in o) {  
						            if (new RegExp("(" + k + ")").test(format)) {  
						                format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));  
						            }  
						        }  
						        return format;  
						    }  
						    /**   
						    *转换日期对象为日期字符串   
						    * @param date 日期对象   
						    * @param isFull 是否为完整的日期数据,   
						    *               为true时, 格式如"2000-03-05 01:05:04"   
						    *               为false时, 格式如 "2000-03-05"   
						    * @return 符合要求的日期字符串   
						    */    
						    function getSmpFormatDate(date, isFull) {  
						        var pattern = "";  
						        if (isFull == true || isFull == undefined) {  
						            pattern = "yyyy-MM-dd hh:mm:ss";  
						        } else {  
						            pattern = "yyyy-MM-dd";  
						        }  
						        return getFormatDate(date, pattern);  
						    }  
						    /**   
						    *转换当前日期对象为日期字符串   
						    * @param date 日期对象   
						    * @param isFull 是否为完整的日期数据,   
						    *               为true时, 格式如"2000-03-05 01:05:04"   
						    *               为false时, 格式如 "2000-03-05"   
						    * @return 符合要求的日期字符串   
						    */    
						 
						    function getSmpFormatNowDate(isFull) {  
						        return getSmpFormatDate(new Date(), isFull);  
						    }  
						    /**   
						    *转换long值为日期字符串   
						    * @param l long值   
						    * @param isFull 是否为完整的日期数据,   
						    *               为true时, 格式如"2000-03-05 01:05:04"   
						    *               为false时, 格式如 "2000-03-05"   
						    * @return 符合要求的日期字符串   
						    */    
						 
						    function getSmpFormatDateByLong(l, isFull) {  
						        return getSmpFormatDate(new Date(l), isFull);  
						    }  
						    /**   
						    *转换long值为日期字符串   
						    * @param l long值   
						    * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
						    * @return 符合要求的日期字符串   
						    */    
						 
						    function getFormatDateByLong(l, pattern) {  
						        return getFormatDate(new Date(l), pattern);  
						    }  
						    /**   
						    *转换日期对象为日期字符串   
						    * @param l long值   
						    * @param pattern 格式字符串,例如：yyyy-MM-dd hh:mm:ss   
						    * @return 符合要求的日期字符串   
						    */    
						    function getFormatDate(date, pattern) {  
						        if (date == undefined) {  
						            date = new Date();  
						        }  
						        if (pattern == undefined) {  
						            pattern = "yyyy-MM-dd hh:mm:ss";  
						        }  
						        return date.format(pattern);  
						    }  
		</script>
		
<script src="${ctx}/pages/avmon_back_gif.js"></script>

</body>
</html>
