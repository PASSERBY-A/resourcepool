Ext.define('MyApp.store.filterStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.filterModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.filterModel',
            storeId: 'filterStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});