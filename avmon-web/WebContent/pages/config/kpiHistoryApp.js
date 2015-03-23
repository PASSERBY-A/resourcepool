Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    //autoCreateViewport: true,
    name: 'CFG',
    controllers: [ 
        'KpiController'		
    ],
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            border:false,
            items: [
                {
                    id:'kpiHistoryDesk', 
	                layout: 'border',
	                items: [{ 
				            border:false,
				            split: true, 
							collapsible : true, 
							width : 212, 
							minSize : 212, 
							maxSize : 300,      
							region:'west',
							title:avmon.config.targetList,
							layout: 'fit',
							items:[Ext.create('CFG.view.KpiGrid')]
				        },{ 
				            border:false,
				            region: 'center',
							layout: 'border',
							itemId:'kpiHistoryPanel',
							items:[
							   {
							   title:avmon.config.kpiHistoryTendency,
							   region: 'north',
							   tbar:[
							   		{
								   		fieldLabel : avmon.config.host,
								        width:40,
								        editable : false,
								        text:avmon.config.hosts,
								        xtype:'label',
								        margins: '0 0 0 10'
							   		},
							   		{
				                        xtype: 'textfield',
				                        width: 140,
				                        id:'kpiHistoryHostIp',
				                        readOnly:true,
				                        fieldStyle:'padding-top:3px;background:none; border: 0px solid;'
				                    }
							   		,{
								   		fieldLabel : avmon.config.time,
								   		labelWidth: 40,
								        id: 'start_date',
								        format:'Y-m-d',
								        width:140,
								        editable : false,
								        value:Ext.Date.format(Ext.Date.add(new Date(), Ext.Date.HOUR, -6), "Y-m-d"),
								        xtype:'datefield'
							   		},{
								   		fieldLabel : '',
								        id: 'start_time',
								        format: "H:i",// 24小时制
								        increment: 60,
								        editable : false,
								        value:Ext.Date.format(Ext.Date.add(new Date(), Ext.Date.HOUR, -6), "H"),
								        width:58,
								        xtype:'timefield'
							   		},{
								   		fieldLabel : avmon.config.to,
								   		labelWidth: 20,
								   		labelSeparator :'',
								        id: 'end_date',
								        format:'Y-m-d',
								        width:120,
								        editable : false,
								        value:Ext.Date.format(new Date(), "Y-m-d"),
								        xtype:'datefield'
							   		},{
								   		fieldLabel : '',
								        id: 'end_time',
								        format: "H:i",// 24小时制
								        increment: 60,
								        editable : false,
								        value:Ext.Date.format(new Date(), "H"),
								        width:58,
								        xtype:'timefield'
							   		},'-'
							   		,{
				                    text: avmon.config.search,
				                    iconCls: 'icon-add',
				                    handler: function() {
				                    	var chart = Ext.getCmp("KpiHistoryChart_chart");
									    var kpiGrid = Ext.getCmp("KpiGrid_grid");
								    	var selection = kpiGrid.getSelectionModel().getSelection();
								    	if(selection.length==0){
								    		Ext.Msg.alert(avmon.common.reminder,avmon.config.selectKPI);
								    		return;
								    	}
								    	if(selection.length>5){
								    		Ext.Msg.alert(avmon.common.reminder,avmon.config.kpiNumberNotMoreThanFive);
								    		return;
								    	}
								    	var kpis = []; 
									    Ext.each(selection,function(item){ 
									       kpis.push(item.data.kpi); 
									    });
									    var kpiDisplays = []; 
									    Ext.each(selection,function(item){ 
									       kpiDisplays.push(item.data.displayKpi); 
									    });
									    var storeFields = ["time"];
										var axesFields = [];
										var series = [];
										if(kpis.length==1){
											var displayKpi = kpiDisplays[0];
											var kpi = kpis[0];
											storeFields.push(kpi+"_avg");
											storeFields.push(kpi+"_max");
											storeFields.push(kpi+"_min");
											storeFields.push("threshold");
											axesFields.push(kpi+"_avg");
											axesFields.push(kpi+"_max");
											axesFields.push(kpi+"_min");
											axesFields.push("threshold");
											series.push({
							                    type: 'line',
							                    xField: 'time',
							                    yField: [
							                    	kpi+"_avg"
							                    ],
							                    title: [
							                        //displayKpi+
							                        avmon.config.averageValue
							                    ],
							                    markerConfig: {
								                    type: 'cross',
								                    size: 4,
								                    radius: 4,
								                    'stroke-width': 0
								                },
							                    fill: true,
							                    smooth: 3
							                    ,tips: {  
													trackMouse: true,  
													width: 250,  
													height: 50,  
													renderer: function(storeItem, item) {  
														this.setTitle(avmon.config.times+storeItem.get('time') + '<br>'+ avmon.config.kpi + displayKpi + '<br>' + 'avmon.config.averageValue + storeItem.get(kpi+"_avg"));  
													}  
												}
							                });
								            series.push({
								                type: 'line',
								                highlight: {
								                    size: 7,
								                    radius: 7
								                },
								                smooth: 3,
								                axis: 'left',
								                xField: 'time',
								                yField: kpi+"_max",
								                title: [
							                        //displayKpi+
							                        avmon.config.maxValue
							                    ],
								                markerConfig: {
								                    type: 'cross',
								                    size: 4,
								                    radius: 4,
								                    'stroke-width': 0
								                }
								                ,tips: {  
													trackMouse: true,  
													width: 250,  
													height: 50,  
													renderer: function(storeItem, item) {  
														this.setTitle(avmon.config.times+storeItem.get('time') + '<br>' + avmon.config.kpi + displayKpi + '<br>' + avmon.config.maxValue + storeItem.get(kpi+"_max"));  
													}  
												}
								            });
								            series.push({
								                type: 'line',
								                highlight: {
								                    size: 7,
								                    radius: 7
								                },
								                smooth: 3,
								                axis: 'left',
								                xField: 'time',
								                yField: kpi+"_min",
								                title: [
							                        avmon.config.minValue
							                    ],
								                markerConfig: {
								                    type: 'cross',
								                    size: 4,
								                    radius: 4,
								                    'stroke-width': 0
								                }
								                ,tips: {  
													trackMouse: true,  
													width: 250,  
													height: 50,  
													renderer: function(storeItem, item) {  
														this.setTitle(avmon.config.times+storeItem.get('time') + '<br>' + avmon.config.kpi + displayKpi + '<br>' + avmon.config.minValue + storeItem.get(kpi+"_min"));  
													}  
												}
								            });
								            series.push({
								                type: 'line',
								                highlight: {
								                    size: 7,
								                    radius: 7
								                },
								                smooth: 3,
								                axis: 'left',
								                xField: 'time',
								                yField: 'threshold',
								                title: [
							                        avmon.config.seriousAlarmThresholds
							                    ],
								                markerConfig: {
								                    type: 'circle',
								                    size: 4,
								                    radius: 4,
								                    'stroke-width': 0
								                }
								                ,tips: {  
													trackMouse: true,  
													width: 250,  
													height: 50,  
													renderer: function(storeItem, item) {  
														this.setTitle(avmon.config.kpi + displayKpi + '<br>' + avmon.config.seriousAlarmThresholds + storeItem.get('threshold'));  
													}  
												}
								            });
										}else{
											for (var i = 0; i < kpis.length; i++) {
												var displayKpi = kpiDisplays[i];
												var kpi = kpis[i];
					
												storeFields.push(kpi);
												axesFields.push(kpi);
											
												series.push({
									                type: 'line',
									                highlight: {
									                    size: 7,
									                    radius: 7
									                },
									                smooth: 3,
									                axis: 'left',
									                xField: 'time',
									                yField: kpi,
									                title: displayKpi,
									                markerConfig: {
									                    type: 'cross',
									                    size: 4,
									                    radius: 4,
									                    'stroke-width': 0
									                }
									                ,tips: {  
														trackMouse: true,  
														width: 250,  
														height: 50,
														id:kpi,
														name:displayKpi,
														renderer: function(storeItem, item) {
															this.setTitle(avmon.config.times+storeItem.get('time') + '<br>' + avmon.config.kpi + this.name + '<br>avmon.config.averageValue + storeItem.get(this.id));  
														}  
													}
									            });
											}
										}
									    										
										var startTime = Ext.getCmp("start_date").rawValue + " " + Ext.getCmp("start_time").rawValue;
										var endTime = Ext.getCmp("end_date").rawValue + " " + Ext.getCmp("end_time").rawValue;
																				
									    Ext.Ajax.request({
											url : "../config/kpiChartData?kpis=" + kpis.join(',') + 
												"&startTime=" + startTime + "&endTime=" + endTime + "&moId=" + Ext.getDom("moId").value,
											success : function(response, opts) {
												var chartData = Ext.JSON.decode(response.responseText);
												if(chartData.length==0){
													Ext.Msg.alert(avmon.common.reminder,avmon.config.noHistoricalInformation);
												}else{
													Ext.getCmp("kpiHistoryHostIp").setValue(Ext.getDom("moId").value);
													var chartStorex = Ext.create('Ext.data.JsonStore', {
												        fields: storeFields,
												        data: chartData
												    }); 
											    	chart.store = chartStorex;
													chart.axes.items[0].fields = axesFields;
													try{
														var lines = chart.series.items;
														Ext.Array.each(lines, function(line) { line.hideAll(); })
													}catch(error){}
													chart.series = new Ext.util.MixedCollection(false, function(a) { return a.seriesId || (a.seriesId = Ext.id(null, "ext-chart-series-")); });
													if (series) {
													    chart.series.addAll(series);
													}
													chart.redraw();
												}
											},
											failure : function(response, opts) {
												Ext.MessageBox.alert(avmon.common.reminder, avmon.config.loadFail);
											}
										});
				                    }
				                }]}
							   ,Ext.create('CFG.view.KpiHistoryChart')
					        ]
				        }
	                ] 
                }
            ]
        });
    }
});
