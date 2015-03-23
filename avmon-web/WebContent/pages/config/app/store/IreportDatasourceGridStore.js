Ext.define('CFG.store.IreportDatasourceGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.IreportDatasourceModel'],
	model: 'CFG.model.IreportDatasourceModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'reportDatasourceIndex',
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