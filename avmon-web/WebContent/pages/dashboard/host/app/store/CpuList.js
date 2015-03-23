Ext.define('MyApp.store.CpuList', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.Cpu'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'MyApp.model.Cpu',
            storeId: 'MyJsonStore2',
            proxy: {
                type: 'ajax',
                url: 'cpu',
                reader: {
                    type: 'json',
                    root: 'rows'
                }
            }
        }, cfg)]);
    }
});