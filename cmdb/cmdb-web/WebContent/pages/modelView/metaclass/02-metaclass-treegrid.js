// We use a document ready jquery function.
jQuery(document).ready(function(){

	var data = {
		"page" : "1",
		"records" : "23",
		
//        new cell(){Id=nodeid+1000,Name="Item 2For ("+nodeid+")1",score=nodeid+100,level=1,parentId=nodeid,isLeaf=true,isExpanded=false},
//        new cell(){Id=nodeid+1001,Name="Item 2For ("+nodeid+")2",score=nodeid+101,level=1,parentId=nodeid,isLeaf=true,isExpanded=false},
//        new cell(){Id=nodeid+10022,Name="Item 2For ("+nodeid+")3",score=nodeid+102,level=1,parentId=nodeid,isLeaf=true,isExpanded=false}

		"rows" : [ 
		          {id:"1",res_class_name:"Hardware",res_class_icon:"hardware.16.gif",level:"0",parent:"",isLeaf:"false",expanded:"true",loaded:"true"},
		          {id:"2",res_class_name:"Network",res_class_icon:"network.16.gif",level:"1",parent:"1",isLeaf:"true",expanded:"false",loaded:"true"},
		          {id:"3",res_class_name:"Server",res_class_icon:"server.16.gif",level:"1",parent:"1",isLeaf:"true",expanded:"false",loaded:"true"},
		          {res_class_id:"4",res_class_name:"Storage",res_class_icon:"storage.16.gif"},
		          {res_class_id:"5",res_class_name:"Printer",res_class_icon:"printer.16.gif"},
		          {res_class_id:"6",res_class_name:"OS",res_class_icon:"computer.16.png"},
		          {res_class_id:"7",res_class_name:"AIX",res_class_icon:"aix.16.png"},
		          {res_class_id:"8",res_class_name:"HP-UX",res_class_icon:"hpux.16.png"},
		          {res_class_id:"9",res_class_name:"Linux",res_class_icon:"linux.16.png"},
		          {res_class_id:"10",res_class_name:"Redhat",res_class_icon:"redhat.16.gif"},
		          {res_class_id:"11",res_class_name:"FreeBSD",res_class_icon:"freebsd.16.gif"},
		          {res_class_id:"12",res_class_name:"Solaris",res_class_icon:"solaris.16.png"},
		          {res_class_id:"13",res_class_name:"Windows",res_class_icon:"windows.16.png"},
		          {res_class_id:"14",res_class_name:"MacOS",res_class_icon:"macos.16.gif"},
		          {res_class_id:"15",res_class_name:"Database",res_class_icon:"database.16.png"},
		          {res_class_id:"16",res_class_name:"DB2",res_class_icon:"db2.16.gif"},
		          {res_class_id:"17",res_class_name:"MySQL",res_class_icon:"mysql.16.gif"},
		          {res_class_id:"18",res_class_name:"Middleware",res_class_icon:"middleware.16.png"},
		          {res_class_id:"19",res_class_name:"Jboss",res_class_icon:"jboss.16.jpg"},
		          {res_class_id:"20",res_class_name:"Tomcat",res_class_icon:"tomcat.16.gif"},
		          {res_class_id:"21",res_class_name:"VirtualMachine",res_class_icon:"vm.16.png"},
		          {res_class_id:"22",res_class_name:"Application",res_class_icon:"application.16.gif"},
		          {res_class_id:"23",res_class_name:"Unknown",res_class_icon:"unknown.16.gif"},
        ]
    };

jQuery("#metaclass").jqGrid({
    datatype: "jsonstring",
    datastr: data,
    jsonReader: { repeatitems: false },
    height: 'auto',
    rowNum: 10, // TreeGrid is not supporting Pager
    rowList: [10,20,30],
//    loadonce: true,
//    ExpandColClick: true, //enable TreeGrid
    treeGrid: true, //set TreeGrid model
    treeGridModel: 'adjacency', //set expand column
    ExpandColumn: 'res_class_icon',
//    treeIcons: {leaf:'ui-icon-document-b'},
    colNames:['id', 'icon', 'ClassName'],
    colModel:[
           {name:'id', width:60, hidden:true},
           {name:'res_class_icon', width:60, key:true, align:'center', formatter: imageFormatter},
           {name:'res_class_name', width:200}
    ],
    sortname: 'id',            
    sortorder: "asc",
    pager: "#page-metaclass",
    viewrecords: true,
    caption: "Meta-Class table from json data",
    jsonReader : { root: "rows", page: "page", total: "total" , records: "records" },
    loadComplete: function (data) {
        isFirst = false;
    },
    onCellSelect: function (rowid, iCol, cellcontent, e) {
        var cell = rowid;
    },
    expandNode: function (record) {
        var t = rc.Name;
    },
    expandRow: function (record) {
        var r = record.Name;
    },
    onSelectRow: function(id) { 
        if(id && id !== lastsel) { 
        	grid.jqGrid('restoreRow', lastsel); 
        	grid.jqGrid('editRow', id, true); 
        	lastsel = id; 
        }
    } 
});  

jQuery("#page-metaclass").jqGrid('navGrid','#metaclass',{add:false,edit:false,del:false});

	function imageFormatter(cellvalue, options, rowObject) {
		var icon = rowObject['res_class_icon'];
	    if (icon != null)
			return '<img src="../images/meta-class/' + icon + '" />';
	    else 
	        return '<img src="../images/meta-class/unknown.16.gif" />';
	};
});