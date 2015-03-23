Ext.define('MyApp.model.treeModel', {
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
            name: 'iconCls'
        },
        {
            name: 'isDir'
        }
    ]
});