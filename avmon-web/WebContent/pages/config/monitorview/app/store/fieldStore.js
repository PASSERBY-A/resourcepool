Ext.define('MyApp.store.fieldStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.fieldModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'MyApp.model.fieldModel',
            storeId: 'fieldStore',
            proxy: {
                type: 'ajax',
                url: 'filterField',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});