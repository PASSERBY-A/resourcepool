<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'>This example shows how to create a Grid from JSON data.</title>
    <link rel="stylesheet" href="js/jquery/jqwidgets/styles/jqx.base.css" type="text/css" />
    <link rel="stylesheet" href="js/jquery/jqwidgets/styles/jqx.energyblue.css" type="text/css" />
    <script type="text/javascript" src="js/jquery/scripts/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.columnsresize.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxdata.js"></script>
        <script type="text/javascript" src="js/jquery/jqwidgets/jqxdata.export.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.export.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.sort.js"></script>
    <script type="text/javascript" src="js/jquery/scripts/gettheme.js"></script>
    
        <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.pager.js"></script> 
            <script type="text/javascript" src="js/jquery/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxdropdownlist.js"></script>
     <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.grouping.js"></script>

    
    <script type="text/javascript">
        $(document).ready(function () {
            var theme = "energyblue";
            var url = "getHostListJqxgrid";
            // prepare the data
            var source =
            {
                datatype: "json",
                datafields: [
                    { name: 'host', type: 'string' },
                    { name: 'att_1', type: 'string' },
                    { name: 'att_2', type: 'string' }
                ],
                id: 'host',
                url: url
            };
            var dataAdapter = new $.jqx.dataAdapter(source,
            		{
                formatData: function (data) {
                    data.host_startsWith = $("#searchField").val();
                    return data;
                }
            }
                    );
            $("#jqxgrid").jqxGrid(
            {
                width: 670,
                source: dataAdapter,
                theme: "energyblue",
                pageable: true,
                columnsresize: true,
                columns: [
                  { text: '主机名', datafield: 'host', width: 250 },
                  { text: '属性一', datafield: 'att_1', width: 250 },
                  { text: '属性一', datafield: 'att_2', width: 180 }
              ],
              showtoolbar: true,
              autoheight: true,
//              groupable: true,
 //             groups: ['host'],
              rendertoolbar: function (toolbar) {
                  var me = this;
                  var container = $("<div style='margin: 5px;'></div>");
                  var span = $("<span style='float: left; margin-top: 5px; margin-right: 4px;'>搜索主机名: </span>");
                  var input = $("<input class='jqx-input jqx-widget-content jqx-rc-all' id='searchField' type='text' style='height: 23px; float: left; width: 223px;' />");
                  var expertButton = $("<input type='button' value='Export to Excel' id='excelExport'  />");
                  toolbar.append(container);
                  container.append(span);
                  container.append(input);
                  container.append(expertButton);
                  if (theme != "") {
                      input.addClass('jqx-widget-content-' + theme);
                      input.addClass('jqx-rc-all-' + theme);
                  }
                  var oldVal = "";
                  input.on('keydown', function (event) {
                      if (input.val().length >= 2) {
                          if (me.timer) {
                              clearTimeout(me.timer);
                          }
                          if (oldVal != input.val()) {
                              alert(input.val());
                              me.timer = setTimeout(function () {
                                  $("#jqxgrid").jqxGrid('updatebounddata');
                              }, 1000);
                              oldVal = input.val();
                          }
                      }
                  });
              }
            });
            $('#jqxgrid').on('rowclick', function (event) 
            		{
            		    var args = event.args;
            		    var row = args.rowindex;
            		    alert("jqxGrid:" + row);
            		});


            $("#excelExport").click(function () {
                $("#jqxgrid").jqxGrid('exportdata', 'xls', 'jqxgrid');    
            });
            $("#excelExport").jqxButton({ theme: theme });
        });
    </script>
</head>
<body class='default'>
    <div id='jqxWidget' style="font-size: 13px; font-family: Verdana; float: left;">
        <div id="jqxgrid"></div>
    </div>
</body>
</html>