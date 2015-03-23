Ext.define('MyApp.model.NetcardModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'mac'
        },
        {
            name: 'type'
        },
        {
            name: 'nic'
        }
    ]
});