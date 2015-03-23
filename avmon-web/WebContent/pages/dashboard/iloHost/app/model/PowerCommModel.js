Ext.define('MyApp.model.PowerCommModel', {
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