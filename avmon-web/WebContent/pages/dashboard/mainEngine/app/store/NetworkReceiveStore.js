Ext.define('MyApp.store.NetworkReceiveStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkReceiveModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.NetworkReceiveModel',
            storeId: 'NetworkReceiveStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});