Ext.define('MyApp.model.AlarmModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'ID'
        },
        {
            name: 'LEVEL'
        },
        {
            name: 'STATUS'
        },
        {
            name: 'MO_CAPTION'
        },
        {
            name: 'CONTENT'
        },
        {
            name: 'OCCUR_TIMES'
        },
        {
            name: 'AMOUNT'
        }
    ]
});