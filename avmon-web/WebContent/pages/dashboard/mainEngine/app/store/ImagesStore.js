Ext.define('MyApp.store.ImagesStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.BucketModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: true,
            model: 'MyApp.model.BucketModel',//父MODEL
            storeId: 'ImagesStore',
            proxy: {  
                type: 'ajax',
                url: 'multipleBucket',
                reader: {  
                    type: 'json',  
                    root: 'buckets'
                }  
            }  
        }, cfg)]);
    }
});