Ext.define('SYS.store.PortalMoPropertiesGridStore',{
	extend: 'Ext.data.Store',
	requires: ['SYS.model.PortalMoPropertiesModel'],
	model: 'SYS.model.PortalMoPropertiesModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'moPropertiesIndex',
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