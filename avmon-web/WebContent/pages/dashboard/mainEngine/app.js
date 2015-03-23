//@require @packageOverrides
Ext.Loader.setConfig({
    enabled: true
});
Ext.application({
    requires: [
        'MyApp.view.MyViewport'
    ],
    models: [
        'ErrorLogModel',
        'CourseModel',
        'MemModel',
        'CpuModel',
        'SkipFileModel',
        'DiskModel',
        'FileSysModel',
        'AlarmModel',
        'NetworkSendModel',
        'NetworkReceiveModel',
        'BasicInfoModel',
        'Network',
        'ImageModel',
        'BucketModel',
        'NewBucketModel',
        'MPLogModel'
    ],
    stores: [
        'ErrorLogStore',
        'CourseStore',
        'MemStore',
        'CpuStore',
        'SkipFileStore',
        'DiskStore',
        'FileSysStore',
        'AlarmStore',
        'NetworkSendStore',
        'NetworkReceiveStore',
        'CourseSendStore',
        'CourseReceiveStore',
        'MemSendStore',
        'MemReceiveStore',
        'BasicInfoStore',
        'NetworkStore',
		'ImagesStore',
		'NewImagesStore',
		'NewImagesFileStore',
		'MPLogStore'
    ],
    views: [
        'MyViewport'
    ],
    autoCreateViewport: true,
    name: 'MyApp',
    launch: function() {
    Ext.mainEngine.application = this;
        var moId=Ext.mainEngine.moId;
        if (moId != null && moId != '') {
            Ext.mainEngine.application.loadAll();
            // 定制定时刷新
            setInterval(this.refresh_10s, 60000);
        }
    },
    loadErrorLog: function() {
    	if(Ext.mainEngineThreshold.syslog == 0)
    		{
    			Ext.getCmp("syslogImage").setSrc('images/Green.png');
    		}
    	else 
    		{
    			Ext.getCmp("syslogImage").setSrc('images/Red.png');
    		}
    	if(Ext.mainEngineThreshold.maillog == 0)
		{
			Ext.getCmp("maillogImage").setSrc('images/Green.png');
		}
    	else 
		{
    		Ext.getCmp("maillogImage").setSrc('images/Red.png');
    		Ext.getCmp("maillogLabel").addCls('errorlog');
		}
    	if(Ext.mainEngineThreshold.rclog == 0)
		{
			Ext.getCmp("rclogImage").setSrc('images/Green.png');
		}
    	else 
		{
    		Ext.getCmp("rclogImage").setSrc('images/Red.png');
		}
    	Ext.getCmp("syslogLabel").setText(Ext.mainEngineThreshold.syslog);
    	Ext.getCmp("maillogLabel").setText(Ext.mainEngineThreshold.mplog);
    	Ext.getCmp("rclogLabel").setText(Ext.mainEngineThreshold.rclog);
    },
    loadErrorLogNew: function() {
        if (Ext.mainEngine.moId != null && Ext.mainEngine.moId != '') {
            // 使用率
            Ext.Ajax.request({
                url: 'getLogError',
                params: {mo:Ext.mainEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                	if(obj.centerLogLabel == "mplog")
            		{
            			//动态添加点击事件
            			Ext.getCmp("centerLogPanel_2").setVisible(true);
            			Ext.getCmp("centerLogPanel_1").setVisible(false);
            		}else {
                    	Ext.getCmp("centerLogPanel_1").setVisible(true);
                    	Ext.getCmp("centerLogPanel_2").setVisible(false);
                    }
                    if(obj.leftLogNum == 0)
            		{
            			Ext.getCmp("leftLogImage").setSrc('images/Green.png');
            		}
            	else 
            		{
            			Ext.getCmp("leftLogImage").setSrc('images/Red.png');
            		}
            	if(obj.centerLogNum == 0)
        		{
        			Ext.getCmp("centerLogImage").setSrc('images/Green.png');
        		}
            	else 
        		{
            		Ext.getCmp("centerLogImage").setSrc('images/Red.png');
        		}
            	if(obj.rightLogNum == 0)
        		{
        			Ext.getCmp("rightLogImage").setSrc('images/Green.png');
        		}
            	else 
        		{
            		Ext.getCmp("rightLogImage").setSrc('images/Red.png');
        		}
            	Ext.getCmp("leftLogLabel").setText(obj.leftLogLabel);
            	Ext.getCmp("centerLogLabel").setText(obj.centerLogLabel);
            	Ext.getCmp("rightLogLabel").setText(obj.rightLogLabel);
            	Ext.getCmp("leftLogNum").setText(obj.leftLogNum);
            	Ext.getCmp("centerLogNum").setText(obj.centerLogNum);
            	Ext.getCmp("rightLogNum").setText(obj.rightLogNum);
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            });
        }
    },
    loadCourse: function() {
        if (Ext.mainEngine.moId != null && Ext.mainEngine.moId != '') {
            // 使用率
            Ext.Ajax.request({
                url: 'courseBasic',
                params: {mo:Ext.mainEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    var proc_user = obj.USR_PROC_Percent;
                    var level = obj.USR_PROC_Level;
                    //Ext.getCmp("courseChart").store.loadData(obj, false);
                    Ext.getCmp('progressPanel').body.update('<iframe  scrolling="auto" frameborder="0" width="100%" height="100%" align="right" src="../mainEngine/littleColumn.jsp?kpi='+proc_user+'&level='+level+'"></iframe>');
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            });
            // 统计信息
            Ext.Ajax.request({
                url: 'courseBasic',
                params: {mo:Ext.mainEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    Ext.getCmp("courseForm").getForm().setValues(obj);
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            });       
        }
    },
    loadMem: function() {
        if (Ext.mainEngine.moId != null && Ext.mainEngine.moId != '') {
            // 使用率
            Ext.Ajax.request({
                url: 'memUse',
                params: {mo:Ext.mainEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    var mem_user = obj[0].MEM_USR;
                    if(mem_user.indexOf("error") != -1)
                    	{
                    		mem_user = 0;//error 
                    	}
                    var level = obj[0].MEM_LEVEL;
                    Ext.getCmp('memPanel').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../mainEngine/littleColumn.jsp?kpi='+mem_user+'&level='+level+'"></iframe>');
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            });
            // 统计信息
            Ext.Ajax.request({
                url: 'memBasic',
                params: {mo:Ext.mainEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    Ext.getCmp("memForm").getForm().setValues(obj);
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            }); 
        }
    },
    loadCpu: function() {
        if (Ext.mainEngine.moId != null && Ext.mainEngine.moId != '') {

            Ext.Ajax.request({
                url: 'cpuUse',
                params: {mo:Ext.mainEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    var cpu_user = obj[0].CPU_USR;
                    var level = obj[0].CPU_LEVEL;
                    Ext.getCmp('gaugePanel').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../mainEngine/gauge.jsp?cpu_user='+cpu_user+'&level='+level+'"></iframe>');
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            });
        }
    },
    loadSkipFile: function() {
        if (Ext.mainEngine.moId != null && Ext.mainEngine.moId != '') {
            Ext.Ajax.request({
                url: 'skipFileUse',
                params: {mo:Ext.mainEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    var swap_user = obj[0].SKIP_FILE_USE;
                    var level = obj[0].SKIP_LEVEL;
                    Ext.getCmp('swapPanel').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../mainEngine/littleColumn.jsp?kpi='+swap_user+'&level='+level+'"></iframe>');   
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            });
            // 统计信息
            Ext.Ajax.request({
                url: 'skipFileBasic',
                params: {mo:Ext.mainEngine.moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    Ext.getCmp("swapForm").getForm().setValues(obj);
                },
                failure: function(response, opts) {
                    if(console && console.log){
                        console.log("load dashboard-mem error!");
                    }
                }
            }); 
        }
    },
    loadDisk: function() {
        if (Ext.mainEngine.moId != null && Ext.mainEngine.moId != '') {
            var activeGrid = Ext.getCmp('bucketdiskData');
            var activeStoreProxy = activeGrid.getStore().getProxy();
            activeStoreProxy.extraParams.moId = Ext.mainEngine.moId;
            activeGrid.getStore().load();
        }
    },
    loadFileSys: function() {
        if (Ext.mainEngine.moId != null && Ext.mainEngine.moId != '') {
            var activeGrid = Ext.getCmp('bucketfileData');
            var activeStoreProxy = activeGrid.getStore().getProxy();
            activeStoreProxy.extraParams.mo = Ext.mainEngine.moId;
            activeGrid.getStore().load();
        	}
    },
    loadAlarm: function() {},
    refresh_10s: function() {},
    loadBasicInfo: function() {
        var moId = Ext.mainEngine.moId;
        if (moId != null && moId != '') {
            var activeGrid = Ext.getCmp('basicInfoGrid');
            var activeStoreProxy = activeGrid.getStore().getProxy();
            activeStoreProxy.extraParams.treeId = Ext.mainEngine.moId;
            activeGrid.getStore().load();
            Ext.getCmp("hostImage").setSrc('images/SYS_All.png');
            Ext.Ajax.request({
                url: 'basicInfo',
                params: {mo:moId},
                success: function(response, opts) {
                    var obj = Ext.decode(response.responseText);
                    Ext.getCmp("hostImage").setSrc('images/'+obj.hostImage);
                },
                failure: function(response, opts) {
                }
            });
        }
    },
    loadNetwork: function() {
        var moId = Ext.mainEngine.moId;
        if (moId != null && moId != '') {
        	// 使用率
            var activeGrid = Ext.getCmp('networkGrid');
            var activeStoreProxy = activeGrid.getStore().getProxy();
            activeStoreProxy.extraParams.treeId = Ext.mainEngine.moId;
            activeGrid.getStore().load();
        }
    },
    loadNetworkSend: function() {
        Ext.Ajax.request({
            url: 'networkSend',
            params: {mo:Ext.mainEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("networkSendPic").setSrc('images/arrow_G_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("networkSendPic").setSrc('images/arrow_G_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("networkSendPic").setSrc('images/arrow_G_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("networkSendForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadNetworkReceive: function() {
        Ext.Ajax.request({
            url: 'networkReceive',
            params: {mo:Ext.mainEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("netwrokReceivePic").setSrc('images/arrow_R_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("netwrokReceivePic").setSrc('images/arrow_R_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("netwrokReceivePic").setSrc('images/arrow_R_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("networkReceiveForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadSkipfilePagein: function() {
        Ext.Ajax.request({
            url: 'skipfilePagein',
            params: {mo:Ext.mainEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("skipfileInPic").setSrc('images/arrow_G_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("skipfileInPic").setSrc('images/arrow_G_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("skipfileInPic").setSrc('images/arrow_G_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("skipFileInForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadSkipfilePageout: function() {
        Ext.Ajax.request({
            url: 'skipfilePageout',
            params: {mo:Ext.mainEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("skipfileOutPic").setSrc('images/arrow_R_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("skipfileOutPic").setSrc('images/arrow_R_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("skipfileOutPic").setSrc('images/arrow_R_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("skipFileOutForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadDiskWrite: function() {
        Ext.Ajax.request({
            url: 'diskWrite',
            params: {mo:Ext.mainEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("diskWritePic").setSrc('images/arrow_G_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("diskWritePic").setSrc('images/arrow_G_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("diskWritePic").setSrc('images/arrow_G_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("diskRWForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadDiskRead: function() {
        Ext.Ajax.request({
            url: 'diskRead',
            params: {mo:Ext.mainEngine.moId},
            success: function(response, opts) {
                var obj = Ext.decode(response.responseText);
                Ext.getCmp("diskReadChart").store.loadData(obj.history,false);
                var speed = obj.speed;
                var content = obj.content;
                if (speed) {
                    if (speed < 50) {
                        Ext.getCmp("diskReadPic").setSrc('images/arrow_R_01.gif');
                    } else if (speed < 100) {
                        Ext.getCmp("diskReadPic").setSrc('images/arrow_R_02.gif');
                    } else if (100 < speed) {
                        Ext.getCmp("diskReadPic").setSrc('images/arrow_R_03.gif');
                    }
                }
                if (content) {
                    Ext.getCmp("diskReadForm").getForm().setValues(content);
                }
            },
            failure: function(response, opts) {
                if(console && console.log){
                    console.log("load dashboard-mem error!");
                }
            }
        });
    },
    loadModel: function() {
        Ext.getCmp('alarmPanel').body.update('<iframe scrolling="auto" frameborder="0" width="100%" height="100%" src="../alarmSearch/index.jsp?mo=' + Ext.mainEngine.moId + '"></iframe>');
    },
    loadAll: function() {
        Ext.mainEngine.application = this;
        this.loadBasicInfo();
        this.loadNetwork();
        this.loadErrorLogNew();
        this.loadCourse();
        this.loadMem();
        this.loadCpu();
        this.loadSkipFile();
        this.loadDisk();
        this.loadFileSys();
        this.loadNetworkSend();
        this.loadNetworkReceive();
        this.loadSkipfilePagein();
        this.loadSkipfilePageout();
        this.loadDiskWrite();
    }
});
