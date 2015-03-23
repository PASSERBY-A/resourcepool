Ext.Loader.setConfig({
    enabled: true
});

Ext.Loader.setPath('Ext.ux', '../pages/commons/ux');
Ext.require(['Ext.chart.*','Ext.ux.DynamicChart']);
Ext.application({
    models: [
        'HostTreeNode'
    ],
    stores: [
        'HostTreeStore',
        'ChartStore',
        'KpiStore'
    ],
    views: [
        'MyViewport',
        'LineChart',
        'MyTreePanel'
    ],
    autoCreateViewport: true,
    name: 'MyApp',
    controllers: [
        'KpiCompareController'
    ]
});
