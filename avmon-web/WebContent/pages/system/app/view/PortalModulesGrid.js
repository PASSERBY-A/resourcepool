Ext.Loader.setPath('Ext.ux', 'commons');
Ext.require(['Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.ux.ComboBoxTree',
		'Ext.selection.CheckboxModel']);

var treeStore = Ext.create('Ext.data.TreeStore', {
			root : {
				text : avmon.system.root,
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
			fieldLabel : avmon.system.parentMenuName,
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
			fieldLabel : avmon.system.whetherDisplay,
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
						fieldLabel : avmon.system.accordingSequence,
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
						fieldLabel : avmon.system.menuName,
						name : 'module_name',
						allowBlank : false
					}, {
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : avmon.system.menuRUL,
						name : 'module_url'
					}, parentId, {
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : avmon.system.note,
						name : 'remark'
					}, {
						labelStyle : 'text-align:right;width:80;',
						width : 340,
						fieldLabel : avmon.system.icon,
						name : 'icon_cls'
					}],
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
				iconCls : 'icon-close',
				handler : function() {
					this.up('form').getForm().reset();
					eidtWin.hide();
				}
			}]
		});
var eidtWin = Ext.create('Ext.window.Window', {
			title : avmon.system.add,
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
			title : avmon.system.moduleManagement,
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
						text : avmon.system.whetherDisplay,
						width : 100,
						sortable : true,
						dataIndex : 'display_flag'
					}, {
						text : avmon.system.accordingSequence,
						width : 100,
						sortable : true,
						dataIndex : 'display_order'
					}, {
						text : avmon.system.moduleId,
						width : 100,
						sortable : true,
						hidden : true,
						dataIndex : 'module_id'
					}, {
						text : avmon.system.menuName,
						width : 130,
						sortable : true,
						dataIndex : 'module_name'
					}, {
						text : avmon.system.parentMenuName,
						width : 130,
						sortable : true,
						dataIndex : 'parent_name'
					}, {
						text : avmon.system.menuRUL,
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
						text : avmon.system.note,
						width : 100,
						sortable : true,
						dataIndex : 'remark'
					}, {
						text : avmon.system.icon,
						width : 100,
						sortable : true,
						dataIndex : 'icon_cls'
					}],
			store : gridStore,
			selModel : selModel,
			dockedItems : [{
				xtype : 'toolbar',
				items : [{
							text : avmon.system.add,
							iconCls : 'icon-add',
							handler : function() {
								editForm.getForm().reset();
								eidtWin.setTitle(avmon.system.add);
								eidtWin.show();
								treeStore.load();
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
						}]
			}, {
				xtype : 'pagingtoolbar',
				store : gridStore,
				dock : 'bottom',
				displayInfo : true,
				beforePageText : avmon.system.beforePageText,
				afterPageText : avmon.system.beforePageText,
				displayMsg : avmon.system.displayMsg,
				emptyMsg : avmon.system.emptyMsg
			}],
			listeners : {
				itemdblclick : function(view, record, item, index, e, eOpts) {
					
					eidtWin.setTitle(avmon.system.modify);
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
								Ext.MessageBox.alert(avmon.common.reminder,
										returnJson.msg);
							}
						},
						failure : function(response, opts) {
							Ext.MessageBox.alert(avmon.common.reminder, avmon.system.loadFail);
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