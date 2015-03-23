Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    models: [
        'thresholdModel'
    ],
    stores: [
        'thresholdStore',
        'opertaorStore',
        'alarmLevelStore'
    ],
    views: [
        'MyViewport',
        'thresholdWindow'
    ],
    autoCreateViewport: true,
    name: 'MyApp'
});
