Ext.define('MyApp.model.AmpNormalScheduleModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'kpiCode'
        },
        {
            name: 'caption'
        },
        {
            name: 'schedule'
        },
        {
            name: 'kpiGroup'
        },
        {
            name: 'agentId'
        },
        {
            name: 'ampInstId'
        },
        {
            name: 'nodeKey'
        },
        {
          name: 'status'
        }
    ]
});