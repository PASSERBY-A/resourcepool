Ext.define('MyApp.store.treeStore', {
    extend: 'Ext.data.TreeStore',
    requires: [
        'MyApp.model.treeModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.treeModel',
            storeId: 'treeStore',
            nodeParam: 'id',
            root: {
                expanded: true,
                text: 'AVMON',
                id: 'root'
            },
            proxy: {
                type: 'ajax',
                url: 'menuTree',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});