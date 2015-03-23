Ext.define('MyApp.store.inpectTypeStore', {
    extend: 'Ext.data.Store',
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'inpectCommStore',
            proxy: {
                type: 'ajax',
                extraParams: {
                    DEVICE_TYPE_CODE: Ext.inspectDeviceType
                },
                url: 'getInspectCommandData',
                reader: {
                    type: 'json'
                }
            },
            fields: [
                {
                    name: 'COMM_CODE'
                },
                {
                    name: 'COMM_VALUE'
                }
            ]
        }, cfg)]);
    }
});