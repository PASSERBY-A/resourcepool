Ext.define('MyApp.store.Processes', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.Process'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.Process',
            storeId: 'MyJsonStore4',
            proxy: {
                type: 'ajax',
                url: 'keyProcess',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});