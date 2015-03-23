Ext.define('MyApp.view.ILOConfigWindow', {
    extend: 'Ext.window.Window',
    height: 450,
    width: 750,
    layout: {
        type: 'anchor'
    },
    title: avmon.config.iloHostConfiguration,
    opener:null,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    height: 189,
                    itemId: 'configPanel',
                    layout: {
                        type: 'absolute'
                    },
                    frame:true,
                    bodyBorder: false,
                    //bodyPadding: 20,
                    collapsed: false,
                    title: avmon.config.basicConfiguration,
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    iconCls: 'icon-save',
                                    text: avmon.common.save,
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick3,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ],
                    items: [
                        {
                            xtype: 'textfield',
                            x: 20,
                            y: 10,
                            itemId: 'ip',
                            width: 250,
                            fieldLabel: avmon.config.hostIP
                        },
                        {
                            xtype: 'textfield',
                            x: 360,
                            y: 10,
                            itemId: 'extParam1',
                            width: 250,
                            fieldLabel: avmon.config.reservedConfigurationItermOne
                        },
                        {
                            xtype: 'textfield',
                            x: 20,
                            y: 100,
                            itemId: 'password',
                            width: 250,
                            fieldLabel: avmon.config.password
                        },
                        {
                            xtype: 'textfield',
                            x: 20,
                            y: 70,
                            itemId: 'userName',
                            width: 250,
                            fieldLabel: avmon.config.userName
                        },
                        {
                            xtype: 'textfield',
                            x: 360,
                            y: 50,
                            itemId: 'extParam2',
                            width: 250,
                            fieldLabel: avmon.config.reservedConfigurationItermTwo
                        },
                        {
                            xtype: 'textfield',
                            x: 360,
                            y: 90,
                            itemId: 'extParam3',
                            width: 250,
                            fieldLabel: avmon.config.reservedConfigurationItermThree
                        },
                        {
                            xtype: 'textfield',
                            x: 20,
                            y: 40,
                            itemId: 'hostName',
                            width: 250,
                            fieldLabel: avmon.config.hostname
                        },
                        {
                            xtype: 'textfield',
                            x: 429,
                            y: 173,
                            hidden: true,
                            itemId: 'flag',
                            fieldLabel: 'Label'
                        },
                        {
                            xtype: 'textfield',
                            x: 429,
                            y: 173,
                            hidden: true,
                            itemId: 'oldIp',
                            fieldLabel: 'Label'
                        },
                        {
                            xtype: 'textfield',
                            x: 429,
                            y: 173,
                            hidden: true,
                            itemId: 'moId',
                            fieldLabel: 'Label'
                        }
                    ]
                },
                {
                    xtype: 'gridpanel',
                    height: 270,
                    itemId: 'policyGrid',
                    title: avmon.config.schedulingPolicy,
                    store: 'AmpInstPolicyStore',
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    iconCls: 'icon-edit',
                                    text: avmon.config.editSchedulingPolicy,
                                    id: 'editScheduleButId',
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'button',
                                    iconCls: 'icon-ok',
                                    text: avmon.config.issueByChosenPolicy,
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick1,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'button',
                                    iconCls: 'icon-ok',
                                    text: avmon.config.issueByAllPolicy,
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick2,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ],
                    viewConfig: {},
                    selModel: Ext.create('Ext.selection.CheckboxModel', {}),
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'kpiCode',
                            text: 'KPI Code'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'kpiName',
                            text: avmon.config.kpiName
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'schedule',
                            itemId: 'scheduleEditorColum',
                            text: avmon.config.schedulingConfigString,
                            editor: {
                            	itemId:'scheduleEditor',
                                xtype: 'textfield'
                            }
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'kpiGroup',
                            text: avmon.config.group
                        }
                    ],
