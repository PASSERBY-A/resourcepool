//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});

Ext.application({

    requires: [
        'MyApp.view.MyViewport'
    ],
    views: [
        'MyViewport'
    ],
    autoCreateViewport: true,
    name: 'MyApp'
});
