Ext.define('MyApp.view.AgentViewport', {
    extend: 'Ext.container.Viewport',

    focusOnToFront: false,
    layout: {
        type: 'fit'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    layout: {
                        type: 'fit'
                    },
                    title: avmon.config.agentManagement,
                    items: [
                        {
                            xtype: 'gridpanel',
                            id: 'agentGrid',
                            itemId:'agentGrid',
                            header: false,
                            title: 'My Grid Panel',
                            forceFit: false,
                            hideHeaders: false,
                            store: 'AgentGridJsonStore',
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    maxWidth: 550,
                                    width: 250,
                                    dataIndex: 'agentId',
                                    text: 'Agent ID',
                                    hidden:true
                                },
                                {
                                  	 xtype: 'gridcolumn',
                                       dataIndex: 'hostName',
                                       width: 120,
                                       text: avmon.config.referenceHostName
                                },
                                {
                                    xtype: 'gridcolumn',
                                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                        if(value == null || value == "" || value == "null")
                                        return "";
                                        return value;
                                    },
                                    maxWidth: 250,
                                    width: 110,
                                    dataIndex: 'ip',
                                    text: 'IP'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width:60,
                                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
//                                        
                                        var ostype=record.data.os;
                                        if(ostype=='Linux')
                                        	return "<img src='../../../resources/images/motype/LINUX.png'/>";
                                        if(ostype=='Windows')
                                        	return "<img src='../../../resources/images/motype/WINDOWS.png'/>";
                                        if(ostype=='HP-UX')
                                        	return "<img src='../../../resources/images/motype/Icon_HPUX.png'/>";
                                        if(ostype=='AIX')
                                        	return "<img src='../../../resources/images/motype/Icon_AIX.png'/>";
                                        if(ostype=='SunOS')
                                        	return "<img src='../../../resources/images/motype/Icon_SOLARIS.png'/>";
                                    },
                                    dataIndex: 'os',
                                    text: avmon.config.systemId
                                },                            
                                {
                                	 xtype: 'gridcolumn',
                                     dataIndex: 'os',
                                     width: 70,
                                     text: avmon.config.system
                                },
                                {
                               	 xtype: 'gridcolumn',
                                    dataIndex: 'osVersion',
                                    width: 220,
                                    text: avmon.config.systemVersion
                               },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'agentVersion',
                                    width: 70,
                                    text: avmon.config.agentVersion
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'gateway',
                                    text: 'Gateway',
                                    hidden:true
                                },
                                {
                                    xtype: 'datecolumn',
                                    dataIndex: 'lastHeartbeatTime',
                                    width: 150,
                                    text: avmon.config.lastActivity,
                                    format: 'Y-m-d H:i:s'
                                },
                                {
                                    xtype: 'datecolumn',
                                    dataIndex: 'lastUpdateTime',
                                    width: 150,
                                    text: avmon.config.lastUpdateTime,
                                    format: 'Y-m-d H:i:s'
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                        if(value<10)
                                        	return "<img src='../../../resources/images/alarm/level0.png'/>Good";
                                        
                                        var lv="2";
                                        if(value>30)
                                        	lv=3;
                                        if(value>180)
                                        	lv=4;
                                        if(value>24*60)
                                        	lv=5;
                                        
                                        return "<img src='../../../resources/images/alarm/level"+lv+".png'/>超过"+value+"分钟未有心跳";  

                                    },
                                    dataIndex: 'agentStatus',
                                    text: avmon.config.status
                                },
                                {
                                  xtype: 'gridcolumn',
                                  width: 100,
                                  renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                      if(value<10)
                                        return "<img src='../../../resources/images/alarm/level0.png'/>Good";
                                      
                                      var lv="2";
                                      if(value>30)
                                        lv=3;
                                      if(value>180)
                                        lv=4;
                                      if(value>24*60)
                                        lv=5;
                                      
                                      return "<img src='../../../resources/images/alarm/level"+lv+".png'/>超过"+value+"分钟未送数据";  

                                  },
                                  dataIndex: 'updateStatus',
                                  text: avmon.config.updateStatus
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 80,
                                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                        if(value<1)
                                        return "<img src='../../../resources/images/alarm/level4.png'/>AMP="+value; 
                                        return "<img src='../../../resources/images/alarm/level0.png'/>AMP="+value;
                                               
                                    },
                                    dataIndex: 'ampCount',
                                    text: avmon.config.ampInstallSituation
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'moId',
                                    text: 'MOID',
                                    hidden:true
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'agentCollectFlag',
                                    text: avmon.config.agentAcquisitionStartupState,
                                    hidden:true
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 180,
                                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                        if(value == '1'||value == 1){
                                        	return "<img src='../../../resources/images/alarm/level0.png'/>Good";
                                        }else if(value == '0'||value == 0){
                                        	return "<img src='../../../resources/images/alarm/level"+5+".png'/>Down";
                                        }else{
                                        	return "<img src='../../../resources/images/alarm/level"+5+".png'/>Unknown";	
                                        }
                                    },
                                    dataIndex: 'status',
                                    text: avmon.config.agentStatus,
                                    hidden:true
                                },
                                {
                                    xtype: 'actioncolumn',
                                    items: [
                                            /*
                                        {
                                            handler: function(view, rowIndex, colIndex, item, e, record, row) {

                                                var status = record.data.agentStatus;
                                                if(status == 0 || status == "0"){
                                                    Ext.MessageBox.show({
                                                        title: '警告',
                                                        msg: 'Agent已经处于启动状态中！',
                                                        buttons: Ext.MessageBox.OK,
                                                        icon: Ext.MessageBox.WARNING
                                                    });
                                                }else{

                                                    var processBar = Ext.MessageBox.show({
                                                        msg: '正在启动请稍等t...',
                                                        progressText: '启动中...',
                                                        width:300,
                                                        wait:true
                                                    });

                                                    Ext.Ajax.request({
                                                        url: 'startAgent',
                                                        params: {
                                                            status: 0,
                                                            agentId:record.data.agentId
                                                        },
                                                        success: function(response, opts){
                                                            processBar.close();
                                                            var returnJson = Ext.JSON.decode(response.responseText);
															if(returnJson.success=="true"){
																//重新加载列表数据
	                                                            var gridProxy = view.getStore().getProxy();
	                                                            //设置参数
														        gridProxy.extraParams.agentId = view.up("panel").down("#agentId").getValue();
														        gridProxy.extraParams.ip = view.up("panel").down("#ip").getValue();
	                                                            
	                                                            //gridProxy.extraParams.searchKeyValue = Ext.getCmp('searchInput').getValue();
	                                                            //重新加载数据
	                                                            view.getStore().load();
															}
                                                            Ext.MessageBox.alert(avmon.common.reminder, returnJson.msg);
                                                        },
                                                        failure: function(response, opts) {
                                                        	 var returnJson = Ext.JSON.decode(response.responseText);
                											 Ext.MessageBox.alert(avmon.config.wrong, returnJson.msg);
                                                        }

                                                    });

                                                }



                                            },
                                            icon: 'image/start.gif',
                                            iconCls: 'hand-msg',
                                            tooltip: '启动'
                                        },
                                        {
                                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                                var status = record.data.agentStatus;
                                                if(status == 1 || status == "1"){

                                                    Ext.MessageBox.show({
                                                        title: '警告',
                                                        msg: 'Agent已经处于停止状态中！',
                                                        buttons: Ext.MessageBox.OK,
                                                        icon: Ext.MessageBox.WARNING
                                                    });
                                                }else{

                                                    var processBar = Ext.MessageBox.show({
                                                        msg: '正在停止请稍等...',
                                                        progressText: '停止中...',
                                                        width:300,
                                                        wait:true
                                                    });

                                                    Ext.Ajax.request({
                                                        url: 'stopAgent',
                                                        params: {
                                                            status: 1,
                                                            agentId:record.data.agentId
                                                        },
                                                        success: function(response){
                                                            processBar.close();
                                                            var returnJson = Ext.JSON.decode(response.responseText);
															if(returnJson.success=="true"){
																//重新加载列表数据
	                                                            var gridProxy = view.getStore().getProxy();
	                                                            //设置参数
	                                                            gridProxy.extraParams.agentId = view.up("panel").down("#agentId").getValue();
														        gridProxy.extraParams.ip = view.up("panel").down("#ip").getValue();
	                                                            //gridProxy.extraParams.searchKeyValue = Ext.getCmp('searchInput').getValue();
	                                                            //重新加载数据
	                                                            view.getStore().load();
															}
                                                            Ext.MessageBox.alert(avmon.config.wrong, returnJson.msg);
                                                        },
                                                        failure: function(response, opts) {
                                                            Ext.Msg.alert('失败','操作失败！');
                                                        }

                                                    });


                                                }
                                            },
                                            icon: 'image/pause.gif',
                                            iconCls: 'hand-msg',
                                            tooltip: avmon.config.stop
                                        },
                                        //*/
                                        {
                                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                            	me.onEditAgent(view,record);
                                            },
                                            icon: 'image/settings.gif',
                                            iconCls: 'hand-msg',
                                            tooltip: avmon.config.configuration
                                        }
                                        ,
                                        {
                                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                            	me.onRemoveAgent(view,record);
                                            },
                                            icon: 'image/delete.gif',
                                            iconCls: 'hand-msg',
                                            tooltip: avmon.common.deleted
                                        }
                                        ,
                                        {
                                            handler: function(view, rowIndex, colIndex, item, e, record, row) {
                                            	me.onUpgradeAgent(view,record);
                                            },
                                            icon: 'image/upgrade.gif',
                                            iconCls: 'hand-msg',
                                            tooltip: avmon.config.updateAgent
                                        }
                                    ]
                                }
                            ],
                            viewConfig: {

                            },
                            listeners:{
                		    	itemdblclick:function(view, record, item, index, e, eOpts){
//                		    		Ext.alarm.os = record.data.os;
                		    		Ext.alarm.itemOs = record.data.os;                		    		
                		    		me.onEditAgent(view,record);
                		    	}
                            },
                            dockedItems: [
                                {
                                    xtype: 'pagingtoolbar',
                                    id : 'bbarId',
                                    dock: 'bottom',
                                    width: 360,
                                    afterPageText: avmon.config.afterPageText,
                                    beforePageText: avmon.config.beforePageText,
                                    displayInfo: true,
                                    displayMsg: avmon.config.displayMsg,
                                    emptyMsg: avmon.config.emptyMsg,
                                    store: 'AgentGridJsonStore'
                                },
                                {
                                    xtype: 'toolbar',
                                    dock: 'top',
                                    items: [
                                        {
                                            xtype: 'textfield',
                                            id: 'agentId',
                                            itemId: 'agentId',
                                            width: 233,
                                            labelWidth:70,
                                            fieldLabel: 'Agent ID'
                                        },{
                                            xtype: 'textfield',
                                            id: 'ip',
                                            itemId: 'ip',
                                            width: 200,
                                            labelWidth:30,
                                            fieldLabel: 'IP'
                                        },
                                        {
                                            xtype: 'button',
                                            id: 'searchBut',
                                            icon: 'image/search.gif',
                                            text: avmon.config.retrieve,
                                            listeners: {
                                                click: {
                                                    fn: me.onSearchButClick,
                                                    scope: me
                                                }
                                            }
                                        }
                                    ]
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onSearchButClick: function(button, e, options) {
        //获取agentGrid实例    
        var agentGrid = button.ownerCt.ownerCt;//Ext.getCmp('agentGrid');
        //获取其代理
        var agentGridStoreProxy = agentGrid.getStore().getProxy();
        
        //设置参数
        agentGridStoreProxy.extraParams.agentId = button.ownerCt.down("#agentId").getValue();//Ext.getCmp('searchInput').getValue();
        agentGridStoreProxy.extraParams.ip = button.ownerCt.down("#ip").getValue();
        agentGridStoreProxy.extraParams.os = Ext.alarm.os;
        //agentGridStoreProxy.extraParams.start = 0;
        
        //重新加载数据
        agentGrid.getStore().currentPage=1;
        agentGrid.getStore().load();
    },
    openAgentDetailWindow:function(){
    	
    },
    onRemoveAgent:function(view,record){
    	Ext.MessageBox.confirm(avmon.common.reminder, avmon.config.whetherToDelete,
				function(btn) {
					if (btn == "yes") {
						Ext.Ajax.request({
							url : 'removeAgent?agentId='
									+ record.data.agentId,
							success : function(
									response, opts) {
								var returnJson = Ext.JSON
										.decode(response.responseText);
								Ext.MessageBox
										.alert(
												avmon.common.reminder,
												returnJson.msg);
								view.getStore().load();
							},
							failure : function(
									response, opts) {

							}
						});
					}
				});
    },
    onUpgradeAgent:function(view,record){
    	Ext.MessageBox.confirm(avmon.common.reminder, avmon.config.whetherUpgrade,
				function(btn) {
					if (btn == "yes") {
						
                        var processBar = Ext.MessageBox.show({
                            msg: avmon.config.updatingAgentWait,
                            progressText: avmon.config.updatingAgent,
                            width:300,
                            wait:true
                        });
                        
						Ext.Ajax.request({
							url : 'upgradeAgent?agentId='
									+ record.data.agentId,
							success : function(response, opts) {
								processBar.close();
								var returnJson = Ext.JSON
										.decode(response.responseText);
								Ext.MessageBox
										.alert(
												avmon.common.reminder,
												returnJson.msg);
								view.getStore().load();
							},
							failure : function(response, opts) {
								Ext.MessageBox
								.alert(avmon.config.wrong,avmon.config.netErrorUpdateFail);
							}
						});
					}
				});
    },
    onEditAgent:function(view,record){
    	var moId = record.data.moId;
    	var win = null;
        var agentStatus = record.data.status;
        // add by mark start 
        var agentCollectFlag = record.data.agentCollectFlag;
        var os = record.data.os;
        // add by mark end
    	var titleString = record.data.agentId;
        var agentIp = record.data.ip;
        
    	if(moId==null){//添加监控对象
    		Ext.Ajax.request({
                url: 'addAgentMo',
                params: {
                    moId: titleString,
                    agentId:titleString
                },
                success: function(response, opts){
                	var returnJson = Ext.JSON.decode(response.responseText);
					if(returnJson.success){
						view.getStore().load();
                        if(!win){
                            win=Ext.create('widget.agentDeatilWindow',{
                                title:avmon.config.agentDetail + ' - '+titleString
                            });
                            win.needReload=true;

                            //设置详细面板显示值
                            win.down('#agentIdLable1').setValue(titleString);
                            win.down('#angentIpLable1').setValue(agentIp);
                            win.down('#agentStatusLable1').setValue(agentStatus == '1'?avmon.config.normal:avmon.config.stop);
                            
                            //如果状态是启动，则启动按钮不可用反之停用按钮不可用
                            // modify by mark start
                            /**
                             * agent存活的情况下才能采集agent信息
                             * 启动agent条件 1.agent存活 2.agent采集没有启动或者启动失败
                             */
//                            if(agentStatus == 1||agentStatus == ""){//agent活着的情况下，才能进行采集
//	                            if(agentCollectFlag == 0 || agentCollectFlag == ""){
////	                            	win.down('#agentDStart1').setDisabled(true);
////	                                win.down('#agentDPause1').setDisabled(false);
//	                            }else{
////	                            	win.down('#agentDStart1').setDisabled(false);
////	                                win.down('#agentDPause1').setDisabled(true);
//	                            }
//                            // modify by mark end
//                            }else{
//                                win.down('#agentDStart1').setDisabled(true);
//                                win.down('#agentDPause1').setDisabled(true);
//                            }
                        }
						var ampGridStore = win.down("#ampListGridId").getStore();
                        var gridProxy = ampGridStore.getProxy();

                        gridProxy.extraParams.agentId = titleString;
                        ampGridStore.load();

                        win.center();
                        win.show();
					}else{
						Ext.MessageBox.alert(avmon.config.wrong, avmon.config.cannotCreatObjectForAgent + '<br>' + avmon.common.errorMessage + '-'+returnJson.msg);
					}
                },
                failure: function(response, opts) {
					 Ext.MessageBox.alert(avmon.config.wrong, avmon.config.addMonitorObjectFail);
                }
            });
    	}else{
        	if(!win){
                win=Ext.create('widget.agentDeatilWindow',{
                    title:avmon.config.agentDetail + ' - '+titleString
                });
                win.needReload=true;
                //设置详细面板显示值
                win.down('#agentIdLable1').setValue(titleString);
                win.down('#angentIpLable1').setValue(agentIp);
                win.down('#agentStatusLable1').setValue(agentStatus == '1'?avmon.config.normal:avmon.config.stop);
                // 如果是windown amp的话，隐藏其他按钮 add by mark start
                if(os=='Windows'){
                  win.down('#deployDefaultAmp').hide();
                  win.down('#issuedAmpScriptButId').hide();
                  win.down('#issuedAmpScheduleButId').hide();
                }
                // 如果是windown amp的话，隐藏其他按钮 add by mark end
                //如果状态是启动，则启动按钮不可用反之停用按钮不可用
                // modify by mark start
//                if(agentStatus == 1 || agentStatus == ""){
//                	if(agentCollectFlag == 0 || agentCollectFlag == ""){
////	                	win.down('#agentDStart1').setDisabled(true);
////	                    win.down('#agentDPause1').setDisabled(false);
//                    }else{
////                    	win.down('#agentDStart1').setDisabled(false);
////	                    win.down('#agentDPause1').setDisabled(true);
//                    }
//                // modify by mark end
//                }else{
//                    win.down('#agentDStart1').setDisabled(true);
//                    win.down('#agentDPause1').setDisabled(true);
//                }
            }
			var ampGridStore = win.down("#ampListGridId").getStore();
            var gridProxy = ampGridStore.getProxy();
            gridProxy.extraParams.agentId = titleString;
            ampGridStore.load();
            win.center();
            win.show();
    	}
    }
});