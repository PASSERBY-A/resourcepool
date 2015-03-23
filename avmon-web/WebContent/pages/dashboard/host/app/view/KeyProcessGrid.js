Ext.define('MyApp.view.KeyProcessGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.keyProcessGrid',
    height: 230,
    id: 'keyProcessGrid',
    width: 260,
    title: avmon.dashboard.keyProcesses,
    store: 'Processes',
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            viewConfig: {
                loadMask: false
            },
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'NAME',
                    text: avmon.dashboard.process
                },
                {
                    xtype: 'gridcolumn',
                    width: 45,
                    dataIndex: 'CPU',
                    text: 'CPU'
                },
                {
                    xtype: 'gridcolumn',
                    width: 45,
                    dataIndex: 'MEM',
                    text: 'MEM'
                },
                {
                    xtype: 'gridcolumn',
                    width: 40,
                    dataIndex: 'STATUS',
                    text: avmon.config.status
                }
            ]
        });
        me.callParent(arguments);
    }
});