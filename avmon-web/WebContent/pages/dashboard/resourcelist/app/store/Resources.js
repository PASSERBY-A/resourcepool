Ext.define('MyApp.store.Resources', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.Resource'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.Resource',
            storeId: 'MyJsonStore3',
            proxy: {
                type: 'ajax',
                url: '../../performance/performanceSummaryList',
                reader: {
                    type: 'json',
                    root: 'rows'
                }
            }
        }, cfg)]);
    }
});