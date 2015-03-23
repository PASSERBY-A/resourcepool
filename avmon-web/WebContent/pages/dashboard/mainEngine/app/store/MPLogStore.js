Ext.define('MyApp.store.MPLogStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.MPLogModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.MPLogModel',
            storeId: 'MPLogStore',
            proxy: {
                type: 'ajax',
                extraParams: {
        			mo: ''
                },
                url: 'getMPLogData',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});