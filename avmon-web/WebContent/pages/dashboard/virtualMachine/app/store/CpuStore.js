Ext.define('MyApp.store.CpuStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.CpuModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'CpuStore',
            model: 'MyApp.model.CpuModel',
            proxy: {
                type: 'ajax',
                url: 'cpuUse',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});