Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',
    layout: {
        type: 'fit'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    reloadView: function(moId) {},
                    id: 'resourceListPanel',
                    width: 150,
                    layout: {
                        type: 'fit'
                    },
                    title: '',
                    items: [
                        {
                            xtype: 'dataview',
                            id: 'resourceListGrid',
                            autoScroll: true,
                            emptyText: avmon.dashboard.noSubclassInfomation,
                            itemSelector: 'div.thumb-wrap',
                            itemTpl: [
                                '<tpl for=".">',
                                '    <div class="thumb-wrap" >',
                                '        <tpl if="nodeType==\'type\'">',
                                '            <div class="resourceStatusBox_{moType}_GROUP" >',
                                '                ',
                                '                <div class="{iconCls} boxMessage1" >{text}:&nbsp;{itemCount}&nbsp;</div>',
                                '                <div class="icon-alarm-level{maxAlarmLevel} boxMessage2">'+ avmon.dashboard.alarm +':&nbsp;{alarmCount}&nbsp;'+ avmon.dashboard.item +'</div>',
                                '            </div>',
                                '        </tpl>',
                                '',
                                '        <tpl if="nodeType!=\'type\'">',
                                '            <div class="resourceStatusBox_{moType}" >',
                                '                ',
                                '                <div class="{iconCls} boxMessage1" >{text}</div>',
                                '                <div class="icon-alarm-level{maxAlarmLevel} boxMessage2">'+ avmon.dashboard.alarm +':&nbsp;{alarmCount}&nbsp;'+ avmon.dashboard.item +'</div>',
                                '            </div>',
                                '        </tpl>',
                                '        ',
                                '    </div>',
                                '   ',
                                '</tpl>'
                            ],
                            overItemCls: 'x-view-over',
                            store: 'Resources',
                            trackOver: true,
                            listeners: {
                                itemdblclick: {
                                    fn: me.onResourceListGridItemDblClick,
                                    scope: me
                                }
                            }
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onResourceListGridItemDblClick: function(dataview, record, item, index, e, eOpts) {
        if(window.parent.selectPerformanceTreeNode){
            window.parent.selectPerformanceTreeNode(record.get("id"));
        }
        if(window.parent.window.parent.selectPerformanceTreeNode){
            window.parent.window.parent.selectPerformanceTreeNode(record.get("id"));
        }
    }
});