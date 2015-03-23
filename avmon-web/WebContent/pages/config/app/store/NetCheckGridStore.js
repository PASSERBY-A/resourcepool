Ext.define('CFG.store.NetCheckGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.NetCheckModel'],
	model: 'CFG.model.NetCheckModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'netCheckIndex',
	    extraParams :{
			deviceIp:null,
			kpi:null,
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