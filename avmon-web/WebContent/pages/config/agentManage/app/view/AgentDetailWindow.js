Ext.define('MyApp.view.AgentDetailWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.agentDeatilWindow',

    height: 450,
    id: 'agentDeatilWindow',
    width: 850,
    layout: {
        type: 'absolute'
    },
    title: avmon.config.agentDetail,
    modal: true,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    x: 0,
                    y: 0,
                    height: 120,
                    width: 840,
                    layout: {
                        type: 'absolute'
                    },
                    frame:true,
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 20,
                            id: 'agentIdLable1',
                            itemId: 'agentIdLable1',
                            value: 'Display Field',
                            width: 350,
                            labelWidth: 80,
                            fieldLabel: 'Agent ID'
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 60,
                            id: 'angentIpLable1',
                            itemId: 'angentIpLable1',
                            value: 'Display Field',
                            width: 250,
                            labelWidth: 80,
                            fieldLabel: 'IP'
                        },
                        {
                            xtype: 'displayfield',
                            x: 10,
                            y: 70,
                            hidden:true,
                            id: 'agentStatusLable1',
                            itemId: 'agentStatusLable1',
                            value: 'Display Field',
                            width: 250,
                            labelWidth: 80,
                            fieldLabel: avmon.config.status
                        }
//                        ,
//                        {
//                            xtype: 'button',
//                            x: 220,
//                            y: 70,
//                            id: 'agentDStart1',
//                            itemId: 'agentDStart1',
//                            icon: 'image/start.gif',
//                            text: avmon.config.startGathering+'jklmokaishi',
//                            listeners: {
//                                click: {
//                                    fn: me.onAgentDStart1Click,
//                                    scope: me
//                                }
//                            }
//                        }
//                        ,
//                        {
//                            xtype: 'button',
//                            x: 320,
//                            y: 70,
//                            id: 'agentDPause1',
//                            itemId: 'agentDPause1',
//                            icon: 'image/pause.gif',
//                            text: avmon.config.stopGathering,
//                            listeners: {
//                                click: {
//                                    fn: me.onAgentDPause1Click,
//                                    scope: me
//                                }
//                            }
//                        }
                    ]
                },
                {
                    xtype: 'panel',
                    getJsonSource: function(msg, animateTargetButId) {
                        var grid = Ext.getCmp("ampListGridId");
                        var selectCount = grid.getSelectionModel().getCount();
                        var agentId = Ext.getCmp("agentIdLable1").getValue();
                        var jsonSource = "";
                        if(selectCount == 0){
                            Ext.MessageBox.show({
                                title:avmon.config.wrong,
                                msg:msg,
                                buttons: Ext.MessageBox.OK,
                                icon: Ext.MessageBox.ERROR,
                                animateTarget:animateTargetButId
                            });

                        }else{
                            //获取选择的AMP实例信息
                            var dataSelection = grid.getSelectionModel().getSelection();
                            jsonSource = "[";    
                            for(var i=0;i<dataSelection.length;i++){
                                if(i == dataSelection.length-1){
                                    jsonSource += Ext.JSON.encode(dataSelection[i].data);  
                                }else{
                                    jsonSource += Ext.JSON.encode(dataSelection[i].data)+","; 
                                }

                            }
                            jsonSource+= "]";
                        }
                        return jsonSource;
                    },
                    x: 0,
                    y: 120,
                    height: 330,
                    id: 'ampGridPanelId',
                    itemId: 'ampGridPanelId',
                    layout: {
                        type: 'fit'
                    },
                    title: avmon.config.ampList,
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                            	{
                                    xtype: 'button',
                                    itemId: 'deployDefaultAmp',
                                    id:'deployDefaultAmp',
                                    text: avmon.config.deployDefaultAMP,
                                    iconCls: 'icon-add',
                                    listeners: {
                                        click: {
                                            fn: me.deployDefaultAmp,
                                            scope: me
                                        }
                                    }
                                },'-',
                                {
                                    xtype: 'button',
                                    id: 'addAmp1',
                                    text: avmon.config.addAMP,
                                    iconCls: 'icon-add',
                                    listeners: {
                                        click: {
                                            fn: me.onAddAmp1Click,
                                            scope: me
                                        }
                                    }
                                },'-',
                                {
                                    xtype: 'button',
                                    id: 'ampUinstalBut',
                                    text: avmon.config.ampUninstall,
                                    iconCls: 'icon-delete',
                                    listeners: {
                                        click: {
                                            fn: me.onAmpUinstalButClick,
                                            scope: me
                                        }
                                    }
                                },'-',
                                {
                                    xtype: 'button',
                                    id: 'issuedAmpScriptButId',
                                    itemId:'issuedAmpScriptButId',
                                    text: avmon.config.ampScriptIssued,
                                    iconCls: 'icon-ok',
                                    listeners: {
                                        click: {
                                            fn: me.onIssuedAmpScriptButIdClick,
                                            scope: me
                                        }
                                    }
                                },'-',
                                {
                                    xtype: 'button',
                                    id: 'issuedAmpScheduleButId',
                                    itemId:'issuedAmpScheduleButId',
                                    text: avmon.config.ampSchedulingIssued,
                                    iconCls: 'icon-ok',
                                    listeners: {
                                        click: {
                                            fn: me.onIssuedAmpScheduleButIdClick,
                                            scope: me
                                        }
                                    }
                                }
//                                ,'-',
//                                {
//                                    xtype: 'button',
//                                    id: 'startAmpBatchButId',
//                                    text: '启动AMP',
//                                    iconCls: 'icon-start',
//                                    listeners: {
//                                        click: {
//                                            fn: me.onStartAmpBatchButIdClick,
//                                            scope: me
//                                        }
//                                    }
//                                },'-',
//                                {
//                                    xtype: 'button',
//                                    id: 'pauseAmpBatchButId',
//                                    text: '停止AMP采集',
//                                    iconCls: 'icon-pause',
//                                    listeners: {
//                                        click: {
//                                            fn: me.onPauseAmpBatchButIdClick,
//                                            scope: me
//                                        }
//                                    }
//                                }
                            ]
                        }
                    ],
                    items: [
                        {
                            xtype: 'gridpanel',
                            id: 'ampListGridId',
                            itemId: 'ampListGridId',
                            forceFit: true,
                            store: 'AgentAmpListStore',
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'ampInstId',
                                    text: avmon.config.ampInstanceID
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'caption',
                                    text: avmon.config.ampName
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'ampInstId',
                                    text: avmon.config.installDirectory
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'ampVersion',
                                    text: avmon.config.currentVersion
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'schedule',
                                    text: avmon.config.schedulingConfigString
                                },
                                {
                                    xtype: 'gridcolumn',
                                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                        //0: 未安装
                                        //1：正在运行
                                        //2：停止运行
                                        //9：未知

                                        if( value== 0){
                                            return avmon.config.notInstall;   
                                        }else if(value == 1){
                                            return avmon.config.running;
                                        }else if(value == 2){
                                            return avmon.config.stopRun;
                                        }else{
                                            return avmon.config.unkonwn;
                                        }
                                    },
                                    dataIndex: 'status',
                                    text: avmon.config.status
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'ampType',
                                    text: avmon.config.ampType
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width:	132,
                                    dataIndex: 'lastAmpUpdateTime',
                                    text: avmon.config.scriptIssuedTime
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width:	132,
                                    dataIndex: 'lastScheduleUpdateTime',
                                    text: avmon.config.schedulIssuedTime
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width:	132,
                                    dataIndex: 'lastConfigUpdateTime',
                                    text: avmon.config.configIssuedTime
                                },
                                {
                                    xtype: 'actioncolumn',
                                    width:40,
                                    items: [
                                        {
                                            handler: me.onEditAmp,
                                            icon: 'image/settings.gif',
                                            iconCls: 'hand-msg'
                                        }
                                    ]
                                },
                                //添加amp最后更新时间
                                {
                                    xtype: 'gridcolumn',
                                    hidden: true,
                                    text: 'id'
                                }
                            ],
                            viewConfig: {

                            },
                            listeners:{
                		    	itemdblclick:function(view, record, item, index, e, eOpts){
                		    		me.onEditAmp(view, index, 0, item, e, record, null);
                		    	}
                            },
                            dockedItems: [
                                {
                                    xtype: 'pagingtoolbar',
                                    dock: 'bottom',
                                    width: 360,
                                    afterPageText: avmon.config.afterPageText,
                                    beforePageText: avmon.config.beforePageText,
                                    displayInfo: true,
                                    displayMsg: avmon.config.displayMsg,
                                    emptyMsg: avmon.config.emptyMsg,
                                    store: 'AgentAmpListStore'
                                }
                            ],
                            selModel: Ext.create('Ext.selection.CheckboxModel', {
                            })
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onAgentDStart1Click: function(button, e, options) {
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.startingWait,
            progressText: avmon.config.starting,
            width:300,
            wait:true
        });
        Ext.Ajax.request({
            url: 'startAgent',
            params: {
                status: 0,
                agentId:Ext.getCmp('agentIdLable1').getValue()
            },
            success: function(response, opts){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
				if(returnJson.success==true||returnJson.success=="true"){
	                //重新加载列表数据
	                var gridProxy = Ext.getCmp('agentGrid').getStore().getProxy();
	                //设置参数
	                // modify by mark start 2013-8-4
	                gridProxy.extraParams.agentId = Ext.getCmp('agentId').getValue();
	                gridProxy.extraParams.ip = Ext.getCmp('ip').getValue();
	                gridProxy.extraParams.os = Ext.alarm.os;
	                // modify by mark start 2013-8-4
	                //重新加载数据
	                Ext.getCmp('agentGrid').getStore().reload();
	                //修改按钮状态
	                Ext.getCmp('agentDStart1').setDisabled(true);
	                Ext.getCmp('agentDPause1').setDisabled(false);
	                //修改文本状态
	                // delete by mark start 2013-9-4
	                //Ext.getCmp('agentStatusLable1').setValue('正常');
	                // delete by mark end 2013-9-4
	                top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
				}else{
					top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
				}
            },
            failure: function(response, opts) {
                processBarc.close();
                top.Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }
        });
    },
    onAgentDPause1Click: function(button, e, options) {
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.stoppingWait,
            progressText: avmon.config.stopping,
            width:300,
            wait:true
        });
        Ext.Ajax.request({
            url: 'stopAgent',
            params: {
                status: 1,
                agentId:Ext.getCmp('agentIdLable1').getValue()
            },
            success: function(response){
                processBar.close();
				var returnJson = Ext.JSON.decode(response.responseText);
				if(returnJson.success=="true"||returnJson.success==true){
	                //重新加载列表数据
	                var gridProxy = Ext.getCmp('agentGrid').getStore().getProxy();
	                // modify by mark start 2013-8-4
	                // 设置参数
	                gridProxy.extraParams.agentId = Ext.getCmp('agentId').getValue();
	                gridProxy.extraParams.ip = Ext.getCmp('ip').getValue();
	                gridProxy.extraParams.os = Ext.alarm.os;
	                // modify by mark start 2013-8-4
	                //重新加载数据
	                Ext.getCmp('agentGrid').getStore().reload();
	
	                //修改按钮状态
	                Ext.getCmp('agentDStart1').setDisabled(false);
	                Ext.getCmp('agentDPause1').setDisabled(true);
	                //修改文本状态
				}
                top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
            },
            failure: function(response, opts) {
                processBar.close();
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }
        });
    },
    onAddAmp1Click: function(button, e, options) {
        var ampAddWindow = null;
        if(!ampAddWindow){
            ampAddWindow = Ext.create('widget.ampAddWindow',{
                title:avmon.config.addAMP
            });
            ampAddWindow.needReload=true;
        }
        ampAddWindow.center();
        ampAddWindow.show();
    },
    onAmpUinstalButClick: function(button, e, options) {
        var agentId = Ext.getCmp("agentIdLable1").getValue();
        var jsonSource = Ext.getCmp("ampGridPanelId").getJsonSource(avmon.config.selectNeedUninstallAMP,"ampUinstalBut");
        if(jsonSource == "")
        return;

        //开始卸载
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.uninstallAMPWait,
            progressText: avmon.config.uninstalling,
            width:300,
            wait:true
        });

        //卸载成功
        Ext.Ajax.request({
            url: 'removeAmp',
            method :"POST",
            params: {
                agentAmpInfo:jsonSource,
                agentId:agentId
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                if(returnJson.success==true||returnJson.success == 'true'){
                    //重新加载数据源
                    var gridProxy = Ext.getCmp("ampListGridId").getStore().getProxy();
                    gridProxy.extraParams.agentId = agentId;
                    Ext.getCmp("ampListGridId").getStore().load();
                }
				top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.errorMsg);
            },
            failure: function(response, opts) {
            	processBar.close();
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }
        });    
    },

    onIssuedAmpScriptButIdClick: function(button, e, options) {
        var agentId = Ext.getCmp("agentIdLable1").getValue();
        var jsonSource = Ext.getCmp("ampGridPanelId").getJsonSource(avmon.config.selectNeedIssuedScriptAMP,"issuedAmpScriptButId");
        if(jsonSource == "")
        return;

        var processBar = Ext.MessageBox.show({
            msg: avmon.config.beIssuedByScriptPleaseWait,
            progressText: avmon.config.issuing,
            width:300,
            wait:true
        });
        //下发脚本
        Ext.Ajax.request({
            url: 'pushAgentAmpScript',
            method :"POST",
            params: {
                agentAmpInfo:jsonSource,
                agentId:agentId
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
                if(returnJson.success){
                    //重新加载数据源
                    var gridProxy = Ext.getCmp("ampListGridId").getStore().getProxy();

                    gridProxy.extraParams.agentId = agentId;
                    Ext.getCmp("ampListGridId").getStore().load();
                }
            }
        });
    },
    onIssuedAmpScheduleButIdClick: function(button, e, options) {
        var agentId = Ext.getCmp("agentIdLable1").getValue();

        var jsonSource = Ext.getCmp("ampGridPanelId").getJsonSource(avmon.config.selectNeedSchedulIssuedAMP,"issuedAmpScheduleButId");
        if(jsonSource == "")
        return;
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.issuingSchedulWait,
            progressText: avmon.config.issuing,
            width:300,
            wait:true
        });
        //下发调度
        Ext.Ajax.request({
            url: 'pushAgentAmpSchedule',
            method :"POST",
            params: {
                agentAmpInfo:jsonSource,
                agentId:agentId
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
                if(returnJson.success){
					//重新加载数据源
                    var gridProxy = Ext.getCmp("ampListGridId").getStore().getProxy();
                    gridProxy.extraParams.agentId = agentId;
                    Ext.getCmp("ampListGridId").getStore().load();
                }
            }
        });
    },
    onStartAmpBatchButIdClick: function(button, e, options) {
        var agentId = Ext.getCmp("agentIdLable1").getValue();
        var jsonSource = Ext.getCmp("ampGridPanelId").getJsonSource(avmon.config.selectNeedStartAMP,"startAmpBatchButId");
        if(jsonSource == "")
        return;
        var processBar = Ext.MessageBox.show({
            msg: avmon.config.startingWait,
            progressText: avmon.config.starting,
            width:300,
            wait:true
        });
        //AMP启动
        Ext.Ajax.request({
            url: 'startNormalAmp',
            method :"POST",
            params: {
                agentAmpInfo:jsonSource,
                agentId:agentId
            },
            success: function(response){
                processBar.close();
                var returnJson = Ext.JSON.decode(response.responseText);
                if(returnJson.success){
					//重新加载数据源
                    var gridProxy = Ext.getCmp("ampListGridId").getStore().getProxy();

                    gridProxy.extraParams.agentId = agentId;
                    Ext.getCmp("ampListGridId").getStore().load();
                }
				top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.errorMsg);
            },
            failure: function(response, opts) {
                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
            }

        });
    },
