Ext.define('MyApp.store.MemSendStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkSendModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.NetworkSendModel',
            storeId: 'MemSendStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});