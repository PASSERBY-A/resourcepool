//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
    'Ext.selection.CheckboxModel' 
]); 

var gridStore = Ext.create('CFG.store.TfAvmonRouteInspectionDevGridStore');
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveUpdateRouteDeviceInfo',
    fieldDefaults:{//统一设置表单字段默认属性
    	labelSeparator :'：',//分隔符
    	labelWidth : 90//标签宽度
    	,width:340
    },
    defaultType: 'textfield',
    items: [
		{
	        fieldLabel: 'Id',
	        name: 'id',
			hidden:true
	    }
		,{
	        fieldLabel: avmon.config.devicename,
	        name: 'device_name',
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.deviceType,
	        name: 'device_type',
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.deviceIP,
	        name: 'device_ip',
	        allowBlank: false
	    }
		,{
	    	//labelStyle : 'text-align:right;width:80;',
	    	//columnWidth: .5 ,
	        fieldLabel: avmon.config.deviceDescription,
	        name: 'device_desc',
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.deviceStatus,
	        name: 'device_status',
	        allowBlank: false
	    }
	    
		,{
	        fieldLabel: 'DeviceOthrer1',
	        name: 'device_othrer1',
	        hidden:true
	    }
		,{
	        fieldLabel: 'DeviceOthrer2',
	        name: 'device_othrer2',
	        hidden:true
	    }
		,{
	        fieldLabel: avmon.config.recentReportTime,
	        name: 'report_date',
	        hidden:true
	    }
    ],
    buttons: [{
        text: avmon.common.save,
        formBind: true, //only enabled once the form is valid
        disabled: true,
        iconCls:'icon-save',
        handler: function() {
            var submitForm = this.up('form').getForm();
            if (submitForm.isValid()) {
                submitForm.submit({
                    method: 'POST',
                    waitMsg:avmon.common.saving,
                    success: function(form, action) {
                       Ext.MessageBox.alert(avmon.common.reminder, action.result.msg);
                       gridStore.load();
                       eidtWin.hide();
                    }
                });
            }
        }
    },{
        text: avmon.common.close,
        iconCls:'icon-close',
        handler: function() {
        	this.up('form').getForm().reset();
            eidtWin.hide();
        }
    }]
});
var eidtWin = Ext.create('Ext.window.Window',{
    title: avmon.config.add,
    height: 220,
    width:400,
    hidden: true,
    iconCls:'icon-form',
    layout: 'fit',
    plain: true,
    modal:true,
    closable: true,
    closeAction: 'hide',
    items: [editForm]
});
//创建多选 
var selModel = Ext.create('Ext.selection.CheckboxModel');
//创建搜索框，后台通过query参数获取查询条件
Ext.define('CFG.view.TfAvmonRouteInspectionDevGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.TfAvmonRouteInspectionDevGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'TfAvmonRouteInspectionDev_grid',
            title: avmon.config.networkMonitoringDeviceManagement,
            iconCls: 'icon-grid',
            border: false,
            columns: [
				{
	                text: 'Id',
	                width: 100,
	                sortable: true,
	                hidden:true,
	                dataIndex: 'id'
	            }
				,{
	                text: avmon.config.devicename,
	                width: 150,
	                sortable: true,
	                dataIndex: 'device_name'
	            }
				,{
	                text: avmon.config.deviceType,
	                width: 100,
	                sortable: true,
	                dataIndex: 'device_type'
	            }
				,{
	                text: avmon.config.deviceIP,
	                width: 100,
	                sortable: true,
	                dataIndex: 'device_ip'
	            }
				,{
	                text: avmon.config.deviceDescription,
	                width: 100,
	                sortable: true,
	                dataIndex: 'device_desc'
	            }
				,{
	                text: avmon.config.deviceStatus,
	                width: 100,
	                sortable: true,
	                hidden:true,
	                dataIndex: 'device_status'
	            }
				,{
	                text: avmon.config.lastestReportTime,
	                width: 280,
	                sortable: true,
	                dataIndex: 'report_date'
	            }
            ],
            store: gridStore,
            selModel:selModel, 
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: avmon.config.add,
                    iconCls: 'icon-add',
                    handler: function() {
                    	editForm.getForm().reset();
                    	eidtWin.setTitle(avmon.config.add);
                    	eidtWin.show();
                    }
                },
                '-', {
                    itemId: 'delete',
                    text: avmon.common.deleted,
                    id:'delete_button',
                    iconCls: 'icon-delete',
                    handler: function() {
                        var selection = this.up("panel").getSelectionModel().getSelection();
                        var panelStore = this.up("panel").getStore();
                        if(selection.length == 0){ 
	                        Ext.MessageBox.alert(avmon.common.reminder,avmon.config.selectOperateLine);
	                        return; 
	                    }else{ 
	                    	Ext.MessageBox.confirm(avmon.common.reminder, avmon.config.whetherToDelete, function(btn){
								if(btn=="yes"){			   	      
									var ids = []; 
								    Ext.each(selection,function(item){ 
								       ids.push(item.data.id); 
								    });
									Ext.Ajax.request({
									    url: 'deleteRouteDeviceByIds?ids=' + ids.join(','),
									    success: function(response, opts) {
									    	var returnJson = Ext.JSON.decode(response.responseText);
									    	Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
											panelStore.load();
									    },
									    failure: function(response, opts) {}
								    });
							    }
	                    	});
                        }
                    }
                }
	            ] 
            },{
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
		    	itemdblclick:function(view, record, item, index, e, eOpts){
		    		eidtWin.setTitle(avmon.config.modify);
		    		editForm.loadRecord(record);
		    		eidtWin.show();
		    	},
		    	itemclick:function(){},
		    	select:function(){}
		    }
        });
        this.callParent(arguments);
    }
});