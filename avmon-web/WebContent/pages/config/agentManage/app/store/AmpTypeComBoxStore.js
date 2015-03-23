Ext.define('MyApp.store.AmpTypeComBoxStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.AmpTypeComBoxModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'MyJsonStore2',
            model: 'MyApp.model.AmpTypeComBoxModel',
            proxy: {
                type: 'ajax',
                url: 'getAmpTypeComboxValue',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            },
            listeners : {  
            	beforeload: function(store, operation, options){
            		//设置参数
            		var inOs = Ext.alarm.itemOs;
            		if(inOs){
            			store.proxy.extraParams.os=inOs;	
            		}
            	}
            }
        }, cfg)]);
    }
});