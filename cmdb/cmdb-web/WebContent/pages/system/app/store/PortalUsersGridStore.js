Ext.define('SYS.store.PortalUsersGridStore',{
	extend: 'Ext.data.Store',
	requires: ['SYS.model.PortalUsersModel'],
	model: 'SYS.model.PortalUsersModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'userIndex',
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