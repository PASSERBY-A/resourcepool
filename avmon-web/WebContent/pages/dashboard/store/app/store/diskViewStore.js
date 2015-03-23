Ext.define('MyApp.store.diskViewStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.diskViewModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'diskViewStore',
            model: 'MyApp.model.diskViewModel',
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