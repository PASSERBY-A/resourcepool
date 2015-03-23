Ext.define('MyApp.store.AmpInstPolicyStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.AmpInstPolicyModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'MyJsonStore5',
            model: 'MyApp.model.AmpInstPolicyModel',
            proxy: {
                type: 'ajax',
                extraParams: {
                    agentId: null,
                    ampInstId: null,
                    flag: null,
                    nodeKey: null
                },
                url: 'findAmpInstPolicyGridInfo',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});