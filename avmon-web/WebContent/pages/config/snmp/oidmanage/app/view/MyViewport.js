/*
 * File: app/view/MyViewport.js
 *
 * This file was generated by Sencha Architect version 2.2.2.
 * http://www.sencha.com/products/architect/
 *
 * This file requires use of the Ext JS 4.1.x library, under independent license.
 * License of Sencha Architect does not include license for Ext JS 4.1.x. For more
 * details see http://www.sencha.com/license or contact license@sencha.com.
 *
 * This file will be auto-generated each and everytime you save your project.
 *
 * Do NOT hand edit this file.
 */

Ext.define('app.view.MyViewport', {
    extend: 'Ext.container.Viewport',

    requires: [
        'app.view.MenuTree',
        'app.view.ConfigPanel'
    ],

    itemId: 'main',
    layout: {
        type: 'border'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    region: 'west',
                    split: true,
                    frame: true,
                    width: 240,
                    layout: {
                        type: 'fit'
                    },
                    collapsible: false,
                    rootVisible: false,    
                    title: 'Menu',
                    items: [
                          {
	                          xtype: 'treepanel',
	                          id: 'treemenu',
	                          store: 'treeStore',
	                          expanded:true,
	                          rootVisible: false,
	                          listeners: {
                                  itemclick: {
                                      fn: me.onTreeviewItemClick,
                                      scope: me
                                  },
                                  beforeitemexpand:{
                      				  fn:me.onBeforeTreeItemExpend,
                      				  scope: me
                      			  }
                              }
                          }
                          ] 
                },
                {
                    xtype: 'panel',
                    region:'west',
                    id:	'equipmentList',
                    split: true,
                    frame: true,
                    width: 240,
                    layout: {
                        type: 'card'
                    },
                    collapsible: false,
                    title: avmon.config.installedEquipment,
                    items: [
                        {
                            xtype: 'menuTree',
                            id: 'menuTree',
                            region: 'center',
                            autoLoad:false
                        }
                    ]
                },
                {
                    xtype: 'container',
                    region: 'center',
                    id: 'contentPanel',
                    layout: {
                        type: 'card'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            id: 'panel1',
                            title: ''
                        },
                        {
                            xtype: 'configPanel'
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        if(!Ext.getCmp('panel1').loadFlag){
            Ext.getCmp('panel1').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../pages/config/agentManage/index.jsp"></iframe>');
            Ext.getCmp('panel1').loadFlag=true;
        }

        Ext.getCmp('contentPanel').getLayout().setActiveItem(0);


    },

    onButtonClick2: function(button, e, eOpts) {
        Ext.getCmp('contentPanel').getLayout().setActiveItem(3);

        Ext.getCmp('upgradeMonitorGrid').getStore().load();

    },

    onUpgradeMonitorGridSelect: function(rowmodel, record, index, eOpts) {
        var grid=Ext.getCmp("upgradeMonitorInstanceGrid");
        grid.getStore().load({
            params:{
                monitorId:record.get("name")
            }
        });

    },


    onUpgradeMonitorInstanceGridScrollerShow: function() {
        if (scroller && scroller.scrollEl) {
            scroller.clearManagedListeners(); 
            scroller.mon(scroller.scrollEl, 'scroll', scroller.onElScroll, scroller); 
        }
    },
    
    onTreeviewItemClick: function(me, record, item, index, e, eOpts) {
    	var id = record.data.id;
    	var ids =  record.data.id.split("*");
    	
		if(!Ext.alarm){
	    	Ext.alarm = {};
	    }else{
	    	Ext.alarm.newBusinessType = ids[ids.length-1];
        }
		Ext.getCmp("treeSearch").setValue('');
    	Ext.apply(Ext.getCmp("menuTree").store.proxy.extraParams,{queryFlag:"0",parentId:id});
    	Ext.getCmp("menuTree").getStore().load();
    },
    
    onBeforeTreeItemExpend:function(record, eOpts){
    	var rootId = record.data.pid;
    	var id = record.data.id;
    	    	
    	if('businessTree'==rootId){
    		if(!Ext.alarm){
            	Ext.alarm = {};
            }else{
            	Ext.alarm.businessType = record.data.id;
            }
    	}
        Ext.apply(Ext.getCmp("treemenu").store.proxy.extraParams,{businessType:Ext.alarm.businessType,pid:record.data.pid});
    }    
});