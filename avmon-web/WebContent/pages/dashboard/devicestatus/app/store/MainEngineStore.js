Ext.define('MyApp.store.MainEngineStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.MainEngineModel'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            storeId: 'MainEngineStore',
            model: 'MyApp.model.MainEngineModel',
            proxy: {
                type: 'ajax',
                extraParams: {
                    mo: '',
                    condition: ''
                },
                url: 'mainEngine',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});