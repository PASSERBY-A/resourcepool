//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.Loader.setPath('Ext.ux', '../pages/commons/ux');
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
    'Ext.ux.PopupField',
//    'Ext.ux.form.SearchField', 
    'Ext.selection.CheckboxModel',
    'CFG.view.TdAvmonKpiInfoAutoCloseRuleGrid' 
]); 
var gridStore = Ext.create('CFG.store.TdAvmonAutoCloseRuleGridStore');
var pupKpiGridPanel = Ext.create('CFG.view.TdAvmonKpiInfoAutoCloseRuleGrid',{pup:true,noPup:false});
var pupKpiWin = Ext.create('Ext.window.Window',{
    title: avmon.config.selectKPI,
    height: 370,
    width:850,
    hidden: true,
    iconCls:'icon-form',
    layout: 'fit',
    plain: true,
    modal:true,
    closable: true,
    closeAction: 'hide',
    items: [pupKpiGridPanel]
});
function pupKpiGrid(){
	pupKpiWin.show();
	var selection = pupKpiGridPanel.getSelectionModel().getSelection();
	if(selection.length > 0){
		kpiGridStore = pupKpiGridPanel.store;
		kpiGridStore.load();
	}
}
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveUpdateAlarmAutoCloseRuleInfo',
    fieldDefaults:{//统一设置表单字段默认属性
    	labelSeparator :' ',//分隔符
    	labelWidth : 160//标签宽度
    	,labelStyle : 'text-align:left'
    	,width:440
    },
    defaultType: 'textfield',
    items: [
		{
	        fieldLabel: 'Id',
	        name: 'id',
	        hidden:true
	    }
	    ,{
	        fieldLabel: avmon.config.monitorObjectNameOrIP,
	        name: 'mo',
	        allowBlank: false
	    }
	    ,{
	        fieldLabel: avmon.alarm.alarmLevel,
	        name: 'grade',
	        allowBlank: false
	    }
		,{
	        fieldLabel: 'KPI',
	        name: 'kpi',
	        itemId:'kpi',
	        maxLength: 255,
	        xtype:'popupfield',
	        allowBlank: false,
		    onTriggerClick : pupKpiGrid
	    }
		,{
	        fieldLabel: avmon.alarm.alarmContent,
	        name: 'content',
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.clearingTimeMinutes,
	        name: 'timeout',
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
pupKpiGridPanel.pupKpiWin = pupKpiWin;
pupKpiGridPanel.autoCloseRuleForm = editForm;
var eidtWin = Ext.create('Ext.window.Window',{
    title: avmon.config.add,
    height: 230,
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
Ext.define('CFG.view.TdAvmonAutoCloseRuleGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.TdAvmonAutoCloseRuleGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'TdAvmonAutoCloseRule_grid',
            title: avmon.config.alarmAutomaticallyClearRules,
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
	                text: avmon.alarm.alarmLevel,
	                width: 100,
	                sortable: true,
	                dataIndex: 'grade'
	            }
				,{
	                text: 'KPI',
	                width: 100,
	                sortable: true,
	                dataIndex: 'kpi'
	            }
				,{
	                text: avmon.alarm.alarmContent,
	                width: 200,
	                sortable: true,
	                dataIndex: 'content'
	            }
				,{
	                text: avmon.config.clearingTimeMinutes,
	                width: 100,
	                sortable: true,
	                dataIndex: 'timeout'
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
                    	editForm.getForm().findField("timeout").setValue("0");
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
									    url: 'deleteAlarmAutoCloseRuleByIds?ids=' + ids.join(','),
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