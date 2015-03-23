Ext.define('MyApp.store.CaseJsonStore', {
    extend: 'Ext.data.Store',
    requires: [
        'MyApp.model.CaseModel',
        'Ext.data.proxy.Ajax',
        'Ext.data.reader.Json'
    ],
    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            autoLoad: false,
            model: 'MyApp.model.CaseModel',
            storeId: 'MyJsonStore7',
            proxy: {
                type: 'ajax',
                extraParams: {
                    mo: document.getElementById('mo').value
                },
                url: 'getCaseList',
                reader: {
                    type: 'json',
                    root: 'record'
                }
            }
        }, cfg)]);
    }
});