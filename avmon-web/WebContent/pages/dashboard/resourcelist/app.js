//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    requires: [
        'MyApp.view.MyViewport'
    ],
    models: [
        'Resource'
    ],
    stores: [
        'Resources'
    ],
    views: [
        'MyViewport'
    ],
    autoCreateViewport: true,
    name: 'MyApp',
    launch: function() {
        var p=Ext.getCmp("resourceListPanel");
        var grid=Ext.getCmp("resourceListGrid");
        grid.getStore().load({params:{id:Ext.avmon.currentMoId,biz:encodeURI(Ext.avmon.businessSystem)}});
    }
});
