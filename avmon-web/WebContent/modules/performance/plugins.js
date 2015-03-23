var performancePlugins=[
                        
{iconCls:'icon-monitor',id:'storageDashboard',title:avmon.dashboard.storageDashboard,url:'../pages/dashboard/store/index.jsp?a=1'},
{iconCls:'icon-chart',id:'totalView',title:avmon.dashboard.totalView,url:'../dashboard/totalview/index?a=1'},
{iconCls:'icon-monitor',id:'dashboardPanel',title:avmon.dashboard.hostDashboard,url:'../pages/dashboard/mainEngine/index.jsp?a=1'},//url:'../dashboard/host/index?a=1'},
{iconCls:'icon-grid',id:'deviceStatusPanel',title:avmon.dashboard.deviceStatusView,url:'../pages/dashboard/devicestatus?a=1'},
//{id:'hostListPanel',title:'主机列表',url:'../dashboard/kpilist/index?a=1'},
{iconCls:'icon-grid',id:'resourceListPanel',title:avmon.dashboard.resourceView,url:'../dashboard/resourcelist/index?a=1'},
{iconCls:'icon-monitor',id:'vmHost',title:avmon.dashboard.physicalEngineDashboard,url:'../pages/dashboard/physicalEngine/index.jsp?a=1'},
{iconCls:'icon-monitor',id:'iloHost',title:avmon.dashboard.iloDashboard,url:'../pages/dashboard/iloHost/index.jsp?a=1'},
{iconCls:'icon-monitor',id:'vmVHost',title:avmon.dashboard.vmDashboard,url:'../pages/dashboard/virtualMachine/index.jsp?a=1'},
{iconCls:'icon-bluebook',id:'kpiListPanel',title:avmon.dashboard.kpiView,url:'../dashboard/kpilist/index?a=1'},
{iconCls:'icon-trend',id:'kpiTrend',title:avmon.dashboard.kpiTrend,url:'../config/index?type=kpiHistory'},
//add by mark start
{iconCls:'icon-monitor',id:'alarmSearch',title:avmon.dashboard.alarmList,url:'../pages/dashboard/alarmSearch/index.jsp?a=1'},
{iconCls:'icon-monitor',id:'vmList',title:avmon.dashboard.vmList,url:'../pages/dashboard/vmList/index.jsp?a=1'},
{iconCls:'icon-grid',id:'pingStatusPanel',title:avmon.deploy.hostPingStatus,url:'../dashboard/pingStatus/index?a=1'}
//add by mark end
];


function getPerformancePlugin(itemId){
	var result=null;
	Ext.each(performancePlugins,function(item){
		if(item.id==itemId){
			result=item;
			return false;
		}
	});
	return result;
}

function loadPerformancePluginPanel(item){
  var agentId = "";
	if(item.newMoId.indexOf('&') > -1)
	{
		var agentId = item.newMoId.split('&')[2];
	}
  
  
  
	setTimeout(function(){
	    Ext.MessageBox.show({
	        msg: avmon.common.loadingWait,
	        progressText: 'Loading...',
	        width:300,
	        wait:true,
	        waitConfig: {interval:200},
	        icon:'icon-download'
	    });
	},500);
    setTimeout(function(){
	item.panel.update('<iframe id="iframe-'+item.id+'" scrolling="auto" frameborder="0" width="100%" height="100%" src="'+item.url+'&moId='+item.newMoId+'&mo='+item.newMoId+'&agentId='+agentId+'"></iframe>',
			false,function(){
				setTimeout(function(){
						Ext.MessageBox.hide();
						item.initFlag=1;
						item.currentMoId=item.newMoId;
					},600);
				  
					
				});
    },10);

}

function showPerformancePlugin(itemId){
	//console.log("show "+itemId);
	//console.log("showing "+itemId);
	//记录当前显示的模块，用于自动刷新时判断，只刷新当前显示的页面
	Ext.avmon.currentActiveModule="performance."+itemId;
	
	var showingItem=getPerformancePlugin(itemId);
//	//add by yuanpeng vmvare判断
	//刷新当前页面
	var tabs=Ext.getCmp('performance-tabPanel');
	if(!showingItem.panel){
		//console.log("create tab "+item.id);
		showingItem.panel=Ext.create('Ext.panel.Panel',
				{itemId:showingItem.id,
				html:avmon.common.loading+showingItem.title
			});
		//add to tabs
		tabs.add(showingItem.panel);
	}
	//bring panel to front
	tabs.getLayout().setActiveItem(showingItem.panel);
	tabs._activePageId=showingItem.id;

	//判断是否已经加载页面
	if(!showingItem.initFlag){
		//如果未加载，则加载页面
		//console.log("first load page "+showingItem.id);
		loadPerformancePluginPanel(showingItem);
	}
	else{
		//如果已加载则只刷新数据
		if(showingItem.newMoId!=showingItem.currentMoId){
			//console.log("reload page "+showingItem.id);
			//reloadModule();
			var fm=document.getElementById("iframe-"+showingItem.id);
			if(fm && fm.contentWindow.reloadModule){
				
				fm.contentWindow.reloadModule(showingItem.newMoId);
				showingItem.currentMoId=showingItem.newMoId;
			}
			else{
				loadPerformancePluginPanel(showingItem);
			}
		}
		else{
			//console.log("same moId, no load need");
			var fm=document.getElementById("iframe-"+showingItem.id);
			if(fm && fm.contentWindow.onShowPage){
				fm.contentWindow.onShowPage();
			}
		}
	}

}


function selectPerformanceTreeNode(moId){

	if(!isShow("performance")){
		if(Ext.avmon.subMenuClick){
			Ext.avmon.subMenuClick(null,{id:"mainFrame_labMenu2"});
		}
	}
	Ext.Ajax.request({
		url: '../performance/getMoPath',
		params:{
		    moId : moId
		},
		success:function(response){
		    var result = Ext.decode(response.responseText);
	
		    if(result.success == true || result.success == 'true'){
		        var path = result.path;
		    	var tree=Ext.getCmp("performance-menuTree");
		    	tree.expandPath(path, 'id', '/', function(bSuccess, oLastNode){
		    		if(bSuccess){
		    			Ext.getCmp("performance-menuTree").getSelectionModel().select(oLastNode,true);
		    		}
		    		else{
//		    			alert('未找到节点'+moId);
		    		}
		    	});
		    }
		},
		failure:function(response){
		},
		scope:this
	});
}
