Ext.define('MyApp.store.AmpInstAttrStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.AmpInstAttrModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            storeId: 'MyJsonStore3',
            model: 'MyApp.model.AmpInstAttrModel',
            proxy: {
                type: 'ajax',
                extraParams: {
                    agentId: null,
                    ampInstId: null
                },
                url: 'findAmpInstAttrGridInfo',
                reader: {
                    type: 'json',
                    root: 'root'
                }
            }
        }, cfg)]);
    }
});