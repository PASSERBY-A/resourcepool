Ext.Loader.setPath('Ext.ux', 'commons');
Ext.require(['Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.data.TreeStore',
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

var gridStore = Ext.create('SYS.store.PortalBusinessSysGridStore');

var editForm = Ext.create('Ext.form.Panel', {
			bodyPadding : 5,
			border : false,
			frame : true,
			buttonAlign : 'center',
			xtype : 'filedset',
			url : 'saveUpdateBusinessSysInfo',
			defaultType : 'textfield',
			items : [{
						labelStyle : 'text-align:right;width:80;',
						fieldLabel : avmon.system.businessSystemID,
						name : 'id',
						vtype: 'alphanum',
						allowBlank : false,
						width : 340
					}, {
						labelStyle : 'text-align:right;width:80;',
						fieldLabel : avmon.system.businessSystemName,
						name : 'businessname',
						allowBlank : false,
						regex : /^[\u4e00-\u9fa5_a-zA-Z0-9]+$/,
				        regexText:avmon.system.onlywordsallowed,
						width : 340
					}, {
						xtype: 'hiddenfield',
						name : 'insFlag',
						id:'insFlag',
						value:''
					}
					
					],
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
									},
									failure : function(response, opts) {
										Ext.MessageBox.alert(avmon.common.reminder, opts.result.msg);
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

Ext.define('SYS.view.PortalBusinessSysGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.PortalBusinessSysGrid',
	initComponent : function() {
		Ext.apply(this, {
			id : 'PortalBusinessSys_grid',
			title : avmon.system.businessSystemManagement,
			iconCls : 'icon-grid',
			border : false,
			columns : [
			        {
						text : 'ID',
						width : 100,
						sortable : true,
						dataIndex : 'id'
					}, {
						text : avmon.system.businessSystemName,
						width : 100,
						sortable : true,
						dataIndex : 'businessname'
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
								Ext.getCmp('insFlag').setValue('0');
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
																ids.push("'"+ item.data.id+ "'");
															});
													Ext.Ajax.request({
														url : 'deleteBusinessSysByIds?ids='
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
			},
			{
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
					eidtWin.setTitle(avmon.system.modify);
					editForm.loadRecord(record);
					Ext.getCmp('insFlag').setValue('1');
					eidtWin.show();
				}
			}
		});
		this.callParent(arguments);
	}
});