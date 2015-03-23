//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
//    'Ext.ux.form.SearchField', 
    'Ext.selection.CheckboxModel' 
]); 
var gridStore = Ext.create('CFG.store.IreportDatasourceGridStore');
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveReportDatasourceInfo',
    //layout: 'column',
    fieldDefaults:{//统一设置表单字段默认属性
    	labelSeparator :' ',//分隔符
    	labelWidth : 160//标签宽度
    	,width:440
    },
    defaultType: 'textfield',
    items: [
		{
	        fieldLabel: 'Id',
	        name: 'id',
	        hidden: true
	    }
		,{
	        fieldLabel: avmon.config.name,
	        name: 'title',
	        allowBlank: false
	    }
		,{
	        fieldLabel: 'Driver',
	        name: 'driver',
	        allowBlank: false
	    }
		,{
	        fieldLabel: 'Url',
	        name: 'url',
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.userName,
	        name: 'user',
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.password,
	        name: 'password',
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.updateTime,
	        name: 'updated_dt',
	        readOnly:true
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
    height: 250,
    width:550,
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
Ext.define('CFG.view.IreportDatasourceGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.IreportDatasourceGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'IreportDatasource_grid',
            title: avmon.config.reportDataSource,
            iconCls: 'icon-grid',
            border: false,
            columns: [
				{
	                text: 'Id',
	                width: 40,
	                sortable: true,
	                dataIndex: 'id'
	            }
				,{
	                text: avmon.config.name,
	                width: 100,
	                sortable: true,
	                dataIndex: 'title'
	            }
				,{
	                text: 'Driver',
	                width: 200,
	                sortable: true,
	                dataIndex: 'driver'
	            }
				,{
	                text: 'Url',
	                width: 200,
	                sortable: true,
	                dataIndex: 'url'
	            }
				,{
	                text: avmon.config.userName,
	                width: 100,
	                sortable: true,
	                dataIndex: 'user'
	            }
				,{
	                text: avmon.config.password,
	                width: 100,
	                sortable: true,
	                dataIndex: 'password'
	            }
				,{
	                text: avmon.config.updateTime,
	                width: 200,
	                sortable: true,
	                dataIndex: 'updated_dt'
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
									    url: 'deleteReportDatasourceByIds?ids=' + ids.join("','"),
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
                //,'-',searchField
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
		    	itemclick:function(){
		    		//alert("itemclick");
		    	},
		    	select:function(){
		    		//alert("select");
		    	}
		    }
        });
        this.callParent(arguments);
    }
});