Ext.define('MyApp.view.PowerPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.powerPanel',
    height: 165,
    id: 'powerPanel',
    layout: {
        type: 'absolute'
    },
    title: avmon.dashboard.powerSupplyFan,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'image',
                    x: 30,
                    y: 5,
                    height: 60,
                    width: 60,
                    src: './imgs/pw_good.png'
                },
                {
                    xtype: 'image',
                    x: 30,
                    y: 70,
                    height: 60,
                    width: 60,
                    src: './imgs/fan_good.png'
                },
                {
                    xtype: 'label',
                    x: 110,
                    y: 20,
                    height: 40,
                    width: 120,
                    text: avmon.dashboard.powerSupplyConditionNormal
                },
                {
                    xtype: 'label',
                    x: 110,
                    y: 80,
                    height: 30,
                    text: avmon.dashboard.fanConditionNormal
                }
            ]
        });
        me.callParent(arguments);
    }
});