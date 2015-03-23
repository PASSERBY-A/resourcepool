Ext.define('MyApp.store.groupViewStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.groupViewModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'groupViewStore',
            model: 'MyApp.model.groupViewModel',
            proxy: {
                type: 'ajax',
                url: 'groupView',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});