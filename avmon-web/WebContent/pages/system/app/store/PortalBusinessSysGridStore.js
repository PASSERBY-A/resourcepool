Ext.define('SYS.store.PortalBusinessSysGridStore',{
	extend: 'Ext.data.Store',
	requires: ['SYS.model.PortalBusinessSysModel'],
	model: 'SYS.model.PortalBusinessSysModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'businessSysIndex',
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