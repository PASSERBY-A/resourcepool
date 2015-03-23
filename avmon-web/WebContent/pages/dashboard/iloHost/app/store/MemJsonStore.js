Ext.define('MyApp.store.MemJsonStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.MemModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            model: 'MyApp.model.MemModel',
            storeId: 'MyJsonStore2',
            proxy: {
                type: 'ajax',
                extraParams: {
                    mo: Ext.iloHost.mo
                },
                url: 'getMemList',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});