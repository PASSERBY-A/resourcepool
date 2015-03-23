Ext.define('vmList.store.PhysicalMachineStore', {
    extend: 'Ext.data.Store',

    requires: [
        'vmList.model.PhysicalMachineModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'vmList.model.PhysicalMachineModel',
            storeId: 'MyJsonStore',
            pageSize: 50,
            proxy: {
                type: 'ajax',
                url: 'getPhysicalMachineList',
                extraParams:{moId:Ext.alarm.moId,agentId:Ext.alarm.agentId},
                reader: {
                    type: 'json',
                    root: 'items'
                }
            }
        }, cfg)]);
    }
});