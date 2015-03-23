Ext.define('MyApp.store.BiosJsonStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.BiosModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.BiosModel',
            storeId: 'BiosJsonStore',
            proxy: {
                type: 'ajax',
                url: 'getBiosList',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});