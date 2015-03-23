Ext.define('MyApp.store.DiskListStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.DiskListModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'DiskListStore',
            model: 'MyApp.model.DiskListModel',
            proxy: {
                type: 'ajax',
                url: 'getDiskListData',
                reader: {
                    type: 'json',
                    root: 'diskData',
                    totalProperty: 'diskTotal'
                }
            }
        }, cfg)]);
    }
});