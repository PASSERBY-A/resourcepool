Ext.define('MyApp.store.DiskStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.DiskModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.DiskModel',
            storeId: 'DiskStore',
            proxy: {
                type: 'ajax',
                url: 'diskUse',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});