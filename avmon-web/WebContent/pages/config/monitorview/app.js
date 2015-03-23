//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    requires: [
        'MyApp.view.MyViewport',
        'MyApp.view.filterWindow'
    ],
    models: [
        'treeModel',
        'filterModel',
        'fieldModel'
    ],
    stores: [
        'treeStore',
        'filterStore',
        'operatorStore',
        'fieldStore'
    ],
    views: [
        'MyViewport',
        'filterWindow'
    ],
    autoCreateViewport: true,
    name: 'MyApp'
});
