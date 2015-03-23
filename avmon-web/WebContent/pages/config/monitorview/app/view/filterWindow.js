Ext.define('MyApp.view.filterWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.filterWindow',
    height: 250,
    id: 'filterWindow',
    width: 400,
    title: avmon.config.filterCondition,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 218,
                    id: 'editFilterForm',
                    layout: {
                        type: 'absolute'
                    },
                    items: [
                        {
                            xtype: 'numberfield',
                            x: 10,
                            y: 10,
                            id: 'FILTER_NO',
                            width: 200,
                            fieldLabel: avmon.config.condition,
                            labelWidth: 60,
                            readOnly: true,
                            allowBlank: false,
                            minValue: 1
                        },
                        {
                            xtype: 'combobox',
                            anchor: '100%',
                            x: 10,
                            y: 40,
                            id: 'FILTER_FIELD',
                            width: 366,
                            fieldLabel: avmon.config.column,
                            labelWidth: 60,
                            allowBlank: false,
                            displayField: 'METADATA_NM',
                            store: 'fieldStore',
                            valueField: 'METADATA_ID'
                        },
                        {
                            xtype: 'combobox',
                            anchor: '100%',
                            x: 10,
                            y: 70,
                            id: 'FILTER_OPERATOR',
                            fieldLabel: avmon.config.operationalCharacter,
                            labelWidth: 60,
                            allowBlank: false,
                            displayField: 'FILTER_OPERATOR_NAME',
                            store: 'operatorStore',
                            valueField: 'FILTER_OPERATOR'
                        },
                        {
                            xtype: 'textareafield',
                            anchor: '100%',
                            x: 10,
                            y: 100,
                            id: 'FILTER_VALUE',
                            fieldLabel: avmon.config.value,
                            labelWidth: 60,
                            allowBlank: false
                        },
                        {
                            xtype: 'button',
                            x: 90,
                            y: 185,
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
                            x: 210,
                            y: 185,
                            width: 80,
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
            ]
        });
        me.callParent(arguments);
    },
    onButtonClick: function(button, e, eOpts) {
        var myform = Ext.getCmp('editFilterForm').getForm();
        if (myform.isValid()) {
            var FILTER_NO = Ext.getCmp('FILTER_NO').getValue();
            var FILTER_FIELD = Ext.getCmp('FILTER_FIELD').getValue();
            var FILTER_OPERATOR = Ext.getCmp('FILTER_OPERATOR').getValue();
            var FILTER_VALUE = Ext.getCmp('FILTER_VALUE').getValue();
            if (Ext.filterWindowMode == 0) {
                // 加载日志grid
                //Ext.getCmp('filterGrid').getStore().removeAll();
                var len = Ext.getCmp('filterGrid').getStore().length;
                var data = {FILTER_NO: FILTER_NO, FILTER_FIELD: FILTER_FIELD, FILTER_OPERATOR: FILTER_OPERATOR, FILTER_VALUE: FILTER_VALUE};
                if (len) {
                    Ext.getCmp('filterGrid').getStore().insert(len, data);
                } else {
                    Ext.getCmp('filterGrid').getStore().insert(0, data);
                }
                // 隐藏弹出窗口
                var win = Ext.filterWindow;
                win.hide();
            } else {
                var row = Ext.getCmp('filterGrid').getStore().getById(FILTER_NO.toString());
                if (row) {
                    row.set("FILTER_FIELD", FILTER_FIELD);
                    row.set("FILTER_OPERATOR", FILTER_OPERATOR);
                    row.set("FILTER_VALUE", FILTER_VALUE);
                }
                // 隐藏弹出窗口
                var win = Ext.filterWindow;
                win.hide();
            }
        }
    },
    onButtonClick1: function(button, e, eOpts) {
        var win = Ext.filterWindow;
        win.hide();
    }
});