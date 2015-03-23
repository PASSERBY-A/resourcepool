Ext.define('MyApp.view.MyViewport', {
    extend: 'Ext.container.Viewport',
    requires: [
        'Ext.grid.Panel',
        'Ext.grid.View',
        'Ext.grid.column.Column',
        'Ext.XTemplate'
    ],
    id: 'iloHostViewport',
    layout: {
        type: 'vbox',
        align: 'stretch'
    },
    initComponent: function() {
        var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'container',
                    flex: 1,
                    height: 184,
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            flex: 1.1,
                            autoScroll: true,
                            title: avmon.dashboard.cpu,
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'panel',
                                    flex: 1,
                                    border: false,
                                    autoScroll: true,
                                    layout: {
                                        type: 'hbox',
                                        align: 'stretch'
                                    },
                                    items: [
                                        {
                                            xtype: 'gridpanel',
                                            flex: 1.2,
                                            autoShow: true,
                                            itemId: 'cpuCommList',
                                            width: 95,
                                            bodyBorder: true,
                                            forceFit: true,
                                            hideHeaders: true,
                                            rowLines: false,
                                            store: 'CpuCommJsonStore',
                                            viewConfig: {
                                                autoScroll: false
                                            },
                                            columns: [
                                                {
                                                    xtype: 'gridcolumn',
                                                    width: 50,
                                                    defaultWidth: 50,
                                                    dataIndex: 'key'
                                                },
                                                {
                                                    xtype: 'gridcolumn',
                                                    renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                                        var cpuStatus=record.data.value;
                                                        if(cpuStatus=='ok')
                                                        {
                                                            return "<img src='./image/status.png'/>";
                                                        }
                                                        else if(cpuStatus=='NA'){
                                                            return "<img src='./image/status1.png'/>";
                                                        }else{
                                                            return cpuStatus;
                                                        }

                                                    },
                                                    width: 50,
                                                    defaultWidth: 50,
                                                    dataIndex: 'value'
                                                }
                                            ]
                                        },
                                        {
                                            xtype: 'dataview',
                                            flex: 0.8,
                                            itemId: 'cpuList',
                                            shrinkWrap: 3,
                                            autoScroll: true,
                                            itemSelector: 'div .mometer',
                                            itemTpl: [
                                                '  <div class="mometer">  ',
                                                '  ',
                                                '   <div style="text-align: center;">',
                                                '		{cpuTemp}<br>',
                                                '        <tpl if="alarm==\'true\'">',
                                                '            <img src="./image/cpugreen.png"><br>',
                                                '        </tpl>',
                                                '        <tpl if="alarm==\'false\'">',
                                                '            <img src="./image/cpugray.png"><br>',
                                                '        </tpl>',
                                                '       {cpu}',
                                                '   </div>',
                                                ' </div>'
                                            ],
                                            store: 'CpuUsageStore'
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            flex: 1,
                            height: 212,
                            autoScroll: true,
                            layout: 'fit',
                            title: avmon.dashboard.memory,
                            items: [
                                {
                                    xtype: 'dataview',
                                    itemId: 'memList',
                                    autoScroll: true,
                                    itemSelector: 'div .mem',
                                    itemTpl: [
                                        '<div class="mem" style="float:left;width: 33%;text-align: center;overflow:hidden">',
                                        '<tpl if="alarm==\'false\'">',
                                        '    <span title="{label}">{label}</span><br>           ',
                                        '    <img src="./image/redmem.png"><br>',
                                        '    <span>'+avmon.dashboard.size+'：{size}</span><br>',
                                        '    <span>'+avmon.dashboard.speed+'：{speed}</span>            ',
                                        '</tpl>',
                                        '    ',
                                        '<tpl if="alarm==\'true\'">',
                                        '    <span title="{label}">{label}</span><br>            ',
                                        '    <img src="./image/bluemem.png"><br>            ',
                                        '    <span>'+avmon.dashboard.size+'：{size}</span><br>',
                                        '    <span>'+avmon.dashboard.speed+'：{speed}</span>       ',
                                        '</tpl>',
                                        '<tpl if="alarm==\'none\'">    ',
                                        '    <span title="{label}">{label}</span><br>',
                                        '    <img src="./image/redgrey.png"><br>',
                                        '    <span>'+avmon.dashboard.size+'：{size}</span><br>',
                                        '    <span>'+avmon.dashboard.speed+'：{speed}</span><br>',
                                        '</tpl>',
                                        '</div>'
                                    ],
                                    store: 'MemJsonStore'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            flex: 0.9,
                            height: 212,
                            layout: 'fit',
                            title: avmon.dashboard.disk,
                            items: [
                                {
                                    xtype: 'dataview',
                                    itemId: 'diskList',
                                    autoScroll: true,
                                    emptyText: avmon.dashboard.notRelatedDataCanDisplay,
                                    itemSelector: 'div .mometer',
                                    itemTpl: [
                                        '<div class="mometer">',
                                        ' <div style="float:left;text-align: center;">     ',
                                        '	<tpl if="alarm==\'false\'">',
                                        '        {diskTemp}<br/>',
                                        '        <img src="./image/cputempred.png"><br/>',
                                        '        {disk}<br/>',
                                        '        <img src="./image/diskgray.png"><br>',
                                        '	</tpl>',
                                        '    <tpl if="alarm==\'true\'">',
                                        '        {diskTemp}<br/>',
                                        '        <img src="./image/cputempgreen.png"><br/>',
                                        '        {disk}<br/>',
                                        '        <img src="./image/diskgreen.png"><br>',
                                        '    </tpl>',
                                        '    </div>',
                                        '</div>'
                                    ],
                                    store: 'DiskJsonStore'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            flex: 1,
                            height: 212,
                            layout: 'fit',
                            title: avmon.dashboard.networkCard,
                            items: [
                                {
                                    xtype: 'dataview',
                                    itemId: 'netcardList',
                                    autoScroll: true,
                                    itemSelector: 'div .netcard',
                                    itemTpl: [
                                        '<div class="netcard" style="float:left;width:50%;overflow:hidden">  ',
                                        '    <font>{nic}</font><br>',
                                        '    <img src="./image/netcard.png" width="58px" heigth="58px"><br>',
                                        '    <span title=\'{type}\'>'+avmon.config.type+'：{type}</span><br/>',
                                        '    <div style="width:100%;overflow:hidden" title=\'{type}\'>MAC：{mac}</div>    ',
                                        '</div>'
                                    ],
                                    store: 'NetcardJsonStore'
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'container',
                    flex: 1,
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            flex: 1.1,
                            height: 212,
                            autoScroll: true,
                            manageHeight: false,
                            title: avmon.dashboard.powerSupply,
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'gridpanel',
                                    flex: 1,
                                    border: 'none',
                                    frameHeader: false,
                                    header: false,
                                    title: avmon.dashboard.powerSupply,
                                    emptyText: avmon.dashboard.notRelatedDataCanDisplay,
                                    forceFit: true,
                                    hideHeaders: true,
                                    store: 'PowerCommJsonStore',
                                    viewConfig: {
                                        itemId: 'powerCommList'
                                    },
                                    columns: [
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'key'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            renderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
                                                var powerStatus=record.data.value;
                                                if(powerStatus=='on'||powerStatus=='not_redundant')
                                                {
                                                    return "<img src='./image/status.png'/>";
                                                }
                                                else{
                                                    return "<img src='./image/status1.png'/>";
                                                }

                                            },
                                            dataIndex: 'value'
                                        }
                                    ]
                                },
                                {
                                    xtype: 'dataview',
                                    flex: 1.6,
                                    itemId: 'powerList',
                                    autoScroll: true,
                                    emptyText: avmon.dashboard.notRelatedDataCanDisplay,
                                    itemSelector: 'div .power',
                                    itemTpl: [
                                        '<div class="power" style="float: left;width:49%;overflow:hidden;text-align: center;">    ',
                                        '    <span style="text-align:center;">{temp}</span>',
                                        '    <tpl if="alarm==\'on\'">',
                                        '        <div>',
                                        '            <img src="./image/cputempgreen.png">',
                                        '        </div>',
                                        '    </tpl>',
                                        '    <tpl if="alarm==\'off\'">',
                                        '        <div>',
                                        '            <img src="./image/cputempgray.png">',
                                        '        </div>',
                                        '    </tpl>',
                                        '    <tpl if="status==\'on\'">',
                                        '        <div>',
                                        '            <img src="./image/powergreen.png">',
                                        '        </div>',
                                        '    </tpl>',
                                        '    <tpl if="status==\'off\'">',
                                        '        <div>',
                                        '            <img src="./image/powerred.png">',
                                        '        </div>',
                                        '    </tpl>',
                                        '    <div title="{power}" style="overflow:hidden">{power}</div>',
                                        '</div>'
                                    ],
                                    store: 'PowerStore'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            flex: 2.9,
                            autoScroll: true,
                            title: avmon.dashboard.mainBoard,
                            layout: {
                                type: 'hbox',
                                align: 'stretch'
                            },
                            items: [
                                {
                                    xtype: 'dataview',
                                    flex: 3,
                                    itemId: 'boardList',
                                    autoScroll: true,
                                    itemSelector: 'div .board',
                                    itemTpl: [
                                        '<tpl for=".">',
                                        '    ',
                                        '        <div class="board" style="float:left;width:16%;overflow:hidden">',
                                        '            {name}</br></br>',
                                        '        		<div><img src="./image/board.png"></div>',
                                        '        	<div>',
                                        '            	<span>'+avmon.config.type+'：{type}</span><br>',
                                        '            	<span>'+avmon.dashboard.width+'：{width}</span>',
                                        '        	</div>',
                                        '    	</div>',
                                        '	',
                                        '</tpl>'
                                    ],
                                    store: 'BoardJsonStore'
                                }
                            ]
                        }
                    ]
                },
                {
                    xtype: 'container',
                    flex: 1,
                    height: 250,
                    width: 400,
                    layout: {
                        type: 'hbox',
                        align: 'stretch'
                    },
                    items: [
                        {
                            xtype: 'panel',
                            flex: 1.1,
                            title: 'BIOS',
                            items: [
                                {
                                    xtype: 'gridpanel',
                                    itemId: 'biosList',
                                    margin: 5,
                                    hideHeaders: true,
                                    store: 'BiosJsonStore',
                                    columns: [
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'key'
                                        },
                                        {
                                            xtype: 'gridcolumn',
                                            dataIndex: 'value'
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            flex: 1.9,
                            autoScroll: true,
                            layout: 'fit',
                            title: avmon.dashboard.fan,
                            items: [
                                {
                                    xtype: 'dataview',
                                    itemId: 'fanList',
                                    autoScroll: true,
                                    emptyText: avmon.dashboard.notRelatedDataCanDisplay,
                                    itemSelector: 'div .fan',
                                    itemTpl: [
                                        '<div class="fan" style="width:16.6%;overflow:hidden">',
                                        '    {name}<br>',
                                        '    <tpl if="status==\'ok\'">',
                                        '        <img src="./image/fan2.png"><br>',
                                        '    </tpl>',
                                        '    <tpl if="status!=\'ok\'">',
                                        '        <img width="10%" height="8%" src="./image/fan1.png"><br>',
                                        '    </tpl>',
                                        '    '+avmon.dashboard.rotateSpeed+'：{speed}<br>',
                                        '    <div title="{location}" style="width:100%;overflow:hidden">'+avmon.alarm.position+'：{location}</div>',
                                        '</div>'
                                    ],
                                    store: 'FanJsonStore'
                                }
                            ]
                        },
                        {
                            xtype: 'panel',
                            flex: 1,
                            autoScroll: true,
                            title: avmon.dashboard.crate,
                            items: [
                                {
                                    xtype: 'dataview',
                                    itemId: 'caseList',
                                    itemSelector: 'div .mometer',
                                    itemTpl: [
                                        '<div class="mometer" style="width:100%;">    ',
                                        '    <div style="width:40%;padding-left:3px;float:left;margin-top:10%">',
                                        '        <div>{caseTemp}</div>',
                                        '        <div style="heigth：50%"></div>',
                                        '    </div>',
                                        '    <div style="margin-top:10%">',
                                        '        <tpl if="alarm==\'ok\'">',
                                        '        	<img src="./image/disktempgreen.png">',
                                        '        </tpl>    ',
                                        '        <tpl if="alarm==\'no\'">',
                                        '            <img src="./image/disktempred.png">',
                                        '        </tpl>    ',
                                        '    </div>',
                                        '</div>'
                                    ],
                                    store: 'CaseJsonStore'
                                }
                            ]
                        }
                    ]
                }
            ]
        });

        me.callParent(arguments);
    }

});