Ext.define('SYS.store.TdAvmonKpiInfoGridStore',{
	extend: 'Ext.data.Store',
	requires: ['SYS.model.TdAvmonKpiInfoModel'],
	model: 'SYS.model.TdAvmonKpiInfoModel',

//	autoLoad: false,
	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : '../config/findKpiGridInfo',
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