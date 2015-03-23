Ext.define('MyApp.model.BiosModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'biosSeries'
        },
        {
            name: 'lastUpdateTime'
        },
        {
            name: 'bios'
        },
        {
            name: 'key'
        },
        {
            name: 'value'
        }
    ]
});