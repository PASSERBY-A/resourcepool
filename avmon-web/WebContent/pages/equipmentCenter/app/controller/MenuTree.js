Ext.define('app.controller.MenuTree', {
    extend: 'Ext.app.Controller',
    alias: 'controller.menuTree',

    refs: [
        {
            ref: 'propertyGrid',
            selector: 'propertyGrid'
        },
        {
            ref: 'menuTree',
            selector: 'menuTree'
        },
        {
            ref: 'monitorGrid',
            selector: 'monitorGrid'
        },
        {
            ref: 'kpiGrid',
            selector: 'kpiGrid'
        }
    ],

    onTreepanelItemClick: function(dataview, record, item, index, e, eOpts) {
        Ext.getCmp('contentPanel').getLayout().setActiveItem(1);

        var p=this.getPropertyGrid();

        if(record.get("leaf")){
            p.getStore().load({params:{mo:record.get("id")}});
            Ext.getCmp('moId').setValue(record.get("id"));

            Ext.getCmp("configPanel").setTitle("Config-"+record.get("text")+"("+record.get("id")+")");

//            //get status
//            Ext.getCmp("agentStatus").setValue(record.get("agentStatus"));
//            Ext.getCmp("agentLastUpdateTime").setValue(record.get("agentLastUpdateTime"));

        }
    },

    onTreepanelItemContextMenu: function(dataview, record, item, index, e, eOpts) {

        var me=this;

        var menuTree=this.getMenuTree();

        var currentNode=menuTree.getStore().getNodeById(record.get("id"));

        e.preventDefault(); 
        e.stopEvent(); 

        if(record.get("id")=="02host"){

            var menu = new Ext.menu.Menu({ 
                //控制右键菜单位置 
                float:true, 
                items:[{ 
                    text:avmon.common.refresh, 
                    iconCls:'icon-refresh', 
                    handler:function(){ 
                        //当点击时隐藏右键菜单 
                        this.up("menu").hide(); 
                        me.refresh();
                    } 
                },{ 
                    text:avmon.config.addHost, 
                    iconCls:'icon-add', 
                    handler:function(){ 
                        this.up("menu").hide(); 
                        me.insertTreeNode(currentNode,'HOST','');
                    } 
                }
                ] 
            }).showAt(e.getXY());//让右键菜单跟随鼠标位置

        }
        else if(record.get("id")=="03db"){

        }
        else if(record.get("pid")=="02host"){
            var menu = new Ext.menu.Menu({ 
                //控制右键菜单位置 
                float:true, 
                items:[{ 
                    text:avmon.common.refresh, 
                    iconCls:'icon-refresh', 
                    handler:function(){ 
                        //当点击时隐藏右键菜单 
                        this.up("menu").hide(); 
                        me.refresh();
                    } 
                },{ 
                    text:avmon.config.add+record.get("id")+avmon.common.host, 
                    iconCls:'icon-add', 
                    handler:function(){ 
                        this.up("menu").hide(); 
                        me.insertTreeNode(currentNode,'HOST',record.get("id"));
                    } 
                }
                ] 
            }).showAt(e.getXY());//让右键菜单跟随鼠标位置
        }
        else{

            var menu = new Ext.menu.Menu({ 
                //控制右键菜单位置 
                float:true, 
                items:[{ 
                    text:avmon.common.refresh, 
                    iconCls:'icon-refresh', 
                    handler:function(){ 
                        //当点击时隐藏右键菜单 
                        this.up("menu").hide(); 
                        me.refresh();
                    } 
                },{ 
                    text:avmon.common.deleted, 
                    iconCls:'icon-delete', 
                    handler:function(){ 
                        this.up("menu").hide(); 
                        me.deleteTreeNode(currentNode,record.get("id"));
                    } 
                } 
                ] 
            }).showAt(e.getXY());//让右键菜单跟随鼠标位置

        }
    },

    onSearchButtonClick: function(button, e, eOpts) {
    	var queryName = Ext.getCmp("treeSearch").getValue();
    	if(""==queryName || null==queryName||'' == queryName){
    		Ext.Msg.alert('Message', avmon.config.inputContentCannotBeEmpty);
    		return;
    	}
    	
    	Ext.apply(Ext.getCmp("menuTree").store.proxy.extraParams,{queryFlag:"1",parentId:queryName});
    	Ext.getCmp("menuTree").getStore().load();
    },
    
    onTreeSearchSpecialkey:function(field,e,eOpts){
    	if (e.getKey() == Ext.EventObject.ENTER) {
    	}
    },
    
    onBtnAddHostCancelClick: function(button, e, eOpts) {

    },

    onBtnRefreshTreeClick: function(button, e, eOpts) {
        var tree=Ext.getCmp("menuTree");

        if(!tree.getStore().isLoading()){
            if(tree.getStore().getRootNode()){
                tree.getStore().getRootNode().removeAll();
            }
            tree.getStore().load({params:{id:"root"}});
        }

    },

    onBtnAddMOClick: function(button, e, eOpts) {
        var win = Ext.selectMoTypeWindow;

        if(!win){
            win=Ext.create('widget.selectMoTypeWindow');
            Ext.selectMoTypeWindow=win;
            win.needReload=true;
        }
        win.show();
    },

    onBtnRemoveMOClick: function(button, e, eOpts) {
        var me=this;

        var menuTree=this.getMenuTree();

        var aa=menuTree.getSelectionModel().getSelection();
        if(aa && aa.length>0){
            var record=aa[0];
            var currentNode=menuTree.getStore().getNodeById(record.get("id"));
            this.deleteTreeNode(currentNode,record.get("id"));
        }
    },
    
    onBitCodeButtonClick: function(button, e, eOpts) {
        var me=this;

        var menuTree=this.getMenuTree();

        var aa=menuTree.getSelectionModel().getSelection();
        
        var imgId = '';
        
        if(aa && aa.length>0){
            var record=aa[0];
            var currentNode=menuTree.getStore().getNodeById(record.get("id"));
            this.bitCodeTreeNode(currentNode,record.get("id"));
            imgId = record.get("id");
        }
    },
    
    onShowBitCodeButtonClick: function(button, e, eOpts) {
    	var imgpanel = Ext.create('Ext.panel.Panel', {
    		layout: {
                type: 'anchor'
            },
    	    autoScroll: true,
    	    height:450
    	
		});
    	var win;
    	var printButton = Ext.create("Ext.Button", {
    		text:avmon.equipmentCenter.print,
//    		anchor:'10%',
    		width:100,
    		height:30,
    		listeners: {
        		click:function(){
        			Ext.Ajax.request({
        				url:'printAllImg',
        				success: function(response, opts) {
        					
        				}
        			});
//        			win.show();
//        			win.print();
        		}}
    	});
    	
    	
    	
    	Ext.Ajax.request({
    		url:'getAllbitcodeFile',
    		success: function(response, opts) {
    			var filename = response.responseText.split(',');
    			
    			for(var n=0;n<filename.length;n++)
    			{
    				var nameArr = filename[n].split('.');
    				var bitcodeContainer = Ext.create("Ext.Container", {
    					anchor:'20%',
    					height:200,
    					autoEl: {
    	        	        tag: 'img',    //指定为img标签
    	        	        src: 'images/' + filename[n],
    	        	        title:nameArr[0]
    	        	    }
    				});
    				imgpanel.add(bitcodeContainer).show();
    				imgpanel.doLayout();
    			}
    		}
    	});
    	
    	win = Ext.create("Ext.window.Window", {
        	title:avmon.equipmentCenter.queryBitCode,
        	width:800,
        	height:480,
        	items:[printButton,imgpanel]
        });
        
        win.show();
    },

    onBtnSelectMoTypeWindowOkClick: function(button, e, eOpts) {
        var moId=button.up("window").down("form").getForm().findField('moId').getValue();
        var caption=button.up("window").down("form").getForm().findField('caption').getValue();
        var agentId=button.up("window").down("form").getForm().findField('agentId').getValue();
        var parentId=button.up("window").down("form").getForm().findField('parent_id').getValue();
        var protocol=button.up("window").down("form").getForm().findField('protocol').getValue();

        if(moId==""){
            Ext.MessageBox.alert("Warning",avmon.config.objectNumberCannotBeEmpty);
            return;

        }
        if(caption==""){
            Ext.MessageBox.alert("Warning",avmon.config.objectNameCannotBeEmpty);
            return;
        }
        if(parentId==""){
            Ext.MessageBox.alert("Warning",avmon.config.parentCanNotBeEmpty);
            return;
        }
        if(protocol==""){
            Ext.MessageBox.alert("Warning",avmon.config.protocolCanNotEmpty);
            return;
        }

        var record=null;
        var businessType = Ext.alarm.newBusinessType;
        Ext.each(Ext.getCmp("moTypeTree").getSelectionModel().getSelection(),function(item){
            record=item;
        });
        

        if(record==null){
            Ext.MessageBox.alert("Warning",avmon.config.pleaseSelectObjectType);
            return;
        }
        
        if(businessType==null){
            Ext.MessageBox.alert("Warning",avmon.config.pleaseSelectBusinessSystem);
            return;
        }

        var typeId=record.get("id");

        Ext.Ajax.request({
            url: 'createMo',
            params: {mo:moId,caption:caption,moType:typeId,parentId:parentId,protocol:protocol,agentId:agentId,businessType:businessType},
            success: function(response, opts) {
                var obj=Ext.decode(response.responseText);
                if(obj.success!=true){
                	Ext.MessageBox.alert("ERROR",avmon.config.saveFailure);
                }
                else{
                	Ext.MessageBox.alert("INFO",avmon.config.saveSuccess);
                    button.up("window").hide();
                }
            },
            failure: function(response, opts) {
                Ext.MessageBox.alert("Error",avmon.config.wrongRetry);
            }
        });
    },

    insertTreeNode: function(treeNode, type, os) {
        var iconCls='icon-host';
        if(os){
            iconCls='icon-'+os;
        }
        var node = Ext.create('app.model.TreeNode', {
            id:(new Date()).valueOf(),
            text:avmon.config.newHost,
            os:os,
            type:type,
            leaf:true,
            iconCls:iconCls
        });


        var win = Ext.addMoWindow || (Ext.addMoWindow = Ext.create('widget.addMoWindow'));
        win.treeNode=treeNode;
        win.down("form").getForm().loadRecord(node);
        win.show();
    },

    deleteTreeNode: function(treeNode, nodeId) {

        Ext.MessageBox.confirm('Confirm', avmon.equipmentCenter.confirmDelete, function(btn){

            if(btn=="yes"){
                Ext.Ajax.request({
                    url: 'deleteMo',
                    params: {mo:nodeId},
                    success: function(response, opts) {
                    	console.log(response);
                    	console.log(response.responseText.success);
                    	var result = Ext.JSON.decode(response.responseText);
                    		
                    	if (result.success==true ||result.success=='true') {
                    		treeNode.remove();
                            Ext.example.msg('Done', avmon.equipmentCenter.moDeleteSuccess);
						}else{
							 Ext.example.msg('Error', avmon.equipmentCenter.moDeleteFailed);
						}
                    },
                    failure: function(response, opts) {
                    	  Ext.example.msg('Error', avmon.equipmentCenter.moDeleteFailed);
                    }
                });
            }

        });

    },
    

    bitCodeTreeNode: function(treeNode, nodeId) {

        Ext.MessageBox.confirm('Confirm', avmon.equipmentCenter.confirmGenerate2Dcode, function(btn){

            if(btn=="yes"){
                Ext.Ajax.request({
                    url: 'bitcodeMo',
                    params: {mo:nodeId},
                    success: function(response, opts) {
                        
                        Ext.example.msg('Done', avmon.equipmentCenter.BitCodeGenerated);
                    },
                    failure: function(response, opts) {
                    	Ext.example.msg('Done', avmon.equipmentCenter.BitCodeGeneratedFailed);
                    }
                });
            }

        });

    },    

    refresh: function() {
        var tree=this.getMenuTree();

        if(!tree.getStore().isLoading()){
            if(tree.getStore().getRootNode()){
                tree.getStore().getRootNode().removeAll();
            }
            tree.getStore().load({params:{id:"root"}});
        }
    },

    init: function(application) {
        this.control({
            "menuTree": {
                itemclick: this.onTreepanelItemClick,
                itemcontextmenu: this.onTreepanelItemContextMenu
            },
            "#btnTest": {
                click: this.onBtnTestClick
            },
            "#btnAddHostOk": {
                click: this.onBtnAddHostOkClick
            },
            "#btnAddHostCancel": {
                click: this.onBtnAddHostCancelClick
            },
            "#btnRefreshTree": {
                click: this.onBtnRefreshTreeClick
            },
            "#btnAddMO": {
                click: this.onBtnAddMOClick
            },
            "#btnRemoveMO": {
                click: this.onBtnRemoveMOClick
            },
            "#btnSelectMoTypeWindowOk": {
                click: this.onBtnSelectMoTypeWindowOkClick
            },
            "#btnTreeSearch":{
            	click: this.onSearchButtonClick
            },
            "#btnBitCode":{
            	click: this.onBitCodeButtonClick
            }
            
            ,
            "#btnshowBitCode":{
            	click: this.onShowBitCodeButtonClick
            }
        });
    }

});
