Ext.define('MyApp.view.AmpAddWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.ampAddWindow',
    height: 206,
    id: 'ampAddWindow',
    width: 408,
    layout: {
        type: 'fit'
    },
    title: avmon.config.addAMP,
    modal: true,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    id: 'addAmpFormId',
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'combobox',
                            anchor: '100%',
                            name: 'ampId',
                            fieldLabel: avmon.config.ampType,
                            allowBlank: false,
                            editable: false,
                            displayField: 'name',
                            store: 'AmpTypeComBoxStore',
                            valueField: 'value',
                            listeners: {
                                select: {
                                    fn: me.onComboboxSelect,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            id: 'ampInstInputId1',
                            itemId: 'ampInstInputId1',
                            name: 'ampInstId',
                            fieldLabel: avmon.config.ampInstance,
                            allowBlank: false
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            id: 'ampNameInputId1',
                            itemId: 'ampNameInputId1',
                            name: 'ampName',
                            fieldLabel: avmon.config.ampName,
                            allowBlank: false
                        },
                        {
                            xtype: 'combobox',
                            anchor: '100%',
                            id: 'statusComboBox1',
                            name: 'status',
                            readOnly: false,
                            tabIndex: 1,
                            fieldLabel: avmon.config.whetherStart,
                            allowBlank: false,
                            editable: false,
                            displayField: 'name',
                            forceSelection: true,
                            queryMode: 'local',
                            store: 'StatusStoreList',
                            valueField: 'value'
                        },
                        {
                            xtype: 'container',
                            height: 43,
                            layout: {
                                type: 'absolute'
                            },
                            items: [
                                {
                                    xtype: 'button',
                                    x: 230,
                                    y: 20,
                                    width: 60,
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
                                    x: 310,
                                    y: 20,
                                    width: 60,
                                    text: avmon.common.close,
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
                            xtype: 'hiddenfield',
                            anchor: '100%',
                            id: 'hiddenAgentId',
                            name: 'agentId',
                            fieldLabel: 'Label'
                        },
                        {
                            xtype: 'hiddenfield',
                            anchor: '100%',
                            id: 'ampType',
                            name: 'ampType',
                            itemId:'ampType',
                            fieldLabel: 'Label'
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onComboboxSelect: function(combo, records, options) {
    	var form = combo.ownerCt;
        //设置agent实例
        form.down("#ampInstInputId1").setValue(records[0].data.value);
        //设置agent名称
        form.down("#ampNameInputId1").setValue(records[0].data.name);
        
        form.down("#ampType").setValue(records[0].data.type);
    },
    onButtonClick1: function(button, e, options) {
        var addAmpForm = Ext.getCmp("addAmpFormId").getForm();
        var agentIdHidden = Ext.getCmp("agentIdLable1").getValue();
        //设置Agent IDvalue
        Ext.getCmp("hiddenAgentId").setValue(agentIdHidden);
        //from 验证
        if(addAmpForm.isValid()){
            //提交请求
            addAmpForm.submit({
                clientValidation: true,
                method :"POST",
                waitTitle : avmon.config.waiting ,   
                waitMsg: avmon.config.dataSubmitting, 
                url: 'saveAgentAmp',
                success: function(form, action) {
                    var responseArray = Ext.JSON.decode(action.response.responseText);
                    Ext.MessageBox.show({
                        title: avmon.common.message,
                        msg: avmon.config.ampAddedSuccess,
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.MessageBox.INFO
                    });
                    //重新加载数据源
                    var gridProxy = Ext.getCmp("ampListGridId").getStore().getProxy();
                    gridProxy.extraParams.agentId = agentIdHidden;
                    Ext.getCmp("ampListGridId").getStore().load();
                    //关闭窗口
                    button.up('ampAddWindow').close();
                },
                failure: function(form, action) {
                	var returnJson = Ext.JSON.decode(action.response.responseText);
                    Ext.MessageBox.show({
                        title: avmon.config.wrong,
                        msg: returnJson.msg,
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.MessageBox.ERROR
                    });
                }
            });
        }else{
            Ext.MessageBox.show({
                title: avmon.config.wrong,
                msg: avmon.config.fillOutCompleteInformation,
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            });
        }
    },
    onButtonClick: function(button, e, options) {
        button.up('ampAddWindow').close();
    }
});