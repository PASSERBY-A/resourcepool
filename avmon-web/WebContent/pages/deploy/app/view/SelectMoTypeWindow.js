Ext.define('app.view.SelectMoTypeWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.selectMoTypeWindow',

    height: 416,
    width: 402,
    layout: {
        type: 'fit'
    },
    closeAction: 'hide',
    title: avmon.deploy.addMonitoringObjects,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    frame: true,
                    height: 353,
                    width: 381,
                    layout: {
                        type: 'absolute'
                    },
                    bodyPadding: 10,
                    title: '',
                    items: [
                        {
                            xtype: 'label',
                            text: avmon.deploy.chooseObjectType
                        },
                        {
                            xtype: 'label',
                            x: 80,
                            y: 260,
                            text: avmon.deploy.objectNumberCannotBeModified
                        },
                        {
                            xtype: 'treepanel',
                            x: 10,
                            y: 30,
                            height: 200,
                            id: 'moTypeTree',
                            width: 360,
                            title: '',
                            store: 'MoTypeTrees',
                            rootVisible: false,
                            viewConfig: {

                            }
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            x: 10,
                            y: 240,
                            fieldLabel: avmon.deploy.objectNumber,
                            labelWidth: 60,
                            name: 'moId',
                            allowBlank: false
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            x: 10,
                            y: 280,
                            fieldLabel: avmon.deploy.objectName,
                            labelWidth: 60,
                            name: 'caption',
                            allowBlank: false
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            x: 10,
                            y: 310,
                            hidden: true,
                            fieldLabel: 'Agent ID',
                            labelWidth: 60,
                            name: 'agentId',
                            allowBlank: false,
                            emptyText: avmon.deploy.receivingPerformanceData
                        },
                        {
                            xtype: 'button',
                            x: 250,
                            y: 340,
                            id: 'btnSelectMoTypeWindowOk',
                            iconCls: 'icon-ok',
                            text: avmon.common.ok
                        },
                        {
                            xtype: 'button',
                            x: 310,
                            y: 340,
                            iconCls: 'icon-cancel',
                            text: avmon.common.cancel,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
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
        Ext.selectMoTypeWindow.hide();
    }

});