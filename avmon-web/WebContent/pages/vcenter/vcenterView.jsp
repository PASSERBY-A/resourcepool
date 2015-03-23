<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<!DOCTYPE html>

<head>
<meta charset="utf-8">
<title>Vcenter视图</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta name="format-detection" content="telephone=no" />
<meta name="apple-mobile-web-app-capable" content="yes" />
	
<!-- jqwidgets -->

<script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxscrollbar.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/jquery.easy-pie-chart.js"></script>
<%-- <script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqx-all.js"></script> --%>
<style type="text/css">
/*update 20140918*/

#jqxProgressBar1 .jqx-fill-state-pressed { 
background-color:#37bc29 !important; 
} 
#jqxProgressBar2 .jqx-fill-state-pressed { 
background-color:#0077dc !important; 
} 
#jqxProgressBar3 .jqx-fill-state-pressed { 
background-color:#eea025 !important; 
} 
#jqxProgressBar4 .jqx-fill-state-pressed { 
background-color:#e16725 !important; 
} 
#jqxProgressBar5 .jqx-fill-state-pressed { 
background-color:#e43e1e !important; 
} 

#jqxSystem .jqx-icon-arrow-down, #jqxSystem .jqx-icon-arrow-down-hover, #jqxSystem .jqx-icon-arrow-down-selected,
#jqxLocation .jqx-icon-arrow-down, #jqxLocation .jqx-icon-arrow-down-hover, #jqxLocation .jqx-icon-arrow-down-selected{
background-image: url('${ctx}/resources/images/icon-down-white.png');
background-repeat: no-repeat;
background-position: center;
}

#jqxSystem .jqx-icon-arrow-down, #jqxSystem .jqx-icon-arrow-down-hover, #jqxSystem .jqx-icon-arrow-down-selected,
#jqxLocation .jqx-icon-arrow-down, #jqxLocation .jqx-icon-arrow-down-hover, #jqxLocation .jqx-icon-arrow-down-selected{
background-image: url('images/icon-down-white.png');
background-repeat: no-repeat;
background-position: center;
}

.jqx-widget-header {
color:#FFFFFF;
background: #0096d6;
}

.jqx-grid-column-header, .jqx-grid-pager{
	color:#000000;	
	background: #E8F8FE !important;}

.jqx-tabs-title-selected-top {
color:#0096d6;
}

.jqx-progressbar-value{
background: #0b97d4 !important;
border-color: #0b97d4 !important;
	}	

.jqx-grid-column-menubutton{
background-color: #E8F8FE !important;
}

#jqxSystem .jqx-icon-arrow-down, #jqxSystem .jqx-icon-arrow-down-hover, #jqxSystem .jqx-icon-arrow-down-selected,
#jqxLocation .jqx-icon-arrow-down, #jqxLocation .jqx-icon-arrow-down-hover, #jqxLocation .jqx-icon-arrow-down-selected{
background-image: url('images/icon-down-white.png');
background-repeat: no-repeat;
background-position: center;
}

.jqx-widget-header {
color:#FFFFFF;
background: #0096d6;
}

.jqx-grid-column-header, .jqx-grid-pager{
	color:#000000;	
	background: #E8F8FE !important;}

.jqx-tabs-title-selected-top {
color:#0096d6;
}

.jqx-progressbar-value{
background: #0b97d4 !important;
border-color: #0b97d4 !important;
	}	

.jqx-grid-column-menubutton{
background-color: #E8F8FE !important;
}

#jqxVerticalProgressBar1 .jqx-fill-state-pressed {
background: #c8d8a5 !important;
} 


#jqxVerticalProgressBar2 .jqx-fill-state-pressed {
background: #7fbb00 !important;
}	

.jqx-tabs-titleContentWrapper{
margin-top: 0px !important;
}
.bx-wrapper .bx-viewport {
    border: 2px solid #fff;
}
</style>
</head>

<body>

