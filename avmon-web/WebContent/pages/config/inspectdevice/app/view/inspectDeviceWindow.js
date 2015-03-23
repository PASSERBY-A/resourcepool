Ext.define('MyApp.view.inspectDeviceWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.inspectDeviceWindow',
    height: 466,
    id: 'inspectDeviceWindow',
    width: 551,
    closeAction: 'hide',
    title: avmon.config.networkInspectionDeviceManagement,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 432,
                    id: 'inspectDeviceForm',
                    width: 538,
                    layout: {
                        type: 'absolute'
                    },
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'fieldset',
                            x: 10,
                            y: 10,
                            height: 190,
                            padding: 5,
                            width: 510,
                            layout: {
                                type: 'absolute'
                            },
                            items: [
                                {
                                    xtype: 'textfield',
                                    x: 0,
                                    y: 0,
                                    id: 'INSPECT_TYPE',
                                    width: 200,
                                    name: 'INSPECT_TYPE',
                                    fieldLabel: avmon.config.inspectionType,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'combobox',
                                    x: 270,
                                    y: 120,
                                    id: 'DEVICE_TYPE',
                                    width: 200,
                                    name: 'DEVICE_TYPE',
                                    fieldLabel: avmon.config.deviceType,
                                    labelAlign: 'right',
                                    labelWidth: 60,
                                    displayField: 'DEVICE_TYPE_VALUE',
                                    store: 'deviceTypeStore',
                                    valueField: 'DEVICE_TYPE_CODE',
                                    listeners: {
                                        select: {
                                            fn: me.onComboboxSelect,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'textfield',
                                    x: 0,
                                    y: 60,
                                    id: 'DEVICE_VERSION',
                                    width: 470,
                                    name: 'DEVICE_VERSION',
                                    fieldLabel: avmon.config.version,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    x: 270,
                                    y: 0,
                                    id: 'DEVICE_IP',
                                    width: 200,
                                    name: 'DEVICE_IP',
                                    fieldLabel: avmon.config.deviceIP,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    x: -2,
                                    y: 30,
                                    id: 'DEVICE_NM',
                                    width: 470,
                                    name: 'DEVICE_NM',
                                    fieldLabel: avmon.config.devicename,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    x: 0,
                                    y: 90,
                                    id: 'USR',
                                    width: 200,
                                    name: 'USR',
                                    fieldLabel: avmon.config.userName,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    x: 270,
                                    y: 90,
                                    id: 'PWD',
                                    width: 200,
                                    inputType: 'password',
                                    name: 'PWD',
                                    fieldLabel: avmon.config.password,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    x: 0,
                                    y: 120,
                                    id: 'QUIT_MODE1',
                                    width: 200,
                                    name: 'QUIT_MODE1',
                                    fieldLabel: avmon.config.exitCommand,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    x: 0,
                                    y: 150,
                                    id: 'DEPLOY_ENGINE',
                                    width: 200,
                                    name: 'DEPLOY_ENGINE',
                                    fieldLabel: avmon.config.deployHost,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    x: 270,
                                    y: 150,
                                    id: 'BACKUP1',
                                    width: 200,
                                    name: 'BACKUP1',
                                    fieldLabel: avmon.system.note,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                }
                            ]
                        },
                        {
                            xtype: 'button',
                            x: 140,
                            y: 400,
                            width: 80,
                            iconCls: 'icon-save',
                            text: avmon.common.save,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            x: 320,
                            y: 400,
                            width: 80,
                            iconCls: 'icon-cancel',
                            text: avmon.common.cancel,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick1,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'gridpanel',
                            x: 10,
                            y: 210,
                            height: 180,
                            id: 'riDeviceCommGrid',
                            width: 510,
                            title: '',
                            store: 'riDeviceCommStore',
                            viewConfig: {
                                listeners: {
                                    itemdblclick: {
                                        fn: me.onGridviewItemDblClick,
                                        scope: me
                                    }
                                }
                            },
                            dockedItems: [
                                {
                                    xtype: 'pagingtoolbar',
                                    dock: 'bottom',
                                    width: 360,
                                    displayInfo: true,
                                    store: 'riDeviceCommStore'
                                },
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
                                                            fn: me.onButtonClick2,
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
                                                            fn: me.onButtonClick3,
                                                            scope: me
                                                        }
                                                    }
                                                }
                                            ]
                                        }
                                    ]
                                }
                            ],
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'DEVICE_IP',
                                    text: avmon.config.deviceIP
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
                                    dataIndex: 'COMM_CODE',
                                    text: avmon.config.command
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'QUIT_MODE1',
                                    text: avmon.config.exitCommandFirst
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'QUIT_MODE2',
                                    text: avmon.config.exitCommandSecond
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    dataIndex: 'TEMP_ID',
                                    text: 'ID'
                                }
                            ],
                            selModel: Ext.create('Ext.selection.CheckboxModel', {})
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onComboboxSelect: function(combo, records, options) {
        Ext.inspectDeviceType = combo.getValue('DEVICE_TYPE_CODE');
    },
    onButtonClick: function(button, e, options) {
        var myform = Ext.getCmp('inspectDeviceForm').getForm();
        if (myform.isValid()) {
            var records = [];
            var commGridStore = Ext.getCmp('riDeviceCommGrid').getStore();
            for(var i = 0; i < commGridStore.data.length; i++){   
                var COMM_DEVICE_IP = commGridStore.getAt(i).get('DEVICE_IP');
                var COMM_USR = commGridStore.getAt(i).get('USR');
                var COMM_PWD = commGridStore.getAt(i).get('PWD');
                var COMM_COMM_CODE = commGridStore.getAt(i).get('COMM_CODE');
                var COMM_QUIT_MODE1 = commGridStore.getAt(i).get('QUIT_MODE1');
                var COMM_QUIT_MODE2 = commGridStore.getAt(i).get('QUIT_MODE2');
                var data = {COMM_DEVICE_IP:'', COMM_USR:'', COMM_PWD:'', COMM_COMM_CODE:'', COMM_QUIT_MODE1:'',COMM_QUIT_MODE2:''};
                data.COMM_DEVICE_IP = encodeURI(COMM_DEVICE_IP);
                data.COMM_USR = encodeURI(COMM_USR);
                data.COMM_PWD = encodeURI(COMM_PWD);
                data.COMM_COMM_CODE = encodeURI(COMM_COMM_CODE);
                data.COMM_QUIT_MODE1 = encodeURI(COMM_QUIT_MODE1);
                data.COMM_QUIT_MODE2 = encodeURI(COMM_QUIT_MODE2);
                records.push(data);
            }
            Ext.Ajax.request({  
                url:'saveRIDeviceAndComm',  
                params:{  
                    //传递参数  
                    ID: Ext.selectId,
                    INSPECT_TYPE: Ext.getCmp('INSPECT_TYPE').getValue(),
                    DEVICE_TYPE: Ext.inspectDeviceType,
                    DEVICE_VERSION: Ext.getCmp('DEVICE_VERSION').getValue(),
                    DEVICE_IP: Ext.getCmp('DEVICE_IP').getValue(),
                    DEVICE_NM: Ext.getCmp('DEVICE_NM').getValue(),
                    USR: Ext.getCmp('USR').getValue(),
                    PWD: Ext.getCmp('PWD').getValue(),
                    QUIT_MODE1: Ext.getCmp('QUIT_MODE1').getValue(),
                    DEPLOY_ENGINE: Ext.getCmp('DEPLOY_ENGINE').getValue(),
                    BACKUP1: Ext.getCmp('BACKUP1').getValue(),
                    COMMANDS: Ext.JSON.encode(records)
                }, 
                async : false,
                success:function(response, options){        
                    var responseArray = Ext.JSON.decode(response.responseText);
                    if(responseArray.success == 'true' || responseArray.success == true) {
                        Ext.Msg.alert(avmon.common.message,avmon.config.saveSuccess);  
                        Ext.getCmp('riDeviceGrid').getStore().removeAll();
                        Ext.getCmp('riDeviceGrid').getStore().load();
                    }
                },
                failure:function(){}
            });
            var win = Ext.inspectDeviceWindow;
            if (win) {
                win.hide();
            }
        }
    },
    onButtonClick1: function(button, e, options) {
        var win = Ext.inspectDeviceWindow;
        if (win) {
            win.hide();
        }
    },
    onGridviewItemDblClick: function(dataview, record, item, index, e, options) {
        Ext.commId = record.data.TEMP_ID;
        var win = Ext.inspectCommWindow;
        if(!win){
            win = Ext.create('widget.inspectCommWindow');
            Ext.inspectCommWindow = win;
            win.needReload = true;
        }
        Ext.commWindowMode = 1;
        win.show();
        var comm = Ext.getCmp('COMM_COMM_CODE');
        var commStoreProxy = comm.getStore().getProxy();
        commStoreProxy.extraParams.DEVICE_TYPE_CODE = Ext.inspectDeviceType;
        comm.getStore().load();
        // 将历史记录清空
        Ext.getCmp('COMM_COMM_CODE').setRawValue(record.data.COMM_CODE);
        //Ext.getCmp('COMM_COMM_CODE').setReadOnly(true);
        Ext.deviceIp = Ext.getCmp('DEVICE_IP').getValue();
        Ext.getCmp('COMM_USR').setValue(record.data.USR);
        Ext.getCmp('COMM_PWD').setValue(record.data.PWD);
        Ext.getCmp('COMM_QUIT_MODE1').setValue(record.data.QUIT_MODE1);
        Ext.getCmp('COMM_QUIT_MODE2').setValue(record.data.QUIT_MODE2);
    },
    onButtonClick2: function(button, e, options) {
        // 设备IP
        Ext.deviceIp = Ext.getCmp('DEVICE_IP').getValue();
        if (Ext.deviceIp == null || Ext.deviceIp == '') {
            Ext.Msg.alert(avmon.common.reminder,avmon.config.pleaseInpuDeviceIP); 
            return;
        }
        // 设备类型
        if (Ext.inspectDeviceType == null || Ext.inspectDeviceType == '') {
            Ext.Msg.alert(avmon.common.reminder,avmon.config.selectDeviceType); 
            return;
        }   
        var win = Ext.inspectCommWindow;
        if(!win){
            win=Ext.create('widget.inspectCommWindow');
            Ext.inspectCommWindow=win;
            win.needReload=true;
            win.show();
        } else {
            win.show();
            var comm = Ext.getCmp('COMM_COMM_CODE');
            var commStoreProxy = comm.getStore().getProxy();
            commStoreProxy.extraParams.DEVICE_TYPE_CODE = Ext.inspectDeviceType;
            comm.getStore().load();
            // 将历史记录清空
            Ext.getCmp('COMM_COMM_CODE').setRawValue('');
            //Ext.getCmp('COMM_COMM_CODE').setReadOnly(false);
            Ext.getCmp('COMM_USR').setValue('');
            Ext.getCmp('COMM_PWD').setValue('');
            Ext.getCmp('COMM_QUIT_MODE1').setValue('');
            Ext.getCmp('COMM_QUIT_MODE2').setValue('');
        }
        // 窗口打开模式
        Ext.commWindowMode = 0;
        Ext.commId = '';
    },
    onButtonClick3: function(button, e, options) {
        var riDeviceCommGrid = Ext.getCmp('riDeviceCommGrid');
        var selrecord = riDeviceCommGrid.getSelectionModel().getSelection();
        if(selrecord.length == 0){
            Ext.MessageBox.show({
                title:avmon.common.reminder,
                msg:avmon.config.selectOperateLine
            })
            return;
        } else {
            var ids = "";
            for(var i = 0; i < selrecord.length; i++){
                riDeviceCommGrid.getStore().remove(selrecord[i]);
            }
        }
    }
});