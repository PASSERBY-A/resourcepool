Ext.define('main.view.MainFrame', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.MainFrame',

    id: 'mainFrame',
    layout: {
        type: 'border'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    region: 'north',
                    border: 0,
                    height: 98,
                    id: 'topPanel',
                    layout: {
                        type: 'fit'
                    },
                    bodyBorder: false,
                    items: [
                        {
                            xtype: 'panel',
                            border: 0,
                            height: 70,
                            id: 'mainMenuPanel',
                            autoScroll: false,
                            layout: {
                                type: 'absolute'
                            },
                            bodyBorder: false,
                            bodyStyle: 'background:url(\''+AVMON_GLOBAL_HEADER_IMAGE+'\')',
                            items: [
                                {
                                    xtype: 'container',
                                    x: 860,
                                    y: 50,
                                    height: 20,
                                    html: avmon.modules.logout,
                                    id: 'loginInfoPanel',
                                    width: 330
                                }
                            ]
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'panel',
                            dock: 'bottom',
                            border: 0,
                            height: 28,
                            id: 'subMenuPanel',
                            layout: {
                                type: 'column'
                            },
                            bodyStyle: 'background:url(\'main/images/menu_back.png\')',
                            items: [
                                {
                                    xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu1',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu1_hidden,
                                    text: avmon.modules.alarmMonitoring
                                },
                                {
                                    xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu2',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu2_hidden,
                                    text: avmon.modules.realtimePerformance
                                },
                                {
                                    xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu7',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu7_hidden,
                                    text: avmon.modules.analysisPerformance
                                },
                                {
                                    xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu8',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu8_hidden,
                                    text: avmon.modules.realTime
                                },
                                {
                                    xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu3',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu3_hidden,
                                    text: avmon.modules.queryAndReport
                                },
                                //add by mark start
                                {
                                	xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu10',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu10_hidden,
                                    text: avmon.modules.resourceManagement
                                },
                                //--add by mark end
                                {
                                    xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu4',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu4_hidden,
                                    text: avmon.modules.configurationManagement
                                },
                                {
                                    xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu5',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu5_hidden,
                                    text: avmon.modules.deploymentManagement
                                },
                                {
                                    xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu6',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu6_hidden,
                                    text: avmon.modules.systemManagement
                                },
                                {
                                    xtype: 'label',
                                    baseCls: 'button2-back',
                                    height: 28,
                                    id: 'mainFrame_labMenu9',
                                    overCls: 'button2-over',
                                    width: 113,
                                    hidden:mainFrame_labMenu9_hidden,
                                    text: avmon.modules.networkInspectionConfig
                                }
                                // add by mark end
                            ]
                        }
                    ],
                    listeners: {
                        afterlayout: {
                            fn: me.onTopPanelAfterLayout,
                            scope: me
                        }
                    }
                },
                {
                    xtype: 'container',
                    region: 'center',
                    border: 0,
                    id: 'mainFrame_TabPanel',
                    layout: {
                        type: 'card'
                    },
                    items: [
                        {
                            xtype: 'container',
                            html: 'loading...',
                            id: 'tabAlarm'
                        },{
                            xtype: 'container',
                            html: 'loading...',
                            id: 'tabSim'
                        },
                        {
                            xtype: 'panel',
                            layout: {
                                type: 'fit'
                            },
                            items: [
                                    {
                                        xtype: 'panel',
                                        layout: {
                                            type: 'border'
                                        },
                                        title: '',
                                        items: [
                                            {
                                                xtype: 'treepanel',
                                                region: 'west',
                                                split: true,
                                                itemId: 'performanceTree',
                                                width: 250,
                                                collapsible: true,
                                                title: 'Menu',
                                                store: Ext.create('main.store.PerformanceMenus'),
                                                rootVisible: false,
                                                viewConfig: {

                                                },
                                                listeners: {
                                                    select: {
                                                        fn: me.onPerformanceTreeSelect,
                                                        scope: me
                                                    }
                                                }
                                            },
                                            {
                                                xtype: 'panel',
                                                region: 'center',
                                                id: 'performanceContent',
                                                title: ''
                                            }
                                        ]
                                    }
                                ],
                            //html: 'loading...',
                            id: 'tabPerformance'
                        },
                        {
                            xtype: 'panel',
                            id: 'tabConfig',
                            layout: {
                                type: 'fit'
                            }
                        },
                        {
                            xtype: 'panel',
                            id: 'tabDeploy',
                            layout: {
                                type: 'fit'
                            }
                        },
                        {
                            xtype: 'panel',
                            id: 'tabSystem',
                            layout: {
                                type: 'fit'
                            }
                        },
                        {
                            xtype: 'panel',
                            id: 'tabReport',
                            layout: {
                                type: 'fit'
                            }
                        },
                        {
                            xtype: 'panel',
                            id: 'tabKpiPerformance',
                            layout: {
                                type: 'fit'
                            }
                        },
                        {
                        	xtype: 'panel',
                            id: 'tabPolling',
                            layout: {
                                type: 'fit'
                            }
                        },
                        //add by mark start
                        {
                            xtype: 'panel',
                            html: 'loading...',
                            id: 'tabEquipmentCenter'
                        },
//                        {
//                            xtype: 'panel',
//                            html: 'loading...',
//                            id: 'tabClassMgr'
//                        },
                        //--add by mark end
                        

                        
                        {
                            xtype: 'toolbar'
                        }
                    ]
                }
            ],
            listeners: {
                afterrender: {
                    fn: me.onMainFrameAfterRender,
                    scope: me
                }
            }
        });

        me.callParent(arguments);
    },

    onTopPanelAfterLayout: function(abstractcontainer, layout, options) {
        Ext.getCmp("loginInfoPanel").setPosition(Ext.getCmp("topPanel").getWidth()-300,50);
    },

    onMenuTreeSelect1: function(selModel, record, index, options) {//配置管理

        Ext.getCmp('configContent').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+record.get("url")+'"></iframe>');

    },

    onSystemMenuTreeSelect: function(selModel, record, index, options) {//系统管理

        Ext.getCmp('systemContent').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+record.get("url")+'"></iframe>');

    },
    
    onPollingTreeSelect: function(selModel, record, index, options) {//alert('onPollingTreeSelect');

        Ext.getCmp('pollingContent').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+record.get("url")+'"></iframe>');

    },

    onMenuTreeSelect11: function(selModel, record, index, options) {//查询与报表

        Ext.getCmp('reportContent').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+record.get("url")+'"></iframe>');

    },

    onMainFrameAfterRender: function(abstractcomponent, options) {

        Ext.getCmp('loginInfoPanel').update(avmon.modules.currentUser+ Ext.avmon.loginUserName+ '&nbsp;&nbsp;<a href=../logout>['+ avmon.modules.logout +']</a>&nbsp;&nbsp;<a href="#" onclick="Ext.modifyPwd()">['+ avmon.modules.changePassword +']</a>');



        //register label-menu click event
        Ext.each(Ext.getCmp("mainMenuPanel").query("label"),function(item){

            Ext.fly(item.el).on('click',Ext.avmon.mainMenuClick);

        });

        Ext.each(Ext.getCmp("subMenuPanel").query("label"),function(item){

            Ext.fly(item.el).on('click',Ext.avmon.subMenuClick);

        });


        Ext.avmon.mainMenuClick(null,{id:"labelMonitorCenter"});

    }

});