Ext.define('vmList.store.StorageStore', {
    extend: 'Ext.data.Store',

    requires: [
        'vmList.model.StorageModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'vmList.model.StorageModel',
            storeId: 'StorageStore',
            pageSize: 50,
            proxy: {
                type: 'ajax',
                url: 'getStorageList',
                extraParams:{moId:Ext.alarm.moId,agentId:Ext.alarm.agentId},
                reader: {
                    type: 'json',
                    root: 'items'
                }
            }
        }, cfg)]);
    }
});