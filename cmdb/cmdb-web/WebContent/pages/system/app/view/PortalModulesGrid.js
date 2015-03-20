Ext.Loader.setPath('Ext.ux', 'commons');
Ext.require(['Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.ux.ComboBoxTree',
		'Ext.selection.CheckboxModel']);

var treeStore = Ext.create('Ext.data.TreeStore', {
			root : {
				text : '根节点',
				id : '0',
				expanded : true
			},autoLoad: false,
		    proxy: {
		        type: 'ajax',
		        url: 'findModulesTreeStore'
		    }
		});

var parentId = Ext.create("Ext.ux.ComboBoxTree", {
			id : 'parent_id',
			name : 'parent_id',
			store : treeStore,
			fieldLabel : '父菜单名',
			editable : false,
			multiCascade : false,
			multiSelect : false,
			labelStyle : 'text-align:right;width:80;',
			width : 340,
			//treeHeight:250,
			rootVisible : false
		});

var gridStore = Ext.create('SYS.store.PortalModulesGridStore');

var displayFlag = Ext.create('Ext.form.field.ComboBox', {
			labelStyle : 'text-align:right;width:80;',
			width : 340,
			fieldLabel : '是否显示',
			name : 'display_flag',
			xtype : 'combobox',
			store : Ext.create('Ext.data.Store', {
						fields : ['value', 'display'],
						data : [{
									'value' : 'show',
									'display' : 'show'
								}, {
									'value' : 'unshow',
									'display' : 'unshow'
								}]
					}),
			queryMode : 'local',
			editable : false,
			valueField : 'value',
			displayField : 'display'
		});

var editForm = Ext.create('Ext.form.Panel', {
			bodyPadding : 5,
			border : false,
			frame : true,
			buttonAlign : 'center',
			xtype : 'filedset',
			url : 'saveUpdateModuleInfo',
			// layout: 'column',
			// fieldDefaults:{//统一设置表单字段默认属性
			// labelSeparator :'：',//分隔符
			// labelWidth : 60//标签宽度
			// },
			defaultType : 'textfield',
			items : [{
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : 'Id',
						hidden : true,
						name : 'id'
					}, displayFlag, {
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : '显示序列',
						name : 'display_order'
					}, {
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : 'ModuleId',
						hidden : true,
						name : 'module_id'
					}, {
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : '菜单名',
						name : 'module_name',
						allowBlank : false
					}, {
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : '菜单RUL',
						name : 'module_url'
					}, parentId, {
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : '备注',
						name : 'remark'
					}, {
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : '图标',
						name : 'icon_cls'
					}],
			buttons : [{
				text : '保存',
				formBind : true, // only enabled once the form is valid
				disabled : true,
				iconCls : 'icon-save',
				handler : function() {
					var submitForm = this.up('form').getForm();
					if (submitForm.isValid()) {
						submitForm.submit({
									method : 'POST',
									waitMsg : '正在保存，请稍等...',
									success : function(form, action) {
										Ext.MessageBox.alert('提示',
												action.result.msg);
										gridStore.load();
										eidtWin.hide();
									}
								});
					}
				}
			}, {
				text : '关闭',
				iconCls : 'icon-close',
				handler : function() {
					this.up('form').getForm().reset();
					eidtWin.hide();
				}
			}]
		});
var eidtWin = Ext.create('Ext.window.Window', {
			title : '添加',
			height : 300,
			width : 420,
			hidden : true,
			iconCls : 'icon-form',
			layout : 'fit',
			plain : true,
			modal : true,
			closable : true,
			closeAction : 'hide',
			items : [editForm]
		});
// 创建多选
var selModel = Ext.create('Ext.selection.CheckboxModel');

