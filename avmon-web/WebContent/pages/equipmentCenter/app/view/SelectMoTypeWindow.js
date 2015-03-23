Ext.Loader.setPath('Ext.ux', 'commons');
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging',  
    'Ext.ux.ComboBoxTree', 
]); 

var treeStore = Ext.create('store.moTypeTrees', {
    //extend: 'Ext.data.TreeStore',
    // alias: 'store.moTypeTrees',

    requires: [
        'app.model.MoType'
    ],

    constructor: function(cfg) {
        var me = this;
        cfg = cfg || {};
        me.callParent([Ext.apply({
            model: 'app.model.MoType',
            //storeId: 'moTypeTrees',
            proxy: {
                type: 'ajax',
                url: 'moTypeTree',
                reader: {
                    type: 'json'
                }
            }
        }, cfg)]);
    }
});

var sysModule = Ext.create("Ext.ux.ComboBoxTree", {
			id : 'parent_id',
			name : 'parent_id',
			anchor: '100%',
			store : treeStore,
			fieldLabel : '父类',
			editable : false,
			multiCascade : false,
			multiSelect : false,
			width: 360,
			labelWidth:60,
			labelStyle : 'margin-top:5px',
			treeHeight:250,
			rootVisible : false,
			allowBlank: false
		});

var protocolStatus = Ext.create('Ext.form.field.ComboBox', {
	id : 'protocol',
	name : 'protocol',
	width: 360,
	labelWidth:60,
	fieldLabel:avmon.equipmentCenter.protocol,
	xtype : 'combobox',
	store : Ext.create('Ext.data.Store', {
				fields : ['value', 'display'],
				data : [{
							'value' : 'AGENT',
							'display' : 'AGENT'
						}, {
							'value' : 'AGENTLESS',
							'display' : 'AGENTLESS'
						}, {
							'value' : 'SNMP',
							'display' : 'SNMP'
						}]
				}),
	queryMode : 'local',
	editable : false,
	valueField : 'value',
	displayField : 'display',
	allowBlank: false
});


Ext.define('app.view.SelectMoTypeWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.selectMoTypeWindow',
    
    height: 416,
    width: 422,
    layout: {
        type: 'fit'
    },
    closeAction: 'hide',
    title: avmon.equipmentCenter.addMonitoringObjects,

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    frame: true,
                    height: 420,
                    width: 381,
                    layout: 'anchor',
                    defaults: {
                        anchor: '100%'
                    },
                    // The fields
                    defaultType: 'textfield',
                    bodyPadding: 10,
                    title: '',
                    items: [
                        {
                            xtype: 'label',
                            text: avmon.equipmentCenter.chooseObjectType
                        },
                        {
                            xtype: 'treepanel',
                            height: 200,
                            id: 'moTypeTree',
                            width: 360,
                            title: '',
                            store: 'MoTypeTrees',
                            rootVisible: false,
                            viewConfig: {

                            }
                        },
                        sysModule,
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            fieldLabel: avmon.equipmentCenter.objectNumber,
                            labelWidth: 60,
                            name: 'moId',
                            allowBlank: false
                        },
                        {
                            xtype: 'label',
                            anchor: '100%',
                            style : 'margin-left:64px;',
                            text: avmon.equipmentCenter.objectNumberCannotBeModified
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            fieldLabel: avmon.equipmentCenter.objectName,
                            labelWidth: 60,
                            name: 'caption',
                            allowBlank: false
                        },
                        {
                            xtype: 'textfield',
                            anchor: '100%',
                            hidden: true,
                            fieldLabel: 'Agent ID',
                            labelWidth: 60,
                            name: 'agentId',
                            allowBlank: false,
                            emptyText: avmon.equipmentCenter.receivingPerformanceData
                        },
                        protocolStatus
                    ],
                    buttons: [{
                        id: 'btnSelectMoTypeWindowOk',
                        iconCls: 'icon-ok',
                        text: avmon.common.ok
                    },
                    {
                        xtype: 'button',
                        iconCls: 'icon-cancel',
                        text: avmon.common.cancel,
                        listeners: {
                            click: {
                                fn: me.onButtonClick,
                                scope: me
                            }
                        }
                    }]
                }
            ]
        });

        me.callParent(arguments);
    },

    onButtonClick: function(button, e, eOpts) {
        Ext.selectMoTypeWindow.hide();
    }

});