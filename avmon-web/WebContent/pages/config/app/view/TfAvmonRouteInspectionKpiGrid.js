//Ext.Loader.setPath('Ext.ux', '../system/commons');
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
    //'Ext.ux.PopupField', 
    'Ext.selection.CheckboxModel' 
]); 
var kpiStore = Ext.create('Ext.data.Store', {
			proxy: {
                type: 'ajax',
                url: 'routeKpiList',
                reader: {
                    type: 'json'
                }
            },
            autoLoad: true,
            fields: [
                {
                    name: 'kpi_id'
                },{
                    name: 'kpi_name'
                }
            ]
		});
var kpiId = Ext.create("Ext.form.field.ComboBox", {
			id : 'kpi_id',
			name : 'kpi_id',
			store : kpiStore,
			fieldLabel : 'KPI',
			editable : true,
			labelStyle : 'text-align:left;width:80;',
			width : 340,
			displayField: 'kpi_name',
            hiddenName: 'kpi_id',
            valueField: 'kpi_id',
	        allowBlank: false,
	        selectOnFocus:true,triggerAction: 'all',typeAhead: true, forceSelection:true,
			listeners : {  
	            'beforequery':function(e){  
	                var combo = e.combo;    
	                if(!e.forceAll){    
	                    var input = e.query;  
	                    // 检索的正则   
	                    var regExp = new RegExp(".*" + input + ".*");  
	                    // 执行检索   
	                    combo.store.filterBy(function(record,id){    
	                        // 得到每个record的项目名称值   
	                        var text = record.get(combo.displayField);    
	                        return regExp.test(text);   
	                    });  
	                    combo.expand();    
	                    return false;  
	                }  
	            }  
	        }
		});
var kipType = Ext.create('Ext.form.field.ComboBox', {
			labelStyle : 'text-align:left;width:80;',
			width : 340,
			fieldLabel : avmon.config.thresholdValueType,
			name : 'kpi_type',
			xtype : 'combobox',
			store : Ext.create('Ext.data.Store', {
						fields : ['value', 'display'],
						data : [{
									'value' : 'S',
									'display' : avmon.config.string
								}, {
									'value' : 'C',
									'display' : avmon.config.temperature
								}, {
									'value' : 'I',
									'display' : avmon.config.integer
								}]
					}),
			queryMode : 'local',
			editable : false,
			valueField : 'value',
			displayField : 'display'  
		});
var gridStore = Ext.create('CFG.store.TfAvmonRouteInspectionKpiGridStore');
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveUpdateRouteKpiThresholdInfo',
    fieldDefaults:{//统一设置表单字段默认属性
    	labelSeparator :'：',//分隔符
    	labelWidth : 80//标签宽度
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
            xtype: 'triggerfield',
            id:'device_ip',
            name: 'device_ip',
	        allowBlank: false,
	        readOnly:false,
            fieldLabel: avmon.config.deviceIP,
            triggerCls: 'x-form-search-trigger',
			triggerWrapCls: 'x-form-trigger-wrap',
			triggerNoEditCls: 'x-trigger-noedit',
            onTriggerClick:function(){
            	if(eidtWin.title==avmon.config.add){
            		Ext.create('CFG.view.DeviceWindow').show();
            	}else{
            		Ext.Msg.alert(avmon.common.reminder,avmon.config.notAllowModifyDevice);
            	}
			}
        }
	    ,kpiId
		,{
	        fieldLabel: avmon.config.kpiThresholdValue,
	        name: 'kpi_threshold'
	    }
		,kipType
		,{
	        fieldLabel: avmon.config.ignoreValue,
	        name: 'ignore_value'
	    }
		,{
	        fieldLabel: avmon.config.errorValues,
	        name: 'error_value'
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
    height: 245,
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
Ext.define('CFG.view.TfAvmonRouteInspectionKpiGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.TfAvmonRouteInspectionKpiGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'TfAvmonRouteInspectionKpi_grid',
            title: avmon.config.networkMonitoringThresholdValueManagement,
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
	                text: avmon.config.deviceIP,
	                width: 100,
	                sortable: true,
	                dataIndex: 'device_ip'
	            }
				,{
	                text: 'KPI',
	                width: 100,
	                sortable: true,
	                hidden:true,
	                dataIndex: 'kpi_id'
	            }
	            ,{
	                text: avmon.config.kpiName,
	                width: 100,
	                sortable: false,
	                dataIndex: 'kpi_name'
	            }
				,{
	                text: avmon.config.kpiThresholdValue,
	                width: 100,
	                sortable: true,
	                dataIndex: 'kpi_threshold'
	            }
	            ,{
	                text: avmon.config.ignoreValue,
	                width: 100,
	                sortable: true,
	                dataIndex: 'ignore_value'
	            }
	            ,{
	                text: avmon.config.errorValues,
	                width: 100,
	                sortable: true,
	                dataIndex: 'error_value'
	            }
				,{
	                text: avmon.config.thresholdValueType,
	                width: 100,
	                sortable: true,
	                dataIndex: 'kpi_type',
	                renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
	                	var data = record.data ;
	                	if(data.kpi_type=='C'){
	                		return avmon.config.temperature;
	                	}else if(data.kpi_type=='I'){
	                		return avmon.config.integer;
	                	}else{
	                		return avmon.config.string;
	                	}
	                }
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
                    	//Ext.getCmp("device_ip").readOnly=false;
                    	var regExp = new RegExp(".*.*");  
	                    // 执行检索   
	                    kpiStore.filterBy(function(record,id){    
	                        // 得到每个record的项目名称值   
	                        var text = record.get("kpi_name");    
	                        return regExp.test(text);   
	                    });
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
									    url: 'deleteRouteKpiThresholdByIds?ids=' + ids.join(','),
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
                ,'-',{
                        xtype: 'textfield',
                        width: 180,
                        itemId:'deviceIp',
                        fieldLabel: avmon.config.deviceIP,
                        labelWidth: 50
                    },{
                        xtype: 'textfield',
                        width: 180,
                        itemId:'kpi',
                        fieldLabel: avmon.config.kpiName,
                        labelWidth: 50
                    },{
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
					        grid.store.load();
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
		    		
		    		var regExp = new RegExp(".*.*");  
                    // 执行检索   
                    kpiStore.filterBy(function(record,id){    
                        // 得到每个record的项目名称值   
                        var text = record.get("kpi_name");    
                        return regExp.test(text);   
                    });
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