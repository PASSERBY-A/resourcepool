Ext.define('MyApp.store.MemReceiveStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkReceiveModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.NetworkReceiveModel',
            storeId: 'MemReceiveStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});