Ext.define('MyApp.store.networkStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.networkModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'networkStore',
            model: 'MyApp.model.networkModel',
            proxy: {
                type: 'ajax',
                url: 'networks',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});