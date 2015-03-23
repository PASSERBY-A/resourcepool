Ext.define('app.model.HostPingModel', {
    extend: 'Ext.data.Model',

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
});