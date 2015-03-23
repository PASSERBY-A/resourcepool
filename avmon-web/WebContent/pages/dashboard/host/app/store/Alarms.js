Ext.define('MyApp.store.Alarms', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.Alarm'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.Alarm',
            storeId: 'MyJsonStore3',
            proxy: {
                type: 'ajax',
                url: 'newAlarm',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});