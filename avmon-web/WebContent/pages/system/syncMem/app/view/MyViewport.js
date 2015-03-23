Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',

    border: 0,
    layout: {
        type: 'fit'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    frame: true,
                    layout: {
                        type: 'auto'
                    },
                    bodyPadding: 30,
                    title: avmon.system.synchronousMemory,
                    items: [
                        {
                            xtype: 'label',
                            height: 50,
                            width: 588,
                            text: avmon.system.message
                        },
                        {
                            xtype: 'displayfield',
                            fieldLabel: ''
                        },
                        {
                            xtype: 'checkboxfield',
                            fieldLabel: '',
                            boxLabel: avmon.system.synchronousAlarmRuleIntoMemory,
                            checked: true
                        },
                        {
                            xtype: 'checkboxfield',
                            fieldLabel: '',
                            boxLabel: avmon.system.synchronousMonitoringObjectsIntoMemory,
                            checked: true
                        },
                        {
                            xtype: 'displayfield',
                            fieldLabel: ''
                        },
                        {
                            xtype: 'button',
                            height: 39,
                            width: 139,
                            text: avmon.system.synchronousMemory,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        },
                        {
                            xtype: 'button',
                            height: 39,
                            width: 139,
                            hidden:true,
                            text: "重启调度",
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
        button.disable();


        Ext.MessageBox.show({
            msg: avmon.system.synchronousing,
            progressText: 'Processing...',
            width:300,
            wait:true,
            waitConfig: {interval:200},
            icon:'icon-loading'
        });



        Ext.Ajax.request({
            url: '../../../deploy/syncMem',
            timeout:240000,
            params: {},
            success: function(response, opts) {
                button.enable();
                Ext.MessageBox.hide();
                var obj=Ext.decode(response.responseText);
                Ext.Msg.alert(avmon.system.updateMemory, obj.msg); 

            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.system.updateMemory, avmon.system.synchronousFail);
                Ext.MessageBox.hide();
                button.enable();
            }
        });
    },
    onButtonClick1: function(button, e, eOpts) {
        button.disable();
    	Ext.Ajax.request({
            url: '../../../deploy/restartAll',
            success: function(response, opts) {
            	alert("重启调度成功!!!")
            },
            failure: function(response, opts) {
                alert('error');
            }
        }); 

    }

});