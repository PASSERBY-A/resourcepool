Ext.define('MyApp.model.Lv', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'VG'
        },
        {
            name: 'LV'
        },
        {
            name: 'TOTAL',
            sortType: 'asInt'
        },
        {
            name: 'USAGE',
            sortType: 'asFloat'
        },
        {
            name: 'FREE'
        }
    ]
});