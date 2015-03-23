//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    models: [
        'TreeNode'
    ],
    stores: [
        'TreeNodes'
    ],
    views: [
        'MyViewport'
    ],
    controllers: [
        'MenuTree'
    ],
    name: 'performance'
});
