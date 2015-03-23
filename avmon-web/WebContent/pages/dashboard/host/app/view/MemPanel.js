Ext.define('MyApp.view.MemPanel', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.memPanel',
    height: 214,
    id: 'memPanel',
    width: 540,
    layout: {
        type: 'absolute'
    },
    title: avmon.dashboard.memory,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'chart',
                    x: -1,
                    y: -2,
                    height: 160,
                    id: 'memChart1',
                    width: 270,
                    animate: true,
                    insetPadding: 5,
                    store: 'Mems',
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
                            dashSize: 1,
                            position: 'left',
                            maximum: 100,
                            minimum: 0
                        }
                    ],
                    series: [
                        {
                            type: 'area',
                            highlight: true,
                            title: avmon.dashboard.userMemoryUsage,
                            xField: 'time',
                            yField: [
                                'mem_user',
                                'mem_sys',
                                'mem_free'
                            ]
                        }
                    ],
                    legend: {
                        itemSpacing: 5,
                        position: 'top'
                    }
                },
                {
                    xtype: 'chart',
                    x: 270,
                    y: 0,
                    height: 160,
                    id: 'memChart2',
                    width: 260,
                    animate: true,
                    insetPadding: 5,
                    store: 'Mems',
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
                            dashSize: 1,
                            position: 'left',
                            maximum: 100,
                            minimum: 0
                        }
                    ],
                    series: [
                        {
                            type: 'area',
                            highlight: true,
                            title: avmon.dashboard.swapUsageRate,
                            xField: 'time',
                            yField: [
                                'swap_usage',
                                'swap_free'
                            ]
                        }
                    ],
                    legend: {
                        itemSpacing: 5,
                        position: 'top'
                    }
                }
            ],
            dockedItems: [
                {
                    xtype: 'form',
                    x: -2,
                    y: 150,
                    dock: 'bottom',
                    height: 30,
                    id: 'memInfo',
                    width: 540,
                    layout: {
                        type: 'column'
                    },
                    bodyPadding: 5,
                    title: '',
                    items: [
                        {
                            xtype: 'displayfield',
                            fieldLabel: 'Page-In',
                            labelWidth: 50,
                            name: 'page_in',
                            value: 'loading...'
                        },
                        {
                            xtype: 'displayfield',
                            fieldLabel: 'Page-Out',
                            labelWidth: 60,
                            name: 'page_out',
                            value: 'loading...'
                        },
                        {
                            xtype: 'displayfield',
                            fieldLabel: avmon.dashboard.cacheReadHitRatio,
                            labelWidth: 85,
                            name: 'cache_read',
                            value: 'loading...'
                        },
                        {
                            xtype: 'displayfield',
                            fieldLabel: avmon.dashboard.cacheWriteHitRatio,
                            labelWidth: 85,
                            name: 'cache_write',
                            value: 'loading...'
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    }
});