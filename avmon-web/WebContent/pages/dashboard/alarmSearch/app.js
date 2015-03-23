//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    requires: [
        'MyApp.view.MyViewport',
        'MyApp.view.searchWindow'
    ],
    stores: [
        'AlarmStore'
    ],
    views: [
        'MyViewport',
        'searchWindow'
    ],
    autoCreateViewport: true,
    name: 'MyApp',

    launch: function() {
        Ext.alarmSearch.application = this;

        var moId=Ext.alarmSearch.moId;
        if (moId != null && moId != '') {

            Ext.getCmp('alarmNodeId').setText(avmon.dashboard.alarmList);
            this.loadAlarm();
            // 定制定时刷新
            setInterval(this.refresh_10s, 600000);
        }
    },
    refresh_10s: function() {},
    loadAlarm: function() {
        var activeGrid = Ext.getCmp('alarmGrid');
        var activeStoreProxy = activeGrid.getStore().getProxy();
        activeStoreProxy.extraParams.treeId = Ext.alarmSearch.moId;
        activeStoreProxy.extraParams.isNewalarm = Ext.alarmSearch.isNewalarm_b;
        activeStoreProxy.extraParams.isMyalarm = Ext.alarmSearch.isMyalarm_b;
        activeStoreProxy.extraParams.lastUpdateTime = '';
        activeGrid.getStore().load();
    }
});
