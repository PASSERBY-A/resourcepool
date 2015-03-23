Ext.define('MyApp.store.Disks', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.Disk'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.Disk',
            storeId: 'MyJsonStore6',
            proxy: {
                type: 'ajax',
                url: 'diskIo',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});