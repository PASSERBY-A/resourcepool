Ext.define('MyApp.store.IloHostListStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.IloHostModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'MyJsonStore4',
            model: 'MyApp.model.IloHostModel',
            proxy: {
                type: 'ajax',
                extraParams: {
                    agentId: null,
                    ampInstId: null
                },
                url: 'findIloHostGridInfo',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});