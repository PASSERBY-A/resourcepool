function showPie(capacityObj)
{
	
	if(capacityObj)
	{
		
		percentused1 = capacityObj[1]==null?0.00:capacityObj[1].percentused;
		percentused0 = capacityObj[0]==null?0.00:capacityObj[0].percentused;
		percentused2 = capacityObj[2]==null?0.00:capacityObj[2].percentused;
		percentused6 = capacityObj[6]==null?0.00:capacityObj[6].percentused;
		
		$('#tu1').highcharts({
	        chart: {

	        },
	        credits: {
            	text: ' ',
            	href: '#'
	        },
	        title: {
	            text: 'CPU',
	        },
	        tooltip: {
	    	    pointFormat: '<b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: false,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: false,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: null, 
	            data: [
	                {
	                    name: '未使用:',
	                    y: Math.round(100.00-percentused1),
	                    sliced: false,
	                    selected: true
	                },
	                ['已使用:',   Math.round(percentused1)]
	            ]
	        }]
	    });

		
		$('#tu2').highcharts({
	        chart: {

	        },
	        credits: {
            	text: ' ',
            	href: '#'
	        },
	        title: {
	            text: '内存',
	        },
	        tooltip: {
	    	    pointFormat: '<b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: false,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: false,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: null,
	            data: [
	                {
	                    name: '未使用',
	                    y: Math.round(100.00-percentused0),
	                    sliced: false,
	                    selected: true
	                },
	                ['已使用',   Math.round(percentused0)]
	            ]
	        }]
	    });
		
		
		$('#tu3').highcharts({
	        chart: {

	        },
	        credits: {
            	text: ' ',
            	href: '#'
	        },
	        title: {
	            text: '存储',
	        },
	        tooltip: {
	    	    pointFormat: '<b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: false,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: false,
	                    color: '#000000',
	                    connectorColor: '#000000',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: null,
	            data: [
	                {
	                    name: '未使用',
	                    y: Math.round(100.00-percentused2),
	                    sliced: false,
	                    selected: true
	                },
	                ['已使用',   Math.round(percentused2)]
	            ]
	        }]
	    });
		
		
		
		$('#tu4').highcharts({
	        chart: {

	        },
	        credits: {
            	text: ' ',
            	href: '#'
	        },
	        title: {
	            text: '辅助存储',
	        },
	        tooltip: {
	    	    pointFormat: '<b>{point.percentage:.1f}%</b>'
	        },
	        plotOptions: {
	            pie: {
	                allowPointSelect: false,
	                cursor: 'pointer',
	                dataLabels: {
	                    enabled: false,
	                    color: '#BFF2FC',
	                    connectorColor: '#BFF2FC',
	                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
	                }
	            }
	        },
	        series: [{
	            type: 'pie',
	            name: null,
	            data: [
	                {
	                    name: '未使用',
	                    y: Math.round(100.00-percentused6),
	                    sliced: false,
	                    selected: true
	                },
	                ['已使用',   Math.round(percentused6)]
	            ]
	        }]
	    });
		
		
		
		
		
		
	}
	
	
	
	
	






}