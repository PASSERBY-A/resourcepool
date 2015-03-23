Ext.define('MyApp.store.NetcardJsonStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetcardModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            model: 'MyApp.model.NetcardModel',
            storeId: 'MyJsonStore4',
            proxy: {
                type: 'ajax',
                extraParams: {
                    mo: Ext.iloHost.mo
                },
                url: 'getNetcardList',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});