Ext.require(['Ext.data.*']);

Ext.onReady(function() {

    window.generateData = function(n, floor){
    	var data = [
    	       	{name:"1",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"2",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"3",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"4",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"5",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"6",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"7",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"8",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"9",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"10",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"11",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"12",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"13",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"14",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"15",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"16",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"17",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"18",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"19",data1:0,data2:0,data3:0,data4:0,data5:0},
    	       	{name:"20",data1:0,data2:0,data3:0,data4:0,data5:0}
    	       	]; 
        return data;
    };
    
    window.generateDataNegative = function(n, floor){
        var data = [],
            p = (Math.random() *  11) + 1,
            i;
            
        floor = (!floor && floor !== 0)? 20 : floor;
            
        for (i = 0; i < (n || 12); i++) {
            data.push({
                name: Ext.Date.monthNames[i % 12],
                data1: Math.floor(((Math.random() - 0.5) * 100), floor),
                data2: Math.floor(((Math.random() - 0.5) * 100), floor),
                data3: Math.floor(((Math.random() - 0.5) * 100), floor),
                data4: Math.floor(((Math.random() - 0.5) * 100), floor),
                data5: Math.floor(((Math.random() - 0.5) * 100), floor),
                data6: Math.floor(((Math.random() - 0.5) * 100), floor),
                data7: Math.floor(((Math.random() - 0.5) * 100), floor),
                data8: Math.floor(((Math.random() - 0.5) * 100), floor),
                data9: Math.floor(((Math.random() - 0.5) * 100), floor)
            });
        }
        return data;
    };

    window.store1 = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateData()
    });
    window.storeNegatives = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateDataNegative()
    });
    window.store3 = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateData()
    });
    window.store4 = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateData()
    });
    window.store5 = Ext.create('Ext.data.JsonStore', {
        fields: ['name', 'data1', 'data2', 'data3', 'data4', 'data5', 'data6', 'data7', 'data9', 'data9'],
        data: generateData()
    });    
    
    
});

