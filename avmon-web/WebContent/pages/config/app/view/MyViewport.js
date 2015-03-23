Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',

    layout: {
        type: 'border'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
{
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
				    		"<a href=\"../main\"><div class=\"Avmon_back_gif\" style=\"width:48px;height:43px\">" +
				    		"<img id=\"Avmon_back_img\" src=\"../resources/images/back.png\" " +
				    		" onmouseover=\"mouseover('..');\" onmouseout=\"mouseout('..');\" ></div></a>" +
				    		"<div class=\"MainTitle f28px LineH12\"  style=\"font-family: \'Microsoft yahei\',\'微软雅黑\',\'Lato\', Helvetica,Tahoma,Verdana,Arial,sans-serif \">监控配置</div>"
				        }
				    ]
				},
                {
                    xtype: 'treepanel',
                    region: 'west',
                    id: 'configMenuTree',
                    itemId: 'configMenuTree',
                    width: 250,
                    collapsible: true,
                    title: 'Menu',
                    store: 'ConfigMenus',
                    rootVisible: false,
                    viewConfig: {

                    },
                    listeners: {
                        select: {
                            fn: me.onConfigMenuTreeSelect,
                            scope: me
                        },
                        afterlayout: {
                            fn: me.onConfigMenuTreeAfterLayout,
                            scope: me
                        }
                    }
                },
                {
                    xtype: 'panel',
                    region: 'center',
                    id: 'configContent',
                    itemId: 'configContent',
                    layout: {
                        type: 'border'
                    }
                }
            ]
        });
        me.callParent(arguments);
    },
    onConfigMenuTreeSelect: function(rowmodel, record, index, eOpts) {
    	// console.log(record);
    	if ((record.data.parentId == 'root' && record.data.id=='310')||record.data.text=="告警处理规则") {
			return;
		}
        Ext.getCmp('configContent').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="'+record.get("url")+'"></iframe>');
    },
    onConfigMenuTreeAfterLayout: function(container, layout, eOpts) {
        var tree=Ext.getCmp("configMenuTree");
        var path="/root/310/303";
        tree.getSelectionModel().select(tree.getStore().getNodeById(303),true);
    }
});