Ext.define('MyApp.view.VMAmpSetWindow', {
    extend: 'Ext.window.Window',
    requires: [
        'MyApp.view.MyPanel8'
    ],
    height: 450,
    width: 750,
    id: 'vmAmpSetWindow',
    title: avmon.config.ampConfiguration,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    height: 120,
                    layout: {
                        type: 'absolute'
                    },
                    frame:true,
                    items: [
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 20,
                            id:'normalAmpInstId',
                            itemId: 'ampInstId',
                            width: 250,
                            fieldLabel: avmon.config.ampInstance,
                            labelWidth: 80
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 55,
                            itemId: 'ampInstName',
                            width: 250,
                            fieldLabel: avmon.config.ampName,
                            labelWidth: 80
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 70,
                            hidden:true,
                            itemId: 'ampInstStatus',
                            width: 250,
                            fieldLabel: avmon.config.status,
                            labelWidth: 80
                        },
                        {
                            xtype: 'displayfield',
                            x: 70,
                            y: 70,
                            itemId: 'ampId',
                            width: 250,
                            hidden:true,
                            fieldLabel: 'ampId',
                            labelWidth: 80
                        }
//                        ,
//                        {
//                            xtype: 'button',
//                            x: 220,
//                            y: 70,
//                            icon: 'image/start.gif',
//                            itemId:'ampStart',
//                            text: avmon.config.start,
//                            listeners: {
//                                click: {
//                                    fn: me.onButtonClick00,
//                                    scope: me
//                                }
//                            }
//                        },
//                        {
//                            xtype: 'button',
//                            x: 280,
//                            y: 70,
//                            icon: 'image/pause.gif',
//                            itemId:'ampStop',
//                            text: avmon.config.stop,
//                            listeners: {
//                                click: {
//                                    fn: me.onButtonClick01,
//                                    scope: me
//                                }
//                            }
//                        },
//                        {
//                            xtype: 'button',
//                            x: 340,
//                            y: 70,
//                            text: avmon.config.issuedByScript,
//                            listeners: {
//                                click: {
//                                    fn: me.onButtonClick02,
//                                    scope: me
//                                }
//                            }
//                        },
//                        {
//                            xtype: 'button',
//                            x: 410,
//                            y: 70,
//                            text: avmon.config.issueByConfiguration,
//                            listeners: {
//                                click: {
//                                    fn: me.onButtonClick03,
//                                    scope: me
//                                }
//                            }
//                        }
                    ]
                },
                {
                    xtype: 'tabpanel',
                    height: 300,
                    activeTab: 0,
                    items: [
                        {
                            xtype: 'mypanel8'
                        },
                        {
                            xtype: 'panel',
                            itemId: 'vmTab',
                            layout: {
                                type: 'fit'
                            },
                            title: avmon.config.virtualHost,
                            items: [
                                {
                                    xtype: 'treepanel',
                                    height: 250,
                                    width: 400,
                                    store: 'VmTreeStore',
                                    rootVisible: false,
                                    viewConfig: {
                                        multiSelect: true,
                                        simpleSelect: false
                                    },
                                    columns: [
                                        {
                                            xtype: 'treecolumn',
                                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                                if(record.data.objStatus==1){
                                                    return "<font color='blue' style='font-weight:bold;font-style:italic;'>" + value + "</font>";
                                                }else if(record.data.objStatus==0){
                                                    return "<font color='red' style='font-weight:bold;font-style:italic;'>" + value + "</font>";
                                                }else{
                                                    return value;
                                                }
                                            },
                                            dataIndex: 'text',
                                            flex: 1,
                                            text: 'Nodes'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'hostName',
                                            text: avmon.config.hostname
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'hostStatus',
                                            text: avmon.config.hostStatus
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                                if(value=="1"){
                                                    return avmon.config.monitoring;
                                                }else if(value=="0"){
                                                    return avmon.config.noMonitoring;
                                                }
                                            },
                                            dataIndex: 'enableFlag',
                                            text: avmon.config.monitoringStatus
                                        }
                                    ],
                                    dockedItems: [
                                        {
                                            xtype: 'toolbar',
                                            dock: 'top',
                                            items: [
                                                {
                                                    xtype: 'button',
                                                    iconCls: 'icon-refresh',
                                                    text: avmon.config.refreshVirtualHostList,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick1,
                                                            scope: me
                                                        }
                                                    }
                                                },
                                                {
                                                    xtype: 'tbseparator'
                                                },
                                                {
                                                    xtype: 'button',
                                                    iconCls: 'icon-start',
                                                    text: avmon.config.startMonitor,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick3,
                                                            scope: me
                                                        }
                                                    }
                                                },
                                                {
                                                    xtype: 'tbseparator'
                                                },
                                                {
                                                    xtype: 'button',
                                                    iconCls: 'icon-pause',
                                                    text: avmon.config.stopMonitor,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick4,
                                                            scope: me
                                                        }
                                                    }
                                                },
                                                {
                                                    xtype: 'tbseparator'
                                                },
                                                {
                                                    xtype: 'button',
                                                    iconCls: 'icon-delete',
                                                    text: avmon.config.remove,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick5,
                                                            scope: me
                                                        }
                                                    }
                                                },
                                                {
                                                    xtype: 'tbseparator'
                                                },
                                                {
                                                    xtype: 'textfield',
                                                    itemId: 'schedule',
                                                    hidden:true,
                                                    width: 200,
                                                    fieldLabel: avmon.config.collectionSchedule,
                                                    labelWidth: 60
                                                },
                                                {
                                                    xtype: 'button',
                                                    iconCls: 'icon-ok',
                                                    hidden:true,
                                                    text: avmon.config.saveIssuedCollectionSchedule,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick2,
                                                            scope: me
                                                        }
                                                    }
                                                }
                                            ]
                                        }
                                    ],
                                    listeners: {
                                        checkchange: {
                                            fn: me.onTreepanelCheckChange,
                                            scope: me
                                        }
                                    }
                                }
                            ],
                            listeners: {
                                activate: {
                                    fn: me.onPanelActivate,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'panel',
                            layout: {
                                type: 'fit'
                            },
                            manageHeight: false,
                            title: avmon.config.schedulingPolicy,
                            tabConfig: {
                                xtype: 'tab',
                                id: 'scheduleTabConfig'
                            },
                            items: [
                                {
                                    xtype: 'panel',
                                    layout: {
                                        type: 'fit'
                                    },
                                    bodyBorder: false,
                                    items: [
                                        {
                                            xtype: 'gridpanel',
                                            id: 'normalScheduleGrid',
                                            forceFit: false,
                                            store: 'AmpNormalScheduleStore',
                                            columns: [
                                                {
                                                    xtype: 'gridcolumn',
                                                    dataIndex: 'kpiCode',
                                                    text: 'KPI Code'
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    dataIndex: 'caption',
                                                    text: avmon.config.kpiName
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    id: 'scheduleEditorColum',
                                                    dataIndex: 'schedule',
                                                    text: avmon.config.schedulingConfigString,
                                                    editor: {
                                                        xtype: 'textfield',
                                                        id: 'scheduleEditor',
                                                        allowBlank: false
                                                    }
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    dataIndex: 'kpiGroup',
                                                    text: avmon.config.group
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    renderer: function(value){
                                                      if (value == '1') {
                                                          return avmon.config.yes;
                                                      }
                                                          return avmon.config.no;
                                                    },
                                                    dataIndex: 'status',
                                                    text: avmon.config.enableFlag//是否启用
                                                }
                                            ],
                                            listeners: {
                                              viewready: {
                                                  fn: me.initCheckBox,
                                                  scope: me
                                              }
                                            },
                                            viewConfig: {
                                                id: '',
                                                focusOnToFront: false,
                                                autoScroll: true
                                            },
                                            dockedItems: [
                                                {
                                                    xtype: 'toolbar',
                                                    dock: 'top',
                                                    items: [
                                                        {
                                                            xtype: 'button',
                                                            id: 'editScheduleButId',
                                                            text: avmon.config.editSchedulingPolicy,
                                                            iconCls:'icon-edit',
                                                            listeners: {
                                                                click: {
                                                                    fn: me.editScheduleClick,
                                                                    scope: me
                                                                }
                                                            }
                                                        },
                                                        {
                                                            xtype: 'button',
                                                            id: 'pushSelectedScheduleButId',
                                                            text: avmon.config.issueByChosenPolicy,
                                                            iconCls:'icon-ok',
                                                            listeners: {
                                                                click: {
                                                                    fn: me.onPushSelectedScheduleButIdClick,
                                                                    scope: me
                                                                }
                                                            }
                                                        },'-',
                                                        {
                                                            xtype: 'button',
                                                            id: 'pushALlScheduleButId',
                                                            text: avmon.config.issueByAllPolicy,
                                                            iconCls:'icon-ok',
                                                            listeners: {
                                                                click: {
                                                                    fn: me.onPushALlScheduleButIdClick,
                                                                    scope: me
                                                                }
                                                            }
                                                        }
                                                    ]
                                                }
                                            ],
                                            selModel: Ext.create('Ext.selection.CheckboxModel', {
                                                checkOnly: false
                                            }),
                                            plugins: [
                                                Ext.create('Ext.grid.plugin.CellEditing', {
                                                    ptype: 'cellediting',
                                                    pluginId: 'scheduleEditorPluginId',
                                                    clicksToEdit: 1,
                                                    triggerEvent: 'celldblclick',
                                                    listeners: {
                                                        edit: {
                                                            fn: me.onGridcelleditingpluginEdit,
                                                            scope: me
                                                        }
                                                    }
                                                })
                                            ]
                                        }
                                    ]
                                }
                            ],
                            listeners: {
                              activate: {
                                  fn: me.onSchedulePanelActivate,
                                  scope: me
                              }
                          }
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    initCheckBox:function(me, eOpts){
      var grid = Ext.getCmp("normalScheduleGrid");
      var items = grid.store.data.items;
      var selModes = grid.selModel;
      Ext.each(items,function(item,index){
        if(item.data.status=='1'){
          selModes.doSelect(items[index],true);
        }
      });
    },
    onSchedulePanelActivate:function(abstractcomponent,options){
      var p = abstractcomponent.up("tabpanel").down("#attrPanel").down("grid");
      var agentId = p.store.proxy.extraParams.agentId;
      var ampInstId = p.store.proxy.extraParams.ampInstId;
      var grid = Ext.getCmp('normalScheduleGrid');
      var gridStore =  grid.store;
      var params = gridStore.proxy.extraParams;
      params.agentId = agentId;
      params.ampInstId = ampInstId;
      gridStore.load();
    },
    onGridcelleditingpluginEdit: function(editor, e, options) {
      var recordSource = e.record.data;
      var agentId = Ext.getCmp("agentIdLable1").getValue();
      if(recordSource.kpiGroup==null || recordSource.kpiGroup.length==0){
        var processBar = Ext.MessageBox.show({
                  msg: avmon.config.savingDataPleaseWait,
                  progressText: avmon.config.saving,
                  width:300,
                  wait:true
              });
          Ext.Ajax.request({
              url: 'saveAgentSchedue',
              method :"POST",
              params: {
                  scheduleAttr:Ext.JSON.encode(recordSource),
                  agentId:agentId
              },
              success: function(response){
                  processBar.close();
                  var jsonSource = Ext.JSON.decode(response.responseText);
                  if(jsonSource.success){

                      Ext.MessageBox.show({
                          title: avmon.common.message,
                          msg: avmon.config.schedulingPolicyChangeSuccess,
                          buttons: Ext.MessageBox.OK,
                          icon: Ext.MessageBox.INFO,
                          toFrontOnShow:true,
                          animateTarget:"editScheduleButId"
                      });

                      //加载策略数据
                      var schduleStore = Ext.getStore('AmpNormalScheduleStore');
                      var schduleStoreProxy = schduleStore.getProxy();
                      schduleStoreProxy.extraParams.ampInstId = Ext.getCmp('normalAmpInstId').getValue();
                      schduleStoreProxy.extraParams.agentId = Ext.getCmp("agentIdLable1").getValue();
                      schduleStore.load();
                  }else{
                      Ext.MessageBox.show({
                          title: avmon.config.schedulingPolicyChangeFailure,
                          msg: jsonSource.errorMsg,
                          buttons: Ext.MessageBox.OK,
                          icon: Ext.MessageBox.ERROR,
                          toFrontOnShow:true,
                          animateTarget:"editScheduleButId"
                      }); 
                  }
              },
              failure: function(response, opts) {
                  Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
              }
          }); 
        }else{
          Ext.MessageBox.confirm(avmon.config.confirmChanges, avmon.config.confirmTheOperation, function(but){
            if(but == "yes"){
                var processBar = Ext.MessageBox.show({
                    msg: avmon.config.savingDataPleaseWait,
                    progressText: avmon.config.saving,
                    width:300,
                    wait:true
                });
                Ext.Ajax.request({
                    url: 'saveAgentSchedue',
                    method :"POST",
                    params: {
                        scheduleAttr:Ext.JSON.encode(recordSource),
                        agentId:agentId
                    },
                    success: function(response){
                        processBar.close();
                        var jsonSource = Ext.JSON.decode(response.responseText);
                        if(jsonSource.success){
                            Ext.MessageBox.show({
                                title: avmon.common.message,
                                msg: avmon.config.schedulingPolicyChangeSuccess,
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.INFO,
                                toFrontOnShow:true,
                                animateTarget:"editScheduleButId"
                            });
                            //加载策略数据
                            var schduleStore = Ext.getStore('AmpNormalScheduleStore');
                            var schduleStoreProxy = schduleStore.getProxy();
                            schduleStoreProxy.extraParams.ampInstId = Ext.getCmp('normalAmpInstId').getValue();
                            schduleStoreProxy.extraParams.agentId = Ext.getCmp("agentIdLable1").getValue();
                            schduleStore.load();
                        }else{
                            Ext.MessageBox.show({
                                title: avmon.config.schedulingPolicyChangeFailure,
                                msg: jsonSource.errorMsg,
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.ERROR,
                                toFrontOnShow:true,
                                animateTarget:"editScheduleButId"
                            }); 
                        }
                    },
                    failure: function(response, opts) {
                        Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
                    }
                }); 

            }else{
              //加载策略数据
                var schduleStore = Ext.getStore('AmpNormalScheduleStore');
                var schduleStoreProxy = schduleStore.getProxy();
                schduleStoreProxy.extraParams.ampInstId = Ext.getCmp('normalAmpInstId').getValue();
                schduleStoreProxy.extraParams.agentId = Ext.getCmp("agentIdLable1").getValue();
                schduleStore.load();
            }
        });
      }
  },
  onPushSelectedScheduleButIdClick: function(button, e, options) {
      var grid = Ext.getCmp("normalScheduleGrid");
      var selectCount = grid.getSelectionModel().getCount();
      var agentId = Ext.getCmp("agentIdLable1").getValue();
      if(selectCount == 0){
          Ext.MessageBox.show({
              title:avmon.config.wrong,
              msg:avmon.config.pleaseSelectNeedIssueStrategy,
              buttons: Ext.MessageBox.OK,
              icon: Ext.MessageBox.ERROR,
              animateTarget:'pushSelectedScheduleButId'
          });
      }else{
          var dataSelection = grid.getSelectionModel().getSelection();
          var jsonSource = "[";    
          for(var i=0;i<dataSelection.length;i++){
              if(i == dataSelection.length-1){
                  jsonSource += Ext.JSON.encode(dataSelection[i].data);  
              }else{
                  jsonSource += Ext.JSON.encode(dataSelection[i].data)+","; 
              }
          }
          jsonSource+= "]";
          //下发策略
          Ext.getCmp('vmAmpSetWindow').pushSelectedSchedule(jsonSource,'pushSelectedScheduleButId',agentId,"part");
      }
  },
  onPushALlScheduleButIdClick: function(button, e, options) {
      var grid = Ext.getCmp("normalScheduleGrid");
      var agentId = Ext.getCmp("agentIdLable1").getValue();
      var allData = grid.getStore().getRange();
      var jsonSource ="[{'agentId':'" + agentId + "','ampInstId':'" + Ext.getCmp("normalAmpInstId").getValue() + "'}]"
      //下发策略
      Ext.getCmp('vmAmpSetWindow').pushSelectedSchedule(jsonSource,'pushALlScheduleButId',agentId,"all");
    },
    editScheduleClick: function(button, e, options) {
      var grid = Ext.getCmp("normalScheduleGrid");
      var selectCount = grid.getSelectionModel().getCount();
      if(selectCount == 0 || selectCount >1){
          Ext.MessageBox.show({
              title:avmon.config.wrong,
              msg:avmon.config.pleaseSelectASetOfStrategiesForEdit,
              buttons: Ext.MessageBox.OK,
              icon: Ext.MessageBox.ERROR,
              animateTarget:'editScheduleButId'
          });
      }else{
          //开始编辑选中行
          var selectionModule = grid.getSelectionModel().getSelection()[0];
          var editorPlugin = grid.getPlugin("scheduleEditorPluginId");
          var editorColum = Ext.getCmp("scheduleEditorColum");
          editorPlugin.startEdit(selectionModule,editorColum ); 
      }
  },
    onButtonClick1: function(button, e, options) {
    	var vmTreeStore = button.ownerCt.ownerCt.store;
        vmTreeStore.proxy.url="refreshVmTree";
        vmTreeStore.load({
            scope: this,
            callback: function(records, operation, success) {
            	if(success==false){
            		Ext.MessageBox.alert(avmon.common.reminder,avmon.config.invokeInterfaceFailure,function(){   
            		  vmTreeStore.proxy.url="findVmTreeStore";
            			vmTreeStore.load(); 
					});
            	}
            }
        });
    },
    onButtonClick3: function(button, e, options) {
        var treePanel = button.ownerCt.ownerCt;
        var records = treePanel.getChecked(), names = [], values = [];
        Ext.Array.each(records, function(rec) {
            var enableFlag = rec.get('enableFlag');
            if(enableFlag!=1){
                names.push(rec.get('text'));
                values.push(rec.get('id'));
            }
        });
        var p = button.ownerCt.ownerCt.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        if(values.length<1){
            Ext.MessageBox.alert(avmon.common.reminder, avmon.config.pleaseSelectNotMonitorObject);
        }else{
            var processBar = Ext.MessageBox.show({
                msg: avmon.config.startupMonitorning,
                progressText: avmon.config.startingMonitorning,
                width:300,
                wait:true
            });
            Ext.Ajax.request({
                url: 'updateVmMonitorStatus',
                timeout:240000,
                params:{flag:'start',objIds:values.join(','),agentId:agentId,ampInstId:ampInstId},
                success: function(response, opts) {
                	processBar.close();                	
                    var returnJson = Ext.JSON.decode(response.responseText);
                    Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
                    treePanel.store.proxy.url="findVmTreeStore";
                    treePanel.store.load(); 
                },
                failure: function(response, opts) {
                	processBar.close();
                	Ext.MessageBox.alert(avmon.common.reminder, avmon.config.startupMonitorningFailed);
                }
            });
        }
    },
    onButtonClick4: function(button, e, options) {
        var treePanel = button.ownerCt.ownerCt;
        var records = treePanel.getChecked(), names = [], values = [];
        Ext.Array.each(records, function(rec) {
            var enableFlag = rec.get('enableFlag');
            if(enableFlag==1){
                names.push(rec.get('text'));
                values.push(rec.get('id'));
            }
        });
        var p = button.ownerCt.ownerCt.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        if(values.length<1){
            Ext.MessageBox.alert(avmon.common.reminder, avmon.config.pleaseSelectMonitorObject);
        }else{
            Ext.Ajax.request({
                url: 'updateVmMonitorStatus',
                params:{flag:'stop',objIds:values.join(','),agentId:agentId,ampInstId:ampInstId},
                success: function(response, opts) {
                    var returnJson = Ext.JSON.decode(response.responseText);
                    Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
                    treePanel.store.load();
                },
                failure: function(response, opts) {}
            });
        }
    },
    onButtonClick5: function(button, e, options) {
        var treePanel = button.ownerCt.ownerCt;
        var records = treePanel.getChecked(), names = [], values = [];
        Ext.Array.each(records, function(rec) {
            names.push(rec.get('text'));
            values.push(rec.get('id'));
        });
        var p = button.ownerCt.ownerCt.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        if(values.length<1){
            Ext.MessageBox.alert(avmon.common.reminder, avmon.config.pleaseSelectRemoveObject);
        }else{
        	Ext.MessageBox.confirm(avmon.common.reminder, avmon.config.whetherRemove,
			function(btn) {
				if (btn == "yes") {
					Ext.Ajax.request({
		                url: 'deleteVmHost',
		                params:{objIds:values.join(','),agentId:agentId,ampInstId:ampInstId},
		                success: function(response, opts) {
		                    var returnJson = Ext.JSON.decode(response.responseText);
		                    Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
		                    treePanel.store.load();
		                },
		                failure: function(response, opts) {}
		            });
				}
			});	
        }
    },
    onButtonClick2: function(button, e, options) {
        var schedule = button.ownerCt.down("#schedule").getValue();
        var p = button.ownerCt.ownerCt.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        Ext.Ajax.request({
            url: 'saveVmSchedule',
            params:{agentId:agentId,ampInstId:ampInstId,schedule:schedule},
            success: function(response, opts) {
                var returnJson = Ext.JSON.decode(response.responseText);
                Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
            },
            failure: function(response, opts) {}
        });
    },
    onTreepanelCheckChange: function(node, checked, options) {
        checkChild(node, checked);
    },
    onPanelActivate: function(abstractcomponent, options) {
        var p = abstractcomponent.up("tabpanel").down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        var grid = abstractcomponent.down("panel");
        var gridStore =  grid.store;
        var params = gridStore.proxy.extraParams;
        params.agentId = agentId;
        params.ampInstId = ampInstId;
        gridStore.proxy.url="findVmTreeStore";
//        gridStore.proxy.url="refreshVmTree";
        gridStore.load({
            scope: this,
            callback: function(records, operation, success) {
            	if(success==false){
				   	gridStore.proxy.url="findVmTreeStore";
        			gridStore.load(); 
            	}
            }
        });
    },
    onButtonClick00: function(button, e, options) {
        var p = button.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        //组装json数据源
        var agentAmpInfo = "[{'agentId':'"+agentId+"','ampId':'" + button.ownerCt.down("#ampId").getValue() + "','ampInstId':'"+ampInstId+"'}]";
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.startingAMPInstance,
            progressText: avmon.config.starting,
            width:300,
            wait:true
        });
        Ext.Ajax.request({
            url: 'startNormalAmp',
            method :"POST",
            params: {
                agentAmpInfo:agentAmpInfo
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                if(returnJson.success){
                    button.ownerCt.down("#ampInstStatus").setValue(avmon.config.running)
                    button.ownerCt.down("#ampStart").setDisabled(true);
                    button.ownerCt.down("#ampStop").setDisabled(false);
                }
				top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.errorMsg);
            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }
        });
    },
    onButtonClick01: function(button, e, options) {
        var p = button.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        //组装json数据源
        var agentAmpInfo = "[{'agentId':'"+agentId+"','ampId':'" + button.ownerCt.down("#ampId").getValue() + "','ampInstId':'"+ampInstId+"'}]";
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.stoppingAMPInstance,
            progressText: avmon.config.starting,
            width:300,
            wait:true
        });
        //下发脚本
        Ext.Ajax.request({
            url: 'pauseNormalAmp',
            method :"POST",
            params: {
                agentAmpInfo:agentAmpInfo
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                if(returnJson.success){
                    button.ownerCt.down("#ampInstStatus").setValue(avmon.config.stopRun)
                    button.ownerCt.down("#ampStart").setDisabled(true);
                    button.ownerCt.down("#ampStop").setDisabled(false);
                }
				top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.errorMsg);
            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }
        });
    },
    onButtonClick02: function(button, e, options) {
        var p = button.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        //组装json数据源
        var agentAmpInfo = "[{'agentId':'"+agentId+"','ampId':'" + button.ownerCt.down("#ampId").getValue() + "','ampInstId':'"+ampInstId+"'}]";
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.beIssuedByScriptPleaseWait,
            progressText: avmon.config.issuing,
            width:300,
            wait:true
        });
        //下发脚本
        Ext.Ajax.request({
            url: 'pushAgentAmpScript',
            method :"POST",
            params: {
                agentAmpInfo:agentAmpInfo
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                if(returnJson.success){
                    button.ownerCt.down("#ampInstStatus").setValue(avmon.config.stopRun)
                    button.ownerCt.down("#ampStart").setDisabled(true);
                    button.ownerCt.down("#ampStop").setDisabled(false);
                }
				top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }
        });
    },

    onButtonClick03: function(button, e, options) {
        var p = button.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        //组装json数据源
        var agentAmpInfo = "[{'agentId':'"+agentId+"','ampId':'" + button.ownerCt.down("#ampId").getValue() + "','ampInstId':'"+ampInstId+"'}]";
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.issuingConfigurationPleaseWait,
            progressText: avmon.config.issuing,
            width:300,
            wait:true
        });
        //下发脚本
        Ext.Ajax.request({
            url: 'pushAgentAmpConfig',
            method :"POST",
            params: {
                agentId:agentId,
                agentAmpInfo:agentAmpInfo
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }
        });
    },
    pushSelectedSchedule: function(selectData, butId, agentId,flag) {
      var processBar = Ext.MessageBox.show({
          msg: avmon.config.issuingStrategyPleaseWait,
          progressText: avmon.config.issuing,
          width:300,
          wait:true
      }); 
      var url = "pushAgentAmpSchedule";
      if(flag!="all"){
        url = "pushAmpSchedule";
      }
      Ext.Ajax.request({
          url: url,
          method :"POST",
          params: {
              agentAmpInfo:selectData,
              agentId:agentId
          },
          success: function(response){
              processBar.close();
              var jsonSource = Ext.JSON.decode(response.responseText);
              if(jsonSource.success==true || jsonSource.success=='true'){
                  Ext.MessageBox.show({
                      title: avmon.common.message,
                      msg: avmon.config.issueStrategySuccess,
                      buttons: Ext.MessageBox.OK,
                      icon: Ext.MessageBox.INFO,
                      toFrontOnShow:true,
                      animateTarget:butId
                  });
                  Ext.getCmp('normalScheduleGrid').store.load(
                      {
                        scope: this,
                        callback: function(records, operation, success) {
                          var grid = Ext.getCmp("normalScheduleGrid");
                          var items = grid.store.data.items;
                          var selModes = grid.selModel;
                          Ext.each(items,function(item,index){
                            if(item.data.status=='1'){
                              selModes.doSelect(items[index],true);
                            }
                          });
                        }
                      }
                  );
              }else{
                  Ext.MessageBox.show({
                      title: avmon.config.issueStrategyFailure,
                      msg: jsonSource.errorMsg,
                      buttons: Ext.MessageBox.OK,
                      icon: Ext.MessageBox.ERROR,
                      toFrontOnShow:true,
                      animateTarget:butId
                  }); 
              }
          },
          failure: function(response, opts) {
              Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
          }
      });
  }
});