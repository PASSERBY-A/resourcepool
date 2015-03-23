Ext.define('MyApp.view.DeviceListWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.deviceListWindow',
    height: 482,
    width: 744,
    layout: {
        type: 'fit'
    },
    closeAction: 'hide',
    title: avmon.dashboard.deviceList,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            dockedItems: [
                {
                    xtype: 'panel',
                    dock: 'bottom',
                    border: 0,
                    frame: true,
                    height: 30,
                    padding: 3,
                    layout: {
                        align: 'stretch',
                        type: 'hbox'
                    },
                    title: '',
                    items: [
                        {
                            xtype: 'label',
                            flex: 1,
                            height: 0,
                            width: 500,
                            text: '          '
                        },
                        {
                            xtype: 'button',
                            height: 20,
                            width: 81,
                            text: avmon.common.close,
                            listeners: {
                                click: {
                                    fn: me.onButtonClick,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ],
            items: [
                {
                    xtype: 'panel',
                    border: 0,
                    frame: true,
                    itemId: 'deviceListPanel',
                    title: ''
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        button.up("window").hide();

    }

});