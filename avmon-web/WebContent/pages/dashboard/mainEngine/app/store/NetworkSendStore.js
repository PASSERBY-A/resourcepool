Ext.define('MyApp.store.NetworkSendStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkSendModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.NetworkSendModel',
            storeId: 'NetworkSendStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});