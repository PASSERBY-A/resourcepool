Ext.define('MyApp.store.AmpNormalScheduleStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.AmpNormalScheduleModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'AmpNormalScheduleStore',
            model: 'MyApp.model.AmpNormalScheduleModel',
            proxy: {
                type: 'ajax',
                extraParams: {
                    agentId: '',
                    ampInstId: ''
                },
                url: 'getNormalAmpSchedule',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});