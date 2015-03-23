Ext.Loader.setPath('Ext.ux', 'commons');
Ext.require([ 'Ext.grid.*',
              'Ext.toolbar.Paging', 
              'Ext.ux.ComboBoxTree', 
              'Ext.ux.PopupField',
              'Ext.form.Panel', 
              'Ext.selection.CheckboxModel',
              'SYS.view.TdAvmonKpiInfoGrid'
              ]);

var pupKpiGridPanel = Ext.create('SYS.view.TdAvmonKpiInfoGrid',{pup:true,noPup:false});

var pupKpiWin = Ext.create('Ext.window.Window',{
    title: avmon.config.selectKPI,
    height: 370,
    width:850,
    hidden: true,
    iconCls:'icon-form',
    layout: 'fit',
    plain: true,
    modal:true,
    closable: true,
    closeAction: 'hide',
    items: [pupKpiGridPanel]
});

function pupKpiGrid(){
  pupKpiWin.show();
  
  var selection = pupKpiGridPanel.getSelectionModel().getSelection();
  if(selection.length > 0){
    kpiGridStore = pupKpiGridPanel.store;
    kpiGridStore.load();
  }
}

var gridStore = Ext.create('SYS.store.PortalMoPropertiesGridStore');

var editForm = Ext.create('Ext.form.Panel', {
  bodyPadding : 12,
  border : false,
  frame : true,
  buttonAlign : 'center',
  xtype : 'filedset',
  url : 'saveUpdateMoPropertiesInfo',
  defaultType : 'textfield',
  items : [ {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.typeId,
    name : 'typeId',
    itemId:'typeId',
    id:'typeId',
    editable :false,
    xtype : 'combobox',
    store : Ext.create('SYS.store.PropertiesTypeComBoxStore'),
    displayField: 'name',
    valueField : 'value',
    allowBlank : false,
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.attrId,
    name : 'name',
    itemId:'name',
    validateOnBlur: true,
    validationDelay:2000,
    validateOnChange: false,
    //登录名验证
    validator: function (value) {
      
        if(Ext.getCmp('insFlag').getValue()=="1"){
          return true;
        }
        
        if (!/^[a-zA-Z]+$/.test(value)) {
            return avmon.system.inputNotAllowed;
        }
        
        if(value.length > 100){
          return avmon.system.inputNotLongerThan100;
        }
        var validator = this;
        var error = true;
        Ext.Ajax.request({
            async: false,
            scope: validator,
            url: 'checkEnglistName',
            params:{name:value,typeId:Ext.getCmp('typeId').getValue()},
            method: 'POST',
            success: function (response) {
                var result = Ext.JSON.decode(response.responseText).result;
                if (!result) {
                    error = avmon.system.inputExists;
                }
            }
        });
        return error;
    },
    listeners: {
        focus: {
            fn: function () { this.clearInvalid(); }
        }
    },
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.caption,
    name : 'caption',
    allowBlank : false,
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.classinfo,
    name : 'classInfo',
    allowBlank : true,
    maxLength:200,
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.hide ,
    name : 'hide',
    allowBlank : true,
    xtype : 'combobox',
    store : Ext.create('Ext.data.Store', {
      fields : ['value', 'display'],
      data : [{
            'value' : '0',
            'display' : avmon.config.yes
          }, {
            'value' : '1',
            'display' : avmon.config.no
          }
         ]
    }),
    queryMode : 'local',
    editable : false,
    valueField : 'value',
    displayField : 'display',
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.passwd,
    name : 'passwd',
    allowBlank : true,
    xtype : 'combobox',
    store : Ext.create('Ext.data.Store', {
      fields : ['value', 'display'],
      data : [{
            'value' : '0',
            'display' : avmon.config.yes
          }, {
            'value' : '1',
            'display' : avmon.config.no
          }
         ]
    }),
    queryMode : 'local',
    editable : false,
    valueField : 'value',
    displayField : 'display',
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.valueType,
    name : 'valueType',
    allowBlank : true,
    xtype:'numberfield',
    minValue : 0,//最小值
    maxValue:9,
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.orderIndex,
    name : 'orderIndex',
    xtype:'numberfield',
    minValue : 0,//最小值
    maxValue:99999,
    allowBlank : true,
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.defaultValue,
    name : 'defaultValue',
    allowBlank : true,
    vtype:'alphanum',
    maxLength:200,
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel:avmon.system.nullable,
    name: 'nullable',
    allowBlank: false,
    xtype : 'combobox',
    store : Ext.create('Ext.data.Store', {
      fields : ['value', 'display'],
      data : [{
            'value' : '1',
            'display' : avmon.config.yes
          }, {
            'value' : '0',
            'display' : avmon.config.no
          }
         ]
    }),
    queryMode : 'local',
    editable : false,
    valueField : 'value',
    displayField : 'display',
    width : 340
  }, {
    labelStyle : 'text-align:right;width:80;',
    fieldLabel : avmon.system.kpiCode,
    name : 'kpiCode',
    itemId:'kpiCode',
    allowBlank : true,
    xtype:'popupfield',
    editable :true,
    onTriggerClick : pupKpiGrid,
    width : 340
  }, {
    xtype : 'hiddenfield',
    name : 'insFlag',
    id : 'insFlag',
    value : ''
  }

  ],
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
          },
          failure : function(response, opts) {
            Ext.MessageBox.alert(avmon.common.reminder, opts.result.msg);
          }
        });
      }
    }
  }, {
    text : avmon.common.close,
    iconCls : 'icon-cancel',
    handler : function() {
      this.up('form').getForm().reset();
      eidtWin.hide();
    }
  } , {
    text : avmon.common.reset,
    iconCls : 'icon-reset',
    handler : function() {
      this.up('form').getForm().reset();
    }
  } ]
});

