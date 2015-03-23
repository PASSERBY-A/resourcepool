Ext.Loader.setPath('Ext.ux', '../system/commons');
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
    'Ext.selection.CheckboxModel' 
]); 
var treeStore = Ext.create('Ext.data.TreeStore', {
			nodeParam: 'id',
            root: {
                expanded: true,
                text: 'AVMON',
                id: 'root'
            },autoLoad: false,
		    proxy: {
		        type: 'ajax',
		        url: '../pages/alarm/menuTree?viewOnly=true'
		    }
		});
var viewId = Ext.create("Ext.ux.ComboBoxTree", {
			id : 'view_id',
			name : 'view_id',
			store : treeStore,
			fieldLabel : avmon.config.view,
			editable : false,
			multiCascade : false,
			multiSelect : false,
			allowBlank: false,
			labelWidth: 160,
			labelStyle : 'text-align:left;',
			width : 400,
			rootVisible : false
		});
var smsRecvTime = Ext.create('Ext.form.field.ComboBox', {
	labelStyle : 'text-align:left;width:80;',
	width : 340,
	fieldLabel : avmon.alarm.SMSReceivingTime,
	name : 'sms_recv_time',
	xtype : 'combobox',
	store : Ext.create('Ext.data.Store', {
				fields : ['value', 'display'],
				data : [{
							'value' : '0',
							'display' : avmon.alarm.wholeTime
						}, {
							'value' : '1',
							'display' : avmon.alarm.workingTime
						}]
			}),
	queryMode : 'local',
	allowBlank: false,
	editable : false,
	valueField : 'value',
	value:'0',
	displayField : 'display'
});
var gridStore = Ext.create('CFG.store.TdAvmonNotifyRuleGridStore');
editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveUpdateNotifyRuleInfo',
    fieldDefaults:{//统一设置表单字段默认属性
    	labelSeparator :'：',//分隔符
    	labelWidth : 160//标签宽度
    	,width:500
    },
    defaultType: 'textfield',
    items: [
		viewId
	    ,{
            xtype: 'checkboxgroup',
            width: 500,
            columns: 2,
            vertical: true,
            fieldLabel: avmon.alarm.receiveWarningLevel,
            items: [
                {
                    xtype: 'checkboxfield',
                    name: 'alarm_level1',
                    boxLabel: avmon.common.message
                },
                {
                    xtype: 'checkboxfield',
                    name: 'alarm_level2',
                    boxLabel: avmon.alarm.normalAlarm
                },
                {
                    xtype: 'checkboxfield',
                    name: 'alarm_level3',
                    checked:true,
                    boxLabel: avmon.config.minorAlarm
                },
                {
                    xtype: 'checkboxfield',
                    name: 'alarm_level4',
                    checked:true,
                    boxLabel: avmon.alarm.mainAlarm
                },
                {
                    xtype: 'checkboxfield',
                    name: 'alarm_level5',
                    checked:true,
                    boxLabel: avmon.alarm.seriousAlarm
                }
            ]
        }
        ,{
            xtype: 'checkboxgroup',
            width: 460,
            fieldLabel: avmon.alarm.receiveMode,
            items: [
                {
                    xtype: 'checkboxfield',
                    name: 'email_flag',
                    boxLabel: 'Email'
                },
                {
                    xtype: 'checkboxfield',
                    name: 'sms_flag',
           			checked: true,
                    boxLabel: avmon.alarm.SMS
                }
            ]
        }
		,{
	        fieldLabel: avmon.alarm.dailyMaxNumberOfTextMessage,
	        name: 'max_sms_per_day',
	        xtype: 'numberfield',
	        value:30,
	        width : 300,
	        allowBlank: false
	    }
	    ,{
            xtype: 'checkboxfield',
            name: 'enable_flag',
            checked: true,
            fieldLabel: avmon.alarm.whetherStarts
        }
        ,smsRecvTime,{
	        fieldLabel: 'Id',
	        name: 'id',
	        hidden:true
	    }
		,{
	        fieldLabel: avmon.alarm.lastUpdateTime,
	        name: 'last_update_time',
	        width : 400,
	        fieldStyle:'padding-top:3px;background:none; border: 0px solid;',
	        readOnly: true
	    }
	    ,{
	        fieldLabel: 'UserId',
	        name: 'user_id',
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
    height: 333,
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
Ext.define('CFG.view.TdAvmonNotifyRuleGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.TdAvmonNotifyRuleGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'TdAvmonNotifyRule_grid',
            title: avmon.config.alarmViewSubscriptionManagement,
            iconCls: 'icon-grid',
            border: false,
            columns: [
				{
	                text: avmon.config.userId,
	                width: 50,
	                sortable: true,
	                dataIndex: 'user_id'
	            }
				,{
	                text: avmon.alarm.viewID,
	                width: 100,
	                sortable: true,
	                hidden:true,
	                dataIndex: 'view_id'
	            }
	            ,{
	                text: avmon.alarm.viewName,
	                width: 100,
	                sortable: false,
	                dataIndex: 'view_name'
	            }
				,{
	                text: avmon.common.message,
	                width: 80,
	                sortable: true,
	                dataIndex: 'alarm_level1',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.alarm_level1==1){
	                		return avmon.config.yes
	                	}else{
	                		return avmon.config.no;
	                	}
	                }
	            }
				,{
	                text: avmon.alarm.normalAlarm,
	                width: 80,
	                sortable: true,
	                dataIndex: 'alarm_level2',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.alarm_level2==1){
	                		return avmon.config.yes;
	                	}else{
	                		return avmon.config.no;
	                	}
	                }
	            }
				,{
	                text: avmon.config.minorAlarm,
	                width: 80,
	                sortable: true,
	                dataIndex: 'alarm_level3',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.alarm_level3==1){
	                		return avmon.config.yes;
	                	}else{
	                		return avmon.config.no;
	                	}
	                }
	            }
				,{
	                text: avmon.alarm.mainAlarm,
	                width: 80,
	                sortable: true,
	                dataIndex: 'alarm_level4',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.alarm_level4==1){
	                		return avmon.config.yes;
	                	}else{
	                		return avmon.config.no;
	                	}
	                }
	            }
				,{
	                text: avmon.alarm.seriousAlarm,
	                width: 80,
	                sortable: true,
	                dataIndex: 'alarm_level5',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.alarm_level5==1){
	                		return avmon.config.yes;
	                	}else{
	                		return avmon.config.no;
	                	}
	                }
	            }
				,{
	                text: avmon.config.whetherReceiveEmail,
	                width: 83,
	                sortable: true,
	                dataIndex: 'email_flag',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.email_flag==1){
	                		return avmon.config.yes;
	                	}else{
	                		return avmon.config.no;
	                	}
	                }
	            }
				,{
	                text: avmon.config.whetherReceiveSMS,
	                width: 80,
	                sortable: true,
	                dataIndex: 'sms_flag',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.sms_flag==1){
	                		return avmon.config.yes;
	                	}else{
	                		return avmon.config.no;
	                	}
	                }
	            }
				,{
	                text: avmon.alarm.dailyMaxNumberOfTextMessage,
	                width: 100,
	                sortable: true,
	                dataIndex: 'max_sms_per_day'
	            }
				,{
	                text: avmon.config.whetherStart,
	                width: 70,
	                sortable: true,
	                dataIndex: 'enable_flag',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.enable_flag==1){
	                		return avmon.config.yes;
	                	}else{
	                		return avmon.config.no;
	                	}
	                }
	            }
				,{
	                text: avmon.config.SMSAcceptTime,
	                width: 90,
	                sortable: true,
	                dataIndex: 'sms_recv_time',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.sms_recv_time==1){
	                		return avmon.alarm.workingTime;
	                	}else{
	                		return avmon.alarm.wholeTime;
	                	}
	                }
	            }
				,{
	                text: avmon.alarm.lastUpdateTime,
	                width: 140,
	                sortable: true,
	                dataIndex: 'last_update_time'
	            }
				,{
	                text: 'ID',
	                width: 100,
	                sortable: true,
	                hidden:true,
	                dataIndex: 'id'
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
									    url: 'deleteNotifyRuleByIds?ids=' + ids.join(','),
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
		    		
		    		Ext.Ajax.request({
						url : 'findViewById?viewId=' + record.data.view_id,
						success : function(response, opts) {
							var returnJson = Ext.JSON.decode(response.responseText);
							if (returnJson.res == "success") {
								viewId.setDefaultValue(record.data.view_id,returnJson.viewName);
								eidtWin.show();
							} else {
								Ext.MessageBox.alert(avmon.common.reminder,returnJson.msg);
							}
						},
						failure : function(response, opts) {
							Ext.MessageBox.alert(avmon.common.reminder, avmon.config.loadFail);
						}
					});
					
					treeStore.load();
		    	},
		    	itemclick:function(){},
		    	select:function(){}
		    }
        });
        this.callParent(arguments);
    }
});