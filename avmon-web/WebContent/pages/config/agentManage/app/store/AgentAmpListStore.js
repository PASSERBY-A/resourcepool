Ext.define('MyApp.store.AgentAmpListStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.AgentAmpListModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'agentAmpListStoreId',
            model: 'MyApp.model.AgentAmpListModel',
            pageSize: 30,
            proxy: {
                type: 'ajax',
                extraParams: {
                    agentId: ''
                },
                url: 'findAgentAmp',
                reader: {
                    type: 'json',
                    root: 'root',
                    totalProperty: 'activeTotal'
                }
            }
        }, cfg)]);
    }
});