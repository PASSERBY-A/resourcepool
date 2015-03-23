Ext.define('MyApp.view.MyPanel8', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.mypanel8',
    itemId: 'attrPanel',
    layout: {
        type: 'fit'
    },
    title: avmon.config.basicConfiguration,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'top',
                    items: [
                        {
                            xtype: 'button',
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
//                            x: 410,
                            x:360,
                            y: 70,
                            id: 'normalPushAmpConfigId',
                            itemId:'normalPushAmpConfigId',
                            text: avmon.config.issueByConfiguration,
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
            items: [
                {
                    xtype: 'gridpanel',
                    title: '',
                    store: 'AmpInstAttrStore',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'name',
                            text: avmon.config.parameterName,
                            width: 150
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'value',
                            text: avmon.config.value,
                            width: 500,
                            editor: {
                                xtype: 'textfield'
                            }
                        }
                    ],
                    viewConfig: {},
                    plugins: [
                        Ext.create('Ext.grid.plugin.CellEditing', {
                            ptype: 'cellediting'
                        })
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },

    onButtonClick: function(button, e, options) {
        var p = button.ownerCt.ownerCt.down("grid");
        if(p.activeEditor != null){  
            p.activeEditor.completeEdit();  
        } 
        var records=p.getStore().getUpdatedRecords();
        var attrs="";
        Ext.each(records,function(record){
            attrs+=record.get("name")+"="+record.get("value")+",";
        });
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        Ext.Ajax.request({
            url: 'saveAmpInstAttr',
            params: {agentId:agentId,ampInstId:ampInstId,attrs:attrs},
            success: function(response, opts) {
                Ext.Msg.alert(avmon.common.reminder,avmon.config.saveSuccess);
            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.common.reminder,avmon.config.saveFailure);
            }
        });
    },
    onButtonClick3: function(button, e, options) {
        var p = button.ownerCt.ownerCt.down("grid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        //组装json数据源
        var agentAmpInfo = "[{'agentId':'"+agentId+"','ampInstId':'"+ampInstId+"'}]";
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
                top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
            }
        });
    }
});