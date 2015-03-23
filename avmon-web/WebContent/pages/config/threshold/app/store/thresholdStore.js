Ext.define('MyApp.store.thresholdStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.thresholdModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            storeId: 'thresholdStore',
            model: 'MyApp.model.thresholdModel',
            proxy: {
                type: 'ajax',
                url: 'getThresholdData',
                reader: {
                    type: 'json',
                    root: 'ThresholdData',
                    totalProperty: 'ThresholdTotal'
                }
            }
        }, cfg)]);
    }
});