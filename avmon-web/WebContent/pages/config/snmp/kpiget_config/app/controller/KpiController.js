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
//    	var kpiGrid = this.getKpiGrid();
//    	var selection = kpiGrid.getSelectionModel().getSelection();
//    	
//    	var kpis = []; 
//	    Ext.each(selection,function(item){ 
//	       kpis.push(item.data.kpi); 
//	    });
//	    
//	    alert(kpis.join(','));
//	    
//	    
//	    
//	    var storeFields = ["name"];
//
//		var axesFields = [];
//		
//		var series = [];
//		
//		for (var i = 0; i < kpis.length; i++) {
//		
//			storeFields.push("s" + i);
//		
//			axesFields.push("s" + i);
//		
//			series.push({
//                type: 'line',
//                highlight: {
//                    size: 7,
//                    radius: 7
//                },
//                axis: 'left',
//                xField: 'name',
//                yField: "s" + i,
//                markerConfig: {
//                    type: 'cross',
//                    size: 4,
//                    radius: 4,
//                    'stroke-width': 0
//                }
//                ,tips: {  
//					trackMouse: true,  
//					width: 140,  
//					height: 28,  
//					renderer: function(storeItem, item) {  
//						this.setTitle(storeItem.get('name') + ': ' + storeItem.get("s" + i) + '$' );  
//					}  
//				}
//            });
//		}
//		
//		var chart = Ext.getCmp("KpiHistoryChart_chart");
//		
//		alert(chart); 
//		//chart.getStore().fields = storeFields;
//		
//		chart.axes.items[0].fields = axesFields;
//		
//		chart.series = new Ext.util.MixedCollection(false, function(a) { return a.seriesId || (a.seriesId = Ext.id(null, "ext-chart-series-")); });
//		
//		if (series) {
//		
//		    chart.series.addAll(series);
//		
//		}
//		chart.redraw();
    }
});
