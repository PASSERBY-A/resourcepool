Ext.define('MyApp.store.MemReceiveStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkReceiveModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'MemReceiveStore',
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