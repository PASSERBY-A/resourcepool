
//常规信息
function setBase(inf){
	Ext.getCmp('base_os').setValue(inf['os']);
	Ext.getCmp('base_vs').setValue(inf['version']);
	Ext.getCmp('base_sp').setValue(inf['sp']);
	Ext.getCmp('base_bs').setValue(inf['businessSystem']);
	Ext.getCmp('base_ps').setValue(inf['position']);
	Ext.getCmp('base_usage').setValue(inf['usage']);
	Ext.getCmp('base_owner').setValue(inf['owner']);
	Ext.getCmp('base_uc').setValue(inf['currentUserCount']);
	Ext.getCmp('base_ac').setValue(inf['currentAlarmCount']);
}
//电源与风扇
function setPF(pow,fan){
   if(pow=='good'){
    Ext.getCmp('power_img').setSrc('./imgs/pw_good.png');
	Ext.getCmp('power_inf').setText('电源状态：正常');
	}else{
    Ext.getCmp('power_img').setSrc('./imgs/pw_bad.png');
	Ext.getCmp('power_inf').setText('电源状态：故障'); 
   }
	if(fan=='good'){
    Ext.getCmp('fan_img').setSrc('./imgs/fan_good.png');
	Ext.getCmp('fan_inf').setText('风扇状态：正常');
	}else{
    Ext.getCmp('fan_img').setSrc('./imgs/fan_bad.png');
	Ext.getCmp('fan_inf').setText('风扇状态：故障'); 
   }
}

//内存占用

function setMemo(memo){
   
   Ext.getCmp('sys_memo_head').setText('物理内存: '+memo['sysAmount']);
   Ext.getCmp('use_memo_head').setText('核心内存: '+memo['memAmount']);
   Ext.getCmp('swap_memo_head').setText('虚拟内存: '+memo['swapAmount']);
   
   //比例条
   Ext.getCmp('sys_memo_pb').setText(memo['sysAmount']+'('+memo['sysUsage']+')');
   Ext.getCmp('use_memo_pb').setText(memo['memAmount']+'('+memo['userUsage']+')');
   Ext.getCmp('swap_memo_pb').setText(memo['swapAmount']+'('+memo['swapUsage']+')');

   var tmp=memo['sysUsage'];
   var tt = tmp;
   Ext.getCmp('sys_memo_pb').updateProgress(tt/100,memo['sysAmount']+'('+memo['sysUsage']+')');
   
   tmp=memo['userUsage'];
   tt = tmp;
   Ext.getCmp('use_memo_pb').updateProgress(tt/100,memo['memAmount']+'('+memo['userUsage']+')');
   
   tmp=memo['swapUsage'];
   tt = tmp;
   Ext.getCmp('swap_memo_pb').updateProgress(tt/100,memo['swapAmount']+'('+memo['swapUsage']+')');

}

//新告警信息
function loadNewAlarm(na){
    var new_alarm =  Ext.getCmp('newAlarm');
	//设置头部信息
	new_alarm.setTitle('最新告警('+na['total']+')');
	//定义model
    Ext.define('alarmModel', {
		extend: 'Ext.data.Model',
		fields: [
			{name: 'level', type: 'string'},
			{name: 'time',  type: 'string'},
			{name: 'content', type: 'string'}
		]
	});
   

   var newStore = Ext.create('Ext.data.Store', {
    model: 'alarmModel',
    data : na['rows'],
	proxy: {
        type: 'memory',
        reader: {
            type: 'json'
        }
    }
   });
   

   var cm = [
	{header:"级别", mapping:"level", dataIndex:"level"},
	{header:"时间", mapping:"time", dataIndex:"time"},
	{header:"内容", mapping:"content", dataIndex:"content"}
	];

    new_alarm.reconfigure(newStore, cm );
   //重新加载数据集
   newStore.load();
    
}

//关键进程
function loadKeyProcess(kp){
    var keypro =  Ext.getCmp('keyProcess');
	//设置头部信息
	keypro.setTitle('关键进程('+kp['total']+')');
	//定义model
    Ext.define('kpModel', {
    extend: 'Ext.data.Model',
    fields: [
        {name: 'name', type: 'string'},
        {name: 'cpuUsage',  type: 'string'},
        {name: 'memUsage', type: 'string'},
		{name: 'status', type: 'string'}
    ]
   });
   

   var newStore = Ext.create('Ext.data.Store', {
    model: 'kpModel',
    data : kp['rows'],
	proxy: {
        type: 'memory',
        reader: {
            type: 'json'
        }
    }
   });
   

   var cm = [
	{header:"进程", mapping:"name", dataIndex:"name"},
	{header:"CPU占用", mapping:"cpuUsage", dataIndex:"cpuUsage"},
	{header:"内存占用", mapping:"memUsage", dataIndex:"memUsage"},
	{header:"状态", mapping:"status", dataIndex:"status"}
	];
 
    keypro.reconfigure( newStore, cm );
    newStore.load();  
}

//硬盘
function loadDisk(disk){

   //类似于新告警
}

//网络接口tree grid
function loadNetPorts(){
}





