Ext.define('MyApp.view.inspectCommWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.inspectCommWindow',
    height: 258,
    id: 'inspectCommWindow',
    width: 410,
    closeAction: 'hide',
    title: avmon.config.inspectionOrders,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 224,
                    id: 'commForm',
                    layout: {
                        type: 'absolute'
                    },
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'fieldset',
                            x: 10,
                            y: 10,
                            height: 160,
                            items: [
                                {
                                    xtype: 'combobox',
                                    anchor: '100%',
                                    id: 'COMM_COMM_CODE',
                                    name: 'COMM_COMM_CODE',
                                    fieldLabel: avmon.config.inspectionOrders,
                                    labelAlign: 'right',
                                    labelWidth: 60,
                                    displayField: 'COMM_VALUE',
                                    multiSelect: true,
                                    store: 'inpectTypeStore',
                                    valueField: 'COMM_CODE',
                                    listeners: {
                                        select: {
                                            fn: me.onCOMM_COMM_CODESelect,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'textfield',
                                    anchor: '100%',
                                    id: 'COMM_USR',
                                    name: 'COMM_USR',
                                    fieldLabel: avmon.config.userName,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    anchor: '100%',
                                    id: 'COMM_PWD',
                                    inputType: 'password',
                                    name: 'COMM_PWD',
                                    fieldLabel: avmon.config.password,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    anchor: '100%',
                                    id: 'COMM_QUIT_MODE1',
                                    name: 'COMM_QUIT_MODE1',
                                    fieldLabel: avmon.config.exitCommandFirst,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                },
                                {
                                    xtype: 'textfield',
                                    anchor: '100%',
                                    id: 'COMM_QUIT_MODE2',
                                    name: 'COMM_QUIT_MODE2',
                                    fieldLabel: avmon.config.exitCommandSecond,
                                    labelAlign: 'right',
                                    labelWidth: 60
                                }
                            ]
                        },
                        {
                            xtype: 'button',
                            x: 90,
                            y: 190,
                            width: 80,
                            iconCls: 'icon-save',
                            text: avmon.common.ok,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            x: 240,
                            y: 190,
                            width: 80,
                            iconCls: 'icon-cancel',
                            text: avmon.common.cancel,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick1,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ],
            listeners: {
                afterlayout: {
                    fn: me.onInspectCommWindowAfterLayout,
                    scope: me
                }
            }
        });
        me.callParent(arguments);
    },
    onCOMM_COMM_CODESelect: function(combo, records, options) {
        Ext.commCode = combo.getValue(combo.getValue('COMM_CODE'));
    },
    onButtonClick: function(button, e, options) {
        var myform = Ext.getCmp('commForm').getForm();
        if (myform.isValid()) {
            var win = Ext.inspectCommWindow;
            var DEVICE_IP = Ext.deviceIp;
            var COMM_COMM_CODE = Ext.commCode;
            var COMM_USR = Ext.getCmp('COMM_USR').getValue();
            var COMM_PWD = Ext.getCmp('COMM_PWD').getValue();
            var COMM_QUIT_MODE1 = Ext.getCmp('COMM_QUIT_MODE1').getValue();
            var COMM_QUIT_MODE2 = Ext.getCmp('COMM_QUIT_MODE2').getValue();
            if (Ext.commWindowMode == 0) {
                // 添加记录到grid
                if (COMM_COMM_CODE != null) {
                    for (var str in COMM_COMM_CODE) {
                        var len = Ext.getCmp('riDeviceCommGrid').getStore().data.length;

                        var data = {
                            TEMP_ID:len + 1,
                            DEVICE_IP:DEVICE_IP,
                            USR: COMM_USR, 
                            PWD: COMM_PWD, 
                            COMM_CODE: COMM_COMM_CODE[str], 
                            QUIT_MODE1: COMM_QUIT_MODE1,
                            QUIT_MODE2: COMM_QUIT_MODE2
                        };
                        if (len) {
                            Ext.getCmp('riDeviceCommGrid').getStore().insert(len, data);
                        } else {
                            Ext.getCmp('riDeviceCommGrid').getStore().insert(0, data);
                        }
                    }
                    // 隐藏弹出窗口
                    win.hide();
                } else {
                    return;
                }
            } else {
                if (Ext.commId != null && Ext.commId != '') {
                    var commId = Ext.commId.toString();
                    var commGridStore = Ext.getCmp('riDeviceCommGrid').getStore();
                    for(var i = 0; i < commGridStore.data.length; i++){   
                        var row = commGridStore.getAt(i);
                        if (row.get('TEMP_ID') == commId) {
                            if (row) {
                                row.set("DEVICE_IP", DEVICE_IP);
                                row.set("USR", COMM_USR);
                                row.set("PWD", COMM_PWD);
                                //row.set("COMM_CODE", COMM_COMM_CODE);
                                row.set("QUIT_MODE1", COMM_QUIT_MODE1);
                                row.set("QUIT_MODE2", COMM_QUIT_MODE2);
                            }
                        }
                    }
                    // 隐藏弹出窗口
                    win.hide();
                }        
            }
        }
    },
    onButtonClick1: function(button, e, options) {
        var win = Ext.inspectCommWindow;
        if (win) {
            win.hide();
        }
    },
    onInspectCommWindowAfterLayout: function(abstractcontainer, layout, options) {
        var commStoreProxy = Ext.getCmp('COMM_COMM_CODE').getStore().getProxy();
        commStoreProxy.extraParams.DEVICE_TYPE_CODE = Ext.inspectDeviceType;
    }
});