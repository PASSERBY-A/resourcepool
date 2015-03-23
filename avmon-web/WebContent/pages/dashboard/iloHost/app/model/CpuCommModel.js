Ext.define('MyApp.model.CpuCommModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'key'
        },
        {
            name: 'value'
        }
    ]
});