Ext.define('MyApp.store.NetworkSendStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.NetworkSendModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'NetworkSendStore',
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