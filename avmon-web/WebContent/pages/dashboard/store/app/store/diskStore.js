Ext.define('MyApp.store.diskStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.diskModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'diskStore',
            model: 'MyApp.model.diskModel',
            proxy: {
                type: 'ajax',
                url: 'diskInfo',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});