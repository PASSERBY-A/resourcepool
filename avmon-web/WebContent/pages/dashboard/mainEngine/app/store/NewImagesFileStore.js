Ext.define('MyApp.store.NewImagesFileStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.NewBucketModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
        	autoLoad: true,
        	storeId: 'NewImagesStore',
            model: 'MyApp.model.NewBucketModel',
            proxy: {  
	        	type: 'ajax',
	            url: 'fileSysUse',
                extraParams: {
        			mo: ''
                },
	            reader: {  
	                type: 'json',  
	                root: 'buckets'
	            }  
        }
        }, cfg)]);
    }
});