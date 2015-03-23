Ext.define('MyApp.store.alarmLevelStore', {
    extend: 'Ext.data.Store',
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'alarmLevelStore',
            data: [
                {
                    LEVEL_ID: '1',
                    LEVEL_NM: avmon.alarm.well
                },
                {
                    LEVEL_ID: '2',
                    LEVEL_NM: avmon.alarm.normalAlarm
                },
                {
                    LEVEL_ID: '3',
                    LEVEL_NM: avmon.alarm.minorAlarm
                },
                {
                    LEVEL_ID: '4',
                    LEVEL_NM: avmon.alarm.mainAlarm
                },
                {
                    LEVEL_ID: '5',
                    LEVEL_NM: avmon.alarm.seriousAlarm
                }
            ],
            proxy: {
                type: 'memory',
                reader: {
                    type: 'json'
                }
            },
            fields: [
                {
                    name: 'LEVEL_ID'
                },
                {
                    name: 'LEVEL_NM'
                }
            ]
        }, cfg)]);
    }
});