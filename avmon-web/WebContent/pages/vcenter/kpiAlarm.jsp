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
  <script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxgrid.columnsresize.js"></script>
  <%-- <script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqx-all.js"></script> --%>
 </head> 
 <body> 
 <input id="kpiAlarmMoId" style="display:none" value="<%=request.getParameter("moId")%>">
<!-- jay add -->
   <!-- 右键菜单 -->
			    
			      <div id="Menu" class="AvmonRightC" style="z-index:99999;display:none">
				  <ul>
					<li><span class="AvmonRightC_Icon R-icon_1"></span><span
						id="aknowledge" class="AvmonRightC_Name">认领</span></li>
					<li><span class="AvmonRightC_Icon R-icon_2"></span><span
						id="clear" class="AvmonRightC_Name">清除</span></li>
					<li><span class="AvmonRightC_Icon  R-icon_6"></span><span
						id="forward" class="AvmonRightC_Name">告警前转</span></li>
				  </ul>
			     </div> 
    <!-- 右键相关的菜单 -->
    <!-- 告警前转-->
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
                  
                 <!--  <div class="overlay-El-line">
                  <div style="margin-bottom:4px;">告警标题</div>
                 <div><input id="alarm_title" type="text" placeholder="" style="width:195px;" class=""></div> 
                  <div class="jqx-validator-error-label">请输入位置</div>                   
                  </div> -->
                  
                  
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


 <!--高级查询 window open -->
			<div
				style="width: 100%; height: 350px; display: none; margin-top: 50px;"
				id="mainDemoContainer" style="display:none">
				<div id="searchwindow">
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

								<!-- <div class="overlay-El-line">
									<div style="margin-bottom: 4px;">业务系统</div>
									<div id="digits1" class="jqx-validator-error-element"></div>
									<div>
										<input id="s_attr_businessSystem" type="text" placeholder=""
											style="width: 195px;" class="">
									</div>
									<div class="jqx-validator-error-label">请输入业务系统</div>
								</div> -->

								<!-- <div class="overlay-El-line">
									<div style="margin-bottom: 4px;">位置</div>
									<div>
										<input id="s_attr_position" type="text" placeholder=""
											style="width: 195px;" class="">
									</div>
									<div class="jqx-validator-error-label">请输入位置</div>
								</div> -->
							</div>

							<div class="left">
								<!-- <div class="overlay-El-line">
									<div style="margin-bottom: 4px;">IP地址</div>
									<div>
										<input id="s_attr_ipaddr" type="text"
											placeholder="" style="width: 195px;"
											class="">
									</div>
									<div class="jqx-validator-error-label">请输入IP地址</div>
								</div> -->

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
    
    <!-- 右键相关的菜单end -->
    
       <div  class="margin10">
                       <!--    <p>右键菜单
                             </p> -->
                           <div class=" paddingL4">
					     <div class="left paddingL4 NavMainBtn">
							<a href="#" class="Avmon-button" id="showWindowButton">高级查询</a>
						</div>
						<div class="left Padding14 NavMainBtn">
							<a href="#" class="Avmon-button"><span id="batchupdate">批量更新告警状态</span></a>
						</div> 
					<div class="blank20"></div>
						
					    </div>
                             <div id="alarmGrid"> </div>

                         </div>
                       
                    </div>

