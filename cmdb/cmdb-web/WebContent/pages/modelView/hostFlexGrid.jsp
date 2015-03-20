<%@ page language="java" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<%
String nodeId=(String)request.getParameter("nodeId");
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Flexigrid</title>
<link rel="stylesheet" type="text/css" href="css/flexGrid/flexigrid.css">
<script type="text/javascript" src="js/jquery/jquery-1.9.0.min.js"></script>
<script type="text/javascript" src="js/jquery/flexGrid/flexigrid.js"></script>
</head>

<body onLoad="afterLoad();">

<div id="flex1" style="display:none"></div>
<script type="text/javascript">
$("#flex1").flexigrid({
	url: 'getHostList',
	dataType: 'json',
	colModel : [
		{display: '主机名', name : 'host_name', width : 280, sortable : true, align: 'left'},
		{display: '类属性1', name : 'att_1', width : 280, sortable : true, align: 'left'},
		{display: '类属性2', name : 'att_2', width : 300, sortable : true, align: 'left'}
		],
	searchitems : [
		{display: '主机名', name : 'host_name'},
		{display: '类属性1', name : 'att_1'},
		{display: '类属性2', name : 'att_1', isdefault: true}
		],
//		buttons : [ {
//            name : 'Search',
//            bclass : 'add',
//            onpress : Example4
 //           }
//        ],
	sortname: "host_name",
	sortorder: "asc",
	usepager: true,
	title: false,
	useRp: true,
	rp: 25,
	showTableToggleBtn: true,
	width: 650,
	onSubmit: addFormData,
	autoload: true, //自动加载，即第一次发起ajax请求
	height: 220
});

//This function adds paramaters to the post of flexigrid. You can add a verification as well by return to false if you don't want flexigrid to submit			
function addFormData(){
	//passing a form object to serializeArray will get the valid data from all the objects, but, if the you pass a non-form object, you have to specify the input elements that the data will come from
	var dt = $('#sform').serializeArray();
	$("#flex1").flexOptions({params: dt});
	return true;
}

function afterLoad(){
	//$('#flex1').flexReload();	
}


function Example4(com, grid) {
    if (com == 'Delete') {
        var conf = confirm('Delete ' + $('.trSelected', grid).length + ' items?')
        if(conf){
            $.each($('.trSelected', grid),
                function(key, value){
                    $.get('example4.php', { Delete: value.firstChild.innerText}
                        , function(){
                            // when ajax returns (callback), update the grid to refresh the data
                            $(".flexme4").flexReload();
                    });
            });    
        }
    }
    else if (com == 'Edit') {
        var conf = confirm('Edit ' + $('.trSelected', grid).length + ' items?')
        if(conf){
            $.each($('.trSelected', grid),
                function(key, value){
                    // collect the data
                    var OrgEmpID = value.children[0].innerText; // in case we're changing the key
                    var EmpID = prompt("Please enter the New Employee ID",value.children[0].innerText);
                    var Name = prompt("Please enter the Employee Name",value.children[1].innerText);
                    var PrimaryLanguage = prompt("Please enter the Employee's Primary Language",value.children[2].innerText);
                    var FavoriteColor = prompt("Please enter the Employee's Favorite Color",value.children[3].innerText);
                    var FavoriteAnimal = prompt("Please enter the Employee's Favorite Animal",value.children[4].innerText);

                    // call the ajax to save the data to the session
                    $.get('example4.php', 
                        { Edit: true
                            , OrgEmpID: OrgEmpID
                            , EmpID: EmpID
                            , Name: Name
                            , PrimaryLanguage: PrimaryLanguage
                            , FavoriteColor: FavoriteColor
                            , FavoritePet: FavoriteAnimal  }
                        , function(){
                            // when ajax returns (callback), update the grid to refresh the data
                            $(".flexme4").flexReload();
                    });
            });    
        }
    }
    else if (com == 'Search') {
        // collect the data
        var EmpID = prompt("Please enter the Employee ID","5");
        var Name = prompt("Please enter the Employee Name","Mark");
        var PrimaryLanguage = prompt("Please enter the Employee's Primary Language","php");
        var FavoriteColor = prompt("Please enter the Employee's Favorite Color","Tan");
        var FavoriteAnimal = prompt("Please enter the Employee's Favorite Animal","Dog");

        // call the ajax to save the data to the session

                // when ajax returns (callback), update the grid to refresh the data
                $(".flex1").flexReload();

    }
}

</script>
</body>
</html>