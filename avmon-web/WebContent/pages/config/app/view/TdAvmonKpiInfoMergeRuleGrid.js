//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
//    'Ext.ux.form.SearchField', 
    'Ext.selection.CheckboxModel' 
]); 
var kpiInfoGridStore = Ext.create('CFG.store.TdAvmonKpiInfoGridStore');
var aggmethod = Ext.create('Ext.form.field.ComboBox', {
	labelStyle : 'text-align:left;width:80;',
	width : 440,
	fieldLabel : avmon.config.aggregationType,
	name : 'aggmethod',
	xtype : 'combobox',
	store : Ext.create('Ext.data.Store', {
				fields : ['value', 'display'],
				data : [{
							'value' : 'SUM',
							'display' : 'SUM'
						}, {
							'value' : 'AVG',
							'display' : 'AVG'
						}, {
							'value' : 'MIN',
							'display' : 'MIN'
						}, {
							'value' : 'MAX',
							'display' : 'MAX'
						}, {
							'value' : 'COUNT',
							'display' : 'COUNT'
						}]
			}),
	queryMode : 'local',
	editable : false,
	valueField : 'value',
	displayField : 'display'  
});
var datatype = Ext.create('Ext.form.field.ComboBox', {
	labelStyle : 'text-align:left;width:80;',
	width : 440,
	fieldLabel : avmon.config.dataType,
	name : 'datatype',
	xtype : 'combobox',
	store : Ext.create('Ext.data.Store', {
				fields : ['value', 'display'],
				data : [{
							'value' : '1',
							'display' : 'string'
						}, {
							'value' : '0',
							'display' : 'number'
						}]
			}),
	queryMode : 'local',
	editable : false,
	valueField : 'value',
	displayField : 'display'  
});
var recentTrend = Ext.create('Ext.form.field.ComboBox', {
	labelStyle : 'text-align:left;width:80;',
	width : 440,
	fieldLabel : avmon.config.whetherRetainTheRecentRawData,
	name : 'recent_trend',
	xtype : 'combobox',
	store : Ext.create('Ext.data.Store', {
				fields : ['value', 'display'],
				data : [{
							'value' : 1,
							'display' : avmon.config.yes
						}, {
							'value' : 0,
							'display' : avmon.config.no
						}]
			}),
	queryMode : 'local',
	editable : false,
	valueField : 'value',
	displayField : 'display'  
});
var isstore = Ext.create('Ext.form.field.ComboBox', {
	labelStyle : 'text-align:left;width:80;',
	width : 440,
	fieldLabel : avmon.config.storedToDatabase,
	name : 'isstore',
	xtype : 'combobox',
	store : Ext.create('Ext.data.Store', {
				fields : ['value', 'display'],
				data : [{
							'value' : 1,
							'display' : avmon.config.yes
						}, {
							'value' : 0,
							'display' : avmon.config.no
						}]
			}),
	queryMode : 'local',
	editable : false,
	valueField : 'value',
	displayField : 'display',
	listeners: {
		select: {
            fn: onComboboxSelect,
            scope: this
        }
    }
});
function onComboboxSelect(combo, records, options) {
	if(combo.getValue()=='1'){
		combo.ownerCt.down("#storeperiod").show();
		combo.ownerCt.down("#storeperiod").allowBlank = false;
	}else{
		combo.ownerCt.down("#storeperiod").hide();
		combo.ownerCt.down("#storeperiod").allowBlank = true;
	}
}
var iscalc = Ext.create('Ext.form.field.ComboBox', {
	labelStyle : 'text-align:left;width:80;',
	width : 440,
	fieldLabel : avmon.config.whetherComputationalKPI,
	name : 'iscalc',
	xtype : 'combobox',hidden:true,value:'0',
	store : Ext.create('Ext.data.Store', {
				fields : ['value', 'display'],
				data : [{
							'value' : '1',
							'display' : avmon.config.yes
						}, {
							'value' : '0',
							'display' : avmon.config.no
						}]
			}),
	queryMode : 'local',
	editable : false,
	valueField : 'value',
	displayField : 'display',
	listeners: {
		select: {
            fn: onComboboxSelect1,
            scope: this
        }
    }  
});
function onComboboxSelect1(combo, records, options) {
	if(combo.getValue()=='1'){
		combo.ownerCt.down("#calcmethod").show();
		combo.ownerCt.down("#calcmethod").allowBlank = false;
	}else{
		combo.ownerCt.down("#calcmethod").hide();
		combo.ownerCt.down("#calcmethod").allowBlank = true;
	}
}
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveUpdateKpiInfo',
    //layout: 'column',
    fieldDefaults:{//统一设置表单字段默认属性
    	labelSeparator :' ',//分隔符
    	labelWidth : 160//标签宽度
    	,width:440
    },
    defaultType: 'textfield',
    items: [
		{
	        fieldLabel: avmon.config.kpiEncoding,
	        name: 'kpi_code',
	        itemId:'kpi_code',	        
	        maxLength:50,
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.kpiEnglishName,
	        name: 'kpi_name',
	        maxLength:100,
	        allowBlank: false
	    }
		,{
	        fieldLabel: avmon.config.kpiChineseName,
	        name: 'caption',
	        maxLength:200,
	        allowBlank: false
	    }
		,aggmethod
		,{
	        fieldLabel: avmon.config.decimal,
	        name: 'precision',
	        allowBlank: false,
	        xtype:'numberfield',
	        minValue : 1,//最小值
	        maxLength:2,
	        hideTrigger : true
	    }
		,{
	        fieldLabel: avmon.config.unit,
	        name: 'unit',
	        maxLength:50,
	        allowBlank: true
	    }
		,iscalc
		,{
	        fieldLabel: avmon.config.calculationMethod,
	        name: 'calcmethod',
	        itemId:'calcmethod',
	        xtype:'textarea',
	        hidden:true,
	        height:60
	    }
		,isstore
		,{
	        fieldLabel: avmon.config.storageCycleMinutes,
	        name: 'storeperiod',
	        itemId:'storeperiod',
	        xtype:'numberfield',
	        minValue : 1,//最小值
	        maxLength:9,
	        hideTrigger : true
	    }
		,datatype
		,recentTrend
		,{
	        fieldLabel: 'Id',
	        name: 'id',
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
                       kpiInfoGridStore.load();
                       eidtWin.hide();
                    },
				    failure: function(form, action) {
				    	Ext.MessageBox.alert(avmon.common.reminder, action.result.msg);
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
    height: 400,
    width:600,
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
var kpiSelModel = Ext.create('Ext.selection.CheckboxModel');

Ext.define('CFG.view.TdAvmonKpiInfoMergeRuleGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.TdAvmonKpiInfoMergeRuleGrid',
    //自定义属性
    pup:false,
    noPup:true,
    mergeRuleForm:null,
    pupKpiWin:null,
    initComponent: function() {
        Ext.apply(this, {
            id: 'TdAvmonKpiInfo_grid',
            title: avmon.config.kpiInformation,
            iconCls: 'icon-grid',
            border: false,
            mergeRuleForm:this.mergeRuleForm,
    		pupKpiWin:this.pupKpiWin,
            columns: [
				{
				    text: avmon.config.kpiEncoding,
				    width: 100,
				    sortable: true,
				    dataIndex: 'kpi_code'
				}
				,{
	                text: avmon.config.kpiEnglishName,
	                width: 100,
	                sortable: true,
	                dataIndex: 'kpi_name'
	            }
				,{
	                text: avmon.config.kpiChineseName,
	                width: 100,
	                sortable: true,
	                dataIndex: 'caption'
	            }
				,{
	                text: avmon.config.aggregationType,
	                width: 100,
	                sortable: true,
	                dataIndex: 'aggmethod'
	            }
				,{
	                text: avmon.config.decimal,
	                width: 100,
	                sortable: true,
	                dataIndex: 'precision'
	            }
				,{
	                text: avmon.config.unit,
	                width: 100,
	                sortable: true,
	                dataIndex: 'unit'
	            }
				,{
	                text: avmon.config.storedToDatabase,
	                width: 100,
	                sortable: true,
	                dataIndex: 'isstore',
		            renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
						var data = record.data ; 
						var isstore = data.isstore; 
						if(isstore==0){
							return avmon.config.no;
						}else if(isstore==1){
							return avmon.config.yes;
						}else{
							return "";
						}
					}
	            }
				,{
	                text: avmon.config.storageCycle,
	                width: 100,
	                sortable: true,
	                dataIndex: 'storeperiod'
	            }
				,{
	                text: avmon.config.dataType,
	                width: 100,
	                sortable: true,
	                dataIndex: 'datatype',
		            renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
						var data = record.data ; 
						var datatype = data.datatype; 
						if(datatype==0){
							return "number";
						}else if(datatype==1){
							return "string";
						}else{
							return "";
						}
					}
	            }
				,{
	                text: 'Id',
	                width: 100,
	                sortable: true,
	                hidden:true,
	                dataIndex: 'id'
	            }
            ],
            store: kpiInfoGridStore,
            selModel:kpiSelModel, 
            selectKPI:function(){
            	var selection = this.getSelectionModel().getSelection();
			    if(selection.length == 0||selection.length >1){
			        Ext.MessageBox.alert(avmon.common.reminder,avmon.config.selectKPINotMoreThanOne);
			        return; 
			    }else{
			    	this.mergeRuleForm.down("#kpi").setValue(selection[0].data.kpi_code);
//			    	this.upgradeRuleForm.down("#kpiName").setValue(selection[0].data.caption);
			    	this.pupKpiWin.hide();
			    }
            },
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: avmon.config.add,
                    hidden:this.pup,
                    iconCls: 'icon-add',
                    handler: function() {
                    	editForm.getForm().reset();
                    	eidtWin.setTitle(avmon.config.add);
                    	if(editForm.down('#kpi_code').readOnly){
                    		editForm.down('#kpi_code').setReadOnly(false);
                    	}
                    	eidtWin.show();
                    }
                },{
                    text: avmon.config.choose,
                    hidden:this.noPup,
                    iconCls: 'icon-add',
                    handler: function() {
                    	//selectKpi();此方式firefox测试未通过
                    	this.up("panel").selectKPI();
                    }
                },
                '-', {
                    itemId: 'delete',
                    text: avmon.common.deleted,
                    hidden:this.pup,
                    //id:'delete_button',
                    iconCls: 'icon-delete',
                    handler: function() {
                        var selection = this.up("panel").getSelectionModel().getSelection();
                        var panelStore = this.up("panel").getStore();
                        if(selection.length == 0){ 
	                        Ext.MessageBox.alert(avmon.common.reminder,avmon.alarm.selectOperateLine);
	                        return; 
	                    }else{ 
	                    	Ext.MessageBox.confirm(avmon.common.reminder, avmon.config.whetherToDelete, function(btn){
								if(btn=="yes"){			   	      
									var ids = []; 
								    Ext.each(selection,function(item){ 
								       ids.push(item.data.id); 
								    });
									Ext.Ajax.request({
									    url: 'deleteKpiInfoByIds?ids=' + ids.join(','),
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
                    itemId:'kpiCode',
                    fieldLabel: avmon.config.kpiEncoding,
                    labelWidth: 50
                },{
                    xtype: 'textfield',
                    width: 210,
                    itemId:'kpiName',
                    fieldLabel: avmon.config.kpiEnglishName,
                    labelWidth: 80
                },{
                    text: avmon.config.search,
                    iconCls: 'icon-search',
                    handler: function() {
                    	var toolBar = this.ownerCt;
      				        var grid = this.ownerCt.ownerCt;
      				        var kpiCode = toolBar.down('#kpiCode').getValue();
      				        var kpiName = toolBar.down('#kpiName').getValue();
          						var extraParams = grid.store.proxy.extraParams;
          						grid.store.currentPage=1;
          				        if(kpiCode.length>0){
          				        	extraParams.kpiCode=kpiCode;
          				        }else{
          				        	extraParams.kpiCode=null;
          				        }
          				        if(kpiName.length>0){
          				        	extraParams.kpiName=kpiName;
          				        }else{
          				        	extraParams.kpiName=null;
          				        }
          				        grid.store.load();
                      }
                }
	            ] 
            },{
                xtype: 'pagingtoolbar',
                store: kpiInfoGridStore,
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
		    		editForm.down('#kpi_code').setReadOnly(true);
		    		eidtWin.show();
		    	},
		    	itemclick:function(){},
		    	select:function(){},
		    	render:function(){}
		    }
        });
        this.callParent(arguments);
    }
});