//                    plugins: [
//                        Ext.create('Ext.grid.plugin.RowEditing', {
//                            ptype: 'rowediting'
//                        })
//                    ]
                    plugins: [
                          Ext.create('Ext.grid.plugin.CellEditing', {
                              ptype: 'cellediting',
                              pluginId: 'scheduleEditorPluginId',
                              clicksToEdit: 1,
                              triggerEvent: 'celldblclick',
                              listeners: {
                                  edit: {
                                      fn: me.onGridcelleditingpluginEdit,
                                      scope: me
                                  },beforeEdit: {
                                      fn: me.beforeGridcelleditingpluginEdit,
                                      scope: me
                                  }
                              }
                          })
                      ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onButtonClick3: function(button, e, options) {
        var iloConfigWin = button.ownerCt.ownerCt.ownerCt;
        var p = iloConfigWin.down("#policyGrid");
        var agentId = p.store.proxy.extraParams.agentId;
        var ampInstId = p.store.proxy.extraParams.ampInstId;
        var configPanel = iloConfigWin.down("#configPanel");
        var ip = configPanel.down("#ip").getValue();
        var oldIp = configPanel.down("#oldIp").getValue();
        var hostName = configPanel.down("#hostName").getValue();
        var userName = configPanel.down("#userName").getValue();
        var password = configPanel.down("#password").getValue();
        if(ip==""||ip.length==0){
        	Ext.MessageBox.alert(avmon.common.reminder, avmon.config.pleaseEnterIP);
        	return;
        }
        if(hostName==""||hostName.length==0){
        	Ext.MessageBox.alert(avmon.common.reminder, avmon.config.pleaseEnterHostName);
        	return;
        }
        if(userName==""||userName.length==0){
        	Ext.MessageBox.alert(avmon.common.reminder, avmon.config.pleaseEnterUserName);
        	return;
        }
        if(password==""||password.length==0){
        	Ext.MessageBox.alert(avmon.common.reminder, avmon.config.pleaseEnterPassword);
        	return;
        }
        var extParam1 = configPanel.down("#extParam1").getValue();
        var extParam2 = configPanel.down("#extParam2").getValue();
        var extParam3 = configPanel.down("#extParam3").getValue();
        var flag = configPanel.down("#flag").getValue();
        var moId = configPanel.down("#moId").getValue();
        var jsonText = "[]";
        if(flag=="new"){
        	 var policyGridStore = p.getStore();
             var policys = new Array();
             policyGridStore.each(function(rec){
                 policys.push(rec.data);
             });

             var jsonList = eval(policys); //将数组变Json对象
             jsonText = Ext.JSON.encode(jsonList); //json文本
        }
        Ext.Ajax.request({
            url: 'saveIloHost',
            method:'post',
            params:{
                'agentId':agentId,
                'ampInstId':ampInstId,
                'ip':ip,
                'oldIp':oldIp,
                'hostName':hostName,
                'userName':userName,
                'password':password,
                'extParam1':extParam1,
                'extParam2':extParam2,
                'extParam3':extParam3,
                'flag':flag,
                'moId':moId,
                'policys':jsonText
            },
            success: function(response, opts) {
                var returnJson = Ext.JSON.decode(response.responseText);
                Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
                iloConfigWin.opener.store.load();
                if(flag=="new"){
                	iloConfigWin.close();
                }
            },
            failure: function(response, opts) {
            	var returnJson = Ext.JSON.decode(response.responseText);
                Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
            }
        });
    },
    onButtonClick: function(button, e, options) {
		var grid = button.ownerCt.ownerCt;
        var selectCount = grid.getSelectionModel().getCount();

        if(selectCount == 0 || selectCount >1){
            Ext.MessageBox.show({
                title:avmon.config.wrong,
                msg:avmon.config.pleaseSelectASetOfStrategiesForEdit,
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR,
                animateTarget:'editScheduleButId'
            });
        }else{
            //开始编辑选中行
            var selectionModule = grid.getSelectionModel().getSelection()[0];
            var editorPlugin = grid.getPlugin("scheduleEditorPluginId");
            var editorColum = grid.down("#scheduleEditorColum");
            editorPlugin.startEdit(selectionModule,editorColum ); 
        }
    },
    onButtonClick1: function(button, e, options) {
    	var iloConfigWin = button.ownerCt.ownerCt.ownerCt;
    	var moId = iloConfigWin.down("#moId").getValue();
 		var grid = button.ownerCt.ownerCt;
        var selectCount = grid.getSelectionModel().getCount();
        if(selectCount == 0){
            Ext.MessageBox.show({
                title:avmon.config.wrong,
                msg:avmon.config.pleaseSelectNeedIssueStrategy,
                buttons: Ext.MessageBox.OK,
                icon: Ext.MessageBox.ERROR
            });
        }else{
            var dataSelection = grid.getSelectionModel().getSelection();
            var jsonSource = "[";    
            for(var i=0;i<dataSelection.length;i++){
                if(i == dataSelection.length-1){
                    jsonSource += Ext.JSON.encode(dataSelection[i].data);  
                }else{
                    jsonSource += Ext.JSON.encode(dataSelection[i].data)+","; 
                }
            }
            jsonSource+= "]";
            //下发策略
            //Ext.getCmp('normalAmpSetWindow').pushSelectedSchedule(jsonSource,'pushSelectedScheduleButId',agentId,"part");
            var processBar = Ext.MessageBox.show({
	            msg: avmon.config.issuingStrategyPleaseWait,
	            progressText: avmon.config.issuing,
	            width:300,
	            wait:true
	        }); 
	        var url = "pushSelectedIloHostSchedule";
	        Ext.Ajax.request({
	            url: url,
	            method :"POST",
	            params: {
	                agentAmpInfo:jsonSource,
	                moId:moId
	            },
	            success: function(response){
	                processBar.close();
	                var returnJson = Ext.JSON.decode(response.responseText);
	                if(returnJson.success){
	                    Ext.MessageBox.show({
	                        title: avmon.common.message,
	                        msg: avmon.config.issueStrategySuccess,
	                        buttons: Ext.MessageBox.OK,
	                        icon: Ext.MessageBox.INFO,
	                        toFrontOnShow:true
	                    });
	                }else{
	                    Ext.MessageBox.show({
	                        title: avmon.config.issueStrategyFailure,
	                        msg: returnJson.errorMsg,
	                        buttons: Ext.MessageBox.OK,
	                        icon: Ext.MessageBox.ERROR,
	                        toFrontOnShow:true
	                    }); 
	                }
	            },
	            failure: function(response, opts) {
	                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
	            }
	        });
        }
    },
    onButtonClick2: function(button, e, options) {
		var iloConfigWin = button.ownerCt.ownerCt.ownerCt;
    	var moId = iloConfigWin.down("#moId").getValue();
 		var grid = button.ownerCt.ownerCt;
        var dataSelection = grid.store.data;
        var jsonSource = "["; 
        grid.getStore().each(function(record){
			jsonSource += Ext.JSON.encode(dataSelection[i])+","; 
		});
        if(jsonSource.length>1){
        	jsonSource = jsonSource.substring(0,jsonSource.length-1);
        }
        jsonSource+= "]";
        //下发策略
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.issuingStrategyPleaseWait,
            progressText: avmon.config.issuing,
            width:300,
            wait:true
        }); 
        var url = "pushSelectedIloHostSchedule";
        Ext.Ajax.request({
            url: url,
            method :"POST",
            params: {
                agentAmpInfo:jsonSource,
                moId:moId
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                if(returnJson.success){
                    Ext.MessageBox.show({
                        title: avmon.common.message,
                        msg: avmon.config.issueStrategySuccess,
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.MessageBox.INFO,
                        toFrontOnShow:true
                    });
                }else{
                    Ext.MessageBox.show({
                        title: avmon.config.issueStrategyFailure,
                        msg: returnJson.errorMsg,
                        buttons: Ext.MessageBox.OK,
                        icon: Ext.MessageBox.ERROR,
                        toFrontOnShow:true
                    }); 
                }
            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }

        });
    },
    beforeGridcelleditingpluginEdit: function(editor, e, options) {
        var win = editor.view.ownerCt.ownerCt;        
        var flag = win.down("#flag").getValue();
        if(flag=="new"){return false}
    },
    onGridcelleditingpluginEdit: function(editor, e, options) {
        var newValue = editor.context.value;
        var oldValue = editor.context.originalValue;
        if(newValue == oldValue){
        	return;
        }
        var recordSource = e.record.data;
        var win = editor.view.ownerCt.ownerCt;
        var agentId = recordSource.agentId;
        var ampInstId = recordSource.ampInstId;
        var kpiCode = recordSource.kpiCode;
        var kpiGroup = recordSource.kpiGroup;
        var nodeKey = win.down("#ip").getValue();
		if(recordSource.kpiGroup==null || recordSource.kpiGroup.length==0){
			var processBar = Ext.MessageBox.show({
                msg: avmon.config.savingDataPleaseWait,
                progressText: avmon.config.saving,
                width:300,
                wait:true
            });
            Ext.Ajax.request({
                url: 'saveIloSchedule',
                method :"POST",
                params: {
                    ampInstId:ampInstId,
                    agentId:agentId,
                    nodeKey:nodeKey,
                    kpiCode:kpiCode,
                    schedule:newValue,
                    kpiGroup:null
                },
                success: function(response){
                    processBar.close();
                    var jsonSource = Ext.JSON.decode(response.responseText);
                    if(jsonSource.success){
                        Ext.MessageBox.show({
                            title: avmon.common.message,
                            msg: avmon.config.schedulingPolicyChangeSuccess,
                            buttons: Ext.MessageBox.OK,
                            icon: Ext.MessageBox.INFO,
                            toFrontOnShow:true,
                            animateTarget:"editScheduleButId"
                        });
                    }else{
                        Ext.MessageBox.show({
                            title: avmon.config.schedulingPolicyChangeFailure,
                            msg: jsonSource.errorMsg,
                            buttons: Ext.MessageBox.OK,
                            icon: Ext.MessageBox.ERROR,
                            toFrontOnShow:true,
                            animateTarget:"editScheduleButId"
                        }); 
                    }
                },
                failure: function(response, opts) {
                    Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
                }
            }); 
		}else{
			Ext.MessageBox.confirm(avmon.config.confirmChanges, avmon.config.confirmTheOperation, function(but){
	            if(but == "yes"){
	                var processBar = Ext.MessageBox.show({
	                    msg: avmon.config.savingDataPleaseWait,
	                    progressText: avmon.config.saving,
	                    width:300,
	                    wait:true
	                });
	                Ext.Ajax.request({
	                    url: 'saveIloSchedule',
	                    method :"POST",
	                    params: {
	                        ampInstId:ampInstId,
		                    agentId:agentId,
		                    nodeKey:nodeKey,
		                    kpiCode:kpiCode,
		                    schedule:newValue,
		                    kpiGroup:kpiGroup
	                    },
	                    success: function(response){
	                        processBar.close();
	                        var jsonSource = Ext.JSON.decode(response.responseText);
	                        if(jsonSource.success){
	                            Ext.MessageBox.show({
	                                title: avmon.common.message,
	                                msg: avmon.config.schedulingPolicyChangeSuccess,
	                                buttons: Ext.MessageBox.OK,
	                                icon: Ext.MessageBox.INFO,
	                                toFrontOnShow:true,
	                                animateTarget:"editScheduleButId"
	                            });
	                            //加载策略数据	                            
	                            var schduleStore =  win.down("#policyGrid").getStore();
	                            var schduleStoreProxy = schduleStore.getProxy();
	                            schduleStoreProxy.extraParams.ampInstId = ampInstId;
	                            schduleStoreProxy.extraParams.agentId = agentId;
	                            schduleStoreProxy.extraParams.flag = "edit";
	                            schduleStoreProxy.extraParams.nodeKey = nodeKey;
	                            schduleStore.load();
	                        }else{
	                            Ext.MessageBox.show({
	                                title: avmon.config.schedulingPolicyChangeFailure,
	                                msg: jsonSource.errorMsg,
	                                buttons: Ext.MessageBox.OK,
	                                icon: Ext.MessageBox.ERROR,
	                                toFrontOnShow:true,
	                                animateTarget:"editScheduleButId"
	                            }); 
	                        }
	                    },
	                    failure: function(response, opts) {
	                        Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
	                    }
	                }); 
	            }else{
	            	//加载策略数据	                            
                    var schduleStore =  win.down("#policyGrid").getStore();
                    var schduleStoreProxy = schduleStore.getProxy();
                    schduleStoreProxy.extraParams.ampInstId = ampInstId;
                    schduleStoreProxy.extraParams.agentId = agentId;
                    schduleStoreProxy.extraParams.flag = "edit";
                    schduleStoreProxy.extraParams.nodeKey = nodeKey;
                    schduleStore.load();
	            }
	        });
		}
    }
});