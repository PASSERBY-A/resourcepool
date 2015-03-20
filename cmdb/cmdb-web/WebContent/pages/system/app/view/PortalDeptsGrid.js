Ext.Loader.setPath('Ext.ux', 'commons');
Ext
		.require(['Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.data.TreeStore',
				'Ext.ux.ComboBoxTree', 'Ext.form.Panel',
				'Ext.selection.CheckboxModel']);

var treeStore = Ext.create('Ext.data.TreeStore', {
			root : {
				text : '根节点',
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
			fieldLabel : '所属部门',
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
			// layout: 'column',
			// fieldDefaults:{//统一设置表单字段默认属性
			// labelSeparator :'：',//分隔符
			// labelWidth : 60//标签宽度
			// },
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
						fieldLabel : '部门名称',
						name : 'dept_name',
						allowBlank : false,
						width : 340
					}, parentId],
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
				iconCls : 'icon-cancel',
				handler : function() {
					this.up('form').getForm().reset();
					eidtWin.hide();
				}
			}]
		});
var eidtWin = Ext.create('Ext.window.Window', {
			title : '添加',
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
// 创建搜索框，后台通过query参数获取查询条件
// var searchField = Ext.create('Ext.ux.form.SearchField',{
// width: 300,
// fieldLabel: '搜索',
// labelWidth: 40,
// xtype: 'searchfield',
// store: gridStore
// });
Ext.define('SYS.view.PortalDeptsGrid', {
	extend : 'Ext.grid.Panel',
	alias : 'widget.PortalDeptsGrid',
	initComponent : function() {
		Ext.apply(this, {
			id : 'PortalDepts_grid',
			title : "部门管理",
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
						text : '部门名称',
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
						text : '所属部门名称',
						width : 100,
						sortable : true,
						dataIndex : 'parent_name'
					}],
			store : gridStore,
			selModel : selModel,
			dockedItems : [{
				xtype : 'toolbar',
				items : [{
							text : '添加',
							iconCls : 'icon-add',
							handler : function() {
								treeStore.load();
								editForm.getForm().reset();
								eidtWin.setTitle("添加");
								eidtWin.show();
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
						}
				// ,'-',searchField
				]
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
					treeStore.load();

					eidtWin.setTitle("修改");
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
								Ext.MessageBox.alert('提示',
										returnJson.msg);
							}
						},
						failure : function(response, opts) {
							Ext.MessageBox.alert('提示', "加载失败!");
						}
					});
				}
			}
		});
		this.callParent(arguments);
	}
});