Ext.define('app.store.UpgradeMonitors', {
    extend: 'Ext.data.Store',

    requires: [
        'app.model.Monitor'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'app.model.Monitor',
            storeId: 'UpgradeMonitors',
            proxy: {
                type: 'ajax',
                url: 'monitorList',
                reader: {
                    type: 'json',
                    idProperty: 'id',
                    root: 'list',
                    totalProperty: 'total'
                }
            }
        }, cfg)]);
    }
});