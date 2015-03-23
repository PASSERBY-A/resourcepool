Ext.define('app.store.HostPingStore', {
    extend: 'Ext.data.Store',

    requires: [
        'app.model.HostPingModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'app.model.HostPingModel',
            remoteSort: true,
            storeId: 'MyJsonStore',
            pageSize: 30,
            proxy: {
                type: 'ajax',
                extraParams: {
                    ip: ''
                },
                url: 'getHostPing',
                reader: {
                    type: 'json',
                    root: 'root',
                    totalProperty: 'total'
                }
            },
            fields: [
                {
                    name: 'agentId'
                },
                {
                    name: 'ip'
                },
                {
                    name: 'os'
                },
                {
                    name: 'pingTime'
                },
                {
                    name: 'pingStatus'
                },
                {
                    name: 'realip'
                }
            ]
        }, cfg)]);
    }
});