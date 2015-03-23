Ext.define('CFG.store.RouteConfigGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.NetCheckModel'],
	model: 'CFG.model.NetCheckModel',

	autoLoad: false,
	remoteSort: true,
	pageSize: 20,
	proxy: {
	    type: 'ajax',
	    url : 'netCheckIndex?tableName=tf_avmon_route_config_info',
	    extraParams :{
			deviceIp:null,
			deviceName:null,
			startDate:null,
			endDate:null
		},
		actionMethods: {   
            read: 'POST'  
        }, 
	    reader: {
	        type: 'json',
	        root: 'root',
	        totalProperty: 'total'
	    }
	}
});