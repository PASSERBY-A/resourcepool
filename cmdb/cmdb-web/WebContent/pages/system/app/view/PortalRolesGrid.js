Ext.Loader.setPath('Ext.ux', 'commons');
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
    'Ext.ux.ComboBoxTree', 
    'Ext.selection.CheckboxModel' 
]); 

var treeStore = Ext.create('Ext.data.TreeStore', {
			root : {
				text : 'Root',
				id : '0',
				expanded : true
			},autoLoad: false,
		    proxy: {
		        type: 'ajax',
		        url: 'findModulesTreeStore?isChecked=true'
		    }
		});

var sysModule = Ext.create("Ext.ux.ComboBoxTree", {
			id : 'module_ids',
			name : 'module_ids',
			store : treeStore,
			fieldLabel : '系统模块',
			editable : false,
			multiCascade : true,
			multiSelect : true,
			labelStyle : 'text-align:right;width:80;',
			width : 340,
			treeHeight:250,
			rootVisible : false,
			allowBlank: false
		});

var gridStore = Ext.create('SYS.store.PortalRolesGridStore');
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveUpdateRoleInfo',
//    layout: 'column',
//    fieldDefaults:{//统一设置表单字段默认属性
//    	labelSeparator :'：',//分隔符
//    	labelWidth : 60//标签宽度
//    },
    defaultType: 'textfield',
    items: [
		{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340,
	        fieldLabel: 'Id',
	        name: 'id',
	        hidden:true
	    }
		,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340,
	        fieldLabel: 'RoleId',
	        name: 'role_id',
	        hidden:true
	    }
		,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340,
	        fieldLabel: '角色名称',
	        name: 'role_name',
	        allowBlank: false
	    }
	    ,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340,
	    	xtype:'textarea',
	        fieldLabel: '角色描述',
	        name: 'role_desc'
	    }
	    ,sysModule
    ],
    buttons: [{
        text: '保存',
        formBind: true, //only enabled once the form is valid
        disabled: true,
        iconCls:'icon-save',
        handler: function() {
            var submitForm = this.up('form').getForm();
            if (submitForm.isValid()) {
                submitForm.submit({
                    method: 'POST',
                    waitMsg:'正在保存，请稍等...',
                    success: function(form, action) {
                       Ext.MessageBox.alert('提示', action.result.msg);
                       gridStore.load();
                       eidtWin.hide();
                    }
                });
            }
        }
    },{
        text: '关闭',
        iconCls:'icon-close',
        handler: function() {
        	this.up('form').getForm().reset();
            eidtWin.hide();
        }
    }]
});
var eidtWin = Ext.create('Ext.window.Window',{
    title: '添加',
    height: 250,
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
//var searchField = Ext.create('Ext.ux.form.SearchField',{
//	width: 300, 
//    fieldLabel: '搜索', 
//    labelWidth: 40, 
//    xtype: 'searchfield', 
//    store: gridStore
//});
Ext.define('SYS.view.PortalRolesGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.PortalRolesGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'PortalRoles_grid',
            title: "角色管理",
            iconCls: 'icon-grid',
            border: false,
            forceFit:true,//列的自动填充
            autoExpandColumn : 'module_names',
            columns: [
            
				{
	                text: 'Id',
	                width: 100,
	                sortable: true,
	                hidden:true,
	                dataIndex: 'id'
	            }
	            ,{
	                text: '角色名称',
	                width: 100,
	                sortable: true,
	                dataIndex: 'role_name'
	            }
				,{
	                text: '角色描述',
	                width: 100,
	                sortable: true,
	                dataIndex: 'role_desc'
	            }
//	            ,{
//	                text: 'ModuleIds',
//	                width: 100,
//	                sortable: true,
//	                dataIndex: 'module_ids'
//	            }
				,{
	                text: '角色权限',
	                width: 700,
	                sortable: false,
//	                flex:1,
	                dataIndex: 'module_names'
	            }
				,{
	                text: 'RoleId',
	                width: 100,
	                sortable: true,
	                hidden:true,
	                dataIndex: 'role_id'
	            }
            ],
            store: gridStore,
            selModel:selModel, 
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: '添加',
                    iconCls: 'icon-add',
                    handler: function() {
                    	editForm.getForm().reset();
                    	eidtWin.setTitle("添加");
                    	eidtWin.show();
                    	treeStore.load();
                    }
                },
                '-', {
                    itemId: 'delete',
                    text: '删除',
                    id:'delete_button',
                    iconCls: 'icon-delete',
                    handler: function() {
                        var selection = this.up("panel").getSelectionModel().getSelection();
                        var panelStore = this.up("panel").getStore();
                        if(selection.length == 0){ 
	                        Ext.MessageBox.alert("提示","请先选择您要操作的行!");
	                        return; 
	                    }else{ 
	                    	Ext.MessageBox.confirm("提示", "是否删除?", function(btn){
								if(btn=="yes"){			   	      
									var ids = []; 
								    Ext.each(selection,function(item){ 
								       ids.push(item.data.id); 
								    });
									Ext.Ajax.request({
									    url: 'deleteRoleByIds?ids=' + ids.join(','),
									    success: function(response, opts) {
									    	var returnJson = Ext.JSON.decode(response.responseText);
									    	Ext.MessageBox.alert('提示', returnJson.msg);
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
//                ,'-',searchField
	            ] 
            },{
                xtype: 'pagingtoolbar',
                store: gridStore,
                dock: 'bottom',
                displayInfo: true,
                beforePageText : "第",
    			afterPageText : "页,共{0}页",
                displayMsg: '显示 {0} - {1} 条，共计 {2} 条', 
                emptyMsg: "没有数据" 
            }],
            listeners:{
		    	itemdblclick:function(view, record, item, index, e, eOpts){
		    		eidtWin.setTitle("修改");
		    		editForm.loadRecord(record);
					eidtWin.show();
					treeStore.load({
					    callback: function(records, options, success){
					    	sysModule.setDefaultValue(record.data.module_ids,record.data.module_names);
					    }
					});
		    	}
		    }
        });
        this.callParent(arguments);
    }
});