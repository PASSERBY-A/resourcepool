Ext.define('MyApp.store.CpuUsageStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.CpuUsageModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            model: 'MyApp.model.CpuUsageModel',
            storeId: 'MyJsonStore',
            proxy: {
                type: 'ajax',
                extraParams: {
                    mo: ''
                },
                url: 'getIloHostCpuUsage',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});