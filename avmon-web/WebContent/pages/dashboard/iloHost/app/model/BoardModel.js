Ext.define('MyApp.model.BoardModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'name'
        },
        {
            name: 'type'
        },
        {
            name: 'width'
        },
        {
            name: 'board'
        },
        {
            name: 'boardZoonTemp'
        },
        {
            name: 'point'
        },
        {
            name: 'alarm'
        }
    ]
});