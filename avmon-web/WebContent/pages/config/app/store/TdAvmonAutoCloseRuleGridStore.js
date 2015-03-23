Ext.define('CFG.store.TdAvmonAutoCloseRuleGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TdAvmonAutoCloseRuleModel'],
	model: 'CFG.model.TdAvmonAutoCloseRuleModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'alarmAutoCloseRuleIndex',
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