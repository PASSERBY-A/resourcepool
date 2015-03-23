Ext.define('MyApp.view.MyWindow', {
    extend: 'Ext.window.Window',

    height: 312,
    id: 'deviceListWin',
    width: 460,
    title: avmon.ireport.selectDisplayAttributes,
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
                    store: 'DeviceAttributeStore',
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            width: 150,
                            dataIndex: 'attr_name',
                            text: avmon.ireport.properties
                        },
                        {
                            xtype: 'gridcolumn',
                            width: 150,
                            dataIndex: 'attr_caption',
                            text: avmon.ireport.propertiesName
                        }
                    ],
                    selModel: Ext.create('Ext.selection.CheckboxModel', {

                    }),
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'textfield',
                                    width: 180,
                                    itemId:'ip',
                                    fieldLabel: avmon.ireport.deviceIP,
                                    labelWidth: 50
                                },
                                {
                                    xtype : 'combobox',
									store : Ext.create('Ext.data.Store', {
												fields : ['value', 'display'],
												data : [{
															'value' : 'HP-UX',
															'display' : 'HP-UX'
														}, {
															'value' : 'Linux',
															'display' : 'Linux'
														}, {
															'value' : 'AIX',
															'display' : 'AIX'
														}, {
															'value' : 'WINDOWS',
															'display' : 'WINDOWS'
														}, {
															'value' : 'Solaris',
															'display' : 'Solaris'
														}]
											}),
									queryMode : 'local',
									editable : false,
									valueField : 'value',
									displayField : 'display',
									width: 180,
                                    itemId:'os',
                                    fieldLabel: avmon.ireport.system,
                                    labelWidth: 70
                                },
                                {
                                    xtype: 'button',
                                    icon: 'export.gif',
                                    text: avmon.ireport.generateReport,
                                    listeners: {
                                        click: {
                                            fn: me.onButtonClick,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                    ,
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
    onButtonClick: function(button, e, options) {
        var toolBar = button.ownerCt;

        var ip = toolBar.down("#ip").rawValue;
        
        var os = toolBar.down("#os").rawValue;
        
        var deviceAttrGrid = button.ownerCt.ownerCt;
        var selection = deviceAttrGrid.getSelectionModel().getSelection();

        if(selection.length == 0){ 
            Ext.MessageBox.alert(avmon.common.reminder,avmon.ireport.pleaseSelectAttributeOfExportReport);
            return; 
        }else{

            var attrs = []; 
            Ext.each(selection,function(item){ 
                attrs.push(item.data.attr_name); 
            });

            var progressBar=Ext.Msg.wait("",avmon.common.reminder,{text:avmon.ireport.loading});

            var parmstr = "ip|java.lang.String|" + ip + ";os|java.lang.String|" + os + ";" +
            		"attribute|java.lang.String|'" + attrs.join(",").replace(new RegExp(",","gm"),"','") + "'";
            Ext.devicelist.parmstr = parmstr;
            //alert(parmstr);
            //展示报表
            $.ajax({
                type: "POST",
                url: "../source/config/report-html.jsp",
                data: "reportId="+Ext.devicelist.reportId+"&type=html&params=" + parmstr,
                contentType: "application/x-www-form-urlencoded; charset=UTF-8",
                success: function(data){
                    //alert(data);
                    if(data == '' || data == null || data == undefined){
                    }else{
                        data = data.replace(new RegExp('font-size: 10px', 'g'), 'font-size: 14px');

                        Ext.getCmp('contentPanel').update(data);
                        Ext.getCmp('contentPanel').down("toolbar").show();
                        progressBar.hide();

                    }   		        	
                }
            });

            button.ownerCt.ownerCt.ownerCt.close();
        }
    },

    onGridpanelAfterRender: function(abstractcomponent, options) {
        abstractcomponent.store.load();
    }

});