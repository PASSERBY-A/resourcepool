Ext.define('MyApp.store.imagesStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.BucketModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
        	autoLoad: true,
        	storeId: 'imagesStore',
            model: 'MyApp.model.BucketModel',
            proxy: {  
	        	type: 'ajax',
	            url: 'diskUse',
                extraParams: {
        			moId: ''
                },
	            reader: {  
	                type: 'json',  
	                root: 'buckets'
	            }  
        }
        }, cfg)]);
    }
});