pupKpiGridPanel.pupKpiWin = pupKpiWin;
pupKpiGridPanel.editForm = editForm;

var eidtWin = Ext.create('Ext.window.Window', {
  title : avmon.system.add,
  height : 400,
  width : 450,
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
Ext.define('SYS.view.PortalMoPropertiesGrid', {
  extend : 'Ext.grid.Panel',
  alias : 'widget.PortalMoPropertiesGrid',
  initComponent : function() {
    Ext.apply(this, {
      id : 'PortalMoProperties_grid',
      title : avmon.system.moProperties,
      iconCls : 'icon-grid',
      border : false,
      columns : [ {
        text : avmon.system.typeId,
        width : 100,
        sortable : true,
        dataIndex : 'typeId'
      }, {
        text : avmon.system.attrId,
        width : 100,
        sortable : true,
        dataIndex : 'name'
      }, {
        text : avmon.system.caption,
        width : 100,
        sortable : true,
        dataIndex : 'caption'
      }, {
        text : avmon.system.classinfo,
        width : 100,
        sortable : true,
        dataIndex : 'classInfo'
      }, {
        text : avmon.system.hide,
        width : 100,
        sortable : true,
        dataIndex : 'hide',
        renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
          var data = record.data ; 
          var hide = data.hide;
          if(hide && (hide==0)){
            return avmon.config.yes;
          }else if(hide && (hide==1)){
            return avmon.config.no;
          }else{
            return "";
          }
        }
      }, {
        text : avmon.system.passwd,
        width : 100,
        sortable : true,
        dataIndex : 'passwd',
        renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
          var data = record.data ; 
          var passwd = data.passwd; 
          if(passwd && (passwd==0)){
            return avmon.config.yes;
          }else if(passwd && (passwd==1)){
            return avmon.config.no;
          }else{
            return "";
          }
        }
      }, {
        text : avmon.system.valueType,
        width : 100,
        sortable : true,
        dataIndex : 'valueType'
      }, {
        text : avmon.system.orderIndex,
        width : 100,
        sortable : true,
        dataIndex : 'orderIndex'
      }, {
        text : avmon.system.defaultValue,
        width : 100,
        sortable : true,
        dataIndex : 'defaultValue'
      }, {
        text : avmon.system.nullable,
        width : 100,
        sortable : true,
        dataIndex : 'nullable',
        renderer:function(data, metadata, record, rowIndex, columnIndex, store) { 
          var data = record.data ; 
          var nullable = data.nullable; 
          if(nullable && (nullable==0)){
            return avmon.config.yes;
          }else if(nullable && (nullable==1)){
            return avmon.config.no;
          }else{
            return "";
          }
        }
      }, {
        text : avmon.system.kpiCode,
        width : 100,
        sortable : true,
        dataIndex : 'kpiCode'
      } ],
      store : gridStore,
      selModel : selModel,
      dockedItems : [
          {
            xtype : 'toolbar',
            items : [
                {
                  text : avmon.system.add,
                  iconCls : 'icon-add',
                  handler : function() {
                    editForm.getForm().reset();
                    eidtWin.setTitle(avmon.system.add);
                    if(editForm.down('#typeId').readOnly){
                      editForm.down('#typeId').setReadOnly(false);
                    }
                    if(editForm.down('#name').readOnly){
                      editForm.down('#name').setReadOnly(false);
                    }
                    
                    Ext.getCmp('insFlag').setValue('0');
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
                          avmon.system.selectOperateLine);
                      return;
                    } else {
                      Ext.MessageBox.confirm(avmon.common.reminder,
                          avmon.system.whetherToDelete, function(btn) {
                            if (btn == "yes") {
                              var ids = [];
                              Ext.each(selection, function(item) {
                                ids.push(item.data.typeId+"|"+item.data.name);
                              });
                              Ext.Ajax.request({
                                url : 'deleteMoPropertiesByIds?ids='
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
                }
            // ,'-',searchField
            ]
          }, {
            xtype : 'pagingtoolbar',
            store : gridStore,
            dock : 'bottom',
            displayInfo : true,
            beforePageText : avmon.system.beforePageText,
            afterPageText : avmon.system.afterPageText,
            displayMsg : avmon.system.displayMsg,
            emptyMsg : avmon.system.emptyMsg
          } ],
      listeners : {
        itemdblclick : function(view, record, item, index, e, eOpts) {
          eidtWin.setTitle(avmon.system.modify);
          editForm.loadRecord(record);
          editForm.down('#typeId').setReadOnly(true);
          editForm.down('#name').setReadOnly(true);
          Ext.getCmp('insFlag').setValue('1');
          eidtWin.show();
        }
      }
    });
    this.callParent(arguments);
  }
});