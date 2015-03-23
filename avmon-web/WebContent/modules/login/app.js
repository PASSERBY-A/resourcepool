Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    views: [
        'LoginPanel'
    ],
    appFolder: 'app/..',
    autoCreateViewport: true,
    name: 'login'
});
