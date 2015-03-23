Ext.define('MyApp.store.DiscoveryStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.Discovery'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'discoveryStore',
            model: 'MyApp.model.Discovery',
            groupField: 'type',
            proxy: {
                type: 'ajax',
                url: 'discoveryList',
                reader: {
                    type: 'json',
                    root: 'rows'
                }
            }
        }, cfg)]);
    }
});