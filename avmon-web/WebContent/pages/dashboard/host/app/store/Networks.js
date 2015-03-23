Ext.define('MyApp.store.Networks', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'MyApp.model.Network'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.Network',
            storeId: 'MyJsonTreeStore',
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