Ext.define('CFG.view.DeviceWindow', {
    extend: 'Ext.window.Window',
    height: 312,
    width: 500,
    title: avmon.config.chooseDevice,
    modal: true,
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'gridpanel',
                    autoShow: false,
                    disabled: false,
                    frame: false,
                    height: 280,
                    autoScroll: true,
                    bodyBorder: false,
                    title: '',
                    store: Ext.create('CFG.store.DeviceStore'),//'DeviceStore',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            width: 113,
                            dataIndex: 'device_ip',
                            text: avmon.config.deviceIP
                        },
//                        {
//                            xtype: 'gridcolumn',
//                            width: 264,
//                            dataIndex: 'report_date',
//                            text: '最新报表时间'
//                        },
                        {
                            xtype: 'gridcolumn',
                            width: 120,
                            dataIndex: 'device_type',
                            text: avmon.config.deviceType
                        },
                        {
                            xtype: 'gridcolumn',
                            width: 195,
                            dataIndex: 'device_desc',
                            text: avmon.config.deviceDescription
                        }
                    ],
                    selModel: Ext.create('Ext.selection.CheckboxModel', {}),
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'textfield',
                                    width: 180,
                                    fieldLabel: avmon.config.deviceIP,
                                    labelWidth: 50
                                },
                                {
                                    xtype: 'button',
                                    icon: '../resources/images/button/search2.gif',
                                    text: avmon.config.search,
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick1,
                                            scope: me
                                        }
                                    }
                                },
                                {
                                    xtype: 'tbseparator'
                                },
                                {
                                    xtype: 'button',
                                    icon: '../resources/images/button/ok.gif',
                                    text: avmon.config.choose,
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ],
                    listeners: {
                        afterrender: {
                            fn: me.onGridpanelAfterRender,
                            scope: me
                        }
                    }
                }
            ]
        });
        me.callParent(arguments);
    },
    onButtonClick1: function(button, e, options) {
        var toolBar = button.ownerCt;
        var grid = button.ownerCt.ownerCt;
        var ip = toolBar.items.items[0].rawValue;
        var regExp = new RegExp(".*" + ip + ".*");  
        // 执行检索   
        grid.store.filterBy(function(record,id){    
            // 得到每个record的项目名称值   
            var text = record.get("device_ip");    
            return regExp.test(text);   
        });  
    },
    onButtonClick: function(button, e, options) {
        var deviceGrid = button.ownerCt.ownerCt;
        var selection = deviceGrid.getSelectionModel().getSelection();
        if(selection.length == 0){ 
            Ext.MessageBox.alert(avmon.common.reminder,avmon.config.pleaseChooseDevice);
            return; 
        }else{
            var ips = []; 
            Ext.each(selection,function(item){ 
                ips.push(item.data.device_ip); 
            });
            var ip = ips.join(",");
            Ext.getCmp("device_ip").setValue(ip);
            button.ownerCt.ownerCt.ownerCt.close();
        }
    },
    onGridpanelAfterRender: function(abstractcomponent, options) {
        abstractcomponent.store.load();
    }
});