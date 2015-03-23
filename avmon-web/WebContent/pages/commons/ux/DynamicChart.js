Ext.define('Ext.ux.DynamicChart', {
    extend: 'Ext.chart.Chart',
    alias: 'widget.DynamicChart',
    //自定义属性
    axesTitle:'',  
    axesFields:'',
    seriesFields:'',
    chartData:'',
    degress:0,
    categoryTitle:avmon.config.time,
    initComponent: function() {
        Ext.apply(this, {
            border: false,
            height: 250,
            insetPadding: 10,
            xtype: 'chart',
            style: 'background:#fff',
            animate: true,
            store: this.chartData,//chartStore,
            shadow: true,//细线
            theme: 'Category1',
            legend: {
                position: 'top'
            },
            axes: [
            	{
	                type: 'Numeric',
	                minimum: 0,
	                position: 'left',
	                fields: this.axesFields,
	                title: this.axesTitle,
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
	                title: this.categoryTitle,
	                label:{rotate:{degrees:this.degress}}
	            }
            ],
            series: this.seriesFields// [this.seriesFields]
        });
        this.callParent(arguments);
    }
});