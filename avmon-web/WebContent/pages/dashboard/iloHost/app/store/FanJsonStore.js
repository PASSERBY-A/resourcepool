Ext.define('MyApp.store.FanJsonStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.FanModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            model: 'MyApp.model.FanModel',
            storeId: 'MyJsonStore5',
            proxy: {
                type: 'ajax',
                extraParams: {
                    mo: Ext.iloHost.mo
                },
                url: 'getFanList',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});