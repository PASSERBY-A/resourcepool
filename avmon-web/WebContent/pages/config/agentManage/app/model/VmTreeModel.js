Ext.define('MyApp.model.VmTreeModel', {
    extend: 'Ext.data.Model',

    fields: [
        {
            name: 'id'
        },
        {
            name: 'text'
        },
        {
            name: 'pid'
        },
        {
            name: 'hostName'
        },
        {
            name: 'hostStatus'
        },
        {
            name: 'enableFlag'
        },
        {
            name: 'objStatus'
        }
    ]
});