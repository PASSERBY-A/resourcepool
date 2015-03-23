Ext.define('CFG.store.TdAvmonNotifyRuleGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TdAvmonNotifyRuleModel'],
	model: 'CFG.model.TdAvmonNotifyRuleModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'notifyRuleIndex',
	    extraParams :{
			gridParam:null
		},
	    reader: {
	        type: 'json',
	        root: 'root',
	        totalProperty: 'total'
	    }
	}
});