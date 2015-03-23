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
			},
			autoLoad: false,
			proxy : {
				type : 'ajax',
				url : 'findDeptsTreeStore'
			}
		});

var deptId = Ext.create("Ext.ux.ComboBoxTree", {
			id : 'dept_ids',
			name : 'dept_ids',
			store : treeStore,
			fieldLabel : avmon.system.department,
			editable : false,
			multiCascade : false,
			multiSelect : false,
			labelStyle : 'text-align:right;width:80;',
			width : 340,
			treeHeight:200,
			rootVisible : false,
	        allowBlank: false
		});

var gridStore = Ext.create('SYS.store.PortalUsersGridStore');
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveUpdateUserInfo',
    defaultType: 'textfield',
    items: [
		{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: 'Id',
	        hidden:true,
	        name: 'id'
	    }
		,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: avmon.system.account,
	        name: 'user_id',
	        allowBlank: false
	    }
	    ,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: avmon.system.password,
	        name: 'password',
	        id:'password',
	        inputType:'password',
	        allowBlank: false
	    }
	    ,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: avmon.system.userName,
	        name: 'real_name',
	        allowBlank: false
	    }
		,deptId
		,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: 'Email',
	        name: 'email',
	        vtype: 'email' 
	        //验证电子邮件格式的正则表达式   
//            regex : /^([\w]+)(.[\w]+)*@([\w-]+\.){1,5}([A-Za-z]){2,4}$/,   
//            regexText:'格式错误'//验证错误之后的提示信息, 
	    }
		,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: avmon.system.mobilePhone,
	        name: 'mobile'
//	        ,regex:"^[0-9]*$" 
//	        ,regexText="请输入数字"
	    }
		,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: avmon.system.officePhone,
	        name: 'office_phone'
	    }
		,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: avmon.system.note,
	        name: 'remark'
	    }
    ],
    buttons: [{
        text: avmon.common.save,
        formBind: true, //only enabled once the form is valid
        disabled: true,
        iconCls:'icon-save',
        handler: function() {
            var submitForm = this.up('form').getForm();
            var checkboxs = [];
            var checks = submitForm.findField('roles').getValue();
            for (var p in checks) {
                checkboxs.push(checks[p]); //checkbox多选传递值，将值传入数组中后用baseParams传递
            }
            if(checkboxs.length==0){
            	Ext.MessageBox.alert(avmon.common.reminder, '请选择角色!');
            }else{
            	submitForm.url = "saveUpdateUserInfo?roleIds=" + checkboxs;
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

//动态添加role checkbox group
Ext.Ajax.request({
    url: 'findAllCheckBoxRoles',
    success: function(response, opts) {
		var allRoles = Ext.JSON.decode(response.responseText);
		var columNum=3; //设置checkbox的列数,默认是3
		if(columNum<3)   //如果checkbox个数小于3时，列的长度设成checkbox个数
     	columNum=allRoles.length; 
		var rolesGroup = Ext.create('Ext.form.CheckboxGroup',{
			labelStyle : 'text-align:right;width:80;',
		    xtype:'checkboxgroup',
			fieldLabel:avmon.system.role,
			name:'roles',
			id:'roles',
			width:340,
			columns:columNum,
			items:allRoles     
		});
		
		editForm.add(rolesGroup);
    	editForm.doLayout();
	},
    failure: function(response, opts) {
    	
    }
});

var eidtWin = Ext.create('Ext.window.Window',{
    title: avmon.system.add,
    height: 400,
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
Ext.define('SYS.view.PortalUsersGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.PortalUsersGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'PortalUsers_grid',
            title: avmon.system.userManagement,
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
	                text: avmon.system.account,
	                width: 100,
	                sortable: true,
	                dataIndex: 'account'
	            }
	            ,{
	                text: avmon.system.userName,
	                width: 100,
	                sortable: true,
	                dataIndex: 'real_name'
	            }
	            ,{
	                text: avmon.system.userRole,
	                width: 100,
	                sortable: false,
	                dataIndex: 'role_names'
	            }
	            ,{
	                text: avmon.system.department,
	                width: 100,
	                sortable: true,
	                dataIndex: 'dept_name'
	            }
				,{
	                text: 'Email',
	                width: 100,
	                sortable: true,
	                dataIndex: 'email'
	            }
				,{
	                text: avmon.system.mobilePhone,
	                width: 100,
	                sortable: true,
	                dataIndex: 'mobile'
	            }
				,{
	                text: avmon.system.officePhone,
	                width: 100,
	                sortable: true,
	                dataIndex: 'office_phone'
	            }
				,{
	                text: avmon.system.note,
	                width: 100,
	                sortable: true,
	                dataIndex: 'remark'
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
									    url: 'deleteUserByIds?ids=' + ids.join(','),
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
                //,'-',searchField
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
					    	deptId.setDefaultValue(record.data.dept_ids,record.data.dept_name);
					    }
					});
		    		var roleIds = record.data.role_ids.split(",");
		    		for(i in roleIds){
		    			if(roleIds[i].length>0)
		    			Ext.getCmp('rols'+roleIds[i]).setValue(true);
		    		}
		    	}
		    }
        });
        this.callParent(arguments);
    }
});