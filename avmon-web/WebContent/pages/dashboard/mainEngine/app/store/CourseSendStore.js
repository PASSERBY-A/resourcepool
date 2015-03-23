Ext.define('MyApp.store.CourseSendStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NetworkSendModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.NetworkSendModel',
            storeId: 'CourseSendStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});