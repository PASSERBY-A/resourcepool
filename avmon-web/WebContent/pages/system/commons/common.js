//递归添加子节点   
function insertChildren(parentId,treeList,treeStore) {
    var childList = [];
    //根据parentId父节点，查找出子节点   
    for (index=0;index<treeList.length;index++) {
        var treeNode = treeList[index];
        if (treeNode.parent_id == parentId) {
        	var childNode = treeStore.getNodeById(treeNode.id);
            childList.push(treeNode);
        }
    }

    var treeNodeInterface = treeStore.getNodeById(parentId); //这里主要使用了Ext中的树的搜索算法，查找相的节点，当然也可以自己实现  
    
    //如果子列表为空，则递归结束，直接返回   
    if (childList.length == 0) {
    	treeNodeInterface.set('leaf', true);
    }else{
    	treeNodeInterface.set('expanded', true);
    	
    	treeNodeInterface.appendChild(childList);
	    //遍历孩子节点列表，给每个孩子节点继续添加子节点   
	    for (x=0;x<childList.length;x++) {
	        var treeNode = childList[x];
	        insertChildren(treeNode.id,treeList,treeStore); //递归调用，插入子节点   
	    }
    }
}
function initTreeStore(s,url){
	//加载并构建树store
	Ext.Ajax.request({
	    url: url,
	    success: function(response, opts) {
			var treeNods = Ext.JSON.decode(response.responseText);
			insertChildren("0",treeNods,s);
	    },
	    failure: function(response, opts) {
	    	
	    }
	});
}
