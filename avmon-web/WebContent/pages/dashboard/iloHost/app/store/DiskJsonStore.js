Ext.define('MyApp.store.DiskJsonStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.DiskModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            model: 'MyApp.model.DiskModel',
            storeId: 'MyJsonStore3',
            proxy: {
                type: 'ajax',
                extraParams: {
                    mo: Ext.iloHost.mo
                },
                url: 'getDiskList',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});