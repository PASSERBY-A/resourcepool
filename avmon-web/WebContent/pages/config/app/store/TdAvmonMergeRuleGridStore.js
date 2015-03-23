Ext.define('CFG.store.TdAvmonMergeRuleGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TdAvmonMergeRuleModel'],
	model: 'CFG.model.TdAvmonMergeRuleModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'alarmMergeRuleIndex',
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