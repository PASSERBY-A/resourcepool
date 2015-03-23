Ext.define('MyApp.view.NewAlarmGrid', {
    extend: 'Ext.grid.Panel',
    alias: 'widget.newAlarmGrid',
    height: 210,
    id: 'newAlarmGrid',
    width: 260,
    title: avmon.dashboard.latestWarning,
    store: 'Alarms',
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            viewConfig: {
                loadMask: false
            },
            columns: [
                {
                    xtype: 'actioncolumn',
                    width: 25,
                    dataIndex: 'CURRENT_GRADE',
                    items: [
                        {
                            getClass: function(v, metadata, r, rowIndex, colIndex, store) {
                                var s= "icon-alarm-level0";
                                var a=r.get("CURRENT_GRADE");
                                if(a==1) s="icon-alarm-level1";
                                if(a==2) s="icon-alarm-level2";
                                if(a==3) s="icon-alarm-level3";
                                if(a==4) s="icon-alarm-level4";
                                return s;

                            }
                        }
                    ]
                },
                {
                    xtype: 'gridcolumn',
                    width: 80,
                    dataIndex: 'FIRST_OCCUR_TIME',
                    text: avmon.config.time
                },
                {
                    xtype: 'gridcolumn',
                    width: 130,
                    dataIndex: 'TITLE',
                    text: avmon.dashboard.content
                }
            ]
        });
        me.callParent(arguments);
    }
});