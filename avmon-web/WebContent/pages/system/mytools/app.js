Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    views: [
        'MyViewport'
    ],
    autoCreateViewport: true,
    name: 'MyApp'
});
