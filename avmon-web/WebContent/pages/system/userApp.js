Ext.Loader.setConfig({
    enabled: true
});

Ext.application({
    //autoCreateViewport: true,
    name: 'SYS',
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            border: false,
            items: [
                {
                    id:'desk', 
	                layout: 'border', 
	                border: false,
	                items: [{ 
				            region: 'center',
							layout: 'fit',
							border: false,
							items:[
								Ext.create('SYS.view.PortalUsersGrid')
							]
				        }
	                ] 
                }
            ]
        });
    }

});
