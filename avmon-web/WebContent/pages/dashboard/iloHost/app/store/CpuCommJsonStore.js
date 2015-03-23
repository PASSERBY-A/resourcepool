Ext.define('MyApp.store.CpuCommJsonStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.CpuCommModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.CpuCommModel',
            storeId: 'CpuCommJsonStore',
            proxy: {
                type: 'ajax',
                url: 'getCpuComm',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});