Ext.define('MyApp.store.opertaorStore', {
    extend: 'Ext.data.Store',

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'opertaorStore',
            data: [
                {
                    OPERATOR_ID: '=',
                    OPERATOR_NAME: avmon.config.equalTo
                },
                {
                    OPERATOR_ID: '>',
                    OPERATOR_NAME: avmon.config.GreaterThan
                },
                {
                    OPERATOR_ID: '<',
                    OPERATOR_NAME: avmon.config.lessThan
                }
            ],
            proxy: {
                type: 'memory',
                reader: {
                    type: 'json'
                }
            },
            fields: [
                {
                    name: 'OPERATOR_ID'
                },
                {
                    name: 'OPERATOR_NAME'
                }
            ]
        }, cfg)]);
    }
});