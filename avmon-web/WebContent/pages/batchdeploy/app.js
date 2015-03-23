Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    models: [
        'Discovery',
        'DeployResult'
    ],
    stores: [
        'DiscoveryStore',
        'DeployResults'
    ],
    views: [
        'MyViewport',
        'DiscoveryGrid'
    ],
    autoCreateViewport: true,
    name: 'MyApp',
    controllers: [
        'Main'
    ]
});
