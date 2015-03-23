Ext.define('CFG.store.TdAvmonKpiInfoGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TdAvmonKpiInfoModel'],
	model: 'CFG.model.TdAvmonKpiInfoModel',

//	autoLoad: false,
	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'findKpiGridInfo',
	    extraParams :{
			kpiCode:null,
			kpiName:null
		},
	    reader: {
	        type: 'json',
	        root: 'root',
	        totalProperty: 'total'
	    }
	}
});