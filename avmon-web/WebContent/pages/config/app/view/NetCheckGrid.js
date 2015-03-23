//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
//    'Ext.ux.form.SearchField', 
    'Ext.selection.CheckboxModel' 
]); 

var gridStore = Ext.create('CFG.store.NetCheckGridStore');
//创建多选 
var selModel = Ext.create('Ext.selection.CheckboxModel');
//创建搜索框，后台通过query参数获取查询条件
Ext.define('CFG.view.NetCheckGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.NetCheckGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'NetCheck_grid',
            title: avmon.config.networkInspectionData,
            iconCls: 'icon-grid',
            border: false,
            columns: [
            
				{
	                text: '',
	                width: 40,
	                sortable: true,
	                dataIndex: 'kpi_status',
	                renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
						if(value=="OK"){
							return "<img style='width:16px; height:16px;' src='../alarm/image/level0.png'></img>";
						}else if(value=="-"){
							return "  -";
						}else if(value=="WARN"){
							return "<img style='width:16px; height:16px;' src='../alarm/image/level2.png'></img>";
						}else if(value=="ERROR"){
							return "<img style='width:16px; height:16px;' src='../alarm/image/level4.png'></img>";
						}
                    }
	            },
	            {
	                text: avmon.config.monitorType,
	                width: 100,
	                sortable: true,
	                dataIndex: 'monitor_instance_id'
	            }
				,{
	                text: avmon.config.deviceIP,
	                width: 110,
	                sortable: true,
	                dataIndex: 'device_ip'
	            }
				,{
	                text: avmon.config.deviceType,
	                width: 110,
	                sortable: true,
	                dataIndex: 'device_type'
	            }
				,{
	                text: 'KPI',
	                width: 150,
	                sortable: true,
	                dataIndex: 'kpi_id'
	            }
				,{
	                text: avmon.kpiCompare.pkiValue,
	                width: 200,
	                sortable: true,
	                dataIndex: 'str_value'
	            }
	            ,{
	                text: avmon.config.kpiThresholdValue,
	                width: 100,
	                sortable: true,
	                dataIndex: 'kpi_threshold'
	            }
				,{
	                text: avmon.config.updateTime,
	                width: 200,
	                sortable: true,
	                dataIndex: 'last_update_time'
	            }
            ],
            store: gridStore,
            //selModel:selModel, 
            dockedItems: [
	        	{
	            xtype: 'toolbar',
	            items: [
	                {
                        xtype: 'textfield',
                        width: 180,
                        itemId:'deviceIp',
                        fieldLabel: avmon.config.deviceIP,
                        labelWidth: 50
                    },
                    {
                        xtype: 'textfield',
                        width: 180,
                        itemId:'kpi',
                        fieldLabel: 'KPI',
                        labelWidth: 50
                    },
                    {
                        xtype: 'datefield',
                        itemId: 'start_date',
                        width: 140,
                        fieldLabel: avmon.config.time,
                        labelWidth: 40,
                        format: 'Y-m-d'//,
//                        value: Ext.util.Format.date(new Date(), 'Y-m-d')
                    },
                    {
                        xtype: 'timefield',
                        itemId: 'start_time',
                        width: 58,
                        value: '00:00',
                        fieldLabel: '',
                        editable: false,
                        format: 'H:i',
                        increment: 60
                    },
                    {
                        xtype: 'datefield',
                        itemId: 'end_date',
                        width: 120,
                        fieldLabel: avmon.config.to,
                        labelWidth: 20,
                        editable: false,
                        format: 'Y-m-d'//,
//                        value: Ext.util.Format.date(new Date(), 'Y-m-d')
                    },
                    {
                        xtype: 'timefield',
                        itemId: 'end_time',
                        width: 58,
                        value: '23:00',
                        fieldLabel: '',
                        editable: false,
                        format: 'H:i',
                        increment: 60
                    },
                    {
                        xtype: 'tbseparator'
                    },
	            	{
		                text: avmon.config.search,
		                iconCls: 'icon-search',
		                handler: function() {
							var toolBar = this.ownerCt;
					        var grid = this.ownerCt.ownerCt;
					        var deviceIp = toolBar.down('#deviceIp').getValue();
					        var kpi = toolBar.down('#kpi').getValue();
							var extraParams = grid.store.proxy.extraParams;
					        if(deviceIp.length>0){
					        	extraParams.deviceIp=deviceIp;
					        }
					        if(kpi.length>0){
					        	extraParams.kpi=kpi;
					        }
					        if(toolBar.down('#start_date').getRawValue().length>0){
					        	var startDate = toolBar.down('#start_date').getRawValue() + " " + toolBar.down('#start_time').getRawValue() + ":00";
					        	extraParams.startDate = startDate;
					        }
					        if(toolBar.down('#end_date').getRawValue().length>0){
					        	var endDate = toolBar.down('#end_date').getRawValue() + " " + toolBar.down('#end_time').getRawValue() + ":00";
					        	extraParams.endDate = endDate;
					        }
					        grid.store.load();
		                }
		            },
		            {
                        xtype: 'tbseparator'
                    },
	            	{
		                text: avmon.config.all,
		                iconCls: 'icon-search',
		                handler: function() {
							var toolBar = this.ownerCt;
					        var grid = this.ownerCt.ownerCt;
					        toolBar.down('#deviceIp').reset();
					        toolBar.down('#kpi').reset();
					        toolBar.down('#start_date').reset();
					        toolBar.down('#end_date').reset();
					        var extraParams = grid.store.proxy.extraParams;
				        	extraParams.deviceIp=null;
				        	extraParams.kpi=null;
				        	extraParams.startDate = null;
				        	extraParams.endDate = null;
					        
					        grid.store.load();
		                }
		            }
            ] 
        },
        	{
                xtype: 'pagingtoolbar',
                store: gridStore,
                dock: 'bottom',
                displayInfo: true,
                beforePageText : avmon.config.beforePageText,
    			afterPageText : avmon.config.afterPageText,
                displayMsg: avmon.config.displayMsg, 
                emptyMsg: avmon.config.emptyMsg 
            }],
            listeners:{
		    	itemdblclick:function(view, record, item, index, e, eOpts){},
		    	itemclick:function(){},
		    	select:function(){}
		    }
        });
        this.callParent(arguments);
    }
});