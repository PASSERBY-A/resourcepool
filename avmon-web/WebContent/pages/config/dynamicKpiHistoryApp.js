Ext.Loader.setConfig({
    enabled: true
});
Ext.Loader.setPath('Ext.ux', '../pages/commons/ux');
Ext.require(['Ext.chart.*','Ext.ux.DynamicChart']);
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
            autoScroll: true,
            items: [
                {
              id:'kpiHistoryDesk', 
              itmeId:'kpiHistoryDesk', 
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
							itemId:'kpiGridPanel',
							items:[Ext.create('CFG.view.KpiGrid')]
				        },{ 
				            border:false,
				            region: 'center',
				            layout: {
		                        align: 'stretch',
		                        type: 'vbox'
		                    },
							autoScroll:true,
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
										width: 190,
								        fieldLabel: avmon.kpiCompare.sizeSelect,
								        labelStyle : 'text-align:right;width:80;',
								        name: 'grainsize',
								        allowBlank: false,
								        
								        xtype : 'combobox',
										store : Ext.create('Ext.data.Store', {
													fields : ['value', 'display'],
													autoLoad: true,
													data:[
													      {'value':'1',
													    	  'display':avmon.kpiCompare.originalData
													    	  }
													      ,
													      {'value':'2',
													    		  'display':avmon.kpiCompare.byHour}
													      ,
													      {'value':'3',
												    		  'display':avmon.kpiCompare.byDay}
													      ]
												}),
										value:'1',		
										editable : false,
										valueField : 'value',
										displayField : 'display',
										id:'grainsizeid',
										itemId:'grainsizeid'
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
								        itemId: 'start_date',
								        format:'Y-m-d',
								        width:140,
								        editable : false,
								        value:Ext.Date.format(Ext.Date.add(new Date(), Ext.Date.HOUR, -6), "Y-m-d"),
								        xtype:'datefield'
							   		},{
								   		fieldLabel : '',
								        id: 'start_time',
								        itemId: 'start_time',
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
								        itemId: 'end_date',
								        format:'Y-m-d',
								        width:120,
								        editable : false,
								        value:Ext.Date.format(new Date(), "Y-m-d"),
								        xtype:'datefield'
							   		},{
								   		fieldLabel : '',
								        id: 'end_time',
								        itemId: 'end_time',
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
  	                      
	                      var processBar = Ext.MessageBox.show({
                          msg: avmon.modules.logining,
                          progressText: avmon.modules.starting,
                          width:300,
                          wait:true
                        });
	                    	var mainPanel = this.up("#kpiHistoryDesk");
	                    	var kpiGrid = mainPanel.down("#kpiGridPanel").down("panel");
	                    	var selection = kpiGrid.getSelectionModel().getSelection();
								    	
  								    	if(selection.length==0){
  								    		Ext.Msg.alert(avmon.common.reminder,avmon.config.selectKPI);
  								    		return;
  								    	}else if(selection.length>5){
  								    		Ext.Msg.alert(avmon.common.reminder,avmon.config.kpiNumberNotMoreThanFive);
  								    		return;
  								    	}
  								    	mainPanel.down("#chartPanel").removeAll();
  
  								    	var chartPanel=this.up('panel');
  								    	var startTime = chartPanel.down("#start_date").rawValue + " " + chartPanel.down("#start_time").rawValue;
    										var endTime = chartPanel.down("#end_date").rawValue + " " + chartPanel.down("#end_time").rawValue;
    										var grainTransValue =  chartPanel.down("#grainsizeid").value;
								    	
    										//动态添加kpi曲线图
	                    	Ext.suspendLayouts();
	                    	Ext.each(selection,function(item){ 
							    	    var ampKpi = item.data.kpi.split('/');
										    
							    	    //后台取对应的KPI有多少个instance，每一个instance应该对一条曲线
  											Ext.Ajax.request({
  												url : "../config/getKpiInstance?ampInstId=" + ampKpi[0] + "&kpiCode=" + ampKpi[1] + 
  													"&startTime=" + startTime + "&endTime=" + endTime + "&moId=" + Ext.getDom("moId").value + "&grainsize=" + grainTransValue,
  												success : function(response, opts) {
  													var instances = Ext.JSON.decode(response.responseText).root;
  													//遍历instance构建chart所需属性
  													var storeFields = ["time"];
  													var axesFields = [];
  													var series = [];
  													for(i=0;i<instances.length;i++){
  														
  														storeFields.push("instance"+i);
  														axesFields.push("instance"+i);
  														series.push(
  															{
										                type: 'line',
										                highlight: {
										                    size: 7,
										                    radius: 7
										                },
										                smooth: 3,
										                axis: 'left',
										                xField: 'time',
										                yField: "instance"+i,//instances[i].instance,
										                title: instances[i].instance,
										                markerConfig: {
										                    type: 'cross',
										                    size: 4,
										                    radius: 4,
										                    'stroke-width': 0
										                },
										                tips: {  
															trackMouse: true,  
															width: 200,  
															height: 55,
															id:"instance"+i,
															name:instances[i].instance,
															renderer: function(storeItem, item) {
																this.setTitle(avmon.config.times+storeItem.get('time') + '<br>'+avmon.config.kpiInstance + this.name + '<br>'+avmon.config.value + storeItem.get(this.id));  
															}  
										                }
											            }
										            );
  													}
  													
  													//定义数据源
  													var chartStore = Ext.create('Ext.data.JsonStore', {
  												        fields: storeFields,
  												        autoLoad:true,
  												        proxy: {
												          timeout:60000,
  														    type: 'ajax',
  														    async:false,
  														    url : "../config/dynamicKpiChartData?ampInstId=" + ampKpi[0] + "&kpiCode=" + ampKpi[1] + 
  																"&startTime=" + startTime + "&endTime=" + endTime + "&moId=" + Ext.getDom("moId").value + "&grainsize=" + grainTransValue,
  														    reader: {
  														        type: 'json',
  														        root:'root'
  														    }
  														}
  												    });
  												    //定义chart
  												    var kpiChart = Ext.create('Ext.ux.DynamicChart',
  												    	{
  													    	axesTitle:item.data.displayKpi,
  													    	axesFields:axesFields,
  														    seriesFields:series,
  														    chartData:chartStore,
  														    degress:315
  													    }
  												    );
									            mainPanel.down("#chartPanel").add(kpiChart);
									            processBar.close();
  												},
  												failure : function(response, opts) {
  													Ext.MessageBox.alert(avmon.common.reminder, avmon.config.loadFail);
  												}
  											});
									    });
									    Ext.resumeLayouts(true);
	                    }
				                }]}
				                ,{
				                    xtype: 'container',
				                    autoScroll: true,
				                    itemId:'chartPanel',
				                    layout: {
				                        align: 'stretch',
				                        type: 'vbox'
				                    },
				                    items: []
			                    }
					        ]
				        }
	                ] 
                }
            ]
        });
    }
});
