Ext.define('MyApp.view.MyTreePanel', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.mytreepanel',

    width: 150,
    title: avmon.kpiCompare.hostList,
    store: 'HostTreeStore',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            viewConfig: {

            }
        });

        me.callParent(arguments);
    }

});