Ext.define('MyApp.store.MyJsonStore3', {
    extend: 'Ext.data.Store',

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'MyJsonStore3',
            data: [
                {
                    name: avmon.config.yes,
                    value: 0
                },
                {
                    name: avmon.config.no,
                    value: 1
                }
            ],
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            },
            fields: [
                {
                    name: 'name'
                },
                {
                    name: 'value'
                }
            ]
        }, cfg)]);
    }
});