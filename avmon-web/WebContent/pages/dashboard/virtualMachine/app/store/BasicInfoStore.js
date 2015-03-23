Ext.define('MyApp.store.BasicInfoStore', {
    extend: 'Ext.data.Store',

    requires: [
        'MyApp.model.BasicInfoModel'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'MyApp.model.BasicInfoModel',
            storeId: 'BasicInfoStore',
            proxy: {
                type: 'ajax',
                extraParams: {
                    treeId: ''
                },
                url: 'getBasicInfoData',
                reader: {
                    type: 'json',
                    root: 'basicData',
                    totalProperty: 'basicTotal'
                }
            }
        }, cfg)]);
    }
});