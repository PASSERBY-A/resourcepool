Ext.define('MyApp.store.operatorStore', {
    extend: 'Ext.data.Store',

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            storeId: 'operatorStore',
            data: [
                {
                    FILTER_OPERATOR: '=',
                    FILTER_OPERATOR_NAME: avmon.config.equalTo
                },
                {
                    FILTER_OPERATOR: '!=',
                    FILTER_OPERATOR_NAME: avmon.config.notEqual
                },
                {
                    FILTER_OPERATOR: 'like',
                    FILTER_OPERATOR_NAME: avmon.config.like
                },
                {
                    FILTER_OPERATOR: 'not like',
                    FILTER_OPERATOR_NAME: avmon.config.notLike
                },
                {
                    FILTER_OPERATOR: 'in',
                    FILTER_OPERATOR_NAME: avmon.config.include
                },
                {
                    FILTER_OPERATOR: 'not in',
                    FILTER_OPERATOR_NAME: avmon.config.notIn
                },
                //添加为null的判断
                {
                    FILTER_OPERATOR: 'is',
                    FILTER_OPERATOR_NAME: avmon.config.yes
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
                    name: 'FILTER_OPERATOR'
                },
                {
                    name: 'FILTER_OPERATOR_NAME'
                }
            ]
        }, cfg)]);
    }
});