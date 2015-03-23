//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.Loader.setPath('Ext.ux', '../pages/commons/ux');
Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.ux.PopupField',
// 'Ext.ux.form.SearchField',
'Ext.selection.CheckboxModel', 'CFG.view.TdAvmonKpiInfoUpgradeRuleGrid' ]);
var gridStore = Ext.create('CFG.store.TdAvmonUpgradeRuleGridStore');
var pupKpiGridPanel = Ext.create('CFG.view.TdAvmonKpiInfoUpgradeRuleGrid', {
  pup : true,
  noPup : false
});
var pupKpiWin = Ext.create('Ext.window.Window', {
  title : avmon.config.selectKPI,
  height : 370,
  width : 850,
  hidden : true,
  iconCls : 'icon-form',
  layout : 'fit',
  plain : true,
  modal : true,
  closable : true,
  closeAction : 'hide',
  items : [ pupKpiGridPanel ]
});
function selectKpi() {
  var selection = pupKpiGridPanel.getSelectionModel().getSelection();
  if (selection.length == 0 || selection.length > 1) {
    Ext.MessageBox.alert(avmon.common.reminder,
        avmon.config.selectKPINotMoreThanOne);
    return;
  } else {
    editForm.down("#kpi").setValue(selection[0].data.kpi_code);
    editForm.down("#kpiName").setValue(selection[0].data.caption);
    pupKpiWin.hide();
  }
}
function pupKpiGrid() {
  // pupKpiGridPanel.getSelectionModel().clearSelections();
  pupKpiWin.show();

  var selection = pupKpiGridPanel.getSelectionModel().getSelection();
  if (selection.length > 0) {
    kpiGridStore = pupKpiGridPanel.store;
    kpiGridStore.load();
  }
}
var editForm = Ext.create('Ext.form.Panel', {
  bodyPadding : 5,
  border : false,
  frame : true,
  buttonAlign : 'center',
  xtype : 'filedset',
  url : 'saveUpdateAlarmUpgradeRuleInfo',
  fieldDefaults : {// 统一设置表单字段默认属性
    labelSeparator : ' ',// 分隔符
    labelWidth : 200,// 标签宽度
    labelStyle : 'text-align:left;',
    width : 500
  },
  defaultType : 'textfield',
  items : [ {
    fieldLabel : 'Id',
    name : 'id',
    hidden : true
  }, {
    fieldLabel : avmon.config.monitoringObject,
    name : 'mo',
    allowBlank : false
  }, {
    fieldLabel : avmon.alarm.alarmLevel,
    name : 'grade',
    allowBlank : false
  }, {
    fieldLabel : 'KPI',
    name : 'kpi',
    itemId : 'kpi',
    maxLength : 255,
    xtype : 'popupfield',
    allowBlank : false,
    onTriggerClick : pupKpiGrid
  }, {
    fieldLabel : avmon.config.alarmContentRegularExpression,
    name : 'content',
    allowBlank : false
  }, {
    fieldLabel : avmon.config.accumulativeAlarmTimes,
    name : 'alarm_count',
    allowBlank : false
  }, {
    fieldLabel : avmon.config.alarmTimeWindowMinute,
    name : 'alarm_count_duration',
    allowBlank : false
  }
  , {
    fieldLabel : avmon.alarm.alarmLevel,
    name : 'new_grade',
    allowBlank : false,
    xtype : 'combobox',
    store : Ext.create('Ext.data.Store', {
      fields : [ 'value', 'display' ],
      data : [ {
        'value' : 1,
        'display' : avmon.common.message
      }, {
        'value' : 2,
        'display' : avmon.alarm.normalAlarm
      }, {
        'value' : 3,
        'display' : avmon.config.minorAlarm
      }, {
        'value' : 4,
        'display' : avmon.alarm.mainAlarm
      }, {
        'value' : 5,
        'display' : avmon.alarm.seriousAlarm
      } ]
    }),
    queryMode : 'local',
    editable : false,
    valueField : 'value',
    displayField : 'display'
  } ],
  buttons : [ {
    text : avmon.common.save,
    formBind : true, // only enabled once the form is valid
    disabled : true,
    iconCls : 'icon-save',
    handler : function() {
      var submitForm = this.up('form').getForm();
      if (submitForm.isValid()) {
        submitForm.submit({
          method : 'POST',
          waitMsg : avmon.common.saving,
          success : function(form, action) {
            Ext.MessageBox.alert(avmon.common.reminder, action.result.msg);
            gridStore.load();
            eidtWin.hide();
          }
        });
      }
    }
  }, {
    text : avmon.common.close,
    iconCls : 'icon-close',
    handler : function() {
      this.up('form').getForm().reset();
      eidtWin.hide();
    }
  } ]
});
pupKpiGridPanel.pupKpiWin = pupKpiWin;
pupKpiGridPanel.upgradeRuleForm = editForm;
var eidtWin = Ext.create('Ext.window.Window', {
  title : avmon.config.add,
  height : 300,
  width : 600,
  hidden : true,
  iconCls : 'icon-form',
  layout : 'fit',
  plain : true,
  modal : true,
  closable : true,
  closeAction : 'hide',
  items : [ editForm ]
});
// 创建多选
var selModel = Ext.create('Ext.selection.CheckboxModel');
// 创建搜索框，后台通过query参数获取查询条件
Ext.define('CFG.view.TdAvmonUpgradeRuleGrid', {
  extend : 'Ext.grid.Panel',
  alias : 'widget.TdAvmonUpgradeRuleGrid',
  initComponent : function() {
    Ext.apply(this, {
      id : 'TdAvmonUpgradeRule_grid',
      title : avmon.config.alarmUpgradingRule,
      iconCls : 'icon-grid',
      border : false,
      columns : [
      {
        text : 'Id',
        width : 40,
        sortable : true,
        dataIndex : 'id'
      }, {
        text : avmon.config.monitoringObject,
        width : 100,
        sortable : true,
        dataIndex : 'mo'
      }, {
        text : avmon.alarm.alarmLevel,
        width : 80,
        sortable : true,
        dataIndex : 'grade'
      }, {
        text : 'KPI',
        width : 100,
        sortable : true,
        dataIndex : 'kpi'
      }, {
        text : avmon.config.alarmContentRegularExpression,
        width : 200,
        sortable : true,
        dataIndex : 'content'
      }, {
        text : avmon.config.accumulativeAlarmTimes,
        width : 100,
        sortable : true,
        dataIndex : 'alarm_count'
      }, {
        text : avmon.config.alarmTimeWindowMinute,
        width : 100,
        sortable : true,
        hidden : true,
        dataIndex : 'alarm_count_duration'
      }, {
        text : avmon.config.afterUpgradingAlarmLevel,
        width : 100,
        sortable : true,
        hidden : true,
        dataIndex : 'new_grade'
      } ],
      store : gridStore,
      selModel : selModel,
      dockedItems : [
          {
            xtype : 'toolbar',
            items : [
                {
                  text : avmon.config.add,
                  iconCls : 'icon-add',
                  handler : function() {
                    editForm.getForm().reset();
                    editForm.getForm().findField("alarm_count").setValue("0");
                    editForm.getForm().findField("alarm_count_duration")
                        .setValue("0");
                    eidtWin.setTitle(avmon.config.add);
                    eidtWin.show();
                  }
                },
                '-',
                {
                  itemId : 'delete',
                  text : avmon.common.deleted,
                  id : 'delete_button',
                  iconCls : 'icon-delete',
                  handler : function() {
                    var selection = this.up("panel").getSelectionModel()
                        .getSelection();
                    var panelStore = this.up("panel").getStore();
                    if (selection.length == 0) {
                      Ext.MessageBox.alert(avmon.common.reminder,
                          avmon.config.selectOperateLine);
                      return;
                    } else {
                      Ext.MessageBox.confirm(avmon.common.reminder,
                          avmon.config.whetherToDelete, function(btn) {
                            if (btn == "yes") {
                              var ids = [];
                              Ext.each(selection, function(item) {
                                ids.push(item.data.id);
                              });
                              Ext.Ajax.request({
                                url : 'deleteAlarmUpgradeRuleByIds?ids='
                                    + ids.join(','),
                                success : function(response, opts) {
                                  var returnJson = Ext.JSON
                                      .decode(response.responseText);
                                  Ext.MessageBox.alert(avmon.common.reminder,
                                      returnJson.msg);
                                  panelStore.load();
                                },
                                failure : function(response, opts) {

                                }
                              });
                            }
                          });
                    }
                  }
                },// add by mark start 2104-5-21
                '-', {
                  xtype : 'textfield',
                  id : 'mo',
                  itemId : 'mo',
                  width : 233,
                  labelWidth : 70,
                  fieldLabel : avmon.config.monitoringObject
                }, '-', {
                  xtype : 'textfield',
                  itemId : 'kpiCode',
                  width : 200,
                  labelWidth : 50,
                  fieldLabel : avmon.config.kpiEncoding
                }, '-', {
                  fieldLabel : avmon.alarm.alarmLevel,
                  name : 'alarmLevel',
                  itemId : 'alarmLevel',
                  labelWidth : 60,
                  allowBlank : true,
                  xtype : 'combobox',
                  store : Ext.create('Ext.data.Store', {
                    fields : [ 'value', 'display' ],
                    data : [ {
                      'value' : '1',
                      'display' : avmon.alarm.well
                    }, {
                      'value' : '2',
                      'display' : avmon.alarm.normalAlarm
                    }, {
                      'value' : '3',
                      'display' : avmon.alarm.minorAlarm
                    }, {
                      'value' : '4',
                      'display' : avmon.alarm.mainAlarm
                    }, {
                      'value' : '5',
                      'display' : avmon.alarm.seriousAlarm
                    } ,{
                      'value' : '*',
                      'display' : avmon.config.allLevel
                    }]
                  }),
                  queryMode : 'local',
                  editable : false,
                  valueField : 'value',
                  displayField : 'display'
                }, {
                  xtype : 'button',
                  id : 'searchBtn',
                  iconCls : 'icon-search',
                  text : avmon.config.retrieve,
                  handler : function() {
                    var toolBar = this.ownerCt;
                    var grid = this.ownerCt.ownerCt;
                    var mo = toolBar.down('#mo').getValue();
                    var kpiCode = toolBar.down('#kpiCode').getValue();
                    var alarmLevel = toolBar.down('#alarmLevel').getValue();
                    var extraParams = grid.store.proxy.extraParams;

                    if (mo && mo.trim()) {
                      extraParams.mo = mo;
                    } else {
                      extraParams.mo = null;
                    }
                    if (kpiCode && kpiCode.trim()) {
                      extraParams.kpiCode = kpiCode;
                    } else {
                      extraParams.kpiCode = null;
                    }
                    if (alarmLevel && alarmLevel.trim()) {
                      extraParams.alarmLevel = alarmLevel;
                    } else {
                      extraParams.alarmLevel = null;
                    }
                    grid.store.currentPage = 1;
                    grid.store.load();
                  }
                }
            // add by mark end 2104-5-21
            ]
          }, {
            xtype : 'pagingtoolbar',
            store : gridStore,
            dock : 'bottom',
            displayInfo : true,
            beforePageText : avmon.config.beforePageText,
            afterPageText : avmon.config.afterPageText,
            displayMsg : avmon.config.displayMsg,
            emptyMsg : avmon.config.emptyMsg
          } ],
      listeners : {
        itemdblclick : function(view, record, item, index, e, eOpts) {
          eidtWin.setTitle(avmon.config.modify);
          editForm.loadRecord(record);
          eidtWin.show();
        }
      }
    });
    this.callParent(arguments);
  }
});