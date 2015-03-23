Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',
    layout: {
        align: 'stretch',
        type: 'vbox'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    flex: 1,
                    layout: {
                        type: 'border'
                    },
                    title: '',
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'label',
                                    id: 'alarmNodeId',
                                    padding: '0 0 0 10',
                                    style: 'font-weight:bold;',
                                    width: 200,
                                    text: 'XXX'
                                },
                                {
                                    xtype: 'tbspacer',
                                    width: 70
                                },
                                {
                                    xtype: 'buttongroup',
                                    hidden: true,
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            id: 'ckHistory',
                                            enableToggle: true,
                                            icon: 'images/hist_alarm_a.png',
                                            text: avmon.alarm.alreadyClearAlarm,
                                            listeners: {
                                                click: {
                                                    fn: me.onCkHistoryClick,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'tbspacer',
                                    width: 20
                                },
                                {
                                    xtype: 'buttongroup',
                                    hidden: true,
                                    columns: 5,
                                    items: [
                                        {
                                            xtype: 'button',
                                            id: 'level0',
                                            enableToggle: true,
                                            icon: 'images/level_0.png',
                                            pressed: true,
                                            text: '0',
                                            listeners: {
                                                click: {
                                                    fn: me.onLevel0Click,
                                                    scope: me
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'button',
                                            id: 'level1',
                                            enableToggle: true,
                                            icon: 'images/level_1.png',
                                            pressed: true,
                                            text: '0',
                                            listeners: {
                                                click: {
                                                    fn: me.onLevel1Click,
                                                    scope: me
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'button',
                                            id: 'level2',
                                            enableToggle: true,
                                            icon: 'images/level_2.png',
                                            pressed: true,
                                            text: '0',
                                            listeners: {
                                                click: {
                                                    fn: me.onLevel2Click,
                                                    scope: me
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'button',
                                            id: 'level3',
                                            enableToggle: true,
                                            icon: 'images/level_3.png',
                                            pressed: true,
                                            text: '0',
                                            listeners: {
                                                click: {
                                                    fn: me.onLevel3Click,
                                                    scope: me
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'button',
                                            id: 'level4',
                                            enableToggle: true,
                                            icon: 'images/level_4.png',
                                            pressed: true,
                                            text: '0',
                                            listeners: {
                                                click: {
                                                    fn: me.onLevel4Click,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'tbspacer',
                                    width: 20
                                },
                                {
                                    xtype: 'buttongroup',
                                    hidden: true,
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            id: 'myalarm',
                                            enableToggle: true,
                                            icon: 'images/myalarm_a.png',
                                            text: avmon.alarm.myAlarm0,
                                            listeners: {
                                                click: {
                                                    fn: me.onMyalarmClick,
                                                    scope: me
                                                }
                                            }
                                        },
                                        {
                                            xtype: 'button',
                                            id: 'newalarm',
                                            enableToggle: true,
                                            icon: 'images/newalarm_a.png',
                                            text: avmon.alarm.newAlarm0,
                                            listeners: {
                                                click: {
                                                    fn: me.onNewalarmClick,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'tbspacer',
                                    width: 50
                                },
                                {
                                    xtype: 'buttongroup',
                                    hidden: true,
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            iconCls: 'icon-search',
                                            text: avmon.alarm.advancedSearch,
                                            listeners: {
                                                click: {
                                                    fn: me.onButtonClick,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    ],
                    items: [
                        {
                            xtype: 'gridpanel',
                            region: 'center',
                            id: 'alarmGrid',
                            store: 'AlarmStore',
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                        var str="unknown.gif";


                                        if (value != null && value != ''){
                                            str="level_" + value + ".png";
                                        }

                                        return "<img style='width:16px; height:16px;' src='images/"+ str + "'></img>";

                                    },
                                    width: 40,
                                    dataIndex: 'LEVEL',
                                    text: avmon.alarm.level
                                },
                                {
                                    xtype: 'gridcolumn',
                                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                        var str="unknown.gif";

                                        if(value == "9") {
                                            str="close.gif";
                                        } else if(value == "3") {
                                            str="claim.gif";
                                        } else if(value == "2") {
                                            str="claim.gif";
                                        } else if(value == "1") {
                                            str="confirm.gif";
                                        } else if(value == "0") {
                                            str="new.gif";
                                        }
                                        return "<img style='width:16px; height:16px;' src='images/"+ str +"'></img>";
                                    },
                                    width: 40,
                                    dataIndex: 'STATUS',
                                    text: avmon.alarm.status
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'MO_CAPTION',
                                    text: avmon.config.name
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 500,
                                    defaultWidth: 500,
                                    dataIndex: 'CONTENT',
                                    text: avmon.dashboard.content
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'OCCUR_TIMES',
                                    text: avmon.config.time
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 60,
                                    dataIndex: 'AMOUNT',
                                    text: avmon.alarm.times
                                },
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    dataIndex: 'ID',
                                    text: 'ID'
                                }
                            ],
                            dockedItems: [
                                {
                                    xtype: 'pagingtoolbar',
                                    dock: 'bottom',
                                    width: 360,
                                    displayInfo: true,
                                    store: 'AlarmStore'
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onCkHistoryClick: function(button, e, eOpts) {
        var isHist_alarm_b = Ext.mainEngine.isHist_alarm_b;
        if(isHist_alarm_b == 0 || isHist_alarm_b == '0'){
            Ext.mainEngine.isHist_alarm_b=1;
            button.setIcon('images/hist_alarm_b.png');
        } else {
            Ext.mainEngine.isHist_alarm_b=0;
            button.setIcon('images/hist_alarm_a.png');
        }
    },
    onLevel0Click: function(button, e, eOpts) {
        var isLevel0_b = Ext.alarmSearch.isLevel0_b;
        if(isLevel0_b == 0 || isLevel0_b == '0'){
            Ext.alarmSearch.isLevel0_b=1;
        } else {
            Ext.alarmSearch.isLevel0_b=0;
        }
        var levelStr="";
        if(Ext.alarmSearch.isLevel0_b == 1 || Ext.alarmSearch.isLevel0_b == '1') {
            levelStr += "0,";
        }
        if(Ext.alarmSearch.isLevel1_b == 1 || Ext.alarmSearch.isLevel1_b == '1') {
            levelStr += "1,";
        }
        if(Ext.alarmSearch.isLevel2_b == 1 || Ext.alarmSearch.isLevel2_b == '1') {
            levelStr += "2,";
        }
        if(Ext.alarmSearch.isLevel3_b == 1 || Ext.alarmSearch.isLevel3_b == '1') {
            levelStr += "3,";
        }
        if(Ext.alarmSearch.isLevel4_b == 1 || Ext.alarmSearch.isLevel4_b == '1') {
            levelStr += "4,";
        }
        Ext.alarmSearch.levelStr = levelStr;
        // 重新加载活动告警数据
        var activeGrid = Ext.getCmp('alarmGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeStoreProxy.extraParams.treeId = Ext.alarmSearch.moId;
        activeStoreProxy.extraParams.isNewalarm = Ext.alarmSearch.isNewalarm_b;
        activeStoreProxy.extraParams.isMyalarm = Ext.alarmSearch.isMyalarm_b;
        activeStoreProxy.extraParams.lastUpdateTime = '';
        activeStoreProxy.extraParams.level = levelStr;
        activeGrid.getStore().load();
    },
    onLevel1Click: function(button, e, eOpts) {
        var isLevel1_b = Ext.alarmSearch.isLevel1_b;
        if(isLevel1_b == 0 || isLevel1_b == '0'){
            Ext.alarmSearch.isLevel1_b=1;
        } else {
            Ext.alarmSearch.isLevel1_b=0;
        }
        var levelStr="";
        if(Ext.alarmSearch.isLevel0_b == 1 || Ext.alarmSearch.isLevel0_b == '1') {
            levelStr += "0,";
        }
        if(Ext.alarmSearch.isLevel1_b == 1 || Ext.alarmSearch.isLevel1_b == '1') {
            levelStr += "1,";
        }
        if(Ext.alarmSearch.isLevel2_b == 1 || Ext.alarmSearch.isLevel2_b == '1') {
            levelStr += "2,";
        }
        if(Ext.alarmSearch.isLevel3_b == 1 || Ext.alarmSearch.isLevel3_b == '1') {
            levelStr += "3,";
        }
        if(Ext.alarmSearch.isLevel4_b == 1 || Ext.alarmSearch.isLevel4_b == '1') {
            levelStr += "4,";
        }
        Ext.alarmSearch.levelStr = levelStr;
        // 重新加载活动告警数据
        var activeGrid = Ext.getCmp('alarmGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeStoreProxy.extraParams.treeId = Ext.alarmSearch.moId;
        activeStoreProxy.extraParams.isNewalarm = Ext.alarmSearch.isNewalarm_b;
        activeStoreProxy.extraParams.isMyalarm = Ext.alarmSearch.isMyalarm_b;
        activeStoreProxy.extraParams.lastUpdateTime = '';
        activeStoreProxy.extraParams.level = levelStr;
        activeGrid.getStore().load();
    },
    onLevel2Click: function(button, e, eOpts) {
        var isLevel2_b = Ext.alarmSearch.isLevel2_b;
        if(isLevel2_b == 0 || isLevel2_b == '0'){
            Ext.alarmSearch.isLevel2_b=1;
        } else {
            Ext.alarmSearch.isLevel2_b=0;
        }
        var levelStr="";
        if(Ext.alarmSearch.isLevel0_b == 1 || Ext.alarmSearch.isLevel0_b == '1') {
            levelStr += "0,";
        }
        if(Ext.alarmSearch.isLevel1_b == 1 || Ext.alarmSearch.isLevel1_b == '1') {
            levelStr += "1,";
        }
        if(Ext.alarmSearch.isLevel2_b == 1 || Ext.alarmSearch.isLevel2_b == '1') {
            levelStr += "2,";
        }
        if(Ext.alarmSearch.isLevel3_b == 1 || Ext.alarmSearch.isLevel3_b == '1') {
            levelStr += "3,";
        }
        if(Ext.alarmSearch.isLevel4_b == 1 || Ext.alarmSearch.isLevel4_b == '1') {
            levelStr += "4,";
        }

        Ext.alarmSearch.levelStr = levelStr;

        // 重新加载活动告警数据
        var activeGrid = Ext.getCmp('alarmGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeStoreProxy.extraParams.treeId = Ext.alarmSearch.moId;
        activeStoreProxy.extraParams.isNewalarm = Ext.alarmSearch.isNewalarm_b;
        activeStoreProxy.extraParams.isMyalarm = Ext.alarmSearch.isMyalarm_b;
        activeStoreProxy.extraParams.lastUpdateTime = '';
        activeStoreProxy.extraParams.level = levelStr;
        activeGrid.getStore().load();
    },
    onLevel3Click: function(button, e, eOpts) {
        var isLevel3_b = Ext.alarmSearch.isLevel3_b;
        if(isLevel3_b == 0 || isLevel3_b == '0'){
            Ext.alarmSearch.isLevel3_b=1;
        } else {
            Ext.alarmSearch.isLevel3_b=0;
        }
        var levelStr="";
        if(Ext.alarmSearch.isLevel0_b == 1 || Ext.alarmSearch.isLevel0_b == '1') {
            levelStr += "0,";
        }
        if(Ext.alarmSearch.isLevel1_b == 1 || Ext.alarmSearch.isLevel1_b == '1') {
            levelStr += "1,";
        }
        if(Ext.alarmSearch.isLevel2_b == 1 || Ext.alarmSearch.isLevel2_b == '1') {
            levelStr += "2,";
        }
        if(Ext.alarmSearch.isLevel3_b == 1 || Ext.alarmSearch.isLevel3_b == '1') {
            levelStr += "3,";
        }
        if(Ext.alarmSearch.isLevel4_b == 1 || Ext.alarmSearch.isLevel4_b == '1') {
            levelStr += "4,";
        }
        Ext.alarmSearch.levelStr = levelStr;
        // 重新加载活动告警数据
        var activeGrid = Ext.getCmp('alarmGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeStoreProxy.extraParams.treeId = Ext.alarmSearch.moId;
        activeStoreProxy.extraParams.isNewalarm = Ext.alarmSearch.isNewalarm_b;
        activeStoreProxy.extraParams.isMyalarm = Ext.alarmSearch.isMyalarm_b;
        activeStoreProxy.extraParams.lastUpdateTime = '';
        activeStoreProxy.extraParams.level = levelStr;
        activeGrid.getStore().load();
    },
    onLevel4Click: function(button, e, eOpts) {
        var isLevel4_b = Ext.alarmSearch.isLevel4_b;
        if(isLevel4_b == 0 || isLevel4_b == '0'){
            Ext.alarmSearch.isLevel4_b=1;
        } else {
            Ext.alarmSearch.isLevel4_b=0;
        }
        var levelStr="";
        if(Ext.alarmSearch.isLevel0_b == 1 || Ext.alarmSearch.isLevel0_b == '1') {
            levelStr += "0,";
        }
        if(Ext.alarmSearch.isLevel1_b == 1 || Ext.alarmSearch.isLevel1_b == '1') {
            levelStr += "1,";
        }
        if(Ext.alarmSearch.isLevel2_b == 1 || Ext.alarmSearch.isLevel2_b == '1') {
            levelStr += "2,";
        }
        if(Ext.alarmSearch.isLevel3_b == 1 || Ext.alarmSearch.isLevel3_b == '1') {
            levelStr += "3,";
        }
        if(Ext.alarmSearch.isLevel4_b == 1 || Ext.alarmSearch.isLevel4_b == '1') {
            levelStr += "4,";
        }
        Ext.alarmSearch.levelStr = levelStr;
        // 重新加载活动告警数据
        var activeGrid = Ext.getCmp('alarmGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeStoreProxy.extraParams.treeId = Ext.alarmSearch.moId;
        activeStoreProxy.extraParams.isNewalarm = Ext.alarmSearch.isNewalarm_b;
        activeStoreProxy.extraParams.isMyalarm = Ext.alarmSearch.isMyalarm_b;
        activeStoreProxy.extraParams.lastUpdateTime = '';
        activeStoreProxy.extraParams.level = levelStr;
        activeGrid.getStore().load();
    },

    onMyalarmClick: function(button, e, eOpts) {
        var isMyalarm_b = Ext.alarmSearch.isMyalarm_b;
        if(isMyalarm_b == 0 || isMyalarm_b == '0'){
            Ext.alarmSearch.isMyalarm_b=1;
            button.setIcon('images/myalarm_b.png');
        } else {
            Ext.alarmSearch.isMyalarm_b=0;
            button.setIcon('images/myalarm_a.png');
        }

        // 重新加载活动告警数据
        var activeGrid = Ext.getCmp('alarmGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeStoreProxy.extraParams.treeId = Ext.alarmSearch.moId;
        activeStoreProxy.extraParams.isNewalarm = Ext.alarmSearch.isNewalarm_b;
        activeStoreProxy.extraParams.isMyalarm = Ext.alarmSearch.isMyalarm_b;
        activeStoreProxy.extraParams.lastUpdateTime = '';
        activeStoreProxy.extraParams.level = Ext.alarmSearch.levelStr;
        activeGrid.getStore().load();
    },
    onNewalarmClick: function(button, e, eOpts) {
        var isNewalarm_b = Ext.alarmSearch.isNewalarm_b;
        if(isNewalarm_b == 0 || isNewalarm_b == '0'){
            Ext.alarmSearch.isNewalarm_b=1;
            button.setIcon('images/newalarm_b.png');
        } else {
            Ext.alarmSearch.isNewalarm_b=0;
            button.setIcon('images/newalarm_a.png');
        }
        // 重新加载活动告警数据
        var activeGrid = Ext.getCmp('alarmGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeStoreProxy.extraParams.treeId = Ext.alarmSearch.moId;
        activeStoreProxy.extraParams.isNewalarm = Ext.alarmSearch.isNewalarm_b;
        activeStoreProxy.extraParams.isMyalarm = Ext.alarmSearch.isMyalarm_b;
        activeStoreProxy.extraParams.lastUpdateTime = '';
        activeStoreProxy.extraParams.level = Ext.alarmSearch.levelStr;
        activeGrid.getStore().load();
    },
    onButtonClick: function(button, e, eOpts) {
        var win = Ext.searchWindow;
        if(!win){
            win=Ext.create('widget.searchWindow');
            Ext.searchWindow=win;
            win.needReload=true;
        }
        win.center();
        win.show();
    }
});