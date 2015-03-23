Ext.define('MyApp.model.thresholdModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'ID'
        },
        {
            name: 'MO'
        },
        {
            name: 'MONITOR_INSTANCE'
        },
        {
            name: 'KPI'
        },
        {
            name: 'MAX_THRESHOLD'
        },
        {
            name: 'MIN_THRESHOLD'
        },
        {
            name: 'THRESHOLD'
        },
        {
            name: 'CHECK_OPTR'
        },
        {
            name: 'ALARM_LEVEL'
        },
        {
            name: 'ACCUMULATE_COUNT'
        },
        {
            name: 'START_DATE'
        },
        {
            name: 'END_DATE'
        },
        {
            name: 'CONTENT'
        }
    ]
});