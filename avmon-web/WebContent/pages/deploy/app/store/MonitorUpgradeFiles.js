Ext.define('app.store.MonitorUpgradeFiles', {
    extend: 'Ext.data.Store',

    requires: [
        'app.model.MonitorUpgradeFile'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'app.model.MonitorUpgradeFile',
            storeId: 'MonitorUpgradeFiles',
            groupField: 'os',
            proxy: {
                type: 'ajax',
                url: 'monitorUpgradeFiles',
                reader: {
                    type: 'json',
                    root: 'rows'
                }
            }
        }, cfg)]);
    }
});