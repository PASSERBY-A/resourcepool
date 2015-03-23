Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',
    layout: {
        type: 'fit'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    autoScroll: true,
                    layout: {
                        type: 'absolute'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            x: 1110,
                            y: 10,
                            height: 190,
                            hidden: true,
                            id: 'testPanel',
                            width: 70,
                            layout: {
                                align: 'stretch',
                                type: 'vbox'
                            },
                            title: 'My Panel',
                            items: [
                                {
                                    xtype: 'button',
                                    height: 28,
                                    width: 64,
                                    text: 'init',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick11,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    height: 35,
                                    id: 'btnRefresh',
                                    width: 58,
                                    text: 'refresh'
                                },
                                {
                                    xtype: 'button',
                                    height: 28,
                                    id: 'btnTest2',
                                    width: 52,
                                    text: 'remove',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick1,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'button',
                                    height: 28,
                                    id: 'btnTest',
                                    width: 52,
                                    text: 'test'
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onButtonClick11: function(button, e, eOpts) {
        var series = [];
        alert(Ext.avmon.cpuCount);
        for (var i = 0; i < Ext.avmon.cpuCount; i++) {
            series.push({
                type : 'line',
                highlight: false,
                xField : 'time',
                yField : 'usage_cpu' + i,
                tips: {
                    trackMouse: true,
                    width: 65,
                    height: 40,
                    renderer: function(storeItem, item) {
                        var d = new Date();
                        d.setTime(storeItem.get('time'));
                        this.setTitle(Ext.Date.format(d, avmon.dashboard.dDayHHour));
                        this.update(storeItem.get('usage_cpu' + i) + 'm');
                    }
                },
                style: {
                    fill: '#38B8BF',
                    stroke: '#38B8BF',
                    'stroke-width': 3
                },
                markerConfig: {
                    type: 'circle',
                    size: 4,
                    radius: 4,
                    'stroke-width': 0,
                    fill: '#38B8BF',
                    stroke: '#38B8BF'
                }
            });
        }
        var chart = Ext.getCmp("cpuChart");

        chart.series = new Ext.util.MixedCollection(false, function(a) { return a.seriesId || (a.seriesId = Ext.id(null, 'ext-chart-series-')); });
        if (series) {
            chart.series.addAll(series);
        }
        chart.refresh();
    },
    onButtonClick1: function(button, e, eOpts) {
        var bcolor = ['black','red','green','blue','yellow','black','red','green','blue','yellow'];
        var chart=Ext.getCmp("cpuChart");
        var series=Ext.getCmp("cpuChart").series;
        series.get(1).showInLegend=false;
        chart.refresh();
    }
});