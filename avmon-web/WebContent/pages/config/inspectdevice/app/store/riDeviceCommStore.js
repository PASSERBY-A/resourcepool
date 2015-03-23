Ext.define('MyApp.store.riDeviceCommStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.riDeviceCommModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'riDeviceCommStore',
            model: 'MyApp.model.riDeviceCommModel',
            proxy: {
                type: 'ajax',
                extraParams: {
                    DEVICE_IP: ''
                },
                url: 'getRIDeviceCommData',
                reader: {
                    type: 'json',
                    root: 'RIDeviceCData',
                    totalProperty: 'RIDeviceCTotal'
                }
            }
        }, cfg)]);
    }
});