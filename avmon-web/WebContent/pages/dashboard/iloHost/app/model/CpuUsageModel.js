Ext.define('MyApp.model.CpuUsageModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'usage'
        },
        {
            name: 'cpu'
        },
        {
            name: 'point'
        },
        {
            name: 'alarm'
        },
        {
            name: 'mmxx'
        },
        {
            name: 'flag'
        },
        {
            name: 'list'
        },
        {
            name: 'comm'
        },
        {
            name: 'cpuTemp'
        }
    ]
});