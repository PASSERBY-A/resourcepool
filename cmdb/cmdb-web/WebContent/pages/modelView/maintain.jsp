<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <title id='Description'>This demo illustrates the basic functionality of the Grid plugin. The jQWidgets Grid plugin offers rich support for interacting with data, including paging, grouping and sorting. 
    </title>
    <link rel="stylesheet" href="js/jquery/jqwidgets/styles/jqx.base.css" type="text/css" />
    <link rel="stylesheet" href="js/jquery/jqwidgets/styles/jqx.energyblue.css" type="text/css" />
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
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxexpander.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxvalidator.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/globalization/globalize.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxcalendar.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxdatetimeinput.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxmaskedinput.js"></script> 
    <script type="text/javascript" src="js/jquery/jqwidgets/jqxinput.js"></script> 
    <script type="text/javascript">
        $(document).ready(function () {
            var theme = "energyblue";
			
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
                theme: "energyblue",
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
                ]
            //,
//                columngroups: [
//                    { text: '维修单详情', align: 'center', name: 'ProductDetails' }
//                ]
            });


           // $("#register").jqxExpander({ toggleMode: 'none', width: '300px', showArrow: false, theme: theme });
            $('#sendButton').jqxButton({ width: 60, height: 25, theme: theme });
            //$('#acceptInput').jqxCheckBox({ width: 130, theme: theme });
            $('#sendButton').on('click', function () {
                $('#testForm').jqxValidator('validate');
            });
            $("#ssnInput").jqxMaskedInput({ mask: '###-##-####', width: 150, height: 22, theme: theme });
            $("#phoneInput").jqxMaskedInput({ mask: '(###)###-####', width: 150, height: 22, theme: theme });
            //$("#zipInput").jqxMaskedInput({ mask: '#####-####', width: 150, height: 22, theme: theme });
            $('.text-input').jqxInput({ theme: theme });
            var date = new Date();
            date.setFullYear(2000, 0, 1);
            $('#birthInput').jqxDateTimeInput({ theme: theme, height: 22, value: $.jqx._jqxDateTimeInput.getDateTime(date) });
            // initialize validator.
            $('#testForm').jqxValidator({
             rules: [
                    { input: '#ticketNo', message: 'Username is required!', action: 'keyup, blur', rule: 'required' },
                    { input: '#ticketNo', message: 'Your username must be between 3 and 12 characters!', action: 'keyup, blur', rule: 'length=3,12' },
                    { input: '#realNameInput', message: 'Real Name is required!', action: 'keyup, blur', rule: 'required' },
                    { input: '#realNameInput', message: 'Your real name must contain only letters!', action: 'keyup', rule: 'notNumber' },
                    { input: '#realNameInput', message: 'Your real name must be between 3 and 12 characters!', action: 'keyup', rule: 'length=3,12' },
                    { input: '#birthInput', message: 'Your birth date must be between 1/1/1900 and 1/1/2012.', action: 'valuechanged', rule: function (input, commit) {
                        var date = $('#birthInput').jqxDateTimeInput('value');
                        var result = date.getFullYear() >= 1900 && date.getFullYear() <= 2012;
                        // call commit with false, when you are doing server validation and you want to display a validation error on this field. 
                        return result;
                    }
                    },
                    { input: '#passwordInput', message: 'Password is required!', action: 'keyup, blur', rule: 'required' },
                    { input: '#passwordInput', message: 'Your password must be between 4 and 12 characters!', action: 'keyup, blur', rule: 'length=4,12' },
                    { input: '#passwordConfirmInput', message: 'Password is required!', action: 'keyup, blur', rule: 'required' },
                    { input: '#passwordConfirmInput', message: 'Passwords doesn\'t match!', action: 'keyup, focus', rule: function (input, commit) {
                        // call commit with false, when you are doing server validation and you want to display a validation error on this field. 
                        if (input.val() === $('#passwordInput').val()) {
                            return true;
                        }
                        return false;
                    }
                    },
                    { input: '#emailInput', message: 'E-mail is required!', action: 'keyup, blur', rule: 'required' },
                    { input: '#emailInput', message: 'Invalid e-mail!', action: 'keyup', rule: 'email' },
                    { input: '#ssnInput', message: 'Invalid SSN!', action: 'valuechanged, blur', rule: 'ssn' },
                    { input: '#phoneInput', message: 'Invalid phone number!', action: 'valuechanged, blur', rule: 'phone' }
                    //{ input: '#zipInput', message: 'Invalid zip code!', action: 'valuechanged, blur', rule: 'zipCode' },
                    //{ input: '#acceptInput', message: 'You have to accept the terms', action: 'change', rule: 'required', position: 'right:0,0'}
                    ], theme: theme
            });


            
        });
    </script>
    
    
        <style type="text/css">
        .text-input
        {
            height: 21px;
            width: 150px;
        }
        .register-table
        {
            margin-top: 10px;
            margin-bottom: 10px;
        }
        .register-table td, 
        .register-table tr
        {
            margin: 0px;
            padding: 2px;
            border-spacing: 0px;
            border-collapse: collapse;
            font-family: Verdana;
            font-size: 12px;
        }
        h3 
        {
            display: inline-block;
            margin: 0px;
        }
    </style>
</head>
<body class='default'>
<div id="register">
        <div>
            <form id="testForm" action="./">
                <table class="register-table">
                    <tr>
                        <td>维修单号:</td>
                        <td><input type="text" id="ticketNo" class="text-input" /></td>
                        <td>损坏配件序列号:</td>
                        <td><input type="password" id="passwordInput" class="text-input" /></td>
                    </tr>
                    <tr>
                        <td>配件类型:</td>
                        <td><input type="password" id="passwordConfirmInput" class="text-input" /></td>
                        <td>更换配件序列号:</td>
                        <td><input type="text" id="realNameInput" class="text-input" /></td>
                    </tr>
                    <tr>
                        <td>维修日期:</td>
                        <td><div id="birthInput"></div></td>
                         <td>维修类型:</td>
                        <td><input type="text" id="emailInput" placeholder="someone@mail.com" class="text-input" /></td>
                    </tr>
                    <tr>
                        <td>MA合同号:</td>
                        <td><div id="ssnInput"></div></td>
                        <td>日期:</td>
                        <td><div id="phoneInput"></div></td>
                    </tr>
                    <tr>
                        <td colspan="2" style="text-align: center;"><input type="button" value="保存" id="sendButton" /></td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div id='jqxWidget' style="font-size: 13px; font-family: Verdana; float: left;">
        <div id="jqxgrid">
        </div>
     </div>
</body>
</html>