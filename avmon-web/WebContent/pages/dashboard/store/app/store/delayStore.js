Ext.define('MyApp.store.delayStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.delayModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'delayStore',
            model: 'MyApp.model.delayModel',
            proxy: {
                type: 'ajax',
                url: 'delay',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});