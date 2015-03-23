Ext.Loader.setPath('Ext.ux', 'commons');
Ext
		.require(['Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.data.TreeStore',
				'Ext.ux.ComboBoxTree', 'Ext.form.Panel',
				'Ext.selection.CheckboxModel']);

var treeStore = Ext.create('Ext.data.TreeStore', {
			root : {
				text : avmon.system.root,
				id : '0',
				expanded : true
			},
			autoLoad: false,
			proxy : {
				type : 'ajax',
				url : 'findDeptsTreeStore'
			}
		});

var gridStore = Ext.create('SYS.store.PortalDeptsGridStore');

var parentId = Ext.create("Ext.ux.ComboBoxTree", {
			id : 'parent_id',
			name : 'parent_id',
			store : treeStore,
			fieldLabel : avmon.system.department,
			editable : false,
			multiCascade : false,
			multiSelect : false,
			labelStyle : 'text-align:right;width:80;',
			width : 340,
			treeHeight:200,
			rootVisible : false
		});

var editForm = Ext.create('Ext.form.Panel', {
			bodyPadding : 5,
			border : false,
			frame : true,
			buttonAlign : 'center',
			xtype : 'filedset',
			url : 'saveUpdateDeptInfo',
			defaultType : 'textfield',
			items : [{
						labelStyle : 'text-align:right;width:80;',
						fieldLabel : 'Id',
						name : 'id',
						readOnly : true,
						hidden : true,
						width : 340
					}, {
						labelStyle : 'text-align:right;width:80;',
						fieldLabel : 'DeptId',
						name : 'dept_id',
						readOnly : true,
						hidden : true,
						width : 340
					}, {
						labelStyle : 'text-align:right;width:80;',
						fieldLabel : avmon.system.departmentName,
						name : 'dept_name',
						allowBlank : false,
						width : 340
					}, parentId],
			buttons : [{
				text : avmon.common.save,
				formBind : true, // only enabled once the form is valid
				disabled : true,
				iconCls : 'icon-save',
				handler : function() {
					var submitForm = this.up('form').getForm();
					if (submitForm.isValid()) {
						submitForm.submit({
									method : 'POST',
									waitMsg : avmon.common.saving,
									success : function(form, action) {
										Ext.MessageBox.alert(avmon.common.reminder,
												action.result.msg);
										gridStore.load();
										eidtWin.hide();
									}
								});
					}
				}
			}, {
				text : avmon.common.close,
				iconCls : 'icon-cancel',
				handler : function() {
					this.up('form').getForm().reset();
					eidtWin.hide();
				}
			}]
		});
var eidtWin = Ext.create('Ext.window.Window', {
			title : avmon.system.add,
			height : 200,
			width : 400,
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
Ext.define('SYS.view.PortalDeptsGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.PortalDeptsGrid',
	initComponent : function() {
		Ext.apply(this, {
			id : 'PortalDepts_grid',
			title : avmon.system.departmentManagement,
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
						text : 'DeptId',
						width : 100,
						sortable : true,
						hidden : true,
						dataIndex : 'dept_id'
					}, {
						text : avmon.system.departmentName,
						width : 100,
						sortable : true,
						dataIndex : 'dept_name'
					}, {
						text : 'ParentId',
						width : 100,
						sortable : true,
						hidden : true,
						dataIndex : 'parent_id'
					}, {
						text : avmon.system.DepartmentName,
						width : 100,
						sortable : true,
						dataIndex : 'parent_name'
					}],
			store : gridStore,
			selModel : selModel,
			dockedItems : [{
				xtype : 'toolbar',
				items : [{
							text : avmon.system.add,
							iconCls : 'icon-add',
							handler : function() {
								treeStore.load();
								editForm.getForm().reset();
								eidtWin.setTitle(avmon.system.add);
								eidtWin.show();
							}
						}, '-', {
							itemId : 'delete',
							text : avmon.common.deleted,
							id : 'delete_button',
							iconCls : 'icon-delete',
							handler : function() {
								var selection = this.up("panel")
										.getSelectionModel().getSelection();
								var panelStore = this.up("panel").getStore();
								if (selection.length == 0) {
									Ext.MessageBox.alert(avmon.common.reminder, avmon.system.selectOperateLine);
									return;
								} else {
									Ext.MessageBox.confirm(avmon.common.reminder, avmon.system.whetherToDelete,
											function(btn) {
												if (btn == "yes") {
													var ids = [];
													Ext.each(selection,
															function(item) {
																ids
																		.push("'"
																				+ item.data.dept_id
																				+ "'");
															});
													Ext.Ajax.request({
														url : 'deleteDeptByIds?ids='
																+ ids.join(','),
														success : function(
																response, opts) {
															var returnJson = Ext.JSON
																	.decode(response.responseText);
															Ext.MessageBox
																	.alert(
																			avmon.common.reminder,
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
						}
				// ,'-',searchField
				]
			}, {
				xtype : 'pagingtoolbar',
				store : gridStore,
				dock : 'bottom',
				displayInfo : true,
				beforePageText : avmon.system.beforePageText,
				afterPageText : avmon.system.afterPageText,
				displayMsg : avmon.system.displayMsg,
				emptyMsg : avmon.system.emptyMsg
			}],
			listeners : {
				itemdblclick : function(view, record, item, index, e, eOpts) {
					treeStore.load();

					eidtWin.setTitle(avmon.system.modify);
					editForm.loadRecord(record);

					Ext.Ajax.request({
						url : 'findNameByEntityId?type=dept&entityId='
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
								Ext.MessageBox.alert(avmon.common.reminder,
										returnJson.msg);
							}
						},
						failure : function(response, opts) {
							Ext.MessageBox.alert(avmon.common.reminder, avmon.system.loadFail);
						}
					});
				}
			}
		});
		this.callParent(arguments);
	}
});