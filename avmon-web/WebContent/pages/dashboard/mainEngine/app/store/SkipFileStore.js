Ext.define('MyApp.store.SkipFileStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.SkipFileModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.SkipFileModel',
            storeId: 'SkipFileStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});