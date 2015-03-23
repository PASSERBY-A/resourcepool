Ext.define('MyApp.model.DiskModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'usage'
        },
        {
            name: 'disk'
        },
        {
            name: 'point'
        },
        {
            name: 'alarm'
        },
        {
            name: 'diskTemp'
        }
    ]
});