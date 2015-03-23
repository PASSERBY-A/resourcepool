Ext.define('MyApp.store.DiskStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.DiskModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'DiskStore',
            model: 'MyApp.model.DiskModel',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});