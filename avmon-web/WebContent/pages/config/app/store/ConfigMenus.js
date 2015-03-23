Ext.define('MyApp.store.ConfigMenus', {
    extend: 'Ext.data.TreeStore',

    requires: [
        'MyApp.model.Menu'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'MyApp.model.Menu',
            storeId: 'MyTreeStore',
            nodeParam: 'id',
            root: {
                expanded: true,
                text: 'HP',
                id: 'root'
            },
            proxy: {
                type: 'ajax',
                url: 'menuTree?type=config',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});