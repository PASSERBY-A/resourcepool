Ext.define('MyApp.store.ErrorLogStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.ErrorLogModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.ErrorLogModel',
            storeId: 'ErrorLogStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});