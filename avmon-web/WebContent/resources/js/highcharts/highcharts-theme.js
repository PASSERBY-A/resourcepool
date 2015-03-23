 /**
	  * Dark blue theme for Highcharts JS
	  * @author Torstein Honsi
	  */

	 Highcharts.theme = {
	 	colors: ["#54697E", "#FFA500", "#DF5353", "#7798BF", "#aaeeee", "#ff0066", "#eeaaee",
	 		"#55BF3B", "#DF5353", "#7798BF", "#aaeeee"],
	 	chart: {
	 		backgroundColor: 'rgba(255, 255, 255, 0)',
            plotBorderColor : null,
            plotBackgroundColor: null,
            plotBackgroundImage:null,
            plotBorderWidth: null,
            plotShadow: false,  
            
            
	 	},
	 	title: {
	 		style: {
	 			color: '#FFFFFF',
	 			font: 'bold 16px "Trebuchet MS", Verdana, sans-serif'
	 		}
	 	},
	 	subtitle: {
	 		style: {
	 			color: '#FFFFFF',
	 			font: 'bold 12px "Trebuchet MS", Verdana, sans-serif'
	 		}
	 	},
	 	
	 	
	 	
	 	
 
	 };

	 // Apply the theme
	 var highchartsOptions = Highcharts.setOptions(Highcharts.theme);
	 