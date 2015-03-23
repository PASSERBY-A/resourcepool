Ext.define('MyApp.model.MemModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'label'
        },
        {
            name: 'name'
        },
        {
            name: 'speed'
        },
        {
            name: 'size'
        },
        {
            name: 'currentSpeed'
        },
        {
            name: 'usage'
        },
        {
            name: 'point'
        },
        {
            name: 'alarm'
        }
    ]
});