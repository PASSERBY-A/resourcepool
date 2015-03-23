<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
 <head> 
  <meta charset="utf-8" /> 
  <title>Vcenter Dashboard</title> 
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
  <meta name="format-detection" content="telephone=no" /> 
  <meta name="apple-mobile-web-app-capable" content="yes" /> 
  
 <script type="text/javascript" src="${ctx}/resources/js/jqwidgets/jqxgrid.columnsresize.js"></script>
 </head> 
 <body> 
 <input id="kpiViewMoId" style="display:none" value="<%=request.getParameter("moId")%>">
 <div> 
     <!--tab 2-->
     <div class="KPINav">
      <div class="left marginR15" id="autoRefresh"><a href="#"  class="Avmon-button"><b class="R-icon_4 Icon16" style="position:relative;top:3px;"></b>自动刷新</a></div>
       <div class="left" id="manuRefresh"><a href="#"  class="Avmon-button"><b class="R-icon_1 Icon16" style="position:relative;top:3px;"></b>手动刷新</a></div>
     </div>
     <div class="KPIlist">
        <div id="jqxgrid5">Loading</div>
     </div> 
     <!-- end -->
 </div>

<script type="text/javascript">
	$(document).ready(function() {
		var moId = $("#kpiViewMoId").val();
		//alert("kpiView" + moId);
		loadNestGrid(moId);
		// tab 2 creage jqxgrid
	    //loadNestGrid
		$("#autoRefresh").click(function() {
		    setInterval(loadNestGrid(moId), 1000);
		});
		$("#manuRefresh").click(function() {
		    loadNestGrid(moId);
		});
		function loadNestGrid(moId) {
		    $.getJSON('${ctx}/performance/getGroups?moId=' + moId,
		    function(data) {
		        showGroupGrid(data);
		
		    });
		    function showGroupGrid(gridData) {
		        var data = new Array();
		
		        for (var i = 0; i < gridData.length; i++) {
		            var row = {};
		            row["moId"] = gridData[i][0];
		            row["groupName"] = gridData[i][1];
		            data[i] = row;
		        }
		        var source = {
		            localdata: data,
		            datatype: "array",
		            datafields: [{
		                name: 'moId'
		            },
		            {
		                name: 'groupName'
		            }
		
		            ]
		        };
		
		        var nestedGrids = new Array();
		        var innerGridData;
		        var data1 = new Array();
		
		        // create nested grid.
		        var initrowdetails = function(index, parentElement, gridElement, record) {
		
		            $.getJSON('${ctx}/performance/getInnderGrid?groupName=' + record.groupName,
		            function(data) {
		                innerGridData = data;
		                var datafieldsArray = new Array();
		                var datafields = innerGridData.fieldsNames;
		                for (var i = 0; i < datafields.length; i++) {
		                    var obj = new Object();
		                    obj.name = datafields[i];
		                    obj.type = 'string';
		                    datafieldsArray[i] = obj;
		                }
		                var datacolumns = innerGridData.columModle;
		                data1 = innerGridData.data;
		                var grid = $($(parentElement).children()[0]);
		                nestedGrids[index] = grid;
		                var orderssource = {
		                    localdata: data1,
		                    datatype: "array",
		                    datafields: datafieldsArray,
		                    async: false
		                };
		                if (grid != null) {
		                    grid.jqxGrid({
		                        width: 1200,
		                        height: 200,
		                        columnsresize: true,
		                        source: orderssource,
		                        columns: datacolumns
		                    });
		                }
		            });
		        };
		        //end
		        // creage jqxgrid
		        $("#jqxgrid5").jqxGrid({
		            width: 1300,
		            height: 500,
		            source: source,
		            rowdetails: true,
		            rowsheight: 35,
		
		            initrowdetails: initrowdetails,
		            rowdetailstemplate: {
		                rowdetails: "<div id='grid' style='margin: 10px;'></div>",
		                rowdetailsheight: 220,
		                rowdetailshidden: true
		            },
		            columns: [{
		                text: 'Group Name',
		                datafield: 'groupName',
		                width: '100%'
		            }]
		        });
		    }
		}
		//tab 2 end============================================================================
			
	    
	});
</script>  
 </body>
</html>