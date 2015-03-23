Ext.define('CFG.store.IreportMgtGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.IreportMgtModel'],
	model: 'CFG.model.IreportMgtModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'reportTemplateIndex',
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