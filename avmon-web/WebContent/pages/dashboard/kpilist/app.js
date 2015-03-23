Ext.define('Company', {
    extend: 'Ext.data.Model',
    fields: [
            { name: 'moId' },
            { name: 'kpiType' }
         ]
});
var mainStore = Ext.create('Ext.data.ArrayStore', {
    model: 'Company',
    requires: [
               'Ext.data.proxy.Ajax',
               'Ext.data.reader.Array',
               'Company'
               ],
   proxy: {
     type: 'ajax',
     url: '../../performance/getGroups',
     reader: {
         type: 'array'
     }
 },
 autoLoad: true,
 listeners:{
   beforeload:function ( store, operation, eOpts ){
     var moId=Ext.avmon.currentMoId;
     if(!moId){
       moId = firstMoId;
     }
     store.proxy.extraParams.moId = moId;
   }
 }
});
function displayInnerGrid(renderId) {
  var fields;
  var dummyDataForInsideGrid;
  var columns;
  Ext.Ajax.request({
    url: '../../performance/getInnderGrid',
    async:false,
    params: {
        groupName: renderId
    },
    success: function(response){
        var json = Ext.JSON.decode(response.responseText);
        dummyDataForInsideGrid = json.data;
        fields = json.fieldsNames;
        columns = json.columModle;
        Ext.define('TestModel', {
            extend: 'Ext.data.Model',
            fields: fields
        });
        var insideGridStore = Ext.create('Ext.data.Store', {
            model: 'TestModel',
            data: dummyDataForInsideGrid
        });
        innerGrid = Ext.create('Ext.grid.Panel', {
            store: insideGridStore,
            layout: {
              type: 'border'
            },
            columns: columns,
            columnLines: true,
            loadMask : true,
            autoWidth: true,
            height:100,
            autoScroll:true,
            frame: false,
            renderTo: renderId
        });
        innerGrid.getEl().swallowEvent([
                    'mousedown', 'mouseup', 'click',
                    'contextmenu', 'mouseover', 'mouseout',
                    'dblclick', 'mousemove'
                ]);
    }
  });
}
function destroyInnerGrid(record) {
    var parent = document.getElementById(record.get('kpiType'));
    if(parent){
      var child = parent.firstChild;
      while (child) {
          child.parentNode.removeChild(child);
          child = child.nextSibling;
      }
    }else{
      return;
    }
}
Ext.define('MainGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.MainGrid',
    store: mainStore,
    autoScroll: true,
    id:'kpiListGrid',
    columns: [
            { text: avmon.dashboard.groupName, flex: 1, dataIndex: 'kpiType' }
        ],
    selModel: {
        selType: 'cellmodel'
    },
    plugins: [{
        ptype: 'rowexpander',
        rowBodyTpl: [
                '<div id="{kpiType}">',
                '</div>'
            ]
    }],
    dockedItems: [
        {
            xtype: 'toolbar',
            dock: 'top',
            width: 400,
            items: [
                {
                    xtype: 'button',
                    id: 'btnAutoRefresh',
                    enableToggle: true,
                    iconCls: 'icon-refresh',
                    pressed: true,
                    text: avmon.dashboard.aotoRefresh
                },
                {
                    xtype: 'button',
                    id: 'btnRefreshKpiList',
                    allowDepress: false,
                    iconCls: 'icon-grid',
                    text: avmon.dashboard.manualRefresh,
                    listeners: {
                        click: {
                            fn: this.onButtonClick1,
                            scope: this
                        }
                    }
                }
            ]
        }
    ],
    initComponent: function () {
        var me = this;
        this.callParent(arguments);
    }
});
function onButtonClick1(button,e,eOpts){
  Ext.getCmp('kpiListGrid').store.load();
}
Ext.onReady(function () {
    Ext.QuickTips.init();
    var mainGrid = new Ext.create('MainGrid');
    mainGrid.view.on('expandBody', function (rowNode, record, expandRow, eOpts) {
        var kpiType = record.get('kpiType');
        if(kpiType){
          displayInnerGrid(record.get('kpiType'));
        }else{
          return;
        }
    });
    mainGrid.view.on('collapsebody', function (rowNode, record, expandRow, eOpts) {
        destroyInnerGrid(record);
    });
    mainGrid.render(Ext.getBody());
    mainGrid.setHeight(window.innerHeight);
    mainGrid.setWidth(window.innerWidth);
    Ext.EventManager.onWindowResize(function () {
        mainGrid.setHeight(window.innerHeight);
        mainGrid.setWidth(window.innerWidth);
    });
     setInterval(refreshKpiListGrid,300000);
     function refreshKpiListGrid() {
        if(! Ext.getCmp("btnAutoRefresh").pressed){
            return;
        }
        if(isShow("kpiListGrid")){
            var moId=Ext.avmon.currentMoId;
            var grid=Ext.getCmp("kpiListGrid");
            grid.getStore().load({params:{mo:moId}
        });
    }
    else{
        //if(console){
        //    console.log("kpiListPanel is not visible");
        // }
    }
    }
});