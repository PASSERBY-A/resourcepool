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
												xtype : 'container',
												border : false,
												height : 511,
												layout : {
													align : 'stretch',
													type : 'hbox'
												},
												autoScroll : true,
												bodyBorder : false,
												items : [
														{
															xtype : 'container',
															flex : 2,
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
																					height : 20,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								text : 'Basic Info'
																							},
																							'->',
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
																											text : avmon.deploy.properties
																										},
																										{
																											xtype : 'gridcolumn',
																											width : 100,
																											dataIndex : 'VAL',
																											text : avmon.config.value
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
																					height : 20,
																					dock : 'top',
																					items : [ {
																						xtype : 'label',
																						style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																						text : 'Log Error:'
																					}
																					]
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
																								xtype : 'panel',
																								flex : 1,
																								border : false,
																								layout : {
																									align : 'center',
																									type : 'vbox'
																								},
																								items : [
																										{
																											xtype : 'panel',
																											id : 'leftLogPanel_0',
																											flex : 1,
																											border : false,
																											layout : {

																												type : 'fit'
																											},
																											bodyPadding : '5 5 5 5',
																											items : [ {
																												xtype : 'label',
																												id : 'leftLogLabel',
																												text : ''
																											} ]
																										},
																										{
																											xtype : 'panel',
																											id : 'leftLogPanel',
																											flex : 2,
																											border : false,
																											layout : {},
																											items : [ {
																												xtype : 'image',
																												border : false,
																												height : 50,
																												id : 'leftLogImage',
																												width : 28
																											} ]
																										},
																										{
																											xtype : 'panel',
																											id : 'leftLogPanel_1',
																											flex : 1,
																											border : false,
																											layout : {

																												type : 'fit'
																											},
																											items : [ {
																												xtype : 'label',
																												id : 'leftLogNum',
																												text : ''
																											} ]
																										} ]
																							},
																							{
																								xtype : 'panel',
																								flex : 1,
																								border : false,
																								layout : {
																									align : 'center',
																									type : 'vbox'
																								},
																								items : [
																										{
																											xtype : 'panel',
																											id : 'centerLogPanel_0',
																											flex : 1,
																											border : false,
																											layout : {
																												type : 'fit'
																											},
																											bodyPadding : '5 5 5 5',
																											items : [ {
																												xtype : 'label',
																												id : 'centerLogLabel',
																												text : ''
																											} ]
																										},
																										{
																											xtype : 'panel',
																											id : 'centerLogPanel',
																											flex : 2,
																											border : false,
																											layout : {},
																											items : [ {
																												xtype : 'image',
																												border : false,
																												height : 50,
																												id : 'centerLogImage',
																												width : 28
																											} ]
																										},
																										{
																											xtype : 'panel',
																											id : 'centerLogPanel_1',
																											flex : 1,
																											hidden : true,
																											border : false,
																											layout : {
																												type : 'fit'
																											},
																											items : [ {
																												xtype : 'label',
																												id : 'centerLogNum',
																												text : ''
																											} ]
																										},
																										{
																											xtype : 'panel',
																											id : 'centerLogPanel_2',
																											flex : 1,
																											hidden : true,
																											border : false,
																											layout : {
																												type : 'fit'
																											},
																											listeners : {
																												click : {
																													element : 'el',
																													fn : me.onLogClick,
																													scope : me
																												}
																											},
																											items : [ {
																												xtype : 'label',
																												id : 'centerLogNum',
																												text : ''
																											} ]
																										} ]
																							},
																							{
																								xtype : 'panel',
																								flex : 1,
																								border : false,
																								layout : {
																									align : 'center',
																									type : 'vbox'
																								},
																								items : [
																										{
																											xtype : 'panel',
																											id : 'rightLogPanel_0',
																											flex : 1,
																											border : false,
																											layout : {
																												type : 'fit'
																											},
																											bodyPadding : '5 5 5 5',
																											items : [ {
																												xtype : 'label',
																												id : 'rightLogLabel',
																												text : ''
																											} ]
																										},
																										{
																											xtype : 'panel',
																											id : 'rightLogPanel',
																											flex : 2,
																											border : false,
																											layout : {},
																											items : [ {
																												xtype : 'image',
																												border : false,
																												height : 50,
																												id : 'rightLogImage',
																												width : 28
																											} ]
																										},
																										{
																											xtype : 'panel',
																											id : 'rightLogPanel_1',
																											flex : 1,
																											border : false,
																											layout : {
																												type : 'fit'
																											},
																											items : [ {
																												xtype : 'label',
																												id : 'rightLogNum',
																												text : ''
																											} ]
																										} ]
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
																					height : 20,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								text : 'Network'
																							},
																							'->',
																							{
																								xtype : 'label',
																								height : 12,
																								text : ''
																							} ]
																				},
																				{
																					xtype : 'gridpanel',
																					border : false,
																					padding : 5,
																					id : 'networkGrid',
																					flex : 1,
																					layout : {},
																					autoScroll : true,
																					forceFit : true,
																					store : 'NetworkStore',
																					viewConfig : {
																						listeners : {
																							itemdblclick : {
																								fn : me.onViewItemDblClick,
																								scope : me
																							}
																						}
																					},
																					columns : [
																							{
																								xtype : 'gridcolumn',
																								dataIndex : 'NAME',
																								width : 75,
																								text : avmon.dashboard.networkCardName
																							},
																							{
																								xtype : 'gridcolumn',
																								renderer : function(
																										value,
																										metaData,
																										record,
																										rowIndex,
																										colIndex,
																										store,
																										view) {
																									var str;

																									if (value != null
																											&& value != '') {
																										str = "status_"
																												+ value
																												+ ".png";
																										return "<img style='width:16px; height:16px;' src='images/"
																												+ str
																												+ "'></img>";
																									}

																								},
																								dataIndex : 'STATUS',
																								width : 35,
																								text : avmon.dashboard.networkCardStatus
																							},
																							{
																								xtype : 'gridcolumn',
																								dataIndex : 'IO',
																								width : 100,
																								text : avmon.dashboard.networkFlowPKGTS
																							} ]
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
																		flex : 1,
																		border : false
																	},
																	{
																		xtype : 'panel',
																		border : false,
																		height : 200,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		bodyPadding : '75 5 15 5',
																		items : [
																				{
																					xtype : 'panel',
																					height : 15,
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
																					items : [
																							{
																								xtype : 'displayfield',
																								fieldLabel : avmon.dashboard.cumulativeSendPKGTS,
																								labelWidth : 90,
																								name : '',
																								value : ''
																							},
																							{
																								xtype : 'displayfield',
																								fieldLabel : '',
																								labelWidth : 80,
																								name : 'speed_text',
																								value : ''
																							} ]
																				},
																				{
																					xtype : 'panel',
																					height : 15,
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
																					items : [
																							{
																								xtype : 'displayfield',
																								fieldLabel : avmon.dashboard.cumulativeReceivePKGTS,
																								labelWidth : 90,
																								name : '',
																								value : ''
																							},
																							{
																								xtype : 'displayfield',
																								fieldLabel : '',
																								labelWidth : 40,
																								name : 'speed_text',
																								value : ''
																							} ]
																				} ]
																	} ]
														},
														{
															xtype : 'container',
															flex : 2,
															border : false,
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
																					height : 20,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								text : 'Progress'
																							},
																							'->',
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
																								xtype : 'form',
																								flex : 2,
																								border : false,
																								id : 'courseForm',
																								bodyPadding : 5,
																								items : [
																										{
																											xtype : 'displayfield',
																											anchor : '100%',
																											fieldLabel : avmon.dashboard.userProcessesNumber,
																											labelWidth : 80,
																											name : 'USR_PROC_NUM',
																											value : ''
																										},
																										{
																											xtype : 'displayfield',
																											anchor : '100%',
																											fieldLabel : avmon.dashboard.precessPercentage,
																											labelWidth : 70,
																											name : 'USR_PROC_USAGE',
																											value : ''
																										} ]
																							},
																							{
																								xtype : 'panel',
																								id : 'progressPanel',
																								flex : 1,
																								border : false,
																								layout : {
																									type : 'fit'
																								}
																							} ]
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
																					height : 20,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								text : 'MEM'
																							},
																							'->',
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
																								bodyPadding : 5,
																								items : [
																										{
																											xtype : 'displayfield',
																											fieldLabel : avmon.dashboard.residualMemory,
																											labelWidth : 60,
																											name : 'FREE_MEM'
																										},
																										{
																											xtype : 'displayfield',
																											fieldLabel : avmon.dashboard.usage,
																											labelWidth : 60,
																											name : 'MEM_SYS',
																											value : ''
																										} ]
																							},
																							{
																								xtype : 'panel',
																								id : 'memPanel',
																								flex : 1,
																								border : false,
																								layout : {
																									type : 'fit'
																								}
																							} ]
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
																					height : 20,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								text : 'CPU'
																							},
																							'->',
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
															border : true,
															width : 120,
															layout : {
																align : 'stretch',
																type : 'vbox'
															},
															items : [
																	{
																		xtype : 'panel',
																		border : false,
																		height : 120,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		bodyPadding : '10 5 5 5',
																		items : [
																				{
																					xtype : 'panel',
																					height : 15,
																					border : false,
																					layout : {
																						type : 'fit'
																					},
																					items : [ {
																						xtype : 'image',
																						id : 'skipfileInPic',
																						src : 'images/arrow_G_01.gif'
																					} ]
																				},
																				{
																					xtype : 'form',
																					flex : 1,
																					border : false,
																					id : 'skipFileInForm',
																					items : [
																							{
																								xtype : 'displayfield',
																								fieldLabel : avmon.dashboard.writePS,
																								labelAlign : 'left',
																								labelWidth : 90,
																								name : '',
																								value : ''
																							},
																							{
																								xtype : 'displayfield',
																								fieldLabel : '',
																								labelAlign : 'right',
																								labelWidth : 40,
																								name : 'speed_text',
																								value : ''
																							} ]
																				},
																				{
																					xtype : 'panel',
																					// flex:
																					// 1,
																					height : 15,
																					border : false,
																					layout : {
																						type : 'fit'
																					},
																					items : [ {
																						xtype : 'image',
																						id : 'skipfileOutPic',
																						src : 'images/arrow_R_01.gif'
																					} ]
																				},
																				{
																					xtype : 'form',
																					flex : 1,
																					border : false,
																					id : 'skipFileOutForm',
																					items : [
																							{
																								xtype : 'displayfield',
																								fieldLabel : avmon.dashboard.readPS,
																								labelAlign : 'left',
																								labelWidth : 90,
																								name : '',
																								value : ''
																							},
																							{
																								xtype : 'displayfield',
																								fieldLabel : '',
																								labelAlign : 'right',
																								labelWidth : 47,
																								name : 'speed_text',
																								value : ''
																							} ]
																				} ]
																	},
																	{
																		xtype : 'splitter',
																		height : 40
																	},
																	{
																		xtype : 'panel',
																		border : false,
																		height : 120,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		bodyPadding : '10 5 5 5',
																		items : [
																				{
																					xtype : 'panel',
																					border : false,
																					height : 10
																				},
																				{
																					xtype : 'panel',
																					border : false,
																					height : 15,
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
																					id : 'diskRWForm',
																					items : [
																							{
																								xtype : 'displayfield',
																								fieldLabel : avmon.dashboard.literacyRateRWS,
																								labelAlign : 'left',
																								labelWidth : 90,
																								name : '',
																								value : ''
																							},
																							{
																								xtype : 'displayfield',
																								fieldLabel : '',
																								labelAlign : 'right',
																								labelWidth : 100,
																								name : 'speed_text',
																								value : ''
																							} ]
																				},
																				{
																					xtype : 'panel',
																					border : false,
																					height : 15,
																					layout : {
																						type : 'fit'
																					},
																					items : [ {
																						xtype : 'image',
																						id : 'diskReadPic',
																						src : 'images/arrow_R_01.gif'
																					} ]
																				}
																		]
																	},
																	{
																		xtype : 'panel',
																		flex : 1,
																		border : false
																	} ]
														},
														{
															xtype : 'container',
															flex : 2,
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
																					height : 20,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								text : 'Swap'
																							},
																							'->',
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
																								xtype : 'form',
																								flex : 2,
																								border : false,
																								id : 'swapForm',
																								bodyPadding : 5,
																								items : [
																										{
																											xtype : 'displayfield',
																											anchor : '100%',
																											fieldLabel : avmon.dashboard.path,
																											labelWidth : 50,
																											name : 'SWAP_NAME',
																											value : ''
																										},
																										{
																											xtype : 'displayfield',
																											anchor : '100%',
																											fieldLabel : avmon.dashboard.size,
																											labelWidth : 50,
																											name : 'SWAP_SIZE',
																											value : ''
																										},
																										{
																											xtype : 'displayfield',
																											anchor : '100%',
																											fieldLabel : avmon.dashboard.usage,
																											labelWidth : 50,
																											name : 'SWAP_UTIL'
																										} ]
																							},
																							{
																								xtype : 'panel',
																								id : 'swapPanel',
																								flex : 1,
																								border : false,
																								layout : {
																									type : 'fit'
																								}
																							} ]
																				} ]
																	},
																	{
																		xtype : 'panel',
																		id : '',
																		flex : 1,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		title : '',
																		items : [
																				{
																					xtype : 'toolbar',
																					height : 20,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								text : 'Disk'
																							},
																							'->',
																							{
																								xtype : 'button',
																								height : '20px',
																								text : 'More',
																								listeners : {
																									click : {
																										fn : me.onDiskClick,
																										scope : me
																									}
																								}
																							} ]
																				},
																				{
																					xtype : 'panel',
																					id : '',
																					flex : 1,
																					layout : {
																						align : 'stretch',
																						type : 'hbox'
																					},
																					autoScroll : true,
																					title : '',
																					items : [

																					{
																						xtype : 'dataview',
																						autoScroll : true,
																						id : 'bucketdiskData',
																						itemSelector : 'div.thumb-wrap',
																						itemTpl : [
																								'<tpl for=".">',
																								'   <div style="width:150px">',
																								'   <div class="file" style="float:left;width: 50%;text-align:center">',
																								'           <div style="width:30px;margin-left: 0.5em;margin-top: 0.1em;">{name}</div>',
																								'			<div><iframe scrolling="false" frameborder="0" width="45px" height="70px" src="{src}"></iframe></div>',
																								'      		<div style="width:60px;margin-left: 0.5em;margin-top: 0.1em;">{usage}</div>',
																								'   </div>',
																								'   </div>',
																								'</tpl>' ],
																						emptyText : avmon.dashboard.notFindPartOfRealTimeData,
																						store : 'NewImagesStore'
																					}
																					]
																				}
																		]
																	},
																	{
																		xtype : 'panel',
																		id : '',
																		flex : 1,
																		layout : {
																			align : 'stretch',
																			type : 'vbox'
																		},
																		title : '',
																		autoScroll : true,
																		items : [
																				{
																					xtype : 'toolbar',
																					height : 20,
																					dock : 'top',
																					items : [
																							{
																								xtype : 'label',
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								text : 'File System'
																							},
																							'->',
																							{
																								xtype : 'button',
																								height : '22px',
																								style : 'font-weight:bold;font-size:12px;color:rgb(4, 64, 140);',
																								text : 'More',
																								listeners : {
																									click : {
																										fn : me.onFileSysClick,
																										scope : me
																									}
																								}
																							} ]
																				},
																				{
																					xtype : 'panel',
																					id : '',
																					flex : 1,
																					layout : {
																						align : 'stretch',
																						type : 'hbox'
																					},
																					autoScroll : true,
																					title : '',
																					items : [ {
																						xtype : 'dataview',
																						id : 'bucketfileData',
																						itemSelector : 'div .file',
																						itemTpl : [
																								'<tpl for=".">',
																								'   <div style="width:150px">',
																								'   <div class="file" style="float:left;width: 50%;text-align:center">',
																								'           <div style="width:30px;margin-left: 0.5em;margin-top: 0.1em;">{name}</div>',
																								'			<div><iframe scrolling="false" frameborder="0" width="45px" height="70px" src="{src}"></iframe></div>',
																								'      		<div style="width:60px;margin-left: 0.5em;margin-top: 0.1em;">{usage}</div>',
																								'   </div>',
																								'   </div>',
																								'</tpl>' ],
																						emptyText : avmon.dashboard.notFindPartOfRealTimeData,
																						store : 'NewImagesFileStore'
																					} ]
																				}
																		]
																	} ]
														} ]
											} ]
										});

						me.callParent(arguments);
					},

					onFileSysClick : function(button, e, eOpts){// 把这个事件改成点击更多的事件就可以了
						Ext.define('Image', {
							extend : 'Ext.data.Model',
							fields : [ {
								name : 'imgId',
								type : 'string'
							}, {
								name : 'bucketId',
								type : 'string'
							}, {
								name : 'src',
								type : 'string'
							}, {
								name : 'class',
								type : 'string'
							}, {
								name : 'width',
								type : 'string'
							}, {
								name : 'height',
								type : 'string'
							}, {
								name : 'top',
								type : 'string'
							}
							],
							belongsTo : {
								model : 'Bucket'
							}
						});

						Ext.define('Bucket', {
							extend : 'Ext.data.Model',
							fields : [ {
								name : 'bucketId',
								type : 'string'
							}, {
								name : 'usage',
								type : 'string'
							}, {
								name : 'name',
								type : 'string'
							} ],
							hasMany : {
								model : 'Image',
								name : 'images'
							}
						});
						// 循环输出该控件
						var imageTplFile = new Ext.XTemplate(
								'      <tpl for=".">',
								'    <div class="thumb-wrap">',
								'            <div style="float:left;margin-top:5px;margin-left:5px;margin-right:5px;margin-bottom: 0px;">',
								'      <div style="width:30px;margin-left: 0.5em;margin-top: 0.2em;">{name}<br/></div>',
								'		<iframe scrolling="false" frameborder="0" width="40px" height="60px" src="{src}"></iframe>',
								'      <div style="width:60px;margin-left: 0.5em;margin-top: 0.2em;">{usage}<br/></div>',
								'            </div>', '    </div>',
								'      </tpl>');

						Ext.Ajax
								.request({
									url : 'fileSysUse',
									params : {
										mo : Ext.mainEngine.moId
									},
									success : function(response, opts) {
										var obj = Ext
												.decode(response.responseText);
										// 创建该控件的store
										var imagesStoreFile = Ext.create(
												'Ext.data.Store', {
													// autoLoad: true,
													model : 'Bucket',// 父MODEL
													proxy : {
														type : 'memory',
														data : obj,
														reader : {
															type : 'json',
															root : 'buckets'
														}
													}
												});
										var winFile = Ext
												.create(
														'Ext.window.Window',
														{
															title : 'FileSystem',
															height : 500,
															width : 700,
															bodyStyle : 'background:white;',
															autoScroll : true,
															modal : 'true',
															layout : 'fit',
															items : [ {
																xtype : 'dataview',
																tpl : imageTplFile,
																id : 'bucketFileData',
																itemSelector : 'div.thumb-wrap',
																emptyText : avmon.dashboard.notFindPartOfRealTimeData,
																store : imagesStoreFile
															} ]
														}).center().show();
									},
									failure : function(response, opts) {
										if (console && console.log) {
											console
													.log("load dashboard-mem error!");
										}
									}
								});
					},

					onDiskClick_Old : function(button, e, eOpts) {// 把这个事件改成点击更多的事件就可以了
						Ext.define('Image', {
							extend : 'Ext.data.Model',
							fields : [ {
								name : 'imgId',
								type : 'string'
							}, {
								name : 'bucketId',
								type : 'string'
							}, {
								name : 'src',
								type : 'string'
							}, {
								name : 'class',
								type : 'string'
							}, {
								name : 'width',
								type : 'string'
							}, {
								name : 'height',
								type : 'string'
							}, {
								name : 'top',
								type : 'string'
							}
							],
							belongsTo : {
								model : 'Bucket'
							}
						});
						Ext.define('Bucket', {
							extend : 'Ext.data.Model',
							fields : [ {
								name : 'bucketId',
								type : 'string'
							}, {
								name : 'usage',
								type : 'string'
							}, {
								name : 'name',
								type : 'string'
							} ],
							hasMany : {
								model : 'Image',
								name : 'images'
							}
						});
						// 循环输出该控件
						var imageTpl = new Ext.XTemplate(
								'      <tpl for=".">',
								'    <div class="thumb-wrap">',
								'            <div style="float:left;margin-top:15px;margin-left:30px;margin-right:30px;margin-bottom: 15px;">',
								'      <div style="width:30px;margin-left: 0.5em;margin-top: 1.5em;">{name}<br/></div>',
								'		<iframe scrolling="false" frameborder="0" width="127px" height="110px" src="{src}"></iframe>',
								'      <div style="width:60px;margin-left: 0.5em;margin-top: 0.5em;">{usage}<br/></div>',
								'            </div>', '    </div>',
								'      </tpl>');

						Ext.Ajax.request({
									url : 'diskUse',
									params : {
										moId : Ext.mainEngine.moId
									},
									success : function(response, opts) {
										var obj = Ext.decode(response.responseText);
										// 创建该控件的store
										var imagesStore = Ext.create(
												'Ext.data.Store', {
													autoLoad : true,
													model : 'Bucket',// 父MODEL
													proxy : {
														type : 'memory',
														data : obj,
														reader : {
															type : 'json',
															root : 'buckets'
														}
													}
												});
										var winMem = Ext
												.create(
														'Ext.window.Window',
														{
															title : 'Disk',
															height : 500,
															width : 700,
															bodyStyle : 'background:white;',
															autoScroll : true,
															modal : 'true',
															layout : 'fit',
															items : [ {
																xtype : 'dataview',
																tpl : imageTpl,
																id : 'bucketMemData',
																itemSelector : 'div.thumb-wrap',
																emptyText : avmon.dashboard.notFindPartOfRealTimeData,
																store : imagesStore
															} ]
														}).center().show();
									},
									failure : function(response, opts) {
										if (console && console.log) {
											console
													.log("load dashboard-mem error!");
										}
									}
								});
					},
					onDiskClick : function(button, e, eOpts) {
						var imageTpl = new Ext.XTemplate(
								'      <tpl for=".">',
								'    <div class="thumb-wrap">',
								'            <div style="float:left;margin-top:15px;margin-left:30px;margin-right:30px;margin-bottom: 5px;">',
								'      <div style="width:30px;margin-left: 0.5em;margin-top: 0.2em;">{name}<br/></div>',
								'		<iframe scrolling="false" frameborder="0" width="50px" height="75px" src="{src}"></iframe>',
								'      <div style="width:30px;margin-left: 0.5em;margin-top: 0.2em;">{usage}<br/></div>',
								'            </div>', '    </div>',
								'      </tpl>');
						var winMem = Ext
								.create(
										'Ext.window.Window',
										{
											title : 'Disk',
											height : 320,
											width : 650,
											constrainHeader : true,
											bodyStyle : 'background:white;',
											autoScroll : true,
											modal : 'false',
											layout : 'fit',
											items : [ {
												xtype : 'dataview',
												tpl : imageTpl,
												id : 'bucketDiskDataWin',
												itemSelector : 'div.thumb-wrap',
												emptyText : avmon.dashboard.notFindPartOfRealTimeData,
												store : 'NewImagesStore'
											} ]
										}).center().show();

						var activeGrid = Ext.getCmp('bucketDiskDataWin');
						var activeStoreProxy = activeGrid.getStore().getProxy();
						activeStoreProxy.extraParams.moId = Ext.mainEngine.moId;
						activeGrid.getStore().load();
					},

					onFileSysClick : function(button, e, eOpts)
					{
						var imageFileTpl = new Ext.XTemplate(
								'      <tpl for=".">',
								'    <div class="thumb-wrap">',
								'            <div style="float:left;margin-top:5px;margin-left:30px;margin-right:30px;margin-bottom: 5px;">',
								'      <div style="width:30px;margin-left: 0.5em;margin-top: 0.2em;">{name}<br/></div>',
								'		<iframe scrolling="false" frameborder="0" width="50px" height="75px" src="{src}"></iframe>',
								'      <div style="width:30px;margin-left: 0.5em;margin-top: 0.2em;">{usage}<br/></div>',
								'            </div>', '    </div>',
								'      </tpl>');

						var winMem = Ext
								.create(
										'Ext.window.Window',
										{
											title : 'Filesystem',
											height : 320,
											width : 650,
											constrainHeader : true,
											bodyStyle : 'background:white;',
											autoScroll : true,
											modal : 'false',
											layout : 'fit',
											items : [ {
												xtype : 'dataview',
												tpl : imageFileTpl,
												id : 'bucketFileDataWin',
												itemSelector : 'div.thumb-wrap',
												emptyText : avmon.dashboard.notFindPartOfRealTimeData,
												store : 'NewImagesFileStore'
											} ]
										}).center().show();

						var activeGrid = Ext.getCmp('bucketFileDataWin');
						var activeStoreProxy = activeGrid.getStore().getProxy();
						activeStoreProxy.extraParams.moId = Ext.mainEngine.moId;
						activeGrid.getStore().load();
					},
					onLogClick : function(button, e, eOpts)
					{

						var winMem = Ext.create('Ext.window.Window', {
							title : avmon.dashboard.mplogList,
							height : 320,
							width : 650,
							constrainHeader : true,
							bodyStyle : 'background:white;',
							autoScroll : true,
							modal : 'false',
							layout : 'fit',
							items : [ {
								xtype : 'gridpanel',
								region : 'center',
								id : 'MPLogGrid',
								store : 'MPLogStore',
								columns : [ {
									xtype : 'gridcolumn',
									width : 40,
									dataIndex : 'Alert_Level',
									text : avmon.alarm.level
								}, {
									xtype : 'gridcolumn',
									width : 40,
									dataIndex : 'Keyword',
									text : avmon.dashboard.keyword
								}, {
									xtype : 'gridcolumn',
									dataIndex : 'Logged_by',
									text : avmon.dashboard.logEntry
								}, {
									xtype : 'gridcolumn',
									width : 500,
									defaultWidth : 500,
									dataIndex : 'Data',
									text : avmon.dashboard.content
								}, {
									xtype : 'gridcolumn',
									dataIndex : 'Generator',
									text : avmon.dashboard.producer
								}, {
									xtype : 'gridcolumn',
									width : 60,
									dataIndex : 'Sensor_Type',
									text : avmon.dashboard.sensorType
								}, {
									xtype : 'gridcolumn',
									// hidden: true,
									dataIndex : 'Sensor_Number',
									text : avmon.dashboard.sensorNumber
								}, {
									xtype : 'gridcolumn',
									// hidden: true,
									dataIndex : 'MP_status',
									text : avmon.dashboard.mpStatus
								} ],
								dockedItems : [ {
									xtype : 'pagingtoolbar',
									dock : 'bottom',
									width : 360,
									displayInfo : true,
									store : 'MPLogStore'
								} ]
							} ]
						}).center().show();
						var activeGrid = Ext.getCmp('MPLogGrid');
						var activeStoreProxy = activeGrid.getStore().getProxy();
						activeStoreProxy.extraParams.mo = Ext.mainEngine.moId;
						activeGrid.getStore().load();
					}
				});