Ext.define('MyApp.store.FileSysStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.FileSysModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.FileSysModel',
            storeId: 'FileSysStore',
            proxy: {
                type: 'ajax',
                url: 'fileSysUse',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});