// We use a document ready jquery function.
jQuery(document).ready(function(){

	var data = {
		"page" : "1",
		"records" : "3",
		"rows" : [ 
		          {id:"1",classid:"1",name:"BusinessSystem",attrtype:"0",seq:"01",displaytype:"2"} ,  
		          {id:"2",classid:"1",name:"Vender",attrtype:"0",seq:"02",displaytype:"2"},  
		          {id:"3",classid:"5",name:"Hostname",attrtype:"0",seq:"03",displaytype:"2"},  
		          {id:"4",classid:"5",name:"IP",attrtype:"0",seq:"04",displaytype:"2"},  
		          {id:"5",classid:"5",name:"Version",attrtype:"0",seq:"05",displaytype:"2"},  
		          {id:"6",classid:"1",name:"Nodename",attrtype:"0",seq:"06",displaytype:"2"},  
		          {id:"7",classid:"1",name:"Description",attrtype:"0",seq:"07",displaytype:"2"},  
		          {id:"8",classid:"2",name:"Type",attrtype:"0",seq:"08",displaytype:"2"},  
		          {id:"9",classid:"3",name:"Region",attrtype:"0",seq:"09",displaytype:"2"},  
		          {id:"11",classid:"1",name:"Logo",attrtype:"0",seq:"10",displaytype:"2"},  
		          {id:"12",classid:"1",name:"Provence",attrtype:"1",seq:"11",displaytype:"0"},  
		          {id:"13",classid:"1",name:"City",attrtype:"1",seq:"12",displaytype:"0"},  
		          {id:"14",classid:"4",name:"Department",attrtype:"1",seq:"13",displaytype:"0"},  
		          {id:"15",classid:"4",name:"Team",attrtype:"1",seq:"14",displaytype:"0"},  
		          {id:"16",classid:"4",name:"Net",attrtype:"1",seq:"15",displaytype:"0"},  
		          {id:"17",classid:"1",name:"Company",attrtype:"1",seq:"16",displaytype:"0"},  
		          {id:"18",classid:"1",name:"SubVersion",attrtype:"1",seq:"17",displaytype:"0"},  
		          {id:"19",classid:"2",name:"System",attrtype:"1",seq:"18",displaytype:"0"},  
		          {id:"21",classid:"1",name:"SubSys",attrtype:"1",seq:"19",displaytype:"0"},  
		          {id:"22",classid:"3",name:"Transaction",attrtype:"1",seq:"20",displaytype:"0"}
        ]
    };
        
jQuery("#metaclass-attr").jqGrid({
    datatype: "jsonstring",
    datastr: data,
    jsonReader: { repeatitems: false },
    rowNum: 500,
    height: 400,
    width: 600,
    scrollOffset: 0,
//    rowList: [10,20,30],
    loadonce: true,
    colNames:['id', 'classid', 'attrname', 'attrtype', 'displaytype', 'seq'],
    colModel:[
           {name:'id', width:60, hidden:true},
           {name:'classid', width:60, hidden:true},
           {name:'name', width:140, align:'left'},
           {name:'attrtype', width:100, align:'left',
        	   formatter:'select', editoptions:{value:'0:Dimen;1:Ext'},
               stype: 'select',
               searchoptions: { value: ':Any;0:Dimen;1:Ext' }
           },
           {name:'displaytype', width:100, align:'left',
        	   formatter:'select', editoptions:{value:'0:String;1:Number;2:Select'},
               stype: 'select',
               searchoptions: { value: ':Any;0:String;1:Number;2:Select' }
           },
           {name:'seq', width:80, align:"left", search:false}
    ],
    pager: "#page-metaclass-attr",
    gridview: true,
    viewrecords: true,
    rownumbers: true,
    sortable: true,
    sortname: 'seq',
    sortorder: "asc",
    caption: "Meta-Class attribute from json data",
    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" }
});  

jQuery("#metaclass-attr").jqGrid('navGrid','#page-metaclass-attr',
	{edit:false, add:false, del:false, search:false, view:false},
    { }, // edit
    { }, // add
    { }, // delete
    {multipleSearch: true, overlay: false}, // Search 
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
	defaultSearch: 'en'
});
jQuery("#metaclass-attr")[0].toggleToolbar(); // Search toolbar hide by default

jQuery("#metaclass-attr").jqGrid('navButtonAdd', '#page-metaclass-attr',{
	caption: "", 
	buttonicon: 'ui-icon-pin-s',
	title: "Toggle Searching Toolbar",
	onClickButton: function () { jQuery("#metaclass-attr")[0].toggleToolbar(); }
});

var grid = $('#metaclass-attr');
grid.jqGrid('navButtonAdd', '#page-metaclass-attr',{
	caption: "", 
	buttonicon: "ui-icon-calculator",
	title: "Choose Columns",
	onClickButton: function() {
		grid.jqGrid('columnChooser');
	}
});


jQuery("#cm1").click(
	function() {
		var selRowId = jQuery('#metaclass-attr').jqGrid('getGridParam', 'selrow');
		alert ('The number of selected row ID:(' + selRowId + ')');
});

});