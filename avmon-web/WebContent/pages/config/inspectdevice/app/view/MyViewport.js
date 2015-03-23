Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',
    layout: {
        type: 'fit'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    layout: {
                        type: 'fit'
                    },
                    title: avmon.config.networkInspectionDeviceManagement,
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'buttongroup',
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            iconCls: 'icon-add',
                                            text: avmon.config.add,
                                            listeners: {
                                                click: {
                                                    fn: me.onButtonClick,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'buttongroup',
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            iconCls: 'icon-delete',
                                            text: avmon.common.deleted,
                                            listeners: {
                                                click: {
                                                    fn: me.onButtonClick1,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'buttongroup',
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            iconCls: 'icon-download',
                                            text: avmon.config.exported,
                                            listeners: {
                                                click: {
                                                    fn: me.onButtonClick2,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    ],
                    items: [
                        {
                            xtype: 'gridpanel',
                            id: 'riDeviceGrid',
                            store: 'riDeviceStore',
                            viewConfig: {
                                listeners: {
                                    itemdblclick: {
                                        fn: me.onGridviewItemDblClick,
                                        scope: me
                                    }
                                }
                            },
                            selModel: Ext.create('Ext.selection.CheckboxModel', {}),
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'INSPECT_TYPE',
                                    text: avmon.config.inspectionType
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'DEVICE_TYPE',
                                    text: avmon.config.deviceType
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'DEVICE_VERSION',
                                    text: avmon.config.version
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'DEVICE_NM',
                                    text: avmon.config.devicename
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'DEVICE_IP',
                                    text: 'IP'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'USR',
                                    text: avmon.config.userName
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    dataIndex: 'PWD',
                                    text: avmon.config.password
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    dataIndex: 'INSPECT_CMD',
                                    text: avmon.config.inspectionOrders
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'DEPLOY_ENGINE',
                                    text: avmon.config.deployHost
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'QUIT_MODE1',
                                    text: avmon.config.exitWayFirst
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    dataIndex: 'QUIT_MODE2',
                                    text: avmon.config.exitWaySecond
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'BACKUP1',
                                    text: avmon.system.note
                                }
                            ],
                            dockedItems: [
                                {
                                    xtype: 'pagingtoolbar',
                                    dock: 'bottom',
                                    width: 360,
                                    displayInfo: true,
                                    store: 'riDeviceStore'
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onButtonClick: function(button, e, options) {
        var win = Ext.inspectDeviceWindow;
        if(!win){
            win=Ext.create('widget.inspectDeviceWindow');
            Ext.inspectDeviceWindow=win;
            win.needReload=true;
            win.show();
        } else {
            win.show();
            // 将历史记录清空
            Ext.getCmp('DEVICE_TYPE').setRawValue('');
            Ext.getCmp('INSPECT_TYPE').setValue('');
            //Ext.getCmp('DEVICE_TYPE').setValue('');
            Ext.getCmp('DEVICE_VERSION').setValue('');
            Ext.getCmp('DEVICE_IP').setValue('');
            Ext.getCmp('DEVICE_NM').setValue('');
            Ext.getCmp('USR').setValue('');
            Ext.getCmp('PWD').setValue('');
            Ext.getCmp('QUIT_MODE1').setValue('');
            Ext.getCmp('DEPLOY_ENGINE').setValue('');
            Ext.getCmp('BACKUP1').setValue('');
            Ext.getCmp('riDeviceCommGrid').getStore().removeAll();
        }
        Ext.selectId = '';
        Ext.inspectDeviceWindowMode=0;
    },
    onButtonClick1: function(button, e, options) {
        var riDeviceGrid = Ext.getCmp('riDeviceGrid');
        var selrecord = riDeviceGrid.getSelectionModel().getSelection();
        if(selrecord.length == 0){
            Ext.MessageBox.show({
                title:avmon.common.reminder,
                msg:avmon.config.selectOperateLine
            })
            return;
        } else {
            var ids = "";
            for(var i = 0; i < selrecord.length; i++){
                ids += selrecord[i].get("ID")
                if(i < selrecord.length-1){
                    ids = ids + "*";
                }
            }
            Ext.Ajax.request({  
                url:'deleteRIDeviceById',  
                params:{  
                    //传递参数  
                    ids: ids
                }, 
                async : false,
                success:function(response, options){        
                    var responseArray = Ext.JSON.decode(response.responseText);
                    if(responseArray.success == 'true' || responseArray.success == true) {
                        Ext.Msg.alert(avmon.common.message,avmon.config.deleteSuccess);  
                    }
                },
                failure:function(){  
                    //Ext.Msg.alert('错误信息','系统错误');  
                }
            });
            Ext.getCmp('riDeviceGrid').getStore().removeAll();
            Ext.getCmp('riDeviceGrid').getStore().load();
        }
    },
    onButtonClick2: function(button, e, options) {
        // 判断是否有数据
        var dataLen = Ext.getCmp('riDeviceGrid').getStore().data.length;
        if (dataLen <= 0) {
            return;
        }
        Ext.Ajax.request({  
            url:'outputRIDeviceComm',  
            params:{}, 
            async : false,
            success:function(response, options){        
                var responseArray = Ext.JSON.decode(response.responseText);
                if(responseArray.success == 'true' || responseArray.success == true) {
                    Ext.Msg.alert(avmon.common.message,avmon.config.exportSuccess);  
                } else {
                    Ext.Msg.alert(avmon.common.message,avmon.config.exportFailure);  
                }
            },
            failure:function(){}
        });
    },
    onGridviewItemDblClick: function(dataview, record, item, index, e, options) {
        Ext.selectId = record.data.ID;
        var win = Ext.inspectDeviceWindow;
        if(!win){
            win = Ext.create('widget.inspectDeviceWindow');
            Ext.inspectDeviceWindow = win;
            win.needReload = true;
        }
        Ext.inspectDeviceWindowMode = 1;
        win.show();
        Ext.getCmp('INSPECT_TYPE').setValue(record.data.INSPECT_TYPE);
        //Ext.getCmp('DEVICE_TYPE').setValue(record.data.DEVICE_TYPE);
        Ext.getCmp('DEVICE_TYPE').setRawValue(record.data.DEVICE_TYPE);
        Ext.getCmp('DEVICE_VERSION').setValue(record.data.DEVICE_VERSION);
        Ext.getCmp('DEVICE_IP').setValue(record.data.DEVICE_IP);
        Ext.getCmp('DEVICE_NM').setValue(record.data.DEVICE_NM);
        Ext.getCmp('USR').setValue(record.data.USR);
        Ext.getCmp('PWD').setValue(record.data.PWD);
        Ext.getCmp('QUIT_MODE1').setValue(record.data.QUIT_MODE1);
        Ext.getCmp('DEPLOY_ENGINE').setValue(record.data.DEPLOY_ENGINE);
        Ext.getCmp('BACKUP1').setValue(record.data.BACKUP1);
        var commGrid = Ext.getCmp('riDeviceCommGrid');
        var commStoreProxy = commGrid.getStore().getProxy();
        commStoreProxy.extraParams.DEVICE_IP = record.data.DEVICE_IP;
        commGrid.getStore().load();
        Ext.inspectDeviceType = record.data.DEVICE_TYPE;
    }
});