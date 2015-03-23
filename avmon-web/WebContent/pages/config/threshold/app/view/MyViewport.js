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
                    title: avmon.config.kpiDynamicWarningThresholdValue,
                    items: [
                        {
                            xtype: 'gridpanel',
                            id: 'kpiThreshold',
                            store: 'thresholdStore',
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
                                    store: 'thresholdStore'
                                }
                            ],
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'ID',
                                    text: 'ID'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'MO',
                                    text: avmon.config.monitoringObject
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'MONITOR_INSTANCE',
                                    text: avmon.batchdeploy.monitorInstance
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'KPI',
                                    text: 'KPI'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'MIN_THRESHOLD',
                                    text: avmon.config.alarmLowestThresholdValue
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'THRESHOLD',
                                    text: avmon.config.alarmThresholdValue
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'MAX_THRESHOLD',
                                    text: avmon.config.alarmMaximumThresholdValue
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'START_DATE',
                                    text: avmon.config.startTime
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'END_DATE',
                                    text: avmon.config.stopTime
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'CHECK_OPTR',
                                    text: avmon.config.judgmentMethod
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'ALARM_LEVEL',
                                    text: avmon.alarm.alarmLevel
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'ACCUMULATE_COUNT',
                                    text: avmon.config.flashingNumber
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'CONTENT',
                                    text: avmon.alarm.alarmContent
                                }
                            ],
                            selModel: Ext.create('Ext.selection.CheckboxModel', {

                            })
                        }
                    ],
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
                                            id: 'addThreshold',
                                            iconCls: 'icon-add',
                                            text: avmon.config.newIncrease,
                                            listeners: {
                                                click: {
                                                    fn: me.onAddThresholdClick,
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
                                            id: 'delThreshold',
                                            iconCls: 'icon-delete',
                                            text: avmon.common.deleted,
                                            listeners: {
                                                click: {
                                                    fn: me.onDelThresholdClick,
                                                    scope: me
                                                }
                                            }
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

    onGridviewItemDblClick: function(dataview, record, item, index, e, options) {
        Ext.selectId = record.data.ID;
        var win = Ext.thresholdWindow;
        if(!win){
            win=Ext.create('widget.thresholdWindow');
            Ext.thresholdWindow=win;
            win.needReload=true;
        }
        Ext.thresholdWindowMode=1;
        win.show();
        Ext.getCmp('MO').setValue(record.data.MO);
        Ext.getCmp('MONITOR_INSTANCE').setValue(record.data.MONITOR_INSTANCE);
        Ext.getCmp('KPI').setValue(record.data.KPI);
        Ext.getCmp('MAX_THRESHOLD').setValue(record.data.MAX_THRESHOLD);
        Ext.getCmp('MIN_THRESHOLD').setValue(record.data.MIN_THRESHOLD);
        Ext.getCmp('THRESHOLD').setValue(record.data.THRESHOLD);
        Ext.getCmp('CHECK_OPTR').setValue(record.data.CHECK_OPTR);
        Ext.getCmp('ALARM_LEVEL').setValue(record.data.ALARM_LEVEL);
        Ext.getCmp('ACCUMULATE_COUNT').setValue(record.data.ACCUMULATE_COUNT);
        Ext.getCmp('START_DATE').setValue(record.data.START_DATE);
        Ext.getCmp('END_DATE').setValue(record.data.END_DATE);
        Ext.getCmp('CONTENT').setValue(record.data.CONTENT);
    },
    onAddThresholdClick: function(button, e, options) {
        var win = Ext.thresholdWindow;
        if(!win){
            win=Ext.create('widget.thresholdWindow');
            Ext.thresholdWindow=win;
            win.needReload=true;
            win.show();
        } else {
            win.show();
            // 将历史记录清空
            Ext.getCmp('MO').setValue('');
            Ext.getCmp('MONITOR_INSTANCE').setValue('');
            Ext.getCmp('KPI').setValue('');
            Ext.getCmp('MAX_THRESHOLD').setValue('');
            Ext.getCmp('MIN_THRESHOLD').setValue('');
            Ext.getCmp('THRESHOLD').setValue('');
            Ext.getCmp('CHECK_OPTR').setValue('');
            Ext.getCmp('ALARM_LEVEL').setValue('');
            Ext.getCmp('ACCUMULATE_COUNT').setValue('');
            Ext.getCmp('START_DATE').setValue('');
            Ext.getCmp('END_DATE').setValue('');
            Ext.getCmp('CONTENT').setValue('');
        }
        Ext.selectId = '';
        Ext.thresholdWindowMode=0;
    },
    onDelThresholdClick: function(button, e, options) {
        var kpiThresholdGird = Ext.getCmp('kpiThreshold');
        var selrecord = kpiThresholdGird.getSelectionModel().getSelection();
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
                url:'deleteThresholdById',  
                params:{  
                    //传递参数  
                    ids: ids
                }, 
                async : false,
                success:function(response, options){        
                    var responseArray = Ext.JSON.decode(response.responseText);
                },
                failure:function(){  
                    //Ext.Msg.alert('错误信息','系统错误');  
                }
            });
            Ext.getCmp('kpiThreshold').getStore().load();
        }
    }
});