<!-- end -->
<script type="text/javascript">
	$(document).ready(function() {
		//公共部分
     	   var alarmIdList = [];
			var moId = "";
			var alarmId = "";
        	var hputotal=0;
        	var wintotal=0;
        	var linuxtotal=0;
        	var sunostotal=0;
        	var hostip="hostip";
        	var alarmGridId="alarmGrid";
        	var KPIlist="KPIlist";
            var jqxWidgetData="jqxWidgetData1";
            var startDate="startDate";
            var toDate="toDate";
            var startHour="startHour";
            var endHour="endHour";
            var queryKpi="queryKpi";
            var kpigrid="kpigrid";
            var num=1;
            var window="window";
            var batchupdate="batchupdate";
		   var moId = $("#kpiAlarmMoId").val();
		   var source = {
					datatype : "json",
					datafields : [ {
						name : 'ID',
						type : 'string'
					}, {
						name : 'MO_ID',
						type : 'string'
					},
					{
						name : 'type',
						type : 'string'
					},
					 {
						name : 'KPI_CODE',
						type : 'string'
					}, {
						name : 'LEVEL',
						type : 'string'
					}, {
						name : 'STATUS',
						type : 'string'
					}, {
						name : 'AMOUNT',
						type : 'String'
					},
					{
						name : 'OCCUR_TIMES',
						type : 'String'
					},
					{
						name : 'MO_CAPTION',
						type : 'string'
					}, {
						name : 'CONTENT',
						type : 'string'
					}, {
						name : 'AMOUNT',
						type : 'string'
					},{
						name : 'attr_result',
						type : 'string'
					}, {
						name : 'attr_actorUser',
						type : 'string'
					}

					],
					beforeprocessing : function(data) {
						if (data.length == 0) {
							source.totalrecords = 0;
						} else {
							source.totalrecords = data[data.length - 1].totalcount;
						}

					},
					updaterow : function(rowid, rowdata, commit) {
					console.log(rowdata.ID);
						var url = '${ctx}/pages/alarm/aknowledge?content='
								+ rowdata.attr_result
								+ '&alarmId=' + rowdata.ID;
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
						$("#"+alarmGridId).jqxGrid(
								'updatebounddata');
					},
					id : 'id',
					url : '${ctx}/performance/getAlarmData?moId='+moId
				};
  	            loadGridData(source,alarmGridId);
				$("#search").click(
						function() {
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
						 	//alert("moId is:"+$("#kpiAlarmMoId").val());
							source.url = '${ctx}/performance/getAlarmDataByCriteria?s_moCaption='
									+ s_moCaption
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
									+ s_end_time+'&moId='+$("#kpiAlarmMoId").val();
							       $.getJSON(
									'${ctx}/performance/getAlarmDataByCriteria?s_moCaption='
									+ s_moCaption
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
									+ s_end_time+'&moId='+$("#kpiAlarmMoId").val(),
									function(data) {
												alarmIdList= [];
												for(var i=0;i<data.length-1;i++){
													alarmIdList.push(data[i].id);
												}
												isClickSearch=true;
									}); 
							       loadGridData(source,alarmGridId);
									
							$("#searchwindow").jqxWindow(
									'close');
						});
     //queryKpi end
				//右键菜单弹出功能部分js
				// 右键选择菜单.
				$("#Menu").on('itemclick',
								function(event) {
									var args = event.args;
									var rowindex = $("#"+alarmGridId).jqxGrid('getselectedrowindex');
									var rowdata = $('#'+alarmGridId).jqxGrid('getrowdata',rowindex);
									alarmId=rowdata.ID;
									if ($.trim($(args).text()) == $("#aknowledge").text()) {
										//editrow = rowindex; 
										$("#renling")
												.jqxWindow(
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
										$("#renling")
												.jqxWindow(
														'show');
										logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmId;
										loadLogGridData(logsource);
									}else if($.trim($(args).text()) == $("#clear").text()){
						    	    var rowindex = $("#"+alarmGridId).jqxGrid('getselectedrowindex');
						    	    
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
										var data = $('#'+alarmGridId).jqxGrid('getrowdata',rowindex);
										alarmId=data.ID;
										alarmIdList.push(data.ID);
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
			                              
										var rowdata = $('#'+alarmGridId).jqxGrid('getrowdata',rowindex);
										console.log(rowdata);
										$("#alarm_name").val(rowdata.MO_CAPTION==null?"":rowdata.MO_CAPTION);
					                    $("#alarm_index").val(rowdata.KPI_CODE==null?"":rowdata.KPI_CODE);
					                    $("#forward_content").val(rowdata.CONTENT==null?"":rowdata.CONTENT);
					                   // $("#alarm_title").val(rowdata.title==null?"":rowdata.title);
					                    $("#start_time").val(rowdata.firstOccurTime==null?"":rowdata.firstOccurTime);
					                    $("#alarm_level").val(rowdata.LEVEL==null?"":rowdata.LEVEL);
					                    alarmId=rowdata.id;
					                    $("#alarmforward").jqxWindow('show');
									}    
								});
				// end
				
				//renling start
				//renling log grid
				
				//batchupdate
									$("#batchupdate").click(function() {
										                /*  alert(isClickSearch);
										                alert(alarmIdList.length);  */
										                var rowindexes = $('#alarmGrid').jqxGrid('getselectedrowindexes');
										                if(isClickSearch==false&&((rowindexes.length==0||rowindexes[0]==-1))){
										                	
										              			 $("#newWindow").remove();
										       	        		   $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>请选择一条记录！ </div></div>');
										       	                   $('#newWindow').jqxWindow({ isModal : true,height: 80, width: 150});
										       	                   return;
										              		  
										                	return;
										                }
										                
										                if(isClickSearch==false&&(rowindexes.length>0)){
										                	for (var i = 0; i < rowindexes.length; i++) {
																var data = $('#alarmGrid').jqxGrid('getrowdata',rowindexes[i]);
																alarmIdList.push(data.id);
															}
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
										                }
										                if(isClickSearch==true&&(alarmIdList.length>0)){
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
																					$('#renling').jqxWindow('focus');
																				}

																			});
															$('#renling').jqxWindow(
																	'show');
															logsource.url='${ctx}/pages/alarm/getAlarmComments?alarmId='+alarmIdList[0];
															loadLogGridData(logsource);
										                	return;
										                }
													});
									//end
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
					
				};
				 function loadGridData(source,alarmGridId) {
						var statusimagerenderer = function(row,
								datafield, value) {
							if(value!=0){
								return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/confirmed.gif"/>';
							}
							return '<img style="margin-left: 10px;padding-top:5px" height="20" width="20" src="${ctx}/resources/images/newalarm.gif"/>';
						};
						var levelimagerenderer = function(row,
								datafield, value) {
							//alert("level is:"+value);
							return '<img style="margin-left: 10px;padding-top:5px" height="15" width="15" src="${ctx}/resources/images/level' + value + '.png"/>';
						};
						var dataAdapter = new $.jqx.dataAdapter(
								source);
						$("#"+alarmGridId).jqxGrid({
							width : '100%',
							height : '80%',
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
								return dataAdapter.records;
							},
							selectionmode : 'checkbox',
							altrows : true,
							enabletooltips : true,
							columns : [ {
								text : '编号',
								datafield : 'ID',
								width : 10,
								hidden : true
							}, {
								text : 'MO_ID',
								datafield : 'MO_ID',
								width : 10,
								hidden : true
							}, {
								text : 'type',
								datafield : 'type',
								width : 10,
								hidden : true
							},
							{
								text : 'KPI_CODE',
								datafield : 'KPI_CODE',
								width : 10,
								hidden : true
							}, {
								text : '级别',
								datafield : 'LEVEL',
								width : 40,
								cellsrenderer : levelimagerenderer
							}, {
								text : '状态',
								datafield : 'STATUS',
								width : 40,
								cellsrenderer : statusimagerenderer
							}, {
								text : '主机名',
								datafield : 'MO_CAPTION',
								width : 150
							},
							 {
								text : '告警内容',
								datafield : 'CONTENT',
								minwidth : 150
							},{
								text : '时间',
								datafield : 'OCCUR_TIMES',
								minwidth : 50
							}, {
								text : '次数',
								datafield : 'AMOUNT',
								minwidth : 20
							} , {
								text : '处理结果',
								datafield : 'attr_result',
								minwidth : 120
							},{
								text : '处理人',
								datafield : 'attr_actorUser',
								minwidth : 80
							}]
						});
					}
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
						} ]
					});
				}
				loadLogGridData(logsource);
				var editrow = -1;

				$("#confirm").click(function() {
									if ($("#windowtitle").text() == '认领') {
										var rowindex = $("#"+alarmGridId).jqxGrid('getselectedrowindex');
										var row = $("#"+alarmGridId).jqxGrid('getrowdata',rowindex);
										row.attr_actorUser = '${userId}';
										row.attr_result = $("#detailcontent").val();
										row.status=1;
										$('#alarmGrid').jqxGrid('updaterow',rowindex,row);
										$("#renling").jqxWindow('hide');
									 } else {
									  
								        var alarmIds="";
								        alarmIds=alarmIdList.join(",");
											     $.post('${ctx}/pages/alarm/batchClose',
											      {
											    	  alarmIdList:alarmIds
											      },
											      function (data) 
											      {
											    	  $("#renling").jqxWindow('hide');
													  $('#'+alarmGridId).jqxGrid('updatebounddata');
													  
													  alarmIdList = [];
												}
											    ); 
											   
									}

								});

				$("#cancel").click(function() {
					$("#renling").jqxWindow('hide');
				});
				//end
				
				

	       
	           
	            //show searchwindows
	             $('#showWindowButton').click(function() {
				   $('#searchwindow').jqxWindow('open');
				});
	             $("#"+alarmGridId).on('rowclick',
      					function(event) {
      						if (event.args.rightclick) {
      							
      							$('#'+alarmGridId).jqxGrid('clearselection');
      							$("#"+alarmGridId).jqxGrid('selectrow',event.args.rowindex);
      							var scrollTop = $(window).scrollTop();
      							var scrollLeft = $(window).scrollLeft();
      							var contextMenu = $("#Menu").jqxMenu({
      									width : 120,
      									//height : 300,
      									autoOpenPopup : false,
      									mode : 'popup'
      								  }); 
      			            
      			                 $("#"+alarmGridId).on('contextmenu', function() {
      				             return false;
      			                 });  
      							 contextMenu.jqxMenu('open',parseInt(event.args.originalEvent.clientX)+ 5+ scrollLeft,
      								            parseInt(event.args.originalEvent.clientY)+ 5+ scrollTop); 
      							return false;
      						}
      				});
				$("#searchwindow").jqxWindow({
										showCollapseButton : true,
										autoOpen : false,
										isModal : true,
										resizable : false,
										height : 350,
										width : 500,
										initContent : function() {
											$('#searchwindow').jqxWindow('focus');
										}
									});
	             
				$("#start_date").jqxDateTimeInput({ width: '200px',readonly: true, height: '18px',max: new Date(),formatString: 'yyyy/MM/dd'});
				$("#end_date").jqxDateTimeInput({ width: '200px', readonly: true,height: '18px', max: new Date(),formatString: 'yyyy/MM/dd'});
				//search start
				var isClickSearch=false;
				
				$("#searchcancel").click(function() {
					$("#searchwindow").jqxWindow('close');
				});
				$("#tab_close").click(function() {
					$('#mainSplitter').jqxSplitter('collapse');
					$('#'+alarmGridId).jqxGrid({
						height : gridHeight
					});
				});

				//end					
	             
	           
	        	
	});
</script>  
 </body>
</html>