Ext.define('CFG.store.TdAvmonRegexConfirmGridStore',{
	extend: 'Ext.data.Store',
	requires: ['CFG.model.TdAvmonRegexConfirmModel'],
	model: 'CFG.model.TdAvmonRegexConfirmModel',

	autoLoad: true,
	remoteSort: true,
	pageSize: 30,
	proxy: {
	    type: 'ajax',
	    url : 'regexConfirmindex',
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