Ext.define('CFG.store.TdAvmonKpiThresholdGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TdAvmonKpiThresholdModel'],
	model: 'CFG.model.TdAvmonKpiThresholdModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'kpiThresholdIndex',
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