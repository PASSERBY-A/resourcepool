Ext.define('CFG.store.TfAvmonRouteInspectionDevGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TfAvmonRouteInspectionDevModel'],
	model: 'CFG.model.TfAvmonRouteInspectionDevModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 20,
	proxy: {
	    type: 'ajax',
	    url : 'routeDeviceIndex',
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