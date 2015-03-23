Ext.define('MyApp.store.DeployResults', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.DeployResult'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'MyJsonStore1',
            model: 'MyApp.model.DeployResult',
            proxy: {
                type: 'ajax',
                url: 'deployResult',
                reader: {
                    type: 'json',
                    root: 'rows'
                }
            }
        }, cfg)]);
    }
});