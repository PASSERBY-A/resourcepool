Ext.define('MyApp.store.cpuStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.cpuModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'cpuStore',
            model: 'MyApp.model.cpuModel',
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