Ext.define('vmList.store.VirtualMachineStore', {
    extend: 'Ext.data.Store',

    requires: [
        'vmList.model.VirtualMachineModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'vmList.model.VirtualMachineModel',
            storeId: 'VirtualMachineStore',
            pageSize: 50,
            proxy: {
                type: 'ajax',
                url: 'getVirtualMachineList',
                extraParams:{moId:Ext.alarm.moId,agentId:Ext.alarm.agentId},
                reader: {
                    type: 'json',
                    root: 'items'
                }
            }
        }, cfg)]);
    }
});