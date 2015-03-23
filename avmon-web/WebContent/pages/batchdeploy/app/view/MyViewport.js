Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',

    requires: [
        'MyApp.view.DiscoveryGrid'
    ],

    id: 'main',
    layout: {
        type: 'card'
    },

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    id: 'panel1',
                    layout: {
                        type: 'absolute'
                    },
                    title: avmon.batchdeploy.batchDeploy,
                    items: [
                        {
                            xtype: 'label',
                            x: 40,
                            y: 30,
                            height: 30,
                            width: 390,
                            text: avmon.batchdeploy.firstStep
                        },
                        {
                            xtype: 'progressbar',
                            x: 50,
                            y: 200,
                            hidden: true,
                            id: 'progressBar',
                            width: 440
                        },
                        {
                            xtype: 'textfield',
                            x: 50,
                            y: 100,
                            id: 'startIp',
                            fieldLabel: avmon.batchdeploy.startIp,
                            labelWidth: 50,
                            emptyText: '192.168.1.1'
                        },
                        {
                            xtype: 'textfield',
                            x: 280,
                            y: 100,
                            id: 'endIp',
                            fieldLabel: avmon.batchdeploy.stopIp,
                            labelWidth: 50,
                            emptyText: '192.168.1.255'
                        },
                        {
                            xtype: 'button',
                            x: 50,
                            y: 150,
                            height: 30,
                            id: 'btnScanAgent',
                            width: 110,
                            iconCls: 'icon-search',
                            text: avmon.batchdeploy.startScan
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    id: 'panel2',
                    layout: {
                        type: 'border'
                    },
                    title: '扫描结果',
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    height: 22,
                                    id: 'btnDeploy',
                                    iconCls: 'icon-add',
                                    text: avmon.batchdeploy.startDeploy
                                }
                            ]
                        }
                    ],
                    items: [
                        {
                            xtype: 'discoveryGrid',
                            flex: 2,
                            region: 'center'
                        },
                        {
                            xtype: 'gridpanel',
                            flex: 1,
                            region: 'south',
                            height: 150,
                            id: 'deployResultGrid',
                            title: 'Detail',
                            store: 'DeployResults',
                            viewConfig: {

                            },
                            columns: [
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'moId',
                                    text: avmon.batchdeploy.equipmentID
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'monitorInstanceId',
                                    text: avmon.batchdeploy.monitorInstance
                                },
                                {
                                    xtype: 'gridcolumn',
                                    dataIndex: 'monitor',
                                    text: avmon.batchdeploy.monitor
                                },
                                {
                                    xtype: 'gridcolumn',
                                    width: 376,
                                    dataIndex: 'msg',
                                    text: avmon.batchdeploy.message
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'panel',
                    id: 'panel3',
                    title: 'My Panel'
                }
            ]
        });

        me.callParent(arguments);
    }

});