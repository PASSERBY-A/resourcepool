Ext.define('performance.model.TreeNode', {
    extend: 'Ext.data.Model',
    idProperty:'id',
    fields: [
        {
            name: 'id'
        },
        {
            name: 'text'
        },
        {
            name: 'iconCls'
        },
        {
            name: 'pid'
        },
        {
            name: 'views'
        },
        {
            name: 'defaultView'
        }
    ]
});