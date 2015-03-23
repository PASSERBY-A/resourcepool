Ext.define('MyApp.store.NetworkInfoStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkInfoModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.NetworkInfoModel',
            storeId: 'NetworkInfoStore',
            proxy: {
                type: 'ajax',
                extraParams: {
                    treeId: ''
                },
                url: 'getNetworkInfoData',
                reader: {
                    type: 'json',
                    root: 'basicData',
                    totalProperty: 'basicTotal'
                }
            }
        }, cfg)]);
    }
});