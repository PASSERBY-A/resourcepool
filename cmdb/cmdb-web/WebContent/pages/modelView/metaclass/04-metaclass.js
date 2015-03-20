//设置默认显示的名称
var viewTypeName = null;
var viewType = 0;
var setting = {
	view : {
		selectedMulti : false
	},
	edit : {
		enable : true,
		showRemoveBtn : false,
		showRenameBtn : false
	},
	data : {
		simpleData : {
			enable : true
		}
	},
	callback : {
		onClick : onClick
	}
};



var dataMaker = function(count) {
	var nodes = new Array();
	$.ajax({
	  url: "getTreeStructure",
	  data: {
	    
	  },
	  success: function( data ) {
		  //alert("dataMaker:" + data);
		  var myobj=eval(data);  
		  for(var i=0;i<myobj.length;i++){  
		     nodes.push(myobj[i]);
		  }  
		  return nodes;
	  }
	});

}

var zNodes =[
	{id:1, pId:0, name:"Fabric-Name1", iconSkin:"icon_hardware", open:true},
	{id:2, pId:1, name:"San-Switch", iconSkin:"icon_network"},
	{id:3, pId:1, name:"Host", iconSkin:"icon_server", open:true},
	{id:4, pId:1, name:"Storage", iconSkin:"icon_storage"},
	{id:5, pId:2, name:"San1", iconSkin:"icon_network"},
	{id:6, pId:2, name:"San2", iconSkin:"icon_network"},
	{id:7, pId:3, name:"Host1", iconSkin:"icon_network"},
	{id:8, pId:3, name:"Host2", iconSkin:"icon_network"},
	{id:9, pId:4, name:"Storage1", iconSkin:"icon_network"},
	{id:10, pId:4, name:"Storage2", iconSkin:"icon_network"}
];


function onClick(event, treeId, treeNode, clickFlag) {
	//showLog("[ "+getTime()+" onClick ]&nbsp;&nbsp;clickFlag = " + clickFlag + " (" + (clickFlag===1 ? "single selected": (clickFlag===0 ? "<b>cancel selected</b>" : "<b>multi selected</b>")) + ")");
	 
//	alert(treeNode.tId + ", " + treeNode.name +  ", " + treeNode.parentTId);
//	alert(treeNode.tId + ", " + treeNode.name + ", " + treeNode.parentTId + ", " + treeNode.level + ", " + treeNode.iframeURL);
//	if(treeNode.level == 0)
//		{
//			$("#ciListFrame").attr("src","../modelView/mainFrame.jsp");
//		}
//	else if(treeNode.level == 1){
//		
////				alert( treeNode.name);
//				if(treeNode.tId.indexOf("2") > -1)
//					{
//						$("#ciListFrame").attr("src","../modelView/sanFrame.jsp");
//					}
//				else if (treeNode.tId.indexOf("5") > -1)
//					{
//						$("#ciListFrame").attr("src","../modelView/hostFrame.jsp");
//					}
//				else if (treeNode.tId.indexOf("8") > -1)
//					{
//						$("#ciListFrame").attr("src","../modelView/storageFrame.jsp");
//					}
//	}
//	else {
//		if(treeNode.parentTId.indexOf("2") > -1)
//		 {
////				alert( treeNode.name);
//				$("#ciListFrame").attr("src","../modelView/sanFrame.jsp");
//		}else 	if(treeNode.parentTId.indexOf("5") > -1)
//			 {
////					alert( treeNode.name);
//					$("#ciListFrame").attr("src","../modelView/hostInstanceFrame.jsp");
//			}
//		else if (treeNode.parentTId.indexOf("8") > -1)
//				 {
////					alert( treeNode.name);
//					$("#ciListFrame").attr("src","../modelView/storageInstanceFrame.jsp");
//				 }
//	}
	viewTypeName = treeNode.viewName;
	viewType = treeNode.typeName;
	$("#ciListFrame").attr("src","../modelView/" + treeNode.iframeURL + "?nodeId=" + treeNode.id + "&typeName=" + viewType+"&rootViewName="+treeNode.rootViewName+"&viewTypeName="+viewTypeName+"&swq=true");

};	

$(document).ready(function(){
	//var rNodes = dataMaker(100);
	var nodes = new Array();
	$.ajax({
		  url: "getTreeStructure",
		  data: {
		    
		  },
		  success: function( data ) {
			  //alert("dataMaker:" + data);
			  var myobj=eval(data);  
			  for(var i=0;i<myobj.length;i++){  
			     nodes.push(myobj[i]);
			  }  
				$.fn.zTree.init($("#treeDemo"), setting, nodes);
		  }
		});

});