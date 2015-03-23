Ext.define('MyApp.store.groupStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.groupModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'groupStore',
            model: 'MyApp.model.groupModel',
            proxy: {
                type: 'ajax',
                url: 'groupInfo',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});