<div id="vmViewList">
<div class="left W20" style="margin-right:2px;">
  <div id='jqxExpander'>
    <div> Menu </div>
    <div style="overflow: hidden;">
      <div style="border: none;" id='jqxTree'> </div>
    </div>
  </div>
</div>
<div class="left W79">
  <div id='jqxTabs'>
    <ul>
      <li id="jqxtab1">物理机</li>
      <li id="jqxtab2">虚拟机</li>
      <li id="jqxtab3">数据储存</li>
      <li id="jqxtab4" style="display:none">性能视图</li>
      <li id="jqxtab5" style="display:none">KPI视图</li>
      <li id="jqxtab6" style="display:none">KPI趋势视图</li>
      <li id="jqxtab7" style="display:none">告警视图</li>
    </ul>
    <!--//物理机1-->
    <div  style="margin:10px;" id="jqxgrid11">
      <div id="jqxgrid1"> </div>
    </div>
    
    <!-- 虚拟机2-->
    <div style="margin:10px;" id="jqxgrid22">
      <div id="jqxgrid2"> </div>
    </div>
    
    <!-- 数据存储3-->
    <div  style="margin:10px;" id="jqxgrid33">
      <div id="jqxgrid3"> </div>
    </div>
    
    <!-- 性能视图4-->
    <div id="jqxgrid44" style="display:none"></div>
    
    <!-- KPI视图5-->
    <div  style="display:none" id="jqxgrid55"></div>
       
        <!-- KPI趋势视图6-->
    <div id="jqxgrid66"  style="display:none"></div>
    
    <!-- 告警视图7-->
    <div  style="display:none" id="jqxgrid77"></div>
       
  </div>
