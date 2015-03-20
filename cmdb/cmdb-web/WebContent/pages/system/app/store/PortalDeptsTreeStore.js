Ext.define('SYS.store.PortalDeptsTreeStore',{
	extend: 'Ext.data.TreeStore',
	requires: ['SYS.model.PortalDeptsModel'],
	model: 'SYS.model.PortalDeptsModel',
//	fields: ['dept_id','dept_name'],
    autoLoad: true,
    proxy: {
        type: 'ajax',
        url: 'findAllDepts'
    },
    extraParams:{  
		id:'0'  
	},
	nodeParam:'parent_id',
//	listeners:{
//    	load:function(){
//    		alert(this);
//    		Ext.getCmp("system_menus").expandAll();
//    	}
//    },
    root: {
    	expanded: true,
    	text: 'HP',
    	id: '0'
    }
});