Ext.define('MyApp.store.DiskViewStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.DiskViewModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            storeId: 'DiskViewStore',
            model: 'MyApp.model.DiskViewModel',
            proxy: {
                type: 'ajax',
                url: 'diskView',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});