Ext.define('SYS.view.PortalModulesGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.PortalModulesGrid',
	initComponent : function() {
		Ext.apply(this, {
			id : 'PortalModules_grid',
			title : "模块管理",
			iconCls : 'icon-grid',
			border : false,
			columns : [

			{
						text : 'Id',
						width : 100,
						sortable : true,
						hidden : true,
						dataIndex : 'id'
					}, {
						text : '是否显示',
						width : 100,
						sortable : true,
						dataIndex : 'display_flag'
					}, {
						text : '显示序列',
						width : 100,
						sortable : true,
						dataIndex : 'display_order'
					}, {
						text : 'ModuleId',
						width : 100,
						sortable : true,
						hidden : true,
						dataIndex : 'module_id'
					}, {
						text : '菜单名',
						width : 130,
						sortable : true,
						dataIndex : 'module_name'
					}, {
						text : '父菜单名',
						width : 130,
						sortable : true,
						dataIndex : 'parent_name'
					}, {
						text : '菜单URL',
						width : 350,
						sortable : true,
						dataIndex : 'module_url'
					}, {
						text : 'ParentId',
						width : 100,
						sortable : true,
						hidden : true,
						dataIndex : 'parent_id'
					}, {
						text : '备注',
						width : 100,
						sortable : true,
						dataIndex : 'remark'
					}, {
						text : '图标',
						width : 100,
						sortable : true,
						dataIndex : 'icon_cls'
					}],
			store : gridStore,
			selModel : selModel,
			dockedItems : [{
				xtype : 'toolbar',
				items : [{
							text : '添加',
							iconCls : 'icon-add',
							handler : function() {
								editForm.getForm().reset();
								eidtWin.setTitle("添加");
								eidtWin.show();
								treeStore.load();
							}
						}, '-', {
							itemId : 'delete',
							text : '删除',
							id : 'delete_button',
							iconCls : 'icon-delete',
							handler : function() {
								var selection = this.up("panel")
										.getSelectionModel().getSelection();
								var panelStore = this.up("panel").getStore();
								if (selection.length == 0) {
									Ext.MessageBox.alert("提示", "请先选择您要操作的行!");
									return;
								} else {
									Ext.MessageBox.confirm("提示", "是否删除?",
											function(btn) {
												if (btn == "yes") {
													var ids = [];
													var moduleIds = [];
													Ext.each(selection,
															function(item) {
																ids.push(item.data.id);
																moduleIds.push("'" + item.data.module_id + "'");
															});
													
													Ext.Ajax.request({
														url : 'deleteModuleByIds?ids=' + ids.join(',') + "&moduleIds=" + moduleIds.join(','),
														success : function(
																response, opts) {
															var returnJson = Ext.JSON
																	.decode(response.responseText);
															Ext.MessageBox
																	.alert(
																			'提示',
																			returnJson.msg);
															panelStore.load();
														},
														failure : function(
																response, opts) {

														}
													});
												}
											});
								}
							}
						}]
			}, {
				xtype : 'pagingtoolbar',
				store : gridStore,
				dock : 'bottom',
				displayInfo : true,
				beforePageText : "第",
				afterPageText : "页,共{0}页",
				displayMsg : '显示 {0} - {1} 条，共计 {2} 条',
				emptyMsg : "没有数据"
			}],
			listeners : {
				itemdblclick : function(view, record, item, index, e, eOpts) {
					
					eidtWin.setTitle("修改");
					editForm.loadRecord(record);
					
					Ext.Ajax.request({
						url : 'findNameByEntityId?type=module&entityId='
								+ record.data.parent_id,
						success : function(response, opts) {
							var returnJson = Ext.JSON
									.decode(response.responseText);
							if (returnJson.res == "success") {
								parentId.setDefaultValue(
										record.data.parent_id,
										returnJson.entityName);
								eidtWin.show();
							} else {
								Ext.MessageBox.alert('提示',
										returnJson.msg);
							}
						},
						failure : function(response, opts) {
							Ext.MessageBox.alert('提示', "加载失败!");
						}
					});
					
					treeStore.load();
					
				},
				itemclick : function() {
					// alert("itemclick");
				},
				select : function() {
					// alert("select");
				}
			}
		});
		this.callParent(arguments);
	}
});