Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
    'Ext.selection.CheckboxModel' 
]); 
//创建多选 
var selModel = Ext.create('Ext.selection.CheckboxModel');
Ext.define('CFG.view.KpiGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.KpiGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'KpiGrid_grid',
            //iconCls: 'icon-grid',
            border: false,
            columns: [
            	{
	                text: 'KPI',
	                width: 170,
	                sortable: true,
	                dataIndex: 'displayKpi'
	            },{
	                //text: 'KPI',
	                width: 170,
	                sortable: true,
	                hidden:true,
	                dataIndex: 'kpi'
	            }
            ],
            store: { 
			    fields: ['kpi','displayKpi'],
			    remoteSort: false,
				proxy: {
				    type: 'ajax',
				    url : 'kpiList?moId=' + Ext.getDom("moId").value,
				    reader: {
				        type: 'json'
				    }
				},
				autoLoad:true
			},
            selModel:selModel, 
            listeners:{
		    	itemdblclick:function(view, record, item, index, e, eOpts){},
		    	itemclick:function(){},
		    	select:function(){}
		    }
        });
        this.callParent(arguments);
    }
});