Ext.define('MyApp.store.Mems', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.Mem'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.Mem',
            storeId: 'MyJsonStore5',
            proxy: {
                type: 'ajax',
                url: 'mem',
                reader: {
                    type: 'json',
                    root: 'history'
                }
            }
        }, cfg)]);
    }
});