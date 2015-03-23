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
<%--   <script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqx-all.js"></script> --%>
 </head> 
 <body> 
 <input id="kpiTrendMoId" style="display:none" value="<%=request.getParameter("moId")%>">
<!-- jay add -->
 <div>
                             <div class="KPINav">
                          	  <div class="left marginR15"><span class="LineH24 marginR40 left">提醒：KPI指标最多能选择5个。
                          	  </span><span class="LineH24 marginR6 left">Host:</span><span class="LineH24 marginR40 left" id="hostip">
                          	  </span><span id="jqxWidgetData1" class="left marginR40"></span> <span class=" LineH24 marginR6 left">
                          	  Time:</span><span class="LineH24 marginR6 left"><div id="startDate"></div></span>
                          	  <span id="startHour" class="left marginR6"></span>
                          	  <!-- <input type="text" placeholder="2014-08-08" style="width:120px; height:26px;"> -->
                          	  <!-- <span id="jqxWidgetData2" class="left marginR6"></span> -->
                          	  <span class="LineH24 marginR6 left">To</span>
                          	  <span class="LineH24 marginR6 left"><div id="toDate"></div>
                          	  <!-- <input type="text" placeholder="2014-08-08" style="width:120px; height:26px;"> --></span><span id="endHour" class="left marginR15"></span></div>
        	                  <div class="left marginR15" id="queryKpi"><a href="#"  class="Avmon-button"><b class="Search_icon2 Icon16" style="position:relative;top:3px;"></b>查询</a></div>

                          </div>
                          <div>
                      		   <div class="W20 left marginL10">
                                  <p class="f16px marginB10 marginT10">指标列表</p>
                      		      <div id="kpigrid" ></div>
                       		  </div>
                       		  <div class="W76 left marginL20">
                                  <p class="f16px marginB10 marginT10">KPI历史趋势</p>
                                  <div style="height:90%; width:90%;overflow-y: scroll;">
                                  <div id="chart1" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:20px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart2" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart3" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart4" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  <div id="chart5" style="border:1px solid #f4f4f4; width:100%; height:50%; margin:4px 0px;display:block"><span style="margin: 50px auto;display: block;text-align: center;"></span></div>
                                  </div>
                              </div>  
                          </div>                      
                         </div>

