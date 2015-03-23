Ext.define('CFG.store.TfAvmonRouteInspectionKpiGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TfAvmonRouteInspectionKpiModel'],
	model: 'CFG.model.TfAvmonRouteInspectionKpiModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 20,
	proxy: {
	    type: 'ajax',
	    url : 'routeKpiThresholdIndex',
	    actionMethods: {   
            read: 'POST'  
        },
		extraParams :{
			deviceIp:null,
			kpi:null
		},
	    reader: {
	        type: 'json',
	        root: 'root',
	        totalProperty: 'total'
	    }
	}
});