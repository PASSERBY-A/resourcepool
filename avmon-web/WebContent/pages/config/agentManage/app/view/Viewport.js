Ext.define('MyApp.view.Viewport', {
    extend: 'MyApp.view.AgentViewport',
    renderTo: Ext.getBody(),
    requires: [
        'MyApp.view.AgentViewport',
        'MyApp.view.AgentDetailWindow',
        'MyApp.view.AmpAddWindow',
        'MyApp.view.NormalAmpSetWindow',
        'MyApp.view.VMAmpSetWindow',
        'MyApp.view.ILOAmpSetWindow',
        'MyApp.view.ILOConfigWindow'
    ]
});