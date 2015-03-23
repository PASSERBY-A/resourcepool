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
		beforeDrag : beforeDrag,
		beforeDrop : beforeDrop,
		beforeRename: beforeRename,
		onRemove: onRemove
	}
};

var sysData = [
	{id:1, pId:0, name:"基础管理", iconSkin:"icon_hardware", open:true},
	{id:2, pId:1, name:"类管理", iconSkin:"icon_network"},
	{id:3, pId:1, name:"资源Object管理", iconSkin:"icon_server", open:true}           
    ]

var zNodes =[
	{id:1, pId:0, name:"Hardware", iconSkin:"icon_hardware", open:true},
	{id:2, pId:1, name:"Network", iconSkin:"icon_network"},
	{id:3, pId:1, name:"Server", iconSkin:"icon_server", open:true},
	{id:4, pId:1, name:"Storage", iconSkin:"icon_storage"},
	{id:5, pId:1, name:"Printer", iconSkin:"icon_printer"},
	{id:6, pId:3, name:"OS", iconSkin:"icon_computer"},
	{id:7, pId:6, name:"AIX", iconSkin:"icon_aix"},
	{id:8, pId:6, name:"HP-UX", iconSkin:"icon_hpux"},
	{id:9, pId:6, name:"Linux", iconSkin:"icon_linux"},
	{id:10, pId:6, name:"Redhat", iconSkin:"icon_redhat"},
	{id:11, pId:6, name:"FreeBSD", iconSkin:"icon_freebsd"},
	{id:12, pId:6, name:"Solaris", iconSkin:"icon_solaris"},
	{id:13, pId:6, name:"Windows", iconSkin:"icon_windows"},
	{id:14, pId:6, name:"MacOS", iconSkin:"icon_macos"},
	{id:15, pId:3, name:"Database", iconSkin:"icon_database"},
	{id:16, pId:15, name:"DB2", iconSkin:"icon_db2"},
	{id:17, pId:15, name:"MySQL", iconSkin:"icon_mysql"},
	{id:18, pId:3, name:"Middleware", iconSkin:"icon_middleware"},
	{id:19, pId:18, name:"Jboss", iconSkin:"icon_jboss"},
	{id:20, pId:18, name:"Tomcat", iconSkin:"icon_tomcat"},
	{id:21, pId:3, name:"VirtualMachine", iconSkin:"icon_vm"},
	{id:22, pId:3, name:"Application", iconSkin:"icon_app"},
	{id:23, pId:0, name:"Unknown", iconSkin:"icon_unknown"}
];

function beforeDrag(treeId, treeNodes) {
	for ( var i = 0, l = treeNodes.length; i < l; i++) {
		if (treeNodes[i].drag === false) {
			return false;
		}
	}
	return true;
}

function beforeDrop(treeId, treeNodes, targetNode, moveType) {
	return targetNode ? targetNode.drop !== false : true;
}

function onRemove(e, treeId, treeNode) {
	showLog("[ " + getTime() + " onRemove ]&nbsp;&nbsp;&nbsp;&nbsp; "
			+ treeNode.name);
}

function beforeRename(treeId, treeNode, newName) {
	if (newName.length == 0) {
		alert("Node name can not be empty.");
		var zTree = $.fn.zTree.getZTreeObj("treeDemo");
		setTimeout(function() {
			zTree.editName(treeNode);
		}, 10);
		return false;
	}
	return true;
}

function showLog(str) {
	if (!log)
		log = $("#log");
	log.append("<li class='" + className + "'>" + str + "</li>");
	if (log.children("li").length > 8) {
		log.get(0).removeChild(log.children("li")[0]);
	}
}

function getTime() {
	var now = new Date(), h = now.getHours(), m = now.getMinutes(), s = now
			.getSeconds(), ms = now.getMilliseconds();
	return (h + ":" + m + ":" + s + " " + ms);
}

var newCount = 1;
function add(e) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo1"), isParent = e.data.isParent, nodes = zTree
			.getSelectedNodes(), treeNode = nodes[0];
	if (treeNode) {
		treeNode = zTree.addNodes(treeNode, {
			id : (100 + newCount),
			pId : treeNode.id,
			isParent : isParent,
			name : "new node" + (newCount++),
			iconSkin: "icon_unknown"
		});
	} else {
		treeNode = zTree.addNodes(null, {
			id : (100 + newCount),
			pId : 0,
			isParent : isParent,
			name : "new node" + (newCount++),
			iconSkin: "icon_unknown"
		});
	}
	if (treeNode) {
		zTree.editName(treeNode[0]);
	} else {
		alert("Leaf node is locked and can not add child node.");
	}
};

function edit() {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo1"), nodes = zTree
			.getSelectedNodes(), treeNode = nodes[0];
	if (nodes.length == 0) {
		alert("Please select one node at first...");
		return;
	}
	zTree.editName(treeNode);
};

function remove(e) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo1"), nodes = zTree
			.getSelectedNodes(), treeNode = nodes[0];
	if (nodes.length == 0) {
		alert("Please select one node at first...");
		return;
	}
	var callbackFlag = $("#callbackTrigger").attr("checked");
	zTree.removeNode(treeNode, callbackFlag);
};

function clearChildren(e) {
	var zTree = $.fn.zTree.getZTreeObj("treeDemo"), nodes = zTree
			.getSelectedNodes(), treeNode = nodes[0];
	if (nodes.length == 0 || !nodes[0].isParent) {
		alert("Please select one parent node at first...");
		return;
	}
	zTree.removeChildNodes(treeNode);
};

$(document).ready(function(){
	$.fn.zTree.init($("#treeDemo"), setting, sysData);
	$.fn.zTree.init($("#treeDemo1"), setting, zNodes);
	$("#addParent").bind("click", {isParent:true}, add);
	$("#addLeaf").bind("click", {isParent:false}, add);
	$("#edit").bind("click", edit);
	$("#remove").bind("click", remove);
	$("#clearChildren").bind("click", clearChildren);
});