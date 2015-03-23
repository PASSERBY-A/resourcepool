Ext.define('MyApp.model.FanModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'name'
        },
        {
            name: 'speed'
        },
        {
            name: 'status'
        },
        {
            name: 'location'
        }
    ]
});