Ext.define('MyApp.store.portViewStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.portViewModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'portViewStore',
            model: 'MyApp.model.portViewModel',
            proxy: {
                type: 'ajax',
                url: 'portView',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});