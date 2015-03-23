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
                    xtype: 'tabpanel',
                    activeTab: 0,
                    items: [
                        {
                            xtype: 'panel',
                            id: 'boardTab',
                            width: 812,
                            autoScroll: true,
                            layout: {
                                align: 'stretch',
                                type: 'hbox'
                            },
                            title: avmon.dashboard.dashboard,
                            tabConfig: {
                                xtype: 'tab',
                                listeners: {
                                    click: {
                                        fn: me.onTabClick3,
                                        scope: me
                                    }
                                }
                            },
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
                                            height: 160,
                                            id: 'basicInfo',
                                            width: 245,
                                            bodyPadding: 5,
                                            title: avmon.dashboard.generalInfomation,
                                            items: [
                                                {
                                                    xtype: 'displayfield',
                                                    width: 230,
                                                    name: 'OS',
                                                    value: 'loading...',
                                                    fieldLabel: avmon.dashboard.manufacturer,
                                                    labelWidth: 70
                                                },
                                                {
                                                    xtype: 'displayfield',
                                                    width: 230,
                                                    name: 'BUSINESSSYSTEM',
                                                    value: 'loading...',
                                                    fieldLabel: avmon.config.devicename,
                                                    labelWidth: 70
                                                },
                                                {
                                                    xtype: 'displayfield',
                                                    width: 230,
                                                    name: 'USAGE',
                                                    value: 'loading...',
                                                    fieldLabel: avmon.alarm.usage,
                                                    labelWidth: 70
                                                },
                                                {
                                                    xtype: 'displayfield',
                                                    width: 230,
                                                    name: 'ALARMCOUNT',
                                                    value: 'loading...',
                                                    fieldLabel: avmon.dashboard.currentAlarmNumber,
                                                    labelWidth: 70
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'panel',
                                            flex: 1,
                                            width: 245,
                                            layout: {
                                                align: 'stretch',
                                                type: 'vbox'
                                            },
                                            title: avmon.dashboard.latestWarning,
                                            items: [
                                                {
                                                    xtype: 'gridpanel',
                                                    flex: 1,
                                                    id: 'newAlarmGrid',
                                                    autoScroll: true,
                                                    store: 'alarmStore',
                                                    viewConfig: {

                                                    },
                                                    columns: [
                                                        {
                                                            xtype: 'actioncolumn',
                                                            getClass: function(v, metadata, r, rowIndex, colIndex, store) {
                                                                var s= "icon-alarm-level0";
                                                                var a=r.get("CURRENT_GRADE");
                                                                if(a==1) s="icon-alarm-level1";
                                                                if(a==2) s="icon-alarm-level2";
                                                                if(a==3) s="icon-alarm-level3";
                                                                if(a==4) s="icon-alarm-level4";
                                                                return s;

                                                            },
                                                            width: 30,
                                                            align: 'center',
                                                            dataIndex: 'CURRENT_GRADE'
                                                        },
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'FIRST_OCCUR_TIME',
                                                            text: avmon.config.time
                                                        },
                                                        {
                                                            xtype: 'gridcolumn',
                                                            width: 150,
                                                            align: 'center',
                                                            dataIndex: 'TITLE',
                                                            text: avmon.dashboard.content
                                                        }
                                                    ]
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
                                            height: 160,
                                            layout: {
                                                align: 'stretch',
                                                type: 'vbox'
                                            },
                                            title: 'CPU',
                                            items: [
                                                {
                                                    xtype: 'chart',
                                                    height: 135,
                                                    id: 'cpuChart',
                                                    width: 535,
                                                    animate: true,
                                                    store: 'cpuStore',
                                                    axes: [
                                                        {
                                                            type: 'Category',
                                                            dashSize: 1,
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
                                                        padding: 2,
                                                        position: 'top'
                                                    },
                                                    series: [
                                                        {
                                                            type: 'area',
                                                            title: [
                                                                avmon.dashboard.cpuUsageRate,
                                                                avmon.dashboard.cpuVacancyRage
                                                            ],
                                                            xField: 'time',
                                                            yField: [
                                                                'usage1',
                                                                'usage2'
                                                            ]
                                                        }
                                                    ]
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'panel',
                                            flex: 1,
                                            layout: {
                                                align: 'stretch',
                                                type: 'hbox'
                                            },
                                            items: [
                                                {
                                                    xtype: 'chart',
                                                    flex: 1,
                                                    height: 250,
                                                    id: 'iops_chart1',
                                                    animate: true,
                                                    insetPadding: 22,
                                                    store: 'iopsStore',
                                                    axes: [
                                                        {
                                                            position: 'gauge',
                                                            type: 'Gauge',
                                                            margin: 8,
                                                            maximum: 100,
                                                            minimum: 0
                                                        }
                                                    ],
                                                    series: [
                                                        {
                                                            type: 'gauge',
                                                            angleField: 'XP_CLP_MS',
                                                            donut: 50
                                                        }
                                                    ]
                                                },
                                                {
                                                    xtype: 'chart',
                                                    flex: 1,
                                                    id: 'iops_chart2',
                                                    animate: true,
                                                    insetPadding: 22,
                                                    store: 'iopsStore',
                                                    theme: 'Red',
                                                    axes: [
                                                        {
                                                            position: 'gauge',
                                                            type: 'Gauge',
                                                            margin: 8,
                                                            maximum: 100,
                                                            minimum: 0
                                                        }
                                                    ],
                                                    series: [
                                                        {
                                                            type: 'gauge',
                                                            angleField: 'XP_CLP_CMUS',
                                                            donut: 50
                                                        }
                                                    ]
                                                },
                                                {
                                                    xtype: 'chart',
                                                    flex: 1,
                                                    id: 'iops_chart3',
                                                    animate: true,
                                                    insetPadding: 22,
                                                    store: 'iopsStore',
                                                    theme: 'Green',
                                                    axes: [
                                                        {
                                                            position: 'gauge',
                                                            type: 'Gauge',
                                                            margin: 8,
                                                            maximum: 100,
                                                            minimum: 0
                                                        }
                                                    ],
                                                    series: [
                                                        {
                                                            type: 'gauge',
                                                            angleField: 'XP_CLP_CMWR',
                                                            donut: 50
                                                        }
                                                    ]
                                                }
                                            ],
                                            dockedItems: [
                                                {
                                                    xtype: 'panel',
                                                    flex: 1,
                                                    dock: 'bottom',
                                                    height: 20,
                                                    layout: {
                                                        align: 'stretch',
                                                        type: 'hbox'
                                                    },
                                                    items: [
                                                        {
                                                            xtype: 'label',
                                                            flex: 1,
                                                            style: 'text-align: center;',
                                                            text: avmon.dashboard.delayRate
                                                        },
                                                        {
                                                            xtype: 'label',
                                                            flex: 1,
                                                            style: 'text-align: center;',
                                                            text: avmon.dashboard.cacheUsageRate
                                                        },
                                                        {
                                                            xtype: 'label',
                                                            flex: 1,
                                                            style: 'text-align: center;',
                                                            text: avmon.dashboard.writeDelayUtilization
                                                        }
                                                    ]
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'panel',
                                            flex: 1,
                                            height: 170,
                                            layout: {
                                                align: 'stretch',
                                                type: 'vbox'
                                            },
                                            title: avmon.dashboard.netPortInfomation,
                                            items: [
                                                {
                                                    xtype: 'gridpanel',
                                                    flex: 1,
                                                    id: 'networkGrid',
                                                    autoScroll: true,
                                                    store: 'networkStore',
                                                    viewConfig: {

                                                    },
                                                    columns: [
                                                        {
                                                            xtype: 'gridcolumn',
                                                            width: 200,
                                                            align: 'center',
                                                            dataIndex: 'PATH',
                                                            text: avmon.dashboard.frontPort
                                                        },
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'XP_CP_R_MS',
                                                            text: avmon.dashboard.delayRead
                                                        },
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'XP_CP_W_MS',
                                                            text: avmon.dashboard.delayWrite
                                                        },
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'XP_CP_IOPS',
                                                            text: avmon.dashboard.rate
                                                        }
                                                    ]
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
                                            xtype: 'panel',
                                            height: 160,
                                            layout: {
                                                type: 'absolute'
                                            },
                                            title: avmon.dashboard.systemStatus,
                                            items: [
                                                {
                                                    xtype: 'image',
                                                    x: 30,
                                                    y: 5,
                                                    height: 60,
                                                    width: 60,
                                                    src: './image/pw_good.png'
                                                },
                                                {
                                                    xtype: 'image',
                                                    x: 30,
                                                    y: 70,
                                                    height: 60,
                                                    width: 60,
                                                    src: './image/fan_good.png'
                                                },
                                                {
                                                    xtype: 'label',
                                                    x: 110,
                                                    y: 20,
                                                    text: avmon.dashboard.powerStatusUnknown
                                                },
                                                {
                                                    xtype: 'label',
                                                    x: 110,
                                                    y: 85,
                                                    text: avmon.dashboard.fanStatusNuknown
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'panel',
                                            flex: 1,
                                            layout: {
                                                align: 'stretch',
                                                type: 'vbox'
                                            },
                                            title: avmon.dashboard.groupInfomation,
                                            items: [
                                                {
                                                    xtype: 'gridpanel',
                                                    flex: 1,
                                                    id: 'groupInfoGrid',
                                                    store: 'groupStore',
                                                    viewConfig: {

                                                    },
                                                    columns: [
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'PATH',
                                                            text: avmon.dashboard.groupName
                                                        },
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'XP_RG_IOPS',
                                                            text: avmon.dashboard.groupIOPS
                                                        },
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'XP_RG_MS',
                                                            text: avmon.dashboard.groupDelayRate
                                                        }
                                                    ]
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'panel',
                                            flex: 1,
                                            layout: {
                                                align: 'stretch',
                                                type: 'vbox'
                                            },
                                            title: avmon.dashboard.diskInformation,
                                            items: [
                                                {
                                                    xtype: 'gridpanel',
                                                    flex: 1,
                                                    id: 'diskInfoGrid',
                                                    store: 'diskStore',
                                                    viewConfig: {

                                                    },
                                                    columns: [
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'PATH',
                                                            text: avmon.dashboard.diskName
                                                        },
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'XP_DEV_IOPS',
                                                            text: avmon.dashboard.diskIOPS
                                                        },
                                                        {
                                                            xtype: 'gridcolumn',
                                                            align: 'center',
                                                            dataIndex: 'XP_DEV_MS',
                                                            text: avmon.dashboard.diskDelayRate
                                                        }
                                                    ]
                                                }
                                            ]
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            height: 260,
                            id: 'groupTab',
                            layout: {
                                type: 'fit'
                            },
                            title: avmon.dashboard.groupInfomation,
                            tabConfig: {
                                xtype: 'tab',
                                listeners: {
                                    click: {
                                        fn: me.onTabClick,
                                        scope: me
                                    }
                                }
                            },
                            items: [
                                {
                                    xtype: 'panel',
                                    id: 'groupViewPanel',
                                    autoScroll: true,
                                    layout: {
                                        type: 'fit'
                                    },
                                    items: [
                                        {
                                            xtype: 'dataview',
                                            id: 'groupView',
                                            autoScroll: true,
                                            itemSelector: 'div.thumb-wrap',
                                            itemTpl: [
                                                '<div class="thumb-wrap " >',
                                                '    <tpl for=".">',
                                                '        <div class="resourceStatusBox" >',
                                                '            <span style="font-size:10px;color:blue;overflow:hidden;">',
                                                '                 {PATH}',
                                                '            </span>',
                                                '    ',
                                                '            <div >',
                                                '                <div class="BarText" >'+ avmon.dashboard.delayRead +':</div>',
                                                '                <div class="ProgressBar">  ',
                                                '                    <div style="width:100%;"><span>{XP_RG_IOPS}</span></div>  ',
                                                '                </div>   ',
                                                '            </div>',
                                                '            ',
                                                '            <div >',
                                                '                <div class="BarText" >'+ avmon.dashboard.delayWrite +':</div>',
                                                '                <div class="ProgressBar">  ',
                                                '                    <div style="width:100%;"><span>{XP_RG_MS}</span></div>  ',
                                                '                </div>   ',
                                                '            </div>',
                                                '        </div>',
                                                '    </tpl>',
                                                '</div>'
                                            ],
                                            loadMask: false,
                                            overItemCls: 'x-view-over',
                                            singleSelect: true,
                                            store: 'groupViewStore'
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            id: 'portTab',
                            layout: {
                                type: 'fit'
                            },
                            title: avmon.dashboard.frontPort,
                            tabConfig: {
                                xtype: 'tab',
                                listeners: {
                                    click: {
                                        fn: me.onTabClick1,
                                        scope: me
                                    }
                                }
                            },
                            items: [
                                {
                                    xtype: 'panel',
                                    id: 'portViewPanel',
                                    layout: {
                                        type: 'fit'
                                    },
                                    items: [
                                        {
                                            xtype: 'dataview',
                                            id: 'portView',
                                            autoScroll: true,
                                            itemSelector: 'div.thumb-wrap',
                                            itemTpl: [
                                                '<div class="thumb-wrap " >',
                                                '    <tpl for=".">',
                                                '        <div class="resourceStatusBox" >',
                                                '            <span style="font-size:10px;color:blue;overflow:hidden;">',
                                                '                 {PATH}',
                                                '            </span>',
                                                '    ',
                                                '            <div >',
                                                '                <div class="BarText" >'+ avmon.dashboard.rate +':</div>',
                                                '                <div class="ProgressBar">  ',
                                                '                    <div style="width:100%;"><span>{XP_CP_IOPS}</span></div>  ',
                                                '                </div>   ',
                                                '            </div>',
                                                '            ',
                                                '            <div >',
                                                '                <div class="BarText" >'+ avmon.dashboard.delayRead +':</div>',
                                                '                <div class="ProgressBar">  ',
                                                '                    <div style="width:100%;"><span>{XP_CP_R_MS}</span></div>  ',
                                                '                </div>   ',
                                                '            </div>',
                                                '            ',
                                                '            <div >',
                                                '                <div class="BarText" >'+ avmon.dashboard.delayWrite +':</div>',
                                                '                <div class="ProgressBar">  ',
                                                '                    <div style="width:100%;"><span>{XP_CP_W_MS}</span></div>  ',
                                                '                </div>   ',
                                                '            </div>',
                                                '        </div>',
                                                '    </tpl>',
                                                '</div>'
                                            ],
                                            loadMask: false,
                                            overItemCls: 'x-view-over',
                                            singleSelect: true,
                                            store: 'portViewStore'
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            id: 'diskTab',
                            layout: {
                                type: 'fit'
                            },
                            title: avmon.dashboard.diskInformation,
                            tabConfig: {
                                xtype: 'tab',
                                listeners: {
                                    click: {
                                        fn: me.onTabClick2,
                                        scope: me
                                    }
                                }
                            },
                            items: [
                                {
                                    xtype: 'panel',
                                    id: 'diskViewPanel',
                                    layout: {
                                        type: 'fit'
                                    },
                                    items: [
                                        {
                                            xtype: 'dataview',
                                            id: 'diskView',
                                            autoScroll: true,
                                            itemSelector: 'div.thumb-wrap',
                                            itemTpl: [
                                                '<div class="thumb-wrap " >',
                                                '    <tpl for=".">',
                                                '        <div class="resourceStatusBox" >',
                                                '            <span style="font-size:10px;color:blue;overflow:hidden;">',
                                                '                 {PATH}',
                                                '            </span>',
                                                '    ',
                                                '            <div >',
                                                '                <div class="BarText" >'+ avmon.dashboard.delayRead +':</div>',
                                                '                <div class="ProgressBar">  ',
                                                '                    <div style="width:100%;"><span>{XP_DEV_IOPS}</span></div>  ',
                                                '                </div>   ',
                                                '            </div>',
                                                '            ',
                                                '            <div >',
                                                '                <div class="BarText" >'+ avmon.dashboard.delayWrite +':</div>',
                                                '                <div class="ProgressBar">  ',
                                                '                    <div style="width:100%;"><span>{XP_DEV_MS}</span></div>  ',
                                                '                </div>   ',
                                                '            </div>',
                                                '        </div>',
                                                '    </tpl>',
                                                '</div>'
                                            ],
                                            loadMask: false,
                                            overItemCls: 'x-view-over',
                                            singleSelect: true,
                                            store: 'diskViewStore'
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onTabClick3: function(button, e, options) {
        Ext.store.currentTab = 1;

        Ext.store.application.loadBasicInfo();
        Ext.store.application.loadCpu();
        Ext.store.application.loadNewAlarm();
        Ext.store.application.loadIops();
        //Ext.store.application.loadDelay();
        Ext.store.application.loadNetWork();
        Ext.store.application.loadGroupInfo();
        Ext.store.application.loadDiskInfo();
    },

    onTabClick: function(button, e, options) {
        Ext.store.currentTab = 2;

        var groupView = Ext.getCmp('groupView');
        var groupViewStoreProxy = groupView.getStore().getProxy();
        groupViewStoreProxy.extraParams.mo = Ext.store.moId;

        groupView.getStore().load();

    },

    onTabClick1: function(button, e, options) {
        Ext.store.currentTab = 3;

        var portView = Ext.getCmp('portView');
        var portViewStoreProxy = portView.getStore().getProxy();
        portViewStoreProxy.extraParams.mo = Ext.store.moId;

        portView.getStore().load();

    },

    onTabClick2: function(button, e, options) {
        Ext.store.currentTab = 4;

        var diskView = Ext.getCmp('diskView');
        var diskViewStoreProxy = diskView.getStore().getProxy();
        diskViewStoreProxy.extraParams.mo = Ext.store.moId;

        diskView.getStore().load();

    }

});