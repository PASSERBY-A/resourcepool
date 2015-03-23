Ext.ns('CFG.controller');

Ext.define('CFG.controller.KpiController',{ 
    extend: 'Ext.app.Controller', 
    refs:[
  		{ref: 'KpiGrid',selector: 'KpiGrid'}
  		,{ref: 'KpiHistoryChart',selector: 'KpiHistoryChart'}
  	],
    init : function(){ 
    	this.control({
            'KpiGrid': {
                select: this.onMenuClick,
                deselect: this.onMenuClick
            }
        });
    },
    onMenuClick: function(record,eOpts) {
    }
});
