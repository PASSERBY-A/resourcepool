Ext.define('CFG.store.DeviceStore', {
    extend: 'Ext.data.Store',

    requires: [
        'CFG.model.DeviceModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            storeId: 'MyJsonStore',
            model: 'CFG.model.DeviceModel',
            proxy: {
                type: 'ajax',
                url: 'findAllDevice',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});