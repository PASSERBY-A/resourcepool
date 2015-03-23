Ext.define('MyApp.controller.Main', {
    extend: 'Ext.app.Controller',
    refs: [
        {
            ref: 'basicInfo',
            selector: 'basicInfo'
        },
        {
            ref: 'newAlarmGrid',
            selector: 'newAlarmGrid'
        },
        {
            ref: 'keyProcessGrid',
            selector: 'keyProcessGrid'
        },
        {
            ref: 'powerPanel',
            selector: 'powerPanel'
        }
    ],
    onBtnRefreshClick: function(button, e, eOpts) {},
    onBtnTestClick: function(button, e, eOpts) {
        var series=Ext.getCmp("cpuChart").series;
        series.get(10).showInLegend=true;
        series.get(10).showAll();
    },
    onCpuListItemClick: function(dataview, record, item, index, e, eOpts) {
        var se=Ext.getCmp("cpuChart").series.get(index+1);
        if(dataview.getSelectionModel().isSelected(index)){
            se.showAll();
            se.showInLegend=true;
        }
        else{
            se.hideAll();
            se.showInLegend=false;
        }
        Ext.getCmp("cpuChart").refresh();
    },
    init: function(application) {
        this.control({
            "#btnRefresh": {
                click: this.onBtnRefreshClick
            },
            "#btnTest": {
                click: this.onBtnTestClick
            },
            "#cpuList": {
                itemclick: this.onCpuListItemClick
            }
        });
    }
});
