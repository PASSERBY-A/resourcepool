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
			fieldLabel : avmon.system.systemModule,
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
	        fieldLabel: avmon.system.roleName,
	        name: 'role_name',
	        allowBlank: false
	    }
	    ,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340,
	    	xtype:'textarea',
	        fieldLabel: avmon.system.roleDescription,
	        name: 'role_desc'
	    }
	    ,sysModule
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
    title: avmon.system.add,
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
Ext.define('SYS.view.PortalRolesGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.PortalRolesGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'PortalRoles_grid',
            title: avmon.system.roleManagement,
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
	                text: avmon.system.systemModule,
	                width: 100,
	                sortable: true,
	                dataIndex: 'role_name'
	            }
				,{
	                text: avmon.system.roleDescription,
	                width: 100,
	                sortable: true,
	                dataIndex: 'role_desc'
	            }
				,{
	                text: avmon.system.roleAuthorization,
	                width: 700,
	                sortable: false,
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
                    text: avmon.system.add,
                    iconCls: 'icon-add',
                    handler: function() {
                    	editForm.getForm().reset();
                    	eidtWin.setTitle(avmon.system.add);
                    	eidtWin.show();
                    	treeStore.load();
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
	                        Ext.MessageBox.alert(avmon.common.reminder,avmon.system.selectOperateLine);
	                        return; 
	                    }else{ 
	                    	Ext.MessageBox.confirm(avmon.common.reminder, avmon.system.whetherToDelete, function(btn){
								if(btn=="yes"){			   	      
									var ids = []; 
								    Ext.each(selection,function(item){ 
								       ids.push(item.data.id); 
								    });
									Ext.Ajax.request({
									    url: 'deleteRoleByIds?ids=' + ids.join(','),
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
//                ,'-',searchField
	            ] 
            },{
                xtype: 'pagingtoolbar',
                store: gridStore,
                dock: 'bottom',
                displayInfo: true,
                beforePageText : avmon.system.beforePageText,
    			afterPageText : avmon.system.afterPageText,
                displayMsg: avmon.system.displayMsg, 
                emptyMsg: avmon.system.emptyMsg 
            }],
            listeners:{
		    	itemdblclick:function(view, record, item, index, e, eOpts){
		    		eidtWin.setTitle(avmon.system.modify);
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