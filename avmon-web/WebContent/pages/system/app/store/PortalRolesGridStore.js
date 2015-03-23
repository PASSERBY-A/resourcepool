Ext.define('SYS.store.PortalRolesGridStore',{
	extend: 'Ext.data.Store',
	requires: ['SYS.model.PortalRolesModel'],
	model: 'SYS.model.PortalRolesModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'roleIndex',
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