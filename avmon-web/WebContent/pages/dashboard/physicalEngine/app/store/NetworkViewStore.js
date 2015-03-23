Ext.define('MyApp.store.NetworkViewStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkViewModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            storeId: 'NetworkViewStore',
            model: 'MyApp.model.NetworkViewModel',
            proxy: {
                type: 'ajax',
                url: 'networkView',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});