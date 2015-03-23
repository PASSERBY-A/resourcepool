Ext.define('performance.view.PerformancePanel', {
    extend: 'Ext.panel.Panel',

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
                    width: 190,
                    layout: {
                        type: 'fit'
                    },
                    closable: false,
                    collapsible: true,
                    hideCollapseTool: false,
                    title: 'Menu',
//                    dockedItems: [
//                                  {
//                                      xtype: 'toolbar',
//                                      dock: 'top',
//                                      items: [
//                                          {
//                                              xtype: 'textfield',
//                                              id: 'treeSearch',
//                                              width: 120
//                                          },
//                                          {
//                                              xtype: 'buttongroup',
//                                              columns: 1,
//                                              items: [
//                                                  {
//                                                      xtype: 'button',
//                                                      iconCls: 'icon-search',
//                                                      text: '检索',
//                                                      listeners: {
//                                                          click: {
//                                                              fn: me.onButtonClick,
//                                                              scope: me
//                                                          }
//                                                      }
//                                                  }
//                                              ]
//                                          }
//                                      ]
//                                  }
//                              ],
                    items: [
                        {
                            xtype: 'treepanel',
                            id: 'performance-menuTree',
                            title: '',
                            animate:false,
                            store: Ext.create('performance.store.TreeNodes'),
                            rootVisible: true
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    region: 'center',
                    id: 'performance-tabPanel',
                    layout: {
                        type: 'card'
                    },
                    activeItem: 1,
                    items: [

                    ],
                    dockedItems: [
                                  {
                                      xtype: 'toolbar',
                                      dock: 'top',
                                      items: [
											{
					                            xtype: 'tbspacer',
					                            width: 10
					                        },
					                        {
											    xtype: 'label',
											    itemId: 'labelTitle',
											    style: 'font-weight:bold;',
											    text: '  '
											},
											{
					                            xtype: 'tbspacer',
					                            width: 30
					                        }
                                      ]
                                  }
                              ]
                }
               
            ] 
        });

        me.callParent(arguments);
    },

    onButtonClick:function(button, e, eOpts){
    	alert('search');
    }
});