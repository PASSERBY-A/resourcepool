Ext.define('app.store.MoTypeTrees', {
    extend: 'Ext.data.TreeStore',
    alias: 'store.moTypeTrees',

    requires: [
        'app.model.MoType'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'app.model.MoType',
            storeId: 'moTypeTrees',
            proxy: {
                type: 'ajax',
                url: 'moTypeTree',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});