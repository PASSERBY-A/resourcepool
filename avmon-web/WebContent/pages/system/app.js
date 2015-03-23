//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    models: [
        'Menu'
    ],
    stores: [
        'SystemMenus'
    ],
    views: [
        'MyViewport'
    ],
    autoCreateViewport: true,
    name: 'MyApp'
});
