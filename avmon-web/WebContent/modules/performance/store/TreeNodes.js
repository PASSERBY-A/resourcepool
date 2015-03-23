Ext.define('performance.store.TreeNodes', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'performance.model.TreeNode'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            storeId: 'MyJsonTreeStore',
            model: 'performance.model.TreeNode',
            nodeParam: 'id',
            root: {
                expanded: false,
                views: 'totalView',
                defaultView: 'totalView',
                text: avmon.dashboard.dataCenter,
                id: 'root'
            },
            proxy: {
                type: 'ajax',
                method: 'post',
                extraParams: {
                    aaaa: 'ddddd'
                },
                url: '../performance/menuTree',
                reader: {
                    type: 'json',
                    idProperty: 'id'
                }
            }
        }, cfg)]);
    }
});