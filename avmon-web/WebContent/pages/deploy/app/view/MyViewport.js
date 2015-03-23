/*
 * File: app/view/MyViewport.js
 *
 * This file was generated by Sencha Architect version 2.2.2.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.1.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.1.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext
    .define(
        'app.view.MyViewport',
        {
          extend : 'Ext.container.Viewport',

          requires : [ 'app.view.MenuTree', 'app.view.ConfigPanel', 'Ext.ProgressBar' ],

          itemId : 'main',
          layout : {
            type : 'border'
          },

          initComponent : function() {
            var me = this;

            Ext
                .applyIf(
                    me,
                    {
                      items : [{
                          xtype: 'panel',
                          region: 'north',
                          border: 0,
                          height: 70,
                          id: 'topPanel',
                          layout: {
                              type: 'fit'
                          },
                          bodyBorder: false,
                          items: [
                              {
                                  xtype: 'panel',
                                  border: 0,
                                  height: 70,
                                  id: 'mainMenuPanel',
                                  autoScroll: false,
                                  layout: {
                                      type: 'absolute'
                                  },
                                  bodyBorder: false,
                                  html:"<div id=\"top\" class=\"top\"><div class=\"fixed\">" +
	                          		"<a href=\"../main\"><div class=\"Avmon_back_gif\" style=\"width:48px;height:43px\">" +
	                          		"<img id=\"Avmon_back_img\" src=\"../resources/images/back.png\" " +
	                          		" onmouseover=\"mouseover('..');\" onmouseout=\"mouseout('..');\" ></div></a>" +
	                          		"<div class=\"MainTitle f28px LineH12\" style=\"font-family: \'Microsoft yahei\',\'微软雅黑\',\'Lato\', Helvetica,Tahoma,Verdana,Arial,sans-serif \">部署管理</div>"
	                              }
	                          ]
	                      },
                          {
                            xtype : 'menuTree',
                            id : 'agentTree',
                            region : 'west',
                            collapsible: true,
                            width : 160,
                            layout : {
                              type : 'border'
                            }
                          },
                          {
                            xtype : 'container',
                            region : 'center',
                            id : 'contentPanel',
                            layout : {
                              type : 'card'
                            },
                            items : [
                                {
                                  xtype : 'panel',
                                  id : 'panel1',
                                  title : '',
                                  listeners : {
                                    afterlayout : {
                                      fn : me.onPanel1AfterLayout,
                                      scope : me
                                    }
                                  }
                                },
                                {
                                  xtype : 'configPanel'
                                },
                                {
                                  xtype : 'panel',
                                  id : 'agentDownloadPanel',
                                  layout : {
                                    type : 'fit'
                                  },
                                  title : 'Agent Download',
                                  items : [ {
                                    xtype : 'gridpanel',
                                    id : 'agentDownloadGrid',
                                    title : '',
                                    store : 'AgentDownloads',
                                    columns : [
                                        {
                                          xtype : 'actioncolumn',
                                          getClass : function(v, metadata, r,
                                              rowIndex, colIndex, store) {
                                            return r.get("icon");
                                          },
                                          width : 30,
                                          dataIndex : 'icon'
                                        },
                                        {
                                          xtype : 'gridcolumn',
                                          dataIndex : 'os',
                                          text : 'Os'
                                        },
                                        {
                                          xtype : 'gridcolumn',
                                          width : 400,
                                          dataIndex : 'description',
                                          text : 'Description'
                                        },
                                        {
                                          xtype : 'gridcolumn',
                                          renderer : function(value, metaData,
                                              record, rowIndex, colIndex,
                                              store, view) {
                                            return "<a href='"
                                                + record.get("url") + "'>"
                                                + avmon.deploy.download
                                                + "</a>";

                                          },
                                          width : 50,
                                          dataIndex : 'url'
                                        } ]
                                  } ]
                                },
                                {
                                  xtype : 'panel',
                                  id : 'agentUpgradePanel',
                                  layout : {
                                    type : 'border'
                                  },
                                  title : '',
                                  dockedItems : [ {
                                    xtype : 'gridpanel',
                                    dock : 'left',
                                    collapsible: true,
                                    collapseDirection : 'left',
                                    title :avmon.deploy.ampUpgrade,
                                    id : 'upgradeMonitorGrid',
                                    width : 230,
                                    store : 'MonitorTypes',
                                    columns : [ {
                                      xtype : 'gridcolumn',
                                      dataIndex : 'name',
                                      text : 'Name'
                                    }, {
                                      xtype : 'gridcolumn',
                                      width : 100,
                                      dataIndex : 'caption',
                                      text : 'Caption'
                                    }, {
                                      xtype : 'gridcolumn',
                                      dataIndex : 'type',
                                      text : 'Type'
                                    }, {
                                      xtype : 'gridcolumn',
                                      width : 40,
                                      dataIndex : 'version',
                                      text : 'Version'
                                    } ],
                                    features : [ {
                                      ftype : 'grouping',
                                      hideGroupedHeader : true
                                    } ],
                                    listeners : {
                                      select : {
                                        fn : me.onUpgradeMonitorGridSelect,
                                        scope : me
                                      }
                                    }
                                  } ],
                                  items : [
                                      {
                                        xtype : 'gridpanel',
                                        id : 'upgradeMonitorInstanceGrid',
                                        title : '',
                                        store : 'UpgradeMonitors',
                                        height : '100%',
                                        region : 'north',
                                        autoScroll : true,
                                        columns : [ {
                                          xtype : 'gridcolumn',
                                          hidden : true,
                                          width : 150,
                                          dataIndex : 'agentId',
                                          text : avmon.deploy.monitoringAgent
                                        }, {
                                          xtype : 'gridcolumn',
                                          width : 150,
                                          dataIndex : 'hostName',
                                          text : avmon.deploy.hostName
                                        }, {
                                          xtype : 'gridcolumn',
                                          dataIndex : 'ip',
                                          text : avmon.deploy.ipaddress
                                        }, {
                                          xtype : 'gridcolumn',
                                          dataIndex : 'os',
                                          text : avmon.deploy.operatingSystem
                                        }, {
                                          xtype : 'gridcolumn',
                                          width : 80,
                                          dataIndex : 'deploy',
                                          text : avmon.deploy.deployedStatus
                                        }, {
                                          xtype : 'gridcolumn',
                                          dataIndex : 'path',
                                          text : avmon.deploy.ampInstance
                                        }, {
                                          xtype : 'gridcolumn',
                                          dataIndex : 'caption',
                                          text : avmon.deploy.title
                                        }, {
                                          xtype : 'gridcolumn',
                                          width : 60,
                                          dataIndex : 'version',
                                          text : avmon.deploy.currentVersion
                                        }, {
                                          xtype : 'gridcolumn',
                                          width : 200,
                                          dataIndex : 'lastUpdateTime',
                                          text : avmon.deploy.ampUpgradeTime
                                        }, {
                                          xtype : 'gridcolumn',
                                          width : 200,
                                          dataIndex : 'osVersion',
                                          text : avmon.deploy.osVersion
                                        }, {
                                          xtype : 'gridcolumn',
                                          width : 200,
                                          dataIndex : 'type',
                                          hidden:true,
                                          text : avmon.deploy.targetMoType
                                        } ],
                                        selModel : Ext.create(
                                            'Ext.selection.CheckboxModel', {

                                            }),
                                        dockedItems : [ {
                                          xtype : 'toolbar',
                                          dock : 'top',
                                          frame : false,
                                          items : [
                                              {
                                                xtype : 'button',
                                                id : 'DeployedMonitorBtn',
                                                allowDepress : false,
                                                iconCls : 'icon-monitor',
                                                text : avmon.deploy.installAndUpdateAMP,
                                                listeners : {
                                                  click : {
                                                    fn : me.onBatBatchDeployMonitorClick,
                                                    scope : me
                                                  }
                                                }
                                              },
                                              {
                                                width : 190,
                                                fieldLabel : avmon.deploy.deployedStatus,
                                                labelStyle : 'text-align:right;width:80;',
                                                name : 'deploystatus',
                                                allowBlank : false,

                                                xtype : 'combobox',
                                                store : Ext.create(
                                                    'Ext.data.Store', {
                                                      fields : [ 'value',
                                                          'display' ],
                                                      autoLoad : true,
                                                      data : [ {
                                                        'value' : '1',
                                                        'display' : avmon.deploy.deployed
                                                      }, {
                                                        'value' : '0',
                                                        'display' : avmon.deploy.undeployed
                                                      }
                                                      ]
                                                    }),
                                                allowBlank : true,
                                                editable : false,
                                                valueField : 'value',
                                                displayField : 'display',
                                                id : 'deploystatus',
                                                itemId : 'deploystatus'
                                              },
                                              {
                                                xtype : 'textfield',
                                                fieldLabel : avmon.equipmentCenter.ipaddress,
                                                id : 'ipAddressID',
                                                width : 140,
                                                itemId : 'ipAddressID',
                                                labelWidth : 30
                                              },
                                              {
                                                xtype: 'datefield',
                                                id: 'start_date',
                                                itemId: 'start_date',
                                                width: 170,
                                                fieldLabel: avmon.deploy.deployedTime,
                                                labelWidth: 60,
                                                editable: false,
                                                allowBlank : true,
                                                format: 'Y-m-d'
                                              },
                                              {
                                                  xtype: 'timefield',
                                                  id: 'start_time',
                                                  itemId: 'start_time',
                                                  width: 58,
                                                  fieldLabel: '',
                                                  editable: false,
                                                  allowBlank : true,
                                                  format: 'H:i',
                                                  increment: 60
                                              },
                                              {
                                                xtype: 'datefield',
                                                id: 'end_date',
                                                itemId: 'end_date',
                                                width: 140,
                                                fieldLabel: avmon.kpiCompare.to,
                                                labelSeparator :'',
                                                labelWidth: 20,
                                                editable: false,
                                                format: 'Y-m-d'
                                              },
                                              {
                                                  xtype: 'timefield',
                                                  id: 'end_time',
                                                  itemId: 'end_time',
                                                  width: 58,
                                                  fieldLabel: '',
                                                  editable: false,
                                                  format: 'H:i',
                                                  increment: 60
                                              },
                                              {
                                                xtype : 'button',
                                                text : avmon.deploy.retrieve,
                                                listeners : {
                                                  click : {
                                                    fn : me.onDeployQueryButtonClick,
                                                    scope : me
                                                  }
                                                }
                                              }
                                          ]
                                        } , 
                                        {
                                          xtype : 'pagingtoolbar',
                                          dock : 'bottom',
                                          width : 360,
                                          displayInfo : true,
                                          store : 'UpgradeMonitors'
                                        }],
                                        listeners : {
                                          scrollershow : {
                                            fn : me.onUpgradeMonitorInstanceGridScrollerShow,
                                            scope : me
                                          }
                                        }
                                      }, 
                                      {
                                        xtype : 'textarea',
                                        grow : true,
                                        name : 'message',
                                        id : 'messageId',
                                        itemId : 'messageId',
                                        hidden:true,
                                        readOnly:true,
                                        // fieldLabel: '下发日志',
                                        anchor : '100%',
                                        emptyText : avmon.deploy.logs,
                                        maxLength : 1000, // 设置多行文本框的最大长度为100
                                        minLength : 5,
                                        region : 'south',
                                        height : '30%',
                                        preventScrollbars : true,
                                        listeners:{
                                        	'blur':function(el){el.hide();}
                                        }
                                      // 设置多行文本框没有滚动条显示
                                      }
                                  ]
                                }
                                ]
                          } ]
                    });

            me.callParent(arguments);
          },

          onButtonClick : function(button, e, eOpts) {
            if (!Ext.getCmp('panel1').loadFlag) {
              Ext.getCmp('panel1').body
                  .update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../pages/config/agentManage/index.jsp"></iframe>');
              Ext.getCmp('panel1').loadFlag = true;
            }

            Ext.getCmp('contentPanel').getLayout().setActiveItem(0);

          },

          onButtonClick1 : function(button, e, eOpts) {
            Ext.getCmp('contentPanel').getLayout().setActiveItem(2);

            Ext.getCmp('agentDownloadGrid').getStore().load();

          },

          onButtonClick2 : function(button, e, eOpts) {
            Ext.getCmp('contentPanel').getLayout().setActiveItem(3);

            Ext.getCmp('upgradeMonitorGrid').getStore().load();

          },

          onPanel1AfterLayout : function(container, layout, eOpts) {
            if (!Ext.getCmp('panel1').loadFlag) {
              Ext.getCmp('panel1').body
                  .update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../pages/config/agentManage/index.jsp"></iframe>');
              Ext.getCmp('panel1').loadFlag = true;
            }

            Ext.getCmp('contentPanel').getLayout().setActiveItem(0);

          },

          onUpgradeMonitorGridSelect : function(rowmodel, record, index, eOpts) {
            var grid = Ext.getCmp("upgradeMonitorInstanceGrid");
            if (!Ext.alarm) {
              Ext.alarm = {};
            } else {
              Ext.alarm.display = record.get("name");
              Ext.alarm.type = record.get("type");
            }
            gridProxy = grid.getStore().getProxy();
            gridProxy.extraParams.monitorId = record.get("name");
            grid.getStore().currentPage = 1;
            grid.getStore().load();
          },

          onAgentUpgradeFilesGridSelect : function(rowmodel, record, index, eOpts) {
            var grid = Ext.getCmp("agentGrid");
            if (!Ext.alarm) {
              Ext.alarm = {};
            } else {
              Ext.alarm.os = record.get("os");
              Ext.alarm.osVersion = record.get("osVersion");
            }

            grid.getStore().load({
              params : {
                os : record.get("os"),
                osVersion : record.get("osVersion")
              }
            });

          },

          /**
           * 下发AMP
           */
          onBatBatchDeployMonitorClick : function(button, e, eOpts) {
            var g = Ext.getCmp("upgradeMonitorInstanceGrid");
            var store = g.getStore();
            var sm = g.getSelectionModel();
            var records = sm.getSelection();
            var type = "";
            var items = "";
            var monitorId = "";
            var logmsg = "";
            var logbox = Ext.getCmp('messageId');
            var ip = "";
            //滚动条被刷新的次数
            var count = 0;
            //进度百分比
            var percentage = 0;
            //进度条信息
            var processText = '';

            var allCount = records.length;
            if (allCount <= 0) {
              Ext.MessageBox.alert(avmon.common.message,
                  avmon.deploy.choiceBeIssuedHost);
              return;
            }
            logbox.setValue('');
            logbox.show();
            Ext.MessageBox.show({
              title: avmon.deploy.loadWin,
              msg: avmon.deploy.ampDetail,
              progressText:avmon.deploy.pushing,
              width: 300,
              progress: true,
              closable: false,
              animateTarget: 'DeployedMonitorBtn'
            });

            var f = function (v,items) {
                return function () {
                    var flag = false;
                    var percentage = v / allCount;
                    count++;
                    percentage = count/allCount;
                    processText = avmon.deploy.process+count+'/'+allCount;
                    Ext.Ajax.request({
                      url : 'oneDeployMonitor',
                      params : {
                        item : items
                      },
                      async:false,
                      success : function(response, opts) {
                        var obj = Ext.decode(response.responseText);
                        if (obj.msg != "") {
//                          console.log('obj.msg is '+obj.msg);
                          logmsg = logmsg + obj.msg + "\n";
                        }

                        Ext.MessageBox.updateProgress(percentage, processText +'  ' +avmon.deploy.finish);
                        Ext.getCmp('messageId').setValue(logmsg);
                        if(v==allCount){flag = true}

                      },
                      failure : function(response, opts) {
                        logmsg = logmsg +ip+ ampName + avmon.deploy.timeout+"\n";
                        Ext.getCmp('messageId').setValue(logmsg);
                        if(v==allCount){flag = true}
                      }
                    });
                    
                    if(flag==true){
                      setTimeout(function(){},1000);
                      Ext.MessageBox.hide();
                      Ext.Msg.alert(avmon.common.reminder, avmon.deploy.pushAmpFinish,function(){
                        g.getStore().load({
                          params : {
                            deploystatus : Ext.getCmp('deploystatus').value,
                            ipaddress : Ext.getCmp('ipAddressID').value
                          }
                        });
                        logbox.focus();
                      });
                    }
                };
            };
            Ext.each(records, function(record,index) {
              var instanceId = record.get("path");
              var moId = record.get("agentId");
              monitorId = record.get("name");
              ip = record.get("ip");
              type = record.get("type");
              var ampName = record.get("caption");
              items = moId + "|" + instanceId + "|" + type + "|" + ampName;
              setTimeout(f(index+1,items), (index+1) * 500);
            })
          },

          onDeployQueryButtonClick : function(rowmodel, record, index, eOpts) {
            var startTime = Ext.getCmp("start_date").rawValue + " " + Ext.getCmp("start_time").rawValue;
            var endTime = Ext.getCmp("end_date").rawValue + " " + Ext.getCmp("end_time").rawValue;
            var gridProxy;
            Ext.getCmp('messageId').hide();
            var grid = Ext.getCmp("upgradeMonitorInstanceGrid");
            grid.getStore().currentPage=1;
            gridProxy = grid.getStore().getProxy();
            gridProxy.extraParams.deploystatus = Ext.getCmp('deploystatus').value;
            gridProxy.extraParams.ipaddress = Ext.getCmp('ipAddressID').value;
            gridProxy.extraParams.startTime = startTime;
            gridProxy.extraParams.endTime = endTime;
            grid.getStore().load();
          },

          onUpgradeMonitorInstanceGridScrollerShow : function() {
            if (scroller && scroller.scrollEl) {
              scroller.clearManagedListeners();
              scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll,
                  scroller);
            }
          },
          /**
           * 批量更新agent
           */
          onDeployedAgentClick : function() {
            Ext.MessageBox.confirm(avmon.common.reminder,
                avmon.deploy.whetherUpgrade, function(btn) {
                  if (btn == "yes") {
                    var g = Ext.getCmp("agentGrid");

                    var sm = g.getSelectionModel();
                    var records = sm.getSelection();
                    if (records.length <= 0) {
                      // Ext.window.alert('信息','请先选择要下发的脚本');
                      Ext.MessageBox.alert(avmon.common.message,
                          avmon.deploy.choiseBeUpgradedHost);
                      return;
                    }

                    var store = g.getStore();
                    var items = "";
                    var monitorId = "";
                    var os = Ext.alarm.os;
                    var os = Ext.alarm.osVersion;
                  }
                });
          }

        });