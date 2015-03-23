Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',
    layout: {
        align: 'stretch',
        type: 'hbox'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    width: 316,
                    layout: {
                        type: 'border'
                    },
                    title: 'Menu',
                    resizable : true,
                    items: [
                        {
                            xtype: 'treepanel',
                            flex: 1,
                            region: 'center',
                            id: 'menuTree',
                            store: 'treeStore',
                            rootVisible: false,
                            viewConfig: {
                                listeners: {
                                    itemclick: {
                                        fn: me.onTreeviewItemClick,
                                        scope: me
                                    }
                                }
                            }
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            flex: 1,
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'buttongroup',
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            icon: 'image/refresh.gif',
                                            text: avmon.config.refresh,
                                            listeners: {
                                                click: {
                                                    fn: me.onButtonClick3,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'buttongroup',
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            icon: 'image/add.gif',
                                            text: avmon.config.directory,
                                            listeners: {
                                                click: {
                                                    fn: me.onButtonClick,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'buttongroup',
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            icon: 'image/add2.gif',
                                            text: avmon.config.view,
                                            listeners: {
                                                click: {
                                                    fn: me.onButtonClick1,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                },
                                {
                                    xtype: 'buttongroup',
                                    columns: 2,
                                    items: [
                                        {
                                            xtype: 'button',
                                            icon: 'image/delete.gif',
                                            text: avmon.config.remove,
                                            listeners: {
                                                click: {
                                                    fn: me.onButtonClick2,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    ],
                    listeners: {
                        afterrender: {
                            fn: me.onPanelAfterRender,
                            scope: me
                        }
                    }
                },
                {
                    xtype: 'panel',
                    flex: 1,
                    layout: {
                        align: 'stretch',
                        type: 'vbox'
                    },
                    title: avmon.config.monitorViewManagement,
                    items: [
                        {
                            xtype: 'form',
                            flex: 1,
                            id: 'editViewForm',
                            layout: {
                                align: 'stretch',
                                type: 'vbox'
                            },
                            bodyPadding: 10,
                            dockedItems: [
                                {
                                    xtype: 'toolbar',
                                    flex: 1,
                                    dock: 'top',
                                    items: [
                                        {
                                            xtype: 'buttongroup',
                                            columns: 2,
                                            items: [
                                                {
                                                    xtype: 'button',
                                                    id: 'saveFilter',
                                                    icon: 'image/save.gif',
                                                    text: avmon.common.save,
                                                    listeners: {
                                                        click: {
                                                            fn: me.onButtonClick6,
                                                            scope: me
                                                        }
                                                    }
                                                }
                                            ]
                                        }
                                    ]
                                }
                            ],
                            items: [
                                {
                                    xtype: 'fieldset',
                                    title: avmon.config.basicInformation,
                                    items: [
                                        {
                                            xtype: 'textfield',
                                            anchor: '100%',
                                            id: 'VIEW_ID',
                                            fieldLabel: avmon.alarm.viewID,
                                            labelWidth: 60,
                                            readOnly: true,
                                            allowBlank: false
                                        },
                                        {
                                            xtype: 'textfield',
                                            anchor: '100%',
                                            id: 'VIEW_NAME',
                                            fieldLabel: avmon.alarm.viewName,
                                            labelWidth: 60,
                                            allowBlank: false
                                        }
                                    ]
                                },
                                {
                                    xtype: 'fieldset',
                                    flex: 1,
                                    layout: {
                                        align: 'stretch',
                                        type: 'vbox'
                                    },
                                    title: avmon.config.filterCondition,
                                    items: [
                                        {
                                            xtype: 'gridpanel',
                                            flex: 1,
                                            id: 'filterGrid',
                                            autoScroll: true,
                                            store: 'filterStore',
                                            dockedItems: [
                                                {
                                                    xtype: 'toolbar',
                                                    dock: 'top',
                                                    items: [
                                                        {
                                                            xtype: 'buttongroup',
                                                            columns: 2,
                                                            items: [
                                                                {
                                                                    xtype: 'button',
                                                                    id: 'addFilter',
                                                                    icon: 'image/add.gif',
                                                                    text: avmon.config.newIncrease,
                                                                    listeners: {
                                                                        click: {
                                                                            fn: me.onButtonClick4,
                                                                            scope: me
                                                                        }
                                                                    }
                                                                }
                                                            ]
                                                        },
                                                        {
                                                            xtype: 'buttongroup',
                                                            columns: 2,
                                                            items: [
                                                                {
                                                                    xtype: 'button',
                                                                    id: 'delFilter',
                                                                    icon: 'image/delete.gif',
                                                                    text: avmon.common.deleted,
                                                                    listeners: {
                                                                        click: {
                                                                            fn: me.onButtonClick5,
                                                                            scope: me
                                                                        }
                                                                    }
                                                                }
                                                            ]
                                                        }
                                                    ]
                                                }
                                            ],
                                            viewConfig: {
                                                listeners: {
                                                    itemdblclick: {
                                                        fn: me.onGridviewItemDblClick,
                                                        scope: me
                                                    }
                                                }
                                            },
                                            columns: [
                                                {
                                                    xtype: 'gridcolumn',
                                                    width: 50,
                                                    dataIndex: 'FILTER_NO',
                                                    text: avmon.config.condition
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    width: 200,
                                                    dataIndex: 'FILTER_FIELD',
                                                    text: avmon.config.column
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    width: 80,
                                                    dataIndex: 'FILTER_OPERATOR',
                                                    text: avmon.config.operationalCharacter
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    width: 200,
                                                    defaultWidth: 200,
                                                    dataIndex: 'FILTER_VALUE',
                                                    text: avmon.config.value
                                                }
                                            ],
                                            selModel: Ext.create('Ext.selection.CheckboxModel', {

                                            })
                                        }
                                    ]
                                },
                                {
                                    xtype: 'fieldset',
                                    title: avmon.config.other,
                                    items: [
                                        {
                                            xtype: 'textfield',
                                            anchor: '100%',
                                            id: 'FILTERS_RULE',
                                            fieldLabel: avmon.config.combinationalRule,
                                            labelWidth: 60
                                        }
                                    ]
                                }
                            ]
                        }
                    ],
                    listeners: {
                        afterrender: {
                            fn: me.onPanelAfterRender1,
                            scope: me
                        }
                    }
                }
            ]
        });
        me.callParent(arguments);
    },
    onTreeviewItemClick: function(dataview, record, item, index, e, eOpts) {
        // 存放选择节点id
        Ext.selectViewId=record.data.id;
        if (record.data.isDir == '0' || record.data.isDir == 0) {
            // 选择视图的场合
            var viewId = record.data.id;
            Ext.Ajax.request({  
                url:'editMonitorView',  
                params:{  
                    //传递参数  
                    viewId:viewId
                },  
                async : false,
                success:function(response, eOpts){  
                    //Ext.Msg.alert('提示信息','修改成功！');  
                    var responseArray = Ext.JSON.decode(response.responseText);
                    //清空页面数据
                    Ext.getCmp('FILTERS_RULE').setValue('');
                    Ext.getCmp('VIEW_ID').setValue('');
                    Ext.getCmp('VIEW_NAME').setValue('');
                    if (responseArray.VIEW_ID != null) {
                        Ext.getCmp('VIEW_ID').setValue(responseArray.VIEW_ID.toString());
                    }
                    if (responseArray.VIEW_NAME != null) {
                        Ext.getCmp('VIEW_NAME').setValue(responseArray.VIEW_NAME.toString());
                    }
                    if (responseArray.FILTERS_RULE != null) {
                        Ext.getCmp('FILTERS_RULE').setValue(responseArray.FILTERS_RULE.toString());
                    }
                    // 先清除原数据
                    Ext.getCmp('filterGrid').getStore().removeAll();
                    if (responseArray.FILTERS != null) {                
                        var filters = Ext.JSON.decode(responseArray.FILTERS);
                        var len = 0;
                        for (var temp in filters) {
                            //console.dir(filters[temp]);
                            var FILTER_NO = filters[temp].FILTER_NO;
                            var FILTER_FIELD = filters[temp].FILTER_FIELD;
                            var FILTER_OPERATOR = filters[temp].FILTER_OPERATOR;
                            var FILTER_VALUE = filters[temp].FILTER_VALUE;
                            var data = {FILTER_NO: FILTER_NO, FILTER_FIELD: FILTER_FIELD, FILTER_OPERATOR: FILTER_OPERATOR, FILTER_VALUE: FILTER_VALUE};
                            Ext.getCmp('filterGrid').getStore().insert(len, data);
                            len = len + 1;
                        }
                    }
                    Ext.getCmp('saveFilter').setDisabled(false);
                    Ext.getCmp('addFilter').setDisabled(false);
                    Ext.getCmp('delFilter').setDisabled(false);
                    Ext.getCmp('FILTERS_RULE').setReadOnly(false);
                },
                failure:function(){}
            });
        } else {
            // 选择目录的场合
            var viewId = record.data.id;
            Ext.Ajax.request({  
                url:'editMonitorView',  
                params:{  
                    viewId:viewId
                },  
                async : false,
                success:function(response, eOpts){
                    var responseArray = Ext.JSON.decode(response.responseText);
                    if (responseArray.VIEW_ID != null) {
                        Ext.getCmp('VIEW_ID').setValue(responseArray.VIEW_ID.toString());
                    }
                    if (responseArray.VIEW_NAME != null) {
                        Ext.getCmp('VIEW_NAME').setValue(responseArray.VIEW_NAME.toString());
                    }
                    Ext.getCmp('FILTERS_RULE').setValue('');
                    // 先清除原数据
                    Ext.getCmp('filterGrid').getStore().removeAll();
                    //Ext.getCmp('saveFilter').setDisabled(true);
                    Ext.getCmp('addFilter').setDisabled(true);
                    Ext.getCmp('delFilter').setDisabled(true);
                    Ext.getCmp('FILTERS_RULE').setReadOnly(true);
                },
                failure:function(){  
                    //Ext.Msg.alert('错误信息','系统错误');  
                }
            });
        }
    },
    onButtonClick3: function(button, e, eOpts) {
        var tree=Ext.getCmp("menuTree");
        if(!tree.getStore().isLoading()){
            if(tree.getStore().getRootNode()){
                tree.getStore().getRootNode().removeAll();
            }
            tree.getStore().load({params:{id:"root"}});
            Ext.selectViewId = "root";
            Ext.getCmp('VIEW_ID').setValue("root");
            Ext.getCmp('VIEW_NAME').setValue("root");
            Ext.getCmp('FILTERS_RULE').setValue("");
            // 先清除原数据
            Ext.getCmp('filterGrid').getStore().removeAll();
            Ext.getCmp('saveFilter').setDisabled(true);
            Ext.getCmp('addFilter').setDisabled(true);
            Ext.getCmp('delFilter').setDisabled(true);
            Ext.getCmp('FILTERS_RULE').setReadOnly(true);
        }
    },
    onButtonClick: function(button, e, eOpts) {
        // 是否选择树节点
        if (Ext.selectViewId!='root') {
            // 弹出输入框
            Ext.MessageBox.prompt(avmon.config.newIncrease, avmon.config.pleaseEnterDirectoryName, function(btn, text) {
                if (text != null && text != '') {
                    Ext.Ajax.request({  
                        url:'addFolderTreeView',  
                        params:{  
                            //传递参数  
                            caption:text,
                            parentId:Ext.selectViewId
                        },  
                        async : false,
                        success:function(response, eOpts){  
                            var responseArray = Ext.JSON.decode(response.responseText);
                            var viewId = responseArray.VIEW_ID.toString();
                            if (viewId != null && viewId != '') {
                                // 刷新树结构
                                var tree=Ext.getCmp("menuTree");
                                if(!tree.getStore().isLoading()){
                                    if(tree.getStore().getRootNode()){
                                        tree.getStore().getRootNode().removeAll();
                                    }
                                    tree.getStore().load({params:{id:"root"}});
                                }
                            }
                        },
                        failure:function(){}
                    });
                }
            });
        } else {
            //只有是在根节点的情况下需要选择是公共视图、组视图还是私有视图
            var loginUser = Ext.loginUserId;
            var viewType;
            if(loginUser=="admin"){//只有admin可以定义公共视图
                viewType = Ext.create('Ext.form.field.ComboBox', {
                    labelStyle : 'text-align:left;width:80;',
                    width : 340,
                    fieldLabel : avmon.config.viewType,
                    name : 'viewType',
                    //id:'viewType',
                    xtype : 'combobox',
                    store : Ext.create('Ext.data.Store', {
                        fields : ['value', 'display'],
                        data : [{
                            'value' : 'public',
                            'display' : avmon.config.publicView
                        }, {
                            'value' : 'group',
                            'display' : avmon.config.groupView
                        }, {
                            'value' : 'private',
                            'display' : avmon.config.privateView
                        }]
                    }),
                    queryMode : 'local',
                    editable : false,
                    valueField : 'value',
                    displayField : 'display',
                    allowBlank: false  
                });
            }else{
                viewType = Ext.create('Ext.form.field.ComboBox', {
                    labelStyle : 'text-align:left;width:80;',
                    width : 340,
                    fieldLabel : avmon.config.viewType,
                    name : 'viewType',
                    xtype : 'combobox',
                    store : Ext.create('Ext.data.Store', {
                        fields : ['value', 'display'],
                        data : [{
                            'value' : 'group',
                            'display' : avmon.config.groupView
                        }, {
                            'value' : 'private',
                            'display' : avmon.config.privateView
                        }]
                    }),
                    queryMode : 'local',
                    editable : false,
                    valueField : 'value',
                    displayField : 'display',
                    allowBlank: false 
                });
            }
            var editForm = Ext.create('Ext.form.Panel', {
                bodyPadding: 5,
                border: false,
                frame: true,
                buttonAlign: 'center',
                xtype: 'filedset',
                url: 'addFolderTreeView?parentId=root',
                defaultType: 'textfield',
                items: [
                {
                    labelStyle : 'text-align:left;width:80;',
                    width:340,
                    fieldLabel: avmon.config.directoryName,
                    name: 'caption',
                    allowBlank: false
                },viewType
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
                                    var viewId = action.result.VIEW_ID;//responseArray.VIEW_ID.toString();
                                    if (viewId != null && viewId != '') {
                                        // 刷新树结构
                                        var tree=Ext.getCmp("menuTree");
                                        if(!tree.getStore().isLoading()){
                                            if(tree.getStore().getRootNode()){
                                                tree.getStore().getRootNode().removeAll();
                                            }
                                            tree.getStore().load({params:{id:"root"}});
                                        }
                                    }
                                    eidtWin.close();
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
                height: 160,
                width:400,
                hidden: true,
                iconCls:'icon-form',
                layout: 'fit',
                plain: true,
                modal:true,
                closable: true,
                closeAction: 'destroy',
                items: [editForm]
            });
            eidtWin.show();
        }
    },
    onButtonClick1: function(button, e, eOpts) {
        // 是否选择树节点
        if (Ext.selectViewId!='root') {
            // 弹出输入框
            Ext.MessageBox.prompt(avmon.config.newIncrease, avmon.config.pleaseEnterViewName, function(btn, text) {
                if (text != null && text != '') {
                    Ext.Ajax.request({  
                        url:'addTreeView',  
                        params:{  
                            //传递参数  
                            caption:text,
                            parentId:Ext.selectViewId
                        },  
                        async : false,
                        success:function(response, eOpts){  
                            //Ext.Msg.alert('提示信息','修改成功！');  
                            var responseArray = Ext.JSON.decode(response.responseText);
                            var viewId = responseArray.VIEW_ID.toString();
                            if (viewId != null && viewId != '') {
                                // 刷新树结构
                                var tree=Ext.getCmp("menuTree");
                                if(!tree.getStore().isLoading()){
                                    if(tree.getStore().getRootNode()){
                                        tree.getStore().getRootNode().removeAll();
                                    }
                                    tree.getStore().load({params:{id:"root"}});
                                }
                            }
                        },
                        failure:function(){}
                    });
                }
            });
        } else {
            //只有是在根节点的情况下需要选择是公共视图、组视图还是私有视图
            var loginUser = Ext.loginUserId;
            var viewType;
            if(loginUser=="admin"){//只有admin可以定义公共视图
                viewType = Ext.create('Ext.form.field.ComboBox', {
                    labelStyle : 'text-align:left;width:80;',
                    width : 340,
                    fieldLabel : avmon.config.viewType,
                    name : 'viewType',
                    //id:'viewType',
                    xtype : 'combobox',
                    store : Ext.create('Ext.data.Store', {
                        fields : ['value', 'display'],
                        data : [{
                            'value' : 'public',
                            'display' : avmon.config.publicView
                        }, {
                            'value' : 'group',
                            'display' : avmon.config.groupView
                        }, {
                            'value' : 'private',
                            'display' : avmon.config.privateView
                        }]
                    }),
                    queryMode : 'local',
                    editable : false,
                    valueField : 'value',
                    displayField : 'display',
                    allowBlank: false  
                });
            }else{
                viewType = Ext.create('Ext.form.field.ComboBox', {
                    labelStyle : 'text-align:left;width:80;',
                    width : 340,
                    fieldLabel : avmon.config.viewType,
                    name : 'viewType',
                    //id:'viewType',
                    xtype : 'combobox',
                    store : Ext.create('Ext.data.Store', {
                        fields : ['value', 'display'],
                        data : [{
                            'value' : 'group',
                            'display' : avmon.config.groupView
                        }, {
                            'value' : 'private',
                            'display' : avmon.config.privateView
                        }]
                    }),
                    queryMode : 'local',
                    editable : false,
                    valueField : 'value',
                    displayField : 'display',
                    allowBlank: false 
                });
            }
            var editForm = Ext.create('Ext.form.Panel', {
                bodyPadding: 5,
                border: false,
                frame: true,
                buttonAlign: 'center',
                xtype: 'filedset',
                url: 'addTreeView?parentId=root',
                defaultType: 'textfield',
                items: [
                {
                    labelStyle : 'text-align:left;width:80;',
                    width:340,
                    fieldLabel: avmon.alarm.viewName,
                    name: 'caption',
                    allowBlank: false
                },viewType
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
                                    //Ext.Msg.alert('提示信息','修改成功！');  
                                    var viewId = action.result.VIEW_ID;
                                    if (viewId != null && viewId != '') {
                                        // 刷新树结构
                                        var tree=Ext.getCmp("menuTree");
                                        if(!tree.getStore().isLoading()){
                                            if(tree.getStore().getRootNode()){
                                                tree.getStore().getRootNode().removeAll();
                                            }
                                            tree.getStore().load({params:{id:"root"}});
                                        }
                                    }
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
                title: avmon.config.add,
                height: 160,
                width:400,
                hidden: true,
                iconCls:'icon-form',
                layout: 'fit',
                plain: true,
                modal:true,
                closable: true,
                closeAction: 'destroy',
                items: [editForm]
            });
            eidtWin.show();
        }
    },
    onButtonClick2: function(button, e, eOpts) {
        // 是否选择树节点
        if (Ext.selectViewId) {
            Ext.Ajax.request({  
                url:'delTreeViewById',  
                params:{  
                    //传递参数  
                    viewId:Ext.selectViewId
                },  
                async : false,
                success:function(response, eOpts){  
                    //Ext.Msg.alert('提示信息','修改成功！');  
                    var responseArray = Ext.JSON.decode(response.responseText);
                    var result = responseArray.result.toString();
                    if (result == 'success') {
                        // 刷新树结构
                        var tree=Ext.getCmp("menuTree");
                        if(!tree.getStore().isLoading()){
                            if(tree.getStore().getRootNode()){
                                tree.getStore().getRootNode().removeAll();
                            }
                            tree.getStore().load({params:{id:"root"}});
                        }
                    }
                    Ext.selectViewId = "root";
                    Ext.getCmp('VIEW_ID').setValue("root");
                    Ext.getCmp('VIEW_NAME').setValue("root");
                    Ext.getCmp('FILTERS_RULE').setValue("");
                    // 先清除原数据
                    Ext.getCmp('filterGrid').getStore().removeAll();
                    Ext.getCmp('saveFilter').setDisabled(true);
                    Ext.getCmp('addFilter').setDisabled(true);
                    Ext.getCmp('delFilter').setDisabled(true);
                    Ext.getCmp('FILTERS_RULE').setReadOnly(true);
                },
                failure:function(){}
            });
        } else {
            Ext.Msg.alert(avmon.common.reminder,avmon.config.selectNode);  
        }
    },
    onPanelAfterRender: function(component, eOpts) {
        Ext.selectViewId="root";
    },
    onButtonClick6: function(button, e, eOpts) {
        var myform = Ext.getCmp('editViewForm').getForm();
        if (myform.isValid()) {
            var records = [];
            for(var i = 0; i < Ext.getCmp('filterGrid').getStore().data.length; i++){   
                var FILTER_NO = Ext.getCmp('filterGrid').getStore().getAt(i).get('FILTER_NO');
                var FILTER_FIELD = Ext.getCmp('filterGrid').getStore().getAt(i).get('FILTER_FIELD');
                var FILTER_OPERATOR = Ext.getCmp('filterGrid').getStore().getAt(i).get('FILTER_OPERATOR');
                var FILTER_VALUE = Ext.getCmp('filterGrid').getStore().getAt(i).get('FILTER_VALUE');;
                var data = {FILTER_NO:'', FILTER_FIELD:'', FILTER_OPERATOR:'', FILTER_VALUE:''};
                data.FILTER_NO = encodeURI(FILTER_NO);
                data.FILTER_FIELD = encodeURI(FILTER_FIELD);
                data.FILTER_OPERATOR = encodeURI(FILTER_OPERATOR);
                data.FILTER_VALUE = encodeURI(FILTER_VALUE);
                records.push(data);
            }
            Ext.Ajax.request({  
                url:'saveMonitorView',  
                params:{  
                    //传递参数  
                    viewId: Ext.getCmp('VIEW_ID').getValue(),
                    viewNm: Ext.getCmp('VIEW_NAME').getValue(),
                    filters: Ext.JSON.encode(records),
                    filterRule: Ext.getCmp('FILTERS_RULE').getValue()
                }, 
                async : false,
                success:function(response, eOpts){        
                    var responseArray = Ext.JSON.decode(response.responseText);
                    // 刷新树节点列表
                    var tree=Ext.getCmp("menuTree");
                    if(!tree.getStore().isLoading()){
                        if(tree.getStore().getRootNode()){
                            tree.getStore().getRootNode().removeAll();
                        }
                        tree.getStore().load({params:{id:"root"}});
                    }
                    // 重新选择保存前选择节点
                    if(responseArray.result == 'success') {
                        var tree = Ext.getCmp("menuTree");
                        var path = responseArray.message;
                        tree.expandPath(path, 'id', '/', function(bSuccess, oLastNode){
                            if(!bSuccess) 
                            return;
                            //focus 节点，并选中节点！
                            Ext.getCmp("menuTree").getSelectionModel().select(oLastNode, true);
                        });
                        Ext.Msg.alert(avmon.common.message,avmon.config.saveSuccess);  
                    }
                },
                failure:function(){}
            });
        }
    },
    onButtonClick4: function(button, e, eOpts) {
        var win = Ext.filterWindow;
        var gridCnt = Ext.getCmp('filterGrid').getStore().data.length;
        if(!win){
            win=Ext.create('widget.filterWindow');
            Ext.filterWindow=win;
            win.needReload=true;
            win.show();
            Ext.getCmp('FILTER_NO').setValue(gridCnt + 1);
        } else {
            win.show();
            // 将历史记录清空
            Ext.getCmp('FILTER_NO').setValue(gridCnt + 1);
            Ext.getCmp('FILTER_FIELD').setValue('');
            Ext.getCmp('FILTER_OPERATOR').setValue('');
            Ext.getCmp('FILTER_VALUE').setValue('');
        }
        Ext.filterWindowMode=0;
    },
    onButtonClick5: function(button, e, eOpts) {
        var filterGrid = Ext.getCmp('filterGrid');
        var selrecord = filterGrid.getSelectionModel().getSelection();
        if(selrecord.length == 0){
            Ext.MessageBox.show({
                title:avmon.common.reminder,
                msg:avmon.config.selectOperateLine
                //icon: Ext.MessageBox.INFO
            })
            return;
        }else{
            for(var i = 0; i < selrecord.length; i++){
                var id = selrecord[i].get("FILTER_NO")
                var row = Ext.getCmp('filterGrid').getStore().getById(id);
                if (row) {
                    Ext.getCmp('filterGrid').getStore().remove(row);
                }
            }
        }
    },
    onGridviewItemDblClick: function(dataview, record, item, index, e, eOpts) {
        var win = Ext.filterWindow;
        if(!win){
            win=Ext.create('widget.filterWindow');
            Ext.filterWindow=win;
            win.needReload=true;
        }
        Ext.filterWindowMode=1;
        win.show();
        var filterGrid = Ext.getCmp('filterGrid');
        Ext.getCmp('FILTER_NO').setValue(record.data.FILTER_NO);
        Ext.getCmp('FILTER_FIELD').setValue(record.data.FILTER_FIELD);
        Ext.getCmp('FILTER_OPERATOR').setValue(record.data.FILTER_OPERATOR);
        Ext.getCmp('FILTER_VALUE').setValue(record.data.FILTER_VALUE);
    },
    onPanelAfterRender1: function(component, eOpts) {
        Ext.getCmp('saveFilter').setDisabled(true);
        Ext.getCmp('addFilter').setDisabled(true);
        Ext.getCmp('delFilter').setDisabled(true);
        Ext.getCmp('FILTERS_RULE').setReadOnly(true);
    }
});