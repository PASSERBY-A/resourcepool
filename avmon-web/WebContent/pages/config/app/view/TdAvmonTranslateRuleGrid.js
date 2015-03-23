//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
//    'Ext.ux.form.SearchField', 
    'Ext.selection.CheckboxModel' 
]); 

var gridStore = Ext.create('CFG.store.TdAvmonTranslateRuleGridStore');
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveUpdateAlarmTranslateRuleInfo',
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
	        fieldLabel: avmon.config.monitorObjectNameOrIP,
	        name: 'mo',
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.originalContent,
	        name: 'content',
	        xtype:'textarea',
	        height:100,
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.translatedContent,
	        name: 'translated_content',
	        xtype:'textarea',
	        height:100,
	        allowBlank: false
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
    height: 330,
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
Ext.define('CFG.view.TdAvmonTranslateRuleGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.TdAvmonTranslateRuleGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'TdAvmonTranslateRule_grid',
            title: "告警翻译规则",
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
	                text: avmon.config.monitoringObject,
	                width: 100,
	                sortable: true,
	                dataIndex: 'mo'
	            }
				,{
	                text: avmon.config.originalContent,
	                width: 300,
	                sortable: true,
	                dataIndex: 'content'
	            }
				,{
	                text: avmon.config.translatedContent,
	                width: 300,
	                sortable: true,
	                dataIndex: 'translated_content'
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
									    url: 'deleteAlarmTranslateRuleByIds?ids=' + ids.join(','),
									    success: function(response, opts) {
									    	var returnJson = Ext.JSON.decode(response.responseText);
									    	Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
											panelStore.load();
									    },
									    failure: function(response, opts) {
									    	
									    }
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