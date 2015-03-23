Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',

    layout: {
        type: 'border'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [{
                xtype: 'panel',
                region: 'north',
                border: 0,
                height: 70,
                id: 'topPanel',
                layout: {
                    type: 'fit'
                },
                bodyBorder: false,
                items: [
                    {
                        xtype: 'panel',
                        border: 0,
                        height: 70,
                        id: 'mainMenuPanel',
                        autoScroll: false,
                        layout: {
                            type: 'absolute'
                        },
                        bodyBorder: false,
                        html:"<div id=\"top\" class=\"top\"><div class=\"fixed\">" +
                		"<a href=\"../main\"><div class=\"Avmon_back_gif\" style=\"width:48px;height:43px\" >" +
                		"<img id=\"Avmon_back_img\" src=\"../resources/images/back.png\" " +
                		" onmouseover=\"mouseover('..');\" ></div></a>" +
                		"<div class=\"MainTitle f28px LineH12\" style=\"font-family: \'Microsoft yahei\',\'微软雅黑\',\'Lato\', Helvetica,Tahoma,Verdana,Arial,sans-serif \" >系统管理</div>"
                    }
                ]
            	},
                {
                    xtype: 'treepanel',
                    region: 'west',
                    split: true,
                    id: 'systemMenuTree',
                    itemId: 'systemMenuTree',
                    width: 250,
                    collapsible: true,
                    title: 'Menu',
                    store: 'SystemMenus',
                    rootVisible: false,
                    viewConfig: {

                    },
                    listeners: {
                        select: {
                            fn: me.onSystemMenuTreeSelect,
                            scope: me
                        },
                        afterlayout: {
                            fn: me.onSystemMenuTreeAfterLayout,
                            scope: me
                        }
                    }
                },
                {
                    xtype: 'panel',
                    region: 'center',
                    id: 'systemContent'
                }
            ]
        });

        me.callParent(arguments);
    },

    onSystemMenuTreeSelect: function(rowmodel, record, index, eOpts) {
        Ext.getCmp('systemContent').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+record.get("url")+'"></iframe>');
    },

    onSystemMenuTreeAfterLayout: function(container, layout, eOpts) {
        var tree=Ext.getCmp("systemMenuTree");
        var path="/root/" + tree.getRootNode().firstChild.internalId;
        tree.expandPath(path, 'id', '/', function(bSuccess, oLastNode){
            if(bSuccess){
                tree.getSelectionModel().select(oLastNode,true);
            }
            else{
                //alert('未找到节点'+moId);
            }
        });

    }

});