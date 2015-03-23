//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});

Ext.application({

    requires: [
        'MyApp.view.MyViewport',
        'MyApp.view.CpuPanel',
        'MyApp.view.BasicInfo',
        'MyApp.view.NewAlarmGrid',
        'MyApp.view.KeyProcessGrid',
        'MyApp.view.MemPanel',
        'MyApp.view.PowerPanel',
        'MyApp.view.MyViewport1'
    ],
    models: [
        'CpuLine',
        'Cpu',
        'Alarm',
        'Process',
        'Mem',
        'Disk',
        'Lv',
        'Network'
    ],
    stores: [
        'MyJsonStore',
        'CpuLines',
        'CpuList',
        'Alarms',
        'Processes',
        'Mems',
        'Disks',
        'Lvs',
        'Networks',
        'Swaps'
    ],
    views: [
        'MyViewport',
        'CpuPanel',
        'BasicInfo',
        'NewAlarmGrid',
        'KeyProcessGrid',
        'MemPanel',
        'PowerPanel',
        'MyViewport1'
    ],
    autoCreateViewport: true,
    name: 'MyApp',
    controllers: [
        'Main'
    ],

    launch: function() {
        if(!Ext.avmon){
            Ext.avmon={};
        }

        Ext.avmon.application=this;

        this.init();

        this.refreshAll();

        setInterval(this.refresh_5s,25000);

        setInterval(this.refresh_1m,60000);

        setInterval(this.refresh_10m,600000);


    },

    init: function() {

    },

    test: function() {
        console.log("hello wolrd");

    },

    refreshAll: function() {

        if(window.parent.isShow("performance.dashboardPanel")){

            this.refresh_5s();
            this.refresh_1m();
            this.refresh_10m();

        }
        else{
            //console.log("not show dashboard");
        }

    },

    refresh_5s: function() {

        if(window.parent.isShow("performance.dashboardPanel")){
            Ext.avmon.application.reloadCpu();
            Ext.avmon.application.reloadMem();
        }

    },

    refresh_1m: function() {

        if(window.parent.isShow("performance.dashboardPanel")){

            Ext.getCmp("keyProcessGrid").getStore().load({params:{mo:Ext.avmon.moId}});

            Ext.avmon.application.reloadBasicInfo();

            Ext.avmon.application.reloadNewAlarm();

        }
    },

    refresh_10m: function() {

        if(window.parent.isShow("performance.dashboardPanel")){

            Ext.getCmp("diskIoGrid").getStore().load({params:{mo:Ext.avmon.moId}});

            Ext.getCmp("diskVolGrid").getStore().load({params:{mo:Ext.avmon.moId}});

            Ext.getCmp("networkGrid").getStore().load({params:{mo:Ext.avmon.moId}});

            Ext.avmon.application.reloadPowerPanel();
        }

    },

    reloadCpu: function() {

        var cpuChart=Ext.getCmp("cpuChart");

        cpuChart.store.load({params:{mo:Ext.avmon.moId}});

    },

    reloadBasicInfo: function() {
        var moId=Ext.avmon.moId;

        Ext.Ajax.request({
            url: 'basicInfo',
            params: {mo:moId},
            success: function(response, opts) {
                var obj=Ext.decode(response.responseText);
                Ext.getCmp("basicInfo").getForm().setValues(obj);

            },
            failure: function(response, opts) {
                // alert('error');
            }
        });
    },

    reloadNewAlarm: function() {
        Ext.getCmp("newAlarmGrid").getStore().load({params:{mo:Ext.avmon.moId}});

    },

    reloadPowerPanel: function() {
        //this.getPowerPanel();
        //Ext.getCmp("powerPanel");
    },

    reloadMem: function() {
        Ext.Ajax.request({
            url: 'mem',
            params: {mo:Ext.avmon.moId},
            success: function(response, opts) {
                var obj=Ext.decode(response.responseText);
                Ext.getCmp("memInfo").getForm().setValues(obj);
                Ext.getCmp("memChart1").store.loadData(obj.history,false);

            },
            failure: function(response, opts) {
                //alert('error');
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });

        Ext.Ajax.request({
            url: 'swap',
            params: {mo:Ext.avmon.moId},
            success: function(response, opts) {
                var obj=Ext.decode(response.responseText);
                //Ext.getCmp("memInfo").getForm().setValues(obj);
                Ext.getCmp("memChart2").store.loadData(obj.history,false);

            },
            failure: function(response, opts) {
                //alert('error');
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    }

});