<!-- end -->
<script type="text/javascript">
	$(document).ready(function() {
		//公共部分
     	var alarmIdList = [];
		var moId = "";
		var alarmId = "";
    	var hostip="hostip";
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
		 var moId = $("#kpiTrendMoId").val();
		 var kpisource =
         {
		    datatype: "json",
         datafields:
         [
             { name: 'kpi', type: 'string'},
             { name: 'displayKpi', type: 'string'}
         ],
         id : 'kpi',
		 url : '${ctx}/config/kpiListNew?moId='+moId
         
      };
      var kpidataAdapter = new $.jqx.dataAdapter(kpisource);
		 // initialize jqxGrid4
        $("#"+kpigrid).jqxGrid(
       {
           width: '100%',
			height:'85%',
           source: kpidataAdapter,
           sortable : true,
           //editable: true,
           selectionmode : 'checkbox',
			altrows : true,
           columns: [
             { text: 'KPI', editable: 'available', datafield: 'kpi', width: '90%',hidden:true},
             { text: 'KPI', editable: 'available', datafield: 'displayKpi', width: '90%'} 
           ]
       }); 
		//alert("kpiView" + moId);
		 var hours = ['00:00', '01:00', '02:00', '03:00',
                 '04:00', '05:00', '06:00', '07:00', '08:00', '09:00', '10:00','11:00','12:00','13:00',
                 '14:00','15:00','16:00','17:00','18:00','19:00','20:00','21:00','22:00','23:00'
               ];
       	 var grainsizes = [
       	               "Original Date",
                          "By Hour",
                          "By Day",
      		        ];
		 //拦截部分的代码     
        $("#"+jqxWidgetData).jqxDropDownList({ source: grainsizes, selectedIndex: 0,  width: '130px', height: '25px',dropDownHeight: '90' });
        $("#"+startDate).jqxDateTimeInput({width: '160px', readonly: true,height: '25px',formatString: 'yyyy-MM-dd'});
        $("#"+toDate).jqxDateTimeInput({width: '160px', readonly: true,height: '25px',formatString: 'yyyy-MM-dd'});
        $("#"+startHour).jqxDropDownList({ source: hours, selectedIndex: 0,  width: '70px', height: '25px' });
        $("#"+endHour).jqxDropDownList({ source: hours, selectedIndex: 0,  width: '70px', height: '25px' });
                    
         //queryKpi begin
                 	$("#"+queryKpi).click(function() {
                 		 $("#chart1").hide();
		            		$("#chart2").hide();
		            		$("#chart3").hide();
		            		$("#chart4").hide();
		            		$("#chart5").hide();
		            		$("#chart10").hide();
  		                $("#chart11").hide();
  		                $("#chart12").hide();
  		                $("#chart13").hide();
  		            	$("#chart14").hide(); 
                 		var textRotationAngle=0;
                		var rowindexes = $('#'+kpigrid).jqxGrid('getselectedrowindexes');
                		if(rowindexes.length==0){
               			 $("#newWindow").remove();
        	        		   $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>请选择一条KPI！ </div></div>');
        	                   $('#newWindow').jqxWindow({isModal : true, height: 80, width: 150});
        	                   return;  
               		    }
                		 if(rowindexes.length>5){
                			 $('#'+kpigrid).jqxGrid('clearselection');
         			        $("#newWindow").remove();
                		   $(document.body).append('<div id="newWindow" style="z-index:20001"><div>Warning</div><div>KPI最多只能选择5条记录！ </div></div>');
                           $('#newWindow').jqxWindow({ isModal : true,height: 80, width: 150});
                           return;
                		   }

                		 var startHourTemp =$('#'+startHour).jqxDropDownList('getSelectedItem');
                		 var endHourTemp =$('#'+endHour).jqxDropDownList('getSelectedItem');
                		 var grainsize = $("#"+jqxWidgetData).jqxDropDownList('getSelectedIndex')+1;
                		 var startTime = $('#'+startDate).jqxDateTimeInput('getText');
                		  var endTime = $('#'+toDate).jqxDateTimeInput('getText');
                		  startTime=startTime+" "+startHourTemp.label;
                		  endTime=endTime+" "+endHourTemp.label;
                		  for(var i=0;i<rowindexes.length;i++){
                  			var row = $("#"+kpigrid).jqxGrid('getrowdata',rowindexes[i]);
                  			var yAxis=row.displayKpi;
                  			var kpiCode=row.kpi;
                  			var baseUnit='';
                  			
                  			 if(grainsize==3){
                 				baseUnit='day';
                 				textRotationAngle=0;
                 			}
                 			else if(grainsize==2){
                 				baseUnit='hour';
                 				textRotationAngle=-75;
                 			}
                 			else{
                 				baseUnit='minute';
                 				textRotationAngle=-75;
                 			}
                  			$("#chart"+(i+num)).show();
                  			loadTrendChart("",kpiCode,startTime,endTime,moId,grainsize,"chart"+(i+num),yAxis,baseUnit);
                  			
                  		}
                 	      function loadTrendChart(ampInstId,kpiCode,startTime,endTime,moId,grainsize,id,yAxis,baseUnit){
                              var ampInstId="";
                     		   var url='${ctx}/config/dynamicKpiChartData?ampInstId='+ampInstId+'&kpiCode='+kpiCode+'&startTime='+startTime+'&endTime='+endTime+'&moId='+moId+'&grainsize='+grainsize;
                  			  //////////////
                              var data1 = new Array();
                              var datafields = new Array();
                              var instanceTemp;
                              var count=0;
                              $.blockUI({
                       		    message: 'Loading...',
                       		    css: { 
                       		    width:	'10%',
                       		    left: '40%',
                 		            border: 'none', 
                 		            padding: '15px', 
                 		            backgroundColor: '#000', 
                 		            '-webkit-border-radius': '10px', 
                 		            '-moz-border-radius': '10px', 
                 		            opacity: .5, 
                 		            color: '#fff' },
                 		            onBlock: function() { 
                 		            $.getJSON(url,function(data) {
                 		               
                 		             	for (var key in data[0]) 
                                     	{
                 		             	   count++;

                                     	} 

                                           if(count>=0&&count<=3){
                                         	    var row = {};
                                  	           instanceTemp="instance";
                                  	           row["dataField"] = instanceTemp;
                                  	           row["displayText"] ="All";
                                  	           data1[0] = row;
                                  	            var row1 = {};
                                  	            row1["name"] = "instance";
                                	           datafields[0] = row1;
                                           } else{
                                          	 var count1=0;

                                  	           for (var key in data[0]) 
                                                	{
                                  	        	   var row = {};
                                      	           var row1 = {};
                                  	        	   count1++;
                            		                if(count1<=count-2){
                            		                  instanceTemp="instance"+i;
                                     	              row["dataField"] = key;
                                     	              row["displayText"] =key;
                                     	              data1.push(row);
                            		             	
                            		             	  row1["name"] = key;
                                  	               datafields.push(row1);
                            		                }
                                                	}  
                                           }  
                 		             	  var row2 = {"name":"time"};
                                        datafields[count-2]=row2;
                                        var trendsource =
                                        {
                                            datatype: "json",
                                            datafields:datafields,
                                            url:url 
                                        }; 

                                             var trenddataAdapter = new $.jqx.dataAdapter(trendsource, { async: false, autoBind: true, loadError: function (xhr, status, error) { alert('Error loading "' + source.url + '" : ' + error); } });
                                             var months = ['01', '02', '03', '04', '05', '06', '07', '08', '09', '10', '11', '12'];
                                             var settings = {
                                                     title: "",
                                                     description: "",
                                                     enableAnimations: true,
                                                     showLegend: true,
                                                     padding: { left: 5, top: 5, right: 11, bottom: 5 },
                                                     titlePadding: { left: 10, top: 0, right: 0, bottom: 10 },
                                                     source: trenddataAdapter,
                                                     xAxis:
                                                         {
                                                             dataField: 'time',
                                                             formatFunction: function (value) {
                                                                 return months[value.getMonth()] + '-' + value.getDate()+' '+value.getHours()+':'+value.getMinutes()+':'+value.getSeconds();
                                                             },
                                                             toolTipFormatFunction: function (value) {
                                                                 return value.getFullYear() + '-' + months[value.getMonth()] + '-' + value.getDate()+' '+value.getHours()+':'+value.getMinutes()+':'+value.getSeconds();
                                                             },
                                                             showTickMarks: true,
                                                             type: 'date',
                                                             baseUnit: baseUnit,
                                                             tickMarksInterval: 1,
                                                             tickMarksColor: '#888888',
                                                             unitInterval: 1,
                                                             textRotationAngle: textRotationAngle,
                                                             showGridLines: false,
                                                             gridLinesInterval: 1,
                                                             gridLinesColor: '#888888',
                                                           
                                                             valuesOnTicks: true
                                                         },
                                                     colorScheme: 'scheme01',
                                                     seriesGroups:
                                                         [
                                                             {
                                                                 type: 'stackedline',
                                                                 valueAxis:
                                                                 {
                                                                  
                                                                     minValue: 0,
                                                                     description: yAxis,
                                                                     tickMarksColor: '#888888'
                                                                 },
                                                                
                                                                 series:data1
                                                             }
                                                         ]
                                                 }; 
                                                 
                                                  $('#'+id).jqxChart(settings);
                         		            	   $.unblockUI();
                 		             });
                 		           }
                              });
                 	      }
                 	     });
     //queryKpi end
                 
	});
</script>  
 </body>
</html>