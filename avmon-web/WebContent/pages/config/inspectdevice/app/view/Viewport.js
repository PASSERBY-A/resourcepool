Ext.define('MyApp.view.Viewport', {
    extend: 'MyApp.view.MyViewport',
    renderTo: Ext.getBody(),
    requires: [
        'MyApp.view.MyViewport',
        'MyApp.view.inspectDeviceWindow',
        'MyApp.view.inspectCommWindow'
    ]
});