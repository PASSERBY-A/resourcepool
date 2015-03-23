Ext.define('MyApp.view.DiscoveryGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.discoveryGrid',

    id: 'grid',
    title: '',
    store: 'DiscoveryStore',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            viewConfig: {

            },
            features: [
                {
                    ftype: 'grouping',
                    hideGroupedHeader: true
                }
            ],
            columns: [
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'moId',
                    text: avmon.batchdeploy.equipmentID
                },
                {
                    xtype: 'gridcolumn',
                    width: 234,
                    dataIndex: 'caption',
                    text: avmon.batchdeploy.equipment
                },
                {
                    xtype: 'gridcolumn',
                    width: 169,
                    dataIndex: 'type',
                    text: 'avmon.batchdeploy.resourceType'
                },
                {
                    xtype: 'gridcolumn',
                    width: 139,
                    dataIndex: 'ip',
                    text: avmon.batchdeploy.ipaddress
                },
                {
                    xtype: 'gridcolumn',
                    align: 'center',
                    dataIndex: 'status',
                    text: avmon.batchdeploy.status
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'os',
                    text: avmon.batchdeploy.operatingSystem
                },
                {
                    xtype: 'gridcolumn',
                    dataIndex: 'version',
                    text: avmon.batchdeploy.version
                }
            ],
            selModel: Ext.create('Ext.selection.CheckboxModel', {
                mode: 'SIMPLE',
                checkOnly: false
            })
        });

        me.callParent(arguments);
    }

});