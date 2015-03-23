Ext.define('MyApp.view.CpuPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.cpuPanel',
    height: 242,
    id: 'cpuPanel',
    width: 540,
    layout: {
        type: 'absolute'
    },
    title: avmon.dashboard.cpu,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'chart',
                    x: 0,
                    y: -1,
                    frame: false,
                    height: 180,
                    id: 'cpuChart',
                    width: 540,
                    insetPadding: 5,
                    store: 'CpuLines',
                    axes: [
                        {
                            type: 'Category',
                            fields: [
                                'time'
                            ],
                            position: 'bottom'
                        },
                        {
                            type: 'Numeric',
                            position: 'left',
                            maximum: 100,
                            minimum: 0
                        }
                    ],
                    legend: {
                        boxStroke: '#FFF',
                        itemSpacing: 0,
                        padding: 2,
                        position: 'right'
                    },
                    series: [
                        {
                            type: 'line',
                            showInLegend: false,
                            xField: 'time',
                            yField: [
                                'usage'
                            ],
                            fill: true
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    }
});