//    onPauseAmpBatchButIdClick: function(button, e, options) {
//        var agentId = Ext.getCmp("agentIdLable1").getValue();
//
//
//        var jsonSource = Ext.getCmp("ampGridPanelId").getJsonSource("请选择需要停止采集的AMP！","pauseAmpBatchButId");
//        if(jsonSource == "")
//        return;
//
//        var processBar = Ext.MessageBox.show({
//            msg: avmon.config.stoppingWait,
//            progressText: avmon.config.stopping,
//            width:300,
//            wait:true
//        });
//
//
//        //AMP停止采集
//        Ext.Ajax.request({
//            url: 'pauseNormalAmp',
//            method :"POST",
//            params: {
//                agentAmpInfo:jsonSource,
//                agentId:agentId
//            },
//            success: function(response){
//
//                processBar.close();
//
//                var returnJson = Ext.JSON.decode(response.responseText);
//                if(returnJson.success){
//                    //重新加载数据源
//                    var gridProxy = Ext.getCmp("ampListGridId").getStore().getProxy();
//
//                    gridProxy.extraParams.agentId = agentId;
//                    Ext.getCmp("ampListGridId").getStore().load();
//                }
//				top.Ext.MessageBox.alert(avmon.common.reminder, returnJson.errorMsg);
//
//            },
//            failure: function(response, opts) {
//                Ext.Msg.alert(avmon.common.failure,avmon.config.operationFail);
//            }
//
//        });
//    },
    onEditAmp: function(view, rowIndex, colIndex, item, e, record, row) {
        //根据Amp的类型弹出不同的配置 ---普通AMP采集包、VM采集包、ILO采集包
        var ampType = Ext.util.Format.lowercase(record.data.ampType);
        var ampStatus = record.data.status;
        var ampStatusText = "";
        var status = record.data.status;
        /**
        0: 未安装
        1：正在运行
        2：停止运行
        9：未知
        */
        if(status==0){
            status = avmon.config.notInstall;
        }else if(status==1){
            status = avmon.config.running;
        }else if(status==2){
            status = avmon.config.stopRun;
        }else{
            status = avmon.config.unkonwn;
        }
        if( ampStatus== 0){
            ampStatusText = avmon.config.notInstall;   
        }else if(ampStatus == 1){
            ampStatusText = avmon.config.startGatheringStart;
        }else if(ampStatus == 2){
            ampStatusText = avmon.config.stopGatheringStop;
        }else{
            ampStatusText = avmon.config.unkonwn;
        }
        if(ampType=="vm"){
            var win = Ext.create('MyApp.view.VMAmpSetWindow');
            win.down("panel").down("#ampInstId").setValue(record.data.ampInstId);
            win.down("panel").down("#ampInstName").setValue(record.data.caption);
			win.down("panel").down("#ampId").setValue(record.data.ampId);
            win.down("panel").down("#ampInstStatus").setValue(status);
            win.down("tabpanel").down("#vmTab").down("treepanel").down("toolbar").down("#schedule").setValue(record.data.schedule);
            //加载基本配置信息
            var attrGrid = win.down("tabpanel").down("panel").down("grid");
            var attrStore = attrGrid.store;
            var attrProxy = attrStore.proxy;
            attrProxy.extraParams.agentId = record.data.agentId;
            attrProxy.extraParams.ampInstId = record.data.ampInstId;
            attrStore.load();
            win.show();
        }else if(ampType=="ilo"){
            var win = Ext.create('MyApp.view.ILOAmpSetWindow');
            win.down("panel").down("#ampInstId").setValue(record.data.ampInstId);
            win.down("panel").down("#ampInstName").setValue(record.data.caption);
			win.down("panel").down("#ampId").setValue(record.data.ampId);
            win.down("panel").down("#ampInstStatus").setValue(status);
            //加载基本配置信息
            var attrGrid = win.down("tabpanel").down("panel").down("grid");
            var attrStore = attrGrid.store;
            var attrProxy = attrStore.proxy;
            attrProxy.extraParams.agentId = record.data.agentId;
            attrProxy.extraParams.ampInstId = record.data.ampInstId;
            attrStore.load();
            win.show();
        }else{
        	//如果是windows amp的时候，不显示配置窗口
        	if(record.data.ampInstId=="os-windows"){
        		return;
        	}else{
        		var win = Ext.create('MyApp.view.NormalAmpSetWindow',{
                    title:avmon.config.ampConfiguration+record.data.ampInstId
                });
                //加载基本配置信息
                var attrGrid = win.down("tabpanel").down("panel").down("grid");
                var attrStore = attrGrid.store;
                var attrProxy = attrStore.proxy;
                attrProxy.extraParams.agentId = record.data.agentId;
                attrProxy.extraParams.ampInstId = record.data.ampInstId;
                attrStore.load();
                win.down("panel").down("#hiddenAmpId").setValue(record.data.ampId);
                win.down("panel").down("#normalAmpInstId").setValue(record.data.ampInstId);
                win.down("panel").down("#normalAmpName").setValue(record.data.caption);
                win.down("panel").down("#normalAmpStatus").setValue(ampStatusText);
                win.center();
                win.show();
        	}
        }
    },
    deployDefaultAmp: function(button, e, options) {
    	alert(avmon.config.deployDefaultAMP);
    }

});