Ext.define(
        'app.view.MyViewport',
        {
          extend : 'Ext.container.Viewport',

          requires : [ 'Ext.grid.Panel', 
                       'Ext.grid.View',
                       'Ext.grid.column.Column',
                       'Ext.grid.plugin.CellEditing'
                       ],

           layout : {
             type : 'fit'
           },
          initComponent : function() {
            var me = this;

            Ext.applyIf(
                    me,
                    {
                      items : [ {
                        xtype : 'gridpanel',
                        store : 'HostPingStore',
                        id : 'hostPingGrid',
                        itemid : 'HostPingGrid',
                        plugins : [ Ext.create('Ext.grid.plugin.CellEditing', {
                          clicksToEdit : 2,
                        }) ],

                        columns : [
                            {
                              xtype : 'gridcolumn',
                              width : 240,
                              dataIndex : 'agentId',
                              text : 'Mo_id',
                              hidden : true
                            },
                            {
                              xtype : 'gridcolumn',
                              width : 110,
                              dataIndex : 'ip',
                              text : avmon.deploy.ipaddress
                            },
                            {
                              xtype : 'gridcolumn',
                              width : 60,
                              renderer : function(value, metaData, record,
                                  rowIndex, colIndex, store, view) {
                                //                                        
                                var ostype = record.data.os;
                                if (ostype == 'LINUX')
                                  return "<img src='../../resources/images/motype/LINUX.png'/>";
                                if (ostype == 'WINDOWS')
                                  return "<img src='../../resources/images/motype/WINDOWS.png'/>";
                                if (ostype == 'HP-UX')
                                  return "<img src='../../resources/images/motype/Icon_HPUX.png'/>";
                                if (ostype == 'AIX')
                                  return "<img src='../../resources/images/motype/Icon_AIX.png'/>";
                                if (ostype == 'SunOS')
                                  return "<img src='../../resources/images/motype/Icon_SOLARIS.png'/>";
                              },
                              dataIndex : 'os',
                              text : avmon.deploy.systemId
                            },
                            {
                              xtype : 'gridcolumn',
                              width : 140,
                              dataIndex : 'pingTime',
                              text : avmon.deploy.collectTime
                            },
                            {
                              xtype : 'gridcolumn',
                              width : 60,
                              renderer : function(value, metaData, record,
                                  rowIndex, colIndex, store, view) {
                                var pingStatus = record.data.pingStatus;
                                if (pingStatus == 'ALIVE') {
                                  return "<img src='../../resources/images/alarm/level0.png'/>";
                                } else if (pingStatus == 'DOWN') {
                                  return "<img src='../../resources/images/alarm/level5.png'/>";
                                } else {
                                  return "<img src='../../resources/images/motype/help.gif'/>";
                                }
                              },
                              dataIndex : 'pingStatus',
                              text : avmon.deploy.pingStatus
                            }, {
                              xtype : 'gridcolumn',
                              width : 140,
                              dataIndex : 'realip',
                              text : avmon.deploy.physicsIP,
                              editor : {
                                xtype : 'textfield',
                                completeOnEnter:true,
                                cancelOnEsc:true,
                                ignoreNoChange:true
                              }
                            } ],
                            listeners:{
                              edit: function (editor, e) {
                                var newVal = e.value;
                                var moId = e.record.data.agentId;
                                Ext.Ajax.request({
                                  url : 'batchSavePingHost',
                                  params : {
                                    moid : moId,
                                    ip : newVal
                                  },
                                  async:false,
                                  success : function(response, opts) {
                                   var result = Ext.JSON.decode(response.responseText).result;
                                   if(result==true||result =='true'){
                                     e.record.commit();
                                     Ext.MessageBox.alert(avmon.common.reminder, avmon.dashboard.realipSetSuccess);
                                   }else{
                                     Ext.MessageBox.alert(avmon.common.reminder, avmon.dashboard.realipSetFailed);
                                     editor.startEdit(e.record, 5 );
                                   }
                                  },
                                  failure : function(response, opts) {
                                    Ext.MessageBox.alert(avmon.common.reminder, avmon.dashboard.realipSetFailed);
                                  }
                                });
                              }
                          },
                        dockedItems : [ {
                          xtype : 'toolbar',
                          dock : 'top',
                          items : [ {
                            xtype : 'textfield',
                            fieldLabel : avmon.deploy.ipaddress,
                            id : 'ipInput',
                            width : 200,
                            itemId : 'ipInput',
                            labelWidth : 60
                          }, {
                            fieldLabel : avmon.equipmentCenter.operatingSystem,
                            name : 'os',
                            id:'os',
                            itemId : 'os',
                            labelWidth : 60,
                            allowBlank : true,
                            xtype : 'combobox',
                            store : Ext.create('Ext.data.Store', {
                              fields : [ 'value', 'display' ],
                              data : [ {
                                'value' : 'WINDOWS',
                                'display' : 'Windows'
                              }, {
                                'value' : 'HP-UX',
                                'display' : 'HP-UX'
                              }, {
                                'value' : 'LINUX',
                                'display' : 'LINUX'
                              }, {
                                'value' : 'SunOS',
                                'display' : 'SOLARIS'
                              },{
                                'value' : 'AIX',
                                'display' : 'AIX'
                              }, {
                                'value' : 'ALL',
                                'display' : avmon.deploy.allOS
                              } ]
                            }),
                            queryMode : 'local',
                            editable : false,
                            valueField : 'value',
                            displayField : 'display'
                          }, {
                            fieldLabel : avmon.deploy.pingStatus,
                            name : 'status',
                            itemId : 'status',
                            id : 'status',
                            labelWidth : 60,
                            allowBlank : true,
                            xtype : 'combobox',
                            store : Ext.create('Ext.data.Store', {
                              fields : [ 'value', 'display' ],
                              data : [ {
                                'value' : 'ALIVE',
                                'display' : avmon.config.normal
                              }, {
                                'value' : 'DOWN',
                                'display' : avmon.deploy.unNormal
                              }, {
                                'value' : 'UNKNOWN',
                                'display' : avmon.deploy.unknown
                              }, {
                                'value' : 'ALL',
                                'display' : avmon.deploy.allStatus
                              } ]
                            }),
                            queryMode : 'local',
                            editable : false,
                            valueField : 'value',
                            displayField : 'display'
                          }, 
                          {
                            xtype : 'button',
                            text : avmon.deploy.retrieve,
                            listeners : {
                              click : {
                                fn : me.onPingButtonClick,
                                scope : me
                              }
                            }
                          }
                         ]
                        }, {
                          xtype : 'pagingtoolbar',
                          dock : 'bottom',
                          width : 360,
                          displayInfo : true,
                          store : 'HostPingStore'
                        } ]
                      } ]
                    });

            me.callParent(arguments);
          },
          onPingButtonClick : function(button, e, eOpts) {

            var pingGrid = Ext.getCmp('hostPingGrid');
            // 获取其代理
            var pingGridStoreProxy = pingGrid.getStore().getProxy();
            // 设置参数
            // alert(button.ownerCt.down("#ipInput").getValue());
            pingGridStoreProxy.extraParams.ip = button.ownerCt.down("#ipInput").getValue();
            pingGridStoreProxy.extraParams.os = button.ownerCt.down("#os").getValue();
            pingGridStoreProxy.extraParams.status = button.ownerCt.down("#status").getValue();

            // 重新加载数据
            pingGrid.getStore().currentPage = 1;
            pingGrid.getStore().load();
          }
        });

        /**
         * 定时刷新
         */
        setInterval(function() {
          var pingGrid = Ext.getCmp('hostPingGrid');
          // 获取其代理
          var pingGridStoreProxy = pingGrid.getStore().getProxy();
          // 重新加载数据
//          pingGrid.getStore().currentPage = 1;
          pingGrid.getStore().load();
        },60000);