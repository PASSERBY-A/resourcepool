Ext.define('MyApp.view.MyViewport1', {
    extend: 'Ext.container.Viewport',
    requires: [
        'MyApp.view.PowerPanel'
    ],
    layout: {
        align: 'stretch',
        type: 'hbox'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'container',
                    flex: 1,
                    layout: {
                        align: 'stretch',
                        type: 'vbox'
                    },
                    items: [
                        {
                            xtype: 'form',
                            flex: 1,
                            height: 165,
                            id: 'basicInfo',
                            width: 260,
                            autoScroll: true,
                            bodyPadding: 10,
                            title: avmon.dashboard.generalInfomation,
                            items: [
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.config.system,
                                    labelWidth: 70,
                                    name: 'OS',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.batchdeploy.version,
                                    labelWidth: 70,
                                    name: 'OSVERSION',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.alarm.operationSystem,
                                    labelWidth: 70,
                                    name: 'BUSINESSSYSTEM',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.alarm.position,
                                    labelWidth: 70,
                                    name: 'POSITION',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.alarm.usage,
                                    labelWidth: 70,
                                    name: 'USAGE',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.alarm.administrator,
                                    labelWidth: 70,
                                    name: 'OWNER',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.dashboard.cpuModel,
                                    labelWidth: 70,
                                    name: 'CPUMODEL',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.dashboard.operateFrequency,
                                    labelWidth: 70,
                                    name: 'RUNRATE',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.dashboard.operateTime,
                                    labelWidth: 70,
                                    name: 'RUNTIME',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.dashboard.currentUsers,
                                    labelWidth: 70,
                                    name: 'USERCOUNT',
                                    value: 'loading...'
                                },
                                {
                                    xtype: 'displayfield',
                                    anchor: '100%',
                                    fieldLabel: avmon.dashboard.currentAlarmNumber,
                                    labelWidth: 70,
                                    name: 'ALARMCOUNT',
                                    value: 'loading...'
                                }
                            ]
                        },
                        {
                            xtype: 'gridpanel',
                            flex: 1,
                            id: 'newAlarmGrid',
                            title: avmon.dashboard.latestWarning,
                            store: 'Alarms',
                            viewConfig: {
                                loadMask: false
                            },
                            columns: [
                                {
                                    xtype: 'actioncolumn',
                                    width: 25,
                                    dataIndex: 'CURRENT_GRADE',
                                    items: [
                                        {
                                            getClass: function(v, metadata, r, rowIndex, colIndex, store) {
                                                var s= "icon-alarm-level0";
                                                var a=r.get("CURRENT_GRADE");
                                                if(a==1) s="icon-alarm-level1";
                                                if(a==2) s="icon-alarm-level2";
                                                if(a==3) s="icon-alarm-level3";
                                                if(a==4) s="icon-alarm-level4";
                                                return s;

                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    dataIndex: 'FIRST_OCCUR_TIME',
                                    text: avmon.config.time
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 130,
                                    dataIndex: 'TITLE',
                                    text: avmon.dashboard.content,
                                    flex: 1
                                }
                            ]
                        },
                        {
                            xtype: 'treepanel',
                            flex: 1,
                            id: 'networkGrid',
                            width: 540,
                            title: avmon.dashboard.networkPort,
                            store: 'Networks',
                            rootVisible: false,
                            viewConfig: {
                                loadMask: false
                            },
                            columns: [
                                {
                                    xtype: 'treecolumn',
                                    width: 80,
                                    defaultWidth: 80,
                                    dataIndex: 'NAME',
                                    text: avmon.dashboard.networkPort,
                                    flex: 1
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    width: 60,
                                    dataIndex: 'value',
                                    text: avmon.config.type
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    width: 65,
                                    dataIndex: 'value',
                                    text: avmon.dashboard.bandwidthAvailabilityRatio
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    width: 60,
                                    dataIndex: 'value',
                                    text: avmon.dashboard.sendingRate
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    width: 60,
                                    dataIndex: 'value',
                                    text: avmon.dashboard.receiveRate
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    dataIndex: 'LAN_OPKTS',
                                    text: avmon.dashboard.packetSendSec
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    dataIndex: 'LAN_IPKTS',
                                    text: avmon.dashboard.packetReceptionSec
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'container',
                    flex: 2,
                    layout: {
                        align: 'stretch',
                        type: 'vbox'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            flex: 1,
                            height: 165,
                            id: 'cpuPanel',
                            width: 540,
                            layout: {
                                type: 'fit'
                            },
                            title: 'CPU',
                            items: [
                                {
                                    xtype: 'chart',
                                    frame: false,
                                    id: 'cpuChart',
                                    insetPadding: 5,
                                    store: 'CpuLines',
                                    axes: [
                                        {
                                            type: 'Category',
                                            fields: [
                                                'time'
                                            ],
                                            label: {
                                                rotate: {
                                                    degrees: 0
                                                }
                                            },
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
                        },
                        {
                            xtype: 'panel',
                            flex: 1,
                            id: 'memPanel',
                            width: 540,
                            layout: {
                                align: 'stretch',
                                type: 'hbox'
                            },
                            title: avmon.dashboard.memory,
                            items: [
                                {
                                    xtype: 'chart',
                                    flex: 1,
                                    id: 'memChart1',
                                    animate: true,
                                    insetPadding: 5,
                                    store: 'Mems',
                                    axes: [
                                        {
                                            type: 'Category',
                                            fields: [
                                                'time'
                                            ],
                                            label: {
                                                rotate: {
                                                    degrees: 0
                                                }
                                            },
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
                                    flex: 1,
                                    id: 'memChart2',
                                    animate: true,
                                    insetPadding: 5,
                                    store: 'Swaps',
                                    axes: [
                                        {
                                            type: 'Category',
                                            fields: [
                                                'time'
                                            ],
                                            label: {
                                                rotate: {
                                                    degrees: 0
                                                }
                                            },
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
                                    dock: 'bottom',
                                    height: 30,
                                    hidden: true,
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
                                            hidden: true,
                                            fieldLabel: 'Page-In',
                                            labelWidth: 50,
                                            name: 'page_in',
                                            value: 'loading...'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            hidden: true,
                                            fieldLabel: 'Page-Out',
                                            labelWidth: 60,
                                            name: 'page_out',
                                            value: 'loading...'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            hidden: true,
                                            fieldLabel: avmon.dashboard.cacheReadHitRatio,
                                            labelWidth: 85,
                                            name: 'cache_read',
                                            value: 'loading...'
                                        },
                                        {
                                            xtype: 'displayfield',
                                            hidden: true,
                                            fieldLabel: avmon.dashboard.cacheWriteHitRatio,
                                            labelWidth: 85,
                                            name: 'cache_write',
                                            value: 'loading...'
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            xtype: 'gridpanel',
                            flex: 1,
                            id: 'keyProcessGrid',
                            title: avmon.dashboard.topnProcesses,
                            store: 'Processes',
                            viewConfig: {
                                loadMask: false
                            },
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    width: 200,
                                    dataIndex: 'NAME',
                                    text: avmon.dashboard.process,
                                    flex: 1
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    dataIndex: 'CPU',
                                    text: 'CPU'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    dataIndex: 'MEM',
                                    text: 'MEM'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    dataIndex: 'STATUS',
                                    text: avmon.config.status
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'container',
                    flex: 1,
                    layout: {
                        align: 'stretch',
                        type: 'vbox'
                    },
                    items: [
                        {
                            xtype: 'powerPanel'
                        },
                        {
                            xtype: 'gridpanel',
                            flex: 1,
                            height: 210,
                            id: 'diskIoGrid',
                            title: avmon.dashboard.diskIO,
                            store: 'Disks',
                            viewConfig: {
                                loadMask: false
                            },
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    dataIndex: 'DEVICE',
                                    text: 'Device',
                                    flex: 1
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 45,
                                    dataIndex: 'BUSY',
                                    text: 'Busy'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 45,
                                    dataIndex: 'AVQUE',
                                    text: 'Avque'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 45,
                                    dataIndex: 'RWPS',
                                    text: 'r+w/s'
                                }
                            ]
                        },
                        {
                            xtype: 'gridpanel',
                            flex: 1,
                            id: 'diskVolGrid',
                            title: avmon.dashboard.logicalVolume,
                            store: 'Lvs',
                            viewConfig: {
                                loadMask: false
                            },
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    width: 35,
                                    dataIndex: 'VG',
                                    text: 'Vg'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 100,
                                    dataIndex: 'LV',
                                    text: 'LV (Mount)',
                                    flex: 1
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    align: 'right',
                                    dataIndex: 'TOTAL',
                                    text: 'Total (KB)'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    align: 'right',
                                    dataIndex: 'USAGE',
                                    text: 'Usage'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    width: 40,
                                    dataIndex: 'FREE',
                                    text: 'Free'
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});