<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'>This demo illustrates the basic functionality of the Grid plugin. The jQWidgets Grid plugin offers rich support for interacting with data, including paging, grouping and sorting. 
    </title>
    <link rel="stylesheet" href="js/jquery/jqwidgets/styles/jqx.base.css" type="text/css" />
    <script type="text/javascript" src="js/jquery/scripts/jquery-1.10.2.min.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxcore.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxdata.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxbuttons.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxscrollbar.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxmenu.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxcheckbox.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxlistbox.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxdropdownlist.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.js"></script>
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.sort.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.pager.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.selection.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxgrid.edit.js"></script> 
    <script type="text/javascript" src="js/jquery/scripts/gettheme.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            var theme = "";

            var url = "../modelView/sampledata/products.xml";

            // prepare the data
            var source =
            {
                datatype: "xml",
                datafields: [
                    { name: 'ProductName', type: 'string' },
                    { name: 'QuantityPerUnit', type: 'int' },
                    { name: 'UnitPrice', type: 'float' },
                    { name: 'UnitsInStock', type: 'float' },
                    { name: 'Discontinued', type: 'bool' }
                ],
                root: "Products",
                record: "Product",
                id: 'ProductID',
                url: url
            };

            var cellsrenderer = function (row, columnfield, value, defaulthtml, columnproperties, rowdata) {
                if (value < 20) {
                    return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #ff0000;">' + value + '</span>';
                }
                else {
                    return '<span style="margin: 4px; float: ' + columnproperties.cellsalign + '; color: #008000;">' + value + '</span>';
                }
            }

            var dataAdapter = new $.jqx.dataAdapter(source, {
                downloadComplete: function (data, status, xhr) { },
                loadComplete: function (data) { },
                loadError: function (xhr, status, error) { }
            });

            // initialize jqxGrid
            $("#jqxgrid").jqxGrid(
            {
                width: 570,
                source: dataAdapter,
                theme: theme,
                pageable: true,
                autoheight: true,
                sortable: true,
                altrows: true,
                enabletooltips: true,
                editable: true,
                selectionmode: 'multiplecellsadvanced',
                columns: [
                  { text: '维修单号', columngroup: 'ProductDetails', datafield: 'ProductName', width: 150 },
                  { text: '损坏配件序列号', columngroup: 'ProductDetails', datafield: 'QuantityPerUnit', cellsalign: 'right', align: 'right', width: 100 },
                  { text: '更换配件序列号', columngroup: 'ProductDetails', datafield: 'UnitPrice', align: 'right', cellsalign: 'right', cellsformat: 'c2', width: 80 },
                  { text: '维修日期', datafield: 'UnitsInStock', cellsalign: 'right', cellsrenderer: cellsrenderer, width: 100 },
                  { text: '配件类型', columntype: 'checkbox', datafield: 'Discontinued' , width: 100}
                ],
                columngroups: [
                    { text: '维修单详情', align: 'center', name: 'ProductDetails' }
                ]
            });
        });
    </script>
</head>
<body class='default'>
    <div id='jqxWidget' style="font-size: 13px; font-family: Verdana; float: left;">
        <div id="jqxgrid">
        </div>
     </div>
</body>
</html>