Ext.define('MyApp.store.NetworkListStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.NetworkListModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'NetworkListStore',
            model: 'MyApp.model.NetworkListModel',
            proxy: {
                type: 'ajax',
                url: 'getNetworkListData',
                reader: {
                    type: 'json',
                    root: 'networkData',
                    totalProperty: 'networkTotal'
                }
            }
        }, cfg)]);
    }
});