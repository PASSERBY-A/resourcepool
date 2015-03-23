Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',

    requires: [
        'MyApp.view.MyTreePanel',
        'MyApp.view.LineChart'
    ],

    layout: {
        type: 'border'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'mytreepanel',
                    minWidth: 200,
                    width: 181,
                    region: 'west',
                    split: true
                },
                {
                    xtype: 'panel',
                    region: 'center',
                    itemId:'chartPanel',
                    autoScroll: true,
                    layout: {
                        align: 'stretch',
                        type: 'vbox'
                    },
                    title: avmon.kpiContrast,
                    items: [{
                      xtype: 'container',
                      autoScroll: true,
                      id:'chartPanelContainer',
                      itemId:'chartPanelContainer',
                      layout: {
                          align: 'stretch',
                          type: 'vbox'
                      },
                      items: []
                    }],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'combobox',
                                    id: 'compare_kpi_id',
                                    itemId: 'compare_kpi_id',
                                    fieldLabel: 'KPI',
                                    labelWidth: 30,
                                    displayField: 'displayKpi',
                                    hiddenName: 'kpi',
                                    store: 'KpiStore',
                                    valueField: 'kpi',
                                    width:280
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
                                              'display':avmon.kpiCompare.originalData}
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
                                    xtype: 'datefield',
                                    id: 'start_date',
                                    itemId: 'start_date',
                                    width: 140,
                                    fieldLabel: avmon.kpiCompare.time,
                                    labelWidth: 40,
                                    editable: false,
                                    value:Ext.Date.format(Ext.Date.add(new Date(), Ext.Date.HOUR, -12), "Y-m-d"),
                                    format: 'Y-m-d'
                                },
                                {
                                    xtype: 'timefield',
                                    id: 'start_time',
                                    itemId: 'start_time',
                                    width: 58,
                                    fieldLabel: '',
                                    editable: false,
                                    format: 'H:i',
                                    value:Ext.Date.format(Ext.Date.add(new Date(), Ext.Date.HOUR, -12), "H"),
                                    increment: 60
                                },
                                {
                                    xtype: 'datefield',
                                    id: 'end_date',
                                    itemId: 'end_date',
                                    width: 120,
                                    fieldLabel: avmon.kpiCompare.to,
                                    labelSeparator :'',
                                    labelWidth: 20,
                                    editable: false,
                                    value:Ext.Date.format(new Date(), "Y-m-d"),
                                    format: 'Y-m-d'
                                },
                                {
                                    xtype: 'timefield',
                                    id: 'end_time',
                                    itemId: 'end_time',
                                    width: 58,
                                    fieldLabel: '',
                                    editable: false,
                                    format: 'H:i',
                                    value:Ext.Date.format(new Date(), "H"),
                                    increment: 60
                                },'-',
                                {
                                    xtype: 'button',
                                    icon: '../resources/images/button/search2.gif',
                                    handler: function(button, event) {
                                      if(!button.isDisabled()){
                                        button.setDisabled(true);
                                      }
                                      	var chartPanel = this.ownerCt.ownerCt.down("#chartPanelContainer");
                                      	chartPanel.removeAll();
                                        var startTime = button.prev("#start_date").rawValue + " " + button.prev("#start_time").rawValue;
                                        var endTime = button.prev("#end_date").rawValue + " " + button.prev("#end_time").rawValue;
                                        var grainTransValue =  button.prev("#grainsizeid").value;
                                        if(nodeIds.length==0){
                                        	Ext.Msg.alert(avmon.common.reminder,avmon.kpiCompare.selectContrastHost);
                                        	return;
                                        }else if(nodeIds.length>5){
                                        	Ext.Msg.alert(avmon.common.reminder,avmon.kpiCompare.contrastHostCannotMoreThanFive);
                                        	return;
                                        }
                                        var compareKpiId = button.prev("#compare_kpi_id").getValue();
                                        if(compareKpiId==null||compareKpiId==0){
                                        	Ext.Msg.alert(avmon.common.reminder,avmon.kpicompare.selectContrastKPI);
                                        	return;
                                        }
                                        
                                        var progressBar = Ext.MessageBox.show({
                                          msg: avmon.modules.logining,
                                          progressText: avmon.modules.starting,
                                          width:300,
                                          wait:true
                                        });
                                        
                                        Ext.suspendLayouts();
                                      	var ampKpi = compareKpiId.split('/');
										    
                                      	  //后台取对应的KPI有多少个instance，每一个instance应该对一条曲线
                    											Ext.Ajax.request({
                    												url : "../config/getKpiInstanceCompare?ampInstId=" + ampKpi[0] + "&kpiCode=" + ampKpi[1] + 
                    													"&startTime=" + startTime + "&endTime=" + endTime + "&moId=" + nodeIds+ "&grainsize=" + grainTransValue,
                    												success : function(response, opts) {
                    													var returnValue = Ext.JSON.decode(response.responseText)
                    													var instances = returnValue.root;
                    													//遍历instance构建chart所需属性
                    													var storeFields = ["time"];
                    													var axesFields = [];
                    													var series = [];
                    													for(i=0;i<instances.length;i++){
                    														storeFields.push("instance"+i);
                    														axesFields.push("instance"+i);
                    														
                    														series.push({
            												                type: 'line',
            												                highlight: {
            												                    size: 7,
            												                    radius: 7
            												                },
            												                smooth: 3,
            												                axis: 'left',
            												                xField: 'time',
            												                yField: "instance"+i,
            												                title: instances[i].instance,
            												                markerConfig: {
            												                    type: 'cross',
            												                    size: 4,
            												                    radius: 4,
            												                    'stroke-width': 0
            												                }
            												                ,tips: {  
                    																	trackMouse: true,  
                    																	width: 200,  
                    																	height: 55,
                    																	id:"instance"+i,
                    																	name:instances[i].instance,
                    																	renderer: function(storeItem, item) {
                    																		this.setTitle(avmon.kpiCompare.timeTo+storeItem.get('time') + '<br>'+avmon.kpiCompare.kpiInstance + this.name + 
                    																		'<br>'+avmon.kpiCompare.value + storeItem.get(this.id));  
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
                    														    type: 'ajax',
                    														    url : "../config/dynamicKpiChartDataCompare?ampInstId=" + ampKpi[0] + "&kpiCode=" + ampKpi[1] + 
                    																"&startTime=" + startTime + "&endTime=" + endTime + "&moId=" + returnValue.moId+"&grainsize=" + grainTransValue,
                    														    reader: {
                    														        type: 'json',
                    														        root:'root'
                    														    }
                    														}
                    												    });
                    													
                    												    //定义chart
                    												    var kpiChart = Ext.create('Ext.ux.DynamicChart',
                    												    	{
                    													    	axesTitle:"KPI",
                    													    	axesFields:axesFields,
                    														    seriesFields:series,
                    														    chartData:chartStore,
                    														    degress:315,
                    														    categoryTitle:returnValue.caption
                    													    }
                    													);
                    										      chartPanel.add(kpiChart);
                    										      progressBar.hide();
                    												},
                    												failure : function(response, opts) {
                    													Ext.MessageBox.alert(avmon.common.reminder, avmon.kpiCompare.loadFail);
                    												}
                    											});
                                        	
                    										button.setDisabled(false);
                                        Ext.resumeLayouts(true);
                                    },
                                    text: avmon.kpiCompare.query
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});