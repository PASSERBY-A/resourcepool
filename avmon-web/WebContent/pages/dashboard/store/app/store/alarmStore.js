Ext.define('MyApp.store.alarmStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.alarmModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'alarmStore',
            model: 'MyApp.model.alarmModel',
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