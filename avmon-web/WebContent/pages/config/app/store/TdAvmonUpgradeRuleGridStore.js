Ext.define('CFG.store.TdAvmonUpgradeRuleGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TdAvmonUpgradeRuleModel'],
	model: 'CFG.model.TdAvmonUpgradeRuleModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'alarmUpgradeRuleIndex',
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