//取得大json	
function getData(moId){
	
	//alert('start load data');
    Ext.Ajax.request({
          url: 'alldata',
          params:{mo:moId},
        	  
          success: function(response, opts) {
        	      //调用整个页面刷新数据方法  
                  refreshData(Ext.JSON.decode(response.responseText));
          },
          failure: function(response, opts) {
            
          }
      });
    //alert('load data done.');
}
	
//刷新页面数据	
function refreshData(jd){

    //setBase(jd['basicInfo']);
    //setPF(jd['power'],jd['fan']);
    //loadNewAlarm(jd['newAlarm']);
    //loadKeyProcess(jd['keyProcess']);
    //loadNetPorts();
    //setMemo(jd['mem']);
    setCpuIcons(jd['cpu']['rows']);

	drawCpuLine(jd['cpu']['rows']);
	
	//设置显示折线

	 var se = Ext.getCmp("cpc_line").series;
	 for(var i=0;i<se.length;i++){
		if(lineTags['ls'+i]){
			 se.get(i).showAll();
		}else{
			 se.get(i).hideAll();
		} 
	 }

}

//显示cpu图标，每排8个，自动换行
function setCpuIcons(cpuRows){
	var len = cpuRows.length;
	var els = new Array(len*2);
	var axesFields = [];
	var p=0;
	for(var i=0;i<len;i++){
		axesFields.push();
		 var cpi = new Object();
		 if(cpuRows[i]['status']=='good') {
			 cpi = {
	             xtype: 'label',
	             cls:"cpuGIcon",
	             x: (i%8)*64+10,
	             y: Math.floor(i/8)*80+10,
	             vx:p,
	             html:Math.round(cpuRows[i]['usage'])+'%',
	             listeners : {
	                 render : function() {//渲染后添加click事件
	                  Ext.fly(this.el).on("click",
	                    function(e, t) {
	                 	   toogleLine(Ext.getCmp(t.id).vx);
	                 	   setCIB(this,Ext.getCmp(t.id).vx);
	                    });
	                 }
	             }
	             
	         };
			 p++;
	     }else{
	    	 cpi = {
		             xtype: 'label',
		             cls:"cpuBIcon",
		             x: (i%8)*64+10,
		             y: Math.floor(i/8)*80+10
		           };
	     }
		
		 
		 
		 var cpt = 
         {
             xtype: 'label',
             height: 20,
             html: cpuRows[i]['name'],
             width: 50,
             x: (i%8)*64+20,
             y: Math.floor(i/8)*80+65
         };
		 els[i*2] = cpi;
		 els[i*2+1]=cpt;
	}
	 Ext.getCmp("myScrollPanel").add(els);

	
}


//cpu模块相关代码
var lineTags = {};
var bcolor = ['black','red','green','blue','yellow','black','red','green','blue','yellow'];

function drawCpuLine(data){
	
	//先将参数中传来的data转化成reData样式的数据）并赋值给reData即可
	  
	if(data.length>0){
		
		var cpcline = Ext.getCmp("cpc_line"); 
		
		
		var reData = [];
		var temp = '';
		for(var i=0;i<data[0]['history'].length;i++){
			temp = data[0]['history'][i]['time'].split(':');
			reData.push({'name':temp[1]});
		}
		
		//坐标值选项
		var axesFields=[];
		for (var i=0;i<data.length;i++){
		   if(data[i]['status']=='good'){
			  axesFields.push(data[i]['name']);
		   }
		}
		
	
		
		var p = 0;
		for (var i=0;i<data.length;i++){
		   if(data[i]['status']=='good'){
				var his = data[i]['history'];
				for(var j=0;j<his.length;j++){
					temp = his[j]['usage'];
					reData[j][axesFields[p]] = parseInt(temp);
				}
				p++;
	        }
		}
		
	   //动态设置坐标值选项
    	cpcline.axes.items[0].fields = axesFields;
	   
		//设置数据仓库值域
	
		var filds = [];
		for (var property in reData[0]){
			filds.push({'name':property});
		}
	
		var cpcStore = Ext.create('Ext.data.Store', {
			
			 data : reData,
			 fields: filds,
			 proxy: {
		      type: 'memory',
		      reader: {
		          type: 'json'
		      }
		  }
		 });
		//设置曲线表现
		var series = [];
		for(var i=0;i<axesFields.length;i++){
			series.push(
					{
						type: 'line',
						highlight:false,
						showInLegend:false,
						axis: 'left',
						smooth: false,
						xField: filds[0]['name'],
						yField: axesFields[i],
						style:{
							  stroke: bcolor[i]
					    },
						markerConfig: {
							type: 'circle',
							size: 1,
							radius: 1,
							'stroke-width': 0
						}
					}	
			);
			
			//设置曲线初始是否显示
			lineTags['ls'+i] = false;
		}
		cpcline.series.addAll(series);
	    cpcline.bindStore(cpcStore);
	}
	
}	

function toogleLine(i){
	var se = Ext.getCmp("cpc_line").series;
    if(tag = lineTags['ls'+i]){
        lineTags['ls'+i] = false;
        se.get(i).hideAll();
	}else{
	   lineTags['ls'+i] = true;
       se.get(i).showAll();
	}
}

function setCIB(obj,i){
	 if(lineTags['ls'+i]){
		 obj.addCls(bcolor[i]+"Bor");
	 }else{
		 obj.removeCls(bcolor[i]+"Bor");
	 }
}


