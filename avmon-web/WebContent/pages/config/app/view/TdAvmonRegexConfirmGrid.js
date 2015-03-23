//Ext.Loader.setPath('Ext.ux', 'extjs4/resources/ux'); 
Ext.require([ 
    'Ext.grid.*', 
    'Ext.toolbar.Paging'
]); 

var gridStore = Ext.create('CFG.store.TdAvmonRegexConfirmGridStore');
Ext.define('CFG.view.TdAvmonRegexConfirmGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.TdAvmonRegexConfirmGrid',
    initComponent: function() {
        Ext.apply(this, {
            id: 'TdAvmonRegexConfirm_grid',
            title: avmon.config.AlarmValidationRules,
            iconCls: 'icon-grid',
            border: false,
            columns: [
				{
				    text: 'id',
				    width: 60,
				    sortable: false,
				    dataIndex: 'id'
				},
                 {
	                text: avmon.config.monitoringObject,
	                width: 100,
	                sortable: false,
	                dataIndex: 'mo'
	            }
				,{
	                text: avmon.alarm.alarmLevel,
	                width: 100,
	                sortable: false,
	                dataIndex: 'grade'
	            }
				,{
	                text: 'KPI',
	                width: 100,
	                sortable: false,
	                dataIndex: 'kpi'
	            }
				,{
	                text: avmon.config.alarmContentRegularExpression,
	                width: 300,
	                sortable: false,
	                dataIndex: 'content'
	            },
				{
	                text: avmon.config.module,
	                width: 120,
	                sortable: false,
	                dataIndex: 'module'
	            },
				{
	                text: avmon.config.verifyStatus,
	                width: 120,
	                sortable: true,
	                dataIndex: 'status'
	            }
            ],
            store: gridStore,
            dockedItems: [{
                xtype: 'toolbar',
                items: [{
                    text: avmon.config.validationRules,
                    iconCls: 'icon-add',
                    handler: function() {
                    	gridStore.proxy.extraParams.check='true';
//                    	gridStore.proxy.extraParams.start='1';
                    	gridStore.currentPage = 1;
                    	gridStore.load();
                    }
                }] 
            },{
                xtype: 'pagingtoolbar',
                store: gridStore,
                dock: 'bottom',
                displayInfo: true,
                beforePageText : avmon.config.beforePageText,
    			afterPageText : avmon.config.afterPageText,
                displayMsg: avmon.config.displayMsg, 
                emptyMsg: avmon.config.emptyMsg 
            }]

        });
        this.callParent(arguments);
    }
});