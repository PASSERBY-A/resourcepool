Ext.define('MyApp.view.thresholdWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.thresholdWindow',
    height: 512,
    id: 'thresholdWindow',
    width: 543,
    title: avmon.config.kpiAlarmThresholdValue,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    height: 482,
                    id: 'thresholdForm',
                    width: 530,
                    layout: {
                        type: 'absolute'
                    },
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'textfield',
                            x: 30,
                            y: 10,
                            id: 'MO',
                            width: 460,
                            fieldLabel: avmon.config.monitoringObject,
                            labelWidth: 80,
                            allowBlank: false
                        },
                        {
                            xtype: 'textfield',
                            x: 30,
                            y: 40,
                            id: 'MONITOR_INSTANCE',
                            width: 460,
                            fieldLabel: avmon.batchdeploy.monitorInstance,
                            labelWidth: 80,
                            allowBlank: false
                        },
                        {
                            xtype: 'textfield',
                            x: 30,
                            y: 70,
                            id: 'KPI',
                            width: 460,
                            fieldLabel: 'KPI',
                            labelWidth: 80,
                            allowBlank: false
                        },
                        {
                            xtype: 'button',
                            x: 120,
                            y: 450,
                            height: 20,
                            width: 100,
                            iconCls: 'icon-save',
                            text: avmon.common.save,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick1,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            x: 290,
                            y: 450,
                            height: 20,
                            width: 100,
                            iconCls: 'icon-cancel',
                            text: avmon.common.cancel,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'fieldset',
                            x: 20,
                            y: 100,
                            height: 120,
                            width: 480,
                            layout: {
                                type: 'absolute'
                            },
                            items: [
                                {
                                    xtype: 'textfield',
                                    x: 0,
                                    y: 0,
                                    id: 'THRESHOLD',
                                    width: 220,
                                    fieldLabel: avmon.config.alarmThresholdValue,
                                    labelWidth: 80,
                                    allowBlank: false
                                },
                                {
                                    xtype: 'combobox',
                                    x: 240,
                                    y: 0,
                                    width: 220,
                                    fieldLabel: avmon.config.judgmentMethod,
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'numberfield',
                                    x: 0,
                                    y: 30,
                                    id: 'ACCUMULATE_COUNT',
                                    width: 220,
                                    fieldLabel: avmon.config.flashingNumber,
                                    labelWidth: 80,
                                    allowBlank: false
                                },
                                {
                                    xtype: 'combobox',
                                    x: 240,
                                    y: 30,
                                    id: 'ALARM_LEVEL',
                                    width: 220,
                                    fieldLabel: avmon.alarm.alarmLevel,
                                    labelWidth: 80,
                                    allowBlank: false,
                                    displayField: 'LEVEL_NM',
                                    store: 'alarmLevelStore',
                                    valueField: 'LEVEL_ID'
                                },
                                {
                                    xtype: 'textareafield',
                                    x: -2,
                                    y: 60,
                                    height: 40,
                                    width: 460,
                                    fieldLabel: avmon.config.alarmContentTemplate,
                                    labelWidth: 80
                                }
                            ]
                        },
                        {
                            xtype: 'radiofield',
                            x: 30,
                            y: 90,
                            boxLabel: avmon.config.fixedThreshold,
                            checked: true
                        },
                        {
                            xtype: 'radiofield',
                            x: 30,
                            y: 220,
                            boxLabel: avmon.config.limitOfBaseThreshold
                        },
                        {
                            xtype: 'fieldset',
                            x: 20,
                            y: 230,
                            height: 210,
                            width: 480,
                            layout: {
                                type: 'absolute'
                            },
                            items: [
                                {
                                    xtype: 'textfield',
                                    x: -1,
                                    y: 60,
                                    id: 'MAX_THRESHOLD',
                                    width: 220,
                                    fieldLabel: avmon.config.alarmUpperTolerability,
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'textfield',
                                    x: 850,
                                    y: 100,
                                    id: 'MIN_THRESHOLD',
                                    width: 220,
                                    fieldLabel: avmon.config.alarmFloorTolerability,
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'textfield',
                                    x: 240,
                                    y: 60,
                                    id: 'END_DATE',
                                    width: 220,
                                    fieldLabel: avmon.config.alarmFloorTolerability,
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'textareafield',
                                    x: -1,
                                    y: 150,
                                    height: 40,
                                    id: 'CONTENT',
                                    width: 460,
                                    fieldLabel: avmon.config.alarmContentTemplate,
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'radiogroup',
                                    x: 0,
                                    y: 30,
                                    width: 150,
                                    labelWidth: 50,
                                    items: [
                                        {
                                            xtype: 'radiofield',
                                            boxLabel: avmon.config.absoluteValue,
                                            checked: true
                                        },
                                        {
                                            xtype: 'radiofield',
                                            boxLabel: avmon.config.percent
                                        }
                                    ]
                                },
                                {
                                    xtype: 'combobox',
                                    x: 0,
                                    y: 90,
                                    width: 220,
                                    fieldLabel: avmon.alarm.alarmLevel,
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'textfield',
                                    x: 240,
                                    y: 90,
                                    fieldLabel: avmon.config.samplingFrequency,
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'textfield',
                                    x: 0,
                                    y: 120,
                                    fieldLabel: avmon.config.flashingNumber,
                                    labelWidth: 80
                                },
                                {
                                    xtype: 'checkboxgroup',
                                    x: 10,
                                    y: 0,
                                    width: 350,
                                    fieldLabel: avmon.config.type,
                                    labelWidth: 80,
                                    items: [
                                        {
                                            xtype: 'checkboxfield',
                                            boxLabel: avmon.config.day
                                        },
                                        {
                                            xtype: 'checkboxfield',
                                            boxLabel: avmon.config.week
                                        },
                                        {
                                            xtype: 'checkboxfield',
                                            boxLabel: avmon.config.workday
                                        },
                                        {
                                            xtype: 'checkboxfield',
                                            boxLabel: avmon.config.holiday
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onButtonClick1: function(button, e, options) {
        var myform = Ext.getCmp('thresholdForm').getForm();
        if (myform.isValid()) {
            // 取值 
            var ID = Ext.selectId;
            var MO = Ext.getCmp('MO').getValue();
            var MONITOR_INSTANCE = Ext.getCmp('MONITOR_INSTANCE').getValue();
            var KPI = Ext.getCmp('KPI').getValue();
            var MAX_THRESHOLD = Ext.getCmp('MAX_THRESHOLD').getValue();
            var MIN_THRESHOLD = Ext.getCmp('MIN_THRESHOLD').getValue();
            var THRESHOLD = Ext.getCmp('THRESHOLD').getValue();
            var CHECK_OPTR = Ext.getCmp('CHECK_OPTR').getValue();
            var ALARM_LEVEL = Ext.getCmp('ALARM_LEVEL').getValue();
            var ACCUMULATE_COUNT = Ext.getCmp('ACCUMULATE_COUNT').getValue();
            var START_DATE = Ext.getCmp('START_DATE').getValue();
            var END_DATE = Ext.getCmp('END_DATE').getValue();
            var CONTENT = Ext.getCmp('CONTENT').getValue();
            if (Ext.thresholdWindowMode == 0) {
                // 保存
                Ext.Ajax.request({  
                    url:'saveThreshold',  
                    params:{  
                        //传递参数  
                        ID: ID,
                        MO: MO,
                        MONITOR_INSTANCE: MONITOR_INSTANCE,
                        KPI: KPI,
                        MAX_THRESHOLD: MAX_THRESHOLD,
                        MIN_THRESHOLD: MIN_THRESHOLD,
                        THRESHOLD: THRESHOLD,
                        CHECK_OPTR: CHECK_OPTR,
                        ALARM_LEVEL: ALARM_LEVEL,
                        ACCUMULATE_COUNT: ACCUMULATE_COUNT,
                        START_DATE: START_DATE,
                        END_DATE: END_DATE,
                        CONTENT: CONTENT 
                    }, 
                    async : false,
                    success:function(response, options){        
                        var responseArray = Ext.JSON.decode(response.responseText);
                    },
                    failure:function(){  
                        //Ext.Msg.alert('错误信息','系统错误');  
                    }
                });
                // 展示grid
                Ext.getCmp('kpiThreshold').getStore().load();
                // 隐藏弹出窗口
                var win = Ext.thresholdWindow;
                win.hide();
            } else {
                // 保存
                Ext.Ajax.request({  
                    url:'saveThreshold',  
                    params:{  
                        //传递参数  
                        ID: ID,
                        MO: MO,
                        MONITOR_INSTANCE: MONITOR_INSTANCE,
                        KPI: KPI,
                        MAX_THRESHOLD: MAX_THRESHOLD,
                        MIN_THRESHOLD: MIN_THRESHOLD,
                        THRESHOLD: THRESHOLD,
                        CHECK_OPTR: CHECK_OPTR,
                        ALARM_LEVEL: ALARM_LEVEL,
                        ACCUMULATE_COUNT: ACCUMULATE_COUNT,
                        START_DATE: START_DATE,
                        END_DATE: END_DATE,
                        CONTENT: CONTENT 
                    }, 
                    async : false,
                    success:function(response, options){        
                        var responseArray = Ext.JSON.decode(response.responseText);
                    },
                    failure:function(){}
                });
                Ext.getCmp('kpiThreshold').getStore().load();
                // 隐藏弹出窗口
                var win = Ext.thresholdWindow;
                win.hide();
            }
        }
    },
    onButtonClick: function(button, e, options) {
        var win = Ext.thresholdWindow;
        win.hide();
    }
});