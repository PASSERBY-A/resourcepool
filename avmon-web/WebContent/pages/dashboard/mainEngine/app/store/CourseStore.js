Ext.define('MyApp.store.CourseStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.CourseModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.CourseModel',
            storeId: 'CourseStore',
            proxy: {
                type: 'ajax',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});