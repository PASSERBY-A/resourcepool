Ext.define('MyApp.view.LineChart', {
    extend: 'Ext.chart.Chart',
    alias: 'widget.linechart',
	id: 'compare_kpi_chart',
    autoRender: false,
    autoShow: false,
    disabled: false,
    draggable: false,
    height: 288,
    hidden: false,
    width: 502,
    animate: true,
    insetPadding: 20,
    style: 'background:#fff',
    store: 'ChartStore',
	legend: {
        position: 'top'
    },
    
    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            axes: [
                {
                    type: 'Category',
                    fields: [
                        'x'
                    ],
                    position: 'bottom',
                    title: avmon.kpiCompare.time
                },
                {
                    type: 'Numeric',
                    fields: [
                        'Host1','Host2','Host3','Host4','Host5'
                    ],
                    position: 'left',
                    title: avmon.kpiCompare.pkiValue,
                    grid: {
	                    odd: {
	                        opacity: 1,
	                        fill: '#ddd',
	                        stroke: '#bbb',
	                        'stroke-width': 0.5
	                    }
	                }
                }
            ],
            series: [
                {
                    type: 'line',
                    xField: 'x',
                    yField: [
                        'Host1'
                    ],
                    fill: true,
                    smooth: 3
                    ,tips: {  
						trackMouse: true,  
						width: 200,  
						height: 50,  
						renderer: function(storeItem, item) {  
							this.setTitle(avmon.kpiCompare.timeTo+storeItem.get('x') + '<br>IP：' + nodeIds[0] + '<br>'+avmon.kpiCompare.pkiAverageValue + storeItem.get("Host1"));  
						}  
					}
                },
                {
                    type: 'line',
                    xField: 'x',
                    yField: [
                        'Host2'
                    ],
                    smooth: 3
                    ,tips: {  
						trackMouse: true,  
						width: 200,  
						height: 50,  
						renderer: function(storeItem, item) {  
							this.setTitle(avmon.kpiCompare.timeTo+storeItem.get('x')+ '<br>IP：' + nodeIds[1] + '<br>' +avmon.kpiCompare.pkiAverageValue + storeItem.get("Host2"));  
						}  
					}
                },
                {
                    type: 'line',
                    xField: 'x',
                    yField: [
                        'Host3'
                    ],
                    smooth: 3
                    ,tips: {  
						trackMouse: true,  
						width: 200,  
						height: 50,  
						renderer: function(storeItem, item) {  
							this.setTitle(avmon.kpiCompare.timeTo+storeItem.get('x') + '<br>IP：' + nodeIds[2] + '<br>'+avmon.kpiCompare.pkiAverageValue + storeItem.get("Host3"));  
						}  
					}
                },
                {
                    type: 'line',
                    xField: 'x',
                    yField: [
                        'Host4'
                    ],
                    smooth: 3
                    ,tips: {  
						trackMouse: true,  
						width: 200,  
						height: 50,  
						renderer: function(storeItem, item) {  
							this.setTitle(avmon.kpiCompare.timeTo+storeItem.get('x') + '<br>IP：' + nodeIds[3] + '<br>'+avmon.kpiCompare.pkiAverageValue + storeItem.get("Host4"));  
						}  
					}
                },
                {
                    type: 'line',
                    xField: 'x',
                    yField: [
                        'Host5'
                    ],
                    smooth: 3
                    ,tips: {  
						trackMouse: true,  
						width: 200,  
						height: 50,  
						renderer: function(storeItem, item) {  
							this.setTitle(avmon.kpiCompare.timeTo+storeItem.get('x') + '<br>IP：' + nodeIds[4] + '<br>'+avmon.kpiCompare.pkiAverageValue + storeItem.get("Host5"));  
						}  
					}
                }
            ]
        });

        me.callParent(arguments);
    }

});