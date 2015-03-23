Ext.define('MyApp.store.riDeviceStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.riDeviceModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            storeId: 'riDeviceStore',
            model: 'MyApp.model.riDeviceModel',
            proxy: {
                type: 'ajax',
                url: 'getRouteInspectDeviceData',
                reader: {
                    type: 'json',
                    root: 'RIDeviceData',
                    totalProperty: 'RIDeviceTotal'
                }
            }
        }, cfg)]);
    }
});