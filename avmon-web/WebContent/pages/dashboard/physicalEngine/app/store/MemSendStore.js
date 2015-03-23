Ext.define('MyApp.store.MemSendStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkSendModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'MemSendStore',
            model: 'MyApp.model.NetworkSendModel',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});