Ext.define('MyApp.view.searchWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.searchWindow',
    height: 311,
    id: 'searchWindow',
    width: 493,
    layout: {
        type: 'absolute'
    },
    title: 'My Window',
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'fieldset',
                    x: 10,
                    y: 0,
                    height: 120,
                    width: 460,
                    layout: {
                        type: 'absolute'
                    },
                    title: avmon.alarm.alarmInformation,
                    items: [
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            x: 0,
                            y: 0,
                            fieldLabel: avmon.config.name,
                            labelWidth: 80
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            x: 0,
                            y: 30,
                            width: 400,
                            fieldLabel: avmon.dashboard.content,
                            labelWidth: 80
                        },
                        {
                            xtype: 'combobox',
                            x: 0,
                            y: 60,
                            width: 200,
                            fieldLabel: avmon.alarm.level,
                            labelWidth: 80
                        },
                        {
                            xtype: 'combobox',
                            x: 240,
                            y: 60,
                            width: 200,
                            fieldLabel: avmon.config.status,
                            labelWidth: 80
                        }
                    ]
                },
                {
                    xtype: 'fieldset',
                    x: 10,
                    y: 120,
                    height: 100,
                    width: 460,
                    layout: {
                        type: 'absolute'
                    },
                    title: avmon.alarm.timeHorizon,
                    items: [
                        {
                            xtype: 'datefield',
                            width: 200,
                            fieldLabel: avmon.config.startTime,
                            labelWidth: 80
                        },
                        {
                            xtype: 'datefield',
                            x: 0,
                            y: 30,
                            width: 200,
                            fieldLabel: avmon.alarm.reach,
                            labelWidth: 80
                        },
                        {
                            xtype: 'numberfield',
                            x: 200,
                            y: 0,
                            width: 50
                        },
                        {
                            xtype: 'numberfield',
                            x: 200,
                            y: 30,
                            width: 50
                        },
                        {
                            xtype: 'label',
                            x: 250,
                            y: 10,
                            text: avmon.alarm.hour
                        },
                        {
                            xtype: 'label',
                            x: 250,
                            y: 40,
                            text: avmon.alarm.hour
                        },
                        {
                            xtype: 'label',
                            x: 310,
                            y: 10,
                            text: avmon.alarm.minute
                        },
                        {
                            xtype: 'label',
                            x: 310,
                            y: 40,
                            height: 10,
                            text: avmon.alarm.minute
                        },
                        {
                            xtype: 'numberfield',
                            x: 260,
                            y: 0,
                            width: 50
                        },
                        {
                            xtype: 'numberfield',
                            x: 260,
                            y: 30,
                            width: 50
                        }
                    ]
                },
                {
                    xtype: 'button',
                    x: 100,
                    y: 240,
                    width: 80,
                    text: avmon.config.search
                },
                {
                    xtype: 'button',
                    x: 270,
                    y: 240,
                    width: 80,
                    text: avmon.common.reset
                }
            ]
        });
        me.callParent(arguments);
    }
});