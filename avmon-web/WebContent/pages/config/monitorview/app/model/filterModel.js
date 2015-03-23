Ext.define('MyApp.model.filterModel', {
    extend: 'Ext.data.Model',

    idProperty: 'FILTER_NO',

    fields: [
        {
            name: 'FILTER_NO'
        },
        {
            name: 'FILTER_FIELD'
        },
        {
            name: 'FILTER_OPERATOR'
        },
        {
            name: 'FILTER_VALUE'
        }
    ]
});