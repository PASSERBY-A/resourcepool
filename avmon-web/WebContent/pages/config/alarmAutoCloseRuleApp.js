Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    //autoCreateViewport: true,
    name: 'CFG',
    launch: function() {
        Ext.create('Ext.container.Viewport', {
            layout: 'fit',
            border:false,
            items: [
                {
                    id:'alarmAutoCloseRuleDesk', 
	                layout: 'border',
	                border:false,
	                items: [{ 
				            border:false,
				            region: 'center',
							layout: 'fit',
							items:[
								Ext.create('CFG.view.TdAvmonAutoCloseRuleGrid')
							]
				        }
	                ] 
                }
            ]
        });
    }

});
