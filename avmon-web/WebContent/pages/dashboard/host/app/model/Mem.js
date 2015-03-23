Ext.define('MyApp.model.Mem', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'mem_user'
        },
        {
            name: 'mem_sys'
        },
        {
            name: 'mem_free'
        },
        {
            name: 'swap_usage'
        },
        {
            name: 'swap_free'
        },
        {
            name: 'time'
        }
    ]
});