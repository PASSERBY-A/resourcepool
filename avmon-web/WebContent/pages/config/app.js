//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    models: [
        'Menu'
    ],
    stores: [
        'ConfigMenus'
    ],
    views: [
        'MyViewport'
    ],
    autoCreateViewport: true,
    name: 'MyApp'
});
