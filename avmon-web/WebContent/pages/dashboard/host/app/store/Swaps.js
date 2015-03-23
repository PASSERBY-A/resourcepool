Ext.define('MyApp.store.Swaps', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.Mem'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.Mem',
            storeId: 'MyJsonStore8',
            proxy: {
                type: 'ajax',
                url: 'swap',
                reader: {
                    type: 'json',
                    root: 'history'
                }
            }
        }, cfg)]);
    }
});