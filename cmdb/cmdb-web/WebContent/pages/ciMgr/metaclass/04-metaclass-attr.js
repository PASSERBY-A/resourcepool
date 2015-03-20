// We use a document ready jquery function.



jQuery(document).ready(function(){
        
jQuery("#metaclass-attr").jqGrid({
    datatype: "json",
    url:"getCiTypeAttr?typeName=Ci",
    jsonReader: { repeatitems: false },
    rowNum: 10,
    height: 240,
    width: 800,
    scrollOffset: 0,
    rowList: [10,20,30],
    loadonce: false,
    colNames:['name','dataType','value','attGroup','order','defValue','viewMode','isRequired','recordChange','updateMode','label'],
    colModel:[
           {name:'name', width:60, hidden:false, editable:true},
           {name:'dataType', width:60, hidden:false, editable:true},
           {name:'value', width:60, hidden:false, editable:true},
           {name:'attGroup', width:60, hidden:false, editable:true},
           {name:'order', width:60, hidden:false, editable:true},
           {name:'defValue', width:60, hidden:false, editable:true},
           {name:'viewMode', width:60, hidden:false, editable:true},
           {name:'isRequired', width:60, hidden:false, editable:true},
           {name:'isRequired', width:60, hidden:false, editable:true},
           {name:'recordChange', width:60, hidden:false, editable:true},
           {name:'label', width:140, hidden:false, align:'left', editable:true}
//           {name:'attrtype', width:100, align:'left',
//        	   formatter:'select', editoptions:{value:'0:Dimen;1:Ext'},
//               stype: 'select',
//               searchoptions: { value: ':Any;0:Dimen;1:Ext' }
//           },
//           {name:'displaytype', width:100, align:'left',
//        	   formatter:'select', editoptions:{value:'0:String;1:Number;2:Select'},
//               stype: 'select',
//               searchoptions: { value: ':Any;0:String;1:Number;2:Select' }
//           },
//           {name:'seq', width:80, align:"left", search:false, editable:true}
    ],
    pager: "#page-metaclass-attr",
    gridview: true,
    viewrecords: true,
    rownumbers: true,
    sortable: true,
    sortname: 'name',
    sortorder: "asc",
    caption: "类属性",
    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" },
    editurl:"saveOrUpdateCiTypeAttr"
});  

jQuery("#metaclass-attr").jqGrid('navGrid','#page-metaclass-attr',
	{edit:true, add:true, del:true, search:true, view:true},
    { 
		closeAfterEdit:true,
		afterSubmit:function(response){
			idToSelect = response.responseText;
			var res = $.parseJSON(idToSelect);
			
		    if (res && res.status && "success"==res.status) {
	    		alert(res.status);
	    		return [true,res.status,false];
		    }else{
		    	return [false,res.status,false];
		    }
		},
		reloadAfterSubmit:true
		
    }, // edit
    { 
    	closeAfterAdd:true,
        afterSubmit: function(response) {
        	var res = $.parseJSON(response.responseText);
        	if(res.msg=="success"){
        		alert("保存成功");
        		return [true,res.msg,false];
        	}else{
        		return [false,res.msg,false];
        	}
        },
        reloadAfterSubmit:true
    	
    }, // add
    {
    	url:"delClass",
    	afterSubmit: function(response) {
        	var res = $.parseJSON(response.responseText);
        	if(res.msg=="success"){
        		alert("删除成功");
        		return [true,res.msg,false];
        	}else{
        		return [false,res.msg,false];
        	}
        },
        reloadAfterSubmit:true
    	
    }, // delete
    {multipleSearch: false, overlay: false,top :150,left:250,sField:'searchField',sValue:'searchString',sOper: 'searchOper'}, // Search 
    {   // vew options
        beforeShowForm: function(form) {
            $("metaclass-attr",form[0]).show();
        },
        afterclickPgButtons: function(whichbutton, form, rowid) {
            $("metaclass-attr",form[0]).show();
        }
    }
);

jQuery("#metaclass-attr").jqGrid('filterToolbar', {
	stringResult: true,
	searchOnEnter: true, 
	defaultSearch: 'cn'
});
jQuery("#metaclass-attr")[0].toggleToolbar(); // Search toolbar hide by default


var grid = $('#metaclass-attr');
grid.jqGrid('navButtonAdd', '#page-metaclass-attr',{
	caption: "", 
	buttonicon: "ui-icon-calculator",
	title: "Choose Columns",
	onClickButton: function() {
		alert("click");
		//grid.jqGrid('columnChooser');
		$( "#attrEdit-form" ).dialog( "open" );
	}
});




});