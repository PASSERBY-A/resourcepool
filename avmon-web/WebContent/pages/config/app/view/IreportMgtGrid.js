Ext.Loader.setPath('Ext.ux', '../system/commons'); 
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
    'Ext.selection.CheckboxModel' 
]);
var menuTree = Ext.create("Ext.ux.ComboBoxTree", {
	id : 'menu',
	name : 'menu',
	columnWidth: .5 ,
	store : {
		root : {
			text : avmon.config.root,
			id : '0',
			expanded : true
		},autoLoad: false,
	    proxy: {
	        type: 'ajax',
	        url: '../system/findModulesTreeStore'
	    }
	},
	fieldLabel : avmon.config.theMenu,
	editable : false,
	multiCascade : false,
	multiSelect : false,
	labelStyle : 'text-align:right;width:80;',
	//treeHeight:250,
	allowBlank : false,
	rootVisible : false
});
var gridStore = Ext.create('CFG.store.IreportMgtGridStore');
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding : 5,
	border : false,
	frame : true,
	fileUpload:true,//上传文件标识位
	buttonAlign : 'center',
	xtype : 'filedset',
	url : 'saveReportTemplateInfo',
	layout: 'column',
	fieldDefaults:{//统一设置表单字段默认属性
		labelSeparator :' '//分隔符
	},
	defaultType : 'textfield',
	items : [{
				labelStyle : 'text-align:right;width:60;',
				fieldLabel : 'Id',
				hidden : true,
				name : 'id'
			}
			,{
				columnWidth: .99,
				xtype:'fieldset',
	  	        title: avmon.config.basicInformation,
	  	        layout: 'column',
	  	        height:80,
	  	        items:[
		  	        {
						columnWidth: .5 ,
						labelStyle : 'text-align:right;width:80;',
						fieldLabel : avmon.config.reportNumber,
						xtype:'textfield',
						allowBlank : false,
						maxLength:64,
						regex:/^[A-Za-z0-9]+$/,
						regexText:avmon.config.onlyCanInputEnglishAndNumbers, 
						name : 'report_id'
					}, {
						columnWidth: .5 ,
						labelStyle : 'text-align:right;width:80;',
						fieldLabel : avmon.config.reportName,
						xtype:'textfield',
						maxLength:256,
						allowBlank : false,
						name : 'report_name'
					}, {
						columnWidth: .5 ,
				        fieldLabel: avmon.config.dataSource,
				        labelStyle : 'text-align:right;width:80;',
				        name: 'datasource_id',
				        id:'datasouce_id',
				        allowBlank: false,
				        maxLength:256,
				        xtype : 'combobox',
						store : Ext.create('Ext.data.Store', {
									fields : [{name:'id',type:'string'}, {name:'title',type:'string'}],
									autoLoad: true,
									proxy: {
									    type: 'ajax',
									    url : 'reportDatasourceIndex?dType=combox',
									    reader: {
									        type: 'json'
									    }
									}
								}),
						queryMode : 'local',
						editable : false,
						valueField : 'id',
						displayField : 'title'
				    }, 
				    menuTree
	  	        ]
	  	    }
		    ,{
				columnWidth: .99,
				xtype:'fieldset',
	  	        title: avmon.config.templateFile,
	  	        layout: 'column',
	  	        height:100,
	  	        items:[
		  	        {
				    	columnWidth: .5 ,
			            name : 'myfile1',
			            xtype:'filefield',
			            id:'uploadfile1',
			            blankText : avmon.config.pleaseSelectTemplateFile,
			            buttonText: avmon.config.browse
			
			        }
			        , {
			        	columnWidth: .5 ,
			            name : 'myfile2',
			            xtype:'filefield',
			            id:'uploadfile2',
			            buttonText: avmon.config.browse
			        }
			        , {
			        	columnWidth: .5 ,
			            name : 'myfile3',
			            xtype:'filefield',
			            id:'uploadfile3',
			            buttonText: avmon.config.browse
			        }
			        , {
			        	columnWidth: .5 ,
			            name : 'myfile4',
			            xtype:'filefield',
			            id:'uploadfile4',
			            buttonText: avmon.config.browse
			        }
			        , {
			        	columnWidth: .5 ,
			            name : 'myfile5',
			            xtype:'filefield',
			            id:'uploadfile5',
			            buttonText: avmon.config.browse
			        }
			        , {
			        	columnWidth: .5 ,
			            name : 'myfile6',
			            xtype:'filefield',
			            id:'uploadfile6',
			            buttonText: avmon.config.browse
			        }
	  	        ]
	  	    }
			,{
				columnWidth: .99,
				xtype:'fieldset',
	  	        title: 'HTML',
	  	        layout: 'column',
	  	        height:60,
	  	        items:[
	 				    {
	 				    	columnWidth: .5,
							labelStyle : 'text-align:right;width:80;',
							width : 440,
							fieldLabel : avmon.config.type,
							name : 'html_type',
							xtype : 'combobox',
							store : Ext.create('Ext.data.Store', {
										fields : ['value', 'display'],
										data : [{
													'value' : 'day',
													'display' : 'day'
												}, {
													'value' : 'week',
													'display' : 'week'
												}, {
													'value' : 'month',
													'display' : 'month'
												}]
									}),
							queryMode : 'local',
							editable : false,
							valueField : 'value',
							displayField : 'display'
						}
						, {
							columnWidth: .5 ,
							labelStyle : 'text-align:right;width:80;',
							fieldLabel : avmon.config.startTime,
					        name: 'html_start_time',
					        format:'Y-m-d H:i:s',
					        xtype:'datefield'
						}
	 			 ]
			}
			,{
				columnWidth: .99,
				xtype:'fieldset',
	  	        title: 'Mail',
	  	        layout: 'column',
	  	        height:140,
	  	        items:[
	 				    {
							columnWidth: .5 ,
							labelStyle : 'text-align:right;width:80;',
							fieldLabel : avmon.config.startTime,
					        name: 'email_start_time',
					        format:'Y-m-d H:i:s',
					        xtype:'datefield'
						}
						, {
							columnWidth: .5 ,
							labelStyle : 'text-align:right;width:80;',
							fieldLabel : avmon.config.cycle,
					        name: 'email_period',
					        xtype:'textfield'
						}
						, {
							columnWidth: 1 ,
							labelStyle : 'text-align:right;width:80;',
							fieldLabel : avmon.config.emailServer,
					        name: 'email_host',
					        xtype:'textfield'
						}
						, {
							columnWidth: 1 ,
							labelStyle : 'text-align:right;width:80;',
							fieldLabel : 'Email',
					        name: 'email_email',
					        xtype:'textfield'
						}
						, {
	 				    	columnWidth: .5,
							labelStyle : 'text-align:right;width:80;',
							fieldLabel : avmon.config.reportAttachment,
							name : 'is_attachment',
							xtype : 'combobox',
							store : Ext.create('Ext.data.Store', {
										fields : ['value', 'display'],
										data : [{
													'value' : 'Y',
													'display' : avmon.config.yes
												}, {
													'value' : 'N',
													'display' : avmon.config.no
												}]
									}),
							queryMode : 'local',
							editable : false,
							valueField : 'value',
							displayField : 'display'
						}
						, {
	 				    	columnWidth: .5,
							labelStyle : 'text-align:right;width:80;',
							fieldLabel : avmon.config.reportAttachmentType,
							name : 'attachment_type',
							xtype : 'combobox',
							store : Ext.create('Ext.data.Store', {
										fields : ['value', 'display'],
										data : [{
													'value' : 'pdf',
													'display' : 'PDF'
												}, {
													'value' : 'doc',
													'display' : 'Word'
												}, {
													'value' : 'xls',
													'display' : 'Excel'
												}]
									}),
							queryMode : 'local',
							editable : false,
							valueField : 'value',
							displayField : 'display'
						}
	 			 ]
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
                    },
                    failure:function(form, action){
                    	Ext.MessageBox.alert(avmon.common.reminder, action.result.msg);//'系统异常，请联系管理员!');
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
    height: 500,
    width:800,
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
Ext.define('CFG.view.IreportMgtGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.IreportMgtGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'IreportMgt_grid',
            title: avmon.config.reportManagement,
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
	                text: avmon.config.reportNumber,
	                width: 100,
	                sortable: true,
	                dataIndex: 'report_id'
	            }
				,{
	                text: avmon.config.reportName,
	                width: 100,
	                sortable: true,
	                dataIndex: 'report_name'
	            }
				,{
	                text: avmon.config.dataSource,
	                width: 100,
	                sortable: true,
	                dataIndex: 'db_title'
	            }
				,{
	                text: avmon.config.templatePath,
	                width: 250,
	                sortable: true,
	                dataIndex: 'template'
	            }
				,{
	                text: avmon.config.theMenu,
	                width: 100,
	                sortable: true,
	                dataIndex: 'module_name'
	            }
				,{
	                text: avmon.config.updateTime,
	                width: 150,
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
								       ids.push(item.data.report_id); 
								    });
									Ext.Ajax.request({
									    url: 'deleteReportTemplateByIds?ids=' + ids.join(','),
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
		    		Ext.Ajax.request({
						url : '../system/findNameByEntityId?type=module&entityId=' + record.data.menu,
						success : function(response, opts) {
							var returnJson = Ext.JSON.decode(response.responseText);
							if (returnJson.res == "success") {
								menuTree.setDefaultValue(record.data.menu,returnJson.entityName);
								eidtWin.show();
							} else {
								Ext.MessageBox.alert(avmon.common.reminder,returnJson.msg);
							}
						},
						failure : function(response, opts) {
							Ext.MessageBox.alert(avmon.common.reminder, avmon.config.loadFail);
						}
					});
		    	},
		    	itemclick:function(){
		    	},
		    	select:function(){
		    	}
		    }
        });
        this.callParent(arguments);
    }
});