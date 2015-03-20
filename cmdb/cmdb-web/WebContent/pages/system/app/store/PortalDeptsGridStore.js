Ext.define('SYS.store.PortalDeptsGridStore',{
	extend: 'Ext.data.Store',
	requires: ['SYS.model.PortalDeptsModel'],
	model: 'SYS.model.PortalDeptsModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'deptIndex',
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