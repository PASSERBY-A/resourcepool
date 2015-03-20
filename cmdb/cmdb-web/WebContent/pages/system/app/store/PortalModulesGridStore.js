Ext.define('SYS.store.PortalModulesGridStore',{
	extend: 'Ext.data.Store',
	requires: ['SYS.model.PortalModulesModel'],
	model: 'SYS.model.PortalModulesModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 100,
	proxy: {
	    type: 'ajax',
	    url : 'moduleIndex',
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