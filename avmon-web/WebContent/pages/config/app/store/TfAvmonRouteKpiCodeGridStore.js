Ext.define('CFG.store.TfAvmonRouteKpiCodeGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TfAvmonRouteKpiCodeModel'],
	model: 'CFG.model.TfAvmonRouteKpiCodeModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 20,
	proxy: {
	    type: 'ajax',
	    url : 'routeKpiIndex',
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