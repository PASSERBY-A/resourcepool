// @require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    requires: [
        'MyApp.view.MyViewport'
    ],
    models: [
        'CpuUsageModel',
        'PowerModel',
        'MemModel',
        'DiskModel',
        'NetcardModel',
        'FanModel',
        'BoardModel',
        'CaseModel',
        'BiosModel',
        'CpuCommModel',
        'PowerCommModel'
    ],
    stores: [
        'CpuUsageStore',
        'PowerStore',
        'MemJsonStore',
        'DiskJsonStore',
        'NetcardJsonStore',
        'FanJsonStore',
        'BoardJsonStore',
        'CaseJsonStore',
        'BiosJsonStore',
        'CpuCommJsonStore',
        'PowerCommJsonStore'
    ],
    views: [
        'MyViewport'
    ],
    name: 'MyApp',

    launch: function() {
        Ext.create('MyApp.view.MyViewport');
        this.getPowerStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getPowerStoreStore().load();

        this.getCpuUsageStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getCpuUsageStoreStore().load();

        this.getMemJsonStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getMemJsonStoreStore().load();

        this.getDiskJsonStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getDiskJsonStoreStore().load();

        this.getNetcardJsonStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getNetcardJsonStoreStore().load();

        this.getFanJsonStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getFanJsonStoreStore().load();

        this.getBoardJsonStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getBoardJsonStoreStore().load();

        this.getCaseJsonStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getCaseJsonStoreStore().load();

        this.getBiosJsonStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getBiosJsonStoreStore().load();

        this.getCpuCommJsonStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getCpuCommJsonStoreStore().load();

        this.getPowerCommJsonStoreStore().proxy.extraParams={mo:Ext.iloHost.mo};
        this.getPowerCommJsonStoreStore().load();
    }
});
