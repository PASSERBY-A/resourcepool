Ext.define('MyApp.store.MemStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.MemModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.MemModel',
            storeId: 'MemStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});