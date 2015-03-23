Ext.define('MyApp.view.ILOAmpSetWindow', {
    extend: 'Ext.window.Window',
    requires: [
        'MyApp.view.MyPanel8'
    ],
    height: 450,
    width: 750,
    layout: {
        type: 'absolute'
    },
    title: avmon.config.ampConfig,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    x: 0,
                    y: 0,
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
                            itemId: 'ampInstStatus',
                            hidden:true,
                            width: 250,
                            fieldLabel: avmon.config.status,
                            labelWidth: 70
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
//                        }
//                        ,
//                        {
//                            xtype: 'button',
//                            x: 220,
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
                    x: -3,
                    y: 120,
                    height: 300,
                    items: [
                        {
                            xtype: 'mypanel8'
                        },
                        {
                            xtype: 'panel',
                            layout: {
                                type: 'fit'
                            },
                            frame:true,
                            title: avmon.config.iloHostList,
                            items: [
                                {
                                    xtype: 'gridpanel',
                                    store: 'IloHostListStore',
                                    dockedItems: [
                                        {
                                            xtype: 'toolbar',
                                            dock: 'top',
                                            items: [
                                                {
                                                    xtype: 'button',
                                                    itemId:'new',
                                                    iconCls: 'icon-add',
                                                    text: avmon.config.addILOHost,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick,
                                                            scope: me
                                                        }
                                                    }
                                                },
                                                {
                                                    xtype: 'tbseparator'
                                                },
                                                {
                                                    xtype: 'button',
                                                    itemId:'edit',
                                                    iconCls: 'icon-edit',
                                                    text: avmon.config.configILOHost,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick,
                                                            scope: me
                                                        }
                                                    }
                                                },
                                                {
                                                    xtype: 'tbseparator'
                                                },
                                                {
                                                    xtype: 'button',
                                                    itemId:'delete',
                                                    iconCls: 'icon-delete',
                                                    text: avmon.config.deleteILOHost,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick2,
                                                            scope: me
                                                        }
                                                    }
                                                },
                                                {
                                                    xtype: 'tbseparator'
                                                },
                                                {
                                                    xtype: 'button',
                                                    itemId:'push',
                                                    iconCls: 'icon-ok',
                                                    text: avmon.config.IssuedDispatch,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick3,
                                                            scope: me
                                                        }
                                                    }
                                                }
                                            ]
                                        }
                                    ],
                                    viewConfig: {},
                                    selModel: Ext.create('Ext.selection.CheckboxModel', {}),
                                    columns: [
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'ip',
                                            text: avmon.config.iloHostIP
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'hostName',
                                            text: avmon.config.hostname
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'userName',
                                            text: avmon.config.userName
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'password',
                                            text: avmon.config.password
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'extParam1',
                                            hidden:true,
                                            text: 'extParam1'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'extParam2',
                                            hidden:true,
                                            text: 'extParam2'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'extParam3',
                                            hidden:true,
                                            text: 'extParam3'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'moId',
                                            hidden:true,
                                            text: 'moId'
                                        }
                                    ]
                                }
                            ],
                            listeners: {
                                activate: {
                                    fn: me.onPanelActivate,
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
    onButtonClick: function(button, e, options) {
    	var buttonId = button.itemId;
        var p = button.ownerCt.ownerCt.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        var nodeKey = "";
        if(buttonId=="edit"){
        	var iloGrid = button.ownerCt.ownerCt;
        	var selection = iloGrid.getSelectionModel().getSelection();
            if(selection.length != 1){ 
                Ext.MessageBox.alert(avmon.common.reminder,avmon.config.selectILOHostForConfig);
                return; 
            }else{
            	nodeKey = iloGrid.getSelectionModel().getSelection()[0].data.ip;
            }
        }
        var win = Ext.create('MyApp.view.ILOConfigWindow');
        win.opener = button.ownerCt.ownerCt;
        win.show();
        if(buttonId=="new"){
        	win.down("grid").down("toolbar").hide();
        }
        win.down("panel").down("#flag").setValue(buttonId);
        //从amp定义表中加载调度策略信息
        var gridStore = win.down("grid").store;
        var proxy = gridStore.proxy;
        proxy.extraParams.agentId = agentId;
        proxy.extraParams.ampInstId = ampInstId;
        proxy.extraParams.flag = button.itemId;//"new";
        proxy.extraParams.nodeKey = nodeKey;//"new";
        gridStore.load();
        if(buttonId=="edit"){//加载表单数据
        	var iloGrid = button.ownerCt.ownerCt;
        	var selectData = iloGrid.getSelectionModel().getSelection()[0].data;
        	var configPanel = win.down("#configPanel");
            configPanel.down("#ip").setValue(selectData.ip);
            configPanel.down("#oldIp").setValue(selectData.ip);
            configPanel.down("#moId").setValue(selectData.moId);
            configPanel.down("#hostName").setValue(selectData.hostName);
            configPanel.down("#userName").setValue(selectData.userName);
            configPanel.down("#password").setValue(selectData.password);
            configPanel.down("#extParam1").setValue(selectData.extParam1);
            configPanel.down("#extParam2").setValue(selectData.extParam2);
            configPanel.down("#extParam3").setValue(selectData.extParam3);
            configPanel.down("#flag").setValue("edit");
        }
    },
    onButtonClick2: function(button, e, options) {
        var p = button.ownerCt.ownerCt.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        var selection = button.ownerCt.ownerCt.getSelectionModel().getSelection();
        var iloGridStore = button.ownerCt.ownerCt.getStore();
        if (selection.length == 0) {
            Ext.MessageBox.alert(avmon.common.reminder, avmon.config.selectILOHostForDelete);
            return;
        } else {
            Ext.MessageBox.confirm(avmon.common.reminder, avmon.config.whetherToDelete,function(btn) {
                if (btn == "yes") {
                    var ips = [];
                    Ext.each(selection,function(item) {
                        ips.push("'" + item.data.ip + "'");
                    });
                    Ext.Ajax.request({
                        url : 'deleteIloHostByIps',
                        params:{agentId:agentId,ampInstId:ampInstId,ips:ips.join(',')},
                        success : function(response, opts) {
                            var returnJson = Ext.JSON.decode(response.responseText);
                            Ext.MessageBox.alert(avmon.common.reminder,returnJson.msg);
                            iloGridStore.load();
                        },
                        failure : function(response, opts) {}
                    });
                }
            });
        }
    },
    onButtonClick3: function(button, e, options) {
        var p = button.ownerCt.ownerCt.ownerCt.ownerCt.down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        var ampId = button.ownerCt.ownerCt.ownerCt.ownerCt.ownerCt.down("#ampId").getValue();
        var iloGridStore = button.ownerCt.ownerCt.getStore();
        var selection = button.ownerCt.ownerCt.getSelectionModel().getSelection();
        var jsonSource = "";
        if (selection.length == 0) {
            Ext.MessageBox.alert(avmon.common.reminder, avmon.config.selectILOHost);
            return;
        } else {
        	//获取选择的AMP实例信息
            //var dataSelection = grid.getSelectionModel().getSelection();
            jsonSource = "[";    
            for(var i=0;i<selection.length;i++){
                if(i == selection.length-1){
                    jsonSource += Ext.JSON.encode(selection[i].data);  
                }else{
                    jsonSource += Ext.JSON.encode(selection[i].data)+","; 
                }
            }
            jsonSource+= "]";
        }
        //下发调度
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.issuingSchedulWait,
            progressText: avmon.config.issuing,
            width:300,
            wait:true
        });
        Ext.Ajax.request({
            url: 'pushIloHostSchedule',
            method :"POST",
            params: {
                agentAmpInfo:jsonSource,
                agentId:agentId
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
				top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.errorMsg);
            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }
        });
    },
    onPanelActivate: function(abstractcomponent, options) {
        var p = abstractcomponent.up("tabpanel").down("#attrPanel").down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        var grid = abstractcomponent.down("panel");
        var iloHostGridStore =  grid.store;
        var params = iloHostGridStore.proxy.extraParams;
        params.agentId = agentId;
        params.ampInstId = ampInstId;
        iloHostGridStore.load();
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
				top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.errorMsg);
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
                //ampInstId:ampInstId,
                //ampId:ampId
                agentAmpInfo:agentAmpInfo
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.errorMsg);
            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }
        });
    }
});