Ext.define('MyApp.store.AmpVmSetStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.AmpNormalScheduleModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'AmpVmSetStore',
            model: 'MyApp.model.AmpNormalScheduleModel',
            proxy: {
                type: 'ajax',
                extraParams: {
                    agentId: '',
                    ampInstId: ''
                },
                url: 'getNormalAmpSchedule1',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});