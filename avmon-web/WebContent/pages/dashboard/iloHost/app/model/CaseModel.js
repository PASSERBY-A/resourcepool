Ext.define('MyApp.model.CaseModel', {
    extend: 'Ext.data.Model',
    requires: [
        'Ext.data.Field'
    ],
    fields: [
        {
            name: 'caseTemp'
        },
        {
            name: 'alarm'
        },
        {
            name: 'point'
        }
    ]
});