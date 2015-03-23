Ext.define('MyApp.store.NetworkReceiveStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkReceiveModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'NetworkReceiveStore',
            model: 'MyApp.model.NetworkReceiveModel',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});