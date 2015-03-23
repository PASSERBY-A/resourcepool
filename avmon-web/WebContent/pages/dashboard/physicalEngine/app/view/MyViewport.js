Ext.define(
				'MyApp.view.MyViewport',
				{
					extend : 'Ext.container.Viewport',
					layout : {
						align : 'stretch',
						type : 'vbox'
					},
					initComponent : function() {
						var me = this;
						Ext.applyIf(
										me,
										{
											items : [ {
												xtype : 'panel',
												flex : 2,
												layout : {
													align : 'stretch',
													type : 'hbox'
												},
												items : [
														{
															xtype : 'container',
															flex : 1,
															width : 250,
															layout : {
																align : 'stretch',
																type : 'vbox'
															},
															items : [
																	{
																		xtype : 'panel',
																		flex : 1,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		title : '',
																		items : [
										
																				{
																					xtype : 'toolbar',
																					height : 26,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								width : 334,
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								margin : '4,0,0,0',
																								text : 'Host'
																							},
																							{
																								xtype : 'label',
																								height : 12,
																								text : ''
																							} ]
																				},
																				{
																					xtype : 'panel',
																					flex : 1,
																					layout : {
																						align : 'stretch',
																						type : 'hbox'
																					},
																					title : '',
																					items : [
																							{
																								xtype : 'gridpanel',
																								flex : 1,
																								border : false,
																								id : 'basicInfoGrid',
																								padding : 5,
																								autoScroll : true,
																								frameHeader : false,
																								header : false,
																								forceFit : true,
																								hideHeaders : true,
																								rowLines : false,
																								store : 'BasicInfoStore',
																								viewConfig : {
																									border : false
																								},
																								columns : [
																										{
																											xtype : 'gridcolumn',
																											width : 100,
																											dataIndex : 'KEY',
																											text : ''
																										},
																										{
																											xtype : 'gridcolumn',
																											width : 100,
//																											defaultWidth : 200,
																											dataIndex : 'VAL',
																											text : ''
																										} ]
																							},
																							{
																								xtype : 'panel',
																								border : false,
																								width : 40,
																								bodyPadding : 5,
																								title : '',
																								items : [ {
																									xtype : 'image',
																									border : false,
																									height : 30,
																									id : 'hostImage',
																									width : 30,
																									src : 'images/SYS_Others.png'
																								} ]
																							} ]
																				}

																		]
																	},
																	{
																		xtype : 'panel',
																		flex : 1,
																		width : 250,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		title : '',
																		items : [
																				{
																					xtype : 'toolbar',
																					height : 28,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								width : 334,
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								margin : '4,0,0,0',
																								text : 'Network'
																							},
																							{
																								xtype : 'label',
																								height : 12,
																								text : ''
																							} ]
																				},
																				{
																					xtype : 'gridpanel',
																					flex : 1,
																					border : false,
																					id : 'networkGrid',
																					padding : 10,
																					autoScroll : true,
																					frameHeader : false,
																					header : false,
																					forceFit : true,
																					hideHeaders : true,
																					rowLines : false,
																					store : 'NetworkInfoStore',
																					viewConfig : {
																						border : false
																					},
																					columns : [
																							{
																								xtype : 'gridcolumn',
																								width : 100,
																								dataIndex : 'KEY',
																								text : ''
																							},
																							{
																								xtype : 'gridcolumn',
																								width : 100,
																								defaultWidth : 200,
																								dataIndex : 'VAL',
																								text : ''
																							} ]
																				}
																		]
														
																	} ]
														},
														{
															xtype : 'container',
															width : 120,
															layout : {
																align : 'stretch',
																type : 'vbox'
															},
															items : [
																	{
																		xtype : 'panel',
																		flex : 2,
																		border : false
																	},
																	{
																		xtype : 'panel',
																		border : false,
																		height : 120,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		items : [
																				{
																					xtype : 'panel',
																					border : false,
																					height : 25,
																					width : 120,
																					items : [ {
																						xtype : 'chart',
																						height : 48,
																						id : 'networkLineSend',
																						width : 150,
																						shadow : false,
																						animate : true,
																						insetPadding : 0,
																						store : 'NetworkSendStore',
																						axes : [
																								{
																									type : 'Category',
																									fields : [ 'time' ],
																									position : 'bottom'
																								},
																								{
																									type : 'Numeric',
																									dashSize : 0,
																									fields : [ 'usage' ],
																									position : 'right',
																									maximum : 100,
																									minimum : 0
																								} ],
																						series : [ {
																							type : 'line',
																							xField : 'time',
																							yField : [ 'usage' ],
																							showMarkers : false,
																							smooth : 3
																						} ]
																					} ]
																				},
																				{
																					xtype : 'panel',
																					flex : 1,
																					border : false,
																					layout : {
																						type : 'fit'
																					},
																					items : [ {
																						xtype : 'image',
																						id : 'networkSendPic',
																						src : 'images/arrow_G_01.gif'
																					} ]
																				},
																				{
																					xtype : 'form',
																					flex : 1,
																					border : false,
																					id : 'networkSendForm',
																					items : [ {
																						xtype : 'displayfield',
																						name : 'speed_text',
																						value : '40 k/s',
																						fieldLabel : 'Send',
																						labelWidth : 40
																					} ]
																				},
																				{
																					xtype : 'panel',
																					border : false,
																					height : 25,
																					width : 120,
																					items : [ {
																						xtype : 'chart',
																						height : 48,
																						id : 'networkLineReceive',
																						width : 150,
																						shadow : false,
																						animate : true,
																						insetPadding : 0,
																						store : 'NetworkReceiveStore',
																						axes : [
																								{
																									type : 'Category',
																									dashSize : 0,
																									fields : [ 'time' ],
																									position : 'bottom'
																								},
																								{
																									type : 'Numeric',
																									dashSize : 0,
																									fields : [ 'usage' ],
																									position : 'right',
																									maximum : 100,
																									minimum : 0
																								} ],
																						series : [ {
																							type : 'line',
																							xField : 'time',
																							yField : [ 'usage' ],
																							showMarkers : false,
																							smooth : 3
																						} ]
																					} ]
																				},
																				{
																					xtype : 'panel',
																					flex : 1,
																					border : false,
																					layout : {
																						type : 'fit'
																					},
																					items : [ {
																						xtype : 'image',
																						id : 'netwrokReceivePic',
																						src : 'images/arrow_R_01.gif'
																					} ]
																				},
																				{
																					xtype : 'form',
																					flex : 1,
																					border : false,
																					id : 'networkReceiveForm',
																					items : [ {
																						xtype : 'displayfield',
																						name : 'speed_text',
																						value : '24 k/s',
																						fieldLabel : 'Receive',
																						labelWidth : 40
																					} ]
																				} ]
																	} ]
														},
														{
															xtype : 'container',
															flex : 1,
															layout : {
																align : 'stretch',
																type : 'vbox'
															},
															items : [
															         {
																		xtype : 'panel',
																		flex : 1,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		title : '',
																		items : [
																		{
																			xtype : 'toolbar',
																			height : 28,
																			dock : 'top',
																			items : [
																					{
																						xtype : 'label',
																						width : 334,
																						style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																						margin : '4,0,0,0',
																						text : 'MEM'
																					},
																					{
																						xtype : 'label',
																						height : 12,
																						text : ''
																					} ]
																		},
																		{
																			xtype : 'panel',
																			flex : 1,
																			layout : {
																				align : 'stretch',
																				type : 'hbox'
																			},
																			bodyBorder : false,
																			title : '',
																			items : [
																					{
																						xtype : 'form',
																						flex : 2,
																						border : false,
																						id : 'memForm',
																						height: '280px',
																						bodyPadding : 5,
																						items : [
																								{
																									xtype : 'displayfield',
																									fieldLabel : avmon.dashboard.memory,
																									labelWidth : 30,
																									name : 'FREE_MEM'
																								},
																								{
																									xtype : 'displayfield',
																									fieldLabel : avmon.dashboard.usage,
																									labelWidth : 40,
																									name : 'MEM_SYS',
																									value : ''
																								},
																								{
																									xtype : 'displayfield',
																									fieldLabel : '',
																									labelWidth : 60,
																									name : ''
																								},
																								{
																									xtype : 'displayfield',
																									fieldLabel : '',
																									labelWidth : 60,
																									name : '',
																									value : ''
																								}
																								]
																					},
																					{
																						xtype : 'panel',
																						id : 'memPanel',
																						flex : 1,
																						border : false,
																						layout : {
																							type : 'fit'
																						}
																					}
																					
																					]
																			} ]
																		},
																		{
																			xtype : 'panel',
																			flex : 1,
																			layout : {
																				align : 'stretch',
																				type : 'vbox'
																			},
																			title : '',
																			items : [
																					
																					{
																						xtype : 'toolbar',
																						height : 28,
																						dock : 'top',
																						items : [
																								{
																									xtype : 'label',
																									width : 334,
																									style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																									margin : '4,0,0,0',
																									text : 'CPU'
																								},
																								{
																									xtype : 'label',
																									height : 12,
																									text : ''
																								} ]
																					},
																					{
																						xtype : 'panel',
																						id : 'gaugePanel',
																						flex : 1,
																						layout : {
																							type : 'fit'
																						},
																						title : ''
																					} ]
																		}
																		         
																		         ]
															},
														{
															xtype : 'container',
															width : 120,
															layout : {
																align : 'stretch',
																type : 'vbox'
															},
															items : [
																		{
																			xtype : 'panel',
																			height : 65,
																			border : false
																		},
																	{
																		xtype : 'panel',
																		border : false,
																		height : 120,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		items : [
																				{
																					xtype : 'splitter',
																					height : 5
																				},
																				{
																					xtype : 'panel',
																					border : false,
																					height : 25,
																					items : [ {
																						xtype : 'chart',
																						height : 48,
																						id : 'diskWriteChart',
																						width : 150,
																						shadow : false,
																						animate : true,
																						insetPadding : 0,
																						store : 'MemSendStore',
																						axes : [
																								{
																									type : 'Category',
																									dashSize : 0,
																									fields : [ 'time' ],
																									position : 'bottom'
																								},
																								{
																									type : 'Numeric',
																									dashSize : 0,
																									fields : [ 'usage' ],
																									position : 'right',
																									maximum : 100,
																									minimum : 0
																								} ],
																						series : [ {
																							type : 'line',
																							xField : 'time',
																							yField : [ 'usage' ],
																							showMarkers : false,
																							smooth : 3
																						} ]
																					} ]
																				},
																				{
																					xtype : 'panel',
																					flex : 1,
																					border : false,
																					layout : {
																						type : 'fit'
																					},
																					items : [ {
																						xtype : 'image',
																						id : 'diskWritePic',
																						src : 'images/arrow_G_01.gif'
																					} ]
																				},
																				{
																					xtype : 'form',
																					flex : 1,
																					border : false,
																					id : 'diskWriteForm',
																					items : [ {
																						xtype : 'displayfield',
																						name : 'speed_text',
																						value : '5 k/s',
																						fieldLabel : 'Write',
																						labelWidth : 40
																					} ]
																				},
																				{
																					xtype : 'panel',
																					border : false,
																					height : 25,
																					items : [ {
																						xtype : 'chart',
																						height : 48,
																						id : 'diskReadChart',
																						width : 150,
																						shadow : false,
																						animate : true,
																						insetPadding : 0,
																						store : 'MemReceiveStore',
																						axes : [
																								{
																									type : 'Category',
																									dashSize : 0,
																									fields : [ 'time' ],
																									position : 'bottom'
																								},
																								{
																									type : 'Numeric',
																									dashSize : 0,
																									fields : [ 'usage' ],
																									position : 'right',
																									maximum : 100,
																									minimum : 0
																								} ],
																						series : [ {
																							type : 'line',
																							xField : 'time',
																							yField : [ 'usage' ],
																							showMarkers : false,
																							smooth : 3
																						} ]
																					} ]
																				},
																				{
																					xtype : 'panel',
																					flex : 1,
																					border : false,
																					layout : {
																						type : 'fit'
																					},
																					items : [ {
																						xtype : 'image',
																						id : 'diskReadPic',
																						src : 'images/arrow_R_01.gif'
																					} ]
																				},
																				{
																					xtype : 'form',
																					flex : 1,
																					border : false,
																					id : 'diskReadForm',
																					items : [ {
																						xtype : 'displayfield',
																						name : 'speed_text',
																						value : '11 k/s',
																						fieldLabel : 'Read',
																						labelWidth : 40
																					} ]
																				} ]
																	},
																	{
																		xtype : 'panel',
																		flex : 1,
																		border : false
																	} ]
														},
														{
															xtype : 'container',
															flex : 1,
															layout : {
																align : 'stretch',
																type : 'vbox'
															},
															items : [ {
																xtype : 'panel',
																flex : 1,
																layout : {
																	align : 'stretch',
																	type : 'vbox'
																},
																title : 'Disk',
																items : [ {
																	xtype : 'dataview',
																	id : 'bucketMemData',
																	itemSelector : 'div.thumb-wrap',
																	itemTpl : [
																		'      <tpl for=".">',
																		'    <div class="thumb-wrap">',
																		'            <div style="float:left;margin-top:0px;margin-left:0px;margin-right:5px;margin-bottom: 5px;">',
																		'      <div style="width:30px;margin-left: 0.2em;margin-top: 0.2em;">{name}<br/></div>',
																		'		<iframe scrolling="auto" frameborder="0" width="70px" height="100px" src="{src}"></iframe>',
																		'      <div style="width:60px;margin-left: 0.2em;margin-top: 0.2em;">{usage}<br/></div>',
																		'            </div>',
																		'    </div>',
																		'      </tpl>' ],
																	emptyText : avmon.dashboard.notFindPartOfRealTimeData,
																	store : 'imagesStore'
																}
																
																]
															} ]
														} ]
											} ]
										});

						me.callParent(arguments);
					}

				});