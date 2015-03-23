Ext.define('main.store.PerformanceMenus', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'main.model.Menu'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            storeId: 'MyJsonTreeStore',
            model: 'main.model.Menu',
            nodeParam: 'id',
            root: {
                expanded: true,
                text: 'HP',
                id: 'root'
            },
            proxy: {
                type: 'ajax',
                url: '../main/menuTree?type=performance',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});