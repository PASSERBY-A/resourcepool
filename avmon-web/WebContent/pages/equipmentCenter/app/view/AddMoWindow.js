Ext.define('app.view.AddMoWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.addMoWindow',

    height: 225,
    width: 354,
    layout: {
        type: 'fit'
    },
    closeAction: 'hide',
    title: avmon.equipmentCenter.addMonitorHost,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    bodyPadding: 10,
                    title: '',
                    items: [
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            fieldLabel: avmon.equipmentCenter.monitoringObjectNumber,
                            name: 'id'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            fieldLabel: avmon.equipmentCenter.hostName,
                            name: 'text'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            fieldLabel: avmon.equipmentCenter.objectType,
                            name: 'type'
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            fieldLabel: avmon.equipmentCenter.ipaddress,
                            name: 'ip'
                        },
                        {
                            xtype: 'combobox',
                            anchor: '100%',
                            fieldLabel: avmon.equipmentCenter.operatingSystem,
                            name: 'os',
                            displayField: 'name',
                            queryMode: 'local',
                            store: 'HostTypeList',
                            valueField: 'name'
                        }
                    ]
                }
            ],
            dockedItems: [
                {
                    xtype: 'toolbar',
                    dock: 'bottom',
                    items: [
                        {
                            xtype: 'button',
                            id: 'btnAddHostOk',
                            iconCls: 'icon-ok',
                            text: avmon.common.ok
                        },
                        {
                            xtype: 'button',
                            id: 'btnAddHostCancel',
                            iconCls: 'icon-cancel',
                            text: avmon.common.cancel
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});