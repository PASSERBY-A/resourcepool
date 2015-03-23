Ext.define('MyApp.store.VmTreeStore', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'MyApp.model.VmTreeModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            storeId: 'MyJsonTreeStore',
            model: 'MyApp.model.VmTreeModel',
            root: {
                text: 'Root',
                id: '0',
                expanded: true
            },
            proxy: {
                type: 'ajax',
                timeout : 240000,
                extraParams: {
                    isChecked: true,
                    agentId: null,
                    ampInstId: null
                },
                url: 'findVmTreeStore',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});