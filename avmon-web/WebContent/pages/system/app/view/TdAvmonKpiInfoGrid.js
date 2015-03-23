//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.require([ 'Ext.grid.*', 'Ext.toolbar.Paging',
// 'Ext.ux.form.SearchField',
'Ext.selection.CheckboxModel' ]);

var kpiInfoGridStore = Ext.create('SYS.store.TdAvmonKpiInfoGridStore');

// 创建多选
var kpiSelModel = Ext.create('Ext.selection.CheckboxModel');

Ext.define('SYS.view.TdAvmonKpiInfoGrid', {
  extend : 'Ext.grid.Panel',
  alias : 'widget.TdAvmonKpiInfoGrid',
  // 自定义属性
  pup : false,
  noPup : true,
  editForm : null,
  pupKpiWin : null,
  initComponent : function() {
    Ext.apply(this, {
      id : 'TdAvmonKpiInfo_grid',
      title : avmon.config.kpiInformation,
      iconCls : 'icon-grid',
      border : false,
      editForm : this.editForm,
      pupKpiWin : this.pupKpiWin,
      columns : [
          {
            text : avmon.config.kpiEncoding,
            width : 100,
            sortable : true,
            dataIndex : 'kpi_code'
          },
          {
            text : avmon.config.kpiEnglishName,
            width : 100,
            sortable : true,
            dataIndex : 'kpi_name'
          },
          {
            text : avmon.config.kpiChineseName,
            width : 100,
            sortable : true,
            dataIndex : 'caption'
          },
          {
            text : avmon.config.aggregationType,
            width : 100,
            sortable : true,
            dataIndex : 'aggmethod'
          },
          {
            text : avmon.config.decimal,
            width : 100,
            sortable : true,
            dataIndex : 'precision'
          },
          {
            text : avmon.config.unit,
            width : 100,
            sortable : true,
            dataIndex : 'unit'
          }
          ,
          {
            text : avmon.config.storedToDatabase,
            width : 100,
            sortable : true,
            dataIndex : 'isstore',
            renderer : function(data, metadata, record, rowIndex, columnIndex,
                store) {
              var data = record.data;
              var isstore = data.isstore;
              if (isstore == 0) {
                return avmon.config.no;
              } else if (isstore == 1) {
                return avmon.config.yes;
              } else {
                return "";
              }
            }
          },
          {
            text : avmon.config.storageCycle,
            width : 100,
            sortable : true,
            dataIndex : 'storeperiod'
          },
          {
            text : avmon.config.dataType,
            width : 100,
            sortable : true,
            dataIndex : 'datatype',
            renderer : function(data, metadata, record, rowIndex, columnIndex,
                store) {
              var data = record.data;
              var datatype = data.datatype;
              if (datatype == 0) {
                return "number";
              } else if (datatype == 1) {
                return "string";
              } else {
                return "";
              }
            }
          }, {
            text : 'Id',
            width : 100,
            sortable : true,
            hidden : true,
            dataIndex : 'id'
          } ],
      store : kpiInfoGridStore,
      selModel : kpiSelModel,
      selectKPI:function(){
        var selection = this.getSelectionModel().getSelection();
        if(selection.length == 0||selection.length >1){ 
            Ext.MessageBox.alert(avmon.common.reminder,avmon.config.selectKPINotMoreThanOne);
            return; 
        }else{ 
          this.editForm.down("#kpiCode").setValue(selection[0].data.kpi_code);
          this.pupKpiWin.hide();
        }
      },
      dockedItems : [
          {
            xtype : 'toolbar',
            items : [
                {
                  text : avmon.config.choose,
                  hidden : this.noPup,
                  iconCls : 'icon-add',
                  handler : function() {
                    this.up("panel").selectKPI();
                  }
                },
                '-', {
                  xtype : 'textfield',
                  width : 180,
                  itemId : 'kpiCode',
                  fieldLabel : avmon.config.kpiEncoding,
                  labelWidth : 50
                }, {
                  xtype : 'textfield',
                  width : 210,
                  itemId : 'kpiName',
                  fieldLabel : avmon.config.kpiEnglishName,
                  labelWidth : 80
                }, {
                  text : avmon.config.search,
                  iconCls : 'icon-search',
                  handler : function() {
                    var toolBar = this.ownerCt;
                    var grid = this.ownerCt.ownerCt;
                    var kpiCode = toolBar.down('#kpiCode').getValue();
                    var kpiName = toolBar.down('#kpiName').getValue();
                    var extraParams = grid.store.proxy.extraParams;
                    grid.store.currentPage = 1;

                    if (kpiCode.length > 0) {
                      extraParams.kpiCode = kpiCode;
                    } else {
                      extraParams.kpiCode = null;
                    }
                    if (kpiName.length > 0) {
                      extraParams.kpiName = kpiName;
                    } else {
                      extraParams.kpiName = null;
                    }
                    grid.store.load();
                  }
                } ]
          }, {
            xtype : 'pagingtoolbar',
            store : kpiInfoGridStore,
            dock : 'bottom',
            displayInfo : true,
            beforePageText : avmon.config.beforePageText,
            afterPageText : avmon.config.afterPageText,
            displayMsg : avmon.config.displayMsg,
            emptyMsg : avmon.config.emptyMsg
          } ]
    });
    this.callParent(arguments);
  }
});