Ext.define('SYS.store.PropertiesTypeComBoxStore', {
    extend: 'Ext.data.Store',

    requires: [
        'SYS.model.PropertiesTypeComBoxModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'MyJsonStore2',
            model: 'SYS.model.PropertiesTypeComBoxModel',
            proxy: {
                type: 'ajax',
                url: '../config/getPropertiesTypeComboxValue',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});