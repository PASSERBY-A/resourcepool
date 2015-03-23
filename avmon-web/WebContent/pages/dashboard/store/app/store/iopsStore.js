Ext.define('MyApp.store.iopsStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.iopsModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'iopsStore',
            model: 'MyApp.model.iopsModel',
            proxy: {
                type: 'ajax',
                url: 'iops',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});