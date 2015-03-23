Ext.define('CFG.store.TdAvmonFilterRuleGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TdAvmonFilterRuleModel'],
	model: 'CFG.model.TdAvmonFilterRuleModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'alarmFilterRuleIndex',
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