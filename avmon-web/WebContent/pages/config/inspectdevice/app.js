Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    models: [
        'riDeviceModel',
        'riDeviceCommModel'
    ],
    stores: [
        'deviceTypeStore',
        'inpectTypeStore',
        'riDeviceStore',
        'riDeviceCommStore'
    ],
    views: [
        'MyViewport',
        'inspectDeviceWindow',
        'inspectCommWindow'
    ],
    autoCreateViewport: true,
    name: 'MyApp'
});