Ext.define('MyApp.store.PowerCommJsonStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.PowerCommModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.PowerCommModel',
            storeId: 'PowerCommJsonStore',
            proxy: {
                type: 'ajax',
                url: 'getPowerComm',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});