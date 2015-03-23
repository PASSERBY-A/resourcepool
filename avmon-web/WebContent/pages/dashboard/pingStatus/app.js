//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    requires: [
        'app.view.MyViewport'
    ],
    models: [
        'HostPingModel'
    ],
    stores: [
        'HostPingStore'
    ],
    views: [
        'MyViewport'    
    ],
    autoCreateViewport: true,
    name: 'app'
});
