Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    models: [
        'cpuModel',
        'alarmModel',
        'iopsModel',
        'delayModel',
        'networkModel',
        'groupModel',
        'diskModel',
        'groupViewModel',
        'portViewModel',
        'diskViewModel'
    ],
    stores: [
        'cpuStore',
        'alarmStore',
        'iopsStore',
        'delayStore',
        'groupStore',
        'diskStore',
        'networkStore',
        'groupViewStore',
        'portViewStore',
        'diskViewStore'
    ],
    views: [
        'MyPanel'
    ],
    autoCreateViewport: true,
    name: 'MyApp',

    launch: function() {
        Ext.store.application = this;

        var moId=Ext.store.moId;

        if (moId != null && moId != '') {
            this.init();
            this.loadCpu();
            this.loadBasicInfo();
            this.loadNewAlarm();
            this.loadNetWork();
            this.loadGroupInfo();
            this.loadDiskInfo();
            this.loadIops();
            //this.loadDelay();

            Ext.store.currentTab = 1;
            // 定制定时刷新
            setInterval(this.refresh_5s, 300000);
        }
    },

    loadBasicInfo: function() {
        var moId = Ext.store.moId;

        Ext.Ajax.request({
            url: 'basicInfo',
            params: {mo:moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                Ext.getCmp("basicInfo").getForm().setValues(obj);
            },
            failure: function(response, opts) {
            }
        });
    },

    loadNewAlarm: function() {
        Ext.getCmp("newAlarmGrid").getStore().load({params:{mo:Ext.store.moId}});

    },

    loadNetWork: function() {
        Ext.getCmp("networkGrid").getStore().load({params:{mo:Ext.store.moId}});

    },

    loadGroupInfo: function() {
        Ext.getCmp("groupInfoGrid").getStore().load({params:{mo:Ext.store.moId}});

    },

    loadDiskInfo: function() {
        Ext.getCmp("diskInfoGrid").getStore().load({params:{mo:Ext.store.moId}});
    },

    loadIops: function() {
        Ext.Ajax.request({
            url: 'iops',
            params: {mo:Ext.store.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                //Ext.getCmp("memInfo").getForm().setValues(obj);
                //Ext.getCmp("iopsChart").store.loadData(obj.history,false);
                Ext.getCmp("iops_chart1").store.loadData(obj, false);
                Ext.getCmp("iops_chart2").store.loadData(obj, false);
                Ext.getCmp("iops_chart3").store.loadData(obj, false);
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },

    loadCpu: function() {
        Ext.Ajax.request({
            url: 'cpu',
            params: {mo:Ext.store.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                Ext.getCmp("cpuChart").store.loadData(obj.history,false);
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },

    refresh_5s: function() {
        // 判断当前活动页面是否为存储页面
        if(window.parent.isShow("performance.storageDashboard")){

            if (Ext.store.currentTab == 1) {
                // 当前tab=【仪表板】
                Ext.store.application.loadBasicInfo();
                Ext.store.application.loadCpu();
                Ext.store.application.loadNewAlarm();
                Ext.store.application.loadIops();
                //Ext.store.application.loadDelay();
                Ext.store.application.loadNetWork();
                Ext.store.application.loadGroupInfo();
                Ext.store.application.loadDiskInfo();
            } else if (Ext.store.currentTab == 2) {
                // 当前tab=【组信息】
                var groupView = Ext.getCmp('groupView');
                var groupViewStoreProxy = groupView.getStore().getProxy();
                groupViewStoreProxy.extraParams.mo = Ext.store.moId;

                groupView.getStore().load();
            } else if (Ext.store.currentTab == 3) {
                // 当前tab=【前段口】
                var portView = Ext.getCmp('portView');
                var portViewStoreProxy = portView.getStore().getProxy();
                portViewStoreProxy.extraParams.mo = Ext.store.moId;

                portView.getStore().load();
            } else if (Ext.store.currentTab == 4) {
                // 当前tab=【盘信息】
                var diskView = Ext.getCmp('diskView');
                var diskViewStoreProxy = diskView.getStore().getProxy();
                diskViewStoreProxy.extraParams.mo = Ext.store.moId;

                diskView.getStore().load();
            }
        }

    },

    refreshByLoad: function() {
        if (Ext.store.currentTab == 1) {
            // 当前tab=【仪表板】
            Ext.store.application.loadBasicInfo();
            Ext.store.application.loadCpu();
            Ext.store.application.loadNewAlarm();
            Ext.store.application.loadIops();
            //Ext.store.application.loadDelay();
            Ext.store.application.loadNetWork();
            Ext.store.application.loadGroupInfo();
            Ext.store.application.loadDiskInfo();
        } else if (Ext.store.currentTab == 2) {
            // 当前tab=【组信息】
            var groupView = Ext.getCmp('groupView');
            var groupViewStoreProxy = groupView.getStore().getProxy();
            groupViewStoreProxy.extraParams.mo = Ext.store.moId;

            groupView.getStore().load();
        } else if (Ext.store.currentTab == 3) {
            // 当前tab=【前段口】
            var portView = Ext.getCmp('portView');
            var portViewStoreProxy = portView.getStore().getProxy();
            portViewStoreProxy.extraParams.mo = Ext.store.moId;

            portView.getStore().load();
        } else if (Ext.store.currentTab == 4) {
            // 当前tab=【盘信息】
            var diskView = Ext.getCmp('diskView');
            var diskViewStoreProxy = diskView.getStore().getProxy();
            diskViewStoreProxy.extraParams.mo = Ext.store.moId;

            diskView.getStore().load();
        }
    }

});
