Ext.define('MyApp.model.PowerModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'power'
        },
        {
            name: 'status'
        },
        {
            name: 'alarm'
        },
        {
            name: 'temp'
        }
    ]
});