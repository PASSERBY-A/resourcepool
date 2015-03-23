Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',
    border: 0,
    layout: {
        type: 'fit'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'panel',
                    border: 0,
                    frame: false,
                    id: 'mainEnginePanel',
                    autoScroll: true,
                    layout: {
                        type: 'fit'
                    },
                    title: '',
                    items: [
                        {
                            xtype: 'dataview',
                            frame: false,
                            id: 'deviceList',
                            itemSelector: 'div.thumb-wrap',
                            itemTpl: [
                                '<tpl for=".">',
                                '    <div class="thumb-wrap " >',
                                '        <tpl if="CURRENT_GRADE != \'5\'">',
                                '            <div class="thumb" style="width:120px;height:75px;background-image:url(\'image/host_status_good_back.png\');background-repeat:no-repeat!important;">',
                                '                <span style="width:95px;font-size:11px;color:red;margin-left: 23px;overflow:hidden;">',
                                '                    <tpl if="CURRENT_GRADE == \'1\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level0.png\'></img>',
                                '                    </tpl>',
                                '                    <tpl if="CURRENT_GRADE == \'2\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level1.png\'></img>',
                                '                    </tpl>',
                                '                    <tpl if="CURRENT_GRADE == \'3\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level2.png\'></img>',
                                '                    </tpl>',
                                '                    <tpl if="CURRENT_GRADE == \'4\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level3.png\'></img>',
                                '                    </tpl>',
                                '                    <tpl if="CURRENT_GRADE == \'5\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level4.png\'></img>',
                                '                    </tpl>',
                                '                    {MO_NAME}',
                                '                </span>',
                                '',
                                '                <tpl if="SYSLOG == \'0\'">',
                                '                    <span style="font-size:8px;color:green;padding-left:0px;padding-top:0px;margin-left: 40px;">',
                                '                        SysLog OK',
                                '                    </span>',
                                '                </tpl>',
                                '                <tpl if="SYSLOG != \'0\'">',
                                '                    <span style="font-size:8px;color:red;padding-left:0px;padding-top:0px;margin-left: 38px;">',
                                '                        SysLog Error',
                                '                    </span>',
                                '                </tpl>',
                                '    ',
                                '                <div >',
                                '                    <div class="BarText" >CPU:</div>',
                                '                    <tpl if="CPU_LEVEL == \'5\'">',
                                '                        <div class="ProgressBar2">  ',
                                '                            <div style="width: {CPU}%;"><span>{CPU}%</span></div>  ',
                                '                        </div>',
                                '                    </tpl>',
                                '                    <tpl if="CPU_LEVEL != \'5\'">',
                                '                        <div class="ProgressBar1">  ',
                                '                            <div style="width: {CPU}%;"><span>{CPU}%</span></div>  ',
                                '                        </div>',
                                '                    </tpl>    ',
                                '                </div>',
                                '',
                                '                <div >',
                                '                    <div class="BarText" >MEM:</div>',
                                '                    <tpl if="MEM_LEVEL == \'5\'">',
                                '                        <div class="ProgressBar2">  ',
                                '                            <div style="width: {MEM}%;"><span>{MEM}%</span></div>  ',
                                '                        </div> ',
                                '                    </tpl>',
                                '                    <tpl if="MEM_LEVEL != \'5\'">',
                                '                        <div class="ProgressBar1">  ',
                                '                            <div style="width: {MEM}%;"><span>{MEM}%</span></div>  ',
                                '                        </div>',
                                '                    </tpl>    ',
                                '                </div>',
                                '    ',
                                '                <div>',
                                '                    <div class="BarText" >SWAP:</div>',
                                '                    <tpl if="DISK_LEVEL == \'5\'">',
                                '                        <div class="ProgressBar2">  ',
                                '                            <div style="width: {DISK}%;"><span>{DISK}%</span></div>  ',
                                '                        </div> ',
                                '                    </tpl>',
                                '                    <tpl if="DISK_LEVEL != \'5\'">',
                                '                        <div class="ProgressBar1">  ',
                                '                            <div style="width: {DISK}%;"><span>{DISK}%</span></div>  ',
                                '                        </div>',
                                '                    </tpl>    ',
                                '                </div>',
                                '            </div>',
                                '        </tpl>',
                                '    ',
                                '        <tpl if="CURRENT_GRADE == \'5\'">',
                                '            <div class="thumb" style="width:120px;height:75px;background-image:url(\'image/host_status_bad_back.png\');background-repeat:no-repeat!important;">',
                                '                <span style="width:95px;font-size:11px;color:red;margin-left: 23px;overflow:hidden;">',
                                '                    <tpl if="CURRENT_GRADE == \'1\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level0.png\'></img>',
                                '                    </tpl>',
                                '                    <tpl if="CURRENT_GRADE == \'2\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level1.png\'></img>',
                                '                    </tpl>',
                                '                    <tpl if="CURRENT_GRADE == \'3\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level2.png\'></img>',
                                '                    </tpl>',
                                '                    <tpl if="CURRENT_GRADE == \'4\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level3.png\'></img>',
                                '                    </tpl>',
                                '                    <tpl if="CURRENT_GRADE == \'5\'">',
                                '                        <img style=\'width:16px; height:16px;\' src=\'image/level4.png\'></img>',
                                '                    </tpl>',
                                '                    {MO_NAME}',
                                '                </span>',
                                '',
                                '                <tpl if="SYSLOG == \'0\'">',
                                '                    <span style="font-size:8px;color:green;padding-left:0px;padding-top:0px;margin-left: 40px;">',
                                '                        SysLog OK',
                                '                    </span>',
                                '                </tpl>',
                                '                <tpl if="SYSLOG != \'0\'">',
                                '                    <span style="font-size:8px;color:red;padding-left:0px;padding-top:0px;margin-left: 38px;">',
                                '                        SysLog Error',
                                '                    </span>',
                                '                </tpl>',
                                '                ',
                                '    ',
                                '                <div >',
                                '                    <div class="BarText" >CPU:</div>',
                                '                    <tpl if="CPU_LEVEL == \'5\'">',
                                '                        <div class="ProgressBar2">  ',
                                '                            <div style="width: {CPU}%;"><span>{CPU}%</span></div>  ',
                                '                        </div>',
                                '                    </tpl>',
                                '                    <tpl if="CPU_LEVEL != \'5\'">',
                                '                        <div class="ProgressBar1">  ',
                                '                            <div style="width: {CPU}%;"><span>{CPU}%</span></div>  ',
                                '                        </div>',
                                '                    </tpl>    ',
                                '                </div>',
                                '    ',
                                '                <div >',
                                '                    <div class="BarText" >MEM:</div>',
                                '                    <tpl if="MEM_LEVEL == \'5\'">',
                                '                        <div class="ProgressBar2">  ',
                                '                            <div style="width: {MEM}%;"><span>{MEM}%</span></div>  ',
                                '                        </div> ',
                                '                    </tpl>',
                                '                    <tpl if="MEM_LEVEL != \'5\'">',
                                '                        <div class="ProgressBar1">  ',
                                '                            <div style="width: {MEM}%;"><span>{MEM}%</span></div>  ',
                                '                        </div>',
                                '                    </tpl>    ',
                                '                </div>',
                                '    ',
                                '                <div>',
                                '                    <div class="BarText" >SWAP:</div>',
                                '                    <tpl if="DISK_LEVEL == \'5\'">',
                                '                        <div class="ProgressBar2">  ',
                                '                            <div style="width: {DISK}%;"><span>{DISK}%</span></div>  ',
                                '                        </div> ',
                                '                    </tpl>',
                                '                    <tpl if="DISK_LEVEL != \'5\'">',
                                '                        <div class="ProgressBar1">  ',
                                '                            <div style="width: {DISK}%;"><span>{DISK}%</span></div>  ',
                                '                        </div>',
                                '                    </tpl>    ',
                                '                </div>',
                                '            </div>',
                                '        </tpl>',
                                '    </div>',
                                '</tpl>'
                            ],
                            loadMask: false,
                            overItemCls: 'x-view-over',
                            singleSelect: true,
                            store: 'MainEngineStore',
                            listeners: {
                                itemdblclick: {
                                    fn: me.onDeviceListItemDblClick,
                                    scope: me
                                }
                            }
                        }
                    ],
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            height: 30,
                            items: [
                                {
                                    xtype: 'label',
                                    text: avmon.config.deviceStatus
                                },
                                {
                                    xtype: 'tbspacer',
                                    width: 30
                                },
                                {
                                    xtype: 'radiogroup',
                                    id: 'condition',
                                    width: 860,
                                    fieldLabel: '',
                                    labelWidth: 70,
                                    items: [
                                        {
                                            xtype: 'radiofield',
                                            name: 'condition',
                                            boxLabel: avmon.dashboard.severeAlarmInHalfAnHour,
                                            labelWidth:280,
                                            checked: false,
                                            inputValue: '1'
                                        },
                                        {
                                            xtype: 'radiofield',
                                            name: 'condition',
                                            boxLabel: avmon.dashboard.severeAlarmInAnHour,
                                            labelWidth:200,
                                            inputValue: '2'
                                        },
                                        {
                                            xtype: 'radiofield',
                                            name: 'condition',
                                            fieldLabel: '',
                                            boxLabel: avmon.dashboard.allOfSevereAlarm,
                                            inputValue: '3'
                                        },
                                        {
                                            xtype: 'radiofield',
                                            name: 'condition',
                                            fieldLabel: '',
                                            boxLabel: avmon.dashboard.allDevice,
                                            checked: true,
                                            inputValue: '4'
                                        }
                                    ],
                                    listeners: {
                                        change: {
                                            fn: me.onFilterConditionChange,
                                            scope: me
                                        }
                                    }
                                }
                            ]
                        }
                    ]
                }
            ]
        });
        me.callParent(arguments);
    },
    onDeviceListItemDblClick: function(dataview, record, item, index, e, options) {
        if(window.parent.selectPerformanceTreeNode){
            window.parent.selectPerformanceTreeNode(record.get("MO_ID"));
        }
        else{
            if(window.parent.window.parent.selectPerformanceTreeNode){
                window.parent.window.parent.selectPerformanceTreeNode(record.get("MO_ID"));
            }
        }
    },
    onFilterConditionChange: function(field, newValue, oldValue, options) {
        if (newValue.condition.length == 1) {
            Ext.devicestatus.condition = newValue.condition;
            var deviceList = Ext.getCmp('deviceList');
            var deviceStoreProxy = deviceList.getStore().getProxy();
            deviceStoreProxy.extraParams.mo = Ext.devicestatus.mo;
            deviceStoreProxy.extraParams.condition = Ext.devicestatus.condition;
            deviceList.getStore().load();
        }
    }
});