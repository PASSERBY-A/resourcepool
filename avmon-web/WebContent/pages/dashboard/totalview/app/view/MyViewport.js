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
                    reloadView: function(moId) {
                    },
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
                                '',
                                '            <div class="resourceStatusBox resourceStatusBox1" >',
                                '                <div class="kpiBox" >',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.alarmDevices +': <font color=red style="font-weight:bold;">{HOSTKPI1}</font></span></div>',
                                '',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.healthDegree +': <font color=red style="font-weight:bold;">{HOSTKPI2}</font></span></div>',
                                '',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.availability +': <font color=red style="font-weight:bold;">{HOSTKPI3}</font></span></div>',
                                '                </div>',
                                '                <div class="resourceTitle"><a href="javascript:showDeviceList(\'host\',\'{BIZ}\');">'+ avmon.config.host +'：{HOSTITEMCOUNT} </a></div>',
                                '            </div> ',
                                '',
                                '            <div class="resourceStatusBox resourceStatusBox2" >',
                                '                <div class="kpiBox" >',
                                '                <div class="kpiLine ">-</div>',
                                '                <div class="kpiLine ">-</div>',
                                '                <div class="kpiLine ">-</div>',
                                '                </div>',
                                '                <div class="resourceTitle">'+ avmon.dashboard.database +'：0'+ '' +'</div>',
                                '            </div>',
                                '',
                                '            <div class="resourceStatusBox resourceStatusBox3" >',
                                '                <div class="kpiBox" >',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.alarmDevices +': <font color=red style="font-weight:bold;">{SNMPKPI1}</font> </span></div>',
                                '',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.healthDegree +': <font color=red style="font-weight:bold;">{SNMPKPI2}</font> </span></div>',
                                '',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.availability +': <font color=red style="font-weight:bold;">{SNMPKPI3}</font> </span></div>',
                                '                </div>',
                                '                <div class="resourceTitle">'+ avmon.dashboard.network +'：{SNMPITEMCOUNT}</div>',
                                '            </div>',
                                '',
                                '            <div class="resourceStatusBox resourceStatusBox4" >',
                                '                <div class="kpiBox" >',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.alarmDevices +': <font color=red style="font-weight:bold;">{STOREKPI1}</font> </span></div>',
                                '',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.healthDegree +': <font color=red style="font-weight:bold;">{STOREKPI2}</font> </span></div>',
                                '',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.availability +': <font color=red style="font-weight:bold;">{STOREKPI3}</font> </span></div>',
                                '                </div>',
                                '                <div class="resourceTitle"><a href="javascript:showDeviceList(\'storage\',\'{BIZ}\');">'+ avmon.dashboard.storage +'：{STOREITEMCOUNT} </a></div>',
                                '            </div>',
                                '',
                                '            <div class="resourceStatusBox resourceStatusBox5" >',
                                '                <div class="kpiBox" >',
                                '                <div class="kpiLine ">-</div>',
                                '                <div class="kpiLine ">-</div>',
                                '                <div class="kpiLine ">-</div>',
                                '                </div>',
                                '                <div class="resourceTitle">'+ avmon.dashboard.middleware +'：0'+'' +'</div>',
                                '            </div>',
                                '',
                                '            <div class="resourceStatusBox resourceStatusBox6" >',
                                '                <div class="kpiBox" >',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.alarmDevices +': <font color=red style="font-weight:bold;">{VMHOSTKPI1}</font> </span></div>',
                                '',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.healthDegree +': <font color=red style="font-weight:bold;">{VMHOSTKPI2}</font> </span></div>',
                                '',
                                '                    <div class="kpiLine "><span>'+ avmon.dashboard.availability +': <font color=red style="font-weight:bold;">{VMHOSTKPI3}</font> </span></div>',
                                '                </div>',
                                '                <div class="resourceTitle"><a href="javascript:showDeviceList(\'vmhost\',\'{BIZ}\');">'+ avmon.dashboard.vmhost +'：{VMHOSTITEMCOUNT} </a></div>',
                                '            </div>',
                                '',
                                '        <div class="businessTitle" >',
                                '            {BIZ}',
                                '        </div>',
                                '    </div>',
                                '</tpl>'
                            ],
                            overItemCls: 'x-view-over',
                            store: 'Resources',
                            trackOver: true
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});