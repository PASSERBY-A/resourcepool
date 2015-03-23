Ext.define('MyApp.store.PowerStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.PowerModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            model: 'MyApp.model.PowerModel',
            storeId: 'MyJsonStore1',
            proxy: {
                type: 'ajax',
                extraParams: {
                    mo: Ext.iloHost.mo
                },
                url: 'getPowerList',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});