</div>
</div>

 
<script type="text/javascript">
//grid localization
var getLocalization = function() {
    var localizationobj = {};
    localizationobj.pagergotopagestring = "当前页:";
    localizationobj.pagershowrowsstring = "每页显示:";
    localizationobj.pagerrangestring = " 总共 ";
    localizationobj.pagernextbuttonstring = "后页";
    localizationobj.pagerpreviousbuttonstring = "前页";
    localizationobj.sortascendingstring = "正序";
    localizationobj.sortdescendingstring = "倒序";
    localizationobj.sortremovestring = "清除排序";
    localizationobj.firstDay = 1;
    localizationobj.percentsymbol = "%";
    localizationobj.currencysymbol = "￥";
    localizationobj.currencysymbolposition = "before";
    localizationobj.decimalseparator = ".";
    localizationobj.thousandsseparator = ",";
    localizationobj.emptydatastring = "暂没有数据";

    return localizationobj;
};

        $(document).ready(function () {
			
			//物理机tab
            // prepare the data
            var grid1source =
            {
            	datatype: "json",
                datafields: [
                    {
			            name: 'name',type:'string'
			        },
			        {
			            name: 'powerStatus',type:'string'
			        },
			        {
			            name: 'vmlist',type:'string'
			        },
			        {
			            name: 'cpu',type:'string'
			        },
			        {
			            name: 'mem',type:'string'
			        },
			        {
			            name: 'memPer',type:'string'
			        }
                ],
				root:'items',
				autoLoad:false,
                sort: function() {
                    $("#jqxgrid1").jqxGrid('updatebounddata');
                },
                async: false,
                cache: false,
                type:'POST',
                beforeprocessing: function(data) {
                	grid1source.totalrecords = data.totalRows;
                }, 
                id: 'name'//,
                //url: '${ctx}/vcenter/getPhysicalMachineList?datacenterId=&agentId='
            };

            var dataAdapter1 = new $.jqx.dataAdapter(grid1source, {
                loadComplete: function(records) {}
            });
            // initialize jqxGrid
            $("#jqxgrid1").jqxGrid({
            	width: '99%',   
				height: '95%',
		        source: dataAdapter1,
		        localization: getLocalization(),
		        //columnsresize: true,
		        pageable: true,
		        pagesize: 20,
		        //filterable: true,
		        sortable: true,
		        //page
		        virtualmode: true,
		        //selectionmode : 'checkbox',
		        altrows: true,
		        enabletooltips: true,
		        rendergridrows: function() {
                    return dataAdapter1.records;
                },
                columns: [
                  { text: '物理机名称', datafield: 'name', width: '15%' },
                  { text: '状态', datafield: 'powerStatus', cellsalign: 'left', align: 'left', width: '10%' },
                  { text: '虚拟机列表', datafield: 'vmlist', align: 'left', cellsalign: 'left', cellsformat: 'c2', width: '15%' },
                  { text: 'CPU使用率（%）', datafield: 'cpu', cellsalign: 'left',  align: 'left', width: '20%' },
                  { text: '内存大小（M）', datafield: 'mem', cellsalign: 'left', align: 'left', width: '20%' },
				  { text: '内存使用率（%）', datafield: 'memPer', cellsalign: 'left', align: 'left', width: '20%' },                       
                ]
            });
 // initialize jqxGrid
 
 		//虚拟机tab
 		var grid2source =
            {
            	datatype: "json",
                datafields: [
                    {
			            name: 'name',type:'string'
			        },
			        {
			            name: 'status',type:'string'
			        },
			        {
			            name: 'host',type:'string'
			        },
			        {
			            name: 'usedSpace',type:'string'
			        },
			        {
			            name: 'hostFre',type:'string'
			        },
			        {
			            name: 'hostMem',type:'string'
			        },
			        {
			            name: 'clientMemUsage',type:'string'
			        },
			        {
			            name: 'mem',type:'string'
			        },
			        {
			            name: 'uuid',type:'string'
			        }
                ],
				root:'items',
                sort: function() {
                    $("#jqxgrid2").jqxGrid('updatebounddata');
                },
                async: false,
                cache: false,
                type:'POST',
                beforeprocessing: function(data) {
                	grid2source.totalrecords = data.total;
                }, 
                id: 'name'//,
                //url: '${ctx}/vcenter/getVirtualMachineList?hostId=&agentId='
            };

            var dataAdapter2 = new $.jqx.dataAdapter(grid2source, {
                loadComplete: function(records) {} 
            });
 
            $("#jqxgrid2").jqxGrid(
            {
                width: '99%',
				height: '95%',
                source: dataAdapter2, 
                localization: getLocalization(),
		        //columnsresize: true,
		        pageable: true,
		        pagesize: 20,
		        //filterable: true,
		        sortable: true,
		        //page
		        virtualmode: true,
		        //selectionmode : 'checkbox',
		        altrows: true,
		        enabletooltips: true,
		        rendergridrows: function() {
                    return dataAdapter2.records;
                },
                columns: [
                  { text: '虚拟机名称', datafield: 'name', width: '20%' },
                  { text: '状态', datafield: 'status', cellsalign: 'left', align: 'left', width: '10%' },
                  { text: '主机', datafield: 'host', align: 'left', cellsalign: 'left', cellsformat: 'c2', width: '10%' },
                  { text: '已用空间', datafield: 'usedSpace', cellsalign: 'left',  align: 'left', width: '10%' },
                  { text: '主机空闲率', datafield: 'hostFre', cellsalign: 'left', align: 'left', width: '10%' },
				  { text: '主机内存使用率', datafield: 'hostMem', cellsalign: 'left', align: 'left', width: '10%' },  
                  { text: 'clientMemUsage', datafield: 'clientMemUsage', cellsalign: 'left', align: 'left', width: '10%' },
				  { text: 'mem', datafield: 'mem', cellsalign: 'left', align: 'left', width: '10%' },  
				  { text: 'uuid', datafield: 'uuid', cellsalign: 'left', align: 'left', width: '10%' }
                ]
            });
			
			 // initialize jqxGrid
			//数据存储tab
			var grid3source =
            {
            	datatype: "json",
                datafields: [
                    {
			            name: 'name',type:'string'
			        },
			        {
			            name: 'vmlist',type:'string'
			        },
			        {
			            name: 'diskType',type:'string'
			        },
			        {
			            name: 'size',type:'string'
			        },
			        {
			            name: 'freeSpace',type:'string'
			        },
			        {
			            name: 'type',type:'string'
			        }
                ],
				root:'items',
                sort: function() {
                    $("#jqxgrid3").jqxGrid('updatebounddata');
                },
                async: false,
                cache: false,
                type:'POST',
                beforeprocessing: function(data) {
                	grid3source.totalrecords = data.total;
                }, 
                id: 'name'//,
                //url: '${ctx}/vcenter/getStorageList?datacenterId=&agentId='
            };
 
            var dataAdapter3 = new $.jqx.dataAdapter(grid3source, {
                loadComplete: function(records) {}
            }); 
			 
			 
            $("#jqxgrid3").jqxGrid(
            {
                width: '99%',
				height: '95%',
                source: dataAdapter3, 
                localization: getLocalization(),
		        //columnsresize: true,
		        pageable: true,
		        pagesize: 20,
		        //filterable: true,
		        sortable: true,
		        //page
		        virtualmode: true,
		        //selectionmode : 'checkbox',
		        altrows: true,
		        enabletooltips: true,
		        rendergridrows: function() {
                    return dataAdapter3.records;
                },
                columns: [
                  { text: '名称', datafield: 'name', width: '15%' },
                  { text: '设备', datafield: 'vmlist', cellsalign: 'left', align: 'left', width: '10%' },
                  { text: '驱动器类型', datafield: 'diskType', align: 'left', cellsalign: 'left', cellsformat: 'c2', width: '15%' },
                  { text: '容量', datafield: 'size', cellsalign: 'left',  align: 'left', width: '20%' },
                  { text: '可用空间', datafield: 'freeSpace', cellsalign: 'left', align: 'left', width: '20%' },
				  { text: '类型', datafield: 'type', cellsalign: 'left', align: 'left', width: '20%' },                       
                ]
            });		
			
            //创建左边树
            // Create jqxExpander
            //$('#jqxExpander').jqxExpander({ showArrow: false, toggleMode: 'none', width: '100%', height: '69%'});
            $('#jqxExpander').jqxExpander({ showArrow: false, toggleMode: 'none', width: '100%', height: '600px'});
            // Create jqxTree
            var source = null;
            $.ajax({
                async: false,
                url: "${ctx}/vcenter/vmTree",
                success: function (data, status, xhr) {
                    source = jQuery.parseJSON(data);
                }
            });
            var tree = $('#jqxTree');
            tree.jqxTree({ source: source, width: '100%', height: '100%'});
            
            tree.jqxTree('selectItem', null);
            var firstNode = $("#jqxTree").find('li:first')[0];
            tree.jqxTree('selectItem', firstNode);
            selectTreeNode(tree,firstNode);
            tree.on('expand', function (event) {
            	var selectElement = tree.jqxTree('getItem', event.args.element);
                var label = tree.jqxTree('getItem', event.args.element).label;
                var $element = $(event.args.element);
                var loader = false;
                var loaderItem = null;
                var children = $element.find('ul:first').children();
                $.each(children, function () {
                    var item = tree.jqxTree('getItem', this);
                    if (item && item.label == 'Loading...') {
                        loaderItem = item;
                        loader = true;
                        return false
                    };
                });
                if (loader) {
                    $.ajax({
                        url: loaderItem.value,
                        type: "POST",
                        data: "id="+selectElement.id,
                        success: function (data, status, xhr) {
                            var items = jQuery.parseJSON(data);
                            tree.jqxTree('addTo', items, $element[0]);
                            tree.jqxTree('removeItem', loaderItem.element);
                        }
                    });
                }
            });
            
            function selectTreeNode(tree,element){
            	var item = tree.jqxTree('getItem', element);
        		var label = item.label; 
        		var itemId = item.id;
        		//alert(itemId)
        		if(itemId.indexOf("$")>-1){
        			$("#jqxtab1").css("display", "block");
        			$("#jqxtab2").css("display", "block");
        			$("#jqxtab3").css("display", "block");
        			$("#jqxgrid1").css("display", "block");
        			$("#jqxgrid2").css("display", "block");
        			$("#jqxgrid3").css("display", "block");
        			$("#jqxgrid11").css("display", "block");
        			$("#jqxgrid22").css("display", "block");
        			$("#jqxgrid33").css("display", "block");
        			

        			$("#jqxtab4").css("display", "none");
        			$("#jqxgrid44").css("display", "none");
        			$("#jqxtab5").css("display", "none");
        			$("#jqxgrid55").css("display", "none");
        			
        			$("#jqxtab1").click();
        			var idsArr = itemId.split("$");
        			grid1source.url = '${ctx}/vcenter/getPhysicalMachineList?datacenterId=&agentId='+idsArr[1];
        			$("#jqxgrid1").jqxGrid('updatebounddata');
        			grid2source.url = '${ctx}/vcenter/getVirtualMachineList?hostId=&agentId='+idsArr[1];
        			$("#jqxgrid2").jqxGrid('updatebounddata');
        			grid3source.url = '${ctx}/vcenter/getStorageList?datacenterId=&agentId='+idsArr[1];
        			$("#jqxgrid3").jqxGrid('updatebounddata');
        		}else if(itemId.indexOf("#")>-1){//data center
        			$("#jqxtab1").css("display", "block");
        			$("#jqxtab2").css("display", "block");
        			$("#jqxtab3").css("display", "block");
        			$("#jqxgrid1").css("display", "block");
        			$("#jqxgrid2").css("display", "block");
        			$("#jqxgrid3").css("display", "block");
        			$("#jqxgrid11").css("display", "block");
        			$("#jqxgrid22").css("display", "block");
        			$("#jqxgrid33").css("display", "block");
        			$("#jqxtab4").css("display", "none");
        			$("#jqxgrid44").css("display", "none");
        			$("#jqxtab5").css("display", "none");
        			$("#jqxgrid55").css("display", "none");
        			$("#jqxtab1").click();
        			var idsArr = itemId.split("#");
        			grid1source.url = '${ctx}/vcenter/getPhysicalMachineList?datacenterId='+idsArr[0]+'&agentId='+idsArr[2];
        			$("#jqxgrid1").jqxGrid('updatebounddata');
        			grid2source.url = '${ctx}/vcenter/getVirtualMachineList?datacenterId='+idsArr[0]+'&agentId='+idsArr[2];
        			$("#jqxgrid2").jqxGrid('updatebounddata');
        			grid3source.url = '${ctx}/vcenter/getStorageList?datacenterId='+idsArr[0]+'&agentId='+idsArr[2];
        			$("#jqxgrid3").jqxGrid('updatebounddata');
        		}else if(itemId.indexOf("@")>-1){//物理主机
        			$("#jqxtab1").css("display", "none");
        			$("#jqxgrid1").css("display", "none");
        			$("#jqxgrid11").css("display", "none");
        			$("#jqxtab3").css("display", "none");
        			$("#jqxgrid3").css("display", "none");
        			$("#jqxgrid33").css("display", "none");
        			
        			$("#jqxtab2").css("display", "block");
        			$("#jqxgrid2").css("display", "block");
        			$("#jqxgrid22").css("display", "block");
        			
        			$("#jqxtab4").css("display", "block");
        			$("#jqxgrid44").css("display", "block");
        			$("#jqxtab5").css("display", "block");
        			$("#jqxgrid55").css("display", "block");
        			$("#jqxtab6").css("display", "block");
        			$("#jqxgrid66").css("display", "block");
        			$("#jqxtab7").css("display", "block");
        			$("#jqxgrid77").css("display", "block");
        			$("#jqxtab2").click();
        			
        			var idsArr = itemId.split("@");
        			grid2source.url = '${ctx}/vcenter/getVirtualMachineList?hostId='+idsArr[0]+'&agentId='+idsArr[2];
        			$("#jqxgrid2").jqxGrid('updatebounddata');
        			var hostMoId=idsArr[1];
        			if(hostMoId.length>0){
        				//hostMoId="Ezd2Gl9Os7VAe3Hm0Pt8WBf4IDh6Kp3";//需要注释
        				
        				$('#jqxgrid44').load('../pages/vcenter/hostDashboard.jsp?moId='+hostMoId);//加载物理主机dashboard
        				$('#jqxgrid55').load('../pages/vcenter/kpiView.jsp?moId='+hostMoId);//加载kpi列表

        				$('#jqxgrid66').load('../pages/vcenter/kpiTrend.jsp?moId='+hostMoId);//加载KPI趋势图
        				$('#jqxgrid77').load('../pages/vcenter/kpiAlarm.jsp?moId='+hostMoId);//加载告警列表
        			}
        		}else {//虚拟机
        			var idsArr = itemId.split("/");
        			$("#jqxtab1").css("display", "none");
        			$("#jqxtab2").css("display", "none");
        			$("#jqxtab3").css("display", "none");
        			$("#jqxgrid1").css("display", "none");
        			$("#jqxgrid2").css("display", "none");
        			$("#jqxgrid3").css("display", "none");
        			$("#jqxgrid11").css("display", "none");
        			$("#jqxgrid22").css("display", "none");
        			$("#jqxgrid33").css("display", "none");
        			$("#jqxtab4").css("display", "block");
        			$("#jqxgrid44").css("display", "block");
        			$("#jqxtab5").css("display", "block");
        			$("#jqxgrid55").css("display", "block");
        			$("#jqxtab6").css("display", "block");
        			$("#jqxgrid66").css("display", "block");
        			$("#jqxtab7").css("display", "block");
        			$("#jqxgrid77").css("display", "block");
					$("#jqxtab4").click();
        			var vmMoId=idsArr[1];
        			if(vmMoId.length>0){
        				$('#jqxgrid44').load('../pages/vcenter/vcenterDashboard.jsp?moId='+vmMoId);//加载虚机dashboard
        				$('#jqxgrid55').load('../pages/vcenter/kpiView.jsp?moId='+vmMoId);//加载kpi列表
        				
        				$('#jqxgrid66').load('../pages/vcenter/kpiTrend.jsp?moId='+vmMoId);//加载KPI趋势图
        				$('#jqxgrid77').load('../pages/vcenter/kpiAlarm.jsp?moId='+vmMoId);//加载告警列表
        			}
        		}
            }      
            tree.on('select',function (event)//当选中vcenter树节点时右边的列表需要刷新数据
        	{
            	var args = event.args;
            	selectTreeNode(tree,args.element);
        	});
        });
    </script> 

<script type="text/javascript">
        $(document).ready(function () {
            // Create jqxTabs.
            $('#jqxTabs').jqxTabs({ width: '100%', height: '600px', position: 'top'});
            $('#settings div').css('margin-top', '10px');
            //$('#animation').jqxCheckBox({ theme: theme });
            //$('#contentAnimation').jqxCheckBox({ theme: theme });

            $('#animation').on('change', function (event) {
                var checked = event.args.checked;
                $('#jqxTabs').jqxTabs({ selectionTracker: checked });
            });
           
            $('#contentAnimation').on('change', function (event) {
                var checked = event.args.checked;
                if (checked) {
                    $('#jqxTabs').jqxTabs({ animationType: 'fade' });
                }
                else {
                    $('#jqxTabs').jqxTabs({ animationType: 'none' });
                }
            });
            
        });
    </script>
</body>
</html>
