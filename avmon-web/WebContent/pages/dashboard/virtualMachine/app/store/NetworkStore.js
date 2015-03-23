Ext.define('MyApp.store.NetworkStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.Network'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.Network',
            storeId: 'MyJsonStore15',
            proxy: {
                type: 'ajax',
                url: 'networkInfo',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});