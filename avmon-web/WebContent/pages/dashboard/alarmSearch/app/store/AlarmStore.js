Ext.define('MyApp.store.AlarmStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.AlarmModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.AlarmModel',
            storeId: 'AlarmStore',
            proxy: {
                type: 'ajax',
                extraParams: {
                    treeId: '',
                    level: '',
                    isNewalarm: 0,
                    isMyalarm: 0,
                    lastUpdateTime: '',
                    lastTime: '',
                    s_moCaption: '',
                    s_content_t: '',
                    s_attr_ipaddr: '',
                    s_aknowledge_by: '',
                    s_attr_businessSystem: '',
                    s_attr_position: '',
                    s_start_date: '',
                    s_end_date: '',
                    s_start_hours: '',
                    s_start_minutes: '',
                    s_end_hours: '',
                    s_end_minutes: ''
                },
                url: 'getAlarmData',
                reader: {
                    type: 'json',
                    root: 'alarmData',
                    totalProperty: 'alarmTotal'
                }
            }
        }, cfg)]);
    }
});