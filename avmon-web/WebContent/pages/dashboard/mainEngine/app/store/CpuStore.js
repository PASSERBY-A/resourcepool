Ext.define('MyApp.store.CpuStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.CpuModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.CpuModel',
            storeId: 'CpuStore',
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