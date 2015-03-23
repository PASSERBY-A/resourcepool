Ext.define('MyApp.store.AgentGridJsonStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.AgentGridModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            remoteSort: true,
            storeId: 'MyJsonStore',
            model: 'MyApp.model.AgentGridModel',
            pageSize: 30,
            fields: [
                {
                    name: 'agentId'
                },
                {
                    name: 'ip'
                },
                {
                    name: 'agentVersion'
                },
                {
                    name: 'gateway'
                },
                {
                    name: 'agentStatus'
                },
                {
                    name: 'updateStatus'
                },
                {
                    name: 'agentHostStatus'
                },
                {
                    name: 'agentCollectFlag'
                },{
                    name: 'status'
                }
            ],
            proxy: {
                type: 'ajax',
                extraParams: {
                    agentId: '',
                    ip: '',
                    os:''
                },
                url: 'findAgentGridInfo',
                reader: {
                    type: 'json',
                    root: 'root',
                    totalProperty: 'activeTotal'
                }
            },
            listeners : {  
            	beforeload: function(store, operation, options){
            		//设置参数
            		var inOs = Ext.alarm.os;
		    		store.proxy.extraParams.os=inOs;
		    		
            	}
            }
        }, cfg)]);
    }
});