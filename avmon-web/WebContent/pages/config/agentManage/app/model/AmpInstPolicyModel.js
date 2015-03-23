Ext.define('MyApp.model.AmpInstPolicyModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'agentId'
        },
        {
            name: 'ampInstId'
        },
        {
            name: 'kpiCode'
        },
        {
            name: 'kpiName'
        },
        {
            name: 'nodeKey'
        },
        {
            name: 'kpiGroup'
        },
        {
            name: 'schedule'
        }
    ]
});