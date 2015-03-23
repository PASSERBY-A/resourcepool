//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
//    'Ext.ux.form.SearchField', 
    'Ext.selection.CheckboxModel' 
]); 
var gridStore = Ext.create('CFG.store.RouteConfigGridStore');
//创建多选 
var selModel = Ext.create('Ext.selection.CheckboxModel');
//创建搜索框，后台通过query参数获取查询条件
Ext.define('CFG.view.RouteConfigGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.RouteConfigGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'ConfigBck_grid',
            title: avmon.config.backupConfigurationInformation,
            iconCls: 'icon-grid',
            border: false,
            columns: [
				{
	                text: avmon.config.devicename,
	                width: 110,
	                sortable: false,
	                dataIndex: 'device_name'
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
	                text: avmon.config.configuartionFile,
	                width: 300,
	                sortable: true,
	                dataIndex: 'str_value'
	            }
				,{
	                text: avmon.config.updateTime,
	                width: 200,
	                sortable: true,
	                dataIndex: 'last_update_time'
	            }
	            ,{
	                text: avmon.config.operation,
	                width: 100,
	                sortable: true,
					renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	return "<a href='#' onclick='downLoadFile(\"" + record.data.str_value + "\")'>下载</a>"
	                }
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
                        width: 190,
                        itemId:'deviceName',
                        fieldLabel: avmon.config.devicename,
                        labelWidth: 70
                    },
                	{
                        xtype: 'textfield',
                        width: 180,
                        itemId:'deviceIp',
                        fieldLabel: avmon.config.deviceIP,
                        labelWidth: 50
                    },
                    {
                        xtype: 'datefield',
                        itemId: 'start_date',
                        width: 140,
                        fieldLabel: avmon.config.time,
                        labelWidth: 40,
                        format: 'Y-m-d',
                        value: Ext.util.Format.date(new Date(), 'Y-m-d')
                    },
                    {
                        xtype: 'timefield',
                        itemId: 'start_time',
                        width: 58,
                        value: '00:00',
                        fieldLabel: '',
                        editable: false,
                        hidden:true,
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
                        format: 'Y-m-d',
                        value: Ext.util.Format.date(new Date(), 'Y-m-d')
                    },
                    {
                        xtype: 'timefield',
                        itemId: 'end_time',
                        width: 58,
                        value: '23:59',
                        fieldLabel: '',
                        editable: false,
                        hidden:true,
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
					        var deviceName = toolBar.down('#deviceName').getValue();
							var extraParams = grid.store.proxy.extraParams;
					        if(deviceIp.length>0){
					        	extraParams.deviceIp=deviceIp;
					        }
					        if(deviceName.length>0){
					        	extraParams.deviceName=deviceName;
					        }
					        
					        if(toolBar.down('#start_date').getRawValue().length>0){
					        	var startDate = toolBar.down('#start_date').getRawValue() + " " + toolBar.down('#start_time').getRawValue() + ":00";
					        	extraParams.startDate = startDate;
					        }
					        if(toolBar.down('#end_date').getRawValue().length>0){
					        	var endDate = toolBar.down('#end_date').getRawValue() + " " + toolBar.down('#end_time').getRawValue() + ":59";
					        	extraParams.endDate = endDate;
					        }
					        
					        grid.store.load();
					        
		                },
		                listeners: {
			                afterrender: function(abstractcomponent, option){
			                	//abstractcomponent.fire
				                var toolBar = this.ownerCt;
						        var grid = this.ownerCt.ownerCt;
						        var deviceIp = toolBar.down('#deviceIp').getValue();
								var extraParams = grid.store.proxy.extraParams;
						        						        
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