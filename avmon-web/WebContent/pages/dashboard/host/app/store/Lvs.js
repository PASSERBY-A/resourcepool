Ext.define('MyApp.store.Lvs', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.Lv'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.Lv',
            storeId: 'MyJsonStore7',
            proxy: {
                type: 'ajax',
                url: 'lvs',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});