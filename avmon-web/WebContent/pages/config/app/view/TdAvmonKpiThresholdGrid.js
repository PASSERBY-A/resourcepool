Ext.Loader.setPath('Ext.ux', '../pages/commons/ux');
Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging', 'Ext.ux.PopupField',
    'Ext.selection.CheckboxModel', 'CFG.view.TdAvmonKpiInfoGrid' ]);
var gridStore = Ext.create('CFG.store.TdAvmonKpiThresholdGridStore');
var pupKpiGridPanel = Ext.create('CFG.view.TdAvmonKpiInfoGrid', {
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
  url : 'saveUpdateKpiThresholdInfo',
  // layout: 'column',
  fieldDefaults : {// 统一设置表单字段默认属性
    labelSeparator : ' ',// 分隔符
    labelWidth : 160// 标签宽度
    ,
    width : 440
  },
  defaultType : 'textfield',
  items : [ {
    fieldLabel : 'Id',
    name : 'id',
    hidden : true
  }, {
    fieldLabel : avmon.config.monitoringObject,
    name : 'mo',
    maxLength : 255,
    allowBlank : false
  }, {
    fieldLabel : avmon.batchdeploy.monitorInstance,
    name : 'monitor_instance',
    maxLength : 255,
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
    fieldLabel : avmon.deploy.kpiName,
    name : 'kpiName',
    itemId : 'kpiName',
    xtype : 'displayfield',
    maxLength : 255,
    allowBlank : false
  }, {
    fieldLabel : avmon.config.alarmThresholdValue,
    name : 'threshold',
    maxLength : 50,
    allowBlank : false
  }, {
    fieldLabel : avmon.config.judgmentMethod,
    name : 'check_optr',
    allowBlank : false,
    xtype : 'combobox',
    store : Ext.create('Ext.data.Store', {
      fields : [ 'value', 'display' ],
      data : [ {
        'value' : 0,
        'display' : avmon.config.GreaterThan
      }, {
        'value' : 1,
        'display' : avmon.config.lessThan
      }, {
        'value' : 2,
        'display' : avmon.config.equalTo
      }
      // add by mark start 2014-2-18
      , {
        'value' : 3,
        'display' : avmon.config.notEqual
      }, {
        'value' : 4,
        'display' : avmon.config.include
      }, {
        'value' : 5,
        'display' : avmon.config.notIn
      }, {
        'value' : 6,
        'display' : avmon.config.regex
      } ]
    }),
    queryMode : 'local',
    editable : false,
    valueField : 'value',
    displayField : 'display'
  }, {
    fieldLabel : avmon.alarm.alarmLevel,
    name : 'alarm_level',
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
  }, {
    fieldLabel : avmon.config.flashingNumber,
    name : 'accumulate_count',
    allowBlank : false,
    xtype : 'numberfield',
    minValue : 1,// 最小值
    hideTrigger : true
  }, {
    fieldLabel : avmon.alarm.alarmContent,
    name : 'content',
    allowBlank : false,
    xtype : 'textarea',
    maxLength : 255,
    height : 100
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
pupKpiGridPanel.threholdForm = editForm;

var eidtWin = Ext.create('Ext.window.Window', {
  title : avmon.config.add,
  height : 370,
  width : 550,
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
Ext.define('CFG.view.TdAvmonKpiThresholdGrid', {
  extend : 'Ext.grid.Panel',
  alias : 'widget.TdAvmonKpiThresholdGrid',
  initComponent : function() {
    Ext.apply(this, {
      id : 'TdAvmonKpiThreshold_grid',
      title : avmon.config.kpiAlarmThresholdValue,
      iconCls : 'icon-grid',
      border : false,
      columns : [
          {
            text : 'Id',
            width : 40,
            sortable : true,
            dataIndex : 'id'
          },
          {
            text : avmon.config.monitoringObject,
            width : 100,
            sortable : true,
            dataIndex : 'mo'
          },
          {
            text : avmon.batchdeploy.monitorInstance,
            width : 100,
            sortable : true,
            dataIndex : 'monitor_instance'
          },
          {
            text : 'KPI',
            width : 100,
            sortable : true,
            dataIndex : 'kpi'
          },
          {
            text : avmon.deploy.kpiName,
            width : 100,
            sortable : false,
            dataIndex : 'kpiName'
          },
          {
            text : avmon.config.alarmThresholdValue,
            width : 100,
            sortable : true,
            dataIndex : 'threshold'
          },
          {
            text : avmon.config.judgmentMethod,
            width : 100,
            sortable : true,
            dataIndex : 'check_optr',
            renderer : function(data, metadata, record, rowIndex, columnIndex,
                store) {
              var data = record.data;
              var check_optr = data.check_optr;
              if (check_optr == 0) {
                return avmon.config.GreaterThan;
              } else if (check_optr == 1) {
                return avmon.config.lessThan;
              } else if (check_optr == 2) {
                return avmon.config.equalTo;
              } else if (check_optr == 3) {
                return avmon.config.notEqual;
              } else if (check_optr == 4) {
                return avmon.config.include;
              } else if (check_optr == 5) {
                return avmon.config.notIn;
              } else if (check_optr == 6) {
                return avmon.config.regex;
              } else {
                return "";
              }
            }
          },
          {
            header : avmon.alarm.alarmLevel,
            sortable : true,
            width : 100,
            dataIndex : 'alarm_level',
            renderer : function(data, metadata, record, rowIndex, columnIndex,
                store) {
              var data = record.data;
              var alarm_level = data.alarm_level;
              if (alarm_level == 1) {
                return avmon.common.message;
              } else if (alarm_level == 2) {
                return avmon.alarm.normalAlarm;
              } else if (alarm_level == 3) {
                return avmon.config.minorAlarm;
              } else if (alarm_level == 4) {
                return avmon.alarm.mainAlarm;
              } else if (alarm_level == 5) {
                return avmon.alarm.seriousAlarm;
              } else {
                return "";
              }
            }
          }, {
            text : avmon.config.flashingNumber,
            width : 100,
            sortable : true,
            dataIndex : 'accumulate_count'
          }, {
            text : avmon.alarm.alarmContent,
            width : 200,
            sortable : true,
            dataIndex : 'content'
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
                    eidtWin.setTitle(avmon.config.add);
                    eidtWin.show();
                  }
                },
                '-',
                {
                  itemId : 'delete',
                  text : avmon.common.deleted,
                  // id:'delete_button',
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
                                url : 'deleteKpiThresholdByIds?ids='
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
                },
                // add by mark start 2104-5-21 
                '-',
                {
                  xtype: 'textfield',
                  id: 'mo',
                  itemId: 'mo',
                  width: 233,
                  labelWidth:70,
                  fieldLabel: avmon.config.monitoringObject
              }, '-',{
                  xtype: 'textfield',
                  itemId: 'kpiCode',
                  width: 200,
                  labelWidth:50,
                  fieldLabel: avmon.config.kpiEncoding
              }, '-',{
                xtype: 'textfield',
                itemId: 'kpiName',
                width: 200,
                labelWidth:50,
                fieldLabel: avmon.deploy.kpiName
              }, '-',{
                fieldLabel : avmon.alarm.alarmLevel,
                name : 'alarmLevel',
                itemId: 'alarmLevel',
                labelWidth:60,
                allowBlank : true,
                xtype : 'combobox',
                store : Ext.create('Ext.data.Store', {
                  fields : [ 'value', 'display' ],
                  data : [ {
                    'value' : 1,
                    'display' : avmon.alarm.well
                  }, {
                    'value' : 2,
                    'display' : avmon.alarm.normalAlarm
                  }, {
                    'value' : 3,
                    'display' : avmon.alarm.minorAlarm
                  }
                  , {
                    'value' : 4,
                    'display' : avmon.alarm.mainAlarm
                  }, {
                    'value' : 5,
                    'display' : avmon.alarm.seriousAlarm
                  }, {
                    'value' : '*',
                    'display' : avmon.config.allLevel
                  }]
                }),
                queryMode : 'local',
                editable : false,
                valueField : 'value',
                displayField : 'display'
              },
              {
                  xtype: 'button',
                  id: 'searchBtn',
                  iconCls : 'icon-search',
                  text: avmon.config.retrieve,
                  handler : function() {
                    var toolBar = this.ownerCt;
                    var grid = this.ownerCt.ownerCt;
                    var mo = toolBar.down('#mo').getValue();
                    var kpiCode = toolBar.down('#kpiCode').getValue();
                    var kpiName = toolBar.down('#kpiName').getValue();
                    var alarmLevel = toolBar.down('#alarmLevel').getValue();
                    var extraParams = grid.store.proxy.extraParams;
                    if(mo && mo.trim()){
                      extraParams.mo = mo;
                    }else{
                      extraParams.mo = null;
                    }
                    if(kpiCode && kpiCode.trim()){
                      extraParams.kpiCode = kpiCode;
                    }else{
                      extraParams.kpiCode = null;
                    }
                    if(kpiName && kpiName.trim()){
                      extraParams.kpiName = kpiName;
                    }else{
                      extraParams.kpiName = null;
                    }
                    if((alarmLevel && alarmLevel > 0)||(alarmLevel=="*")){
                      extraParams.alarmLevel = alarmLevel;
                    }else{
                      extraParams.alarmLevel = null;
                    }
                    grid.store.currentPage=1;
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
        },
        itemclick : function() {},
        select : function() {}
      }
    });
    this.callParent(arguments);
  }
});