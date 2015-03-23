Ext.define('MyApp.store.CpuLines', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.CpuLine'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'MyApp.model.CpuLine',
            storeId: 'cpuLines',
            proxy: {
                type: 'ajax',
                url: 'cpu',
                reader: {
                    type: 'json',
                    root: 'history'
                }
            }
        }, cfg)]);
    }
});