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
			fieldLabel : '所属部门',
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
//var checkboxs = [];
var editForm = Ext.create('Ext.form.Panel', {
    bodyPadding: 5,
    border: false,
    frame: true,
    buttonAlign: 'center',
    xtype: 'filedset',
    url: 'saveUpdateUserInfo',
//    layout: 'column',
//    fieldDefaults:{//统一设置表单字段默认属性
//    	labelSeparator :'：',//分隔符
//    	labelWidth : 60//标签宽度
//    },
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
	        fieldLabel: '账号',
	        name: 'user_id',
	        allowBlank: false
	    }
	    ,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: '密码',
	        name: 'password',
	        id:'password',
	        inputType:'password',
	        allowBlank: false
	    }
//	    ,{
//	    	labelStyle : 'text-align:right;width:80;',
//	    	width:340 ,
//	        fieldLabel: '密码确认',
//	        name: 'repassword',
//	        inputType:'password',
//	    	vtype : 'password',
//        	initialPassField : 'password',
//	        allowBlank: false
//	    }
	    ,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: '用户名称',
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
	        fieldLabel: '手机',
	        name: 'mobile'
//	        ,regex:"^[0-9]*$" 
//	        ,regexText="请输入数字"
	    }
		,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: '办公电话',
	        name: 'office_phone'
	    }
		,{
	    	labelStyle : 'text-align:right;width:80;',
	    	width:340 ,
	        fieldLabel: '备注',
	        name: 'remark'
	    }
    ],
    buttons: [{
        text: '保存',
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
            	Ext.MessageBox.alert('提示', '请选择角色!');
            }else{
            	submitForm.url = "saveUpdateUserInfo?roleIds=" + checkboxs;
	            if (submitForm.isValid()) {
	                submitForm.submit({
	                    method: 'POST',
	                    waitMsg:'正在保存，请稍等...',
	                    success: function(form, action) {
	                        Ext.MessageBox.alert('提示', action.result.msg);
                        	gridStore.load();
                       		eidtWin.hide();
	                    },
	                    failure:function(form, action){
	                    	Ext.MessageBox.alert('提示', action.result.msg);//'系统异常，请联系管理员!');
	                    }
	                });
	            }
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
			fieldLabel:'角色',
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
    title: '添加',
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
//创建搜索框，后台通过query参数获取查询条件
//var searchField = Ext.create('Ext.ux.form.SearchField',{
//	width: 300, 
//    fieldLabel: '搜索', 
//    labelWidth: 40, 
//    xtype: 'searchfield', 
//    store: gridStore
//});
Ext.define('SYS.view.PortalUsersGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.PortalUsersGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'PortalUsers_grid',
            title: "用户管理",
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
	                text: '账号',
	                width: 100,
	                sortable: true,
	                dataIndex: 'account'
	            }
	            ,{
	                text: '用户名称',
	                width: 100,
	                sortable: true,
	                dataIndex: 'real_name'
	            }
	            ,{
	                text: '用户角色',
	                width: 100,
	                sortable: false,
	                dataIndex: 'role_names'
	            }
	            ,{
	                text: '所属部门',
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
	                text: '手机',
	                width: 100,
	                sortable: true,
	                dataIndex: 'mobile'
	            }
				,{
	                text: '办公电话',
	                width: 100,
	                sortable: true,
	                dataIndex: 'office_phone'
	            }
				,{
	                text: '备注',
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
									    url: 'deleteUserByIds?ids=' + ids.join(','),
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
                //,'-',searchField
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