Ext.define('MyApp.store.deviceTypeStore', {
    extend: 'Ext.data.Store',
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'deviceTypeStore',
            proxy: {
                type: 'ajax',
                url: 'getDeviceTypeData',
                reader: {
                    type: 'json'
                }
            },
            fields: [
                {
                    name: 'DEVICE_TYPE_CODE'
                },
                {
                    name: 'DEVICE_TYPE_VALUE'
                }
            ]
        }, cfg)]);
    }
});