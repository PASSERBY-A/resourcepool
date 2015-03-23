Ext.define('CFG.store.TdAvmonTranslateRuleGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TdAvmonTranslateRuleModel'],
	model: 'CFG.model.TdAvmonTranslateRuleModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'alarmTranslateRuleIndex',
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