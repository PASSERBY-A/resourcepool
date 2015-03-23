Ext.define('MyApp.model.AgentAmpListModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'ampId'
        },
        {
            name: 'agentId'
        },
        {
            name: 'ampInstId'
        },
        {
            name: 'ampVersion'
        },
        {
            name: 'caption'
        },
        {
            name: 'enableFlg'
        },
        {
            name: 'lastActiveTime'
        },
        {
            name: 'schedule'
        },
        {
            name: 'status'
        },
        {
            name: 'ampType'
        },
        {
            name: 'lastAmpUpdateTime'
        },
        {
            name: 'lastScheduleUpdateTime'
        },
        {
            name: 'lastConfigUpdateTime'
        }
    ]
});