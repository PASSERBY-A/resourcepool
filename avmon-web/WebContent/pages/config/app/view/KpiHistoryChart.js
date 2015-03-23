var chartStore = Ext.create('Ext.data.JsonStore', {
        fields: ['time'],
        autoLoad:false,
        proxy: {
		    type: 'ajax',
		    url : '',
		    reader: {
		        type: 'json'
		    }
		}
    }); 
Ext.define('CFG.view.KpiHistoryChart', {
    extend: 'Ext.chart.Chart',
    alias: 'widget.KpiHistoryChart',
    initComponent: function() {
        Ext.apply(this, {
            id: 'KpiHistoryChart_chart',
            border: false,
            region: 'center',
            xtype: 'chart',
            style: 'background:#fff',
            animate: true,
            store: chartStore,
            shadow: true,//细线
            theme: 'Category1',
            legend: {
                position: 'top'
            },
            axes: [{
                type: 'Numeric',
                minimum: 0,
                position: 'left',
                fields: [],
                title: avmon.kpiCompare.pkiValue,
                labelRotation: 0,
                minorTickSteps: 0,
                grid: {
                    odd: {
                        opacity: 1,
                        fill: '#ddd',
                        stroke: '#bbb',
                        'stroke-width': 0.5
                    }
                }
            }, {
                type: 'Category',
                position: 'bottom',
                fields: ['time'],
                title: avmon.kpiCompare.time
            }],
            series: []
        });
        this.callParent(arguments);
    }
});